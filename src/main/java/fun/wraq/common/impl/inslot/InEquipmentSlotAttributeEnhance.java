package fun.wraq.common.impl.inslot;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.Map;

public interface InEquipmentSlotAttributeEnhance {
    double getAttributeValue(Player player);

    Map<Item, Double> baseAttributeMap();

    static double getAttribute(Item item, Player player, Map<Item, Double> baseAttributeMap) {
        if (item instanceof InEquipmentSlotAttributeEnhance inEquipmentSlotAttributeEnhance
                && baseAttributeMap.equals(inEquipmentSlotAttributeEnhance.baseAttributeMap())) {
            return inEquipmentSlotAttributeEnhance.getAttributeValue(player);
        }
        return 0;
    }
}
