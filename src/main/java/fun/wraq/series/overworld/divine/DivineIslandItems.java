package fun.wraq.series.overworld.divine;

import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.overworld.divine.equip.armor.DivineArmor;
import fun.wraq.series.overworld.divine.equip.boss.DivineKnife;
import fun.wraq.series.overworld.divine.equip.weapon.DivineBow;
import fun.wraq.series.overworld.divine.equip.weapon.DivineSceptre;
import fun.wraq.series.overworld.divine.equip.weapon.DivineSword;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DivineIslandItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> DIVINE_SOUL = ITEMS.register("divine_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_RARITY)));
    public static final RegistryObject<Item> DIVINE_RUNE = ITEMS.register("divine_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY)));

    public static final RegistryObject<Item> DIVINE_SWORD_0 = ITEMS.register("divine_sword_0",
            () -> new DivineSword(
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0.2, 0.2, 3000, 20));
    public static final RegistryObject<Item> DIVINE_BOW_0 = ITEMS.register("divine_bow_0",
            () -> new DivineBow(
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0.2, 0.2, 3000, 20));
    public static final RegistryObject<Item> DIVINE_SCEPTRE_0 = ITEMS.register("divine_sceptre_0",
            () -> new DivineSceptre(
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0.2, 0.2, 3000, 20));

    public static final RegistryObject<Item> DIVINE_HELMET_0 = ITEMS.register("divine_helmet_0",
            () -> new DivineArmor(ModArmorMaterials.DIVINE, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0.15));
    public static final RegistryObject<Item> DIVINE_CHEST_0 = ITEMS.register("divine_chest_0",
            () -> new DivineArmor(ModArmorMaterials.DIVINE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0.15));
    public static final RegistryObject<Item> DIVINE_LEGGINGS_0 = ITEMS.register("divine_leggings_0",
            () -> new DivineArmor(ModArmorMaterials.DIVINE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0.15));
    public static final RegistryObject<Item> DIVINE_BOOTS_0 = ITEMS.register("divine_boots_0",
            () -> new DivineArmor(ModArmorMaterials.DIVINE, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.DIVINE_ITALIC_RARITY), 0.15));

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

    public static final RegistryObject<Item> GHASTLY_SOUL = ITEMS.register("ghastly_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GHASTLY_RARITY)));
    public static final RegistryObject<Item> GHASTLY_RUNE = ITEMS.register("ghastly_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GHASTLY_BOLD_RARITY)));
}
