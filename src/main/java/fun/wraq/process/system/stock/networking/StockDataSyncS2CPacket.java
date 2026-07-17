/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock.networking;

import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.stock.StockIndex;
import fun.wraq.process.system.stock.StockMarketData;
import fun.wraq.process.system.stock.StockPriceData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StockDataSyncS2CPacket {
    private final boolean marketPaused;
    private final double[] prices;
    private final double[] changePercents;
    private final boolean[] available;

    public StockDataSyncS2CPacket(boolean marketPaused, double[] prices, double[] changePercents, boolean[] available) {
        this.marketPaused = marketPaused;
        this.prices = prices;
        this.changePercents = changePercents;
        this.available = available;
    }

    public StockDataSyncS2CPacket(FriendlyByteBuf buf) {
        marketPaused = buf.readBoolean();
        int count = StockIndex.values().length;
        prices = new double[count];
        changePercents = new double[count];
        available = new boolean[count];
        for (int i = 0; i < count; i++) {
            prices[i] = buf.readDouble();
            changePercents[i] = buf.readDouble();
            available[i] = buf.readBoolean();
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(marketPaused);
        StockIndex[] indices = StockIndex.values();
        for (StockIndex index : indices) {
            StockPriceData data = StockMarketData.getPrice(index);
            if (data != null) {
                buf.writeDouble(data.getCurrentPrice());
                buf.writeDouble(data.getChangePercent());
                buf.writeBoolean(data.isAvailable());
            } else {
                buf.writeDouble(0);
                buf.writeDouble(0);
                buf.writeBoolean(false);
            }
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientStockData.marketPaused = marketPaused;
            StockIndex[] indices = StockIndex.values();
            for (int i = 0; i < indices.length; i++) {
                ClientStockData.PriceEntry entry = ClientStockData.priceCache.get(indices[i]);
                if (entry == null) {
                    entry = new ClientStockData.PriceEntry();
                    ClientStockData.priceCache.put(indices[i], entry);
                }
                if (i < prices.length) {
                    entry.price = prices[i];
                    entry.changePercent = changePercents[i];
                    entry.available = available[i];
                }
            }
        });
        return true;
    }

    public static void sendTo(ServerPlayer player) {
        StockIndex[] indices = StockIndex.values();
        double[] prices = new double[indices.length];
        double[] changes = new double[indices.length];
        boolean[] avail = new boolean[indices.length];
        for (int i = 0; i < indices.length; i++) {
            StockPriceData data = StockMarketData.getPrice(indices[i]);
            if (data != null) {
                prices[i] = data.getCurrentPrice();
                changes[i] = data.getChangePercent();
                avail[i] = data.isAvailable();
            }
        }
        ModNetworking.sendToClient(
                new StockDataSyncS2CPacket(StockMarketData.marketPaused, prices, changes, avail),
                player);
    }
}
