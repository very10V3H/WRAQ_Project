package fun.wraq.series.instance.series.mushroom;

import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.instance.series.mushroom.gem.MushroomParasitismGem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MushroomItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> PARASITISM_GEM_MUSHROOM = ITEMS.register("parasitism_gem_mushroom",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MUSHROOM_RARITY), false,
                    MushroomParasitismGem::onPickUp));

    public static final RegistryObject<Item> BROWN_MUSHROOM = ITEMS.register("brown_mushroom",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MUSHROOM_RARITY)));

    public static final RegistryObject<Item> RED_MUSHROOM = ITEMS.register("red_mushroom",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY)));


}
