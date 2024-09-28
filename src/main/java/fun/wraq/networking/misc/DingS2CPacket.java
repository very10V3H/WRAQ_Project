package fun.wraq.networking.misc;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DingS2CPacket {
    private final int PsValue;

    public DingS2CPacket(int counts) {
        this.PsValue = counts;
    }

    public DingS2CPacket(FriendlyByteBuf buf) {
        this.PsValue = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.PsValue);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.DingCounts = this.PsValue;
        });
        return true;
    }
}
