package fun.wraq.networking.misc.TeamPackets;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ScreenSetS2CPacket {
    private final int type;

    public ScreenSetS2CPacket(int type) {
        this.type = type;
    }

    public ScreenSetS2CPacket(FriendlyByteBuf buf) {
        this.type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.clientScreenSetFlag = this.type;
        });
        return true;
    }
}