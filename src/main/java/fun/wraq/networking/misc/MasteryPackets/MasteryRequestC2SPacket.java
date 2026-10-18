/** AI-Generated, 2026-10-18 */
package fun.wraq.networking.misc.MasteryPackets;

import fun.wraq.process.system.mastery.MasteryData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 客户端请求精通数据（打开精通界面时发送）。
 */
public class MasteryRequestC2SPacket {

    public MasteryRequestC2SPacket() {
    }

    public MasteryRequestC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                MasteryData.sync(player);
            }
        });
        return true;
    }
}
