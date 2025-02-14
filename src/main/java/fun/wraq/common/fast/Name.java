package fun.wraq.common.fast;

import net.minecraft.world.entity.player.Player;

public class Name {
    public static String get(Player player) {
        return player.getName().getString();
    }
}
