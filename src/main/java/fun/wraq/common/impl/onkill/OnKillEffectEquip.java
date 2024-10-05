package fun.wraq.common.impl.onkill;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface OnKillEffectEquip {
    void onKill(Player player, Mob mob);

    static void kill(Player player, Mob mob) {
        InventoryOperation.getDistinctAllEquipSlotItems(player)
                .stream()
                .filter(stack -> stack.getItem() instanceof OnKillEffectEquip)
                .map(stack -> (OnKillEffectEquip) stack.getItem())
                .forEach(equip -> {
                    equip.onKill(player, mob);
                });
    }
}
