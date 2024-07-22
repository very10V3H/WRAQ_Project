package com.very.wraq.process.system.respawn;

import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRespawnRule {
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

    public static Map<String, SpawnPos> playerLastOverWorldPos = new HashMap<>();

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
        add(new SpawnPoint(2417, 152, -1372, 0, Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle)));
        add(new SpawnPoint(1883, 147, -461, 0, Component.literal("月影坡").withStyle(CustomStyle.styleOfMoon)));
    }};

    public static void setPlayerSpawnPoint(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        String name = serverPlayer.getGameProfile().getName();
        Level level = serverPlayer.level();
        Level overworld = serverPlayer.getServer().getLevel(Level.OVERWORLD);
        if (level.equals(overworld)) {
            SpawnPoint spawnPoint = findNearestSpawnPoint(player);
            serverPlayer.setRespawnPosition(Level.OVERWORLD, new BlockPos((int) spawnPoint.vec3.x, (int) spawnPoint.vec3.y, (int) spawnPoint.vec3.z), spawnPoint.rotX, true, false);
            playerLastOverWorldPos.put(name, new SpawnPos(player.position(), player.getXRot()));
        } else {
            SpawnPos spawnPos = playerLastOverWorldPos.getOrDefault(name, overworldSpawnPos.get(0).toSpawnPos());
            serverPlayer.setRespawnPosition(overworld.dimension(), new BlockPos((int) spawnPos.vec3.x, (int) spawnPos.vec3.y, (int) spawnPos.vec3.z), spawnPos.rotX, true, false);
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
