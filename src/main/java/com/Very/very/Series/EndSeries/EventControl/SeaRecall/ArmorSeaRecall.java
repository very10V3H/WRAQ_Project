package com.Very.very.Series.EndSeries.EventControl.SeaRecall;

import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.world.item.ArmorItem;

public class ArmorSeaRecall extends ArmorItem {
    private float Defence = 500.0F;
    private float ManaDefence = 50.0F;
    public ArmorSeaRecall(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties());
        Utils.Defence.put(this,this.Defence);
        Utils.ManaDefence.put(this,this.ManaDefence);
        Utils.MobLevel.put(this,90f);
    }
}
