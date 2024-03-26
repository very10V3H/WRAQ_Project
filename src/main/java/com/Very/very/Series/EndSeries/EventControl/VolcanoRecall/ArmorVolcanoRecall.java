package com.Very.very.Series.EndSeries.EventControl.VolcanoRecall;

import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.world.item.ArmorItem;

public class ArmorVolcanoRecall extends ArmorItem {
    private double Defence = 800.0d;
    private double ManaDefence = 50.0d;
    public ArmorVolcanoRecall(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties());
        Utils.Defence.put(this,this.Defence);
        Utils.ManaDefence.put(this,this.ManaDefence);
        Utils.MobLevel.put(this,90d);
    }
}
