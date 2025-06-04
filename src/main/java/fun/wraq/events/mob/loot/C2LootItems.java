package fun.wraq.events.mob.loot;

import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class C2LootItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> FIRE_LIGHT_KNIFE = ITEMS.register("fire_light_knife", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.VolcanoBold), CustomStyle.styleOfVolcano,
                    ComponentUtils.getSuffixOfChapterI(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.critDamage, 0.9, 1.1),
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 90, 110)), 40));

    public static final RegistryObject<Item> SEARED_SPIRIT_STICK = ITEMS.register("seared_spirit_stick", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.VolcanoBold), CustomStyle.styleOfVolcano,
                    ComponentUtils.getSuffixOfChapterII(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.critRate, 0.15, 0.35),
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 95, 115)), 48));

    public static final RegistryObject<Item> EVOKER_SCEPTRE = ITEMS.register("evoker_sceptre", () ->
            new RandomSceptre(new Item.Properties().rarity(CustomStyle.EvokerBold), CustomStyle.styleOfMana,
                    ComponentUtils.getSuffixOfChapterII(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaRecover, 15, 25),
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaDamage, 180, 220)), 56));

    public static final RegistryObject<Item> LUMBER_JACK_AXE = ITEMS.register("lumber_jack_axe", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.ForestBold), CustomStyle.styleOfForest,
                    ComponentUtils.getSuffixOfChapterII(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.defencePenetration0, 1, 2),
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 80, 100)), 60));

    public static final RegistryObject<Item> FOREST_WOLF_CHEST = ITEMS.register("forest_wolf_chest", () ->
            new RandomArmor(ModArmorMaterials.Forest, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.ForestBold), CustomStyle.styleOfForest,
                    ComponentUtils.getSuffixOfChapterII(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.maxHealth, 180, 220),
                    new RandomAttributeValue(StringUtils.RandomAttribute.defence, 9, 11)), 66));

    public static final RegistryObject<Item> SKY_VEX_BOW = ITEMS.register("sky_vex_bow", () ->
            new RandomBow(new Item.Properties().rarity(CustomStyle.SkyBold), CustomStyle.styleOfSky,
                    ComponentUtils.getSuffixOfChapterII(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.critRate, 0.2, 0.3),
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 90, 110),
                    new RandomAttributeValue(StringUtils.RandomAttribute.movementSpeedWithoutBattle, 0.5, 0.7)), 72));

    public static final RegistryObject<Item> SLIME_CHEST = ITEMS.register("slime_chest", () ->
            new RandomArmor(ModArmorMaterials.LifeElement, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.LifeBold), CustomStyle.styleOfLife,
                    ComponentUtils.getSuffixOfChapterII(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.maxHealth, 300, 400),
                    new RandomAttributeValue(StringUtils.RandomAttribute.movementSpeedWithoutBattle, 0.4, 0.6)), 76));

    public static final RegistryObject<Item> JOROGUMO_LEGGINGS = ITEMS.register("jorogumo_leggings", () ->
            new RandomArmor(ModArmorMaterials.PurpleIron, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.PurpleIronBold), CustomStyle.styleOfPurpleIron,
                    ComponentUtils.getSuffixOfChapterII(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.maxHealth, 600, 800),
                    new RandomAttributeValue(StringUtils.RandomAttribute.defence, 18, 22)), 92));

    public static final RegistryObject<Item> WIND_SKELETON_SWORD = ITEMS.register("wind_skeleton_sword", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.WindBold), CustomStyle.styleOfWind,
                    ComponentUtils.getSuffixOfChapterII(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 110, 130),
                    new RandomAttributeValue(StringUtils.RandomAttribute.movementSpeedWithoutBattle, 0.7, 0.9)), 80));

    public static final RegistryObject<Item> HUSK_SWORD = ITEMS.register("husk_sword", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.HuskBold), CustomStyle.styleOfHusk,
                    ComponentUtils.getSuffixOfChapterII(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.healthSteal, 0.05, 0.1),
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 90, 110)), 84));

    public static final RegistryObject<Item> LIGHTNING_ZOMBIE_HELMET = ITEMS.register("lightning_zombie_helmet", () ->
            new RandomArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.LightningBold), CustomStyle.styleOfLightning,
                    ComponentUtils.getSuffixOfChapterII(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaPenetration0, 2.5, 5.5),
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 75, 95)), 92));

    public static final RegistryObject<Item> GUARDIAN_SCEPTRE = ITEMS.register("guardian_sceptre", () ->
            new RandomSceptre(new Item.Properties().rarity(CustomStyle.SeaBold), CustomStyle.styleOfSea,
                    ComponentUtils.getSuffixOfChapterII(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaDamage, 200, 250),
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaRecover, 15, 25)), 100));

    public static final RegistryObject<Item> SNOW_STRAY_PICKAXE = ITEMS.register("snow_stray_pickaxe", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.SnowBold), CustomStyle.styleOfSnow,
                    ComponentUtils.getSuffixOfChapterII(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 110, 130),
                    new RandomAttributeValue(StringUtils.RandomAttribute.critDamage, 0.5, 0.7),
                    new RandomAttributeValue(StringUtils.RandomAttribute.critRate, 0.35, 0.45)), 100));
}
