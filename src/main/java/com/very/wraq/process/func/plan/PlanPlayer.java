package com.very.wraq.process.func.plan;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.files.dataBases.DataBase;
import com.very.wraq.process.system.lottery.NewLotteries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.*;

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

    public static List<ItemStack> tier1DailyRewardList = new ArrayList<>();

    public static void setTier1DailyRewardList() {
        tier1DailyRewardList.add(new ItemStack(ModItems.completeGem.get()));
    }

    public boolean canReward() throws ParseException {
        if (lastRewardTime == null || lastRewardTime.equals("null")) {
            lastRewardTime = Compute.CalendarToString(Calendar.getInstance());
            return true;
        }
        Calendar calendar = Calendar.getInstance();
        Calendar lastRewardTime = Compute.StringToCalendar(this.lastRewardTime);
        return calendar.before(Compute.StringToCalendar(overDate)) && calendar.get(Calendar.DATE) != lastRewardTime.get(Calendar.DATE);
    }

    public static void rewardPlayer(Player player) throws ParseException {
        if (!map.containsKey(player.getName().getString())) return;
        PlanPlayer planPlayer = map.get(player.getName().getString());
        if (planPlayer.canReward()) {
            planPlayer.lastRewardTime = Compute.CalendarToString(Calendar.getInstance());
            player.sendSystemMessage(Component.literal("reward"));

            // reward content
        } else {
            // time
            player.sendSystemMessage(Component.literal("cant reward"));
        }
    }

/*    public static void writeToSql() {
        LogUtils.getLogger().info("writing data to MySQL...");
        new Thread(() -> PlanPlayer.list.forEach(planPlayer -> {
            try {
                DataBase.put(planPlayer.name, PlanPlayer.lastRewardTimeString, planPlayer.lastRewardTime);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        })).start();
    }*/

    public static void synchronizedWrite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    write();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public static void write() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        PlanPlayer.map.forEach((key, value) -> {
            try {
                if (value.lastRewardTime != null)
                    DataBase.put(statement, value.name, PlanPlayer.lastRewardTimeString, value.lastRewardTime);
                if (value.overDate != null)
                    DataBase.put(statement, value.name, PlanPlayer.overDateString, value.overDate);
                DataBase.put(statement, value.name, PlanPlayer.tierString, String.valueOf(value.tier));
                DataBase.put(statement, value.name, PlanPlayer.getDailyRewardTimesString, String.valueOf(value.getDailyRewardTimes));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        statement.close();
        connection.close();
    }

    public static void synchronizedRead() throws SQLException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    read();
                } catch (SQLException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public static void synchronizedReadTier() throws SQLException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    readTier();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public static void readTier() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from playerdata1";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String overDate = resultSet.getString(overDateString);
            String lastRewardTime = resultSet.getString(lastRewardTimeString);
            String tier = resultSet.getString("tier");
            int tierInt = 0;
            if (tier != null) tierInt = Integer.parseInt(tier);
            if (map.containsKey(name)) {
                PlanPlayer planPlayer = map.get(name);
                planPlayer.tier = tierInt;
            } else {
                if (overDate == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, 31);
                    overDate = Compute.CalendarToString(calendar);
                }
                if (lastRewardTime == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, -1);
                    lastRewardTime = Compute.CalendarToString(calendar);
                }
                map.put(name, new PlanPlayer(name, tierInt, overDate, lastRewardTime, 0));
            }
        }
        statement.close();
        connection.close();
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
        connection.close();
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

    public static int getPlayerTier(Player player) throws ParseException {
        String name = player.getName().getString();
        if (!map.containsKey(name)) return 0;
        else {
            PlanPlayer planPlayer = map.get(name);
            String overDate = planPlayer.overDate;
            if (overDate == null) return 0;
            Calendar calendar = Calendar.getInstance();
            if (calendar.after(Compute.StringToCalendar(overDate))) return 0;
            else return map.get(name).tier;
        }
    }

    public static void setFoodData(ServerPlayer serverPlayer) throws ParseException {
        if (getPlayerTier(serverPlayer) != 0) {
            serverPlayer.getFoodData().setFoodLevel(20);
        }
    }
}
