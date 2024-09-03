package com.very.wraq.core;

import com.very.wraq.commands.stable.players.DebugCommand;
import com.very.wraq.common.Compute;
import com.very.wraq.common.attribute.DamageInfluence;
import com.very.wraq.common.attribute.MobAttributes;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.attribute.SameTypeModule;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.customized.uniform.bow.BowCurios0;
import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.events.modules.AttackEventModule;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.OnHitEffectCurios;
import com.very.wraq.projectiles.OnHitEffectMainHandWeapon;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.series.castle.CastleBow;
import com.very.wraq.series.instance.series.castle.CastleSwiftArmor;
import com.very.wraq.series.instance.series.devil.DevilSwiftArmor;
import com.very.wraq.series.instance.series.moon.Equip.MoonBow;
import com.very.wraq.series.instance.series.moon.Equip.MoonKnife;
import com.very.wraq.series.instance.series.moon.MoonCurios;
import com.very.wraq.series.instance.series.taboo.TabooSwiftArmor;
import com.very.wraq.series.newrunes.chapter1.VolcanoNewRune;
import com.very.wraq.series.overworld.castle.BeaconBracelet;
import com.very.wraq.series.overworld.chapter7.BoneImpKnife;
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

    public Player player;
    public double BaseDamage;
    private boolean WhetherShootByPlayer;
    private boolean IsManaArrow;
    private boolean AdjustOneTime = false;
    private ParticleOptions particleOptions;
    public boolean mainShoot = true;

    protected MyArrow(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    protected MyArrow(EntityType<? extends AbstractArrow> p_36711_, double p_36712_, double p_36713_, double p_36714_, Level p_36715_) {
        super(p_36711_, p_36712_, p_36713_, p_36714_, p_36715_);
    }

    public MyArrow(EntityType<? extends AbstractArrow> p_36717_, Level level, Player player, double damage, boolean WhetherShootByPlayer) {
        super(p_36717_, player, level);
        this.player = player;
        this.BaseDamage = damage;
        this.WhetherShootByPlayer = WhetherShootByPlayer;
    }

    public MyArrow(EntityType<? extends AbstractArrow> p_36717_, Level level, Player player, boolean WhetherShootByPlayer) {
        super(p_36717_, player, level);
        this.player = player;
        this.BaseDamage = PlayerAttributes.attackDamage(player);
        this.WhetherShootByPlayer = WhetherShootByPlayer;
    }

    public MyArrow(EntityType<? extends AbstractArrow> p_36717_, Level level, Player player, boolean WhetherShootByPlayer, double rate) {
        super(p_36717_, player, level);
        this.player = player;
        this.BaseDamage = PlayerAttributes.attackDamage(player) * rate;
        this.WhetherShootByPlayer = WhetherShootByPlayer;
    }

    public MyArrow(EntityType<? extends AbstractArrow> p_36717_, Level level, Player player,
                   boolean WhetherShootByPlayer, double rate, boolean mainShoot) {
        super(p_36717_, player, level);
        this.player = player;
        this.BaseDamage = PlayerAttributes.attackDamage(player) * rate;
        this.WhetherShootByPlayer = WhetherShootByPlayer;
        this.mainShoot = mainShoot;
    }

    public MyArrow(EntityType<? extends AbstractArrow> p_36717_, LivingEntity p_36718_, Level level,
                   Player player, boolean WhetherShootByPlayer, boolean isManaArrow, double rate) {
        super(p_36717_, p_36718_, level);
        this.player = player;
        this.BaseDamage = PlayerAttributes.attackDamage(player) * rate;
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
        this.playSound(this.getDefaultHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.discard();
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
                List<Mob> mobList = this.level().getEntitiesOfClass(Mob.class, AABB.ofSize(this.getPosition(1), 20, 20, 20));
                mobList.removeIf(mob -> mob instanceof Civil);
                if (!mobList.isEmpty()) {
                    Mob mob = null;
                    double length = 50;
                    for (Mob mob1 : mobList) {
                        if (mob1.isAlive() && mob1.position().distanceTo(this.position()) < length) {
                            mob = mob1;
                            length = mob1.position().distanceTo(this.position());
                        }
                    }
                    if (mob != null) {
                        Vec3 Delta = mob.getPosition(1).add(0, 1, 0).subtract(this.getPosition(1));
                        if (Delta.length() > 0.1) {
                            if (particleOptions != null)
                                ParticleProvider.LineParticle(level(), 20, this.position(), mob.position().add(0, 1, 0), particleOptions);
                            this.setDeltaMovement(Delta.normalize().scale(3));
                            ProjectileUtil.rotateTowardsMovement(this, 1);
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

    public static void CauseDamage(MyArrow myArrow, Entity entity, double baseDamage) {
        if (myArrow.player == null) return;
        Player player = myArrow.player;
        CompoundTag data = player.getPersistentData();
        CompoundTag dataArrow = myArrow.getPersistentData();
        Level level = player.level();
        Random r = new Random();
        boolean shootByPlayer = myArrow.WhetherShootByPlayer;
        double defencePenetration = PlayerAttributes.defencePenetration(player);
        double defencePenetration0 = PlayerAttributes.defencePenetration0(player);
        double critRate = PlayerAttributes.critRate(player);
        double critDamage = PlayerAttributes.critDamage(player);
        ItemStack mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (entity instanceof Mob monster && !level.isClientSide) {
            Utils.PlayerFireWorkFightCoolDown.put(player, player.getServer().getTickCount() + 200);
            int tickCount = Objects.requireNonNull(player.getServer()).getTickCount();
            double defence = MobAttributes.defence(monster);

            baseDamage *= DamageInfluence.getPlayerNormalAttackBaseRate(player);
            baseDamage *= BowCurios0.BaseDamageEnhance(player);
            if (shootByPlayer) baseDamage *= VolcanoNewRune.attackEnhance(player);

            double damage;
            double exDamage = 0;
            double damageIgnoreDefence = 0;
            double damageEnhance = 0;

            AttackEventModule.BowSkill6Attack(data, player, true); // 连续命中目标的箭矢，将会提供至多30%攻击力加成
            AttackEventModule.SnowArmorEffect(player, monster); //冰川增幅

            exDamage += AttackEventModule.BowSkill12(data, player, baseDamage); // 热能注入（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心范围内造成100%伤害）
            exDamage += AttackEventModule.BowSkill14(data, player, baseDamage); // 全神贯注（完成一次攻击后，基于时间在下次攻击额外造成至多500%的额外伤害，在攻击间隔时间5s后达最大值）
            exDamage += AttackEventModule.BlackForest(player, monster); // 灵魂收割者主动
            exDamage += TabooSwiftArmor.ExDamage(player);

            damageIgnoreDefence += AttackEventModule.BowSkill0(data, baseDamage); // 弓术热诚（你的箭矢额外造成攻击力1%的真实伤害）
            damageIgnoreDefence += AttackEventModule.SeaSword(player, monster); //灵魂救赎者主动
            damageIgnoreDefence += MoonCurios.Passive(player, monster); // 朔望馈赠
            damageIgnoreDefence += CastleSwiftArmor.ExIgnoreDefenceDamage(player);

            damageEnhance += AttackEventModule.BowSkill3(data, player, monster); // 习惯获取（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在3次攻击后达到最大值）
            damageEnhance += AttackEventModule.NetherArmorEffect(player, monster); // 下界套装
            damageEnhance += AttackEventModule.NetherBow(player, monster); // 夸塔兹长弓
            damageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster);
            damageEnhance += DamageInfluence.getPlayerAttackDamageEnhance(player, monster);

            double NormalAttackDamageEnhance = 0;
            NormalAttackDamageEnhance += DamageInfluence.getPlayerNormalBowAttackDamageEnhance(player);

            boolean critFlag = false;
            if (BoneImpKnife.passive(player, monster)) critRate = 1;
            if (r.nextDouble(1) < critRate) {
                critFlag = true;
                AttackEventModule.BowSkill5(data, player); // 狂暴（造成暴击后，提升1%攻击力，持续5s）
                AttackEventModule.IceBow(player, monster); // 冬
                damage = baseDamage * (1 + critDamage);
                data.putBoolean(StringUtils.DamageTypes.Crit, true);
            } else damage = baseDamage;

            if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false)) {
                player.sendSystemMessage(Component.literal("---NormalBowAttack---"));
                player.sendSystemMessage(Component.literal("Damage : " + damage));
                player.sendSystemMessage(Component.literal("ExDamage : " + exDamage));
                player.sendSystemMessage(Component.literal("DamageIgnoreDefence : " + damageIgnoreDefence));
            }

            //
            exDamage *= (1 + damageEnhance);
            damageIgnoreDefence *= (1 + damageEnhance);
            damage *= (1 + damageEnhance) * (1 + NormalAttackDamageEnhance);
            damage += exDamage;
            //
            damage *= (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
            damageIgnoreDefence *= (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
            //
            damage *= Compute.defenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0);
            // total damage
            damage *= DamageInfluence.getPlayerTotalDamageRate(player);
            damageIgnoreDefence *= DamageInfluence.getPlayerTotalDamageRate(player);
            // mob control
            damage *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
            damageIgnoreDefence *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
            // 至此 关于基本的计算已结束 下方是最终乘区的计算
            damageIgnoreDefence += BoneImpKnife.exDamageIgnoreDefence(player, monster) * damage;

            // 元素
            double ElementDamageEnhance = 0;
            double ElementDamageEffect = 1;
            String elementType = "";
            if (shootByPlayer) {
                ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
                Element.Unit playerUnit = Element.entityElementUnit.getOrDefault(player.getId(), new Element.Unit(Element.life, 0));
                elementType = playerUnit.type();
                if (playerUnit.value() > 0) {
                    ElementDamageEffect = Element.ElementEffectAddToEntity(player, monster, playerUnit.type(), playerUnit.value(), false, damage + damageIgnoreDefence);
                    Element.entityElementUnit.put(player.getId(), new Element.Unit(Element.life, 0));
                }
            }

            double elementDamage = (damage + damageIgnoreDefence) * ((1 + ElementDamageEnhance) * ElementDamageEffect - 1);

            damage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
            damageIgnoreDefence *= (1 + ElementDamageEnhance) * ElementDamageEffect;

            Compute.Damage.DirectDamageToMob(player, entity, damage + damageIgnoreDefence);

            if (critFlag)
                Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage + damageIgnoreDefence)).withStyle(CustomStyle.styleOfPower), 0);
            else
                Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage + damageIgnoreDefence)).withStyle(ChatFormatting.YELLOW), 0);
            if (elementDamage != 0 && !elementType.isEmpty())
                Compute.damageActionBarPacketSend(player, damage, damageIgnoreDefence, false, critFlag, elementType, elementDamage);
            else Compute.damageActionBarPacketSend(player, damage, damageIgnoreDefence, false, critFlag);

            // Health steal
            Compute.healByHealthSteal(player, damage * PlayerAttributes.healthSteal(player));

            AttackEventModule.BowPositiveEffect(mainHandItem, player, data, tickCount);
            AttackEventModule.BowSkill3Attack(data, player, monster); // 习惯获取（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在3次攻击后达到最大值）
            AttackEventModule.BowSkill12Attack(data, player); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心范围内造成100%伤害）
            AttackEventModule.BowSkill13Attack(data, player, monster); // 箭雨（每过10s，下次攻击将在箭矢落点造成箭雨）
            AttackEventModule.SakuraBowDefenceUp(player); // 妖弓-樱
            AttackEventModule.SakuraBowHealth(player); // 妖弓-樱
            AttackEventModule.SakuraBow(player); // 妖弓-樱
            AttackEventModule.ManaKnifeHealthRecover(player); // 猎魔者小刀
            AttackEvent.SpringSwiftArmor(player, monster);
            Compute.ChargingModule(data, player);
            BeaconBracelet.Passive(player, monster); // 烽火手镯
            DevilSwiftArmor.DevilSwiftArmorPassive(player, monster); // 猎魔者足具
            if (shootByPlayer) {
                MoonBow.Passive(player, monster);
                MoonKnife.MoonKnife(player, monster);
                CastleBow.NormalAttack(player, monster, damage);
                Compute.AdditionEffects(player, monster, damage + damageIgnoreDefence, 0);
                if (mainHandItem.getItem() instanceof OnHitEffectMainHandWeapon onHitEffectMainHandWeapon)
                    onHitEffectMainHandWeapon.onHit(player, monster);
                SameTypeModule.onNormalAttackHitMob(player, monster, 0, damage + damageIgnoreDefence);
            }
            if (myArrow.mainShoot) {
                OnHitEffectCurios.hit(player, monster);
            }
            if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false)) {
                player.sendSystemMessage(Component.literal("NormalAttackDamageEnhance : " + NormalAttackDamageEnhance));
                player.sendSystemMessage(Component.literal("DamageEnhance : " + damageEnhance));
                player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageInfluence.getPlayerFinalDamageEnhance(player, monster)));
                player.sendSystemMessage(Component.literal("Compute.DefenceDamageDecreaseRate(Defence, DefencePenetration, DefencePenetration0) : " + Compute.defenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0)));
                player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
                player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
                player.sendSystemMessage(Component.literal("Damage + DamageIgnoreDefence : " + (damage + damageIgnoreDefence)));
                player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
            }
        }
        if (entity instanceof Player hurter) {
            AttackEventModule.BowSkill6Attack(data, player, true); // 连续命中目标的箭矢，将会提供至多30%攻击力加成
            int TickCount = Objects.requireNonNull(hurter.getServer()).getTickCount();

            double Defence = PlayerAttributes.defence(hurter);
            double damage;
            double ExDamage = 0;
            double DamageIgnoreDefence = 0;
            double DamageEnhance = 0;

            ExDamage += AttackEventModule.BowSkill12(data, player, baseDamage); // 热能注入（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心范围内造成100%伤害）
            ExDamage += AttackEventModule.BowSkill14(data, player, baseDamage); // 全神贯注（完成一次攻击后，基于时间在下次攻击额外造成至多500%的额外伤害，在攻击间隔时间5s后达最大值）

            DamageIgnoreDefence += AttackEventModule.BowSkill0(data, baseDamage); // 弓术热诚（你的箭矢额外造成攻击力1%的真实伤害）

            DamageEnhance += AttackEventModule.BowSkill3(data, player, hurter); // 习惯获取（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在3次攻击后达到最大值）
            DamageEnhance += Compute.BowSkillLevelGet(data, 4) * 0.03; // 专注训练（额外造成3%的伤害，额外受到1.5%的伤害）

            if (Defence == 0)
                Defence = (double) Objects.requireNonNull(hurter.getAttribute(Attributes.ARMOR)).getValue();
            if (r.nextDouble(1.0d) < critRate) {
                AttackEventModule.BowSkill5(data, player); // 狂暴（造成暴击后，提升1%攻击力，持续5s）
                if (defencePenetration0 >= Defence) damage = baseDamage * (1.0d + critDamage);
                else
                    damage = baseDamage * (1.0d + critDamage) * Compute.defenceDamageDecreaseRate(Defence, defencePenetration, defencePenetration0);
                data.putBoolean(StringUtils.DamageTypes.Crit, true);
            } else {
                if (defencePenetration0 >= Defence) damage = baseDamage;
                else
                    damage = baseDamage * Compute.defenceDamageDecreaseRate(Defence, defencePenetration, defencePenetration0);
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
        }
    }
}

