package com.Very.very.CoreAttackModule;

import com.Very.very.Customize.Customize;
import com.Very.very.Customize.Players.Lei_yan233.LeiyanBow;
import com.Very.very.Customize.Players.Lei_yan233.LeiyanCurios;
import com.Very.very.Customize.Players.Qi_Fu.QiFuCurios1;
import com.Very.very.Customize.Uniform.BowCurios0;
import com.Very.very.Entity.Entities.Civil.Civil;
import com.Very.very.Events.Instance.IceKnight;
import com.Very.very.Events.WaltzAndModule.AttackEventModule;
import com.Very.very.Series.InstanceSeries.Castle.CastleBow;
import com.Very.very.Series.InstanceSeries.Castle.CastleSwiftArmor;
import com.Very.very.Series.InstanceSeries.Devil.DevilSwiftArmor;
import com.Very.very.Series.InstanceSeries.Moon.Equip.MoonBow;
import com.Very.very.Series.InstanceSeries.Moon.Equip.MoonKnife;
import com.Very.very.Series.InstanceSeries.Moon.MoonCurios;
import com.Very.very.Series.InstanceSeries.Taboo.TabooSwiftArmor;
import com.Very.very.Series.OverWorldSeries.Castle.BeaconBracelet;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MyArrow extends AbstractArrow {

    private Player player;
    private double BaseDamage;
    private boolean WhetherShootByPlayer;
    private boolean IsManaArrow;
    private boolean AdjustOneTime = false;
    private ParticleOptions particleOptions;


    protected MyArrow(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    protected MyArrow(EntityType<? extends AbstractArrow> p_36711_, double p_36712_, double p_36713_, double p_36714_, Level p_36715_) {
        super(p_36711_, p_36712_, p_36713_, p_36714_, p_36715_);
    }

    public MyArrow(EntityType<? extends AbstractArrow> p_36717_, LivingEntity p_36718_, Level level, Player player,
                   double damage, boolean WhetherShootByPlayer) {
        super(p_36717_, p_36718_, level);
        this.player = player;
        this.BaseDamage = damage;
        this.WhetherShootByPlayer = WhetherShootByPlayer;
    }

    public MyArrow(EntityType<? extends AbstractArrow> p_36717_, Level level, Player player, boolean WhetherShootByPlayer) {
        super(p_36717_, player, level);
        this.player = player;
        this.BaseDamage = Compute.PlayerAttributes.PlayerAttackDamage(player);
        this.WhetherShootByPlayer = WhetherShootByPlayer;
    }

    public MyArrow(EntityType<? extends AbstractArrow> p_36717_, Level level, Player player, boolean WhetherShootByPlayer, double rate) {
        super(p_36717_, player, level);
        this.player = player;
        this.BaseDamage = Compute.PlayerAttributes.PlayerAttackDamage(player) * rate;
        this.WhetherShootByPlayer = WhetherShootByPlayer;
    }

    public MyArrow(EntityType<? extends AbstractArrow> p_36717_, LivingEntity p_36718_, Level level,
                   Player player, boolean WhetherShootByPlayer, boolean isManaArrow, double rate) {
        super(p_36717_, p_36718_, level);
        this.player = player;
        this.BaseDamage = Compute.PlayerAttributes.PlayerAttackDamage(player) * rate;
        this.WhetherShootByPlayer = WhetherShootByPlayer;
        this.IsManaArrow = isManaArrow;
        this.AdjustOneTime = isManaArrow;
    }

    public MyArrow(EntityType<? extends AbstractArrow> p_36717_, LivingEntity p_36718_, Level level, Player player,
                   double damage, boolean WhetherShootByPlayer, boolean isManaArrow, ParticleOptions particleOptions) {
        super(p_36717_, p_36718_, level);
        this.player = player;
        this.BaseDamage = damage;
        this.WhetherShootByPlayer = WhetherShootByPlayer;
        this.IsManaArrow = isManaArrow;
        this.AdjustOneTime = isManaArrow;
        this.particleOptions = particleOptions;
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return MinecartItem.byId(0).getDefaultInstance();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        CauseDamage(this,result.getEntity(),BaseDamage);
        this.remove(RemovalReason.KILLED);
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_) {
        if (!this.level().isClientSide && player != null) {
            CompoundTag data = player.getPersistentData();
            if (WhetherShootByPlayer) AttackEventModule.BowSkill6Attack(data, player, false);
        }
        this.remove(RemovalReason.KILLED);
        super.onHitBlock(p_36755_);
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide && player != null) {
            if (AdjustOneTime && this.tickCount > 4) {
                List<Mob> mobList = this.level().getEntitiesOfClass(Mob.class, AABB.ofSize(this.getPosition(1),20,20,20));
                mobList.removeIf(mob -> mob instanceof Civil);
                if (mobList.size() > 0) {
                    Mob mob = null;
                    double length = 50;
                    for (Mob mob1 : mobList) {
                        if (mob1.isAlive() && mob1.position().distanceTo(this.position()) < length) {
                            mob = mob1;
                            length = mob1.position().distanceTo(this.position());
                        }
                    }
                    if (mob != null) {
                        Vec3 Delta = mob.getPosition(1).add(0,1,0).subtract(this.getPosition(1));
                        if (Delta.length() > 0.1) {
/*
                            ParticleProvider.LineParticle(level(),20,this.position(),mob.position().add(0,1,0),particleOptions);
*/
                            this.setDeltaMovement(Delta.normalize().scale(3));
                            ProjectileUtil.rotateTowardsMovement(this,1);
                            AdjustOneTime = false;
                        }
                    }
                }
            }
        }
        if (this.tickCount >= 100 || this.getDeltaMovement().length() <= 0.05) {
            this.remove(RemovalReason.KILLED);
            if (!this.level().isClientSide && player != null) {
                CompoundTag data = player.getPersistentData();
                if (WhetherShootByPlayer) AttackEventModule.BowSkill6Attack(data, player, false);
            }
        }
        if (!this.level().isClientSide && player != null && this.distanceTo(player) > 60) {
            this.remove(RemovalReason.KILLED);
        }
        super.tick();
    }

    @Override
    public boolean shouldRender(double p_20296_, double p_20297_, double p_20298_) {
        return super.shouldRender(p_20296_, p_20297_, p_20298_);
    }

    public static void CauseDamage(MyArrow myArrow, Entity entity, double BaseDamage) {
        Player player = myArrow.player;
        CompoundTag data = player.getPersistentData();
        CompoundTag dataArrow = myArrow.getPersistentData();
        Level level = player.level();
        Random r = new Random();
        boolean WhetherShootByPlayer = myArrow.WhetherShootByPlayer;
        boolean IsManaArrow = myArrow.IsManaArrow;
        double DefencePenetration = Compute.PlayerAttributes.PlayerDefencePenetration(player);
        double DefencePenetration0 = Compute.PlayerAttributes.PlayerDefencePenetration0(player);
        double CritRate = Compute.PlayerAttributes.PlayerCritRate(player);
        double CritDamage = Compute.PlayerAttributes.PlayerCritDamage(player);
        ItemStack mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (entity instanceof Mob monster && !level.isClientSide && myArrow.IsManaArrow && LeiyanCurios.IsOn(player) && WhetherShootByPlayer) {
            LeiyanCurios.CountAdd(player);
        }
        if (entity instanceof Mob monster && !level.isClientSide) {
            Utils.PlayerFireWorkFightCoolDown.put(player,player.getServer().getTickCount() + 200);
            int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
            double Defence = Compute.MonsterDefence(monster);
            if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 0)
                BaseDamage += player.getAttribute(Attributes.MAX_HEALTH).getValue() * 0.1;

            BaseDamage *= BowCurios0.BaseDamageEnhance(player);

            double Damage;
            double ExDamage = 0;
            double DamageIgnoreDefence = 0;
            double DamageEnhance = 0;

            AttackEventModule.ArrowSnowRune3(player, data, monster, level); //
            AttackEventModule.BowSkill6Attack(data, player, true); // 连续命中目标的箭矢，将会提供至多30%攻击力加成
            AttackEventModule.SnowArmorEffect(player, monster); //冰川增幅
            AttackEventModule.ShowDickerBow(player);
            AttackEventModule.CrushArrow(player,monster);

            ExDamage += AttackEventModule.BowSkill12(data, player, BaseDamage); // 热能注入（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心范围内造成100%伤害）
            ExDamage += AttackEventModule.BowSkill14(data, player, BaseDamage); // 全神贯注（完成一次攻击后，基于时间在下次攻击额外造成至多500%的额外伤害，在攻击间隔时间5s后达最大值）
            ExDamage += AttackEventModule.ShowDickerBow(player,monster);
            ExDamage += AttackEventModule.BlackForest(player, monster); // 灵魂收割者主动
            ExDamage += TabooSwiftArmor.ExDamage(player);
            if (WhetherShootByPlayer) ExDamage += AttackEventModule.GuangYiArrowExDamage(player,monster);
            if (IsManaArrow) ExDamage += LeiyanCurios.ExXpStrengthDamage(player);
            ExDamage += LeiyanBow.ExDamage(player,monster);

            DamageIgnoreDefence += AttackEventModule.BowSkill0(data, BaseDamage); // 弓术热诚（你的箭矢额外造成攻击力1%的真实伤害）
            DamageIgnoreDefence += AttackEventModule.SeaSword(player,  monster); //灵魂救赎者主动
            DamageIgnoreDefence += AttackEventModule.VolcanoRune2(player); //火山符石-熔岩强击
            DamageIgnoreDefence += MoonCurios.Passive(player,monster); // 朔望馈赠
            DamageIgnoreDefence += CastleSwiftArmor.ExIgnoreDefenceDamage(player);

            DamageEnhance += AttackEventModule.BowSkill3(data, player, monster); // 习惯获取（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在3次攻击后达到最大值）
            DamageEnhance += AttackEventModule.NetherArmorEffect(player, monster); // 下界套装
            DamageEnhance += AttackEventModule.NetherBow(player, monster); // 夸塔兹长弓
            DamageEnhance += Compute.PlayerCommonDamageUpOrDown(player,monster);
            DamageEnhance += Compute.PlayerAttackDamageEnhance(player);
            DamageEnhance += IceKnight.IceKnightHealthAttackDamageFix(monster); // 冰霜骑士伤害修正
            DamageEnhance += AttackEventModule.GuangYiArrowDistanceDamageEnhance(player,monster);

            double NormalAttackDamageEnhance = 0;
            NormalAttackDamageEnhance += Compute.PlayerNormalBowAttackDamageEnhance(player);

            boolean CritFlag = false;
            if (r.nextDouble(1) < CritRate) {
                CritFlag = true;
                AttackEventModule.BowSkill5(data, player); // 狂暴（造成暴击后，提升1%攻击力，持续5s）
                AttackEventModule.IceBow(player,monster); // 冬
                Damage = BaseDamage * (1 + CritDamage);
                data.putBoolean(StringUtils.DamageTypes.Crit, true);
                QiFuCurios1.Passive1(player);
            } else Damage = BaseDamage;
            //
            ExDamage *= (1 + DamageEnhance);
            DamageIgnoreDefence *= (1 + DamageEnhance);
            Damage *= (1 + DamageEnhance) * (1 + NormalAttackDamageEnhance);
            Damage += ExDamage;
            //
            Damage *= (1 + Compute.PlayerFinalDamageEnhance(player,monster));
            DamageIgnoreDefence *= (1 + Compute.PlayerFinalDamageEnhance(player,monster));
            //
            Damage *= Compute.DefenceDamageDecreaseRate(Defence, DefencePenetration, DefencePenetration0);
            //
            Compute.Damage.DirectDamageToMob(player, entity, Damage + DamageIgnoreDefence);
            //
            if (CritFlag) Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", Damage + DamageIgnoreDefence)).withStyle(Utils.styleOfPower));
            else Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", Damage + DamageIgnoreDefence)).withStyle(ChatFormatting.YELLOW));
            Compute.DamageActionBarPacketSend(player,Damage,DamageIgnoreDefence,false,CritFlag);
            //
            AttackEventModule.BowPositiveEffect(mainHandItem, player, data, TickCount);
            AttackEventModule.BowSkill3Attack(data, player, monster); // 习惯获取（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在3次攻击后达到最大值）
            AttackEventModule.BowSkill12Attack(data, player); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心范围内造成100%伤害）
            AttackEventModule.BowSkill13Attack(data, player, monster); // 箭雨（每过10s，下次攻击将在箭矢落点造成箭雨）
            AttackEventModule.WitherBow(player, mainHandItem, data, TickCount); // 凋零长弓
            AttackEventModule.SakuraBowDefenceUp(player); // 妖弓-樱
            AttackEventModule.SakuraBowHealth(player); // 妖弓-樱
            AttackEventModule.SakuraBow(player); // 妖弓-樱
            AttackEventModule.SeaBow(player, monster); // 灵晶锻弓
            AttackEventModule.MineBow(player, monster); // 精钢锻弓
            AttackEventModule.EndRune2(player,monster); // 末地符石
            AttackEventModule.ManaKnifeHealthRecover(player); // 猎魔者小刀
            AttackEvent.SpringSwiftArmor(player,monster);
            Compute.ChargingModule(data,player);
            BeaconBracelet.Passive(player,monster); // 烽火手镯
            if (WhetherShootByPlayer) MoonBow.Passive(player,monster);
            Customize.ArrowNormalAttackEffect(player,monster,Damage,WhetherShootByPlayer,IsManaArrow);
            DevilSwiftArmor.DevilSwiftArmorPassive(player,monster); // 猎魔者足具
            if (WhetherShootByPlayer) MoonKnife.MoonKnife(player,monster);
            if (WhetherShootByPlayer) CastleBow.NormalAttack(player,monster,Damage);
        }
        if (entity instanceof Player hurter) {
            AttackEventModule.ArrowSnowRune3(player, data, hurter, level);
            AttackEventModule.BowSkill6Attack(data, player, true); // 连续命中目标的箭矢，将会提供至多30%攻击力加成
            int TickCount = Objects.requireNonNull(hurter.getServer()).getTickCount();

            double Defence = Compute.PlayerAttributes.PlayerDefence(hurter);
            double damage;
            double ExDamage = 0;
            double DamageIgnoreDefence = 0;
            double DamageEnhance = 0;

            ExDamage += AttackEventModule.BowSkill12(data, player, BaseDamage); // 热能注入（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心范围内造成100%伤害）
            ExDamage += AttackEventModule.BowSkill14(data, player, BaseDamage); // 全神贯注（完成一次攻击后，基于时间在下次攻击额外造成至多500%的额外伤害，在攻击间隔时间5s后达最大值）

            DamageIgnoreDefence += AttackEventModule.BowSkill0(data, BaseDamage); // 弓术热诚（你的箭矢额外造成攻击力1%的真实伤害）

            DamageEnhance += AttackEventModule.BowSkill3(data, player, hurter); // 习惯获取（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在3次攻击后达到最大值）
            DamageEnhance += Compute.BowSkillLevelGet(data, 4) * 0.03; // 专注训练（额外造成3%的伤害，额外受到1.5%的伤害）

            if (Defence == 0)
                Defence = (double) Objects.requireNonNull(hurter.getAttribute(Attributes.ARMOR)).getValue();
            if (r.nextDouble(1.0d) < CritRate) {
                AttackEventModule.BowSkill5(data, player); // 狂暴（造成暴击后，提升1%攻击力，持续5s）
                if (DefencePenetration0 >= Defence) damage = BaseDamage * (1.0d + CritDamage);
                else
                    damage = BaseDamage * (1.0d + CritDamage) * Compute.DefenceDamageDecreaseRate(Defence, DefencePenetration, DefencePenetration0);
                data.putBoolean(StringUtils.DamageTypes.Crit, true);
            } else {
                if (DefencePenetration0 >= Defence) damage = BaseDamage;
                else damage = BaseDamage * Compute.DefenceDamageDecreaseRate(Defence, DefencePenetration, DefencePenetration0);
            }
            damage *= AttackEventModule.NetherBowDamageEnhance(myArrow, dataArrow, hurter);
            damage += ExDamage;
            damage *= (1 + DamageEnhance);
            DamageIgnoreDefence *= (1 + DamageEnhance);
            Compute.Damage.DirectDamageToPlayer(player, hurter, (damage + DamageIgnoreDefence) * 0.1f);
            AttackEventModule.BowPositiveEffect(mainHandItem, player, data, TickCount);
            AttackEventModule.BowSkill3Attack(data, player, hurter); // 习惯获取（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在3次攻击后达到最大值）
            AttackEventModule.BowSkill12Attack(data, player); // 热能注入（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心范围内造成100%伤害）
            AttackEventModule.BowSkill13Attack(data, player, hurter); // 箭雨（每过10s，下次攻击将在箭矢落点造成箭雨）
            AttackEventModule.WitherBow(player, mainHandItem, data, TickCount);
        }
    }
}

