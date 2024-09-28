package fun.wraq.common.util.struct;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class ServerWaltzMonster {
    private final Player Attacker;
    private final Mob Target;

    public ServerWaltzMonster(Player attacker, Mob target) {
        this.Attacker = attacker;
        this.Target = target;
    }

    public Player getAttacker() {
        return Attacker;
    }

    public Mob getTarget() {
        return Target;
    }
}
