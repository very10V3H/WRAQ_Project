package fun.wraq.series.overworld.c1;

import fun.wraq.common.util.Utils;
import fun.wraq.series.WraqItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NewC1Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> GRAY_SLIME_BALL = ITEMS.register("gray_slime_ball",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));
}
