package fun.wraq.events.mob.loot;

import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.loot.RandomArmor;
import fun.wraq.events.mob.loot.RandomAttributeValue;
import fun.wraq.events.mob.loot.RandomBow;
import fun.wraq.events.mob.loot.RandomSceptre;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class C5LootItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> pillagerBow = ITEMS.register("pillager_bow", () ->
            new RandomBow(new Item.Properties().rarity(CustomStyle.ShipBold), CustomStyle.styleOfShip,
                    ComponentUtils.getSuffixOfChapterV(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 200, 300),
                    new RandomAttributeValue(StringUtils.RandomAttribute.critRate, 0.3, 0.4),
                    new RandomAttributeValue(StringUtils.RandomAttribute.defencePenetration0, 2, 3)), 108));

    public static final RegistryObject<Item> bloodManaBoots = ITEMS.register("blood_mana_loot_boots", () ->
            new RandomArmor(ItemMaterial.BloodMana, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.BloodManaBold), CustomStyle.styleOfBloodMana,
                    ComponentUtils.getSuffixOfChapterV(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.manaDamage, 300, 400),
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 200, 300),
                    new RandomAttributeValue(StringUtils.RandomAttribute.movementSpeed, 0.3, 0.6)), 116));

    public static final RegistryObject<Item> earthManaSceptre = ITEMS.register("earth_mana_sceptre", () ->
            new RandomSceptre(new Item.Properties().rarity(CustomStyle.JacarandaBold), CustomStyle.styleOfJacaranda,
                    ComponentUtils.getSuffixOfChapterV(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.manaDamage, 300, 400),
                    new RandomAttributeValue(StringUtils.RandomAttribute.healthRecover, 25, 45)), 124));

    public static final RegistryObject<Item> sakuraChest = ITEMS.register("sakura_chest", () ->
            new RandomArmor(ItemMaterial.BloodMana, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.SakuraBold), CustomStyle.styleOfSakura,
                    ComponentUtils.getSuffixOfChapterV(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.manaDamage, 350, 450),
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaRecover, 30, 50),
                    new RandomAttributeValue(StringUtils.RandomAttribute.healthRecover, 20, 40)), 132));
}
