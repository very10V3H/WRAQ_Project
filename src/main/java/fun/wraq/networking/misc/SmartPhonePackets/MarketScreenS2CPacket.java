package fun.wraq.networking.misc.SmartPhonePackets;

import fun.wraq.render.gui.market.MarketScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MarketScreenS2CPacket {

    private final int page;

    public MarketScreenS2CPacket(int page) {
        this.page = page;
    }

    public MarketScreenS2CPacket(FriendlyByteBuf buf) {
        this.page = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(page);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            MarketScreen.openFlag = true;
            MarketScreen.tempPage = 0;
            MarketScreen.tempSortByPrice = -1;
        });
        return true;
    }
}
