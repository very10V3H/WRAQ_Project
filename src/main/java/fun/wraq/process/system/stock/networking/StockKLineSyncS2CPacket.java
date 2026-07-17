/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock.networking;

import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.stock.StockKLineData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class StockKLineSyncS2CPacket {
    private final int indexOrdinal;
    private final int scale;
    private final List<StockKLineData> bars;

    public StockKLineSyncS2CPacket(int indexOrdinal, int scale, List<StockKLineData> bars) {
        this.indexOrdinal = indexOrdinal;
        this.scale = scale;
        this.bars = bars;
    }

    public StockKLineSyncS2CPacket(FriendlyByteBuf buf) {
        this.indexOrdinal = buf.readInt();
        this.scale = buf.readInt();
        int count = buf.readInt();
        this.bars = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String time = buf.readUtf();
            double open = buf.readDouble();
            double high = buf.readDouble();
            double low = buf.readDouble();
            double close = buf.readDouble();
            double volume = buf.readDouble();
            this.bars.add(new StockKLineData(time, open, high, low, close, volume));
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(indexOrdinal);
        buf.writeInt(scale);
        buf.writeInt(bars.size());
        for (StockKLineData bar : bars) {
            buf.writeUtf(bar.getTime());
            buf.writeDouble(bar.getOpen());
            buf.writeDouble(bar.getHigh());
            buf.writeDouble(bar.getLow());
            buf.writeDouble(bar.getClose());
            buf.writeDouble(bar.getVolume());
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientStockData.updateKLineData(indexOrdinal, scale, bars);
        });
        return true;
    }

    public static void sendTo(ServerPlayer player, int indexOrdinal, int scale, List<StockKLineData> data) {
        ModNetworking.sendToClient(new StockKLineSyncS2CPacket(indexOrdinal, scale, data), player);
    }
}
