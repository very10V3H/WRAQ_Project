package fun.wraq.series.overworld.chapter1.forest;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public class ForestPowerEffectMob {
    private final Vec3 Destination;
    private int EffectTick;
    private final Mob mob;

    public ForestPowerEffectMob(Vec3 destination, int effectTick, Mob mob) {
        this.Destination = destination;
        this.EffectTick = effectTick;
        this.mob = mob;
    }

    public Mob getMob() {
        return mob;
    }

    public int getEffectTick() {
        return EffectTick;
    }

    public Vec3 getDestination() {
        return Destination;
    }

    public void TickSub() {
        this.EffectTick--;
    }
}
