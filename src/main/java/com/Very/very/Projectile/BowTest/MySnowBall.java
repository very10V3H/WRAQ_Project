package com.Very.very.Projectile.BowTest;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class MySnowBall extends Snowball {
    public LivingEntity player;
    public MySnowBall(EntityType<? extends Snowball> p_37391_, Level p_37392_) {
        super(p_37391_, p_37392_);
    }

    public MySnowBall(Level p_37399_, LivingEntity p_37400_) {
        super(p_37399_, p_37400_);
        this.player = p_37400_;
    }

    public MySnowBall(Level p_37394_, double p_37395_, double p_37396_, double p_37397_) {
        super(p_37394_, p_37395_, p_37396_, p_37397_);
    }
    @Override
    protected void onHitEntity(EntityHitResult p_37404_) {
        super.onHitEntity(p_37404_);
        Entity entity = p_37404_.getEntity();
/*        if(player instanceof Player) entity.hurt(DamageSource.playerAttack((Player)player),5.0F);*/
    }
}
