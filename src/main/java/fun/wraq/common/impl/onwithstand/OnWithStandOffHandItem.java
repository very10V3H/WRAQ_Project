package fun.wraq.common.impl.onwithstand;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface OnWithStandOffHandItem {
    void onWithStand(Mob mob, Player player);
}
