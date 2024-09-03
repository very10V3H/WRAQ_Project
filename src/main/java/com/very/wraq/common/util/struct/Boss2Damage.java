package com.very.wraq.common.util.struct;

import net.minecraft.world.entity.player.Player;

public class Boss2Damage {

    private Player player;
    private double damage;

    public Boss2Damage(Player player, double damage) {
        this.player = player;
        this.damage = damage;
    }

    public Player getPlayer() {
        return player;
    }

    public double getDamage() {
        return damage;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
