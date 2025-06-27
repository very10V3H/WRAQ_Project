package fun.wraq.series.comsumable.passive.mixture;

import net.minecraft.world.entity.player.Player;

public interface Mixture {
    static void onReleaseNormalAttackOrHit(Player player) {
        MixtureAttack.addCount(player);
        MixturePenetration.addCount(player);
        MixturePenetration0.addCount(player);
    }
}
