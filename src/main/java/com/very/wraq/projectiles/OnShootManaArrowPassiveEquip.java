package com.very.wraq.projectiles;

import com.very.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.player.Player;

public interface OnShootManaArrowPassiveEquip {
    void onShoot(Player player);

    static void shoot(Player player) {
        InventoryOperation.getPassiveEquip(player)
                .stream()
                .filter(stack -> stack.getItem() instanceof OnShootManaArrowPassiveEquip)
                .map(stack -> (OnShootManaArrowPassiveEquip) stack.getItem())
                .forEach(equip -> equip.onShoot(player));
    }
}
