package fun.wraq.common.impl.damage;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface BattleDamageInfluenceEquip {
    double rate(Player player, Mob mob);

    static double getRate(Player player, Mob mob) {
        return InventoryOperation.getDistinctAllEquipSlotItems(player)
                .stream().filter(itemStack -> itemStack.getItem() instanceof BattleDamageInfluenceEquip)
                .map(itemStack -> (BattleDamageInfluenceEquip) itemStack.getItem())
                .mapToDouble(curios -> curios.rate(player, mob)).sum();
    }
}
