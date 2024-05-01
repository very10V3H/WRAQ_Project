package com.very.wraq.customized.players.bow.ShowDicker;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
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
            MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(),
                    serverPlayer, PlayerAttributes.PlayerAttackDamage(player) * 0.25, true);
            
            arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 3F, 1.0f);
            arrow.setCritArrow(true);
            serverPlayer.level().addFreshEntity(arrow);
            Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
            ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.SNOWFLAKE);
        }
    }
}
