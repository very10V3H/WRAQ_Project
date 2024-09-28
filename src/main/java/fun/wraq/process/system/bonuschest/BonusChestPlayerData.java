package fun.wraq.process.system.bonuschest;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.bonuschest.BonusChestInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 奖励箱玩家数据
 */
public class BonusChestPlayerData {

    private static final String BONUS_CHEST_DATA_KEY = "bonus_chest_data_key";
    private static CompoundTag getBonusChestData(Player player) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(BONUS_CHEST_DATA_KEY)) {
            data.put(BONUS_CHEST_DATA_KEY, new CompoundTag());
        }
        return data.getCompound(BONUS_CHEST_DATA_KEY);
    }

    private static final String ORIGIN_INFO_STRING = "0".repeat(255);
    private static final String ZONE_INFO_KEY = "bonus_chest_zone_";
    private static String getZoneInfoKey(int zoneNum) {
        return ZONE_INFO_KEY + zoneNum;
    }

    private static final int MAX_ZONE_NUM = 4;
    private static String getZoneInfo(Player player, int zoneNum) {
        if (zoneNum >= MAX_ZONE_NUM) {
            player.sendSystemMessage(
                    Te.m("看到此信息请联系管理员!奖励箱错误：zoneNum = " + zoneNum));
            return "1".repeat(255);
        }
        CompoundTag data = getBonusChestData(player);
        if (!data.contains(getZoneInfoKey(zoneNum))) data.putString(getZoneInfoKey(zoneNum), ORIGIN_INFO_STRING);
        return data.getString(getZoneInfoKey(zoneNum));
    }

    private static void setZoneInfo(Player player, int zoneNum, String info) {
        CompoundTag data = getBonusChestData(player);
        data.putString(getZoneInfoKey(zoneNum), info);
    }

    public static void resetZoneInfo(Player player, int zoneNum) {
        setZoneInfo(player, zoneNum, ORIGIN_INFO_STRING);
    }

    public static void resetAllZoneInfo(Player player) {
        for (int i = 0 ; i <= 2 ; i ++) {
            setZoneInfo(player, i, ORIGIN_INFO_STRING);
        }
    }

    private static int getInfoBySerialNum(Player player, int zoneNum, int serialNum) {
        if (serialNum >= 255) {
            player.sendSystemMessage(
                    Te.m("看到此信息请联系管理员!奖励箱错误：zoneNum = " + zoneNum + " serialNum = " + serialNum));
            return 1;
        }
        return Integer.parseInt(String.valueOf(getZoneInfo(player, zoneNum).charAt(serialNum)));
    }

    private static void setInfoBySerialNum(Player player, int zoneNum, int serialNum) {
        if (serialNum >= 255) {
            player.sendSystemMessage(
                    Te.m("看到此信息请联系管理员!奖励箱错误：zoneNum = " + zoneNum + " serialNum = " + serialNum));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(getZoneInfo(player, zoneNum));
        stringBuilder.setCharAt(serialNum, '1');
        setZoneInfo(player, zoneNum, stringBuilder.toString());
    }

    private static final String ZONE_COUNT_KEY = "bonus_chest_zone_count_";
    private static final String TIER_COUNT_KEY = "bonus_chest_tier_count_";
    public static int getZoneCount(Player player, int zone) {
        return getBonusChestData(player).getInt(ZONE_COUNT_KEY + zone);
    }
    private static void addZoneCount(Player player, int zone) {
        CompoundTag data = getBonusChestData(player);
        data.putInt(ZONE_COUNT_KEY + zone, data.getInt(ZONE_COUNT_KEY + zone) + 1);
    }
    public static int getTierCount(Player player, int tier) {
        return getBonusChestData(player).getInt(TIER_COUNT_KEY + tier);
    }
    private static void addTierCount(Player player, int tier) {
        CompoundTag data = getBonusChestData(player);
        data.putInt(TIER_COUNT_KEY + tier, data.getInt(TIER_COUNT_KEY + tier) + 1);
    }

    public static void onPlayerSuccessOpenBonusChest(Player player, BlockPos blockPos,
                                                     PlayerInteractEvent.RightClickBlock event) {
        fun.wraq.process.system.bonuschest.BonusChestInfo bonusChestInfo = fun.wraq.process.system.bonuschest.BonusChestInfo.getBonusChestInfo(blockPos);
        if (bonusChestInfo == null) {
            player.sendSystemMessage(
                    Te.m("看到此信息请联系管理员!奖励箱错误：blockPos = " + blockPos));
            return;
        }
        int serial = BonusChestInfo.getSerialNum(blockPos);
        if (getInfoBySerialNum(player, bonusChestInfo.zone, serial) == 0) {
            // 未获取过奖励
            setInfoBySerialNum(player, bonusChestInfo.zone, serial);
            addZoneCount(player, bonusChestInfo.zone);
            addTierCount(player, bonusChestInfo.tier);

            ChestBlockEntity chestBlockEntity = (ChestBlockEntity) player.level().getBlockEntity(blockPos);
            Random random = new Random();
            for (int i = 0; i < 27; i++) {
                chestBlockEntity.setItem(i, Items.AIR.getDefaultInstance());
            }
            sendMSG(player, Te.m("你找到了一个奖励箱。"));
            Utils.playerIsUsingBlockBlockPosMap.put(player.getName().getString(), blockPos);
        } else {
            // 已获取过奖励
            event.setCanceled(true);
            sendMSG(player, Te.m("你最近已经打开过这个奖励箱了。"));
        }
    }

    private static List<ItemAndRate> commonTier1Loot;
    public static List<ItemStack> getOneTimeCommonTier1Loot() {
        if (commonTier1Loot == null) {
            commonTier1Loot = List.of();
        }
        return ItemAndRate.getOneTimeLoot(commonTier1Loot);
    }

    private static List<ItemAndRate> commonTier2Loot = new ArrayList<>();
    public static List<ItemStack> getOneTimeCommonTier2Loot() {
        if (commonTier2Loot == null) {
            commonTier2Loot = List.of();
        }
        List<ItemStack> result = new ArrayList<>();
        result.addAll(getOneTimeCommonTier1Loot());
        result.addAll(ItemAndRate.getOneTimeLoot(commonTier2Loot));
        return result;
    }

    private static List<ItemAndRate> commonTier3Loot = new ArrayList<>();
    public static List<ItemStack> getOneTimeCommonTier3Loot() {
        if (commonTier3Loot == null) {
            commonTier3Loot = List.of();
        }
        List<ItemStack> result = new ArrayList<>();
        result.addAll(getOneTimeCommonTier1Loot());
        result.addAll(getOneTimeCommonTier2Loot());
        result.addAll(ItemAndRate.getOneTimeLoot(commonTier3Loot));
        return result;
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Component.literal("奖励箱").withStyle(ChatFormatting.LIGHT_PURPLE), content);
    }
}
