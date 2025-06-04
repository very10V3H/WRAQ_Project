package fun.wraq.events.mob.loot;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class C3LootItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> WITHER_SKELETON_LOOT_SWORD = ITEMS.register("wither_skeleton_loot_sword", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.WitherBold), CustomStyle.styleOfWither,
                    ComponentUtils.getSuffixOfNether(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 130, 150),
                    new RandomAttributeValue(StringUtils.RandomAttribute.critDamage, 0.35, 0.75)), 80));

    public static final RegistryObject<Item> MAGMA_LOOT_SCEPTRE = ITEMS.register("magma_loot_sceptre", () ->
            new RandomSceptre(new Item.Properties().rarity(CustomStyle.MagmaBold), CustomStyle.styleOfPower,
                    ComponentUtils.getSuffixOfNether(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaDamage, 175, 185),
                    new RandomAttributeValue(StringUtils.RandomAttribute.critDamage, 0.4, 0.8)), 80));

    public static final RegistryObject<Item> NETHER_SKELETON_LOOT_BOW = ITEMS.register("nether_skeleton_loot_bow", () ->
            new RandomBow(new Item.Properties().rarity(CustomStyle.WitherBold), CustomStyle.styleOfWither,
                    ComponentUtils.getSuffixOfNether(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 110, 130),
                    new RandomAttributeValue(StringUtils.RandomAttribute.defence, 2, 4)), 80));

    public static final RegistryObject<Item> PIGLIN_SWORD = ITEMS.register("piglin_sword", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.PiglinBold), CustomStyle.styleOfGold,
                    ComponentUtils.getSuffixOfNether(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.defence, 2, 4),
                    new RandomAttributeValue(StringUtils.RandomAttribute.critRate, 0.2, 0.4)), 80));
}
