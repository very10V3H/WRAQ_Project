package fun.wraq.series.gems.passive.impl;

import fun.wraq.series.gems.WraqGem;
import net.minecraft.world.entity.player.Player;

public interface GemEnhanceSkillRate {
    int getType(Player player);

    double getEnhanceRate(Player player);

    static double getEnhanceRate(Player player, int type) {
        return WraqGem.getPlayerAllEquipGems(player)
                .stream().filter(gem -> gem instanceof GemEnhanceSkillRate gemEnhanceSkillRate
                        && gemEnhanceSkillRate.getType(player) == type)
                .mapToDouble(gem -> ((GemEnhanceSkillRate) gem).getEnhanceRate(player))
                .sum();
    }
}
