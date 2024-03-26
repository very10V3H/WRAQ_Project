package com.Very.very.ValueAndTools.Utils;

import com.Very.very.ValueAndTools.Compute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.io.IOException;

public class ItemAndRate {
    private final ItemStack itemStack;
    private final double rate;
    public ItemAndRate (ItemStack itemStack, double rate) {
        this.itemStack = itemStack;
        this.rate = rate;
    }
    public void Give (Player player) throws IOException {
        if (rate > 1) {
            int Num = (int) Math.floor(rate);
            itemStack.setCount(Num);
            Compute.ItemStackGive(player,itemStack);
        }
        else {
            Compute.RateItemStackGive(itemStack, rate, player);
        }
    }
}
