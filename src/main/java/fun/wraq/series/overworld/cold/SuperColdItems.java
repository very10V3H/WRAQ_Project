package fun.wraq.series.overworld.cold;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.items.misc.PocketItem;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.overworld.cold.sc2.stray.SuperColdFlower;
import fun.wraq.series.overworld.cold.sc3.fir.FirCrystal;
import fun.wraq.series.overworld.cold.sc3.maple.MapleKnife;
import fun.wraq.series.overworld.cold.sc4.BlizzardBoots;
import fun.wraq.series.overworld.cold.sc4.ColdIronArmor;
import fun.wraq.series.overworld.cold.sc5.dragon.curio.SuperColdDragonCurio;
import fun.wraq.series.overworld.cold.sc5.dragon.weapon.SuperColdDragonBow;
import fun.wraq.series.overworld.cold.sc5.dragon.weapon.SuperColdDragonSceptre;
import fun.wraq.series.overworld.cold.sc5.dragon.weapon.SuperColdDragonSword;
import fun.wraq.series.overworld.cold.sc5.dragon.gem.SuperColdDragonBreathGem;
import fun.wraq.series.overworld.cold.sc5.dragon.gem.SuperColdGlacierGem;
import fun.wraq.series.overworld.cold.sc5.dragon.gem.SuperColdWindGem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class SuperColdItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> FLOWER = ITEMS.register("super_cold_flower",
            () -> new SuperColdFlower(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> GREEN_WOOL = ITEMS.register("green_wool",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GreenBold), true, true));
    public static final RegistryObject<Item> GREEN_WOOL_POCKET = ITEMS.register("green_wool_pocket",
            () -> new PocketItem(new Item.Properties().rarity(CustomStyle.GreenBold), GREEN_WOOL.get()));
    public static final RegistryObject<Item> BLUE_WOOL = ITEMS.register("blue_wool",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WaterBold), true, true));
    public static final RegistryObject<Item> BLUE_WOOL_POCKET = ITEMS.register("blue_wool_pocket",
            () -> new PocketItem(new Item.Properties().rarity(CustomStyle.WaterBold), BLUE_WOOL.get()));
    public static final RegistryObject<Item> YELLOW_WOOL = ITEMS.register("yellow_wool",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GoldBold), true, true));
    public static final RegistryObject<Item> YELLOW_WOOL_POCKET = ITEMS.register("yellow_wool_pocket",
            () -> new PocketItem(new Item.Properties().rarity(CustomStyle.GoldBold), YELLOW_WOOL.get()));
    public static final RegistryObject<Item> RED_WOOL = ITEMS.register("red_wool",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.RedBold), true, true));
    public static final RegistryObject<Item> RED_WOOL_POCKET = ITEMS.register("red_wool_pocket",
            () -> new PocketItem(new Item.Properties().rarity(CustomStyle.RedBold), RED_WOOL.get()));
    public static final RegistryObject<Item> PURPLE_WOOL = ITEMS.register("purple_wool",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LightPurpleBold), true, true));
    public static final RegistryObject<Item> PURPLE_WOOL_POCKET = ITEMS.register("purple_wool_pocket",
            () -> new PocketItem(new Item.Properties().rarity(CustomStyle.LightPurpleBold), PURPLE_WOOL.get()));

    public static final RegistryObject<Item> MAPLE_SOUL = ITEMS.register("maple_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MAPLE_BOLD_RARITY), true, false));
    public static final RegistryObject<Item> MAPLE_RUNE = ITEMS.register("maple_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MAPLE_BOLD_RARITY), true, true));
    public static final RegistryObject<Item> MAPLE_KNIFE_0 = ITEMS.register("maple_knife_0",
            () -> new MapleKnife(new Item.Properties().rarity(CustomStyle.MAPLE_BOLD_RARITY),
                    Te.s("匕首", CustomStyle.styleOfStone), 0));
    public static final RegistryObject<Item> MAPLE_KNIFE_1 = ITEMS.register("maple_knife_1",
            () -> new MapleKnife(new Item.Properties().rarity(CustomStyle.MAPLE_BOLD_RARITY),
                    Te.s("匕首", CustomStyle.styleOfStone), 1));
    public static final RegistryObject<Item> MAPLE_KNIFE_2 = ITEMS.register("maple_knife_2",
            () -> new MapleKnife(new Item.Properties().rarity(CustomStyle.MAPLE_BOLD_RARITY),
                    Te.s("匕首", CustomStyle.styleOfStone), 2));
    public static final RegistryObject<Item> MAPLE_KNIFE_3 = ITEMS.register("maple_knife_3",
            () -> new MapleKnife(new Item.Properties().rarity(CustomStyle.MAPLE_BOLD_RARITY),
                    Te.s("匕首", CustomStyle.styleOfStone), 3));

    public static final RegistryObject<Item> FIR_SOUL = ITEMS.register("fir_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.FIR_BOLD_RARITY), true, false));
    public static final RegistryObject<Item> FIR_RUNE = ITEMS.register("fir_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.FIR_BOLD_RARITY), true, true));
    public static final RegistryObject<Item> FIR_CRYSTAL_0 = ITEMS.register("fir_crystal_0",
            () -> new FirCrystal(new Item.Properties().rarity(CustomStyle.FIR_BOLD_RARITY),
                    Te.s("魔法水晶", CustomStyle.styleOfJacaranda), 0));
    public static final RegistryObject<Item> FIR_CRYSTAL_1 = ITEMS.register("fir_crystal_1",
            () -> new FirCrystal(new Item.Properties().rarity(CustomStyle.FIR_BOLD_RARITY),
                    Te.s("魔法水晶", CustomStyle.styleOfJacaranda), 1));
    public static final RegistryObject<Item> FIR_CRYSTAL_2 = ITEMS.register("fir_crystal_2",
            () -> new FirCrystal(new Item.Properties().rarity(CustomStyle.FIR_BOLD_RARITY),
                    Te.s("魔法水晶", CustomStyle.styleOfJacaranda), 2));
    public static final RegistryObject<Item> FIR_CRYSTAL_3 = ITEMS.register("fir_crystal_3",
            () -> new FirCrystal(new Item.Properties().rarity(CustomStyle.FIR_BOLD_RARITY),
                    Te.s("魔法水晶", CustomStyle.styleOfJacaranda), 3));

    public static final RegistryObject<Item> BLIZZARD_SOUL = ITEMS.register("blizzard_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SnowBold), true, false));
    public static final RegistryObject<Item> BLIZZARD_RUNE = ITEMS.register("blizzard_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SnowBold), true, true));
    public static final RegistryObject<Item> BLIZZARD_BOOTS_0 = ITEMS.register("blizzard_boots_0",
            () -> new BlizzardBoots(ModArmorMaterials.COLD, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.SnowItalic), 0));
    public static final RegistryObject<Item> BLIZZARD_BOOTS_1 = ITEMS.register("blizzard_boots_1",
            () -> new BlizzardBoots(ModArmorMaterials.COLD, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.SnowItalic), 1));
    public static final RegistryObject<Item> BLIZZARD_BOOTS_2 = ITEMS.register("blizzard_boots_2",
            () -> new BlizzardBoots(ModArmorMaterials.COLD, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.SnowItalic), 2));

    public static final RegistryObject<Item> COLD_IRON_GOLEM_SOUL = ITEMS.register("cold_iron_golem_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold), true, false));
    public static final RegistryObject<Item> COLD_IRON_GOLEM_RUNE = ITEMS.register("cold_iron_golem_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold), true, true));
    public static final RegistryObject<Item> COLD_IRON_HELMET_0 = ITEMS.register("cold_iron_helmet_0",
            () -> new ColdIronArmor(ModArmorMaterials.COLD, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.IceItalic), 0));
    public static final RegistryObject<Item> COLD_IRON_HELMET_1 = ITEMS.register("cold_iron_helmet_1",
            () -> new ColdIronArmor(ModArmorMaterials.COLD, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.IceItalic), 1));
    public static final RegistryObject<Item> COLD_IRON_HELMET_2 = ITEMS.register("cold_iron_helmet_2",
            () -> new ColdIronArmor(ModArmorMaterials.COLD, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.IceItalic), 2));
    public static final RegistryObject<Item> COLD_IRON_CHEST_0 = ITEMS.register("cold_iron_chest_0",
            () -> new ColdIronArmor(ModArmorMaterials.COLD, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.IceItalic), 0));
    public static final RegistryObject<Item> COLD_IRON_CHEST_1 = ITEMS.register("cold_iron_chest_1",
            () -> new ColdIronArmor(ModArmorMaterials.COLD, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.IceItalic), 1));
    public static final RegistryObject<Item> COLD_IRON_CHEST_2 = ITEMS.register("cold_iron_chest_2",
            () -> new ColdIronArmor(ModArmorMaterials.COLD, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.IceItalic), 2));
    public static final RegistryObject<Item> COLD_IRON_LEGGINGS_0 = ITEMS.register("cold_iron_leggings_0",
            () -> new ColdIronArmor(ModArmorMaterials.COLD, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.IceItalic), 0));
    public static final RegistryObject<Item> COLD_IRON_LEGGINGS_1 = ITEMS.register("cold_iron_leggings_1",
            () -> new ColdIronArmor(ModArmorMaterials.COLD, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.IceItalic), 1));
    public static final RegistryObject<Item> COLD_IRON_LEGGINGS_2 = ITEMS.register("cold_iron_leggings_2",
            () -> new ColdIronArmor(ModArmorMaterials.COLD, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.IceItalic), 2));

    public static final RegistryObject<Item> COLD_RUNE = ITEMS.register("cold_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold), true, true));

    public static final RegistryObject<Item> DRAGON_SWORD_0 = ITEMS.register("super_cold_dragon_sword_0",
            () -> new SuperColdDragonSword(new Item.Properties().rarity(CustomStyle.IceItalic), 0));
    public static final RegistryObject<Item> DRAGON_SWORD_1 = ITEMS.register("super_cold_dragon_sword_1",
            () -> new SuperColdDragonSword(new Item.Properties().rarity(CustomStyle.IceItalic), 1));
    public static final RegistryObject<Item> DRAGON_SWORD_2 = ITEMS.register("super_cold_dragon_sword_2",
            () -> new SuperColdDragonSword(new Item.Properties().rarity(CustomStyle.IceItalic), 2));

    public static final RegistryObject<Item> DRAGON_BOW_0 = ITEMS.register("super_cold_dragon_bow_0",
            () -> new SuperColdDragonBow(new Item.Properties().rarity(CustomStyle.IceItalic), 0));
    public static final RegistryObject<Item> DRAGON_BOW_1 = ITEMS.register("super_cold_dragon_bow_1",
            () -> new SuperColdDragonBow(new Item.Properties().rarity(CustomStyle.IceItalic), 1));
    public static final RegistryObject<Item> DRAGON_BOW_2 = ITEMS.register("super_cold_dragon_bow_2",
            () -> new SuperColdDragonBow(new Item.Properties().rarity(CustomStyle.IceItalic), 2));

    public static final RegistryObject<Item> DRAGON_SCEPTRE_0 = ITEMS.register("super_cold_dragon_sceptre_0",
            () -> new SuperColdDragonSceptre(new Item.Properties().rarity(CustomStyle.IceItalic), 0));
    public static final RegistryObject<Item> DRAGON_SCEPTRE_1 = ITEMS.register("super_cold_dragon_sceptre_1",
            () -> new SuperColdDragonSceptre(new Item.Properties().rarity(CustomStyle.IceItalic), 1));
    public static final RegistryObject<Item> DRAGON_SCEPTRE_2 = ITEMS.register("super_cold_dragon_sceptre_2",
            () -> new SuperColdDragonSceptre(new Item.Properties().rarity(CustomStyle.IceItalic), 2));

    public static final RegistryObject<Item> WEAPON_CORE = ITEMS.register("super_cold_weapon_core",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold), true, true));
    public static final RegistryObject<Item> EQUIP_CORE = ITEMS.register("super_cold_equip_core",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold), true, true));

    public static final RegistryObject<Item> DRAGON_BREATH_GEM_0 = ITEMS.register("super_cold_dragon_breath_gem_0",
            () -> new SuperColdDragonBreathGem(new Item.Properties().rarity(CustomStyle.IceBold), List.of(),
                    CustomStyle.styleOfIce, Te.s("冰龙之龙息", CustomStyle.styleOfIce),
                    ComponentUtils.getSuffixOfSuperCold(), 0));
    public static final RegistryObject<Item> DRAGON_BREATH_GEM_1 = ITEMS.register("super_cold_dragon_breath_gem_1",
            () -> new SuperColdDragonBreathGem(new Item.Properties().rarity(CustomStyle.IceBold), List.of(),
                    CustomStyle.styleOfIce, Te.s("冰龙之龙息", CustomStyle.styleOfIce),
                    ComponentUtils.getSuffixOfSuperCold(), 1));
    public static final RegistryObject<Item> DRAGON_BREATH_GEM_2 = ITEMS.register("super_cold_dragon_breath_gem_2",
            () -> new SuperColdDragonBreathGem(new Item.Properties().rarity(CustomStyle.IceBold), List.of(),
                    CustomStyle.styleOfIce, Te.s("冰龙之龙息", CustomStyle.styleOfIce),
                    ComponentUtils.getSuffixOfSuperCold(), 2));

    public static final RegistryObject<Item> WIND_GEM_0 = ITEMS.register("super_cold_wind_gem_0",
            () -> new SuperColdWindGem(new Item.Properties().rarity(CustomStyle.IceBold), List.of(),
                    CustomStyle.styleOfIce, Te.s("极寒之狂风", CustomStyle.styleOfIce),
                    ComponentUtils.getSuffixOfSuperCold(), 0));
    public static final RegistryObject<Item> WIND_GEM_1 = ITEMS.register("super_cold_wind_gem_1",
            () -> new SuperColdWindGem(new Item.Properties().rarity(CustomStyle.IceBold), List.of(),
                    CustomStyle.styleOfIce, Te.s("极寒之狂风", CustomStyle.styleOfIce),
                    ComponentUtils.getSuffixOfSuperCold(), 1));
    public static final RegistryObject<Item> WIND_GEM_2 = ITEMS.register("super_cold_wind_gem_2",
            () -> new SuperColdWindGem(new Item.Properties().rarity(CustomStyle.IceBold), List.of(),
                    CustomStyle.styleOfIce, Te.s("极寒之狂风", CustomStyle.styleOfIce),
                    ComponentUtils.getSuffixOfSuperCold(), 2));

    public static final RegistryObject<Item> GLACIER_GEM_0 = ITEMS.register("super_cold_glacier_gem_0",
            () -> new SuperColdGlacierGem(new Item.Properties().rarity(CustomStyle.IceBold), List.of(),
                    CustomStyle.styleOfIce, Te.s("极寒之冰川", CustomStyle.styleOfIce),
                    ComponentUtils.getSuffixOfSuperCold(), 0));
    public static final RegistryObject<Item> GLACIER_GEM_1 = ITEMS.register("super_cold_glacier_gem_1",
            () -> new SuperColdGlacierGem(new Item.Properties().rarity(CustomStyle.IceBold), List.of(),
                    CustomStyle.styleOfIce, Te.s("极寒之冰川", CustomStyle.styleOfIce),
                    ComponentUtils.getSuffixOfSuperCold(), 1));
    public static final RegistryObject<Item> GLACIER_GEM_2 = ITEMS.register("super_cold_glacier_gem_2",
            () -> new SuperColdGlacierGem(new Item.Properties().rarity(CustomStyle.IceBold), List.of(),
                    CustomStyle.styleOfIce, Te.s("极寒之冰川", CustomStyle.styleOfIce),
                    ComponentUtils.getSuffixOfSuperCold(), 2));

    public static final RegistryObject<Item> DRAGON_CURIO = ITEMS.register("super_cold_dragon_curio",
            () -> new SuperColdDragonCurio(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> SUPER_COLD_STONE = ITEMS.register("super_cold_stone",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold), true, true));
}
