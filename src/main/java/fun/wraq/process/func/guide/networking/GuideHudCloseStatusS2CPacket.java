package fun.wraq.process.func.guide.networking;

import fun.wraq.process.func.guide.Guide;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GuideHudCloseStatusS2CPacket {

    private final boolean status;

    public GuideHudCloseStatusS2CPacket(boolean status) {
        this.status = status;
    }

    public GuideHudCloseStatusS2CPacket(FriendlyByteBuf buf) {
        this.status = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(status);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Guide.clientGuideHudCloseStatus = status;
        });
        return true;
    }
}
