package fun.wraq.networking.hud;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveDebuffTimeS2CPacket {

    private final String url;

    public RemoveDebuffTimeS2CPacket(String url) {
        this.url = url;
    }

    public RemoveDebuffTimeS2CPacket(FriendlyByteBuf buf) {
        this.url = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.url);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.debuffTimes.removeIf(hudIcon -> hudIcon.url.equals(this.url));
        });
        return true;
    }
}
