package fun.wraq.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class VersionCheckS2CPacket {

    public VersionCheckS2CPacket() {

    }

    public VersionCheckS2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ModNetworking.sendToServer(new VersionC2SPacket("2.1.0"));
        });
        return true;
    }
}
