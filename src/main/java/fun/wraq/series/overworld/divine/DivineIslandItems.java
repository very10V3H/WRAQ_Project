package fun.wraq.series.overworld.divine;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.gems.WraqGem;
import fun.wraq.series.overworld.divine.equip.armor.DivineArmor;
import fun.wraq.series.overworld.divine.equip.boss.DivineKnife;
import fun.wraq.series.overworld.divine.equip.passive.GhastlyBow;
import fun.wraq.series.overworld.divine.equip.passive.GhastlySceptre;
import fun.wraq.series.overworld.divine.equip.passive.GhastlySword;
import fun.wraq.series.overworld.divine.equip.weapon.DivineBow;
import fun.wraq.series.overworld.divine.equip.weapon.DivineSceptre;
import fun.wraq.series.overworld.divine.equip.weapon.DivineSword;
import fun.wraq.series.overworld.divine.gem.DivineGem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class DivineIslandItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> DIVINE_SOUL = ITEMS.register("divine_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_RARITY)));
    public static final RegistryObject<Item> DIVINE_ARROW = ITEMS.register("divine_arrow",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_RARITY)));
    public static final RegistryObject<Item> DIVINE_GOLEM_SOUL = ITEMS.register("divine_golem_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_RARITY)));
    public static final RegistryObject<Item> DIVINE_RUNE_ARMOR = ITEMS.register("divine_rune_armor",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY)));
    public static final RegistryObject<Item> DIVINE_RUNE_WEAPON = ITEMS.register("divine_rune_weapon",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY)));
    public static final RegistryObject<Item> DIVINE_CRYSTAL = ITEMS.register("divine_crystal",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY)));

    public static final RegistryObject<Item> GHASTLY_NUGGET = ITEMS.register("ghastly_nugget",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GHASTLY_RARITY)));
    public static final RegistryObject<Item> GHASTLY_GUN_POWDER = ITEMS.register("ghastly_gun_powder",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GHASTLY_RARITY)));
    public static final RegistryObject<Item> GHASTLY_SOUL = ITEMS.register("ghastly_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GHASTLY_RARITY)));
    public static final RegistryObject<Item> GHASTLY_INGOT = ITEMS.register("ghastly_ingot",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GHASTLY_BOLD_RARITY)));

    public static final RegistryObject<Item> DIVINE_SWORD_0 = ITEMS.register("divine_sword_0",
            () -> new DivineSword(
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0));
    public static final RegistryObject<Item> DIVINE_BOW_0 = ITEMS.register("divine_bow_0",
            () -> new DivineBow(
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0));
    public static final RegistryObject<Item> DIVINE_SCEPTRE_0 = ITEMS.register("divine_sceptre_0",
            () -> new DivineSceptre(
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0));

    public static final RegistryObject<Item> GHASTLY_SWORD = ITEMS.register("ghastly_sword",
            () -> new GhastlySword(new Item.Properties().rarity(CustomStyle.GHASTLY_ITALIC_RARITY)));
    public static final RegistryObject<Item> GHASTLY_BOW = ITEMS.register("ghastly_bow",
            () -> new GhastlyBow(new Item.Properties().rarity(CustomStyle.GHASTLY_ITALIC_RARITY)));
    public static final RegistryObject<Item> GHASTLY_SCEPTRE = ITEMS.register("ghastly_sceptre",
            () -> new GhastlySceptre(new Item.Properties().rarity(CustomStyle.GHASTLY_ITALIC_RARITY)));

    public static final RegistryObject<Item> DIVINE_HELMET_0 = ITEMS.register("divine_helmet_0",
            () -> new DivineArmor(ModArmorMaterials.DIVINE, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0));
    public static final RegistryObject<Item> DIVINE_CHEST_0 = ITEMS.register("divine_chest_0",
            () -> new DivineArmor(ModArmorMaterials.DIVINE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0));
    public static final RegistryObject<Item> DIVINE_LEGGINGS_0 = ITEMS.register("divine_leggings_0",
            () -> new DivineArmor(ModArmorMaterials.DIVINE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0));
    public static final RegistryObject<Item> DIVINE_BOOTS_0 = ITEMS.register("divine_boots_0",
            () -> new DivineArmor(ModArmorMaterials.DIVINE, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0));

    public static final RegistryObject<Item> MOB_FANVER_DIAMOND_HELMET = ITEMS.register("fanver_diamond_helmet",
            () -> new ArmorItem(ModArmorMaterials.FANVER_DIAMOND, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MOB_FANVER_DIAMOND_CHEST = ITEMS.register("fanver_diamond_chest",
            () -> new ArmorItem(ModArmorMaterials.FANVER_DIAMOND, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MOB_FANVER_DIAMOND_LEGGINGS = ITEMS.register("fanver_diamond_leggings",
            () -> new ArmorItem(ModArmorMaterials.FANVER_DIAMOND, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MOB_FANVER_DIAMOND_BOOTS = ITEMS.register("fanver_diamond_boots",
            () -> new ArmorItem(ModArmorMaterials.FANVER_DIAMOND, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MOB_FANVER_IRON_HELMET = ITEMS.register("fanver_iron_helmet",
            () -> new ArmorItem(ModArmorMaterials.FANVER_IRON, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MOB_FANVER_IRON_CHEST = ITEMS.register("fanver_iron_chest",
            () -> new ArmorItem(ModArmorMaterials.FANVER_IRON, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MOB_FANVER_IRON_LEGGINGS = ITEMS.register("fanver_iron_leggings",
            () -> new ArmorItem(ModArmorMaterials.FANVER_IRON, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MOB_FANVER_IRON_BOOTS = ITEMS.register("fanver_iron_boots",
            () -> new ArmorItem(ModArmorMaterials.FANVER_IRON, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MOB_FANVER_GOLDEN_HELMET = ITEMS.register("fanver_golden_helmet",
            () -> new ArmorItem(ModArmorMaterials.FANVER_GOLDEN, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MOB_FANVER_GOLDEN_CHEST = ITEMS.register("fanver_golden_chest",
            () -> new ArmorItem(ModArmorMaterials.FANVER_GOLDEN, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MOB_FANVER_GOLDEN_LEGGINGS = ITEMS.register("fanver_golden_leggings",
            () -> new ArmorItem(ModArmorMaterials.FANVER_GOLDEN, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MOB_FANVER_GOLDEN_BOOTS = ITEMS.register("fanver_golden_boots",
            () -> new ArmorItem(ModArmorMaterials.FANVER_GOLDEN, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MOB_DIVINE_HELMET = ITEMS.register("mob_divine_helmet",
            () -> new ArmorItem(ModArmorMaterials.DIVINE, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MOB_DIVINE_CHEST = ITEMS.register("mob_divine_chest",
            () -> new ArmorItem(ModArmorMaterials.DIVINE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MOB_DIVINE_LEGGINGS = ITEMS.register("mob_divine_leggings",
            () -> new ArmorItem(ModArmorMaterials.DIVINE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MOB_DIVINE_BOOTS = ITEMS.register("mob_divine_boots",
            () -> new ArmorItem(ModArmorMaterials.DIVINE, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> DIVINE_BOSS_SOUL = ITEMS.register("divine_boss_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY)));

    public static final RegistryObject<Item> DIVINE_KNIFE = ITEMS.register("divine_knife",
            () -> new DivineKnife(new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY)));

    public static final RegistryObject<Item> DIVINE_GEM_PIECE_0 = ITEMS.register("divine_gem_piece_0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY)));
    public static final RegistryObject<Item> DIVINE_GEM_PIECE_1 = ITEMS.register("divine_gem_piece_1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY)));
    public static final RegistryObject<Item> DIVINE_GEM_PIECE_2 = ITEMS.register("divine_gem_piece_2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY)));
    public static final RegistryObject<Item> DIVINE_GEM_PIECE_3 = ITEMS.register("divine_gem_piece_3",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY)));

    public static final RegistryObject<Item> DIVINE_GEM_PASSIVE_TIER_0 =
            ITEMS.register("divine_gem_passive_tier_0",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.05)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 0, 0.09));
    public static final RegistryObject<Item> DIVINE_GEM_PASSIVE_TIER_1 =
            ITEMS.register("divine_gem_passive_tier_1",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.1)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 0, 0.18));
    public static final RegistryObject<Item> DIVINE_GEM_PASSIVE_TIER_2 =
            ITEMS.register("divine_gem_passive_tier_2",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.15)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 0, 0.27));
    public static final RegistryObject<Item> DIVINE_GEM_PASSIVE_TIER_3 =
            ITEMS.register("divine_gem_passive_tier_3",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.2)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 0, 0.36));

    public static final RegistryObject<Item> DIVINE_GEM_BASE_1_TIER_0 =
            ITEMS.register("divine_gem_base_1_tier_0",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.05)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 1, 0.09));
    public static final RegistryObject<Item> DIVINE_GEM_BASE_1_TIER_1 =
            ITEMS.register("divine_gem_base_1_tier_1",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.1)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 1, 0.18));
    public static final RegistryObject<Item> DIVINE_GEM_BASE_1_TIER_2 =
            ITEMS.register("divine_gem_base_1_tier_2",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.15)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 1, 0.27));
    public static final RegistryObject<Item> DIVINE_GEM_BASE_1_TIER_3 =
            ITEMS.register("divine_gem_base_1_tier_3",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.2)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 1, 0.36));

    public static final RegistryObject<Item> DIVINE_GEM_BASE_2_TIER_0 =
            ITEMS.register("divine_gem_base_2_tier_0",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.05)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 2, 0.09));
    public static final RegistryObject<Item> DIVINE_GEM_BASE_2_TIER_1 =
            ITEMS.register("divine_gem_base_2_tier_1",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.1)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 2, 0.18));
    public static final RegistryObject<Item> DIVINE_GEM_BASE_2_TIER_2 =
            ITEMS.register("divine_gem_base_2_tier_2",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.15)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 2, 0.27));
    public static final RegistryObject<Item> DIVINE_GEM_BASE_2_TIER_3 =
            ITEMS.register("divine_gem_base_2_tier_3",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.2)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 2, 0.36));

    public static final RegistryObject<Item> DIVINE_GEM_BASE_3_TIER_0 =
            ITEMS.register("divine_gem_base_3_tier_0",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.05)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 3, 0.09));
    public static final RegistryObject<Item> DIVINE_GEM_BASE_3_TIER_1 =
            ITEMS.register("divine_gem_base_3_tier_1",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.1)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 3, 0.18));
    public static final RegistryObject<Item> DIVINE_GEM_BASE_3_TIER_2 =
            ITEMS.register("divine_gem_base_3_tier_2",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.15)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 3, 0.27));
    public static final RegistryObject<Item> DIVINE_GEM_BASE_3_TIER_3 =
            ITEMS.register("divine_gem_base_3_tier_3",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.2)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 3, 0.36));

    public static final RegistryObject<Item> DIVINE_GEM_FINAL_TIER_0 =
            ITEMS.register("divine_gem_final_tier_0",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.05)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 4, 0.09));
    public static final RegistryObject<Item> DIVINE_GEM_FINAL_TIER_1 =
            ITEMS.register("divine_gem_final_tier_1",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.1)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 4, 0.18));
    public static final RegistryObject<Item> DIVINE_GEM_FINAL_TIER_2 =
            ITEMS.register("divine_gem_final_tier_2",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.15)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 4, 0.27));
    public static final RegistryObject<Item> DIVINE_GEM_FINAL_TIER_3 =
            ITEMS.register("divine_gem_final_tier_3",
                    () -> new DivineGem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY),
                            List.of(new WraqGem.AttributeMapValue(Utils.elementStrength, 0.2)),
                            CustomStyle.DIVINE_STYLE, Te.s("圣光普照", CustomStyle.DIVINE_STYLE),
                            ComponentUtils.getSuffixOfDivine(), 4, 0.36));

    public static final RegistryObject<Item> DIVINE_BALANCE_STAR = ITEMS.register("divine_balance_star",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), true, true));

    public static final RegistryObject<Item> DIVINE_SWORD_1 = ITEMS.register("divine_sword_1",
            () -> new DivineSword(
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 1));
    public static final RegistryObject<Item> DIVINE_BOW_1 = ITEMS.register("divine_bow_1",
            () -> new DivineBow(
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 1));
    public static final RegistryObject<Item> DIVINE_SCEPTRE_1 = ITEMS.register("divine_sceptre_1",
            () -> new DivineSceptre(
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 1));

    public static final RegistryObject<Item> DIVINE_HELMET_1 = ITEMS.register("divine_helmet_1",
            () -> new DivineArmor(ModArmorMaterials.DIVINE, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 1));
    public static final RegistryObject<Item> DIVINE_CHEST_1 = ITEMS.register("divine_chest_1",
            () -> new DivineArmor(ModArmorMaterials.DIVINE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 1));
    public static final RegistryObject<Item> DIVINE_LEGGINGS_1 = ITEMS.register("divine_leggings_1",
            () -> new DivineArmor(ModArmorMaterials.DIVINE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 1));
    public static final RegistryObject<Item> DIVINE_BOOTS_1 = ITEMS.register("divine_boots_1",
            () -> new DivineArmor(ModArmorMaterials.DIVINE, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 1));

    public static final RegistryObject<Item> REFINED_DIVINE_PIECE = ITEMS.register("refined_divine_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY)));
}
