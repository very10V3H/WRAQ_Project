package fun.wraq.networking.unSorted;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class VillagerTradeScreenC2SPacket {
    private final String type;

    public VillagerTradeScreenC2SPacket(String type) {
        this.type = type;
    }

    public VillagerTradeScreenC2SPacket(FriendlyByteBuf buf) {
        this.type = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();

        });
        return true;
    }
}
