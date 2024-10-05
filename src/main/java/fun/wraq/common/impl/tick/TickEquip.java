package fun.wraq.common.impl.tick;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.player.Player;

public interface TickEquip {
    void tick(Player player);

    static void handleTick(Player player) {
        InventoryOperation.getDistinctAllEquipSlotItems(player).stream()
                .filter(stack -> stack.getItem() instanceof TickEquip)
                .map(stack -> (TickEquip) stack.getItem())
                .forEach(tickArmor -> {
                    tickArmor.tick(player);
                });
    }
}
