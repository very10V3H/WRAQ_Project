package fun.wraq.networking.misc.ParticlePackets.EffectParticle;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CritHitParticleS2CPacket {
    private final double X;
    private final double Y;
    private final double Z;

    public CritHitParticleS2CPacket(double X, double Y, double Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public CritHitParticleS2CPacket(FriendlyByteBuf buf) {
        this.X = buf.readDouble();
        this.Y = buf.readDouble();
        this.Z = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.X);
        buf.writeDouble(this.Y);
        buf.writeDouble(this.Z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.CritHitFlag = true;
            ClientUtils.CritX = X;
            ClientUtils.CritY = Y;
            ClientUtils.CritZ = Z;
        });
        return true;
    }
}
