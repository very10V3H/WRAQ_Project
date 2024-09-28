package fun.wraq.process.system.forge.networking;

import fun.wraq.render.gui.blocks.ForgingBlockScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DecomposeDoubleClickTickS2CPacket {

    public DecomposeDoubleClickTickS2CPacket() {

    }

    public DecomposeDoubleClickTickS2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ForgingBlockScreen.doubleClick = 20;
        });
        return true;
    }
}
