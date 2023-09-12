package com.Very.very.Items.MobItem;

import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.world.item.ArmorItem;

public class MobArmor extends ArmorItem {

    public MobArmor(ItemMaterial Material, Type Slots,
                    float Defence, float ManaDefence, float MobLevel, float BreakDefence,
                    float BreakDefence0, float CritRate, float CritDamage, float HealSteal) {
        super(Material,Slots,new Properties());
        Utils.Defence.put(this,Defence);
        Utils.ManaDefence.put(this,ManaDefence);
        Utils.MobLevel.put(this,MobLevel);
        Utils.BreakDefence.put(this,BreakDefence);
        Utils.BreakDefence0.put(this,BreakDefence0);
        Utils.CriticalHitRate.put(this,CritRate);
        Utils.CHitDamage.put(this,CritDamage);
        Utils.HealSteal.put(this,HealSteal);
    }

    public MobArmor(float Defence, float ManaDefence, float MobLevel) {
        super(ItemMaterial.MaterialForArmor2, Type.HELMET,new Properties());
        Utils.Defence.put(this,Defence);
        Utils.ManaDefence.put(this,ManaDefence);
        Utils.MobLevel.put(this,MobLevel);
        Utils.BreakDefence.put(this,0f);
        Utils.BreakDefence0.put(this,0f);
        Utils.CriticalHitRate.put(this,0f);
        Utils.CHitDamage.put(this,0f);
        Utils.HealSteal.put(this,0f);
    }

    public MobArmor(ItemMaterial Material, Type Slots, float Defence, float ManaDefence, float MobLevel) {
        super(Material, Slots,new Properties());
        Utils.Defence.put(this,Defence);
        Utils.ManaDefence.put(this,ManaDefence);
        Utils.MobLevel.put(this,MobLevel);
        Utils.BreakDefence.put(this,0f);
        Utils.BreakDefence0.put(this,0f);
        Utils.CriticalHitRate.put(this,0f);
        Utils.CHitDamage.put(this,0f);
        Utils.HealSteal.put(this,0f);
    }

}
