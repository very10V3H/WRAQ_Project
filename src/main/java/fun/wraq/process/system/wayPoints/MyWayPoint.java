package fun.wraq.process.system.wayPoints;

import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.wayPoints.networking.SpecificWayPointAddS2CPacket;
import fun.wraq.process.system.wayPoints.networking.SpecificWayPointRemoveS2CPacket;
import net.minecraft.client.Minecraft;
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
    public Vec3 pos;
    public String name;
    public int color;
    public int type; // 0 - 局部 / 1 - 全局

    public MyWayPoint(Vec3 pos, String name, int color, int type) {
        this.pos = pos;
        this.name = name;
        this.color = color;
        this.type = type;
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

    public static List<MyWayPoint> overworldPointList = new ArrayList<>() {{
        add(new MyWayPoint(new Vec3(756, 84, 207), "平原村", colorMap.get(green), 0));
        add(new MyWayPoint(new Vec3(956, 232, 17), "天空城", colorMap.get(aqua), 0));
        add(new MyWayPoint(new Vec3(754, 181, -86), "德朗斯蒂克", colorMap.get(green), 1));
        add(new MyWayPoint(new Vec3(1091, 80, 40), "雨林村", colorMap.get(darkGreen), 0));
        add(new MyWayPoint(new Vec3(889, 62, -422), "海岸村", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(2573, 120, -492), "火山村", colorMap.get(yellow), 0));
        add(new MyWayPoint(new Vec3(1157, 76, -1077), "薰楠村", colorMap.get(purple), 0));
        add(new MyWayPoint(new Vec3(1036, 76, -1288), "薰曦村", colorMap.get(purple), 0));
        add(new MyWayPoint(new Vec3(1329, 71, -1612), "北洋村", colorMap.get(aqua), 0));
        add(new MyWayPoint(new Vec3(1911, 86, 1688), "沙岸村", colorMap.get(yellow), 0));
        add(new MyWayPoint(new Vec3(2381, 182, 1752), "绯樱村", colorMap.get(purple), 0));
        add(new MyWayPoint(new Vec3(2381, 182, 1752), "绯樱村", colorMap.get(purple), 0));
        add(new MyWayPoint(new Vec3(2122, 152, -1138), "艾里蒙特大陆", colorMap.get(purple), 0));

        add(new MyWayPoint(new Vec3(735, 79, 263), "平原僵尸刷怪点", colorMap.get(green), 0));
        add(new MyWayPoint(new Vec3(621, 70, 443), "怀德风骨刷怪点", colorMap.get(green), 0));
        add(new MyWayPoint(new Vec3(887, 79, 229), "森林僵尸刷怪点", colorMap.get(darkGreen), 0));
        add(new MyWayPoint(new Vec3(1106, 72, 132), "雨林蜘蛛刷怪点", colorMap.get(darkGreen), 0));
        add(new MyWayPoint(new Vec3(804, 68, 366), "脆弱的岩灵刷怪点", colorMap.get(yellow), 0));
        add(new MyWayPoint(new Vec3(1220, 58, 55), "河流故灵刷怪点", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(1287, 75, 62), "焰芒虫刷怪点", colorMap.get(red), 0));
        add(new MyWayPoint(new Vec3(1479, 78, -19), "炽魂刷怪点", colorMap.get(red), 0));
        add(new MyWayPoint(new Vec3(1500, 69, -179), "唤魔者刷怪点", colorMap.get(purple), 0));
        add(new MyWayPoint(new Vec3(1050, 65, -510), "史莱姆刷怪点", colorMap.get(green), 0));
        add(new MyWayPoint(new Vec3(1559, 63, -2214), "冰川流浪者刷怪点", colorMap.get(aqua), 0));
        add(new MyWayPoint(new Vec3(1119, 77, -1208), "紫晶妖妇刷怪点", colorMap.get(purple), 0));
        add(new MyWayPoint(new Vec3(1614, 74, 45), "伐木工刷怪点", colorMap.get(darkGreen), 0));
        add(new MyWayPoint(new Vec3(1768, 78, 43), "森林狼刷怪点", colorMap.get(darkGreen), 0));
        add(new MyWayPoint(new Vec3(1188, 70, -29), "矿洞入口", colorMap.get(gray), 0));
        add(new MyWayPoint(new Vec3(976, 250, 47), "本源回收装置", colorMap.get(aqua), 0));

        add(new MyWayPoint(new Vec3(2414, 151, -1537), "烽火台哨卫刷怪点", colorMap.get(red), 0));
        add(new MyWayPoint(new Vec3(2392, 154, -1443), "古树魔能研究者刷怪点", colorMap.get(purple), 0));
        add(new MyWayPoint(new Vec3(2450, 149, -1419), "熔岩湖溢出物刷怪点", colorMap.get(red), 0));

        add(new MyWayPoint(new Vec3(1057, 226, 616), "梦灵刷怪点", colorMap.get(white), 0));
        add(new MyWayPoint(new Vec3(1088, 23, 892), "神殿守卫刷怪点", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(1039, 67, 1094), "海盗刷怪点", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(1743, 69, 1241), "雷光灯塔守卫刷怪点", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(2262, 71, 1408), "腥月血灵刷怪点", colorMap.get(red), 0));
        add(new MyWayPoint(new Vec3(2388, 163, 1622), "地蕴蓝灵刷怪点", colorMap.get(purple), 0));
        add(new MyWayPoint(new Vec3(2338, 151, 1683), "樱灵刷怪点", colorMap.get(purple), 0));

        add(new MyWayPoint(new Vec3(2566, 126, -621), "炽魂刷怪点-A", colorMap.get(red), 0));
        add(new MyWayPoint(new Vec3(2609, 132, -650), "炽鬼刷怪点", colorMap.get(red), 0));

        add(new MyWayPoint(new Vec3(2624, 192, 1724), "魔王挑战点", colorMap.get(red), 0));
        add(new MyWayPoint(new Vec3(1565, 63, -2924), "冰霜骑士挑战点", colorMap.get(aqua), 0));
        add(new MyWayPoint(new Vec3(1761, 130, -463), "阿尔忒弥斯挑战点", colorMap.get(white), 0));
        add(new MyWayPoint(new Vec3(1167, 111, 24), "普莱尼挑战点", colorMap.get(green), 0));
        add(new MyWayPoint(new Vec3(1171.5, -35.5, -171.5), "紫晶巨蟹挑战点", colorMap.get(purple), 0));
        add(new MyWayPoint(new Vec3(2257, 140, 1694), "突见忍挑战点", colorMap.get(gold), 0));
        add(new MyWayPoint(new Vec3(2417, 152, -1372), "暗黑城堡团队副本挑战点", colorMap.get(black), 0));

        add(new MyWayPoint(new Vec3(2335, 148, 17), "东洋塔", colorMap.get(red), 0));

        // 24.9.15 新增地点命名
        add(new MyWayPoint(new Vec3(2132, 304, -228), "朔山", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(2232, 281, -775), "望山", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(1954, 153, -881), "望山阁", colorMap.get(darkPurple), 0));

        add(new MyWayPoint(new Vec3(1460, 74, -900), "炼雨湖", colorMap.get(aqua), 0));
        add(new MyWayPoint(new Vec3(1488, 105, -1059), "炼雨府邸", colorMap.get(darkAqua), 0));

        add(new MyWayPoint(new Vec3(2688, 222, -968), "焱山", colorMap.get(darkRed), 0));

        add(new MyWayPoint(new Vec3(1534, 81, 305), "炼魔平原", colorMap.get(darkPurple), 0));
        add(new MyWayPoint(new Vec3(1584, 81, 143), "炼魔庙", colorMap.get(darkPurple), 0));
        add(new MyWayPoint(new Vec3(1742, 63, 241), "旭升湾", colorMap.get(yellow), 0));
        add(new MyWayPoint(new Vec3(1101, 76, 260), "纽维雨林", colorMap.get(green), 0));
        add(new MyWayPoint(new Vec3(1280, 82, 209), "纽维庙", colorMap.get(green), 0));
        add(new MyWayPoint(new Vec3(1147, 300, 554), "尘月之梦", colorMap.get(white), 0));
        add(new MyWayPoint(new Vec3(1484, 63, -240), "唤魔湖", colorMap.get(purple), 0));
        add(new MyWayPoint(new Vec3(1376, 81, -271), "唤魔庙", colorMap.get(purple), 0));
        add(new MyWayPoint(new Vec3(2243, 72, 1418), "腥月岛", colorMap.get(red), 0));
        add(new MyWayPoint(new Vec3(2310, 140, 1586), "绯樱林", colorMap.get(purple), 0));
        add(new MyWayPoint(new Vec3(2437, 135, 1485), "蓝花林", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(1743, 68, 1285), "雷光岛", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(2688, 222, -968), "焱山", colorMap.get(red), 0));
        add(new MyWayPoint(new Vec3(1216, 63, -107), "斯蒂河", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(1035, 63, -629), "斯蒂河", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(658, 63, -437), "德朗湾", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(752, 63, -1241), "薰花河", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(886, 63, -836), "薰花河", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(136, 63, -1433), "薰花河", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(1276, 63, -1503), "北洋江", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(871, 63, -1886), "江东湖", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(290, 63, -1738), "江西湖", colorMap.get(blue), 0));
        add(new MyWayPoint(new Vec3(1173, 63, -2095), "北西洋", colorMap.get(aqua), 0));
        add(new MyWayPoint(new Vec3(2320, 63, -2111), "北东洋", colorMap.get(aqua), 0));
        add(new MyWayPoint(new Vec3(416, 63, -3332), "北西海", colorMap.get(aqua), 0));
        add(new MyWayPoint(new Vec3(2964, 63, -3404), "北东海", colorMap.get(aqua), 0));

        add(new MyWayPoint(new Vec3(1921, 151, -936), "望山据点", colorMap.get(darkGreen), 0));
        add(new MyWayPoint(new Vec3(2046, 172, -818), "望山矿道西口", colorMap.get(darkGreen), 0));
        add(new MyWayPoint(new Vec3(2342, 182, -790), "望山矿道东口", colorMap.get(darkRed), 0));

        add(new MyWayPoint(new Vec3(2412, 185, 1733), "绯樱村 - 魔王府邸通路西口", colorMap.get(red), 0));

        add(new MyWayPoint(new Vec3(2459, 170, 1753), "粉钻矿区", colorMap.get(purple), 0));
        add(new MyWayPoint(new Vec3(1408, 12, -2853), "北境晶钻矿区", colorMap.get(aqua), 0));

        add(new MyWayPoint(new Vec3(1808, 74, 339), "旭升岛", colorMap.get(yellow), 0));

    }};

    public static List<MyWayPoint> netherPointList = new ArrayList<>() {{
        add(new MyWayPoint(new Vec3(603, 79, -601), "下界猪灵刷怪点", colorMap.get(gold), 0));
        add(new MyWayPoint(new Vec3(558, 67, -633), "下界凋零骷髅/骷髅刷怪点", colorMap.get(gray), 0));
        add(new MyWayPoint(new Vec3(529, 64, -541), "燃魂挑战点", colorMap.get(red), 0));
        add(new MyWayPoint(new Vec3(480, 64, -617), "熔岩能量聚合物刷怪点", colorMap.get(red), 0));
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
        ModNetworking.sendToClient(new SpecificWayPointAddS2CPacket(myWayPoint.pos.toVector3f(), myWayPoint.name, myWayPoint.color, myWayPoint.type), (ServerPlayer) player);
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
