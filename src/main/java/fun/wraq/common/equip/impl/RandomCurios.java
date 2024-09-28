package fun.wraq.common.equip.impl;

import net.minecraft.world.item.ItemStack;

public interface RandomCurios {
    void setAttribute(ItemStack stack);

    double rate();
}
