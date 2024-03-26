package com.Very.very.ValueAndTools.Utils.Struct;

import net.minecraft.world.phys.Vec3;

public class ManaAttackParticle {
    private Vec3 Pos;
    private int TickTime;
    private int Type;

    public ManaAttackParticle(Vec3 Pos, int TickTime, int Type) {
        this.Pos = Pos;
        this.TickTime = TickTime;
        this.Type = Type;
    }

    public void setTickTime(int tickTime) {
        TickTime = tickTime;
    }

    public int getTickTime() {
        return TickTime;
    }

    public Vec3 getPos() {
        return Pos;
    }

    public void setPos(Vec3 pos) {
        Pos = pos;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
