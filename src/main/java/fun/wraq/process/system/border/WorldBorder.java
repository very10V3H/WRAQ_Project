package fun.wraq.process.system.border;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.event.TickEvent;

import java.util.List;

public class WorldBorder {
    public record BorderRegion(Vec2 up, Vec2 down) {}

    public static List<BorderRegion> overworldBorders = List.of(
            new BorderRegion(new Vec2(5600, 7000), new Vec2(2400, 1800))
    );

    public static List<BorderRegion> netherBorders = List.of(
            new BorderRegion(new Vec2(900, -300), new Vec2(200, -900))
    );

    public static List<BorderRegion> endBorders = List.of(
            new BorderRegion(new Vec2(171, 140), new Vec2(-183, -350)),
            new BorderRegion(new Vec2(1100, -680), new Vec2(1040, -740))
    );

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.tickCount % 20 == 0 && !event.player.isCreative() && !event.player.isSpectator()) {
            ServerPlayer serverPlayer = (ServerPlayer) event.player;
            if (serverPlayer.level().dimension().equals(Level.OVERWORLD))
                sameModule(serverPlayer, overworldBorders);
            if (serverPlayer.level().dimension().equals(Level.NETHER))
                sameModule(serverPlayer, netherBorders);
            if (serverPlayer.level().dimension().equals(Level.END))
                sameModule(serverPlayer, endBorders);
        }
    }

    private static final double BORDER_MARGIN = 5.0;

    public static void sameModule(ServerPlayer serverPlayer, List<BorderRegion> borderRegions) {
        // 玩家在任意一个边界区域内 → 不做处理
        if (borderRegions.stream().anyMatch(borderRegion -> {
            return serverPlayer.position().x > borderRegion.down.x && serverPlayer.position().x < borderRegion.up.x
                    && serverPlayer.position().z > borderRegion.down.y && serverPlayer.position().z < borderRegion.up.y;
        })) {
            return;
        }

        // 玩家超出所有边界区域 → 找到最近的区域，传送到该区域内距边界一定距离的位置
        BorderRegion nearestRegion = null;
        double minDistSq = Double.MAX_VALUE;
        double playerX = serverPlayer.position().x;
        double playerZ = serverPlayer.position().z;

        for (BorderRegion region : borderRegions) {
            // 计算玩家到该矩形区域最近点的距离
            double nearestX = Math.max(region.down.x, Math.min(playerX, region.up.x));
            double nearestZ = Math.max(region.down.y, Math.min(playerZ, region.up.y));
            double distSq = (playerX - nearestX) * (playerX - nearestX)
                    + (playerZ - nearestZ) * (playerZ - nearestZ);
            if (distSq < minDistSq) {
                minDistSq = distSq;
                nearestRegion = region;
            }
        }

        if (nearestRegion != null) {
            // 将玩家坐标钳制到最近区域内，并保留 margin 距离边界
            double targetX = Math.max(nearestRegion.down.x + BORDER_MARGIN,
                    Math.min(playerX, nearestRegion.up.x - BORDER_MARGIN));
            double targetZ = Math.max(nearestRegion.down.y + BORDER_MARGIN,
                    Math.min(playerZ, nearestRegion.up.y - BORDER_MARGIN));
            BlockPos surfacePos = serverPlayer.level().getHeightmapPos(
                    Heightmap.Types.MOTION_BLOCKING, new BlockPos((int) targetX, 0, (int) targetZ));
            serverPlayer.teleportTo(targetX, surfacePos.getY() + 1.0, targetZ);
        }

        Compute.sendFormatMSG(serverPlayer, Te.s("边界", ChatFormatting.RED),
                Te.s("前面的区域，以后再来探索吧！", ChatFormatting.WHITE));
    }
}
