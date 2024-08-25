package com.very.wraq.common.attributeValues;

import com.very.wraq.core.ManaAttackModule;
import com.very.wraq.customized.uniform.attack.AttackCurios0;
import com.very.wraq.customized.uniform.attack.AttackCurios1;
import com.very.wraq.customized.uniform.attack.AttackCurios2;
import com.very.wraq.customized.uniform.bow.BowCurios0;
import com.very.wraq.customized.uniform.bow.BowCurios1;
import com.very.wraq.customized.uniform.bow.BowCurios2;
import com.very.wraq.customized.uniform.element.*;
import com.very.wraq.customized.uniform.mana.ManaCurios0;
import com.very.wraq.customized.uniform.mana.ManaCurios1;
import com.very.wraq.customized.uniform.mana.ManaCurios2;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.modules.AttackEventModule;
import com.very.wraq.process.system.element.equipAndCurios.fireElement.FireEquip;
import com.very.wraq.process.system.instance.MobEffectAndDamageMethods;
import com.very.wraq.process.series.labourDay.LabourDayIronHoe;
import com.very.wraq.process.series.labourDay.LabourDayIronPickaxe;
import com.very.wraq.process.series.potion.NewPotionEffects;
import com.very.wraq.process.system.season.MySeason;
import com.very.wraq.process.system.tower.Tower;
import com.very.wraq.process.system.tower.TowerMob;
import com.very.wraq.series.instance.series.castle.CastleCurios;
import com.very.wraq.series.instance.series.castle.CastleSword;
import com.very.wraq.series.instance.series.ice.IceBook;
import com.very.wraq.series.instance.series.moon.Equip.MoonArmor;
import com.very.wraq.series.instance.series.purple.PurpleIronSword;
import com.very.wraq.series.newrunes.chapter1.MineNewRune;
import com.very.wraq.series.newrunes.chapter2.HuskNewRune;
import com.very.wraq.series.newrunes.chapter6.MoonNewRune;
import com.very.wraq.series.overworld.chapter7.star.StarArmor;
import com.very.wraq.series.overworld.chapter7.star.StarBottle;
import com.very.wraq.common.Compute;
import com.very.wraq.series.overworld.chapter7.vd.VdWeaponCommon;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import static com.very.wraq.core.ManaAttackModule.ManaSkill11;

public class DamageInfluence {
    public static double PlayerCommonDamageUpOrDown(Player player, Mob monster) {
        double rate = 0;
        rate += Compute.LevelSuppress(player, monster); // 等级压制
        rate += AttackEventModule.IceArmorDamageEnhance(player, monster); // 雪上覆霜
        rate += IceBook.IceBookDamageEnhance(player, monster); // 冰封的记忆
        rate += MoonArmor.DamageEnhance(player, monster); // 尘月膝
        rate += CastleCurios.DamageEnhance(player, monster); // 随机饰品被动
        rate += PurpleIronSword.damageEnhance(player, monster);
        rate += StarArmor.DamageEnhance(player, monster); // 梦月
        rate += PlayerCommonDamageUpOrDown(player);
        rate += VdWeaponCommon.damageEnhance(player, monster); // vd weapon
        return rate;
    }

    public static double PlayerCommonDamageUpOrDown(Player player) {
        double rate = 0;
        rate += AttackEventModule.SwordSkill5DamageEnhance(player); // 双刃剑
        rate += AttackEventModule.ManaSkill5DamageEnhance(player); // 法术专注
        rate += Compute.PlayerColdEffect(player); // 寒冷
        rate += CastleSword.DamageEnhance(player);
        rate += StarBottle.DamageEnhance(player); // 星星瓶
        rate += FireEquip.DamageEnhance(player); // 炽焰元素武器
        rate += NewPotionEffects.damageEnhance(player);
        rate += MySeason.playerResonanceDamageEnhance(player);
        rate += MineNewRune.damageEnhance(player);
        rate += HuskNewRune.damageEnhance(player);
        rate += MoonNewRune.damageEnhance(player);
        return rate;
    }

    public static double PlayerFinalDamageEnhance(Player player, Mob mob) {
        double rate = 0;
        rate -= MobEffectAndDamageMethods.PlayerDamageDecreaseRate(player, mob);
        rate += PlayerFinalDamageEnhance(player);
        return rate;
    }

    public static double PlayerAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += AttackEventModule.SwordSKillEnhance(player); // 多余技能点
        rate += AttackEventModule.BowSKillEnhance(player); // 多余技能点
        rate += LabourDayIronPickaxe.playerAttackDamageEnhance(player);
        rate += NewPotionEffects.attackDamageEnhance(player);
        return rate;
    }

    public static double PlayerManaDamageEnhance(Player player) {
        double rate = 0;
        rate += ManaAttackModule.ManaSkill10DamageEnhance(player);
        rate += ManaSkill11(player);
        rate += AttackEventModule.ManaSKillEnhance(player);
        rate += LabourDayIronHoe.playerManaDamageEnhance(player);
        rate += NewPotionEffects.manaDamageEnhance(player);
        return rate;
    }

    public static double PlayerNormalSwordAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += AttackEventModule.MineShield(player); // 盾击
        return rate;
    }

    public static double PlayerNormalBowAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += 2.25 * (1 - (16 / (16 + PlayerAttributes.extraSwiftness(player)))); // 迅捷加成
        return rate;
    }

    public static double PlayerNormalManaAttackDamageEnhance(Player player) {
        double rate = 0;

        return rate;
    }

    public static double PlayerFinalDamageEnhance(Player player) {
        double rate = 0;

        rate += AttackCurios0.playerFinalDamageEnhance(player);
        rate += BowCurios0.playerFinalDamageEnhance(player);
        rate += ManaCurios0.playerFinalDamageEnhance(player);

        rate += AttackCurios1.playerFinalDamageEnhance(player);
        rate += BowCurios1.playerFinalDamageEnhance(player);
        rate += ManaCurios1.playerFinalDamageEnhance(player);

        rate += AttackCurios2.playerFinalDamageEnhance(player);
        rate += BowCurios2.playerFinalDamageEnhance(player);
        rate += ManaCurios2.playerFinalDamageEnhance(player);

        rate += LifeCurios0.playerFinalDamageEnhance(player);
        rate += WaterCurios0.playerFinalDamageEnhance(player);
        rate += FireCurios0.playerFinalDamageEnhance(player);
        rate += StoneCurios0.playerFinalDamageEnhance(player);
        rate += IceCurios0.playerFinalDamageEnhance(player);
        rate += WindCurios0.playerFinalDamageEnhance(player);
        rate += LightningCurios0.playerFinalDamageEnhance(player);
        return rate;
    }

    public static double PlayerTotalDamageRate(Player player) {
        double rate = 1;
        rate += MySeason.playerTotalDamageRate();
        return rate;
    }

    public static double MonsterControlDamageEffect(Player player, Mob mob) {
        double rate = 1;
        rate += TowerMob.playerIsChallenging3FloorAndInFire(player) ? -0.5 : 0;
        rate -= TowerMob.playerIsChallenging5FloorDamageDecrease(player);
        rate -= MySeason.mobHurtDamageEffect(mob);
        if (Tower.mobIsTowerMob(mob) != -1 && Tower.playerIsChallengingTower(player) != Tower.mobIsTowerMob(mob))
            return 0;
        if (MobSpawn.getMobOriginName(mob).contains("本源") && player.distanceTo(mob) >= 16) rate += -0.6;
        return rate;
    }

    public static double playerWithstandDamageInfluence(Player player, Mob mob) {
        double rate = 1;
        rate += MineNewRune.withstandDamageInfluence(player);
        return rate;
    }

    public static double playerNormalAttackBaseRate(Player player) {
        double rate = 1;
        rate += VdWeaponCommon.normalAttackRateEnhance(player);
        return rate;
    }
}
