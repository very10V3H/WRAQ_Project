package fun.wraq.common.impl.tick;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashSet;
import java.util.Set;

public interface TickCurios {
    void tick(Player player);

    static void tickEvent(Player player) {
        Set<Class<? extends Item>> set = new HashSet<>();
        HashSet<Item> curiosSet = new HashSet<>();

        CuriosApi.getCuriosInventory(player).ifPresent(inv -> {
            for (int i = 0; i < inv.getEquippedCurios().getSlots(); i++) {
                Item item = inv.getEquippedCurios().getStackInSlot(i).getItem();
                curiosSet.add(item);
            }
        });

        curiosSet.forEach(item -> {
            if (!set.contains(item.getClass())) {
                set.add(item.getClass());
                if (item instanceof TickCurios curios) {
                    curios.tick(player);
                }
            }
        });
    }
}