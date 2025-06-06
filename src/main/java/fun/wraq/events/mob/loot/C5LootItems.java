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

public class C5LootItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> PILLAGER_BOW = ITEMS.register("pillager_bow", () ->
            new RandomBow(new Item.Properties().rarity(CustomStyle.ShipBold), CustomStyle.styleOfShip,
                    ComponentUtils.getSuffixOfSakura(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 200, 300),
                    new RandomAttributeValue(StringUtils.RandomAttribute.critRate, 0.3, 0.4),
                    new RandomAttributeValue(StringUtils.RandomAttribute.defencePenetration0, 2, 3)), 108));

    public static final RegistryObject<Item> BLOOD_MANA_LOOT_BOOTS = ITEMS.register("blood_mana_loot_boots", () ->
            new RandomArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.BloodManaBold), CustomStyle.styleOfBloodMana,
                    ComponentUtils.getSuffixOfSakura(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaDamage, 300, 400),
                    new RandomAttributeValue(StringUtils.RandomAttribute.attackDamage, 200, 300),
                    new RandomAttributeValue(StringUtils.RandomAttribute.movementSpeedWithoutBattle, 0.3, 0.6)), 116));

    public static final RegistryObject<Item> EARTH_MANA_SCEPTRE = ITEMS.register("earth_mana_sceptre", () ->
            new RandomSceptre(new Item.Properties().rarity(CustomStyle.JacarandaBold), CustomStyle.styleOfJacaranda,
                    ComponentUtils.getSuffixOfSakura(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaDamage, 300, 400),
                    new RandomAttributeValue(StringUtils.RandomAttribute.healthRecover, 25, 45)), 124));

    public static final RegistryObject<Item> SAKURA_CHEST = ITEMS.register("sakura_chest", () ->
            new RandomArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.SakuraBold), CustomStyle.styleOfSakura,
                    ComponentUtils.getSuffixOfSakura(), List.of(
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaDamage, 350, 450),
                    new RandomAttributeValue(StringUtils.RandomAttribute.manaRecover, 30, 50),
                    new RandomAttributeValue(StringUtils.RandomAttribute.healthRecover, 20, 40)), 132));
}
