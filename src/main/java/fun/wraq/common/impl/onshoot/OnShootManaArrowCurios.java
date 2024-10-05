package fun.wraq.common.impl.onshoot;

import fun.wraq.common.Compute;
import net.minecraft.world.entity.player.Player;

public interface OnShootManaArrowCurios {
    void onShoot(Player player);

    static void shoot(Player player) {
        Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof OnShootManaArrowCurios)
                .map(stack -> (OnShootManaArrowCurios) stack.getItem())
                .forEach(curios -> curios.onShoot(player));
    }
}
