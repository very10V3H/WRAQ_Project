package fun.wraq.events.mob.loot;

import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.loot.RandomArmor;
import fun.wraq.events.mob.loot.RandomAttributeValue;
import fun.wraq.events.mob.loot.RandomSceptre;
import fun.wraq.render.toolTip.CustomStyle;
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
                    ComponentUtils.getSuffixChapterIV(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.defencePenetration0, 2, 3),
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaPenetration0, 2, 3)), 80));

    public static final RegistryObject<Item> endermiteSceptre = ITEMS.register("endermite_sceptre", () ->
            new RandomSceptre(new Item.Properties().rarity(CustomStyle.EndBold), CustomStyle.styleOfEnd,
                    ComponentUtils.getSuffixChapterIV(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.defence, 300, 350),
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaPenetration0, 3, 4),
                    new RandomAttributeValue(StringUtils.RandomAttribute.defencePenetration0, 3, 4)), 140));

    public static final RegistryObject<Item> shulkerChest = ITEMS.register("shulker_chest", () ->
            new RandomArmor(ItemMaterial.PurpleIron, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.EndBold), CustomStyle.styleOfEnd,
                    ComponentUtils.getSuffixChapterIV(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.defence, 3, 4),
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 300, 350),
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaDamage, 300, 350)), 140));
}