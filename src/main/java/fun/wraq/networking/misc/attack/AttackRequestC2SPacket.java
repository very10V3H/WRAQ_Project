package fun.wraq.networking.misc.attack;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.impl.AxeWeapon;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.core.AttackEvent;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.skill.skillv2.sword.SwordNewSkillFinal0;
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
            boolean firstAttack = countExpiredTickMap.getOrDefault(name, 0) < Tick.get();
            boolean isAxe = player.getMainHandItem().getItem() instanceof AxeWeapon;
            double rate = firstAttack ? 0.8 : 1.2;
            String animation;
            int trigTickDelta;
            int endTickDelta;
            if (isAxe) {
                // 斧类武器使用独立的劈砍动画，前后摇更长，攻击节奏更慢
                animation = firstAttack
                        ? DelayOperationWithAnimation.Animation.axeAttack1
                        : DelayOperationWithAnimation.Animation.axeAttack2;
                trigTickDelta = 10;
                endTickDelta = 14;
            } else {
                animation = firstAttack
                        ? DelayOperationWithAnimation.Animation.swordAttack1
                        : DelayOperationWithAnimation.Animation.swordAttack2;
                trigTickDelta = 8;
                endTickDelta = 10;
            }
            boolean success = DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                    animation, trigTickDelta, endTickDelta, player, 1, 0
            ) {
                @Override
                public void trig() {
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
