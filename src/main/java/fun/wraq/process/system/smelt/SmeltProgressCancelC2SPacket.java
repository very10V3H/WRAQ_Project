package fun.wraq.process.system.smelt;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SmeltProgressCancelC2SPacket {

    private final int index;

    public SmeltProgressCancelC2SPacket(int index) {
        this.index = index;
    }

    public SmeltProgressCancelC2SPacket(FriendlyByteBuf buf) {
        this.index = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.index);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Smelt.cancelProgress(context.getSender(), index + 1);
            Smelt.sendDataToClient(context.getSender());
        });
        return true;
    }
}
