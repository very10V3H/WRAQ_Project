package com.very.wraq.networking.misc.ParticlePackets.ElementParticle;

import com.very.wraq.process.system.element.Element;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClearElementParticleS2CPacket {
    private final int Id;

    public ClearElementParticleS2CPacket(int Id) {
        this.Id = Id;
    }

    public ClearElementParticleS2CPacket(FriendlyByteBuf buf) {
        this.Id = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.Id);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Element.ClientRemoveEntityParticle(Id);
        });
        return true;
    }
}
