package com.very.wraq.common.util.struct;

import net.minecraft.world.phys.Vec3;

public class PosAndLastTime {
    public int TickCount;
    public Vec3 vec3;
    public int levelType;

    public PosAndLastTime(Vec3 vec3, int tickCount, int levelType) {
        this.TickCount = tickCount;
        this.vec3 = vec3;
        this.levelType = levelType;
    }
}
