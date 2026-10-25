/** AI-Generated, 2026-10-18 */
package fun.wraq.process.system.mastery;

import fun.wraq.common.util.StringUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.MasteryPackets.MasteryDataS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

/**
 * 精通系统的服务端数据入口。
 *
 * <p>点数池：旧「能力点」与旧「专精点」合并为 {@link StringUtils#MasteryPoint_Total}。
 * 按设计，总精通点数 = 玩家等级（1 级 = 1 点，升级 +1）；旧池只作为历史来源，
 * 不再分别计数。</p>
 *
 * <p>所有配置均以每棵树一个 int[36] 存于玩家 PersistentData，服务端保存前统一走
 * {@link MasteryAllocation#sanitize} 校验，客户端无法写入非法配置。</p>
 *
 * <p>另保存 5 个精通页：每页一个 180 长度的扁平 int 数组、一个名称和一个当前激活页。</p>
 */
public final class MasteryData {

    public static final int PRESET_COUNT = 5;
    private static final String PRESET_NAME_PREFIX = "MasteryPresetName_";
    private static final String PRESET_DATA_PREFIX = "MasteryPresetData_";
    private static final String PRESET_ACTIVE = "MasteryPreset_Active";
    private static final int PRESET_NAME_LIMIT = 16;

    private MasteryData() {
    }

    public static String keyFor(MasteryTree tree) {
        return "Mastery_" + tree.name();
    }

    public static int[][] read(CompoundTag data) {
        int[][] allocation = MasteryAllocation.empty();
        for (int tree = 0; tree < MasteryAllocation.TREE_COUNT; tree++) {
            int[] stored = data.getIntArray(keyFor(MasteryTree.byIndex(tree)));
            if (stored.length == MasteryAllocation.ROW_COUNT * 3) {
                // 旧版布局：每排 3 个槽位，迁移到每排 6 个槽位
                for (int row = 0; row < MasteryAllocation.ROW_COUNT; row++) {
                    for (int node = 0; node < 3; node++) {
                        allocation[tree][row * MasteryAllocation.MAX_NODES_PER_ROW + node] =
                                stored[row * 3 + node];
                    }
                }
            } else if (stored.length > 0) {
                System.arraycopy(stored, 0, allocation[tree], 0,
                        Math.min(stored.length, MasteryAllocation.NODES_PER_TREE));
            }
        }
        return allocation;
    }

    public static void write(CompoundTag data, int[][] allocation) {
        int[][] copy = MasteryAllocation.copy(allocation);
        for (int tree = 0; tree < MasteryAllocation.TREE_COUNT; tree++) {
            data.putIntArray(keyFor(MasteryTree.byIndex(tree)), copy[tree]);
        }
        data.putInt(StringUtils.MasteryPoint_Used, MasteryAllocation.used(copy));
    }

    /** 登录/打开界面时初始化并校正数据 */
    public static void ensureInitialized(Player player) {
        ensureInitialized(player, player.experienceLevel);
    }

    private static void ensureInitialized(Player player, int fallbackLevel) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(StringUtils.MasteryPoint_Total)) {
            // 两池合并后的总量 = 等级（与旧能力点/专精点不再分别计算）
            data.putInt(StringUtils.MasteryPoint_Total, Math.max(0, fallbackLevel));
            // 旧能力五维按同一对应关系迁移到五条路径，尽量保留玩家原有加点
            int[][] migrated = MasteryAllocation.empty();
            migrateTree(migrated, MasteryTree.PRECISION,
                    data.getInt(StringUtils.Ability.Flexibility));
            migrateTree(migrated, MasteryTree.DOMINATION,
                    data.getInt(StringUtils.Ability.Power));
            migrateTree(migrated, MasteryTree.SORCERY,
                    data.getInt(StringUtils.Ability.Intelligent));
            migrateTree(migrated, MasteryTree.RESOLVE,
                    data.getInt(StringUtils.Ability.Vitality));
            migrateTree(migrated, MasteryTree.INSPIRATION,
                    data.getInt(StringUtils.Ability.Lucky));
            write(data, MasteryAllocation.sanitize(migrated, data.getInt(StringUtils.MasteryPoint_Total)));
        }
        // 点数池与等级保持一致（只向上同步，避免升级事件与调试命令导致点数滞后）
        if (data.getInt(StringUtils.MasteryPoint_Total) < player.experienceLevel) {
            data.putInt(StringUtils.MasteryPoint_Total, player.experienceLevel);
        }
        int total = data.getInt(StringUtils.MasteryPoint_Total);
        int[][] clean = MasteryAllocation.sanitize(read(data), total);
        write(data, clean);
        applyLegacyAbilityMapping(player, clean);
        ensurePresets(player, clean);
        MasteryBonusCache.invalidate(player);
    }

    /**
     * 把旧能力点数按「从第一排开始依次点满」的方式放入一棵树（最多 45 点）。
     */
    private static void migrateTree(int[][] allocation, MasteryTree tree, int oldPoints) {
        int remaining = Math.max(0, Math.min(MasteryAllocation.TREE_CAP, oldPoints));
        int[] points = allocation[tree.ordinal()];
        for (int row = 0; row < MasteryAllocation.ROW_COUNT && remaining > 0; row++) {
            int put = Math.min(remaining, MasteryAllocation.ROW_CAP[row]);
            points[MasteryAllocation.index(row, 0)] = put;
            remaining -= put;
            if (put < MasteryAllocation.ROW_CAP[row]) {
                break;
            }
        }
    }

    /**
     * AI-Generated, 2026-10-18
     * 旧「能力」五维已与精通路径合并：路径总点数直接写入旧属性键，
     * 这样 PlayerAttributes / 主动技能等现有逻辑无需改动即可生效。
     *
     * <p>映射：主宰→力量、巫术→智力、精密→精巧、启迪→启迪、坚决→活力。</p>
     */
    public static void applyLegacyAbilityMapping(Player player, int[][] allocation) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Ability.Power,
                MasteryAllocation.treeTotal(allocation[MasteryTree.DOMINATION.ordinal()]));
        data.putInt(StringUtils.Ability.Intelligent,
                MasteryAllocation.treeTotal(allocation[MasteryTree.SORCERY.ordinal()]));
        data.putInt(StringUtils.Ability.Flexibility,
                MasteryAllocation.treeTotal(allocation[MasteryTree.PRECISION.ordinal()]));
        data.putInt(StringUtils.Ability.Lucky,
                MasteryAllocation.treeTotal(allocation[MasteryTree.INSPIRATION.ordinal()]));
        data.putInt(StringUtils.Ability.Vitality,
                MasteryAllocation.treeTotal(allocation[MasteryTree.RESOLVE.ordinal()]));
        data.putInt(StringUtils.AbilityPoint_Used, MasteryAllocation.used(allocation));
        data.putInt(StringUtils.AbilityPoint_Total,
                data.getInt(StringUtils.MasteryPoint_Total));
    }

    public static int getTotal(Player player) {
        ensureInitialized(player);
        return player.getPersistentData().getInt(StringUtils.MasteryPoint_Total);
    }

    public static int[][] getSanitized(Player player) {
        ensureInitialized(player);
        CompoundTag data = player.getPersistentData();
        return MasteryAllocation.sanitize(read(data), data.getInt(StringUtils.MasteryPoint_Total));
    }

    /**
     * 升级授予精通点。若此前尚未初始化，则直接按新等级初始化（避免重复计数）。
     */
    public static void onLevelUp(Player player, int fromLevel, int toLevel) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(StringUtils.MasteryPoint_Total)) {
            ensureInitialized(player, toLevel);
            return;
        }
        int gained = Math.max(0, toLevel - fromLevel);
        if (gained > 0) {
            data.putInt(StringUtils.MasteryPoint_Total,
                    data.getInt(StringUtils.MasteryPoint_Total) + gained);
            MasteryBonusCache.invalidate(player);
        }
    }

    /** 保存客户端配置（服务端权威校验），并写入当前精通页 */
    public static void save(ServerPlayer player, int[][] requested, int page) {
        int total = getTotal(player);
        int[][] clean = MasteryAllocation.sanitize(requested, total);
        CompoundTag data = player.getPersistentData();
        write(data, clean);
        applyLegacyAbilityMapping(player, clean);
        int activePage = clampPreset(page);
        data.putInt(PRESET_ACTIVE, activePage);
        writePreset(data, activePage, clean);
        MasteryBonusCache.invalidate(player);
        sendData(player, total, clean);
    }

    public static void save(ServerPlayer player, int[][] requested) {
        save(player, requested, getActivePreset(player));
    }

    /** 一键切换到指定精通页 */
    public static void switchPreset(ServerPlayer player, int page) {
        int activePage = clampPreset(page);
        int total = getTotal(player);
        CompoundTag data = player.getPersistentData();
        if (!data.contains(presetDataKey(activePage))) {
            // 首次切换到该页：以当前已保存配置为起点，避免直接清空
            writePreset(data, activePage, getSanitized(player));
        }
        int[][] clean = MasteryAllocation.sanitize(readPreset(data, activePage), total);
        write(data, clean);
        applyLegacyAbilityMapping(player, clean);
        data.putInt(PRESET_ACTIVE, activePage);
        MasteryBonusCache.invalidate(player);
        sendData(player, total, clean);
    }

    /** 重命名精通页 */
    public static void renamePreset(ServerPlayer player, int page, String name) {
        int activePage = clampPreset(page);
        CompoundTag data = player.getPersistentData();
        data.putString(presetNameKey(activePage), sanitizePresetName(name, activePage));
        sendData(player, getTotal(player), getSanitized(player));
    }

    public static String[] getPresetNames(Player player) {
        CompoundTag data = player.getPersistentData();
        String[] names = new String[PRESET_COUNT];
        for (int page = 0; page < PRESET_COUNT; page++) {
            String name = data.getString(presetNameKey(page));
            names[page] = name == null || name.isBlank()
                    ? defaultPresetName(page) : name;
        }
        return names;
    }

    public static int getActivePreset(Player player) {
        return clampPreset(player.getPersistentData().getInt(PRESET_ACTIVE));
    }

    private static void ensurePresets(Player player, int[][] current) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(PRESET_ACTIVE)) {
            data.putInt(PRESET_ACTIVE, 0);
        }
        for (int page = 0; page < PRESET_COUNT; page++) {
            if (!data.contains(presetNameKey(page))) {
                data.putString(presetNameKey(page), defaultPresetName(page));
            }
            if (!data.contains(presetDataKey(page))) {
                // 只初始化第 1 页；其余页在第一次切换时复制当前配置，避免切到“空页”直接清空
                if (page == 0) {
                    writePreset(data, page, current);
                }
            }
        }
    }

    private static void sendData(ServerPlayer player, int total, int[][] allocation) {
        ModNetworking.sendToClient(new MasteryDataS2CPacket(
                total, allocation, getPresetNames(player), getActivePreset(player)), player);
    }

    private static String presetNameKey(int page) {
        return PRESET_NAME_PREFIX + page;
    }

    private static String presetDataKey(int page) {
        return PRESET_DATA_PREFIX + page;
    }

    private static int clampPreset(int page) {
        return Math.max(0, Math.min(PRESET_COUNT - 1, page));
    }

    public static String defaultPresetName(int page) {
        return "配置 " + (clampPreset(page) + 1);
    }

    private static String sanitizePresetName(String name, int page) {
        if (name == null) {
            return defaultPresetName(page);
        }
        String clean = name.replace("§", "").trim();
        if (clean.isEmpty()) {
            return defaultPresetName(page);
        }
        return clean.length() > PRESET_NAME_LIMIT
                ? clean.substring(0, PRESET_NAME_LIMIT) : clean;
    }

    private static void writePreset(CompoundTag data, int page, int[][] allocation) {
        int[][] copy = MasteryAllocation.copy(allocation);
        int[] flat = new int[MasteryAllocation.TREE_COUNT * MasteryAllocation.NODES_PER_TREE];
        for (int tree = 0; tree < MasteryAllocation.TREE_COUNT; tree++) {
            System.arraycopy(copy[tree], 0, flat,
                    tree * MasteryAllocation.NODES_PER_TREE, MasteryAllocation.NODES_PER_TREE);
        }
        data.putIntArray(presetDataKey(page), flat);
    }

    private static int[][] readPreset(CompoundTag data, int page) {
        int[][] allocation = MasteryAllocation.empty();
        int[] flat = data.getIntArray(presetDataKey(page));
        if (flat.length == 0) {
            return allocation;
        }
        for (int tree = 0; tree < MasteryAllocation.TREE_COUNT; tree++) {
            int base = tree * MasteryAllocation.NODES_PER_TREE;
            for (int node = 0; node < MasteryAllocation.NODES_PER_TREE; node++) {
                if (base + node < flat.length) {
                    allocation[tree][node] = flat[base + node];
                }
            }
        }
        return allocation;
    }

    /** 同步完整数据到客户端 */
    public static void sync(ServerPlayer player) {
        ensureInitialized(player);
        sendData(player, getTotal(player), getSanitized(player));
    }
}
