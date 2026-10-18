package fun.wraq.process.system.estate;

import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.data.PersistentDataUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class EstatePlayerData {
    public static final String ESTATE_DATA_KEY = "EstateData";
    public static CompoundTag getEstateData(Player player) {
        return PersistentDataUtil.getPlayerSpecificKeyCompoundTagData(player, ESTATE_DATA_KEY);
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
        if (getEstateSerial(player) == serial) {
            if (estateMSGSendTickMap.getOrDefault(Name.get(player), 0) < Tick.get()) {
                estateMSGSendTickMap.put(Name.get(player), Tick.get() + Tick.min(1));
                EstateUtil.sendMSG(player, Te.s("欢迎回到 ",
                        EstateInfo.values()[serial].estateName.getString(), ChatFormatting.GOLD));
            }
            EstateUtil.onPlayerOpenEstateDoor(player);
        }
        if (getRealEstateSerial(player) == serial) {
            if (realEstateMSGSendTickMap.getOrDefault(Name.get(player), 0) < Tick.get()) {
                realEstateMSGSendTickMap.put(Name.get(player), Tick.get() + Tick.min(1));
                EstateUtil.sendMSG(player, Te.s("欢迎回到 ",
                        EstateInfo.values()[serial].estateName.getString(), ChatFormatting.GOLD));
            }
            EstateUtil.onPlayerOpenRealEstateDoor(player);
        }
    }

    public static void sendDataToClient(Player player) {
        ModNetworking.sendToClient(new EstateDataS2CPacket(
                getEstateSerial(player), getRealEstateSerial(player)), player);
    }
}
