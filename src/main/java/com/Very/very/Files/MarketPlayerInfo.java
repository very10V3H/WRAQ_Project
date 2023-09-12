package com.Very.very.Files;

public class MarketPlayerInfo {
    private String player;
    private double get;
    public MarketPlayerInfo (String player, double get){
        this.player  = player;
        this.get = get;
    }

    public String getPlayer() {
        return this.player;
    }

    public double getGet() {
        return this.get;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setGet(double get) {
        this.get = get;
    }
}
