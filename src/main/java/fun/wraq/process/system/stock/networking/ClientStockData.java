/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock.networking;

import fun.wraq.process.system.stock.StockIndex;
import fun.wraq.process.system.stock.StockKLineData;
import fun.wraq.process.system.stock.StockPosition;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ClientStockData {
    public static boolean marketPaused = false;
    public static final Map<StockIndex, PriceEntry> priceCache = new EnumMap<>(StockIndex.class);
    public static final List<StockPosition> positions = new ArrayList<>();

    // K-line data cache: index ordinal -> (scale -> list of bars)
    // 0=minute, 1=daily, 2=weekly
    public static final List<StockKLineData>[] klineCache = new List[3];
    public static int klineIndexOrdinal = 0;
    public static int klineScale = 0;

    static {
        for (StockIndex index : StockIndex.values()) {
            priceCache.put(index, new PriceEntry());
        }
        for (int i = 0; i < 3; i++) {
            klineCache[i] = new ArrayList<>();
        }
    }

    public static void updateKLineData(int indexOrdinal, int scale, List<StockKLineData> data) {
        klineIndexOrdinal = indexOrdinal;
        klineScale = scale;
        if (scale >= 0 && scale < 3) {
            klineCache[scale].clear();
            klineCache[scale].addAll(data);
        }
    }

    public static class PriceEntry {
        public double price = 0;
        public double changePercent = 0;
        public boolean available = false;
    }
}
