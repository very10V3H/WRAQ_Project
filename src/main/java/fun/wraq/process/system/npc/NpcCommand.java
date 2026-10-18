/** AI-Generated, 2026-10-17 */
package fun.wraq.process.system.npc;

import com.mojang.authlib.properties.Property;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.util.Utils;
import fun.wraq.entities.entities.npc.VmdNpc;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * {@code /vmd npc ...} 管理命令。
 * <p>
 * 设计原则：所有「找 NPC」的操作统一走准星射线（{@link #findTargetNpc}），GM 不需要记坐标；
 * 生成 NPC 时自动从在线玩家抄取 {@code textures} 皮肤属性，使玩家离线/改名后皮肤依然可还原。
 */
public final class NpcCommand {

    /** 准星射线最大距离。 */
    private static final double PICK_DISTANCE = 8.0D;

    private static final List<String> SLOT_NAMES = List.of("hand", "offhand", "head", "chest", "legs", "feet");

    private NpcCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(Utils.MOD_ID)
                .then(Commands.literal("npc")
                        .then(Commands.literal("create")
                                .then(Commands.argument("dialogId", StringArgumentType.string())
                                        .suggests((context, builder) -> SharedSuggestionProvider
                                                .suggest(NpcIds.knownIds(), builder))
                                        .then(Commands.argument("skinName", StringArgumentType.string())
                                                .executes(context -> create(context, false))
                                                .then(Commands.literal("slim")
                                                        .executes(context -> create(context, true))))))
                        .then(Commands.literal("remove")
                                .executes(context -> remove(context)))
                        .then(Commands.literal("set")
                                .then(Commands.literal("name")
                                        .then(Commands.argument("value", StringArgumentType.greedyString())
                                                .executes(context -> setName(context,
                                                        StringArgumentType.getString(context, "value")))))
                                .then(Commands.literal("namejson")
                                        .then(Commands.argument("value", StringArgumentType.greedyString())
                                                .executes(context -> setNameJson(context,
                                                        StringArgumentType.getString(context, "value")))))
                                .then(Commands.literal("skin")
                                        .then(Commands.argument("value", StringArgumentType.string())
                                                .executes(context -> setSkin(context,
                                                        StringArgumentType.getString(context, "value")))))
                                .then(Commands.literal("skinfile")
                                        .then(Commands.argument("value", StringArgumentType.string())
                                                .executes(context -> setSkinFile(context,
                                                        StringArgumentType.getString(context, "value")))))
                                .then(Commands.literal("slim")
                                        .then(Commands.argument("value", BoolArgumentType.bool())
                                                .executes(context -> setSlim(context,
                                                        BoolArgumentType.getBool(context, "value")))))
                                .then(Commands.literal("scale")
                                        .then(Commands.argument("value", FloatArgumentType.floatArg(0.1F, 10.0F))
                                                .executes(context -> setScale(context,
                                                        FloatArgumentType.getFloat(context, "value")))))
                                .then(Commands.literal("lookradius")
                                        .then(Commands.argument("value", FloatArgumentType.floatArg(0.0F, 64.0F))
                                                .executes(context -> setLookRadius(context,
                                                        FloatArgumentType.getFloat(context, "value")))))
                                .then(Commands.literal("sitting")
                                        .then(Commands.argument("value", BoolArgumentType.bool())
                                                .executes(context -> setSitting(context,
                                                        BoolArgumentType.getBool(context, "value")))))
                                .then(Commands.literal("sitoffset")
                                        .then(Commands.argument("value", FloatArgumentType.floatArg(-2.0F, 2.0F))
                                                .executes(context -> setSitOffset(context,
                                                        FloatArgumentType.getFloat(context, "value")))))
                                .then(Commands.literal("dialog")
                                        .then(Commands.argument("value", StringArgumentType.string())
                                                .suggests((context, builder) -> SharedSuggestionProvider
                                                        .suggest(NpcIds.knownIds(), builder))
                                                .executes(context -> setDialogId(context,
                                                        StringArgumentType.getString(context, "value"))))))
                        .then(Commands.literal("equip")
                                .then(Commands.argument("slot", StringArgumentType.word())
                                        .suggests((context, builder) -> SharedSuggestionProvider
                                                .suggest(SLOT_NAMES, builder))
                                        .executes(context -> equip(context,
                                                StringArgumentType.getString(context, "slot")))))
                        .then(Commands.literal("list")
                                .executes(context -> list(context)))
                        .then(Commands.literal("stamp")
                                .executes(context -> stamp(context)))
                        .then(Commands.literal("scan")
                                .executes(context -> scan(context)))
                        .then(Commands.literal("apply")
                                .executes(context -> apply(context)))
                        .then(Commands.literal("dedup")
                                .executes(context -> dedup(context)))
                        .then(Commands.literal("reload")
                                .executes(context -> reload(context)))
                        .then(Commands.literal("save")
                                .executes(context -> save(context)))));
    }

    // ------------------------------------------------------------------
    // create
    // ------------------------------------------------------------------

    private static int create(CommandContext<CommandSourceStack> context, boolean slim)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayerOrException();
        String dialogId = StringArgumentType.getString(context, "dialogId");
        String skinName = StringArgumentType.getString(context, "skinName");

        ServerLevel level = player.serverLevel();
        Vec3 pos = player.pick(4.0D, 0.0F, false).getLocation();
        float yaw = player.getYRot() + 180.0F;

        VmdNpc npc = new VmdNpc(ModEntityType.VMD_NPC.get(), level);
        npc.moveTo(pos.x, pos.y, pos.z, yaw, 0.0F);
        npc.setHomeYaw(yaw);
        npc.setDialogId(dialogId);
        npc.setSlim(slim);
        npc.setSkinName(skinName);
        npc.setCustomName(Te.s(displayNameOrDefault(dialogId)));
        npc.setCustomNameVisible(true);
        copySkinProperty(npc, source, skinName);

        level.addFreshEntity(npc);
        source.sendSuccess(() -> Te.s("[NPC] 已生成 ", displayNameOrDefault(dialogId),
                " (", dialogId, ") 皮肤: ", skinName), true);
        return 1;
    }

    /** 从在线玩家抄取 textures 属性（离线也能还原皮肤的关键）。 */
    private static void copySkinProperty(VmdNpc npc, CommandSourceStack source, String skinName) {
        ServerPlayer owner = source.getServer().getPlayerList().getPlayerByName(skinName);
        if (owner == null) {
            return;
        }
        Collection<Property> textures = owner.getGameProfile().getProperties().get("textures");
        if (textures.isEmpty()) {
            return;
        }
        Property property = textures.iterator().next();
        npc.setSkinProperty(property.getValue(), property.getSignature());
    }

    private static String displayNameOrDefault(String dialogId) {
        String displayName = NpcIds.displayNameOf(dialogId);
        return displayName == null || displayName.isEmpty() ? dialogId : displayName;
    }

    // ------------------------------------------------------------------
    // remove / setXxx
    // ------------------------------------------------------------------

    private static int remove(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayerOrException();
        VmdNpc npc = findTargetNpc(player);
        if (npc == null) {
            return fail(source, Te.s("准星没有指向任何 NPC。"));
        }
        Component name = nameOf(npc);
        npc.discard();
        source.sendSuccess(() -> Te.s("[NPC] 已删除 ", name), true);
        return 1;
    }

    private static int setName(CommandContext<CommandSourceStack> context, String value)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        VmdNpc npc = requireTarget(source);
        if (npc == null) {
            return 0;
        }
        npc.setCustomName(Te.s(value));
        npc.setCustomNameVisible(true);
        source.sendSuccess(() -> Te.s("[NPC] 名字已改为 ", value, "（纯文本，颜色会被清掉；要保留颜色请用 set namejson）"), true);
        return 1;
    }

    /** 用文本组件 JSON 设置名字，保留颜色/样式。 */
    private static int setNameJson(CommandContext<CommandSourceStack> context, String value)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        VmdNpc npc = requireTarget(source);
        if (npc == null) {
            return 0;
        }
        Component parsed;
        try {
            parsed = Component.Serializer.fromJson(value);
        } catch (Exception e) {
            parsed = null;
        }
        if (parsed == null) {
            return fail(source, Te.s("不是合法的文本组件 JSON：", value,
                    "（示例：{\"text\":\"魔导书制作师\",\"color\":\"#BA00DF\"}）"));
        }
        npc.setCustomName(parsed);
        npc.setCustomNameVisible(true);
        source.sendSuccess(() -> Te.s("[NPC] 名字已按组件 JSON 设置（颜色已保留）"), true);
        return 1;
    }

    private static int setSkin(CommandContext<CommandSourceStack> context, String value)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        VmdNpc npc = requireTarget(source);
        if (npc == null) {
            return 0;
        }
        npc.setSkinName(value);
        npc.setSkinFile("");
        copySkinProperty(npc, source, value);
        source.sendSuccess(() -> Te.s("[NPC] 皮肤来源已改为玩家 ", value), true);
        return 1;
    }

    private static int setSkinFile(CommandContext<CommandSourceStack> context, String value)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        VmdNpc npc = requireTarget(source);
        if (npc == null) {
            return 0;
        }
        npc.setSkinFile(value);
        source.sendSuccess(() -> Te.s("[NPC] 已改用内置皮肤 ",
                Utils.MOD_ID + ":textures/entity/npc/" + value + ".png"), true);
        return 1;
    }

    private static int setSlim(CommandContext<CommandSourceStack> context, boolean value)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        VmdNpc npc = requireTarget(source);
        if (npc == null) {
            return 0;
        }
        npc.setSlim(value);
        source.sendSuccess(() -> Te.s("[NPC] 模型已设为 ", value ? "slim" : "wide"), true);
        return 1;
    }

    private static int setScale(CommandContext<CommandSourceStack> context, float value)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        VmdNpc npc = requireTarget(source);
        if (npc == null) {
            return 0;
        }
        npc.setScale(value);
        source.sendSuccess(() -> Te.s("[NPC] 模型缩放已设为 ", String.valueOf(value)), true);
        return 1;
    }

    private static int setLookRadius(CommandContext<CommandSourceStack> context, float value)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        VmdNpc npc = requireTarget(source);
        if (npc == null) {
            return 0;
        }
        npc.setLookRadius(value);
        source.sendSuccess(() -> Te.s("[NPC] 注视半径已设为 ", String.valueOf(value)), true);
        return 1;
    }

    private static int setSitting(CommandContext<CommandSourceStack> context, boolean value)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        VmdNpc npc = requireTarget(source);
        if (npc == null) {
            return 0;
        }
        npc.setSitting(value);
        source.sendSuccess(() -> Te.s("[NPC] 坐姿已设为 ", value ? "开" : "关"), true);
        return 1;
    }

    private static int setSitOffset(CommandContext<CommandSourceStack> context, float value)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        VmdNpc npc = requireTarget(source);
        if (npc == null) {
            return 0;
        }
        npc.setSitOffset(value);
        source.sendSuccess(() -> Te.s("[NPC] 坐姿下移量已设为 ", String.valueOf(value),
                "（正值向下，0 = 纯姿态）"), true);
        return 1;
    }

    private static int setDialogId(CommandContext<CommandSourceStack> context, String value)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        VmdNpc npc = requireTarget(source);
        if (npc == null) {
            return 0;
        }
        npc.setDialogId(value);
        source.sendSuccess(() -> Te.s("[NPC] 对话 id 已设为 ", value), true);
        return 1;
    }

    // ------------------------------------------------------------------
    // equip
    // ------------------------------------------------------------------

    private static int equip(CommandContext<CommandSourceStack> context, String slotName)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayerOrException();
        VmdNpc npc = requireTarget(source);
        if (npc == null) {
            return 0;
        }
        EquipmentSlot slot = toSlot(slotName);
        if (slot == null) {
            return fail(source, Te.s("未知槽位: ", slotName, "（可选 hand/offhand/head/chest/legs/feet）"));
        }
        ItemStack stack = player.getMainHandItem();
        if (stack.isEmpty()) {
            return fail(source, Te.s("手上没有物品可装备。"));
        }
        npc.setItemSlot(slot, stack.copy());
        npc.setDropChance(slot, 0.0F);
        Component itemName = stack.getHoverName();
        source.sendSuccess(() -> Te.s("[NPC] 已装备 ", itemName, " -> ", slotName), true);
        return 1;
    }

    private static EquipmentSlot toSlot(String slotName) {
        return switch (slotName.toLowerCase()) {
            case "hand" -> EquipmentSlot.MAINHAND;
            case "offhand" -> EquipmentSlot.OFFHAND;
            case "head" -> EquipmentSlot.HEAD;
            case "chest" -> EquipmentSlot.CHEST;
            case "legs" -> EquipmentSlot.LEGS;
            case "feet" -> EquipmentSlot.FEET;
            default -> null;
        };
    }

    // ------------------------------------------------------------------
    // list / stamp
    // ------------------------------------------------------------------

    private static int list(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        int count = 0;
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (!(entity instanceof VmdNpc npc)) {
                    continue;
                }
                count++;
                Component name = nameOf(npc);
                String line = String.format("%s | id=%s | %s | %.1f %.1f %.1f | skin=%s",
                        level.dimension().location(), npc.getDialogId(), name.getString(),
                        npc.getX(), npc.getY(), npc.getZ(), skinSourceOf(npc));
                source.sendSuccess(() -> Te.s(line), false);
            }
        }
        int total = count;
        source.sendSuccess(() -> Te.s("[NPC] 共 ", String.valueOf(total), " 个（仅统计已加载区块）"), false);
        return count;
    }

    private static String skinSourceOf(VmdNpc npc) {
        if (!npc.getSkinFile().isEmpty()) {
            return "file:" + npc.getSkinFile();
        }
        if (!npc.getSkinProperty().isEmpty()) {
            return "property";
        }
        if (!npc.getSkinName().isEmpty()) {
            return "name:" + npc.getSkinName();
        }
        return "default";
    }

    /**
     * 给附近原版村民补写 {@code vmd.npcId}（幂等）。
     * 这是可选操作：不补写也能靠「显示名 → id」回退正常工作。
     */
    private static int stamp(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayerOrException();
        int stamped = 0;
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof VmdNpc || !(entity instanceof LivingEntity)) {
                    continue;
                }
                String dialogId = NpcIds.byDisplayName(NpcIdResolver.displayNameOf(entity));
                if (dialogId == null) {
                    continue;
                }
                if (dialogId.equals(NpcIdResolver.storedId(entity))) {
                    continue;
                }
                NpcIdResolver.stamp(entity, dialogId);
                stamped++;
                Component name = nameOf(entity);
                source.sendSuccess(() -> Te.s("[NPC] 已补写 ", name, " -> ", dialogId), false);
            }
        }
        int total = stamped;
        source.sendSuccess(() -> Te.s("[NPC] 补写完成，共 ", String.valueOf(total), " 个实体"), true);
        return stamped;
    }

    // ------------------------------------------------------------------
    // reload / save（配置文件）
    // ------------------------------------------------------------------

    private static int reload(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String result = NpcRegistry.reload(source.getServer());
        source.sendSuccess(() -> Te.s("[NPC] reload: ", result), true);
        return 1;
    }

    private static int scan(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String result = NpcRegistry.scan(source.getServer());
        source.sendSuccess(() -> Te.s("[NPC] scan: ", result), true);
        return 1;
    }

    /** 立即按配置转换/清理（等价于重启时的自动逻辑，但不用重启）。 */
    private static int apply(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String result = NpcRegistry.applyNow(source.getServer());
        source.sendSuccess(() -> Te.s("[NPC] apply: ", result), true);
        return 1;
    }

    /** 主动去重：同一位置同名的 NPC 只保留一只。 */
    private static int dedup(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        int removed = 0;
        for (ServerLevel level : source.getServer().getAllLevels()) {
            removed += NpcRegistry.dedupLevel(level);
        }
        int total = removed;
        source.sendSuccess(() -> Te.s("[NPC] dedup: 删除重复 NPC ", String.valueOf(total),
                " 只（仅统计已加载区块）"), true);
        return total;
    }

    private static int save(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String result = NpcRegistry.save(source.getServer());
        source.sendSuccess(() -> Te.s("[NPC] save: ", result), true);
        return 1;
    }

    // ------------------------------------------------------------------
    // 工具
    // ------------------------------------------------------------------

    private static VmdNpc requireTarget(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        VmdNpc npc = findTargetNpc(player);
        if (npc == null) {
            fail(source, Te.s("准星没有指向任何 NPC。"));
            return null;
        }
        return npc;
    }

    /** 准星射线命中的最近 NPC。 */
    private static VmdNpc findTargetNpc(ServerPlayer player) {
        Vec3 eye = player.getEyePosition();
        Vec3 end = eye.add(player.getLookAngle().scale(PICK_DISTANCE));
        AABB searchBox = new AABB(eye, end).inflate(1.0D);
        List<VmdNpc> candidates = player.level().getEntitiesOfClass(VmdNpc.class, searchBox,
                npc -> npc.getBoundingBox().inflate(0.3D).clip(eye, end).isPresent());
        return candidates.stream()
                .min(Comparator.comparingDouble(npc -> npc.distanceToSqr(player)))
                .orElse(null);
    }

    private static Component nameOf(Entity entity) {
        Component customName = entity.getCustomName();
        return customName != null ? customName : entity.getName();
    }

    private static int fail(CommandSourceStack source, Component content) {
        source.sendFailure(content);
        return 0;
    }
}
