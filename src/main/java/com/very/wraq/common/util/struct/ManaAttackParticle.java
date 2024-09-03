package com.very.wraq.common.util.struct;

import net.minecraft.world.phys.Vec3;

public class ManaAttackParticle {
    private Vec3 Pos;
    private int TickTime;
    private String Type;

    public ManaAttackParticle(Vec3 Pos, int TickTime, String Type) {
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
