package fun.wraq.series.gems.passive.impl;

import fun.wraq.series.gems.WraqGem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface GemOnNormalAttackHit {
    void onHit(Player player, Mob mob, double damage);

    static void hit(Player player, Mob mob, double damage) {
        WraqGem.getPlayerAllEquipGems(player)
                .stream().filter(gem -> gem instanceof GemOnNormalAttackHit)
                .map(gem -> (GemOnNormalAttackHit) gem)
                .forEach(gem -> gem.onHit(player, mob, damage));
    }
}
