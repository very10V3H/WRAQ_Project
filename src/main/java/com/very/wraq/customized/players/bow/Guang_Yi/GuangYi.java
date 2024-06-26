package com.very.wraq.customized.players.bow.Guang_Yi;

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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

public class GuangYi {
    public static void GuangYiSwitchMode(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.GuangYiBow.get())
                && !Utils.GuangYiIsInMode) {
            int TickCount = player.getServer().getTickCount();
            Utils.GuangYiModeStartTick = TickCount;
            Utils.GuangYiIsInMode = true;
        }
        else {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.GuangYiBow.get()) && Utils.GuangYiIsInMode)
                Utils.GuangYiIsInMode = false;
        }
    }

    public static void GuangYiSecondArrow(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.GuangYiBow.get())) {
            if (Utils.GuangYiSecondArrowDelay > 0) Utils.GuangYiSecondArrowDelay--;
            if (Utils.GuangYiSecondArrowDelay == 0) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(),
                        serverPlayer, PlayerAttributes.PlayerAttackDamage(player), true);
                
                arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5F, 1.0f);
                arrow.setCritArrow(true);
                WraqBow.adjustArrow(arrow, serverPlayer);
                serverPlayer.level().addFreshEntity(arrow);
                Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WAX_OFF);
                ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.WAX_OFF);
                Utils.GuangYiSecondArrowDelay--;
            }
        }
    }

    public static void GuangYiMode(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.GuangYiBow.get())) {
            int TickCount = player.getServer().getTickCount();
            if (Utils.GuangYiIsInMode) {
                if ((TickCount - Utils.GuangYiModeStartTick) % 20 == 0) {
                    Compute.EffectLastTimeSend(player, ModItems.GuangYiBow.get().getDefaultInstance(), 20, Math.min(5, (TickCount - Utils.GuangYiModeStartTick) / 20));
                }
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 5, false, false, false));
            }
        }
    }

}
