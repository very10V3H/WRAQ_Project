package com.Very.very.Projectile.BowTest;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class Arrowtest extends Projectile {

    public Arrowtest(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
/*        entity.hurt(DamageSource.ANVIL,5.0F);*/
        super.onHitEntity(result);
    }

    @Override
    protected void defineSynchedData() {
    }
}
