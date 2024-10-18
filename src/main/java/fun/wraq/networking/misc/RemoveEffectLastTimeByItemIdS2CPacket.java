package fun.wraq.networking.misc;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveEffectLastTimeByItemIdS2CPacket {
    private final String itemId;

    public RemoveEffectLastTimeByItemIdS2CPacket(String itemId) {
        this.itemId = itemId;
    }

    public RemoveEffectLastTimeByItemIdS2CPacket(FriendlyByteBuf buf) {
        this.itemId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.itemId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.effectTimeLasts.removeIf(hudIcon -> hudIcon.url.equals("item/" + itemId));
        });
        return true;
    }
}
