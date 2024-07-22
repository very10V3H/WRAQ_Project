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

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class EntityEffectVerticleCircleParticleS2CPacket {
    private final Vector3f vector3f;
    private final double pickDistance;
    private final double r;
    private final int num;
    private final String particleType;

    public EntityEffectVerticleCircleParticleS2CPacket(Vector3f vector3f, double pickDistance, double r, int num, String type) {
        this.vector3f = vector3f;
        this.pickDistance = pickDistance;
        this.r = r;
        this.num = num;
        this.particleType = type;
    }

    public EntityEffectVerticleCircleParticleS2CPacket(FriendlyByteBuf buf) {
        this.vector3f = buf.readVector3f();
        this.pickDistance = buf.readDouble();
        this.r = buf.readDouble();
        this.num = buf.readInt();
        this.particleType = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVector3f(vector3f);
        buf.writeDouble(pickDistance);
        buf.writeDouble(r);
        buf.writeInt(num);
        buf.writeUtf(particleType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ParticleOptions particleOptions = Utils.ParticleStringToParticleMap.getOrDefault(particleType, ParticleTypes.WITCH);
            Vec3 bottomPos = new Vec3(vector3f.x, vector3f.y, vector3f.z);
            for (int i = 0; i < num; i++) {
                double angle = (2 * Math.PI / num) * (i);
                Vec3 Point = new Vec3(bottomPos.x + r * cos(angle), bottomPos.y + pickDistance, bottomPos.z + r * sin(angle));
                Minecraft.getInstance().level.addParticle(particleOptions, Point.x
                        , Point.y, Point.z, 0, 0, 0);
            }

        });
        return true;
    }
}
