package fun.wraq.process.system.cooking;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.system.tower.Tower;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.Calendar;

public class CookingPlayerData {
    public static final String COOKING_DATA_KEY = "CookingData";
    public static CompoundTag getData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, COOKING_DATA_KEY);
    }

    public static final String FINISHED_TIMES_COUNT_KEY = "FinishedTimesCount";
    public static void incrementEntrustmentFinishedTimesCount(Player player) {
        CompoundTag data = getData(player);
        data.putInt(FINISHED_TIMES_COUNT_KEY, getEntrustmentFinishedTimesCount(player) + 1);
    }

    public static void setEntrustmentFinishedTimesCount(Player player, int count) {
        CompoundTag data = getData(player);
        data.putInt(FINISHED_TIMES_COUNT_KEY, count);
    }

    public static int getEntrustmentFinishedTimesCount(Player player) {
        CompoundTag data = getData(player);
        return data.getInt(FINISHED_TIMES_COUNT_KEY);
    }

    public static final String SELL_FOOD_COUNT_KEY = "SellFoodCount";
    public static void incrementSellFoodCount(Player player, int count) {
        CompoundTag data = getData(player);
        data.putInt(SELL_FOOD_COUNT_KEY, getSellFoodCount(player) + count);
    }

    public static int getSellFoodCount(Player player) {
        CompoundTag data = getData(player);
        return data.getInt(SELL_FOOD_COUNT_KEY);
    }

    public static final String DAILY_FINISHED_TIMES_COUNT_KEY = "DailyFinishedTimesCount";
    public static void incrementDailyFinishedTimesCount(Player player) {
        CompoundTag data = getData(player);
        data.putInt(DAILY_FINISHED_TIMES_COUNT_KEY, getDailyFinishedTimesCount(player) + 1);
        int count = getDailyFinishedTimesCount(player);
        if (isInActivityDate() && count <= 10) {
            Tower.givePlayerStar(player, 4, "烹饪限时活动");
            CookingVillager.sendMSG(player, Te.s("烹饪活动进行中！今日剩余聚星获取次数:",
                    (10 - count), ChatFormatting.AQUA, "/" + 10));
        }
    }

    public static int getDailyFinishedTimesCount(Player player) {
        CompoundTag data = getData(player);
        return data.getInt(DAILY_FINISHED_TIMES_COUNT_KEY);
    }

    public static void resetDailyFinishedTimesCount(Player player) {
        CompoundTag data = getData(player);
        data.putInt(DAILY_FINISHED_TIMES_COUNT_KEY, 0);
    }

    public static boolean isInActivityDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) == 2025
                && calendar.get(Calendar.MONTH) == Calendar.MAY && calendar.get(Calendar.DAY_OF_MONTH) <= 10;
    }

    public static int getPlayerCookingLevel(Player player) {
        int count = getEntrustmentFinishedTimesCount(player);
        if (count < 100) {
            return 0;
        } else if (count < 300) {
            return 1;
        } else if (count < 600) {
            return 2;
        } else if (count < 1000) {
            return 3;
        } else if (count < 2000) {
            return 4;
        } else if (count < 4000) {
            return 5;
        } else {
            return 6;
        }
    }
}
