package com.very.wraq.netWorking.misc.SkillPackets;

import com.very.wraq.valueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BufferChangeS2CPacket {
    public BufferChangeS2CPacket()
    {

    }
    public BufferChangeS2CPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.SkillDataBuffer = false;
            ClientUtils.AbilityDataBuffer = false;
        });
        return true;
    }
}
