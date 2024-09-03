package com.very.wraq.networking.misc.ParticlePackets;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SnowSwordParticleS2CPacket {
    private final double X;
    private final double Y;
    private final double Z;
    private final double X1;
    private final double Y1;
    private final double Z1;

    public SnowSwordParticleS2CPacket(double X, double Y, double Z, double X1, double Y1, double Z1) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.X1 = X1;
        this.Y1 = Y1;
        this.Z1 = Z1;
    }

    public SnowSwordParticleS2CPacket(FriendlyByteBuf buf) {
        this.X = buf.readDouble();
        this.Y = buf.readDouble();
        this.Z = buf.readDouble();
        this.X1 = buf.readDouble();
        this.Y1 = buf.readDouble();
        this.Z1 = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.X);
        buf.writeDouble(this.Y);
        buf.writeDouble(this.Z);
        buf.writeDouble(this.X1);
        buf.writeDouble(this.Y1);
        buf.writeDouble(this.Z1);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.UseOfSnowSwordParticle = true;
            ClientUtils.X = X;
            ClientUtils.Y = Y;
            ClientUtils.Z = Z;
            ClientUtils.X1 = X1;
            ClientUtils.Y1 = Y1;
            ClientUtils.Z1 = Z1;
        });
        return true;
    }
}
