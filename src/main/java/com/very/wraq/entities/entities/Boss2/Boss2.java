package com.very.wraq.entities.entities.Boss2;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.AnimationPackets.Boss2AnimationStartS2CPacket;
import com.very.wraq.networking.misc.ParticlePackets.UtilsParticleS2CPacket;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Boss2 extends WitherSkeleton implements GeoEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private Level level;
    private int random;
    public static int MaxHealth = 100000;
    private static final EntityDataAccessor<Integer> DATA_ID_INV = SynchedEntityData.defineId(Boss2.class, EntityDataSerializers.INT);
    private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = (Tmp) -> {
        return Tmp.getMobType() != MobType.UNDEAD && Tmp.attackable();
    };

    private static int AttackMode = -1;
    private static int AttackTick = -1;
    private static int StopTick = -1;

    public Boss2(EntityType<? extends WitherSkeleton> p_33325_, Level level) {
        super(p_33325_, level);
        this.isSunSensitive();
        this.xpReward = 0;
        this.level = level;
    }

    protected boolean isSunSensitive() {
        return false;
    }

    public void aiStep() {
        super.aiStep();
    }

    public static AttributeSupplier.Builder HBossAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 100000.0D).
                add(Attributes.MOVEMENT_SPEED, 0.3D).
                add(Attributes.ATTACK_DAMAGE, 120.0D).
                add(Attributes.ARMOR_TOUGHNESS, 10.0D).
                add(Attributes.KNOCKBACK_RESISTANCE, 10.0D).
                add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    public static AttributeSupplier setAttributes() {
        return PathfinderMob.createMobAttributes().
                add(Attributes.MAX_HEALTH, MaxHealth).
                add(Attributes.ARMOR, 1000.0D).
                add(Attributes.ATTACK_DAMAGE, 0).
                add(Attributes.KNOCKBACK_RESISTANCE, 10.0D).
                add(Attributes.MOVEMENT_SPEED, 0.3)
                .build();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 10.0f));
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(ZombifiedPiglin.class));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public boolean doHurtTarget(@NotNull Entity entityIn) {
        if (!super.doHurtTarget(entityIn)) {
            return false;
        } else {
            if (entityIn instanceof LivingEntity) {
                ((LivingEntity) entityIn).addEffect(new MobEffectInstance(MobEffects.WITHER, 20, 0, true, true));
                ((LivingEntity) entityIn).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 3, true, true));
                ((LivingEntity) entityIn).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 2, true, true));
            }
            return true;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        Random ran = new Random();
        int co = ran.nextInt(3);
        if (co == 0) {
            return SoundEvents.ENDER_DRAGON_GROWL;
        } else if (co == 1) return SoundEvents.ENDER_DRAGON_DEATH;
        return SoundEvents.ENDER_DRAGON_HURT;
    }

    protected SoundEvent getAmbientSound() {
        Random ran = new Random();
        int co = ran.nextInt(3);
        if (co == 0) {
            return SoundEvents.WITHER_SKELETON_AMBIENT;
        } else if (co == 1) return SoundEvents.WITHER_SKELETON_AMBIENT;
        return SoundEvents.WITHER_SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return SoundEvents.ENDER_DRAGON_HURT;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose p_21131_, @NotNull EntityDimensions p_21132_) {
        return 3.0f;
    }

    public void setInvulnerableTicks(int p_31511_) {
        this.entityData.set(DATA_ID_INV, p_31511_);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag p_33344_) {
        super.readAdditionalSaveData(p_33344_);
        this.setInvulnerableTicks(p_33344_.getInt("Invul"));
    }

    @Override
    public void setCustomName(@Nullable Component p_20053_) {
        super.setCustomName(p_20053_);
    }

    protected boolean SummonFlag = true;

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.isSunSensitive()) {
            this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 1, true, true));
        }
        if (this.tickCount % 200 == 140) {
            Random r = new Random();
            random = r.nextInt(4);
            List<ServerPlayer> playerList = this.getServer().getPlayerList().getPlayers();
            Iterator<ServerPlayer> iterator = playerList.iterator();
            while (iterator.hasNext()) {
                ModNetworking.sendToClient(new UtilsParticleS2CPacket(random, true), iterator.next());
            }
        }
        if (StopTick > -1) {
            StopTick--;
        } else {
            AttackTick = -1;
            AttackMode = -1;
            this.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
                ModNetworking.sendToClient(new Boss2AnimationStartS2CPacket(AttackMode), serverPlayer);
            });
        }
        if (this.tickCount % 100 == 0 && AttackMode == -1 && StopTick == -1) {
            AttackMode = new Random().nextInt(4);
            switch (AttackMode) {
                case 0 -> {
                    AttackTick = 8;
                    StopTick = 20;
                }
                case 1 -> {
                    AttackTick = 35;
                    StopTick = 40;
                }
                case 2 -> {
                    AttackTick = 5;
                    StopTick = 20;
                }
                case 3 -> {
                    AttackTick = 10;
                    StopTick = 15;
                }
            }
            this.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
                ModNetworking.sendToClient(new Boss2AnimationStartS2CPacket(AttackMode), serverPlayer);
            });
        } else {
            AttackTick--;
            if (AttackMode == 1) {
                if (AttackTick % 10 == 0) {
                    List<Player> playerList = this.level().getEntitiesOfClass(Player.class, AABB.ofSize(this.getEyePosition(), 8, 8, 8));
                    playerList.forEach(player -> {
                        Compute.Damage.AttackDamageToPlayer_NumDamage(this, player, 100 * (1 + Utils.Boss2DeadTimes), 0.5f, 100);
                    });
                }
            }
            if (AttackTick == 0) {
                switch (AttackMode) {
                    case 0 -> {
                        List<Player> playerList = this.level().getEntitiesOfClass(Player.class, AABB.ofSize(this.pick(3, 0, true).getLocation(), 10, 10, 10));
                        playerList.forEach(player -> {
                            if (player.getEyePosition().distanceTo(this.pick(3, 0, true).getLocation()) <= 3)
                                Compute.Damage.AttackDamageToPlayer(this, player, 100 * (1 + Utils.Boss2DeadTimes));
                        });
                    }
                    case 2 -> {
                        Vec3 vec3 = this.pick(1, 0, true).getLocation().subtract(this.getEyePosition());
                        this.setDeltaMovement(vec3);

                        List<Player> playerList = this.level().getEntitiesOfClass(Player.class, AABB.ofSize(this.pick(3, 0, true).getLocation(), 10, 10, 10));
                        playerList.forEach(player -> {
                            if (player.getEyePosition().distanceTo(this.pick(3, 0, true).getLocation()) <= 3)
                                Compute.Damage.AttackDamageToPlayer_NumDamage(this, player, 100 * (1 + Utils.Boss2DeadTimes), 0.85f, 100);
                        });
                    }
                    case 3 -> {
                        List<Player> playerList = this.level().getEntitiesOfClass(Player.class, AABB.ofSize(this.pick(3, 0, true).getLocation(), 10, 10, 10));
                        playerList.forEach(player -> {
                            if (player.onGround())
                                Compute.Damage.AttackDamageToPlayer(this, player, 200 * (1 + Utils.Boss2DeadTimes));
                        });
                    }
                }
            }
        }

        if (this.tickCount % 30 == 0 && AttackMode == -1 && StopTick == -1) {
            AttackMode = 0;
            AttackTick = 8;
            StopTick = 20;
            this.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
                ModNetworking.sendToClient(new Boss2AnimationStartS2CPacket(AttackMode), serverPlayer);
            });
        }
    }

    public boolean isBoss() {
        return true;
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer p_20119_) {
        super.startSeenByPlayer(p_20119_);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer p_20174_) {
        super.stopSeenByPlayer(p_20174_);
    }

    @Override
    public SynchedEntityData getEntityData() {
        return super.getEntityData();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_INV, 1);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if (ClientUtils.Boss2AnimationMode != -1) {
            switch (ClientUtils.Boss2AnimationMode) {
                case 0 -> {
                    tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.normal_attack", Animation.LoopType.LOOP));
                    return PlayState.CONTINUE;
                }
                case 1 -> {
                    tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.round", Animation.LoopType.LOOP));
                    return PlayState.CONTINUE;
                }
                case 2 -> {
                    tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.rush", Animation.LoopType.LOOP));
                    return PlayState.CONTINUE;
                }
                case 3 -> {
                    tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.jump", Animation.LoopType.LOOP));
                    return PlayState.CONTINUE;
                }
            }
        }

        if (tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.walking", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
