/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock;

import java.util.EnumMap;
import java.util.Map;

public class StockMarketData {

    public static final Map<StockIndex, StockPriceData> prices = new EnumMap<>(StockIndex.class);

    public static boolean marketPaused = false;
    public static int consecutiveFailures = 0;
    public static final int MAX_FAILURES = 3;
    public static long lastSuccessfulFetch = 0;
    /** Poll interval in milliseconds — sell cooldown is 2x this value */
    public static final long POLL_INTERVAL_MS = 30000;
    /** Whether all 10 indices have been fetched at least once */
    public static boolean initialized = false;

    static {
        for (StockIndex index : StockIndex.values()) {
            prices.put(index, new StockPriceData());
        }
    }

    public static StockPriceData getPrice(StockIndex index) {
        return prices.get(index);
    }

    public static double getCurrentPrice(StockIndex index) {
        StockPriceData data = prices.get(index);
        return data != null ? data.getCurrentPrice() : 0;
    }

    public static void updatePrice(StockIndex index, double price, double previousClose) {
        StockPriceData data = prices.get(index);
        if (data == null) return;
        data.setCurrentPrice(price);
        data.setPreviousClose(previousClose);
        data.setChangePercent(previousClose > 0 ? ((price - previousClose) / previousClose) * 100 : 0);
        data.setLastUpdateTime(System.currentTimeMillis());
        data.setAvailable(true);
    }

    public static void markUnavailable(StockIndex index) {
        StockPriceData data = prices.get(index);
        if (data != null) data.setAvailable(false);
    }
}
