package fun.wraq.render.hud.networking;

import fun.wraq.render.hud.main.QuickUseHud;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class QuickUseDisplayS2CPacket {

    private final boolean quickUseDisplay;
    public QuickUseDisplayS2CPacket(boolean quickUseDisplay) {
        this.quickUseDisplay = quickUseDisplay;
    }

    public QuickUseDisplayS2CPacket(FriendlyByteBuf buf) {
        this.quickUseDisplay = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(quickUseDisplay);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            QuickUseHud.display = quickUseDisplay;
        });
        return true;
    }
}
