package com.Very.very.NetWorking.Packets.USE;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UtilsLakeSwordS2CPacket {
    private final boolean flag;
    public UtilsLakeSwordS2CPacket(boolean flag)
    {
        this.flag = flag;
    }
    public UtilsLakeSwordS2CPacket(FriendlyByteBuf buf)
    {
        this.flag = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeBoolean(this.flag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.UseOfLakeSword = flag;
        });
        return true;
    }
}
