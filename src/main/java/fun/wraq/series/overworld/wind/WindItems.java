package fun.wraq.series.overworld.wind;

import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WindItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> WIND_SOUL = ITEMS.register("kazesoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Wind)));
    public static final RegistryObject<Item> WIND_RUNE = ITEMS.register("kazerune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.KazeBold)));

    public static final RegistryObject<Item> WIND_CRYSTAL_0 = ITEMS.register("wind_crystal_0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WindBold)));
    public static final RegistryObject<Item> WIND_CRYSTAL_1 = ITEMS.register("wind_crystal_1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WindBold)));
    public static final RegistryObject<Item> WIND_CRYSTAL_2 = ITEMS.register("wind_crystal_2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WindBold)));

    public static final RegistryObject<Item> WIND_BOOTS_0 = ITEMS.register("wind_boots_0",
            () -> new WindBoots(ModArmorMaterials.ArmorKaze, ArmorItem.Type.BOOTS, 0));
    public static final RegistryObject<Item> WIND_BOOTS_1 = ITEMS.register("wind_boots_1",
            () -> new WindBoots(ModArmorMaterials.ArmorKaze, ArmorItem.Type.BOOTS, 1));
    public static final RegistryObject<Item> WIND_BOOTS_2 = ITEMS.register("wind_boots_2",
            () -> new WindBoots(ModArmorMaterials.ArmorKaze, ArmorItem.Type.BOOTS, 2));
    public static final RegistryObject<Item> WIND_BOOTS_3 = ITEMS.register("wind_boots_3",
            () -> new WindBoots(ModArmorMaterials.ArmorKaze, ArmorItem.Type.BOOTS, 3));

    public static final RegistryObject<Item> WIND_SWORD_0 = ITEMS.register("wind_sword_0",
            () -> new WindSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 0));
    public static final RegistryObject<Item> WIND_SWORD_1 = ITEMS.register("wind_sword_1",
            () -> new WindSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 1));
    public static final RegistryObject<Item> WIND_SWORD_2 = ITEMS.register("wind_sword_2",
            () -> new WindSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 2));
    public static final RegistryObject<Item> WIND_SWORD_3 = ITEMS.register("wind_sword_3",
            () -> new WindSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 3));
}
