package fun.wraq.common.impl.damage;

import fun.wraq.common.equip.WraqCurios;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface OnCauseFinalDamageCurios {
    void onCauseFinalDamage(Player player, Mob mob, double damage);

    static void causeFinalDamage(Player player, Mob mob, double damage) {
        WraqCurios.CuriosAttribute.getDistinctCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof OnCauseFinalDamageCurios)
                .map(stack -> (OnCauseFinalDamageCurios) stack.getItem())
                .forEach(curios -> curios.onCauseFinalDamage(player, mob, damage));
    }
}
