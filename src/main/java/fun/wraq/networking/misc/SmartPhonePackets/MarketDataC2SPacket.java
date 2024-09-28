package fun.wraq.networking.misc.SmartPhonePackets;

import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SmartPhonePackets.MarketDataS2CPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MarketDataC2SPacket {


    public MarketDataC2SPacket() {

    }

    public MarketDataC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ModNetworking.sendToClient(new MarketDataS2CPacket(Utils.marketItemInfos), context.getSender());
        });
        return true;
    }
}
