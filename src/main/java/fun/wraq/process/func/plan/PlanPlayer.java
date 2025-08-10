package fun.wraq.process.func.plan;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.files.dataBases.DataBase;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.process.system.lottery.NewLotteries;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PlanPlayer {
    public static int clientPlanLeftDate = 0;
    public static int clientPlanTier = 0;

    public final static Map<String, PlanPlayer> map = new HashMap<>();

    public String name;
    public int tier;
    public String overDate;
    public String lastRewardTime;
    public int getDailyRewardTimes;

    public static String tierString = "tier";
    public static String overDateString = "overDate";
    public static String lastRewardTimeString = "lastRewardTime";
    public static String getDailyRewardTimesString = "getDailyRewardTimes";

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

    public static void onPlayerLoginSync(Player player) {
        CompoundTag data = getPlanData(player);
        if (!data.contains(SYNC_FLAG_KEY)) {
            data.putBoolean(SYNC_FLAG_KEY, true);
            if (map.containsKey(Name.get(player))) {
                PlanPlayer planPlayer = map.get(Name.get(player));
                data.putInt(TIER_KEY, planPlayer.tier);
                data.putString(OVER_DATE_KEY, planPlayer.overDate);
                data.putString(LAST_REWARD_TIME_KEY, planPlayer.lastRewardTime == null ? "" : planPlayer.lastRewardTime);
                data.putInt(GET_DAILY_REWARD_TIMES_KEY, planPlayer.getDailyRewardTimes);
                data.putInt(GET_STAR_COUNTS_KEY, Tower.playerStarGetCounts.getOrDefault(Name.get(player), 0));
                for (Item item : NewLotteries.getGetRewardSerial().keySet()) {
                    String itemString = item.toString();
                    String openTimesString = itemString + "_openTimes";
                    String winTimesString = itemString + "_winTimes";
                    int rewardTimes = NewLotteries.getPlayerLotteryData(Name.get(player))
                            .getOrDefault(itemString, 0);
                    int openTimes = NewLotteries.getPlayerLotteryData(Name.get(player))
                            .getOrDefault(openTimesString, 0);
                    int winTimes = NewLotteries.getPlayerLotteryData(Name.get(player))
                            .getOrDefault(winTimesString, 0);
                    getLotteryData(player).putInt(itemString, rewardTimes);
                    getLotteryData(player).putInt(openTimesString, openTimes);
                    getLotteryData(player).putInt(winTimesString, winTimes);
                }
                Compute.sendFormatMSG(player, Te.s("安全", CustomStyle.styleOfFlexible),
                        Te.s("计划/开箱统计/聚星获得数已同步至新版数据存储"));
            }
        }
    }

    public static void read() throws SQLException, ParseException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        map.clear();

        String sql = "select * from playerdata1";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String overDate = resultSet.getString(overDateString);
            String lastRewardTime = resultSet.getString(lastRewardTimeString);
            String tier = resultSet.getString("tier");
            String getDailyRewardTimes = resultSet.getString(getDailyRewardTimesString);
            int tierInt = 0;
            int getDailyRewardTimesInt = 0;
            if (tier != null) tierInt = Integer.parseInt(tier);
            if (getDailyRewardTimes != null) getDailyRewardTimesInt = Integer.parseInt(getDailyRewardTimes);
            if (overDate == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 31);
                overDate = Compute.CalendarToString(calendar);
            }
            if (map.containsKey(name) && lastRewardTime != null) {
                PlanPlayer planPlayer = map.get(name);
                String lastRewardTimeCurrent = planPlayer.lastRewardTime;
                if (lastRewardTimeCurrent != null) {
                    Calendar lastRewardTimeCurrentCalendar = Compute.StringToCalendar(lastRewardTimeCurrent);
                    Calendar lastRewardTimeCalendar = Compute.StringToCalendar(lastRewardTime);
                    if (lastRewardTimeCurrentCalendar.after(lastRewardTimeCalendar)) {
                        lastRewardTime = planPlayer.lastRewardTime;
                    }
                }
            }
            map.put(name, new PlanPlayer(name, tierInt, overDate, lastRewardTime, getDailyRewardTimesInt));

            for (Item item : NewLotteries.getGetRewardSerial().keySet()) {
                String itemString = item.toString();
                String openTimes = itemString + "_openTimes";
                String winTimes = itemString + "_winTimes";
                handle(resultSet, name, itemString);
                handle(resultSet, name, openTimes);
                handle(resultSet, name, winTimes);
            }
        }
        statement.close();
    }

    public static void handle(ResultSet resultSet, String name, String itemString) throws SQLException {
        if (DataBase.containsKey(itemString, "playerdata1")) {
            String timesString = resultSet.getString(itemString);
            int times = 0;
            if (timesString != null) times = Integer.parseInt(timesString);
            NewLotteries.getPlayerLotteryData(name).put(itemString, times);
        } else {
            NewLotteries.getPlayerLotteryData(name).put(itemString, 0);
        }
    }

    public static int getPlayerTier(Player player) {
        String rank = RankData.getCurrentRank(player);
        if (RankData.getRankSerial(rank) >= RankData.rankSerialList.indexOf("19")) {
            return 3;
        } else if (RankData.getRankSerial(rank) >= RankData.rankSerialList.indexOf("17")) {
            return 2;
        } else if (RankData.getRankSerial(rank) >= RankData.rankSerialList.indexOf("15B")) {
            return 1;
        }
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
