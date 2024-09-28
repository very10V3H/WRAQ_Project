package fun.wraq.process.system.element;

import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SimpleFoiledItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElementItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> weakPiece0 = ITEMS.register("weak_piece_0",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Castle)));

    public static final RegistryObject<Item> weakPiece1 = ITEMS.register("weak_piece_1",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.CastleBold)));

    public static final RegistryObject<Item> weakPiece2 = ITEMS.register("weak_piece_2",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.CastleItalic)));

}
