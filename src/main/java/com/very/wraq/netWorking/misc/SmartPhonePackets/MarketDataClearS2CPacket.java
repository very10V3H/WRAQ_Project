package com.very.wraq.netWorking.misc.SmartPhonePackets;

import com.very.wraq.valueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MarketDataClearS2CPacket {
    public MarketDataClearS2CPacket()
    {

    }
    public MarketDataClearS2CPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.MarketInfo.clear();
            for(int i = 0; i < 999; i++){
                if(ClientUtils.MarketInfoArray[i] == null) break;
                ClientUtils.MarketInfoArray[i] = null;
            }
        });
        return true;
    }
}
