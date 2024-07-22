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

public class FaceCircleParticleS2CPacket {
    private final Vector3f pickVec3;
    private final Vector3f faceVec3;
    private final double pickDistance;
    private final double r;
    private final int num;
    private final String particleType;

    public FaceCircleParticleS2CPacket(Vector3f pickVec3, Vector3f faceVec3, double pickDistance, double r, int num, String type) {
        this.pickVec3 = pickVec3;
        this.faceVec3 = faceVec3;
        this.pickDistance = pickDistance;
        this.r = r;
        this.num = num;
        this.particleType = type;
    }

    public FaceCircleParticleS2CPacket(FriendlyByteBuf buf) {
        this.pickVec3 = buf.readVector3f();
        this.faceVec3 = buf.readVector3f();
        this.pickDistance = buf.readDouble();
        this.r = buf.readDouble();
        this.num = buf.readInt();
        this.particleType = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVector3f(pickVec3);
        buf.writeVector3f(faceVec3);
        buf.writeDouble(pickDistance);
        buf.writeDouble(r);
        buf.writeInt(num);
        buf.writeUtf(particleType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ParticleOptions particleOptions = Utils.ParticleStringToParticleMap.getOrDefault(particleType, ParticleTypes.WITCH);

            Vector3f vector3f = pickVec3.sub(faceVec3);
            Vec3 nVec = new Vec3(vector3f.x, vector3f.y, vector3f.z);
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
                double angle = (2 * Math.PI / num) * (i);
                Vec3 Point = new Vec3(faceVec3.x + r * Math.cos(angle) * aVec.x + r * Math.sin(angle) * bVec.x,
                        faceVec3.y + r * Math.cos(angle) * aVec.y + r * Math.sin(angle) * bVec.y,
                        faceVec3.z + r * Math.cos(angle) * aVec.z + r * Math.sin(angle) * bVec.z);
                Minecraft.getInstance().level.addParticle(particleOptions, Point.x, Point.y, Point.z,
                        0, 0, 0);
            }

        });
        return true;
    }
}
