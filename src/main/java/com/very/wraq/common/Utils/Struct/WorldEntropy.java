package com.very.wraq.common.Utils.Struct;

import net.minecraft.world.phys.Vec3;

public class WorldEntropy {
    private Vec3 vec3;
    private double mediumNum;
    private String desName;

    public WorldEntropy(Vec3 vec3, double mediumNum, String desName) {
        this.vec3 = vec3;
        this.mediumNum = mediumNum;
        this.desName = desName;
    }

    public double getMediumNum() {
        return mediumNum;
    }

    public Vec3 getVec3() {
        return vec3;
    }

    public void setMediumNum(double mediumNum) {
        this.mediumNum = mediumNum;
    }

    public void setVec3(Vec3 vec3) {
        this.vec3 = vec3;
    }

    public String getDesName() {
        return desName;
    }

    public void setDesName(String desName) {
        this.desName = desName;
    }
}
