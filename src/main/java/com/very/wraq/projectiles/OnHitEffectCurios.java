package com.very.wraq.projectiles;

import com.very.wraq.common.Compute;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface OnHitEffectCurios {
    void onHit(Player player, Mob mob);

    static void hit(Player player, Mob mob) {
        Compute.CuriosAttribute.getCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof OnHitEffectCurios)
                .map(stack -> (OnHitEffectCurios) stack.getItem())
                .forEach(curios -> curios.onHit(player, mob));
    }
}
