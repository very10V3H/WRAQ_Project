package fun.wraq.common.impl.damage;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface DamageInfluenceEquip {
    double rate(Player player);

    static double getRate(Player player) {
        double rate = 0;
        for (ItemStack armor : InventoryOperation.getDistinctAllEquipSlotItems(player)) {
            if (armor.getItem() instanceof DamageInfluenceEquip armorItem) {
                rate += armorItem.rate(player);
            }
        }
        return rate;
    }
}
