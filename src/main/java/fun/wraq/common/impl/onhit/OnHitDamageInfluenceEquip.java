package fun.wraq.common.impl.onhit;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicReference;

public interface OnHitDamageInfluenceEquip {
    double onHitDamageInfluence(Player player, Mob mob);

    static double damageInfluence(Player player, Mob mob) {
        AtomicReference<Double> influence = new AtomicReference<>((double) 0);
        InventoryOperation.getDistinctAllEquipSlotItems(player).stream()
                .filter(stack -> stack.getItem() instanceof OnHitDamageInfluenceEquip)
                .map(stack -> (OnHitDamageInfluenceEquip) stack.getItem())
                .forEach(curios -> {
                    influence.updateAndGet(v -> (v + curios.onHitDamageInfluence(player, mob)));
                });
        return influence.get();
    }
}
