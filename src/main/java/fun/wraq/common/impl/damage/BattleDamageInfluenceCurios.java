package fun.wraq.common.impl.damage;

import fun.wraq.common.Compute;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface BattleDamageInfluenceCurios {
    double rate(Player player, Mob mob);

    static double getRate(Player player, Mob mob) {
        return Compute.CuriosAttribute.getDistinctCuriosList(player)
                .stream().filter(itemStack -> itemStack.getItem() instanceof BattleDamageInfluenceCurios)
                .map(itemStack -> (BattleDamageInfluenceCurios) itemStack.getItem())
                .mapToDouble(curios -> curios.rate(player, mob)).sum();
    }
}
