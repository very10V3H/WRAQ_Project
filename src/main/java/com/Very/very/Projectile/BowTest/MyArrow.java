package com.Very.very.Projectile.BowTest;

import com.Very.very.Events.WaltzAndModule.AttackEventModule;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

import static java.lang.Math.E;
import static java.lang.Math.log;

public class MyArrow extends AbstractArrow {
    private Player player;
    private float BaseDamage;
    private float CriticalHitRate;
    private float CHitDamage;
    private float BreakDefence;
    private float ExpUp;
    private ItemStack Bow;
    private float BreakDefence0;
    private boolean WhetherShootByPlayer;
    protected MyArrow(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }
    protected MyArrow(EntityType<? extends AbstractArrow> p_36711_, double p_36712_, double p_36713_, double p_36714_, Level p_36715_) {
        super(p_36711_, p_36712_, p_36713_, p_36714_, p_36715_);
    }

    public MyArrow(EntityType<? extends AbstractArrow> p_36717_, LivingEntity p_36718_, Level p_36719_, Player player, float damage, float CriticalHitRate, float CHitDamage, float BreakDefence, float ExpUp, float BreakDefence0 , ItemStack Bow, boolean WhetherShootByPlayer) {
        super(p_36717_, p_36718_, p_36719_);
        this.player = player;
        this.BaseDamage = damage;
        this.CriticalHitRate = CriticalHitRate;
        this.CHitDamage = CHitDamage;
        this.BreakDefence = BreakDefence;
        this.ExpUp = ExpUp;
        this.Bow = Bow;
        this.BreakDefence0 = BreakDefence0;
        this.WhetherShootByPlayer = WhetherShootByPlayer;
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return MinecartItem.byId(0).getDefaultInstance();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        CompoundTag data = player.getPersistentData();
        CompoundTag dataArrow = this.getPersistentData();
        Entity entity = result.getEntity();
        Random r = new Random();
        if(entity instanceof Mob monster && !level().isClientSide)
        {
            int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
            AttackEventModule.ArrowSnowRune3(player,data,monster,level());
            AttackEventModule.BowSkill6Attack(data,player,true); // 连续命中目标的箭矢，将会提供至多30%攻击力加成
            float Defence = Compute.MonsterDefence(monster);
            if(data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 0) BaseDamage += player.getAttribute(Attributes.MAX_HEALTH).getValue()*0.1;

            float damage;
            float ExDamage = 0;
            float DamageIgnoreDefence = 0;
            float DamageEnhance = 0;

            ExDamage += AttackEventModule.BowSkill12(data,player,BaseDamage); // 热能注入（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心范围内造成100%伤害）
            ExDamage += AttackEventModule.BowSkill14(data,player,BaseDamage); // 全神贯注（完成一次攻击后，基于时间在下次攻击额外造成至多500%的额外伤害，在攻击间隔时间5s后达最大值）

            DamageIgnoreDefence += AttackEventModule.BowSkill0(data,BaseDamage); // 弓术热诚（你的箭矢额外造成攻击力1%的真实伤害）

            DamageEnhance += AttackEventModule.BowSkill3(data,player,monster); // 习惯获取（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在3次攻击后达到最大值）
            DamageEnhance += Compute.BowSkillLevelGet(data,4) * 0.03; // 专注训练（额外造成3%的伤害，额外受到1.5%的伤害）
            DamageEnhance += Compute.LevelSuppress(player,monster); // 等级压制

            if (Defence == 0) Defence = (float) Objects.requireNonNull(monster.getAttribute(Attributes.ARMOR)).getValue();
            if (r.nextFloat(1.0F) < CriticalHitRate) {
                AttackEventModule.BowSkill5(data,player); // 狂暴（造成暴击后，提升1%攻击力，持续5s）
                if (BreakDefence0 > Defence) damage = BaseDamage * (1.0F + CHitDamage);
                else damage = BaseDamage * (1.0F + CHitDamage) * Compute.DefenceDamageDecreaseRate(Defence,BreakDefence,BreakDefence0);
                if (Utils.OverWorldLevelIsNight && player.level().equals(player.getServer().getLevel(Level.OVERWORLD))) damage *= 0.8f;
                data.putBoolean(StringUtils.DamageTypes.Crit,true);
            } else {
                if (BreakDefence0 > Defence) damage = BaseDamage;
                else damage = BaseDamage * Compute.DefenceDamageDecreaseRate(Defence,BreakDefence,BreakDefence0);
                if (Utils.OverWorldLevelIsNight && player.level().equals(player.getServer().getLevel(Level.OVERWORLD))) damage *= 0.8f;
            }
            damage *= AttackEventModule.NetherBowDamageEnhance(this,dataArrow,monster);
            damage += ExDamage;
            damage *= (1 + DamageEnhance);
            DamageIgnoreDefence *= (1 + DamageEnhance);
            data.putBoolean(StringUtils.DamageTypes.IsBow,true);
            data.putFloat(StringUtils.DamageTypes.DamageAmount,damage);
            data.putFloat(StringUtils.DamageTypes.DamageIgnoreDefenceAmount,DamageIgnoreDefence);
            entity.hurt(entity.damageSources().playerAttack(player), (damage + DamageIgnoreDefence));
            AttackEventModule.BowPositiveEffect(Bow,player,data,TickCount);
            AttackEventModule.BowSkill3Attack(data,player,monster); // 习惯获取（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在3次攻击后达到最大值）
            AttackEventModule.BowSkill12Attack(data,player); // 热能注入（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心范围内造成100%伤害）
            AttackEventModule.BowSkill13Attack(data,player,monster); // 箭雨（每过10s，下次攻击将在箭矢落点造成箭雨）
        }
        if(entity instanceof Player hurter)
        {
            AttackEventModule.ArrowSnowRune3(player,data,hurter,level());
            AttackEventModule.BowSkill6Attack(data,player,true); // 连续命中目标的箭矢，将会提供至多30%攻击力加成
            int TickCount = Objects.requireNonNull(hurter.getServer()).getTickCount();

            float Defence = Compute.PlayerDefence(hurter);
            float damage;
            float ExDamage = 0;
            float DamageIgnoreDefence = 0;
            float DamageEnhance = 0;

            ExDamage += AttackEventModule.BowSkill12(data,player,BaseDamage); // 热能注入（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心范围内造成100%伤害）
            ExDamage += AttackEventModule.BowSkill14(data,player,BaseDamage); // 全神贯注（完成一次攻击后，基于时间在下次攻击额外造成至多500%的额外伤害，在攻击间隔时间5s后达最大值）

            DamageIgnoreDefence += AttackEventModule.BowSkill0(data,BaseDamage); // 弓术热诚（你的箭矢额外造成攻击力1%的真实伤害）

            DamageEnhance += AttackEventModule.BowSkill3(data,player,hurter); // 习惯获取（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在3次攻击后达到最大值）
            DamageEnhance += Compute.BowSkillLevelGet(data,4) * 0.03; // 专注训练（额外造成3%的伤害，额外受到1.5%的伤害）

            if(Defence == 0) Defence = (float) Objects.requireNonNull(hurter.getAttribute(Attributes.ARMOR)).getValue();
            if (r.nextFloat(1.0F) < CriticalHitRate) {
                AttackEventModule.BowSkill5(data,player); // 狂暴（造成暴击后，提升1%攻击力，持续5s）
                if (BreakDefence0 >= Defence) damage = BaseDamage * (1.0F + CHitDamage);
                else damage = BaseDamage * (1.0F + CHitDamage) * Compute.DefenceDamageDecreaseRate(Defence,BreakDefence,BreakDefence0);
                data.putBoolean(StringUtils.DamageTypes.Crit,true);
            } else {
                if (BreakDefence0 >= Defence) damage = BaseDamage;
                else damage = BaseDamage * Compute.DefenceDamageDecreaseRate(Defence,BreakDefence,BreakDefence0);
            }
            damage *= AttackEventModule.NetherBowDamageEnhance(this,dataArrow,hurter);
            damage += ExDamage;
            damage *= (1 + DamageEnhance);
            DamageIgnoreDefence *= (1 + DamageEnhance);
            data.putBoolean(StringUtils.DamageTypes.IsBow,true);
            data.putFloat(StringUtils.DamageTypes.DamageAmount,damage);
            data.putFloat(StringUtils.DamageTypes.DamageIgnoreDefenceAmount,DamageIgnoreDefence);
            entity.hurt(entity.damageSources().playerAttack(player), (damage + DamageIgnoreDefence) * 0.1f);

            AttackEventModule.BowPositiveEffect(Bow,player,data,TickCount);
            AttackEventModule.BowSkill3Attack(data,player,hurter); // 习惯获取（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在3次攻击后达到最大值）
            AttackEventModule.BowSkill12Attack(data,player); // 热能注入（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心范围内造成100%伤害）
            AttackEventModule.BowSkill13Attack(data,player,hurter); // 箭雨（每过10s，下次攻击将在箭矢落点造成箭雨）
        }
        this.remove(RemovalReason.KILLED);
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_) {
        if (!this.level().isClientSide) {
            CompoundTag data = player.getPersistentData();
            if (WhetherShootByPlayer) AttackEventModule.BowSkill6Attack(data,player,false);
        }
        this.remove(RemovalReason.KILLED);
        super.onHitBlock(p_36755_);
    }
    @Override
    public void tick() {
        if(this.tickCount >= 1200){
            this.remove(RemovalReason.KILLED);
            if (!this.level().isClientSide) {
                CompoundTag data = player.getPersistentData();
                if (WhetherShootByPlayer)  AttackEventModule.BowSkill6Attack(data,player,false);
            }
        }
        super.tick();
    }

    @Override
    public boolean shouldRender(double p_20296_, double p_20297_, double p_20298_) {
        return super.shouldRender(p_20296_, p_20297_, p_20298_);
    }
}

