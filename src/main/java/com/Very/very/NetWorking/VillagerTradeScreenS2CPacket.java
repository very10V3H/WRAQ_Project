package com.Very.very.NetWorking;

import ca.weblite.objc.Client;
import com.Very.very.Render.Gui.VillagerTrade.Trade;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class VillagerTradeScreenS2CPacket {
    private final String type;
    public VillagerTradeScreenS2CPacket(String type)
    {
        this.type = type;
    }
    public VillagerTradeScreenS2CPacket(FriendlyByteBuf buf)
    {
        this.type = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeUtf(this.type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.TradeScreenType = type;
            ClientUtils.TradeScreenOpenFlag = true;
        });
        return true;
    }
}
