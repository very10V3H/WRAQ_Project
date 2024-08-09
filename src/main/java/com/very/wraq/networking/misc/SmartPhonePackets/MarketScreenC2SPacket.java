package com.very.wraq.networking.misc.SmartPhonePackets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MarketScreenC2SPacket {

    private final int page;

    public MarketScreenC2SPacket(int page) {
        this.page = page;
    }

    public MarketScreenC2SPacket(FriendlyByteBuf buf) {
        this.page = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(page);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
/*            ModNetworking.sendToClient(new MarketDataS2CPacket(Utils.marketItemInfos), context.getSender());
            ModNetworking.sendToClient(new MarketScreenS2CPacket(page), context.getSender());*/
            context.getSender().sendSystemMessage(Component.literal("市场系统维护中。。"));
        });
        return true;
    }
}
