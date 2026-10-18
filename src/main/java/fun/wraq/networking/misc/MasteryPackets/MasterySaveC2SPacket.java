/** AI-Generated, 2026-10-18 */
package fun.wraq.networking.misc.MasteryPackets;

import fun.wraq.process.system.mastery.MasteryAllocation;
import fun.wraq.process.system.mastery.MasteryData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 客户端提交完整精通配置（服务端保存前会重新校验）。
 */
public class MasterySaveC2SPacket {

    private final int[][] allocation;

    public MasterySaveC2SPacket(int[][] allocation) {
        this.allocation = MasteryAllocation.copy(allocation);
    }

    public MasterySaveC2SPacket(FriendlyByteBuf buf) {
        this.allocation = new int[MasteryAllocation.TREE_COUNT][MasteryAllocation.NODES_PER_TREE];
        for (int tree = 0; tree < MasteryAllocation.TREE_COUNT; tree++) {
            for (int node = 0; node < MasteryAllocation.NODES_PER_TREE; node++) {
                this.allocation[tree][node] = buf.readInt();
            }
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        for (int tree = 0; tree < MasteryAllocation.TREE_COUNT; tree++) {
            for (int node = 0; node < MasteryAllocation.NODES_PER_TREE; node++) {
                buf.writeInt(this.allocation[tree][node]);
            }
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }
            MasteryData.save(player, allocation);
        });
        return true;
    }
}
