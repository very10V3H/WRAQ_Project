package fun.wraq.common.impl.inslot;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.util.Utils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InCuriosOrEquipSlotAttributesModify {

    record Attribute(Map<Item, Double> baseAttributeMap, double attributeValue) {}

    List<Attribute> getAttributes(Player player);

    static double getAttributes(Player player, Map<Item, Double> baseAttributeMap) {
        double value = 0;
        Set<Class<? extends Item>> set = new HashSet<>();
        for (ItemStack stack : Utils.playerCuriosListMap.getOrDefault(player, List.of())) {
            Item item = stack.getItem();
            if (!set.contains(item.getClass()) && item instanceof InCuriosOrEquipSlotAttributesModify curios) {
                for (Attribute attribute : curios.getAttributes(player)) {
                    if (baseAttributeMap.equals(attribute.baseAttributeMap)) {
                        value += attribute.attributeValue;
                    }
                }
                set.add(item.getClass());
            }
        }
        for (ItemStack equip : PlayerAttributes.getAllEquipSlotItems(player)) {
            if (equip.getItem() instanceof InCuriosOrEquipSlotAttributesModify item) {
                for (Attribute attribute : item.getAttributes(player)) {
                    if (baseAttributeMap.equals(attribute.baseAttributeMap)) {
                        value += attribute.attributeValue;
                    }
                }
            }
        }
        return value;
    }
}
