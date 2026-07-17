/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock;

public class StockPosition {
    private StockIndex index;
    private int leverage;
    private double buyPrice;
    private int quantity;
    private String currencyType;
    private long buyTimestamp;
    private double investment;

    public StockPosition(StockIndex index, int leverage, double buyPrice, int quantity,
                         String currencyType, long buyTimestamp, double investment) {
        this.index = index;
        this.leverage = leverage;
        this.buyPrice = buyPrice;
        this.quantity = quantity;
        this.currencyType = currencyType;
        this.buyTimestamp = buyTimestamp;
        this.investment = investment;
    }

    public StockIndex getIndex() { return index; }
    public void setIndex(StockIndex v) { this.index = v; }
    public int getLeverage() { return leverage; }
    public void setLeverage(int v) { this.leverage = v; }
    public double getBuyPrice() { return buyPrice; }
    public void setBuyPrice(double v) { this.buyPrice = v; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int v) { this.quantity = v; }
    public String getCurrencyType() { return currencyType; }
    public void setCurrencyType(String v) { this.currencyType = v; }
    public long getBuyTimestamp() { return buyTimestamp; }
    public void setBuyTimestamp(long v) { this.buyTimestamp = v; }
    public double getInvestment() { return investment; }
    public void setInvestment(double v) { this.investment = v; }
}
