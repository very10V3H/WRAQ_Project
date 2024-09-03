package com.very.wraq.networking.misc.ParticlePackets.NewParticlePackets;

import com.very.wraq.common.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

import static java.lang.Math.PI;

public class EntityFaceConnectCircleParticleS2CPacket {
    private final Vector3f position;
    private final Vector3f location;
    private final double pickDistance;
    private final double r;
    private final int num;
    private final String particleType;
    private final double DX;
    private final double DY;
    private final double DZ;
    private final double Start;

    public EntityFaceConnectCircleParticleS2CPacket(Vector3f position, Vector3f location, double pickDistance
            , double r, int num, String type, double DX, double DY, double DZ, double Start) {
        this.position = position;
        this.location = location;
        this.pickDistance = pickDistance;
        this.r = r;
        this.num = num;
        this.particleType = type;
        this.DX = DX;
        this.DY = DY;
        this.DZ = DZ;
        this.Start = Start;
    }

    public EntityFaceConnectCircleParticleS2CPacket(FriendlyByteBuf buf) {
        this.position = buf.readVector3f();
        this.location = buf.readVector3f();
        this.pickDistance = buf.readDouble();
        this.r = buf.readDouble();
        this.num = buf.readInt();
        this.particleType = buf.readUtf();
        this.DX = buf.readDouble();
        this.DY = buf.readDouble();
        this.DZ = buf.readDouble();
        this.Start = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVector3f(position);
        buf.writeVector3f(location);
        buf.writeDouble(pickDistance);
        buf.writeDouble(r);
        buf.writeInt(num);
        buf.writeUtf(particleType);
        buf.writeDouble(DX);
        buf.writeDouble(DY);
        buf.writeDouble(DZ);
        buf.writeDouble(Start);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ParticleOptions particleOptions = Utils.ParticleStringToParticleMap.getOrDefault(particleType, ParticleTypes.WITCH);

            Vec3 nVec = new Vec3(position.x, position.y, position.z);
            Vec3 iVec = new Vec3(1, 0, 0);
            Vec3 jVec = new Vec3(0, 1, 0);
            Vec3 kVec = new Vec3(0, 0, 1);
            Vec3 aVec;
            if (nVec.cross(iVec).length() == 0) {
                aVec = nVec.cross(jVec);
            } else aVec = nVec.cross(iVec);
            aVec = aVec.normalize();
            Vec3 bVec = nVec.cross(aVec).normalize();
            for (int i = 0; i < num; i++) {
                double angle = (2 * Math.PI / num) * (i) + Start * (PI);
                Vec3 Point = new Vec3(location.x + r * Math.cos(angle) * aVec.x + r * Math.sin(angle) * bVec.x,
                        location.y + r * Math.cos(angle) * aVec.y + r * Math.sin(angle) * bVec.y,
                        location.z + r * Math.cos(angle) * aVec.z + r * Math.sin(angle) * bVec.z);
                double rate = i * 1.0 / num;
                Minecraft.getInstance().level.addParticle(particleOptions, Point.x + DX * rate, Point.y + DY * rate, Point.z + DZ * rate, 0, 0, 0);
            }
        });
        return true;
    }
}
