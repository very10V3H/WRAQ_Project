package fun.wraq.common.util.items;

import net.minecraft.world.item.ItemStack;

public interface AdjustStackBeforeGive {
    void adjust(ItemStack stack);
}
