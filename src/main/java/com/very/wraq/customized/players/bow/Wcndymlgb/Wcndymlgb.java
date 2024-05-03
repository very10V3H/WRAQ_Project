package com.very.wraq.customized.players.bow.Wcndymlgb;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

public class Wcndymlgb {
    public static int WcBowAttackTick = 0;
    public static void WcBowTick(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WcBow.get())) {
            if (WcBowAttackTick > 0) {
                WcBowAttackTick --;
                if (WcBowAttackTick % 2 == 0 && WcBowAttackTick != 0) {
                    ServerPlayer serverPlayer = (ServerPlayer) player;
                    double damage = PlayerAttributes.PlayerAttackDamage(serverPlayer);
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                            serverPlayer, damage * 0.5, true,true,ParticleTypes.COMPOSTER);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 1.5f, 1.0f);
                    arrow.setCritArrow(true);
                    arrow.setNoGravity(true);
                    WraqBow.adjustArrow(arrow, serverPlayer);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.COMPOSTER);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);
                }
            }
        }
    }
    public static void WcBowSwitchMode(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WcBow.get())) {
            Utils.WcBowStatus = !Utils.WcBowStatus;
        }
    }
    public static void WcBow(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WcBow.get())) {
            if (Utils.WcBowStatus) {
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
            }
        }
    }
}
