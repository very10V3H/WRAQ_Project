package fun.wraq.blocks.blocks.inject;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.struct.InjectingRecipe;
import fun.wraq.process.system.cooking.CookingItems;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.series.end.citadel.CitadelItems;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.gems.GemItems;
import fun.wraq.series.instance.series.warden.WardenItems;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.*;

public class InjectRecipe {

    public static Map<Item, InjectingRecipe> injectingRecipeMap = new LinkedHashMap<>();

    // 通过得到的物品获取源物品
    public static Map<Item, Item> productSourceItemMap = new HashMap<>();

    // 材料可以灌注何种物品的map
    public static Map<Item, List<InjectingRecipe>> injectingWaysMap = new HashMap<>();

    public static void setInjectingRecipeMap() {
        injectingRecipeMap.put(ModItems.PlainSword0.get(),
                new InjectingRecipe(ModItems.PlainRune.get(), 1,
                        ModItems.PlainSword1.get()));
        injectingRecipeMap.put(ModItems.PlainSword1.get(),
                new InjectingRecipe(ModItems.PlainRune.get(), 2,
                        ModItems.PlainSword2.get()));
        injectingRecipeMap.put(ModItems.PlainSword2.get(),
                new InjectingRecipe(ModItems.PlainRune.get(), 3,
                        ModItems.PlainSword3.get()));

        injectingRecipeMap.put(ModItems.PlainBow0.get(),
                new InjectingRecipe(ModItems.PlainRune.get(), 1,
                        ModItems.PlainBow1.get()));
        injectingRecipeMap.put(ModItems.PlainBow1.get(),
                new InjectingRecipe(ModItems.PlainRune.get(), 2,
                        ModItems.PlainBow2.get()));
        injectingRecipeMap.put(ModItems.PlainBow2.get(),
                new InjectingRecipe(ModItems.PlainRune.get(), 3,
                        ModItems.PlainBow3.get()));

        injectingRecipeMap.put(ModItems.LIFE_SCEPTRE_0.get(),
                new InjectingRecipe(ModItems.PlainRune.get(), 1,
                        ModItems.LIFE_SCEPTRE_1.get()));
        injectingRecipeMap.put(ModItems.LIFE_SCEPTRE_1.get(),
                new InjectingRecipe(ModItems.PlainRune.get(), 2,
                        ModItems.LIFE_SCEPTRE_2.get()));
        injectingRecipeMap.put(ModItems.LIFE_SCEPTRE_2.get(),
                new InjectingRecipe(ModItems.ForestRune.get(), 3,
                        ModItems.LIFE_SCEPTRE_3.get()));

        injectingRecipeMap.put(ModItems.ForestSword0.get(),
                new InjectingRecipe(ModItems.ForestRune.get(), 1,
                        ModItems.ForestSword1.get()));
        injectingRecipeMap.put(ModItems.ForestSword1.get(),
                new InjectingRecipe(ModItems.ForestRune.get(), 2,
                        ModItems.ForestSword2.get()));
        injectingRecipeMap.put(ModItems.ForestSword2.get(),
                new InjectingRecipe(ModItems.ForestRune.get(), 3,
                        ModItems.ForestSword3.get()));

        injectingRecipeMap.put(ModItems.ForestBow0.get(),
                new InjectingRecipe(ModItems.ForestRune.get(), 1,
                        ModItems.ForestBow1.get()));
        injectingRecipeMap.put(ModItems.ForestBow1.get(),
                new InjectingRecipe(ModItems.ForestRune.get(), 2,
                        ModItems.ForestBow2.get()));
        injectingRecipeMap.put(ModItems.ForestBow2.get(),
                new InjectingRecipe(ModItems.ForestRune.get(), 3,
                        ModItems.ForestBow3.get()));

        injectingRecipeMap.put(ModItems.LakeSword0.get(),
                new InjectingRecipe(ModItems.LakeRune.get(), 1,
                        ModItems.LakeSword1.get()));
        injectingRecipeMap.put(ModItems.LakeSword1.get(),
                new InjectingRecipe(ModItems.LakeRune.get(), 2,
                        ModItems.LakeSword2.get()));
        injectingRecipeMap.put(ModItems.LakeSword2.get(),
                new InjectingRecipe(ModItems.LakeRune.get(), 3,
                        ModItems.LakeSword3.get()));

        injectingRecipeMap.put(ModItems.lakeBow0.get(),
                new InjectingRecipe(ModItems.LakeRune.get(), 1,
                        ModItems.lakeBow1.get()));
        injectingRecipeMap.put(ModItems.lakeBow1.get(),
                new InjectingRecipe(ModItems.LakeRune.get(), 2,
                        ModItems.lakeBow2.get()));
        injectingRecipeMap.put(ModItems.lakeBow2.get(),
                new InjectingRecipe(ModItems.LakeRune.get(), 3,
                        ModItems.lakeBow3.get()));

        injectingRecipeMap.put(ModItems.lakeSceptre0.get(),
                new InjectingRecipe(ModItems.LakeRune.get(), 1,
                        ModItems.lakeSceptre1.get()));
        injectingRecipeMap.put(ModItems.lakeSceptre1.get(),
                new InjectingRecipe(ModItems.LakeRune.get(), 2,
                        ModItems.lakeSceptre2.get()));
        injectingRecipeMap.put(ModItems.lakeSceptre2.get(),
                new InjectingRecipe(ModItems.LakeRune.get(), 3,
                        ModItems.lakeSceptre3.get()));

        injectingRecipeMap.put(ModItems.VolcanoSword0.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(), 1,
                        ModItems.VolcanoSword1.get()));
        injectingRecipeMap.put(ModItems.VolcanoSword1.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(), 2,
                        ModItems.VolcanoSword2.get()));
        injectingRecipeMap.put(ModItems.VolcanoSword2.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(), 3,
                        ModItems.VolcanoSword3.get()));

        injectingRecipeMap.put(ModItems.VolcanoBow0.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(), 1,
                        ModItems.VolcanoBow1.get()));
        injectingRecipeMap.put(ModItems.VolcanoBow1.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(), 2,
                        ModItems.VolcanoBow2.get()));
        injectingRecipeMap.put(ModItems.VolcanoBow2.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(), 3,
                        ModItems.VolcanoBow3.get()));

        injectingRecipeMap.put(ModItems.MineSword0.get(),
                new InjectingRecipe(ModItems.MineRune.get(), 1,
                        ModItems.MineSword1.get()));
        injectingRecipeMap.put(ModItems.MineSword1.get(),
                new InjectingRecipe(ModItems.MineRune.get(), 2,
                        ModItems.MineSword2.get()));
        injectingRecipeMap.put(ModItems.MineSword2.get(),
                new InjectingRecipe(ModItems.MineRune.get(), 3,
                        ModItems.MineSword3.get()));

        injectingRecipeMap.put(ModItems.FieldSword0.get(),
                new InjectingRecipe(ModItems.FieldRune.get(), 1,
                        ModItems.FieldSword1.get()));
        injectingRecipeMap.put(ModItems.FieldSword1.get(),
                new InjectingRecipe(ModItems.FieldRune.get(), 2,
                        ModItems.FieldSword2.get()));
        injectingRecipeMap.put(ModItems.FieldSword2.get(),
                new InjectingRecipe(ModItems.FieldRune.get(), 3,
                        ModItems.FieldSword3.get()));

        injectingRecipeMap.put(ModItems.SnowSword0.get(),
                new InjectingRecipe(ModItems.SnowRune.get(), 1,
                        ModItems.SnowSword1.get()));
        injectingRecipeMap.put(ModItems.SnowSword1.get(),
                new InjectingRecipe(ModItems.SnowRune.get(), 2,
                        ModItems.SnowSword2.get()));
        injectingRecipeMap.put(ModItems.SnowSword2.get(),
                new InjectingRecipe(ModItems.SnowRune.get(), 3,
                        ModItems.SnowSword3.get()));

        injectingRecipeMap.put(ModItems.SeaSword0.get(),
                new InjectingRecipe(ModItems.SeaRune.get(), 1,
                        ModItems.SeaSword1.get()));
        injectingRecipeMap.put(ModItems.SeaSword1.get(),
                new InjectingRecipe(ModItems.SeaRune.get(), 2,
                        ModItems.SeaSword2.get()));
        injectingRecipeMap.put(ModItems.SeaSword2.get(),
                new InjectingRecipe(ModItems.SeaRune.get(), 3,
                        ModItems.SeaSword3.get()));

        injectingRecipeMap.put(ModItems.huskSword0.get(),
                new InjectingRecipe(ModItems.HUSK_RUNE.get(), 1,
                        ModItems.huskSword1.get()));
        injectingRecipeMap.put(ModItems.huskSword1.get(),
                new InjectingRecipe(ModItems.HUSK_RUNE.get(), 2,
                        ModItems.huskSword2.get()));
        injectingRecipeMap.put(ModItems.huskSword2.get(),
                new InjectingRecipe(ModItems.HUSK_RUNE.get(), 3,
                        ModItems.huskSword3.get()));

        injectingRecipeMap.put(ModItems.KazeSword0.get(),
                new InjectingRecipe(ModItems.VOLCANO_CORE.get(), 1,
                        ModItems.KazeSword1.get()));
        injectingRecipeMap.put(ModItems.KazeSword1.get(),
                new InjectingRecipe(ModItems.VOLCANO_CORE.get(), 2,
                        ModItems.KazeSword2.get()));
        injectingRecipeMap.put(ModItems.KazeSword2.get(),
                new InjectingRecipe(ModItems.VOLCANO_CORE.get(), 3,
                        ModItems.KazeSword3.get()));

        injectingRecipeMap.put(ModItems.WitherSword0.get(),
                new InjectingRecipe(ModItems.WITHER_RUNE.get(), 1,
                        ModItems.WitherSword1.get()));
        injectingRecipeMap.put(ModItems.WitherSword1.get(),
                new InjectingRecipe(ModItems.WITHER_RUNE.get(), 2,
                        ModItems.WitherSword2.get()));
        injectingRecipeMap.put(ModItems.WitherSword2.get(),
                new InjectingRecipe(ModItems.WITHER_RUNE.get(), 3,
                        ModItems.WitherSword3.get()));

        injectingRecipeMap.put(ModItems.WitherBow0.get(),
                new InjectingRecipe(ModItems.NetherRune.get(), 1,
                        ModItems.WitherBow1.get()));
        injectingRecipeMap.put(ModItems.WitherBow1.get(),
                new InjectingRecipe(ModItems.NetherRune.get(), 2,
                        ModItems.WitherBow2.get()));
        injectingRecipeMap.put(ModItems.WitherBow2.get(),
                new InjectingRecipe(ModItems.NetherRune.get(), 3,
                        ModItems.WitherBow3.get()));

        injectingRecipeMap.put(ModItems.MagmaSceptre0.get(),
                new InjectingRecipe(ModItems.MagmaRune.get(), 1,
                        ModItems.MagmaSceptre1.get()));
        injectingRecipeMap.put(ModItems.MagmaSceptre1.get(),
                new InjectingRecipe(ModItems.MagmaRune.get(), 2,
                        ModItems.MagmaSceptre2.get()));
        injectingRecipeMap.put(ModItems.MagmaSceptre2.get(),
                new InjectingRecipe(ModItems.MagmaRune.get(), 3,
                        ModItems.MagmaSceptre3.get()));

        injectingRecipeMap.put(ModItems.PiglinHelmet0.get(),
                new InjectingRecipe(ModItems.PIGLIN_RUNE.get(), 1,
                        ModItems.PiglinHelmet1.get()));
        injectingRecipeMap.put(ModItems.PiglinHelmet1.get(),
                new InjectingRecipe(ModItems.PIGLIN_RUNE.get(), 2,
                        ModItems.PiglinHelmet2.get()));
        injectingRecipeMap.put(ModItems.PiglinHelmet2.get(),
                new InjectingRecipe(ModItems.PIGLIN_RUNE.get(), 3,
                        ModItems.PiglinHelmet3.get()));

        injectingRecipeMap.put(ModItems.LightningRune.get(),
                new InjectingRecipe(10, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.LightningPower.get()));

        injectingRecipeMap.put(ModItems.KazeRecallSoul.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.IntensifiedKazeSoul.get()));

        injectingRecipeMap.put(ModItems.SpiderRecallSoul.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.IntensifiedSpiderSoul.get()));

        injectingRecipeMap.put(ModItems.BlackForestRecallSoul.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.IntensifiedBlackForestSoul.get()));


        injectingRecipeMap.put(ModItems.SeaRecallSoul.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.IntensifiedSeaSoul.get()));

        injectingRecipeMap.put(ModItems.LightningRecallSoul.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.IntensifiedLightningSoul.get()));

        injectingRecipeMap.put(ModItems.NetherRecallSoul.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.IntensifiedNetherSoul.get()));

        injectingRecipeMap.put(ModItems.SnowRecallSoul.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.IntensifiedSnowSoul.get()));

        injectingRecipeMap.put(ModItems.LightningRecallSoul.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.IntensifiedLightningSoul.get()));

        injectingRecipeMap.put(ModItems.KazeSoul.get(),
                new InjectingRecipe(64, ModItems.LAKE_CORE.get(), 6,
                        ModItems.KazeRune.get()));

        injectingRecipeMap.put(ModItems.SeaSoul.get(),
                new InjectingRecipe(64, ModItems.SunPower.get(), 6,
                        ModItems.SeaRune.get()));

        injectingRecipeMap.put(ModItems.huskSoul.get(),
                new InjectingRecipe(64, ModItems.SunPower.get(), 6,
                        ModItems.HUSK_RUNE.get()));

        injectingRecipeMap.put(ModItems.LightningSoul.get(),
                new InjectingRecipe(64, PickaxeItems.TINKER_IRON.get(), 1,
                        ModItems.LightningRune.get()));

        injectingRecipeMap.put(ModItems.KazeSword3.get(),
                new InjectingRecipe(ModItems.IntensifiedKazeSoul.get(), 1,
                        ModItems.KazeSword4.get()));

        injectingRecipeMap.put(ModItems.SBoots.get(),
                new InjectingRecipe(ModItems.IntensifiedSpiderSoul.get(), 1,
                        ModItems.ISArmorBoots.get()));

        injectingRecipeMap.put(ModItems.SLeggings.get(),
                new InjectingRecipe(ModItems.IntensifiedSpiderSoul.get(), 1,
                        ModItems.ISArmorLeggings.get()));

        injectingRecipeMap.put(ModItems.SChest.get(),
                new InjectingRecipe(ModItems.IntensifiedSpiderSoul.get(), 1,
                        ModItems.ISArmorChest.get()));

        injectingRecipeMap.put(ModItems.SHelmet.get(),
                new InjectingRecipe(ModItems.IntensifiedSpiderSoul.get(), 1,
                        ModItems.ISArmorHelmet.get()));

        injectingRecipeMap.put(ModItems.huskSword3.get(),
                new InjectingRecipe(ModItems.IntensifiedBlackForestSoul.get(), 1,
                        ModItems.BlackForestSword4.get()));

        injectingRecipeMap.put(ModItems.SeaSword3.get(),
                new InjectingRecipe(ModItems.IntensifiedSeaSoul.get(), 1,
                        ModItems.SeaSword4.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_HELMET.get(),
                new InjectingRecipe(ModItems.IntensifiedLightningSoul.get(), 1,
                        ModItems.ILArmorHelmet.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_CHEST.get(),
                new InjectingRecipe(ModItems.IntensifiedLightningSoul.get(), 1,
                        ModItems.ILArmorChest.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_LEGGINGS.get(),
                new InjectingRecipe(ModItems.IntensifiedLightningSoul.get(), 1,
                        ModItems.ILArmorLeggings.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_BOOTS.get(),
                new InjectingRecipe(ModItems.IntensifiedLightningSoul.get(), 1,
                        ModItems.ILArmorBoots.get()));

        injectingRecipeMap.put(ModItems.ManaSword.get(),
                new InjectingRecipe(ModItems.IntensifiedNetherSoul.get(), 1,
                        ModItems.ManaSword1.get()));

        injectingRecipeMap.put(ModItems.SnowSword3.get(),
                new InjectingRecipe(ModItems.IntensifiedSnowSoul.get(), 1,
                        ModItems.SnowSword4.get()));

        injectingRecipeMap.put(ModItems.ForestSword3.get(),
                new InjectingRecipe(ModItems.IntensifiedForestRecallSoul.get(), 1,
                        ModItems.ForestSword4.get()));

        injectingRecipeMap.put(ModItems.VolcanoSword3.get(),
                new InjectingRecipe(ModItems.IntensifiedVolcanoSoul.get(), 1,
                        ModItems.VolcanoSword4.get()));

        injectingRecipeMap.put(ModItems.MineSword3.get(),
                new InjectingRecipe(ModItems.SnowRune.get(), 1,
                        ModItems.SnowSword0.get()));

        injectingRecipeMap.put(ModItems.LIFE_SCEPTRE_3.get(),
                new InjectingRecipe(ModItems.ForestRune.get(), 2,
                        ModItems.LIFE_SCEPTRE_X.get()));

        injectingRecipeMap.put(ModItems.PlainAttackRing0.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(), 1,
                        ModItems.PlainAttackRing1.get()));
        injectingRecipeMap.put(ModItems.PlainAttackRing1.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(), 1,
                        ModItems.PlainAttackRing2.get()));
        injectingRecipeMap.put(ModItems.PlainAttackRing2.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(), 1,
                        ModItems.PlainAttackRing3.get()));

        injectingRecipeMap.put(ModItems.PlainManaAttackRing0.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(), 1,
                        ModItems.PlainManaAttackRing1.get()));
        injectingRecipeMap.put(ModItems.PlainManaAttackRing1.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(), 1,
                        ModItems.PlainManaAttackRing2.get()));
        injectingRecipeMap.put(ModItems.PlainManaAttackRing2.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(), 1,
                        ModItems.PlainManaAttackRing3.get()));

        injectingRecipeMap.put(ModItems.PlainHealthRing0.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(), 1,
                        ModItems.PlainHealthRing1.get()));
        injectingRecipeMap.put(ModItems.PlainHealthRing1.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(), 1,
                        ModItems.PlainHealthRing2.get()));
        injectingRecipeMap.put(ModItems.PlainHealthRing2.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(), 1,
                        ModItems.PlainHealthRing3.get()));

        injectingRecipeMap.put(ModItems.PlainDefenceRing0.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(), 1,
                        ModItems.PlainDefenceRing1.get()));
        injectingRecipeMap.put(ModItems.PlainDefenceRing1.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(), 1,
                        ModItems.PlainDefenceRing2.get()));
        injectingRecipeMap.put(ModItems.PlainDefenceRing2.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(), 1,
                        ModItems.PlainDefenceRing3.get()));

        injectingRecipeMap.put(ModItems.PlainBossSoul.get(),
                new InjectingRecipe(16, ModItems.COMPLETE_GEM.get(), 1,
                        ModItems.PlainCompleteGem.get()));

        injectingRecipeMap.put(ModItems.IceSoul.get(),
                new InjectingRecipe(16, ModItems.COMPLETE_GEM.get(), 1,
                        ModItems.IceCompleteGem.get()));

        injectingRecipeMap.put(ModItems.VolcanoBow3.get(),
                new InjectingRecipe(ModItems.QuartzRune.get(), 1,
                        ModItems.NetherBow.get()));

        injectingRecipeMap.put(ModItems.MineBow0.get(),
                new InjectingRecipe(ModItems.MineRune.get(), 1,
                        ModItems.MineBow1.get()));
        injectingRecipeMap.put(ModItems.MineBow1.get(),
                new InjectingRecipe(ModItems.MineRune.get(), 2,
                        ModItems.MineBow2.get()));
        injectingRecipeMap.put(ModItems.MineBow2.get(),
                new InjectingRecipe(ModItems.MineRune.get(), 3,
                        ModItems.MineBow3.get()));

        injectingRecipeMap.put(ModItems.LifeManaArmorHelmet.get(),
                new InjectingRecipe(ModItems.NaturalCore.get(), 3,
                        ModItems.LifeManaArmorHelmetE.get()));

        injectingRecipeMap.put(ModItems.LifeManaArmorChest.get(),
                new InjectingRecipe(ModItems.NaturalCore.get(), 3,
                        ModItems.LifeManaArmorChestE.get()));

        injectingRecipeMap.put(ModItems.LifeManaArmorLeggings.get(),
                new InjectingRecipe(ModItems.NaturalCore.get(), 3,
                        ModItems.LifeManaArmorLeggingsE.get()));

        injectingRecipeMap.put(ModItems.LifeManaArmorBoots.get(),
                new InjectingRecipe(ModItems.NaturalCore.get(), 3,
                        ModItems.LifeManaArmorBootsE.get()));

        injectingRecipeMap.put(ModItems.ObsiManaArmorHelmet.get(),
                new InjectingRecipe(ModItems.OreRune.get(), 3,
                        ModItems.ObsiManaArmorHelmetE.get()));

        injectingRecipeMap.put(ModItems.ObsiManaArmorChest.get(),
                new InjectingRecipe(ModItems.OreRune.get(), 3,
                        ModItems.ObsiManaArmorChestE.get()));

        injectingRecipeMap.put(ModItems.ObsiManaArmorLeggings.get(),
                new InjectingRecipe(ModItems.OreRune.get(), 3,
                        ModItems.ObsiManaArmorLeggingsE.get()));

        injectingRecipeMap.put(ModItems.ObsiManaArmorBoots.get(),
                new InjectingRecipe(ModItems.OreRune.get(), 3,
                        ModItems.ObsiManaArmorBootsE.get()));

        injectingRecipeMap.put(ModItems.EvokerBook3.get(),
                new InjectingRecipe(ModItems.IceHeart.get(), 2,
                        ModItems.IceBook.get()));

        injectingRecipeMap.put(SpecialEventItems.RING.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 3,
                        SpecialEventItems.RING_1.get()));
        injectingRecipeMap.put(SpecialEventItems.RING_1.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 6,
                        SpecialEventItems.RING_2.get()));
        injectingRecipeMap.put(SpecialEventItems.RING_2.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 12,
                        SpecialEventItems.RING_3.get()));
        injectingRecipeMap.put(SpecialEventItems.RING_3.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 18,
                        SpecialEventItems.RING_4.get()));

        injectingRecipeMap.put(SpecialEventItems.HAND.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 3,
                        SpecialEventItems.HAND_1.get()));
        injectingRecipeMap.put(SpecialEventItems.HAND_1.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 6,
                        SpecialEventItems.HAND_2.get()));
        injectingRecipeMap.put(SpecialEventItems.HAND_2.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 12,
                        SpecialEventItems.HAND_3.get()));
        injectingRecipeMap.put(SpecialEventItems.HAND_3.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 18,
                        SpecialEventItems.HAND_4.get()));

        injectingRecipeMap.put(SpecialEventItems.NECKLACE.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 3,
                        SpecialEventItems.NECKLACE_1.get()));
        injectingRecipeMap.put(SpecialEventItems.NECKLACE_1.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 6,
                        SpecialEventItems.NECKLACE_2.get()));
        injectingRecipeMap.put(SpecialEventItems.NECKLACE_2.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 12,
                        SpecialEventItems.NECKLACE_3.get()));
        injectingRecipeMap.put(SpecialEventItems.NECKLACE_3.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 18,
                        SpecialEventItems.NECKLACE_4.get()));

        injectingRecipeMap.put(SpecialEventItems.BELT.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 3,
                        SpecialEventItems.BELT_1.get()));
        injectingRecipeMap.put(SpecialEventItems.BELT_1.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 6,
                        SpecialEventItems.BELT_2.get()));
        injectingRecipeMap.put(SpecialEventItems.BELT_2.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 12,
                        SpecialEventItems.BELT_3.get()));
        injectingRecipeMap.put(SpecialEventItems.BELT_3.get(),
                new InjectingRecipe(SpecialEventItems.SPRING_GOLD_INGOT.get(), 18,
                        SpecialEventItems.BELT_4.get()));

        injectingRecipeMap.put(SpecialEventItems.SPRING_SOUL.get(),
                new InjectingRecipe(24, SpecialEventItems.SPRING_GOLD_COIN.get(), 6,
                        SpecialEventItems.SpringHeart.get()));

        injectingRecipeMap.put(ModItems.BloodManaCurios.get(),
                new InjectingRecipe(ModItems.DevilBlood.get(), 6,
                        ModItems.DevilBloodManaCurios.get()));

        injectingRecipeMap.put(ModItems.EarthManaCurios.get(),
                new InjectingRecipe(ModItems.DevilBlood.get(), 6,
                        ModItems.DevilEarthManaCurios.get()));

        injectingRecipeMap.put(ModItems.MoonSoul.get(),
                new InjectingRecipe(16, ModItems.COMPLETE_GEM.get(), 2,
                        ModItems.MoonCompleteGem.get()));

        injectingRecipeMap.put(ModItems.ICE_SWORD_E.get(),
                new InjectingRecipe(ModItems.DevilBlood.get(), 8,
                        ModItems.DevilSword.get()));

        injectingRecipeMap.put(ModItems.ICE_BOW_E.get(),
                new InjectingRecipe(ModItems.DevilBlood.get(), 8,
                        ModItems.DevilBow.get()));

        injectingRecipeMap.put(ModItems.ICE_SCEPTRE_E.get(),
                new InjectingRecipe(ModItems.DevilBlood.get(), 8,
                        ModItems.DevilSceptre.get()));

        injectingRecipeMap.put(GemItems.skyGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.skyGemD.get()));

        injectingRecipeMap.put(GemItems.evokerGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.evokerGemD.get()));

        injectingRecipeMap.put(GemItems.plainGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.plainGemD.get()));

        injectingRecipeMap.put(GemItems.forestGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.forestGemD.get()));

        injectingRecipeMap.put(GemItems.lakeGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.lakeGemD.get()));

        injectingRecipeMap.put(GemItems.volcanoGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.volcanoGemD.get()));

        injectingRecipeMap.put(GemItems.snowGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.snowGemD.get()));

        injectingRecipeMap.put(GemItems.fieldGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.fieldGemD.get()));

        injectingRecipeMap.put(GemItems.mineGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.mineGemD.get()));

        injectingRecipeMap.put(GemItems.lifeManaGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.lifeManaGemD.get()));

        injectingRecipeMap.put(GemItems.obsiManaGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.obsiManaGemD.get()));

        injectingRecipeMap.put(GemItems.netherSkeletonGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.netherSkeletonGemD.get()));

        injectingRecipeMap.put(GemItems.magmaGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.magmaGemD.get()));

        injectingRecipeMap.put(GemItems.witherGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.witherGemD.get()));

        injectingRecipeMap.put(GemItems.piglinGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.piglinGemD.get()));

        injectingRecipeMap.put(GemItems.sakuraGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.sakuraGemD.get()));

        injectingRecipeMap.put(GemItems.shipGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.shipGemD.get()));

        injectingRecipeMap.put(GemItems.moonAttackGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.moonAttackGemD.get()));

        injectingRecipeMap.put(GemItems.moonManaGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        GemItems.moonManaGemD.get()));

        injectingRecipeMap.put(ModItems.BeaconBow.get(),
                new InjectingRecipe(ModItems.BeaconRune.get(), 4,
                        ModItems.BeaconBow1.get()));

        injectingRecipeMap.put(ModItems.BeaconBow1.get(),
                new InjectingRecipe(ModItems.BeaconRune.get(), 6,
                        ModItems.BeaconBow2.get()));

        injectingRecipeMap.put(ModItems.BeaconBow2.get(),
                new InjectingRecipe(ModItems.BeaconRune.get(), 8,
                        ModItems.BeaconBow3.get()));

        injectingRecipeMap.put(ModItems.BlazeSword.get(),
                new InjectingRecipe(ModItems.BlazeRune.get(), 4,
                        ModItems.BlazeSword1.get()));

        injectingRecipeMap.put(ModItems.BlazeSword1.get(),
                new InjectingRecipe(ModItems.BlazeRune.get(), 6,
                        ModItems.BlazeSword2.get()));

        injectingRecipeMap.put(ModItems.BlazeSword2.get(),
                new InjectingRecipe(ModItems.BlazeRune.get(), 8,
                        ModItems.BlazeSword3.get()));

        injectingRecipeMap.put(ModItems.TreeSceptre.get(),
                new InjectingRecipe(ModItems.TreeRune.get(), 4,
                        ModItems.TreeSceptre1.get()));

        injectingRecipeMap.put(ModItems.TreeSceptre1.get(),
                new InjectingRecipe(ModItems.TreeRune.get(), 6,
                        ModItems.TreeSceptre2.get()));

        injectingRecipeMap.put(ModItems.TreeSceptre2.get(),
                new InjectingRecipe(ModItems.TreeRune.get(), 8,
                        ModItems.TreeSceptre3.get()));

        injectingRecipeMap.put(ModItems.CastleNecklace.get(),
                new InjectingRecipe(ModItems.CastleNecklace.get(), 1,
                        ModItems.CastleNecklace.get()));

        injectingRecipeMap.put(ModItems.RubyNecklace.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(), 8,
                        ModItems.RubyNecklace1.get()));

        injectingRecipeMap.put(ModItems.RubyNecklace1.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(), 16,
                        ModItems.RubyNecklace2.get()));

        injectingRecipeMap.put(ModItems.RubyNecklace2.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(), 24,
                        ModItems.RubyNecklace3.get()));

        injectingRecipeMap.put(ModItems.SapphireNecklace.get(),
                new InjectingRecipe(ModItems.LakeRune.get(), 8,
                        ModItems.SapphireNecklace1.get()));

        injectingRecipeMap.put(ModItems.SapphireNecklace1.get(),
                new InjectingRecipe(ModItems.LakeRune.get(), 16,
                        ModItems.SapphireNecklace2.get()));

        injectingRecipeMap.put(ModItems.SapphireNecklace2.get(),
                new InjectingRecipe(ModItems.LakeRune.get(), 24,
                        ModItems.SapphireNecklace3.get()));

        injectingRecipeMap.put(ModItems.FancySapphireNecklace.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 1,
                        ModItems.FancySapphireNecklace1.get()));

        injectingRecipeMap.put(ModItems.FancySapphireNecklace1.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 2,
                        ModItems.FancySapphireNecklace2.get()));

        injectingRecipeMap.put(ModItems.FancySapphireNecklace2.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(), 3,
                        ModItems.FancySapphireNecklace3.get()));

        injectingRecipeMap.put(ModItems.PurpleIronBud1.get(),
                new InjectingRecipe(16, ModItems.PurpleIron.get(), 4,
                        ModItems.PurpleIronBud2.get()));

        injectingRecipeMap.put(ModItems.PurpleIronBud2.get(),
                new InjectingRecipe(16, ModItems.PurpleIron.get(), 16,
                        ModItems.PurpleIronBud3.get()));

        injectingRecipeMap.put(ModItems.PurpleIronSword.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(), 1,
                        ModItems.PurpleIronSword1.get()));

        injectingRecipeMap.put(ModItems.PurpleIronSword1.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(), 1,
                        ModItems.PurpleIronSword2.get()));

        injectingRecipeMap.put(ModItems.PurpleIronSword2.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(), 1,
                        ModItems.PurpleIronSword3.get()));

        injectingRecipeMap.put(ModItems.PurpleIronBow.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(), 1,
                        ModItems.PurpleIronBow1.get()));

        injectingRecipeMap.put(ModItems.PurpleIronBow1.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(), 1,
                        ModItems.PurpleIronBow2.get()));

        injectingRecipeMap.put(ModItems.PurpleIronBow2.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(), 1,
                        ModItems.PurpleIronBow3.get()));

        injectingRecipeMap.put(ModItems.PurpleIronSceptre.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(), 1,
                        ModItems.PurpleIronSceptre1.get()));

        injectingRecipeMap.put(ModItems.PurpleIronSceptre1.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(), 1,
                        ModItems.PurpleIronSceptre2.get()));

        injectingRecipeMap.put(ModItems.PurpleIronSceptre2.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(), 1,
                        ModItems.PurpleIronSceptre3.get()));

        injectingRecipeMap.put(ModItems.StarRune.get(),
                new InjectingRecipe(8, ModItems.StarSoul.get(), 64,
                        ModItems.StarStar.get()));

        injectingRecipeMap.put(ModItems.MoonHelmet.get(),
                new InjectingRecipe(ModItems.StarStar.get(), 32,
                        ModItems.StarHelmet.get()));

        injectingRecipeMap.put(ModItems.MoonLeggings.get(),
                new InjectingRecipe(ModItems.StarStar.get(), 32,
                        ModItems.StarLeggings.get()));

        injectingRecipeMap.put(ModItems.LifeCrystal0.get(),
                new InjectingRecipe(ModItems.LifeElementPiece2.get(), 1,
                        ModItems.LifeCrystal1.get()));

        injectingRecipeMap.put(ModItems.LifeCrystal1.get(),
                new InjectingRecipe(ModItems.LifeElementPiece2.get(), 2,
                        ModItems.LifeCrystal2.get()));

        injectingRecipeMap.put(ModItems.LifeCrystal2.get(),
                new InjectingRecipe(ModItems.LifeElementPiece2.get(), 3,
                        ModItems.LifeCrystal3.get()));

        injectingRecipeMap.put(ModItems.WaterCrystal0.get(),
                new InjectingRecipe(ModItems.WaterElementPiece2.get(), 1,
                        ModItems.WaterCrystal1.get()));

        injectingRecipeMap.put(ModItems.WaterCrystal1.get(),
                new InjectingRecipe(ModItems.WaterElementPiece2.get(), 2,
                        ModItems.WaterCrystal2.get()));

        injectingRecipeMap.put(ModItems.WaterCrystal2.get(),
                new InjectingRecipe(ModItems.WaterElementPiece2.get(), 3,
                        ModItems.WaterCrystal3.get()));

        injectingRecipeMap.put(ModItems.FireCrystal0.get(),
                new InjectingRecipe(ModItems.FireElementPiece2.get(), 1,
                        ModItems.FireCrystal1.get()));

        injectingRecipeMap.put(ModItems.FireCrystal1.get(),
                new InjectingRecipe(ModItems.FireElementPiece2.get(), 2,
                        ModItems.FireCrystal2.get()));

        injectingRecipeMap.put(ModItems.FireCrystal2.get(),
                new InjectingRecipe(ModItems.FireElementPiece2.get(), 3,
                        ModItems.FireCrystal3.get()));

        injectingRecipeMap.put(ModItems.IceCrystal0.get(),
                new InjectingRecipe(ModItems.IceElementPiece2.get(), 1,
                        ModItems.IceCrystal1.get()));

        injectingRecipeMap.put(ModItems.IceCrystal1.get(),
                new InjectingRecipe(ModItems.IceElementPiece2.get(), 2,
                        ModItems.IceCrystal2.get()));

        injectingRecipeMap.put(ModItems.IceCrystal2.get(),
                new InjectingRecipe(ModItems.IceElementPiece2.get(), 3,
                        ModItems.IceCrystal3.get()));

        injectingRecipeMap.put(ModItems.WindCrystal0.get(),
                new InjectingRecipe(ModItems.WindElementPiece2.get(), 1,
                        ModItems.WindCrystal1.get()));

        injectingRecipeMap.put(ModItems.WindCrystal1.get(),
                new InjectingRecipe(ModItems.WindElementPiece2.get(), 2,
                        ModItems.WindCrystal2.get()));

        injectingRecipeMap.put(ModItems.WindCrystal2.get(),
                new InjectingRecipe(ModItems.WindElementPiece2.get(), 3,
                        ModItems.WindCrystal3.get()));

        injectingRecipeMap.put(ModItems.StoneCrystal0.get(),
                new InjectingRecipe(ModItems.StoneElementPiece2.get(), 1,
                        ModItems.StoneCrystal1.get()));

        injectingRecipeMap.put(ModItems.StoneCrystal1.get(),
                new InjectingRecipe(ModItems.StoneElementPiece2.get(), 2,
                        ModItems.StoneCrystal2.get()));

        injectingRecipeMap.put(ModItems.StoneCrystal2.get(),
                new InjectingRecipe(ModItems.StoneElementPiece2.get(), 3,
                        ModItems.StoneCrystal3.get()));

        injectingRecipeMap.put(ModItems.LightningCrystal0.get(),
                new InjectingRecipe(ModItems.LightningElementPiece2.get(), 1,
                        ModItems.LightningCrystal1.get()));

        injectingRecipeMap.put(ModItems.LightningCrystal1.get(),
                new InjectingRecipe(ModItems.LightningElementPiece2.get(), 2,
                        ModItems.LightningCrystal2.get()));

        injectingRecipeMap.put(ModItems.LightningCrystal2.get(),
                new InjectingRecipe(ModItems.LightningElementPiece2.get(), 3,
                        ModItems.LightningCrystal3.get()));

        injectingRecipeMap.put(ModItems.EvokerSword.get(),
                new InjectingRecipe(ModItems.EvokerRune.get(), 4,
                        ModItems.EvokerSword1.get()));

        injectingRecipeMap.put(ModItems.EvokerSword1.get(),
                new InjectingRecipe(ModItems.EvokerRune.get(), 6,
                        ModItems.EvokerSword2.get()));

        injectingRecipeMap.put(ModItems.EvokerSword2.get(),
                new InjectingRecipe(ModItems.EvokerRune.get(), 8,
                        ModItems.EvokerSword3.get()));

        injectingRecipeMap.put(PickaxeItems.STONE_PICKAXE_0.get(),
                new InjectingRecipe(PickaxeItems.TINKER_STONE.get(), 1, PickaxeItems.STONE_PICKAXE_1.get()));
        injectingRecipeMap.put(PickaxeItems.STONE_PICKAXE_1.get(),
                new InjectingRecipe(PickaxeItems.TINKER_STONE.get(), 1, PickaxeItems.STONE_PICKAXE_2.get()));
        injectingRecipeMap.put(PickaxeItems.STONE_PICKAXE_2.get(),
                new InjectingRecipe(PickaxeItems.TINKER_STONE.get(), 1, PickaxeItems.STONE_PICKAXE_3.get()));
        injectingRecipeMap.put(PickaxeItems.STONE_PICKAXE_3.get(),
                new InjectingRecipe(PickaxeItems.TINKER_STONE.get(), 1, PickaxeItems.STONE_PICKAXE_4.get()));
        injectingRecipeMap.put(PickaxeItems.STONE_PICKAXE_4.get(),
                new InjectingRecipe(PickaxeItems.TINKER_IRON.get(), 1, PickaxeItems.IRON_PICKAXE_0.get()));

        injectingRecipeMap.put(PickaxeItems.IRON_PICKAXE_0.get(),
                new InjectingRecipe(PickaxeItems.TINKER_IRON.get(), 1, PickaxeItems.IRON_PICKAXE_1.get()));
        injectingRecipeMap.put(PickaxeItems.IRON_PICKAXE_1.get(),
                new InjectingRecipe(PickaxeItems.TINKER_IRON.get(), 1, PickaxeItems.IRON_PICKAXE_2.get()));
        injectingRecipeMap.put(PickaxeItems.IRON_PICKAXE_2.get(),
                new InjectingRecipe(PickaxeItems.TINKER_IRON.get(), 1, PickaxeItems.IRON_PICKAXE_3.get()));
        injectingRecipeMap.put(PickaxeItems.IRON_PICKAXE_3.get(),
                new InjectingRecipe(PickaxeItems.TINKER_IRON.get(), 1, PickaxeItems.IRON_PICKAXE_4.get()));
        injectingRecipeMap.put(PickaxeItems.IRON_PICKAXE_4.get(),
                new InjectingRecipe(PickaxeItems.TINKER_GOLD.get(), 1, PickaxeItems.GOLD_PICKAXE_0.get()));

        injectingRecipeMap.put(PickaxeItems.GOLD_PICKAXE_0.get(),
                new InjectingRecipe(PickaxeItems.TINKER_GOLD.get(), 1, PickaxeItems.GOLD_PICKAXE_1.get()));
        injectingRecipeMap.put(PickaxeItems.GOLD_PICKAXE_1.get(),
                new InjectingRecipe(PickaxeItems.TINKER_GOLD.get(), 1, PickaxeItems.GOLD_PICKAXE_2.get()));
        injectingRecipeMap.put(PickaxeItems.GOLD_PICKAXE_2.get(),
                new InjectingRecipe(PickaxeItems.TINKER_GOLD.get(), 1, PickaxeItems.GOLD_PICKAXE_3.get()));
        injectingRecipeMap.put(PickaxeItems.GOLD_PICKAXE_3.get(),
                new InjectingRecipe(PickaxeItems.TINKER_GOLD.get(), 1, PickaxeItems.GOLD_PICKAXE_4.get()));
        injectingRecipeMap.put(PickaxeItems.GOLD_PICKAXE_4.get(),
                new InjectingRecipe(PickaxeItems.TINKER_DIAMOND.get(), 1, PickaxeItems.DIAMOND_PICKAXE_0.get()));

        injectingRecipeMap.put(PickaxeItems.DIAMOND_PICKAXE_0.get(),
                new InjectingRecipe(PickaxeItems.TINKER_DIAMOND.get(), 1, PickaxeItems.DIAMOND_PICKAXE_1.get()));
        injectingRecipeMap.put(PickaxeItems.DIAMOND_PICKAXE_1.get(),
                new InjectingRecipe(PickaxeItems.TINKER_DIAMOND.get(), 1, PickaxeItems.DIAMOND_PICKAXE_2.get()));
        injectingRecipeMap.put(PickaxeItems.DIAMOND_PICKAXE_2.get(),
                new InjectingRecipe(PickaxeItems.TINKER_DIAMOND.get(), 1, PickaxeItems.DIAMOND_PICKAXE_3.get()));
        injectingRecipeMap.put(PickaxeItems.DIAMOND_PICKAXE_3.get(),
                new InjectingRecipe(PickaxeItems.TINKER_DIAMOND.get(), 1, PickaxeItems.DIAMOND_PICKAXE_4.get()));
        injectingRecipeMap.put(PickaxeItems.DIAMOND_PICKAXE_4.get(),
                new InjectingRecipe(PickaxeItems.TINKER_NETHERITE.get(), 1, PickaxeItems.NETHERITE_PICKAXE_0.get()));

        injectingRecipeMap.put(PickaxeItems.NETHERITE_PICKAXE_0.get(),
                new InjectingRecipe(PickaxeItems.TINKER_NETHERITE.get(), 1, PickaxeItems.NETHERITE_PICKAXE_1.get()));
        injectingRecipeMap.put(PickaxeItems.NETHERITE_PICKAXE_1.get(),
                new InjectingRecipe(PickaxeItems.TINKER_NETHERITE.get(), 1, PickaxeItems.NETHERITE_PICKAXE_2.get()));
        injectingRecipeMap.put(PickaxeItems.NETHERITE_PICKAXE_2.get(),
                new InjectingRecipe(PickaxeItems.TINKER_NETHERITE.get(), 1, PickaxeItems.NETHERITE_PICKAXE_3.get()));
        injectingRecipeMap.put(PickaxeItems.NETHERITE_PICKAXE_3.get(),
                new InjectingRecipe(PickaxeItems.TINKER_NETHERITE.get(), 1, PickaxeItems.NETHERITE_PICKAXE_4.get()));

        injectingRecipeMap.put(ModItems.BAMBOO_KANATA.get(),
                new InjectingRecipe(ModItems.SkyRune.get(), 4, ModItems.SKY_KANATA.get()));
        injectingRecipeMap.put(ModItems.SKY_KANATA.get(),
                new InjectingRecipe(ModItems.NetherRune.get(), 2, ModItems.NETHER_KANATA.get()));
        injectingRecipeMap.put(ModItems.NETHER_KANATA.get(),
                new InjectingRecipe(ModItems.GOLDEN_SHEET.get(), 1, ModItems.SAKURA_KANATA.get()));

        injectingRecipeMap.put(CitadelItems.CITADEL_CURIO_0.get(),
                new InjectingRecipe(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 1, CitadelItems.CITADEL_CURIO_1.get()));
        injectingRecipeMap.put(CitadelItems.CITADEL_CURIO_1.get(),
                new InjectingRecipe(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 2, CitadelItems.CITADEL_CURIO_2.get()));
        injectingRecipeMap.put(CitadelItems.CITADEL_CURIO_2.get(),
                new InjectingRecipe(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 4, CitadelItems.CITADEL_CURIO_3.get()));

        injectingRecipeMap.put(ModItems.IceArmorHelmet.get(),
                new InjectingRecipe(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 8, CitadelItems.CITADEL_HELMET.get()));
        injectingRecipeMap.put(ModItems.IceArmorChest.get(),
                new InjectingRecipe(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 8, CitadelItems.CITADEL_CHEST.get()));
        injectingRecipeMap.put(ModItems.IceArmorLeggings.get(),
                new InjectingRecipe(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 8, CitadelItems.CITADEL_LEGGINGS.get()));
        injectingRecipeMap.put(ModItems.IceArmorBoots.get(),
                new InjectingRecipe(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 8, CitadelItems.CITADEL_BOOTS.get()));

        injectingRecipeMap.put(CitadelItems.CITADEL_PIECE.get(),
                new InjectingRecipe(16, ModItems.COMPLETE_GEM.get(), 1, CitadelItems.CITADEL_EQUIP_ENHANCER.get()));

        injectingRecipeMap.put(GemItems.ANCIENT_DARKNESS_GEM_0.get(),
                new InjectingRecipe(WardenItems.WARDEN_HEART.get(), 1, GemItems.ANCIENT_DARKNESS_GEM_1.get()));
        injectingRecipeMap.put(GemItems.ANCIENT_DARKNESS_GEM_1.get(),
                new InjectingRecipe(WardenItems.WARDEN_HEART.get(), 2, GemItems.ANCIENT_DARKNESS_GEM_2.get()));
        injectingRecipeMap.put(GemItems.ANCIENT_DARKNESS_GEM_2.get(),
                new InjectingRecipe(WardenItems.WARDEN_HEART.get(), 3, GemItems.ANCIENT_DARKNESS_GEM_3.get()));

        injectingRecipeMap.put(GemItems.ANCIENT_SILENT_GEM_0.get(),
                new InjectingRecipe(WardenItems.WARDEN_HEART.get(), 1, GemItems.ANCIENT_SILENT_GEM_1.get()));
        injectingRecipeMap.put(GemItems.ANCIENT_SILENT_GEM_1.get(),
                new InjectingRecipe(WardenItems.WARDEN_HEART.get(), 2, GemItems.ANCIENT_SILENT_GEM_2.get()));
        injectingRecipeMap.put(GemItems.ANCIENT_SILENT_GEM_2.get(),
                new InjectingRecipe(WardenItems.WARDEN_HEART.get(), 3, GemItems.ANCIENT_SILENT_GEM_3.get()));

        injectingRecipeMap.put(GemItems.ANCIENT_ECHO_GEM_0.get(),
                new InjectingRecipe(WardenItems.WARDEN_HEART.get(), 1, GemItems.ANCIENT_ECHO_GEM_1.get()));
        injectingRecipeMap.put(GemItems.ANCIENT_ECHO_GEM_1.get(),
                new InjectingRecipe(WardenItems.WARDEN_HEART.get(), 2, GemItems.ANCIENT_ECHO_GEM_2.get()));
        injectingRecipeMap.put(GemItems.ANCIENT_ECHO_GEM_2.get(),
                new InjectingRecipe(WardenItems.WARDEN_HEART.get(), 3, GemItems.ANCIENT_ECHO_GEM_3.get()));

        injectingRecipeMap.put(SpecialEventItems.SCALE_2025_0.get(),
                new InjectingRecipe(SpecialEventItems.SCALE_PIECE.get(), 2, SpecialEventItems.SCALE_2025_1.get()));
        injectingRecipeMap.put(SpecialEventItems.SCALE_2025_1.get(),
                new InjectingRecipe(SpecialEventItems.SCALE_PIECE.get(), 4, SpecialEventItems.SCALE_2025_2.get()));
        injectingRecipeMap.put(SpecialEventItems.SCALE_2025_2.get(),
                new InjectingRecipe(SpecialEventItems.SCALE_PIECE.get(), 8, SpecialEventItems.SCALE_2025_3.get()));

        injectingRecipeMap.put(Items.GOLD_INGOT,
                new InjectingRecipe(4, Items.NETHERITE_SCRAP, 4, Items.NETHERITE_INGOT));

        injectingRecipeMap.put(WardenItems.WARDEN_SOUL_INGOT.get(),
                new InjectingRecipe(64, ModItems.REFINED_PIECE.get(), 16, WardenItems.WARDEN_HEART.get()));

        injectingRecipeMap.put(ModItems.BlazeSword3.get(),
                new InjectingRecipe(DivineIslandItems.GHASTLY_INGOT.get(), 64,
                        DivineIslandItems.GHASTLY_SWORD.get()));
        injectingRecipeMap.put(ModItems.BeaconBow3.get(),
                new InjectingRecipe(DivineIslandItems.GHASTLY_INGOT.get(), 64,
                        DivineIslandItems.GHASTLY_BOW.get()));
        injectingRecipeMap.put(ModItems.TreeSceptre3.get(),
                new InjectingRecipe(DivineIslandItems.GHASTLY_INGOT.get(), 64,
                        DivineIslandItems.GHASTLY_SCEPTRE.get()));

        injectingRecipeMap.put(CookingItems.FOOD_MEDAL_0.get(),
                new InjectingRecipe(CookingItems.FOOD_BIG_COIN.get(), 20, CookingItems.FOOD_MEDAL_1.get()));
        injectingRecipeMap.put(CookingItems.FOOD_MEDAL_1.get(),
                new InjectingRecipe(CookingItems.FOOD_BIG_COIN.get(), 28, CookingItems.FOOD_MEDAL_2.get()));
        injectingRecipeMap.put(CookingItems.FOOD_MEDAL_2.get(),
                new InjectingRecipe(CookingItems.FOOD_BIG_COIN.get(), 40, CookingItems.FOOD_MEDAL_3.get()));

        injectingRecipeMap.put(GemItems.MUSHROOM_SPLIT_GEM.get(),
                new InjectingRecipe(CookingItems.FOOD_BIG_COIN.get(), 40, CookingItems.MUSHROOM_SPLIT_GEM_1.get()));
        injectingRecipeMap.put(GemItems.MUSHROOM_PARASITISM_GEM.get(),
                new InjectingRecipe(CookingItems.FOOD_BIG_COIN.get(), 40, CookingItems.MUSHROOM_PARASITISM_GEM_1.get()));
        injectingRecipeMap.put(GemItems.MUSHROOM_SPUTTER_GEM.get(),
                new InjectingRecipe(CookingItems.FOOD_BIG_COIN.get(), 40, CookingItems.MUSHROOM_SPUTTER_GEM_1.get()));

        for (Map.Entry<Item, InjectingRecipe> itemInjectingRecipeEntry : injectingRecipeMap.entrySet()) {
            Item material = itemInjectingRecipeEntry.getValue().material;
            if (!injectingWaysMap.containsKey(material)) {
                injectingWaysMap.put(material, new ArrayList<>());
            }
            injectingWaysMap.get(material).add(itemInjectingRecipeEntry.getValue());
            productSourceItemMap.put(itemInjectingRecipeEntry.getValue().getProduct(),
                    itemInjectingRecipeEntry.getKey());
        }
    }
}