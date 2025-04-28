package fun.wraq.common.impl.onkill;

import fun.wraq.common.Compute;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface OnKillEffectCurios {
    void onKill(Player player, Mob mob, ItemStack stack);

    static void kill(Player player, Mob mob) {
        Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof OnKillEffectCurios)
                .forEach(stack -> ((OnKillEffectCurios) stack.getItem()).onKill(player, mob, stack));
    }
}
