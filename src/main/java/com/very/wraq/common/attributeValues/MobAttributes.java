package com.very.wraq.common.attributeValues;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.element.equipAndCurios.waterElement.WaterElementSword;
import com.very.wraq.process.system.tower.TowerMob;
import com.very.wraq.series.overworld.sakuraSeries.EarthMana.EarthPower;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;

public class MobAttributes {
    public static double defence(Mob monster) {
        int tickCount = monster.getServer().getTickCount();
        double defence = MobSpawn.MobBaseAttributes.defence.getOrDefault(MobSpawn.getMobOriginName(monster), 0d);
        double exDefence = 0;
        double rate = 1;

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

        rate *= WaterElementSword.MobDefenceDecrease(monster);
        rate *= Element.ElementDefenceDecrease(monster);

        defence *= rate;
        // 固定
        exDefence += TowerMob.mobDefenceUp(monster);
        if (Utils.shipSwordEffect.containsKey(monster) && Utils.shipSwordTime.get(monster) > tickCount) {
            exDefence -= Utils.shipSwordEffect.get(monster) * 250;
        }

        return Math.max(defence + exDefence, 0);
    }

    public static double manaDefence(Mob monster) {
        int tick = monster.getServer().getTickCount();
        double defence = MobSpawn.MobBaseAttributes.manaDefence.getOrDefault(MobSpawn.getMobOriginName(monster), 0d);
        double exDefence = 0;
        double rate = 1;

        // 百分比
        if (Utils.LakePowerEffectMobMap.containsKey(monster) && Utils.LakePowerEffectMobMap.get(monster).getTick() > tick)
            rate *= (1 - Utils.LakePowerEffectMobMap.get(monster).getEffect() * 0.05);

        if (Utils.MobSpringManaTick.containsKey(monster) && Utils.MobSpringManaTick.get(monster) > tick)
            rate *= (1 - Utils.SpringEffect[Utils.MobSpringManaEffect.get(monster) - 1]);

        if (Utils.NetherBoneMealPowerEffectMap.containsKey(monster) && Utils.NetherBoneMealPowerEffectMap.get(monster) > tick)
            rate *= 0.5;

        if (Utils.WitherBookMobEffectTick.containsKey(monster) && Utils.WitherBookMobEffectTick.get(monster) > tick)
            rate *= 0.5;

        rate *= Element.ElementDefenceDecrease(monster);
        rate *= WaterElementSword.MobDefenceDecrease(monster);

        defence *= rate;

        // 固定
        exDefence += defence * EarthPower.MobManaDefenceDecrease(monster); // 地蕴法术
        exDefence += TowerMob.mobManaDefenceUp(monster);
        return Math.max(defence + exDefence, 0);
    }

    public static double attackDamage(Mob mob) {
        double baseValue = MobSpawn.MobBaseAttributes.attackDamage.getOrDefault(MobSpawn.getMobOriginName(mob), 0d);
        double exValue = 0;
        return baseValue + exValue;
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

}
