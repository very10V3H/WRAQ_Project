package fun.wraq.networking.misc.ParticlePackets;

import fun.wraq.render.hud.main.ManaAoeRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaAttackParticleS2CPacket {
    private final double X;
    private final double Y;
    private final double Z;
    private final int Color;

    public ManaAttackParticleS2CPacket(double X, double Y, double Z, int Color) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.Color = Color;
    }

    public ManaAttackParticleS2CPacket(FriendlyByteBuf buf) {
        this.X = buf.readDouble();
        this.Y = buf.readDouble();
        this.Z = buf.readDouble();
        this.Color = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.X);
        buf.writeDouble(this.Y);
        buf.writeDouble(this.Z);
        buf.writeInt(this.Color);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ManaAoeRenderer.addEffect(new Vec3(X, Y, Z), Color);
        });
        return true;
    }
}
