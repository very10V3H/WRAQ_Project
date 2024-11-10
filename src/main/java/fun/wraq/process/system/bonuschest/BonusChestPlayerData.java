package fun.wraq.process.system.bonuschest;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.process.func.security.Security;
import fun.wraq.process.system.point.Point;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

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

    private static final int EACH_ZONE_MAX_CHEST_NUM = 512;
    private static final String ORIGIN_INFO_STRING = "0".repeat(EACH_ZONE_MAX_CHEST_NUM);
    private static final String ZONE_INFO_KEY = "bonus_chest_zone_";
    private static String getZoneInfoKey(int zoneNum) {
        return ZONE_INFO_KEY + zoneNum;
    }

    private static String getZoneInfo(Player player, int zoneNum) {
        if (zoneNum > BonusChestInfo.Util.MAX_ZONE_NUM) {
            player.sendSystemMessage(
                    Te.m("看到此信息请联系管理员!奖励箱错误：zoneNum = " + zoneNum));
            return "1".repeat(EACH_ZONE_MAX_CHEST_NUM);
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
        for (int i = 0 ; i <= BonusChestInfo.Util.MAX_ZONE_NUM ; i ++) {
            setZoneInfo(player, i, ORIGIN_INFO_STRING);
        }
        sendMSG(player, Te.s("奖励箱已被刷新！", ChatFormatting.WHITE));
    }

    private static int getInfoBySerialNum(Player player, int zoneNum, int serialNum) {
        if (serialNum >= EACH_ZONE_MAX_CHEST_NUM) {
            player.sendSystemMessage(
                    Te.m("看到此信息请联系管理员!奖励箱错误：zoneNum = " + zoneNum + " serialNum = " + serialNum));
            return 1;
        }
        return Integer.parseInt(String.valueOf(getZoneInfo(player, zoneNum).charAt(serialNum)));
    }

    private static void setInfoBySerialNum(Player player, int zoneNum, int serialNum) {
        if (serialNum >= EACH_ZONE_MAX_CHEST_NUM) {
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

    private static final String FIRST_OPEN_KEY = "first_open_key";

    public static Map<Player, ChestBlockEntity> openBonusChestMap = new HashMap<>();
    public static void onPlayerSuccessOpenBonusChest(Player player, BlockPos blockPos,
                                                     PlayerInteractEvent.RightClickBlock event) {
        BonusChestInfo bonusChestInfo = BonusChestInfo.getBonusChestInfo(blockPos);
        if (bonusChestInfo == null) {
            player.sendSystemMessage(
                    Te.m("看到此信息请联系管理员!奖励箱错误：blockPos = " + blockPos));
            return;
        }
        if ((player.level().dimension().equals(Level.OVERWORLD) && bonusChestInfo.levelSerial == 0)
                || (player.level().dimension().equals(Level.NETHER) && bonusChestInfo.levelSerial == 1)
                || (player.level().dimension().equals(Level.END) && bonusChestInfo.levelSerial == 2)) {
            int serial = BonusChestInfo.getSerialNum(blockPos);
            int levelRequire = BonusChestInfo.Util.ZONE_LEVEL_REQUIREMENT.getOrDefault(bonusChestInfo.zone, 0);
            if (player.experienceLevel < levelRequire) {
                sendMSG(player, Te.s(BonusChestInfo.Util.ZONE_NAME_MAP.get(bonusChestInfo.zone),
                        "区域的奖励箱需要达到", "Lv." + levelRequire, Utils.levelStyleList.get(levelRequire / 25), "才能开启"));
                event.setCanceled(true);
                return;
            }
            if (getInfoBySerialNum(player, bonusChestInfo.zone, serial) == 0 || player.isCreative()) {
                // 未获取过奖励
                setInfoBySerialNum(player, bonusChestInfo.zone, serial);
                addZoneCount(player, bonusChestInfo.zone);
                addTierCount(player, bonusChestInfo.tier);

                ChestBlockEntity chestBlockEntity = (ChestBlockEntity) player.level().getBlockEntity(blockPos);
                chestBlockEntity.clearContent();
                BonusChestContent.getBonusContent(bonusChestInfo.tier).forEach(pair -> {
                    ItemStack itemStack = pair.getFirst();
                    InventoryCheck.addOwnerTagToItemStack(player, itemStack);
                    Security.recordItemStream(player.getName().getString(), itemStack,
                            Security.RecordType.BONUS_CHEST_REWARD);
                    chestBlockEntity.setItem(pair.getSecond(), itemStack);
                });
                Point.increment(player, BonusChestContent.getZoneToPointType(bonusChestInfo.zone),
                        bonusChestInfo.tier + 1);
                Point.increment(player, Point.EXPT, bonusChestInfo.tier + 1);

                sendMSG(player, Te.s("你找到了一个:", BonusChestInfo.Util.TIER_NAME_MAP.get(bonusChestInfo.tier)));
                Utils.playerIsUsingBlockBlockPosMap.put(player.getName().getString(), blockPos);
                openBonusChestMap.put(player, chestBlockEntity);
                if (!getBonusChestData(player).contains(FIRST_OPEN_KEY)) {
                    getBonusChestData(player).putBoolean(FIRST_OPEN_KEY, true);
                    sendMSG(player, Te.s("你知道吗，找到奖励箱后直接按", "E", ChatFormatting.AQUA, "关闭，奖励箱的",
                            "所有物品", ChatFormatting.LIGHT_PURPLE, "会直接送至背包!"));
                }
            } else {
                // 已获取过奖励
                event.setCanceled(true);
                sendMSG(player, Te.m("你最近已经打开过这个奖励箱了，下个月再来试试吧!"));
            }
        }
    }

    private static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Component.literal("奖励箱").withStyle(ChatFormatting.LIGHT_PURPLE), content);
    }
}
