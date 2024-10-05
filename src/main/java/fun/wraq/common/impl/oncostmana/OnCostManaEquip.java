package fun.wraq.common.impl.oncostmana;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.player.Player;

public interface OnCostManaEquip {
    void onCostMana(Player player, double costManaValue);

    static void costMana(Player player, double costManaValue) {
        InventoryOperation.getDistinctAllEquipSlotItems(player)
                .stream().filter(stack -> stack.getItem() instanceof OnCostManaEquip)
                .map(stack -> (OnCostManaEquip) stack.getItem())
                .forEach(onCostManaEquip -> onCostManaEquip.onCostMana(player, costManaValue));
    }
}
