package fun.wraq.common.impl.display;

import net.minecraft.world.item.ItemStack;

public interface BeforeRemoveMaterialOnForge {
    void beforeRemoveMaterialOnForge(ItemStack product, ItemStack removingStack);
}
