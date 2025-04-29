package fun.wraq.process.system.estate;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

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

    public static final String LAST_REWARD_DATE_KEY = "LastRewardDate";
    public static String getLastRewardDate(Player player) {
        if (!getEstateData(player).contains(LAST_REWARD_DATE_KEY)) {
            return "";
        }
        return getEstateData(player).getString(LAST_REWARD_DATE_KEY);
    }

    public static void setEstateData(Player player, int serial, String lastRewardDate) {
        getEstateData(player).putInt(SERIAL_KEY, serial);
        getEstateData(player).putString(LAST_REWARD_DATE_KEY, lastRewardDate);
    }

    public static void onLogin(Player player) {
        if (hasEstate(player)) {
            int serial = getEstateSerial(player);
            if (!EstateServerData.getEstateServerData(serial).ownerId.equals(Name.get(player))) {
                EstateInfo estateInfo = EstateInfo.values()[serial];
                setEstateData(player, -1, "");
                EstateUtil.sendMSG(player, Te.s("你已失去",
                        estateInfo.estateName.getString(), CustomStyle.styleOfGold, "的所有权."));
            }
        }
    }
}
