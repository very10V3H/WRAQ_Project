package com.Very.very.NetWorking.Packets.SmartPhonePackets;

import com.Very.very.Files.MarketItemInfo;
import com.Very.very.Files.MarketPlayerInfo;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.Iterator;
import java.util.function.Supplier;

public class RequestGetC2SPacket {
    public RequestGetC2SPacket()
    {

    }
    public RequestGetC2SPacket(FriendlyByteBuf buf)
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
            Iterator<MarketPlayerInfo> iterator = Utils.MarketPlayerInfo.iterator();
            boolean flag = true;
            while(iterator.hasNext()){
                MarketPlayerInfo marketPlayerInfo = iterator.next();
                if(marketPlayerInfo.getPlayer().equals(player.getName().getString()) && marketPlayerInfo.getGet() > 0) {
                    flag = false;
                    Compute.VBgetAndMSGSend(player,(float) marketPlayerInfo.getGet());
                    marketPlayerInfo.setGet(0);
                }
            }
            if(flag){
                Compute.FormatMSGSend(player, Component.literal("市场").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("似乎没有VB可以收取。"));
            }
        });
        return true;
    }
}
