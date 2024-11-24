package fun.wraq.series.moontain;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.moontain.equip.armor.MoontainArmor;
import fun.wraq.series.moontain.equip.curios.MoontainCurios;
import fun.wraq.series.moontain.equip.weapon.MoontainBow;
import fun.wraq.series.moontain.equip.weapon.MoontainSceptre;
import fun.wraq.series.moontain.equip.weapon.MoontainSword;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class MoontainItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    // 望山孤魂残躯
    public static final RegistryObject<Item> SOUL_FRAGMENT = ITEMS.register("moontain_soul_fragment",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN), true, true));
    // 望山魂革
    public static final RegistryObject<Item> LEATHER = ITEMS.register("moontain_leather",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN), true, true));
    // 望山阁灵落
    public static final RegistryObject<Item> FALLING_SOUL = ITEMS.register("moontain_falling_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN), true, true));
    // 望山屑 - 武器
    public static final RegistryObject<Item> FRAGMENT = ITEMS.register("moontain_fragment",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD), true, true));
    // 望山心屑 - 防具
    public static final RegistryObject<Item> NUGGET = ITEMS.register("moontain_nugget",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD), true, true));
    // 衡望山之心 - 召唤物
    public static final RegistryObject<Item> HEART = ITEMS.register("moontain_heart",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC), true, true));
    // 望山母岩碎块 - common drops
    public static final RegistryObject<Item> STONE_FRAGMENT = ITEMS.register("moontain_stone_fragment",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN), true, true));
    // 望山翡翠 - 望山武士掉落物
    public static final RegistryObject<Item> JADEITE = ITEMS.register("moontain_jadeite",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD), true, true));
    // 望山碧玉 - 望山骑士掉落物
    public static final RegistryObject<Item> EMERALD = ITEMS.register("moontain_emerald",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD), true, true));

    // 饰品 - 望山武魂掉落物
    public static final RegistryObject<Item> CHEST_CURIOS = ITEMS.register("moontain_chest_curios",
            () -> new MoontainCurios(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD)));

    public static final RegistryObject<Item> BRACELET = ITEMS.register("moontain_bracelet",
            () -> new MoontainCurios(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD)));

    public static final RegistryObject<Item> HAND = ITEMS.register("moontain_hand",
            () -> new MoontainCurios(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD)));

    public static final RegistryObject<Item> RING = ITEMS.register("moontain_ring",
            () -> new MoontainCurios(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD)));

    public static final RegistryObject<Item> FEATHER = ITEMS.register("moontain_feather",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN), true, true));

    public static final RegistryObject<Item> CURIOS_PIECE = ITEMS.register("moontain_curios_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN), true, true));

    public static final RegistryObject<Item> WEAPON_ENHANCER = ITEMS.register("moontain_weapon_enhancer",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC), true, true, List.of(
                    Te.s("用于", "提升", ChatFormatting.AQUA, "望山武器", CustomStyle.styleOfMoontain,
                            "阶数", ChatFormatting.GOLD)
            )));

    public static final RegistryObject<Item> ARMOR_ENHANCER = ITEMS.register("moontain_armor_enhancer",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC), true, true, List.of(
                    Te.s("用于", "提升", ChatFormatting.AQUA, "望山防具", CustomStyle.styleOfMoontain,
                            "阶数", ChatFormatting.GOLD)
            )));

    public static final RegistryObject<Item> CURIOS_RATE_ENHANCER = ITEMS.register("moontain_curios_rate_enhancer",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC), true, true, List.of(
                    Te.s("用于", "提升", ChatFormatting.AQUA, "望山饰品", CustomStyle.styleOfMoontain,
                            "属性数值", CustomStyle.styleOfWorld)

            )));

    public static final RegistryObject<Item> CURIOS_FULL_RATE_ENHANCER = ITEMS.register("moontain_curios_full_rate_enhancer",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC), true, true, List.of(
                    Te.s("用于", "提升", ChatFormatting.AQUA, "望山饰品", CustomStyle.styleOfMoontain,
                            "数值上限", ChatFormatting.RED)

            )));

    public static final RegistryObject<Item> SWORD = ITEMS.register("moontain_sword",
            () -> new MoontainSword(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> BOW = ITEMS.register("moontain_bow",
            () -> new MoontainBow(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> SCEPTRE = ITEMS.register("moontain_sceptre",
            () -> new MoontainSceptre(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> HELMET = ITEMS.register("moontain_helmet",
            () -> new MoontainArmor(ModArmorMaterials.MOONTAIN, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> CHEST = ITEMS.register("moontain_chest",
            () -> new MoontainArmor(ModArmorMaterials.MOONTAIN, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> LEGGINGS = ITEMS.register("moontain_leggings",
            () -> new MoontainArmor(ModArmorMaterials.MOONTAIN, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));

    public static final RegistryObject<Item> BOOTS = ITEMS.register("moontain_boots",
            () -> new MoontainArmor(ModArmorMaterials.MOONTAIN, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));
}
