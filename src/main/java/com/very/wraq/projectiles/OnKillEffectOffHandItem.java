package com.very.wraq.projectiles;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface OnKillEffectOffHandItem {
    void onKill(Player player, Mob mob);
}
