package fun.wraq.process.func.particle.packets;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class LastVerticalCircleParticleS2CPacket {

    private final Vector3f vector3f;
    private final double r;
    private final int num;
    private final String particleType;
    private final int lastTick;

    public LastVerticalCircleParticleS2CPacket(Vector3f vector3f, double r, int num, String type, int lastTick) {
        this.vector3f = vector3f;
        this.r = r;
        this.num = num;
        this.particleType = type;
        this.lastTick = lastTick;
    }

    public LastVerticalCircleParticleS2CPacket(FriendlyByteBuf buf) {
        this.vector3f = buf.readVector3f();
        this.r = buf.readDouble();
        this.num = buf.readInt();
        this.particleType = buf.readUtf();
        this.lastTick = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVector3f(vector3f);
        buf.writeDouble(r);
        buf.writeInt(num);
        buf.writeUtf(particleType);
        buf.writeInt(lastTick);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ParticleOptions particleOptions =
                    Utils.ParticleStringToParticleMap.getOrDefault(particleType, ParticleTypes.WITCH);
            ParticleProvider.clientLastVerticalCircleParticles
                    .add(new ParticleProvider.ClientLastVerticalCircleParticle(new Vec3(vector3f),
                            r, num, particleOptions, ClientUtils.serverTick + lastTick));
        });
        return true;
    }
}
