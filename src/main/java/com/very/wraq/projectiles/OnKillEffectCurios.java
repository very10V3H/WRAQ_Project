package com.very.wraq.projectiles;

import com.very.wraq.common.Compute;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface OnKillEffectCurios {
    void onKill(Player player, Mob mob);

    static void kill(Player player, Mob mob) {
        Compute.CuriosAttribute.getCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof OnKillEffectCurios)
                .map(stack -> (OnKillEffectCurios) stack.getItem())
                .forEach(curios -> curios.onKill(player, mob));
    }
}
