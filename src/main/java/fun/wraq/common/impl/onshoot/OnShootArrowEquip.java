package fun.wraq.common.impl.onshoot;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.player.Player;

public interface OnShootArrowEquip {
    void onShoot(Player player);

    static void shoot(Player player) {
        InventoryOperation.getDistinctAllEquipSlotItems(player).stream()
                .filter(stack -> stack.getItem() instanceof OnShootArrowEquip)
                .map(stack -> (OnShootArrowEquip) stack.getItem())
                .forEach(equip -> {
                    equip.onShoot(player);
                });
    }
}
