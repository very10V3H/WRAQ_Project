package com.Very.very.Projectile.Mana;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.EntityInit;
import com.Very.very.ValueAndTools.Utils.Struct.Power;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Render.Particles.ModParticles;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import static java.lang.Math.*;

public class NewArrow extends AbstractArrow {
    private float BaseDamage;
    private float BreakDefence;
    private float BreakDefence0;
    private float ExpUp;
    private Player player;
    private double PositionX;
    private double PositionY;
    private double PositionZ;
    private boolean flag;
    public NewArrow(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    public NewArrow(LivingEntity shooter, Level level)
    {
        super(EntityInit.NEW_ARROW.get(), shooter, level);
    }
    public NewArrow(LivingEntity shooter, Level level , float BaseDamage, float BreakDefence, float ExpUp, boolean flag, float BreakDefence0)
    {
        super(EntityInit.NEW_ARROW.get(), shooter, level);
        this.player = (Player) shooter;
        this.BaseDamage = BaseDamage;
        this.BreakDefence = BreakDefence;
        this.ExpUp = ExpUp;
        this.flag = flag;
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
        if(this.player != null && !flag && !level().isClientSide)
        {
            Entity entity = result.getEntity();
            ManaAttackModule.BasicAttack(player,entity,BaseDamage,BreakDefence,BreakDefence0,level());
        }
        if(this.player != null && flag && Utils.PowerMap.containsKey((ServerPlayer) player))
        {
            Power power = Utils.PowerMap.get((ServerPlayer) player);
            int Effect = power.get1Count() + 1;
            int Range = power.get2Count();
            int Damage = power.get3Count();
            int Break = power.get4Count();
            int Kaze = power.get5Count();
            int Snow = power.get6Count();
            int Lightning = power.get7Count();
            int Gather = power.get8Count();
            Entity entity = result.getEntity();
            CompoundTag data = player.getPersistentData();
            if (data.contains("snowRune") && data.getInt("snowRune") == 0) {
                if (!data.contains("snowRune0") || data.getInt("snowRune0") == 0) {
                    data.putInt("snowRune0",1);
                    data.putInt("snowRune0Time",100);
                }
                else {
                    if (data.getInt("snowRune0") < 5) {
                        data.putInt("snowRune0",data.getInt("snowRune0")+1);
                        data.putInt("snowRune0Time",100);
                    }
                    else {
                        data.putInt("snowRune0Time",100);
                    }
                }
            }
            if(entity instanceof Mob && !(entity instanceof Villager))
            {
                float damage;
                Mob monster = (Mob) entity;
                float Defence = Compute.MonsterManaDefence(monster);
                if (Break > 0) BreakDefence = (1 - (1 - BreakDefence) * (1 - 0.15f * Break));
                if(data.contains("ManaRune") && data.getInt("ManaRune") == 2 && data.contains("ManaRune2") && data.getInt("ManaRune2") == 0)
                {
                    data.putInt("ManaRune2",200);
                    monster.getPersistentData().putInt("ManaRune2",60);
                    Utils.MonsterAttributeDataProvider.add(monster);
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level());
                    lightningBolt.setCause((ServerPlayer) player);
                    lightningBolt.setDamage(0);
                    lightningBolt.setVisualOnly(true);
                    lightningBolt.moveTo(monster.getPosition(1.0f));
                    lightningBolt.setSilent(true);
                    level().addFreshEntity(lightningBolt);
                    if(Defence == 0)
                    {
                        damage = BaseDamage*(1.0F-(0.25F*(float)log(((monster.getAttribute(Attributes.ARMOR).getValue()*(1.0F-BreakDefence)*(E*E/100)+1)))));
                        damage *= (1 + Damage * Effect);
                        if (Range >= 0) Compute.CodeHitMonster(level(),monster,Range,(ServerPlayer) player,damage,Kaze,Effect,Snow,Lightning,Gather);
                        monster.hurt(monster.damageSources().playerAttack(player),damage*3);
                        if(!data.contains("IgnoreRune")||(!data.getBoolean("IgnoreRune"))) Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                                Component.literal("魔源符石-苍雷之怒").withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal("在本次攻击中，造成了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(String.format("%.2f",damage*3))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE).
                                        append(Component.literal("伤害").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                    else
                    {
                        damage = BaseDamage*(1.0F-(0.25F*(float)log(((Defence)*(1.0F-BreakDefence))*(E*E/100)+1)));
                        damage *= (1 + Damage * Effect);
                        if (Range >= 0) Compute.CodeHitMonster(level(),monster,Range,(ServerPlayer) player,damage,Kaze,Effect,Snow,Lightning,Gather);
                        monster.hurt(monster.damageSources().playerAttack(player),damage*3);
                        if(!data.contains("IgnoreRune")||(!data.getBoolean("IgnoreRune"))) Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                                Component.literal("魔源符石-苍雷之怒").withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal("在本次攻击中，造成了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(String.format("%.2f",damage*3))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE).
                                        append(Component.literal("伤害").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                }
                else
                {
                    if(Defence == 0)
                    {
                        damage = BaseDamage*(1.0F-(0.25F*(float)log(((monster.getAttribute(Attributes.ARMOR).getValue()*(1.0F-BreakDefence)*(E*E/100)+1)))));
                        damage *= (1 + Damage * Effect);
                        if (Range >= 0) Compute.CodeHitMonster(level(),monster,Range,(ServerPlayer) player,damage,Kaze,Effect,Snow,Lightning,Gather);
                        monster.hurt(monster.damageSources().playerAttack(player),damage);
                    }
                    else
                    {
                        damage = BaseDamage*(1.0F-(0.25F*(float)log(((Defence)*(1.0F-BreakDefence))*(E*E/100)+1)));
                        damage *= (1 + Damage * Effect);
                        if (Range >= 0) Compute.CodeHitMonster(level(),monster,Range,(ServerPlayer) player,damage,Kaze,Effect,Snow,Lightning,Gather);
                        monster.hurt(monster.damageSources().playerAttack(player),damage);
                    }
                }
                if (data.contains("MagmaPower") && data.getBoolean("MagmaPower")) {
                    Compute.MagmaPower(monster,level(),player);
                    data.putBoolean("MagmaPower",false);
                }
            }
            if(entity instanceof Player)
            {
                float damage;
                Player hurter = (Player) entity;
                float Defence = Compute.PlayerManaDefence(hurter);
                if(Defence == 0)
                {
                    damage = BaseDamage*(1.0F-(0.25F*(float)log(((hurter.getAttribute(Attributes.ARMOR).getValue()*(1.0F-BreakDefence)*(E*E/100)+1)))));
                    if (Range >= 0) Compute.CodeHitPlayer(level(),hurter,Range,(ServerPlayer) player,damage,Kaze,Effect,Snow,Lightning,Gather);
                    hurter.hurt(hurter.damageSources().playerAttack(player),damage*0.1f);
                }
                else
                {
                    damage = BaseDamage*(1.0F-(0.25F*(float)log(((Defence)*(1.0F-BreakDefence))*(E*E/100)+1)));
                    if (Range >= 0) Compute.CodeHitPlayer(level(),hurter,Range,(ServerPlayer) player,damage,Kaze,Effect,Snow,Lightning,Gather);
                    hurter.hurt(hurter.damageSources().playerAttack(player),damage*0.1f);
                }
                if (data.contains("MagmaPower") && data.getBoolean("MagmaPower")) {
                    Compute.MagmaPower(hurter,level(),player);
                    data.putBoolean("MagmaPower",false);
                }
            }
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
        if (!level().isClientSide) {
            if (flag) {
                Power power = Utils.PowerMap.get((ServerPlayer) player);
                int Effect = power.get1Count() ;
                int Range = power.get2Count();
                int Damage = power.get3Count();
                int Break = power.get4Count();
                int Kaze = power.get5Count();
                int Snow = power.get6Count();
                int Lightning = power.get7Count();
                int Gather = power.get8Count();
                int Count = 0;
                while (Effect > 0) {
                    Compute.EntityFaceConnectCircleCreate(this,this.getDeltaMovement(),new Vec3(this.PositionX,this.PositionY,this.PositionZ),0,0.5,80,
                            ModParticles.EFFECT_MANA.get(), DX,DY,DZ,Count * 0.5);
                    Effect--;
                    Count++;
                }
                while (Range > 0) {
                    Compute.EntityFaceConnectCircleCreate(this,this.getDeltaMovement(),new Vec3(this.PositionX,this.PositionY,this.PositionZ),0,0.5,80,
                            ModParticles.RANGE_MANA.get(), DX,DY,DZ,Count * 0.5);
                    Range--;
                    Count++;
                }
                while (Damage > 0) {
                    Compute.EntityFaceConnectCircleCreate(this,this.getDeltaMovement(),new Vec3(this.PositionX,this.PositionY,this.PositionZ),0,0.5,80,
                            ModParticles.DAMAGE_MANA.get(), DX,DY,DZ,Count * 0.5);
                    Damage--;
                    Count++;
                }
                while (Break > 0) {
                    Compute.EntityFaceConnectCircleCreate(this,this.getDeltaMovement(),new Vec3(this.PositionX,this.PositionY,this.PositionZ),0,0.5,80,
                            ModParticles.BREAKDEFENCE_MANA.get(), DX,DY,DZ,Count * 0.5);
                    Break--;
                    Count++;
                }
                while (Kaze > 0) {
                    Compute.EntityFaceConnectCircleCreate(this,this.getDeltaMovement(),new Vec3(this.PositionX,this.PositionY,this.PositionZ),0,0.5,80,
                            ModParticles.KAZE_MANA.get(), DX,DY,DZ,Count * 0.5);
                    Kaze--;
                    Count++;
                }
                while (Snow > 0) {
                    Compute.EntityFaceConnectCircleCreate(this,this.getDeltaMovement(),new Vec3(this.PositionX,this.PositionY,this.PositionZ),0,0.5,80,
                            ModParticles.SNOW_MANA.get(), DX,DY,DZ,Count * 0.5);
                    Snow--;
                    Count++;
                }
                while (Lightning > 0) {
                    Compute.EntityFaceConnectCircleCreate(this,this.getDeltaMovement(),new Vec3(this.PositionX,this.PositionY,this.PositionZ),0,0.5,80,
                            ModParticles.LIGHTNING_MANA.get(), DX,DY,DZ,Count * 0.5);
                    Lightning--;
                    Count++;
                }
                while (Gather > 0) {
                    Compute.EntityFaceConnectCircleCreate(this,this.getDeltaMovement(),new Vec3(this.PositionX,this.PositionY,this.PositionZ),0,0.5,80,
                            ModParticles.GATHER_MANA.get(), DX,DY,DZ,Count * 0.5);
                    Gather--;
                    Count++;
                }
                for(double i = 0.1 ;i <= 1 ;i += 0.1)
                {
                    Compute.ParticleWITCH(this.PositionX+i*DX,this.PositionY+i*DY,this.PositionZ+i*DZ,level(),0.2,ModParticles.EFFECT_MANA.get());
                }
            }
            else {
                Compute.ParticleWITCH(this.PositionX,this.PositionY,this.PositionZ,level(),0.2,ParticleTypes.WITCH);
                for(double i = 0.1 ;i <= 1 ;i += 0.1)
                {
                    Compute.ParticleWITCH(this.PositionX+i*DX,this.PositionY+i*DY,this.PositionZ+i*DZ,level(),0.2,ParticleTypes.WITCH);
                }
            }
        }
        this.PositionX = this.getX();
        this.PositionY = this.getY();
        this.PositionZ = this.getZ();
    }
}
