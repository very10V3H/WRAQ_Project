package fun.wraq.series.gems.passive.impl;

import fun.wraq.series.gems.WraqGem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface GemOnKillMob {
    void onKill(Player player, Mob mob);

    static void kill(Player player, Mob mob) {
        WraqGem.getPlayerAllEquipGems(player)
                .stream().filter(gem -> gem instanceof GemOnKillMob)
                .map(gem -> (GemOnKillMob) gem)
                .forEach(gem -> gem.onKill(player, mob));
    }
}
