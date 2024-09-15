package com.very.wraq.process.system.element;

import com.very.wraq.common.Compute;

public class Color {
    public record RGB(double r, double g, double b) {
    }

    public RGB originRGB;
    public RGB targetRGB;
    public int maxDistance;
    public int currentDistance = 0;

    public Color(RGB originRGB, RGB targetRGB, int maxDistance) {
        this.originRGB = originRGB;
        this.targetRGB = targetRGB;
        this.maxDistance = maxDistance;
    }

    public boolean Add() {
        if (currentDistance == maxDistance) return true;
        currentDistance++;
        return false;
    }

    public int getR() {
        return (int) (originRGB.r + (targetRGB.r - originRGB.r) * (currentDistance * 1.0 / maxDistance));
    }

    public int getG() {
        return (int) (originRGB.g + (targetRGB.g - originRGB.g) * (currentDistance * 1.0 / maxDistance));
    }

    public int getB() {
        return (int) (originRGB.b + (targetRGB.b - originRGB.b) * (currentDistance * 1.0 / maxDistance));
    }

    public String getRGB() {
        return Compute.getRGB(getR(), getG(), getB());
    }


}
