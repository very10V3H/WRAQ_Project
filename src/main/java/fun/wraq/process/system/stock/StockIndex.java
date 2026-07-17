/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock;

public enum StockIndex {
    SHANGHAI("上证指数", "s_sh000001", ApiSource.SINA),
    SHENZHEN("深证成指", "s_sz399001", ApiSource.SINA),
    CHINEXT("创业板指", "s_sz399006", ApiSource.SINA),
    STAR50("科创50", "s_sh000688", ApiSource.SINA),
    CSI300("沪深300", "s_sh000300", ApiSource.SINA),
    HANGSENG("恒生指数", "int_hangseng", ApiSource.SINA),
    NASDAQ_COMP("纳斯达克综合指数", "^IXIC", ApiSource.YAHOO),
    NASDAQ100("纳斯达克100", "^NDX", ApiSource.YAHOO),
    XAU("伦敦金现货(XAU)", "GC=F", ApiSource.YAHOO),
    BRENT("布伦特原油期货", "BZ=F", ApiSource.YAHOO);

    private final String displayName;
    private final String apiSymbol;
    private final ApiSource apiSource;

    StockIndex(String displayName, String apiSymbol, ApiSource apiSource) {
        this.displayName = displayName;
        this.apiSymbol = apiSymbol;
        this.apiSource = apiSource;
    }

    public String getDisplayName() { return displayName; }
    public String getApiSymbol() { return apiSymbol; }
    public ApiSource getApiSource() { return apiSource; }

    public enum ApiSource { SINA, YAHOO }
}
