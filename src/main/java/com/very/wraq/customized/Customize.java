package com.very.wraq.customized;

import com.very.wraq.customized.uniform.attack.AttackCurios2;
import com.very.wraq.customized.uniform.bow.BowCurios2;
import com.very.wraq.customized.uniform.mana.ManaCurios2;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class Customize {
    public static void CustomizeTickEvent(Player player) {
        AttackCurios2.tick(player);
        BowCurios2.tick(player);
        ManaCurios2.tick(player);
    }

    public static double ManaExDamage(Player player, Mob monster, double BaseDamage) {
        double ExDamage = 0;
        return ExDamage;
    }

    public static double DamageIgnoreDefence(Player player) {
        double DamageIgnoreDefence = 0;
        return DamageIgnoreDefence;
    }

    public static double ManaDamageEnhance(Player player, Mob monster) {
        double DamageEnhance = 0;
        return DamageEnhance;
    }

    public static void ManaNormalAttackEffect(Player player, Mob monster) {

    }

    public static void ArrowNormalAttackEffect(Player player, Mob monster, double Damage, boolean WhetherShootByPlayer, boolean IsManaArrow) {
        if (WhetherShootByPlayer) {

        }

        if (IsManaArrow) {

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
