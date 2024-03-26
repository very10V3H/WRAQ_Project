package com.Very.very.Files;

public class MarketItemInfo {
    private String playerName;
    private String itemStackName;
    private double price;
    public MarketItemInfo (String player, String itemStack, double Price) {
        this.playerName = player;
        this.itemStackName = itemStack;
        this.price = Price;
    }
    public String getPlayer() {
        return playerName;
    }

    public void setPlayer(String player) {
        this.playerName = player;
    }

    public String getItemStack() {
        return itemStackName;
    }

    public void setItemStack(String itemStackName) {
        this.itemStackName = itemStackName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public String TransformInfoToString () {
        String Info;
        Info = playerName + "#";
        Info += itemStackName + "*";
        Info += price + "$";
        return Info;
    }
    public String getItemStackName() {
        for(int i = 0; i < itemStackName.length(); i++){
            if(itemStackName.charAt(i) == ' '){
                return itemStackName.substring(i+1);
            }
        }
        return " ";
    }
    public int getItemStackCount() {
        for(int i = 0; i < itemStackName.length(); i++){
            if(itemStackName.charAt(i) == ' '){
                return Integer.parseInt(itemStackName.substring(0,i));
            }
        }
        return 0;
    }
}
