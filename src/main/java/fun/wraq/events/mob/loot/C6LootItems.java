package fun.wraq.events.mob.loot;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.loot.RandomAttributeValue;
import fun.wraq.events.mob.loot.RandomBow;
import fun.wraq.events.mob.loot.RandomSceptre;
import fun.wraq.events.mob.loot.RandomSword;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class C6LootItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> beaconBow = ITEMS.register("beacon_loot_bow", () ->
            new RandomBow(new Item.Properties().rarity(CustomStyle.MagmaBold), CustomStyle.styleOfPower,
                    ComponentUtils.getSuffixOfCastle(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 500, 600),
                    new RandomAttributeValue(StringUtils.RandomAttribute.critDamage, 0.8, 1),
                    new RandomAttributeValue(StringUtils.RandomAttribute.movementSpeed, 0.5, 0.6)), 180));

    public static final RegistryObject<Item> blazeSword = ITEMS.register("blaze_loot_sword", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.MagmaBold), CustomStyle.styleOfPower,
                    ComponentUtils.getSuffixOfCastle(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 500, 600),
                    new RandomAttributeValue(StringUtils.RandomAttribute.defence, 4, 5),
                    new RandomAttributeValue(StringUtils.RandomAttribute.maxHealth, 800, 1000)), 180));

    public static final RegistryObject<Item> treeSceptre = ITEMS.register("tree_loot_sceptre", () ->
            new RandomSceptre(new Item.Properties().rarity(CustomStyle.EvokerBold), CustomStyle.styleOfMana,
                    ComponentUtils.getSuffixOfCastle(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.manaDamage, 1000, 1200),
                    new RandomAttributeValue(StringUtils.RandomAttribute.maxMana, 75, 100),
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaPenetration0, 3, 4)), 180));
}