package fun.wraq.process.func.guide.networking;

import fun.wraq.process.func.guide.Guide;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GuideDisplayS2CPacket {

    private final boolean display;

    public GuideDisplayS2CPacket(boolean display) {
        this.display = display;
    }

    public GuideDisplayS2CPacket(FriendlyByteBuf buf) {
        this.display = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(display);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Guide.clientDisplay = display;
        });
        return true;
    }
}
