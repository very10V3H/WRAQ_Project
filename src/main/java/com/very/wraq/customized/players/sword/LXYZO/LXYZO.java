package com.very.wraq.customized.players.sword.LXYZO;

import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class LXYZO {
    public static void Active(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;

        if (Utils.LengXuePlayer != null && Utils.LengXuePlayer.equals(player)
                && Utils.LengXueMob != null && Utils.LengXueMobCount >= 0) {
            if (Utils.LengXueMobCount % 5 == 0) {
                Compute.Damage.AttackDamageToMonster_RateAdDamage(player,Utils.LengXueMob,5);
            }
            Utils.LengXueMobCount --;
            if (Utils.LengXueMobCount == 0) Utils.LengXueMob = null;
        }

        if (player.tickCount % 20 == 0 && Utils.LengXuePlayer != null && Utils.LengXuePlayer.equals(player)) {
            if (Utils.LengXueCounts != -1 && Utils.LengXueVec3 != null) {
                List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,
                        AABB.ofSize(Utils.LengXueVec3,15,15,15));
                mobList.forEach(mob -> {
                    if (mob.position().distanceTo(Utils.LengXueVec3) < 6) {
                        Compute.Damage.AttackDamageToMonster_AdDamage(player,mob,
                                PlayerAttributes.PlayerDefence(player) * 2.24 * (1 + PlayerAttributes.PlayerCritDamage(player)));
                        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,player.level());
                        lightningBolt.moveTo(mob.position());
                        lightningBolt.setDamage(0);
                        lightningBolt.setCause(serverPlayer);
                        lightningBolt.setVisualOnly(true);
                        player.level().addFreshEntity(lightningBolt);
                    }
                });

                Compute.BallParticle(serverPlayer,Utils.LengXueVec3,6, ModParticles.LONG_LIGHTNINGISLAND.get(), 200);
                if (Utils.LengXueCounts == 0) {
                    List<ServerPlayer> serverPlayerList = serverPlayer.getServer().getPlayerList().getPlayers();
                    serverPlayerList.forEach(serverPlayer1 -> {
                        ClientboundSoundPacket clientboundSoundPacket =
                                new ClientboundSoundPacket(Holder.direct(SoundEvents.GENERIC_EXPLODE),
                                        SoundSource.PLAYERS,
                                        Utils.LengXueVec3.x,Utils.LengXueVec3.y,Utils.LengXueVec3.z,1,1,0);
                        serverPlayer1.connection.send(clientboundSoundPacket);
                    });
                    mobList.forEach(mob -> {
                        if (mob.position().distanceTo(Utils.LengXueVec3) < 6) {
                            Compute.Damage.AttackDamageToMonster_AdDamage(player,mob,
                                    PlayerAttributes.PlayerAttackDamage(player) * 2.24 * (1 + PlayerAttributes.PlayerCritDamage(player)));
                            ClientboundLevelParticlesPacket clientboundLevelParticlesPacket =
                                    new ClientboundLevelParticlesPacket(ParticleTypes.EXPLOSION_EMITTER, true,
                                            mob.getX(), mob.getY(), mob.getZ(),
                                            0, 0, 0, 0, 0);
                            serverPlayerList.forEach(serverPlayer1 -> serverPlayer1.connection.send(clientboundLevelParticlesPacket));
                        }
                    });
                }
                Utils.LengXueCounts --;
            }
        }
    }
}
