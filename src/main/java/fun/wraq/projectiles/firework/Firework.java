package fun.wraq.projectiles.firework;

import fun.wraq.common.Compute;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.randomevent.impl.special.SpringMobEvent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.apache.commons.lang3.RandomUtils;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Random;

public class Firework extends AbstractArrow implements GeoEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private Player player;

    public Firework(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public Firework(EntityType<? extends AbstractArrow> entityType, LivingEntity shooter, Level level) {
        super(entityType, shooter, level);
        this.player = (Player) shooter;
    }

    @Override
    public ItemStack getPickupItem() {
        return MinecartItem.byId(0).getDefaultInstance();
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_) {
        super.onHitBlock(p_36755_);
        if (this.player != null && !this.level().isClientSide) {
            CompoundTag compoundTag = new CompoundTag();
            byte a = 1;
            byte[] bytes = {0, 1, 2, 3, 4};
            String[] strings = {
                    "SMALL_BALL",
                    "LARGE_BALL",
                    "CREEPER",
                    "STAR",
                    "BURST"
            };
            Random random = new Random();
            compoundTag.putByte("Type", a);
            compoundTag.putByte("Trail", a);
            compoundTag.putIntArray("Colors", new int[]{14602026, 15790320});
            compoundTag.putByte("Flicker", a);
            compoundTag.putIntArray("FadeColors", new int[]{random.nextInt(20000000)});
            compoundTag.putString("forge:shape_type", strings[random.nextInt(5)]);
            ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
            itemStack.getOrCreateTagElement("Fireworks").putByte("Flight", bytes[random.nextInt(1, 3)]);
            ListTag listTag = new ListTag();
            listTag.add(compoundTag);
            itemStack.getOrCreateTagElement("Fireworks").put("Explosions", listTag);

            FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(
                    this.level(), itemStack,
                    this.position().x, this.position().y, this.position().z, true);
            fireworkRocketEntity.setSilent(true);
            fireworkRocketEntity.setDeltaMovement(0, 0, 0);
            fireworkRocketEntity.moveTo(this.position());
            this.level().addFreshEntity(fireworkRocketEntity);
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
            Entity entity = result.getEntity();
            if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity instanceof Mob mob) {
                    if (MobSpawn.getMobOriginName(mob).equals(SpringMobEvent.mobName)) {
                        Damage.causeTrueDamageToMonster(player, mob,
                                RandomUtils.nextInt(100 * (int) Math.pow(10, 4), 1000 * (int) Math.pow(10, 4)));
                    }
                    Compute.addSlowDownEffect(mob, 20, 1);
                }
                CompoundTag compoundTag = new CompoundTag();
                byte a = 1;
                byte[] bytes = {0, 1, 2, 3, 4};
                String[] strings = {
                        "SMALL_BALL",
                        "LARGE_BALL",
                        "CREEPER",
                        "STAR",
                        "BURST"
                };
                Random random = new Random();
                compoundTag.putByte("Type", a);
                compoundTag.putByte("Trail", a);
                compoundTag.putIntArray("Colors", new int[]{14602026, 15790320});
                compoundTag.putByte("Flicker", a);
                compoundTag.putIntArray("FadeColors", new int[]{random.nextInt(20000000)});
                compoundTag.putString("forge:shape_type", strings[random.nextInt(5)]);
                ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
                itemStack.getOrCreateTagElement("Fireworks").putByte("Flight", bytes[random.nextInt(1, 3)]);
                ListTag listTag = new ListTag();
                listTag.add(compoundTag);
                itemStack.getOrCreateTagElement("Fireworks").put("Explosions", listTag);

                FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(
                        this.level(), itemStack, livingEntity);
                fireworkRocketEntity.setSilent(true);
                fireworkRocketEntity.setDeltaMovement(0, 0, 0);
                fireworkRocketEntity.moveTo(livingEntity.position().add(0, 1, 0));
                this.level().addFreshEntity(fireworkRocketEntity);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (((this.tickCount >= 100) && player != null) || this.isInWater()) {
            this.remove(RemovalReason.KILLED);
        }
        this.level().addParticle(ParticleTypes.ASH, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
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
