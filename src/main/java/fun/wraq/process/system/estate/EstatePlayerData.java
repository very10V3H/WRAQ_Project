package fun.wraq.process.system.estate;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EstatePlayerData {
    public static final String ESTATE_DATA_KEY = "EstateData";
    public static CompoundTag getEstateData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, ESTATE_DATA_KEY);
    }

    public static final String SERIAL_KEY = "EstateSerial";
    public static int getEstateSerial(Player player) {
        if (!getEstateData(player).contains(SERIAL_KEY)) {
            return -1;
        }
        return getEstateData(player).getInt(SERIAL_KEY);
    }

    public static boolean hasEstate(Player player) {
        return getEstateSerial(player) != -1;
    }

    public static final String REAL_ESTATE_KEY = "RealEstateSerial";
    public static int getRealEstateSerial(Player player) {
        if (!getEstateData(player).contains(REAL_ESTATE_KEY)) {
            return -1;
        }
        return getEstateData(player).getInt(REAL_ESTATE_KEY);
    }

    public static boolean hasRealEstate(Player player) {
        return getRealEstateSerial(player) != -1;
    }

    public static final String LAST_REWARD_DATE_KEY = "LastRewardDate";
    public static String getEstateLastRewardDate(Player player) {
        if (!getEstateData(player).contains(LAST_REWARD_DATE_KEY)) {
            return "";
        }
        return getEstateData(player).getString(LAST_REWARD_DATE_KEY);
    }

    public static void setEstateLastRewardDate(Player player, String date) {
        getEstateData(player).putString(LAST_REWARD_DATE_KEY, date);
    }

    public static final String REAL_ESTATE_LAST_REWARD_DATE_KEY = "RealEstateLastRewardDate";
    public static String getRealEstateLastRewardDate(Player player) {
        if (!getEstateData(player).contains(REAL_ESTATE_LAST_REWARD_DATE_KEY)) {
            return "";
        }
        return getEstateData(player).getString(REAL_ESTATE_LAST_REWARD_DATE_KEY);
    }

    public static void setRealEstateLastRewardDate(Player player, String date) {
        getEstateData(player).putString(REAL_ESTATE_LAST_REWARD_DATE_KEY, date);
    }

    public static final String ESTATE_VB_REWARD_COUNT_DATA_KEY = "EstateVBRewardCount";
    public static final String REAL_ESTATE_VB_REWARD_COUNT_DATA_KEY = "RealEstateVBRewardCount";
    public static final String ESTATE_STAR_REWARD_COUNT_DATA_KEY = "EstateStarRewardCount";
    public static final String REAL_ESTATE_STAR_REWARD_COUNT_DATA_KEY = "RealEstateStarRewardCount";
    public static final String ESTATE_GB_REWARD_COUNT_DATA_KEY = "EstateGBRewardCount";
    public static final String REAL_ESTATE_GB_REWARD_COUNT_DATA_KEY = "RealEstateGBRewardCount";

    public static void incrementData(Player player, String dataKey, int amount) {
        getEstateData(player).putInt(dataKey, getEstateData(player).getInt(dataKey) + amount);
    }

    public static int getData(Player player, String dataKey) {
        return getEstateData(player).getInt(dataKey);
    }

    public static void resetData(Player player, String dataKey) {
        getEstateData(player).remove(dataKey);
    }

    public static void resetEstateRewardCountData(Player player) {
        resetData(player, ESTATE_VB_REWARD_COUNT_DATA_KEY);
        resetData(player, ESTATE_STAR_REWARD_COUNT_DATA_KEY);
        resetData(player, ESTATE_GB_REWARD_COUNT_DATA_KEY);
    }

    public static void resetRealEstateRewardCountData(Player player) {
        resetData(player, REAL_ESTATE_VB_REWARD_COUNT_DATA_KEY);
        resetData(player, REAL_ESTATE_STAR_REWARD_COUNT_DATA_KEY);
        resetData(player, REAL_ESTATE_GB_REWARD_COUNT_DATA_KEY);
    }

    public static void setEstateData(Player player, int serial, String lastRewardDate) {
        getEstateData(player).putInt(SERIAL_KEY, serial);
        getEstateData(player).putString(LAST_REWARD_DATE_KEY, lastRewardDate);
        if (serial == -1) {
            sendDataToClient(player);
        }
    }

    public static void setRealEstateData(Player player, int serial, String lastRewardDate) {
        getEstateData(player).putInt(REAL_ESTATE_KEY, serial);
        getEstateData(player).putString(REAL_ESTATE_LAST_REWARD_DATE_KEY, lastRewardDate);
        if (serial == -1) {
            sendDataToClient(player);
        }
    }

    public static void onLogin(Player player) {
/*        if (hasEstate(player)) {
            int serial = getEstateSerial(player);
            if (!EstateServerData.getEstateServerData(serial).ownerId.equals(Name.get(player))) {
                EstateInfo estateInfo = EstateInfo.values()[serial];
                setEstateData(player, -1, "");
                EstateUtil.sendMSG(player, Te.s("你已失去",
                        estateInfo.estateName.getString(), CustomStyle.styleOfGold, "的所有权."));
            }
        }
        if (hasRealEstate(player)) {
            int serial = getRealEstateSerial(player);
            if (!EstateServerData.getEstateServerData(serial).ownerId.equals(Name.get(player))) {
                EstateInfo estateInfo = EstateInfo.values()[serial];
                setRealEstateData(player, -1, "");
                EstateUtil.sendMSG(player, Te.s("你已失去",
                        estateInfo.estateName.getString(), CustomStyle.styleOfGold, "的所有权."));
            }
        }*/
        sendDataToClient(player);
    }

    public static Map<String, Integer> estateMSGSendTickMap = new HashMap<>();
    public static Map<String, Integer> realEstateMSGSendTickMap = new HashMap<>();

    public static void onPlayerOpenDoor(Player player, int serial) {
        Calendar calendar = Calendar.getInstance();
        if (getEstateSerial(player) == serial) {
            if (getEstateLastRewardDate(player).isEmpty()
                    || Compute.castStringToCalendar(getEstateLastRewardDate(player))
                    .get(Calendar.DAY_OF_YEAR) != calendar.get(Calendar.DAY_OF_YEAR)) {
                setEstateLastRewardDate(player, Compute.CalendarToString(calendar));
                int price = (int) EstateInfo.values()[serial].price;
                price = Math.min(price, 2500 * 10000);
                EstateUtil.sendMSG(player, Te.s("收取了该资产的今日收益."));
                Compute.VBIncomeAndMSGSend(player, (double) price / 1000000 * 10000);
                int starCount = (int) Math.ceil(price / 1250000d);
                int gbCount = (int) Math.ceil(price / 2500000d);
                InventoryOperation.giveItemStackWithMSG(player, ModItems.WORLD_SOUL_5.get(), starCount);
                InventoryOperation.giveItemStackWithMSG(player, ModItems.GOLDEN_BEANS.get(), gbCount);
                incrementData(player, ESTATE_VB_REWARD_COUNT_DATA_KEY, price / 1000000 * 10000);
                incrementData(player, ESTATE_STAR_REWARD_COUNT_DATA_KEY, starCount);
                incrementData(player, ESTATE_GB_REWARD_COUNT_DATA_KEY, gbCount);
                sendEstateRewardMSG(player);
            } else {
                if (estateMSGSendTickMap.getOrDefault(Name.get(player), 0) < Tick.get()) {
                    estateMSGSendTickMap.put(Name.get(player), Tick.get() + Tick.min(1));
                    EstateUtil.sendMSG(player, Te.s("欢迎回到 ",
                                    EstateInfo.values()[serial].estateName.getString(), ChatFormatting.GOLD));
                    sendEstateRewardMSG(player);
                }
            }
            EstateUtil.onPlayerOpenEstateDoor(player);
        }
        if (getRealEstateSerial(player) == serial) {
            if (getRealEstateLastRewardDate(player).isEmpty()
                    || Compute.castStringToCalendar(getRealEstateLastRewardDate(player))
                    .get(Calendar.DAY_OF_YEAR) != calendar.get(Calendar.DAY_OF_YEAR)) {
                setRealEstateLastRewardDate(player, Compute.CalendarToString(calendar));
                int price = (int) EstateInfo.values()[serial].price;
                price = Math.min(price, 2000);
                EstateUtil.sendMSG(player, Te.s("收取了该资产的今日收益."));
                int starCount = (int) Math.ceil(price / 100d);
                int gbCount = (int) Math.ceil(price / 200d);
                Compute.VBIncomeAndMSGSend(player, (double) price / 80 * 10000);
                InventoryOperation.giveItemStackWithMSG(player, ModItems.WORLD_SOUL_5.get(), starCount);
                InventoryOperation.giveItemStackWithMSG(player, ModItems.GOLDEN_BEANS.get(), gbCount);
                incrementData(player, REAL_ESTATE_VB_REWARD_COUNT_DATA_KEY, price / 80 * 10000);
                incrementData(player, REAL_ESTATE_STAR_REWARD_COUNT_DATA_KEY, starCount);
                incrementData(player, REAL_ESTATE_GB_REWARD_COUNT_DATA_KEY, gbCount);
                sendRealEstateRewardMSG(player);
            } else {
                if (realEstateMSGSendTickMap.getOrDefault(Name.get(player), 0) < Tick.get()) {
                    realEstateMSGSendTickMap.put(Name.get(player), Tick.get() + Tick.min(1));
                    EstateUtil.sendMSG(player, Te.s("欢迎回到 ",
                                    EstateInfo.values()[serial].estateName.getString(), ChatFormatting.GOLD));
                    sendRealEstateRewardMSG(player);
                }
            }
            EstateUtil.onPlayerOpenRealEstateDoor(player);
        }
    }

    public static void sendEstateRewardMSG(Player player) {
        MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
        EstateUtil.sendMSG(player, Te.s("你已累积通过房产获得了:"));
        player.sendSystemMessage(Te.s(" ".repeat(12), " - ",
                getData(player, ESTATE_VB_REWARD_COUNT_DATA_KEY) + "VB", ChatFormatting.GOLD));
        player.sendSystemMessage(Te.s(" ".repeat(12), " - ",
                getData(player, ESTATE_STAR_REWARD_COUNT_DATA_KEY) + "枚聚星", CustomStyle.styleOfWorld));
        player.sendSystemMessage(Te.s(" ".repeat(12), " - ",
                getData(player, ESTATE_GB_REWARD_COUNT_DATA_KEY) + "GB", ChatFormatting.GOLD));
    }

    public static void sendRealEstateRewardMSG(Player player) {
        MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
        EstateUtil.sendMSG(player, Te.s("你已累积通过不动产获得了:"));
        player.sendSystemMessage(Te.s(" ".repeat(12), " - ",
                getData(player, REAL_ESTATE_VB_REWARD_COUNT_DATA_KEY) + "VB", ChatFormatting.GOLD));
        player.sendSystemMessage(Te.s(" ".repeat(12), " - ",
                getData(player, REAL_ESTATE_STAR_REWARD_COUNT_DATA_KEY) + "枚聚星", CustomStyle.styleOfWorld));
        player.sendSystemMessage(Te.s(" ".repeat(12), " - ",
                getData(player, REAL_ESTATE_GB_REWARD_COUNT_DATA_KEY) + "GB", ChatFormatting.GOLD));
    }

    public static void sendDataToClient(Player player) {
        ModNetworking.sendToClient(new EstateDataS2CPacket(
                getEstateSerial(player), getRealEstateSerial(player)), player);
    }
}
