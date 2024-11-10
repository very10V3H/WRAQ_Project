package fun.wraq.process.func.security.mac.network;

import fun.wraq.process.func.security.mac.MacServer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MacC2SPacket {
    private final String mac;

    public MacC2SPacket(String mac) {
        this.mac = mac;
    }

    public MacC2SPacket(FriendlyByteBuf buf) {
        this.mac = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.mac);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            MacServer.checkMac(context.getSender(), this.mac);
        });
        return true;
    }
}
