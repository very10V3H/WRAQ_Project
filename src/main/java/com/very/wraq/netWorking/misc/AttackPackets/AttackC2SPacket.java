package com.very.wraq.netWorking.misc.AttackPackets;

import com.very.wraq.coreAttackModule.AttackEvent;
import com.very.wraq.coreAttackModule.ManaAttackModule;
import com.very.wraq.customized.players.sword.ZuoSI.ZuoSiCurios;
import com.very.wraq.customized.players.sceptre.shangmengli.ShangMengLiSword;
import com.very.wraq.events.fight.HurtEvent;
import com.very.wraq.events.modules.AttackEventModule;
import com.very.wraq.events.modules.HurtEventModule;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.series.instance.Castle.CastleAttackArmor;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
            if (Utils.PlayerAttackTime.containsKey(serverPlayer) && tick - Utils.PlayerAttackTime.get(serverPlayer) < 9) return;
            Utils.PlayerAttackTime.put(serverPlayer, tick);
            CastleAttackArmor.NormalAttack(player); //
            if (ZuoSiCurios.IsPlayer(player)) return;
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

    public static void Module(Player player, double Rate) {
        Level level = player.level();
        Vec3 vec3 = player.pick(4, 0, false).getLocation();
        Vec3 delta = vec3.subtract(player.getEyePosition());
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class, player.getBoundingBox().expandTowards(delta));

/*        Vec3 pos = player.pick(2,0,true).getLocation();
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(pos,5,5,5));*/

        AtomicReference<Mob> NearestMob = new AtomicReference<>();
        AtomicReference<Double> Distance = new AtomicReference<>((double) 20);
        mobList.forEach(mob -> {
            if (mob.distanceTo(player) < Distance.get()) {
                NearestMob.set(mob);
                Distance.set((double) mob.distanceTo(player));
            }
        });

        if (NearestMob.get() != null && player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSword.get())) {
            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SAKURA.get(), player, level,
                    Compute.PlayerAttributes.PlayerManaDamage(player),
                    Compute.PlayerAttributes.PlayerManaPenetration(player),
                    Compute.PlayerAttributes.PlayerManaPenetration0(player), StringUtils.ParticleTypes.Sea);
            ManaAttackModule.BasicAttack(player, NearestMob.get(), Compute.PlayerAttributes.PlayerManaDamage(player),
                    Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerManaPenetration0(player),
                    player.level(), newArrow);
            if (Utils.LiuLiXian != null && Utils.LiuLiXian.equals(player) && Utils.LiuLiXianCore && Utils.LiuLiXianManaAttackDelay == -1) {
                Utils.LiuLiXianManaAttackDelay = 3;
            }
            Compute.SoundToAll(player, SoundEvents.PLAYER_ATTACK_SWEEP);
            return;
        }

        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ShangMengLiSword.get())) {

            if (ShangMengLiSword.ForthNormalAttack(player)) return;

            if (Rate == 0.8) ShangMengLiSword.SwordAir(player,true);
            if (Rate == 1.2) ShangMengLiSword.SwordAir(player,true);
            if (Rate == 1.5) ShangMengLiSword.SwordAir(player,true);

            if (ShangMengLiSword.ShangMengLiAttackCount < 3) ShangMengLiSword.ShangMengLiAttackCount ++;
            Compute.SoundToAll(player, SoundEvents.PLAYER_ATTACK_SWEEP);
            Compute.EffectLastTimeSend(player,ModItems.ShangMengLiSword.get().getDefaultInstance(),8888,ShangMengLiSword.ShangMengLiAttackCount,true);

            return;
        }


        if (NearestMob.get() != null) {
            Item equip = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            AttackEvent.AttackToMonster(NearestMob.get(), player, equip, player.getPersistentData(), Rate);
            HurtEventModule.ForestRune3Judge(player, NearestMob.get(), Compute.PlayerAttributes.PlayerAttackDamage(player));
            HurtEvent.BlazeReflectDamage(NearestMob.get(), player);
            AttackEventModule.SwordSkill3Attack(player.getPersistentData(), player, NearestMob.get());// 破绽观察（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在10次攻击后达到最大值）
            AttackEventModule.SwordSkill12Attack(player.getPersistentData(), player); // 刀光剑影（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以自身为中心范围内造成100%伤害）
            mobList.forEach(mob -> {
                if (mob != NearestMob.get()) AttackEvent.AttackToMonster(mob,player,
                        player.getItemInHand(InteractionHand.MAIN_HAND).getItem(),player.getPersistentData(),0.5);
            });
        }

        Compute.SoundToAll(player, SoundEvents.PLAYER_ATTACK_SWEEP);

    }
}
