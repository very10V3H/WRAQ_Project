package fun.wraq.common.impl.onhit;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface OnHitEffectEquip {
    void onHit(Player player, Mob mob);

    static void hit(Player player, Mob mob) {
        InventoryOperation.getDistinctAllEquipSlotItems(player)
                .stream()
                .filter(stack -> stack.getItem() instanceof OnHitEffectEquip)
                .map(stack -> (OnHitEffectEquip) stack.getItem())
                .forEach(equip -> {
                    equip.onHit(player, mob);
                });
    }
}
