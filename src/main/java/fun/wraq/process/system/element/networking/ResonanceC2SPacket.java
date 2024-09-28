package fun.wraq.process.system.element.networking;

import fun.wraq.process.system.element.Element;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ResonanceC2SPacket {

    private final String elementType;

    public ResonanceC2SPacket(String elementType) {
        this.elementType = elementType;
    }

    public ResonanceC2SPacket(FriendlyByteBuf buf) {
        this.elementType = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(elementType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Element.resonance(player, elementType);
        });
        return true;
    }
}
