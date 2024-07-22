package com.very.wraq.process.system.missions.series.labourDay;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.process.system.missions.Mission;
import com.very.wraq.process.system.missions.series.labourDay.netWorking.LabourDayMissionStatusS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LabourDayMission {
    public static Map<Player, Integer> mineCounts = new HashMap<Player, Integer>();
    public static Map<Player, Integer> lopCounts = new HashMap<Player, Integer>();
    public static Map<Player, Integer> cropCounts = new HashMap<Player, Integer>();
    public static Map<Player, Integer> fishCounts = new HashMap<Player, Integer>();

    public static boolean serialNumJudge(Player player, int serialNum) {
        switch (serialNum) {
            case 15 -> {
                return countsEnough(player, mineCounts);
            }
            case 16 -> {
                return countsEnough(player, lopCounts);
            }
            case 17 -> {
                return countsEnough(player, cropCounts);
            }
            case 18 -> {
                return countsEnough(player, fishCounts);
            }
            default -> {
                return false;
            }
        }
    }

    public static boolean countsEnough(Player player, Map<Player, Integer> map) {
        return map.containsKey(player) && map.get(player) != -1 && map.get(player) > 200;
    }

    public static void count(Player player, Map<Player, Integer> map) {
        /*map.put(player, map.getOrDefault(player, 0) + 1);*/
    }

    public static void finishStatusSet(Player player, int serialNum) {
        switch (serialNum) {
            case 15 -> mineCounts.put(player, -1);
            case 16 -> lopCounts.put(player, -1);
            case 17 -> cropCounts.put(player, -1);
            case 18 -> fishCounts.put(player, -1);
        }
    }

    public static void clear(Player player) {
        for (int i = 15; i <= 18; i++) {
            Mission.setPlayerMissionStatus(player, i, Mission.Status.UnableToAccept);
        }
    }

    public static void acceptStatusSet(Player player) {
        mineCounts.put(player, 0);
        lopCounts.put(player, 0);
        cropCounts.put(player, 0);
        fishCounts.put(player, 0);
        for (int i = 15; i <= 18; i++) Mission.setPlayerMissionStatus(player, i, Mission.Status.InProgress);
    }

    public static int clientMineCounts = 0;
    public static int clientLopCounts = 0;
    public static int clientCropCounts = 0;
    public static int clientFishCounts = 0;

    public static void sendPacketsToPlayer(Player player) {
        if (player.tickCount % 20 == 0) ModNetworking.sendToClient(new LabourDayMissionStatusS2CPacket(
                mineCounts.getOrDefault(player, 0), lopCounts.getOrDefault(player, 0),
                cropCounts.getOrDefault(player, 0), fishCounts.getOrDefault(player, 0)), (ServerPlayer) player);
    }

    public static void setMissionDetailContent(int i) {
        if (i == 15) Mission.detailContent.put(i, new ArrayList<>() {{
            add(Component.literal(" 进度: ").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(LabourDayMission.clientMineCounts + " / 200").withStyle(ChatFormatting.RED)));
        }});
        if (i == 16) Mission.detailContent.put(i, new ArrayList<>() {{
            add(Component.literal(" 进度: ").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(LabourDayMission.clientLopCounts + " / 200").withStyle(ChatFormatting.RED)));
        }});
        if (i == 17) Mission.detailContent.put(i, new ArrayList<>() {{
            add(Component.literal(" 进度: ").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(LabourDayMission.clientCropCounts + " / 200").withStyle(ChatFormatting.RED)));
        }});
        if (i == 18) Mission.detailContent.put(i, new ArrayList<>() {{
            add(Component.literal(" 进度: ").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(LabourDayMission.clientFishCounts + " / 200").withStyle(ChatFormatting.RED)));
        }});
    }
}


