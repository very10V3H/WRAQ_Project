package com.Very.very.Series.EndSeries.EventControl.ForestRecall;

import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.world.item.ArmorItem;

public class ArmorForestRecall extends ArmorItem {
    private float Defence = 800.0F;
    private float ManaDefence = 50.0F;
    public ArmorForestRecall(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties());
        Utils.Defence.put(this,this.Defence);
        Utils.ManaDefence.put(this,this.ManaDefence);
        Utils.MobLevel.put(this,90f);
    }
}
