package fun.wraq.common.attribute;

import fun.wraq.common.impl.onhit.OnHitEffectOffHandWeapon;
import fun.wraq.series.newrunes.chapter2.KazeNewRune;
import fun.wraq.series.newrunes.chapter2.LightningNewRune;
import fun.wraq.series.overworld.sakuraSeries.BloodMana.BloodManaArmor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class SameTypeModule {
    public static void onNormalAttackHitMob(Player player, Mob mob, int type, double damage) {
        // type : 0 - attack 1 - mana
        LightningNewRune.onHit(player, mob);
        if (player.getOffhandItem().getItem() instanceof OnHitEffectOffHandWeapon onHitEffectOffHandWeapon)
            onHitEffectOffHandWeapon.onHit(player, mob);
        if (type == 0) BloodManaArmor.onAttackOrArrowHit(player, mob);
        KazeNewRune.onHit(player);
    }
}