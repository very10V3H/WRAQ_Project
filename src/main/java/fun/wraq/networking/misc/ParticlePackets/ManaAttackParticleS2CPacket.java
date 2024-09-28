package fun.wraq.networking.misc.ParticlePackets;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.struct.ManaAttackParticle;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaAttackParticleS2CPacket {
    private final double X;
    private final double Y;
    private final double Z;
    private final String Type;

    public ManaAttackParticleS2CPacket(double X, double Y, double Z, String Type) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.Type = Type;
    }

    public ManaAttackParticleS2CPacket(FriendlyByteBuf buf) {
        this.X = buf.readDouble();
        this.Y = buf.readDouble();
        this.Z = buf.readDouble();
        this.Type = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.X);
        buf.writeDouble(this.Y);
        buf.writeDouble(this.Z);
        buf.writeUtf(this.Type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.manaAttackParticleArrayList.add(new ManaAttackParticle(new Vec3(X, Y, Z), 20, Type));
        });
        return true;
    }
}
