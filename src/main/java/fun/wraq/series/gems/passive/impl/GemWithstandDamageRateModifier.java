package fun.wraq.series.gems.passive.impl;

import fun.wraq.series.gems.WraqGem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface GemWithstandDamageRateModifier {
    double getModifiedRate(Player player, Mob mob, double damage);

    static double onWithstandDamageRate(Player player, Mob mob, double damage) {
        return 1 + WraqGem.getPlayerDistinctEquipGemsSet(player)
                .stream().filter(gem -> gem instanceof GemWithstandDamageRateModifier)
                .mapToDouble(gem -> ((GemWithstandDamageRateModifier) gem).getModifiedRate(player, mob, damage))
                .sum();
    }
}
