package fun.wraq.process.system.afk;

import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.jungle.JungleMobSpawnController;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.afk.AfkDataS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

/**
 * AFK挂机刷怪系统 - 玩家离线后自动扫荡怪物，模拟MMORPG的挂机刷怪。
 * AI-Generated, 2026-05-17
 */
public class AfkSystem {

    private static final String AFK_SELECTED_MOB = "AFK_SELECTED_MOB_ID";
    private static final String AFK_LAST_HARVEST_TIME = "AFK_LAST_HARVEST_TIME";

    /** 每秒模拟击杀数 */
    public static final double KILLS_PER_SECOND = 1.0;

    /** 所有可选的怪物类型 */
    public static final Map<String, AfkMobType> MOB_TYPES = new LinkedHashMap<>();

    /**
     * 怪物类型记录
     */
    public record AfkMobType(String id, String displayName, List<ItemAndRate> dropList, String category) {
        public Component displayComponent() {
            return Component.literal(displayName);
        }
    }

    /**
     * 注册一个MobSpawnController子类为可扫荡类型
     */
    public static void register(MobSpawnController controller) {
        String key = controller.getKillCountDataKey();
        if (key == null || key.isEmpty()) return;
        String name = controller.mobName != null ? controller.mobName.getString() : key;
        MOB_TYPES.putIfAbsent(key, new AfkMobType(key, name, controller.getDropList(), "普通怪物"));
    }

    /**
     * 注册一个JungleMobSpawnController子类为可扫荡类型
     */
    public static void register(JungleMobSpawnController controller) {
        String key = "jungle_" + controller.name.getString();
        if (key.isEmpty()) return;
        String name = controller.name != null ? controller.name.getString() : key;
        MOB_TYPES.putIfAbsent(key, new AfkMobType(key, name, controller.getRewardItemList(), "丛林怪物"));
    }

    /**
     * 获取玩家的AFK数据
     */
    private static CompoundTag getAfkData(Player player) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(AFK_SELECTED_MOB)) {
            data.putString(AFK_SELECTED_MOB, "");
            data.putLong(AFK_LAST_HARVEST_TIME, 0);
        }
        return data;
    }

    /**
     * 获取玩家当前选择的怪物类型ID
     */
    public static String getSelectedMobTypeId(Player player) {
        return getAfkData(player).getString(AFK_SELECTED_MOB);
    }

    /**
     * 获取玩家上次收获时间(tick)
     */
    public static long getLastHarvestTime(Player player) {
        return getAfkData(player).getLong(AFK_LAST_HARVEST_TIME);
    }

    /**
     * 玩家选择怪物类型
     */
    public static void handleSelectMob(ServerPlayer player, String mobTypeId) {
        CompoundTag data = getAfkData(player);
        String oldMobTypeId = data.getString(AFK_SELECTED_MOB);

        if (!oldMobTypeId.isEmpty()) {
            // 切换前先结算旧怪物的收益
            handleHarvest(player);
        }

        data.putString(AFK_SELECTED_MOB, mobTypeId);
        data.putLong(AFK_LAST_HARVEST_TIME, Tick.get());

        AfkMobType mobType = MOB_TYPES.get(mobTypeId);
        if (mobType != null) {
            player.sendSystemMessage(Component.literal("已选择挂机扫荡目标: " + mobType.displayName));
        }
    }

    /**
     * 玩家收获挂机收益
     */
    public static void handleHarvest(ServerPlayer player) {
        CompoundTag data = getAfkData(player);
        String mobTypeId = data.getString(AFK_SELECTED_MOB);

        if (mobTypeId.isEmpty()) {
            player.sendSystemMessage(Component.literal("尚未选择挂机扫荡的怪物类型！"));
            return;
        }

        AfkMobType mobType = MOB_TYPES.get(mobTypeId);
        if (mobType == null) {
            player.sendSystemMessage(Component.literal("选择的怪物类型无效，请重新选择。"));
            data.putString(AFK_SELECTED_MOB, "");
            data.putLong(AFK_LAST_HARVEST_TIME, 0);
            return;
        }

        long lastHarvestTime = data.getLong(AFK_LAST_HARVEST_TIME);
        long currentTick = Tick.get();
        long elapsedTicks = currentTick - lastHarvestTime;

        if (elapsedTicks < 20) {
            player.sendSystemMessage(Component.literal("距离上次收获不足1秒，请稍后再试。"));
            return;
        }

        double killCount = elapsedTicks / 20.0 * KILLS_PER_SECOND;
        double elapsedSeconds = elapsedTicks / 20.0;

        List<ItemAndRate> dropList = mobType.dropList;
        int totalItems = 0;
        for (ItemAndRate itemAndRate : dropList) {
            ItemAndRate copy = new ItemAndRate(itemAndRate.getItemStack().copy(), itemAndRate.getRate());
            copy.sendWithMSG(player, killCount);
            totalItems++;
        }

        // 更新最后收获时间
        data.putLong(AFK_LAST_HARVEST_TIME, currentTick);

        player.sendSystemMessage(Component.literal(
                String.format("已领取「%s」的挂机收益（离线%.1f秒 ≈ %.0f次击杀）",
                        mobType.displayName, elapsedSeconds, killCount)));
    }

    /**
     * 同步AFK数据到客户端
     */
    public static void syncToClient(ServerPlayer player) {
        ModNetworking.sendToClient(new AfkDataS2CPacket(
                getSelectedMobTypeId(player),
                getLastHarvestTime(player),
                Tick.get(),
                getMobTypeDisplayList()
        ), player);
    }

    /**
     * 获取前端展示用的怪物类型列表（简化数据）
     */
    public static List<Map<String, String>> getMobTypeDisplayList() {
        List<Map<String, String>> list = new ArrayList<>();
        for (AfkMobType type : MOB_TYPES.values()) {
            Map<String, String> entry = new HashMap<>();
            entry.put("id", type.id);
            entry.put("name", type.displayName);
            entry.put("category", type.category);
            list.add(entry);
        }
        return list;
    }
}
