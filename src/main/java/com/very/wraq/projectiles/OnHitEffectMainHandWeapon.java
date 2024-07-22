package com.very.wraq.projectiles;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface OnHitEffectMainHandWeapon {
    void onHit(Player player, Mob mob);
}
