package com.very.wraq.networking.misc.SmartPhonePackets;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestGetC2SPacket {
    public RequestGetC2SPacket() {

    }

    public RequestGetC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
/*            Iterator<MarketPlayerInfo> iterator = Utils.marketPlayerInfos.iterator();
            while(iterator.hasNext()){
                MarketPlayerInfo marketPlayerInfo = iterator.next();
                if(marketPlayerInfo.getPlayer().equals(player.getName().getString()) && marketPlayerInfo.getProfit() > 0) {
                    flag = false;
                    Compute.VBgetAndMSGSend(player,(float) marketPlayerInfo.getProfit());
                    marketPlayerInfo.setProfit(0);
                }
            }*/
            double num = Utils.marketPlayerInfos.getOrDefault(player.getName().getString(), 0d);
            if (num > 0) {
                Compute.VBIncomeAndMSGSend(player, num);
                Utils.marketPlayerInfos.put(player.getName().getString(), 0d);
            } else {
                Compute.sendFormatMSG(player, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("似乎没有VB可以收取。"));
            }
        });
        return true;
    }
}
