package com.very.wraq.netWorking.misc.SmartPhonePackets;

import com.very.wraq.files.MarketPlayerInfo;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
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
                Compute.FormatMSGSend(player, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("似乎没有VB可以收取。"));
            }
        });
        return true;
    }
}
