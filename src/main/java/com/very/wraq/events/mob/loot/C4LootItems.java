package com.very.wraq.events.mob.loot;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.registry.ItemMaterial;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class C4LootItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> enderManSword = ITEMS.register("enderman_sword", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.EndBold), CustomStyle.styleOfEnd,
                    ComponentUtils.getSuffixChapterIV(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.defencePenetration0, 200, 300),
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaPenetration0, 200, 300)), 80));

    public static final RegistryObject<Item> endermiteSceptre = ITEMS.register("endermite_sceptre", () ->
            new RandomSceptre(new Item.Properties().rarity(CustomStyle.EndBold), CustomStyle.styleOfEnd,
                    ComponentUtils.getSuffixChapterIV(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.defence, 300, 350),
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaPenetration0, 300, 350),
                    new RandomAttributeValue(StringUtils.RandomAttribute.defencePenetration0, 300, 350)), 140));

    public static final RegistryObject<Item> shulkerChest = ITEMS.register("shulker_chest", () ->
            new RandomArmor(ItemMaterial.PurpleIron, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.EndBold), CustomStyle.styleOfEnd,
                    ComponentUtils.getSuffixChapterIV(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.defence, 300, 350),
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 300, 350),
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaDamage, 300, 350)), 140));
}
