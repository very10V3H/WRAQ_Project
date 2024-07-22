package com.very.wraq.render.gui.villagerTrade;

import net.minecraft.world.item.Item;

public class Goods {
    private Item item;
    private int num;

    public Goods(Item item, int num) {
        this.item = item;
        this.num = num;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object obj) {
        Goods goods = (Goods) obj;
        return goods.num == this.num && goods.item.equals(this.item);
    }


}
