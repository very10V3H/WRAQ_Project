package com.very.wraq.series.moontain;

import com.very.wraq.common.registry.ItemMaterial;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.FoiledMaterialItem;
import com.very.wraq.series.moontain.equip.armor.MoontainArmor;
import com.very.wraq.series.moontain.equip.weapon.MoontainBow;
import com.very.wraq.series.moontain.equip.weapon.MoontainSceptre;
import com.very.wraq.series.moontain.equip.weapon.MoontainSword;
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
            () -> new FoiledMaterialItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD)));

    public static final RegistryObject<Item> LEATHER = ITEMS.register("moontain_leather",
            () -> new FoiledMaterialItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD)));

    public static final RegistryObject<Item> FALLING_SOUL = ITEMS.register("moontain_falling_soul",
            () -> new FoiledMaterialItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD)));

    public static final RegistryObject<Item> FRAGMENT = ITEMS.register("moontain_fragment",
            () -> new FoiledMaterialItem(new Item.Properties().rarity(CustomStyle.MOONTAIN)));

    public static final RegistryObject<Item> NUGGET = ITEMS.register("moontain_nugget",
            () -> new FoiledMaterialItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> RING = ITEMS.register("moontain_heart",
            () -> new FoiledMaterialItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> STONE_FRAGMENT = ITEMS.register("moontain_stone_fragment",
            () -> new FoiledMaterialItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> JADEITE = ITEMS.register("moontain_jadeite",
            () -> new FoiledMaterialItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> EMERALD = ITEMS.register("moontain_emerald",
            () -> new FoiledMaterialItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));
}
