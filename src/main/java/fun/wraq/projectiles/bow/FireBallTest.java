package fun.wraq.projectiles.bow;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class FireBallTest extends LargeFireball {


    public FireBallTest(EntityType<? extends LargeFireball> p_37199_, Level p_37200_) {
        super(p_37199_, p_37200_);
    }

    public FireBallTest(Level p_181151_, LivingEntity p_181152_, double p_181153_, double p_181154_, double p_181155_, int p_181156_) {
        super(p_181151_, p_181152_, p_181153_, p_181154_, p_181155_, p_181156_);
    }

    @Override
    public void tick() {
        if (this.tickCount >= 80) this.remove(RemovalReason.KILLED);
        super.tick();
    }

    @Override
    protected void onHitEntity(EntityHitResult p_37216_) {
        super.onHitEntity(p_37216_);
        if (!this.level().isClientSide) {
            Entity entity = p_37216_.getEntity();
            Entity entity1 = this.getOwner();
            /*            entity.hurt(this.damageSources().playerAttack(), 20.0d);*/
            if (entity1 instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity) entity1, entity);
            }

        }
    }
}
