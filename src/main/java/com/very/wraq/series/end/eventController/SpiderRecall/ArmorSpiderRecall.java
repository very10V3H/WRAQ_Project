package com.very.wraq.series.end.eventController.SpiderRecall;

import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import net.minecraft.world.item.ArmorItem;

public class ArmorSpiderRecall extends ArmorItem {
    private double Defence = 500.0d;
    private double ManaDefence = 50.0d;
    public ArmorSpiderRecall(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties());
        Utils.Defence.put(this,this.Defence);
        Utils.ManaDefence.put(this,this.ManaDefence);
        Utils.MobLevel.put(this,90d);
    }
}
