package fun.wraq.series.gems.passive.impl;

import fun.wraq.series.gems.WraqGem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface GemOnWithstandDamage {
    void onWithStandDamage(Player player, Mob mob, double damage);

    static void withStandDamage(Player player, Mob mob, double damage) {
        WraqGem.getPlayerDistinctEquipGemsSet(player)
                .stream().filter(gem -> gem instanceof GemOnWithstandDamage)
                .map(gem -> (GemOnWithstandDamage) gem)
                .forEach(gem -> gem.onWithStandDamage(player, mob, damage));
    }
}
