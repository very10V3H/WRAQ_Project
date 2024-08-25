package com.very.wraq.common;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.AnimationPackets.AnimationS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;

import java.util.*;

public abstract class DelayOperationWithAnimation {

    public static class Animation {
        public static String samurai = "samurai";
    }

    private final String animationId;
    private final int trigTick;
    private final Player trigPlayer;
    public DelayOperationWithAnimation(String animationId, int trigTick, Player trigPlayer) {
        this.animationId = animationId;
        this.trigTick = trigTick;
        this.trigPlayer = trigPlayer;
    }

    public abstract void trig();

    public int getTrigTick() {
        return trigTick;
    }

    public Player getTrigPlayer() {
        return trigPlayer;
    }

    public void sendAnimation(MinecraftServer server) {
        server.getPlayerList().getPlayers().stream()
                .filter(p -> p.level().equals(trigPlayer.level()) && p.distanceTo(trigPlayer) <= 48)
                .forEach(serverPlayer -> {
                    ModNetworking.sendToClient(
                            new AnimationS2CPacket(trigPlayer.getId(), animationId, trigTick - Tick.get())
                            , serverPlayer);
        });
    }

    public static Queue<DelayOperationWithAnimation> queue = new ArrayDeque<>();
    public static HashSet<Player> isPlayerAnimationPlayerSet = new HashSet<>();
    public static boolean addToQueue(DelayOperationWithAnimation delayOperationWithAnimation) {
        if (isPlayerAnimationPlayerSet.contains(delayOperationWithAnimation.trigPlayer)) return false;
        queue.add(delayOperationWithAnimation);
        animationList.add(delayOperationWithAnimation);
        isPlayerAnimationPlayerSet.add(delayOperationWithAnimation.trigPlayer);
        return true;
    }

    public static List<DelayOperationWithAnimation> animationList = new ArrayList<>();

    public static void serverTick(TickEvent.ServerTickEvent event) {
        MinecraftServer server = event.getServer();
        // 发送动画
        animationList.forEach(animation -> animation.sendAnimation(server));
        animationList.clear();

        // 触发效果
        if (queue.peek() != null && queue.peek().trigTick <= Tick.get()) {
            while (!queue.isEmpty() && queue.peek().trigTick <= Tick.get()) {
                DelayOperationWithAnimation delayOperationWithAnimation = queue.remove();
                isPlayerAnimationPlayerSet.remove(delayOperationWithAnimation.trigPlayer);
                delayOperationWithAnimation.trig();
            }
        }
    }

    public static int clientPlayerAnimationPlayingLeftTick = 0;

    public static boolean clientPlayerIsPlayingAnimation() {
        return clientPlayerAnimationPlayingLeftTick > 0;
    }

    public static void clientTick(TickEvent.ClientTickEvent event) {
        clientPlayerAnimationPlayingLeftTick = Math.max(0, clientPlayerAnimationPlayingLeftTick - 1);
    }
}
