package fun.wraq.process.system.cooking;

import fun.wraq.common.Compute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class CookingPlayerData {
    public static final String COOKING_DATA_KEY = "CookingData";
    public static CompoundTag getData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, COOKING_DATA_KEY);
    }

    public static final String FINISHED_TIMES_COUNT_KEY = "FinishedTimesCount";
    public static void incrementFinishedTimesCount(Player player) {
        CompoundTag data = getData(player);
        data.putInt(FINISHED_TIMES_COUNT_KEY, getFinishedTimesCount(player) + 1);
    }

    public static int getFinishedTimesCount(Player player) {
        CompoundTag data = getData(player);
        return data.getInt(FINISHED_TIMES_COUNT_KEY);
    }
}
