package fun.wraq.process.func.particle.packets;

import org.joml.Vector3f;

public class SpaceEffectParticle {
    public Vector3f pos;
    public double radius;
    public int num;
    public int color;
    public int lastTick;

    public SpaceEffectParticle(Vector3f pos, double radius, int num, int color, int lastTick) {
        this.pos = pos;
        this.radius = radius;
        this.num = num;
        this.color = color;
        this.lastTick = lastTick;
    }
}
