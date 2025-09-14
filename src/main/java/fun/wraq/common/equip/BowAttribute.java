package fun.wraq.common.equip;

import net.minecraft.world.entity.player.Player;

public interface BowAttribute {
    static boolean isHandling(Player player) {
        return player.getMainHandItem().getItem() instanceof BowAttribute;
    }
}
