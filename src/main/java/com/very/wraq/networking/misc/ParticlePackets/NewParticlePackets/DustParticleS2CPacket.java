package com.very.wraq.networking.misc.ParticlePackets.NewParticlePackets;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.Random;
import java.util.function.Supplier;

public class DustParticleS2CPacket {
    private final Vector3f position;
    private final double r;
    private final int num;
    private final String color;
    private final Vector3f delta;

    public DustParticleS2CPacket(Vector3f position, double r, int num, String color, Vector3f delta) {
        this.position = position;
        this.r = r;
        this.num = num;
        this.color = color;
        this.delta = delta;
    }

    public DustParticleS2CPacket(FriendlyByteBuf buf) {
        this.position = buf.readVector3f();
        this.r = buf.readDouble();
        this.num = buf.readInt();
        this.color = buf.readUtf();
        this.delta = buf.readVector3f();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVector3f(position);
        buf.writeDouble(r);
        buf.writeInt(num);
        buf.writeUtf(color);
        buf.writeVector3f(delta);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ParticleOptions particleOptions = new DustParticleOptions(Vec3.fromRGB24(Integer.parseInt(color)).toVector3f(), 1);
            Random random = new Random();
            for (int i = 0; i < num; i++) {
                Vec3 pos = new Vec3(position.x + r * Math.cos(2 * Math.PI * random.nextDouble()),
                        position.y - r + r * 2 * random.nextDouble(),
                        position.z + r * Math.sin(2 * Math.PI * random.nextDouble()));
                Minecraft.getInstance().level.addParticle(particleOptions, pos.x, pos.y, pos.z, delta.x, delta.y, delta.z);
            }
        });
        return true;
    }
}
