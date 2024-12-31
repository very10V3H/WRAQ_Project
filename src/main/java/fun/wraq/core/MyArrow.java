package fun.wraq.core;

import fun.wraq.commands.stable.players.DebugCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.DamageInfluence;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.attribute.SameTypeModule;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.*;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.uniform.bow.BowCurios0;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.events.modules.AttackEventModule;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.skill.BowSkillTree;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.CastleBow;
import fun.wraq.series.instance.series.castle.CastleSwiftArmor;
import fun.wraq.series.instance.series.moon.Equip.MoonKnife;
import fun.wraq.series.instance.series.moon.MoonCurios;
import fun.wraq.series.instance.series.taboo.TabooSwiftArmor;
import fun.wraq.series.overworld.chapter7.BoneImpKnife;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
                                ParticleProvider.createLineParticle(level(), 20, this.position(), mob.position().add(0, 1, 0), particleOptions);
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

    public static void causeDamage(MyArrow myArrow, Entity entity, double rate) {
        if (myArrow.player == null) return;
        Player player = myArrow.player;
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        boolean shootByPlayer = myArrow.WhetherShootByPlayer;
        double defencePenetration = PlayerAttributes.defencePenetration(player);
        double defencePenetration0 = PlayerAttributes.defencePenetration0(player);
        double critRate = PlayerAttributes.critRate(player);
        double critDamage = PlayerAttributes.critDamage(player);
        ItemStack mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (entity instanceof Mob monster && !level.isClientSide) {
            if (SpecialEffectOnPlayer.inBlind(player)) {
                Compute.summonValueItemEntity(monster.level(), player, monster,
                        Te.s("未命中", CustomStyle.styleOfEnd), 0);
                return;
            }
            Utils.PlayerFireWorkFightCoolDown.put(player, Tick.get() + 200);
            double defence = MobAttributes.defence(monster);

            if (shootByPlayer) {
                rate += DamageInfluence.getPlayerNormalAttackBaseDamageEnhance(player, 1);
                rate += BowCurios0.BaseDamageEnhance(player);
            }

            rate += StableTierAttributeModifier
                    .getModifierValue(player, StableTierAttributeModifier.baseArrowDamageEnhanceRate);

            double baseDamage = PlayerAttributes.attackDamage(player) * rate;
            double damage;
            double exDamage = 0;
            double trueDamage = 0;
            double damageEnhance = 0;

            AttackEventModule.BowSkill6Attack(data, player, true); // 连续命中目标的箭矢，将会提供至多30%攻击力加成
            AttackEventModule.SnowArmorEffect(player, monster); //冰川增幅

            exDamage += AttackEventModule.BowSkill12(data, player, baseDamage); // 热能注入（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心范围内造成100%伤害）
            exDamage += AttackEventModule.BlackForest(player, monster); // 灵魂收割者主动
            exDamage += TabooSwiftArmor.ExDamage(player);

            trueDamage += AttackEventModule.BowSkill0(data, baseDamage); // 弓术热诚（你的箭矢额外造成攻击力1%的真实伤害）
            trueDamage += AttackEventModule.SeaSword(player, monster); //灵魂救赎者主动
            trueDamage += MoonCurios.Passive(player, monster); // 朔望馈赠
            trueDamage += CastleSwiftArmor.ExIgnoreDefenceDamage(player);

            damageEnhance += AttackEventModule.BowSkill3(data, player, monster); // 习惯获取（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在3次攻击后达到最大值）
            damageEnhance += AttackEventModule.NetherBow(player, monster); // 夸塔兹长弓
            damageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster);
            damageEnhance += DamageInfluence.getPlayerAttackDamageEnhance(player, monster);

            double NormalAttackDamageEnhance = 0;
            NormalAttackDamageEnhance += DamageInfluence.getPlayerNormalBowAttackDamageEnhance(player);

            boolean critFlag = false;
            if (BoneImpKnife.passive(player, monster)) critRate = 1;
            if (RandomUtils.nextDouble(0, 1) < critRate) {
                critFlag = true;
                AttackEventModule.BowSkill5(data, player); // 狂暴（造成暴击后，提升1%攻击力，持续5s）
                damage = baseDamage * (1 + critDamage);
                data.putBoolean(StringUtils.DamageTypes.Crit, true);
                OnCritHitEffectMainHandWeapon.critHit(player, monster);
            } else damage = baseDamage;

            if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false)) {
                player.sendSystemMessage(Component.literal("---NormalBowAttack---"));
                player.sendSystemMessage(Component.literal("baseDamage : " + baseDamage));
                player.sendSystemMessage(Component.literal("ExDamage : " + exDamage));
                player.sendSystemMessage(Component.literal("DamageIgnoreDefence : " + trueDamage));
            }

            //
            exDamage *= (1 + damageEnhance);
            trueDamage *= (1 + damageEnhance);
            damage *= (1 + damageEnhance) * (1 + NormalAttackDamageEnhance);
            damage += exDamage;
            //
            damage *= (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
            trueDamage *= (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
            //
            damage *= Damage.defenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0);
            // total damage
            damage *= DamageInfluence.getPlayerTotalDamageRate(player);
            trueDamage *= DamageInfluence.getPlayerTotalDamageRate(player);
            // livingEntity control
            damage *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
            trueDamage *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
            // 至此 关于基本的计算已结束 下方是最终乘区的计算
            trueDamage += BoneImpKnife.exTrueDamage(player, monster) * damage;

            // 元素
            double ElementDamageEnhance = 0;
            double ElementDamageEffect = 1;
            String elementType = "";
            if (shootByPlayer) {
                ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
                Element.Unit playerUnit = Element.entityElementUnit.getOrDefault(player, new Element.Unit(Element.life, 0));
                elementType = playerUnit.type();
                if (playerUnit.value() > 0) {
                    ElementDamageEffect = Element.ElementEffectAddToEntity(player, monster, playerUnit.type(), playerUnit.value(), false, damage + trueDamage);
                    Element.entityElementUnit.put(player, new Element.Unit(Element.life, 0));
                }
                EnhanceNormalAttackModifier.onHitEffect(player, monster, 1);
            }

            double elementDamage = (damage + trueDamage) * ((1 + ElementDamageEnhance) * ElementDamageEffect - 1);

            damage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
            trueDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect;

            Damage.beforeCauseDamage(player, monster, damage + trueDamage);
            Damage.causeDirectDamageToMob(player, entity, damage + trueDamage);

            if (critFlag)
                Compute.summonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage + trueDamage)).withStyle(CustomStyle.styleOfPower), 0);
            else
                Compute.summonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage + trueDamage)).withStyle(ChatFormatting.YELLOW), 0);
            if (elementDamage != 0 && !elementType.isEmpty())
                Compute.damageActionBarPacketSend(player, damage, trueDamage, false, critFlag, elementType, elementDamage);
            else Compute.damageActionBarPacketSend(player, damage, trueDamage, false, critFlag);

            // Health steal
            Compute.healByHealthSteal(player, damage * PlayerAttributes.healthSteal(player));

            AttackEventModule.BowSkill3Attack(data, player, monster); // 习惯获取（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在3次攻击后达到最大值）
            AttackEventModule.BowSkill12Attack(data, player); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心范围内造成100%伤害）
            AttackEventModule.ManaKnifeHealthRecover(player); // 猎魔者小刀
            AttackEvent.SpringSwiftArmor(player, monster);
            Compute.ChargingModule(data, player);
            if (shootByPlayer) {
                MoonKnife.MoonKnife(player, monster);
                CastleBow.NormalAttack(player, monster, damage);
                Compute.AdditionEffects(player, monster, damage + trueDamage, 0);
                OnHitEffectEquip.hit(player, monster);
                OnHitEffectCurios.hit(player, monster);
                OnArrowHitEffectCurios.hit(player, monster);
                SameTypeModule.onNormalAttackHitMob(player, monster, 0, damage + trueDamage);
                BowSkillTree.skillIndex13(player);
            }
            if (myArrow.mainShoot) {
                OnHitEffectPassiveEquip.hit(player, monster);
            }
            if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false)) {
                player.sendSystemMessage(Component.literal("NormalAttackDamageEnhance : " + NormalAttackDamageEnhance));
                player.sendSystemMessage(Component.literal("DamageEnhance : " + damageEnhance));
                player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageInfluence.getPlayerFinalDamageEnhance(player, monster)));
                player.sendSystemMessage(Component.literal("Damage.defenceDamageDecreaseRate(Defence, DefencePenetration, DefencePenetration0) : " + Damage.defenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0)));
                player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
                player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
                player.sendSystemMessage(Component.literal("Damage + DamageIgnoreDefence : " + (damage + trueDamage)));
                player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
            }
        }
    }
}

