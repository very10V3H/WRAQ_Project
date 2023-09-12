package com.Very.very.NetWorking.Packets.SmartPhonePackets;

import com.Very.very.Files.MarketItemInfo;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MarketDataS2CPacket {
    private final String playerName;
    private final String itemStackName;
    private final double price;
    public MarketDataS2CPacket(String playerName, String itemStackName, double price)
    {
        this.playerName = playerName;
        this.itemStackName = itemStackName;
        this.price = price;
    }
    public MarketDataS2CPacket(FriendlyByteBuf buf)
    {
        this.playerName = buf.readUtf();
        this.itemStackName = buf.readUtf();
        this.price = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeUtf(this.playerName);
        buf.writeUtf(this.itemStackName);
        buf.writeDouble(this.price);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            MarketItemInfo marketItemInfo = new MarketItemInfo(playerName,itemStackName,price);
            ClientUtils.MarketInfo.add(marketItemInfo);
        });
        return true;
    }
}
