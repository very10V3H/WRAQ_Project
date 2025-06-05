package fun.wraq.projectiles.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.util.StringUtils;
import fun.wraq.core.ManaAttackModule;
import fun.wraq.process.func.damage.Damage;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;

public class ManaArrow extends AbstractArrow implements GeoEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public double manaPenetration;
    public double manaPenetration0;
    public Player player;
    private boolean AdjustOneTimes = false;
    private Vec3 InWaterVec3;
    public String particleType;
    private Mob mob;
    public double rate = 1;
    public boolean mainShoot = true;
    public ManaArrowHitEntity manaArrowHitEntity;

    public ManaArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public ManaArrow(EntityType<? extends AbstractArrow> entityType, LivingEntity shooter, Level level,
                     double rate, double manaPenetration, double manaPenetration0, String particleType,
                     ManaArrowHitEntity manaArrowHitEntity) {
        super(entityType, Compute.getPlayerHandItemPos(shooter, true).x,
                Compute.getPlayerHandItemPos(shooter, true).y,
                Compute.getPlayerHandItemPos(shooter, true).z, level);
        this.setOwner(shooter);
        this.player = (Player) shooter;
        this.rate = rate;
        this.manaPenetration = manaPenetration;
        this.manaPenetration0 = manaPenetration0;
        this.particleType = particleType;
        this.manaArrowHitEntity = manaArrowHitEntity;
    }

    public ManaArrow(EntityType<? extends AbstractArrow> entityType, Player player, double rate) {
        this(entityType, player, player.level(), rate, PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.EVOKER);
    }

    public ManaArrow(EntityType<? extends AbstractArrow> entityType, LivingEntity shooter, Level level,
                     double rate, double manaPenetration, double manaPenetration0, String particleType) {
        this(entityType, shooter, level, rate, manaPenetration, manaPenetration0, particleType, null);
    }

    public ManaArrow(EntityType<? extends AbstractArrow> entityType, LivingEntity mob, Level level, double rate) {
        super(entityType, mob, level);
        this.mob = (Mob) mob;
        this.rate = rate;
    }

    @Override
    public ItemStack getPickupItem() {
        return MinecartItem.byId(0).getDefaultInstance();
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_) {
        super.onHitBlock(p_36755_);
        if (this.player != null && !this.level().isClientSide) {
            CompoundTag data = player.getPersistentData();
            ManaAttackModule.ManaSkill6Attack(data, player, false);
        }
        this.remove(RemovalReason.KILLED);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (this.mob != null && !level().isClientSide) {
            Entity entity = result.getEntity();
            if (entity instanceof Player player1) {
                Damage.manaDamageToPlayer_RateApDamage(mob, player1, rate);
            }
        }
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.player != null && !this.level().isClientSide && this.tickCount > 8) {
            if (AdjustOneTimes) {
                List<Mob> mobList = this.level().getEntitiesOfClass(Mob.class, AABB.ofSize(this.getPosition(1), 20, 20, 20));
                if (mobList.size() > 0) {
                    Mob mob = null;
                    double length = 30;
                    for (Mob mob1 : mobList) {
                        if (mob1.isAlive() && mob1.position().distanceTo(this.position()) < length) {
                            mob = mob1;
                            length = mob1.position().distanceTo(this.position());
                        }
                    }
                    if (mob != null) {
                        Vec3 Delta = mob.getPosition(1).add(0, 1, 0).subtract(this.getPosition(1));
                        Delta.normalize();
                        if (Delta.length() > 0.1) {
                            this.setDeltaMovement(Delta.scale(0.2));
                            this.InWaterVec3 = Delta.scale(0.2);
                            AdjustOneTimes = false;
                        }
                    }
                }
            }
        }
        if ((this.tickCount >= 100) && player != null) {
            if (!this.level().isClientSide) {
                CompoundTag data = player.getPersistentData();
                ManaAttackModule.ManaSkill6Attack(data, player, false);
            }
            this.remove(RemovalReason.KILLED);
        }
        if (this.getDeltaMovement().length() <= 0.05) {
            if (this.isInWater()) {
                if (this.InWaterVec3 == null) {
                    Vec3 Delta = this.getDeltaMovement();
                    this.InWaterVec3 = Delta.normalize().scale(1);
                }
                this.setDeltaMovement(this.InWaterVec3);
            } else this.remove(RemovalReason.KILLED);
        }
        if (!this.level().isClientSide && this.isNoGravity() && player != null && this.distanceTo(player) > 60)
            this.remove(RemovalReason.KILLED);

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

    public boolean shootFromMob() {
        return this.mob != null;
    }
}
