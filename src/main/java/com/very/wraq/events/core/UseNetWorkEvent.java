package com.very.wraq.events.core;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.ParticlePackets.SnowSwordParticleC2SPacket;
import com.very.wraq.networking.misc.USE.MoveToC2SPacket;
import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class UseNetWorkEvent {
    @SubscribeEvent
    public static void AllTickEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        if (level.isClientSide && event.player.equals(Minecraft.getInstance().player)) {
            if (ClientUtils.UseOfSnowSword) {
                ClientUtils.UseOfSnowSword = false;
                HitResult hitResult = player.pick(10, 0, true); //第一个参数为最远距离
                double X = hitResult.getLocation().x;
                double Y = hitResult.getLocation().y;
                double Z = hitResult.getLocation().z;
                player.playSound(SoundEvents.FOX_TELEPORT);
                ModNetworking.sendToServer(new SnowSwordParticleC2SPacket(X, Y, Z, player.getX(), player.getY(), player.getZ()));
                ModNetworking.sendToServer(new MoveToC2SPacket(player.getDeltaMovement().x, player.getDeltaMovement().y, player.getDeltaMovement().z));
            }
            if (ClientUtils.UseOfSnowSwordParticle) {
                ClientUtils.UseOfSnowSwordParticle = false;
                double X = ClientUtils.X;
                double Y = ClientUtils.Y;
                double Z = ClientUtils.Z;
                double X1 = ClientUtils.X1;
                double Y1 = ClientUtils.Y1;
                double Z1 = ClientUtils.Z1;
                level.addParticle(ParticleTypes.FLASH, X1, Y1, Z1, 0, 0, 0);
                level.addParticle(ParticleTypes.FLASH, X, Y, Z, 0, 0, 0);
                level.addParticle(ParticleTypes.FLASH, X + 2, Y, Z, 0, 0, 0);
                level.addParticle(ParticleTypes.FLASH, X - 2, Y, Z, 0, 0, 0);
                level.addParticle(ParticleTypes.FLASH, X, Y, Z + 2, 0, 0, 0);
                level.addParticle(ParticleTypes.FLASH, X, Y, Z - 2, 0, 0, 0);
                level.addParticle(ParticleTypes.FLASH, X, Y + 2, Z, 0, 0, 0);
                level.addParticle(ParticleTypes.FLASH, X, Y - 2, Z, 0, 0, 0);
                level.addParticle(ParticleTypes.FIREWORK, X, Y, Z, 0, 0, 0);
                for (int i = 0; i <= 100; i++) {
                    double x = 2.0 * Math.cos(i * Math.PI / 10);
                    double z = 2.0 * Math.sin(i * Math.PI / 10);
                    double y = 3.0 * (i / 100.0);
                    level.addParticle(ParticleTypes.FIREWORK, X + x, Y + y, Z + z, 0, 0, 0);
                }
                for (int i = 0; i <= 100; i++) {
                    double x = 2.0 * Math.cos(i * Math.PI / 10);
                    double z = 2.0 * Math.sin(i * Math.PI / 10);
                    double y = 3.0 * (i / 100.0);
                    level.addParticle(ParticleTypes.FIREWORK, X1 + x, Y1 + y, Z1 + z, 0, 0, 0);
                }
                for (int i = 0; i <= 100; i++) {
                    double x = 3.0 * Math.cos(i * Math.PI / 10);
                    double z = 3.0 * Math.sin(i * Math.PI / 10);
                    level.addParticle(ParticleTypes.SNOWFLAKE, X + x, Y + 5, Z + z, 0, 0, 0);
                }
            }
            if (ClientUtils.UseOfLakeSword) {
                ClientUtils.UseOfLakeSword = false;
                if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LakeSword3.get()))
                    player.playSound(SoundEvents.AMBIENT_UNDERWATER_EXIT);
                player.setDeltaMovement(2 * player.getViewVector(5).x, 2 * player.getViewVector(5).y, 2 * player.getViewVector(5).z);
            }
            if (ClientUtils.UseOfBow) {
                ClientUtils.UseOfBow = false;
                player.playSound(SoundEvents.ARROW_SHOOT);
            }
            if (ClientUtils.UseOfSkyBow) {
                ClientUtils.UseOfSkyBow = false;
                level.addParticle(ParticleTypes.FLASH, player.getX() + 2.0 * player.getViewVector(1).x, player.getY() + 2.0 * player.getViewVector(1).y, player.getZ() + 2.0 * player.getViewVector(1).z, 0, 0, 0);
            }
            if (ClientUtils.QuartzSabreParticlePos != -1) {
                if (ClientUtils.QuartzSabreParticlePos == 0) {
                    level.addParticle(ParticleTypes.FIREWORK, ClientUtils.QuartzSabreParticleIndexX, ClientUtils.QuartzSabreParticleIndexY + 1, ClientUtils.QuartzSabreParticleIndexZ - 1, 0, 0, 0);
                }
                if (ClientUtils.QuartzSabreParticlePos == 1) {
                    level.addParticle(ParticleTypes.FIREWORK, ClientUtils.QuartzSabreParticleIndexX + 1, ClientUtils.QuartzSabreParticleIndexY + 1, ClientUtils.QuartzSabreParticleIndexZ, 0, 0, 0);
                }
                if (ClientUtils.QuartzSabreParticlePos == 2) {
                    level.addParticle(ParticleTypes.FIREWORK, ClientUtils.QuartzSabreParticleIndexX, ClientUtils.QuartzSabreParticleIndexY + 1, ClientUtils.QuartzSabreParticleIndexZ + 1, 0, 0, 0);
                }
                if (ClientUtils.QuartzSabreParticlePos == 3) {
                    level.addParticle(ParticleTypes.FIREWORK, ClientUtils.QuartzSabreParticleIndexX - 1, ClientUtils.QuartzSabreParticleIndexY + 1, ClientUtils.QuartzSabreParticleIndexZ, 0, 0, 0);
                }
                ClientUtils.QuartzSabreParticlePos = -1;
            }
            if (ClientUtils.CritHitFlag) {
                ClientUtils.CritHitFlag = false;
                double X = ClientUtils.CritX;
                double Y = ClientUtils.CritY;
                double Z = ClientUtils.CritZ;
                level.addParticle(ParticleTypes.CRIT, X + 0.5, Y + 1.35, Z, 1, 0.1, 0);
                level.addParticle(ParticleTypes.CRIT, X - 0.5, Y + 1.35, Z, -1, 0.1, 0);
                level.addParticle(ParticleTypes.CRIT, X, Y + 1.35, Z + 0.5, 0, 0.1, 1);
                level.addParticle(ParticleTypes.CRIT, X, Y + 1.35, Z - 0.5, 0, 0.1, -1);
                level.addParticle(ParticleTypes.CRIT, X + 0.3, Y + 1.35, Z + 0.3, 1, 0.1, 1);
                level.addParticle(ParticleTypes.CRIT, X + 0.3, Y + 1.35, Z - 0.3, 1, 0.1, -1);
                level.addParticle(ParticleTypes.CRIT, X - 0.3, Y + 1.35, Z + 0.3, -1, 0.1, 1);
                level.addParticle(ParticleTypes.CRIT, X - 0.3, Y + 1.35, Z - 0.3, -1, 0.1, -1);
            }
        }
    }
}
