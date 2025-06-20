package fun.wraq.common.attribute;

import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.process.system.element.equipAndCurios.waterElement.WaterElementSword;
import fun.wraq.process.system.tower.TowerMob;
import fun.wraq.series.overworld.sakura.EarthMana.EarthPower;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobAttributes {

    public final double attackDamage;
    public final double defence;
    public final double manaDefence;
    public final double critRate;
    public final double critDamage;
    public final double defencePenetration;
    public final double defencePenetration0;
    public final double healthSteal;
    public final double maxHealth;
    public final double movementSpeed;

    public MobAttributes(double attackDamage, double defence, double manaDefence, double critRate, double critDamage,
                         double defencePenetration, double defencePenetration0, double healthSteal,
                         double maxHealth, double movementSpeed) {
        this.attackDamage = attackDamage;
        this.defence = defence;
        this.manaDefence = manaDefence;
        this.critRate = critRate;
        this.critDamage = critDamage;
        this.defencePenetration = defencePenetration;
        this.defencePenetration0 = defencePenetration0;
        this.healthSteal = healthSteal;
        this.maxHealth = maxHealth;
        this.movementSpeed = movementSpeed;
    }

    public static double defence(Mob monster) {
        int tickCount = Tick.get();
        double defence = MobSpawn.MobBaseAttributes.defence.getOrDefault(MobSpawn.getMobOriginName(monster), 0d);
        double exDefence = 0;
        double rate = 1;
        // 固定
        exDefence += TowerMob.mobDefenceUp(monster);
        exDefence += StableAttributesModifier.getModifierValue(monster, StableAttributesModifier.mobDefenceModifier);
        CompoundTag data = monster.getPersistentData();
        // 百分比
        if (data.getInt(StringUtils.Entropy.Snow) > tickCount)
            rate *= (1 - data.getDouble(StringUtils.SnowBossSwordActive.Pare));
        if (Utils.MobSpringAttackTick.containsKey(monster) && Utils.MobSpringAttackTick.get(monster) > tickCount)
            rate *= (1 - Utils.SpringEffect[Utils.MobSpringAttackEffect.get(monster) - 1]);
        if (Utils.MobSpringSwiftTick.containsKey(monster) && Utils.MobSpringSwiftTick.get(monster) > tickCount)
            rate *= (1 - Utils.SpringEffect[Utils.MobSpringSwiftEffect.get(monster) - 1]);
        if (Utils.SnowShieldMobEffectMap.containsKey(monster) && Utils.SnowShieldMobEffectMap.get(monster) > tickCount)
            rate *= 0.75;
        rate *= (1 + StableTierAttributeModifier.getModifierValue(monster, StableTierAttributeModifier.percentDefence));
        rate *= (1 + StableAttributesModifier.getModifierValue(monster, StableAttributesModifier.mobPercentDefenceModifier));
        rate *= WaterElementSword.MobDefenceDecrease(monster);
        defence += exDefence;
        defence *= rate;
        return Math.max(defence, 0);
    }

    public static double manaDefence(Mob monster) {
        int tick = Tick.get();
        double defence = MobSpawn.MobBaseAttributes.manaDefence.getOrDefault(MobSpawn.getMobOriginName(monster), 0d);
        double exDefence = 0;
        double rate = 1;
        exDefence += defence * EarthPower.MobManaDefenceDecrease(monster); // 地蕴法术
        exDefence += TowerMob.mobManaDefenceUp(monster);
        exDefence += StableTierAttributeModifier.getModifierValue(monster, StableTierAttributeModifier.manaDefence);
        exDefence += StableAttributesModifier.getModifierValue(monster, StableAttributesModifier.mobManaDefenceModifier);
        if (Utils.MobSpringManaTick.containsKey(monster) && Utils.MobSpringManaTick.get(monster) > tick)
            rate *= (1 - Utils.SpringEffect[Utils.MobSpringManaEffect.get(monster) - 1]);
        rate *= (1 + StableAttributesModifier.getModifierValue(monster, StableAttributesModifier.mobPercentManaDefenceModifier));
        rate *= (1 + StableTierAttributeModifier.getModifierValue(monster, StableTierAttributeModifier.percentManaDefence));
        rate *= WaterElementSword.MobDefenceDecrease(monster);
        defence += exDefence;
        defence *= rate;

        return Math.max(defence, 0);
    }

    public static double attackDamage(Mob mob) {
        double baseValue = MobSpawn.MobBaseAttributes.attackDamage.getOrDefault(MobSpawn.getMobOriginName(mob), 0d);
        double exValue = 0;
        return (baseValue + exValue) * 2;
    }

    public static double critRate(Mob mob) {
        double baseValue = MobSpawn.MobBaseAttributes.critRate.getOrDefault(MobSpawn.getMobOriginName(mob), 0d);
        double exValue = 0;
        return baseValue + exValue;
    }

    public static double defencePenetration(Mob mob) {
        double baseValue = MobSpawn.MobBaseAttributes.defencePenetration.getOrDefault(MobSpawn.getMobOriginName(mob), 0d);
        double exValue = 0;
        return baseValue + exValue;
    }

    public static double defencePenetration0(Mob mob) {
        double baseValue = MobSpawn.MobBaseAttributes.defencePenetration0.getOrDefault(MobSpawn.getMobOriginName(mob), 0d);
        double exValue = 0;
        return baseValue + exValue;
    }

    public static double healthSteal(Mob mob) {
        double baseValue = MobSpawn.MobBaseAttributes.healthSteal.getOrDefault(MobSpawn.getMobOriginName(mob), 0d);
        double exValue = 0;
        return baseValue + exValue;
    }

    public static double critDamage(Mob mob) {
        double baseValue = MobSpawn.MobBaseAttributes.critDamage.getOrDefault(MobSpawn.getMobOriginName(mob), 0d);
        double exValue = 0;
        return baseValue + exValue;
    }

    public static Map<String, Map<String, Double>> readAttributes(List<String> content) {
        Map<String, Map<String, Double>> attributesMap = new HashMap<>();
        String[] header = null;
        for (int i = 0 ; i < content.size() ; i++) {
            String s = content.get(i);
            if (s.startsWith(",")) continue;
            if (i == 0) header = s.split(",");
            else {
                String[] attributes = s.split(",");
                for (int i1 = 0; i1 < attributes.length; i1++) {
                    attributes[i1] = attributes[i1].replaceAll("\\[", "");
                    attributes[i1] = attributes[i1].replaceAll("]", "");
                    attributes[i1] = attributes[i1].trim();
                }
                attributesMap.put(attributes[0], new HashMap<>());
                Map<String, Double> eachAttributes = attributesMap.get(attributes[0]);
                for (int i1 = 1; i1 < attributes.length; i1++) {
                    if (org.apache.commons.lang3.StringUtils.isNumeric(attributes[i1])) {
                        eachAttributes.put(header[i1].trim(), Double.parseDouble(attributes[i1]));
                    } else {
                        eachAttributes.put(header[i1].trim(), 0.0);
                    }
                }
            }
        }
        return attributesMap;
    }

    public static double getMobHealAmplifier(Mob mob) {
        double value = 1;
        value += StableAttributesModifier.getModifierValue(mob, StableAttributesModifier.mobHealAmplifierModifier);
        return value;
    }
}
