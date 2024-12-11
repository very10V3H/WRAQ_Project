package fun.wraq.networking.misc;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveEffectLastTimeS2CPacket {
    private final String url;

    public RemoveEffectLastTimeS2CPacket(String url) {
        this.url = url;
    }

    public RemoveEffectLastTimeS2CPacket(FriendlyByteBuf buf) {
        this.url = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.url);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.effectTimeLasts.removeIf(hudIcon -> hudIcon.url.equals(url));
        });
        return true;
    }
}
