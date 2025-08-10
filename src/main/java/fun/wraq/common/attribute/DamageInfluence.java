package fun.wraq.common.attribute;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.impl.damage.DamageInfluenceCurios;
import fun.wraq.common.impl.onhit.OnHitDamageInfluenceCurios;
import fun.wraq.common.impl.onhit.OnHitDamageInfluenceEquip;
import fun.wraq.common.impl.withstand.ModifyPlayerWithstandDamageInfluenceCurios;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.core.ManaAttackModule;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.customized.uniform.attack.enhanced.WraqAttackEnhancedUniformCurios;
import fun.wraq.customized.uniform.attack.normal.WraqAttackUniformCurios;
import fun.wraq.customized.uniform.bow.enhanced.WraqBowEnhancedUniformCurios;
import fun.wraq.customized.uniform.bow.normal.WraqBowUniformCurios;
import fun.wraq.customized.uniform.mana.enhanced.WraqManaEnhancedUniformCurios;
import fun.wraq.customized.uniform.mana.normal.WraqManaUniformCurios;
import fun.wraq.events.fight.MonsterAttackEvent;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.instances.element.IceInstance;
import fun.wraq.events.mob.instance.instances.element.MushroomInstance;
import fun.wraq.events.mob.instance.instances.element.WardenInstance;
import fun.wraq.events.modules.AttackEventModule;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.MobEffectAndDamageMethods;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.process.system.cold.ColdSystem;
import fun.wraq.process.system.element.equipAndCurios.fireElement.FireEquip;
import fun.wraq.process.system.estate.EstateUtil;
import fun.wraq.process.system.randomevent.impl.special.SpringMobEvent;
import fun.wraq.process.system.season.MySeason;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.process.system.tower.TowerMob;
import fun.wraq.render.mobEffects.ModEffects;
import fun.wraq.series.comsumable.passive.mixture.MixtureAttack;
import fun.wraq.series.dragon.SilverDragonBloodWeapon;
import fun.wraq.series.events.labourDay.LabourDayIronHoe;
import fun.wraq.series.events.labourDay.LabourDayIronPickaxe;
import fun.wraq.series.gems.passive.impl.GemCommonDamageEnhanceRateModifier;
import fun.wraq.series.gems.passive.impl.GemWithstandDamageRateModifier;
import fun.wraq.series.holy.ice.curio.IceHolyRune;
import fun.wraq.series.holy.ice.curio.IceHolySword;
import fun.wraq.series.instance.series.moon.Equip.MoonArmor;
import fun.wraq.series.instance.series.moon.MoonCurios;
import fun.wraq.series.instance.series.purple.EnhancePurpleIronArmor;
import fun.wraq.series.instance.series.taboo.TabooAttackArmor;
import fun.wraq.series.newrunes.chapter1.MineNewRune;
import fun.wraq.series.newrunes.chapter1.VolcanoNewRune;
import fun.wraq.series.newrunes.chapter2.HuskNewRune;
import fun.wraq.series.newrunes.chapter6.MoonNewRune;
import fun.wraq.series.overworld.chapter7.star.StarArmor;
import fun.wraq.series.overworld.chapter7.vd.VdWeaponCommon;
import fun.wraq.series.overworld.cold.sc4.ColdIronArmor;
import fun.wraq.series.overworld.divine.DivineUtils;
import fun.wraq.series.overworld.divine.equip.boss.DivineKnife;
import fun.wraq.series.overworld.sun.DevilPowerCurio;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class DamageInfluence {
    public static double getPlayerCommonDamageUpOrDown(Player player, Mob monster) {
        double rate = 0;
        rate += levelSuppress(player, monster); // 等级压制
        rate += AttackEventModule.IceArmorDamageEnhance(player, monster); // 雪上覆霜
        rate += MoonArmor.DamageEnhance(player, monster); // 尘月膝
        /*eachTierValue += CastleCurios.DamageEnhance(player, monster); // 随机饰品被动*/
        rate += StarArmor.DamageEnhance(player, monster); // 梦月
        rate += VdWeaponCommon.damageEnhance(player, monster); // vd weapon
        rate += OnHitDamageInfluenceCurios.damageInfluence(player, monster);
        rate += OnHitDamageInfluenceEquip.damageInfluence(player, monster);
        rate += ManaAttackModule.getManaSkill3DamageEnhance(player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
        rate += getPlayerCommonDamageUpOrDown(player);
        rate += EstateUtil.getExCommonDamageRate(player);
        rate += GemCommonDamageEnhanceRateModifier.getCommonDamageEnhanceRate(player, monster);
        return rate;
    }

    public static double getPlayerCommonDamageUpOrDown(Player player) {
        double rate = 0;
        rate += AttackEventModule.SwordSkill5DamageEnhance(player); // 双刃剑
        rate += AttackEventModule.ManaSkill5DamageEnhance(player); // 法术专注
        rate += FireEquip.DamageEnhance(player); // 炽焰元素武器
        rate += MineNewRune.damageEnhance(player);
        rate += HuskNewRune.damageEnhance(player);
        rate += MoonNewRune.damageEnhance(player);
        rate += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerCommonDamageEnhance);
        rate += DamageInfluenceCurios.getRate(player);
        rate += Compute.getPlayerPotionEffectRate(player, ModEffects.DAMAGE_ENHANCE.get(), 0.35, 0.5);
        rate += StableTierAttributeModifier
                .getModifierValue(player, StableTierAttributeModifier.playerCommonDamageEnhance);
        rate += EnhancePurpleIronArmor.getCommonDamageEnhanceRate(player);
        rate += DivineUtils.getPlayerExCommonDamageEnhanceRate(player);
        rate += MoonCurios.getExCommonDamageEnhance(player);
        rate += Compute.CuriosAttribute
                .attributeValue(player, Utils.commonDamageEnhance, StringUtils.RandomCuriosAttribute.commonDamageEnhance);
        return rate;
    }

    public static double getPlayerFinalDamageEnhance(Player player, Mob mob) {
        double rate = 0;
        rate -= MobEffectAndDamageMethods.PlayerDamageDecreaseRate(player, mob);
        rate += getPlayerFinalDamageEnhance(player);
        rate += StableTierAttributeModifier
                .getModifierValue(mob, StableTierAttributeModifier.monsterWithstandDamageEnhance);
        rate += DivineKnife.getEnhanceMobWithstandDamage(mob);
        return rate;
    }

    public static double getPlayerAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += AttackEventModule.SwordSKillEnhance(player); // 多余技能点
        rate += AttackEventModule.BowSKillEnhance(player); // 多余技能点
        rate += LabourDayIronPickaxe.playerAttackDamageEnhance(player);
        rate += Compute.getPlayerPotionEffectRate(player,
                ModEffects.ATTACK_DAMAGE_ENHANCE.get(), 0.35, 0.5);
        rate += Compute.CuriosAttribute
                .attributeValue(player, Utils.attackDamageEnhance, StringUtils.RandomCuriosAttribute.attackDamageEnhance);
        rate += PlayerAttributes.computeAllEquipSlotBaseAttributeValue(player,
                Utils.attackDamageEnhance, false);
        return rate;
    }

    public static double getPlayerAttackDamageEnhance(Player player, Mob mob) {
        double rate = 0;
        rate += IceInstance.IceKnightHealthAttackDamageFix(mob);
        rate += getPlayerAttackDamageEnhance(player);
        return rate;
    }

    public static double getPlayerManaDamageEnhance(Player player) {
        double rate = 0;
        rate += AttackEventModule.ManaSKillEnhance(player);
        rate += LabourDayIronHoe.playerManaDamageEnhance(player);
        rate += Compute.getPlayerPotionEffectRate(player,
                ModEffects.MANA_DAMAGE_ENHANCE.get(), 0.35, 0.5);
        rate += Compute.CuriosAttribute
                .attributeValue(player, Utils.manaDamageEnhance, StringUtils.RandomCuriosAttribute.manaDamageEnhance);
        rate += PlayerAttributes.computeAllEquipSlotBaseAttributeValue(player,
                Utils.manaDamageEnhance, false);
        rate += MixtureAttack.getManaDamageEnhance(player);
        return rate;
    }
    public static double getPlayerNormalSwordAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += AttackEventModule.MineShield(player); // 盾击
        return rate;
    }

    public static double getPlayerNormalBowAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += 2.25 * (1 - (16 / (16 + PlayerAttributes.extraSwiftness(player)))); // 迅捷加成
        return rate;
    }

    public static double getPlayerNormalManaAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += MixtureAttack.getManaDamageEnhance(player);
        return rate;
    }

    public static double getPlayerFinalDamageEnhance(Player player) {
        double rate = 0;
        Item item = player.getMainHandItem().getItem();
        rate += Compute.CuriosAttribute.getDistinctCuriosList(player)
                .stream().filter(curios -> {
                    boolean isUniform = curios.getItem() instanceof WraqUniformCurios;
                    if (isUniform) {
                        Item curio = curios.getItem();
                        if ((curio instanceof WraqAttackUniformCurios
                                || curio instanceof WraqAttackEnhancedUniformCurios) && item instanceof WraqSword) {
                            return true;
                        } else if ((curio instanceof WraqBowUniformCurios
                                || curio instanceof WraqBowEnhancedUniformCurios) && item instanceof WraqBow) {
                            return true;
                        } else return (curio instanceof WraqManaUniformCurios
                                || curio instanceof WraqManaEnhancedUniformCurios) && item instanceof WraqSceptre;
                    }
                    return false;
                }).mapToDouble(curio -> ((WraqUniformCurios) curio.getItem()).getFinalDamageEnhanceRate())
                .sum();
        rate += Compute.CuriosAttribute
                .attributeValue(player, Utils.finalDamageEnhance, StringUtils.RandomCuriosAttribute.finalDamageEnhance);
        rate += DevilPowerCurio.finalDamageEnhanceRate(player);
        return rate;
    }

    public static double getPlayerTotalDamageRate(Player player) {
        double rate = 1;
        rate += MySeason.playerTotalDamageRate();
        rate += ColdSystem.getMovementSpeedAndDamageEffectRate(player);
        return rate;
    }

    public static double getMonsterControlDamageEffect(Player player, Mob mob) {
        double rate = 1;
        rate += TowerMob.playerIsChallenging3FloorAndInFire(player) ? -0.5 : 0;
        rate -= TowerMob.playerIsChallenging5FloorDamageDecrease(player);
        if (Tower.mobIsTowerMob(mob) != -1 && Tower.playerIsChallengingTower(player) != Tower.mobIsTowerMob(mob)) {
            return 0;
        }
        if (MobSpawn.getMobOriginName(mob).contains("本源") && player.distanceTo(mob) >= 16) rate += -0.6;
        rate += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerMonsterControlDamageEffect);
        rate += SilverDragonBloodWeapon.getMobWithstandDamageRate(mob);
        return rate;
    }

    public static double modifyPlayerWithstandDamageRate(Player player, Mob mob, double damage) {
        double rate = 0;
        CompoundTag data = player.getPersistentData();
        rate += MineNewRune.withstandDamageInfluence(player);
        rate += Compute.getPlayerPotionEffectRate(player, ModEffects.STONE.get(), -0.15, -0.25);
        rate -= StableTierAttributeModifier
                .getModifierValue(player, StableTierAttributeModifier.playerWithstandDamageReduce);
        rate += DivineUtils.getPlayerWithstandDamageExRate(player);
        rate -= IceHolyRune.getExDamageDecreaseRate(player);
        rate -= ColdIronArmor.getWithstandDamageReductionRate(player);
        rate += Compute.getSwordSkill1And4(data, player);
        rate += Compute.getSwordSkill14(data, player, mob);
        rate += Compute.getBowSkill4(data, player);
        rate += Compute.getManaSkill4(data, player);
        if (!MobSpawn.getMobOriginName(mob).equals(SpringMobEvent.mobName)) {
            rate -= DamageInfluence.levelSuppress(player, mob); // 等级压制
        }
        rate += MonsterAttackEvent.SnowArmorEffectDamageDecrease(mob); // 冰川盔甲
        rate += ModifyPlayerWithstandDamageInfluenceCurios.modifyPlayerWithstandDamageRate(player, mob);
        rate += WardenInstance.modifyPlayerWithstandDamageRate(player, mob);
        rate += GemWithstandDamageRateModifier.modifyPlayerWithstandDamageRate(player, mob, damage);
        rate += TabooAttackArmor.modifyPlayerWithstandDamageRate(player);
        return rate;
    }

    public static double getPlayerNormalAttackBaseDamageEnhance(Player player, int type) {
        double rate = 0;
        rate += VdWeaponCommon.normalAttackRateEnhance(player);
        rate += VolcanoNewRune.attackEnhance(player);
        rate += EnhanceNormalAttackModifier.onHitDamageEnhance(player, type);
        rate += IceHolySword.getExAttackBaseDamageRate(player);
        return rate;
    }

    public static double levelSuppress(Player player, Mob monster) {
        int mobLevel = MobSpawn.MobBaseAttributes.xpLevel.getOrDefault(MobSpawn.getMobOriginName(monster), 0);
        if (MobSpawn.getMobOriginName(monster).equals(SpringMobEvent.mobName)) {
            return 0;
        }
        return (player.experienceLevel - mobLevel) / 500d;
    }

    public static double getAdjustAttackDamageRate(Player player, Mob mob) {
        double rate = 0;
        rate += MushroomInstance.getAdjustDamageRate(mob);
        return rate;
    }

    public static double getAdjustManaDamageRate(Player player, Mob mob) {
        double rate = 0;
        rate += MushroomInstance.getAdjustDamageRate(mob);
        return rate;
    }
}
