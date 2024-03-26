package com.Very.very.ValueAndTools.Utils.Struct;

import net.minecraft.world.phys.Vec3;

public class Gather {
    private int Time;
    private Vec3 Pos;
    public Gather(int time,Vec3 pos) {
        this.Time = time;
        this.Pos = pos;
    }

    public int getTime() {
        return Time;
    }

    public Vec3 getPos() {
        return Pos;
    }
    public void setTime(int time) {
        this.Time = time;
    }
    public void setPos(Vec3 pos) {
        this.Pos = pos;
    }

}
