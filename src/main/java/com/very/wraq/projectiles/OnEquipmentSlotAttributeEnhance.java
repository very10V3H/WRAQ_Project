package com.very.wraq.projectiles;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.Map;

public interface OnEquipmentSlotAttributeEnhance {
    double getAttributeValue(Player player);

    Map<Item, Double> baseAttributeMap();

    static double getAttribute(Item item, Player player, Map<Item, Double> baseAttributeMap) {
        if (item instanceof OnEquipmentSlotAttributeEnhance onEquipmentSlotAttributeEnhance
                && baseAttributeMap.equals(onEquipmentSlotAttributeEnhance.baseAttributeMap())) {
            return onEquipmentSlotAttributeEnhance.getAttributeValue(player);
        }
        return 0;
    }
}
