package com.very.wraq.networking.misc.ParticlePackets.NewParticlePackets;

import com.very.wraq.common.Utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class CurveParticleS2CPacket {
    private final Vector3f endVec3;
    private final Vector3f startVec3;
    private final Vector3f delta;
    private final double pickDistance;
    private final double r;
    private final int num;
    private final String particleType;

    public CurveParticleS2CPacket(Vector3f endVec3, Vector3f startVec3, Vector3f delta, double pickDistance, double r, int num, String type) {
        this.endVec3 = endVec3;
        this.startVec3 = startVec3;
        this.delta = delta;
        this.pickDistance = pickDistance;
        this.r = r;
        this.num = num;
        this.particleType = type;
    }

    public CurveParticleS2CPacket(FriendlyByteBuf buf) {
        this.endVec3 = buf.readVector3f();
        this.startVec3 = buf.readVector3f();
        this.delta = buf.readVector3f();
        this.pickDistance = buf.readDouble();
        this.r = buf.readDouble();
        this.num = buf.readInt();
        this.particleType = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVector3f(endVec3);
        buf.writeVector3f(startVec3);
        buf.writeVector3f(delta);
        buf.writeDouble(pickDistance);
        buf.writeDouble(r);
        buf.writeInt(num);
        buf.writeUtf(particleType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ParticleOptions particleOptions = Utils.ParticleStringToParticleMap.getOrDefault(particleType, ParticleTypes.WITCH);

            Vec3 vec3 = new Vec3(delta.x, delta.y, delta.z);
            double distance = startVec3.distance(endVec3);
            Vector3f vector3f = endVec3.sub(startVec3);
            Vec3 vec = new Vec3(vector3f.x, vector3f.y, vector3f.z);
            for (double i = 0; i < distance; i += 1.0 / num) {
                double scale = Math.abs(-(i) * (i - distance)) / Math.pow(distance / 2, 2);
                Vec3 pointVec = new Vec3(startVec3.x + vec.x * (i / distance) + vec3.x * scale
                        , startVec3.y + vec.y * (i / distance) + vec3.y * scale
                        , startVec3.z + vec.z * (i / distance) + vec3.z * scale);

                Minecraft.getInstance().level.addParticle(particleOptions, pointVec.x, pointVec.y
                        , pointVec.z, 0, 0, 0);
            }

        });
        return true;
    }
}
