/** AI-Generated, 2026-10-25 */
package fun.wraq.networking.misc.MasteryPackets;

import fun.wraq.process.system.mastery.MasteryData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 客户端请求重命名精通页。
 */
public class MasteryPresetRenameC2SPacket {

    private final int page;
    private final String name;

    public MasteryPresetRenameC2SPacket(int page, String name) {
        this.page = page;
        this.name = name == null ? "" : name;
    }

    public MasteryPresetRenameC2SPacket(FriendlyByteBuf buf) {
        this.page = buf.readInt();
        this.name = buf.readUtf(32);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(page);
        buf.writeUtf(name, 32);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                MasteryData.renamePreset(player, page, name);
            }
        });
        return true;
    }
}
