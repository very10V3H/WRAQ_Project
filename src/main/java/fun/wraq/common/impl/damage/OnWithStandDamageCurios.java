package fun.wraq.common.impl.damage;

import fun.wraq.common.Compute;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface OnWithStandDamageCurios {
    void onWithStandDamage(Player player, Mob mob, double damage);

    static void withStandDamage(Player player, Mob mob, double damage) {
        Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof OnWithStandDamageCurios)
                .map(stack -> (OnWithStandDamageCurios) stack.getItem())
                .forEach(curios -> curios.onWithStandDamage(player, mob, damage));
    }
}
