package com.very.wraq.netWorking.misc.SmartPhonePackets;

import com.very.wraq.files.MarketItemInfo;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.Iterator;
import java.util.function.Supplier;

public class RequestMarketC2SPacket {
    public RequestMarketC2SPacket()
    {

    }
    public RequestMarketC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer player = context.getSender();
            Iterator<MarketItemInfo> iterator = Utils.MarketInfo.iterator();
            ModNetworking.sendToClient(new MarketDataClearS2CPacket(),player);
            while(iterator.hasNext()){
                MarketItemInfo marketItemInfo = iterator.next();
                ModNetworking.sendToClient(new MarketDataS2CPacket(marketItemInfo.getPlayer(),marketItemInfo.getItemStack(),marketItemInfo.getPrice()),(ServerPlayer) player);
            }

        });
        return true;
    }
}
