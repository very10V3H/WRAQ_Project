package fun.wraq.Items.MobItem;

import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.MobArmorNum;
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

    public MobArmor(ItemMaterial Material, Type Slots, double AttackDamage,
                    double Defence, double ManaDefence, double MobLevel, double BreakDefence,
                    double BreakDefence0, double CritRate, double CritDamage, double HealSteal) {
        super(Material, Slots, new Properties());
        Utils.defence.put(this, Defence);
        Utils.manaDefence.put(this, ManaDefence);
        Utils.mobLevel.put(this, MobLevel);
        Utils.defencePenetration.put(this, BreakDefence);
        Utils.defencePenetration0.put(this, BreakDefence0);
        Utils.critRate.put(this, CritRate);
        Utils.critDamage.put(this, CritDamage);
        Utils.healthSteal.put(this, HealSteal);
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
        super(ItemMaterial.BasicArmor2, Type.HELMET, new Properties());
        Utils.defence.put(this, Defence);
        Utils.manaDefence.put(this, ManaDefence);
        Utils.mobLevel.put(this, MobLevel);
        Utils.defencePenetration.put(this, 0d);
        Utils.defencePenetration0.put(this, 0d);
        Utils.critRate.put(this, 0d);
        Utils.critDamage.put(this, 0d);
        Utils.healthSteal.put(this, 0d);
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
        super(Material, Slots, new Properties());
        Utils.defence.put(this, Defence);
        Utils.manaDefence.put(this, ManaDefence);
        Utils.mobLevel.put(this, MobLevel);
        Utils.defencePenetration.put(this, 0d);
        Utils.defencePenetration0.put(this, 0d);
        Utils.critRate.put(this, 0d);
        Utils.critDamage.put(this, 0d);
        Utils.healthSteal.put(this, 0d);
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
        super(Material, Slots, new Properties());
        MobArmorNum mobArmorNum = MobArmorNum.mobArmorNumHashMap.get(MobName);
        Utils.defence.put(this, mobArmorNum.Defence);
        Utils.manaDefence.put(this, mobArmorNum.ManaDefence);
        Utils.mobLevel.put(this, mobArmorNum.MobLevel);
        Utils.defencePenetration.put(this, mobArmorNum.DefencePenetration);
        Utils.defencePenetration0.put(this, mobArmorNum.DefencePenetration0);
        Utils.critRate.put(this, mobArmorNum.CriticalHitRate);
        Utils.critDamage.put(this, mobArmorNum.CritDamage);
        Utils.healthSteal.put(this, mobArmorNum.HealSteal);
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
        super(ItemMaterial.BasicArmor2, Type.HELMET, new Properties());
        MobArmorNum mobArmorNum = MobArmorNum.mobArmorNumHashMap.get(MobName);
        Utils.defence.put(this, mobArmorNum.Defence);
        Utils.manaDefence.put(this, mobArmorNum.ManaDefence);
        Utils.mobLevel.put(this, mobArmorNum.MobLevel);
        Utils.defencePenetration.put(this, mobArmorNum.DefencePenetration);
        Utils.defencePenetration0.put(this, mobArmorNum.DefencePenetration0);
        Utils.critRate.put(this, mobArmorNum.CriticalHitRate);
        Utils.critDamage.put(this, mobArmorNum.CritDamage);
        Utils.healthSteal.put(this, mobArmorNum.HealSteal);
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
