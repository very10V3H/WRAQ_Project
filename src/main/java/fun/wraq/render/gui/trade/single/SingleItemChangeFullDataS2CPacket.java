package fun.wraq.render.gui.trade.single;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public class SingleItemChangeFullDataS2CPacket {

    private final Map<String, Integer> clientData;

    public SingleItemChangeFullDataS2CPacket(Map<String, Integer> clientData) {
        this.clientData = clientData;
    }

    public SingleItemChangeFullDataS2CPacket(FriendlyByteBuf buf) {
        this.clientData = buf.readMap(FriendlyByteBuf::readUtf, FriendlyByteBuf::readInt);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeMap(this.clientData, FriendlyByteBuf::writeUtf, FriendlyByteBuf::writeInt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SingleItemChangePurchaseLimit.clientDataMap.clear();
            SingleItemChangePurchaseLimit.clientDataMap = clientData;
        });
        return true;
    }
}
