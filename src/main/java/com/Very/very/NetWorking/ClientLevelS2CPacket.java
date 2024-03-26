package com.Very.very.NetWorking;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientLevelS2CPacket {
    private final int type;
    public ClientLevelS2CPacket(int type)
    {
        this.type = type;
    }
    public ClientLevelS2CPacket(FriendlyByteBuf buf)
    {
        this.type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.ClientLevelFlag = type;
        });
        return true;
    }
}
