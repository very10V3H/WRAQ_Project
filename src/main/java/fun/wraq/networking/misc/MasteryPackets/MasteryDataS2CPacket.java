/** AI-Generated, 2026-10-18 */
package fun.wraq.networking.misc.MasteryPackets;

import fun.wraq.process.system.mastery.MasteryAllocation;
import fun.wraq.process.system.mastery.MasteryClientData;
import fun.wraq.process.system.mastery.MasteryData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 精通完整数据同步（服务端 → 客户端）。
 */
public class MasteryDataS2CPacket {

    private final int total;
    private final int[][] allocation;
    private final String[] presetNames;
    private final int activePreset;

    public MasteryDataS2CPacket(int total, int[][] allocation,
                                String[] presetNames, int activePreset) {
        this.total = total;
        this.allocation = MasteryAllocation.copy(allocation);
        this.presetNames = presetNames == null ? new String[0] : presetNames.clone();
        this.activePreset = activePreset;
    }

    public MasteryDataS2CPacket(FriendlyByteBuf buf) {
        this.total = buf.readInt();
        this.allocation = new int[MasteryAllocation.TREE_COUNT][MasteryAllocation.NODES_PER_TREE];
        for (int tree = 0; tree < MasteryAllocation.TREE_COUNT; tree++) {
            for (int node = 0; node < MasteryAllocation.NODES_PER_TREE; node++) {
                this.allocation[tree][node] = buf.readInt();
            }
        }
        this.presetNames = new String[MasteryData.PRESET_COUNT];
        for (int page = 0; page < MasteryData.PRESET_COUNT; page++) {
            this.presetNames[page] = buf.readUtf(32);
        }
        this.activePreset = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.total);
        for (int tree = 0; tree < MasteryAllocation.TREE_COUNT; tree++) {
            for (int node = 0; node < MasteryAllocation.NODES_PER_TREE; node++) {
                buf.writeInt(this.allocation[tree][node]);
            }
        }
        for (int page = 0; page < MasteryData.PRESET_COUNT; page++) {
            buf.writeUtf(presetNames.length > page && presetNames[page] != null
                    ? presetNames[page] : "配置 " + (page + 1), 32);
        }
        buf.writeInt(activePreset);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> MasteryClientData.update(
                total, allocation, presetNames, activePreset));
        return true;
    }
}
