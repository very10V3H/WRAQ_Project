package fun.wraq.networking.misc.attack;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.core.AttackEvent;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.skill.skillv2.sword.SwordNewSkillFinal0;
import fun.wraq.series.instance.series.castle.CastleAttackArmor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AttackRequestC2SPacket {

    public AttackRequestC2SPacket() {}

    public AttackRequestC2SPacket(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    public static Map<String, Integer> countExpiredTickMap = new HashMap<>();

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            String name = player.getName().getString();
            double rate;
            String animation;
            if (countExpiredTickMap.getOrDefault(name, 0) < Tick.get()) {
                rate = 0.8;
                animation = DelayOperationWithAnimation.Animation.swordAttack1;
            } else {
                rate = 1.2;
                animation = DelayOperationWithAnimation.Animation.swordAttack2;
            }
            boolean success = DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                    animation, 8, 10, player, 1
            ) {
                @Override
                public void trig() {
                    CastleAttackArmor.NormalAttack(player);
                    Damage.onPlayerReleaseNormalAttack(player);
                    AttackEvent.module(player, rate);
                    MySound.soundToNearPlayer(player,
                            rate == 0.8 ? SoundEvents.PLAYER_ATTACK_WEAK : SoundEvents.PLAYER_ATTACK_STRONG);
                    SwordNewSkillFinal0.onPlayerNormalAttack(player);
                }
            });
            if (success) {
                if (rate == 0.8) {
                    countExpiredTickMap.put(name,
                            (int) (Tick.get() + 15 / (1 + PlayerAttributes.getAttackSpeedEnhanceRate(player))));
                } else {
                    countExpiredTickMap.put(name, 0);
                }
            }
        });
        return true;
    }
}
