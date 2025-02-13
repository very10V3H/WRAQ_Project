package fun.wraq.networking.misc.SmartPhonePackets;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.files.MarketItemInfo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collections;
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
            int price = buf.readInt();
            int type = buf.readInt();
            return new MarketItemInfo(player, itemStack, price, type);
        });
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeCollection(this.itemInfos, ((friendlyByteBuf, marketItemInfo) -> {
            String player = marketItemInfo.playerName;
            ItemStack itemStack = marketItemInfo.itemStack;
            int price = marketItemInfo.price;
            int type = marketItemInfo.type;
            buf.writeUtf(player);
            buf.writeItem(itemStack);
            buf.writeInt(price);
            buf.writeInt(type);
        }));
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.marketInfo.clear();
            ClientUtils.marketInfo.addAll(this.itemInfos);
            Collections.reverse(ClientUtils.marketInfo);
            ClientUtils.receiveMarketInfo = true;
        });
        return true;
    }
}
