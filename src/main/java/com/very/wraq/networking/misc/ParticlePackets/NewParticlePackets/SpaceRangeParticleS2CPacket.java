package com.very.wraq.networking.misc.ParticlePackets.NewParticlePackets;

import com.very.wraq.common.Utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.Random;
import java.util.function.Supplier;

public class SpaceRangeParticleS2CPacket {
    private final Vector3f position;
    private final double r;
    private final int num;
    private final String particleType;

    public SpaceRangeParticleS2CPacket(Vector3f position, double r, int num, String type) {
        this.position = position;
        this.r = r;
        this.num = num;
        this.particleType = type;
    }

    public SpaceRangeParticleS2CPacket(FriendlyByteBuf buf) {
        this.position = buf.readVector3f();
        this.r = buf.readDouble();
        this.num = buf.readInt();
        this.particleType = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVector3f(position);
        buf.writeDouble(r);
        buf.writeInt(num);
        buf.writeUtf(particleType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ParticleOptions particleOptions = Utils.ParticleStringToParticleMap.getOrDefault(particleType, ParticleTypes.WITCH);
            Random random = new Random();
            for (int i = 0; i < num; i++) {
                Vec3 pos = new Vec3(position.x + r * Math.cos(2 * Math.PI * random.nextDouble()),
                        position.y - r + r * 2 * random.nextDouble(),
                        position.z + r * Math.sin(2 * Math.PI * random.nextDouble()));
                Minecraft.getInstance().level.addParticle(particleOptions, pos.x, pos.y, pos.z, 0, 0, 0);
            }
        });
        return true;
    }
}
