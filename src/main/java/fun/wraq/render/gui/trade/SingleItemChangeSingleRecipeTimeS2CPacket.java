package fun.wraq.render.gui.trade;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SingleItemChangeSingleRecipeTimeS2CPacket {

    private final String key;
    private final int time;

    public SingleItemChangeSingleRecipeTimeS2CPacket(String key, int time) {
        this.key = key;
        this.time = time;
    }

    public SingleItemChangeSingleRecipeTimeS2CPacket(FriendlyByteBuf buf) {
        this.key = buf.readUtf();
        this.time = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.key);
        buf.writeInt(this.time);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SingleItemChangePurchaseLimit.clientDataMap.put(key, time);
        });
        return true;
    }
}
