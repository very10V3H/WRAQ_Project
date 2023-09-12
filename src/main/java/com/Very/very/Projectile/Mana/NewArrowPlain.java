package com.Very.very.Projectile.Mana;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.EntityInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import static java.lang.Math.*;

public class NewArrowPlain extends AbstractArrow {
    private float BaseDamage;
    private float BreakDefence;
    private float BreakDefence0;
    private float ExpUp;
    private Player player;
    private double PositionX;
    private double PositionY;
    private double PositionZ;
    private boolean flag = true;
    public NewArrowPlain(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    public NewArrowPlain(LivingEntity shooter, Level level)
    {
        super(EntityInit.NEW_ARROW_PLAIN.get(), shooter, level);
    }
    public NewArrowPlain(LivingEntity shooter, Level level , float BaseDamage, float BreakDefence , float ExpUp, float BreakDefence0)
    {
        super(EntityInit.NEW_ARROW_PLAIN.get(), shooter, level);
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
            if (data.contains("snowRune") && data.getInt("snowRune") == 0) {
                data.putInt("snowRune0",0);
                data.putInt("snowRune0Time",0);
            }
            ManaAttackModule.ManaSkill6Attack(data,player,false);
        }
        this.remove(RemovalReason.KILLED);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if(this.player != null && !level().isClientSide)
        {
            Entity entity = result.getEntity();
            ManaAttackModule.BasicAttack(player,entity,BaseDamage,BreakDefence,BreakDefence0,level());
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(this.isUnderWater() || this.tickCount >= 100) {
            this.remove(RemovalReason.KILLED);
            if (!this.level().isClientSide){
                CompoundTag data = player.getPersistentData();
                ManaAttackModule.ManaSkill6Attack(data,player,false);
            }
        }
        if(abs(this.getDeltaMovement().x) + abs( this.getDeltaMovement().y) + abs(this.getDeltaMovement().z) <= 1) this.remove(RemovalReason.KILLED);
        double DX = this.getX()-this.PositionX;
        double DY = this.getY()-this.PositionY;
        double DZ = this.getZ()-this.PositionZ;
        Compute.ParticleSCRAPE(this.PositionX,this.PositionY,this.PositionZ,level(),0.2);
        for(double i = 0.1 ;i <= 1 ;i += 0.1)
        {
            Compute.ParticleSCRAPE(this.PositionX+i*DX,this.PositionY+i*DY,this.PositionZ+i*DZ,level(),0.2);
        }
/*
        Compute.EntityFaceConnectCircleCreate(this,this.getDeltaMovement(),new Vec3(this.PositionX,this.PositionY,this.PositionZ),0,0.5,40,ParticleTypes.SCRAPE,DX,DY,DZ,0);
        Compute.EntityFaceConnectCircleCreate(this,this.getDeltaMovement(),new Vec3(this.PositionX,this.PositionY,this.PositionZ),0,0.5,40,ParticleTypes.SCRAPE,DX,DY,DZ,1);
*/
        this.PositionX = this.getX();
        this.PositionY = this.getY();
        this.PositionZ = this.getZ();
    }
}
