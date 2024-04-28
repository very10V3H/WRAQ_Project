package com.very.wraq.customized.players.sceptre.very_new;

import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.projectiles.mana.Meteorite;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class VeryNew {
    public static void veryNew(Player player) {
        if (Utils.VeryNew != null && Utils.VeryNew.equals(player)) {
            if (Utils.VeryNewManaDalay > 0) Utils.VeryNewManaDalay --;
            if (Utils.VeryNewManaDalay == 0) {
                Utils.VeryNewManaDalay --;

                if (Utils.VeryNewManaCount == 3) {
/*                    ServerLevel serverLevel = (ServerLevel) player.level();
                    List<Mob> mobList = serverLevel.getEntitiesOfClass(Mob.class,
                            AABB.ofSize(player.position(), 20, 20, 20));
                    List<ServerPlayer> playerList = serverLevel.getServer().getPlayerList().getPlayers();
                    ParticleProvider.DisperseParticle(player.position(), serverLevel, 1, 2, 40, ModParticles.LONG_VOLCANO.get(),1);
                    ParticleProvider.DisperseParticle(player.position(), serverLevel, 1.5, 2, 40, ModParticles.LONG_VOLCANO.get(),1 );

                    mobList.forEach(mob -> {
                        Vec3 PosVec = mob.position().subtract(player.position());
                        if (PosVec.length() <= 6) {
                            ParticleProvider.VerticleCircleParticle(mob.position(), serverLevel, 1, 2, 40, ModParticles.LONG_VOLCANO.get());
                            ParticleProvider.VerticleCircleParticle(mob.position(), serverLevel, 1.5, 2, 40, ModParticles.LONG_VOLCANO.get());
                            List<Mob> mobList1 = serverLevel.getEntitiesOfClass(Mob.class,
                                    AABB.ofSize(mob.position(), 10, 10, 10));
                            mobList1.forEach(mob1 -> {
                                if (mob1.position().subtract(mob.position()).length() <= 2) {
                                    Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob1, 20,false);
                                    Compute.AddSlowDownEffect(mob, 40, 3);
                                    ClientboundLevelParticlesPacket clientboundLevelParticlesPacket =
                                            new ClientboundLevelParticlesPacket(ParticleTypes.EXPLOSION_EMITTER, true,
                                                    mob1.getX(), mob1.getY(), mob1.getZ(),
                                                    0, 0, 0, 0, 0);
                                    playerList.forEach(serverPlayer -> serverPlayer.connection.send(clientboundLevelParticlesPacket));
                                }
                            });
                        }
                    });
                    Compute.SoundToAll(player, SoundEvents.DRAGON_FIREBALL_EXPLODE);*/

                }
                if (Utils.VeryNewManaCount == 5 && Utils.VeryNewManaPos != null) {
/*                    Level level = player.level();
                    Random random = new Random();
                    for (int i = 0; i < 16; i ++) {
                        Meteorite meteorite = new Meteorite(EntityInit.METEORITE.get(), player,level,true);
                        meteorite.moveTo(Utils.VeryNewManaPos.x + random.nextInt(8) - 4,
                                Utils.VeryNewManaPos.y + random.nextInt(8) + 15,
                                Utils.VeryNewManaPos.z + random.nextInt(8) - 4);
                        meteorite.setDeltaMovement(0,-0.75,0);
                        meteorite.setSilent(true);
                        level.addFreshEntity(meteorite);
                    }*/
                }
            }
        }
    }

    public static void VolcanoPowerModifier(Player player) {
        if (Utils.VeryNew != null && Utils.VeryNew.equals(player)) {
            /*if (Utils.VeryNewManaDalay == -1) Utils.VeryNewManaCount ++;*/
            Utils.VeryNewManaCount ++;
            if (Utils.VeryNewManaCount > 5) Utils.VeryNewManaCount = 1;
            if (Utils.VeryNewManaCount == 3 || Utils.VeryNewManaCount == 5) {
                /*Utils.VeryNewManaDalay = 20;*/
                if (Utils.VeryNewManaCount == 5)
                    Utils.VeryNewManaPos = player.pick(15, 0, false).getLocation();
                if (Compute.powerDetectPlayerPickMob(player) != null) Utils.VeryNewManaPos = Compute.powerDetectPlayerPickMob(player).position();
                if (Utils.VeryNewManaCount == 3) Power1(player);
                else Power2(player);
            }
            Compute.EffectLastTimeSend(player, ModItems.VeryNewCurios.get().getDefaultInstance(), 88888, Utils.VeryNewManaCount,true);
        }
    }

    public static void Power1(Player player) {
        ServerLevel serverLevel = (ServerLevel) player.level();
        List<Mob> mobList = serverLevel.getEntitiesOfClass(Mob.class,
                AABB.ofSize(player.position(), 20, 20, 20));
        List<ServerPlayer> playerList = serverLevel.getServer().getPlayerList().getPlayers();
        ParticleProvider.DisperseParticle(player.position(), serverLevel, 1, 2, 40, ModParticles.LONG_VOLCANO.get(),1);
        ParticleProvider.DisperseParticle(player.position(), serverLevel, 1.5, 2, 40, ModParticles.LONG_VOLCANO.get(),1 );

        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(player.position());
            if (PosVec.length() <= 6) {
                ParticleProvider.VerticleCircleParticle(mob.position(), serverLevel, 1, 2, 40, ModParticles.LONG_VOLCANO.get());
                ParticleProvider.VerticleCircleParticle(mob.position(), serverLevel, 1.5, 2, 40, ModParticles.LONG_VOLCANO.get());
                List<Mob> mobList1 = serverLevel.getEntitiesOfClass(Mob.class,
                        AABB.ofSize(mob.position(), 10, 10, 10));
                mobList1.forEach(mob1 -> {
                    if (mob1.position().subtract(mob.position()).length() <= 2) {
                        Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob1, 20,false);
                        Compute.AddSlowDownEffect(mob, 40, 3);
                        ClientboundLevelParticlesPacket clientboundLevelParticlesPacket =
                                new ClientboundLevelParticlesPacket(ParticleTypes.EXPLOSION_EMITTER, true,
                                        mob1.getX(), mob1.getY(), mob1.getZ(),
                                        0, 0, 0, 0, 0);
                        playerList.forEach(serverPlayer -> serverPlayer.connection.send(clientboundLevelParticlesPacket));
                    }
                });
            }
        });
        Compute.SoundToAll(player, SoundEvents.DRAGON_FIREBALL_EXPLODE);
    }

    public static void Power2(Player player) {
        Level level = player.level();
        Random random = new Random();
        for (int i = 0; i < 16; i ++) {
            Meteorite meteorite = new Meteorite(ModEntityType.METEORITE.get(), player,level,true);
            meteorite.moveTo(Utils.VeryNewManaPos.x + random.nextInt(8) - 4,
                    Utils.VeryNewManaPos.y + random.nextInt(8) + 15,
                    Utils.VeryNewManaPos.z + random.nextInt(8) - 4);
            meteorite.setDeltaMovement(0,-0.75,0);
            meteorite.setSilent(true);
            level.addFreshEntity(meteorite);
        }
    }
}
