package fun.wraq.series.moontain;

import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.moontain.equip.armor.MoontainArmor;
import fun.wraq.series.moontain.equip.weapon.MoontainBow;
import fun.wraq.series.moontain.equip.weapon.MoontainSceptre;
import fun.wraq.series.moontain.equip.weapon.MoontainSword;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MoontainItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> SWORD = ITEMS.register("moontain_sword",
            () -> new MoontainSword(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> BOW = ITEMS.register("moontain_bow",
            () -> new MoontainBow(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> SCEPTRE = ITEMS.register("moontain_sceptre",
            () -> new MoontainSceptre(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> HELMET = ITEMS.register("moontain_helmet",
            () -> new MoontainArmor(ItemMaterial.MOONTAIN, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> CHEST = ITEMS.register("moontain_chest",
            () -> new MoontainArmor(ItemMaterial.MOONTAIN, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> LEGGINGS = ITEMS.register("moontain_leggings",
            () -> new MoontainArmor(ItemMaterial.MOONTAIN, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> BOOTS = ITEMS.register("moontain_boots",
            () -> new MoontainArmor(ItemMaterial.MOONTAIN, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> SOUL_FRAGMENT = ITEMS.register("moontain_soul_fragment",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD), false, true));

    public static final RegistryObject<Item> LEATHER = ITEMS.register("moontain_leather",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD), false, true));

    public static final RegistryObject<Item> FALLING_SOUL = ITEMS.register("moontain_falling_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD), false, true));

    public static final RegistryObject<Item> FRAGMENT = ITEMS.register("moontain_fragment",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN), false, true));

    public static final RegistryObject<Item> NUGGET = ITEMS.register("moontain_nugget",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC), false, true));

    public static final RegistryObject<Item> RING = ITEMS.register("moontain_heart",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC), false, true));

    public static final RegistryObject<Item> STONE_FRAGMENT = ITEMS.register("moontain_stone_fragment",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC), false, true));

    public static final RegistryObject<Item> JADEITE = ITEMS.register("moontain_jadeite",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC), false, true));

    public static final RegistryObject<Item> EMERALD = ITEMS.register("moontain_emerald",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC), false, true));
}
