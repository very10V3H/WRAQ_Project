package fun.wraq.series.gems.passive.impl;

import fun.wraq.series.gems.WraqGem;
import net.minecraft.world.entity.player.Player;

public interface GemTickHandler {
    void tick(Player player);

    static void handleTick(Player player) {
        WraqGem.getPlayerAllEquipGems(player)
                .stream().filter(gem -> gem instanceof GemTickHandler)
                .map(gem -> (GemTickHandler) gem)
                .forEach(gem -> gem.tick(player));
    }
}
