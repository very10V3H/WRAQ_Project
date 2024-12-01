package fun.wraq.common.impl.damage;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface OnCauseFinalDamageEquip {
    void onCauseFinalDamage(Player player, Mob mob, double damage);

    static void causeFinalDamage(Player player, Mob mob, double damage) {
        InventoryOperation.getAllEquipSlotItems(player).stream()
                .filter(stack -> stack.getItem() instanceof OnCauseFinalDamageEquip)
                .map(stack -> (OnCauseFinalDamageEquip) stack.getItem())
                .forEach(curios -> curios.onCauseFinalDamage(player, mob, damage));
    }
}
