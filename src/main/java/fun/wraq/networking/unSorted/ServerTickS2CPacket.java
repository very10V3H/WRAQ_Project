package fun.wraq.networking.unSorted;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerTickS2CPacket {

    private final int tick;

    public ServerTickS2CPacket(int tick) {
        this.tick = tick;
    }

    public ServerTickS2CPacket(FriendlyByteBuf buf) {
        this.tick = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.tick);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.serverTick = tick;
        });
        return true;
    }
}
