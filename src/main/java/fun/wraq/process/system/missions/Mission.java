package fun.wraq.process.system.missions;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.system.missions.netWorking.MissionScreenOpenS2CPacket;
import fun.wraq.process.system.missions.netWorking.MissionStatusS2CPacket;
import fun.wraq.process.system.missions.series.dailyMission.DailyMission;
import fun.wraq.process.system.missions.series.labourDay.LabourDayMission;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class Mission {

    public int serialNum;
    public Component title;
    public Component description;
    public Component description1;
    public Component frontLoadedDescription;
    public boolean canAutoSubmit;

    public Mission(int serialNum, Component title, Component description, Component description1, Component frontLoadedDescription, boolean canAutoSubmit) {
        this.serialNum = serialNum;
        this.title = title;
        this.description = description;
        this.description1 = description1;
        this.frontLoadedDescription = frontLoadedDescription;
        this.canAutoSubmit = canAutoSubmit;
    }

    public Mission(int serialNum, Component title, Component description, Component description1, boolean canAutoSubmit) {
        this.serialNum = serialNum;
        this.title = title;
        this.description = description;
        this.description1 = description1;
        this.frontLoadedDescription = Component.literal("");
        this.canAutoSubmit = canAutoSubmit;
    }

    public String getName() {
        return title.getString();
    }

    public static class Status {
        public static char unableToAccept = 'U'; // 未达接受任务条件
        public static char ableToAccepted = 'A'; // 达到任务接受条件、未开始
        public static char inProgress = 'I'; // 正在进行任务
        public static char done = 'D'; // 任务已经完成

        public static String UnableToAccept = "Unable to accept";
        public static String AbleToAccepted = "Able to accepted";
        public static String InProgress = "In Progress";
        public static String Done = "Done";

        public static Map<Character, String> characterStringMap = new HashMap<Character, String>() {{
            put(unableToAccept, UnableToAccept);
            put(ableToAccepted, AbleToAccepted);
            put(inProgress, InProgress);
            put(done, Done);
        }};

        public static Map<String, Character> stringCharacterMap = new HashMap<>() {{
            put(UnableToAccept, unableToAccept);
            put(AbleToAccepted, ableToAccepted);
            put(InProgress, inProgress);
            put(Done, done);
        }};
    }

    public static String missionStatus = "missionStatus";

    public static String originalMissionStatus = "U".repeat(192);

    public static String getPlayerMissionStatus(Player player, int serialNum) {
        CompoundTag tag = player.getPersistentData();
        if (tag.contains(missionStatus)) {
            String status = tag.getString(missionStatus);
            if (serialNum < status.length()) {
                char ch = status.charAt(serialNum);
                return Status.characterStringMap.get(ch);
            } else return null;
        } else tag.putString(missionStatus, originalMissionStatus);
        return null;
    }

    public static void setPlayerMissionStatus(Player player, int serialNum, String status) {
        CompoundTag tag = player.getPersistentData();
        if (tag.contains(missionStatus)) {
            StringBuilder stringBuilder = new StringBuilder(tag.getString(missionStatus));
            stringBuilder.setCharAt(serialNum, Status.stringCharacterMap.get(status));
            tag.putString(missionStatus, stringBuilder.toString());
        } else tag.putString(missionStatus, originalMissionStatus);
    }

    public static List<Mission> missionList = new ArrayList<>() {{
        int index = 0;
        add(new Mission(index++, Component.literal("游览地图 #1").withStyle(CustomStyle.styleOfMoon),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("平原村").withStyle(CustomStyle.styleOfPlain)),
                Component.literal(""), true)); // 位置 0

        add(new Mission(index++, Component.literal("游览地图 #2").withStyle(CustomStyle.styleOfMoon),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("雨林村").withStyle(CustomStyle.styleOfForest)),
                Component.literal(""),
                Component.literal("需先完成 游览地图#1").withStyle(ChatFormatting.WHITE), true)); // 位置 1

        add(new Mission(index++, Component.literal("游览地图 #3").withStyle(CustomStyle.styleOfMoon),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("天空城").withStyle(CustomStyle.styleOfSky)),
                Component.literal("你可以在天空城下方使用回城卷轴来前往天空城").withStyle(ChatFormatting.WHITE),
                Component.literal("需先完成 游览地图#2").withStyle(ChatFormatting.WHITE), true)); // 位置 2

        add(new Mission(index++, Component.literal("游览地图 #4").withStyle(CustomStyle.styleOfMoon),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("海岸村").withStyle(CustomStyle.styleOfLake)),
                Component.literal("游览任务不建议一次性全部完成，有些地点距离较远").withStyle(ChatFormatting.WHITE),
                Component.literal("需先完成 游览地图#3").withStyle(ChatFormatting.WHITE), true)); // 位置 3

        add(new Mission(index++, Component.literal("游览地图 #5").withStyle(CustomStyle.styleOfMoon),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("唤魔森林").withStyle(CustomStyle.styleOfMana)),
                Component.literal("游览任务不建议一次性全部完成，有些地点距离较远").withStyle(ChatFormatting.WHITE),
                Component.literal("需先完成 游览地图#4").withStyle(ChatFormatting.WHITE), true)); // 位置 4

        add(new Mission(index++, Component.literal("游览地图 #6").withStyle(CustomStyle.styleOfMoon),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("火山村").withStyle(CustomStyle.styleOfVolcano)),
                Component.literal("游览任务不建议一次性全部完成，有些地点距离较远").withStyle(ChatFormatting.WHITE),
                Component.literal("需先完成 游览地图#5").withStyle(ChatFormatting.WHITE), true)); // 位置 5

        add(new Mission(index++, Component.literal("游览地图 #7").withStyle(CustomStyle.styleOfMoon),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("北洋村").withStyle(CustomStyle.styleOfSnow)),
                Component.literal("游览任务不建议一次性全部完成，有些地点距离较远").withStyle(ChatFormatting.WHITE),
                Component.literal("需先完成 游览地图#6").withStyle(ChatFormatting.WHITE), true)); // 位置 6

        add(new Mission(index++, Component.literal("游览地图 #8").withStyle(CustomStyle.styleOfMoon),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("绯樱村").withStyle(CustomStyle.styleOfSakura)),
                Component.literal("游览任务不建议一次性全部完成，有些地点距离较远").withStyle(ChatFormatting.WHITE),
                Component.literal("需先完成 游览地图#7").withStyle(ChatFormatting.WHITE), true)); // 位置 7

        add(new Mission(index++, Component.literal("共鸣生机元素").withStyle(CustomStyle.styleOfLife),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("生机元素祭坛").withStyle(CustomStyle.styleOfLife)),
                Component.literal("").withStyle(ChatFormatting.WHITE),
                Component.literal("过时任务").withStyle(ChatFormatting.WHITE), true)); // 位置 8

        add(new Mission(index++, Component.literal("共鸣碧水元素").withStyle(CustomStyle.styleOfWater),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("碧水元素祭坛").withStyle(CustomStyle.styleOfWater)),
                Component.literal("").withStyle(ChatFormatting.WHITE),
                Component.literal("过时任务").withStyle(ChatFormatting.WHITE), true)); // 位置 9

        add(new Mission(index++, Component.literal("共鸣炽焰元素").withStyle(CustomStyle.styleOfFire),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("炽焰元素祭坛").withStyle(CustomStyle.styleOfFire)),
                Component.literal("").withStyle(ChatFormatting.WHITE),
                Component.literal("过时任务").withStyle(ChatFormatting.WHITE), true)); // 位置 10

        add(new Mission(index++, Component.literal("共鸣层岩元素").withStyle(CustomStyle.styleOfStone),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("层岩元素祭坛").withStyle(CustomStyle.styleOfStone)),
                Component.literal("").withStyle(ChatFormatting.WHITE),
                Component.literal("过时任务").withStyle(ChatFormatting.WHITE), true)); // 位置 11

        add(new Mission(index++, Component.literal("共鸣凛冰元素").withStyle(CustomStyle.styleOfIce),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("凛冰元素祭坛").withStyle(CustomStyle.styleOfIce)),
                Component.literal("").withStyle(ChatFormatting.WHITE),
                Component.literal("过时任务").withStyle(ChatFormatting.WHITE), true)); // 位置 12

        add(new Mission(index++, Component.literal("共鸣澄风元素").withStyle(CustomStyle.styleOfWind),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("澄风元素祭坛").withStyle(CustomStyle.styleOfWind)),
                Component.literal("").withStyle(ChatFormatting.WHITE),
                Component.literal("过时任务").withStyle(ChatFormatting.WHITE), true)); // 位置 13

        add(new Mission(index++, Component.literal("共鸣怒雷元素").withStyle(CustomStyle.styleOfLightning),
                Component.literal("前往:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("怒雷元素祭坛").withStyle(CustomStyle.styleOfLightning)),
                Component.literal("").withStyle(ChatFormatting.WHITE),
                Component.literal("过时任务").withStyle(ChatFormatting.WHITE), true)); // 位置 14

        add(new Mission(index++, Component.literal("从事劳动 #1").withStyle(ChatFormatting.GOLD),
                Component.literal("完成: 200次").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(" 采矿").withStyle(ChatFormatting.GRAY)),
                Component.literal("劳动节每日任务").withStyle(ChatFormatting.RED),
                Component.literal("Lv.80可接受").withStyle(ChatFormatting.WHITE), true)); // 位置 15

        add(new Mission(index++, Component.literal("从事劳动 #2").withStyle(ChatFormatting.GOLD),
                Component.literal("完成: 200次").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(" 砍伐").withStyle(CustomStyle.styleOfHusk)),
                Component.literal("劳动节每日任务").withStyle(ChatFormatting.RED),
                Component.literal("Lv.80可接受").withStyle(ChatFormatting.WHITE), true)); // 位置 16

        add(new Mission(index++, Component.literal("从事劳动 #3").withStyle(ChatFormatting.GOLD),
                Component.literal("完成: 200次").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(" 采集").withStyle(CustomStyle.styleOfField)),
                Component.literal("劳动节每日任务").withStyle(ChatFormatting.RED),
                Component.literal("Lv.80可接受").withStyle(ChatFormatting.WHITE), true)); // 位置 17

        add(new Mission(index++, Component.literal("从事劳动 #4").withStyle(ChatFormatting.GOLD),
                Component.literal("完成: 200次").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(" 钓鱼").withStyle(CustomStyle.styleOfSea)),
                Component.literal("劳动节每日任务").withStyle(ChatFormatting.RED),
                Component.literal("Lv.80可接受").withStyle(ChatFormatting.WHITE), true)); // 位置 18


        // 备注 serialNum 19 ~ 23为每日勘探任务
        add(new Mission(index++, Component.literal("日常勘探任务").withStyle(CustomStyle.styleOfSnow),
                Component.literal("前往：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("德朗斯蒂克角").withStyle(CustomStyle.styleOfLife)),
                Component.literal("日常勘探任务").withStyle(CustomStyle.styleOfMoon),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 19

        add(new Mission(index++, Component.literal("日常勘探任务").withStyle(CustomStyle.styleOfSnow),
                Component.literal("前往：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("火山").withStyle(CustomStyle.styleOfVolcano)),
                Component.literal("日常勘探任务").withStyle(CustomStyle.styleOfMoon),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 20

        add(new Mission(index++, Component.literal("日常勘探任务").withStyle(CustomStyle.styleOfSnow),
                Component.literal("前往：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("唤雷灯塔").withStyle(CustomStyle.styleOfLightning)),
                Component.literal("日常勘探任务").withStyle(CustomStyle.styleOfMoon),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 21

        add(new Mission(index++, Component.literal("日常勘探任务").withStyle(CustomStyle.styleOfSnow),
                Component.literal("前往：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("腥月岛").withStyle(CustomStyle.styleOfBloodMana)),
                Component.literal("日常勘探任务").withStyle(CustomStyle.styleOfMoon),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 22

        add(new Mission(index++, Component.literal("日常勘探任务").withStyle(CustomStyle.styleOfSnow),
                Component.literal("前往：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("月影坡").withStyle(CustomStyle.styleOfMoon)),
                Component.literal("日常勘探任务").withStyle(CustomStyle.styleOfMoon),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 23

        // 备注 24 ~ 28 为每日清理任务
        add(new Mission(index++, Component.literal("日常清理任务").withStyle(CustomStyle.styleOfBloodMana),
                Component.literal("击杀：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("平原僵尸 * 128").withStyle(CustomStyle.styleOfPlain)),
                Component.literal("日常清理任务").withStyle(CustomStyle.styleOfBloodMana),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 24

        add(new Mission(index++, Component.literal("日常清理任务").withStyle(CustomStyle.styleOfBloodMana),
                Component.literal("击杀：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("森林狼 * 128").withStyle(CustomStyle.styleOfForest)),
                Component.literal("日常清理任务").withStyle(CustomStyle.styleOfBloodMana),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 25

        add(new Mission(index++, Component.literal("日常清理任务").withStyle(CustomStyle.styleOfBloodMana),
                Component.literal("击杀：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("怀德风骨 * 128").withStyle(CustomStyle.styleOfPower)),
                Component.literal("日常清理任务").withStyle(CustomStyle.styleOfBloodMana),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 26

        add(new Mission(index++, Component.literal("日常清理任务").withStyle(CustomStyle.styleOfBloodMana),
                Component.literal("击杀：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("樱灵 * 128").withStyle(CustomStyle.styleOfSakura)),
                Component.literal("日常清理任务").withStyle(CustomStyle.styleOfBloodMana),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 27

        add(new Mission(index++, Component.literal("日常清理任务").withStyle(CustomStyle.styleOfBloodMana),
                Component.literal("击杀：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("寂域灵螨 * 128").withStyle(CustomStyle.styleOfEnd)),
                Component.literal("日常清理任务").withStyle(CustomStyle.styleOfBloodMana),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 28

        // 备注 29 ~ 33 为每日收集任务
        add(new Mission(index++, Component.literal("日常收集任务").withStyle(CustomStyle.styleOfFlexible),
                Component.literal("收集：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("平原根源 * 64").withStyle(CustomStyle.styleOfField)),
                Component.literal("日常收集任务").withStyle(ChatFormatting.GREEN),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), false)); // 位置 29

        add(new Mission(index++, Component.literal("日常收集任务").withStyle(CustomStyle.styleOfFlexible),
                Component.literal("收集：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("火山根源 * 64").withStyle(CustomStyle.styleOfSnow)),
                Component.literal("日常收集任务").withStyle(ChatFormatting.GREEN),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), false)); // 位置 30

        add(new Mission(index++, Component.literal("日常收集任务").withStyle(CustomStyle.styleOfFlexible),
                Component.literal("收集：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("天空碎片 * 64").withStyle(CustomStyle.styleOfSky)),
                Component.literal("日常收集任务").withStyle(ChatFormatting.GREEN),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), false)); // 位置 31

        add(new Mission(index++, Component.literal("日常收集任务").withStyle(CustomStyle.styleOfFlexible),
                Component.literal("收集：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("樱花 * 64").withStyle(CustomStyle.styleOfPurpleIron)),
                Component.literal("日常收集任务").withStyle(ChatFormatting.GREEN),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), false)); // 位置 32

        add(new Mission(index++, Component.literal("日常收集任务").withStyle(CustomStyle.styleOfFlexible),
                Component.literal("收集：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("莘岛史莱姆球 * 64").withStyle(CustomStyle.styleOfLife)),
                Component.literal("日常收集任务").withStyle(ChatFormatting.GREEN),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), false)); // 位置 33

        // 备注 34 ~ 38 为日常挑战任务
        add(new Mission(index++, Component.literal("日常挑战任务").withStyle(ChatFormatting.RED),
                Component.literal("挑战：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("普莱尼 8次").withStyle(CustomStyle.styleOfPlain)),
                Component.literal("日常挑战任务").withStyle(ChatFormatting.RED),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 34

        add(new Mission(index++, Component.literal("日常挑战任务").withStyle(ChatFormatting.RED),
                Component.literal("挑战：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("冰霜骑士 8次").withStyle(CustomStyle.styleOfIce)),
                Component.literal("日常挑战任务").withStyle(ChatFormatting.RED),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 35

        add(new Mission(index++, Component.literal("日常挑战任务").withStyle(ChatFormatting.RED),
                Component.literal("挑战：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("魔王 8次").withStyle(CustomStyle.styleOfDemon)),
                Component.literal("日常挑战任务").withStyle(ChatFormatting.RED),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 36

        add(new Mission(index++, Component.literal("日常挑战任务").withStyle(ChatFormatting.RED),
                Component.literal("挑战：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("阿尔忒弥斯 8次").withStyle(CustomStyle.styleOfMoon1)),
                Component.literal("日常挑战任务").withStyle(ChatFormatting.RED),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 37

        add(new Mission(index++, Component.literal("日常挑战任务").withStyle(ChatFormatting.RED),
                Component.literal("挑战：").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("紫水晶巨蟹 8次").withStyle(CustomStyle.styleOfPurpleIron)),
                Component.literal("日常挑战任务").withStyle(ChatFormatting.RED),
                Component.literal("每日任务").withStyle(ChatFormatting.WHITE), true)); // 位置 38
    }};

    public static class MissionName {
        public static String ExploreMap1 = missionList.get(0).getName();
        public static String ExploreMap2 = missionList.get(1).getName();
        public static String ExploreMap3 = missionList.get(2).getName();
        public static String ExploreMap4 = missionList.get(3).getName();
        public static String ExploreMap5 = missionList.get(4).getName();
        public static String ExploreMap6 = missionList.get(5).getName();
        public static String ExploreMap7 = missionList.get(6).getName();
        public static String ExploreMap8 = missionList.get(7).getName();

        public static String LifeElementAltar = missionList.get(8).getName();
        public static String WaterElementAltar = missionList.get(9).getName();
        public static String FireElementAltar = missionList.get(10).getName();
        public static String StoneElementAltar = missionList.get(11).getName();
        public static String IceElementAltar = missionList.get(12).getName();
        public static String WindElementAltar = missionList.get(13).getName();
        public static String LightningElementAltar = missionList.get(14).getName();

        public static String LabourDayMissionMine = missionList.get(15).getName();
        public static String LabourDayMissionLop = missionList.get(16).getName();
        public static String LabourDayMissionCrop = missionList.get(17).getName();
        public static String LabourDayMissionFish = missionList.get(18).getName();
    }

    public static Map<Integer, Mission> missionsMap = new HashMap<>() {{
        for (Mission mission : missionList) {
            put(mission.serialNum, mission);
        }
    }};

    public static Map<String, Integer> nameToSerailNumMap = new HashMap<>() {{
        for (Mission mission : missionList) {
            put(mission.getName(), mission.serialNum);
        }
    }};

    public static boolean detectPlayerMissionComplete(Player player, int serialNum) {
        String status = getPlayerMissionStatusString(player);
        if (status.charAt(serialNum) == Status.inProgress) {

            // 检测是否已是完成状态
            if (Objects.equals(getPlayerMissionStatus(player, serialNum), Status.Done)) return false;

            // 若任务有物品需求 检测玩家是否有对应数量的物品
            if (needItemContent.isEmpty()) setNeedItemContent();
            if (needItemContent.containsKey(serialNum)) {
                for (ItemStack itemStack : needItemContent.get(serialNum)) {
                    if (!InventoryOperation.checkPlayerHasItem(player.getInventory(), itemStack.getItem(), itemStack.getCount()))
                        return false;
                }
            }

            if (15 <= serialNum && serialNum <= 18) return LabourDayMission.serialNumJudge(player, serialNum);
            if ((24 <= serialNum && serialNum <= 28) || (34 <= serialNum && serialNum <= 38))
                return DailyMission.serialNumJudge(player, serialNum);
            if (posMap.containsKey(serialNum)) {
                return player.position().distanceTo(posMap.get(serialNum)) < 5;
            }

            // 无限制 则返回true
            return true;
        }
        return false;
    }

    public static Vec3[] pos = {
            new Vec3(756, 84, 207),
            new Vec3(1091, 80, 40),
            new Vec3(956, 232, 17),
            new Vec3(889, 62, -422),

            new Vec3(1499, 69, -181),
            new Vec3(2573, 120, -492),
            new Vec3(1329, 71, -1612),
            new Vec3(2381, 182, 1752),

            new Vec3(406, 72, 1006),
            new Vec3(35, -52, 997),
            new Vec3(36, 11, 1106),
            new Vec3(3, -53, 913),
            new Vec3(-217, 195, 1340),
            new Vec3(-24, 93, 1516),
            new Vec3(-146, 144, 986)
    };

    public static Vec3[] dailyMissionPos = {
            new Vec3(593, 66, 784),
            new Vec3(2601, 133, -654),
            new Vec3(1744, 67, 1204),
            new Vec3(2280, 67, 1428),
            new Vec3(1883, 147, -461)
    };

    public static Map<Integer, Vec3> posMap = new HashMap<>() {{
        for (int i = 0; i < pos.length; i++) {
            put(i, pos[i]);
        }
        for (int i = 19; i <= 23; i++) put(i, dailyMissionPos[i - 19]);
    }};

    public static String getPlayerMissionStatusString(Player player) {
        CompoundTag tag = player.getPersistentData();
        if (tag.contains(missionStatus)) {
            return tag.getString(missionStatus);
        } else tag.putString(missionStatus, originalMissionStatus);
        return originalMissionStatus;
    }

    public static Map<Integer, List<ItemStack>> rewardContent = new HashMap<>();
    public static Map<Integer, List<Component>> detailContent = new HashMap<>();

    public static void setDetailContent() {
        for (int i = 0; i < missionList.size(); i++) {
            int finalI = i;
            if (posMap.containsKey(i)) {
                detailContent.put(i, new ArrayList<>() {{
                    add(Component.literal(" 靠近坐标:").withStyle(CustomStyle.styleOfMoon).
                            append(Component.literal(" " + posMap.get(finalI))));
                }});
            }
            LabourDayMission.setMissionDetailContent(i);
        }
        DailyMission.setMissionDetailContent();
    }

    public static void setRewardContent() {
        rewardContent.put(nameToSerailNumMap.get(MissionName.ExploreMap1), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get()));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.ExploreMap2), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get()));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.ExploreMap3), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get()));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.ExploreMap4), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get()));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.ExploreMap5), new ArrayList<>() {{
            add(new ItemStack(ModItems.SkySoul.get(), 16));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.ExploreMap6), new ArrayList<>() {{
            add(new ItemStack(ModItems.goldCoin.get(), 1));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.ExploreMap7), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 1));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.ExploreMap8), new ArrayList<>() {{
            add(new ItemStack(ModItems.UnCommonLotteries.get(), 1));
            add(new ItemStack(ModItems.SakuraPetal.get(), 4));
        }});

        rewardContent.put(nameToSerailNumMap.get(MissionName.LifeElementAltar), new ArrayList<>() {{
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 32));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.WaterElementAltar), new ArrayList<>() {{
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 32));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.FireElementAltar), new ArrayList<>() {{
            add(new ItemStack(ModItems.FireElementPiece0.get(), 32));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.StoneElementAltar), new ArrayList<>() {{
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 32));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.IceElementAltar), new ArrayList<>() {{
            add(new ItemStack(ModItems.IceElementPiece0.get(), 32));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.LightningElementAltar), new ArrayList<>() {{
            add(new ItemStack(ModItems.LightningElementPiece0.get(), 32));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.WindElementAltar), new ArrayList<>() {{
            add(new ItemStack(ModItems.WindElementPiece0.get(), 32));
        }});

        rewardContent.put(nameToSerailNumMap.get(MissionName.LabourDayMissionMine), new ArrayList<>() {{
            add(new ItemStack(ModItems.OldGoldCoin.get(), 1));
            add(new ItemStack(ModItems.LabourDayLottery.get(), 1));
            add(new ItemStack(ModItems.OldSilverCoin.get(), 16));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.LabourDayMissionLop), new ArrayList<>() {{
            add(new ItemStack(ModItems.OldGoldCoin.get(), 1));
            add(new ItemStack(ModItems.LabourDayLottery.get(), 1));
            add(new ItemStack(ModItems.OldSilverCoin.get(), 16));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.LabourDayMissionCrop), new ArrayList<>() {{
            add(new ItemStack(ModItems.OldGoldCoin.get(), 1));
            add(new ItemStack(ModItems.LabourDayLottery.get(), 1));
            add(new ItemStack(ModItems.OldSilverCoin.get(), 16));
        }});
        rewardContent.put(nameToSerailNumMap.get(MissionName.LabourDayMissionFish), new ArrayList<>() {{
            add(new ItemStack(ModItems.OldGoldCoin.get(), 1));
            add(new ItemStack(ModItems.LabourDayLottery.get(), 1));
            add(new ItemStack(ModItems.OldSilverCoin.get(), 16));
        }});

        for (int i = 19; i <= 38; i++) {
            rewardContent.put(i, List.of(new ItemStack(ModItems.worldSoul5.get(), 4),
                    new ItemStack(ModItems.goldCoin.get(), 2),
                    new ItemStack(ModItems.RevelationBook.get(), 10)));
        }
    }

    public static Map<Integer, List<ItemStack>> needItemContent = new HashMap<>();

    public static void setNeedItemContent() {
        needItemContent.put(29, List.of(new ItemStack(ModItems.PlainSoul.get(), 64)));
        needItemContent.put(30, List.of(new ItemStack(ModItems.VolcanoSoul.get(), 64)));
        needItemContent.put(31, List.of(new ItemStack(ModItems.SkySoul.get(), 64)));
        needItemContent.put(32, List.of(new ItemStack(ModItems.SakuraPetal.get(), 64)));
        needItemContent.put(33, List.of(new ItemStack(ModItems.SlimeBall.get(), 64)));
    }

    public static void playerCompleteReward(Player player, int serialNum) {
        setPlayerMissionStatus(player, serialNum, Status.Done);
        if (rewardContent.isEmpty()) setRewardContent();
        rewardContent.get(serialNum).forEach(itemStack -> {
            ItemStack rewardItem = new ItemStack(itemStack.getItem(), itemStack.getCount());
            if (rewardItem.is(ModItems.worldSoul5.get())) {
                try {
                    Tower.givePlayerStar(player, itemStack.getCount() * (PlanPlayer.getPlayerTier(player) > 0 ? 2 : 1), "每日任务");
                    if (PlanPlayer.getPlayerTier(player) > 0) {
                        Compute.sendFormatMSG(player, Component.literal("本源").withStyle(CustomStyle.styleOfWorld),
                                Component.literal("计划为你额外提供了 ").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(itemStack.getCount() + "*").withStyle(CustomStyle.styleOfWorld)).
                                        append(ModItems.worldSoul5.get().getDefaultInstance().getDisplayName()));
                    }
                } catch (SQLException | ParseException e) {
                    throw new RuntimeException(e);
                }
                return;
            }

            if (rewardItem.is(ModItems.OldGoldCoin.get()) || rewardItem.is(ModItems.OldSilverCoin.get())
                    || rewardItem.is(ModItems.LabourDayLottery.get()) || rewardItem.is(ModItems.RevelationBook.get())) {
                InventoryCheck.addOwnerTagToItemStack(player, rewardItem);
            } // 为奖励添加owner标签

            InventoryOperation.itemStackGive(player, rewardItem);
        });
        if (player.experienceLevel < 160) {
            InventoryOperation.itemStackGive(player, new ItemStack(ModItems.RevelationBook.get(), 10));
        }
    }

    public static void acceptMission(Player player, String missionName) {
        acceptMission(player, nameToSerailNumMap.get(missionName));
    }

    public static void acceptMission(Player player, int serialNum) {
        if (Objects.equals(getPlayerMissionStatus(player, serialNum), Status.Done) ||
                Objects.equals(getPlayerMissionStatus(player, serialNum), Status.InProgress)) return;
        setPlayerMissionStatus(player, serialNum, Status.InProgress);
        Compute.sendFormatMSG(player, Component.literal("任务").withStyle(CustomStyle.styleOfFlexible),
                Component.literal("成功接取: ").withStyle(ChatFormatting.GREEN).
                        append(Mission.missionsMap.get(serialNum).title));

        ModNetworking.sendToClient(new MissionStatusS2CPacket(
                Mission.getPlayerMissionStatusString(player)), (ServerPlayer) player);

        ModNetworking.sendToClient(new MissionScreenOpenS2CPacket(2), (ServerPlayer) player);
    }

    public static int inProgressMission(Player player) {
        String status = getPlayerMissionStatusString(player);
        int num = 0;
        for (int i = 0; i < missionList.size(); i++) {
            if (status.charAt(i) == Status.inProgress) num++;
        }
        return num;
    }

    public static void autoAcceptMission(Player player) {
        int XpLevel = player.experienceLevel;
        Mission.acceptMission(player, 0);

        // 引导任务
        if (Objects.equals(Mission.getPlayerMissionStatus(player, 0), Status.Done)) acceptMission(player, 1);
        if (Objects.equals(Mission.getPlayerMissionStatus(player, 1), Status.Done)) acceptMission(player, 2);
        if (Objects.equals(Mission.getPlayerMissionStatus(player, 2), Status.Done)) acceptMission(player, 3);
        if (Objects.equals(Mission.getPlayerMissionStatus(player, 3), Status.Done)) acceptMission(player, 4);
        if (Objects.equals(Mission.getPlayerMissionStatus(player, 4), Status.Done)) acceptMission(player, 5);
        if (Objects.equals(Mission.getPlayerMissionStatus(player, 5), Status.Done)) acceptMission(player, 6);
        if (Objects.equals(Mission.getPlayerMissionStatus(player, 6), Status.Done)) acceptMission(player, 7);

        // 元素祭坛任务
/*        if (XpLevel >= 10) Mission.acceptMission(player,
                Mission.nameToSerailNumMap.get(MissionName.LifeElementAltar));
        if (XpLevel >= 25) Mission.acceptMission(player,
                Mission.nameToSerailNumMap.get(MissionName.WaterElementAltar));
        if (XpLevel >= 32) Mission.acceptMission(player,
                Mission.nameToSerailNumMap.get(MissionName.FireElementAltar));
        if (XpLevel >= 40) Mission.acceptMission(player,
                Mission.nameToSerailNumMap.get(MissionName.StoneElementAltar));
        if (XpLevel >= 40) Mission.acceptMission(player,
                Mission.nameToSerailNumMap.get(MissionName.IceElementAltar));
        if (XpLevel >= 70) Mission.acceptMission(player,
                Mission.nameToSerailNumMap.get(MissionName.WindElementAltar));
        if (XpLevel >= 70) Mission.acceptMission(player,
                Mission.nameToSerailNumMap.get(MissionName.LightningElementAltar));*/
    }

    public static void playerTryToSubmit(Player player, int serialNum) throws IOException {
        if (Objects.equals(Mission.getPlayerMissionStatus(player, serialNum), Status.InProgress)) {
            if (Mission.detectPlayerMissionComplete(player, serialNum)) {
                if (needItemContent.isEmpty()) setNeedItemContent();
                if (needItemContent.containsKey(serialNum)) {
                    List<ItemStack> itemStackList = needItemContent.get(serialNum);
                    for (ItemStack itemStack : itemStackList) {
                        InventoryOperation.removeItem(player.getInventory(), itemStack.getItem(), itemStack.getCount());
                    }
                }
                submitModule(player, serialNum);
            } else {
                Compute.sendFormatMSG(player, Component.literal("任务").withStyle(CustomStyle.styleOfFlexible),
                        Component.literal("似乎还没有达成任务目标").withStyle(ChatFormatting.WHITE));
            }
        } else {
            Compute.sendFormatMSG(player, Component.literal("任务").withStyle(CustomStyle.styleOfFlexible),
                    Component.literal("似乎还不能提交这个任务").withStyle(ChatFormatting.WHITE));
        }
    }

    public static void autoTryToSubmit(Player player, int serialNum) throws IOException {
        if (Objects.equals(Mission.getPlayerMissionStatus(player, serialNum), Status.InProgress)) {
            if (Mission.detectPlayerMissionComplete(player, serialNum)) {
                if (needItemContent.isEmpty()) setNeedItemContent();
                if (needItemContent.containsKey(serialNum)) {
                    List<ItemStack> itemStackList = needItemContent.get(serialNum);
                    for (ItemStack itemStack : itemStackList) {
                        InventoryOperation.removeItem(player.getInventory(), itemStack.getItem(), itemStack.getCount());
                    }
                }
                submitModule(player, serialNum);
            }
        }
    }

    public static void submitModule(Player player, int serialNum) {
        Mission.setPlayerMissionStatus(player, serialNum, Status.Done);
        Compute.sendFormatMSG(player, Component.literal("任务").withStyle(CustomStyle.styleOfFlexible),
                Component.literal("你已完成: ").withStyle(ChatFormatting.GREEN).
                        append(Mission.missionsMap.get(serialNum).title));
        ModNetworking.sendToClient(new MissionStatusS2CPacket(getPlayerMissionStatusString(player)), (ServerPlayer) player);
        ModNetworking.sendToClient(new MissionScreenOpenS2CPacket(3), (ServerPlayer) player);
        ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
        Mission.playerCompleteReward(player, serialNum);
        if (serialNum >= 15 && serialNum <= 18) LabourDayMission.finishStatusSet(player, serialNum);
    }

    public static void autoSubmit(Player player) throws IOException {
        if (player.tickCount % 5 == 0) {
            for (int i = 0; i < missionList.size(); i++) {
                if (missionList.get(i).canAutoSubmit) autoTryToSubmit(player, i);
            }
        }
    }

}
