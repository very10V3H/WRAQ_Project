package com.very.wraq.events.mob.loot;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
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
                    new RandomAttributeValue(StringUtils.RandomAttribute.defence, 400, 500),
                    new RandomAttributeValue(StringUtils.RandomAttribute.maxHealth, 800, 1000)), 180));

    public static final RegistryObject<Item> treeSceptre = ITEMS.register("tree_loot_sceptre", () ->
            new RandomSceptre(new Item.Properties().rarity(CustomStyle.EvokerBold), CustomStyle.styleOfMana,
                    ComponentUtils.getSuffixOfCastle(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.manaDamage, 1000, 1200),
                    new RandomAttributeValue(StringUtils.RandomAttribute.maxMana, 75, 100),
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaPenetration0, 300, 400)), 180));
}
