package com.very.wraq.series.end.eventController.SnowRecall;

import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import net.minecraft.world.item.ArmorItem;

public class ArmorSnowRecall extends ArmorItem {
    private double Defence = 500.0d;
    private double ManaDefence = 50.0d;
    public ArmorSnowRecall(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties());
        Utils.Defence.put(this,this.Defence);
        Utils.ManaDefence.put(this,this.ManaDefence);
        Utils.MobLevel.put(this,90d);
    }
}
