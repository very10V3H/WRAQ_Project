package com.Very.very.NetWorking.Packets.SoundsPackets;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SoundsS2CPacket {
    private final int flag;
    public SoundsS2CPacket(int flag)
    {
        this.flag = flag;
    }
    public SoundsS2CPacket(FriendlyByteBuf buf)
    {
        this.flag = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.flag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.Sounds = flag;
        });
        return true;
    }
}
