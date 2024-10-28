package fun.wraq.process.func.multiblockactive.rightclick.drive;

import net.minecraft.world.item.ItemStack;

public interface EnhanceCondition {
    boolean check(ItemStack stack);
}
