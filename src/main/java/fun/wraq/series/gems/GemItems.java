package fun.wraq.series.gems;

import fun.wraq.common.equip.WraqPickaxe;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.instance.series.mushroom.gem.MushroomParasitismGem;
import fun.wraq.series.instance.series.mushroom.gem.MushroomSplitGem;
import fun.wraq.series.instance.series.mushroom.gem.MushroomSputterGem;
import fun.wraq.series.instance.series.warden.gem.AncientDarknessGem;
import fun.wraq.series.instance.series.warden.gem.AncientEchoGem;
import fun.wraq.series.instance.series.warden.gem.AncientSilentGem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class GemItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    //* 1 *//

    public static final RegistryObject<Item> FIELD_GEM = ITEMS.register("field_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.FieldBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.healingAmplification, 0.1));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.05));
            }}, CustomStyle.styleOfField, Component.literal("原野意志的具象，凝聚成此石。").withStyle(CustomStyle.styleOfField),
                    ComponentUtils.getSuffixOfChapterI()));
    public static final RegistryObject<Item> FIELD_GEM_D = ITEMS.register("field_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) FIELD_GEM.get()));
    public static final RegistryObject<Item> FIELD_GEM_O = ITEMS.register("field_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) FIELD_GEM.get(), 1));

    public static final RegistryObject<Item> FOREST_GEM = ITEMS.register("forest_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.ForestBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.03));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.03));
            }}, CustomStyle.styleOfForest, Component.literal("森林意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfForest),
                    ComponentUtils.getSuffixOfChapterI()));
    public static final RegistryObject<Item> FOREST_GEM_D = ITEMS.register("forest_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) FOREST_GEM.get()));
    public static final RegistryObject<Item> FOREST_GEM_O = ITEMS.register("forest_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) FOREST_GEM.get(), 1));

    public static final RegistryObject<Item> LAKE_GEM = ITEMS.register("lake_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.LakeBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.coolDownDecrease, 0.08));
            }}, CustomStyle.styleOfLake, Component.literal("湖泊意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfLake),
                    ComponentUtils.getSuffixOfChapterI()));
    public static final RegistryObject<Item> LAKE_GEM_D = ITEMS.register("lake_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) LAKE_GEM.get()));
    public static final RegistryObject<Item> LAKE_GEM_O = ITEMS.register("lake_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) LAKE_GEM.get(), 1));

    public static final RegistryObject<Item> MINE_GEM = ITEMS.register("mine_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.MineBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.05));
                add(new WraqGem.AttributeMapValue(WraqPickaxe.mineSpeed, 0.33));
            }}, CustomStyle.styleOfMine, Component.literal("劳动意志的精华，凝聚成此石。").withStyle(CustomStyle.styleOfMine),
                    ComponentUtils.getSuffixOfChapterI()));
    public static final RegistryObject<Item> MINE_GEM_D = ITEMS.register("mine_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) MINE_GEM.get()));
    public static final RegistryObject<Item> MINE_GEM_O = ITEMS.register("mine_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) MINE_GEM.get(), 1));

    public static final RegistryObject<Item> PLAIN_GEM = ITEMS.register("plain_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.PlainBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.movementSpeedCommon, 0.01));
                add(new WraqGem.AttributeMapValue(Utils.healthRecover, 5));
            }}, CustomStyle.styleOfPlain, Component.literal("平原意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfPlain),
                    ComponentUtils.getSuffixOfChapterI()));
    public static final RegistryObject<Item> PLAIN_GEM_D = ITEMS.register("plain_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) PLAIN_GEM.get()));
    public static final RegistryObject<Item> PLAIN_GEM_O = ITEMS.register("plain_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) PLAIN_GEM.get(), 1));

    //* 2 *//

    public static final RegistryObject<Item> SNOW_GEM = ITEMS.register("snow_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.SnowBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.critRate, 0.02));
                add(new WraqGem.AttributeMapValue(Utils.critDamage, 0.03));
            }}, CustomStyle.styleOfSnow, Component.literal("冰川意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfSnow),
                    ComponentUtils.getSuffixOfChapterII()));
    public static final RegistryObject<Item> SNOW_GEM_D = ITEMS.register("snow_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) SNOW_GEM.get()));
    public static final RegistryObject<Item> SNOW_GEM_O = ITEMS.register("snow_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) SNOW_GEM.get(), 2));

    public static final RegistryObject<Item> VOLCANO_GEM = ITEMS.register("volcano_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.VolcanoBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.03));
            }}, CustomStyle.styleOfVolcano, Component.literal("火山意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfVolcano),
                    ComponentUtils.getSuffixOfChapterII()));
    public static final RegistryObject<Item> VOLCANO_GEM_D = ITEMS.register("volcano_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) VOLCANO_GEM.get()));
    public static final RegistryObject<Item> VOLCANO_GEM_O = ITEMS.register("volcano_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) VOLCANO_GEM.get(), 2));

    public static final RegistryObject<Item> EVOKER_GEM = ITEMS.register("evoker_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.ManaBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentManaDamageEnhance, 0.03));
            }}, CustomStyle.styleOfMana, Component.literal("蕴含唤魔力量的宝石。").withStyle(CustomStyle.styleOfMana),
                    ComponentUtils.getSuffixOfChapterII()));
    public static final RegistryObject<Item> EVOKER_GEM_D = ITEMS.register("evoker_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) EVOKER_GEM.get()));
    public static final RegistryObject<Item> EVOKER_GEM_O = ITEMS.register("evoker_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) EVOKER_GEM.get(), 2));

    public static final RegistryObject<Item> LIFE_MANA_GEM = ITEMS.register("life_mana_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.LifeBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.manaRecover, 5));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.02));
                add(new WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.02));
            }}, CustomStyle.styleOfLife, Component.literal("平原和森林的意志具象，凝聚成此石。").withStyle(CustomStyle.styleOfLife),
                    ComponentUtils.getSuffixOfChapterII()));
    public static final RegistryObject<Item> LIFE_MANA_GEM_D = ITEMS.register("life_mana_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) LIFE_MANA_GEM.get()));
    public static final RegistryObject<Item> LIFE_MANA_GEM_O = ITEMS.register("life_mana_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) LIFE_MANA_GEM.get(), 2));

    public static final RegistryObject<Item> OBSI_MANA_GEM = ITEMS.register("obsi_mana_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.ManaBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.manaRecover, 5));
                add(new WraqGem.AttributeMapValue(Utils.coolDownDecrease, 0.08));
            }}, CustomStyle.styleOfMana, Component.literal("湖泊与火山的意志具象，凝聚成此石。").withStyle(CustomStyle.styleOfMana),
                    ComponentUtils.getSuffixOfChapterII()));
    public static final RegistryObject<Item> OBSI_MANA_GEM_D = ITEMS.register("obsi_mana_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) OBSI_MANA_GEM.get()));
    public static final RegistryObject<Item> OBSI_MANA_GEM_O = ITEMS.register("obsi_mana_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) OBSI_MANA_GEM.get(), 2));

    public static final RegistryObject<Item> SKY_GEM = ITEMS.register("sky_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.SkyBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.01));
                add(new WraqGem.AttributeMapValue(Utils.movementSpeedCommon, 0.03));
            }}, CustomStyle.styleOfSky, Component.literal("天空意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfSky),
                    ComponentUtils.getSuffixOfChapterII()));
    public static final RegistryObject<Item> SKY_GEM_D = ITEMS.register("sky_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) SKY_GEM.get()));
    public static final RegistryObject<Item> SKY_GEM_O = ITEMS.register("sky_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) SKY_GEM.get(), 2));

    //* 3 *//

    public static final RegistryObject<Item> MAGMA_GEM = ITEMS.register("magma_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.MagmaBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration, 0.03));
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration0, 3));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, -0.05));
            }}, CustomStyle.styleOfMagma, Component.literal("用封装下界熔岩能量所打造而成。").withStyle(CustomStyle.styleOfMagma),
                    ComponentUtils.getSuffixOfNether()));
    public static final RegistryObject<Item> MAGMA_GEM_D = ITEMS.register("magma_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) MAGMA_GEM.get()));
    public static final RegistryObject<Item> MAGMA_GEM_O = ITEMS.register("magma_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) MAGMA_GEM.get(), 4));

    public static final RegistryObject<Item> NETHER_SKELETON_GEM = ITEMS.register("nether_skeleton_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.WitherBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration, 0.03));
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration0, 3));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, -0.05));
            }}, CustomStyle.styleOfWither, Component.literal("用精细下界骷髅粉末打造而成。").withStyle(CustomStyle.styleOfWither),
                    ComponentUtils.getSuffixOfNether()));
    public static final RegistryObject<Item> NETHER_SKELETON_GEM_D = ITEMS.register("nether_skeleton_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) NETHER_SKELETON_GEM.get()));
    public static final RegistryObject<Item> NETHER_SKELETON_GEM_O = ITEMS.register("nether_skeleton_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) NETHER_SKELETON_GEM.get(), 4));

    public static final RegistryObject<Item> PIGLIN_GEM = ITEMS.register("piglin_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.PiglinBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.expUp, 0.4));
                add(new WraqGem.AttributeMapValue(Utils.healthRecover, -5));
            }}, CustomStyle.styleOfPiglin, Component.literal("用猪灵之好所打造而成。").withStyle(CustomStyle.styleOfPiglin),
                    ComponentUtils.getSuffixOfNether()));
    public static final RegistryObject<Item> PIGLIN_GEM_D = ITEMS.register("piglin_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) PIGLIN_GEM.get()));
    public static final RegistryObject<Item> PIGLIN_GEM_O = ITEMS.register("piglin_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) PIGLIN_GEM.get(), 4));

    public static final RegistryObject<Item> WITHER_GEM = ITEMS.register("wither_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.WitherBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.critDamage, 0.08));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, -0.05));
            }}, CustomStyle.styleOfWither, Component.literal("用能量灌注凋零残骨所打造而成。").withStyle(CustomStyle.styleOfWither),
                    ComponentUtils.getSuffixOfNether()));
    public static final RegistryObject<Item> WITHER_GEM_D = ITEMS.register("wither_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) WITHER_GEM.get()));
    public static final RegistryObject<Item> WITHER_GEM_O = ITEMS.register("wither_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) WITHER_GEM.get(), 4));

    //* 5 *//

    public static final RegistryObject<Item> SAKURA_GEM = ITEMS.register("sakura_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.SakuraBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.01));
                add(new WraqGem.AttributeMapValue(Utils.percentManaDamageEnhance, 0.01));
                add(new WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.01));
                add(new WraqGem.AttributeMapValue(Utils.percentManaDefenceEnhance, 0.01));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.01));
            }}, CustomStyle.styleOfSakura, Component.literal("樱花所凝聚而成。").withStyle(CustomStyle.styleOfSakura),
                    ComponentUtils.getSuffixOfSakura()));
    public static final RegistryObject<Item> SAKURA_GEM_D = ITEMS.register("sakura_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) SAKURA_GEM.get()));
    public static final RegistryObject<Item> SAKURA_GEM_O = ITEMS.register("sakura_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) SAKURA_GEM.get(), 3));

    public static final RegistryObject<Item> SHIP_GEM = ITEMS.register("ship_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.ShipBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration, 0.04));
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration, 0.04));
            }}, CustomStyle.styleOfShip, Component.literal("用破损船锚碎片所打造而成。").withStyle(CustomStyle.styleOfShip),
                    ComponentUtils.getSuffixOfSakura()));
    public static final RegistryObject<Item> SHIP_GEM_D = ITEMS.register("ship_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) SHIP_GEM.get()));
    public static final RegistryObject<Item> SHIP_GEM_O = ITEMS.register("ship_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) SHIP_GEM.get(), 3));

    //* instance *//

    public static final RegistryObject<Item> CASTLE_ARMOR_GEM = ITEMS.register("castle_armor_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.CastleBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration0, 8));
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration0, 8));
            }}, CustomStyle.styleOfCastle, Component.literal("暗黑防具注能(仅可用于防具)")
                    .withStyle(CustomStyle.styleOfCastleCrystal),
                    ComponentUtils.getSuffixOfCastle()));
    public static final RegistryObject<Item> CASTLE_WEAPON_GEM = ITEMS.register("castle_weapon_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.CastleBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration, 0.1));
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration, 0.1));
            }}, CustomStyle.styleOfCastle, Component.literal("暗黑武器蕴能(仅可用于主手)")
                    .withStyle(CustomStyle.styleOfCastleCrystal),
                    ComponentUtils.getSuffixOfCastle()));

    public static final RegistryObject<Item> MOON_ATTACK_GEM = ITEMS.register("moon_attack_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.MoonBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentManaDefenceEnhance, 0.08));
            }}, CustomStyle.styleOfMoon, Component.literal("尘月宫明镜之心").withStyle(CustomStyle.styleOfMoon),
                    ComponentUtils.getSuffixOfMoon()));
    public static final RegistryObject<Item> MOON_ATTACK_GEM_D = ITEMS.register("moon_attack_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) MOON_ATTACK_GEM.get()));
    public static final RegistryObject<Item> MOON_ATTACK_GEM_O = ITEMS.register("moon_attack_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) MOON_ATTACK_GEM.get(), 3));

    public static final RegistryObject<Item> MOON_MANA_GEM = ITEMS.register("moon_mana_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.Moon1Bold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defence, 4));
                add(new WraqGem.AttributeMapValue(Utils.manaDamage, 120));
            }}, CustomStyle.styleOfMoon1, Component.literal("尘月宫天镜之心").withStyle(CustomStyle.styleOfMoon1),
                    ComponentUtils.getSuffixOfMoon()));
    public static final RegistryObject<Item> MOON_MANA_GEM_D = ITEMS.register("moon_mana_gem_d",
            () -> new WraqGem.WraqGemD((WraqGem) MOON_MANA_GEM.get()));
    public static final RegistryObject<Item> MOON_MANA_GEM_O = ITEMS.register("moon_mana_gem_o",
            () -> new WraqGem.WraqGemO((WraqGem) MOON_MANA_GEM.get(), 3));

    public static final RegistryObject<Item> ANCIENT_DARKNESS_GEM_0 = ITEMS.register("ancient_darkness_gem_0",
            () -> new AncientDarknessGem(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD), List.of(),
                    CustomStyle.styleOfWarden,
                    Te.s("无止境的远古暗黑深渊", CustomStyle.styleOfWarden),
                    ComponentUtils.getSuffixOfAncient(), 0));
    public static final RegistryObject<Item> ANCIENT_DARKNESS_GEM_1 = ITEMS.register("ancient_darkness_gem_1",
            () -> new AncientDarknessGem(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD), List.of(),
                    CustomStyle.styleOfWarden,
                    Te.s("无止境的远古暗黑深渊", CustomStyle.styleOfWarden),
                    ComponentUtils.getSuffixOfAncient(), 1));
    public static final RegistryObject<Item> ANCIENT_DARKNESS_GEM_2 = ITEMS.register("ancient_darkness_gem_2",
            () -> new AncientDarknessGem(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD), List.of(),
                    CustomStyle.styleOfWarden,
                    Te.s("无止境的远古暗黑深渊", CustomStyle.styleOfWarden),
                    ComponentUtils.getSuffixOfAncient(), 2));

    public static final RegistryObject<Item> ANCIENT_DARKNESS_GEM_3 = ITEMS.register("ancient_darkness_gem_3",
            () -> new AncientDarknessGem(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD), List.of(),
                    CustomStyle.styleOfWarden,
                    Te.s("无止境的远古暗黑深渊", CustomStyle.styleOfWarden),
                    ComponentUtils.getSuffixOfAncient(), 3));

    public static final RegistryObject<Item> ANCIENT_ECHO_GEM_0 = ITEMS.register("ancient_echo_gem_0",
            () -> new AncientEchoGem(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD), List.of(),
                    CustomStyle.styleOfWarden,
                    Te.s("无界限的远古暗黑回响", CustomStyle.styleOfWarden),
                    ComponentUtils.getSuffixOfAncient(), 0));

    public static final RegistryObject<Item> ANCIENT_ECHO_GEM_1 = ITEMS.register("ancient_echo_gem_1",
            () -> new AncientEchoGem(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD), List.of(),
                    CustomStyle.styleOfWarden,
                    Te.s("无界限的远古暗黑回响", CustomStyle.styleOfWarden),
                    ComponentUtils.getSuffixOfAncient(), 1));

    public static final RegistryObject<Item> ANCIENT_ECHO_GEM_2 = ITEMS.register("ancient_echo_gem_2",
            () -> new AncientEchoGem(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD), List.of(),
                    CustomStyle.styleOfWarden,
                    Te.s("无界限的远古暗黑回响", CustomStyle.styleOfWarden),
                    ComponentUtils.getSuffixOfAncient(), 2));

    public static final RegistryObject<Item> ANCIENT_ECHO_GEM_3 = ITEMS.register("ancient_echo_gem_3",
            () -> new AncientEchoGem(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD), List.of(),
                    CustomStyle.styleOfWarden,
                    Te.s("无界限的远古暗黑回响", CustomStyle.styleOfWarden),
                    ComponentUtils.getSuffixOfAncient(), 3));

    public static final RegistryObject<Item> ANCIENT_SILENT_GEM_0 = ITEMS.register("ancient_silent_gem_0",
            () -> new AncientSilentGem(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD), List.of(),
                    CustomStyle.styleOfWarden,
                    Te.s("无作声的寂静暗黑封印", CustomStyle.styleOfWarden),
                    ComponentUtils.getSuffixOfAncient(), 0));

    public static final RegistryObject<Item> ANCIENT_SILENT_GEM_1 = ITEMS.register("ancient_silent_gem_1",
            () -> new AncientSilentGem(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD), List.of(),
                    CustomStyle.styleOfWarden,
                    Te.s("无作声的寂静暗黑封印", CustomStyle.styleOfWarden),
                    ComponentUtils.getSuffixOfAncient(), 1));

    public static final RegistryObject<Item> ANCIENT_SILENT_GEM_2 = ITEMS.register("ancient_silent_gem_2",
            () -> new AncientSilentGem(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD), List.of(),
                    CustomStyle.styleOfWarden,
                    Te.s("无作声的寂静暗黑封印", CustomStyle.styleOfWarden),
                    ComponentUtils.getSuffixOfAncient(), 2));

    public static final RegistryObject<Item> ANCIENT_SILENT_GEM_3 = ITEMS.register("ancient_silent_gem_3",
            () -> new AncientSilentGem(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD), List.of(),
                    CustomStyle.styleOfWarden,
                    Te.s("无作声的寂静暗黑封印", CustomStyle.styleOfWarden),
                    ComponentUtils.getSuffixOfAncient(), 3));

    public static final RegistryObject<Item> MUSHROOM_SPUTTER_GEM = ITEMS.register("mushroom_sputter_gem",
            () -> new MushroomSputterGem(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY), List.of(),
                    CustomStyle.MUSHROOM_STYLE,
                    Te.s("繁育与扩张的生命魔力", CustomStyle.MUSHROOM_STYLE),
                    ComponentUtils.getSuffixOfMushroom(), false));

    public static final RegistryObject<Item> MUSHROOM_PARASITISM_GEM = ITEMS.register("mushroom_parasitism_gem",
            () -> new MushroomParasitismGem(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY), List.of(),
                    CustomStyle.MUSHROOM_STYLE,
                    Te.s("繁育与扩张的生命魔力", CustomStyle.MUSHROOM_STYLE),
                    ComponentUtils.getSuffixOfMushroom(), false));

    public static final RegistryObject<Item> MUSHROOM_SPLIT_GEM = ITEMS.register("mushroom_split_gem",
            () -> new MushroomSplitGem(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY), List.of(),
                    CustomStyle.MUSHROOM_STYLE,
                    Te.s("繁育与扩张的生命魔力", CustomStyle.MUSHROOM_STYLE),
                    ComponentUtils.getSuffixOfMushroom(), false));

    public static final RegistryObject<Item> OPEN_SLOT = ITEMS.register("open_slot",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.UNCOMMON), false, false, List.of(
                    Te.s("用于", "武器宝石孔 - 1 与 防具宝石孔 - 1", ChatFormatting.LIGHT_PURPLE, "的开孔")
            )));

    public static final RegistryObject<Item> OPEN_SLOT_GOLDEN = ITEMS.register("open_slot_golden",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.RARE), false, false, List.of(
                    Te.s("用于", "武器宝石孔 - 2 与 副手宝石孔 - 1", ChatFormatting.LIGHT_PURPLE, "的开孔")
            )));

    public static final RegistryObject<Item> OPEN_SLOT_DIAMOND = ITEMS.register("open_slot_diamond",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC), false, false, List.of(
                    Te.s("用于", "武器宝石孔 - 3 与 防具宝石孔 - 2", ChatFormatting.LIGHT_PURPLE, "的开孔")
            )));

    public static final RegistryObject<Item> DISMANTLE = ITEMS.register("dismantle",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.UNCOMMON), false, false, List.of(
                    Te.s("用于", "任意孔位宝石拆卸", ChatFormatting.LIGHT_PURPLE)
            )));
}
