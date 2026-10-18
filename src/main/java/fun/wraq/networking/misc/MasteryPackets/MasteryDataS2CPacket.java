/** AI-Generated, 2026-10-18 */
package fun.wraq.networking.misc.MasteryPackets;

import fun.wraq.process.system.mastery.MasteryAllocation;
import fun.wraq.process.system.mastery.MasteryClientData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 精通完整数据同步（服务端 → 客户端）。
 */
public class MasteryDataS2CPacket {

    private final int total;
    private final int[][] allocation;

    public MasteryDataS2CPacket(int total, int[][] allocation) {
        this.total = total;
        this.allocation = MasteryAllocation.copy(allocation);
    }

    public MasteryDataS2CPacket(FriendlyByteBuf buf) {
        this.total = buf.readInt();
        this.allocation = new int[MasteryAllocation.TREE_COUNT][MasteryAllocation.NODES_PER_TREE];
        for (int tree = 0; tree < MasteryAllocation.TREE_COUNT; tree++) {
            for (int node = 0; node < MasteryAllocation.NODES_PER_TREE; node++) {
                this.allocation[tree][node] = buf.readInt();
            }
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.total);
        for (int tree = 0; tree < MasteryAllocation.TREE_COUNT; tree++) {
            for (int node = 0; node < MasteryAllocation.NODES_PER_TREE; node++) {
                buf.writeInt(this.allocation[tree][node]);
            }
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> MasteryClientData.update(total, allocation));
        return true;
    }
}
