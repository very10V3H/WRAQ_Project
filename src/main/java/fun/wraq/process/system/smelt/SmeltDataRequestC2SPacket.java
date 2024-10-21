package fun.wraq.process.system.smelt;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SmeltDataRequestC2SPacket {

    public SmeltDataRequestC2SPacket() {

    }

    public SmeltDataRequestC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Smelt.sendDataToClient(context.getSender());
        });
        return true;
    }
}
