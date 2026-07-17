package fun.wraq.common.impl.damage;

import fun.wraq.common.equip.WraqCurios;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface BeforeCauseFinalDamageCurios {
    void onBeforeCauseFinalDamage(Player player, Mob mob, double damage);

    static void beforeCauseFinalDamage(Player player, Mob mob, double damage) {
        WraqCurios.CuriosAttribute.getDistinctCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof BeforeCauseFinalDamageCurios)
                .map(stack -> (BeforeCauseFinalDamageCurios) stack.getItem())
                .forEach(curios -> curios.onBeforeCauseFinalDamage(player, mob, damage));
    }
}
