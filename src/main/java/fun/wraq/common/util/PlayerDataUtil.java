/** AI-Generated, 2026-05-10 */
package fun.wraq.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PlayerDataUtil {

    private static final String DAILY_KILL_COUNT_DATA_KEY = "daily_kill_count";
    public static final String CHALLENGE_RECORD_KEY = "ChallengeRecord";
    public static final String TEMP_TAG_KEY = "tempTagKey";

    public static CompoundTag getPlayerSpecificKeyCompoundTagData(Player player, String dataKey) {
        CompoundTag tag = player.getPersistentData();
        if (!tag.contains(dataKey)) {
            tag.put(dataKey, new CompoundTag());
        }
        return tag.getCompound(dataKey);
    }

    public static int getSpecificKeyDataIntValue(Player player, String dataKey, String valueKey) {
        return getPlayerSpecificKeyCompoundTagData(player, dataKey).getInt(valueKey);
    }

    public static void setSpecificKeyDataIntValue(Player player, String dataKey, String valueKey, int value) {
        getPlayerSpecificKeyCompoundTagData(player, dataKey).putInt(valueKey, value);
    }

    public static void incrementSpecificKeyDataIntValue(Player player, String dataKey, String valueKey, int increment) {
        CompoundTag data = getPlayerSpecificKeyCompoundTagData(player, dataKey);
        data.putInt(valueKey, data.getInt(valueKey) + increment);
    }

    public static void incrementDataIntValue(Player player, String key, int increment) {
        CompoundTag data = player.getPersistentData();
        data.putInt(key, data.getInt(key) + increment);
    }

    public static int getDataIntValue(Player player, String key) {
        CompoundTag data = player.getPersistentData();
        return data.getInt(key);
    }

    public static void setDataIntValue(Player player, String key, int value) {
        CompoundTag data = player.getPersistentData();
        data.putInt(key, value);
    }

    public static void setDataBooleanValue(Player player, String key, boolean value) {
        CompoundTag data = player.getPersistentData();
        data.putBoolean(key, value);
    }

    public static boolean getDataBooleanValue(Player player, String key) {
        CompoundTag data = player.getPersistentData();
        return data.getBoolean(key);
    }

    public static CompoundTag getChallengeRecordData(Player player) {
        return getPlayerSpecificKeyCompoundTagData(player, CHALLENGE_RECORD_KEY);
    }

    public static CompoundTag getTempTag(Player player) {
        return getPlayerSpecificKeyCompoundTagData(player, TEMP_TAG_KEY);
    }

    public static int getPlayerDailyKillCount(Player player) {
        return getDataIntValue(player, DAILY_KILL_COUNT_DATA_KEY);
    }

    public static void incrementPlayerDailyKillCount(Player player) {
        incrementDataIntValue(player, DAILY_KILL_COUNT_DATA_KEY, 1);
    }

    public static void setPlayerDailyKillCount(Player player, int value) {
        setDataIntValue(player, DAILY_KILL_COUNT_DATA_KEY, value);
    }
}
