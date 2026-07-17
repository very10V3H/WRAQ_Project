/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock;

public class StockPriceData {
    private double currentPrice = 0;
    private double previousClose = 0;
    private double changePercent = 0;
    private long lastUpdateTime = 0;
    private boolean available = false;

    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double v) { this.currentPrice = v; }
    public double getPreviousClose() { return previousClose; }
    public void setPreviousClose(double v) { this.previousClose = v; }
    public double getChangePercent() { return changePercent; }
    public void setChangePercent(double v) { this.changePercent = v; }
    public long getLastUpdateTime() { return lastUpdateTime; }
    public void setLastUpdateTime(long v) { this.lastUpdateTime = v; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean v) { this.available = v; }
}
