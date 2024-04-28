package com.very.wraq.netWorking.misc.ParticlePackets.ElementParticle;

import com.very.wraq.process.element.Element;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class WaterElementParticleS2CPacket {
    private final int Id;
    private final int Time;

    public WaterElementParticleS2CPacket(int Id, int Time)
    {
        this.Id = Id;
        this.Time = Time;
    }
    public WaterElementParticleS2CPacket(FriendlyByteBuf buf)
    {
        this.Id = buf.readInt();
        this.Time = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.Id);
        buf.writeInt(this.Time);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            Element.ClientRemoveEntityParticle(Id);
            Element.WaterElementParticle.put(Id,Time);
        });
        return true;
    }
}
