/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock;

/**
 * A single OHLCV bar for K-line chart rendering.
 */
public class StockKLineData {
    private String time;     // "09:35" or "2026-05-17"
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;

    public StockKLineData(String time, double open, double high, double low, double close, double volume) {
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public String getTime() { return time; }
    public void setTime(String v) { this.time = v; }
    public double getOpen() { return open; }
    public void setOpen(double v) { this.open = v; }
    public double getHigh() { return high; }
    public void setHigh(double v) { this.high = v; }
    public double getLow() { return low; }
    public void setLow(double v) { this.low = v; }
    public double getClose() { return close; }
    public void setClose(double v) { this.close = v; }
    public double getVolume() { return volume; }
    public void setVolume(double v) { this.volume = v; }
}
