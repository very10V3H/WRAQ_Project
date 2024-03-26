package com.Very.very.Blocks.Blocks;

import com.Very.very.ValueAndTools.Utils.Struct.InjectingRecipe;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class InjectRecipe {

    public static Map<Item, InjectingRecipe> injectingRecipeMap = new HashMap<>();

    public static void setInjectingRecipeMap() {
        injectingRecipeMap.put(ModItems.PlainSword0.get(),
                new InjectingRecipe(ModItems.PlainRune.get(),2,
                        ModItems.PlainSword1.get()));
        injectingRecipeMap.put(ModItems.PlainSword1.get(),
                new InjectingRecipe(ModItems.PlainRune.get(),3,
                        ModItems.PlainSword2.get()));
        injectingRecipeMap.put(ModItems.PlainSword2.get(),
                new InjectingRecipe(ModItems.PlainRune.get(),5,
                        ModItems.PlainSword3.get()));

        injectingRecipeMap.put(ModItems.PlainBow0.get(),
                new InjectingRecipe(ModItems.PlainRune.get(),2,
                        ModItems.PlainBow1.get()));
        injectingRecipeMap.put(ModItems.PlainBow1.get(),
                new InjectingRecipe(ModItems.PlainRune.get(),3,
                        ModItems.PlainBow2.get()));
        injectingRecipeMap.put(ModItems.PlainBow2.get(),
                new InjectingRecipe(ModItems.PlainRune.get(),5,
                        ModItems.PlainBow3.get()));

        injectingRecipeMap.put(ModItems.PlainSceptre0.get(),
                new InjectingRecipe(ModItems.PlainRune.get(),2,
                        ModItems.PlainSceptre1.get()));
        injectingRecipeMap.put(ModItems.PlainSceptre1.get(),
                new InjectingRecipe(ModItems.PlainRune.get(),3,
                        ModItems.PlainSceptre2.get()));
        injectingRecipeMap.put(ModItems.PlainSceptre2.get(),
                new InjectingRecipe(ModItems.PlainRune.get(),5,
                        ModItems.PlainSceptre3.get()));

        injectingRecipeMap.put(ModItems.ForestSword0.get(),
                new InjectingRecipe(ModItems.ForestRune.get(),2,
                        ModItems.ForestSword1.get()));
        injectingRecipeMap.put(ModItems.ForestSword1.get(),
                new InjectingRecipe(ModItems.ForestRune.get(),3,
                        ModItems.ForestSword2.get()));
        injectingRecipeMap.put(ModItems.ForestSword2.get(),
                new InjectingRecipe(ModItems.ForestRune.get(),5,
                        ModItems.ForestSword3.get()));

        injectingRecipeMap.put(ModItems.ForestBow0.get(),
                new InjectingRecipe(ModItems.ForestRune.get(),2,
                        ModItems.ForestBow1.get()));
        injectingRecipeMap.put(ModItems.ForestBow1.get(),
                new InjectingRecipe(ModItems.ForestRune.get(),3,
                        ModItems.ForestBow2.get()));
        injectingRecipeMap.put(ModItems.ForestBow2.get(),
                new InjectingRecipe(ModItems.ForestRune.get(),5,
                        ModItems.ForestBow3.get()));

        injectingRecipeMap.put(ModItems.LakeSword0.get(),
                new InjectingRecipe(ModItems.LakeRune.get(),2,
                        ModItems.LakeSword1.get()));
        injectingRecipeMap.put(ModItems.LakeSword1.get(),
                new InjectingRecipe(ModItems.LakeRune.get(),3,
                        ModItems.LakeSword2.get()));
        injectingRecipeMap.put(ModItems.LakeSword2.get(),
                new InjectingRecipe(ModItems.LakeRune.get(),5,
                        ModItems.LakeSword3.get()));

        injectingRecipeMap.put(ModItems.VolcanoSword0.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(),2,
                        ModItems.VolcanoSword1.get()));
        injectingRecipeMap.put(ModItems.VolcanoSword1.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(),3,
                        ModItems.VolcanoSword2.get()));
        injectingRecipeMap.put(ModItems.VolcanoSword2.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(),5,
                        ModItems.VolcanoSword3.get()));

        injectingRecipeMap.put(ModItems.VolcanoBow0.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(),2,
                        ModItems.VolcanoBow1.get()));
        injectingRecipeMap.put(ModItems.VolcanoBow1.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(),3,
                        ModItems.VolcanoBow2.get()));
        injectingRecipeMap.put(ModItems.VolcanoBow2.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(),5,
                        ModItems.VolcanoBow3.get()));

        injectingRecipeMap.put(ModItems.MineSword0.get(),
                new InjectingRecipe(ModItems.MineRune.get(),2,
                        ModItems.MineSword1.get()));
        injectingRecipeMap.put(ModItems.MineSword1.get(),
                new InjectingRecipe(ModItems.MineRune.get(),3,
                        ModItems.MineSword2.get()));
        injectingRecipeMap.put(ModItems.MineSword2.get(),
                new InjectingRecipe(ModItems.MineRune.get(),5,
                        ModItems.MineSword3.get()));

        injectingRecipeMap.put(ModItems.FieldSword0.get(),
                new InjectingRecipe(ModItems.FieldRune.get(),2,
                        ModItems.FieldSword1.get()));
        injectingRecipeMap.put(ModItems.FieldSword1.get(),
                new InjectingRecipe(ModItems.FieldRune.get(),3,
                        ModItems.FieldSword2.get()));
        injectingRecipeMap.put(ModItems.FieldSword2.get(),
                new InjectingRecipe(ModItems.FieldRune.get(),5,
                        ModItems.FieldSword3.get()));

        injectingRecipeMap.put(ModItems.SnowSword0.get(),
                new InjectingRecipe(ModItems.SnowRune.get(),2,
                        ModItems.SnowSword1.get()));
        injectingRecipeMap.put(ModItems.SnowSword1.get(),
                new InjectingRecipe(ModItems.SnowRune.get(),3,
                        ModItems.SnowSword2.get()));
        injectingRecipeMap.put(ModItems.SnowSword2.get(),
                new InjectingRecipe(ModItems.SnowRune.get(),5,
                        ModItems.SnowSword3.get()));

        injectingRecipeMap.put(ModItems.SeaSword0.get(),
                new InjectingRecipe(ModItems.SeaRune.get(),2,
                        ModItems.SeaSword1.get()));
        injectingRecipeMap.put(ModItems.SeaSword1.get(),
                new InjectingRecipe(ModItems.SeaRune.get(),3,
                        ModItems.SeaSword2.get()));
        injectingRecipeMap.put(ModItems.SeaSword2.get(),
                new InjectingRecipe(ModItems.SeaRune.get(),5,
                        ModItems.SeaSword3.get()));

        injectingRecipeMap.put(ModItems.BlackForestSword0.get(),
                new InjectingRecipe(ModItems.BlackForestRune.get(),2,
                        ModItems.BlackForestSword1.get()));
        injectingRecipeMap.put(ModItems.BlackForestSword1.get(),
                new InjectingRecipe(ModItems.BlackForestRune.get(),3,
                        ModItems.BlackForestSword2.get()));
        injectingRecipeMap.put(ModItems.BlackForestSword2.get(),
                new InjectingRecipe(ModItems.BlackForestRune.get(),5,
                        ModItems.BlackForestSword3.get()));

        injectingRecipeMap.put(ModItems.KazeSword0.get(),
                new InjectingRecipe(ModItems.VolcanoCore.get(),2,
                        ModItems.KazeSword1.get()));
        injectingRecipeMap.put(ModItems.KazeSword1.get(),
                new InjectingRecipe(ModItems.VolcanoCore.get(),3,
                        ModItems.KazeSword2.get()));
        injectingRecipeMap.put(ModItems.KazeSword2.get(),
                new InjectingRecipe(ModItems.VolcanoCore.get(),5,
                        ModItems.KazeSword3.get()));

        injectingRecipeMap.put(ModItems.WitherSword0.get(),
                new InjectingRecipe(ModItems.WitherRune.get(),2,
                        ModItems.WitherSword1.get()));
        injectingRecipeMap.put(ModItems.WitherSword1.get(),
                new InjectingRecipe(ModItems.WitherRune.get(),3,
                        ModItems.WitherSword2.get()));
        injectingRecipeMap.put(ModItems.WitherSword2.get(),
                new InjectingRecipe(ModItems.WitherRune.get(),5,
                        ModItems.WitherSword3.get()));

        injectingRecipeMap.put(ModItems.WitherBow0.get(),
                new InjectingRecipe(ModItems.NetherRune.get(),2,
                        ModItems.WitherBow1.get()));
        injectingRecipeMap.put(ModItems.WitherBow1.get(),
                new InjectingRecipe(ModItems.NetherRune.get(),3,
                        ModItems.WitherBow2.get()));
        injectingRecipeMap.put(ModItems.WitherBow2.get(),
                new InjectingRecipe(ModItems.NetherRune.get(),5,
                        ModItems.WitherBow3.get()));

        injectingRecipeMap.put(ModItems.MagmaSceptre0.get(),
                new InjectingRecipe(ModItems.MagmaRune.get(),2,
                        ModItems.MagmaSceptre1.get()));
        injectingRecipeMap.put(ModItems.MagmaSceptre1.get(),
                new InjectingRecipe(ModItems.MagmaRune.get(),3,
                        ModItems.MagmaSceptre2.get()));
        injectingRecipeMap.put(ModItems.MagmaSceptre2.get(),
                new InjectingRecipe(ModItems.MagmaRune.get(),5,
                        ModItems.MagmaSceptre3.get()));

        injectingRecipeMap.put(ModItems.LightningRune.get(),
                new InjectingRecipe(10,ModItems.VolcanoCore.get(),64,
                        ModItems.LightningPower.get()));

        injectingRecipeMap.put(ModItems.KazeRecallSoul.get(),
                new InjectingRecipe(4,ModItems.VolcanoCore.get(),64,
                        ModItems.IntensifiedKazeSoul.get()));

        injectingRecipeMap.put(ModItems.SpiderRecallSoul.get(),
                new InjectingRecipe(4,ModItems.VolcanoCore.get(),64,
                        ModItems.IntensifiedSpiderSoul.get()));

        injectingRecipeMap.put(ModItems.BlackForestRecallSoul.get(),
                new InjectingRecipe(4,ModItems.VolcanoCore.get(),64,
                        ModItems.IntensifiedBlackForestSoul.get()));


        injectingRecipeMap.put(ModItems.SeaRecallSoul.get(),
                new InjectingRecipe(4,ModItems.VolcanoCore.get(),64,
                        ModItems.IntensifiedSeaSoul.get()));

        injectingRecipeMap.put(ModItems.LightningRecallSoul.get(),
                new InjectingRecipe(4,ModItems.VolcanoCore.get(),64,
                        ModItems.IntensifiedLightningSoul.get()));

        injectingRecipeMap.put(ModItems.NetherRecallSoul.get(),
                new InjectingRecipe(4,ModItems.VolcanoCore.get(),64,
                        ModItems.IntensifiedNetherRecallSoul.get()));

        injectingRecipeMap.put(ModItems.SnowRecallSoul.get(),
                new InjectingRecipe(4,ModItems.VolcanoCore.get(),64,
                        ModItems.IntensifiedSnowRecallSoul.get()));

        injectingRecipeMap.put(ModItems.LightningRecallSoul.get(),
                new InjectingRecipe(4,ModItems.VolcanoCore.get(),64,
                        ModItems.IntensifiedLightningSoul.get()));

        injectingRecipeMap.put(ModItems.KazeSoul.get(),
                new InjectingRecipe(64,ModItems.LakeCore.get(),6,
                        ModItems.KazeRune.get()));

        injectingRecipeMap.put(ModItems.SeaSoul.get(),
                new InjectingRecipe(64,ModItems.SunPower.get(),6,
                        ModItems.SeaRune.get()));

        injectingRecipeMap.put(ModItems.BlackForestSoul.get(),
                new InjectingRecipe(64,ModItems.SunPower.get(),6,
                        ModItems.BlackForestRune.get()));

        injectingRecipeMap.put(ModItems.LightningSoul.get(),
                new InjectingRecipe(64,ModItems.SpeIron.get(),1,
                        ModItems.LightningRune.get()));

        injectingRecipeMap.put(ModItems.KazeSword3.get(),
                new InjectingRecipe(ModItems.IntensifiedKazeSoul.get(),1,
                        ModItems.KazeSword4.get()));

        injectingRecipeMap.put(ModItems.SBoots.get(),
                new InjectingRecipe(ModItems.IntensifiedSpiderSoul.get(),1,
                        ModItems.ISArmorBoots.get()));

        injectingRecipeMap.put(ModItems.SLeggings.get(),
                new InjectingRecipe(ModItems.IntensifiedSpiderSoul.get(),1,
                        ModItems.ISArmorLeggings.get()));

        injectingRecipeMap.put(ModItems.SChest.get(),
                new InjectingRecipe(ModItems.IntensifiedSpiderSoul.get(),1,
                        ModItems.ISArmorChest.get()));

        injectingRecipeMap.put(ModItems.SHelmet.get(),
                new InjectingRecipe(ModItems.IntensifiedSpiderSoul.get(),1,
                        ModItems.ISArmorHelmet.get()));

        injectingRecipeMap.put(ModItems.BlackForestSword3.get(),
                new InjectingRecipe(ModItems.IntensifiedBlackForestSoul.get(),1,
                        ModItems.BlackForestSword4.get()));

        injectingRecipeMap.put(ModItems.SeaSword3.get(),
                new InjectingRecipe(ModItems.IntensifiedSeaSoul.get(),1,
                        ModItems.SeaSword4.get()));

        injectingRecipeMap.put(ModItems.IslandArmorHelmet.get(),
                new InjectingRecipe(ModItems.IntensifiedLightningSoul.get(),1,
                        ModItems.ILArmorHelmet.get()));

        injectingRecipeMap.put(ModItems.IslandArmorChest.get(),
                new InjectingRecipe(ModItems.IntensifiedLightningSoul.get(),1,
                        ModItems.ILArmorChest.get()));

        injectingRecipeMap.put(ModItems.IslandArmorLeggings.get(),
                new InjectingRecipe(ModItems.IntensifiedLightningSoul.get(),1,
                        ModItems.ILArmorLeggings.get()));

        injectingRecipeMap.put(ModItems.IslandArmorBoots.get(),
                new InjectingRecipe(ModItems.IntensifiedLightningSoul.get(),1,
                        ModItems.ILArmorBoots.get()));

        injectingRecipeMap.put(ModItems.ManaSword.get(),
                new InjectingRecipe(ModItems.IntensifiedNetherRecallSoul.get(),1,
                        ModItems.ManaSword1.get()));

        injectingRecipeMap.put(ModItems.SnowSword3.get(),
                new InjectingRecipe(ModItems.IntensifiedSnowRecallSoul.get(),1,
                        ModItems.SnowSword4.get()));

        injectingRecipeMap.put(ModItems.ForestSword3.get(),
                new InjectingRecipe(ModItems.IntensifiedForestRecallSoul.get(),1,
                        ModItems.ForestSword4.get()));

        injectingRecipeMap.put(ModItems.VolcanoSword3.get(),
                new InjectingRecipe(ModItems.IntensifiedVolcanoRecallSoul.get(),1,
                        ModItems.VolcanoSword4.get()));

        injectingRecipeMap.put(ModItems.MineSword3.get(),
                new InjectingRecipe(ModItems.SnowRune.get(),1,
                        ModItems.SnowSword0.get()));

        injectingRecipeMap.put(ModItems.PlainSceptre3.get(),
                new InjectingRecipe(ModItems.SunPower.get(),24,
                        ModItems.PlainSceptre4.get()));

        injectingRecipeMap.put(ModItems.PlainPower.get(),
                new InjectingRecipe(ModItems.PlainRune.get(),1,
                        ModItems.PlainPower1.get()));

        injectingRecipeMap.put(ModItems.PlainPower1.get(),
                new InjectingRecipe(ModItems.PlainRune.get(),1,
                        ModItems.PlainPower2.get()));

        injectingRecipeMap.put(ModItems.PlainPower2.get(),
                new InjectingRecipe(ModItems.PlainRune.get(),1,
                        ModItems.PlainPower3.get()));

        injectingRecipeMap.put(ModItems.ForestPower.get(),
                new InjectingRecipe(ModItems.ForestRune.get(),1,
                        ModItems.ForestPower1.get()));

        injectingRecipeMap.put(ModItems.ForestPower1.get(),
                new InjectingRecipe(ModItems.ForestRune.get(),1,
                        ModItems.ForestPower2.get()));

        injectingRecipeMap.put(ModItems.ForestPower2.get(),
                new InjectingRecipe(ModItems.ForestRune.get(),1,
                        ModItems.ForestPower3.get()));

        injectingRecipeMap.put(ModItems.LakePower.get(),
                new InjectingRecipe(ModItems.LakeRune.get(),1,
                        ModItems.LakePower1.get()));

        injectingRecipeMap.put(ModItems.LakePower1.get(),
                new InjectingRecipe(ModItems.LakeRune.get(),1,
                        ModItems.LakePower2.get()));

        injectingRecipeMap.put(ModItems.LakePower2.get(),
                new InjectingRecipe(ModItems.LakeRune.get(),1,
                        ModItems.LakePower3.get()));

        injectingRecipeMap.put(ModItems.VolcanoPower.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(),1,
                        ModItems.VolcanoPower1.get()));

        injectingRecipeMap.put(ModItems.VolcanoPower1.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(),1,
                        ModItems.VolcanoPower2.get()));

        injectingRecipeMap.put(ModItems.VolcanoPower2.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(),1,
                        ModItems.VolcanoPower3.get()));

        injectingRecipeMap.put(ModItems.SnowPower.get(),
                new InjectingRecipe(ModItems.SnowRune.get(),1,
                        ModItems.SnowPower1.get()));

        injectingRecipeMap.put(ModItems.SnowPower1.get(),
                new InjectingRecipe(ModItems.SnowRune.get(),1,
                        ModItems.SnowPower2.get()));

        injectingRecipeMap.put(ModItems.SnowPower2.get(),
                new InjectingRecipe(ModItems.SnowRune.get(),1,
                        ModItems.SnowPower3.get()));

        injectingRecipeMap.put(ModItems.PlainAttackRing0.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(),1,
                        ModItems.PlainAttackRing1.get()));
        injectingRecipeMap.put(ModItems.PlainAttackRing1.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(),2,
                        ModItems.PlainAttackRing2.get()));
        injectingRecipeMap.put(ModItems.PlainAttackRing2.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(),4,
                        ModItems.PlainAttackRing3.get()));

        injectingRecipeMap.put(ModItems.PlainManaAttackRing0.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(),1,
                        ModItems.PlainManaAttackRing1.get()));
        injectingRecipeMap.put(ModItems.PlainManaAttackRing1.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(),2,
                        ModItems.PlainManaAttackRing2.get()));
        injectingRecipeMap.put(ModItems.PlainManaAttackRing2.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(),4,
                        ModItems.PlainManaAttackRing3.get()));

        injectingRecipeMap.put(ModItems.PlainHealthRing0.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(),1,
                        ModItems.PlainHealthRing1.get()));
        injectingRecipeMap.put(ModItems.PlainHealthRing1.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(),2,
                        ModItems.PlainHealthRing2.get()));
        injectingRecipeMap.put(ModItems.PlainHealthRing2.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(),4,
                        ModItems.PlainHealthRing3.get()));

        injectingRecipeMap.put(ModItems.PlainDefenceRing0.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(),1,
                        ModItems.PlainDefenceRing1.get()));
        injectingRecipeMap.put(ModItems.PlainDefenceRing1.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(),2,
                        ModItems.PlainDefenceRing2.get()));
        injectingRecipeMap.put(ModItems.PlainDefenceRing2.get(),
                new InjectingRecipe(ModItems.PlainCompleteGem.get(),4,
                        ModItems.PlainDefenceRing3.get()));

        injectingRecipeMap.put(ModItems.PlainBossSoul.get(),
                new InjectingRecipe(16,ModItems.CompleteGem.get(),1,
                        ModItems.PlainCompleteGem.get()));

        injectingRecipeMap.put(ModItems.IceSoul.get(),
                new InjectingRecipe(64,ModItems.CompleteGem.get(),16,
                        ModItems.IceCompleteGem.get()));

        injectingRecipeMap.put(ModItems.VolcanoBow3.get(),
                new InjectingRecipe(ModItems.QuartzRune.get(),1,
                        ModItems.NetherBow.get()));

        injectingRecipeMap.put(ModItems.MineBow0.get(),
                new InjectingRecipe(ModItems.MineSoul1.get(),2,
                        ModItems.MineBow1.get()));
        injectingRecipeMap.put(ModItems.MineBow1.get(),
                new InjectingRecipe(ModItems.MineSoul1.get(),3,
                        ModItems.MineBow2.get()));
        injectingRecipeMap.put(ModItems.MineBow2.get(),
                new InjectingRecipe(ModItems.MineSoul1.get(),5,
                        ModItems.MineBow3.get()));

        injectingRecipeMap.put(ModItems.LifeManaArmorHelmet.get(),
                new InjectingRecipe(ModItems.NaturalCore.get(),6,
                        ModItems.LifeManaArmorHelmetE.get()));

        injectingRecipeMap.put(ModItems.LifeManaArmorChest.get(),
                new InjectingRecipe(ModItems.NaturalCore.get(),6,
                        ModItems.LifeManaArmorChestE.get()));

        injectingRecipeMap.put(ModItems.LifeManaArmorLeggings.get(),
                new InjectingRecipe(ModItems.NaturalCore.get(),6,
                        ModItems.LifeManaArmorLeggingsE.get()));

        injectingRecipeMap.put(ModItems.LifeManaArmorBoots.get(),
                new InjectingRecipe(ModItems.NaturalCore.get(),6,
                        ModItems.LifeManaArmorBootsE.get()));

        injectingRecipeMap.put(ModItems.ObsiManaArmorHelmet.get(),
                new InjectingRecipe(ModItems.OreRune.get(),6,
                        ModItems.ObsiManaArmorHelmetE.get()));

        injectingRecipeMap.put(ModItems.ObsiManaArmorChest.get(),
                new InjectingRecipe(ModItems.OreRune.get(),6,
                        ModItems.ObsiManaArmorChestE.get()));

        injectingRecipeMap.put(ModItems.ObsiManaArmorLeggings.get(),
                new InjectingRecipe(ModItems.OreRune.get(),6,
                        ModItems.ObsiManaArmorLeggingsE.get()));

        injectingRecipeMap.put(ModItems.ObsiManaArmorBoots.get(),
                new InjectingRecipe(ModItems.OreRune.get(),6,
                        ModItems.ObsiManaArmorBootsE.get()));

        injectingRecipeMap.put(ModItems.FINAL_CORD.get(),
                new InjectingRecipe(ModItems.IceHeart.get(),2,
                        ModItems.IceBracelet.get()));

        injectingRecipeMap.put(ModItems.EvokerBook3.get(),
                new InjectingRecipe(ModItems.IceHeart.get(),2,
                        ModItems.IceBook.get()));

        injectingRecipeMap.put(ModItems.SpringRing0.get(),
                new InjectingRecipe(ModItems.SpringGoldCoin.get(),8,
                        ModItems.SpringRing1.get()));
        injectingRecipeMap.put(ModItems.SpringRing1.get(),
                new InjectingRecipe(ModItems.SpringGoldCoin.get(),16,
                        ModItems.SpringRing2.get()));
        injectingRecipeMap.put(ModItems.SpringRing2.get(),
                new InjectingRecipe(ModItems.SpringGoldCoin.get(),24,
                        ModItems.SpringRing3.get()));

        injectingRecipeMap.put(ModItems.SpringHand0.get(),
                new InjectingRecipe(ModItems.SpringGoldCoin.get(),8,
                        ModItems.SpringHand1.get()));
        injectingRecipeMap.put(ModItems.SpringHand1.get(),
                new InjectingRecipe(ModItems.SpringGoldCoin.get(),16,
                        ModItems.SpringHand2.get()));
        injectingRecipeMap.put(ModItems.SpringHand2.get(),
                new InjectingRecipe(ModItems.SpringGoldCoin.get(),24,
                        ModItems.SpringHand3.get()));

        injectingRecipeMap.put(ModItems.SpringNecklace0.get(),
                new InjectingRecipe(ModItems.SpringGoldCoin.get(),8,
                        ModItems.SpringNecklace1.get()));
        injectingRecipeMap.put(ModItems.SpringNecklace1.get(),
                new InjectingRecipe(ModItems.SpringGoldCoin.get(),16,
                        ModItems.SpringNecklace2.get()));
        injectingRecipeMap.put(ModItems.SpringNecklace2.get(),
                new InjectingRecipe(ModItems.SpringGoldCoin.get(),24,
                        ModItems.SpringNecklace3.get()));

        injectingRecipeMap.put(ModItems.SpringBelt0.get(),
                new InjectingRecipe(ModItems.SpringGoldCoin.get(),8,
                        ModItems.SpringBelt1.get()));
        injectingRecipeMap.put(ModItems.SpringBelt1.get(),
                new InjectingRecipe(ModItems.SpringGoldCoin.get(),16,
                        ModItems.SpringBelt2.get()));
        injectingRecipeMap.put(ModItems.SpringBelt2.get(),
                new InjectingRecipe(ModItems.SpringGoldCoin.get(),24,
                        ModItems.SpringBelt3.get()));

        injectingRecipeMap.put(ModItems.SpringSoul.get(),
                new InjectingRecipe(24,ModItems.SpringGoldCoin.get(),6,
                        ModItems.SpringHeart.get()));

        injectingRecipeMap.put(ModItems.BloodManaCurios.get(),
                new InjectingRecipe(ModItems.DevilBlood.get(),6,
                        ModItems.DevilBloodManaCurios.get()));

        injectingRecipeMap.put(ModItems.EarthManaCurios.get(),
                new InjectingRecipe(ModItems.DevilBlood.get(),6,
                        ModItems.DevilEarthManaCurios.get()));

        injectingRecipeMap.put(ModItems.MoonSoul.get(),
                new InjectingRecipe(64,ModItems.CompleteGem.get(),16,
                        ModItems.MoonCompleteGem.get()));

        injectingRecipeMap.put(ModItems.IceSword.get(),
                new InjectingRecipe(ModItems.DevilBlood.get(),8,
                        ModItems.DevilSword.get()));

        injectingRecipeMap.put(ModItems.IceBow.get(),
                new InjectingRecipe(ModItems.DevilBlood.get(),8,
                        ModItems.DevilBow.get()));

        injectingRecipeMap.put(ModItems.IceSceptre.get(),
                new InjectingRecipe(ModItems.DevilBlood.get(),8,
                        ModItems.DevilSceptre.get()));

        injectingRecipeMap.put(ModItems.SkyGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.SkyGemD.get()));

        injectingRecipeMap.put(ModItems.EvokerGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.EvokerGemD.get()));

        injectingRecipeMap.put(ModItems.PlainGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.PlainGemD.get()));

        injectingRecipeMap.put(ModItems.ForestGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.ForestGemD.get()));

        injectingRecipeMap.put(ModItems.LakeGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.LakeGemD.get()));

        injectingRecipeMap.put(ModItems.VolcanoGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.VolcanoGemD.get()));

        injectingRecipeMap.put(ModItems.SnowGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.SnowGemD.get()));

        injectingRecipeMap.put(ModItems.FieldGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.FieldGemD.get()));

        injectingRecipeMap.put(ModItems.MineGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.MineGemD.get()));

        injectingRecipeMap.put(ModItems.LifeManaGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.LifeManaGemD.get()));

        injectingRecipeMap.put(ModItems.ObsiManaGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.ObsiManaGemD.get()));

        injectingRecipeMap.put(ModItems.NetherSkeletonGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.NetherSkeletonGemD.get()));

        injectingRecipeMap.put(ModItems.MagmaGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.MagmaGemD.get()));

        injectingRecipeMap.put(ModItems.WitherGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.WitherGemD.get()));

        injectingRecipeMap.put(ModItems.PiglinGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.PiglinGemD.get()));

        injectingRecipeMap.put(ModItems.SakuraGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.SakuraGemD.get()));

        injectingRecipeMap.put(ModItems.ShipGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.ShipGemD.get()));

        injectingRecipeMap.put(ModItems.MoonAttackGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.MoonAttackGemD.get()));

        injectingRecipeMap.put(ModItems.MoonManaGem.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),1,
                        ModItems.MoonManaGemD.get()));

        injectingRecipeMap.put(ModItems.BeaconBow.get(),
                new InjectingRecipe(ModItems.BeaconRune.get(),16,
                        ModItems.BeaconBow1.get()));

        injectingRecipeMap.put(ModItems.BeaconBow1.get(),
                new InjectingRecipe(ModItems.BeaconRune.get(),32,
                        ModItems.BeaconBow2.get()));

        injectingRecipeMap.put(ModItems.BeaconBow2.get(),
                new InjectingRecipe(ModItems.BeaconRune.get(),64,
                        ModItems.BeaconBow3.get()));

        injectingRecipeMap.put(ModItems.BlazeSword.get(),
                new InjectingRecipe(ModItems.BlazeRune.get(),16,
                        ModItems.BlazeSword1.get()));

        injectingRecipeMap.put(ModItems.BlazeSword1.get(),
                new InjectingRecipe(ModItems.BlazeRune.get(),32,
                        ModItems.BlazeSword2.get()));

        injectingRecipeMap.put(ModItems.BlazeSword2.get(),
                new InjectingRecipe(ModItems.BlazeRune.get(),64,
                        ModItems.BlazeSword3.get()));

        injectingRecipeMap.put(ModItems.TreeSceptre.get(),
                new InjectingRecipe(ModItems.TreeRune.get(),16,
                        ModItems.TreeSceptre1.get()));

        injectingRecipeMap.put(ModItems.TreeSceptre1.get(),
                new InjectingRecipe(ModItems.TreeRune.get(),32,
                        ModItems.TreeSceptre2.get()));

        injectingRecipeMap.put(ModItems.TreeSceptre2.get(),
                new InjectingRecipe(ModItems.TreeRune.get(),64,
                        ModItems.TreeSceptre3.get()));

        injectingRecipeMap.put(ModItems.EndPower.get(),
                new InjectingRecipe(ModItems.RecallPiece.get(),64,
                        ModItems.EndPower1.get()));

        injectingRecipeMap.put(ModItems.EndPower1.get(),
                new InjectingRecipe(ModItems.RecallPiece.get(),64,
                        ModItems.EndPower2.get()));

        injectingRecipeMap.put(ModItems.EndPower2.get(),
                new InjectingRecipe(ModItems.RecallPiece.get(),64,
                        ModItems.EndPower3.get()));

        injectingRecipeMap.put(ModItems.CastleNecklace.get(),
                new InjectingRecipe(ModItems.CastleNecklace.get(),1,
                        ModItems.CastleNecklace.get()));

        injectingRecipeMap.put(ModItems.CastleBrooch.get(),
                new InjectingRecipe(ModItems.CastleBrooch.get(),1,
                        ModItems.CastleBrooch.get()));

        injectingRecipeMap.put(ModItems.RubyNecklace.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(),8,
                        ModItems.RubyNecklace1.get()));

        injectingRecipeMap.put(ModItems.RubyNecklace1.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(),16,
                        ModItems.RubyNecklace2.get()));

        injectingRecipeMap.put(ModItems.RubyNecklace2.get(),
                new InjectingRecipe(ModItems.VolcanoRune.get(),24,
                        ModItems.RubyNecklace3.get()));

        injectingRecipeMap.put(ModItems.SapphireNecklace.get(),
                new InjectingRecipe(ModItems.LakeRune.get(),8,
                        ModItems.SapphireNecklace1.get()));

        injectingRecipeMap.put(ModItems.SapphireNecklace1.get(),
                new InjectingRecipe(ModItems.LakeRune.get(),16,
                        ModItems.SapphireNecklace2.get()));

        injectingRecipeMap.put(ModItems.SapphireNecklace2.get(),
                new InjectingRecipe(ModItems.LakeRune.get(),24,
                        ModItems.SapphireNecklace3.get()));

        injectingRecipeMap.put(ModItems.FancySapphireNecklace.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),3,
                        ModItems.FancySapphireNecklace1.get()));

        injectingRecipeMap.put(ModItems.FancySapphireNecklace1.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),6,
                        ModItems.FancySapphireNecklace2.get()));

        injectingRecipeMap.put(ModItems.FancySapphireNecklace2.get(),
                new InjectingRecipe(ModItems.ConstrainTaboo.get(),9,
                        ModItems.FancySapphireNecklace3.get()));

        injectingRecipeMap.put(ModItems.PurpleIronBud1.get(),
                new InjectingRecipe(16,ModItems.PurpleIron.get(),4,
                        ModItems.PurpleIronBud2.get()));

        injectingRecipeMap.put(ModItems.PurpleIronBud2.get(),
                new InjectingRecipe(16,ModItems.PurpleIron.get(),16,
                        ModItems.PurpleIronBud3.get()));

        injectingRecipeMap.put(ModItems.PurpleIronSword.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(),2,
                        ModItems.PurpleIronSword1.get()));

        injectingRecipeMap.put(ModItems.PurpleIronSword1.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(),4,
                        ModItems.PurpleIronSword2.get()));

        injectingRecipeMap.put(ModItems.PurpleIronSword2.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(),8,
                        ModItems.PurpleIronSword3.get()));

        injectingRecipeMap.put(ModItems.PurpleIronBow.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(),2,
                        ModItems.PurpleIronBow1.get()));

        injectingRecipeMap.put(ModItems.PurpleIronBow1.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(),4,
                        ModItems.PurpleIronBow2.get()));

        injectingRecipeMap.put(ModItems.PurpleIronBow2.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(),8,
                        ModItems.PurpleIronBow3.get()));

        injectingRecipeMap.put(ModItems.PurpleIronSceptre.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(),2,
                        ModItems.PurpleIronSceptre1.get()));

        injectingRecipeMap.put(ModItems.PurpleIronSceptre1.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(),4,
                        ModItems.PurpleIronSceptre2.get()));

        injectingRecipeMap.put(ModItems.PurpleIronSceptre2.get(),
                new InjectingRecipe(ModItems.PurpleIronBud3.get(),8,
                        ModItems.PurpleIronSceptre3.get()));

    }
}