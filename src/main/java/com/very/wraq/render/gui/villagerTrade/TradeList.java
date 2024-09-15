package com.very.wraq.render.gui.villagerTrade;

import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.customized.UniformItems;
import com.very.wraq.process.system.element.ElementItems;
import com.very.wraq.process.system.enhanceForge.ForgeMaterials;
import com.very.wraq.process.system.spur.Items.SpurItems;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.gems.GemItems;
import com.very.wraq.series.newrunes.NewRuneItems;
import com.very.wraq.series.specialevents.SpecialEventItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.*;

public class TradeList {
    public static Map<ItemStack, List<ItemStack>> tradeRecipeMap = new HashMap<>();
    public static Map<String, List<ItemStack>> tradeContent = new HashMap<>();

    public static void setTradeContent() {
        /* 2.0 */
        plainLeather();
        plainForgeHammer();
        forestTool();
        forestForgeHammer();
        riverTool();
        mineTool();

        volcanoTool();
        volcanoForgeHammer();
        snowTool();
        snowForgeHammer();
        skyTool();

        mineCharm();
        seaCharm();
        cropCharm();
        logCharm();

        elementMaster();
        runeMaster();

        summerEvent();

        /* 1.0 */
        LifeElement();
        WindElement();
        StoneElement();
        FireElement();
        LightningElement();
        WaterElement();
        IceElement();

        Plain();
        Forest();
        Lake();
        Volcano();
        Mine();
        Snow();
        Sky();
        Evoker();

        Wither();
        Piglin();
        Skeleton();
        Magma();
        Crest();
        PlainForge();
        SoulEquipment();
        PurpleIron();
        Ice();
        Currency();
        SoulToGoldCoin();
        BossCore();
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
        NetherRune();
        LakeRune();

        NetherPower();
        NetherArmor();
        NetherBow();
        NetherSwordModel();
        NetherWeapon();
        Ruby();
        PlainForestRune();
        LakeVolcanoRune();
        ManaArmor();
        Brewing();
        Spider();
        Sakura();
        Nature();
        Pickaxe();
        Axe();
        Ore();
        GoldCoinStore();
        NewGive();
        Field();
        Spring();
        EndRecall();
        EndPower();
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
        QingMing();
        Food();
        RoseGoldStore();
        LabourDayStore();
        BackPack();
        Pearl();
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
                GemItems.plainGem.get().getDefaultInstance()
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Plain, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainSoul.get(), 64));
                    add(new ItemStack(ModItems.goldCoin.get(), 1));
                }});
                case 1, 2, 3, 4, 5, 6, 7, 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainRune.get()));
                }});
                case 9, 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainRune.get(), 1));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainRune.get(), 10));
                    add(new ItemStack(ModItems.completeGem.get()));
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
                GemItems.forestGem.get().getDefaultInstance(),
                new ItemStack(Items.OAK_LOG, 4),
                new ItemStack(Items.OAK_LOG, 64),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Forest, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestSoul.get(), 64));
                    add(new ItemStack(ModItems.goldCoin.get(), 3));
                }});
                case 1, 2, 3, 4, 5, 6, 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestRune.get()));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestRune.get(), 5));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainManaBook.get(), 1));
                    add(new ItemStack(ModItems.ForestRune.get(), 1));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestRune.get(), 10));
                    add(new ItemStack(ModItems.completeGem.get(), 16));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestSoul.get(), 1));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestSoul.get(), 16));
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
                GemItems.lakeGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Lake, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeSoul.get(), 64));
                    add(new ItemStack(ModItems.goldCoin.get(), 5));
                }});
                case 1, 2, 3, 4, 5, 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeRune.get()));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeRune.get(), 5));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestManaBook.get(), 1));
                    add(new ItemStack(ModItems.LakeRune.get(), 1));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeRune.get(), 10));
                    add(new ItemStack(ModItems.completeGem.get()));
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
                GemItems.volcanoGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Volcano, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoSoul.get(), 64));
                    add(new ItemStack(ModItems.goldCoin.get(), 5));
                }});
                case 1, 2, 3, 4, 5, 6, 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoRune.get()));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoRune.get(), 5));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeManaBook.get(), 1));
                    add(new ItemStack(ModItems.VolcanoRune.get(), 1));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoRune.get(), 10));
                    add(new ItemStack(ModItems.completeGem.get()));
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
                GemItems.mineGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Mine, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MineSoul.get(), 64));
                    add(new ItemStack(ModItems.goldCoin.get(), 5));
                }});
                case 1, 2, 3, 4, 5, 6, 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MineRune.get()));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MineRune.get(), 5));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.completeGem.get(), 1));
                    add(new ItemStack(ModItems.MineRune.get(), 10));
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
                GemItems.snowGem.get().getDefaultInstance(),
                ModItems.SnowShield.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Snow, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SnowSoul.get(), 64));
                    add(new ItemStack(ModItems.goldCoin.get(), 5));
                }});
                case 1, 2, 3, 4, 5, 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SnowRune.get()));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SnowRune.get(), 5));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoManaBook.get(), 1));
                    add(new ItemStack(ModItems.SnowRune.get(), 1));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SnowRune.get(), 10));
                    add(new ItemStack(ModItems.completeGem.get()));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SnowRune.get(), 5));
                }});

            }
        }
    }

    public static void Sky() {
        ItemStack[] itemStacks = {
                ModItems.SkyRune.get().getDefaultInstance(),
                ModItems.SkyBow.get().getDefaultInstance(),
                ModItems.SkyBracelet.get().getDefaultInstance(),
                GemItems.skyGem.get().getDefaultInstance(),
                ModItems.SkyArmorHelmet.get().getDefaultInstance(),
                ModItems.SkyArmorChest.get().getDefaultInstance(),
                ModItems.SkyArmorLeggings.get().getDefaultInstance(),
                ModItems.SkyArmorBoots.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Sky, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SkySoul.get(), 64));
                    add(new ItemStack(ModItems.goldCoin.get(), 5));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SkyRune.get(), 10));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SkyRune.get(), 5));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.completeGem.get(), 1));
                    add(new ItemStack(ModItems.SkyRune.get(), 10));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SkyHelmetForgeDraw.get(), 1));
                    add(new ItemStack(ModItems.SkyArmorHelmet.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SkyChestForgeDraw.get(), 1));
                    add(new ItemStack(ModItems.SkyArmorChest.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SkyLeggingsForgeDraw.get(), 1));
                    add(new ItemStack(ModItems.SkyArmorLeggings.get(), 1));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SkyBootsForgeDraw.get(), 1));
                    add(new ItemStack(ModItems.SkyArmorBoots.get(), 1));
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
                GemItems.evokerGem.get().getDefaultInstance(),
                ModItems.EvokerBook0.get().getDefaultInstance(),
                ModItems.EvokerBook1.get().getDefaultInstance(),
                ModItems.EvokerBook2.get().getDefaultInstance(),
                ModItems.EvokerBook3.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Evoker, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EvokerSoul.get(), 4));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 2));
                    add(new ItemStack(ModItems.gemPiece.get(), 4));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBucket.get(), 8));
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 2));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EvokerRune.get(), 5));
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 2));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EvokerRune.get(), 10));
                    add(new ItemStack(ModItems.completeGem.get(), 16));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoManaBook.get(), 1));
                    add(new ItemStack(ModItems.EvokerRune.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EvokerBook0.get(), 1));
                    add(new ItemStack(ModItems.EvokerRune.get(), 2));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EvokerBook1.get(), 1));
                    add(new ItemStack(ModItems.EvokerRune.get(), 3));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EvokerBook2.get(), 1));
                    add(new ItemStack(ModItems.EvokerRune.get(), 5));
                }});
            }
        }
    }

    public static void Wither() {
        ItemStack[] itemStacks = {
                ModItems.WitherRune.get().getDefaultInstance(),
                GemItems.witherGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Wither, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.witherSkeletonSoul.get(), 64));
                    add(new ItemStack(ModItems.NetherSoul.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.completeGem.get(), 16));
                    add(new ItemStack(ModItems.WitherRune.get(), 10));
                }});
            }
        }
    }

    public static void Piglin() {
        ItemStack[] itemStacks = {
                ModItems.PiglinRune.get().getDefaultInstance(),
                GemItems.piglinGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Piglin, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PigLinSoul.get(), 64));
                    add(new ItemStack(ModItems.NetherSoul.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.completeGem.get(), 16));
                    add(new ItemStack(ModItems.PiglinRune.get(), 10));
                }});
            }
        }
    }

    public static void Skeleton() {
        ItemStack[] itemStacks = {
                ModItems.NetherSkeletonRune.get().getDefaultInstance(),
                GemItems.netherSkeletonGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Skeleton, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.netherSkeletonSoul.get(), 64));
                    add(new ItemStack(ModItems.NetherSoul.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.completeGem.get(), 16));
                    add(new ItemStack(ModItems.NetherSkeletonRune.get(), 10));
                }});
            }
        }
    }

    public static void Magma() {
        ItemStack[] itemStacks = {
                ModItems.MagmaRune.get().getDefaultInstance(),
                GemItems.magmaGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Magma, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.magmaSoul.get(), 64));
                    add(new ItemStack(ModItems.NetherSoul.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.completeGem.get(), 16));
                    add(new ItemStack(ModItems.MagmaRune.get(), 10));
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
        tradeContent.put(StringUtils.VillagerName.Crest, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainCrest0.get(), 16));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainCrest1.get(), 16));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainCrest2.get(), 16));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainCrest3.get(), 16));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestCrest0.get(), 16));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestCrest1.get(), 16));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestCrest2.get(), 16));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestCrest3.get(), 16));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeCrest0.get(), 16));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeCrest1.get(), 16));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeCrest2.get(), 16));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeCrest3.get(), 16));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoCrest0.get(), 16));
                }});
                case 13 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoCrest1.get(), 16));
                }});
                case 14 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoCrest2.get(), 16));
                }});
                case 15 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoCrest3.get(), 16));
                }});
                case 16 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MineCrest0.get(), 16));
                }});
                case 17 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MineCrest1.get(), 16));
                }});
                case 18 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MineCrest2.get(), 16));
                }});
                case 19 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MineCrest3.get(), 16));
                }});
                case 20 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SnowCrest0.get(), 16));
                }});
                case 21 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SnowCrest1.get(), 16));
                }});
                case 22 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SnowCrest2.get(), 16));
                }});
                case 23 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SnowCrest3.get(), 16));
                }});
                case 24 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SkyCrest0.get(), 16));
                }});
                case 25 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SkyCrest1.get(), 16));
                }});
                case 26 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SkyCrest2.get(), 16));
                }});
                case 27 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SkyCrest3.get(), 16));
                }});
                case 28 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaCrest0.get(), 16));
                }});
                case 29 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaCrest1.get(), 16));
                }});
                case 30 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaCrest2.get(), 16));
                }});
                case 31 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaCrest3.get(), 16));
                }});
            }
        }
    }

    public static void PlainForge() {
        ItemStack[] itemStacks = {
                ModItems.SpeIron.get().getDefaultInstance(),
                ModItems.SpeIron.get().getDefaultInstance(),
                ModItems.OpenSlot.get().getDefaultInstance(),
                new ItemStack(ModItems.MineSoul1.get(), 16),
                ModItems.ForgingStone0.get().getDefaultInstance(),
                ModItems.ForgingStone1.get().getDefaultInstance(),
                ModItems.ForgingStone1.get().getDefaultInstance(),
                ModItems.ForgingStone1.get().getDefaultInstance(),
                ModItems.ForgingStone1.get().getDefaultInstance(),
                ModItems.ForgingStone2.get().getDefaultInstance(),

                ModItems.ForgeProtect.get().getDefaultInstance(),
                ModItems.ForgeEnhance0.get().getDefaultInstance(),
                ModItems.ForgeEnhance1.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Forge, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MineSoul.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MineSoul1.get(), 16));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 16));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MineSoul.get(), 16));
                    add(new ItemStack(ModItems.goldCoin.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.DIAMOND, 4));
                    add(new ItemStack(ModItems.goldCoin.get(), 4));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.EMERALD, 4));
                    add(new ItemStack(ModItems.goldCoin.get(), 4));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.REDSTONE, 16));
                    add(new ItemStack(ModItems.goldCoin.get(), 4));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.LAPIS_LAZULI, 16));
                    add(new ItemStack(ModItems.goldCoin.get(), 4));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NetherQuartz.get(), 32));
                    add(new ItemStack(ModItems.goldCoin.get(), 8));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 8));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 1));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 4));
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
                ModItems.Dismantle.get().getDefaultInstance(),
                ModItems.Dismantle.get().getDefaultInstance(),
                ModItems.LifeTeleportTicket.get().getDefaultInstance(),
                ModItems.WindTeleportTicket.get().getDefaultInstance(),
                ModItems.StoneTeleportTicket.get().getDefaultInstance(),
                ModItems.SwordLottery.get().getDefaultInstance(),
                ModItems.BowLottery.get().getDefaultInstance(),
                ModItems.SceptreLottery.get().getDefaultInstance()
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        List<ItemStack> uniformList = new ArrayList<>() {{
            for (Item item : Utils.uniformList) add(item.getDefaultInstance());
        }};
        contentList.addAll(uniformList);
        tradeContent.put(StringUtils.VillagerName.WorldSoul, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoulNote.get(), 1));
                }});
                case 1, 2, 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul3.get(), 16));
                }});
                case 4, 8, 9, 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul3.get(), 4));
                }});
                case 5, 6, 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WorldSoul2.get(), 8));
                }});
                case 11, 12, 13 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.worldSoul5.get(), 40));
                }});
            }
        }
        for (ItemStack itemStack : uniformList)
            tradeRecipeMap.put(itemStack, List.of(new ItemStack(UniformItems.uniformPiece.get(), 2)));
    }

    public static void PurpleIron() {

        ItemStack[] itemStacks = {
                ModItems.PurpleIron.get().getDefaultInstance(),
                ModItems.PurpleIron.get().getDefaultInstance(),
                ModItems.goldCoin.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.PurpleIron, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 16));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIronPiece.get(), 64));
                    add(new ItemStack(Items.RAW_GOLD, 8));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PurpleIronPiece.get(), 64));
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
        tradeContent.put(StringUtils.VillagerName.Ice, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LeatherSoul.get(), 64));
                    add(new ItemStack(ModItems.goldCoin.get(), 10));
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
                ModItems.goldCoin.get().getDefaultInstance(),
                ModItems.goldCoin.get().getDefaultInstance(),
                ModItems.goldCoin.get().getDefaultInstance(),
                ModItems.goldCoin.get().getDefaultInstance(),
                ModItems.completeGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Currency, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.silverCoin.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.gemPiece.get(), 9));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RunePiece.get(), 32));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MineSoul.get(), 64));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.gemPiece.get(), 64));
                }});
            }
        }
    }

    public static void SoulToGoldCoin() {
        ItemStack[] itemStacks = {
                ModItems.goldCoin.get().getDefaultInstance(),
                ModItems.goldCoin.get().getDefaultInstance(),
                ModItems.goldCoin.get().getDefaultInstance(),
                ModItems.goldCoin.get().getDefaultInstance(),
                ModItems.goldCoin.get().getDefaultInstance(),
                ModItems.goldCoin.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.SoulToGoldCoin, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.BossCore, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.Cord, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
                new ItemStack(ModItems.PlainBossSoul.get(), 32),
                new ItemStack(ModItems.PlainBossSoul.get(), 32),
                new ItemStack(ModItems.PlainBossSoul.get(), 32),
                new ItemStack(ModItems.PlainBossSoul.get(), 32),

                new ItemStack(ModItems.IceLoot.get(), 6),
                new ItemStack(ModItems.IceLoot.get(), 6),
                new ItemStack(ModItems.IceLoot.get(), 12),

                new ItemStack(ModItems.DevilAttackSoul.get(), 32),
                new ItemStack(ModItems.DevilSwiftSoul.get(), 32),
                new ItemStack(ModItems.DevilManaSoul.get(), 32),

                new ItemStack(ModItems.PurpleIronBud3.get(), 1),
                new ItemStack(ModItems.PurpleIronBud3.get(), 1),
                new ItemStack(ModItems.PurpleIronBud3.get(), 1)
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.T1Equip, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.SeaEquip, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.BlackForestEquip, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BlackForestManaCore.get(), 1));
                    add(new ItemStack(ModItems.BlackForestSword4.get(), 1));
                    add(new ItemStack(ModItems.huskSword3.get(), 1));
                    add(new ItemStack(ModItems.huskSword2.get(), 1));
                    add(new ItemStack(ModItems.huskSword1.get(), 1));
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
        tradeContent.put(StringUtils.VillagerName.LightningEquip, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.lightningArmorHelmet.get(), 1));
                    add(new ItemStack(ModItems.lightningArmorChest.get(), 1));
                    add(new ItemStack(ModItems.lightningArmorLeggings.get(), 1));
                    add(new ItemStack(ModItems.lightningArmorBoots.get(), 1));
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
        tradeContent.put(StringUtils.VillagerName.PlainRune, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 1));
                    add(new ItemStack(ModItems.PlainSoul.get(), 64));
                }});
                case 1, 2, 3, 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
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
        tradeContent.put(StringUtils.VillagerName.ForestRune, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 3));
                    add(new ItemStack(ModItems.ForestSoul.get(), 64));
                }});
                case 1, 2, 3, 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
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
        tradeContent.put(StringUtils.VillagerName.VolcanoRune, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 5));
                    add(new ItemStack(ModItems.VolcanoSoul.get(), 64));
                }});
                case 1, 2, 3, 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
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
        tradeContent.put(StringUtils.VillagerName.ManaRune, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0, 1, 2, 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
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
        tradeContent.put(StringUtils.VillagerName.SnowRune, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 5));
                    add(new ItemStack(ModItems.SnowSoul.get(), 64));
                }});
                case 1, 2, 3, 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
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
        tradeContent.put(StringUtils.VillagerName.NetherRune, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0, 1, 2, 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
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
        tradeContent.put(StringUtils.VillagerName.NetherPower, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Ruby.get(), 64));
                    add(new ItemStack(ModItems.EvokerRune.get(), 2));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.witherSkeletonSoul.get(), 64));
                    add(new ItemStack(ModItems.PowerModel.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PigLinSoul.get(), 64));
                    add(new ItemStack(ModItems.PowerModel.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.netherSkeletonSoul.get(), 64));
                    add(new ItemStack(ModItems.PowerModel.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.magmaSoul.get(), 64));
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
        tradeContent.put(StringUtils.VillagerName.NetherArmor, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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

    public static void NetherBow() {
        ItemStack[] itemStacks = {
                ModItems.QuartzRune.get().getDefaultInstance(),
                ModItems.NetherBow.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.NetherBow, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.NetherSwordModel, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0, 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 10));
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
        tradeContent.put(StringUtils.VillagerName.NetherWeapon, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
                ModItems.toNether.get().getDefaultInstance(),
                ModItems.toEnd.get().getDefaultInstance()
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Ruby, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 10));
                    add(new ItemStack(ModItems.Ruby.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 25));
                    add(new ItemStack(ModItems.NetherQuartz.get(), 64));
                }});
                case 2, 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 4));
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
                GemItems.lifeManaGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.PlainForestRune, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
                    add(new ItemStack(GemItems.plainGem.get(), 1));
                    add(new ItemStack(GemItems.forestGem.get(), 1));
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
                GemItems.obsiManaGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.LakeVolcanoRune, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
                    add(new ItemStack(GemItems.lakeGem.get(), 1));
                    add(new ItemStack(GemItems.volcanoGem.get(), 1));
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
        tradeContent.put(StringUtils.VillagerName.ManaArmor, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0, 1, 2, 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.forestmana.get(), 1));
                    add(new ItemStack(ModItems.plainmana.get(), 1));
                }});
                case 4, 5, 6, 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
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
                new ItemStack(ModItems.Splasher.get(), 8),

                new ItemStack(ModItems.WaterBottle.get(), 64)
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Brewing, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 3));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.ANDESITE, 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.DIORITE, 64));
                }});
                case 3, 4, 7, 8, 9, 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 1));
                    add(new ItemStack(Items.ANDESITE, 64));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 1));
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
        tradeContent.put(StringUtils.VillagerName.Spider, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 8));
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
                new ItemStack(GemItems.sakuraGem.get(), 1),
                new ItemStack(GemItems.shipGem.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Sakura, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SakuraPetal.get(), 12));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.completeGem.get(), 16));
                    add(new ItemStack(ModItems.SakuraPetal.get(), 64));
                    add(new ItemStack(ModItems.SakuraPetal.get(), 64));
                    add(new ItemStack(ModItems.SakuraPetal.get(), 64));
                    add(new ItemStack(ModItems.SakuraPetal.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.completeGem.get(), 16));
                    add(new ItemStack(ModItems.ShipPiece.get(), 64));
                    add(new ItemStack(ModItems.ShipPiece.get(), 64));
                    add(new ItemStack(ModItems.ShipPiece.get(), 64));
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
        tradeContent.put(StringUtils.VillagerName.Nature, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        potion.getOrCreateTag().putString("Potion", "minecraft:long_night_vision");

        ItemStack[] itemStacks = {
                new ItemStack(Items.STONE_PICKAXE, 1),
                new ItemStack(Items.IRON_PICKAXE, 1),
                potion,
                new ItemStack(Items.DIAMOND_PICKAXE, 1),
                new ItemStack(Items.NETHERITE_PICKAXE, 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Pickaxe, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.silverCoin.get(), 16));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.silverCoin.get(), 8));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 4));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 16));
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
        tradeContent.put(StringUtils.VillagerName.Axe, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.silverCoin.get(), 16));
                }});
                case 1, 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 8));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 16));
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
        tradeContent.put(StringUtils.VillagerName.Ore, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
                new ItemStack(ModItems.BackSpawn.get(), 1),
                new ItemStack(ModItems.WorldSoul2.get(), 2),
                new ItemStack(ModItems.MineHat.get(), 1),
                new ItemStack(ModItems.U_Disk.get(), 1),
                new ItemStack(ModItems.BackPackTickets.get(), 1),
                new ItemStack(ModItems.goldCoinLottery.get(), 1),
                new ItemStack(moe.plushie.armourers_workshop.init.ModItems.SKIN_TEMPLATE.get(), 1),
                new ItemStack(net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.STACK_UPGRADE_TIER_4.get(), 1),
                new ItemStack(net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.PICKUP_UPGRADE.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.GoldCoinStore, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 8));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 16));
                }});
                case 2, 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 32));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 64));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 8));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.skinTemplatePaper.get()));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.stackUpgradePaper.get()));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.pickUpgradePaper.get()));
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
                PatchouliAPI.get().getBookStack(new ResourceLocation(Utils.MOD_ID, "guide")),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.NewGive, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0, 1, 2, 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.silverCoin.get(), 1));
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
                new ItemStack(GemItems.fieldGem.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Field, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FieldSoul.get(), 64));
                    add(new ItemStack(ModItems.goldCoin.get(), 5));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FieldRune.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.completeGem.get(), 16));
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
        tradeContent.put(StringUtils.VillagerName.Spring, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringMoney.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SpringMoney.get(), 64));
                }});
                case 2, 3, 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
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
        tradeContent.put(StringUtils.VillagerName.GoldSmith, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainAttackRing3.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 16));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2AttackRing0.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 32));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2AttackRing1.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 48));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2AttackRing2.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainManaAttackRing3.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 16));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2ManaAttackRing0.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 32));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2ManaAttackRing1.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 48));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2ManaAttackRing2.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainDefenceRing3.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 16));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2DefenceRing0.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 32));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2DefenceRing1.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 48));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2DefenceRing2.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 64));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainHealthRing3.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 16));
                }});
                case 13 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2HealthRing0.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 32));
                }});
                case 14 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2HealthRing1.get(), 1));
                    add(new ItemStack(ModItems.Boss2Piece.get(), 48));
                }});
                case 15 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Boss2HealthRing2.get(), 1));
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
        tradeContent.put(StringUtils.VillagerName.Giant, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                default -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 5));
                }});
            }
        }
    }

    public static void Blood() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.ManaBalance_Empty.get(), 1),
                new ItemStack(ModItems.BloodManaRune.get(), 2),
                new ItemStack(ModItems.BloodManaCurios.get())
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Blood, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.gemPiece.get(), 4));
                    add(new ItemStack(ModItems.goldCoin.get(), 2));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.BloodManaSoul.get(), 64));
                    add(new ItemStack(ModItems.BloodManaSoul.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BloodManaRune.get(), 12));
                    add(new ItemStack(ModItems.completeGem.get()));
                }});
            }
        }
    }

    public static void Earth() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.ManaBalance_Empty.get(), 1),
                new ItemStack(ModItems.EarthManaRune.get(), 2),
                new ItemStack(ModItems.EarthPower.get(), 1),
                new ItemStack(ModItems.EarthManaCurios.get())
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Earth, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.gemPiece.get(), 4));
                    add(new ItemStack(ModItems.goldCoin.get(), 2));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 5));
                    add(new ItemStack(ModItems.EarthManaSoul.get(), 64));
                    add(new ItemStack(ModItems.EarthManaSoul.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EarthManaRune.get(), 8));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EarthManaRune.get(), 12));
                    add(new ItemStack(ModItems.completeGem.get()));
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
        tradeContent.put(StringUtils.VillagerName.ManaBook, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WitherRune.get(), 5));
                    add(new ItemStack(ModItems.EvokerBook3.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EarthManaRune.get(), 12));
                    add(new ItemStack(ModItems.WitherBook.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EarthManaRune.get(), 4));
                    add(new ItemStack(ModItems.IceBook.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EarthManaRune.get(), 4));
                    add(new ItemStack(ModItems.GoldenBook.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.IceHeart.get(), 4));
                    add(new ItemStack(ModItems.EvokerBook3.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.IceHeart.get(), 4));
                    add(new ItemStack(ModItems.WitherBook.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.IceHeart.get(), 4));
                    add(new ItemStack(ModItems.EarthBook.get(), 1));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.IceHeart.get(), 4));
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
        tradeContent.put(StringUtils.VillagerName.Slime, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 10));
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
        tradeContent.put(StringUtils.VillagerName.Taboo, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
                    add(new ItemStack(ModItems.completeGem.get(), 16));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.TabooPiece.get(), 64));
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
                new ItemStack(ModItems.completeGem.get(), 1),
                new ItemStack(ModItems.ReputationMedal.get(), 1),
                new ItemStack(ModItems.CropBag.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Parkour, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ParkourMedal.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ParkourMedal.get(), 4));
                }});
                case 2, 3, 4, 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
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
        tradeContent.put(StringUtils.VillagerName.Castle, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
                new ItemStack(GemItems.castleWeaponGem.get(), 1),
                new ItemStack(GemItems.castleArmorGem.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.CastleCommon, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.completeGem.get(), 1));
                    add(new ItemStack(ModItems.ReputationMedal.get(), 1));
                    add(new ItemStack(ModItems.BeaconSoul.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.completeGem.get(), 1));
                    add(new ItemStack(ModItems.ReputationMedal.get(), 1));
                    add(new ItemStack(ModItems.BlazeSoul.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.completeGem.get(), 1));
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
                    add(new ItemStack(ModItems.completeGem.get(), 2));
                    add(new ItemStack(ModItems.ReputationMedal.get(), 2));
                    add(new ItemStack(ModItems.CastleKnightStone.get(), 64));
                }});
                case 7, 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ManaBalance_Empty.get(), 16));
                    add(new ItemStack(ModItems.CastleCrystal.get(), 1));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CastleNecklace.get(), 1));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CastleBrooch.get(), 1));
                }});
                case 11, 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.completeGem.get(), 16));
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
        tradeContent.put(StringUtils.VillagerName.EndPower, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.RecallPiece.get(), 64));
                    add(new ItemStack(ModItems.toEnd.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.gemPiece.get(), 8));
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
        tradeContent.put(StringUtils.VillagerName.SkyGemStore, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.gemPiece.get(), 64));
                    add(new ItemStack(ModItems.PlainRune.get(), 4));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.gemPiece.get(), 64));
                    add(new ItemStack(ModItems.ForestRune.get(), 4));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.gemPiece.get(), 64));
                    add(new ItemStack(ModItems.VolcanoRune.get(), 4));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.gemPiece.get(), 64));
                    add(new ItemStack(ModItems.LakeRune.get(), 4));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoCore.get(), 64));
                    add(new ItemStack(ModItems.gemPiece.get(), 64));
                    add(new ItemStack(ModItems.gemPiece.get(), 64));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LakeCore.get(), 64));
                    add(new ItemStack(ModItems.gemPiece.get(), 64));
                    add(new ItemStack(ModItems.gemPiece.get(), 64));
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
        tradeContent.put(StringUtils.VillagerName.LakeRune, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.QingMing, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.LifeElement, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.WindElement, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.StoneElement, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.LightningElement, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.WaterElement, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.FireElement, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.IceElement, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
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
        tradeContent.put(StringUtils.VillagerName.Food, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.silverCoin.get(), 8));
                }});
                case 1, 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.silverCoin.get(), 24));
                }});
                case 3, 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.silverCoin.get(), 32));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.silverCoin.get(), 48));
                }});
            }
        }
    }

    public static void EndRecall() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.KazeRecallBook.get()),
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
        tradeContent.put(StringUtils.VillagerName.EndRecall, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.KazeRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 32));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.huskRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 32));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SeaRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 32));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LightningRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 32));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NetherSoul.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 32));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SnowRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 32));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ForestRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 32));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VolcanoRune.get(), 2));
                    add(new ItemStack(ModItems.RecallPiece.get(), 32));
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
        tradeContent.put(StringUtils.VillagerName.RoseGoldStore, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 64));
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
                new ItemStack(ModItems.OldGoldCoin.get(), 3),
                new ItemStack(ModItems.OldGoldCoin.get(), 6)
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.LabourDayStore, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0, 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
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

                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LabourDayPrefix.get(), 1));
                }});

                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LabourDayGem.get(), 1));
                }});
            }
        }
    }

    public static String id = "sophisticatedbackpacks";
    public static Item netheriteBackPack = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id, "netherite_backpack"));

    public static Item stackUpGrade1 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id, "stack_upgrade_tier_1"));
    public static Item stackUpGrade2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id, "stack_upgrade_tier_2"));
    public static Item stackUpGrade3 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id, "stack_upgrade_tier_3"));
    public static Item stackUpGrade4 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id, "stack_upgrade_tier_4"));

    public static void BackPack() {
        ItemStack[] itemStacks = {
                new ItemStack(netheriteBackPack, 1),
                new ItemStack(stackUpGrade1, 1),
                new ItemStack(stackUpGrade2, 1),
                new ItemStack(stackUpGrade3, 1),
                new ItemStack(stackUpGrade4, 1)
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.BackPack, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BackPackTickets.get(), 1));
                }});

                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 12));
                }});

                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 16));
                }});

                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 20));
                }});

                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.goldCoin.get(), 28));
                }});
            }
        }
    }

    public static void Pearl() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.Pearl1.get(), 1),
                new ItemStack(ModItems.Pearl2.get(), 1),
                new ItemStack(ModItems.Pearl3.get(), 1),
                new ItemStack(ModItems.Pearl4.get(), 1),
                new ItemStack(ModItems.Pearl5.get(), 1),
                new ItemStack(ModItems.Pearl6.get(), 1),
                new ItemStack(ModItems.worldForgeStone.get(), 1)
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Pearl, contentList);
        if (ForgeMaterials.chapter1.isEmpty()) ForgeMaterials.setChapter1();
        if (ForgeMaterials.chapter2.isEmpty()) ForgeMaterials.setChapter2();
        if (ForgeMaterials.chapter3.isEmpty()) ForgeMaterials.setChapter3();
        if (ForgeMaterials.chapter4.isEmpty()) ForgeMaterials.setChapter4();
        if (ForgeMaterials.chapter5.isEmpty()) ForgeMaterials.setChapter5();
        if (ForgeMaterials.chapter6.isEmpty()) ForgeMaterials.setChapter6();

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    for (Item item : ForgeMaterials.chapter1) {
                        add(new ItemStack(item, 16));
                    }
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    for (Item item : ForgeMaterials.chapter2) {
                        add(new ItemStack(item, 16));
                    }
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    for (Item item : ForgeMaterials.chapter3) {
                        add(new ItemStack(item, 16));
                    }
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    for (Item item : ForgeMaterials.chapter4) {
                        add(new ItemStack(item, 16));
                    }
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    for (Item item : ForgeMaterials.chapter5) {
                        add(new ItemStack(item, 16));
                    }
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    for (Item item : ForgeMaterials.chapter6) {
                        add(new ItemStack(item, 4));
                    }
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.Pearl1.get(), 1));
                    add(new ItemStack(ModItems.Pearl2.get(), 1));
                    add(new ItemStack(ModItems.Pearl3.get(), 1));
                    add(new ItemStack(ModItems.Pearl4.get(), 1));
                    add(new ItemStack(ModItems.Pearl5.get(), 1));
                    add(new ItemStack(ModItems.Pearl6.get(), 1));
                }});
            }
        }
    }

    public static void plainLeather() {
        ItemStack[] itemStacks = {
                Items.LEATHER.getDefaultInstance(),
                ModItems.PlainRune.get().getDefaultInstance(),
                ModItems.PlainManaBook.get().getDefaultInstance(),
                ModItems.PlainPower.get().getDefaultInstance(),
                new ItemStack(ModItems.silverCoin.get(), 4),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("平原村皮革制作师", "plainLeather", CustomStyle.styleOfPlain, VillagerType.PLAINS,
                VillagerProfession.LEATHERWORKER, contentList);
        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.copperCoin.get(), 8));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainSoul.get(), 64));
                    add(new ItemStack(ModItems.copperCoin.get(), 32));
                    add(new ItemStack(ModItems.gemPiece.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainRune.get(), 4));
                    add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
                    add(new ItemStack(Items.LEATHER, 12));
                    add(new ItemStack(ModItems.gemPiece.get(), 8));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainRune.get(), 2));
                    add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
                    add(new ItemStack(Items.LEATHER, 6));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainSoul.get(), 64));
                }});
            }
        }
    }

    public static void plainForgeHammer() {
        ItemStack[] itemStacks = {
                ModItems.WoodHammer.get().getDefaultInstance(),
                ModItems.StoneHammer.get().getDefaultInstance(),
                ModItems.IronHammer.get().getDefaultInstance(),
                Items.IRON_INGOT.getDefaultInstance(),
                ModItems.PlainBracelet.get().getDefaultInstance(),
                GemItems.plainGem.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("平原村锻造师", "plainForgeHammer", CustomStyle.styleOfPlain, VillagerType.PLAINS,
                VillagerProfession.ARMORER, contentList);
        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.copperCoin.get(), 32));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.copperCoin.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.silverCoin.get(), 16));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.silverCoin.get(), 8));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainRune.get(), 4));
                    add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
                    add(new ItemStack(Items.IRON_INGOT, 2));
                    add(new ItemStack(ModItems.gemPiece.get(), 8));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PlainRune.get(), 10));
                    add(new ItemStack(ModItems.completeGem.get(), 16));
                }});
            }
        }
    }

    public static void forestForgeHammer() {
        ItemStack woodHammer = new ItemStack(ModItems.WoodHammer.get());
        ItemStack stoneHammer = new ItemStack(ModItems.StoneHammer.get());
        ItemStack ironHammer = new ItemStack(ModItems.IronHammer.get());
        ItemStack copperHammer = new ItemStack(ModItems.CopperHammer.get());
        ItemStack ironIngot = new ItemStack(Items.IRON_INGOT);
        ItemStack forestBracelet = new ItemStack(ModItems.ForestBracelet.get());
        ItemStack forestGem = new ItemStack(GemItems.forestGem.get());
        ItemStack[] itemStacks = {
                woodHammer, stoneHammer, ironHammer, copperHammer, ironIngot, forestBracelet, forestGem
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("雨林村森林锻造师", "forestForgeHammer", CustomStyle.styleOfForest, VillagerType.JUNGLE,
                VillagerProfession.ARMORER, contentList);
        tradeRecipeMap.put(woodHammer,
                List.of(new ItemStack(ModItems.silverCoin.get(), 4)));
        tradeRecipeMap.put(stoneHammer,
                List.of(new ItemStack(ModItems.silverCoin.get(), 8)));
        tradeRecipeMap.put(ironHammer,
                List.of(new ItemStack(ModItems.silverCoin.get(), 16)));
        tradeRecipeMap.put(copperHammer,
                List.of(new ItemStack(ModItems.goldCoin.get(), 4)));
        tradeRecipeMap.put(ironIngot,
                List.of(new ItemStack(ModItems.silverCoin.get(), 8)));
        tradeRecipeMap.put(forestBracelet,
                List.of(new ItemStack(ModItems.ForestRune.get(), 4),
                        new ItemStack(ModItems.LifeElementPiece0.get(), 14),
                        new ItemStack(Items.IRON_INGOT, 2),
                        new ItemStack(ModItems.gemPiece.get(), 8)));
        tradeRecipeMap.put(forestGem,
                List.of(new ItemStack(ModItems.ForestRune.get(), 10),
                        new ItemStack(ModItems.completeGem.get(), 16)));
    }

    public static void forestTool() {
        ItemStack oakLog4 = new ItemStack(Items.OAK_LOG, 4);
        ItemStack oakLog16 = new ItemStack(Items.OAK_LOG, 16);
        ItemStack forestRune = new ItemStack(ModItems.ForestRune.get());
        ItemStack forestPower = new ItemStack(ModItems.ForestPower.get());
        ItemStack forestManaBook = new ItemStack(ModItems.ForestManaBook.get());
        ItemStack silverCoin = new ItemStack(ModItems.silverCoin.get(), 8);
        ItemStack[] itemStacks = {
                oakLog4, oakLog16, forestRune,
                forestPower, forestManaBook, silverCoin
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("雨林村森林工具制作师", "forestTool", CustomStyle.styleOfForest, VillagerType.JUNGLE,
                VillagerProfession.TOOLSMITH, contentList);
        tradeRecipeMap.put(oakLog4,
                List.of(new ItemStack(ModItems.ForestSoul.get(), 1)));
        tradeRecipeMap.put(oakLog16,
                List.of(new ItemStack(ModItems.ForestSoul.get(), 4)));
        tradeRecipeMap.put(forestRune,
                List.of(new ItemStack(ModItems.ForestSoul.get(), 64),
                        new ItemStack(ModItems.silverCoin.get(), 8),
                        new ItemStack(ModItems.gemPiece.get(), 1)));
        tradeRecipeMap.put(forestPower,
                List.of(new ItemStack(ModItems.ForestRune.get(), 4),
                        new ItemStack(ModItems.LifeElementPiece0.get(), 14),
                        new ItemStack(Items.LEATHER, 12),
                        new ItemStack(ModItems.gemPiece.get(), 8)));
        tradeRecipeMap.put(forestManaBook,
                List.of(new ItemStack(ModItems.PlainManaBook.get(), 1),
                        new ItemStack(ModItems.ForestRune.get(), 4),
                        new ItemStack(ModItems.LifeElementPiece0.get(), 14),
                        new ItemStack(Items.LEATHER, 12)));
        tradeRecipeMap.put(silverCoin,
                List.of(new ItemStack(ModItems.ForestSoul.get(), 64)));
    }

    public static void riverTool() {
        ItemStack waterBottle = new ItemStack(ModItems.WaterBottle.get(), 64);
        ItemStack lakeRune = new ItemStack(ModItems.LakeRune.get());
        ItemStack lakePower = new ItemStack(ModItems.LakePower.get());
        ItemStack lakeBracelet = new ItemStack(ModItems.LakeBracelet.get());
        ItemStack lakeManaBook = new ItemStack(ModItems.LakeManaBook.get());
        ItemStack lakeGem = new ItemStack(GemItems.lakeGem.get());
        ItemStack silverCoin = new ItemStack(ModItems.silverCoin.get(), 8);
        ItemStack[] itemStacks = {
                waterBottle, lakeRune, lakePower,
                lakeBracelet, lakeManaBook, lakeGem, silverCoin
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("德朗斯蒂克水文学者", "riverTool", CustomStyle.styleOfLake, VillagerType.SWAMP,
                VillagerProfession.LIBRARIAN, contentList);
        tradeRecipeMap.put(waterBottle,
                List.of(new ItemStack(ModItems.LakeSoul.get(), 8)));
        tradeRecipeMap.put(lakeRune,
                List.of(new ItemStack(ModItems.LakeSoul.get(), 64),
                        new ItemStack(ModItems.silverCoin.get(), 8),
                        new ItemStack(ModItems.gemPiece.get(), 2)));
        tradeRecipeMap.put(lakePower,
                List.of(new ItemStack(ModItems.LakeRune.get(), 4),
                        new ItemStack(ModItems.WaterElementPiece0.get(), 14),
                        new ItemStack(Items.LEATHER, 12),
                        new ItemStack(ModItems.gemPiece.get(), 8)));
        tradeRecipeMap.put(lakeBracelet,
                List.of(new ItemStack(ModItems.LakeRune.get(), 4),
                        new ItemStack(ModItems.WaterElementPiece0.get(), 14),
                        new ItemStack(Items.IRON_INGOT, 2),
                        new ItemStack(ModItems.gemPiece.get(), 8)));
        tradeRecipeMap.put(lakeManaBook,
                List.of(new ItemStack(ModItems.ForestManaBook.get()),
                        new ItemStack(ModItems.LakeRune.get(), 4),
                        new ItemStack(ModItems.WaterElementPiece0.get(), 14),
                        new ItemStack(Items.LEATHER, 12)));
        tradeRecipeMap.put(lakeGem,
                List.of(new ItemStack(ModItems.LakeRune.get(), 10),
                        new ItemStack(ModItems.completeGem.get(), 16)));
        tradeRecipeMap.put(silverCoin,
                List.of(new ItemStack(ModItems.LakeSoul.get(), 64)));
    }

    public static void mineTool() {
        ItemStack mineRune = new ItemStack(ModItems.MineRune.get());
        ItemStack mineBracelet = new ItemStack(ModItems.MineBracelet.get());
        ItemStack mineShield = new ItemStack(ModItems.MineShield.get());
        ItemStack mineGem = new ItemStack(GemItems.mineGem.get());
        ItemStack silverCoin = new ItemStack(ModItems.silverCoin.get(), 10);
        ItemStack[] itemStacks = {
                mineRune, mineBracelet, mineShield, mineGem, silverCoin
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("雨林村铁匠", "mineTool", CustomStyle.styleOfMine, VillagerType.SWAMP,
                VillagerProfession.WEAPONSMITH, contentList);
        tradeRecipeMap.put(mineRune,
                List.of(new ItemStack(ModItems.MineSoul.get(), 64),
                        new ItemStack(ModItems.silverCoin.get(), 8),
                        new ItemStack(ModItems.gemPiece.get(), 2)));
        tradeRecipeMap.put(mineBracelet,
                List.of(new ItemStack(ModItems.MineRune.get(), 4),
                        new ItemStack(ModItems.StoneElementPiece0.get(), 14),
                        new ItemStack(Items.IRON_INGOT, 2),
                        new ItemStack(ModItems.gemPiece.get(), 8)));
        tradeRecipeMap.put(mineShield,
                List.of(new ItemStack(ModItems.MineRune.get(), 8),
                        new ItemStack(ModItems.StoneElementPiece0.get(), 14),
                        new ItemStack(Items.IRON_INGOT, 4)));
        tradeRecipeMap.put(mineGem,
                List.of(new ItemStack(ModItems.MineRune.get(), 10),
                        new ItemStack(ModItems.completeGem.get(), 16)));
        tradeRecipeMap.put(silverCoin,
                List.of(new ItemStack(ModItems.MineSoul.get(), 64)));
    }

    public static void volcanoForgeHammer() {
        ItemStack ironHammer = new ItemStack(ModItems.IronHammer.get());
        ItemStack copperHammer = new ItemStack(ModItems.CopperHammer.get());
        ItemStack goldHammer = new ItemStack(ModItems.GoldHammer.get());
        ItemStack diamondHammer = new ItemStack(ModItems.DiamondHammer.get());
        ItemStack ironIngot = new ItemStack(Items.IRON_INGOT);
        ItemStack volcanoBracelet = new ItemStack(ModItems.VolcanoBracelet.get());
        ItemStack volcanoGem = new ItemStack(GemItems.volcanoGem.get());
        ItemStack[] itemStacks = {
                ironHammer, copperHammer, goldHammer, diamondHammer, ironIngot, volcanoBracelet, volcanoGem
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("火山村锻造师", "volcanoForgeHammer", CustomStyle.styleOfVolcano, VillagerType.DESERT,
                VillagerProfession.ARMORER, contentList);
        tradeRecipeMap.put(ironHammer,
                List.of(new ItemStack(ModItems.silverCoin.get(), 16)));
        tradeRecipeMap.put(copperHammer,
                List.of(new ItemStack(ModItems.goldCoin.get(), 4)));
        tradeRecipeMap.put(goldHammer,
                List.of(new ItemStack(ModItems.goldCoin.get(), 16)));
        tradeRecipeMap.put(diamondHammer,
                List.of(new ItemStack(ModItems.goldCoin.get(), 64)));
        tradeRecipeMap.put(ironIngot,
                List.of(new ItemStack(ModItems.silverCoin.get(), 8)));
        tradeRecipeMap.put(volcanoBracelet,
                List.of(new ItemStack(ModItems.VolcanoRune.get(), 4),
                        new ItemStack(ModItems.FireElementPiece0.get(), 14),
                        new ItemStack(Items.IRON_INGOT, 2),
                        new ItemStack(ModItems.gemPiece.get(), 8)));
        tradeRecipeMap.put(volcanoGem,
                List.of(new ItemStack(ModItems.VolcanoRune.get(), 10),
                        new ItemStack(ModItems.completeGem.get(), 16)));
    }

    public static void volcanoTool() {
        ItemStack coal4 = new ItemStack(Items.COAL, 4);
        ItemStack coal16 = new ItemStack(Items.COAL, 16);
        ItemStack volcanoRune = new ItemStack(ModItems.VolcanoRune.get());
        ItemStack volcanoPower = new ItemStack(ModItems.VolcanoPower.get());
        ItemStack volcanoManaBook = new ItemStack(ModItems.VolcanoManaBook.get());
        ItemStack silverCoin = new ItemStack(ModItems.silverCoin.get(), 12);
        ItemStack[] itemStacks = {
                coal4, coal16, volcanoRune,
                volcanoPower, volcanoManaBook, silverCoin
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("火山村工具制作师", "volcanoTool", CustomStyle.styleOfVolcano, VillagerType.DESERT,
                VillagerProfession.TOOLSMITH, contentList);
        tradeRecipeMap.put(coal4,
                List.of(new ItemStack(ModItems.VolcanoSoul.get(), 1)));
        tradeRecipeMap.put(coal16,
                List.of(new ItemStack(ModItems.VolcanoSoul.get(), 4)));
        tradeRecipeMap.put(volcanoRune,
                List.of(new ItemStack(ModItems.VolcanoSoul.get(), 64),
                        new ItemStack(ModItems.goldCoin.get(), 2),
                        new ItemStack(ModItems.gemPiece.get(), 1)));
        tradeRecipeMap.put(volcanoPower,
                List.of(new ItemStack(ModItems.VolcanoRune.get(), 4),
                        new ItemStack(ModItems.FireElementPiece0.get(), 14),
                        new ItemStack(Items.LEATHER, 12),
                        new ItemStack(ModItems.gemPiece.get(), 8)));
        tradeRecipeMap.put(volcanoManaBook,
                List.of(new ItemStack(ModItems.LakeManaBook.get(), 1),
                        new ItemStack(ModItems.VolcanoRune.get(), 4),
                        new ItemStack(ModItems.FireElementPiece0.get(), 14),
                        new ItemStack(Items.LEATHER, 12)));
        tradeRecipeMap.put(silverCoin,
                List.of(new ItemStack(ModItems.VolcanoSoul.get(), 64)));
    }

    public static void snowForgeHammer() {
        ItemStack ironHammer = new ItemStack(ModItems.IronHammer.get());
        ItemStack copperHammer = new ItemStack(ModItems.CopperHammer.get());
        ItemStack goldHammer = new ItemStack(ModItems.GoldHammer.get());
        ItemStack diamondHammer = new ItemStack(ModItems.DiamondHammer.get());
        ItemStack ironIngot = new ItemStack(Items.IRON_INGOT);
        ItemStack snowBracelet = new ItemStack(ModItems.SnowBracelet.get());
        ItemStack snowGem = new ItemStack(GemItems.snowGem.get());
        ItemStack[] itemStacks = {
                ironHammer, copperHammer, goldHammer, diamondHammer, ironIngot, snowBracelet, snowGem
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("北冰村锻造师", "snowForgeHammer", CustomStyle.styleOfSnow, VillagerType.SNOW,
                VillagerProfession.ARMORER, contentList);
        tradeRecipeMap.put(ironHammer,
                List.of(new ItemStack(ModItems.silverCoin.get(), 16)));
        tradeRecipeMap.put(copperHammer,
                List.of(new ItemStack(ModItems.goldCoin.get(), 4)));
        tradeRecipeMap.put(goldHammer,
                List.of(new ItemStack(ModItems.goldCoin.get(), 16)));
        tradeRecipeMap.put(diamondHammer,
                List.of(new ItemStack(ModItems.goldCoin.get(), 64)));
        tradeRecipeMap.put(ironIngot,
                List.of(new ItemStack(ModItems.silverCoin.get(), 8)));
        tradeRecipeMap.put(snowBracelet,
                List.of(new ItemStack(ModItems.SnowRune.get(), 4),
                        new ItemStack(ModItems.IceElementPiece0.get(), 14),
                        new ItemStack(Items.IRON_INGOT, 2),
                        new ItemStack(ModItems.gemPiece.get(), 8)));
        tradeRecipeMap.put(snowGem,
                List.of(new ItemStack(ModItems.SnowRune.get(), 10),
                        new ItemStack(ModItems.completeGem.get(), 16)));
    }

    public static void snowTool() {
        ItemStack snowRune = new ItemStack(ModItems.SnowRune.get());
        ItemStack snowPower = new ItemStack(ModItems.SnowPower.get());
        ItemStack snowManaBook = new ItemStack(ModItems.SnowManaBook.get());
        ItemStack silverCoin = new ItemStack(ModItems.silverCoin.get(), 16);
        ItemStack[] itemStacks = {
                snowRune, snowPower, snowManaBook, silverCoin
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("北冰村工具制作师", "snowTool", CustomStyle.styleOfSnow, VillagerType.DESERT,
                VillagerProfession.TOOLSMITH, contentList);
        tradeRecipeMap.put(snowRune,
                List.of(new ItemStack(ModItems.SnowSoul.get(), 64),
                        new ItemStack(ModItems.goldCoin.get(), 3),
                        new ItemStack(ModItems.gemPiece.get(), 1)));
        tradeRecipeMap.put(snowPower,
                List.of(new ItemStack(ModItems.SnowRune.get(), 4),
                        new ItemStack(ModItems.IceElementPiece0.get(), 14),
                        new ItemStack(Items.LEATHER, 12),
                        new ItemStack(ModItems.gemPiece.get(), 8)));
        tradeRecipeMap.put(snowManaBook,
                List.of(new ItemStack(ModItems.VolcanoManaBook.get(), 1),
                        new ItemStack(ModItems.SnowRune.get(), 4),
                        new ItemStack(ModItems.IceElementPiece0.get(), 14),
                        new ItemStack(Items.LEATHER, 12)));
        tradeRecipeMap.put(silverCoin,
                List.of(new ItemStack(ModItems.SnowSoul.get(), 64)));
    }

    public static void skyTool() {
        ItemStack skyRune = new ItemStack(ModItems.SkyRune.get());
        ItemStack skyBracelet = new ItemStack(ModItems.SkyBracelet.get());
        ItemStack skyGem = new ItemStack(GemItems.skyGem.get());
        ItemStack silverCoin = new ItemStack(ModItems.silverCoin.get(), 16);
        ItemStack[] itemStacks = {
                skyRune, skyBracelet, skyGem, silverCoin
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("天空城锻造师", "skyTool", CustomStyle.styleOfSky, VillagerType.PLAINS,
                VillagerProfession.WEAPONSMITH, contentList);

        tradeRecipeMap.put(skyRune,
                List.of(new ItemStack(ModItems.SkySoul.get(), 64),
                        new ItemStack(ModItems.goldCoin.get(), 4),
                        new ItemStack(ModItems.gemPiece.get(), 1)));
        tradeRecipeMap.put(skyBracelet,
                List.of(new ItemStack(ModItems.SkyRune.get(), 4),
                        new ItemStack(ModItems.WindElementPiece0.get(), 14),
                        new ItemStack(Items.LEATHER, 12),
                        new ItemStack(ModItems.gemPiece.get(), 8)));
        tradeRecipeMap.put(skyGem,
                List.of(new ItemStack(ModItems.SkyRune.get(), 10),
                        new ItemStack(ModItems.completeGem.get(), 16)));
        tradeRecipeMap.put(silverCoin,
                List.of(new ItemStack(ModItems.SkySoul.get(), 64)));
    }

    public static void mineCharm() {
        ItemStack ironPickaxe = new ItemStack(Items.IRON_PICKAXE);
        ItemStack diamondPickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
        ItemStack netheritePickaxe = new ItemStack(Items.NETHERITE_PICKAXE);
        ItemStack potion = new ItemStack(Items.POTION);
        potion.getOrCreateTag().putString("Potion", "minecraft:long_night_vision");

        ItemStack minePiece1 = new ItemStack(SpurItems.minePiece1.get());
        ItemStack mineCharm0 = new ItemStack(SpurItems.mineCharm0.get());
        ItemStack mineCharm1 = new ItemStack(SpurItems.mineCharm1.get());
        ItemStack mineCharm2 = new ItemStack(SpurItems.mineCharm2.get());
        ItemStack mineCharm3 = new ItemStack(SpurItems.mineCharm3.get());
        ItemStack mineCharm4 = new ItemStack(SpurItems.mineCharm4.get());
        ItemStack mineCharm5 = new ItemStack(SpurItems.mineCharm5.get());
        ItemStack mineCharm6 = new ItemStack(SpurItems.mineCharm6.get());
        ItemStack oreRune = new ItemStack(ModItems.OreRune.get(), 1);
        ItemStack[] itemStacks = {
                ironPickaxe, diamondPickaxe, netheritePickaxe, minePiece1, potion, oreRune,
                mineCharm0, mineCharm1, mineCharm2, mineCharm3, mineCharm4, mineCharm5, mineCharm6,

        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("采矿大师", "mineCharm", CustomStyle.styleOfMine, VillagerType.PLAINS,
                VillagerProfession.WEAPONSMITH, contentList);

        tradeRecipeMap.put(ironPickaxe, List.of(new ItemStack(ModItems.goldCoin.get(), 1)));
        tradeRecipeMap.put(diamondPickaxe, List.of(new ItemStack(ModItems.goldCoin.get(), 3)));
        tradeRecipeMap.put(netheritePickaxe, List.of(new ItemStack(ModItems.goldCoin.get(), 8)));
        tradeRecipeMap.put(potion, List.of(new ItemStack(ModItems.silverCoin.get(), 1)));
        tradeRecipeMap.put(oreRune, List.of(new ItemStack(Items.EMERALD, 4), new ItemStack(Items.DIAMOND, 4),
                new ItemStack(Items.REDSTONE, 16), new ItemStack(Items.LAPIS_LAZULI, 16)));

        tradeRecipeMap.put(minePiece1, List.of(new ItemStack(SpurItems.minePiece.get(), 64)));

        tradeRecipeMap.put(mineCharm0, List.of(new ItemStack(SpurItems.minePiece1.get(), 1)));

        tradeRecipeMap.put(mineCharm1, List.of(new ItemStack(SpurItems.minePiece1.get(), 2),
                new ItemStack(SpurItems.mineCharm0.get())));

        tradeRecipeMap.put(mineCharm2, List.of(new ItemStack(SpurItems.minePiece1.get(), 4),
                new ItemStack(SpurItems.mineCharm1.get())));

        tradeRecipeMap.put(mineCharm3, List.of(new ItemStack(SpurItems.minePiece1.get(), 8),
                new ItemStack(SpurItems.mineCharm2.get())));

        tradeRecipeMap.put(mineCharm4, List.of(new ItemStack(SpurItems.minePiece1.get(), 16),
                new ItemStack(SpurItems.mineCharm3.get())));

        tradeRecipeMap.put(mineCharm5, List.of(new ItemStack(SpurItems.minePiece1.get(), 32),
                new ItemStack(SpurItems.mineCharm4.get())));

        tradeRecipeMap.put(mineCharm6, List.of(new ItemStack(SpurItems.minePiece1.get(), 64),
                new ItemStack(SpurItems.mineCharm5.get())));
    }

    public static void seaCharm() {
        ItemStack fishingRod = new ItemStack(Items.FISHING_ROD);
        ItemStack seaPiece1 = new ItemStack(SpurItems.seaPiece1.get());
        ItemStack seaCharm0 = new ItemStack(SpurItems.seaCharm0.get());
        ItemStack seaCharm1 = new ItemStack(SpurItems.seaCharm1.get());
        ItemStack seaCharm2 = new ItemStack(SpurItems.seaCharm2.get());
        ItemStack seaCharm3 = new ItemStack(SpurItems.seaCharm3.get());
        ItemStack seaCharm4 = new ItemStack(SpurItems.seaCharm4.get());
        ItemStack seaCharm5 = new ItemStack(SpurItems.seaCharm5.get());
        ItemStack seaCharm6 = new ItemStack(SpurItems.seaCharm6.get());
        ItemStack[] itemStacks = {
                fishingRod, seaPiece1,
                seaCharm0, seaCharm1, seaCharm2, seaCharm3, seaCharm4, seaCharm5, seaCharm6
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("钓鱼大师", "seaCharm", CustomStyle.styleOfSea, VillagerType.SWAMP,
                VillagerProfession.FISHERMAN, contentList);

        tradeRecipeMap.put(fishingRod, List.of(new ItemStack(ModItems.goldCoin.get(), 1)));

        tradeRecipeMap.put(seaPiece1, List.of(new ItemStack(SpurItems.seaPiece.get(), 64)));

        tradeRecipeMap.put(seaCharm0, List.of(new ItemStack(SpurItems.seaPiece1.get(), 1)));

        tradeRecipeMap.put(seaCharm1, List.of(new ItemStack(SpurItems.seaPiece1.get(), 2),
                new ItemStack(SpurItems.seaCharm0.get())));

        tradeRecipeMap.put(seaCharm2, List.of(new ItemStack(SpurItems.seaPiece1.get(), 4),
                new ItemStack(SpurItems.seaCharm1.get())));

        tradeRecipeMap.put(seaCharm3, List.of(new ItemStack(SpurItems.seaPiece1.get(), 8),
                new ItemStack(SpurItems.seaCharm2.get())));

        tradeRecipeMap.put(seaCharm4, List.of(new ItemStack(SpurItems.seaPiece1.get(), 16),
                new ItemStack(SpurItems.seaCharm3.get())));

        tradeRecipeMap.put(seaCharm5, List.of(new ItemStack(SpurItems.seaPiece1.get(), 32),
                new ItemStack(SpurItems.seaCharm4.get())));

        tradeRecipeMap.put(seaCharm6, List.of(new ItemStack(SpurItems.seaPiece1.get(), 64),
                new ItemStack(SpurItems.seaCharm5.get())));
    }

    public static void cropCharm() {
        ItemStack cropPiece1 = new ItemStack(SpurItems.cropPiece1.get());
        ItemStack cropCharm0 = new ItemStack(SpurItems.cropCharm0.get());
        ItemStack cropCharm1 = new ItemStack(SpurItems.cropCharm1.get());
        ItemStack cropCharm2 = new ItemStack(SpurItems.cropCharm2.get());
        ItemStack cropCharm3 = new ItemStack(SpurItems.cropCharm3.get());
        ItemStack cropCharm4 = new ItemStack(SpurItems.cropCharm4.get());
        ItemStack cropCharm5 = new ItemStack(SpurItems.cropCharm5.get());
        ItemStack cropCharm6 = new ItemStack(SpurItems.cropCharm6.get());
        ItemStack natureCore = new ItemStack(ModItems.NaturalCore.get());
        ItemStack[] itemStacks = {
                cropPiece1, natureCore,
                cropCharm0, cropCharm1, cropCharm2, cropCharm3, cropCharm4, cropCharm5, cropCharm6
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("农耕大师", "cropCharm", CustomStyle.styleOfField, VillagerType.PLAINS,
                VillagerProfession.FARMER, contentList);

        tradeRecipeMap.put(cropPiece1, List.of(new ItemStack(SpurItems.cropPiece.get(), 64)));

        tradeRecipeMap.put(natureCore, List.of(new ItemStack(Items.WHEAT, 24),
                new ItemStack(Items.CARROT, 24),
                new ItemStack(Items.POTATO, 24),
                new ItemStack(Items.BEETROOT, 24),
                new ItemStack(Items.TORCHFLOWER, 24),
                new ItemStack(Items.SWEET_BERRIES, 24)));

        tradeRecipeMap.put(cropCharm0, List.of(new ItemStack(SpurItems.cropPiece1.get(), 1)));

        tradeRecipeMap.put(cropCharm1, List.of(new ItemStack(SpurItems.cropPiece1.get(), 2),
                new ItemStack(SpurItems.cropCharm0.get())));

        tradeRecipeMap.put(cropCharm2, List.of(new ItemStack(SpurItems.cropPiece1.get(), 4),
                new ItemStack(SpurItems.cropCharm1.get())));

        tradeRecipeMap.put(cropCharm3, List.of(new ItemStack(SpurItems.cropPiece1.get(), 8),
                new ItemStack(SpurItems.cropCharm2.get())));

        tradeRecipeMap.put(cropCharm4, List.of(new ItemStack(SpurItems.cropPiece1.get(), 16),
                new ItemStack(SpurItems.cropCharm3.get())));

        tradeRecipeMap.put(cropCharm5, List.of(new ItemStack(SpurItems.cropPiece1.get(), 32),
                new ItemStack(SpurItems.cropCharm4.get())));

        tradeRecipeMap.put(cropCharm6, List.of(new ItemStack(SpurItems.cropPiece1.get(), 64),
                new ItemStack(SpurItems.cropCharm5.get())));
    }

    public static void logCharm() {
        ItemStack ironAxe = new ItemStack(Items.IRON_AXE);
        ItemStack diamondAxe = new ItemStack(Items.DIAMOND_AXE);
        ItemStack netheriteAxe = new ItemStack(Items.NETHERITE_AXE);
        ItemStack logPiece1 = new ItemStack(SpurItems.logPiece1.get());
        ItemStack logCharm0 = new ItemStack(SpurItems.logCharm0.get());
        ItemStack logCharm1 = new ItemStack(SpurItems.logCharm1.get());
        ItemStack logCharm2 = new ItemStack(SpurItems.logCharm2.get());
        ItemStack logCharm3 = new ItemStack(SpurItems.logCharm3.get());
        ItemStack logCharm4 = new ItemStack(SpurItems.logCharm4.get());
        ItemStack logCharm5 = new ItemStack(SpurItems.logCharm5.get());
        ItemStack logCharm6 = new ItemStack(SpurItems.logCharm6.get());
        ItemStack[] itemStacks = {
                ironAxe, diamondAxe, netheriteAxe, logPiece1,
                logCharm0, logCharm1, logCharm2, logCharm3, logCharm4, logCharm5, logCharm6
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("伐木大师", "logCharm", CustomStyle.styleOfHusk, VillagerType.JUNGLE,
                VillagerProfession.LEATHERWORKER, contentList);

        tradeRecipeMap.put(ironAxe, List.of(new ItemStack(ModItems.goldCoin.get(), 1)));
        tradeRecipeMap.put(diamondAxe, List.of(new ItemStack(ModItems.goldCoin.get(), 3)));
        tradeRecipeMap.put(netheriteAxe, List.of(new ItemStack(ModItems.goldCoin.get(), 8)));

        tradeRecipeMap.put(logPiece1, List.of(new ItemStack(SpurItems.logPiece.get(), 64)));

        tradeRecipeMap.put(logCharm0, List.of(new ItemStack(SpurItems.logPiece1.get(), 1)));

        tradeRecipeMap.put(logCharm1, List.of(new ItemStack(SpurItems.logPiece1.get(), 2),
                new ItemStack(SpurItems.logCharm0.get())));

        tradeRecipeMap.put(logCharm2, List.of(new ItemStack(SpurItems.logPiece1.get(), 4),
                new ItemStack(SpurItems.logCharm1.get())));

        tradeRecipeMap.put(logCharm3, List.of(new ItemStack(SpurItems.logPiece1.get(), 8),
                new ItemStack(SpurItems.logCharm2.get())));

        tradeRecipeMap.put(logCharm4, List.of(new ItemStack(SpurItems.logPiece1.get(), 16),
                new ItemStack(SpurItems.logCharm3.get())));

        tradeRecipeMap.put(logCharm5, List.of(new ItemStack(SpurItems.logPiece1.get(), 32),
                new ItemStack(SpurItems.logCharm4.get())));

        tradeRecipeMap.put(logCharm6, List.of(new ItemStack(SpurItems.logPiece1.get(), 64),
                new ItemStack(SpurItems.logCharm5.get())));
    }

    public static void elementMaster() {
        ItemStack rainbowPowder = new ItemStack(ModItems.RainbowPowder.get());
        ItemStack rainbowCrystal = new ItemStack(ModItems.RainbowCrystal.get());

        List<ItemStack> piece0 = List.of(new ItemStack(ModItems.LifeElementPiece0.get()),
                new ItemStack(ModItems.WaterElementPiece0.get()),
                new ItemStack(ModItems.StoneElementPiece0.get()),
                new ItemStack(ModItems.FireElementPiece0.get()),
                new ItemStack(ModItems.IceElementPiece0.get()),
                new ItemStack(ModItems.WindElementPiece0.get()),
                new ItemStack(ModItems.LightningElementPiece0.get()));

        List<ItemStack> piece0_64 = List.of(new ItemStack(ModItems.LifeElementPiece0.get(), 64),
                new ItemStack(ModItems.WaterElementPiece0.get(), 64),
                new ItemStack(ModItems.StoneElementPiece0.get(), 64),
                new ItemStack(ModItems.FireElementPiece0.get(), 64),
                new ItemStack(ModItems.IceElementPiece0.get(), 64),
                new ItemStack(ModItems.WindElementPiece0.get(), 64),
                new ItemStack(ModItems.LightningElementPiece0.get(), 64));

        List<ItemStack> piece1 = List.of(new ItemStack(ModItems.LifeElementPiece1.get()),
                new ItemStack(ModItems.WaterElementPiece1.get()),
                new ItemStack(ModItems.StoneElementPiece1.get()),
                new ItemStack(ModItems.FireElementPiece1.get()),
                new ItemStack(ModItems.IceElementPiece1.get()),
                new ItemStack(ModItems.WindElementPiece1.get()),
                new ItemStack(ModItems.LightningElementPiece1.get()));

        List<ItemStack> piece2 = List.of(new ItemStack(ModItems.LifeElementPiece2.get()),
                new ItemStack(ModItems.WaterElementPiece2.get()),
                new ItemStack(ModItems.StoneElementPiece2.get()),
                new ItemStack(ModItems.FireElementPiece2.get()),
                new ItemStack(ModItems.IceElementPiece2.get()),
                new ItemStack(ModItems.WindElementPiece2.get()),
                new ItemStack(ModItems.LightningElementPiece2.get()));

        ItemStack weakPiece0_64 = new ItemStack(ElementItems.weakPiece0.get(), 64);

        List<ItemStack> crystal = List.of(new ItemStack(ModItems.LifeCrystal0.get()),
                new ItemStack(ModItems.WaterCrystal0.get()),
                new ItemStack(ModItems.StoneCrystal0.get()),
                new ItemStack(ModItems.FireCrystal0.get()),
                new ItemStack(ModItems.IceCrystal0.get()),
                new ItemStack(ModItems.WindCrystal0.get()),
                new ItemStack(ModItems.LightningCrystal0.get()));

        List<ItemStack> holyStone = List.of(
                new ItemStack(ModItems.LifeHolyStone0.get()),
                new ItemStack(ModItems.WaterHolyStone0.get()),
                new ItemStack(ModItems.StoneHolyStone0.get()),
                new ItemStack(ModItems.FireHolyStone0.get()),
                new ItemStack(ModItems.IceHolyStone0.get()),
                new ItemStack(ModItems.WindHolyStone0.get()),
                new ItemStack(ModItems.LightningHolyStone0.get()),

                new ItemStack(ModItems.LifeHolyStone1.get()),
                new ItemStack(ModItems.WaterHolyStone1.get()),
                new ItemStack(ModItems.StoneHolyStone1.get()),
                new ItemStack(ModItems.FireHolyStone1.get()),
                new ItemStack(ModItems.IceHolyStone1.get()),
                new ItemStack(ModItems.WindHolyStone1.get()),
                new ItemStack(ModItems.LightningHolyStone1.get()),

                new ItemStack(ModItems.LifeHolyStone2.get()),
                new ItemStack(ModItems.WaterHolyStone2.get()),
                new ItemStack(ModItems.StoneHolyStone2.get()),
                new ItemStack(ModItems.FireHolyStone2.get()),
                new ItemStack(ModItems.IceHolyStone2.get()),
                new ItemStack(ModItems.WindHolyStone2.get()),
                new ItemStack(ModItems.LightningHolyStone2.get()));

        List<ItemStack> weakPiece0_64List = new ArrayList<>() {{
            for (int i = 0; i < 7; i++) add(new ItemStack(ElementItems.weakPiece0.get(), 64));
        }};
        List<ItemStack> contentList = new ArrayList<>() {{
            add(rainbowPowder);
            add(rainbowCrystal);
            addAll(piece1);
            addAll(weakPiece0_64List);
            addAll(piece0_64);
            addAll(piece2);
            addAll(crystal);
            addAll(holyStone);
        }};
        MyVillagerData.setMyVillagerData("元素大师", "elementMaster", CustomStyle.styleOfWorld, VillagerType.SNOW,
                VillagerProfession.LIBRARIAN, contentList);

        tradeRecipeMap.put(rainbowPowder, new ArrayList<>() {{
            for (ItemStack itemStack : piece0) {
                add(new ItemStack(itemStack.getItem(), 7));
            }
        }});

        tradeRecipeMap.put(rainbowCrystal, List.of(new ItemStack(ModItems.RainbowPowder.get(), 49),
                new ItemStack(ModItems.completeGem.get(), 7)));

        for (int i = 0; i < piece1.size(); i++) {
            ItemStack itemPiece0 = piece0.get(i);
            ItemStack itemPiece1 = piece1.get(i);
            tradeRecipeMap.put(itemPiece1, List.of(new ItemStack(itemPiece0.getItem(), 64),
                    rainbowPowder));
        }

        for (int i = 0; i < weakPiece0_64List.size(); i++) {
            ItemStack weakPiece0_64Item = weakPiece0_64List.get(i);
            ItemStack piece0Item = piece0_64.get(i);
            tradeRecipeMap.put(weakPiece0_64Item, List.of(piece0Item,
                    rainbowPowder));
        }

        for (ItemStack piece0Item : piece0_64) {
            tradeRecipeMap.put(piece0Item, List.of(weakPiece0_64,
                    new ItemStack(piece0Item.getItem())));
        }

        for (int i = 0; i < piece2.size(); i++) {
            ItemStack itemPiece1 = piece1.get(i);
            ItemStack itemPiece2 = piece2.get(i);
            tradeRecipeMap.put(itemPiece2, List.of(new ItemStack(itemPiece1.getItem(), 64),
                    rainbowCrystal));
        }

        for (int i = 0; i < crystal.size(); i++) {
            ItemStack itemCrystal = crystal.get(i);
            ItemStack itemPiece1 = piece1.get(i);
            ItemStack completeGem = ModItems.completeGem.get().getDefaultInstance();
            tradeRecipeMap.put(itemCrystal, List.of(new ItemStack(itemPiece1.getItem(), 32),
                    completeGem));
        }

        for (int i = 0; i < holyStone.size(); i++) {
            ItemStack itemHolyStone = holyStone.get(i);
            ItemStack itemPiece2 = piece2.get(i % 7);
            tradeRecipeMap.put(itemHolyStone, List.of(new ItemStack(itemPiece2.getItem(), 2)));
        }
    }

    public static void runeMaster() {
        ItemStack[] itemStacks = {
                new ItemStack(NewRuneItems.plainNewRune.get()),
                new ItemStack(NewRuneItems.forestNewRune.get()),
                new ItemStack(NewRuneItems.lakeNewRune.get()),
                new ItemStack(NewRuneItems.volcanoNewRune.get()),
                new ItemStack(NewRuneItems.evokerNewRune.get()),

                new ItemStack(NewRuneItems.mineNewRune.get()),
                new ItemStack(NewRuneItems.huskNewRune.get()),
                new ItemStack(NewRuneItems.lightningNewRune.get()),
                new ItemStack(NewRuneItems.netherNewRune.get())
        };
        ItemStack[] materials = {
                new ItemStack(ModItems.PlainRune.get(), 8),
                new ItemStack(ModItems.ForestRune.get(), 8),
                new ItemStack(ModItems.LakeRune.get(), 8),
                new ItemStack(ModItems.VolcanoRune.get(), 8),
                new ItemStack(ModItems.EvokerRune.get(), 2),

                new ItemStack(ModItems.MineRune.get(), 8),
                new ItemStack(ModItems.huskRune.get(), 6),
                new ItemStack(ModItems.LightningRune.get(), 6),
                new ItemStack(ModItems.NetherRune.get(), 2)
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("符石制作大师", "runeMaster", CustomStyle.styleOfMana, VillagerType.JUNGLE,
                VillagerProfession.LIBRARIAN, contentList);
        for (int i = 0; i < itemStacks.length; i++) {
            tradeRecipeMap.put(itemStacks[i], List.of(new ItemStack(NewRuneItems.emptyRune.get(), 2),
                    materials[i]));
        }
    }

    public static void summerEvent() {
        ItemStack goldenCoin = new ItemStack(ModItems.goldCoin.get());
        ItemStack goldenCoin_4 = new ItemStack(ModItems.goldCoin.get(), 4);
        ItemStack summerCurios0 = new ItemStack(SpecialEventItems.SUMMER_CURIOS0.get());
        ItemStack summerCurios1 = new ItemStack(SpecialEventItems.SUMMER_CURIOS1.get());
        ItemStack summerCurios2 = new ItemStack(SpecialEventItems.SUMMER_CURIOS2.get());
        ItemStack summerCurios3 = new ItemStack(SpecialEventItems.SUMMER_CURIOS3.get());
        ItemStack summerCurios4 = new ItemStack(SpecialEventItems.SUMMER_CURIOS4.get());
        ItemStack summerCurios5 = new ItemStack(SpecialEventItems.SUMMER_CURIOS5.get());
        ItemStack[] itemStacks = {
                goldenCoin, goldenCoin_4,
                summerCurios0, summerCurios1, summerCurios2,
                summerCurios3, summerCurios4, summerCurios5
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("暑期摸鱼大师", "summerEvent", CustomStyle.styleOfWater, VillagerType.SWAMP,
                VillagerProfession.FISHERMAN, contentList);

        tradeRecipeMap.put(goldenCoin, List.of(new ItemStack(Items.TROPICAL_FISH, 8)));

        tradeRecipeMap.put(goldenCoin_4, List.of(new ItemStack(SpecialEventItems.SUMMER_VOUCHER.get(), 1)));

        tradeRecipeMap.put(summerCurios0, List.of(new ItemStack(SpecialEventItems.SUMMER_VOUCHER.get(), 24)));

        tradeRecipeMap.put(summerCurios1, List.of(new ItemStack(SpecialEventItems.SUMMER_CURIOS0.get()),
                new ItemStack(SpecialEventItems.SUMMER_VOUCHER.get(), 40)));

        tradeRecipeMap.put(summerCurios2, List.of(new ItemStack(SpecialEventItems.SUMMER_CURIOS1.get()),
                new ItemStack(SpecialEventItems.SUMMER_VOUCHER.get(), 56)));

        tradeRecipeMap.put(summerCurios3, List.of(new ItemStack(SpecialEventItems.SUMMER_CURIOS2.get()),
                new ItemStack(SpecialEventItems.SUMMER_VOUCHER.get(), 8),
                new ItemStack(SpecialEventItems.SUMMER_VOUCHER.get(), 64)));

        tradeRecipeMap.put(summerCurios4, List.of(new ItemStack(SpecialEventItems.SUMMER_CURIOS3.get()),
                new ItemStack(SpecialEventItems.SUMMER_VOUCHER.get(), 24),
                new ItemStack(SpecialEventItems.SUMMER_VOUCHER.get(), 64)));

        tradeRecipeMap.put(summerCurios5, List.of(new ItemStack(SpecialEventItems.SUMMER_CURIOS4.get()),
                new ItemStack(SpecialEventItems.SUMMER_VOUCHER.get(), 40),
                new ItemStack(SpecialEventItems.SUMMER_VOUCHER.get(), 64)));
    }
}
