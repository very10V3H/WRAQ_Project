package fun.wraq.projectiles.mana;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.util.StringUtils;
import fun.wraq.core.ManaAttackModule;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.projectiles.mana.ManaArrow;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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


public class SwordAir extends AbstractArrow implements GeoEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private Player player;

    private boolean small;

    public SwordAir(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public SwordAir(EntityType<? extends AbstractArrow> entityType, LivingEntity shooter, Level level, boolean small) {
        super(entityType, shooter, level);
        this.player = (Player) shooter;
        this.small = small;
    }

    @Override
    public ItemStack getPickupItem() {
        return MinecartItem.byId(0).getDefaultInstance();
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_) {
        super.onHitBlock(p_36755_);
        this.setDeltaMovement(0, 0, 0);
    }

    @Override
    protected boolean canHitEntity(Entity p_36743_) {
        return super.canHitEntity(p_36743_);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        this.setDeltaMovement(0, 0, 0);
    }

    @Override
    public void tick() {
        super.tick();
        for (int i = 0; i < 10; i++) {
            if (!small) {
                this.level().addParticle(ParticleTypes.SNOWFLAKE,
                        this.getX() + (random.nextInt(5) - 2.5) * random.nextDouble(),
                        this.getY() + random.nextDouble(),
                        this.getZ() + (random.nextInt(5) - 2.5) * random.nextDouble(), 0, 0, 0);
            } else {
                this.level().addParticle(ParticleTypes.SNOWFLAKE,
                        this.getX() + (random.nextInt(3) - 1.5) * random.nextDouble(),
                        this.getY() + random.nextDouble(),
                        this.getZ() + (random.nextInt(3) - 1.5) * random.nextDouble(), 0, 0, 0);
            }
        }
        if (this.player != null && !this.level().isClientSide && this.tickCount % 20 == 0) {
            List<Mob> mobList = this.level().getEntitiesOfClass(Mob.class, AABB.ofSize(this.position(), 15, 15, 15));
            mobList.forEach(mob -> {
                if (mob.distanceTo(this) < 3) {
                    if (small) {
                        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player, player.level(),
                                PlayerAttributes.manaDamage(player),
                                PlayerAttributes.manaPenetration(player),
                                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Sea);
                        ManaAttackModule.BasicAttack(player, mob, PlayerAttributes.manaDamage(player) * 0.5,
                                PlayerAttributes.manaPenetration(player), PlayerAttributes.manaPenetration0(player),
                                player.level(), newArrow, true);
                    } else {
                        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player, player.level(),
                                PlayerAttributes.manaDamage(player),
                                PlayerAttributes.manaPenetration(player),
                                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Sea);
                        ManaAttackModule.BasicAttack(player, mob, PlayerAttributes.manaDamage(player) * 2,
                                PlayerAttributes.manaPenetration(player), PlayerAttributes.manaPenetration0(player),
                                player.level(), newArrow, true);
                    }
                }
            });
            if (this.tickCount == 100) {
                mobList.forEach(mob -> {
                    if (mob.distanceTo(this) < 3) {
                        if (small) {
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player, player.level(),
                                    PlayerAttributes.manaDamage(player),
                                    PlayerAttributes.manaPenetration(player),
                                    PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Sea);
                            ManaAttackModule.BasicAttack(player, mob, PlayerAttributes.manaDamage(player) * 2,
                                    PlayerAttributes.manaPenetration(player), PlayerAttributes.manaPenetration0(player),
                                    player.level(), newArrow, true);
                        } else {
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player, player.level(),
                                    PlayerAttributes.manaDamage(player),
                                    PlayerAttributes.manaPenetration(player),
                                    PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Sea);
                            ManaAttackModule.BasicAttack(player, mob, PlayerAttributes.manaDamage(player) * 8,
                                    PlayerAttributes.manaPenetration(player), PlayerAttributes.manaPenetration0(player),
                                    player.level(), newArrow, true);
                        }
                    }
                });
                ParticleProvider.DisperseParticle(this.position(), (ServerLevel) this.level(), 0.5, 2, 40, ParticleTypes.SNOWFLAKE, 1);
            }
            if (this.tickCount > 100) this.remove(RemovalReason.KILLED);
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

    public boolean getSmall() {
        return this.small;
    }

}
