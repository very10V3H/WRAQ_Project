package fun.wraq.common.attribute;

import fun.wraq.common.Compute;
import fun.wraq.common.impl.onhit.OnHitDamageInfluenceCurios;
import fun.wraq.core.ManaAttackModule;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.instances.IceInstance;
import fun.wraq.events.modules.AttackEventModule;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.MobEffectAndDamageMethods;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.system.element.equipAndCurios.fireElement.FireEquip;
import fun.wraq.process.system.potion.NewPotionEffects;
import fun.wraq.process.system.season.MySeason;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.process.system.tower.TowerMob;
import fun.wraq.render.hud.ColdData;
import fun.wraq.series.instance.series.castle.CastleSword;
import fun.wraq.series.instance.series.ice.IceBook;
import fun.wraq.series.instance.series.moon.Equip.MoonArmor;
import fun.wraq.series.newrunes.chapter1.MineNewRune;
import fun.wraq.series.newrunes.chapter1.VolcanoNewRune;
import fun.wraq.series.newrunes.chapter2.HuskNewRune;
import fun.wraq.series.newrunes.chapter6.MoonNewRune;
import fun.wraq.series.overworld.chapter7.star.StarArmor;
import fun.wraq.series.overworld.chapter7.star.StarBottle;
import fun.wraq.series.overworld.chapter7.vd.VdWeaponCommon;
import fun.wraq.series.specialevents.labourDay.LabourDayIronHoe;
import fun.wraq.series.specialevents.labourDay.LabourDayIronPickaxe;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import static fun.wraq.core.ManaAttackModule.NetherManaArmor;

public class DamageInfluence {
    public static double getPlayerCommonDamageUpOrDown(Player player, Mob monster) {
        double rate = 0;
        rate += levelSuppress(player, monster); // 等级压制
        rate += AttackEventModule.IceArmorDamageEnhance(player, monster); // 雪上覆霜
        rate += IceBook.IceBookDamageEnhance(player, monster); // 冰封的记忆
        rate += MoonArmor.DamageEnhance(player, monster); // 尘月膝
        /*rate += CastleCurios.DamageEnhance(player, monster); // 随机饰品被动*/
        rate += StarArmor.DamageEnhance(player, monster); // 梦月
        rate += VdWeaponCommon.damageEnhance(player, monster); // vd weapon
        rate += OnHitDamageInfluenceCurios.damageInfluence(player, monster);
        rate += ManaAttackModule.getManaSkill3DamageEnhance(player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
        rate += NetherManaArmor(player, monster); // 下界混沌套装

        rate += getPlayerCommonDamageUpOrDown(player);
        return rate;
    }

    public static double getPlayerCommonDamageUpOrDown(Player player) {
        double rate = 0;
        rate += AttackEventModule.SwordSkill5DamageEnhance(player); // 双刃剑
        rate += AttackEventModule.ManaSkill5DamageEnhance(player); // 法术专注
        rate += ColdData.PlayerColdEffect(player); // 寒冷
        rate += CastleSword.DamageEnhance(player);
        rate += StarBottle.DamageEnhance(player); // 星星瓶
        rate += FireEquip.DamageEnhance(player); // 炽焰元素武器
        rate += NewPotionEffects.damageEnhance(player);
        rate += MySeason.playerResonanceDamageEnhance(player);
        rate += MineNewRune.damageEnhance(player);
        rate += HuskNewRune.damageEnhance(player);
        rate += MoonNewRune.damageEnhance(player);
        rate += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerCommonDamageEnhance);
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
        rate += IceInstance.IceKnightHealthAttackDamageFix(mob);
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

        rate += 0.5 * Compute.CuriosAttribute.getDistinctCuriosList(player)
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

    public static double getPlayerNormalAttackBaseRate(Player player, int type) {
        double rate = 1;
        rate += VdWeaponCommon.normalAttackRateEnhance(player);
        rate += VolcanoNewRune.attackEnhance(player);
        rate += EnhanceNormalAttackModifier.onHitDamageEnhance(player, type);
        return rate;
    }

    public static double levelSuppress(Player player, Mob monster) {
        int mobLevel = MobSpawn.MobBaseAttributes.xpLevel.getOrDefault(MobSpawn.getMobOriginName(monster), 0);
        return (player.experienceLevel - mobLevel) / 500d;
    }
}
