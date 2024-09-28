package fun.wraq.process.system.randomStore.networking;

import fun.wraq.process.system.randomStore.RandomStore;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TradeListClearS2CPacket {

    public TradeListClearS2CPacket() {

    }

    public TradeListClearS2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            RandomStore.clientTradeList.clear();
        });
        return true;
    }
}
