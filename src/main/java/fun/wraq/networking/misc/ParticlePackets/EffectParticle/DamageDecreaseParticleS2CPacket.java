package fun.wraq.networking.misc.ParticlePackets.EffectParticle;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DamageDecreaseParticleS2CPacket {
    private final int Id;
    private final int Time;

    public DamageDecreaseParticleS2CPacket(int Id, int Time) {
        this.Id = Id;
        this.Time = Time;
    }

    public DamageDecreaseParticleS2CPacket(FriendlyByteBuf buf) {
        this.Id = buf.readInt();
        this.Time = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.Id);
        buf.writeInt(this.Time);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.DamageDecreaseParticle.put(Id, Time);
        });
        return true;
    }
}
