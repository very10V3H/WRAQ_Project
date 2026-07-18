/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock;

import com.mojang.logging.LogUtils;
import fun.wraq.process.system.stock.StockIndex.ApiSource;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockKLineFetcher {

    /** Minute-level K-line (分时) — 5min bars for current day */
    public static final int SCALE_MINUTE = 0;
    /** Daily K-line (日K) */
    public static final int SCALE_DAILY = 1;
    /** Weekly K-line (周K) */
    public static final int SCALE_WEEKLY = 2;

    // Cache: index -> scale -> kline list
    private static final Map<StockIndex, Map<Integer, List<StockKLineData>>> cache = new EnumMap<>(StockIndex.class);
    private static final Map<StockIndex, Map<Integer, Long>> cacheTimestamps = new EnumMap<>(StockIndex.class);
    private static final long CACHE_TTL_MS = 120_000; // 2 min cache

    static {
        for (StockIndex index : StockIndex.values()) {
            Map<Integer, List<StockKLineData>> dataMap = new HashMap<>();
            dataMap.put(SCALE_MINUTE, new ArrayList<>());
            dataMap.put(SCALE_DAILY, new ArrayList<>());
            dataMap.put(SCALE_WEEKLY, new ArrayList<>());
            cache.put(index, dataMap);

            Map<Integer, Long> tsMap = new HashMap<>();
            tsMap.put(SCALE_MINUTE, 0L);
            tsMap.put(SCALE_DAILY, 0L);
            tsMap.put(SCALE_WEEKLY, 0L);
            cacheTimestamps.put(index, tsMap);
        }
    }

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public static synchronized List<StockKLineData> getKLineData(StockIndex index, int scale) {
        Map<Integer, List<StockKLineData>> indexCache = cache.get(index);
        Map<Integer, Long> indexTs = cacheTimestamps.get(index);
        if (indexCache == null || indexTs == null) return List.of();

        long now = System.currentTimeMillis();
        Long lastFetch = indexTs.get(scale);
        List<StockKLineData> cached = indexCache.get(scale);
        if (lastFetch != null && (now - lastFetch) < CACHE_TTL_MS && cached != null && !cached.isEmpty()) {
            return new ArrayList<>(cached);
        }

        LogUtils.getLogger().info("[Stock] try to fetch {}", index.getDisplayName());
        List<StockKLineData> result;
        try {
            if (index.getApiSource() == ApiSource.SINA) {
                result = fetchFromSina(index, scale);
            } else {
                result = fetchFromYahoo(index, scale);
            }
            if (result != null && !result.isEmpty()) {
                indexCache.put(scale, result);
                indexTs.put(scale, now);
            }
            return result != null ? result : List.of();
        } catch (Exception e) {
            System.err.println("[StockKLine] Failed to fetch " + index.getDisplayName() + " scale=" + scale + ": " + e.getMessage());
            // Return stale cache if available
            if (cached != null && !cached.isEmpty()) return new ArrayList<>(cached);
            return List.of();
        }
    }

    private static List<StockKLineData> fetchFromSina(StockIndex index, int scale) throws Exception {
        // Sina K-line API
        // scale mapping: 5=5min, 15=15min, 30=30min, 60=60min, 240=daily, 1200=weekly
        String sinaScale;
        int datalen;
        switch (scale) {
            case SCALE_MINUTE -> { sinaScale = "5"; datalen = 60; }
            case SCALE_DAILY  -> { sinaScale = "240"; datalen = 60; }
            case SCALE_WEEKLY -> { sinaScale = "1200"; datalen = 40; }
            default -> throw new IllegalArgumentException("Unknown scale: " + scale);
        }

        String url = "http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData"
                + "?symbol=" + index.getApiSymbol()
                + "&scale=" + sinaScale
                + "&datalen=" + datalen;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Referer", "https://finance.sina.com.cn/")
                .header("User-Agent", "Mozilla/5.0")
                .timeout(Duration.ofSeconds(5))
                .GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        if (body == null || body.isEmpty() || !body.startsWith("[")) {
            throw new RuntimeException("Invalid Sina K-line response");
        }
        return parseSinaKLineJson(body, scale);
    }

    private static List<StockKLineData> fetchFromYahoo(StockIndex index, int scale) throws Exception {
        String interval;
        String range;
        switch (scale) {
            case SCALE_MINUTE -> { interval = "5m"; range = "1d"; }
            case SCALE_DAILY  -> { interval = "1d"; range = "3mo"; }
            case SCALE_WEEKLY -> { interval = "1wk"; range = "1y"; }
            default -> throw new IllegalArgumentException("Unknown scale: " + scale);
        }

        String symbol = index.getApiSymbol().replace("^", "%5E");
        String url = "https://query1.finance.yahoo.com/v8/finance/chart/" + symbol
                + "?range=" + range + "&interval=" + interval;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .timeout(Duration.ofSeconds(5))
                .GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        if (body == null || body.isEmpty()) throw new RuntimeException("Empty Yahoo response");

        return parseYahooChartJson(body, scale);
    }

    /** Parse Sina K-line JSON array: [{"day":"...", "open":"...", "high":"...", "low":"...", "close":"...", "volume":"..."}, ...] */
    private static List<StockKLineData> parseSinaKLineJson(String json, int scale) {
        List<StockKLineData> result = new ArrayList<>();
        int pos = 0;
        while ((pos = json.indexOf("\"day\":\"", pos)) >= 0) {
            pos += 7;
            int timeEnd = json.indexOf('"', pos);
            if (timeEnd < 0) break;
            String time = json.substring(pos, timeEnd);
            // Shorten time display
            if (scale == SCALE_MINUTE && time.length() >= 16) {
                time = time.substring(11, 16); // "09:35"
            } else if (scale == SCALE_DAILY && time.length() >= 10) {
                time = time.substring(5, 10); // "05-17"
            } else if (scale == SCALE_WEEKLY && time.length() >= 10) {
                time = time.substring(5, 10);
            }
            double open = extractField(json, timeEnd, "open");
            double high = extractField(json, timeEnd, "high");
            double low = extractField(json, timeEnd, "low");
            double close = extractField(json, timeEnd, "close");
            double volume = extractField(json, timeEnd, "volume");
            if (!Double.isNaN(open) && !Double.isNaN(close)) {
                result.add(new StockKLineData(time, open, high, low, close, volume));
            }
        }
        return result;
    }

    /** Parse Yahoo Finance chart JSON for OHLCV bars */
    private static List<StockKLineData> parseYahooChartJson(String json, int scale) {
        List<StockKLineData> result = new ArrayList<>();
        // Find "timestamp" array start
        int tsStart = json.indexOf("\"timestamp\"");
        if (tsStart < 0) return result;
        int bracketStart = json.indexOf('[', tsStart);
        if (bracketStart < 0) return result;

        // Find "indicators" → "quote" → first object with open/high/low/close/volume
        int quoteStart = json.indexOf("\"quote\"");
        if (quoteStart < 0) return result;
        int quoteBracket = json.indexOf('[', quoteStart);
        if (quoteBracket < 0) return result;

        // Parse arrays: open, high, low, close, volume
        double[] opens = parseNumberArray(json, "open", quoteBracket);
        double[] highs = parseNumberArray(json, "high", quoteBracket);
        double[] lows = parseNumberArray(json, "low", quoteBracket);
        double[] closes = parseNumberArray(json, "close", quoteBracket);
        double[] volumes = parseNumberArray(json, "volume", quoteBracket);

        // Parse timestamps
        List<Long> timestamps = new ArrayList<>();
        int tsArrayEnd = json.indexOf(']', bracketStart);
        if (tsArrayEnd > bracketStart) {
            String tsStr = json.substring(bracketStart + 1, tsArrayEnd);
            for (String part : tsStr.split(",")) {
                try { timestamps.add(Long.parseLong(part.trim())); } catch (NumberFormatException ignored) {}
            }
        }

        int count = Math.min(opens.length, Math.min(closes.length, timestamps.size()));
        for (int i = 0; i < count; i++) {
            long ts = timestamps.get(i);
            String timeLabel;
            java.time.Instant instant = java.time.Instant.ofEpochSecond(ts);
            java.time.ZoneId zone = java.time.ZoneId.of("Asia/Shanghai");
            java.time.ZonedDateTime dt = instant.atZone(zone);
            if (scale == SCALE_MINUTE) {
                timeLabel = dt.toLocalTime().toString().substring(0, 5); // "09:35"
            } else if (scale == SCALE_WEEKLY) {
                timeLabel = dt.toLocalDate().toString().substring(5, 10); // "05-17"
            } else {
                timeLabel = dt.toLocalDate().toString().substring(5, 10);
            }
            double vol = i < volumes.length ? volumes[i] : 0;
            result.add(new StockKLineData(timeLabel,
                    opens[i],
                    i < highs.length ? highs[i] : opens[i],
                    i < lows.length ? lows[i] : opens[i],
                    closes[i],
                    vol));
        }
        return result;
    }

    private static double extractField(String json, int searchFrom, String fieldName) {
        String key = "\"" + fieldName + "\":\"";
        int idx = json.indexOf(key, searchFrom);
        if (idx < 0) {
            // Try without quotes (numeric)
            key = "\"" + fieldName + "\":";
            idx = json.indexOf(key, searchFrom);
            if (idx < 0) return Double.NaN;
            idx += key.length();
            int end = idx;
            while (end < json.length() && (Character.isDigit(json.charAt(end))
                    || json.charAt(end) == '.' || json.charAt(end) == '-')) {
                end++;
            }
            if (end == idx) return Double.NaN;
            try { return Double.parseDouble(json.substring(idx, end)); } catch (NumberFormatException e) { return Double.NaN; }
        }
        idx += key.length();
        int end = json.indexOf('"', idx);
        if (end < 0) return Double.NaN;
        try { return Double.parseDouble(json.substring(idx, end)); } catch (NumberFormatException e) { return Double.NaN; }
    }

    private static double[] parseNumberArray(String json, String fieldName, int searchFrom) {
        String key = "\"" + fieldName + "\":[";
        int idx = json.indexOf(key, searchFrom);
        if (idx < 0) return new double[0];
        idx += key.length();
        int end = json.indexOf(']', idx);
        if (end < 0) return new double[0];
        String arr = json.substring(idx, end);
        String[] parts = arr.split(",");
        double[] result = new double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String s = parts[i].trim();
            if ("null".equals(s)) {
                result[i] = Double.NaN;
            } else {
                try { result[i] = Double.parseDouble(s); } catch (NumberFormatException e) { result[i] = Double.NaN; }
            }
        }
        return result;
    }
}
