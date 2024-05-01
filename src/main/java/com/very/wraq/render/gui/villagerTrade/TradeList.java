package com.very.wraq.render.gui.villagerTrade;

import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.*;

public class TradeList {
    public static Map<ItemStack, List<ItemStack>> tradeRecipeMap = new HashMap<>();
    public static Map<String, List<ItemStack>> tradeContent = new HashMap<>();
    public static void setTradeContent() {
        LifeElement();WindElement();StoneElement();FireElement();LightningElement();WaterElement();IceElement();
        Plain();Forest();Lake();Volcano();Mine();Snow();Sky();Evoker();
        Wither();Piglin();Skeleton();Magma();
        Crest();
        PlainForge();
        SoulEquipment();
        PurpleIron();
        Ice();
        Currency();
        SoulToGoldCoin();
        BossCore();
        Main1Gems();
        Cord();
        BossItem();
        SeaEquip();
        BlackForestEquip();
        LightingEquip();
        PlainRune();
        ForestRune();
        VolcanoRune();
        ManaRune();
        SnowRune();
        NetherRune();NetherPower();NetherArmor();NetherGem();NetherBow();NetherSwordModel();NetherWeapon();Ruby();
        PlainForestRune();LakeVolcanoRune();
        ManaArmor();
        Brewing();
        Spider();
        Sakura();
        Nature();
        Pickaxe();Axe();Ore();
        GoldCoinStore();
        NewGive();
        Field();
        Spring();
        EndRune(); EndRecall(); EndPower();
        Customized();
        GoldSmith();
        Giant();
        Earth();
        Blood();
        ManaBook();
        Slime();
        Taboo();
        Parkour();
        Castle();
        CastleCommon();
        SkyGemStore();
        LakeRune();
        QingMing();
        Food();
        RoseGoldStore();
        LabourDayStore();
    }
    public static void Plain() {
        ItemStack[] itemStacks = {
                ModItems.PlainRune.get().getDefaultInstance(),
                ModItems.PlainSword0.get().getDefaultInstance(),
                ModItems.PlainBow0.get().getDefaultInstance(),
                ModItems.PlainSceptre0.get().getDefaultInstance(),
                ModItems.PlainPower.get().getDefaultInstance(),
                ModItems.PlainArmorHelmet.get().getDefaultInstance(),
                ModItems.PlainArmorChest.get().getDefaultInstance(),
                ModItems.PlainArmorLeggings.get().getDefaultInstance(),
                ModItems.PlainArmorBoots.get().getDefaultInstance(),
                ModItems.PlainBracelet.get().getDefaultInstance(),
                ModItems.PlainManaBook.get().getDefaultInstance(),
                ModItems.PlainGem.get().getDefaultInstance()
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Plain,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.PlainSoul.get(),64));
                    add(new ItemStack(ModItems.GoldCoin.get(),1));
                }});
                case 1,2,3,4,5,6,7,8 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.PlainRune.get()));
                }});
                case 9,10 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.PlainRune.get(),1));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.PlainRune.get(),10));
                    add(new ItemStack(ModItems.CompleteGem.get()));
                }});
            }
        }
    }

    public static void Forest() {
        ItemStack[] itemStacks = {
                ModItems.ForestRune.get().getDefaultInstance(),
                ModItems.ForestSword0.get().getDefaultInstance(),
                ModItems.ForestBow0.get().getDefaultInstance(),
                ModItems.ForestPower.get().getDefaultInstance(),
                ModItems.ForestArmorHelmet.get().getDefaultInstance(),
                ModItems.ForestArmorChest.get().getDefaultInstance(),
                ModItems.ForestArmorLeggings.get().getDefaultInstance(),
                ModItems.ForestArmorBoots.get().getDefaultInstance(),
                ModItems.ForestBracelet.get().getDefaultInstance(),
                ModItems.ForestManaBook.get().getDefaultInstance(),
                ModItems.ForestGem.get().getDefaultInstance(),
                new ItemStack(Items.OAK_LOG,4),
                new ItemStack(Items.OAK_LOG,64),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Forest,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ForestSoul.get(),64));
                    add(new ItemStack(ModItems.GoldCoin.get(),3));
                }});
                case 1,2,3,4,5,6,7 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ForestRune.get()));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ForestRune.get(),5));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.PlainManaBook.get(),1));
                    add(new ItemStack(ModItems.ForestRune.get(),1));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ForestRune.get(),10));
                    add(new ItemStack(ModItems.CompleteGem.get()));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ForestSoul.get(),1));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ForestSoul.get(),16));
                }});
            }
        }
    }

    public static void Lake() {
        ItemStack[] itemStacks = {
                ModItems.LakeRune.get().getDefaultInstance(),
                ModItems.LakeSword0.get().getDefaultInstance(),
                ModItems.LakePower.get().getDefaultInstance(),
                ModItems.LakeArmorHelmet.get().getDefaultInstance(),
                ModItems.LakeArmorChest.get().getDefaultInstance(),
                ModItems.LakeArmorLeggings.get().getDefaultInstance(),
                ModItems.LakeArmorBoots.get().getDefaultInstance(),
                ModItems.LakeBracelet.get().getDefaultInstance(),
                ModItems.LakeManaBook.get().getDefaultInstance(),
                ModItems.LakeGem.get().getDefaultInstance(),

        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Lake,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.LakeSoul.get(),64));
                    add(new ItemStack(ModItems.GoldCoin.get(),5));
                }});
                case 1,2,3,4,5,6 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.LakeRune.get()));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.LakeRune.get(),5));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ForestManaBook.get(),1));
                    add(new ItemStack(ModItems.LakeRune.get(),1));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.LakeRune.get(),10));
                    add(new ItemStack(ModItems.CompleteGem.get()));
                }});
            }
        }
    }

    public static void Volcano() {
        ItemStack[] itemStacks = {
                ModItems.VolcanoRune.get().getDefaultInstance(),
                ModItems.VolcanoSword0.get().getDefaultInstance(),
                ModItems.VolcanoBow0.get().getDefaultInstance(),
                ModItems.VolcanoPower.get().getDefaultInstance(),
                ModItems.VolcanoArmorHelmet.get().getDefaultInstance(),
                ModItems.VolcanoArmorChest.get().getDefaultInstance(),
                ModItems.VolcanoArmorLeggings.get().getDefaultInstance(),
                ModItems.VolcanoArmorBoots.get().getDefaultInstance(),
                ModItems.VolcanoBracelet.get().getDefaultInstance(),
                ModItems.VolcanoManaBook.get().getDefaultInstance(),
                ModItems.VolcanoGem.get().getDefaultInstance(),

        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Volcano,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.VolcanoSoul.get(),64));
                    add(new ItemStack(ModItems.GoldCoin.get(),5));
                }});
                case 1,2,3,4,5,6,7 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.VolcanoRune.get()));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.VolcanoRune.get(),5));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.LakeManaBook.get(),1));
                    add(new ItemStack(ModItems.VolcanoRune.get(),1));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.VolcanoRune.get(),10));
                    add(new ItemStack(ModItems.CompleteGem.get()));
                }});
            }
        }
    }

    public static void Mine() {
        ItemStack[] itemStacks = {
                ModItems.MineRune.get().getDefaultInstance(),
                ModItems.MineSword0.get().getDefaultInstance(),
                ModItems.MineBow0.get().getDefaultInstance(),
                ModItems.MineShield.get().getDefaultInstance(),
                ModItems.MineArmorHelmet.get().getDefaultInstance(),
                ModItems.MineArmorChest.get().getDefaultInstance(),
                ModItems.MineArmorLeggings.get().getDefaultInstance(),
                ModItems.MineArmorBoots.get().getDefaultInstance(),
                ModItems.MineBracelet.get().getDefaultInstance(),
                ModItems.MineGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Mine,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.MineSoul.get(),64));
                    add(new ItemStack(ModItems.GoldCoin.get(),5));
                }});
                case 1,2,3,4,5,6,7 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.MineRune.get()));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.MineRune.get(),5));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.CompleteGem.get(),1));
                    add(new ItemStack(ModItems.MineRune.get(),10));
                }});
            }
        }
    }
    public static void Snow() {
        ItemStack[] itemStacks = {
                ModItems.SnowRune.get().getDefaultInstance(),
                ModItems.SnowSword0.get().getDefaultInstance(),
                ModItems.SnowPower.get().getDefaultInstance(),
                ModItems.SnowArmorHelmet.get().getDefaultInstance(),
                ModItems.SnowArmorChest.get().getDefaultInstance(),
                ModItems.SnowArmorLeggings.get().getDefaultInstance(),
                ModItems.SnowArmorBoots.get().getDefaultInstance(),
                ModItems.SnowBracelet.get().getDefaultInstance(),
                ModItems.SnowManaBook.get().getDefaultInstance(),
                ModItems.SnowGem.get().getDefaultInstance(),
                ModItems.SnowShield.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Snow,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SnowSoul.get(),64));
                    add(new ItemStack(ModItems.GoldCoin.get(),5));
                }});
                case 1,2,3,4,5,6 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SnowRune.get()));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SnowRune.get(),5));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.VolcanoManaBook.get(),1));
                    add(new ItemStack(ModItems.SnowRune.get(),1));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SnowRune.get(),10));
                    add(new ItemStack(ModItems.CompleteGem.get()));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SnowRune.get(),5));
                }});

            }
        }
    }
    public static void Sky() {
        ItemStack[] itemStacks = {
                ModItems.SkyRune.get().getDefaultInstance(),
                ModItems.SkyBow.get().getDefaultInstance(),
                ModItems.SkyBracelet.get().getDefaultInstance(),
                ModItems.SkyGem.get().getDefaultInstance(),
                ModItems.SkyArmorHelmet.get().getDefaultInstance(),
                ModItems.SkyArmorChest.get().getDefaultInstance(),
                ModItems.SkyArmorLeggings.get().getDefaultInstance(),
                ModItems.SkyArmorBoots.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Sky,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SkySoul.get(),64));
                    add(new ItemStack(ModItems.GoldCoin.get(),5));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SkyRune.get(),10));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SkyRune.get(),5));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.CompleteGem.get(),1));
                    add(new ItemStack(ModItems.SkyRune.get(),10));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SkyHelmetForgeDraw.get(),1));
                    add(new ItemStack(ModItems.SkyArmorHelmet.get(),1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SkyChestForgeDraw.get(),1));
                    add(new ItemStack(ModItems.SkyArmorChest.get(),1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SkyLeggingsForgeDraw.get(),1));
                    add(new ItemStack(ModItems.SkyArmorLeggings.get(),1));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SkyBootsForgeDraw.get(),1));
                    add(new ItemStack(ModItems.SkyArmorBoots.get(),1));
                }});
            }
        }
    }
    public static void Evoker() {
        ItemStack[] itemStacks = {
                ModItems.ManaBucket.get().getDefaultInstance(),
                ModItems.ManaBalance_Empty.get().getDefaultInstance(),
                ModItems.EvokerRune.get().getDefaultInstance(),
                ModItems.EvokerSword.get().getDefaultInstance(),
                ModItems.EvokerGem.get().getDefaultInstance(),
                ModItems.EvokerBook0.get().getDefaultInstance(),
                ModItems.EvokerBook1.get().getDefaultInstance(),
                ModItems.EvokerBook2.get().getDefaultInstance(),
                ModItems.EvokerBook3.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Evoker,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.EvokerSoul.get(),4));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.GoldCoin.get(),2));
                    add(new ItemStack(ModItems.GemPiece.get(),4));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ManaBucket.get(),16));
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(),2));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.EvokerRune.get(),5));
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(),2));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.EvokerRune.get(),4));
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(),4));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SnowManaBook.get(),1));
                    add(new ItemStack(ModItems.EvokerRune.get(),1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.EvokerBook0.get(),1));
                    add(new ItemStack(ModItems.EvokerRune.get(),2));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.EvokerBook1.get(),1));
                    add(new ItemStack(ModItems.EvokerRune.get(),3));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.EvokerBook2.get(),1));
                    add(new ItemStack(ModItems.EvokerRune.get(),5));
                }});
            }
        }
    }
    public static void Wither() {
        ItemStack[] itemStacks = {
                ModItems.WitherRune.get().getDefaultInstance(),
                ModItems.WitherSword0.get().getDefaultInstance(),
                ModItems.WitherGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Wither,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.witherBone.get(),64));
                    add(new ItemStack(ModItems.NetherSoul.get(),1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.WitherRune.get(),1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.CompleteGem.get(),1));
                    add(new ItemStack(ModItems.WitherRune.get(),2));
                }});
            }
        }
    }
    public static void Piglin() {
        ItemStack[] itemStacks = {
                ModItems.PiglinRune.get().getDefaultInstance(),
                ModItems.PiglinHelmet0.get().getDefaultInstance(),
                ModItems.PiglinGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Piglin,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.PigLinSoul.get(),64));
                    add(new ItemStack(ModItems.NetherSoul.get(),1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.PiglinRune.get(),1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.CompleteGem.get(),1));
                    add(new ItemStack(ModItems.PiglinRune.get(),2));
                }});
            }
        }
    }
    public static void Skeleton() {
        ItemStack[] itemStacks = {
                ModItems.NetherSkeletonRune.get().getDefaultInstance(),
                ModItems.WitherBow0.get().getDefaultInstance(),
                ModItems.NetherShield.get().getDefaultInstance(),
                ModItems.NetherSkeletonGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Skeleton,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.netherbonemeal.get(),64));
                    add(new ItemStack(ModItems.NetherSoul.get(),1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.NetherSkeletonRune.get(),1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.NetherSkeletonRune.get(),5));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.CompleteGem.get(),1));
                    add(new ItemStack(ModItems.NetherSkeletonRune.get(),2));
                }});
            }
        }
    }
    public static void Magma() {
        ItemStack[] itemStacks = {
                ModItems.MagmaRune.get().getDefaultInstance(),
                ModItems.MagmaSceptre0.get().getDefaultInstance(),
                ModItems.MagmaGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Magma,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.NetherMagmaPower.get(),64));
                    add(new ItemStack(ModItems.NetherSoul.get(),1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.MagmaRune.get(),1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.CompleteGem.get(),1));
                    add(new ItemStack(ModItems.MagmaRune.get(),2));
                }});
            }
        }
    }
    public static void Crest() {
        ItemStack[] itemStacks = {
                ModItems.PlainCrest1.get().getDefaultInstance(),
                ModItems.PlainCrest2.get().getDefaultInstance(),
                ModItems.PlainCrest3.get().getDefaultInstance(),
                ModItems.PlainCrest4.get().getDefaultInstance(),
                ModItems.ForestCrest1.get().getDefaultInstance(),
                ModItems.ForestCrest2.get().getDefaultInstance(),
                ModItems.ForestCrest3.get().getDefaultInstance(),
                ModItems.ForestCrest4.get().getDefaultInstance(),
                ModItems.LakeCrest1.get().getDefaultInstance(),
                ModItems.LakeCrest2.get().getDefaultInstance(),
                ModItems.LakeCrest3.get().getDefaultInstance(),
                ModItems.LakeCrest4.get().getDefaultInstance(),
                ModItems.VolcanoCrest1.get().getDefaultInstance(),
                ModItems.VolcanoCrest2.get().getDefaultInstance(),
                ModItems.VolcanoCrest3.get().getDefaultInstance(),
                ModItems.VolcanoCrest4.get().getDefaultInstance(),
                ModItems.MineCrest1.get().getDefaultInstance(),
                ModItems.MineCrest2.get().getDefaultInstance(),
                ModItems.MineCrest3.get().getDefaultInstance(),
                ModItems.MineCrest4.get().getDefaultInstance(),
                ModItems.SnowCrest1.get().getDefaultInstance(),
                ModItems.SnowCrest2.get().getDefaultInstance(),
                ModItems.SnowCrest3.get().getDefaultInstance(),
                ModItems.SnowCrest4.get().getDefaultInstance(),
                ModItems.SkyCrest1.get().getDefaultInstance(),
                ModItems.SkyCrest2.get().getDefaultInstance(),
                ModItems.SkyCrest3.get().getDefaultInstance(),
                ModItems.SkyCrest4.get().getDefaultInstance(),
                ModItems.ManaCrest1.get().getDefaultInstance(),
                ModItems.ManaCrest2.get().getDefaultInstance(),
                ModItems.ManaCrest3.get().getDefaultInstance(),
                ModItems.ManaCrest4.get().getDefaultInstance()
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Crest,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.PlainCrest0.get(),16));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.PlainCrest1.get(),16));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.PlainCrest2.get(),16));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.PlainCrest3.get(),16));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ForestCrest0.get(),16));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ForestCrest1.get(),16));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ForestCrest2.get(),16));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ForestCrest3.get(),16));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.LakeCrest0.get(),16));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.LakeCrest1.get(),16));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.LakeCrest2.get(),16));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.LakeCrest3.get(),16));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.VolcanoCrest0.get(),16));
                }});
                case 13 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.VolcanoCrest1.get(),16));
                }});
                case 14 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.VolcanoCrest2.get(),16));
                }});
                case 15 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.VolcanoCrest3.get(),16));
                }});
                case 16 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.MineCrest0.get(),16));
                }});
                case 17 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.MineCrest1.get(),16));
                }});
                case 18 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.MineCrest2.get(),16));
                }});
                case 19 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.MineCrest3.get(),16));
                }});
                case 20 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SnowCrest0.get(),16));
                }});
                case 21 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SnowCrest1.get(),16));
                }});
                case 22 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SnowCrest2.get(),16));
                }});
                case 23 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SnowCrest3.get(),16));
                }});
                case 24 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SkyCrest0.get(),16));
                }});
                case 25 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SkyCrest1.get(),16));
                }});
                case 26 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SkyCrest2.get(),16));
                }});
                case 27 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SkyCrest3.get(),16));
                }});
                case 28 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ManaCrest0.get(),16));
                }});
                case 29 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ManaCrest1.get(),16));
                }});
                case 30 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ManaCrest2.get(),16));
                }});
                case 31 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.ManaCrest3.get(),16));
                }});
            }
        }
    }
    public static void PlainForge() {
        ItemStack[] itemStacks = {
                ModItems.SpeIron.get().getDefaultInstance(),
                ModItems.SpeIron.get().getDefaultInstance(),
                ModItems.OpenSlot.get().getDefaultInstance(),
                new ItemStack(ModItems.MineSoul1.get(),16),
                ModItems.ForgingStone0.get().getDefaultInstance(),
                ModItems.ForgingStone1.get().getDefaultInstance(),
                ModItems.ForgingStone1.get().getDefaultInstance(),
                ModItems.ForgingStone1.get().getDefaultInstance(),
                ModItems.ForgingStone1.get().getDefaultInstance(),
                ModItems.ForgingStone2.get().getDefaultInstance(),
                ModItems.ForgeProtect.get().getDefaultInstance(),
                ModItems.ForgeImprove0.get().getDefaultInstance(),
                ModItems.ForgeImprove1.get().getDefaultInstance(),
                ModItems.ForgeEnhance0.get().getDefaultInstance(),
                ModItems.ForgeEnhance1.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Forge,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.MineSoul.get(),64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.MineSoul1.get(),16));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.GoldCoin.get(),16));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.MineSoul.get(),16));
                    add(new ItemStack(ModItems.GoldCoin.get(),1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.SilverCoin.get(),32));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(Items.DIAMOND,4));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(Items.EMERALD,4));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(Items.REDSTONE,16));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(Items.LAPIS_LAZULI,16));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.NetherQuartz.get(),16));
                    add(new ItemStack(ModItems.GoldCoin.get(),1));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.GoldCoin.get(),8));
                }});
                case 11,13 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.GoldCoin.get(),1));
                }});
                case 12,14 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.GoldCoin.get(),4));
                }});
            }
        }
    }
    public static void SoulEquipment() {
        ItemStack[] itemStacks = {
                ModItems.WorldSoulNote.get().getDefaultInstance(),
                ModItems.SoulSword.get().getDefaultInstance(),
                ModItems.SoulBow.get().getDefaultInstance(),
                ModItems.SoulSceptre.get().getDefaultInstance(),
                ModItems.SkillReset.get().getDefaultInstance(),
                ModItems.Dismantle.get().getDefaultInstance(),
                ModItems.TpToSakura.get().getDefaultInstance(),
                ModItems.TpToSky.get().getDefaultInstance(),
                ModItems.LifeTeleportTicket.get().getDefaultInstance(),
                ModItems.WindTeleportTicket.get().getDefaultInstance(),
                ModItems.StoneTeleportTicket.get().getDefaultInstance()
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.WorldSoul,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.WorldSoulNote.get(),1));
                }});
                case 1,2,3 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.WorldSoul3.get(),16));
                }});
                case 4,8,9,10 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.WorldSoul3.get(),4));
                }});
                case 5,6,7 -> tradeRecipeMap.put(itemStacks[i],new ArrayList<>(){{
                    add(new ItemStack(ModItems.WorldSoul2.get(),16));
                }});
            }
        }
    }
    public static void PurpleIron() {
        ItemStack[] itemStacks = {
                ModItems.PurpleIron.get().getDefaultInstance(),
                ModItems.PurpleIron.get().getDefaultInstance(),
                ModItems.GoldCoin.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.PurpleIron,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 16));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIronPiece.get(), 64));
                    add(new ItemStack(ModItems.MineSoul1.get(), 2));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIron.get(), 8));
                }});
            }
        }
    }
    public static void Ice() {
        ItemStack[] itemStacks = {
                ModItems.LeatherRune.get().getDefaultInstance(),
                ModItems.LeatherArmorHelmet.get().getDefaultInstance(),
                ModItems.LeatherArmorChest.get().getDefaultInstance(),
                ModItems.LeatherArmorLeggings.get().getDefaultInstance(),
                ModItems.LeatherArmorBoots.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Ice,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LeatherSoul.get(), 64));
                    add(new ItemStack(ModItems.GoldCoin.get(), 10));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LeatherRune.get(), 5));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LeatherRune.get(), 8));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LeatherRune.get(), 7));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LeatherRune.get(), 4));
                }});
            }
        }
    }
    public static void Currency() {
        ItemStack[] itemStacks = {
                ModItems.GoldCoin.get().getDefaultInstance(),
                ModItems.GoldCoin.get().getDefaultInstance(),
                ModItems.GoldCoin.get().getDefaultInstance(),
                ModItems.GoldCoin.get().getDefaultInstance(),
                ModItems.CompleteGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Currency,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SilverCoin.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GemPiece.get(), 9));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RunePiece.get(), 32));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MineSoul.get(), 64));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GemPiece.get(), 64));
                }});
            }
        }
    }
    public static void SoulToGoldCoin() {
        ItemStack[] itemStacks = {
                ModItems.GoldCoin.get().getDefaultInstance(),
                ModItems.GoldCoin.get().getDefaultInstance(),
                ModItems.GoldCoin.get().getDefaultInstance(),
                ModItems.GoldCoin.get().getDefaultInstance(),
                ModItems.GoldCoin.get().getDefaultInstance(),
                ModItems.GoldCoin.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.SoulToGoldCoin,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainSoul.get(), 64));
                    add(new ItemStack(ModItems.PlainSoul.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestSoul.get(), 64));
                    add(new ItemStack(ModItems.ForestSoul.get(), 32));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeSoul.get(), 64));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoSoul.get(), 64));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FieldSoul.get(), 64));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SnowSoul.get(), 32));
                }});
            }
        }
    }
    public static void BossCore() {
        ItemStack[] itemStacks = {
                ModItems.ForestBossPocket.get().getDefaultInstance(),
                ModItems.LakeBossPocket.get().getDefaultInstance(),
                ModItems.VolcanoBossPocket.get().getDefaultInstance(),
                ModItems.SkyBossPocket.get().getDefaultInstance(),
                ModItems.SnowBossPocket.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.BossCore,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestBossCore.get(), 12));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeBossCore.get(), 12));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoBossCore.get(), 12));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SkyBossCore.get(), 12));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SnowBossCore.get(), 12));
                }});
            }
        }
    }
    public static void Main1Gems() {
        ItemStack[] itemStacks = {
                ModItems.GemBag.get().getDefaultInstance(),
                ModItems.RandomGemPiece.get().getDefaultInstance(),
                ModItems.RandomGemPiece.get().getDefaultInstance(),
                ModItems.RandomGemPiece.get().getDefaultInstance(),
                ModItems.RandomGemPiece.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Main1Gems,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RandomGemPiece.get(), 4));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Main1BeltGem.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Main1BraceletGem.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Main1HandGem.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Main1necklaceGem.get(), 1));
                }});
            }
        }
    }
    public static void Cord() {
        ItemStack[] itemStacks = {
                ModItems.PlainCord.get().getDefaultInstance(),
                ModItems.ForestCord.get().getDefaultInstance(),
                ModItems.LakeCord.get().getDefaultInstance(),
                ModItems.VolcanoCord.get().getDefaultInstance(),
                ModItems.PlainForestCord.get().getDefaultInstance(),
                ModItems.LakeVolcanoCord.get().getDefaultInstance(),
                ModItems.FinalCord.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Cord,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainBracelet.get(), 1));
                    add(new ItemStack(ModItems.Main1Crystal.get(), 16));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestBracelet.get(), 1));
                    add(new ItemStack(ModItems.Main1Crystal.get(), 16));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeBracelet.get(), 1));
                    add(new ItemStack(ModItems.Main1Crystal.get(), 16));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoBracelet.get(), 1));
                    add(new ItemStack(ModItems.Main1Crystal.get(), 16));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainCord.get(), 1));
                    add(new ItemStack(ModItems.ForestCord.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeCord.get(), 1));
                    add(new ItemStack(ModItems.VolcanoCord.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeVolcanoCord.get(), 1));
                    add(new ItemStack(ModItems.PlainForestCord.get(), 1));
                }});
            }
        }
    }
    public static void BossItem() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.PlainBossSoul.get(),32),
                new ItemStack(ModItems.PlainBossSoul.get(),32),
                new ItemStack(ModItems.PlainBossSoul.get(),32),
                new ItemStack(ModItems.PlainBossSoul.get(),32),

                new ItemStack(ModItems.IceLoot.get(),6),
                new ItemStack(ModItems.IceLoot.get(),6),
                new ItemStack(ModItems.IceLoot.get(),12),

                new ItemStack(ModItems.DevilAttackSoul.get(),32),
                new ItemStack(ModItems.DevilSwiftSoul.get(),32),
                new ItemStack(ModItems.DevilManaSoul.get(),32),

                new ItemStack(ModItems.PurpleIronBud3.get(),1),
                new ItemStack(ModItems.PurpleIronBud3.get(),1),
                new ItemStack(ModItems.PurpleIronBud3.get(),1)
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.T1Equip,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainAttackRing0.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainManaAttackRing0.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainDefenceRing0.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainHealthRing0.get(), 1));
                }});

                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.IceSoul.get(), 64));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.IceHeart.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.IceCompleteGem.get(), 1));
                }});

                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.DevilSwiftSoul.get(), 32));
                    add(new ItemStack(ModItems.DevilManaSoul.get(), 32));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.DevilAttackSoul.get(), 32));
                    add(new ItemStack(ModItems.DevilManaSoul.get(), 32));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.DevilAttackSoul.get(), 32));
                    add(new ItemStack(ModItems.DevilSwiftSoul.get(), 32));
                }});

                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIronSword.get(), 1));
                }});

                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIronBow.get(), 1));
                }});

                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIronSceptre.get(), 1));
                }});
            }
        }
    }
    public static void SeaEquip() {
        ItemStack[] itemStacks = {
                ModItems.SeaSwordForgeDraw.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.SeaEquip,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SeaManaCore.get(), 1));
                    add(new ItemStack(ModItems.SeaSword4.get(), 1));
                    add(new ItemStack(ModItems.SeaSword3.get(), 1));
                    add(new ItemStack(ModItems.SeaSword2.get(), 1));
                    add(new ItemStack(ModItems.SeaSword1.get(), 1));
                }});
            }
        }
    }
    public static void BlackForestEquip() {
        ItemStack[] itemStacks = {
                ModItems.BlackForestSwordForgeDraw.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.BlackForestEquip,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BlackForestManaCore.get(), 1));
                    add(new ItemStack(ModItems.BlackForestSword4.get(), 1));
                    add(new ItemStack(ModItems.BlackForestSword3.get(), 1));
                    add(new ItemStack(ModItems.BlackForestSword2.get(), 1));
                    add(new ItemStack(ModItems.BlackForestSword1.get(), 1));
                }});
            }
        }
    }
    public static void LightingEquip() {
        ItemStack[] itemStacks = {
                ModItems.IslandChestForgeDraw.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.LightningEquip,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.IslandArmorHelmet.get(), 1));
                    add(new ItemStack(ModItems.IslandArmorChest.get(), 1));
                    add(new ItemStack(ModItems.IslandArmorLeggings.get(), 1));
                    add(new ItemStack(ModItems.IslandArmorBoots.get(), 1));
                }});
            }
        }
    }
    public static void PlainRune() {
        ItemStack[] itemStacks = {
                ModItems.PlainRune.get().getDefaultInstance(),
                ModItems.PlainRune0.get().getDefaultInstance(),
                ModItems.PlainRune1.get().getDefaultInstance(),
                ModItems.PlainRune2.get().getDefaultInstance(),
                ModItems.PlainRune3.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.PlainRune,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 1));
                    add(new ItemStack(ModItems.PlainSoul.get(), 64));
                }});
                case 1,2,3,4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RunePiece.get(), 24));
                    add(new ItemStack(ModItems.PlainRune.get(), 1));
                }});
            }
        }
    }
    public static void ForestRune() {
        ItemStack[] itemStacks = {
                ModItems.ForestRune.get().getDefaultInstance(),
                ModItems.ForestRune0.get().getDefaultInstance(),
                ModItems.ForestRune1.get().getDefaultInstance(),
                ModItems.ForestRune2.get().getDefaultInstance(),
                ModItems.ForestRune3.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.ForestRune,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 3));
                    add(new ItemStack(ModItems.ForestSoul.get(), 64));
                }});
                case 1,2,3,4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RunePiece.get(), 64));
                    add(new ItemStack(ModItems.ForestRune.get(), 1));
                }});
            }
        }
    }
    public static void VolcanoRune() {
        ItemStack[] itemStacks = {
                ModItems.VolcanoRune.get().getDefaultInstance(),
                ModItems.volcanoRune0.get().getDefaultInstance(),
                ModItems.volcanoRune1.get().getDefaultInstance(),
                ModItems.volcanoRune2.get().getDefaultInstance(),
                ModItems.volcanoRune3.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.VolcanoRune,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 5));
                    add(new ItemStack(ModItems.VolcanoSoul.get(), 64));
                }});
                case 1,2,3,4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RunePiece.get(), 64));
                    add(new ItemStack(ModItems.VolcanoRune.get(), 1));
                }});
            }
        }
    }
    public static void ManaRune() {
        ItemStack[] itemStacks = {
                ModItems.manaRune0.get().getDefaultInstance(),
                ModItems.manaRune1.get().getDefaultInstance(),
                ModItems.manaRune2.get().getDefaultInstance(),
                ModItems.manaRune3.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.ManaRune,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0,1,2,3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RunePiece.get(), 64));
                    add(new ItemStack(ModItems.EvokerRune.get(), 2));
                }});
            }
        }
    }
    public static void SnowRune() {
        ItemStack[] itemStacks = {
                ModItems.SnowRune.get().getDefaultInstance(),
                ModItems.SnowRune0.get().getDefaultInstance(),
                ModItems.SnowRune1.get().getDefaultInstance(),
                ModItems.SnowRune2.get().getDefaultInstance(),
                ModItems.SnowRune3.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.SnowRune,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 5));
                    add(new ItemStack(ModItems.SnowSoul.get(), 64));
                }});
                case 1,2,3,4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RunePiece.get(), 64));
                    add(new ItemStack(ModItems.SnowRune.get(), 1));
                }});
            }
        }
    }
    public static void NetherRune() {
        ItemStack[] itemStacks = {
                ModItems.NetherRune0.get().getDefaultInstance(),
                ModItems.NetherRune1.get().getDefaultInstance(),
                ModItems.NetherRune2.get().getDefaultInstance(),
                ModItems.NetherRune3.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.NetherRune,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0,1,2,3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RunePiece.get(), 64));
                    add(new ItemStack(ModItems.NetherRune.get(), 1));
                }});
            }
        }
    }
    public static void NetherPower() {
        ItemStack[] itemStacks = {
                ModItems.PowerModel.get().getDefaultInstance(),
                ModItems.WitherBonePower.get().getDefaultInstance(),
                ModItems.PigLinPower.get().getDefaultInstance(),
                ModItems.WitherBoneMealPower.get().getDefaultInstance(),
                ModItems.MagmaPower.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.NetherPower,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Ruby.get(), 64));
                    add(new ItemStack(ModItems.EvokerRune.get(), 2));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.witherBone.get(), 64));
                    add(new ItemStack(ModItems.PowerModel.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PigLinSoul.get(), 64));
                    add(new ItemStack(ModItems.PowerModel.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.netherbonemeal.get(), 64));
                    add(new ItemStack(ModItems.PowerModel.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NetherMagmaPower.get(), 64));
                    add(new ItemStack(ModItems.PowerModel.get(), 1));
                }});
            }
        }
    }
    public static void NetherArmor() {
        ItemStack[] itemStacks = {
                ModItems.NetherHelmetForgeDraw.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.NetherArmor,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NetherArmorBoots.get(), 64));
                    add(new ItemStack(ModItems.NetherArmorLeggings.get(), 2));
                    add(new ItemStack(ModItems.NetherArmorChest.get(), 2));
                    add(new ItemStack(ModItems.NetherArmorHelmet.get(), 2));
                }});
            }
        }
    }
    public static void NetherGem() {
        ItemStack[] itemStacks = {
                ModItems.NetherGemPieceBag1.get().getDefaultInstance(),
                ModItems.NetherGemPieceBag2.get().getDefaultInstance(),
                ModItems.NetherGemPieceBag3.get().getDefaultInstance(),
                ModItems.NetherGemPieceBag4.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.NetherGem,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NetherGemPiece.get(), 3));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NetherGemPiece.get(), 9));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NetherGemPiece.get(), 27));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NetherGemPiece.get(), 64));
                    add(new ItemStack(ModItems.NetherGemPiece.get(), 64));
                }});
            }
        }
    }
    public static void NetherBow() {
        ItemStack[] itemStacks = {
                ModItems.QuartzRune.get().getDefaultInstance(),
                ModItems.NetherBow.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.NetherBow,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 25));
                    add(new ItemStack(ModItems.QuartzSoul.get(), 5));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NetherBow.get(), 1));
                    add(new ItemStack(ModItems.QuartzRune.get(), 1));
                    add(new ItemStack(ModItems.VolcanoBow3.get(), 1));
                }});
            }
        }
    }
    public static void NetherSwordModel() {
        ItemStack[] itemStacks = {
                ModItems.NetherSwordModel.get().getDefaultInstance(),
                ModItems.NetherSabreModel.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.NetherSwordModel,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0,1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 10));
                    add(new ItemStack(ModItems.Ruby.get(), 64));
                }});
            }
        }
    }
    public static void NetherWeapon() {
        ItemStack[] itemStacks = {
                ModItems.NetherRune.get().getDefaultInstance(),
                ModItems.ManaSword.get().getDefaultInstance(),
                ModItems.QuartzRune.get().getDefaultInstance(),
                ModItems.QuartzSword.get().getDefaultInstance(),
                ModItems.NetherPower.get().getDefaultInstance(),
                ModItems.QuartzSabre.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.NetherWeapon,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 10));
                    add(new ItemStack(ModItems.NetherSoul.get(), 5));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NetherRune.get(), 2));
                    add(new ItemStack(ModItems.NetherSwordModel.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 25));
                    add(new ItemStack(ModItems.QuartzSoul.get(), 5));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 10));
                    add(new ItemStack(ModItems.NetherSoul.get(), 5));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.QuartzRune.get(), 10));
                    add(new ItemStack(ModItems.NetherRune.get(), 5));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.QuartzRune.get(), 64));
                    add(new ItemStack(ModItems.NetherSabreModel.get(), 64));
                }});

            }
        }
    }
    public static void Ruby() {
        ItemStack[] itemStacks = {
                ModItems.NetherSoul.get().getDefaultInstance(),
                ModItems.QuartzSoul.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Ruby,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 10));
                    add(new ItemStack(ModItems.Ruby.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 25));
                    add(new ItemStack(ModItems.NetherQuartz.get(), 64));
                }});
            }
        }
    }
    public static void PlainForestRune() {
        ItemStack[] itemStacks = {
                ModItems.plainmana.get().getDefaultInstance(),
                ModItems.plainmana.get().getDefaultInstance(),
                ModItems.plainmana.get().getDefaultInstance(),
                ModItems.plainmana.get().getDefaultInstance(),
                ModItems.forestmana.get().getDefaultInstance(),
                ModItems.forestmana.get().getDefaultInstance(),
                ModItems.forestmana.get().getDefaultInstance(),
                ModItems.forestmana.get().getDefaultInstance(),
                ModItems.LifeManaGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.PlainForestRune,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.PlainArmorHelmet.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.PlainArmorChest.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.PlainArmorLeggings.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.PlainArmorBoots.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.ForestArmorHelmet.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.ForestArmorChest.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.ForestArmorLeggings.get(), 1));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.ForestArmorBoots.get(), 1));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 10));
                    add(new ItemStack(ModItems.PlainGem.get(), 1));
                    add(new ItemStack(ModItems.ForestGem.get(), 1));
                }});
            }
        }
    }
    public static void LakeVolcanoRune() {
        ItemStack[] itemStacks = {
                ModItems.lakemana.get().getDefaultInstance(),
                ModItems.lakemana.get().getDefaultInstance(),
                ModItems.lakemana.get().getDefaultInstance(),
                ModItems.lakemana.get().getDefaultInstance(),
                ModItems.volcanomana.get().getDefaultInstance(),
                ModItems.volcanomana.get().getDefaultInstance(),
                ModItems.volcanomana.get().getDefaultInstance(),
                ModItems.volcanomana.get().getDefaultInstance(),
                ModItems.ObsiManaGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.LakeVolcanoRune,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.LakeArmorHelmet.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.LakeArmorChest.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.LakeArmorLeggings.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.LakeArmorBoots.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.VolcanoArmorHelmet.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.VolcanoArmorChest.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.VolcanoArmorLeggings.get(), 1));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.VolcanoArmorBoots.get(), 1));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 10));
                    add(new ItemStack(ModItems.LakeGem.get(), 1));
                    add(new ItemStack(ModItems.VolcanoGem.get(), 1));
                }});
            }
        }
    }
    public static void ManaArmor() {
        ItemStack[] itemStacks = {
                ModItems.LifeManaArmorHelmet.get().getDefaultInstance(),
                ModItems.LifeManaArmorChest.get().getDefaultInstance(),
                ModItems.LifeManaArmorLeggings.get().getDefaultInstance(),
                ModItems.LifeManaArmorBoots.get().getDefaultInstance(),
                ModItems.ObsiManaArmorHelmet.get().getDefaultInstance(),
                ModItems.ObsiManaArmorChest.get().getDefaultInstance(),
                ModItems.ObsiManaArmorLeggings.get().getDefaultInstance(),
                ModItems.ObsiManaArmorBoots.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.ManaArmor,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0,1,2,3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.forestmana.get(), 1));
                    add(new ItemStack(ModItems.plainmana.get(), 1));
                }});
                case 4,5,6,7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.volcanomana.get(), 1));
                    add(new ItemStack(ModItems.lakemana.get(), 1));
                }});
            }
        }
    }
    public static void Brewing() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.BrewingNote.get(), 1),
                new ItemStack(ModItems.Solidifier.get(), 4),
                new ItemStack(ModItems.Purifier.get(), 4),
                new ItemStack(ModItems.Solidifier.get(), 8),
                new ItemStack(ModItems.Purifier.get(), 8),
                new ItemStack(ModItems.Solidifier.get(), 16),
                new ItemStack(ModItems.Purifier.get(), 16),
                new ItemStack(ModItems.Stabilizer.get(), 8),
                new ItemStack(ModItems.Concentrater.get(), 8),
                new ItemStack(Items.GLASS_BOTTLE, 64),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Brewing,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 12));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.ANDESITE, 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.DIORITE, 64));
                }});
                case 3,4,7,8,9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 1));
                    add(new ItemStack(Items.ANDESITE, 64));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 1));
                    add(new ItemStack(Items.DIORITE, 64));
                }});
            }
        }
    }
    public static void Spider() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.SpiderRune.get(), 1),
                new ItemStack(ModItems.SHelmet.get(), 1),
                new ItemStack(ModItems.SChest.get(), 1),
                new ItemStack(ModItems.SLeggings.get(), 1),
                new ItemStack(ModItems.SBoots.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Spider,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 8));
                    add(new ItemStack(ModItems.SpiderSoul.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpiderRune.get(), 5));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpiderRune.get(), 8));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpiderRune.get(), 7));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpiderRune.get(), 4));
                }});
            }
        }
    }
    public static void Sakura() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.SakuraPocket.get(), 1),
                new ItemStack(ModItems.WheatPocket.get(), 1),
                new ItemStack(ModItems.SakuraGem.get(), 1),
                new ItemStack(ModItems.ShipGem.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Sakura,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SakuraPetal.get(), 12));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Wheat.get(), 12));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CompleteGem.get(), 2));
                    add(new ItemStack(ModItems.SakuraPetal.get(), 64));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CompleteGem.get(), 2));
                    add(new ItemStack(ModItems.ShipPiece.get(), 64));
                }});
            }
        }
    }

    public static void Nature() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.NaturalCore.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Nature,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.WHEAT, 64));
                    add(new ItemStack(Items.CARROT, 64));
                    add(new ItemStack(Items.POTATO, 64));
                    add(new ItemStack(Items.BEETROOT, 64));
                    add(new ItemStack(Items.TORCHFLOWER, 64));
                    add(new ItemStack(Items.SWEET_BERRIES, 64));
                }});
            }
        }
    }
    public static void Pickaxe() {
        ItemStack potion = new ItemStack(Items.POTION);
        potion.getOrCreateTag().putString("Potion","minecraft:long_night_vision");

        ItemStack[] itemStacks = {
                new ItemStack(Items.STONE_PICKAXE, 1),
                new ItemStack(Items.IRON_PICKAXE, 1),
                potion,
                new ItemStack(Items.DIAMOND_PICKAXE, 1),
                new ItemStack(Items.NETHERITE_PICKAXE, 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Pickaxe,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SilverCoin.get(), 16));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SilverCoin.get(), 8));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 4));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 16));
                }});
            }
        }
    }

    public static void Axe() {
        ItemStack[] itemStacks = {
                new ItemStack(Items.STONE_AXE, 1),
                new ItemStack(Items.IRON_AXE, 1),
                new ItemStack(Items.GOLDEN_AXE, 1),
                new ItemStack(Items.DIAMOND_AXE, 1),
                new ItemStack(Items.NETHERITE_AXE, 1),
                new ItemStack(Items.OAK_LOG, 1),
                new ItemStack(Items.OAK_LOG, 1),
                new ItemStack(Items.OAK_LOG, 1),
                new ItemStack(Items.OAK_LOG, 1),
                new ItemStack(Items.OAK_LOG, 1),
                new ItemStack(Items.OAK_LOG, 1),
                new ItemStack(Items.OAK_LOG, 1),
                new ItemStack(Items.OAK_LOG, 64),
                new ItemStack(Items.OAK_LOG, 64),
                new ItemStack(Items.OAK_LOG, 64),
                new ItemStack(Items.OAK_LOG, 64),
                new ItemStack(Items.OAK_LOG, 64),
                new ItemStack(Items.OAK_LOG, 64),
                new ItemStack(Items.OAK_LOG, 64),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Axe,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SilverCoin.get(), 16));
                }});
                case 1,2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 8));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 16));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.SPRUCE_LOG, 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.BIRCH_LOG, 1));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.JUNGLE_LOG, 1));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.ACACIA_LOG, 1));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.DARK_OAK_LOG, 1));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.MANGROVE_LOG, 1));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.CHERRY_LOG, 1));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.SPRUCE_LOG, 64));
                }});
                case 13 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.BIRCH_LOG, 64));
                }});
                case 14 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.JUNGLE_LOG, 64));
                }});
                case 15 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.ACACIA_LOG, 64));
                }});
                case 16 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.DARK_OAK_LOG, 64));
                }});
                case 17 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.MANGROVE_LOG, 64));
                }});
                case 18 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.CHERRY_LOG, 64));
                }});
            }
        }
    }

    public static void Ore() {
        ItemStack[] itemStacks = {
                new ItemStack(Items.COAL, 4),
                new ItemStack(Items.COAL, 4),
                new ItemStack(Items.COAL, 16),
                new ItemStack(Items.COAL, 16),
                new ItemStack(ModItems.OreRune.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Ore,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.LAPIS_LAZULI, 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.REDSTONE, 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.DIAMOND, 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.EMERALD, 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.EMERALD, 4));
                    add(new ItemStack(Items.DIAMOND, 4));
                    add(new ItemStack(Items.REDSTONE, 16));
                    add(new ItemStack(Items.LAPIS_LAZULI, 16));
                }});
            }
        }
    }

    public static void GoldCoinStore() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.GemBag.get(), 2),
                new ItemStack(ModItems.WorldSoul2.get(), 2),
                new ItemStack(ModItems.MineHat.get(), 1),
                new ItemStack(ModItems.U_Disk.get(), 1),
                new ItemStack(ModItems.FireWorkGun.get(), 1),
                new ItemStack(ModItems.BackPackTickets.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.GoldCoinStore,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0,1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 16));
                }});
                case 2,3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 32));
                }});
                case 4,5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 64));
                }});
            }
        }
    }

    public static void NewGive() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.ID_Card.get(), 1),
                new ItemStack(ModItems.SmartPhone.get(), 1),
                new ItemStack(ModItems.BackSpawn.get(), 1),
                new ItemStack(ModItems.UnCommonLotteries.get(), 15),
                new ItemStack(ModItems.UnCommonLotteries.get(), 15),
                PatchouliAPI.get().getBookStack(new ResourceLocation(Utils.MOD_ID,"guide")),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.NewGive,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0,1,2,5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SilverCoin.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FantasyBracelet.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FantasyMedal.get(), 1));
                }});
            }
        }
    }
    public static void Field() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.FieldRune.get(), 1),
                new ItemStack(ModItems.FieldSword0.get(), 1),
                new ItemStack(ModItems.FieldGem.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Field,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FieldSoul.get(), 64));
                    add(new ItemStack(ModItems.GoldCoin.get(), 5));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FieldRune.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CompleteGem.get(), 1));
                    add(new ItemStack(ModItems.FieldRune.get(), 10));
                }});
            }
        }
    }
    public static void Spring() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.FireCracker.get(), 1),
                new ItemStack(ModItems.SpringGoldCoin.get(), 1),
                new ItemStack(ModItems.SpringHand0.get(), 1),
                new ItemStack(ModItems.SpringBelt0.get(), 1),
                new ItemStack(ModItems.SpringNecklace0.get(), 1),
                new ItemStack(ModItems.SpringGoldCoin.get(), 4),
                new ItemStack(ModItems.SpringScale0.get(), 1),
                new ItemStack(ModItems.SpringScale1.get(), 1),
                new ItemStack(ModItems.SpringScale2.get(), 1),
                new ItemStack(ModItems.SpringScale3.get(), 1),
                new ItemStack(ModItems.SpringGoldCoin.get(), 1),
                new ItemStack(ModItems.SpringGoldCoin.get(), 1),
                new ItemStack(ModItems.SpringGoldCoin.get(), 4),
                new ItemStack(ModItems.DragonPrefix.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Spring,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringMoney.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringMoney.get(), 64));
                }});
                case 2,3,4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringGoldCoin.get(), 4));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringRing0.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringPiece.get(), 64));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringScale0.get(), 1));
                    add(new ItemStack(ModItems.SpringPiece.get(), 64));
                    add(new ItemStack(ModItems.SpringPiece.get(), 64));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringScale1.get(), 1));
                    add(new ItemStack(ModItems.SpringPiece.get(), 64));
                    add(new ItemStack(ModItems.SpringPiece.get(), 64));
                    add(new ItemStack(ModItems.SpringPiece.get(), 64));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringScale2.get(), 1));
                    add(new ItemStack(ModItems.SpringPiece.get(), 64));
                    add(new ItemStack(ModItems.SpringPiece.get(), 64));
                    add(new ItemStack(ModItems.SpringPiece.get(), 64));
                    add(new ItemStack(ModItems.SpringPiece.get(), 64));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringPiece.get(), 32));
                    add(new ItemStack(ModItems.SpringPiece.get(), 64));
                    add(new ItemStack(ModItems.SpringPiece.get(), 64));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringSoul.get(), 32));
                    add(new ItemStack(ModItems.SpringSoul.get(), 64));
                    add(new ItemStack(ModItems.SpringSoul.get(), 64));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringHeart.get(), 1));
                }});
                case 13 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringMoney.get(), 64));
                }});
            }
        }
    }
    public static void EndRune() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.EndRune0.get(), 1),
                new ItemStack(ModItems.EndRune1.get(), 1),
                new ItemStack(ModItems.EndRune2.get(), 1),
                new ItemStack(ModItems.EndRune3.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.EndRune,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                default -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RunePiece.get(), 64));
                    add(new ItemStack(ModItems.RecallPiece.get(), 64));
                }});
            }
        }
    }

    public static void Customized() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.LXYZOSword.get(), 1),
                new ItemStack(ModItems.ShangMengLi_Sceptre.get(), 1),
                new ItemStack(ModItems.XiangLiPickaxe.get(), 1),
                new ItemStack(ModItems.ZeusCurios.get(), 1),
                new ItemStack(ModItems.ZeusSword.get(), 1),
                new ItemStack(ModItems.ShangMengLiCurios.get(), 1),
                new ItemStack(ModItems.LiuLiXianCurios.get(), 1),
                new ItemStack(ModItems.VeryNewCurios.get(), 1),
                new ItemStack(ModItems.ShowDickerBow.get(), 1),
                new ItemStack(ModItems.LiuLiXianSword.get(), 1),
                new ItemStack(ModItems.ShangMengLiCurios1.get(), 1),
                new ItemStack(ModItems.GuangYiBow.get(), 1),
                new ItemStack(ModItems.FengxiaojuCurios.get(), 1),
                new ItemStack(ModItems.XiangLiSmoke.get(), 1),
                new ItemStack(ModItems.WcBow.get(), 1),
                new ItemStack(ModItems.XiangLiCurios.get(), 1),
                new ItemStack(ModItems.BlackFeisaSceptre.get(), 1),
                new ItemStack(ModItems.EliaoiBook.get(), 1),
                new ItemStack(ModItems.EliaoiCurios.get(), 1),
                new ItemStack(ModItems.LiuLiXianCurios1.get(), 1),
                new ItemStack(ModItems.ShangMengLiCurios2.get(), 1),
                new ItemStack(ModItems.BlackFeisaCurios.get(), 1),
                new ItemStack(ModItems.QiFuBow.get(), 1),
                new ItemStack(ModItems.BlackFeisaCurios2.get(), 1),
                new ItemStack(ModItems.ShangMengLiSword.get(), 1),
                new ItemStack(ModItems.YuanShiRenSceptre.get(), 1),
                new ItemStack(ModItems.YuanShiRenCurios.get(), 1),
                new ItemStack(ModItems.QiFuCurios.get(), 1),
                new ItemStack(ModItems.BlackFeisaCurios3.get(), 1),
                new ItemStack(ModItems.CgswdCurios.get(), 1),
                new ItemStack(ModItems.LeiyanCurios.get(), 1),
                new ItemStack(ModItems.CgswdSceptre.get(), 1),
                new ItemStack(ModItems.YxwgBow.get(), 1),
                new ItemStack(ModItems.MyMissionBow.get(), 1),
                new ItemStack(ModItems.YxwgCurios.get(), 1),
                new ItemStack(ModItems.YxwgCurios1.get(), 1),
                new ItemStack(ModItems.SoraCurios1.get(), 1),
                new ItemStack(ModItems.MyMissionCurios.get(), 1),
                new ItemStack(ModItems.MyMissionCurios1.get(), 1),
                new ItemStack(ModItems.LeiyanBow.get(), 1),
                new ItemStack(ModItems.FengxiaojuCurios_1.get(), 1),
                new ItemStack(ModItems.EliaoiCurios1.get(), 1),
                new ItemStack(ModItems.LiulixianCurios2.get(), 1),
                new ItemStack(ModItems.SoraCurios2.get(), 1),
                new ItemStack(ModItems.AttackCurios0.get(), 1),
                new ItemStack(ModItems.BowCurios0.get(), 1),
                new ItemStack(ModItems.ManaCurios0.get(), 1),
                new ItemStack(ModItems.YxwgCurios2.get(), 1),
                new ItemStack(ModItems.WcCurios.get(), 1),
                new ItemStack(ModItems.QiFuCurios1.get(), 1),
                new ItemStack(ModItems.ShangmengliCurios3.get(), 1),
                new ItemStack(ModItems.BlackFeisaCurios4.get(), 1),
                new ItemStack(ModItems.SoraSword.get(), 1),
                new ItemStack(ModItems.EliaoiCurios2.get(), 1),
                new ItemStack(ModItems.ZuosiCurios.get(), 1),
                new ItemStack(ModItems.LiulixianCurios3.get(), 1),
                new ItemStack(ModItems.BlackFeisaCurios5.get(), 1),

        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Customized,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LXYZOSwordPaper.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ShangMengLiSceptre_Paper.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.XiangLiPickaxe_Paper.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Crush1CuriosPaper.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ZeusSwordPaper.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ShangMengLiCuriosPaper.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LiuLiXianCuriosPaper.get(), 1));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VeryNewCuriosPaper.get(), 1));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ShowDickerBowPaper.get(), 1));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LiuLiXianSwordPaper.get(), 1));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ShangMengLiCurios1Paper.get(), 1));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GuangYiBowPaper.get(), 1));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FengxiaojuCuriosPaper.get(), 1));
                }});
                case 13 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.XiangLiSmokePaper.get(), 1));
                }});
                case 14 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WcBowPaper.get(), 1));
                }});
                case 15 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.XiangLiCuriosPaper.get(), 1));
                }});
                case 16 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BlackFeisaSceptrePaper.get(), 1));
                }});
                case 17 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EliaoiBookPaper.get(), 1));
                }});
                case 18 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EliaoiCuriosPaper.get(), 1));
                }});
                case 19 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LiuLiXianCurios1Paper.get(), 1));
                }});
                case 20 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ShangMengLiCurios2Paper.get(), 1));
                }});
                case 21 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BlackFeisaCuriosPaper.get(), 1));
                }});
                case 22 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.QiFuSceptrePaper.get(), 1));
                }});
                case 23 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BlackFeisaCurios2Paper.get(), 1));
                }});
                case 24 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ShangMengLiSwordPaper.get(), 1));
                }});
                case 25 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.YuanShiRenSceptrePaper.get(), 1));
                }});
                case 26 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.YuanShiRenCuriosPaper.get(), 1));
                }});
                case 27 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.QiFuCuriosPaper.get(), 1));
                }});
                case 28 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BlackFeisaCurios3Paper.get(), 1));
                }});
                case 29 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CgswdCuriosPaper.get(), 1));
                }});
                case 30 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LeiyanCuriosPaper.get(), 1));
                }});
                case 31 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CgswdSceptrePaper.get(), 1));
                }});
                case 32 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.YxwgBowPaper.get(), 1));
                }});
                case 33 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MyMissionBowPaper.get(), 1));
                }});
                case 34 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.YxwgCuriosPaper.get(), 1));
                }});
                case 35 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.YxwgCurios1Paper.get(), 1));
                }});
                case 36 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SoraCurios1Paper.get(), 1));
                }});
                case 37 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MyMissionCuriosPaper.get(), 1));
                }});
                case 38 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MyMissionCurios1Paper.get(), 1));
                }});
                case 39 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LeiyanBowPaper.get(), 1));
                }});
                case 40 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FengxiaojuCurios_1Paper.get(), 1));
                }});
                case 41 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EliaoiCurios1Paper.get(), 1));
                }});
                case 42 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LiulixianCurios2Paper.get(), 1));
                }});
                case 43 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SoraCurios2Paper.get(), 1));
                }});
                case 44 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.AttackCurios0.get(), 1));
                }});
                case 45 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BowCurios0.get(), 1));
                }});
                case 46 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaCurios0.get(), 1));
                }});
                case 47 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.YxwgCurios2Paper.get(), 1));
                }});
                case 48 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WcCuriosPaper.get(), 1));
                }});
                case 49 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.QiFuCurios1Paper.get(), 1));
                }});
                case 50 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ShangmengliCurios3Paper.get(), 1));
                }});
                case 51 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BlackFeisaCurios4Paper.get(), 1));
                }});
                case 52 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SoraSwordPaper.get(), 1));
                }});
                case 53 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EliaoiCurios2Paper.get(), 1));
                }});
                case 54 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ZuosiCuriosPaper.get(), 1));
                }});
                case 55 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LiulixianCurios3Paper.get(), 1));
                }});
                case 56 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BlackFeisaCurios5Paper.get(), 1));
                }});
            }
        }
    }
    public static void GoldSmith() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.Boss2AttackRing0.get(), 1),
                new ItemStack(ModItems.Boss2AttackRing1.get(), 1),
                new ItemStack(ModItems.Boss2AttackRing2.get(), 1),
                new ItemStack(ModItems.Boss2AttackRing3.get(), 1),
                new ItemStack(ModItems.Boss2ManaAttackRing0.get(), 1),
                new ItemStack(ModItems.Boss2ManaAttackRing1.get(), 1),
                new ItemStack(ModItems.Boss2ManaAttackRing2.get(), 1),
                new ItemStack(ModItems.Boss2ManaAttackRing3.get(), 1),
                new ItemStack(ModItems.Boss2DefenceRing0.get(), 1),
                new ItemStack(ModItems.Boss2DefenceRing1.get(), 1),
                new ItemStack(ModItems.Boss2DefenceRing2.get(), 1),
                new ItemStack(ModItems.Boss2DefenceRing3.get(), 1),
                new ItemStack(ModItems.Boss2HealthRing0.get(), 1),
                new ItemStack(ModItems.Boss2HealthRing1.get(), 1),
                new ItemStack(ModItems.Boss2HealthRing2.get(), 1),
                new ItemStack(ModItems.Boss2HealthRing3.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.GoldSmith,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainAttackRing3.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2AttackRing0.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2AttackRing1.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2AttackRing2.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainManaAttackRing3.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2ManaAttackRing0.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2ManaAttackRing1.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2ManaAttackRing2.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainDefenceRing3.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2DefenceRing0.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2DefenceRing1.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2DefenceRing2.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainHealthRing3.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 13 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2HealthRing0.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 14 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2HealthRing1.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 15 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2HealthRing2.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
            }
        }
    }
    public static void Giant() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.GiantTicket.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Giant,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                default -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 5));
                }});
            }
        }
    }
    public static void Blood() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.ManaBalance_Empty.get(), 1),
                new ItemStack(ModItems.BloodManaRune.get(), 1),
                new ItemStack(ModItems.ManaShield.get(), 1),
                new ItemStack(ModItems.ManaKnife.get(), 1),
                new ItemStack(ModItems.BloodManaArmorHelmet.get(), 1),
                new ItemStack(ModItems.BloodManaArmorChest.get(), 1),
                new ItemStack(ModItems.BloodManaArmorLeggings.get(), 1),
                new ItemStack(ModItems.BloodManaArmorBoots.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Blood,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GemPiece.get(), 4));
                    add(new ItemStack(ModItems.GoldCoin.get(), 2));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.BloodManaSoul.get(), 64));
                }});
                case 2,3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BloodManaRune.get(), 8));
                }});
                case 4,5,6,7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BloodManaRune.get(), 5));
                }});
            }
        }
    }
    public static void Earth() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.ManaBalance_Empty.get(), 1),
                new ItemStack(ModItems.EarthManaRune.get(), 1),
                new ItemStack(ModItems.EarthPower.get(), 1),
                new ItemStack(ModItems.ManaKnife.get(), 1),
                new ItemStack(ModItems.EarthManaArmorHelmet.get(), 1),
                new ItemStack(ModItems.EarthManaArmorChest.get(), 1),
                new ItemStack(ModItems.EarthManaArmorLeggings.get(), 1),
                new ItemStack(ModItems.EarthManaArmorBoots.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Earth,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GemPiece.get(), 4));
                    add(new ItemStack(ModItems.GoldCoin.get(), 2));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.EarthManaSoul.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EarthManaRune.get(), 8));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EarthManaRune.get(), 8));
                }});
                case 4,5,6,7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EarthManaRune.get(), 5));
                }});
            }
        }
    }
    public static void ManaBook() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.WitherBook.get(), 1),
                new ItemStack(ModItems.EarthBook.get(), 1),
                new ItemStack(ModItems.EarthBook.get(), 1),
                new ItemStack(ModItems.EarthBook.get(), 1),
                new ItemStack(ModItems.IceBook.get(), 1),
                new ItemStack(ModItems.IceBook.get(), 1),
                new ItemStack(ModItems.IceBook.get(), 1),
                new ItemStack(ModItems.IceBook.get(), 1),
                new ItemStack(ModItems.GoldenBook.get(), 1),
                new ItemStack(ModItems.GoldenBook.get(), 1),
                new ItemStack(ModItems.GoldenBook.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.ManaBook,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WitherRune.get(), 5));
                    add(new ItemStack(ModItems.EvokerBook3.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EarthManaRune.get(), 8));
                    add(new ItemStack(ModItems.WitherBook.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EarthManaRune.get(), 2));
                    add(new ItemStack(ModItems.IceBook.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EarthManaRune.get(), 2));
                    add(new ItemStack(ModItems.GoldenBook.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.IceHeart.get(), 2));
                    add(new ItemStack(ModItems.EvokerBook3.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.IceHeart.get(), 2));
                    add(new ItemStack(ModItems.WitherBook.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.IceHeart.get(), 2));
                    add(new ItemStack(ModItems.EarthBook.get(), 1));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.IceHeart.get(), 2));
                    add(new ItemStack(ModItems.GoldenBook.get(), 1));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.WitherBook.get(), 1));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.EarthBook.get(), 1));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                    add(new ItemStack(ModItems.IceBook.get(), 1));
                }});
            }
        }
    }
    public static void Slime() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.BigSlimeBall.get(), 1),
                new ItemStack(ModItems.SlimeBoots.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Slime,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 10));
                    add(new ItemStack(ModItems.SlimeBall.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestArmorBoots.get(), 1));
                    add(new ItemStack(ModItems.BigSlimeBall.get(), 12));
                }});
            }
        }
    }
    public static void Taboo() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.IntensifiedDevilBlood.get(), 1),
                new ItemStack(ModItems.IntensifiedDevilBlood.get(), 1),
                new ItemStack(ModItems.IntensifiedDevilBlood.get(), 1),
                new ItemStack(ModItems.PurpleIronConstraintStone.get(), 1),
                new ItemStack(ModItems.ConstrainTaboo.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Taboo,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EarthManaSoul.get(), 32));
                    add(new ItemStack(ModItems.DevilBlood.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BloodManaSoul.get(), 32));
                    add(new ItemStack(ModItems.DevilBlood.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoCore.get(), 8));
                    add(new ItemStack(ModItems.DevilBlood.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIron.get(), 8));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.TabooPiece.get(), 64));
                    add(new ItemStack(ModItems.PurpleIronConstraintStone.get(), 1));
                }});
            }
        }
    }

    public static void Parkour() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.ParkourGloves.get(), 1),
                new ItemStack(ModItems.KillPaperLoot.get(), 1),
                new ItemStack(ModItems.Ps_Bottle2.get(), 1),
                new ItemStack(ModItems.CompleteGem.get(), 1),
                new ItemStack(ModItems.ReputationMedal.get(), 1),
                new ItemStack(ModItems.CropBag.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Parkour,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ParkourMedal.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ParkourMedal.get(), 4));
                }});
                case 2,3,4,5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ParkourMedal.get(), 2));
                }});
            }
        }
    }

    public static void Castle() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.CastleIngot.get(), 1),
                new ItemStack(ModItems.CastleArmorPiece.get(), 1),
                new ItemStack(ModItems.CastleArmorPiece.get(), 1),
                new ItemStack(ModItems.CastleArmorPiece.get(), 1),
                new ItemStack(ModItems.CastleSwordForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleBowForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleSceptreForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleAttackHelmetForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleAttackChestForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleAttackLeggingsForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleAttackBootsForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleSwiftHelmetForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleSwiftChestForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleSwiftLeggingsForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleSwiftBootsForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleManaHelmetForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleManaChestForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleManaLeggingsForgeDraw.get(), 1),
                new ItemStack(ModItems.CastleManaBootsForgeDraw.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Castle,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CastlePiece.get(), 6));
                    add(new ItemStack(ModItems.CastleSoul.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CastleSwordPiece.get(), 1));
                    add(new ItemStack(ModItems.CastleKnightStone.get(), 8));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CastleBowPiece.get(), 1));
                    add(new ItemStack(ModItems.CastleKnightStone.get(), 8));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CastleSceptrePiece.get(), 1));
                    add(new ItemStack(ModItems.CastleKnightStone.get(), 8));
                }});

                default -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CastleIngot.get(), 64));
                    add(new ItemStack(ModItems.CastleIngot.get(), 64));
                    add(new ItemStack(ModItems.CastleIngot.get(), 64));
                }});
            }
        }
    }

    public static void CastleCommon() {

        ItemStack[] itemStacks = {
                new ItemStack(ModItems.BeaconRune.get(), 1),
                new ItemStack(ModItems.BlazeRune.get(), 1),
                new ItemStack(ModItems.TreeRune.get(), 1),
                new ItemStack(ModItems.BeaconBow.get(), 1),
                new ItemStack(ModItems.BlazeSword.get(), 1),
                new ItemStack(ModItems.TreeSceptre.get(), 1),
                new ItemStack(ModItems.CastleCrystal.get(), 1),
                new ItemStack(ModItems.CastleCrystalNorth.get(), 1),
                new ItemStack(ModItems.CastleCrystalSouth.get(), 1),
                new ItemStack(ModItems.CastleCuriosPowder.get(), 1),
                new ItemStack(ModItems.CastleCuriosPowder.get(), 1),
                new ItemStack(ModItems.CastleWeaponGem.get(), 1),
                new ItemStack(ModItems.CastleArmorGem.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.CastleCommon,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CompleteGem.get(), 1));
                    add(new ItemStack(ModItems.ReputationMedal.get(), 1));
                    add(new ItemStack(ModItems.BeaconSoul.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CompleteGem.get(), 1));
                    add(new ItemStack(ModItems.ReputationMedal.get(), 1));
                    add(new ItemStack(ModItems.BlazeSoul.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CompleteGem.get(), 1));
                    add(new ItemStack(ModItems.ReputationMedal.get(), 1));
                    add(new ItemStack(ModItems.TreeSoul.get(), 64));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BeaconRune.get(), 8));
                    add(new ItemStack(ModItems.NetherBow.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BlazeRune.get(), 8));
                    add(new ItemStack(ModItems.VolcanoSword3.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.TreeRune.get(), 8));
                    add(new ItemStack(ModItems.PlainSceptre4.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CompleteGem.get(), 2));
                    add(new ItemStack(ModItems.ReputationMedal.get(), 2));
                    add(new ItemStack(ModItems.CastleKnightStone.get(), 64));
                }});
                case 7,8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 16));
                    add(new ItemStack(ModItems.CastleCrystal.get(), 1));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CastleNecklace.get(), 1));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CastleBrooch.get(), 1));
                }});
                case 11,12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CompleteGem.get(), 16));
                    add(new ItemStack(ModItems.CastleCuriosPowder.get(), 16));
                }});
            }
        }
    }

    public static void EndPower() {

        ItemStack[] itemStacks = {
                new ItemStack(ModItems.EndPower.get(), 1),
                new ItemStack(ModItems.EndCrystal.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.EndPower,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RecallPiece.get(), 64));
                    add(new ItemStack(ModItems.SilverFishSoul.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CompleteGem.get(), 8));
                    add(new ItemStack(ModItems.ShulkerSoul.get(), 64));
                    add(new ItemStack(ModItems.EnderMiteSoul.get(), 64));
                }});
            }
        }
    }

    public static void SkyGemStore() {

        ItemStack[] itemStacks = {
                new ItemStack(ModItems.PlainRing.get(), 1),
                new ItemStack(ModItems.ForestRing.get(), 1),
                new ItemStack(ModItems.VolcanoRing.get(), 1),
                new ItemStack(ModItems.LakeRing.get(), 1),
                new ItemStack(ModItems.RubyNecklace.get(), 1),
                new ItemStack(ModItems.SapphireNecklace.get(), 1),
                new ItemStack(ModItems.FancySapphireNecklace.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.SkyGemStore,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GemPiece.get(), 64));
                    add(new ItemStack(ModItems.Note_0.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GemPiece.get(), 64));
                    add(new ItemStack(ModItems.Note_1.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GemPiece.get(), 64));
                    add(new ItemStack(ModItems.Note_2.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GemPiece.get(), 64));
                    add(new ItemStack(ModItems.Note_3.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoCore.get(), 64));
                    add(new ItemStack(ModItems.GemPiece.get(), 64));
                    add(new ItemStack(ModItems.GemPiece.get(), 64));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeCore.get(), 64));
                    add(new ItemStack(ModItems.GemPiece.get(), 64));
                    add(new ItemStack(ModItems.GemPiece.get(), 64));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeRune.get(), 64));
                    add(new ItemStack(ModItems.SapphireNecklace3.get(), 1));
                    add(new ItemStack(ModItems.VolcanoRune.get(), 64));
                    add(new ItemStack(ModItems.RubyNecklace3.get(), 1));
                }});
            }
        }
    }

    public static void LakeRune() {

        ItemStack[] itemStacks = {
                new ItemStack(ModItems.LakeRune0.get(), 1),
                new ItemStack(ModItems.LakeRune1.get(), 1),
                new ItemStack(ModItems.LakeRune2.get(), 1),
                new ItemStack(ModItems.LakeRune3.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.LakeRune,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                default -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeRune.get(), 5));
                    add(new ItemStack(ModItems.RunePiece.get(), 64));
                }});
            }
        }
    }

    public static void QingMing() {

        ItemStack[] itemStacks = {
                new ItemStack(ModItems.QingMingPrefix.get(), 1),
                new ItemStack(ModItems.QingMingGem.get(), 1),
                new ItemStack(ModItems.QingMingForgePaper.get(), 1),
                new ItemStack(ModItems.QingMingAttackRing.get(), 1),
                new ItemStack(ModItems.QingMingBowRing.get(), 1),
                new ItemStack(ModItems.QingMingManaRing.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.QingMing,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.QingTuan.get(), 32));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.QingTuan.get(), 64));
                    add(new ItemStack(ModItems.QingTuan.get(), 32));
                }});
                default -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.QingTuan.get(), 64));
                    add(new ItemStack(ModItems.QingTuan.get(), 64));
                    add(new ItemStack(ModItems.QingTuan.get(), 64));
                    add(new ItemStack(ModItems.QingTuan.get(), 64));
                }});
            }
        }
    }

    public static void LifeElement() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.LifeElementPiece1.get(), 1),
                new ItemStack(ModItems.LifeElementPiece2.get(), 1),
                new ItemStack(ModItems.LifeCrystal0.get(), 1),
                new ItemStack(ModItems.LifeTeleportTicket.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.LifeElement,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul2.get(), 1));
                    add(new ItemStack(ModItems.LifeElementPiece0.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RainbowPowder.get(), 4));
                    add(new ItemStack(ModItems.LifeElementPiece1.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIronBud3.get(), 2));
                    add(new ItemStack(ModItems.LifeElementPiece2.get(), 4));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul3.get(), 4));
                }});
            }
        }
    }
    public static void WindElement() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.WindElementPiece1.get(), 1),
                new ItemStack(ModItems.WindElementPiece2.get(), 1),
                new ItemStack(ModItems.WindCrystal0.get(), 1),
                new ItemStack(ModItems.WindTeleportTicket.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.WindElement,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul2.get(), 1));
                    add(new ItemStack(ModItems.WindElementPiece0.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RainbowPowder.get(), 4));
                    add(new ItemStack(ModItems.WindElementPiece1.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIronBud3.get(), 2));
                    add(new ItemStack(ModItems.WindElementPiece2.get(), 4));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul3.get(), 4));
                }});
            }
        }
    }

    public static void StoneElement() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.StoneElementPiece1.get(), 1),
                new ItemStack(ModItems.StoneElementPiece2.get(), 1),
                new ItemStack(ModItems.StoneCrystal0.get(), 1),
                new ItemStack(ModItems.StoneTeleportTicket.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.StoneElement,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul2.get(), 1));
                    add(new ItemStack(ModItems.StoneElementPiece0.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RainbowPowder.get(), 4));
                    add(new ItemStack(ModItems.StoneElementPiece1.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIronBud3.get(), 2));
                    add(new ItemStack(ModItems.StoneElementPiece2.get(), 4));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul3.get(), 4));
                }});
            }
        }
    }

    public static void LightningElement() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.LightningElementPiece1.get(), 1),
                new ItemStack(ModItems.LightningElementPiece2.get(), 1),
                new ItemStack(ModItems.LightningCrystal0.get(), 1),
                new ItemStack(ModItems.LightningTeleportTicket.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.LightningElement,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul2.get(), 1));
                    add(new ItemStack(ModItems.LightningElementPiece0.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RainbowPowder.get(), 4));
                    add(new ItemStack(ModItems.LightningElementPiece1.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIronBud3.get(), 2));
                    add(new ItemStack(ModItems.LightningElementPiece2.get(), 4));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul3.get(), 4));
                }});
            }
        }
    }

    public static void WaterElement() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.WaterElementPiece1.get(), 1),
                new ItemStack(ModItems.WaterElementPiece2.get(), 1),
                new ItemStack(ModItems.WaterCrystal0.get(), 1),
                new ItemStack(ModItems.WaterTeleportTicket.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.WaterElement,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul2.get(), 1));
                    add(new ItemStack(ModItems.WaterElementPiece0.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RainbowPowder.get(), 4));
                    add(new ItemStack(ModItems.WaterElementPiece1.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIronBud3.get(), 2));
                    add(new ItemStack(ModItems.WaterElementPiece2.get(), 4));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul3.get(), 4));
                }});
            }
        }
    }

    public static void FireElement() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.FireElementPiece1.get(), 1),
                new ItemStack(ModItems.FireElementPiece2.get(), 1),
                new ItemStack(ModItems.FireCrystal0.get(), 1),
                new ItemStack(ModItems.FireTeleportTicket.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.FireElement,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul2.get(), 1));
                    add(new ItemStack(ModItems.FireElementPiece0.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RainbowPowder.get(), 4));
                    add(new ItemStack(ModItems.FireElementPiece1.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIronBud3.get(), 2));
                    add(new ItemStack(ModItems.FireElementPiece2.get(), 4));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul3.get(), 4));
                }});
            }
        }
    }

    public static void IceElement() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.IceElementPiece1.get(), 1),
                new ItemStack(ModItems.IceElementPiece2.get(), 1),
                new ItemStack(ModItems.IceCrystal0.get(), 1),
                new ItemStack(ModItems.IceTeleportTicket.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.IceElement,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul2.get(), 1));
                    add(new ItemStack(ModItems.IceElementPiece0.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RainbowPowder.get(), 4));
                    add(new ItemStack(ModItems.IceElementPiece1.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIronBud3.get(), 2));
                    add(new ItemStack(ModItems.IceElementPiece2.get(), 4));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul3.get(), 4));
                }});
            }
        }
    }

    public static void Food() {
        ItemStack[] itemStacks = {
                new ItemStack(Items.COOKIE, 64),
                new ItemStack(Items.BREAD, 64),
                new ItemStack(Items.BAKED_POTATO, 64),
                new ItemStack(Items.COOKED_COD, 64),
                new ItemStack(Items.COOKED_SALMON, 64),
                new ItemStack(Items.PUMPKIN_PIE, 64),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Food,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SilverCoin.get(), 8));
                }});
                case 1,2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SilverCoin.get(), 24));
                }});
                case 3,4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SilverCoin.get(), 32));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SilverCoin.get(), 48));
                }});
            }
        }
    }

    public static void EndRecall() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.KazeRecallBook.get()),
                new ItemStack(ModItems.SpiderRecallBook.get()),
                new ItemStack(ModItems.BlackForestRecallBook.get()),
                new ItemStack(ModItems.SeaRecallBook.get()),
                new ItemStack(ModItems.LightningRecallBook.get()),
                new ItemStack(ModItems.NetherRecallBook1.get()),
                new ItemStack(ModItems.SnowRecallBook.get()),
                new ItemStack(ModItems.ForestRecallBook.get()),
                new ItemStack(ModItems.VolcanoRecallBook.get()),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.EndRecall,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.KazeRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 16));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpiderRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 16));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BlackForestRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 16));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SeaRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 16));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LightningRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 16));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NetherSoul.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 16));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SnowRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 16));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 16));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 16));
                }});
            }
        }
    }

    public static void RoseGoldStore() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.RoseGoldCoin.get(), 1),

        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.RoseGoldStore,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GoldCoin.get(), 64));
                }});

            }
        }
    }

    public static void LabourDayStore() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.LabourDayIronPickaxe.get(), 1),
                new ItemStack(ModItems.LabourDayIronHoe.get(), 1),
                new ItemStack(ModItems.LabourDayForgePaper.get(), 1),
                new ItemStack(ModItems.LabourDayGem.get(), 1),
                new ItemStack(ModItems.LabourDayLottery.get(), 1),
                new ItemStack(ModItems.LabourDayPrefix.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.LabourDayStore,contentList);

        for (int i = 0; i < itemStacks.length; i ++) {
            switch (i) {
                case 0,1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.OldGoldCoin.get(), 64));
                }});

                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.OldSilverCoin.get(), 256));
                }});

                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.OldSilverCoin.get(), 32));
                    add(new ItemStack(ModItems.OldSilverCoin.get(), 64));
                }});

                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.OldGoldCoin.get(), 1));
                }});

                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.OldSilverCoin.get(), 32));
                }});

            }
        }
    }
}
