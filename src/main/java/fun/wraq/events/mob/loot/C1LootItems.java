package fun.wraq.events.mob.loot;

import fun.wraq.common.registry.ItemMaterial;
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

public class C1LootItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> plainZombieHoe = ITEMS.register("plain_zombie_hoe", () ->
            new RandomSceptre(new Item.Properties().rarity(CustomStyle.PlainBold), CustomStyle.styleOfPlain,
                    ComponentUtils.getSuffixOfChapterI(), List.of(new RandomAttributeValue(
                    StringUtils.RandomAttribute.manaDamage, 20, 60)), 4));

    public static final RegistryObject<Item> forestZombieAxe = ITEMS.register("forest_zombie_axe", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.ForestBold), CustomStyle.styleOfForest,
                    ComponentUtils.getSuffixOfChapterI(), List.of(new RandomAttributeValue(
                    StringUtils.RandomAttribute.attackDamage, 20, 60)), 12));

    public static final RegistryObject<Item> lakeDrownHelmet = ITEMS.register("lake_drown_helmet", () ->
            new RandomArmor(ItemMaterial.Lake, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.WaterBold), CustomStyle.styleOfWater,
                    ComponentUtils.getSuffixOfChapterI(), List.of(new RandomAttributeValue(
                    StringUtils.RandomAttribute.coolDown, 0.2, 0.6)), 18));

    public static final RegistryObject<Item> mineSkeletonPickaxe = ITEMS.register("mine_skeleton_pickaxe", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.MineBold), CustomStyle.styleOfMine,
                    ComponentUtils.getSuffixOfChapterI(), List.of(new RandomAttributeValue(
                    StringUtils.RandomAttribute.attackDamage, 40, 100)), 24));

    public static final RegistryObject<Item> spiderBoots = ITEMS.register("spider_boots", () ->
            new RandomArmor(ItemMaterial.ArmorS, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.SpiderBold), CustomStyle.styleOfSpider,
                    ComponentUtils.getSuffixOfChapterI(), List.of(new RandomAttributeValue(
                    StringUtils.RandomAttribute.movementSpeedWithoutBattle, 0.2, 0.6)), 32));
}
