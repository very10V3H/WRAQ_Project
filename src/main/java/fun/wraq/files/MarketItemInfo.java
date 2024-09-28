package fun.wraq.files;

import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import fun.wraq.events.core.InventoryCheck;
import net.minecraft.world.item.ItemStack;

public class MarketItemInfo {
    private String playerName;
    private ItemStack itemStack;
    private double price;

    public MarketItemInfo(String player, ItemStack itemStack, double Price) {
        this.playerName = player;
        this.itemStack = itemStack;
        this.price = Price;
    }

    public String getPlayer() {
        return playerName;
    }

    public void setPlayer(String player) {
        this.playerName = player;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String TransformInfoToString() {
        String Info;
        Info = playerName + "#";
        Info += itemStack + "*";
        Info += price + "$";
        return Info;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getItemStackCount() {
        return itemStack.getCount();
    }

    public static boolean itemCanBeSold(ItemStack itemStack) {
        if (itemStack.getItem().toString().contains("backpack")) return false;
        if (Utils.uniformList.contains(itemStack.getItem())) return false;
        return !InventoryCheck.getBoundingList().contains(itemStack.getItem());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MarketItemInfo anotherObj) {
            return this.playerName.equals(anotherObj.playerName)
                    && Compute.getItemStackString(this.itemStack).equals(Compute.getItemStackString(anotherObj.itemStack))
                    && this.price == anotherObj.price;
        }
        return super.equals(obj);
    }
}
