package fun.wraq.process.func.particle.packets;

import fun.wraq.process.func.particle.ParticleProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class SpaceEffectParticleS2CPacket {

    private final Vector3f pos;
    private final double radius;
    private final int num;
    private final int color;
    private final int lastTick;

    public SpaceEffectParticleS2CPacket(Vector3f pos, double radius, int num, int color, int lastTick) {
        this.pos = pos;
        this.radius = radius;
        this.num = num;
        this.color = color;
        this.lastTick = lastTick;
    }

    public SpaceEffectParticleS2CPacket(FriendlyByteBuf buf) {
        this.pos = buf.readVector3f();
        this.radius = buf.readDouble();
        this.num = buf.readInt();
        this.color = buf.readInt();
        this.lastTick = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVector3f(pos);
        buf.writeDouble(radius);
        buf.writeInt(num);
        buf.writeInt(color);
        buf.writeInt(lastTick);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ParticleProvider.spaceEffectParticles.add(new SpaceEffectParticle(pos, radius, num, color, lastTick));
        });
        return true;
    }
}
