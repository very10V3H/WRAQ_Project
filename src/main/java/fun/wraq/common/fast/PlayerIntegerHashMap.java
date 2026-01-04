package fun.wraq.common.fast;

import net.minecraft.world.entity.player.Player;

public class PlayerIntegerHashMap extends PlayerHashMap<Integer> {

    public void increment(Player player, int max) {
        map.compute(Name.get(player), (k, v) -> v == null ? 1 : Math.min(max, v + 1));
    }

    public void decrement(Player player, int min) {
        map.compute(Name.get(player), (k, v) -> v == null ? 1 : Math.max(min, v - 1));
    }
}
