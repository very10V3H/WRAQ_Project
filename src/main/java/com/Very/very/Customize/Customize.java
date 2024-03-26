package com.Very.very.Customize;

import com.Very.very.Customize.Players.Black_Feisa_.*;
import com.Very.very.Customize.Players.Eliaoi.Eliaoi;
import com.Very.very.Customize.Players.FengXiaoju.FengXiaoJu;
import com.Very.very.Customize.Players.FengXiaoju.FengXiaoJuCurios1;
import com.Very.very.Customize.Players.Guang_Yi.GuangYi;
import com.Very.very.Customize.Players.LXYZO.LXYZO;
import com.Very.very.Customize.Players.Lei_yan233.LeiyanBow;
import com.Very.very.Customize.Players.Lei_yan233.LeiyanCurios;
import com.Very.very.Customize.Players.MyMission.MyMissionBow;
import com.Very.very.Customize.Players.MyMission.MyMissionCurios;
import com.Very.very.Customize.Players.MyMission.MyMissionCurios1;
import com.Very.very.Customize.Players.Qi_Fu.QiFuBow;
import com.Very.very.Customize.Players.Qi_Fu.QiFuCurios;
import com.Very.very.Customize.Players.Qi_Fu.QiFuCurios1;
import com.Very.very.Customize.Players.ShowDicker.showdicker;
import com.Very.very.Customize.Players.Sora_vanilla.SoraVanilla;
import com.Very.very.Customize.Players.Sora_vanilla.SoraVanilla1;
import com.Very.very.Customize.Players.Sora_vanilla.SoraVanilla2;
import com.Very.very.Customize.Players.Wcndymlgb.Wcndymlgb;
import com.Very.very.Customize.Players.Wcndymlgb.WcndymlgbCurios;
import com.Very.very.Customize.Players.XiangLi.XiangLi;
import com.Very.very.Customize.Players.YuanShiRen.YuanShiRen;
import com.Very.very.Customize.Players.YuanShiRen.YuanShiRenCurios;
import com.Very.very.Customize.Players.Yxwg.YxwgBow;
import com.Very.very.Customize.Players.Yxwg.YxwgCurios;
import com.Very.very.Customize.Players.Yxwg.YxwgCurios1;
import com.Very.very.Customize.Players.Yxwg.YxwgCurios2;
import com.Very.very.Customize.Players.cgswd.CgswdCurios;
import com.Very.very.Customize.Players.cgswd.CgswdSceptre;
import com.Very.very.Customize.Players.liulixian_.LiuLiXianCurios1F;
import com.Very.very.Customize.Players.liulixian_.LiulixianCurios;
import com.Very.very.Customize.Players.liulixian_.LiulixianCurios2;
import com.Very.very.Customize.Players.liulixian_.liulixian_;
import com.Very.very.Customize.Players.shangmengli.ShangMengLi;
import com.Very.very.Customize.Players.shangmengli.ShangMengLiCurios2;
import com.Very.very.Customize.Players.shangmengli.ShangMengLiSword;
import com.Very.very.Customize.Players.shangmengli.ShangMengLiCurios1;
import com.Very.very.Customize.Players.very_new.VeryNew;
import com.Very.very.Customize.Players.very_new.VeryNewCurios;
import com.Very.very.Events.WaltzAndModule.AttackEventModule;
import com.Very.very.CoreAttackModule.ManaAttackModule;
import com.Very.very.ValueAndTools.Utils.Utils;
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
        }

        if (IsManaArrow) {
            MyMissionBow.RangeDamage(player,monster);
            MyMissionBow.ArrowAdd(player,monster);
        }
    }

    public static double AttackDamage = 550;
    public static double DefencePenetration0 = 3600;
    public static double ManaDamage = 3072;
    public static double ManaPenetration0 = 3600;
    public static double DefencePenetration = 0.2;
    public static double CritRate = 0.3;
    public static double CritDamage = 2.4;
    public static double HealthSteal = 0.08;
    public static double MovementSpeed = 0.5;

    public static double BowCritRate = 0.25;
    public static double BowMovementSpeed = 0.6;
    public static double BowCritDamage = 4;
}
