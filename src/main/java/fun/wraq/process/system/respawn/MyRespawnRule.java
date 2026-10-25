package fun.wraq.process.system.respawn;

import fun.wraq.common.Compute;
import fun.wraq.entities.entities.npc.VmdNpc;
import fun.wraq.items.m.NearestSpawnPointS2CPacket;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.dialog.DialogManager;
import fun.wraq.process.system.npc.NpcIds;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class MyRespawnRule {

    public static final Vec3 RESPAWN_POS = new Vec3(956, 232, 17);

    public static final Vec3 FIRST_RESPAWN_POS = new Vec3(1801, 72, 252);

    /**
     * 新玩家首次进入世界：固定出生在轻轨站台，并自动打开站台贝贝的主线对话。
     */
    public static void firstRespawn(ServerPlayer player) {
        ServerLevel overWorld = player.getServer().getLevel(Level.OVERWORLD);
        if (overWorld == null) {
            return;
        }
        player.teleportTo(overWorld, FIRST_RESPAWN_POS.x, FIRST_RESPAWN_POS.y, FIRST_RESPAWN_POS.z, 0, 0);
    }

    /** 找到站台贝贝并打开主线第一段对话；NPC 尚未生成时静默跳过。 */
    public static void openFirstMainQuestDialog(ServerPlayer player) {
        ServerLevel overWorld = player.getServer().getLevel(Level.OVERWORLD);
        if (overWorld == null) {
            return;
        }
        AABB searchArea = new AABB(FIRST_RESPAWN_POS, FIRST_RESPAWN_POS).inflate(16);
        List<VmdNpc> stationNpcs = overWorld.getEntitiesOfClass(VmdNpc.class, searchArea,
                npc -> NpcIds.MAIN_BEIBEI_STATION.equals(npc.getDialogId()));
        if (stationNpcs.isEmpty()) {
            return;
        }
        stationNpcs.sort(Comparator.comparingDouble(npc -> npc.distanceToSqr(FIRST_RESPAWN_POS)));
        DialogManager.open(player, stationNpcs.get(0), NpcIds.MAIN_BEIBEI_STATION);
    }

    public static void respawnPlayer(Player player) {
        player.heal(player.getMaxHealth());
        ServerLevel overWorld = player.level().getServer().getLevel(Level.OVERWORLD);
        ServerPlayer serverPlayer = (ServerPlayer) player;
        BlockPos spawnPos = serverPlayer.getRespawnPosition();
        if (spawnPos != null) {
            serverPlayer.teleportTo(overWorld,
                    spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), serverPlayer.getRespawnAngle(), 0);
        } else {
            Compute.teleportPlayerToPos(player, RESPAWN_POS);
        }
    }

    public record SpawnPoint(Vec3 vec3, float rotX, Component zoneName) {
        public SpawnPoint(double x, double y, double z, float rotX, Component zoneName) {
            this(new Vec3(x, y, z), rotX, zoneName);
        }

        public SpawnPos toSpawnPos() {
            return new SpawnPos(vec3, rotX);
        }

        public void teleport(ServerPlayer player) {
            player.teleportTo(player.getServer().getLevel(Level.OVERWORLD), vec3.x, vec3.y, vec3.z, rotX, 0);
        }
    }

    public record SpawnPos(Vec3 vec3, float rotX) {
    }

    public static Map<String, SpawnPos> playerLastOverWorldPos = new ConcurrentHashMap<>();

    public static List<SpawnPoint> overworldSpawnPos = new ArrayList<>() {{
        add(new SpawnPoint(956, 232, 17, 0, Component.literal("天空城").withStyle(CustomStyle.styleOfSky))); // 天空城
        add(new SpawnPoint(756, 84, 207, 0, Component.literal("平原村").withStyle(CustomStyle.styleOfPlain))); // 平原村
        add(new SpawnPoint(1091, 80, 40, 0, Component.literal("雨林村").withStyle(CustomStyle.styleOfForest))); // 雨林村
        add(new SpawnPoint(889, 62, -422, 0, Component.literal("海岸村").withStyle(CustomStyle.styleOfLake))); // 海岸村
        add(new SpawnPoint(2573, 120, -492, 0, Component.literal("火山村").withStyle(CustomStyle.styleOfVolcano))); // 火山村
        add(new SpawnPoint(1157, 76, -1077, 0, Component.literal("薰楠村").withStyle(CustomStyle.styleOfJacaranda))); // 薰楠村
        add(new SpawnPoint(1036, 76, -1288, 0, Component.literal("薰曦村").withStyle(CustomStyle.styleOfJacaranda))); // 薰曦村
        add(new SpawnPoint(1329, 71, -1612, 0, Component.literal("北洋村").withStyle(CustomStyle.styleOfIce))); // 北洋村
        add(new SpawnPoint(1911, 86, 1688, 0, Component.literal("沙岸村").withStyle(CustomStyle.styleOfHusk))); // 沙岸村
        add(new SpawnPoint(2381, 182, 1752, 0, Component.literal("绯樱村").withStyle(CustomStyle.styleOfSakura))); // 绯樱村
        add(new SpawnPoint(1883, 147, -461, 0, Component.literal("月影坡").withStyle(CustomStyle.styleOfMoon)));
        add(new SpawnPoint(1921, 151, -936, 0, Component.literal("望山据点").withStyle(CustomStyle.styleOfMoontain)));
        add(new SpawnPoint(1808, 74, 339, 90, Component.literal("旭升岛").withStyle(CustomStyle.styleOfSunIsland)));
    }};

    public static void setPlayerSpawnPoint(Player player) {
        if (player.tickCount % 20 == 9) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            String name = serverPlayer.getName().getString();
            Level level = serverPlayer.level();
            if (level.dimension().equals(Level.OVERWORLD)) {
                CompletableFuture.runAsync(() -> {
                    SpawnPoint spawnPoint = findNearestSpawnPoint(player);
                    serverPlayer.setRespawnPosition(Level.OVERWORLD,
                            new BlockPos((int) spawnPoint.vec3.x, (int) spawnPoint.vec3.y, (int) spawnPoint.vec3.z),
                            spawnPoint.rotX, true, false);
                    playerLastOverWorldPos.put(name, new SpawnPos(player.position(), player.getXRot()));
                    ModNetworking.sendToClient(new NearestSpawnPointS2CPacket(spawnPoint.zoneName), serverPlayer);
                });
            }
        }
    }

    public static SpawnPoint findNearestSpawnPoint(Player player) {
        double distance = Double.MAX_VALUE;
        SpawnPoint result = overworldSpawnPos.get(0);
        for (SpawnPoint spawnPoint : overworldSpawnPos) {
            if (player.position().distanceTo(spawnPoint.vec3()) < distance) {
                distance = Math.sqrt(Math.pow(player.getX() - spawnPoint.vec3.x, 2) + Math.pow(player.getZ() - spawnPoint.vec3.z, 2));
                result = spawnPoint;
            }
        }
        return result;
    }
}
