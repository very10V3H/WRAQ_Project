package fun.wraq.process.system.randomevent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class RandomEventData {

    public static final String RANDOM_EVENT_DATA = "RANDOM_EVENT_DATA";
    public static CompoundTag getPlayerData(Player player) {
        CompoundTag tag = player.getPersistentData();
        if (!tag.contains(RANDOM_EVENT_DATA)) {
            tag.put(RANDOM_EVENT_DATA, new CompoundTag());
        }
        return tag.getCompound(RANDOM_EVENT_DATA);
    }

    public static void incrementTimes(Player player, String key) {
        int count = getPlayerData(player).getInt(key);
        getPlayerData(player).putInt(key, count + 1);
    }

    public static int getTimes(Player player, String key) {
        return getPlayerData(player).getInt(key);
    }

    private static final String DAILY_WORLD_SOUL_5_TIMES = "DAILY_WORLD_SOUL_5_TIMES";
    public static int getWorldSoul5DailyGetTimes(Player player) {
        return getPlayerData(player).getInt(DAILY_WORLD_SOUL_5_TIMES);
    }

    public static void setWorldSoul5DailyGetTimes(Player player, int times) {
        getPlayerData(player).putInt(DAILY_WORLD_SOUL_5_TIMES, times);
    }

    public static void incrementWorldSoul5DailyGetTimes(Player player) {
        incrementTimes(player, DAILY_WORLD_SOUL_5_TIMES);
    }

    public static void resetWorldSoul5DailyGetTimes(Player player) {
        setWorldSoul5DailyGetTimes(player, 0);
    }
}
