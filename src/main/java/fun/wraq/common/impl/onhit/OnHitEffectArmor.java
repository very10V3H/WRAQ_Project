package fun.wraq.common.impl.onhit;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;

public interface OnHitEffectArmor {
    void onHit(Player player, Mob mob);

    static void hit(Player player, Mob mob) {
        Set<Class<? extends OnHitEffectArmor>> usedItems = new HashSet<>();
        InventoryOperation.getArmors(player)
                .stream()
                .filter(stack -> stack.getItem() instanceof OnHitEffectArmor)
                .map(stack -> (OnHitEffectArmor) stack.getItem())
                .forEach(equip -> {
                    if (!usedItems.contains(equip.getClass())) {
                        equip.onHit(player, mob);
                        usedItems.add(equip.getClass());
                    }
                });
    }
}
