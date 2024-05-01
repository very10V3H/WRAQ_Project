package com.very.wraq.customized;

import com.very.wraq.coreAttackModule.ManaAttackModule;
import com.very.wraq.customized.players.bow.Guang_Yi.GuangYi;
import com.very.wraq.customized.players.bow.Hgj.HgjCurios;
import com.very.wraq.customized.players.bow.Lei_yan233.LeiyanBow;
import com.very.wraq.customized.players.bow.Lei_yan233.LeiyanCurios;
import com.very.wraq.customized.players.bow.MyMission.MyMissionBow;
import com.very.wraq.customized.players.bow.MyMission.MyMissionCurios;
import com.very.wraq.customized.players.bow.MyMission.MyMissionCurios1;
import com.very.wraq.customized.players.bow.Qi_Fu.QiFuBow;
import com.very.wraq.customized.players.bow.Qi_Fu.QiFuCurios;
import com.very.wraq.customized.players.bow.Qi_Fu.QiFuCurios1;
import com.very.wraq.customized.players.bow.ShowDicker.showdicker;
import com.very.wraq.customized.players.bow.Wcndymlgb.Wcndymlgb;
import com.very.wraq.customized.players.bow.Wcndymlgb.WcndymlgbCurios;
import com.very.wraq.customized.players.bow.Yxwg.YxwgBow;
import com.very.wraq.customized.players.bow.Yxwg.YxwgCurios;
import com.very.wraq.customized.players.bow.Yxwg.YxwgCurios1;
import com.very.wraq.customized.players.bow.Yxwg.YxwgCurios2;
import com.very.wraq.customized.players.sceptre.Black_Feisa_.*;
import com.very.wraq.customized.players.sceptre.Eliaoi.Eliaoi;
import com.very.wraq.customized.players.sceptre.Eliaoi.EliaoiCurios2;
import com.very.wraq.customized.players.sceptre.Eliaoi.EliaoiSceptre;
import com.very.wraq.customized.players.sceptre.FengXiaoju.FengXiaoJu;
import com.very.wraq.customized.players.sceptre.FengXiaoju.FengXiaoJuCurios1;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanilla;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanilla1;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanilla2;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanillaSword;
import com.very.wraq.customized.players.sceptre.YuanShiRen.YuanShiRen;
import com.very.wraq.customized.players.sceptre.YuanShiRen.YuanShiRenCurios;
import com.very.wraq.customized.players.sceptre.cgswd.CgswdCurios;
import com.very.wraq.customized.players.sceptre.cgswd.CgswdSceptre;
import com.very.wraq.customized.players.sceptre.liulixian_.*;
import com.very.wraq.customized.players.sceptre.shangmengli.*;
import com.very.wraq.customized.players.sceptre.very_new.VeryNew;
import com.very.wraq.customized.players.sceptre.very_new.VeryNewCurios;
import com.very.wraq.customized.players.sword.LXYZO.LXYZO;
import com.very.wraq.customized.players.sword.XiangLi.XiangLi;
import com.very.wraq.customized.players.sword.ZuoSI.ZuoSiCurios;
import com.very.wraq.events.modules.AttackEventModule;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class Customize {
    public static void CustomizeTickEvent(Player player) {
        LXYZO.Active(player);
        ShangMengLi.DoubleManaAttack(player);
        liulixian_.LiuLiXian(player);
        VeryNew.veryNew(player);
        showdicker.ShowDicker(player);
        GuangYi.GuangYiMode(player);
        GuangYi.GuangYiSecondArrow(player);
        XiangLi.Xiangli(player);
        Wcndymlgb.WcBow(player);
        BlackFeisa.BlackFeisaTick(player);
        Eliaoi.EliaoiAttack(player);
        Wcndymlgb.WcBowTick(player);
        LiuLiXianCurios1F.Tick(player);
        ShangMengLiCurios2.Tick(player);
        BlackFeisaCurios.Tick(player);
        QiFuBow.Tick(player);
        BlackFeisaCurios1.Tick(player);
        ShangMengLiSword.Tick(player);
        YuanShiRen.Tick(player);
        QiFuCurios.Tick(player);
        BlackFeisaSceptre.Tick(player);
        BlackFeisaCurios2.Tick(player);
        SoraVanilla.Tick(player);
        LiulixianCurios.Tick(player);
        LeiyanCurios.Tick(player);
        CgswdSceptre.Tick(player);
        YxwgBow.Tick(player);
        YxwgCurios1.Tick(player);
        SoraVanilla1.Tick(player);
        MyMissionCurios.Tick(player);
        MyMissionCurios1.Tick(player);
        LeiyanBow.Tick(player);
        FengXiaoJuCurios1.Tick(player);
        LiulixianCurios2.Tick(player);
        SoraVanilla2.Tick(player);
        YxwgCurios2.Tick(player);
        CgswdCurios.Tick(player);
        WcndymlgbCurios.Tick(player);
        QiFuCurios1.Tick(player);
        ShangMengLiCurios3.Tick(player);
        BlackFeisaCurios3.Tick(player);
        ZuoSiCurios.Tick(player);
        EliaoiCurios2.Tick(player);
        LiulixianCurios3.Tick(player);
        YuanShiRenCurios.Tick(player);
        EliaoiSceptre.Tick(player);
        SoraVanillaSword.Tick(player);
        BlackFeisaCurios4.Tick(player);
        BlackFeisaCurios4.FlyingAndClearTick(player);
        HgjCurios.Tick(player);
    }

    public static double ManaExDamage(Player player, Mob monster, double BaseDamage) {
        double ExDamage = 0;
        ExDamage += ShangMengLi.SecondMana(player);
        ExDamage += liulixian_.LiuLiXianFirstManaAttack(player,monster);
        ExDamage += liulixian_.LiuLiXianSecondManaAttack(player,monster);
        ExDamage += ManaAttackModule.FengxiaojuExDamagePassive(player,BaseDamage);
        ExDamage += BlackFeisa.BlackFeisaFirstManaAttack(player);
        ExDamage += BlackFeisa.BlackFeisaDoubleManaAttack(player);
        ExDamage += ShangMengLiCurios1.ExManaDamage(player);
        if (LiuLiXianCurios1F.IsLiuLiXian(player)) ExDamage += LiuLiXianCurios1F.Skill4ExDamage(player);
        if (ShangMengLiCurios2.IsShangMengLi(player)) ExDamage += ShangMengLiCurios2.Count2ExDamage(player);
        return ExDamage;
    }

    public static double DamageIgnoreDefence(Player player) {
        double DamageIgnoreDefence = 0;
        DamageIgnoreDefence += ShangMengLi.FirstMana(player);
        return DamageIgnoreDefence;
    }

    public static double ManaDamageEnhance(Player player, Mob monster) {
        double DamageEnhance = 0;
        DamageEnhance += Eliaoi.EliaoiEffect(player,monster,true);
        return DamageEnhance;
    }

    public static void ManaNormalAttackEffect(Player player, Mob monster) {
        ShangMengLi.ShangMengLiCore1Count(player);
        BlackFeisaCurios.CoolDownDecrease(player);
        YuanShiRenCurios.EffectProvider(player,monster);
        if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player)) Utils.ShangMengLiManaCount = !Utils.ShangMengLiManaCount;
        if (Utils.LiuLiXian != null && Utils.LiuLiXian.equals(player)) Utils.LiuLiXianManaFlag = !Utils.LiuLiXianManaFlag;
        if (BlackFeisa.Player != null && BlackFeisa.Player.equals(player)) BlackFeisa.BlackFeisaManaCount = !BlackFeisa.BlackFeisaManaCount;
        FengXiaoJu.FengxiaojuPassiveNormalAttack(player);
        if (LiuLiXianCurios1F.IsLiuLiXian(player)) LiuLiXianCurios1F.CountAdd(player,monster);
        Eliaoi.EliaoiMobCount(player,monster);
        CgswdSceptre.Fire(player,monster);
        CgswdSceptre.Lightning(player,monster);
        CgswdSceptre.SlowDown(player,monster);
        VeryNewCurios.ExManaEffect(player,monster);
    }

    public static void ArrowNormalAttackEffect(Player player, Mob monster, double Damage, boolean WhetherShootByPlayer, boolean IsManaArrow) {
        if (WhetherShootByPlayer) {
            AttackEventModule.WcBowEffect(player);
            YxwgBow.HealthRecover(player,Damage);
            YxwgBow.EffectProvider(player,monster);

            AttackEventModule.GuangYiArrowMobDefenceDecreaseEffect(player,monster);
            AttackEventModule.GuangYiArrowRain(player,monster);
            Utils.GuangYiArrowFlag = !Utils.GuangYiArrowFlag;

            YxwgCurios.Passive(player,monster);
            YxwgCurios1.Passive(player);

            MyMissionCurios.ArrowHit(player);
            LeiyanBow.CountAdd(player);
            QiFuBow.FromMobRangeDamage(player,monster,5,7);
            WcndymlgbCurios.AttackHeal(player);
            YxwgCurios2.Passive7Gather(player,monster);
            if (LiuLiXianCurios1F.IsLiuLiXian(player)) LiuLiXianCurios1F.CountAdd(player,monster);
            HgjCurios.GatherMob(player,monster);
            HgjCurios.ArrowHitMob(player);
        }

        if (IsManaArrow) {
            MyMissionBow.RangeDamage(player,monster);
            MyMissionBow.ArrowAdd(player,monster);
        }
    }

    public static double AttackDamage = 600;
    public static double DefencePenetration0 = 4000;
    public static double ManaDamage = 3548;
    public static double ManaPenetration0 = 4000;
    public static double ManaRecover = 30;
    public static double DefencePenetration = 0.2;
    public static double CritRate = 0.3;
    public static double CritDamage = 0.9;
    public static double HealthSteal = 0.08;
    public static double MovementSpeed = 0.5;

    public static double BowCritRate = 0.25;
    public static double BowMovementSpeed = 0.6;
    public static double BowCritDamage = 1.45;
}
