package fun.wraq.process.system.wayPoints;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.wayPoints.networking.SpecificWayPointAddS2CPacket;
import fun.wraq.process.system.wayPoints.networking.SpecificWayPointRemoveS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
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
/*        add(new ZonePoint(new Vec3(756, 84, 207),
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
                Te.s("风之高地", CustomStyle.styleOfWind),
                Te.s("", CustomStyle.styleOfWind), 70));
        add(new ZonePoint(new Vec3(2006, 130, -1785),
                Te.s("菌菇聚落", CustomStyle.MUSHROOM_STYLE),
                Te.s("", CustomStyle.MUSHROOM_STYLE)));
        add(new ZonePoint(new Vec3(1364, 79, 44),
                Te.s("年兽出没地", CustomStyle.styleOfSpring),
                Te.s("", CustomStyle.styleOfSpring)));*/
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
        add(new MyWayPoint(new Vec3(3925, 82, 3499), "潮汐城中央广场", colorMap.get(aqua), CustomStyle.styleOfWorld, 0));
        add(new MyWayPoint(new Vec3(3977, 76, 3416), "潮汐城东北门", colorMap.get(aqua), CustomStyle.styleOfWorld, 0));
        add(new MyWayPoint(new Vec3(4021, 119, 3158), "项潮林", colorMap.get(green), CustomStyle.styleOfWorld, 0));
        add(new MyWayPoint(new Vec3(3725, 69, 2976), "苍岩隘口", colorMap.get(gray), CustomStyle.styleOfStone, 0));
        add(new MyWayPoint(new Vec3(3759, 81, 3902), "熔岩废墟", colorMap.get(red), CustomStyle.styleOfVolcano, 0));
    }};

    public static List<MyWayPoint> netherPointList = new ArrayList<>() {{
/*        add(new MyWayPoint(new Vec3(603, 79, -601), "下界猪灵刷怪点", colorMap.get(gold), CustomStyle.styleOfGold, 0));
        add(new MyWayPoint(new Vec3(558, 67, -633), "下界凋零骷髅/骷髅刷怪点", colorMap.get(gray), CustomStyle.styleOfWither, 0));
        add(new MyWayPoint(new Vec3(529, 64, -541), "燃魂挑战点", colorMap.get(red), CustomStyle.styleOfFire, 0));
        add(new MyWayPoint(new Vec3(480, 64, -617), "熔岩能量聚合物刷怪点", colorMap.get(red), CustomStyle.styleOfMagma, 0));*/
    }};

    @OnlyIn(Dist.CLIENT)
    public static void clientTick(TickEvent.PlayerTickEvent event) {
        Player player = Minecraft.getInstance().player;
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(player)) {
            int tickCount = event.player.tickCount;
            if (tickCount >= Tick.s(10)) {
                List<MyWayPoint> points = new ArrayList<>();
                if (player.level().dimension().equals(Level.OVERWORLD)) {
                    points = overworldPointList;
                }
                if (player.level().dimension().equals(Level.NETHER)) {
                    points = netherPointList;
                }
                if (tickCount - Tick.s(10) < points.size()) {
                    addWaypoint(points.get(tickCount - Tick.s(10)));
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
