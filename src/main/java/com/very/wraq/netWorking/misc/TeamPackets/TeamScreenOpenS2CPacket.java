package com.very.wraq.netWorking.misc.TeamPackets;

import com.very.wraq.valueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TeamScreenOpenS2CPacket {
    public TeamScreenOpenS2CPacket()
    {

    }
    public TeamScreenOpenS2CPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.clientTeamScreenFlag = true;
        });
        return true;
    }
}
