package fun.wraq.networking.misc.AnimationPackets;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Boss2AnimationStartS2CPacket {
    private final int Mode;

    public Boss2AnimationStartS2CPacket(int mode) {
        this.Mode = mode;
    }

    public Boss2AnimationStartS2CPacket(FriendlyByteBuf buf) {
        this.Mode = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.Mode);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            ClientUtils.Boss2AnimationMode = this.Mode;

        });
        return true;
    }
}
