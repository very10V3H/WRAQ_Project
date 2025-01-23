package fun.wraq.projectiles.mana;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.util.StringUtils;
import fun.wraq.core.ManaAttackModule;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.render.hud.Mana;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;


public class ShangMengLiSwordAir extends AbstractArrow implements GeoEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public Player player;
    private boolean isPower;

    public ShangMengLiSwordAir(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public ShangMengLiSwordAir(EntityType<? extends AbstractArrow> entityType, LivingEntity shooter, Level level, boolean isPower) {
        super(entityType, shooter, level);
        this.player = (Player) shooter;
        this.isPower = isPower;
    }

    @Override
    public ItemStack getPickupItem() {
        return MinecartItem.byId(0).getDefaultInstance();
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_) {
        super.onHitBlock(p_36755_);
        this.remove(RemovalReason.KILLED);
    }

    @Override
    protected boolean canHitEntity(Entity p_36743_) {
        return super.canHitEntity(p_36743_);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!player.level().isClientSide) {
            if (!isPower) {
                Entity entity = result.getEntity();
                fun.wraq.projectiles.mana.ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), player, player.level(),
                        PlayerAttributes.manaDamage(player),
                        PlayerAttributes.manaPenetration(player),
                        PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.EndParticle);
                ManaAttackModule.BasicAttack(player, entity, 1,
                        PlayerAttributes.manaPenetration(player), PlayerAttributes.manaPenetration0(player),
                        player.level(), newArrow, true);
                Mana.addOrCostPlayerMana(player, 20);
                player.sendSystemMessage(Component.literal("false"));
            } else {
                Entity entity = result.getEntity();
                if (!(entity instanceof Mob)) return;
                Damage.causeRateApDamageToMonster(player, (Mob) entity, 1 + 3 * PlayerAttributes.coolDownDecrease(player), isPower);
                Mana.addOrCostPlayerMana(player, 20);
                player.sendSystemMessage(Component.literal("true"));
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        for (int i = 0; i < 10; i++) {
            this.level().addParticle(ParticleTypes.WITCH,
                    this.getX() + (random.nextInt(5) - 2.5) * random.nextDouble(),
                    this.getY() + random.nextDouble(),
                    this.getZ() + (random.nextInt(5) - 2.5) * random.nextDouble(), 0, 0, 0);

            this.level().addParticle(ParticleTypes.WITCH,
                    this.getX() + (random.nextInt(3) - 1.5) * random.nextDouble(),
                    this.getY() + random.nextDouble(),
                    this.getZ() + (random.nextInt(3) - 1.5) * random.nextDouble(), 0, 0, 0);
        }
        if (this.tickCount >= 100) this.remove(RemovalReason.KILLED);
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

    public boolean isPower() {
        return isPower;
    }
}
