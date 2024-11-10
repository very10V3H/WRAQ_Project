package fun.wraq.render.hud.networking;

import fun.wraq.render.hud.main.QuickUseHud;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class QuickUseDisplayS2CPacket {

    private final int mode;
    public QuickUseDisplayS2CPacket(int mode) {
        this.mode = mode;
    }

    public QuickUseDisplayS2CPacket(FriendlyByteBuf buf) {
        this.mode = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(mode);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            QuickUseHud.mode = mode;
        });
        return true;
    }
}
