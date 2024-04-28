package com.very.wraq.blocks.blocks;

import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForgeRecipe {
    public static Map<Item, List<ItemStack>> ForgeDrawRecipe = new HashMap<>();

    public static void ForgeDrawRecipeInit() {
        ForgeDrawRecipe.put(ModItems.SakuraDemonSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SakuraPetal.get(),64));
            add(new ItemStack(ModItems.GoldCoin.get(),192));
            add(new ItemStack(ModItems.CompleteGem.get(),24));
            add(new ItemStack(ModItems.ReputationMedal.get(),24));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.IslandArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.LightningRune.get(),10));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.IslandArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.LightningRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.IslandArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.LightningRune.get(),14));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.IslandArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.LightningRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SkyArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SkyRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SkyArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SkyRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SkyArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SkyRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SkyArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SkyRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SeaSword0.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SeaRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningGold.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.BlackForestSword0.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.BlackForestRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningGold.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.WitherRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.NetherSkeletonRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MagmaRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.PiglinRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.KazeSword0.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.KazeRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningGold.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.KazeBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.KazeRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),64));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.ForestBossSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ForestBossCore.get(),128));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.VolcanoBossSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.VolcanoBossCore.get(),128));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.LakeBossSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.LakeBossCore.get(),128));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SkyBossBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SkyBossCore.get(),128));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SnowBossArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SnowBossCore.get(),64));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningIron.get(),2));
            add(new ItemStack(ModItems.RefiningCopper.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SeaManaCore.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SeaRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.NaturalCore.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.BlackForestManaCore.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.BlackForestRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.NaturalCore.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.KazeManaCore.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.KazeRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.NaturalCore.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SakuraBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SakuraPetal.get(),64));
            add(new ItemStack(ModItems.GoldCoin.get(),192));
            add(new ItemStack(ModItems.CompleteGem.get(),24));
            add(new ItemStack(ModItems.ReputationMedal.get(),24));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SakuraCore.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SakuraPetal.get(),64));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.NaturalCore.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.MinePants.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.Wheat.get(),64));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),32));
            add(new ItemStack(ModItems.ReputationMedal.get(),32));
            add(new ItemStack(ModItems.RefiningIron.get(),4));
            add(new ItemStack(ModItems.RefiningCopper.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.IceArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),4));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningIron.get(),4));
            add(new ItemStack(ModItems.RefiningCopper.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.IceArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),4));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningIron.get(),4));
            add(new ItemStack(ModItems.RefiningCopper.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.IceArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),4));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningIron.get(),4));
            add(new ItemStack(ModItems.RefiningCopper.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.IceArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),4));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningIron.get(),4));
            add(new ItemStack(ModItems.RefiningCopper.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SeaBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SeaRune.get(),8));
            add(new ItemStack(ModItems.BlackForestRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningGold.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.IceSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningGold.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.IceBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningGold.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.IceSceptre.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.NaturalCore.get(),16));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.ShipSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ShipPiece.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),32));
            add(new ItemStack(ModItems.ReputationMedal.get(),32));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.ShipBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ShipPiece.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),32));
            add(new ItemStack(ModItems.ReputationMedal.get(),32));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.ShipSceptre.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ShipPiece.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),32));
            add(new ItemStack(ModItems.ReputationMedal.get(),32));
            add(new ItemStack(ModItems.NaturalCore.get(),8));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.NetherManaArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.WitherRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherManaArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.NetherSkeletonRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherManaArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MagmaRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherManaArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.PiglinRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherSceptre.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.NetherSoul.get(),32));
            add(new ItemStack(ModItems.GoldCoin.get(),192));
            add(new ItemStack(ModItems.CompleteGem.get(),24));
            add(new ItemStack(ModItems.ReputationMedal.get(),24));
            add(new ItemStack(ModItems.RefiningGold.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringAttackArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringAttackArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringAttackArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringAttackArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringSwiftArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringSwiftArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringSwiftArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringSwiftArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringManaArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringManaArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringManaArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringManaArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.GoldenShield.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.Boss2Piece.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningGold.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.DevilAttackChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.DevilAttackSoul.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),80));
            add(new ItemStack(ModItems.ReputationMedal.get(),80));
            add(new ItemStack(ModItems.RefiningGold.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),4));
        }});

        ForgeDrawRecipe.put(ModItems.DevilSwiftBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.DevilSwiftSoul.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),80));
            add(new ItemStack(ModItems.ReputationMedal.get(),80));
            add(new ItemStack(ModItems.RefiningGold.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),4));
        }});

        ForgeDrawRecipe.put(ModItems.DevilManaHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.DevilManaSoul.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),80));
            add(new ItemStack(ModItems.ReputationMedal.get(),80));
            add(new ItemStack(ModItems.RefiningGold.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),4));
        }});

        ForgeDrawRecipe.put(ModItems.MoonShield.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ManaShield.get(),1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(),6));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),96));
            add(new ItemStack(ModItems.ReputationMedal.get(),96));
            add(new ItemStack(ModItems.RefiningGold.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.MoonKnife.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ManaKnife.get(),1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(),6));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),96));
            add(new ItemStack(ModItems.ReputationMedal.get(),96));
            add(new ItemStack(ModItems.RefiningGold.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.MoonBook.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.EarthBook.get(),1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(),6));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),96));
            add(new ItemStack(ModItems.ReputationMedal.get(),96));
            add(new ItemStack(ModItems.RefiningGold.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.MoonLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MoonCompleteGem.get(),12));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),128));
            add(new ItemStack(ModItems.ReputationMedal.get(),128));
            add(new ItemStack(ModItems.RefiningIron.get(),6));
            add(new ItemStack(ModItems.RefiningCopper.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.MoonSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MoonCompleteGem.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),160));
            add(new ItemStack(ModItems.ReputationMedal.get(),160));
            add(new ItemStack(ModItems.RefiningGold.get(),8));
            add(new ItemStack(ModItems.WorldSoul3.get(),8));
        }});

        ForgeDrawRecipe.put(ModItems.MoonBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MoonCompleteGem.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),160));
            add(new ItemStack(ModItems.ReputationMedal.get(),160));
            add(new ItemStack(ModItems.RefiningGold.get(),8));
            add(new ItemStack(ModItems.WorldSoul3.get(),8));
        }});

        ForgeDrawRecipe.put(ModItems.MoonSceptre.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MoonCompleteGem.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),160));
            add(new ItemStack(ModItems.ReputationMedal.get(),160));
            add(new ItemStack(ModItems.NaturalCore.get(),16));
            add(new ItemStack(ModItems.WorldSoul3.get(),8));
        }});

        ForgeDrawRecipe.put(ModItems.MoonBelt.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MoonCompleteGem.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),160));
            add(new ItemStack(ModItems.ReputationMedal.get(),160));
            add(new ItemStack(ModItems.RefiningGold.get(),8));
            add(new ItemStack(ModItems.WorldSoul3.get(),8));
        }});

        ForgeDrawRecipe.put(ModItems.TabooAttackLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ConstrainTaboo.get(),8));
            add(new ItemStack(ModItems.PurpleIronArmorLeggings.get(),1));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),128));
            add(new ItemStack(ModItems.ReputationMedal.get(),128));
            add(new ItemStack(ModItems.RefiningIron.get(),6));
            add(new ItemStack(ModItems.RefiningCopper.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.TabooSwiftHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ConstrainTaboo.get(),8));
            add(new ItemStack(ModItems.PurpleIronArmorHelmet.get(),1));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),128));
            add(new ItemStack(ModItems.ReputationMedal.get(),128));
            add(new ItemStack(ModItems.RefiningIron.get(),6));
            add(new ItemStack(ModItems.RefiningCopper.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.TabooManaBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ConstrainTaboo.get(),8));
            add(new ItemStack(ModItems.PurpleIronArmorBoots.get(),1));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),128));
            add(new ItemStack(ModItems.ReputationMedal.get(),128));
            add(new ItemStack(ModItems.RefiningIron.get(),6));
            add(new ItemStack(ModItems.RefiningCopper.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.MoonHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MoonCompleteGem.get(),12));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),128));
            add(new ItemStack(ModItems.ReputationMedal.get(),128));
            add(new ItemStack(ModItems.RefiningIron.get(),6));
            add(new ItemStack(ModItems.RefiningCopper.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.CastleAttackHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),64));
        }});
        ForgeDrawRecipe.put(ModItems.CastleAttackChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),64));
        }});
        ForgeDrawRecipe.put(ModItems.CastleAttackLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),64));
        }});
        ForgeDrawRecipe.put(ModItems.CastleAttackBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),64));
        }});

        ForgeDrawRecipe.put(ModItems.CastleSwiftHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),64));
        }});
        ForgeDrawRecipe.put(ModItems.CastleSwiftChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),64));
        }});
        ForgeDrawRecipe.put(ModItems.CastleSwiftLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),64));
        }});
        ForgeDrawRecipe.put(ModItems.CastleSwiftBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),64));
        }});

        ForgeDrawRecipe.put(ModItems.CastleManaHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),64));
        }});
        ForgeDrawRecipe.put(ModItems.CastleManaChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),64));
        }});
        ForgeDrawRecipe.put(ModItems.CastleManaLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),64));
        }});
        ForgeDrawRecipe.put(ModItems.CastleManaBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),64));
        }});

        ForgeDrawRecipe.put(ModItems.CastleSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleSwordPiece.get(),32));
        }});
        ForgeDrawRecipe.put(ModItems.CastleBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleBowPiece.get(),32));
        }});
        ForgeDrawRecipe.put(ModItems.CastleSceptre.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleSceptrePiece.get(),32));
        }});

        ForgeDrawRecipe.put(ModItems.StarBottle.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.StarStar.get(),24));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),160));
            add(new ItemStack(ModItems.ReputationMedal.get(),160));
            add(new ItemStack(ModItems.RefiningGold.get(),8));
            add(new ItemStack(ModItems.WorldSoul3.get(),8));
        }});

        ForgeDrawRecipe.put(ModItems.LifeElementSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.LifeElementPiece2.get(),21));
            add(new ItemStack(ModItems.GoldCoin.get(),448));
            add(new ItemStack(ModItems.CompleteGem.get(),200));
            add(new ItemStack(ModItems.ReputationMedal.get(),200));
            add(new ItemStack(ModItems.RefiningGold.get(),12));
            add(new ItemStack(ModItems.WorldSoul3.get(),12));
        }});

        ForgeDrawRecipe.put(ModItems.LifeElementBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.LifeElementPiece2.get(),21));
            add(new ItemStack(ModItems.GoldCoin.get(),448));
            add(new ItemStack(ModItems.CompleteGem.get(),200));
            add(new ItemStack(ModItems.ReputationMedal.get(),200));
            add(new ItemStack(ModItems.RefiningGold.get(),12));
            add(new ItemStack(ModItems.WorldSoul3.get(),12));
        }});

        ForgeDrawRecipe.put(ModItems.LifeElementSceptre.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.LifeElementPiece2.get(),21));
            add(new ItemStack(ModItems.GoldCoin.get(),448));
            add(new ItemStack(ModItems.CompleteGem.get(),200));
            add(new ItemStack(ModItems.ReputationMedal.get(),200));
            add(new ItemStack(ModItems.RefiningGold.get(),12));
            add(new ItemStack(ModItems.WorldSoul3.get(),12));
        }});

        ForgeDrawRecipe.put(ModItems.WaterElementSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.WaterElementPiece2.get(),21));
            add(new ItemStack(ModItems.GoldCoin.get(),448));
            add(new ItemStack(ModItems.CompleteGem.get(),200));
            add(new ItemStack(ModItems.ReputationMedal.get(),200));
            add(new ItemStack(ModItems.RefiningGold.get(),12));
            add(new ItemStack(ModItems.WorldSoul3.get(),12));
        }});

        ForgeDrawRecipe.put(ModItems.WaterElementBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.WaterElementPiece2.get(),21));
            add(new ItemStack(ModItems.GoldCoin.get(),448));
            add(new ItemStack(ModItems.CompleteGem.get(),200));
            add(new ItemStack(ModItems.ReputationMedal.get(),200));
            add(new ItemStack(ModItems.RefiningGold.get(),12));
            add(new ItemStack(ModItems.WorldSoul3.get(),12));
        }});

        ForgeDrawRecipe.put(ModItems.WaterElementSceptre.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.WaterElementPiece2.get(),21));
            add(new ItemStack(ModItems.GoldCoin.get(),448));
            add(new ItemStack(ModItems.CompleteGem.get(),200));
            add(new ItemStack(ModItems.ReputationMedal.get(),200));
            add(new ItemStack(ModItems.RefiningGold.get(),12));
            add(new ItemStack(ModItems.WorldSoul3.get(),12));
        }});

        ForgeDrawRecipe.put(ModItems.FireElementSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.FireElementPiece2.get(),21));
            add(new ItemStack(ModItems.GoldCoin.get(),448));
            add(new ItemStack(ModItems.CompleteGem.get(),200));
            add(new ItemStack(ModItems.ReputationMedal.get(),200));
            add(new ItemStack(ModItems.RefiningGold.get(),12));
            add(new ItemStack(ModItems.WorldSoul3.get(),12));
        }});

        ForgeDrawRecipe.put(ModItems.FireElementBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.FireElementPiece2.get(),21));
            add(new ItemStack(ModItems.GoldCoin.get(),448));
            add(new ItemStack(ModItems.CompleteGem.get(),200));
            add(new ItemStack(ModItems.ReputationMedal.get(),200));
            add(new ItemStack(ModItems.RefiningGold.get(),12));
            add(new ItemStack(ModItems.WorldSoul3.get(),12));
        }});

        ForgeDrawRecipe.put(ModItems.FireElementSceptre.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.FireElementPiece2.get(),21));
            add(new ItemStack(ModItems.GoldCoin.get(),448));
            add(new ItemStack(ModItems.CompleteGem.get(),200));
            add(new ItemStack(ModItems.ReputationMedal.get(),200));
            add(new ItemStack(ModItems.RefiningGold.get(),12));
            add(new ItemStack(ModItems.WorldSoul3.get(),12));
        }});

        ForgeDrawRecipe.put(ModItems.EndCurios.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ShulkerSoul.get(),320));
            add(new ItemStack(ModItems.EnderMiteSoul.get(),320));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),128));
            add(new ItemStack(ModItems.ReputationMedal.get(),128));
            add(new ItemStack(ModItems.RefiningGold.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.EndCurios1.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ShulkerSoul.get(),320));
            add(new ItemStack(ModItems.EnderMiteSoul.get(),320));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),128));
            add(new ItemStack(ModItems.ReputationMedal.get(),128));
            add(new ItemStack(ModItems.RefiningGold.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});
    }

    public static List<Item> ForgedItemList = new ArrayList<>();

    public static void setForgedItemList () {
        ForgedItemList.add(ModItems.SakuraDemonSword.get());
        ForgedItemList.add(ModItems.IslandArmorHelmet.get());
        ForgedItemList.add(ModItems.IslandArmorChest.get());
        ForgedItemList.add(ModItems.IslandArmorLeggings.get());
        ForgedItemList.add(ModItems.IslandArmorBoots.get());
        ForgedItemList.add(ModItems.SkyArmorHelmet.get());
        ForgedItemList.add(ModItems.SkyArmorChest.get());
        ForgedItemList.add(ModItems.SkyArmorLeggings.get());
        ForgedItemList.add(ModItems.SkyArmorBoots.get());
        ForgedItemList.add(ModItems.SeaSword0.get());
        ForgedItemList.add(ModItems.BlackForestSword0.get());
        ForgedItemList.add(ModItems.NetherArmorHelmet.get());
        ForgedItemList.add(ModItems.NetherArmorChest.get());
        ForgedItemList.add(ModItems.NetherArmorLeggings.get());
        ForgedItemList.add(ModItems.NetherArmorBoots.get());
        ForgedItemList.add(ModItems.KazeSword0.get());
        ForgedItemList.add(ModItems.KazeBoots.get());
        ForgedItemList.add(ModItems.ForestBossSword.get());
        ForgedItemList.add(ModItems.VolcanoBossSword.get());
        ForgedItemList.add(ModItems.LakeBossSword.get());
        ForgedItemList.add(ModItems.SkyBossBow.get());
        ForgedItemList.add(ModItems.SnowBossArmorChest.get());
        ForgedItemList.add(ModItems.SeaManaCore.get());
        ForgedItemList.add(ModItems.BlackForestManaCore.get());
        ForgedItemList.add(ModItems.KazeManaCore.get());
        ForgedItemList.add(ModItems.SakuraBow.get());
        ForgedItemList.add(ModItems.SakuraCore.get());
        ForgedItemList.add(ModItems.MinePants.get());
        ForgedItemList.add(ModItems.IceArmorHelmet.get());
        ForgedItemList.add(ModItems.IceArmorChest.get());
        ForgedItemList.add(ModItems.IceArmorLeggings.get());
        ForgedItemList.add(ModItems.IceArmorBoots.get());
        ForgedItemList.add(ModItems.SeaBow.get());
        ForgedItemList.add(ModItems.IceSword.get());
        ForgedItemList.add(ModItems.IceBow.get());
        ForgedItemList.add(ModItems.IceSceptre.get());
        ForgedItemList.add(ModItems.ShipSword.get());
        ForgedItemList.add(ModItems.ShipBow.get());
        ForgedItemList.add(ModItems.ShipSceptre.get());
        ForgedItemList.add(ModItems.NetherManaArmorHelmet.get());
        ForgedItemList.add(ModItems.NetherManaArmorChest.get());
        ForgedItemList.add(ModItems.NetherManaArmorLeggings.get());
        ForgedItemList.add(ModItems.NetherManaArmorBoots.get());
        ForgedItemList.add(ModItems.NetherSceptre.get());
        ForgedItemList.add(ModItems.SpringAttackArmorHelmet.get());
        ForgedItemList.add(ModItems.SpringAttackArmorChest.get());
        ForgedItemList.add(ModItems.SpringAttackArmorLeggings.get());
        ForgedItemList.add(ModItems.SpringAttackArmorBoots.get());
        ForgedItemList.add(ModItems.SpringSwiftArmorHelmet.get());
        ForgedItemList.add(ModItems.SpringSwiftArmorChest.get());
        ForgedItemList.add(ModItems.SpringSwiftArmorLeggings.get());
        ForgedItemList.add(ModItems.SpringSwiftArmorBoots.get());
        ForgedItemList.add(ModItems.SpringManaArmorHelmet.get());
        ForgedItemList.add(ModItems.SpringManaArmorChest.get());
        ForgedItemList.add(ModItems.SpringManaArmorLeggings.get());
        ForgedItemList.add(ModItems.SpringManaArmorBoots.get());
        ForgedItemList.add(ModItems.GoldenShield.get());
        ForgedItemList.add(ModItems.DevilAttackChest.get());
        ForgedItemList.add(ModItems.DevilSwiftBoots.get());
        ForgedItemList.add(ModItems.DevilManaHelmet.get());
        ForgedItemList.add(ModItems.MoonShield.get());
        ForgedItemList.add(ModItems.MoonKnife.get());
        ForgedItemList.add(ModItems.MoonBook.get());
        ForgedItemList.add(ModItems.MoonLeggings.get());
        ForgedItemList.add(ModItems.MoonSword.get());
        ForgedItemList.add(ModItems.MoonBow.get());
        ForgedItemList.add(ModItems.MoonSceptre.get());
        ForgedItemList.add(ModItems.MoonBelt.get());
        ForgedItemList.add(ModItems.TabooAttackLeggings.get());
        ForgedItemList.add(ModItems.TabooSwiftHelmet.get());
        ForgedItemList.add(ModItems.TabooManaBoots.get());
        ForgedItemList.add(ModItems.MoonHelmet.get());
        ForgedItemList.add(ModItems.CastleAttackHelmet.get());
        ForgedItemList.add(ModItems.CastleAttackChest.get());
        ForgedItemList.add(ModItems.CastleAttackLeggings.get());
        ForgedItemList.add(ModItems.CastleAttackBoots.get());
        ForgedItemList.add(ModItems.CastleSwiftHelmet.get());
        ForgedItemList.add(ModItems.CastleSwiftChest.get());
        ForgedItemList.add(ModItems.CastleSwiftLeggings.get());
        ForgedItemList.add(ModItems.CastleSwiftBoots.get());
        ForgedItemList.add(ModItems.CastleManaHelmet.get());
        ForgedItemList.add(ModItems.CastleManaChest.get());
        ForgedItemList.add(ModItems.CastleManaLeggings.get());
        ForgedItemList.add(ModItems.CastleManaBoots.get());
        ForgedItemList.add(ModItems.CastleSword.get());
        ForgedItemList.add(ModItems.CastleBow.get());
        ForgedItemList.add(ModItems.CastleSceptre.get());
        ForgedItemList.add(ModItems.StarBottle.get());
        ForgedItemList.add(ModItems.LifeElementSword.get());
        ForgedItemList.add(ModItems.LifeElementBow.get());
        ForgedItemList.add(ModItems.LifeElementSceptre.get());
        ForgedItemList.add(ModItems.WaterElementSword.get());
        ForgedItemList.add(ModItems.WaterElementBow.get());
        ForgedItemList.add(ModItems.WaterElementSceptre.get());
        ForgedItemList.add(ModItems.FireElementSword.get());
        ForgedItemList.add(ModItems.FireElementBow.get());
        ForgedItemList.add(ModItems.FireElementSceptre.get());
        ForgedItemList.add(ModItems.EndCurios.get());
        ForgedItemList.add(ModItems.EndCurios1.get());
    }
}
