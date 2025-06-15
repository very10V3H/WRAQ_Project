package fun.wraq.series.overworld.sakura.bunker;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.overworld.sakura.bunker.armor.BunkerArmor;
import fun.wraq.series.overworld.sakura.bunker.crest.BunkerAttackCrest;
import fun.wraq.series.overworld.sakura.bunker.crest.BunkerManaCrest;
import fun.wraq.series.overworld.sakura.bunker.weapon.main.BunkerBow;
import fun.wraq.series.overworld.sakura.bunker.weapon.main.BunkerSceptre;
import fun.wraq.series.overworld.sakura.bunker.weapon.main.BunkerSword;
import fun.wraq.series.overworld.sakura.bunker.weapon.off.BunkerBook;
import fun.wraq.series.overworld.sakura.bunker.weapon.off.BunkerKnife;
import fun.wraq.series.overworld.sakura.bunker.weapon.off.BunkerShield;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BunkerItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> BUNKER_SOUL = ITEMS.register("bunker_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BUNKER_RARITY)));
    public static final RegistryObject<Item> BUNKER_RUNE = ITEMS.register("bunker_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY)));

    public static final RegistryObject<Item> BUNKER_BOSS_SOUL = ITEMS.register("bunker_boss_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BUNKER_RARITY)));
    public static final RegistryObject<Item> BUNKER_BOSS_RUNE = ITEMS.register("bunker_boss_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY)));

    public static final RegistryObject<Item> BUNKER_SWORD = ITEMS.register("bunker_sword",
            () -> new BunkerSword(new Item.Properties().rarity(CustomStyle.BUNKER_ITALIC_RARITY)));
    public static final RegistryObject<Item> BUNKER_BOW = ITEMS.register("bunker_bow",
            () -> new BunkerBow(new Item.Properties().rarity(CustomStyle.BUNKER_ITALIC_RARITY)));
    public static final RegistryObject<Item> BUNKER_SCEPTRE = ITEMS.register("bunker_sceptre",
            () -> new BunkerSceptre(new Item.Properties().rarity(CustomStyle.BUNKER_ITALIC_RARITY)));

    public static final RegistryObject<Item> BUNKER_SHIELD = ITEMS.register("bunker_shield",
            () -> new BunkerShield(new Item.Properties().rarity(CustomStyle.BUNKER_ITALIC_RARITY),
                    Te.s("盾牌", CustomStyle.styleOfMine)));
    public static final RegistryObject<Item> BUNKER_KNIFE = ITEMS.register("bunker_knife",
            () -> new BunkerKnife(new Item.Properties().rarity(CustomStyle.BUNKER_ITALIC_RARITY),
                    Te.s("匕首", ChatFormatting.AQUA)));
    public static final RegistryObject<Item> BUNKER_BOOK = ITEMS.register("bunker_book",
            () -> new BunkerBook(new Item.Properties().rarity(CustomStyle.BUNKER_ITALIC_RARITY),
                    Te.s("魔导书", CustomStyle.styleOfMana)));

    public static final RegistryObject<Item> BUNKER_CURIO_0 = ITEMS.register("bunker_curio_0",
            () -> new BunkerCurio(new Item.Properties().rarity(CustomStyle.BUNKER_ITALIC_RARITY), 0));
    public static final RegistryObject<Item> BUNKER_CURIO_1 = ITEMS.register("bunker_curio_1",
            () -> new BunkerCurio(new Item.Properties().rarity(CustomStyle.BUNKER_ITALIC_RARITY), 1));

    public static final RegistryObject<Item> BUNKER_ATTACK_CREST_0 = ITEMS.register("bunker_attack_crest_0",
            () -> new BunkerAttackCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> BUNKER_ATTACK_CREST_1 = ITEMS.register("bunker_attack_crest_1",
            () -> new BunkerAttackCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> BUNKER_ATTACK_CREST_2 = ITEMS.register("bunker_attack_crest_2",
            () -> new BunkerAttackCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> BUNKER_ATTACK_CREST_3 = ITEMS.register("bunker_attack_crest_3",
            () -> new BunkerAttackCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> BUNKER_ATTACK_CREST_4 = ITEMS.register("bunker_attack_crest_4",
            () -> new BunkerAttackCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> BUNKER_MANA_CREST_0 = ITEMS.register("bunker_mana_crest_0",
            () -> new BunkerManaCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> BUNKER_MANA_CREST_1 = ITEMS.register("bunker_mana_crest_1",
            () -> new BunkerManaCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> BUNKER_MANA_CREST_2 = ITEMS.register("bunker_mana_crest_2",
            () -> new BunkerManaCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> BUNKER_MANA_CREST_3 = ITEMS.register("bunker_mana_crest_3",
            () -> new BunkerManaCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> BUNKER_MANA_CREST_4 = ITEMS.register("bunker_mana_crest_4",
            () -> new BunkerManaCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> BUNKER_HELMET_0 = ITEMS.register("bunker_helmet_0",
            () -> new BunkerArmor(ModArmorMaterials.FireElement,
                    ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 0));
    public static final RegistryObject<Item> BUNKER_HELMET_1 = ITEMS.register("bunker_helmet_1",
            () -> new BunkerArmor(ModArmorMaterials.FireElement,
                    ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 1));
    public static final RegistryObject<Item> BUNKER_CHEST_0 = ITEMS.register("bunker_chest_0",
            () -> new BunkerArmor(ModArmorMaterials.FireElement,
                    ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 0));
    public static final RegistryObject<Item> BUNKER_CHEST_1 = ITEMS.register("bunker_chest_1",
            () -> new BunkerArmor(ModArmorMaterials.FireElement,
                    ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 1));
    public static final RegistryObject<Item> BUNKER_LEGGINGS_0 = ITEMS.register("bunker_leggings_0",
            () -> new BunkerArmor(ModArmorMaterials.FireElement,
                    ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 0));
    public static final RegistryObject<Item> BUNKER_LEGGINGS_1 = ITEMS.register("bunker_leggings_1",
            () -> new BunkerArmor(ModArmorMaterials.FireElement,
                    ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 1));
    public static final RegistryObject<Item> BUNKER_BOOTS_0 = ITEMS.register("bunker_boots_0",
            () -> new BunkerArmor(ModArmorMaterials.FireElement,
                    ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 0));
    public static final RegistryObject<Item> BUNKER_BOOTS_1 = ITEMS.register("bunker_boots_1",
            () -> new BunkerArmor(ModArmorMaterials.FireElement,
                    ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 1));
}
