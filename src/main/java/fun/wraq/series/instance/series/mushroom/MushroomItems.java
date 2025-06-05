package fun.wraq.series.instance.series.mushroom;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.items.misc.PocketItem;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.instance.series.mushroom.gem.MushroomParasitismGem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class MushroomItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> PARASITISM_GEM_MUSHROOM
            = ITEMS.register("parasitism_gem_mushroom",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MUSHROOM_RARITY), false,
                    MushroomParasitismGem::onPickUp));

    public static final RegistryObject<Item> PARASITISM_GEM_ENHANCED_MUSHROOM
            = ITEMS.register("parasitism_gem_enhanced_mushroom",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MUSHROOM_RARITY), false,
                    MushroomParasitismGem::onEnhancedPickUp));

    public static final RegistryObject<Item> BROWN_MUSHROOM = ITEMS.register("brown_mushroom",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MUSHROOM_RARITY)));
    public static final RegistryObject<Item> BROWN_MUSHROOM_POCKET = ITEMS.register("brown_mushroom_pocket",
            () -> new PocketItem(new Item.Properties().rarity(CustomStyle.MUSHROOM_RARITY), BROWN_MUSHROOM.get()));

    public static final RegistryObject<Item> RED_MUSHROOM = ITEMS.register("red_mushroom",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY), true, true,
                    List.of(Te.s("菌菇屋二楼", CustomStyle.MUSHROOM_STYLE, "似乎有它的用武之处"))));

    public static final RegistryObject<Item> NETHER_MUSHROOM = ITEMS.register("nether_mushroom_new",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.RedItalic)));

    public static final RegistryObject<Item> UNKNOWN_GEM = ITEMS.register("unknown_gem",
            () -> new UnknownGem(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY)));

    public static final RegistryObject<Item> UNKNOWN_MUSHROOM = ITEMS.register("unknown_mushroom",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD), true, true));

    public static final RegistryObject<Item> MUSHROOM_CURIO = ITEMS.register("mushroom_curio",
            () -> new MushroomCurio(new Item.Properties().rarity(CustomStyle.MUSHROOM_ITALIC_RARITY)));

    public static final RegistryObject<Item> MUSHROOM_GEM_PIECE = ITEMS.register("mushroom_gem_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY)));
}
