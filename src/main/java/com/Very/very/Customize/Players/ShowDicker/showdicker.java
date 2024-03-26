package com.Very.very.Customize.Players.ShowDicker;

import com.Very.very.CoreAttackModule.MyArrow;
import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

public class showdicker {
    public static void ShowDicker(Player player) {
        if (Utils.ShowDicker != null && Utils.ShowDicker.equals(player) && Utils.ShowDickerArrowCount > 0) {
            Utils.ShowDickerArrowCount --;
            ServerPlayer serverPlayer = (ServerPlayer) player;
            MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                    serverPlayer, Compute.PlayerAttributes.PlayerAttackDamage(player) * 0.25, true);
            
            arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 3F, 1.0f);
            arrow.setCritArrow(true);
            serverPlayer.level().addFreshEntity(arrow);
            Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
            ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.SNOWFLAKE);
        }
    }
}
