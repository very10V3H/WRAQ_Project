/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock.networking;

import fun.wraq.process.system.stock.StockIndex;
import fun.wraq.process.system.stock.StockKLineFetcher;
import fun.wraq.process.system.stock.StockKLineData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class StockKLineRequestC2SPacket {
    private final int indexOrdinal;
    private final int scale; // 0=minute, 1=daily, 2=weekly

    public StockKLineRequestC2SPacket(int indexOrdinal, int scale) {
        this.indexOrdinal = indexOrdinal;
        this.scale = scale;
    }

    public StockKLineRequestC2SPacket(FriendlyByteBuf buf) {
        this.indexOrdinal = buf.readInt();
        this.scale = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(indexOrdinal);
        buf.writeInt(scale);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            StockIndex[] indices = StockIndex.values();
            if (indexOrdinal < 0 || indexOrdinal >= indices.length) return;
            StockIndex index = indices[indexOrdinal];
            List<StockKLineData> data = StockKLineFetcher.getKLineData(index, scale);
            StockKLineSyncS2CPacket.sendTo(player, indexOrdinal, scale, data);
        });
        return true;
    }
}
