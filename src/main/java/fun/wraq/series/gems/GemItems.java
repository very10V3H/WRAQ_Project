package fun.wraq.series.gems;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class GemItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    //* 1 *//

    public static final RegistryObject<Item> fieldGem = ITEMS.register("field_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.FieldBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.healEffectUp, 0.1));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.05));
            }}, CustomStyle.styleOfField, Component.literal("原野意志的具象，凝聚成此石。").withStyle(CustomStyle.styleOfField),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> fieldGemD = ITEMS.register("field_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) fieldGem.get()));

    public static final RegistryObject<Item> fieldGemO = ITEMS.register("field_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) fieldGem.get(), 1));

    public static final RegistryObject<Item> forestGem = ITEMS.register("forest_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.ForestBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.03));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.03));
            }}, CustomStyle.styleOfForest, Component.literal("森林意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfForest),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> forestGemD = ITEMS.register("forest_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) forestGem.get()));

    public static final RegistryObject<Item> forestGemO = ITEMS.register("forest_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) forestGem.get(), 1));

    public static final RegistryObject<Item> lakeGem = ITEMS.register("lake_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.LakeBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.coolDownDecrease, 0.1));
            }}, CustomStyle.styleOfLake, Component.literal("湖泊意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfLake),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> lakeGemD = ITEMS.register("lake_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) lakeGem.get()));

    public static final RegistryObject<Item> lakeGemO = ITEMS.register("lake_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) lakeGem.get(), 1));

    public static final RegistryObject<Item> mineGem = ITEMS.register("mine_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.MineBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.05));
            }}, CustomStyle.styleOfMine, Component.literal("劳动意志的精华，凝聚成此石。").withStyle(CustomStyle.styleOfMine),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> mineGemD = ITEMS.register("mine_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) mineGem.get()));

    public static final RegistryObject<Item> mineGemO = ITEMS.register("mine_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) mineGem.get(), 1));

    public static final RegistryObject<Item> plainGem = ITEMS.register("plain_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.PlainBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.movementSpeedCommon, 0.04));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.healthRecover, 5));
            }}, CustomStyle.styleOfPlain, Component.literal("平原意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfPlain),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> plainGemD = ITEMS.register("plain_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) plainGem.get()));

    public static final RegistryObject<Item> plainGemO = ITEMS.register("plain_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) plainGem.get(), 1));

    public static final RegistryObject<Item> snowGem = ITEMS.register("snow_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.SnowBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.critRate, 0.05));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.critDamage, 0.1));
            }}, CustomStyle.styleOfSnow, Component.literal("冰川意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfSnow),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> snowGemD = ITEMS.register("snow_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) snowGem.get()));

    public static final RegistryObject<Item> snowGemO = ITEMS.register("snow_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) snowGem.get(), 2));

    public static final RegistryObject<Item> volcanoGem = ITEMS.register("volcano_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.VolcanoBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.03));
            }}, CustomStyle.styleOfVolcano, Component.literal("火山意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfVolcano),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> volcanoGemD = ITEMS.register("volcano_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) volcanoGem.get()));

    public static final RegistryObject<Item> volcanoGemO = ITEMS.register("volcano_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) volcanoGem.get(), 2));

    //* 2 *//

    public static final RegistryObject<Item> evokerGem = ITEMS.register("evoker_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.ManaBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentManaDamageEnhance, 0.03));
            }}, CustomStyle.styleOfMana, Component.literal("蕴含唤魔力量的宝石。").withStyle(CustomStyle.styleOfMana),
                    ComponentUtils.getSuffixOfChapterII()));

    public static final RegistryObject<Item> evokerGemD = ITEMS.register("evoker_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) evokerGem.get()));

    public static final RegistryObject<Item> evokerGemO = ITEMS.register("evoker_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) evokerGem.get(), 2));

    public static final RegistryObject<Item> lifeManaGem = ITEMS.register("life_mana_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.LifeBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.manaRecover, 5));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.02));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.02));
            }}, CustomStyle.styleOfLife, Component.literal("平原和森林的意志具象，凝聚成此石。").withStyle(CustomStyle.styleOfLife),
                    ComponentUtils.getSuffixOfChapterII()));

    public static final RegistryObject<Item> lifeManaGemD = ITEMS.register("life_mana_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) lifeManaGem.get()));

    public static final RegistryObject<Item> lifeManaGemO = ITEMS.register("life_mana_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) lifeManaGem.get(), 2));

    public static final RegistryObject<Item> obsiManaGem = ITEMS.register("obsi_mana_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.ManaBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.manaRecover, 5));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.coolDownDecrease, 0.1));
            }}, CustomStyle.styleOfMana, Component.literal("湖泊与火山的意志具象，凝聚成此石。").withStyle(CustomStyle.styleOfMana),
                    ComponentUtils.getSuffixOfChapterII()));

    public static final RegistryObject<Item> obsiManaGemD = ITEMS.register("obsi_mana_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) obsiManaGem.get()));

    public static final RegistryObject<Item> obsiManaGemO = ITEMS.register("obsi_mana_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) obsiManaGem.get(), 2));

    public static final RegistryObject<Item> skyGem = ITEMS.register("sky_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.SkyBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.01));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.movementSpeedCommon, 0.15));
            }}, CustomStyle.styleOfSky, Component.literal("天空意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfSky),
                    ComponentUtils.getSuffixOfChapterII()));

    public static final RegistryObject<Item> skyGemD = ITEMS.register("sky_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) skyGem.get()));

    public static final RegistryObject<Item> skyGemO = ITEMS.register("sky_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) skyGem.get(), 2));

    //* 3 *//

    public static final RegistryObject<Item> magmaGem = ITEMS.register("magma_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.MagmaBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.manaPenetration, 0.06));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.manaPenetration0, 3));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, -0.05));
            }}, CustomStyle.styleOfMagma, Component.literal("用封装下界熔岩能量所打造而成。").withStyle(CustomStyle.styleOfMagma),
                    ComponentUtils.getSuffixNether()));

    public static final RegistryObject<Item> magmaGemD = ITEMS.register("magma_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) magmaGem.get()));

    public static final RegistryObject<Item> magmaGemO = ITEMS.register("magma_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) magmaGem.get(), 4));

    public static final RegistryObject<Item> netherSkeletonGem = ITEMS.register("nether_skeleton_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.WitherBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.defencePenetration, 0.06));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.defencePenetration0, 3));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, -0.05));
            }}, CustomStyle.styleOfWither, Component.literal("用精细下界骷髅粉末打造而成。").withStyle(CustomStyle.styleOfWither),
                    ComponentUtils.getSuffixNether()));

    public static final RegistryObject<Item> netherSkeletonGemD = ITEMS.register("nether_skeleton_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) netherSkeletonGem.get()));

    public static final RegistryObject<Item> netherSkeletonGemO = ITEMS.register("nether_skeleton_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) netherSkeletonGem.get(), 4));

    public static final RegistryObject<Item> piglinGem = ITEMS.register("piglin_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.PiglinBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.expUp, 0.4));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.healthRecover, -5));
            }}, CustomStyle.styleOfPiglin, Component.literal("用猪灵之好所打造而成。").withStyle(CustomStyle.styleOfPiglin),
                    ComponentUtils.getSuffixNether()));

    public static final RegistryObject<Item> piglinGemD = ITEMS.register("piglin_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) piglinGem.get()));

    public static final RegistryObject<Item> piglinGemO = ITEMS.register("piglin_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) piglinGem.get(), 4));

    public static final RegistryObject<Item> witherGem = ITEMS.register("wither_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.WitherBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.critDamage, 0.3));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, -0.05));
            }}, CustomStyle.styleOfWither, Component.literal("用能量灌注凋零残骨所打造而成。").withStyle(CustomStyle.styleOfWither),
                    ComponentUtils.getSuffixNether()));

    public static final RegistryObject<Item> witherGemD = ITEMS.register("wither_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) witherGem.get()));

    public static final RegistryObject<Item> witherGemO = ITEMS.register("wither_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) witherGem.get(), 4));

    //* 5 *//

    public static final RegistryObject<Item> sakuraGem = ITEMS.register("sakura_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.SakuraBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.01));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentManaDamageEnhance, 0.01));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.01));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentManaDefenceEnhance, 0.01));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.01));
            }}, CustomStyle.styleOfSakura, Component.literal("樱花所凝聚而成。").withStyle(CustomStyle.styleOfSakura),
                    ComponentUtils.getSuffixOfSakura()));

    public static final RegistryObject<Item> sakuraGemD = ITEMS.register("sakura_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) sakuraGem.get()));

    public static final RegistryObject<Item> sakuraGemO = ITEMS.register("sakura_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) sakuraGem.get(), 3));

    public static final RegistryObject<Item> shipGem = ITEMS.register("ship_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.ShipBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.defencePenetration, 0.04));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.manaPenetration, 0.04));
            }}, CustomStyle.styleOfShip, Component.literal("用破损船锚碎片所打造而成。").withStyle(CustomStyle.styleOfShip),
                    ComponentUtils.getSuffixOfSakura()));

    public static final RegistryObject<Item> shipGemD = ITEMS.register("ship_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) shipGem.get()));

    public static final RegistryObject<Item> shipGemO = ITEMS.register("ship_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) shipGem.get(), 3));

    //* instance *//

    public static final RegistryObject<Item> castleArmorGem = ITEMS.register("castle_armor_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.CastleBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.defencePenetration0, 8));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.manaPenetration0, 8));
            }}, CustomStyle.styleOfCastle, Component.literal("暗黑蕴魔注能").withStyle(CustomStyle.styleOfCastle),
                    ComponentUtils.getSuffixOfCastle()));

    public static final RegistryObject<Item> castleWeaponGem = ITEMS.register("castle_weapon_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.CastleBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.defencePenetration, 0.1));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.manaPenetration, 0.1));
            }}, CustomStyle.styleOfCastle, Component.literal("暗黑蕴魔注能").withStyle(CustomStyle.styleOfCastle),
                    ComponentUtils.getSuffixOfCastle()));

/*    public static final RegistryObject<Item> castleArmorGemD = ITEMS.register("castle_armor_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.CastleBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration0, 1500));
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration0, 1500));
            }}, CustomStyle.styleOfCastle, Component.literal("暗黑蕴魔注能").withStyle(CustomStyle.styleOfCastle),
                    ComponentUtils.getSuffixOfCastle()));

    public static final RegistryObject<Item> castleWeaponGemD = ITEMS.register("castle_weapon_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.CastleBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration, 0.18));
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration, 0.18));
            }}, CustomStyle.styleOfCastle, Component.literal("暗黑蕴魔注能").withStyle(CustomStyle.styleOfCastle),
                    ComponentUtils.getSuffixOfCastle()));*/

    public static final RegistryObject<Item> moonAttackGem = ITEMS.register("moon_attack_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.MoonBold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.percentManaDefenceEnhance, 0.08));
            }}, CustomStyle.styleOfMoon, Component.literal("尘月宫明镜之心").withStyle(CustomStyle.styleOfMoon),
                    ComponentUtils.getSuffixOfMoon()));

    public static final RegistryObject<Item> moonAttackGemD = ITEMS.register("moon_attack_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) moonAttackGem.get()));

    public static final RegistryObject<Item> moonAttackGemO = ITEMS.register("moon_attack_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) moonAttackGem.get(), 3));

    public static final RegistryObject<Item> moonManaGem = ITEMS.register("moon_mana_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.Moon1Bold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.defence, 4));
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.manaDamage, 120));
            }}, CustomStyle.styleOfMoon1, Component.literal("尘月宫天镜之心").withStyle(CustomStyle.styleOfMoon1),
                    ComponentUtils.getSuffixOfMoon()));

    public static final RegistryObject<Item> moonManaGemD = ITEMS.register("moon_mana_gem_d",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemD((fun.wraq.series.gems.WraqGem) moonManaGem.get()));

    public static final RegistryObject<Item> moonManaGemO = ITEMS.register("moon_mana_gem_o",
            () -> new fun.wraq.series.gems.WraqGem.WraqGemO((fun.wraq.series.gems.WraqGem) moonManaGem.get(), 3));

    public static final RegistryObject<Item> TEST_GEM = ITEMS.register("test_gem",
            () -> new fun.wraq.series.gems.WraqGem(new Item.Properties().rarity(CustomStyle.Moon1Bold), new ArrayList<>() {{
                add(new fun.wraq.series.gems.WraqGem.AttributeMapValue(Utils.defence, 4));
                add(new WraqGem.AttributeMapValue(Utils.manaDamage, 120));
            }}, CustomStyle.styleOfMoon1, Component.literal("测试宝石").withStyle(CustomStyle.styleOfMoon1),
                    ComponentUtils.getSuffixOfMoon()));
}
