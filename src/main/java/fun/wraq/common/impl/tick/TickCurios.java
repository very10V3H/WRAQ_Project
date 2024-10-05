package fun.wraq.common.impl.tick;

import fun.wraq.common.Compute;
import net.minecraft.world.entity.player.Player;

public interface TickCurios {
    void tick(Player player);

    static void tickEvent(Player player) {
        Compute.CuriosAttribute.getDistinctCuriosList(player)
                .stream().filter(itemStack -> itemStack.getItem() instanceof TickCurios)
                .map(itemStack -> (TickCurios) itemStack.getItem())
                .forEach(curios -> curios.tick(player));
    }
}
