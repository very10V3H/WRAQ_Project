package com.very.wraq.series.gems;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
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
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.FieldBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.healEffectUp, 0.1));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.05));
            }}, CustomStyle.styleOfField, Component.literal("原野意志的具象，凝聚成此石。").withStyle(CustomStyle.styleOfField),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> fieldGemD = ITEMS.register("field_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.FieldBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.healEffectUp, 0.2));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.1));
            }}, CustomStyle.styleOfField, Component.literal("原野意志的具象，凝聚成此石。").withStyle(CustomStyle.styleOfField),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> forestGem = ITEMS.register("forest_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.ForestBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.03));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.03));
            }}, CustomStyle.styleOfForest, Component.literal("森林意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfForest),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> forestGemD = ITEMS.register("forest_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.ForestBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.06));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.06));
            }}, CustomStyle.styleOfForest, Component.literal("森林意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfForest),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> lakeGem = ITEMS.register("lake_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.LakeBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.coolDownDecrease, 0.2));
            }}, CustomStyle.styleOfLake, Component.literal("湖泊意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfLake),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> lakeGemD = ITEMS.register("lake_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.LakeBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.coolDownDecrease, 0.4));
            }}, CustomStyle.styleOfLake, Component.literal("湖泊意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfLake),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> mineGem = ITEMS.register("mine_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.MineBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.05));
            }}, CustomStyle.styleOfMine, Component.literal("劳动意志的精华，凝聚成此石。").withStyle(CustomStyle.styleOfMine),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> mineGemD = ITEMS.register("mine_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.MineBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.1));
            }}, CustomStyle.styleOfMine, Component.literal("劳动意志的精华，凝聚成此石。").withStyle(CustomStyle.styleOfMine),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> plainGem = ITEMS.register("plain_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.PlainBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.movementSpeedCommon, 0.04));
                add(new WraqGem.AttributeMapValue(Utils.healthRecover, 5));
            }}, CustomStyle.styleOfPlain, Component.literal("平原意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfPlain),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> plainGemD = ITEMS.register("plain_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.PlainBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.movementSpeedCommon, 0.08));
                add(new WraqGem.AttributeMapValue(Utils.healthRecover, 10));
            }}, CustomStyle.styleOfPlain, Component.literal("平原意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfPlain),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> snowGem = ITEMS.register("snow_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.SnowBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.critRate, 0.05));
                add(new WraqGem.AttributeMapValue(Utils.critDamage, 0.1));
            }}, CustomStyle.styleOfSnow, Component.literal("冰川意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfSnow),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> snowGemD = ITEMS.register("snow_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.SnowBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.critRate, 0.1));
                add(new WraqGem.AttributeMapValue(Utils.critDamage, 0.2));
            }}, CustomStyle.styleOfSnow, Component.literal("冰川意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfSnow),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> volcanoGem = ITEMS.register("volcano_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.VolcanoBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.03));
            }}, CustomStyle.styleOfVolcano, Component.literal("火山意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfVolcano),
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> volcanoGemD = ITEMS.register("volcano_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.VolcanoBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.06));
            }}, CustomStyle.styleOfVolcano, Component.literal("火山意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfVolcano),
                    ComponentUtils.getSuffixOfChapterI()));

    //* 2 *//

    public static final RegistryObject<Item> evokerGem = ITEMS.register("evoker_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.ManaBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentManaDamageEnhance, 0.03));
            }}, CustomStyle.styleOfMana, Component.literal("蕴含唤魔力量的宝石。").withStyle(CustomStyle.styleOfMana),
                    ComponentUtils.getSuffixChapterII()));

    public static final RegistryObject<Item> evokerGemD = ITEMS.register("evoker_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.ManaBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentManaDamageEnhance, 0.06));
            }}, CustomStyle.styleOfMana, Component.literal("蕴含唤魔力量的宝石。").withStyle(CustomStyle.styleOfMana),
                    ComponentUtils.getSuffixChapterII()));

    public static final RegistryObject<Item> lifeManaGem = ITEMS.register("life_mana_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.LifeBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.manaRecover, 5));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.02));
                add(new WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.02));
            }}, CustomStyle.styleOfLife, Component.literal("平原和森林的意志具象，凝聚成此石。").withStyle(CustomStyle.styleOfLife),
                    ComponentUtils.getSuffixChapterII()));

    public static final RegistryObject<Item> lifeManaGemD = ITEMS.register("life_mana_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.LifeBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.manaRecover, 10));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.04));
                add(new WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.04));
            }}, CustomStyle.styleOfLife, Component.literal("平原和森林的意志具象，凝聚成此石。").withStyle(CustomStyle.styleOfLife),
                    ComponentUtils.getSuffixChapterII()));

    public static final RegistryObject<Item> obsiManaGem = ITEMS.register("obsi_mana_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.ManaBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.manaRecover, 5));
                add(new WraqGem.AttributeMapValue(Utils.coolDownDecrease, 0.1));
            }}, CustomStyle.styleOfMana, Component.literal("湖泊与火山的意志具象，凝聚成此石。").withStyle(CustomStyle.styleOfMana),
                    ComponentUtils.getSuffixChapterII()));

    public static final RegistryObject<Item> obsiManaGemD = ITEMS.register("obsi_mana_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.ManaBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.manaRecover, 10));
                add(new WraqGem.AttributeMapValue(Utils.coolDownDecrease, 0.2));
            }}, CustomStyle.styleOfMana, Component.literal("湖泊与火山的意志具象，凝聚成此石。").withStyle(CustomStyle.styleOfMana),
                    ComponentUtils.getSuffixChapterII()));

    public static final RegistryObject<Item> skyGem = ITEMS.register("sky_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.SkyBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.01));
                add(new WraqGem.AttributeMapValue(Utils.movementSpeedCommon, 0.15));
            }}, CustomStyle.styleOfSky, Component.literal("天空意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfSky),
                    ComponentUtils.getSuffixChapterII()));

    public static final RegistryObject<Item> skyGemD = ITEMS.register("sky_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.SkyBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.02));
                add(new WraqGem.AttributeMapValue(Utils.movementSpeedCommon, 0.3));
            }}, CustomStyle.styleOfSky, Component.literal("天空意志的具象，凝聚于此石。").withStyle(CustomStyle.styleOfSky),
                    ComponentUtils.getSuffixChapterII()));

    //* 3 *//

    public static final RegistryObject<Item> magmaGem = ITEMS.register("magma_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.MagmaBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration, 0.06));
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration0, 300));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, -0.05));
            }}, CustomStyle.styleOfMagma, Component.literal("用封装下界熔岩能量所打造而成。").withStyle(CustomStyle.styleOfMagma),
                    ComponentUtils.getSuffixChapterIII()));

    public static final RegistryObject<Item> magmaGemD = ITEMS.register("magma_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.MagmaBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration, 0.12));
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration0, 600));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, -0.1));
            }}, CustomStyle.styleOfMagma, Component.literal("用封装下界熔岩能量所打造而成。").withStyle(CustomStyle.styleOfMagma),
                    ComponentUtils.getSuffixChapterIII()));

    public static final RegistryObject<Item> netherSkeletonGem = ITEMS.register("nether_skeleton_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.WitherBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration, 0.06));
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration0, 300));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, -0.05));
            }}, CustomStyle.styleOfWither, Component.literal("用精细下界骷髅粉末打造而成。").withStyle(CustomStyle.styleOfWither),
                    ComponentUtils.getSuffixChapterIII()));

    public static final RegistryObject<Item> netherSkeletonGemD = ITEMS.register("nether_skeleton_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.WitherBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration, 0.12));
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration0, 600));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, -0.1));
            }}, CustomStyle.styleOfWither, Component.literal("用精细下界骷髅粉末打造而成。").withStyle(CustomStyle.styleOfWither),
                    ComponentUtils.getSuffixChapterIII()));

    public static final RegistryObject<Item> piglinGem = ITEMS.register("piglin_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.PiglinBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.expUp, 0.4));
                add(new WraqGem.AttributeMapValue(Utils.healthRecover, -5));
            }}, CustomStyle.styleOfPiglin, Component.literal("用猪灵之好所打造而成。").withStyle(CustomStyle.styleOfPiglin),
                    ComponentUtils.getSuffixChapterIII()));

    public static final RegistryObject<Item> piglinGemD = ITEMS.register("piglin_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.PiglinBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.expUp, 0.8));
                add(new WraqGem.AttributeMapValue(Utils.healthRecover, -10));
            }}, CustomStyle.styleOfPiglin, Component.literal("用猪灵之好所打造而成。").withStyle(CustomStyle.styleOfPiglin),
                    ComponentUtils.getSuffixChapterIII()));

    public static final RegistryObject<Item> witherGem = ITEMS.register("wither_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.WitherBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.critDamage, 0.3));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, -0.05));
            }}, CustomStyle.styleOfWither, Component.literal("用能量灌注凋零残骨所打造而成。").withStyle(CustomStyle.styleOfWither),
                    ComponentUtils.getSuffixChapterIII()));

    public static final RegistryObject<Item> witherGemD = ITEMS.register("wither_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.WitherBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.critDamage, 0.6));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, -0.1));
            }}, CustomStyle.styleOfWither, Component.literal("用能量灌注凋零残骨所打造而成。").withStyle(CustomStyle.styleOfWither),
                    ComponentUtils.getSuffixChapterIII()));

    //* 5 *//

    public static final RegistryObject<Item> sakuraGem = ITEMS.register("sakura_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.SakuraBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.01));
                add(new WraqGem.AttributeMapValue(Utils.percentManaDamageEnhance, 0.01));
                add(new WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.01));
                add(new WraqGem.AttributeMapValue(Utils.percentManaDefenceEnhance, 0.01));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.01));
            }}, CustomStyle.styleOfSakura, Component.literal("樱花所凝聚而成。").withStyle(CustomStyle.styleOfSakura),
                    ComponentUtils.getSuffixOfChapterV()));

    public static final RegistryObject<Item> sakuraGemD = ITEMS.register("sakura_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.SakuraBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.02));
                add(new WraqGem.AttributeMapValue(Utils.percentManaDamageEnhance, 0.02));
                add(new WraqGem.AttributeMapValue(Utils.percentDefenceEnhance, 0.02));
                add(new WraqGem.AttributeMapValue(Utils.percentManaDefenceEnhance, 0.02));
                add(new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.02));
            }}, CustomStyle.styleOfSakura, Component.literal("樱花所凝聚而成。").withStyle(CustomStyle.styleOfSakura),
                    ComponentUtils.getSuffixOfChapterV()));

    public static final RegistryObject<Item> shipGem = ITEMS.register("ship_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.ShipBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration, 0.04));
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration, 0.04));
            }}, CustomStyle.styleOfShip, Component.literal("用破损船锚碎片所打造而成。").withStyle(CustomStyle.styleOfShip),
                    ComponentUtils.getSuffixOfChapterV()));

    public static final RegistryObject<Item> shipGemD = ITEMS.register("ship_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.ShipBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration, 0.08));
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration, 0.08));
            }}, CustomStyle.styleOfShip, Component.literal("用破损船锚碎片所打造而成。").withStyle(CustomStyle.styleOfShip),
                    ComponentUtils.getSuffixOfChapterV()));

    //* instance *//

    public static final RegistryObject<Item> castleArmorGem = ITEMS.register("castle_armor_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.CastleBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration0, 750));
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration0, 750));
            }}, CustomStyle.styleOfCastle, Component.literal("暗黑蕴魔注能").withStyle(CustomStyle.styleOfCastle),
                    ComponentUtils.getSuffixOfCastle()));

    public static final RegistryObject<Item> castleWeaponGem = ITEMS.register("castle_weapon_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.CastleBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration, 0.1));
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration, 0.1));
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
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.MoonBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentManaDefenceEnhance, 0.08));
            }}, CustomStyle.styleOfMoon, Component.literal("尘月宫明镜之心").withStyle(CustomStyle.styleOfMoon),
                    ComponentUtils.getSuffixOfMoon()));

    public static final RegistryObject<Item> moonAttackGemD = ITEMS.register("moon_attack_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.MoonBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.percentManaDefenceEnhance, 0.16));
            }}, CustomStyle.styleOfMoon, Component.literal("尘月宫明镜之心").withStyle(CustomStyle.styleOfMoon),
                    ComponentUtils.getSuffixOfMoon()));

    public static final RegistryObject<Item> moonManaGem = ITEMS.register("moon_mana_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.Moon1Bold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defence, 400));
                add(new WraqGem.AttributeMapValue(Utils.manaDamage, 120));
            }}, CustomStyle.styleOfMoon1, Component.literal("尘月宫明镜之心").withStyle(CustomStyle.styleOfMoon1),
                    ComponentUtils.getSuffixOfMoon()));

    public static final RegistryObject<Item> moonManaGemD = ITEMS.register("moon_mana_gem_d",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.Moon1Bold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defence, 800));
                add(new WraqGem.AttributeMapValue(Utils.manaDamage, 240));
            }}, CustomStyle.styleOfMoon1, Component.literal("尘月宫明镜之心").withStyle(CustomStyle.styleOfMoon1),
                    ComponentUtils.getSuffixOfMoon()));
}
