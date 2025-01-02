package fun.wraq.files;

public class MarketProfitInfo {

    public final String playerName;
    public final int profit;
    public final int type;

    public MarketProfitInfo(String playerName, int profit, int type) {
        this.playerName = playerName;
        this.profit = profit;
        this.type = type;
    }

    @Override
    public String toString() {
        return playerName + "#" + profit + "^" + type;
    }

    public static MarketProfitInfo getMarketProfitInfoFromString(String info) {
        int index1 = info.indexOf('#');
        int index2 = info.lastIndexOf("^");
        String playerName = info.substring(0, index1);
        int profit = Integer.parseInt(info.substring(index1 + 1, index2));
        int type = Integer.parseInt(info.substring(index2 + 1));
        return new MarketProfitInfo(playerName, profit, type);
    }
}
