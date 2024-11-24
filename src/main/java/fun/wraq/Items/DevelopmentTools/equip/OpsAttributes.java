package fun.wraq.Items.DevelopmentTools.equip;

import com.google.common.collect.ImmutableMap;
import fun.wraq.common.util.Utils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class OpsAttributes {
    public static double attackDamage = 50000;
    public static double defence = 1000;
    public static double defencePenetration = 0.5;
    public static double defencePenetration0 = 200;
    public static double critRate = 1;
    public static double critDamage = 5;
    public static double manaDamage = 100000;
    public static double manaDefence = 1000;
    public static double manaPenetration = 0.5;
    public static double manaPenetration0 = 200;
    public static double manaRecover = 100;
    public static double releaseSpeed = 0.5;
    public static double movementSpeed = 0.5;
    public static double maxHealth = 100000;
    public static double healthRecover = 5000;
    public static double healingAmplification = 1;
    public static double maxMana = 2000;


    public static void setValue(String key, double value) {
        if ("attackDamage".toLowerCase().contains(key.toLowerCase())) {
            attackDamage = value;
        }
        if ("defence".toLowerCase().contains(key.toLowerCase())) {
            defence = value;
        }
        if ("defencePenetration".toLowerCase().contains(key.toLowerCase())) {
            defencePenetration = value;
        }
        if ("defencePenetration0".toLowerCase().contains(key.toLowerCase())) {
            defencePenetration0 = value;
        }
        if ("critRate".toLowerCase().contains(key.toLowerCase())) {
            critRate = value;
        }
        if ("critDamage".toLowerCase().contains(key.toLowerCase())) {
            critDamage = value;
        }
        if ("manaDamage".toLowerCase().contains(key.toLowerCase())) {
            manaDamage = value;
        }
        if ("manaDefence".toLowerCase().contains(key.toLowerCase())) {
            manaDefence = value;
        }
        if ("manaPenetration".toLowerCase().contains(key.toLowerCase())) {
            manaPenetration = value;
        }
        if ("manaPenetration0".toLowerCase().contains(key.toLowerCase())) {
            manaPenetration0 = value;
        }
        if ("manaRecover".toLowerCase().contains(key.toLowerCase())) {
            manaRecover = value;
        }
        if ("releaseSpeed".toLowerCase().contains(key.toLowerCase())) {
            releaseSpeed = value;
        }
        if ("movementSpeed".toLowerCase().contains(key.toLowerCase())) {
            movementSpeed = value;
        }
        if ("maxHealth".toLowerCase().contains(key.toLowerCase())) {
            maxHealth = value;
        }
        if ("healthRecover".toLowerCase().contains(key.toLowerCase())) {
            healthRecover = value;
        }
        if ("healingAmplification".toLowerCase().contains(key.toLowerCase())) {
            healingAmplification = value;
        }
        if ("maxMana".toLowerCase().contains(key.toLowerCase())) {
            maxMana = value;
        }
    }

    public static double getValue(Map<Item, Double> attribute, Player player) {
        if (player.getMainHandItem().getItem() instanceof ManageEquip) {
            Map<Map<Item, Double>, Double> map1 = ImmutableMap.of(
                    Utils.attackDamage, attackDamage,
                    Utils.defence, defence,
                    Utils.defencePenetration, defencePenetration,
                    Utils.defencePenetration0, defencePenetration0,
                    Utils.critRate, critRate,
                    Utils.critDamage, critDamage,
                    Utils.healingAmplification, healingAmplification
            );
            Map<Map<Item, Double>, Double> map2 = ImmutableMap.of(
                    Utils.manaDamage, manaDamage,
                    Utils.manaDefence, manaDefence,
                    Utils.manaPenetration, manaPenetration,
                    Utils.manaPenetration0, manaPenetration0,
                    Utils.manaRecover, manaRecover,
                    Utils.maxMana, maxMana,
                    Utils.coolDownDecrease, releaseSpeed,
                    Utils.movementSpeedCommon, movementSpeed,
                    Utils.maxHealth, maxHealth,
                    Utils.healthRecover, healthRecover
            );
            Map<Map<Item, Double>, Double> map = new HashMap<>() {{
                putAll(map1);
                putAll(map2);
            }};
            return map.getOrDefault(attribute, 0d);
        }
        return 0;
    }
}
