package fun.wraq.series.comsumable.passive.quiver;

import net.minecraft.world.entity.player.Player;

public interface Quiver {
    static void onArrowHit(Player player) {
        QuiverAttack.addCount(player);
        QuiverPenetration.addCount(player);
        QuiverPenetration0.addCount(player);
    }
}
