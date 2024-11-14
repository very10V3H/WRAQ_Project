package fun.wraq.networking.misc.Limit;

import fun.wraq.events.core.BlockEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ScreenCloseC2SPacket {

    public ScreenCloseC2SPacket() {

    }

    public ScreenCloseC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            BlockEvent.removePlayerLimit(player);
        });
        return true;
    }
}
