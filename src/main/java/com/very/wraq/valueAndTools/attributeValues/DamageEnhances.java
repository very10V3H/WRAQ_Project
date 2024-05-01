package com.very.wraq.valueAndTools.attributeValues;

import com.very.wraq.coreAttackModule.ManaAttackModule;
import com.very.wraq.customized.players.bow.Hgj.HgjCurios;
import com.very.wraq.customized.players.bow.Lei_yan233.LeiyanBow;
import com.very.wraq.customized.players.bow.Qi_Fu.QiFuCurios1;
import com.very.wraq.customized.players.sceptre.Black_Feisa_.BlackFeisaCurios;
import com.very.wraq.customized.players.sceptre.Black_Feisa_.BlackFeisaCurios3;
import com.very.wraq.customized.players.sceptre.Black_Feisa_.BlackFeisaCurios4;
import com.very.wraq.customized.players.sceptre.Eliaoi.EliaoiCurios1;
import com.very.wraq.customized.players.sceptre.Eliaoi.EliaoiCurios2;
import com.very.wraq.customized.players.sceptre.Eliaoi.EliaoiSceptre;
import com.very.wraq.customized.players.sceptre.FengXiaoju.FengXiaoJuCurios1;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanilla2;
import com.very.wraq.customized.players.sceptre.YuanShiRen.YuanShiRenCurios;
import com.very.wraq.customized.players.sceptre.cgswd.CgswdCurios;
import com.very.wraq.customized.players.sceptre.liulixian_.LiulixianCurios2;
import com.very.wraq.customized.players.sceptre.liulixian_.LiulixianCurios3;
import com.very.wraq.customized.players.sceptre.shangmengli.ShangMengLi;
import com.very.wraq.customized.players.sceptre.shangmengli.ShangMengLiCurios3;
import com.very.wraq.customized.players.sword.Heihuang.HeihuangCurios;
import com.very.wraq.customized.players.sword.ZuoSI.ZuoSiCurios;
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
import com.very.wraq.events.modules.AttackEventModule;
import com.very.wraq.process.element.equipAndCurios.fireElement.FireEquip;
import com.very.wraq.process.instance.MobEffectAndDamageMethods;
import com.very.wraq.process.labourDay.LabourDayIronHoe;
import com.very.wraq.process.labourDay.LabourDayIronPickaxe;
import com.very.wraq.series.instance.Castle.CastleCurios;
import com.very.wraq.series.instance.Castle.CastleSword;
import com.very.wraq.series.instance.Ice.IceBook;
import com.very.wraq.series.instance.Moon.Equip.MoonArmor;
import com.very.wraq.series.instance.PurpleIronKnight.PurpleIronSword;
import com.very.wraq.series.overWorld.MainStoryVII.StarArmor;
import com.very.wraq.series.overWorld.MainStoryVII.StarBottle;
import com.very.wraq.valueAndTools.Compute;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import static com.very.wraq.coreAttackModule.ManaAttackModule.ManaSkill11;

public class DamageEnhances {
    public static double PlayerCommonDamageUpOrDown(Player player, Mob monster) {
        double rate = 0;
        rate += Compute.LevelSuppress(player,monster); // 等级压制
        rate += AttackEventModule.IceArmorDamageEnhance(player, monster); // 雪上覆霜
        rate += IceBook.IceBookDamageEnhance(player,monster); // 冰封的记忆
        rate += MoonArmor.DamageEnhance(player,monster); // 尘月膝
        rate += LeiyanBow.MobDamageEnhance(monster);
        rate += CastleCurios.DamageEnhance(player,monster); // 随机饰品被动
        rate += PurpleIronSword.DamageEnhance(player,monster);
        rate += QiFuCurios1.DamageEnhance(player,monster);
        rate += StarArmor.DamageEnhance(player,monster); // 梦月
        rate += HeihuangCurios.MobWithstandDamageEnhance(monster);
        rate += PlayerCommonDamageUpOrDown(player);
        return rate;
    }

    public static double PlayerCommonDamageUpOrDown(Player player) {
        double rate = 0;
        rate += AttackEventModule.SwordSkill5DamageEnhance(player); // 双刃剑
        rate += AttackEventModule.ManaSkill5DamageEnhance(player); // 法术专注
        rate += Compute.PlayerColdEffect(player); // 寒冷
        rate += CastleSword.DamageEnhance(player);
        rate += EliaoiCurios2.GroupBuff2DamageEnhance(player);
        rate += StarBottle.DamageEnhance(player); // 星星瓶
        rate += ZuoSiCurios.DamageEnhance(player);
        rate += BlackFeisaCurios4.DamageEnhance(player);
        rate += BlackFeisaCurios4.DamageEnhance1(player);
        rate += LiulixianCurios3.DamageEnhance(player);
        rate += FireEquip.DamageEnhance(player); // 炽焰元素武器
        rate += HgjCurios.DamageEnhance(player);
        return rate;
    }

    public static double PlayerFinalDamageEnhance(Player player, Mob mob) {
        double rate = 0;
        rate -= MobEffectAndDamageMethods.PlayerDamageDecreaseRate(player,mob);
        rate += YuanShiRenCurios.DamageEnhance(mob);
        rate += PlayerFinalDamageEnhance(player);
        return rate;
    }

    public static double PlayerAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += AttackEventModule.SwordSKillEnhance(player); // 多余技能点
        rate += AttackEventModule.CrushCuriosCountsDamageEnhance(player);
        rate += AttackEventModule.BowSKillEnhance(player); // 多余技能点
        rate += LabourDayIronPickaxe.playerAttackDamageEnhance(player);
        return rate;
    }

    public static double PlayerManaDamageEnhance(Player player) {
        double rate = 0;
        rate += EliaoiCurios1.DamageEnhance(player);
        rate += EliaoiSceptre.DamageEnhance(player);
        rate += CgswdCurios.ExManaDamageEnhance(player);
        rate += SoraVanilla2.ManaDamageEnhance(player);
        rate += BlackFeisaCurios.DamageEnhance(player);
        rate += ManaAttackModule.ManaSkill10DamageEnhance(player);
        rate += ManaSkill11(player);
        rate += ShangMengLi.ShangMengLiCurios1(player);
        rate += FengXiaoJuCurios1.DamageEnhance(player);
        rate += AttackEventModule.ManaSKillEnhance(player);
        rate += BlackFeisaCurios3.Passive1ManaDamageEnhance(player);
        rate += BlackFeisaCurios3.Passive2ManaDamageEnhance(player);
        rate += ShangMengLiCurios3.ManaDamageEnhance(player);
        rate += LiulixianCurios2.DamageEnhance(player);
        rate += LabourDayIronHoe.playerManaDamageEnhance(player);
        return rate;
    }

    public static double PlayerNormalSwordAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += AttackEventModule.MineShield(player); // 盾击
        return rate;
    }

    public static double PlayerNormalBowAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += 1.5 - (12 / (8 + PlayerAttributes.PlayerExtraSwiftness(player))); // 迅捷加成
        return rate;
    }

    public static double PlayerNormalManaAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += EliaoiCurios1.NormalAttackDamageEnhance(player);
        rate += BlackFeisaCurios3.Passive1ManaDamageEnhance(player);
        rate += LiulixianCurios2.DamageEnhance(player);
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

        rate += LiulixianCurios3.FinalDamageEnhance(player);

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
        rate += LiulixianCurios3.SelfDamageDecrease(player);
        return rate;
    }
}
