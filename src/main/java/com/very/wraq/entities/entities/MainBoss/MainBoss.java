package com.very.wraq.entities.entities.MainBoss;

import com.very.wraq.projectiles.bow.FireBallTest;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.ParticlePackets.UtilsParticleS2CPacket;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class MainBoss extends WitherSkeleton implements GeoEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private Level level;
    private int random;
    private final ServerBossEvent BossInfo = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
    private static final EntityDataAccessor<Integer> DATA_ID_INV = SynchedEntityData.defineId(MainBoss.class, EntityDataSerializers.INT);
    private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = (Tmp) -> {
        return Tmp.getMobType() != MobType.UNDEAD && Tmp.attackable();
    };

    public MainBoss(EntityType<? extends WitherSkeleton> p_33325_, Level level) {
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
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 10000.0D).
                add(Attributes.MOVEMENT_SPEED, 0.3D).
                add(Attributes.ATTACK_DAMAGE, 120.0D).
                add(Attributes.KNOCKBACK_RESISTANCE, 10.0D).
                add(Attributes.FOLLOW_RANGE, 20.0D);
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
        if (this.hasCustomName()) {
            this.BossInfo.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Component p_20053_) {
        super.setCustomName(p_20053_);
        this.BossInfo.setName(this.getDisplayName());
    }

    protected boolean SummonFlag = true;

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.isSunSensitive()) {
            this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 1, true, true));
        }
        Random ran = new Random();
        /*if(this.getHealth() <=20.0f)
        {
            for(int i=0;i<6;i++) this.heal(ran.nextDouble());
        }*/
        this.BossInfo.setProgress(this.getHealth() / this.getMaxHealth());
        if (this.tickCount % 100 == 0) {
            for (int i = 0; i < 10; i++) {
                FireBallTest fireBallTest = new FireBallTest(level, this, 0, 0, 0, 2);
                Random r = new Random();
                fireBallTest.moveTo(-176.5 + r.nextDouble(26), 145 + r.nextDouble(10), 1404.5 + r.nextDouble(26));
                fireBallTest.setDeltaMovement(0, -2, 0);
                level.addFreshEntity(fireBallTest);
            }
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
        if (this.tickCount % 200 == 160) {
            switch (random) {
                case 0 -> {
                    for (double x = -171.5; x <= -164.5; x++) {
                        for (double z = 1409.5; z <= 1416.5; z++) {
                            level.addFreshEntity(new EvokerFangs(level, x, 115, z, 1.0f, 1, this));
                        }
                    }
                }
                case 1 -> {
                    for (double x = -162.5; x <= -155.5; x++) {
                        for (double z = 1409.5; z <= 1416.5; z++) {
                            level.addFreshEntity(new EvokerFangs(level, x, 115, z, 1.0f, 1, this));
                        }
                    }
                }
                case 2 -> {
                    for (double x = -162.5; x <= -155.5; x++) {
                        for (double z = 1418.5; z <= 1425.5; z++) {
                            level.addFreshEntity(new EvokerFangs(level, x, 115, z, 1.0f, 1, this));
                        }
                    }
                }
                case 3 -> {
                    for (double x = -171.5; x <= -164.5; x++) {
                        for (double z = 1418.5; z <= 1425.5; z++) {
                            level.addFreshEntity(new EvokerFangs(level, x, 115, z, 1.0f, 1, this));
                        }
                    }
                }
            }
        }
        if (this.tickCount % 40 == 0 && !this.isDeadOrDying()) {
            this.heal(ran.nextInt(100));
        }
        if (this.getHealth() <= 1500.0f && this.SummonFlag) {
            this.SummonFlag = false;
            this.heal(500.0f);
            Monster entity0 = new Skeleton(EntityType.SKELETON, level);
            entity0.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());
            entity0.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorForestSkeleton.get().getDefaultInstance());
            entity0.getAttribute(Attributes.MAX_HEALTH).setBaseValue(300D);
            entity0.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20);
            entity0.setHealth(entity0.getMaxHealth());
            entity0.moveTo(this.getX() + 1, this.getY(), this.getZ());
            level.addFreshEntity(entity0);

            Monster entity1 = new Skeleton(EntityType.SKELETON, level);
            entity1.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());
            entity1.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorForestSkeleton.get().getDefaultInstance());
            entity1.getAttribute(Attributes.MAX_HEALTH).setBaseValue(300D);
            entity0.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20);
            entity1.setHealth(entity1.getMaxHealth());
            entity1.moveTo(this.getX() - 1, this.getY(), this.getZ());
            level.addFreshEntity(entity1);

            Monster entity2 = new Skeleton(EntityType.SKELETON, level);
            entity2.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());
            entity2.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorForestSkeleton.get().getDefaultInstance());
            entity2.getAttribute(Attributes.MAX_HEALTH).setBaseValue(300D);
            entity0.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20);
            entity2.setHealth(entity2.getMaxHealth());
            entity2.moveTo(this.getX(), this.getY(), this.getZ() + 1);
            level.addFreshEntity(entity2);

            Monster entity3 = new Skeleton(EntityType.SKELETON, level);
            entity3.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());
            entity3.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorForestSkeleton.get().getDefaultInstance());
            entity3.getAttribute(Attributes.MAX_HEALTH).setBaseValue(300D);
            entity0.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20);
            entity3.setHealth(entity3.getMaxHealth());
            entity3.moveTo(this.getX(), this.getY(), this.getZ() - 1);
            level.addFreshEntity(entity3);
        }
    }

    public boolean isBoss() {
        return true;
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer p_20119_) {
        super.startSeenByPlayer(p_20119_);
        this.BossInfo.addPlayer(p_20119_);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer p_20174_) {
        super.stopSeenByPlayer(p_20174_);
        this.BossInfo.removePlayer(p_20174_);
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


        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
