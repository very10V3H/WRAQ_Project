package fun.wraq.process.system.respawn;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.items.m.NearestSpawnPointS2CPacket;
import fun.wraq.networking.ModNetworking;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class MyRespawnRule {
    public static void respawnPlayer(Player player) {
        player.heal(player.getMaxHealth());
        ServerLevel overWorld = player.level().getServer().getLevel(Level.OVERWORLD);
        ServerPlayer serverPlayer = (ServerPlayer) player;
        BlockPos spawnPos = serverPlayer.getRespawnPosition();
        if (spawnPos != null) {
            serverPlayer.teleportTo(overWorld,
                    spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), serverPlayer.getRespawnAngle(), 0);
        } else {
            Compute.teleportPlayerToPos(player, Utils.RESPAWN_POS);
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
        add(new SpawnPoint(3925, 82, 3491, 0, Te.s("潮汐之城", CustomStyle.styleOfSky)));
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
