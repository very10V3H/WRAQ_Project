/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock;

import fun.wraq.process.system.stock.StockIndex.ApiSource;
import net.minecraftforge.event.TickEvent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class StockPriceFetcher {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    private static int fetchIndex = 0;
    private static int tickCounter = 0;

    public static void handleServerTick() {
        tickCounter++;
        // Fetch one index every 20 ticks, cycling through all 10
        if (tickCounter % 20 != 0) return;

        StockIndex[] indices = StockIndex.values();
        StockIndex index = indices[fetchIndex % indices.length];
        fetchIndex++;

        try {
            if (index.getApiSource() == ApiSource.SINA) {
                fetchFromSina(index);
            } else {
                fetchFromYahoo(index);
            }
            StockMarketData.consecutiveFailures = 0;
            StockMarketData.marketPaused = false;
            StockMarketData.lastSuccessfulFetch = System.currentTimeMillis();
            if (fetchIndex >= indices.length) {
                StockMarketData.initialized = true;
            }
        } catch (Exception e) {
            StockMarketData.markUnavailable(index);
            StockMarketData.consecutiveFailures++;
            if (StockMarketData.consecutiveFailures >= StockMarketData.MAX_FAILURES) {
                StockMarketData.marketPaused = true;
            }
            System.err.println("[Stock] Failed to fetch " + index.getDisplayName() + ": " + e.getMessage());
        }
    }

    private static void fetchFromSina(StockIndex index) throws Exception {
        String url = "http://hq.sinajs.cn/list=" + index.getApiSymbol();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Referer", "https://finance.sina.com.cn/")
                .header("User-Agent", "Mozilla/5.0")
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        if (body == null || body.isEmpty() || !body.contains("\"")) {
            throw new RuntimeException("Empty or invalid Sina response");
        }
        // Parse: var hq_str_s_sh000001="name,price,prevClose,...";
        String[] parts = body.split("\"");
        if (parts.length < 2) throw new RuntimeException("Sina parse error: no quoted data");
        String[] values = parts[1].split(",");
        if (values.length < 3) throw new RuntimeException("Sina parse error: insufficient fields");
        double price = Double.parseDouble(values[1]);
        double prevClose = Double.parseDouble(values[2]);
        StockMarketData.updatePrice(index, price, prevClose);
    }

    private static void fetchFromYahoo(StockIndex index) throws Exception {
        String symbol = index.getApiSymbol().replace("^", "%5E");
        String url = "https://query1.finance.yahoo.com/v8/finance/chart/" + symbol
                + "?range=1d&interval=1d";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        if (body == null || body.isEmpty()) {
            throw new RuntimeException("Empty Yahoo response");
        }
        // Manually extract regularMarketPrice from JSON
        double price = extractJsonDouble(body, "regularMarketPrice");
        double prevClose = extractJsonDouble(body, "regularMarketPreviousClose");
        if (Double.isNaN(price)) {
            // Try previousClose as fallback for current price
            price = extractJsonDouble(body, "previousClose");
        }
        if (Double.isNaN(price)) {
            throw new RuntimeException("Yahoo parse error: no price found");
        }
        if (Double.isNaN(prevClose)) {
            prevClose = price;
        }
        StockMarketData.updatePrice(index, price, prevClose);
    }

    /** Simple JSON number extraction without a JSON library — finds `"key":value` patterns */
    private static double extractJsonDouble(String json, String key) {
        String searchKey = "\"" + key + "\"";
        int keyIdx = json.indexOf(searchKey);
        if (keyIdx < 0) return Double.NaN;
        int colonIdx = json.indexOf(':', keyIdx + searchKey.length());
        if (colonIdx < 0) return Double.NaN;
        // Skip whitespace and optional quotes
        int start = colonIdx + 1;
        while (start < json.length() && (json.charAt(start) == ' ' || json.charAt(start) == '"')) {
            start++;
        }
        int end = start;
        while (end < json.length() && (Character.isDigit(json.charAt(end))
                || json.charAt(end) == '.' || json.charAt(end) == '-')) {
            end++;
        }
        if (end == start) return Double.NaN;
        try {
            return Double.parseDouble(json.substring(start, end));
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }
}
