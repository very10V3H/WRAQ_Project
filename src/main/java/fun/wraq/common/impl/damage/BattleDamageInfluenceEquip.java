package fun.wraq.common.impl.damage;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface BattleDamageInfluenceEquip {
    double rate(Player player, Mob mob);

    static double getRate(Player player, Mob mob) {
        double rate = 0;
        for (ItemStack armor : InventoryOperation.getAllEquipSlotItems(player)) {
            if (armor.getItem() instanceof BattleDamageInfluenceEquip armorItem) {
                rate += armorItem.rate(player, mob);
            }
        }
        return rate;
    }
}
