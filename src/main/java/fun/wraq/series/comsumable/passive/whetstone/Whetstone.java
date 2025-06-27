package fun.wraq.series.comsumable.passive.whetstone;

import net.minecraft.world.entity.player.Player;

public interface Whetstone {
    static void onReleaseNormalAttackAndHit(Player player) {
        WhetstoneAttack.addCount(player);
        WhetstonePenetration.addCount(player);
        WhetstonePenetration0.addCount(player);
    }
}
