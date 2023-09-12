package com.Very.very.NetWorking.Packets.EntropyPackets;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EntropyS2CPacket {
    private final int Forest;
    private final int Volcano;
    public EntropyS2CPacket(int Forest, int Volcano)
    {
        this.Forest = Forest;
        this.Volcano = Volcano;
    }
    public EntropyS2CPacket(FriendlyByteBuf buf)
    {
        this.Forest = buf.readInt();
        this.Volcano = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.Forest);
        buf.writeInt(this.Volcano);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.Entropy.Forest = this.Forest;
            ClientUtils.Entropy.Volcano = this.Volcano;
        });
        return true;
    }
}
