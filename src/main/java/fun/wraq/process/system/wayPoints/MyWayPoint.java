package fun.wraq.process.system.wayPoints;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.wayPoints.networking.SpecificWayPointAddS2CPacket;
import fun.wraq.process.system.wayPoints.networking.SpecificWayPointRemoveS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.DivineUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import xaero.common.XaeroMinimapSession;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointsManager;

import java.io.IOException;
import java.util.*;

public class MyWayPoint {
    public final Vec3 pos;
    public final String name;
    public final int color;
    public final int type; // 0 - 局部 / 1 - 全局
    public final Style style;

    public MyWayPoint(Vec3 pos, String name, int color, Style style, int type) {
        this.pos = pos;
        this.name = name;
        this.color = color;
        this.type = type;
        this.style = style;
    }

    public MyWayPoint(Vec3 pos, String name, int color, int type) {
        this(pos, name, color, null, type);
    }

    public Waypoint toWayPoint() {
        Waypoint waypoint = new Waypoint((int) this.pos.x, (int) this.pos.y, (int) this.pos.z,
                this.name, this.name.substring(0, 2), this.color, 0, false);

        waypoint.setVisibilityType(this.type);
        waypoint.setType(0);
        waypoint.setTemporary(false);
        return waypoint;
    }

    public static String black = "black";
    public static String darkBlue = "dark_blue";
    public static String darkGreen = "dark_green";
    public static String darkAqua = "dark_aqua";
    public static String darkRed = "dark_red";
    public static String darkPurple = "dark_purple";
    public static String gold = "gold";
    public static String gray = "gray";
    public static String dark_gray = "dark_gray";
    public static String blue = "blue";
    public static String green = "green";
    public static String aqua = "aqua";
    public static String red = "red";
    public static String purple = "purple";
    public static String yellow = "yellow";
    public static String white = "white";

    public static Map<String, Integer> colorMap = new HashMap<>() {{
        String[] strings = {black, darkBlue, darkGreen, darkAqua, darkRed, darkPurple,
                gold, gray, dark_gray, blue, green, aqua, red, purple, yellow, white};
        for (int i = 0; i < strings.length; i++) put(strings[i], i);
    }};

    public static String wayPointSet = "wayPointSet";

    public static class ZonePoint {
        public final Vec3 pos;
        public final Component zone;
        public final Component description;
        public final int range;
        public ZonePoint(Vec3 pos, Component zone, Component description, int range) {
            this.pos = pos;
            this.zone = zone;
            this.description = description;
            this.range = range;
        }

        public ZonePoint(Vec3 pos, Component zone, Component description) {
            this(pos, zone, description, 64);
        }
    }

    public static List<ZonePoint> overWorldZonePointList = new ArrayList<>() {{
        add(new ZonePoint(new Vec3(756, 84, 207),
                Te.s("平原村", CustomStyle.styleOfPlain),
                Te.s("田园诗意，物阜民丰", CustomStyle.styleOfPlain)));
        add(new ZonePoint(new Vec3(956, 232, 17),
                Te.s("天空城", CustomStyle.styleOfSky),
                Te.s("与浮云共眠之城", CustomStyle.styleOfSky), 80));
        add(new ZonePoint(new Vec3(1091, 80, 40),
                Te.s("雨林村", CustomStyle.styleOfForest),
                Te.s("雨林葱郁，鸟语花香", CustomStyle.styleOfForest), 130));
        add(new ZonePoint(new Vec3(889, 62, -422),
                Te.s("海岸村", CustomStyle.styleOfWater),
                Te.s("碧海蓝天，金沙碧水", CustomStyle.styleOfWater), 90));
        add(new ZonePoint(new Vec3(2573, 120, -492),
                Te.s("火山村", CustomStyle.styleOfVolcano),
                Te.s("红光映天，地质奇观", CustomStyle.styleOfVolcano), 110));
        add(new ZonePoint(new Vec3(1157, 76, -1077),
                Te.s("薰楠村", CustomStyle.styleOfJacaranda),
                Te.s("浓荫蔽日，香气四溢", CustomStyle.styleOfJacaranda)));
        add(new ZonePoint(new Vec3(1036, 76, -1288),
                Te.s("薰曦村", CustomStyle.styleOfJacaranda),
                Te.s("紫色海洋，芳香四溢"), 80));
        add(new ZonePoint(new Vec3(1329, 71, -1612),
                Te.s("北洋村", CustomStyle.styleOfIce),
                Te.s("北风呼啸，寒气逼人", CustomStyle.styleOfIce), 90));
        add(new ZonePoint(new Vec3(1911, 86, 1688),
                Te.s("沙岸村", CustomStyle.styleOfSunIsland),
                Te.s("碧海蓝天，白沙绵延", CustomStyle.styleOfSunIsland)));
        add(new ZonePoint(new Vec3(2381, 182, 1752),
                Te.s("绯樱村", CustomStyle.styleOfSakura),
                Te.s("春樱散华", CustomStyle.styleOfSakura)));
        add(new ZonePoint(new Vec3(2335, 148, 17),
                Te.s("东洋塔", CustomStyle.styleOfHusk),
                Te.s("高耸入云，镇守东洋", CustomStyle.styleOfHusk)));
        add(new ZonePoint(new Vec3(1954, 153, -881),
                Te.s("望山阁", CustomStyle.styleOfMoontain),
                Te.s("坐定望山，接天攘地", CustomStyle.styleOfMoontain), 100));
        add(new ZonePoint(new Vec3(1364, 79, 44),
                Te.s("炼魔平原", CustomStyle.styleOfMana),
                Te.s("旧时战争遗地", CustomStyle.styleOfMana), 100));
        add(new ZonePoint(new Vec3(1101, 76, 260),
                Te.s("纽维雨林", CustomStyle.styleOfForest),
                Te.s("生机与危机共存", CustomStyle.styleOfForest), 100));
        add(new ZonePoint(new Vec3(1054, 226, 626),
                Te.s("尘月之梦", CustomStyle.styleOfMoon1),
                Te.s("月影朦胧，星光璀璨", CustomStyle.styleOfMoon1)));
        add(new ZonePoint(new Vec3(1484, 63, -240),
                Te.s("唤魔湖", CustomStyle.styleOfMana),
                Te.s("鬼魅出没，邪气森森", CustomStyle.styleOfMana)));
        add(new ZonePoint(new Vec3(2243, 72, 1418),
                Te.s("腥月岛", CustomStyle.styleOfBloodMana),
                Te.s("血腥与海雾弥漫", CustomStyle.styleOfBloodMana)));
        add(new ZonePoint(new Vec3(2310, 140, 1586),
                Te.s("绯樱林", CustomStyle.styleOfSakura),
                Te.s("粉黛如霞，花落如雨", CustomStyle.styleOfSakura)));
        add(new ZonePoint(new Vec3(2435, 163, 1569),
                Te.s("蓝花林", CustomStyle.styleOfJacaranda),
                Te.s("紫雾迷蒙，蝶舞花丛", CustomStyle.styleOfJacaranda)));
        add(new ZonePoint(new Vec3(1743, 68, 1285),
                Te.s("雷光岛", CustomStyle.styleOfLightning),
                Te.s("唤起万千响雷", CustomStyle.styleOfLightning)));
        add(new ZonePoint(new Vec3(2459, 170, 1753),
                Te.s("粉钻矿区", CustomStyle.styleOfSakura),
                Te.s("璀璨夺目，光华流转", CustomStyle.styleOfSakura)));
        add(new ZonePoint(new Vec3(1408, 12, -2853),
                Te.s("北境晶钻矿区", CustomStyle.styleOfIce),
                Te.s("冷艳高贵，华美绝伦", CustomStyle.styleOfIce)));
        add(new ZonePoint(new Vec3(1808, 74, 339),
                Te.s("旭升岛", CustomStyle.styleOfSunIsland),
                Te.s("旭日东升", CustomStyle.styleOfSunIsland), 100));
        add(new ZonePoint(new Vec3(1883, 147, -461),
                Te.s("月影坡", CustomStyle.styleOfMoon),
                Te.s("西望皎月，东临朔望", CustomStyle.styleOfMoon)));
        add(new ZonePoint(new Vec3(1761, 130, -463),
                Te.s("尘月宫", CustomStyle.styleOfMoon),
                Te.s("满月皎洁，苍穹如洗", CustomStyle.styleOfMoon)));
        add(new ZonePoint(new Vec3(2352, -34, -704),
                Te.s("远古之城", CustomStyle.styleOfWarden),
                Te.s("神秘遗迹，石墙巍峨", CustomStyle.styleOfWarden), 100));
        add(new ZonePoint(new Vec3(2417, 152, -1372),
                Te.s("暗黑城堡", CustomStyle.styleOfCastle),
                Te.s("危险的暗黑魔法遗址", CustomStyle.styleOfCastle), 90));
        add(new ZonePoint(new Vec3(1088, 23, 892),
                Te.s("海底神殿", CustomStyle.styleOfSea),
                Te.s("波光粼粼，深邃神秘", CustomStyle.styleOfSea)));
        add(new ZonePoint(new Vec3(2454, 130, -171),
                Te.s("蒙特轻轨基地", CustomStyle.styleOfField),
                Te.s("", CustomStyle.styleOfSea)));
        add(new ZonePoint(new Vec3(1908, 165, -1596),
                Te.s("北部高地", CustomStyle.styleOfPlain),
                Te.s("", CustomStyle.styleOfPlain), 70));
        add(new ZonePoint(new Vec3(2006, 130, -1785),
                Te.s("菌菇聚落", CustomStyle.MUSHROOM_STYLE),
                Te.s("", CustomStyle.MUSHROOM_STYLE)));
        add(new ZonePoint(new Vec3(1364, 79, 44),
                Te.s("年兽出没地", CustomStyle.styleOfSpring),
                Te.s("", CustomStyle.styleOfSpring)));
    }};

    public static Map<Player, String> playerLastZoneMap = new WeakHashMap<>();

    public static void zoneTick(Player player) {
        if (player.tickCount % 20 == 0) {
            if (player.level().dimension().equals(Level.OVERWORLD)) {
                Vec3 pos = player.position();
                double distance = Double.MAX_VALUE;
                ZonePoint nearestZonePoint = null;
                for (ZonePoint zonePoint : overWorldZonePointList) {
                    if (zonePoint.pos.distanceTo(pos) < distance) {
                        distance = zonePoint.pos.distanceTo(pos);
                        nearestZonePoint= zonePoint;
                    }
                }
                if (nearestZonePoint == null) return;

                if (!nearestZonePoint.zone.getString().equals(playerLastZoneMap.getOrDefault(player, ""))
                        && distance < nearestZonePoint.range) {
                    playerLastZoneMap.put(player, nearestZonePoint.zone.getString());
                    Compute.setPlayerTitleAndSubTitle((ServerPlayer) player,
                            nearestZonePoint.zone, Te.s(""));
                }
            }
        }
    }

    public static class VillageWayPoint {
        public static final MyWayPoint PLAIN_VILLAGE
                = new MyWayPoint(new Vec3(756, 84, 207), "平原村",
                colorMap.get(green), CustomStyle.styleOfPlain, 0);
        public static final MyWayPoint SKY_CITY
                = new MyWayPoint(new Vec3(956, 232, 17), "天空城",
                colorMap.get(aqua), CustomStyle.styleOfSky, 0);
        public static final MyWayPoint FOREST_VILLAGE
                = new MyWayPoint(new Vec3(1091, 80, 40), "雨林村",
                colorMap.get(darkGreen), CustomStyle.styleOfForest, 0);
        public static final MyWayPoint SEA_VILLAGE
                = new MyWayPoint(new Vec3(889, 62, -422), "海岸村",
                colorMap.get(blue), CustomStyle.styleOfSea, 0);
        public static final MyWayPoint VOLCANO_VILLAGE
                = new MyWayPoint(new Vec3(2573, 120, -492), "火山村",
                colorMap.get(yellow), CustomStyle.styleOfVolcano, 0);
        public static final MyWayPoint XUNNAN_VILLAGE
                = new MyWayPoint(new Vec3(1157, 76, -1077), "薰楠村",
                colorMap.get(purple), CustomStyle.styleOfJacaranda, 0);
        public static final MyWayPoint XUNXI_VILLAGE
                = new MyWayPoint(new Vec3(1036, 76, -1288), "薰曦村",
                colorMap.get(purple), CustomStyle.styleOfJacaranda, 0);
        public static final MyWayPoint SNOW_VILLAGE
                = new MyWayPoint(new Vec3(1329, 71, -1612), "北洋村",
                colorMap.get(aqua), CustomStyle.styleOfSnow, 0);
        public static final MyWayPoint SAND_VILLAGE
                = new MyWayPoint(new Vec3(1911, 86, 1688), "沙岸村",
                colorMap.get(yellow), CustomStyle.styleOfHusk, 0);
        public static final MyWayPoint SAKURA_VILLAGE
                = new MyWayPoint(new Vec3(2381, 182, 1752), "绯樱村",
                colorMap.get(purple), CustomStyle.styleOfSakura, 0);
        public static final MyWayPoint MOONTAIN_BASE
                = new MyWayPoint(new Vec3(1921, 151, -936), "望山据点",
                colorMap.get(darkGreen), CustomStyle.styleOfMoontain, 0);
        public static final MyWayPoint SUN_RISE_ISLAND
                = new MyWayPoint(new Vec3(1808, 74, 339), "旭升岛",
                colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0);
        public static final MyWayPoint FOR_NORTH_VILLAGE
                = new MyWayPoint(new Vec3(1731, 137, 1875), "北望村",
                colorMap.get(green), CustomStyle.styleOfSunIsland, 0);

        public static List<MyWayPoint> getAllVillageWayPoints() {
            return List.of(
                    PLAIN_VILLAGE,
                    SKY_CITY,
                    FOREST_VILLAGE,
                    SEA_VILLAGE,
                    VOLCANO_VILLAGE,
                    XUNNAN_VILLAGE,
                    XUNXI_VILLAGE,
                    SNOW_VILLAGE,
                    SAND_VILLAGE,
                    SAKURA_VILLAGE,
                    MOONTAIN_BASE,
                    SUN_RISE_ISLAND,
                    FOR_NORTH_VILLAGE
            );
        }
    }

    public static List<MyWayPoint> overworldPointList = new ArrayList<>() {{
        add(VillageWayPoint.PLAIN_VILLAGE);
        add(VillageWayPoint.SKY_CITY);
        add(new MyWayPoint(new Vec3(754, 181, -86), "德朗斯蒂克", colorMap.get(green), CustomStyle.styleOfPlain, 0));
        add(VillageWayPoint.FOREST_VILLAGE);
        add(VillageWayPoint.SEA_VILLAGE);
        add(VillageWayPoint.VOLCANO_VILLAGE);
        add(VillageWayPoint.XUNNAN_VILLAGE);
        add(VillageWayPoint.XUNXI_VILLAGE);
        add(VillageWayPoint.SNOW_VILLAGE);
        add(VillageWayPoint.SAND_VILLAGE);
        add(VillageWayPoint.SAKURA_VILLAGE);
        add(new MyWayPoint(new Vec3(2122, 152, -1138), "艾里蒙特大陆", colorMap.get(purple), CustomStyle.styleOfWorld, 0));
        add(new MyWayPoint(new Vec3(735, 79, 263), "平原僵尸刷怪点", colorMap.get(green), CustomStyle.styleOfPlain, 0));
        add(new MyWayPoint(new Vec3(621, 70, 443), "怀德风骨刷怪点", colorMap.get(green), CustomStyle.styleOfKaze, 0));
        add(new MyWayPoint(new Vec3(887, 79, 229), "森林僵尸刷怪点", colorMap.get(darkGreen), CustomStyle.styleOfForest, 0));
        add(new MyWayPoint(new Vec3(1106, 72, 132), "雨林蜘蛛刷怪点", colorMap.get(darkGreen), CustomStyle.styleOfForest, 0));
        add(new MyWayPoint(new Vec3(804, 68, 366), "脆弱的岩灵刷怪点", colorMap.get(yellow), CustomStyle.styleOfHusk, 0));
        add(new MyWayPoint(new Vec3(1220, 58, 55), "河流故灵刷怪点", colorMap.get(blue), CustomStyle.styleOfLake, 0));
        add(new MyWayPoint(new Vec3(1287, 75, 62), "焰芒虫刷怪点", colorMap.get(red), CustomStyle.styleOfFire, 0));
        add(new MyWayPoint(new Vec3(1479, 78, -19), "炽魂刷怪点", colorMap.get(red), CustomStyle.styleOfFire, 0));
        add(new MyWayPoint(new Vec3(1500, 69, -179), "唤魔者刷怪点", colorMap.get(purple), CustomStyle.styleOfMana, 0));
        add(new MyWayPoint(new Vec3(1050, 65, -510), "史莱姆刷怪点", colorMap.get(green), CustomStyle.styleOfLife, 0));
        add(new MyWayPoint(new Vec3(1559, 63, -2214), "冰川流浪者刷怪点", colorMap.get(aqua), CustomStyle.styleOfIce, 0));
        add(new MyWayPoint(new Vec3(1119, 77, -1208), "紫晶妖妇刷怪点", colorMap.get(purple), CustomStyle.styleOfJacaranda, 0));
        add(new MyWayPoint(new Vec3(1614, 74, 45), "伐木工刷怪点", colorMap.get(darkGreen), CustomStyle.styleOfForest, 0));
        add(new MyWayPoint(new Vec3(1768, 78, 43), "森林狼刷怪点", colorMap.get(darkGreen), CustomStyle.styleOfForest, 0));
        add(new MyWayPoint(new Vec3(1188, 70, -29), "矿洞入口", colorMap.get(gray), CustomStyle.styleOfMine, 0));
        add(new MyWayPoint(new Vec3(976, 250, 47), "本源回收装置", colorMap.get(aqua), CustomStyle.styleOfWorld, 0));
        add(new MyWayPoint(new Vec3(2414, 151, -1537), "烽火台哨卫刷怪点", colorMap.get(red), CustomStyle.styleOfMagma, 0));
        add(new MyWayPoint(new Vec3(2392, 154, -1443), "古树魔能研究者刷怪点", colorMap.get(purple), CustomStyle.styleOfLife, 0));
        add(new MyWayPoint(new Vec3(2450, 149, -1419), "熔岩湖溢出物刷怪点", colorMap.get(red), CustomStyle.styleOfMagma, 0));
        add(new MyWayPoint(new Vec3(1057, 226, 616), "梦灵刷怪点", colorMap.get(white), CustomStyle.styleOfMoon1, 0));
        add(new MyWayPoint(new Vec3(1088, 23, 892), "神殿守卫刷怪点", colorMap.get(blue), CustomStyle.styleOfSea, 0));
        add(new MyWayPoint(new Vec3(1039, 67, 1094), "海盗刷怪点", colorMap.get(blue), CustomStyle.styleOfSea, 0));
        add(new MyWayPoint(new Vec3(1743, 69, 1241), "雷光灯塔守卫刷怪点", colorMap.get(blue), CustomStyle.styleOfLightingIsland, 0));
        add(new MyWayPoint(new Vec3(2262, 71, 1408), "腥月血灵刷怪点", colorMap.get(red), CustomStyle.styleOfBloodMana, 0));
        add(new MyWayPoint(new Vec3(2388, 163, 1622), "地蕴蓝灵刷怪点", colorMap.get(purple), CustomStyle.styleOfJacaranda, 0));
        add(new MyWayPoint(new Vec3(2338, 151, 1683), "樱灵刷怪点", colorMap.get(purple), CustomStyle.styleOfSakura, 0));
        add(new MyWayPoint(new Vec3(2566, 126, -621), "炽魂刷怪点-A", colorMap.get(red), CustomStyle.styleOfFire, 0));
        add(new MyWayPoint(new Vec3(2609, 132, -650), "炽鬼刷怪点", colorMap.get(red), CustomStyle.styleOfFire, 0));
        add(new MyWayPoint(new Vec3(2624, 192, 1724), "魔王挑战点", colorMap.get(red), CustomStyle.styleOfDemon, 0));
        add(new MyWayPoint(new Vec3(1565, 63, -2924), "冰霜骑士挑战点", colorMap.get(aqua), CustomStyle.styleOfIce, 0));
        add(new MyWayPoint(new Vec3(1761, 130, -463), "阿尔忒弥斯挑战点", colorMap.get(white), CustomStyle.styleOfMoon, 0));
        add(new MyWayPoint(new Vec3(1167, 111, 24), "普莱尼挑战点", colorMap.get(green), CustomStyle.styleOfPlain, 0));
        add(new MyWayPoint(new Vec3(1171.5, -35.5, -171.5), "紫晶巨蟹挑战点", colorMap.get(purple), CustomStyle.styleOfPurpleIron, 0));
        add(new MyWayPoint(new Vec3(2257, 140, 1694), "突见忍挑战点", colorMap.get(gold), CustomStyle.styleOfSakura, 0));
        add(new MyWayPoint(new Vec3(2417, 152, -1372), "暗黑城堡团队副本挑战点", colorMap.get(black), CustomStyle.styleOfCastle, 0));
        add(new MyWayPoint(new Vec3(2335, 148, 17), "东洋塔", colorMap.get(red), CustomStyle.styleOfHusk, 0));
        // 24.9.15 新增地点命名
        add(new MyWayPoint(new Vec3(2132, 304, -228), "朔山", colorMap.get(blue), CustomStyle.styleOfMoon1, 0));
        add(new MyWayPoint(new Vec3(2232, 281, -775), "望山", colorMap.get(blue), CustomStyle.styleOfMoontain, 0));
        add(new MyWayPoint(new Vec3(1954, 153, -881), "望山阁", colorMap.get(darkPurple), CustomStyle.styleOfMoontain, 0));
        add(new MyWayPoint(new Vec3(1460, 74, -900), "炼雨湖", colorMap.get(aqua), CustomStyle.MANA_TOWER_STYLE, 0));
        add(new MyWayPoint(new Vec3(1488, 105, -1059), "炼雨府邸", colorMap.get(darkAqua), CustomStyle.MANA_TOWER_STYLE, 0));
        add(new MyWayPoint(new Vec3(2688, 222, -968), "焱山", colorMap.get(darkRed), CustomStyle.styleOfFire,  0));
        add(new MyWayPoint(new Vec3(1534, 81, 305), "炼魔平原", colorMap.get(darkPurple), CustomStyle.styleOfMana, 0));
        add(new MyWayPoint(new Vec3(1584, 81, 143), "炼魔庙", colorMap.get(darkPurple), CustomStyle.styleOfMana, 0));
        add(new MyWayPoint(new Vec3(1742, 63, 241), "旭升湾", colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0));
        add(new MyWayPoint(new Vec3(1101, 76, 260), "纽维雨林", colorMap.get(green), CustomStyle.styleOfForest, 0));
        add(new MyWayPoint(new Vec3(1280, 82, 209), "纽维庙", colorMap.get(green), CustomStyle.styleOfForest, 0));
        add(new MyWayPoint(new Vec3(1147, 300, 554), "尘月之梦", colorMap.get(white), CustomStyle.styleOfMoon, 0));
        add(new MyWayPoint(new Vec3(1484, 63, -240), "唤魔湖", colorMap.get(purple), CustomStyle.styleOfMana, 0));
        add(new MyWayPoint(new Vec3(1376, 81, -271), "唤魔庙", colorMap.get(purple), CustomStyle.styleOfMana, 0));
        add(new MyWayPoint(new Vec3(2243, 72, 1418), "腥月岛", colorMap.get(red), CustomStyle.styleOfBloodMana, 0));
        add(new MyWayPoint(new Vec3(2310, 140, 1586), "绯樱林", colorMap.get(purple), CustomStyle.styleOfSakura, 0));
        add(new MyWayPoint(new Vec3(2437, 135, 1485), "蓝花林", colorMap.get(blue), CustomStyle.styleOfJacaranda, 0));
        add(new MyWayPoint(new Vec3(1743, 68, 1285), "雷光岛", colorMap.get(blue), CustomStyle.styleOfLightingIsland, 0));
        add(new MyWayPoint(new Vec3(2688, 222, -968), "焱山", colorMap.get(red), CustomStyle.styleOfFire, 0));
        add(new MyWayPoint(new Vec3(1216, 63, -107), "斯蒂河", colorMap.get(blue), CustomStyle.styleOfWater, 0));
        add(new MyWayPoint(new Vec3(1035, 63, -629), "斯蒂河", colorMap.get(blue), CustomStyle.styleOfWater, 0));
        add(new MyWayPoint(new Vec3(658, 63, -437), "德朗湾", colorMap.get(blue), CustomStyle.styleOfWater, 0));
        add(new MyWayPoint(new Vec3(752, 63, -1241), "薰花河", colorMap.get(blue), CustomStyle.styleOfWater, 0));
        add(new MyWayPoint(new Vec3(886, 63, -836), "薰花河", colorMap.get(blue), CustomStyle.styleOfWater, 0));
        add(new MyWayPoint(new Vec3(136, 63, -1433), "薰花河", colorMap.get(blue), CustomStyle.styleOfWater, 0));
        add(new MyWayPoint(new Vec3(1276, 63, -1503), "北洋江", colorMap.get(blue), CustomStyle.styleOfWater, 0));
        add(new MyWayPoint(new Vec3(871, 63, -1886), "江东湖", colorMap.get(blue), CustomStyle.styleOfWater, 0));
        add(new MyWayPoint(new Vec3(290, 63, -1738), "江西湖", colorMap.get(blue), CustomStyle.styleOfWater, 0));
        add(new MyWayPoint(new Vec3(1173, 63, -2095), "北西洋", colorMap.get(aqua), CustomStyle.styleOfWater, 0));
        add(new MyWayPoint(new Vec3(2320, 63, -2111), "北东洋", colorMap.get(aqua), CustomStyle.styleOfWater, 0));
        add(new MyWayPoint(new Vec3(416, 63, -3332), "北西海", colorMap.get(aqua), CustomStyle.styleOfWater, 0));
        add(new MyWayPoint(new Vec3(2964, 63, -3404), "北东海", colorMap.get(aqua), CustomStyle.styleOfWater, 0));
        add(VillageWayPoint.MOONTAIN_BASE);
        add(new MyWayPoint(new Vec3(2046, 172, -818), "望山矿道西口", colorMap.get(darkGreen), CustomStyle.styleOfMoontain, 0));
        add(new MyWayPoint(new Vec3(2342, 182, -790), "望山矿道东口", colorMap.get(darkRed), CustomStyle.styleOfMoontain, 0));
        add(new MyWayPoint(new Vec3(2412, 185, 1733), "绯樱村 - 魔王府邸通路西口", colorMap.get(red), CustomStyle.styleOfDemon, 0));
        add(new MyWayPoint(new Vec3(2459, 170, 1753), "粉钻矿区", colorMap.get(purple), CustomStyle.styleOfSakura, 0));
        add(new MyWayPoint(new Vec3(1408, 12, -2853), "北境晶钻矿区", colorMap.get(aqua), CustomStyle.styleOfIce, 0));
        add(VillageWayPoint.SUN_RISE_ISLAND);
        add(new MyWayPoint(new Vec3(1168, -36, -69), "玛瑙矿区", colorMap.get(green), CustomStyle.styleOfLife, 0));
        add(new MyWayPoint(new Vec3(1883, 147, -461), "月影坡", colorMap.get(yellow), CustomStyle.styleOfMoon, 0));
        add(new MyWayPoint(new Vec3(962, 207, 13), "天空城传送中枢", colorMap.get(darkPurple), CustomStyle.styleOfSky, 0));
        add(new MyWayPoint(new Vec3(2352, -34, -704), "远古之城", colorMap.get(darkBlue), CustomStyle.styleOfWarden, 0));
        add(VillageWayPoint.FOR_NORTH_VILLAGE);
        add(new MyWayPoint(new Vec3(1934, -25, 1798), "鹰眼工厂", colorMap.get(red), CustomStyle.styleOfHarbinger, 0));
        add(new MyWayPoint(new Vec3(1573, 54, 149), "炼魔涌溢", colorMap.get(darkPurple), CustomStyle.styleOfMana, 0));
        add(new MyWayPoint(new Vec3(2006, 130, -1785), "菌菇聚落", colorMap.get(red), CustomStyle.MUSHROOM_STYLE, 0));
        // 轻轨
        add(new MyWayPoint(new Vec3(2454, 130, -171), "蒙特轻轨检修基地", colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0));
        add(new MyWayPoint(new Vec3(2343, 130, 31), "轻轨东洋塔站", colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0));
        add(new MyWayPoint(new Vec3(2343, 130, 31), "轻轨东洋塔站", colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0));
        add(new MyWayPoint(new Vec3(1800, 72, 274), "轻轨旭升岛站", colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0));
        add(new MyWayPoint(new Vec3(1213, 85, 54), "轻轨雨林村站", colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0));
        add(new MyWayPoint(new Vec3(1003, 226, 0), "轻轨天空城站", colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0));
        add(new MyWayPoint(new Vec3(948, 79, -423), "轻轨海岸村站", colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0));
        add(new MyWayPoint(new Vec3(1071, 80, -1328), "轻轨薰曦村站", colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0));
        add(new MyWayPoint(new Vec3(1270, 72, -1622), "轻轨北洋村站", colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0));
        add(new MyWayPoint(new Vec3(2472, 170, -1374), "轻轨暗黑城堡站", colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0));
        add(new MyWayPoint(new Vec3(2496, 149, -486), "轻轨火山村站", colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0));
        add(new MyWayPoint(new Vec3(1908, 162, -1655), "轻轨北部高地站", colorMap.get(yellow), CustomStyle.styleOfSunIsland, 0));
        // 地铁
        add(new MyWayPoint(new Vec3(2054, 60, 1705), "艾樱地铁检修基地", colorMap.get(purple), CustomStyle.styleOfSakura, 0));
        add(new MyWayPoint(new Vec3(1898, 78, 1664), "地铁沙岸村站", colorMap.get(purple), CustomStyle.styleOfSakura, 0));
        add(new MyWayPoint(new Vec3(1759, 68, 1232), "地铁雷光岛站", colorMap.get(purple), CustomStyle.styleOfSakura, 0));
        add(new MyWayPoint(new Vec3(1797, 74, 318), "地铁旭升岛站", colorMap.get(purple), CustomStyle.styleOfSakura, 0));
        add(new MyWayPoint(new Vec3(1880, 147, -451), "地铁月影坡站", colorMap.get(purple), CustomStyle.styleOfSakura, 0));
        add(new MyWayPoint(new Vec3(1916, 151, -943), "地铁望山据点站", colorMap.get(purple), CustomStyle.styleOfSakura, 0));
        add(new MyWayPoint(new Vec3(1900, 158, -1648), "地铁北部高地站", colorMap.get(purple), CustomStyle.styleOfSakura, 0));
        add(new MyWayPoint(DivineUtils.ToDivineIslandBoatPos, "圣光岛轮渡 - 往圣光岛",
                colorMap.get(yellow), CustomStyle.DIVINE_STYLE, 0));
        add(new MyWayPoint(DivineUtils.ToSunIslandBoatPos, "圣光岛轮渡 - 往旭升岛",
                colorMap.get(yellow), CustomStyle.DIVINE_STYLE, 0));
        add(new MyWayPoint(new Vec3(2280, 66, 844), "南部边境",
                colorMap.get(yellow), CustomStyle.DIVINE_STYLE, 0));
        add(new MyWayPoint(new Vec3(2325, 88, 761), "通港南路",
                colorMap.get(yellow), CustomStyle.DIVINE_STYLE, 0));
        add(new MyWayPoint(new Vec3(2356, 86, 687), "萨仁镇",
                colorMap.get(blue), CustomStyle.DIVINE_STYLE, 0));
        add(new MyWayPoint(new Vec3(2420, 67, 691), "瑕光树",
                colorMap.get(darkGreen), CustomStyle.GHASTLY_STYLE, 0));
        add(new MyWayPoint(new Vec3(2421, 85, 712), "瑕光古栈",
                colorMap.get(darkGreen), CustomStyle.GHASTLY_STYLE, 0));
        add(new MyWayPoint(new Vec3(3841, -16, 1929), "熔岩地堡",
                colorMap.get(red), CustomStyle.BUNKER_STYLE, 0));
        // 铁匠铺
        add(new MyWayPoint(new Vec3(730, 85, 210), "平原村铁匠铺", colorMap.get(gray), CustomStyle.styleOfStone, 0));
        add(new MyWayPoint(new Vec3(976, 232, 34), "天空城铁匠铺", colorMap.get(gray), CustomStyle.styleOfStone, 0));
        add(new MyWayPoint(new Vec3(1114, 83, 46), "雨林村铁匠铺", colorMap.get(gray), CustomStyle.styleOfStone, 0));
        add(new MyWayPoint(new Vec3(1783, 76, 302), "旭升岛铁匠铺", colorMap.get(gray), CustomStyle.styleOfStone, 0));
        add(new MyWayPoint(new Vec3(2555, 122, -501), "火山村铁匠铺", colorMap.get(gray), CustomStyle.styleOfStone, 0));
        add(new MyWayPoint(new Vec3(1303, 67, -1637), "北洋村铁匠铺", colorMap.get(gray), CustomStyle.styleOfStone, 0));
        add(new MyWayPoint(new Vec3(1045, 77, -1282), "薰曦村铁匠铺", colorMap.get(gray), CustomStyle.styleOfStone, 0));
        add(new MyWayPoint(new Vec3(1152, 74, -1059), "薰楠村铁匠铺", colorMap.get(gray), CustomStyle.styleOfStone, 0));
        add(new MyWayPoint(new Vec3(882, 64, -409), "海岸村铁匠铺", colorMap.get(gray), CustomStyle.styleOfStone, 0));
        add(new MyWayPoint(new Vec3(1731, 114, 1855), "北望村铁匠铺", colorMap.get(gray), CustomStyle.styleOfStone, 0));
        add(new MyWayPoint(new Vec3(1931, 86, 1686), "沙岸村铁匠铺", colorMap.get(gray), CustomStyle.styleOfStone, 0));
        add(new MyWayPoint(new Vec3(2394, 179, 1728), "绯樱村铁匠铺", colorMap.get(gray), CustomStyle.styleOfStone, 0));
    }};

    public static List<MyWayPoint> netherPointList = new ArrayList<>() {{
        add(new MyWayPoint(new Vec3(603, 79, -601), "下界猪灵刷怪点", colorMap.get(gold), CustomStyle.styleOfGold, 0));
        add(new MyWayPoint(new Vec3(558, 67, -633), "下界凋零骷髅/骷髅刷怪点", colorMap.get(gray), CustomStyle.styleOfWither, 0));
        add(new MyWayPoint(new Vec3(529, 64, -541), "燃魂挑战点", colorMap.get(red), CustomStyle.styleOfFire, 0));
        add(new MyWayPoint(new Vec3(480, 64, -617), "熔岩能量聚合物刷怪点", colorMap.get(red), CustomStyle.styleOfMagma, 0));
    }};

    @OnlyIn(Dist.CLIENT)
    public static void clientTick(TickEvent.PlayerTickEvent event) {
        Player player = Minecraft.getInstance().player;
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(player)) {
            int tickCount = event.player.tickCount;
            if (tickCount == 400) {
                if (player.level().dimension().equals(Level.OVERWORLD)) {
                    overworldPointList.forEach(myWayPoint -> removeWaypointByName(myWayPoint.name));
                    netherPointList.forEach(myWayPoint -> removeWaypointByName(myWayPoint.name));
                    setClientWaypoints(overworldPointList);
                }
                if (player.level().dimension().equals(Level.NETHER)) {
                    overworldPointList.forEach(myWayPoint -> removeWaypointByName(myWayPoint.name));
                    netherPointList.forEach(myWayPoint -> removeWaypointByName(myWayPoint.name));
                    setClientWaypoints(netherPointList);
                }
            }
        }
    }

    public static void setClientWaypoints(List<MyWayPoint> pointList) {
        XaeroMinimapSession minimapSession = XaeroMinimapSession.getCurrentSession();
        WaypointsManager waypointsManager = minimapSession.getWaypointsManager();
        if (waypointsManager.getCurrentWorld() == null) return;
        List<Waypoint> list = waypointsManager.getCurrentWorld().getCurrentSet().getList();
        Set<String> nameSet = new HashSet<>();
        list.forEach(waypoint -> nameSet.add(waypoint.getName()));
        Set<Vec3> posSet = new HashSet<>();
        list.forEach(waypoint -> posSet.add(new Vec3(waypoint.getX(), waypoint.getY(), waypoint.getZ())));
        pointList.forEach(myWayPoint -> {
            Waypoint waypoint = myWayPoint.toWayPoint();
            if (!nameSet.contains(waypoint.getName())
                    && !posSet.contains(new Vec3(waypoint.getX(), waypoint.getY(), waypoint.getZ())))
                list.add(waypoint);
        });
        try {
            minimapSession.getModMain().getSettings().saveWaypoints(waypointsManager.getCurrentWorld());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendAddPacketToClient(Player player, MyWayPoint myWayPoint) {
        ModNetworking.sendToClient(new SpecificWayPointAddS2CPacket(myWayPoint.pos.toVector3f(), myWayPoint.name,
                myWayPoint.color, myWayPoint.type), (ServerPlayer) player);
    }

    public static void addWaypoint(MyWayPoint myWayPoint) {
        XaeroMinimapSession minimapSession = XaeroMinimapSession.getCurrentSession();
        WaypointsManager waypointsManager = minimapSession.getWaypointsManager();
        List<Waypoint> list = waypointsManager.getCurrentWorld().getCurrentSet().getList();
        Waypoint waypoint = myWayPoint.toWayPoint();
        boolean contain = false;
        for (Waypoint waypoint1 : list) {
            if (waypoint1.getName().equals(waypoint.getName())) contain = true;
        }
        if (!contain) list.add(waypoint);
        try {
            minimapSession.getModMain().getSettings().saveWaypoints(waypointsManager.getCurrentWorld());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendRemovePacketToClient(Player player, String name) {
        ModNetworking.sendToClient(new SpecificWayPointRemoveS2CPacket(name), (ServerPlayer) player);
    }

    public static void removeWaypointByName(String name) {
        XaeroMinimapSession minimapSession = XaeroMinimapSession.getCurrentSession();
        WaypointsManager waypointsManager = minimapSession.getWaypointsManager();
        List<Waypoint> list = waypointsManager.getCurrentWorld().getCurrentSet().getList();
        list.removeIf(waypoint -> waypoint.getName().equals(name));
        try {
            minimapSession.getModMain().getSettings().saveWaypoints(waypointsManager.getCurrentWorld());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
