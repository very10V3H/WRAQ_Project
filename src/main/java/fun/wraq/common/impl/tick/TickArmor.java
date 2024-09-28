package fun.wraq.common.impl.tick;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;

public interface TickArmor {
    void tick(Player player);

    static void handleTick(Player player) {
        Set<Class<? extends TickArmor>> set = new HashSet<>();
        InventoryOperation.getArmors(player).stream()
                .filter(stack -> stack.getItem() instanceof TickArmor)
                .map(stack -> (TickArmor) stack.getItem())
                .forEach(tickArmor -> {
                    if (!set.contains(tickArmor.getClass())) {
                        set.add(tickArmor.getClass());
                        tickArmor.tick(player);
                    }
                });
    }
}
