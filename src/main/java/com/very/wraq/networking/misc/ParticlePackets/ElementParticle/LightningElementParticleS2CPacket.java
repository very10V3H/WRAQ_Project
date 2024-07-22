package com.very.wraq.networking.misc.ParticlePackets.ElementParticle;

import com.very.wraq.process.system.element.Element;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LightningElementParticleS2CPacket {
    private final int Id;
    private final int Time;

    public LightningElementParticleS2CPacket(int Id, int Time) {
        this.Id = Id;
        this.Time = Time;
    }

    public LightningElementParticleS2CPacket(FriendlyByteBuf buf) {
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
            Element.ClientRemoveEntityParticle(Id);
            Element.lightningElementParticle.put(Id, Time);
        });
        return true;
    }
}
