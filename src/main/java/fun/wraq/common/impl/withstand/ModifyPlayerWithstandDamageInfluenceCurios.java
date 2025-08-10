package fun.wraq.common.impl.withstand;

import fun.wraq.common.Compute;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface ModifyPlayerWithstandDamageInfluenceCurios {
    double modifyWithstandDamage(Player player, Mob mob);

    static double modifyPlayerWithstandDamageRate(Player player, Mob mob) {
        return Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof ModifyPlayerWithstandDamageInfluenceCurios)
                .mapToDouble(stack
                        -> ((ModifyPlayerWithstandDamageInfluenceCurios) stack.getItem()).modifyWithstandDamage(player, mob))
                .sum();
    }
}
