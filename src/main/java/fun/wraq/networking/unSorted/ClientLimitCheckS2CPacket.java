package fun.wraq.networking.unSorted;

import fun.wraq.process.func.security.Security;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class ClientLimitCheckS2CPacket {
    private final String name;

    public ClientLimitCheckS2CPacket(String name) {
        this.name = name;
    }

    public ClientLimitCheckS2CPacket(FriendlyByteBuf buf) {
        this.name = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.name);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            try {
                Security.ClientPropertiesCheck(this.name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
