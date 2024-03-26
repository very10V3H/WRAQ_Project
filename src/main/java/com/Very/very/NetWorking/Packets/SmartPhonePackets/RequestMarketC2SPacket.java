package com.Very.very.NetWorking.Packets.SmartPhonePackets;

import com.Very.very.Files.MarketItemInfo;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.nbt.CompoundTag;
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
