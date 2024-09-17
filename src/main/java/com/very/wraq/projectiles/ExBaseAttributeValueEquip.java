package com.very.wraq.projectiles;

import net.minecraft.world.item.Item;

import java.util.Map;

public interface ExBaseAttributeValueEquip {
    String getTag();
    double getValueByTagValue(double tagValue);
    Map<Item, Double> getBaseAttributeMap();
}
