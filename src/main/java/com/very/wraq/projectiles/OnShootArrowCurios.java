package com.very.wraq.projectiles;

import com.very.wraq.common.Compute;
import net.minecraft.world.entity.player.Player;

public interface OnShootArrowCurios {
    void onShoot(Player player);

    static void shoot(Player player) {
        Compute.CuriosAttribute.getCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof OnShootArrowCurios)
                .map(stack -> (OnShootArrowCurios) stack.getItem())
                .forEach(curios -> curios.onShoot(player));
    }
}
