package fun.wraq.common.fast;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class Is {
    public static ItemStack n(Item item, int count) {
        return new ItemStack(item, count);
    }

    public static ItemStack n(RegistryObject<Item> item, int count) {
        return new ItemStack(item.get(), count);
    }

    public static ItemStack n(RegistryObject<Item> item) {
        return new ItemStack(item.get());
    }
}
