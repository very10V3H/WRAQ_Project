package fun.wraq.projectiles.mana.swordair;

import fun.wraq.common.Compute;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Comparator;
import java.util.Set;


public class SwordAir extends AbstractArrow implements GeoEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public Player player;

    public SwordAir(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public SwordAir(EntityType<? extends AbstractArrow> entityType, LivingEntity shooter, Level level) {
        super(entityType, shooter, level);
        this.player = (Player) shooter;
        this.setSilent(true);
    }

    public void shootFromRotation(float speed) {
        if (player == null) return;

        this.setPos(position().add(0, -0.25, 0));
        Vec3 startPos = player.getEyePosition();
        Vec3 targetPos = Compute.getPickLocationIgnoreBlock(player, 32);
        Vec3 delta = targetPos.subtract(startPos);

        Set<Mob> set = Compute.getPlayerRayMobList(player, 0.5, 1, 32);
        if (!set.isEmpty()) {
            Mob mob = set.stream().min(Comparator.comparingDouble(m -> m.distanceTo(player))).orElse(null);
            if (mob != null) {
                if (mob.distanceTo(player) < 2) {
                    delta = mob.getEyePosition().subtract(player.getEyePosition());
                    this.moveTo(player.getEyePosition());
                } else {
                    delta = mob.getEyePosition().subtract(Compute.getPlayerHandItemPos(player, true));
                }
            }
        }

        this.shoot(delta.x, delta.y, delta.z, Math.min(5, speed), 1);
        this.setNoGravity(true);
        player.level().addFreshEntity(this);
    }

    @Override
    public ItemStack getPickupItem() {
        return MinecartItem.byId(0).getDefaultInstance();
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
    }

    public void onHitEntity(Mob mob) {}

    public void clientTick() {}

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            clientTick();
        }
        if (this.player != null && !this.level().isClientSide && this.tickCount % 20 == 0) {
            if (this.tickCount > 100) {
                this.remove(RemovalReason.KILLED);
            }
        }
        if (!this.level().isClientSide && this.isNoGravity() && player != null && this.distanceTo(player) > 60) {
            this.remove(RemovalReason.KILLED);
        }
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
