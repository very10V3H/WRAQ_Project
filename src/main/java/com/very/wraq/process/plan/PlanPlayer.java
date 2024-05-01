package com.very.wraq.process.plan;

import com.mojang.logging.LogUtils;
import com.very.wraq.files.dataBases.DataBase;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlanPlayer {
    public static List<PlanPlayer> list = new ArrayList<>();

    public String name;
    public int tier;
    public String overDate;
    public String lastRewardTime;

    public static String overDateString = "overDate";
    public static String lastRewardTimeString = "lastRewardTime";

    public PlanPlayer(String name, int tier, String date, String lastRewardTime) {
        this.name = name;
        this.tier = tier;
        this.overDate = date;
        this.lastRewardTime = lastRewardTime;
    }

    public static List<ItemStack> tier1DailyRewardList = new ArrayList<>();
    public static void setTier1DailyRewardList() {
        tier1DailyRewardList.add(new ItemStack(ModItems.CompleteGem.get()));
    }

    public boolean canReward() throws ParseException {
        if (lastRewardTime == null) {
            lastRewardTime = Compute.CalendarToString(Calendar.getInstance());
            return true;
        }
        Calendar calendar =  Calendar.getInstance();
        Calendar lastRewardTime = Compute.StringToCalendar(this.lastRewardTime);
        return calendar.get(Calendar.YEAR) != lastRewardTime.get(Calendar.YEAR)
                || calendar.get(Calendar.MONTH) != lastRewardTime.get(Calendar.MONTH)
                || calendar.get(Calendar.DAY_OF_MONTH) != lastRewardTime.get(Calendar.DAY_OF_MONTH);
    }

    public static void writeToSql() {
        LogUtils.getLogger().info("writing data to MySQL...");
        new Thread(() -> PlanPlayer.list.forEach(planPlayer -> {
            try {
                DataBase.put(planPlayer.name, PlanPlayer.lastRewardTimeString, Compute.CalendarToString(Calendar.getInstance()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        })).start();
    }

    public static void writeToSqlOnStopping() {
        LogUtils.getLogger().info("writing data to MySQL...");
        PlanPlayer.list.forEach(planPlayer -> {
            try {
                DataBase.put(planPlayer.name, PlanPlayer.lastRewardTimeString, Compute.CalendarToString(Calendar.getInstance()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        LogUtils.getLogger().info("written data to MySQL successfully");
    }

    public static void readFromSql() throws SQLException {
        LogUtils.getLogger().info("reading data from MySQL...");
        list.clear();
        List<String> nameList = DataBase.getKeyList("name");
        if (nameList == null) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                nameList.forEach(name -> {
                    try {
                        String calender = Compute.CalendarToString(Calendar.getInstance());
                        String overDate = DataBase.get(name, overDateString);
                        String lastRewardTime = DataBase.get(name, lastRewardTimeString);
                        String tier = DataBase.get(name, "tier");

                        list.add(new PlanPlayer(name, tier == null ? 0 : Integer.parseInt(tier),
                                overDate == null ? calender : overDate,
                                lastRewardTime == null ? calender : lastRewardTime));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }).start();
    }

    public static void readFromSqlOnStart() throws SQLException {
        LogUtils.getLogger().info("reading data from MySQL...");
        list.clear();
        List<String> nameList = DataBase.getKeyList("name");
        if (nameList == null) return;
        nameList.forEach(name -> {
            try {
                String calender = Compute.CalendarToString(Calendar.getInstance());
                String overDate = DataBase.get(name, overDateString);
                String lastRewardTime = DataBase.get(name, lastRewardTimeString);
                String tier = DataBase.get(name, "tier");

                list.add(new PlanPlayer(name, tier == null ? 0 : Integer.parseInt(tier),
                        overDate == null ? calender : overDate,
                        lastRewardTime == null ? calender : lastRewardTime));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        LogUtils.getLogger().info("read data from MySQL successfully!");
    }

    public static int GetPlayerTier(Player player) {
        for (PlanPlayer planPlayer : list) {
            if (planPlayer.name.equals(player.getName().getString())) {
                return planPlayer.tier;
            }
        }
        return -1;
    }
}
