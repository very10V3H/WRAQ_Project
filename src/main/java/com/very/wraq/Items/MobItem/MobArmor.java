package com.very.wraq.Items.MobItem;

import com.very.wraq.valueAndTools.Utils.Struct.MobArmorNum;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import net.minecraft.world.item.ArmorItem;

public class MobArmor extends ArmorItem {
    public final double AttackDamage;
    public final double Defence;
    public final double ManaDefence;
    public final double MobLevel;
    public final double DefencePenetration;
    public final double DefencePenetration0;
    public final double CritRate;
    public final double CritDamage;
    public final double HealthSteal;
    public MobArmor(ItemMaterial Material, Type Slots,double AttackDamage,
                    double Defence, double ManaDefence, double MobLevel, double BreakDefence,
                    double BreakDefence0, double CritRate, double CritDamage, double HealSteal) {
        super(Material,Slots,new Properties());
        Utils.Defence.put(this,Defence);
        Utils.ManaDefence.put(this,ManaDefence);
        Utils.MobLevel.put(this,MobLevel);
        Utils.DefencePenetration.put(this,BreakDefence);
        Utils.DefencePenetration0.put(this,BreakDefence0);
        Utils.CritRate.put(this,CritRate);
        Utils.CritDamage.put(this,CritDamage);
        Utils.HealthSteal.put(this,HealSteal);
        this.AttackDamage = AttackDamage;
        this.Defence = Defence;
        this.ManaDefence = ManaDefence;
        this.MobLevel = MobLevel;
        this.DefencePenetration = BreakDefence;
        this.DefencePenetration0 = BreakDefence0;
        this.CritRate = CritRate;
        this.CritDamage = CritDamage;
        this.HealthSteal = HealSteal;
    }

    public MobArmor(double Defence, double ManaDefence, double MobLevel) {
        super(ItemMaterial.MaterialForArmor2, Type.HELMET,new Properties());
        Utils.Defence.put(this,Defence);
        Utils.ManaDefence.put(this,ManaDefence);
        Utils.MobLevel.put(this,MobLevel);
        Utils.DefencePenetration.put(this,0d);
        Utils.DefencePenetration0.put(this,0d);
        Utils.CritRate.put(this,0d);
        Utils.CritDamage.put(this,0d);
        Utils.HealthSteal.put(this,0d);
        this.AttackDamage = 0;
        this.Defence = Defence;
        this.ManaDefence = ManaDefence;
        this.MobLevel = MobLevel;
        this.DefencePenetration = 0;
        this.DefencePenetration0 = 0;
        this.CritRate = 0;
        this.CritDamage = 0;
        this.HealthSteal = 0;
    }

    public MobArmor(ItemMaterial Material, Type Slots, double Defence, double ManaDefence, double MobLevel) {
        super(Material, Slots,new Properties());
        Utils.Defence.put(this,Defence);
        Utils.ManaDefence.put(this,ManaDefence);
        Utils.MobLevel.put(this,MobLevel);
        Utils.DefencePenetration.put(this,0d);
        Utils.DefencePenetration0.put(this,0d);
        Utils.CritRate.put(this,0d);
        Utils.CritDamage.put(this,0d);
        Utils.HealthSteal.put(this,0d);
        this.AttackDamage = 0;
        this.Defence = Defence;
        this.ManaDefence = ManaDefence;
        this.MobLevel = MobLevel;
        this.DefencePenetration = 0;
        this.DefencePenetration0 = 0;
        this.CritRate = 0;
        this.CritDamage = 0;
        this.HealthSteal = 0;
    }
    public MobArmor(ItemMaterial Material, Type Slots, String MobName) {
        super(Material, Slots,new Properties());
        MobArmorNum mobArmorNum = MobArmorNum.mobArmorNumHashMap.get(MobName);
        Utils.Defence.put(this,mobArmorNum.Defence);
        Utils.ManaDefence.put(this,mobArmorNum.ManaDefence);
        Utils.MobLevel.put(this,mobArmorNum.MobLevel);
        Utils.DefencePenetration.put(this,mobArmorNum.DefencePenetration);
        Utils.DefencePenetration0.put(this,mobArmorNum.DefencePenetration0);
        Utils.CritRate.put(this,mobArmorNum.CriticalHitRate);
        Utils.CritDamage.put(this,mobArmorNum.CritDamage);
        Utils.HealthSteal.put(this,mobArmorNum.HealSteal);
        this.AttackDamage = mobArmorNum.AttackDamage;
        this.Defence = mobArmorNum.Defence;
        this.ManaDefence = mobArmorNum.ManaDefence;
        this.MobLevel = mobArmorNum.MobLevel;
        this.DefencePenetration = mobArmorNum.DefencePenetration;
        this.DefencePenetration0 = mobArmorNum.DefencePenetration0;
        this.CritRate = mobArmorNum.CriticalHitRate;
        this.CritDamage = mobArmorNum.CritDamage;
        this.HealthSteal = mobArmorNum.HealSteal;
    }
    public MobArmor(String MobName) {
        super(ItemMaterial.MaterialForArmor2, Type.HELMET,new Properties());
        MobArmorNum mobArmorNum = MobArmorNum.mobArmorNumHashMap.get(MobName);
        Utils.Defence.put(this,mobArmorNum.Defence);
        Utils.ManaDefence.put(this,mobArmorNum.ManaDefence);
        Utils.MobLevel.put(this,mobArmorNum.MobLevel);
        Utils.DefencePenetration.put(this,mobArmorNum.DefencePenetration);
        Utils.DefencePenetration0.put(this,mobArmorNum.DefencePenetration0);
        Utils.CritRate.put(this,mobArmorNum.CriticalHitRate);
        Utils.CritDamage.put(this,mobArmorNum.CritDamage);
        Utils.HealthSteal.put(this,mobArmorNum.HealSteal);
        this.AttackDamage = mobArmorNum.AttackDamage;
        this.Defence = mobArmorNum.Defence;
        this.ManaDefence = mobArmorNum.ManaDefence;
        this.MobLevel = mobArmorNum.MobLevel;
        this.DefencePenetration = mobArmorNum.DefencePenetration;
        this.DefencePenetration0 = mobArmorNum.DefencePenetration0;
        this.CritRate = mobArmorNum.CriticalHitRate;
        this.CritDamage = mobArmorNum.CritDamage;
        this.HealthSteal = mobArmorNum.HealSteal;
    }

}
