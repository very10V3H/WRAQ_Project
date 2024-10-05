package fun.wraq.common.impl.onhit;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface OnPowerCauseDamageEquip {
    void onCauseDamage(Player player, Mob mob);

    static void causeDamage(Player player, Mob mob) {
        InventoryOperation.getDistinctAllEquipSlotItems(player)
                .stream().filter(itemStack -> itemStack.getItem() instanceof OnPowerCauseDamageEquip)
                .map(itemStack -> (OnPowerCauseDamageEquip) itemStack.getItem())
                .forEach(equip -> {
                    equip.onCauseDamage(player, mob);
                });
    }
}
