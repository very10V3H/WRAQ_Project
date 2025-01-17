package fun.wraq.process.func;

import fun.wraq.common.fast.Tick;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.AnimationPackets.AnimationS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class DelayOperationWithAnimation {

    public static class Animation {
        public static String samurai = "samurai";
        public static String stab = "stab";
        public static String bursts = "bursts";
        public static String swordNewSkillBase3_0 = "sword_new_skill_base3_0";
        public static String bowNewSkillBase1_0 = "bow_new_skill_base1_0";
        public static String manaNewSkillBase1_0 = "mana_new_skill_base1_0";
        public static String manaNewSkillBase2_0 = "mana_new_skill_base2_0";
        public static String swordAttack1 = "sword_attack_1";
        public static String swordAttack2 = "sword_attack_2";
        public static String bowAttack = "bow_attack";
        public static String manaAttack = "mana_attack";
        public static String skill = "skill";
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

    public static Map<Player, DelayOperationWithAnimation> playerCurrentOperationMap = new WeakHashMap<>();

    // 有动画的技能会打断当前普攻
    public static void remove(Player player) {
        playerCurrentOperationMap.remove(player);
    }

    public static boolean addToQueue(DelayOperationWithAnimation delayOperationWithAnimation) {
        if (playerCurrentOperationMap.containsKey(delayOperationWithAnimation.trigPlayer)) return false;
        playerCurrentOperationMap.put(delayOperationWithAnimation.trigPlayer, delayOperationWithAnimation);
        animationList.add(delayOperationWithAnimation);
        return true;
    }

    public static List<DelayOperationWithAnimation> animationList = new ArrayList<>();

    public static void serverTick(TickEvent.ServerTickEvent event) {
        MinecraftServer server = event.getServer();
        // 发送动画
        animationList.forEach(animation -> animation.sendAnimation(server));
        animationList.clear();
    }

    public static void playerTick(Player player) {
        if (playerCurrentOperationMap.containsKey(player)) {
            DelayOperationWithAnimation delayOperationWithAnimation = playerCurrentOperationMap.get(player);
            if (delayOperationWithAnimation.trigTick <= Tick.get()) {
                delayOperationWithAnimation.trig();
                playerCurrentOperationMap.remove(player);
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
