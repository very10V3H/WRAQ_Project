package fun.wraq.series.gems.passive.impl;

import fun.wraq.series.gems.WraqGem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface GemCommonDamageEnhanceRateModifier {
    double getModifiedRate(Player player, Mob mob);

    static double getCommonDamageEnhanceRate(Player player, Mob mob) {
        return WraqGem.getPlayerDistinctEquipGemsSet(player)
                .stream().filter(gem -> gem instanceof GemCommonDamageEnhanceRateModifier)
                .mapToDouble(gem -> ((GemCommonDamageEnhanceRateModifier) gem).getModifiedRate(player, mob))
                .sum();
    }
}
