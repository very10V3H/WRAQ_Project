package fun.wraq.series.gems.passive.impl;

import fun.wraq.series.gems.WraqGem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface GemOnCauseDamage {
    void onCauseDamage(Player player, Mob mob, double damage);

    static void causeDamage(Player player, Mob mob, double damage) {
        WraqGem.getPlayerAllEquipGems(player)
                .stream().filter(gem -> gem instanceof GemOnCauseDamage)
                .map(gem -> (GemOnCauseDamage) gem)
                .forEach(gem -> gem.onCauseDamage(player, mob, damage));
    }
}
