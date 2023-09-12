package com.Very.very.Series.EndSeries.EventControl.NetherRecall1;

import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.world.item.ArmorItem;

public class ArmorNetherRecall extends ArmorItem {
    private float Defence = 500.0F;
    private float ManaDefence = 50.0F;
    public ArmorNetherRecall(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties());
        Utils.Defence.put(this,this.Defence);
        Utils.ManaDefence.put(this,this.ManaDefence);
        Utils.MobLevel.put(this,90f);
    }
}
