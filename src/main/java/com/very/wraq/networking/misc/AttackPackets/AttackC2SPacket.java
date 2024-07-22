package com.very.wraq.networking.misc.AttackPackets;

import com.very.wraq.core.AttackEvent;
import com.very.wraq.events.fight.HurtEvent;
import com.very.wraq.events.modules.AttackEventModule;
import com.very.wraq.events.modules.HurtEventModule;
import com.very.wraq.series.instance.Castle.CastleAttackArmor;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class AttackC2SPacket {
    private final int count;

    public AttackC2SPacket(int count) {
        this.count = count;
    }

    public AttackC2SPacket(FriendlyByteBuf buf) {
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerPlayer serverPlayer = context.getSender();
            int tick = serverPlayer.getServer().getTickCount();
            if (Utils.PlayerAttackTime.containsKey(serverPlayer) && tick - Utils.PlayerAttackTime.get(serverPlayer) < 9)
                return;
            Utils.PlayerAttackTime.put(serverPlayer, tick);
            CastleAttackArmor.NormalAttack(player); //
            switch (count) {
                case 0 -> {
                    Module(player, 0.8);
                }
                case 1 -> {
                    Module(player, 1.2);
                }
                case 2 -> {
                    Module(player, 1.5);
                }
            }
        });
        return true;
    }

    public static void Module(Player player, double rate) {
        List<Mob> mobList = Compute.getPlayerRayMobList(player, 0.25, 1.25, 4 + PlayerAttributes.attackRangeUp(player));

        AtomicReference<Mob> NearestMob = new AtomicReference<>();
        AtomicReference<Double> Distance = new AtomicReference<>((double) 20);
        mobList.forEach(mob -> {
            if (mob.distanceTo(player) < Distance.get()) {
                NearestMob.set(mob);
                Distance.set((double) mob.distanceTo(player));
            }
        });

        if (NearestMob.get() != null) {
            Item equip = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            AttackEvent.AttackToMonster(NearestMob.get(), player, equip, player.getPersistentData(), rate);
            HurtEventModule.ForestRune3Judge(player, NearestMob.get(), PlayerAttributes.attackDamage(player));
            HurtEvent.BlazeReflectDamage(NearestMob.get(), player);
            AttackEventModule.SwordSkill3Attack(player.getPersistentData(), player, NearestMob.get());// 破绽观察（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在10次攻击后达到最大值）
            AttackEventModule.SwordSkill12Attack(player.getPersistentData(), player); // 刀光剑影（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以自身为中心范围内造成100%伤害）
            mobList.forEach(mob -> {
                if (mob != NearestMob.get()) AttackEvent.AttackToMonster(mob, player,
                        player.getItemInHand(InteractionHand.MAIN_HAND).getItem(), player.getPersistentData(), 0.5);
            });
        }

        Compute.SoundToAll(player, SoundEvents.PLAYER_ATTACK_SWEEP);

    }
}
