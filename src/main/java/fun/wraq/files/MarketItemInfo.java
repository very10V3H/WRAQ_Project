package fun.wraq.files;

import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import fun.wraq.events.core.InventoryCheck;
import net.minecraft.world.item.ItemStack;

public class MarketItemInfo {

    public final String playerName;
    public final ItemStack itemStack;
    public final int price;
    public final int type; // 0 = VB, 1 = GB

    public MarketItemInfo(String player, ItemStack itemStack, int price, int type) {
        this.playerName = player;
        this.itemStack = itemStack;
        this.price = price;
        this.type = type;
    }

    public int getItemStackCount() {
        return itemStack.getCount();
    }

    public static boolean itemCanBeSold(ItemStack itemStack) {
        if (itemStack.getItem().toString().contains("backpack")) return false;
        if (Utils.uniformList.contains(itemStack.getItem())) return false;
        if (Compute.getItemStackString(itemStack).contains("*")) return false;
        return !InventoryCheck.getBoundingList().contains(itemStack.getItem());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MarketItemInfo anotherObj) {
            return this.playerName.equals(anotherObj.playerName)
                    && Compute.getItemStackString(this.itemStack).equals(Compute.getItemStackString(anotherObj.itemStack))
                    && this.price == anotherObj.price && this.type == anotherObj.type;
        }
        return super.equals(obj);
    }
}
