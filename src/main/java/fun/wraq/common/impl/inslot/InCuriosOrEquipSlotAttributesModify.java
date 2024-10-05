package fun.wraq.common.impl.inslot;

import fun.wraq.common.Compute;
import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

public interface InCuriosOrEquipSlotAttributesModify {

    record Attribute(Map<Item, Double> baseAttributeMap, double attributeValue) {}

    List<Attribute> getAttributes(Player player);

    static double getAttributes(Player player, Map<Item, Double> baseAttributeMap) {
        double value = 0;
        for (ItemStack stack : Compute.CuriosAttribute.getDistinctCuriosList(player)) {
            Item item = stack.getItem();
            if (item instanceof InCuriosOrEquipSlotAttributesModify curios) {
                for (Attribute attribute : curios.getAttributes(player)) {
                    if (baseAttributeMap.equals(attribute.baseAttributeMap)) {
                        value += attribute.attributeValue;
                    }
                }
            }
        }
        for (ItemStack equip : InventoryOperation.getDistinctAllEquipSlotItems(player)) {
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
