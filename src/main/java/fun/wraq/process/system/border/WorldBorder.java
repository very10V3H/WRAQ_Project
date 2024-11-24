package fun.wraq.process.system.border;

import fun.wraq.common.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldBorder {

    public static Map<String, Vec3> playerOverWorldLastTimeInBorderPos = new HashMap<>();
    public static Map<String, Vec3> playerNetherLastTimeInBorderPos = new HashMap<>();
    public static Map<String, Vec3> playerEndLastTimeInBorderPos = new HashMap<>();

    public record BorderRegion(Vec2 up, Vec2 down) {}

    public static List<BorderRegion> overworldBorders = List.of(
            new BorderRegion(new Vec2(3000, 2000), new Vec2(0, -4000))
    );

    public static List<BorderRegion> netherBorders = List.of(
            new BorderRegion(new Vec2(700, -500), new Vec2(400, -700))
    );

    public static List<BorderRegion> endBorders = List.of(
            new BorderRegion(new Vec2(171, 140), new Vec2(-183, -350)),
            new BorderRegion(new Vec2(1100, -680), new Vec2(1040, -740))
    );

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.tickCount % 20 == 0 && !event.player.isCreative() && !event.player.isSpectator()) {
            ServerPlayer serverPlayer = (ServerPlayer) event.player;
            if (serverPlayer.level().dimension().equals(Level.OVERWORLD))
                sameModule(serverPlayer, playerOverWorldLastTimeInBorderPos, overworldBorders);
            if (serverPlayer.level().dimension().equals(Level.NETHER))
                sameModule(serverPlayer, playerNetherLastTimeInBorderPos, netherBorders);
            if (serverPlayer.level().dimension().equals(Level.END))
                sameModule(serverPlayer, playerEndLastTimeInBorderPos, endBorders);
        }
    }

    public static void sameModule(ServerPlayer serverPlayer, Map<String, Vec3> map, List<BorderRegion> borderRegions) {
        String name = serverPlayer.getName().getString();
        if (borderRegions.stream().anyMatch(borderRegion -> {
            return serverPlayer.position().x > borderRegion.down.x && serverPlayer.position().x < borderRegion.up.x
                    && serverPlayer.position().z > borderRegion.down.y && serverPlayer.position().z < borderRegion.up.y;
        })) {
            map.put(name, serverPlayer.position());
        } else {
            Compute.sendFormatMSG(serverPlayer, Component.literal("边界").withStyle(ChatFormatting.RED),
                    Component.literal("前面的区域，以后再来探索吧！").withStyle(ChatFormatting.WHITE));
            if (map.containsKey(name)) {
                Vec3 pos = map.get(name);
                serverPlayer.teleportTo(pos.x, pos.y, pos.z);
            } else serverPlayer.respawn();
        }
    }
}
