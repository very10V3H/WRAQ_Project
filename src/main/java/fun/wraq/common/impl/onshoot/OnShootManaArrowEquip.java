package fun.wraq.common.impl.onshoot;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.player.Player;

public interface OnShootManaArrowEquip {
    void onShoot(Player player);

    static void shoot(Player player) {
        InventoryOperation.getDistinctAllEquipSlotItems(player).stream()
                .filter(stack -> stack.getItem() instanceof OnShootManaArrowEquip)
                .map(stack -> (OnShootManaArrowEquip) stack.getItem())
                .forEach(equip -> {
                    equip.onShoot(player);
                });
    }
}
