package com.Very.very.Customize.Players.Black_Feisa_;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.SoundsPackets.SoundsS2CPacket;
import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.Projectile.Mana.ManaArrow;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.ModEntityType;
import com.Very.very.ValueAndTools.registry.ModItems;
import com.Very.very.ValueAndTools.registry.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class BlackFeisa {
    public static Player Player;
    public static boolean BlackFeisaManaCount = false;
    public static int DoubleManaAttackDelay = 0;
    public static int ActiveExManaDamageTick = 0;
    public static int PassiveExManaDamageCount = 0;
    public static int PassiveExManaDamageTick = 0;
    public static int ActiveCoolDown = 0;
    public static void BlackFeisaTick(Player player) {
        if (Player != null && Player.equals(player)) {
            if (DoubleManaAttackDelay > 0) DoubleManaAttackDelay --;
            if (DoubleManaAttackDelay == 0) {
                DoubleManaAttackDelay --;
                Shoot(Player);
            }
        }
    }
    public static double BlackFeisaFirstManaAttack(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.BlackFeisaSceptre.get())) {
            if (!BlackFeisaManaCount) {
                Compute.PlayerManaAddOrCost(player, (int) ((100 + Compute.PlayerAttributes.PlayerMaxMana(player)) * 0.15));
                return Compute.PlayerAttributes.PlayerManaDamage(player) * 2;
            }
        }
        return 0;
    }
    public static double BlackFeisaDoubleManaAttack(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.BlackFeisaSceptre.get())) {
            if (BlackFeisaManaCount) {
                int TickCount = player.getServer().getTickCount();
                if (PassiveExManaDamageTick < TickCount) PassiveExManaDamageCount = 0;
                PassiveExManaDamageTick = TickCount + 100;
                if (PassiveExManaDamageCount < 5) PassiveExManaDamageCount++;
                Compute.EffectLastTimeSend(player,ModItems.BlackFeisaSceptre.get().getDefaultInstance(),
                        100, PassiveExManaDamageCount);
                return Compute.PlayerAttributes.PlayerManaDamage(player) * 2;
            }
        }
        return 0;
    }
    public static void BlackFeisaActive(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.BlackFeisaSceptre.get())) {
            int TickCount = player.getServer().getTickCount();
            Player = player;
            if (TickCount > ActiveCoolDown) {
                ActiveCoolDown = TickCount + 60;
                List<Mob> monsterList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 20, 20, 20));
                for (Mob mob : monsterList) {
                    if (mob.position().distanceTo(player.position()) < 6) {
                        Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, 50,false);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
                    }
                }
                List<Player> playerList = player.level().getEntitiesOfClass(Player.class,AABB.ofSize(player.position(),20,20,20));
                playerList.forEach(player1 -> {
                    if (player1.distanceTo(player) < 8) {
                        ModNetworking.sendToClient(new SoundsS2CPacket(10),(ServerPlayer) player1);
                    }
                });
                Compute.CoolDownTimeSend(player,ModItems.BlackFeisaSceptre.get().getDefaultInstance(),60);
                ActiveExManaDamageTick = TickCount + 100;
            }
        }
    }
    public static void Shoot(Player player) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), player, level,
                Compute.PlayerAttributes.PlayerManaDamage(player),
                Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerManaPenetration0(player),1);
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);
        
        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        level.addFreshEntity(newArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.WITCH);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.WITCH);
        Compute.SoundToAll(player, ModSounds.Mana.get());
    }
}
