package fun.wraq.process.system.backpack.audit.networking;

import fun.wraq.process.system.backpack.audit.AuditBackpackProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * AI-Generated, 2026-10-18
 * C2S：客户端已把背包内容写入自己的 {@code BackpackStorage} 副本，通知服务端可以打开界面。
 */
public class AuditBackpackReadyC2SPacket {

    private final int requestId;

    public AuditBackpackReadyC2SPacket(int requestId) {
        this.requestId = requestId;
    }

    public AuditBackpackReadyC2SPacket(FriendlyByteBuf buf) {
        this.requestId = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(requestId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                AuditBackpackProvider.onClientReady(player, requestId);
            }
        });
        return true;
    }
}
