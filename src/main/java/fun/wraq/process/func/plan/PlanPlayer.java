package fun.wraq.process.func.plan;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.text.ParseException;
import java.util.Calendar;

public class PlanPlayer {
    public static int clientPlanLeftDate = 0;
    public static int clientPlanTier = 0;

    public String name;
    public int tier;
    public String overDate;
    public String lastRewardTime;
    public int getDailyRewardTimes;

    public PlanPlayer(String name, int tier, String overDate, String lastRewardTime, int getDailyRewardTimes) {
        this.name = name;
        this.tier = tier;
        this.overDate = overDate;
        this.lastRewardTime = lastRewardTime;
        this.getDailyRewardTimes = getDailyRewardTimes;
    }

    public static final String PLAN_DATA_KEY = "PlanData";
    public static final String SYNC_FLAG_KEY = "SyncFlag";
    public static final String TIER_KEY = "Tier";
    public static final String OVER_DATE_KEY = "OverDate";
    public static final String LAST_REWARD_TIME_KEY = "LastRewardTime";
    public static final String GET_DAILY_REWARD_TIMES_KEY = "GetDailyRewardTimes";
    public static final String GET_STAR_COUNTS_KEY = "GetStarCounts";
    public static final String TOWER_STATUS_KEY = "TowerStatus";

    public static CompoundTag getPlanData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, PLAN_DATA_KEY);
    }

    public static final String LOTTERY_KEY = "Lottery";
    public static CompoundTag getLotteryData(Player player) {
        CompoundTag data = getPlanData(player);
        if (!data.contains(LOTTERY_KEY)) {
            data.put(LOTTERY_KEY, new CompoundTag());
        }
        return data.getCompound(LOTTERY_KEY);
    }

    public static int getPlayerTier(Player player) {
        return Math.max(getPlayerPlanTier(player), getPlayerRankPlanTier(player));
    }

    private static int getPlayerRankPlanTier(Player player) {
        String rank = RankData.getCurrentRank(player);
        if (RankData.getRankSerial(rank) >= RankData.rankSerialList.indexOf("19")) {
            return 3;
        } else if (RankData.getRankSerial(rank) >= RankData.rankSerialList.indexOf("17")) {
            return 2;
        } else if (RankData.getRankSerial(rank) >= RankData.rankSerialList.indexOf("15B")) {
            return 1;
        }
        return 0;
    }

    private static int getPlayerPlanTier(Player player) {
        if (!getOverDate(player).isEmpty()) {
            Calendar overData;
            try {
                overData = Compute.StringToCalendar(getOverDate(player));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if (overData != null && overData.before(Calendar.getInstance())) {
                return 0;
            }
        }
        return getPlanData(player).getInt(TIER_KEY);
    }

    public static void setPlayerTier(Player player, int tier) {
        getPlanData(player).putInt(TIER_KEY, tier);
    }

    public static String getOverDate(Player player) {
        return getPlanData(player).getString(OVER_DATE_KEY);
    }

    public static void setOverDate(Player player, String overDate) {
        getPlanData(player).putString(OVER_DATE_KEY, overDate);
    }

    public static String getLastRewardTime(Player player) {
        return getPlanData(player).getString(LAST_REWARD_TIME_KEY);
    }

    public static void setLastRewardTime(Player player, String lastRewardTime) {
        getPlanData(player).putString(LAST_REWARD_TIME_KEY, lastRewardTime);
    }

    public static int getDailyRewardTimes(Player player) {
        return getPlanData(player).getInt(GET_DAILY_REWARD_TIMES_KEY);
    }

    public static void setDailyRewardTimes(Player player, int dailyRewardTimes) {
        getPlanData(player).putInt(GET_DAILY_REWARD_TIMES_KEY, dailyRewardTimes);
    }

    public static void addDailyRewardTimes(Player player) {
        setDailyRewardTimes(player, getDailyRewardTimes(player) + 1);
    }

    public static int getStarCounts(Player player) {
        return getPlanData(player).getInt(GET_STAR_COUNTS_KEY);
    }

    public static void setStartCounts(Player player, int counts) {
        getPlanData(player).putInt(GET_STAR_COUNTS_KEY, counts);
    }

    public static void addStarCounts(Player player, int counts) {
        setStartCounts(player, getStarCounts(player) + counts);
    }

    public static String getTowerStatus(Player player) {
        if (!getPlanData(player).contains(TOWER_STATUS_KEY)) {
            getPlanData(player).putString(TOWER_STATUS_KEY, Tower.rewardGetRecordValue);
        }
        return getPlanData(player).getString(TOWER_STATUS_KEY);
    }

    public static void setTowerStatus(Player player, String status) {
        getPlanData(player).putString(TOWER_STATUS_KEY, status);
    }

    public static void setFoodData(ServerPlayer serverPlayer) {
        if (getPlayerTier(serverPlayer) != 0) {
            serverPlayer.getFoodData().setFoodLevel(20);
        }
    }

    public static void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("计划", CustomStyle.styleOfWorld), content);
    }

    public static void sendDoubleStarTip(Player player) {
        sendFormatMSG(player, Te.s("计划已为你提供", "双倍聚星奖励", CustomStyle.styleOfWorld));
    }

    public static double getEstatePriceRate(Player player) {
        int tier = getPlayerTier(player);
        if (tier == 3) {
            return 0.8;
        } else if (tier == 2) {
            return 0.85;
        } else if (tier == 1) {
            return 0.9;
        } else {
            return 1;
        }
    }

    public static String getEstateDiscountRate(Player player) {
        int tier = getPlayerTier(player);
        if (tier == 3) {
            return "-20%";
        } else if (tier == 2) {
            return "-15%";
        } else if (tier == 1) {
            return "-10%";
        } else {
            return "-0%";
        }
    }

    public static void tryToDelayOverDate(Player player, int days) {
        if (getPlayerTier(player) > 0) {
            Calendar overDate = Compute.castStringToCalendar(getOverDate(player));
            overDate.add(Calendar.DATE, days);
            setOverDate(player, Compute.castCalendarToString(overDate));
            sendFormatMSG(player, Te.s("计划持续时间已延长", days + "天", CustomStyle.styleOfWorld));
        }
    }
}
