package fun.wraq.process.func.particle.packets;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.particle.ParticleProvider;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DisperseBallParticleS2CPacket {

    private final String particleType;
    private final double x;
    private final double y;
    private final double z;
    private final double radius;
    private final int num;

    public DisperseBallParticleS2CPacket(String particleType, double x, double y, double z,
                                         double radius, int num) {
        this.particleType = particleType;
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.num = num;
    }

    public DisperseBallParticleS2CPacket(FriendlyByteBuf buf) {
        particleType = buf.readUtf();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.radius = buf.readDouble();
        this.num = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(particleType);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeDouble(this.radius);
        buf.writeInt(this.num);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ParticleProvider.createBallDisperseParticle(Utils.ParticleStringToParticleMap.get(particleType),
                    (ClientLevel) ClientUtils.clientLevel, new Vec3(x, y, z), radius, num);
        });
        return true;
    }
}
