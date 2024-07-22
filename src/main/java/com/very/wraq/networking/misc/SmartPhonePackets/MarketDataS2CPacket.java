package com.very.wraq.networking.misc.SmartPhonePackets;

import com.very.wraq.files.MarketItemInfo;
import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class MarketDataS2CPacket {

    private final List<MarketItemInfo> itemInfos;

    public MarketDataS2CPacket(List<MarketItemInfo> itemInfos) {
        this.itemInfos = itemInfos;
    }

    public MarketDataS2CPacket(FriendlyByteBuf buf) {
        this.itemInfos = buf.readList((friendlyByteBuf) -> {
            String player = buf.readUtf();
            ItemStack itemStack = buf.readItem();
            double price = buf.readDouble();
            return new MarketItemInfo(player, itemStack, price);
        });
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeCollection(this.itemInfos, ((friendlyByteBuf, marketItemInfo) -> {
            String player = marketItemInfo.getPlayer();
            ItemStack itemStack = marketItemInfo.getItemStack();
            double price = marketItemInfo.getPrice();
            buf.writeUtf(player);
            buf.writeItem(itemStack);
            buf.writeDouble(price);
        }));
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.marketInfo.clear();
            ClientUtils.marketInfo.addAll(this.itemInfos);
            ClientUtils.receiveMarketInfo = true;
        });
        return true;
    }
}
