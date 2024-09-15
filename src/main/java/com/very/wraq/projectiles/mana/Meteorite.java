package com.very.wraq.projectiles.mana;

import com.very.wraq.common.Compute;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.func.damage.Damage;
import com.very.wraq.process.func.particle.ParticleProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;

public class Meteorite extends AbstractArrow implements GeoEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private Player player;
    private boolean IsVeryNew;

    public Meteorite(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public Meteorite(EntityType<? extends AbstractArrow> entityType, LivingEntity shooter, Level level, boolean IsVeryNew) {
        super(entityType, shooter, level);
        this.player = (Player) shooter;
        this.IsVeryNew = IsVeryNew;
    }

    @Override
    public ItemStack getPickupItem() {
        return MinecartItem.byId(0).getDefaultInstance();
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_) {
        super.onHitBlock(p_36755_);
        if (this.player != null && !this.level().isClientSide) {
            if (this.IsVeryNew) {
                List<ServerPlayer> playerList = player.getServer().getPlayerList().getPlayers();
                ClientboundLevelParticlesPacket clientboundLevelParticlesPacket =
                        new ClientboundLevelParticlesPacket(ParticleTypes.EXPLOSION_EMITTER, true,
                                this.getX(), this.getY(), this.getZ(),
                                0, 0, 0, 0, 0);
                playerList.forEach(serverPlayer -> serverPlayer.connection.send(clientboundLevelParticlesPacket));
                ClientboundSoundPacket clientboundSoundPacket =
                        new ClientboundSoundPacket(Holder.direct(SoundEvents.DRAGON_FIREBALL_EXPLODE), SoundSource.PLAYERS, this.getX(), this.getY(), this.getZ(), 1, 1, 0);
                playerList.forEach(serverPlayer -> serverPlayer.connection.send(clientboundSoundPacket));
                List<Mob> mobList = this.player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(this.position(), 15, 15, 15));
                mobList.forEach(mob -> {
                    Damage.causeManaDamageToMonster_RateApDamage(player, mob, 15, false);
                });
            } else {
                int TickCount = player.getServer().getTickCount();
                List<Mob> mobList = this.player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(this.position(), 15, 15, 15));
                List<Player> playerList = this.player.level().getEntitiesOfClass(Player.class, AABB.ofSize(this.position(), 15, 15, 15));
                List<ServerPlayer> serverPlayerList = player.getServer().getPlayerList().getPlayers();
                ClientboundSoundPacket clientboundSoundPacket =
                        new ClientboundSoundPacket(Holder.direct(SoundEvents.DRAGON_FIREBALL_EXPLODE), SoundSource.PLAYERS, this.getX(), this.getY(), this.getZ(), 1, 1, 0);
                serverPlayerList.forEach(serverPlayer -> serverPlayer.connection.send(clientboundSoundPacket));

                switch (random.nextInt(4)) {
                    case 0 -> {
                        mobList.forEach(mob -> {
                            if (mob.distanceTo(this) < 5) {
/*                                LastDamage lastDamage = new LastDamage(player, mob, false, 80, 20, 0.3);
                                Utils.PlayerLastDamageToMonster.add(lastDamage);*/
                            }
                        });
                    }
                    case 1 -> {
                        mobList.forEach(mob -> {
                            if (mob.distanceTo(this) < 5) {
                                Compute.addSlowDownEffect(mob, 80, 2);
                            }
                        });
                    }
                    case 2 -> {
                        mobList.forEach(mob -> {
                            if (mob.distanceTo(this) < 5) {
/*                                LastDamage lastDamage = new LastDamage(player, mob, false, 80, 20, 0.35);
                                Utils.PlayerLastDamageToMonster.add(lastDamage);*/
                                mob.setSecondsOnFire(4);
                            }
                        });
                    }
                    case 3 -> {
                        mobList.forEach(mob -> {
                            if (mob.distanceTo(this) < 5) {
                                Compute.addSlowDownEffect(mob, 80, 4);
                            }
                        });
                    }
                } // 虚弱

                switch (random.nextInt(4)) {
                    case 0 -> {
                        playerList.forEach(player1 -> {
                            if (player1.distanceTo(this) < 5) {
                                Compute.playerHeal(player1, (player1.getMaxHealth() - player1.getHealth() * 0.1));
                                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                            }
                        });
                    }
                    case 1 -> {
                        playerList.forEach(player1 -> {
                            if (player1.distanceTo(this) < 5) {
                                Utils.MeteoriteDefenceMap.put(player1, (int) (PlayerAttributes.manaDamage(player) * 0.05));
                                Utils.MeteoriteDefenceTimeMap.put(player1, TickCount + 80);
                                Compute.sendEffectLastTime(player1, ModItems.SoulSceptre.get().getDefaultInstance(), 80);
                            }
                        });
                    }
                    case 2 -> {
                        playerList.forEach(player1 -> {
                            if (player1.distanceTo(this) < 5) {
                                Utils.MeteoriteAttackTimeMap.put(player1, TickCount + 80);
                                Compute.sendEffectLastTime(player1, ModItems.SoulSceptre.get().getDefaultInstance(), 80);
                            }
                        });
                    }
                    case 3 -> {
                        playerList.forEach(player1 -> {
                            if (player1.distanceTo(this) < 5) {
                                Utils.MeteoritePenetrationTimeMap.put(player1, TickCount + 80);
                                Compute.sendEffectLastTime(player1, ModItems.SoulSceptre.get().getDefaultInstance(), 80);
                            }
                        });
                    }
                } // 增幅
            }
        }
        this.remove(RemovalReason.KILLED);
    }

    @Override
    protected boolean canHitEntity(Entity p_36743_) {
        return super.canHitEntity(p_36743_);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.player != null && !level().isClientSide) {
            int TickCount = this.player.getServer().getTickCount();
            if (this.IsVeryNew) {
                List<ServerPlayer> playerList = player.getServer().getPlayerList().getPlayers();
                ClientboundLevelParticlesPacket clientboundLevelParticlesPacket =
                        new ClientboundLevelParticlesPacket(ParticleTypes.EXPLOSION_EMITTER, true,
                                this.getX(), this.getY(), this.getZ(),
                                0, 0, 0, 0, 0);
                playerList.forEach(serverPlayer -> serverPlayer.connection.send(clientboundLevelParticlesPacket));
                ClientboundSoundPacket clientboundSoundPacket =
                        new ClientboundSoundPacket(Holder.direct(SoundEvents.DRAGON_FIREBALL_EXPLODE), SoundSource.PLAYERS, this.getX(), this.getY(), this.getZ(), 1, 1, 0);
                playerList.forEach(serverPlayer -> serverPlayer.connection.send(clientboundSoundPacket));
                List<Mob> mobList = this.player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(this.position(), 15, 15, 15));
                mobList.forEach(mob -> {
                    Damage.causeManaDamageToMonster_RateApDamage(player, mob, 5, false);
                });
            } else {
                List<Mob> mobList = this.player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(this.position(), 15, 15, 15));
                List<Player> playerList = this.player.level().getEntitiesOfClass(Player.class, AABB.ofSize(this.position(), 15, 15, 15));
                List<ServerPlayer> serverPlayerList = player.getServer().getPlayerList().getPlayers();
                ClientboundSoundPacket clientboundSoundPacket =
                        new ClientboundSoundPacket(Holder.direct(SoundEvents.DRAGON_FIREBALL_EXPLODE), SoundSource.PLAYERS, this.getX(), this.getY(), this.getZ(), 1, 1, 0);
                serverPlayerList.forEach(serverPlayer -> serverPlayer.connection.send(clientboundSoundPacket));
                switch (random.nextInt(4)) {
                    case 0 -> {
                        mobList.forEach(mob -> {
                            if (mob.distanceTo(this) < 5) {
/*                                LastDamage lastDamage = new LastDamage(player, mob, false, 80, 20, 0.3);
                                Utils.PlayerLastDamageToMonster.add(lastDamage);*/
                            }
                        });
                    }
                    case 1 -> {
                        mobList.forEach(mob -> {
                            if (mob.distanceTo(this) < 5) {
                                Compute.addSlowDownEffect(mob, 80, 2);
                            }
                        });
                    }
                    case 2 -> {
                        mobList.forEach(mob -> {
                            if (mob.distanceTo(this) < 5) {
/*                                LastDamage lastDamage = new LastDamage(player, mob, false, 80, 20, 0.35);
                                Utils.PlayerLastDamageToMonster.add(lastDamage);*/
                                mob.setSecondsOnFire(4);
                            }
                        });
                    }
                    case 3 -> {
                        mobList.forEach(mob -> {
                            if (mob.distanceTo(this) < 5) {
                                Compute.addSlowDownEffect(mob, 80, 4);
                            }
                        });
                    }
                } // 虚弱

                switch (random.nextInt(4)) {
                    case 0 -> {
                        playerList.forEach(player1 -> {
                            if (player1.distanceTo(this) < 5) {
                                Compute.playerHeal(player1, (player1.getMaxHealth() - player1.getHealth() * 0.1));
                                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                            }
                        });
                    }
                    case 1 -> {
                        playerList.forEach(player1 -> {
                            if (player1.distanceTo(this) < 5) {
                                Utils.MeteoriteDefenceMap.put(player1, (int) (PlayerAttributes.manaDamage(player) * 0.05));
                                Utils.MeteoriteDefenceTimeMap.put(player1, TickCount + 80);
                                Compute.sendEffectLastTime(player1, ModItems.SoulSceptre.get().getDefaultInstance(), 80);
                            }
                        });
                    }
                    case 2 -> {
                        playerList.forEach(player1 -> {
                            if (player1.distanceTo(this) < 5) {
                                Utils.MeteoriteAttackTimeMap.put(player1, TickCount + 80);
                                Compute.sendEffectLastTime(player1, ModItems.SoulSceptre.get().getDefaultInstance(), 80);
                            }
                        });
                    }
                    case 3 -> {
                        playerList.forEach(player1 -> {
                            if (player1.distanceTo(this) < 5) {
                                Utils.MeteoritePenetrationTimeMap.put(player1, TickCount + 80);
                                Compute.sendEffectLastTime(player1, ModItems.SoulSceptre.get().getDefaultInstance(), 80);
                            }
                        });
                    }
                } // 增幅
            }
        }
        this.remove(RemovalReason.KILLED);
    }

    @Override
    public void tick() {
        super.tick();
        this.level().addParticle(ParticleTypes.ASH, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        this.level().addParticle(ParticleTypes.LAVA, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.new", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


}
