package fun.wraq.networking.misc;

import fun.wraq.process.system.reason.Reason;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PsValueS2CPacket {
    private final int reasonValue;

    public PsValueS2CPacket(int reasonValue) {
        this.reasonValue = reasonValue;
    }

    public PsValueS2CPacket(FriendlyByteBuf buf) {
        this.reasonValue = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.reasonValue);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Reason.clientReasonValue = this.reasonValue;
        });
        return true;
    }
}
