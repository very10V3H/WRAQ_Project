package fun.wraq.series.overworld.extraordinary;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.nether.equip.mana.NetherSceptre;
import fun.wraq.series.overworld.extraordinary.curio.NanHai;
import fun.wraq.series.overworld.extraordinary.curio.TianShou;
import fun.wraq.series.overworld.extraordinary.curio.TongTian;
import fun.wraq.series.overworld.extraordinary.equip.KanupusArmor;
import fun.wraq.series.overworld.extraordinary.equip.KanupusSword;
import fun.wraq.series.overworld.extraordinary.equip.ShiroBow;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ExtraordinaryItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> PIECE = ITEMS.register("mana_tower_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), true, true));
    public static final RegistryObject<Item> RUNE = ITEMS.register("mana_tower_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), true, true));

    public static final RegistryObject<Item> NAN_HAI_A = ITEMS.register("nan_hai_attack",
            () -> new NanHai(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), true));
    public static final RegistryObject<Item> NAN_HAI_M = ITEMS.register("nan_hai_mana",
            () -> new NanHai(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), false));

    public static final RegistryObject<Item> TIAN_SHOU = ITEMS.register("tian_shou",
            () -> new TianShou(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY)));

    public static final RegistryObject<Item> TONG_TIAN = ITEMS.register("tong_tian",
            () -> new TongTian(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY)));

    public static final RegistryObject<Item> KANUPUS_WING_F = ITEMS.register("kanupus_wing_f",
            () -> new KanupusArmor(ModArmorMaterials.FANVER_DIAMOND, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), 0));
    public static final RegistryObject<Item> KANUPUS_WING_E = ITEMS.register("kanupus_wing_e",
            () -> new KanupusArmor(ModArmorMaterials.FANVER_DIAMOND, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), 1));
    public static final RegistryObject<Item> KANUPUS_WING_D = ITEMS.register("kanupus_wing_d",
            () -> new KanupusArmor(ModArmorMaterials.FANVER_DIAMOND, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), 2));
    public static final RegistryObject<Item> KANUPUS_WING_C = ITEMS.register("kanupus_wing_c",
            () -> new KanupusArmor(ModArmorMaterials.FANVER_DIAMOND, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), 3));
    public static final RegistryObject<Item> KANUPUS_WING_B = ITEMS.register("kanupus_wing_b",
            () -> new KanupusArmor(ModArmorMaterials.FANVER_DIAMOND, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), 4));
    public static final RegistryObject<Item> KANUPUS_WING_A = ITEMS.register("kanupus_wing_a",
            () -> new KanupusArmor(ModArmorMaterials.FANVER_DIAMOND, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), 5));
    public static final RegistryObject<Item> KANUPUS_WING_Z = ITEMS.register("kanupus_wing_z",
            () -> new KanupusArmor(ModArmorMaterials.FANVER_DIAMOND, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), 6));

    public static final RegistryObject<Item> KANUPUS_CORE_E = ITEMS.register("kanupus_core_e",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), true, true, List.of(
                    Te.s("我绝不是最后一个..", CustomStyle.MANA_TOWER_STYLE, ChatFormatting.ITALIC)
            )));
    public static final RegistryObject<Item> KANUPUS_CORE_D = ITEMS.register("kanupus_core_d",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), true, true, List.of(
                    Te.s("直面天神..", CustomStyle.MANA_TOWER_STYLE, ChatFormatting.ITALIC)
            )));
    public static final RegistryObject<Item> KANUPUS_CORE_C = ITEMS.register("kanupus_core_c",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), true, true, List.of(
                    Te.s("接近天神..", CustomStyle.MANA_TOWER_STYLE, ChatFormatting.ITALIC)
            )));
    public static final RegistryObject<Item> KANUPUS_CORE_B = ITEMS.register("kanupus_core_b",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), true, true, List.of(
                    Te.s("蔑视天神..", CustomStyle.MANA_TOWER_STYLE, ChatFormatting.ITALIC)
            )));
    public static final RegistryObject<Item> KANUPUS_CORE_A = ITEMS.register("kanupus_core_a",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), true, true, List.of(
                    Te.s("成为天神..", CustomStyle.MANA_TOWER_STYLE, ChatFormatting.ITALIC)
            )));
    public static final RegistryObject<Item> KANUPUS_CORE_Z = ITEMS.register("kanupus_core_z",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), true, true, List.of(
                    Te.s("创造天神..", CustomStyle.MANA_TOWER_STYLE, ChatFormatting.ITALIC)
            )));

    public static final RegistryObject<Item> KANUPUS_SWORD = ITEMS.register("kanupus_sword",
            () -> new KanupusSword(new Item.Properties().rarity(CustomStyle.MANA_TOWER_ITALIC_RARITY)));
    public static final RegistryObject<Item> KANUPUS_SWORD_SOUL = ITEMS.register("kanupus_sword_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY)));

    public static final RegistryObject<Item> SHIRO_BOW = ITEMS.register("shiro_bow",
            () -> new ShiroBow(new Item.Properties().rarity(CustomStyle.SHIRO_ITALIC_RARITY)));
    public static final RegistryObject<Item> SHIRO_BOW_SOUL = ITEMS.register("shiro_bow_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SHIRO_BOLD_RARITY)));

    public static final RegistryObject<Item> NETHER_SCEPTRE_EX = ITEMS.register("nether_sceptre_ex",
            () -> new NetherSceptre(new Item.Properties().rarity(CustomStyle.NetherItalic), 2));
    public static final RegistryObject<Item> NETHER_SCEPTRE_EX_SOUL = ITEMS.register("nether_sceptre_ex_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold)));

    public static final RegistryObject<Item> DAZZLING_DIAMOND = ITEMS.register("dazzling_diamond",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WorldBold)));
}
