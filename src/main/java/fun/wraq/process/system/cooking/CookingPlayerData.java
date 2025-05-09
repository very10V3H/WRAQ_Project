package fun.wraq.process.system.cooking;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.cooking.network.CookingDataS2CPacket;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.Calendar;

public class CookingPlayerData {

    public static final String COOKING_DATA_KEY = "CookingData";
    public static CompoundTag getData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, COOKING_DATA_KEY);
    }

    public static void incrementTimesCount(Player player, String dataKey, int count) {
        getData(player).putInt(dataKey, getData(player).getInt(dataKey) + count);
    }

    public static void incrementTimesCount(Player player, String dataKey) {
        incrementTimesCount(player, dataKey, 1);
    }

    public static int getTimesCount(Player player, String dataKey) {
        return getData(player).getInt(dataKey);
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
        int beforeExp = getCookingExp(player);
        data.putInt(SELL_FOOD_COUNT_KEY, getSellFoodCount(player) + count);
        int afterExp = getCookingExp(player);
        sendCookingExpChangeMSG(player, afterExp - beforeExp);
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
        if (count <= 10) {
            if (isInActivityDate()) {
                Tower.givePlayerStar(player, 4, "烹饪限时活动");
                CookingVillager.sendMSG(player, Te.s("烹饪活动进行中！今日剩余聚星获取次数:",
                        (10 - count), ChatFormatting.AQUA, "/" + 10));
            }
            incrementTimesCount(player, DAILY_FIRST_FINISHED_10_TIMES_COUNT_KEY);
            sendCookingExpChangeMSG(player, 2);
            CookingVillager.sendMSG(player, Te.s("剩余额外烹饪经验获取次数:",
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

    public static final String DAILY_FIRST_FINISHED_10_TIMES_COUNT_KEY = "DailyFirstFinishedTimesCount";
    public static final String FOOD_COIN_USED_COUNT_KEY = "FoodCoinUsedCount";
    public static int getCookingExp(Player player) {
        int cookingEntrustmentFinishedTimesCount = getEntrustmentFinishedTimesCount(player);
        int cookingSellFoodCount = getSellFoodCount(player);
        int cookingDailyFinishedTimesCount = getTimesCount(player, DAILY_FIRST_FINISHED_10_TIMES_COUNT_KEY);
        int foodCoinUsedCount = getTimesCount(player, FOOD_COIN_USED_COUNT_KEY);
        return cookingEntrustmentFinishedTimesCount + cookingSellFoodCount / 16
                + cookingDailyFinishedTimesCount + foodCoinUsedCount;
    }

    public static void sendCookingExpChangeMSG(Player player, int changeCount) {
        if (changeCount <= 0) {
            return;
        }
        CookingVillager.sendMSG(player, Te.s("烹饪经验", CustomStyle.MUSHROOM_STYLE,
                " + ", ChatFormatting.GREEN, changeCount, CustomStyle.MUSHROOM_STYLE,
                "(" + getCookingExp(player) + "/"
                        + getNextLevelNeedExp(getPlayerCookingLevel(player)) + ")", ChatFormatting.GRAY));
    }

    public static int getNextLevelNeedExp(int level) {
        switch (level) {
            case 0 -> {
                return 100;
            }
            case 1 -> {
                return 300;
            }
            case 2 -> {
                return 600;
            }
            case 3 -> {
                return 1000;
            }
            case 4 -> {
                return 2000;
            }
            case 5 -> {
                return 4000;
            }
            default -> {
                return 999999;
            }
        }
    }

    public static int getLevelByExp(int exp) {
        if (exp < 100) {
            return 0;
        } else if (exp < 300) {
            return 1;
        } else if (exp < 600) {
            return 2;
        } else if (exp < 1000) {
            return 3;
        } else if (exp < 2000) {
            return 4;
        } else if (exp < 4000) {
            return 5;
        } else {
            return 6;
        }
    }

    public static int getPlayerCookingLevel(Player player) {
        return getLevelByExp(getCookingExp(player));
    }

    public static String getPrefixByLevel(int level) {
        switch (level) {
            case 0 -> {
                return "小小厨";
            }
            case 1 -> {
                return "小厨";
            }
            case 2 -> {
                return "厨师";
            }
            case 3 -> {
                return "厨师长";
            }
            case 4 -> {
                return "★大厨";
            }
            case 5 -> {
                return "★★大厨";
            }
            case 6 -> {
                return "★★★大厨";
            }
            default -> {
                return "小小厨";
            }
        }
    }

    public static int clientCookingExp = 0;

    public static void sendCookingExpToClient(Player player) {
        ModNetworking.sendToClient(new CookingDataS2CPacket(getCookingExp(player)), player);
    }
}
