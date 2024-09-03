package com.very.wraq.common.attribute;

import com.very.wraq.common.Compute;
import com.very.wraq.core.ManaAttackModule;
import com.very.wraq.events.instance.IceKnight;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.modules.AttackEventModule;
import com.very.wraq.series.specialevents.labourDay.LabourDayIronHoe;
import com.very.wraq.series.specialevents.labourDay.LabourDayIronPickaxe;
import com.very.wraq.process.system.potion.NewPotionEffects;
import com.very.wraq.process.system.element.equipAndCurios.fireElement.FireEquip;
import com.very.wraq.process.func.MobEffectAndDamageMethods;
import com.very.wraq.process.system.season.MySeason;
import com.very.wraq.process.system.tower.Tower;
import com.very.wraq.process.system.tower.TowerMob;
import com.very.wraq.projectiles.OnCuriosSlotHitDamageInfluence;
import com.very.wraq.customized.WraqUniformCurios;
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
import com.very.wraq.series.overworld.chapter7.vd.VdWeaponCommon;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import static com.very.wraq.core.ManaAttackModule.NetherManaArmor;

public class DamageInfluence {
    public static double getPlayerCommonDamageUpOrDown(Player player, Mob monster) {
        double rate = 0;
        rate += Compute.LevelSuppress(player, monster); // 等级压制
        rate += AttackEventModule.IceArmorDamageEnhance(player, monster); // 雪上覆霜
        rate += IceBook.IceBookDamageEnhance(player, monster); // 冰封的记忆
        rate += MoonArmor.DamageEnhance(player, monster); // 尘月膝
        rate += CastleCurios.DamageEnhance(player, monster); // 随机饰品被动
        rate += PurpleIronSword.damageEnhance(player, monster);
        rate += StarArmor.DamageEnhance(player, monster); // 梦月
        rate += VdWeaponCommon.damageEnhance(player, monster); // vd weapon
        rate += OnCuriosSlotHitDamageInfluence.damageInfluence(player, monster);
        rate += ManaAttackModule.getManaSkill3DamageEnhance(player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
        rate += NetherManaArmor(player, monster); // 下界混沌套装

        rate += getPlayerCommonDamageUpOrDown(player);
        return rate;
    }

    public static double getPlayerCommonDamageUpOrDown(Player player) {
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

    public static double getPlayerFinalDamageEnhance(Player player, Mob mob) {
        double rate = 0;
        rate -= MobEffectAndDamageMethods.PlayerDamageDecreaseRate(player, mob);
        rate += getPlayerFinalDamageEnhance(player);
        return rate;
    }

    public static double getPlayerAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += AttackEventModule.SwordSKillEnhance(player); // 多余技能点
        rate += AttackEventModule.BowSKillEnhance(player); // 多余技能点
        rate += LabourDayIronPickaxe.playerAttackDamageEnhance(player);
        rate += NewPotionEffects.attackDamageEnhance(player);
        return rate;
    }

    public static double getPlayerAttackDamageEnhance(Player player, Mob mob) {
        double rate = 0;
        rate += IceKnight.IceKnightHealthAttackDamageFix(mob);
        rate += getPlayerAttackDamageEnhance(player);
        return rate;
    }

    public static double getPlayerManaDamageEnhance(Player player) {
        double rate = 0;
        rate += ManaAttackModule.ManaSkill10DamageEnhance(player);
        rate += ManaAttackModule.ManaSkill11(player);
        rate += AttackEventModule.ManaSKillEnhance(player);
        rate += LabourDayIronHoe.playerManaDamageEnhance(player);
        rate += NewPotionEffects.manaDamageEnhance(player);
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

        return rate;
    }

    public static double getPlayerFinalDamageEnhance(Player player) {
        double rate = 0;

        rate += 0.5 * Compute.CuriosAttribute.getCuriosList(player)
                .stream().filter(curios -> curios.getItem() instanceof WraqUniformCurios)
                .count();
        return rate;
    }

    public static double getPlayerTotalDamageRate(Player player) {
        double rate = 1;
        rate += MySeason.playerTotalDamageRate();
        return rate;
    }

    public static double getMonsterControlDamageEffect(Player player, Mob mob) {
        double rate = 1;
        rate += TowerMob.playerIsChallenging3FloorAndInFire(player) ? -0.5 : 0;
        rate -= TowerMob.playerIsChallenging5FloorDamageDecrease(player);
        rate -= MySeason.mobHurtDamageEffect(mob);
        if (Tower.mobIsTowerMob(mob) != -1 && Tower.playerIsChallengingTower(player) != Tower.mobIsTowerMob(mob))
            return 0;
        if (MobSpawn.getMobOriginName(mob).contains("本源") && player.distanceTo(mob) >= 16) rate += -0.6;
        return rate;
    }

    public static double getPlayerWithstandDamageInfluence(Player player, Mob mob) {
        double rate = 1;
        rate += MineNewRune.withstandDamageInfluence(player);
        return rate;
    }

    public static double getPlayerNormalAttackBaseRate(Player player) {
        double rate = 1;
        rate += VdWeaponCommon.normalAttackRateEnhance(player);
        return rate;
    }
}
