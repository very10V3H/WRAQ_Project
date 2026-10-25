/** AI-Generated, 2026-10-25 */
package fun.wraq.networking.misc.MasteryPackets;

import fun.wraq.process.system.mastery.MasteryData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 客户端请求一键切换精通页。
 */
public class MasteryPresetSwitchC2SPacket {

    private final int page;

    public MasteryPresetSwitchC2SPacket(int page) {
        this.page = page;
    }

    public MasteryPresetSwitchC2SPacket(FriendlyByteBuf buf) {
        this.page = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(page);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                MasteryData.switchPreset(player, page);
            }
        });
        return true;
    }
}
