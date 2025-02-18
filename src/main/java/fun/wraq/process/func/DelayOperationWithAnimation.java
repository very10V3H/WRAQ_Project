package fun.wraq.process.func;

import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Tick;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.AnimationPackets.AnimationS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;

import java.util.*;

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
        public static String rolling = "rolling";
    }

    private final String animationId;
    private final int trigTick;
    private final int endTick;
    private final Player trigPlayer;
    private boolean trigFlag = true;
    public DelayOperationWithAnimation(String animationId, int trigTick, int endTick, Player trigPlayer) {
        this.animationId = animationId;
        this.trigTick = trigTick;
        this.endTick = endTick;
        this.trigPlayer = trigPlayer;
    }

    public DelayOperationWithAnimation(String animationId, int trigTick, Player trigPlayer) {
        this(animationId, trigTick, trigTick, trigPlayer);
    }

    public abstract void trig();

    public void sendAnimation(MinecraftServer server) {
        server.getPlayerList().getPlayers().stream()
                .filter(p -> p.level().equals(trigPlayer.level()) && p.distanceTo(trigPlayer) <= 48)
                .forEach(serverPlayer -> {
                    ModNetworking.sendToClient(
                            new AnimationS2CPacket(trigPlayer.getId(), animationId, trigTick - Tick.get())
                            , serverPlayer);
        });
    }

    public static Map<String, DelayOperationWithAnimation> playerCurrentOperationMap = new HashMap<>();

    public static Set<String> normalAttackAnimationIds = Set.of(Animation.swordAttack1, Animation.swordAttack2,
            Animation.bowAttack, Animation.manaAttack);

    // 技能释放前打断普攻
    public static void beforeReleaseSkill(Player player) {
        DelayOperationWithAnimation delayOperationWithAnimation
                = playerCurrentOperationMap.getOrDefault(Name.get(player), null);
        if (delayOperationWithAnimation != null
                && normalAttackAnimationIds.contains(delayOperationWithAnimation.animationId)) {
            playerCurrentOperationMap.remove(Name.get(player));
        }
    }

    public static boolean isNormalAttacking(Player player) {
        return playerCurrentOperationMap.containsKey(Name.get(player))
                && normalAttackAnimationIds.contains(playerCurrentOperationMap.get(Name.get(player)).animationId);
    }

    // 有动画的技能会打断当前普攻
    public static void remove(Player player) {
        playerCurrentOperationMap.remove(Name.get(player));
    }

    public static boolean addToQueue(DelayOperationWithAnimation delayOperationWithAnimation) {
        if (playerCurrentOperationMap.containsKey(Name.get(delayOperationWithAnimation.trigPlayer))) return false;
        playerCurrentOperationMap.put(Name.get(delayOperationWithAnimation.trigPlayer), delayOperationWithAnimation);
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
        if (playerCurrentOperationMap.containsKey(Name.get(player))) {
            DelayOperationWithAnimation delayOperationWithAnimation = playerCurrentOperationMap.get(Name.get(player));
            if (delayOperationWithAnimation.trigTick <= Tick.get() && delayOperationWithAnimation.trigFlag) {
                delayOperationWithAnimation.trig();
                delayOperationWithAnimation.trigFlag = false;
            }
            if (delayOperationWithAnimation.endTick <= Tick.get() && !delayOperationWithAnimation.trigFlag) {
                playerCurrentOperationMap.remove(Name.get(player));
            }
        }
    }

    public static int clientPlayerAnimationPlayingLeftTick = 0;

    public static void clientTick(TickEvent.ClientTickEvent event) {
        clientPlayerAnimationPlayingLeftTick = Math.max(0, clientPlayerAnimationPlayingLeftTick - 1);
    }
}
