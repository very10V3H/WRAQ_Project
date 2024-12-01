package fun.wraq.projectiles.mana;

import fun.wraq.common.registry.ModEntityType;
import fun.wraq.core.ManaAttackModule;
import fun.wraq.series.nether.power.MagmaPower;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class NewArrowMagma extends AbstractArrow {
    private double BaseDamage;
    private double BreakDefence;
    private double BreakDefence0;
    private double ExpUp;
    private Player player;
    private boolean AdjustOneTimes = true;
    private Vec3 InWaterVec3;

    public NewArrowMagma(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    public NewArrowMagma(LivingEntity shooter, Level level) {
        super(ModEntityType.NEW_ARROW_MAGMA.get(), shooter, level);
    }

    public NewArrowMagma(LivingEntity shooter, Level level, double BaseDamage, double BreakDefence, double ExpUp, double BreakDefence0) {
        super(ModEntityType.NEW_ARROW_MAGMA.get(), shooter, level);
        this.player = (Player) shooter;
        this.BaseDamage = BaseDamage;
        this.BreakDefence = BreakDefence;
        this.ExpUp = ExpUp;
        this.BreakDefence0 = BreakDefence0;
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
        super.onHitEntity(result);
        if (this.player != null && !level().isClientSide) {
            Entity entity = result.getEntity();
            ManaAttackModule.BasicAttack(player, entity, 1, BreakDefence, BreakDefence0, level(), this, true);
            if (entity instanceof Mob mob) {
                MagmaPower.onHit(player, mob, 3);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.tickCount > 8) {
/*            if (AdjustOneTimes) {
                List<Mob> mobList = this.level().getEntitiesOfClass(Mob.class, AABB.ofSize(this.getPosition(1),20,20,20));
                if (mobList.size() > 0) {
                    Mob livingEntity = null;
                    double length = 30;
                    for (Mob mob1 : mobList) {
                        if (mob1.isAlive() && mob1.position().distanceTo(this.position()) < length) {
                            livingEntity = mob1;
                            length = mob1.position().distanceTo(this.position());
                        }
                    }
                    if (livingEntity != null) {
                        Vec3 Delta = livingEntity.getPosition(1).add(0,1,0).subtract(this.getPosition(1));
                        Delta.normalize();
                        if (Delta.length() > 0.1) {
                            this.setDeltaMovement(Delta.scale(0.2));
                            this.InWaterVec3 = Delta.scale(0.2);
                        }
                        AdjustOneTimes = false;
                    }
                }
            }*/
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
}
