package fun.wraq.common.impl.onhit;

import fun.wraq.common.Compute;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface OnArrowHitEffectCurios {
    void onHit(Player player, Mob mob);

    static void hit(Player player, Mob mob) {
        Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof OnArrowHitEffectCurios)
                .map(stack -> (OnArrowHitEffectCurios) stack.getItem())
                .forEach(curios -> {
                    curios.onHit(player, mob);
                });
    }
}
