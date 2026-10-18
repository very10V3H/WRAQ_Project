/** AI-Generated, 2026-10-17 */
package fun.wraq.process.system.npc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.entities.entities.npc.VmdNpc;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NPC 配置注册表（{@code config/vmd_npcs.json}，跨存档复用）。
 * <p>
 * 目的：替代 CustomNPCs 图形化编辑器的「布局可复现」能力。GM 在游戏里摆好 NPC 后
 * 用 {@code /vmd npc save} 写盘；换世界/重置资料片后用 {@code /vmd npc reload} 一键还原。
 * <p>
 * 对齐策略（幂等）：以 {@code dialogId + 维度 + 4 格内} 作为同一只 NPC 的判定条件，
 * 命中则更新（名字/皮肤/模型/缩放/注视半径/坐标），未命中则补建。
 * <b>不删除</b>配置里没有的 NPC——世界里可能有 GM 临时放置的，误删代价远大于留一个孤儿；
 * 孤儿只在 reload 结果里报告数量。
 */
public final class NpcRegistry {

    private static final String FILE_NAME = "vmd_npcs.json";

    /** 同一只 NPC 的判定半径（格）。 */
    private static final double MATCH_DISTANCE_SQR = 16.0D;

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private NpcRegistry() {
    }

    public static Path file() {
        return FMLPaths.CONFIGDIR.get().resolve(FILE_NAME);
    }

    // ------------------------------------------------------------------
    // 数据模型
    // ------------------------------------------------------------------

    /** 一条 NPC 配置。字段保持 public 以便 Gson 直接读写。 */
    public static class NpcDef {
        public String dialogId = "";
        public String name = "";
        /** 原始 Component JSON（保留颜色/样式）；为空时用 name 生成纯文本名字。 */
        public String nameJson = "";
        public String dimension = Level.OVERWORLD.location().toString();
        public double x;
        public double y;
        public double z;
        public float yaw;
        /** file / property / name / default（未指定时统一用内置 civil 皮肤） */
        public String skinType = "file";
        public String skinValue = "civil";
        public String skinSignature = "";
        public boolean slim;
        public float lookRadius = 8.0F;
        public float scale = 1.0F;
        /** 坐姿（骑乘姿态渲染，不真的骑乘实体）。 */
        public boolean sitting;
        /** 坐姿下模型下移格数（贴合椅子/地面，正值向下）。 */
        public float sitOffset = 0.0F;

        public static NpcDef of(VmdNpc npc, ResourceKey<Level> dimension) {
            NpcDef def = new NpcDef();
            def.dialogId = npc.getDialogId();
            def.name = NpcIdResolver.displayNameOf(npc);
            def.nameJson = npc.getCustomName() == null ? "" : Component.Serializer.toJson(npc.getCustomName());
            def.dimension = dimension.location().toString();
            def.x = npc.getX();
            def.y = npc.getY();
            def.z = npc.getZ();
            def.yaw = npc.getHomeYaw();
            def.slim = npc.isSlim();
            def.lookRadius = npc.getLookRadius();
            def.scale = npc.getScale();
            def.sitting = npc.isSitting();
            def.sitOffset = npc.getSitOffset();
            if (!npc.getSkinFile().isEmpty()) {
                def.skinType = "file";
                def.skinValue = npc.getSkinFile();
            } else if (!npc.getSkinProperty().isEmpty()) {
                def.skinType = "property";
                def.skinValue = npc.getSkinProperty();
                def.skinSignature = npc.getSkinSignature();
            } else if (!npc.getSkinName().isEmpty()) {
                def.skinType = "name";
                def.skinValue = npc.getSkinName();
            } else {
                def.skinType = "file";
                def.skinValue = "civil";
            }
            return def;
        }

        /** 从还没转换的原版村民生成一条配置（扫描/固化用）。 */
        public static NpcDef ofVillager(Villager villager, ResourceKey<Level> dimension, String dialogId) {
            NpcDef def = new NpcDef();
            def.dialogId = dialogId;
            def.name = NpcIdResolver.displayNameOf(villager);
            def.nameJson = villager.getCustomName() == null ? "" : Component.Serializer.toJson(villager.getCustomName());
            def.dimension = dimension.location().toString();
            def.x = villager.getX();
            def.y = villager.getY();
            def.z = villager.getZ();
            def.yaw = villager.getYHeadRot();
            def.skinType = "file";
            def.skinValue = "civil";
            return def;
        }
    }

    // ------------------------------------------------------------------
    // reload / 服务端启动应用：按配置幂等对齐
    // ------------------------------------------------------------------

    /**
     * 服务端启动（世界加载完成）时自动执行一次：配置里每个位置上都应该有 VMD NPC。
     * <p>
     * 判定与动作：
     * <ul>
     *     <li>该位置已有 {@link VmdNpc} → 按配置刷新（名字/皮肤/模型/朝向）；</li>
     *     <li>该位置是原版村民 → <b>就地转换</b>（继承其自定义名与位置，删掉村民再生成 NPC）；</li>
     *     <li>什么也没有 → 生成一只（默认 civil 皮肤）。</li>
     * </ul>
     * 只处理配置里列出的位置，不会动世界里其他村民。
     */
    public static String applyOnServerStart(MinecraftServer server) {
        return apply(server, true);
    }

    /**
     * 读配置并应用（不删除/转换村民，GM 手动触发的场景）。
     *
     * @return 人类可读的结果摘要
     */
    public static String reload(MinecraftServer server) {
        return apply(server, false);
    }

    /** 立即执行一次「转换 + 清理」，供 {@code /vmd npc apply} 使用。 */
    public static String applyNow(MinecraftServer server) {
        return apply(server, true);
    }

    private static String apply(MinecraftServer server, boolean convertVillagers) {
        List<NpcDef> defs = dedupeDefs(read());
        if (defs.isEmpty()) {
            return "配置为空或不存在：" + file();
        }
        int updated = 0;
        int spawned = 0;
        int converted = 0;
        int purged = 0;
        int deduped = 0;
        List<String> orphanDimensions = new ArrayList<>();
        Map<ResourceKey<Level>, ServerLevel> levels = new HashMap<>();
        defs.forEach(def -> levels.putIfAbsent(dimensionKey(def.dimension), server.getLevel(dimensionKey(def.dimension))));

        for (NpcDef def : defs) {
            ResourceKey<Level> key = dimensionKey(def.dimension);
            ServerLevel level = levels.get(key);
            if (level == null) {
                continue;
            }
            // 目标区块可能还没加载：不加载就查不到该位置的实体，会误判为「没有 NPC」而重复生成
            level.getChunk(((int) Math.floor(def.x)) >> 4, ((int) Math.floor(def.z)) >> 4);
            VmdNpc existing = findExisting(level, def);
            // 该位置附近的所有原版村民：转换时取最近的一只当锚点，其余一律删除
            List<Villager> nearby = convertVillagers ? villagersNear(level, def) : List.of();

            if (existing != null) {
                applyTo(existing, def, level);
                updated++;
                // 已经转换过：把位置上残留的原版村民一并删掉（历史遗留/位置漂移时常见）
                purged += discardAll(nearby);
                continue;
            }
            if (!nearby.isEmpty()) {
                Villager anchor = nearby.get(0);
                Component anchorName = anchor.getCustomName();
                Vec3 anchorPos = anchor.position();
                float anchorYaw = anchor.getYHeadRot();
                int removed = discardAll(nearby);
                converted += removed;
                // 用村民真实位置覆盖配置坐标，避免下次启动判定漂移
                def.x = anchorPos.x;
                def.y = anchorPos.y;
                def.z = anchorPos.z;
                def.yaw = anchorYaw;
                spawn(level, def, anchorName);
                continue;
            }
            spawn(level, def, null);
            spawned++;
        }

        for (Map.Entry<ResourceKey<Level>, ServerLevel> entry : levels.entrySet()) {
            ServerLevel level = entry.getValue();
            if (level == null) {
                continue;
            }
            int duplicates = dedupLevel(level);
            deduped += duplicates;
            int orphans = countOrphans(level, defs);
            if (orphans > 0) {
                orphanDimensions.add(level.dimension().location() + " x" + orphans);
            }
        }

        String orphanInfo = orphanDimensions.isEmpty() ? "无" : String.join(", ", orphanDimensions);
        return String.format("更新 %d，新建 %d，转换/删除村民 %d（其中已存在 NPC 时清理 %d），去重删除 %d；配置外的 NPC（未删除）: %s",
                updated, spawned, converted, purged, deduped, orphanInfo);
    }

    private static void applyTo(VmdNpc npc, NpcDef def, ServerLevel level) {
        npc.moveTo(def.x, def.y, def.z, def.yaw, 0.0F);
        npc.setHomeYaw(def.yaw);
        npc.setDialogId(def.dialogId);
        npc.setSlim(def.slim);
        npc.setLookRadius(def.lookRadius);
        npc.setScale(def.scale);
        npc.setSitting(def.sitting);
        npc.setSitOffset(def.sitOffset);
        if ((def.name != null && !def.name.isEmpty()) || (def.nameJson != null && !def.nameJson.isEmpty())) {
            // 用 componentOf 而不是 Te.s，才能保留配置里的颜色（nameJson）
            npc.setCustomName(componentOf(def));
        }
        applySkin(npc, def, level);
    }

    private static void applySkin(VmdNpc npc, NpcDef def, ServerLevel level) {
        String type = def.skinType == null ? "default" : def.skinType;
        switch (type) {
            case "file" -> {
                npc.setSkinFile(def.skinValue);
                npc.setSkinProperty("", "");
                npc.setSkinName("");
            }
            case "property" -> {
                npc.setSkinFile("");
                npc.setSkinProperty(def.skinValue, def.skinSignature);
                npc.setSkinName("");
            }
            case "name" -> {
                npc.setSkinFile("");
                npc.setSkinName(def.skinValue);
                npc.setSkinProperty("", "");
                captureSkinProperty(npc, level, def.skinValue);
            }
            default -> {
                // 默认统一用内置 civil 皮肤（决策：先用 civil，之后再逐 NPC 换图）
                npc.setSkinFile("civil");
                npc.setSkinName("");
                npc.setSkinProperty("", "");
            }
        }
    }

    /** 指定玩家刚好在线时，顺手抄一份 textures 属性，等价于用命令创建 NPC 的效果。 */
    private static void captureSkinProperty(VmdNpc npc, ServerLevel level, String skinName) {
        ServerPlayer owner = level.getServer().getPlayerList().getPlayerByName(skinName);
        if (owner == null) {
            return;
        }
        Collection<com.mojang.authlib.properties.Property> textures =
                owner.getGameProfile().getProperties().get("textures");
        if (textures.isEmpty()) {
            return;
        }
        com.mojang.authlib.properties.Property property = textures.iterator().next();
        npc.setSkinProperty(property.getValue(), property.getSignature());
    }

    // ------------------------------------------------------------------
    // 供实体加载期使用的查询（NpcVillagerFilter）
    // ------------------------------------------------------------------

    /** 配置缓存：村民加载时高频查询，避免每次都读盘解析 JSON。 */
    private static List<NpcDef> cachedDefs;

    private static List<NpcDef> defs() {
        if (cachedDefs == null) {
            cachedDefs = read();
        }
        return cachedDefs;
    }

    /** 该坐标命中的配置条目（同维度 4 格内），没有则返回 {@code null}。 */
    public static NpcDef findDefAt(ServerLevel level, Vec3 position) {
        String dimension = level.dimension().location().toString();
        for (NpcDef def : defs()) {
            if (!def.dimension.equals(dimension)) {
                continue;
            }
            double dx = position.x - def.x;
            double dy = position.y - def.y;
            double dz = position.z - def.z;
            if (dx * dx + dy * dy + dz * dz <= MATCH_DISTANCE_SQR) {
                return def;
            }
        }
        return null;
    }

    /**
     * 确保该配置位置上有且只有一只 VMD NPC。
     * <p>
     * 已有 NPC → 按配置刷新；没有 → 以 {@code anchor}（被拦下的村民）的名字与朝向生成。
     */
    public static void ensureNpc(ServerLevel level, NpcDef def, Villager anchor) {
        VmdNpc existing = findExisting(level, def);
        if (existing != null) {
            applyTo(existing, def, level);
            dedupAround(level, existing);
            return;
        }
        Component name = anchor == null ? null : anchor.getCustomName();
        if (anchor != null) {
            // 用村民的真实位置/朝向覆盖配置，让后续判定稳定命中同一只
            Vec3 position = anchor.position();
            def.x = position.x;
            def.y = position.y;
            def.z = position.z;
            def.yaw = anchor.getYHeadRot();
        }
        VmdNpc created = spawn(level, def, name);
        dedupAround(level, created);
    }

    // ------------------------------------------------------------------
    // 主动去重：同一位置同名 NPC 只保留一只
    // ------------------------------------------------------------------

    /**
     * 扫描整个维度并去重：同一「4 格内 + 同名（dialogId 或显示名相同）」只保留一只。
     * <p>
     * 重复来源：坐标漂移导致 4 格判定错过、多次 apply/scan、旧世界误生成等。
     * 保留优先级：命中配置且离配置坐标更近的优先，其次实体 id 更小（更早存在）的优先。
     *
     * @return 被删除的数量
     */
    public static int dedupLevel(ServerLevel level) {
        List<VmdNpc> npcs = new ArrayList<>();
        for (Entity entity : level.getAllEntities()) {
            if (entity instanceof VmdNpc npc) {
                npcs.add(npc);
            }
        }
        if (npcs.size() < 2) {
            return 0;
        }
        npcs.sort(preferenceOrder(level));
        int removed = 0;
        for (int i = 0; i < npcs.size(); i++) {
            VmdNpc kept = npcs.get(i);
            if (kept.isRemoved()) {
                continue;
            }
            for (int j = i + 1; j < npcs.size(); j++) {
                VmdNpc other = npcs.get(j);
                if (other.isRemoved() || !isSameNpc(kept, other)) {
                    continue;
                }
                other.discard();
                removed++;
            }
        }
        return removed;
    }

    /** 以某只 NPC 为中心去重（实体加载时调用，避免等下次启动）。 */
    public static int dedupAround(ServerLevel level, VmdNpc self) {
        if (self.isRemoved()) {
            return 0;
        }
        List<VmdNpc> group = new ArrayList<>();
        group.add(self);
        for (Entity entity : level.getAllEntities()) {
            if (entity instanceof VmdNpc npc && npc != self && isSameNpc(self, npc)) {
                group.add(npc);
            }
        }
        if (group.size() < 2) {
            return 0;
        }
        group.sort(preferenceOrder(level));
        VmdNpc kept = group.get(0);
        int removed = 0;
        for (int i = 1; i < group.size(); i++) {
            if (!group.get(i).isRemoved()) {
                group.get(i).discard();
                removed++;
            }
        }
        if (removed > 0) {
            // 保留的若是被刷新过的那只，按配置再对齐一次
            NpcDef def = findDefAt(level, kept.position());
            if (def != null) {
                applyTo(kept, def, level);
            }
        }
        return removed;
    }

    /** 两只 NPC 是否算「同一只」：4 格内且 dialogId 或显示名相同。 */
    private static boolean isSameNpc(VmdNpc a, VmdNpc b) {
        if (a.distanceToSqr(b) > MATCH_DISTANCE_SQR) {
            return false;
        }
        String idA = a.getDialogId();
        String idB = b.getDialogId();
        if (!idA.isEmpty() && !idB.isEmpty() && idA.equals(idB)) {
            return true;
        }
        return NpcIdResolver.displayNameOf(a).equals(NpcIdResolver.displayNameOf(b));
    }

    /** 去重时的保留优先级：命中配置且更近优先 → 实体 id 更小（更早存在）优先。 */
    private static Comparator<VmdNpc> preferenceOrder(ServerLevel level) {
        return Comparator
                .comparingInt((VmdNpc npc) -> findDefAt(level, npc.position()) == null ? 1 : 0)
                .thenComparingDouble(npc -> {
                    NpcDef def = findDefAt(level, npc.position());
                    return def == null ? 0.0D : npc.position().distanceToSqr(def.x, def.y, def.z);
                })
                .thenComparingInt(VmdNpc::getId);
    }

    /** 配置层面的去重：同维度 + 同 dialogId + 4 格内只保留第一条。 */
    private static List<NpcDef> dedupeDefs(List<NpcDef> defs) {
        List<NpcDef> unique = new ArrayList<>();
        for (NpcDef def : defs) {
            boolean dup = false;
            for (NpcDef kept : unique) {
                if (!kept.dimension.equals(def.dimension) || !kept.dialogId.equals(def.dialogId)) {
                    continue;
                }
                double dx = kept.x - def.x;
                double dy = kept.y - def.y;
                double dz = kept.z - def.z;
                if (dx * dx + dy * dy + dz * dz <= MATCH_DISTANCE_SQR) {
                    dup = true;
                    break;
                }
            }
            if (!dup) {
                unique.add(def);
            }
        }
        return unique;
    }

    private static VmdNpc findExisting(ServerLevel level, NpcDef def) {
        VmdNpc best = null;
        double bestDistance = MATCH_DISTANCE_SQR;
        for (Entity entity : level.getAllEntities()) {
            if (!(entity instanceof VmdNpc npc)) {
                continue;
            }
            if (!def.dialogId.equals(npc.getDialogId())) {
                continue;
            }
            double distance = npc.distanceToSqr(def.x, def.y, def.z);
            if (distance <= bestDistance) {
                bestDistance = distance;
                best = npc;
            }
        }
        return best;
    }

    /**
     * 配置位置附近（4 格内）的所有原版村民，按距离从近到远。
     * <p>
     * 第一只当作「转换锚点」继承名字/坐标/朝向，其余直接删除——
     * 店铺里常有多只村民挤在一起，只转换一只会留下占位残留。
     */
    private static List<Villager> villagersNear(ServerLevel level, NpcDef def) {
        List<Villager> villagers = new ArrayList<>();
        for (Entity entity : level.getAllEntities()) {
            if (entity instanceof Villager villager
                    && villager.distanceToSqr(def.x, def.y, def.z) <= MATCH_DISTANCE_SQR) {
                villagers.add(villager);
            }
        }
        villagers.sort(Comparator.comparingDouble(villager -> villager.distanceToSqr(def.x, def.y, def.z)));
        return villagers;
    }

    /** 删除一批村民，返回实际删除数量。 */
    private static int discardAll(List<Villager> villagers) {
        int removed = 0;
        for (Villager villager : villagers) {
            if (!villager.isRemoved()) {
                villager.discard();
                removed++;
            }
        }
        return removed;
    }

    private static int countOrphans(ServerLevel level, List<NpcDef> defs) {
        int orphans = 0;
        for (Entity entity : level.getAllEntities()) {
            if (!(entity instanceof VmdNpc npc)) {
                continue;
            }
            boolean matched = false;
            for (NpcDef def : defs) {
                if (def.dialogId.equals(npc.getDialogId())
                        && npc.distanceToSqr(def.x, def.y, def.z) <= MATCH_DISTANCE_SQR) {
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                orphans++;
            }
        }
        return orphans;
    }

    private static VmdNpc spawn(ServerLevel level, NpcDef def, Component forcedName) {
        VmdNpc npc = new VmdNpc(ModEntityType.VMD_NPC.get(), level);
        npc.moveTo(def.x, def.y, def.z, def.yaw, 0.0F);
        npc.setHomeYaw(def.yaw);
        npc.setDialogId(def.dialogId);
        npc.setSlim(def.slim);
        npc.setLookRadius(def.lookRadius);
        npc.setScale(def.scale);
        npc.setSitting(def.sitting);
        npc.setSitOffset(def.sitOffset);
        Component displayName = forcedName;
        if (displayName == null) {
            displayName = componentOf(def);
        }
        npc.setCustomName(displayName);
        npc.setCustomNameVisible(true);
        applySkin(npc, def, level);
        level.addFreshEntity(npc);
        return npc;
    }

    /** 优先用配置里的原始 Component JSON（保留颜色），否则用纯文本 name 兜底。 */
    private static Component componentOf(NpcDef def) {
        if (def.nameJson != null && !def.nameJson.isEmpty()) {
            try {
                Component parsed = Component.Serializer.fromJson(def.nameJson);
                if (parsed != null) {
                    return parsed;
                }
            } catch (Exception ignored) {
                // 配置写坏时退回纯文本，不让启动流程中断
            }
        }
        String fallback = def.name == null || def.name.isEmpty() ? def.dialogId : def.name;
        return fun.wraq.common.fast.Te.s(fallback);
    }

    // ------------------------------------------------------------------
    // scan：把世界里的旧村民补录进配置
    // ------------------------------------------------------------------

    /**
     * 扫描当前世界（仅已加载区块）里带自定义名的原版村民，把「VMD 认识的名字」
     * （对话型 id 或交易商人名）补录进配置文件。已存在同位置条目则跳过。
     *
     * @return 人类可读的结果摘要
     */
    public static String scan(MinecraftServer server) {
        List<NpcDef> defs = new ArrayList<>(read());
        int added = 0;
        for (ServerLevel level : server.getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (!(entity instanceof Villager villager) || villager.getCustomName() == null) {
                    continue;
                }
                String dialogId = knownDialogIdOf(villager.getCustomName());
                if (dialogId == null) {
                    continue;
                }
                NpcDef def = NpcDef.ofVillager(villager, level.dimension(), dialogId);
                if (containsSimilar(defs, def)) {
                    continue;
                }
                defs.add(def);
                added++;
            }
        }
        write(defs);
        return String.format("扫描完成：新增 %d 条，配置共 %d 条 -> %s", added, defs.size(), file());
    }

    private static boolean containsSimilar(List<NpcDef> defs, NpcDef def) {
        for (NpcDef d : defs) {
            if (!d.dimension.equals(def.dimension) || !d.dialogId.equals(def.dialogId)) {
                continue;
            }
            double dx = d.x - def.x;
            double dz = d.z - def.z;
            if (dx * dx + dz * dz <= MATCH_DISTANCE_SQR) {
                return true;
            }
        }
        return false;
    }

    private static String knownDialogIdOf(Component name) {
        return name == null ? null : NpcIds.resolveKnown(name.getString());
    }

    // ------------------------------------------------------------------
    // save：把世界里的 NPC 写回配置
    // ------------------------------------------------------------------

    /**
     * 导出当前所有已加载的 NPC 到配置文件。
     *
     * @return 人类可读的结果摘要
     */
    public static String save(MinecraftServer server) {
        List<NpcDef> defs = new ArrayList<>();
        for (ServerLevel level : server.getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof VmdNpc npc) {
                    defs.add(NpcDef.of(npc, level.dimension()));
                } else if (entity instanceof Villager villager && villager.getCustomName() != null) {
                    // 还没转换的旧村民也要固化，否则先 save 再 reload 会丢条目
                    String dialogId = knownDialogIdOf(villager.getCustomName());
                    if (dialogId != null) {
                        defs.add(NpcDef.ofVillager(villager, level.dimension(), dialogId));
                    }
                }
            }
        }
        defs.sort((a, b) -> {
            int byDimension = a.dimension.compareTo(b.dimension);
            return byDimension != 0 ? byDimension : a.dialogId.compareTo(b.dialogId);
        });
        write(defs);
        return String.format("已导出 %d 个 NPC 到 %s（仅包含已加载区块）", defs.size(), file());
    }

    // ------------------------------------------------------------------
    // 文件读写
    // ------------------------------------------------------------------

    public static List<NpcDef> read() {
        Path path = file();
        if (!Files.exists(path)) {
            return List.of();
        }
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            List<NpcDef> defs = GSON.fromJson(reader, new TypeToken<List<NpcDef>>() {
            }.getType());
            return defs == null ? List.of() : defs;
        } catch (Exception e) {
            return List.of();
        }
    }

    public static void write(List<NpcDef> defs) {
        cachedDefs = defs;   // 同步刷新缓存，避免写完还读到旧数据
        Path path = file();
        try {
            Files.createDirectories(path.getParent());
            try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                GSON.toJson(defs, writer);
            }
        } catch (IOException ignored) {
            // 配置目录不可写时静默失败：不影响服务器运行，reload/save 会再次暴露问题
        }
    }

    private static ResourceKey<Level> dimensionKey(String dimension) {
        return ResourceKey.create(Registries.DIMENSION, new ResourceLocation(dimension));
    }

    /** 便于命令层展示坐标。 */
    public static Vec3 positionOf(NpcDef def) {
        return new Vec3(def.x, def.y, def.z);
    }
}
