package fun.wraq.common.impl.onhit;

import fun.wraq.common.Compute;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicReference;

public interface OnHitDamageInfluenceCurios {
    double onHitDamageInfluence(Player player, Mob mob);

    static double damageInfluence(Player player, Mob mob) {
        AtomicReference<Double> influence = new AtomicReference<>((double) 0);
        Compute.CuriosAttribute.getCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof OnHitDamageInfluenceCurios)
                .map(stack -> (OnHitDamageInfluenceCurios) stack.getItem())
                .forEach(curios -> {
                    influence.updateAndGet(v -> (v + curios.onHitDamageInfluence(player, mob)));
                });
        return influence.get();
    }
}
