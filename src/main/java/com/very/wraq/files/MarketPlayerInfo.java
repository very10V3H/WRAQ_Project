package com.very.wraq.files;

public class MarketPlayerInfo {
    private String player;
    private double profit;

    public MarketPlayerInfo(String player, double profit) {
        this.player = player;
        this.profit = profit;
    }

    public String getPlayer() {
        return this.player;
    }

    public double getProfit() {
        return this.profit;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}
