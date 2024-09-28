package fun.wraq.entities.entities.Civil;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class Civil extends PathfinderMob implements GeoEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public Player owner;

    public Civil(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public Civil(EntityType<? extends PathfinderMob> entityType, Level level, Player player) {
        super(entityType, level);
        owner = player;
    }

    public static AttributeSupplier setAttributes() {
        return PathfinderMob.createMobAttributes().
                add(Attributes.MAX_HEALTH, 10000).
                add(Attributes.ATTACK_DAMAGE, 20).
                add(Attributes.MOVEMENT_SPEED, 0)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));

        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));


/*        this.targetSelector.addGoal(1,new NearestAttackableTargetGoal<>(this, Player.class,true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));*/
        super.registerGoals();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

/*
        if (this.hurtTime > 0) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.hurt", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if (tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.walking", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
*/

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.normal", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SoundEvents.PLAYER_HURT;
    }

}
