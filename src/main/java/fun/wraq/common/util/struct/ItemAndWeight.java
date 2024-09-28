package fun.wraq.common.util.struct;

import net.minecraft.world.item.Item;

public class ItemAndWeight {
    private Item item;
    private int weight;

    public ItemAndWeight(Item item, int weight) {
        this.item = item;
        this.weight = weight;
    }

    public Item getItem() {
        return item;
    }

    public int getWeight() {
        return weight;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
