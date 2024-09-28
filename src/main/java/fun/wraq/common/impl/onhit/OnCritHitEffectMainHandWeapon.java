package fun.wraq.common.impl.onhit;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

/**
 * 主武器暴击攻击附加效果
 */
public interface OnCritHitEffectMainHandWeapon {
    void onCritHit(Player player, Mob mob);

    static void critHit(Player player, Mob mob) {
        if (player.getMainHandItem().getItem() instanceof OnCritHitEffectMainHandWeapon weapon) {
            weapon.onCritHit(player, mob);
        }
    }
}
