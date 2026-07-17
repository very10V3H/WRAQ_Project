package fun.wraq.process.system.data;

import fun.wraq.common.util.PlayerDataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PersistentDataUtil {

    public static CompoundTag getPlayerSpecificKeyCompoundTagData(Player player, String dataKey) {
        return PlayerDataUtil.getPlayerSpecificKeyCompoundTagData(player, dataKey);
    }

    public static int getSpecificKeyDataIntValue(Player player, String dataKey, String valueKey) {
        return PlayerDataUtil.getSpecificKeyDataIntValue(player, dataKey, valueKey);
    }

    public static void setSpecificKeyDataIntValue(Player player, String dataKey, String valueKey, int value) {
        PlayerDataUtil.setSpecificKeyDataIntValue(player, dataKey, valueKey, value);
    }

    public static void incrementSpecificKeyDataIntValue(Player player, String dataKey, String valueKey, int increment) {
        PlayerDataUtil.incrementSpecificKeyDataIntValue(player, dataKey, valueKey, increment);
    }

    public static void incrementDataIntValue(Player player, String key, int increment) {
        PlayerDataUtil.incrementDataIntValue(player, key, increment);
    }

    public static int getDataIntValue(Player player, String key) {
        return PlayerDataUtil.getDataIntValue(player, key);
    }

    public static void setDataIntValue(Player player, String key, int value) {
        PlayerDataUtil.setDataIntValue(player, key, value);
    }

    public static void setDataBooleanValue(Player player, String key, boolean value) {
        PlayerDataUtil.setDataBooleanValue(player, key, value);
    }

    public static boolean getDataBooleanValue(Player player, String key) {
        return PlayerDataUtil.getDataBooleanValue(player, key);
    }
}
