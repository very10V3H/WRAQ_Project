package fun.wraq.process.system.missions.series.dailyMission;

import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.missions.Mission;
import fun.wraq.process.system.missions.series.dailyMission.netWorking.DailyMissionStatusS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DailyMission {
    public static Map<String, Integer> plainZombieKillCountMap = new HashMap<String, Integer>();
    public static Map<String, Integer> dreadHoundKillCountMap = new HashMap<>();
    public static Map<String, Integer> sakuraMobKillCountMap = new HashMap<>();
    public static Map<String, Integer> windSkeletonKillCountMap = new HashMap<>();
    public static Map<String, Integer> endermiteKillCountMap = new HashMap<>();

    public static Map<String, Integer> plainInstanceCountMap = new HashMap<>();
    public static Map<String, Integer> iceKnightInstanceCountMap = new HashMap<>();
    public static Map<String, Integer> devilInstanceCountMap = new HashMap<>();
    public static Map<String, Integer> moonInstanceCountMap = new HashMap<>();
    public static Map<String, Integer> purpleIronInstanceCountMap = new HashMap<>();

    public static void resetData(Player player) {
        plainZombieKillCountMap.put(player.getName().getString(), 0);
        dreadHoundKillCountMap.put(player.getName().getString(), 0);
        sakuraMobKillCountMap.put(player.getName().getString(), 0);
        windSkeletonKillCountMap.put(player.getName().getString(), 0);
        endermiteKillCountMap.put(player.getName().getString(), 0);

        plainInstanceCountMap.put(player.getName().getString(), 0);
        iceKnightInstanceCountMap.put(player.getName().getString(), 0);
        devilInstanceCountMap.put(player.getName().getString(), 0);
        moonInstanceCountMap.put(player.getName().getString(), 0);
        purpleIronInstanceCountMap.put(player.getName().getString(), 0);

        // 19 ~ 38
        Random random = new Random();
        int levelEffectRange = random.nextInt(0, Math.min(5, player.experienceLevel / 50 + 1));
        int exploreMissionSerial = 19 + levelEffectRange;
        int killMissionSerial = 24 + levelEffectRange;
        int collectMissionSerial = 29 + levelEffectRange;
        int instanceMissionSerial = 34 + levelEffectRange;

        for (int i = 19; i < 39; i++) {
            Mission.setPlayerMissionStatus(player, i, Mission.Status.UnableToAccept);
        }

        Mission.setPlayerMissionStatus(player, exploreMissionSerial, Mission.Status.InProgress);
        Mission.setPlayerMissionStatus(player, killMissionSerial, Mission.Status.InProgress);
        Mission.setPlayerMissionStatus(player, collectMissionSerial, Mission.Status.InProgress);
        Mission.setPlayerMissionStatus(player, instanceMissionSerial, Mission.Status.InProgress);
    }

    public static void addCount(Player player, Map<String, Integer> countMap) {
        countMap.put(player.getName().getString(), countMap.getOrDefault(player.getName().getString(), 0) + 1);
    }

    public static boolean serialNumJudge(Player player, int serialNum) {
        switch (serialNum) {
            case 24 -> {
                return CountsEnough(player, plainZombieKillCountMap, 192);
            }
            case 25 -> {
                return CountsEnough(player, dreadHoundKillCountMap, 192);
            }
            case 26 -> {
                return CountsEnough(player, windSkeletonKillCountMap, 192);
            }
            case 27 -> {
                return CountsEnough(player, sakuraMobKillCountMap, 192);
            }
            case 28 -> {
                return CountsEnough(player, endermiteKillCountMap, 192);
            }

            case 34 -> {
                return CountsEnough(player, plainInstanceCountMap, 8);
            }
            case 35 -> {
                return CountsEnough(player, iceKnightInstanceCountMap, 8);
            }
            case 36 -> {
                return CountsEnough(player, devilInstanceCountMap, 8);
            }
            case 37 -> {
                return CountsEnough(player, moonInstanceCountMap, 8);
            }
            case 38 -> {
                return CountsEnough(player, purpleIronInstanceCountMap, 8);
            }
        }
        return false;
    }

    public static boolean CountsEnough(Player player, Map<String, Integer> counts, int needCount) {
        return counts.containsKey(player.getName().getString()) && counts.get(player.getName().getString()) >= needCount;
    }

    public static int[] clientKillCount = new int[5];

    public static int[] clientInstanceCount = new int[5];

    public static void setMissionDetailContent() {
        for (int i = 0; i < clientKillCount.length; i++) {
            int finalI = i;
            Mission.detailContent.put(24 + i, new ArrayList<>() {{
                add(Component.literal(" 进度: ").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(clientKillCount[finalI] + " / 192").withStyle(ChatFormatting.RED)));
            }});
        }
        for (int i = 0; i < clientInstanceCount.length; i++) {
            int finalI = i;
            Mission.detailContent.put(34 + i, new ArrayList<>() {{
                add(Component.literal(" 进度: ").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(clientInstanceCount[finalI] + " / 8").withStyle(ChatFormatting.RED)));
            }});
        }
    }

    public static void statusPacketSend(Player player) {
        if (player.tickCount % 20 == 0) {
            ModNetworking.sendToClient(new DailyMissionStatusS2CPacket(new int[]{
                    plainZombieKillCountMap.getOrDefault(player.getName().getString(), 0),
                    dreadHoundKillCountMap.getOrDefault(player.getName().getString(), 0),
                    windSkeletonKillCountMap.getOrDefault(player.getName().getString(), 0),
                    sakuraMobKillCountMap.getOrDefault(player.getName().getString(), 0),
                    endermiteKillCountMap.getOrDefault(player.getName().getString(), 0),
            }, new int[]{
                    plainInstanceCountMap.getOrDefault(player.getName().getString(), 0),
                    iceKnightInstanceCountMap.getOrDefault(player.getName().getString(), 0),
                    devilInstanceCountMap.getOrDefault(player.getName().getString(), 0),
                    moonInstanceCountMap.getOrDefault(player.getName().getString(), 0),
                    purpleIronInstanceCountMap.getOrDefault(player.getName().getString(), 0),
            }), (ServerPlayer) player);
        }
    }
}
