package fun.wraq.networking.misc.AnimationPackets;

import fun.wraq.common.Compute;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UseRequestC2SPacket {
    private final int count;

    public UseRequestC2SPacket(int count) {
        this.count = count;
    }

    public UseRequestC2SPacket(FriendlyByteBuf buf) {
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            Compute.use(serverPlayer, serverPlayer.getMainHandItem().getItem());
        });
        return true;
    }
}
