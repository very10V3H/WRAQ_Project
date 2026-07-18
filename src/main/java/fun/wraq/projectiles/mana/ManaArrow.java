package fun.wraq.projectiles.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.process.func.damage.Damage;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import java.util.List;

public class ManaArrow extends AbstractArrow {

    private static final EntityDataAccessor<Integer> DATA_STYLE =
            SynchedEntityData.defineId(ManaArrow.class, EntityDataSerializers.INT);

    public double manaPenetration;
    public double manaPenetration0;
    public Player player;
    private boolean AdjustOneTimes = false;
    private Vec3 InWaterVec3;
    private Mob mob;
    public double rate = 1;
    public boolean mainShoot = true;
    public ManaArrowHitEntity manaArrowHitEntity;

    public int getStyle() {
        return this.entityData.get(DATA_STYLE);
    }

    public void setStyle(int style) {
        this.entityData.set(DATA_STYLE, style);
    }

    public ManaArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_STYLE, 0xFFAA88FF);
    }

    public ManaArrow(EntityType<? extends AbstractArrow> entityType, LivingEntity shooter, Level level,
                     double rate, double manaPenetration, double manaPenetration0, int style,
                     ManaArrowHitEntity manaArrowHitEntity) {
        super(entityType, Compute.getPlayerHandItemPos(shooter, true).x,
                Compute.getPlayerHandItemPos(shooter, true).y,
                Compute.getPlayerHandItemPos(shooter, true).z, level);
        this.setOwner(shooter);
        this.player = (Player) shooter;
        this.rate = rate;
        this.manaPenetration = manaPenetration;
        this.manaPenetration0 = manaPenetration0;
        setStyle(style);
        this.manaArrowHitEntity = manaArrowHitEntity;
    }

    public ManaArrow(EntityType<? extends AbstractArrow> entityType, Player player, double rate) {
        this(entityType, player, player.level(), rate, PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), 0xFFAA88FF);
    }

    public ManaArrow(EntityType<? extends AbstractArrow> entityType, LivingEntity shooter, Level level,
                     double rate, double manaPenetration, double manaPenetration0, int style) {
        this(entityType, shooter, level, rate, manaPenetration, manaPenetration0, style, null);
    }

    public ManaArrow(EntityType<? extends AbstractArrow> entityType, LivingEntity mob, Level level, double rate) {
        super(entityType, mob, level);
        this.mob = (Mob) mob;
        this.rate = rate;
        setStyle(0xFFAA88FF);
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

    public boolean shootFromMob() {
        return this.mob != null;
    }
}
