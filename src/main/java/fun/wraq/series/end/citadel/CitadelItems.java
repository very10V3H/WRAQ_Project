package fun.wraq.series.end.citadel;

import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CitadelItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> CITADEL_CURIO_0 = ITEMS.register("citadel_curio_0",
            () -> new CitadelCurio(new Item.Properties().rarity(CustomStyle.EndBold), 0));
    public static final RegistryObject<Item> CITADEL_CURIO_1 = ITEMS.register("citadel_curio_1",
            () -> new CitadelCurio(new Item.Properties().rarity(CustomStyle.EndBold), 1));
    public static final RegistryObject<Item> CITADEL_CURIO_2 = ITEMS.register("citadel_curio_2",
            () -> new CitadelCurio(new Item.Properties().rarity(CustomStyle.EndBold), 2));
    public static final RegistryObject<Item> CITADEL_CURIO_3 = ITEMS.register("citadel_curio_3",
            () -> new CitadelCurio(new Item.Properties().rarity(CustomStyle.EndBold), 3));

    public static final RegistryObject<Item> CITADEL_EQUIP_ENHANCER = ITEMS.register("citadel_armor_enhancer",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.EndBold), true, true));

    public static final RegistryObject<Item> CITADEL_HELMET = ITEMS.register("citadel_helmet",
            () -> new CitadelArmor(ModArmorMaterials.PurpleIron, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.EndItalic)));

    public static final RegistryObject<Item> CITADEL_CHEST = ITEMS.register("citadel_chest",
            () -> new CitadelArmor(ModArmorMaterials.PurpleIron, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.EndItalic)));

    public static final RegistryObject<Item> CITADEL_LEGGINGS = ITEMS.register("citadel_leggings",
            () -> new CitadelArmor(ModArmorMaterials.PurpleIron, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.EndItalic)));

    public static final RegistryObject<Item> CITADEL_BOOTS = ITEMS.register("citadel_boots",
            () -> new CitadelArmor(ModArmorMaterials.PurpleIron, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.EndItalic)));

    public static final RegistryObject<Item> CITADEL_PIECE = ITEMS.register("citadel_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.End), true, true));
}
