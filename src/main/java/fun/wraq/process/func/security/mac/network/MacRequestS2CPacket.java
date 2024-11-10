package fun.wraq.process.func.security.mac.network;

import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.security.Security;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.net.InetAddress;
import java.util.function.Supplier;

public class MacRequestS2CPacket {

    public MacRequestS2CPacket() {
    }

    public MacRequestS2CPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            try {
                ModNetworking.sendToServer(new MacC2SPacket(Security.getMACAddress(InetAddress.getLocalHost())));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
