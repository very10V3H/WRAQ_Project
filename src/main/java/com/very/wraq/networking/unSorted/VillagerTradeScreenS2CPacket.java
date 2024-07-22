package com.very.wraq.networking.unSorted;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class VillagerTradeScreenS2CPacket {
    private final String type;

    public VillagerTradeScreenS2CPacket(String type) {
        this.type = type;
    }

    public VillagerTradeScreenS2CPacket(FriendlyByteBuf buf) {
        this.type = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.TradeScreenType = type;
            ClientUtils.TradeScreenOpenFlag = true;
        });
        return true;
    }
}
