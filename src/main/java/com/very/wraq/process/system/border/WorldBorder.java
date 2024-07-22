package com.very.wraq.process.system.border;

import com.very.wraq.common.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.util.HashMap;
import java.util.Map;

public class WorldBorder {

    public static Map<String, Vec3> playerOverWorldLastTimeInBorderPos = new HashMap<>();
    public static Map<String, Vec3> playerNetherLastTimeInBorderPos = new HashMap<>();
    public static Map<String, Vec3> playerEndLastTimeInBorderPos = new HashMap<>();

    public static Vec2 overWorldBorderUp = new Vec2(3000, 2000);
    public static Vec2 overWorldBorderDown = new Vec2(0, -4000);

    public static Vec2 netherBorderUp = new Vec2(700, -500);
    public static Vec2 netherBorderDown = new Vec2(400, -700);

    public static Vec2 endBorderUp = new Vec2(171, 140);
    public static Vec2 endBorderDown = new Vec2(-183, -350);

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.tickCount % 20 == 0 && !event.player.isCreative()) {
            ServerPlayer serverPlayer = (ServerPlayer) event.player;
            if (serverPlayer.level().dimension().equals(Level.OVERWORLD))
                sameModule(serverPlayer, playerOverWorldLastTimeInBorderPos, overWorldBorderDown, overWorldBorderUp);
            if (serverPlayer.level().dimension().equals(Level.NETHER))
                sameModule(serverPlayer, playerNetherLastTimeInBorderPos, netherBorderDown, netherBorderUp);
            if (serverPlayer.level().dimension().equals(Level.END))
                sameModule(serverPlayer, playerEndLastTimeInBorderPos, endBorderDown, endBorderUp);
        }
    }

    public static void sameModule(ServerPlayer serverPlayer, Map<String, Vec3> map, Vec2 borderDown, Vec2 borderUp) {
        String name = serverPlayer.getName().getString();
        if (serverPlayer.position().x > borderDown.x && serverPlayer.position().x < borderUp.x
                && serverPlayer.position().z > borderDown.y && serverPlayer.position().z < borderUp.y) {
            map.put(name, serverPlayer.position());
        } else {
            Compute.formatMSGSend(serverPlayer, Component.literal("边界").withStyle(ChatFormatting.RED),
                    Component.literal("前面的区域，以后再来探索吧！").withStyle(ChatFormatting.WHITE));
            if (map.containsKey(name)) {
                Vec3 pos = map.get(name);
                serverPlayer.teleportTo(pos.x, pos.y, pos.z);
            } else serverPlayer.respawn();
        }
    }
}
