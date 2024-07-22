package com.very.wraq.common.Utils.Struct;

import net.minecraft.world.entity.player.Player;

public class ServerWaltzPlayer {
    private final Player Attacker;
    private final Player Target;

    public ServerWaltzPlayer(Player attacker, Player target) {
        this.Attacker = attacker;
        this.Target = target;
    }

    public Player getAttacker() {
        return Attacker;
    }

    public Player getTarget() {
        return Target;
    }
}
