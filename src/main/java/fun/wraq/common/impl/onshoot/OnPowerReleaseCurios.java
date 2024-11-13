package fun.wraq.common.impl.onshoot;

import fun.wraq.common.Compute;
import net.minecraft.world.entity.player.Player;

public interface OnPowerReleaseCurios {
    void onRelease(Player player);

    static void release(Player player) {
        Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof OnPowerReleaseCurios)
                .map(stack -> (OnPowerReleaseCurios) stack.getItem())
                .forEach(curios -> curios.onRelease(player));
    }
}
