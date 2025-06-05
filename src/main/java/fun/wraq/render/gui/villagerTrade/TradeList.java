package fun.wraq.render.gui.villagerTrade;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.UniformItems;
import fun.wraq.customized.composites.CompositesItems;
import fun.wraq.customized.uniform.UnCommonUniform;
import fun.wraq.process.system.enhanceForge.ForgeMaterials;
import fun.wraq.process.system.entrustment.mob.MobKillEntrustment;
import fun.wraq.process.system.instance.MopUpPaper;
import fun.wraq.process.system.instance.MopUpPaperItems;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.process.system.profession.pet.allay.item.AllayItems;
import fun.wraq.process.system.profession.smith.SmithItems;
import fun.wraq.process.system.spur.Items.SpurItems;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.end.citadel.CitadelItems;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.gems.GemItems;
import fun.wraq.series.holy.HolyItems;
import fun.wraq.series.holy.ice.IceHolyItems;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import fun.wraq.series.instance.series.warden.WardenItems;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import fun.wraq.series.overworld.extraordinary.ExtraordinaryItems;
import fun.wraq.series.overworld.newarea.NewAreaItems;
import fun.wraq.series.overworld.sakura.bunker.BunkerItems;
import fun.wraq.series.overworld.sun.SunIslandItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class TradeList {
    public static Map<ItemStack, List<ItemStack>> tradeRecipeMap = new HashMap<>();
    public static Map<String, List<ItemStack>> tradeContent = new HashMap<>();

    public static void setTradeContent() {
        if (!tradeRecipeMap.isEmpty() && !tradeContent.isEmpty()) {
            return;
        }
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
        endlessCoreStore();
        forgeHammer();
        sunCurio();
        allay();
        entrustmentStore();
        springEvent();
        divineIsland();
        bunker();
        purpleIronWeapon();
        mushroomGem();
        divineGem();
        iceHoly();
        stoneSpider();
        labourDay();
        gateWay();
        mopUpPaper();

        TradeListNew.init();

        /* 1.0 */

        Snow();
        Evoker();
        Wither();
        Piglin();
        Skeleton();
        Magma();
        Crest();
        forgeMaster();
        SoulEquipment();
        PurpleIron();
        Ice();
        BossItem();
        NetherBow();
        NetherSwordModel();
        NetherWeapon();
        Ruby();
        PlainForestRune();
        LakeVolcanoRune();
        ManaArmor();
        Brewing();
        spider();
        Sakura();
        goldCoinStore();
        Field();
        EndRecall();
        EndPower();
        GoldSmith();
        Earth();
        Blood();
        ManaBook();
        Slime();
        taboo();
        Parkour();
        castle();
        SkyGemStore();
        Food();
        RoseGoldStore();
        BackPack();
        Pearl();
    }

    public static void Snow() {
        ItemStack[] itemStacks = {
                ModItems.SNOW_RUNE.get().getDefaultInstance(),
                ModItems.SNOW_SWORD_0.get().getDefaultInstance(),
                ModItems.SNOW_POWER.get().getDefaultInstance(),
                ModItems.SNOW_HELMET.get().getDefaultInstance(),
                ModItems.SNOW_CHEST.get().getDefaultInstance(),
                ModItems.SNOW_LEGGINGS.get().getDefaultInstance(),
                ModItems.SNOW_BOOTS.get().getDefaultInstance(),
                GemItems.SNOW_GEM.get().getDefaultInstance(),
                ModItems.SNOW_SHIELD.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Snow, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SNOW_SOUL.get(), 64));
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 5));
                }});
                case 1, 2, 3, 4, 5, 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SNOW_RUNE.get()));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SNOW_RUNE.get(), 5));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SNOW_RUNE.get(), 10));
                    add(new ItemStack(ModItems.COMPLETE_GEM.get(), 10));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SNOW_RUNE.get(), 5));
                }});

            }
        }
    }

    public static void Evoker() {
        ItemStack[] itemStacks = {
                ModItems.MANA_BUCKET.get().getDefaultInstance(),
                ModItems.MANA_BALANCE_EMPTY.get().getDefaultInstance(),
                ModItems.EVOKER_RUNE.get().getDefaultInstance(),
                ModItems.EVOKER_SWORD.get().getDefaultInstance(),
                GemItems.EVOKER_GEM.get().getDefaultInstance(),
                ModItems.EVOKER_BOOK_0.get().getDefaultInstance(),
                ModItems.EVOKER_BOOK_1.get().getDefaultInstance(),
                ModItems.EVOKER_BOOK_2.get().getDefaultInstance(),
                ModItems.EVOKER_BOOK_3.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Evoker, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EVOKER_SOUL.get(), 4));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 2));
                    add(new ItemStack(ModItems.GEM_PIECE.get(), 4));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BUCKET.get(), 8));
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 2));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EVOKER_RUNE.get(), 5));
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 2));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EVOKER_RUNE.get(), 10));
                    add(new ItemStack(ModItems.COMPLETE_GEM.get(), 10));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VOLCANO_MANA_BOOK.get(), 1));
                    add(new ItemStack(ModItems.EVOKER_RUNE.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EVOKER_BOOK_0.get(), 1));
                    add(new ItemStack(ModItems.EVOKER_RUNE.get(), 2));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EVOKER_BOOK_1.get(), 1));
                    add(new ItemStack(ModItems.EVOKER_RUNE.get(), 3));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EVOKER_BOOK_2.get(), 1));
                    add(new ItemStack(ModItems.EVOKER_RUNE.get(), 5));
                }});
            }
        }
    }

    public static void Wither() {
        ItemStack[] itemStacks = {
                ModItems.WITHER_RUNE.get().getDefaultInstance(),
                GemItems.WITHER_GEM.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Wither, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WITHER_SKELETON_SOUL.get(), 64));
                    add(new ItemStack(ModItems.NETHER_SOUL.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.COMPLETE_GEM.get(), 10));
                    add(new ItemStack(ModItems.WITHER_RUNE.get(), 10));
                }});
            }
        }
    }

    public static void Piglin() {
        ItemStack[] itemStacks = {
                ModItems.PIGLIN_RUNE.get().getDefaultInstance(),
                GemItems.PIGLIN_GEM.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Piglin, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PIGLIN_SOUL.get(), 64));
                    add(new ItemStack(ModItems.NETHER_SOUL.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.COMPLETE_GEM.get(), 10));
                    add(new ItemStack(ModItems.PIGLIN_RUNE.get(), 10));
                }});
            }
        }
    }

    public static void Skeleton() {
        ItemStack[] itemStacks = {
                ModItems.NETHER_SKELETON_RUNE.get().getDefaultInstance(),
                GemItems.NETHER_SKELETON_GEM.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Skeleton, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NETHER_SKELETON_SOUL.get(), 64));
                    add(new ItemStack(ModItems.NETHER_SOUL.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.COMPLETE_GEM.get(), 10));
                    add(new ItemStack(ModItems.NETHER_SKELETON_RUNE.get(), 10));
                }});
            }
        }
    }

    public static void Magma() {
        ItemStack[] itemStacks = {
                ModItems.MAGMA_RUNE.get().getDefaultInstance(),
                GemItems.MAGMA_GEM.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Magma, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MAGMA_SOUL.get(), 64));
                    add(new ItemStack(ModItems.NETHER_SOUL.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.COMPLETE_GEM.get(), 10));
                    add(new ItemStack(ModItems.MAGMA_RUNE.get(), 10));
                }});
            }
        }
    }

    public static void Crest() {
        ItemStack[] itemStacks = {
                ModItems.PLAIN_CREST_1.get().getDefaultInstance(),
                ModItems.PLAIN_CREST_2.get().getDefaultInstance(),
                ModItems.PLAIN_CREST_3.get().getDefaultInstance(),
                ModItems.PLAIN_CREST_4.get().getDefaultInstance(),
                ModItems.FOREST_CREST_1.get().getDefaultInstance(),
                ModItems.FOREST_CREST_2.get().getDefaultInstance(),
                ModItems.FOREST_CREST_3.get().getDefaultInstance(),
                ModItems.FOREST_CREST_4.get().getDefaultInstance(),
                ModItems.LAKE_CREST_1.get().getDefaultInstance(),
                ModItems.LAKE_CREST_2.get().getDefaultInstance(),
                ModItems.LAKE_CREST_3.get().getDefaultInstance(),
                ModItems.LAKE_CREST_4.get().getDefaultInstance(),
                ModItems.VOLCANO_CREST_1.get().getDefaultInstance(),
                ModItems.VOLCANO_CREST_2.get().getDefaultInstance(),
                ModItems.VOLCANO_CREST_3.get().getDefaultInstance(),
                ModItems.VOLCANO_CREST_4.get().getDefaultInstance(),
                ModItems.MINE_CREST_1.get().getDefaultInstance(),
                ModItems.MINE_CREST_2.get().getDefaultInstance(),
                ModItems.MINE_CREST_3.get().getDefaultInstance(),
                ModItems.MINE_CREST_4.get().getDefaultInstance(),
                ModItems.SNOW_CREST_1.get().getDefaultInstance(),
                ModItems.SNOW_CREST_2.get().getDefaultInstance(),
                ModItems.SNOW_CREST_3.get().getDefaultInstance(),
                ModItems.SNOW_CREST_4.get().getDefaultInstance(),
                ModItems.SKY_CREST_1.get().getDefaultInstance(),
                ModItems.SKY_CREST_2.get().getDefaultInstance(),
                ModItems.SKY_CREST_3.get().getDefaultInstance(),
                ModItems.SKY_CREST_4.get().getDefaultInstance(),
                ModItems.MANA_CREST_1.get().getDefaultInstance(),
                ModItems.MANA_CREST_2.get().getDefaultInstance(),
                ModItems.MANA_CREST_3.get().getDefaultInstance(),
                ModItems.MANA_CREST_4.get().getDefaultInstance()
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Crest, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_CREST_0.get(), 16));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_CREST_1.get(), 16));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_CREST_2.get(), 16));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_CREST_3.get(), 16));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FOREST_CREST_0.get(), 16));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FOREST_CREST_1.get(), 16));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FOREST_CREST_2.get(), 16));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FOREST_CREST_3.get(), 16));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LAKE_CREST_0.get(), 16));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LAKE_CREST_1.get(), 16));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LAKE_CREST_2.get(), 16));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LAKE_CREST_3.get(), 16));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VOLCANO_CREST_0.get(), 16));
                }});
                case 13 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VOLCANO_CREST_1.get(), 16));
                }});
                case 14 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VOLCANO_CREST_2.get(), 16));
                }});
                case 15 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VOLCANO_CREST_3.get(), 16));
                }});
                case 16 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MINE_CREST_0.get(), 16));
                }});
                case 17 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MINE_CREST_1.get(), 16));
                }});
                case 18 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MINE_CREST_2.get(), 16));
                }});
                case 19 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MINE_CREST_3.get(), 16));
                }});
                case 20 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SNOW_CREST_0.get(), 16));
                }});
                case 21 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SNOW_CREST_1.get(), 16));
                }});
                case 22 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SNOW_CREST_2.get(), 16));
                }});
                case 23 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SNOW_CREST_3.get(), 16));
                }});
                case 24 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SKY_CREST_0.get(), 16));
                }});
                case 25 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SKY_CREST_1.get(), 16));
                }});
                case 26 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SKY_CREST_2.get(), 16));
                }});
                case 27 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SKY_CREST_3.get(), 16));
                }});
                case 28 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_CREST_0.get(), 16));
                }});
                case 29 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_CREST_1.get(), 16));
                }});
                case 30 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_CREST_2.get(), 16));
                }});
                case 31 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_CREST_3.get(), 16));
                }});
            }
        }
    }

    public static void forgeMaster() {
        ItemStack[] itemStacks = {
                GemItems.OPEN_SLOT.get().getDefaultInstance(),
                GemItems.OPEN_SLOT_GOLDEN.get().getDefaultInstance(),
                GemItems.OPEN_SLOT_DIAMOND.get().getDefaultInstance(),
                new ItemStack(ModItems.MINE_SOUL_1.get(), 16),
                ModItems.FORGING_STONE_0.get().getDefaultInstance(),
                ModItems.FORGING_STONE_1.get().getDefaultInstance(),
                ModItems.FORGING_STONE_1.get().getDefaultInstance(),
                ModItems.FORGING_STONE_1.get().getDefaultInstance(),
                ModItems.FORGING_STONE_1.get().getDefaultInstance(),
                ModItems.FORGING_STONE_2.get().getDefaultInstance(),
                ModItems.FORGE_PROTECT.get().getDefaultInstance(),
                ModItems.FORGE_ENHANCE_0.get().getDefaultInstance(),
                ModItems.FORGE_ENHANCE_1.get().getDefaultInstance(),
                GemItems.DISMANTLE.get().getDefaultInstance(),
                ModItems.FORGE_TEMPLATE.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Forge, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 16));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.REFINED_PIECE.get(), 16));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.REFINED_PIECE.get(), 64));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MINE_SOUL.get(), 16));
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 2));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.DIAMOND, 4));
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 4));
                    add(new ItemStack(ModItems.FORGING_STONE_0.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.EMERALD, 4));
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 4));
                    add(new ItemStack(ModItems.FORGING_STONE_0.get(), 1));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.REDSTONE, 16));
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 4));
                    add(new ItemStack(ModItems.FORGING_STONE_0.get(), 1));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.LAPIS_LAZULI, 16));
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 4));
                    add(new ItemStack(ModItems.FORGING_STONE_0.get(), 1));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NETHER_QUARTZ.get(), 32));
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 8));
                    add(new ItemStack(ModItems.FORGING_STONE_1.get(), 1));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 8));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 1));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 4));
                }});
                case 13 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WORLD_SOUL_2.get(), 16));
                }});
                case 14 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 4));
                }});
            }
        }
    }

    public static void SoulEquipment() {
        ItemStack kanupusWingF = new ItemStack(ExtraordinaryItems.KANUPUS_WING_F.get());
        ItemStack[] itemStacks = {
                ModItems.WORLD_SOUL_NOTE.get().getDefaultInstance(),
                ModItems.SOUL_SWORD.get().getDefaultInstance(),
                ModItems.SOUL_BOW.get().getDefaultInstance(),
                ModItems.SOUL_SCEPTRE.get().getDefaultInstance(),
                ModItems.SKILL_RESET.get().getDefaultInstance(),
                GemItems.DISMANTLE.get().getDefaultInstance(),
                ModItems.SWORD_LOTTERY.get().getDefaultInstance(),
                ModItems.SWORD_LOTTERY_1.get().getDefaultInstance(),
                ModItems.BOW_LOTTERY.get().getDefaultInstance(),
                ModItems.BOW_LOTTERY_1.get().getDefaultInstance(),
                ModItems.SCEPTRE_LOTTERY.get().getDefaultInstance(),
                ModItems.SCEPTRE_LOTTERY_1.get().getDefaultInstance(),
                ModItems.FORGE_TEMPLATE.get().getDefaultInstance(),
                ExtraordinaryItems.RUNE.get().getDefaultInstance(),
                ExtraordinaryItems.NAN_HAI_A.get().getDefaultInstance(),
                ExtraordinaryItems.NAN_HAI_M.get().getDefaultInstance(),
                ExtraordinaryItems.TONG_TIAN.get().getDefaultInstance(),
                kanupusWingF
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        List<ItemStack> uniformList = new ArrayList<>() {{
            for (Item item : Utils.uniformList) {
                if (!(item instanceof UnCommonUniform)) {
                    add(item.getDefaultInstance());
                }
            }
        }};
        contentList.addAll(uniformList);
        tradeContent.put(StringUtils.VillagerName.WorldSoul, contentList);

        tradeRecipeMap.put(kanupusWingF, List.of(
                new ItemStack(ExtraordinaryItems.RUNE.get(), 21),
                new ItemStack(ModItems.WORLD_FORGE_STONE.get(), 40),
                new ItemStack(PickaxeItems.TINKER_DIAMOND.get(), 32),
                new ItemStack(OreItems.WRAQ_ORE_2_ITEM.get(), 128),
                new ItemStack(ModItems.SKY_RUNE.get(), 32),
                new ItemStack(ModItems.FOILED_NETHER_IMPRINT.get(), 8),
                new ItemStack(ModItems.MOON_COMPLETE_GEM.get(), 8),
                new ItemStack(DivineIslandItems.REFINED_DIVINE_PIECE.get(), 8),
                new ItemStack(ModItems.COMPLETE_GEM.get(), 32),
                new ItemStack(ModItems.REPUTATION_MEDAL.get(), 64),
                new ItemStack(ModItems.RANDOM_EVENT_MEDAL.get(), 64),
                new ItemStack(ModItems.MILLION_MONEY.get(), 1)
        ));
        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WORLD_SOUL_NOTE.get(), 1));
                }});
                case 1, 2, 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 16));
                }});
                case 4, 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WORLD_SOUL_2.get(), 16));
                }});
                case 6, 7, 8, 9, 10, 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WORLD_SOUL_5.get(), 40));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 4));
                }});
                case 13 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WORLD_SOUL_5.get(), 100));
                    add(new ItemStack(ExtraordinaryItems.PIECE.get()));
                }});
                case 14, 15 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ExtraordinaryItems.RUNE.get(), 14));
                    add(new ItemStack(ModItems.WORLD_FORGE_STONE.get(), 28));
                    add(new ItemStack(ModItems.SEA_RUNE.get(), 16));
                    add(new ItemStack(ModItems.SHIP_PIECE.get(), 512));
                    add(new ItemStack(ModItems.LIGHTNING_RUNE.get(), 16));
                }});
                case 16 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ExtraordinaryItems.RUNE.get(), 14));
                    add(new ItemStack(ModItems.WORLD_FORGE_STONE.get(), 28));
                    add(new ItemStack(ModItems.SKY_RUNE.get(), 16));
                    add(new ItemStack(ModItems.MOON_COMPLETE_GEM.get(), 8));
                    add(new ItemStack(ModItems.STAR_STAR.get(), 16));
                }});
            }
        }
        for (ItemStack itemStack : uniformList) {
            tradeRecipeMap.put(itemStack, List.of(new ItemStack(UniformItems.UNIFORM_PIECE.get(), 2)));
        }
    }

    public static void PurpleIron() {

        ItemStack[] itemStacks = {
                ModItems.PURPLE_IRON_INGOT.get().getDefaultInstance(),
                ModItems.PURPLE_IRON_INGOT.get().getDefaultInstance(),
                ModItems.GOLD_COIN.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.PurpleIron, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 16));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PURPLE_IRON_PIECE.get(), 64));
                    add(new ItemStack(Items.RAW_GOLD, 8));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PURPLE_IRON_PIECE.get(), 64));
                }});
            }
        }

    }

    public static void Ice() {
        ItemStack[] itemStacks = {
                ModItems.LEATHER_RUNE.get().getDefaultInstance(),
                ModItems.LEATHER_HELMET.get().getDefaultInstance(),
                ModItems.LEATHER_CHEST.get().getDefaultInstance(),
                ModItems.LEATHER_LEGGINGS.get().getDefaultInstance(),
                ModItems.LEATHER_BOOTS.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Ice, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LEATHER_SOUL.get(), 64));
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 10));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LEATHER_RUNE.get(), 5));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LEATHER_RUNE.get(), 8));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LEATHER_RUNE.get(), 7));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LEATHER_RUNE.get(), 4));
                }});
            }
        }
    }

    public static void BossItem() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.PLAIN_BOSS_SOUL.get(), 32),
                new ItemStack(ModItems.PLAIN_BOSS_SOUL.get(), 32),
                new ItemStack(ModItems.PLAIN_BOSS_SOUL.get(), 32),
                new ItemStack(ModItems.PLAIN_BOSS_SOUL.get(), 32),

                new ItemStack(ModItems.ICE_LOOT.get(), 6),
                new ItemStack(ModItems.ICE_LOOT.get(), 6),
                new ItemStack(ModItems.ICE_LOOT.get(), 12),

                new ItemStack(ModItems.DEVIL_ATTACK_SOUL.get(), 32),
                new ItemStack(ModItems.DEVIL_SWIFT_SOUL.get(), 32),
                new ItemStack(ModItems.DEVIL_MANA_SOUL.get(), 32),

                new ItemStack(ModItems.PURPLE_IRON_BUD_3.get(), 1),
                new ItemStack(ModItems.PURPLE_IRON_BUD_3.get(), 1),
                new ItemStack(ModItems.PURPLE_IRON_BUD_3.get(), 1)
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.T1Equip, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_ATTACK_RING_0.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_MANA_RING_0.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_DEFENCE_RING_0.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_HEALTH_RING_0.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ICE_BOSS_SOUL.get(), 64));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ICE_HEART.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ICE_COMPLETE_GEM.get(), 1));
                }});

                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.DEVIL_SWIFT_SOUL.get(), 32));
                    add(new ItemStack(ModItems.DEVIL_MANA_SOUL.get(), 32));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.DEVIL_ATTACK_SOUL.get(), 32));
                    add(new ItemStack(ModItems.DEVIL_MANA_SOUL.get(), 32));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.DEVIL_ATTACK_SOUL.get(), 32));
                    add(new ItemStack(ModItems.DEVIL_SWIFT_SOUL.get(), 32));
                }});

                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PURPLE_IRON_SWORD.get(), 1));
                }});

                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PURPLE_IRON_BOW.get(), 1));
                }});

                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PURPLE_IRON_SCEPTRE.get(), 1));
                }});
            }
        }
    }

    public static void NetherBow() {
        ItemStack[] itemStacks = {
                ModItems.QUARTZ_RUNE.get().getDefaultInstance(),
                ModItems.NETHER_BOW.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.NetherBow, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 25));
                    add(new ItemStack(ModItems.QUARTZ_SOUL.get(), 5));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NETHER_BOW.get(), 1));
                    add(new ItemStack(ModItems.QUARTZ_RUNE.get(), 1));
                    add(new ItemStack(ModItems.VOLCANO_BOW_3.get(), 1));
                }});
            }
        }
    }

    public static void NetherSwordModel() {
        ItemStack[] itemStacks = {
                ModItems.NETHER_SWORD_MODEL.get().getDefaultInstance(),
                ModItems.NETHER_SABRE_MODEL.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.NetherSwordModel, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0, 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 10));
                    add(new ItemStack(ModItems.RUBY.get(), 64));
                }});
            }
        }
    }

    public static void NetherWeapon() {
        ItemStack[] itemStacks = {
                ModItems.NETHER_RUNE.get().getDefaultInstance(),
                ModItems.MANA_SWORD.get().getDefaultInstance(),
                ModItems.QUARTZ_RUNE.get().getDefaultInstance(),
                ModItems.QUARTZ_SWORD.get().getDefaultInstance(),
                ModItems.NETHER_POWER.get().getDefaultInstance(),
                ModItems.QUARTZ_SABRE.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.NetherWeapon, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 10));
                    add(new ItemStack(ModItems.NETHER_SOUL.get(), 5));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NETHER_RUNE.get(), 2));
                    add(new ItemStack(ModItems.NETHER_SWORD_MODEL.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 25));
                    add(new ItemStack(ModItems.QUARTZ_SOUL.get(), 5));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 10));
                    add(new ItemStack(ModItems.NETHER_SOUL.get(), 5));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.QUARTZ_RUNE.get(), 10));
                    add(new ItemStack(ModItems.NETHER_RUNE.get(), 5));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.QUARTZ_RUNE.get(), 64));
                    add(new ItemStack(ModItems.NETHER_SABRE_MODEL.get(), 64));
                }});

            }
        }
    }

    public static void Ruby() {
        ItemStack[] itemStacks = {
                ModItems.NETHER_SOUL.get().getDefaultInstance(),
                ModItems.QUARTZ_SOUL.get().getDefaultInstance(),
                ModItems.NETHER_PEARL.get().getDefaultInstance(),
                ModItems.END_PEARL.get().getDefaultInstance(),
                WardenItems.WARDEN_MATRIX.get().getDefaultInstance()
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Ruby, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 10));
                    add(new ItemStack(ModItems.RUBY.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 25));
                    add(new ItemStack(ModItems.NETHER_QUARTZ.get(), 64));
                }});
                case 2, 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 4));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.CASTLE_PIECE.get(), 1));
                    add(new ItemStack(CitadelItems.CITADEL_PIECE.get(), 1));
                }});
            }
        }
    }

    public static void PlainForestRune() {
        ItemStack[] itemStacks = {
                ModItems.PLAIN_MANA.get().getDefaultInstance(),
                ModItems.PLAIN_MANA.get().getDefaultInstance(),
                ModItems.PLAIN_MANA.get().getDefaultInstance(),
                ModItems.PLAIN_MANA.get().getDefaultInstance(),
                ModItems.FOREST_MANA.get().getDefaultInstance(),
                ModItems.FOREST_MANA.get().getDefaultInstance(),
                ModItems.FOREST_MANA.get().getDefaultInstance(),
                ModItems.FOREST_MANA.get().getDefaultInstance(),
                GemItems.LIFE_MANA_GEM.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.PlainForestRune, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.PLAIN_HELMET.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.PLAIN_CHEST.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.PLAIN_LEGGINGS.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.PLAIN_BOOTS.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.FOREST_HELMET.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.FOREST_CHEST.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.FOREST_LEGGINGS.get(), 1));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.FOREST_BOOTS.get(), 1));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 10));
                    add(new ItemStack(GemItems.PLAIN_GEM.get(), 1));
                    add(new ItemStack(GemItems.FOREST_GEM.get(), 1));
                }});
            }
        }
    }

    public static void LakeVolcanoRune() {
        ItemStack[] itemStacks = {
                ModItems.LAKE_MANA.get().getDefaultInstance(),
                ModItems.LAKE_MANA.get().getDefaultInstance(),
                ModItems.LAKE_MANA.get().getDefaultInstance(),
                ModItems.LAKE_MANA.get().getDefaultInstance(),
                ModItems.VOLCANO_MANA.get().getDefaultInstance(),
                ModItems.VOLCANO_MANA.get().getDefaultInstance(),
                ModItems.VOLCANO_MANA.get().getDefaultInstance(),
                ModItems.VOLCANO_MANA.get().getDefaultInstance(),
                GemItems.OBSI_MANA_GEM.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.LakeVolcanoRune, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.LAKE_HELMET.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.LAKE_CHEST.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.LAKE_LEGGINGS.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.LAKE_BOOTS.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.VOLCANO_HELMET.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.VOLCANO_CHEST.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.VOLCANO_LEGGINGS.get(), 1));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.VOLCANO_BOOTS.get(), 1));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 10));
                    add(new ItemStack(GemItems.LAKE_GEM.get(), 1));
                    add(new ItemStack(GemItems.VOLCANO_GEM.get(), 1));
                }});
            }
        }
    }

    public static void ManaArmor() {
        ItemStack[] itemStacks = {
                ModItems.LIFE_MANA_HELMET.get().getDefaultInstance(),
                ModItems.LIFE_MANA_CHEST.get().getDefaultInstance(),
                ModItems.LIFE_MANA_LEGGINGS.get().getDefaultInstance(),
                ModItems.LIFE_MANA_BOOTS.get().getDefaultInstance(),
                ModItems.OBSI_MANA_HELMET.get().getDefaultInstance(),
                ModItems.OBSI_MANA_CHEST.get().getDefaultInstance(),
                ModItems.OBSI_MANA_LEGGINGS.get().getDefaultInstance(),
                ModItems.OBIS_MANA_BOOTS.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.ManaArmor, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0, 1, 2, 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FOREST_MANA.get(), 1));
                    add(new ItemStack(ModItems.PLAIN_MANA.get(), 1));
                }});
                case 4, 5, 6, 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VOLCANO_MANA.get(), 1));
                    add(new ItemStack(ModItems.LAKE_MANA.get(), 1));
                }});
            }
        }
    }

    public static void Brewing() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.BREWING_NOTE.get(), 1),
                new ItemStack(ModItems.SOLIDIFIER.get(), 4),
                new ItemStack(ModItems.PURIFIER.get(), 4),
                new ItemStack(ModItems.SOLIDIFIER.get(), 8),
                new ItemStack(ModItems.PURIFIER.get(), 8),

                new ItemStack(ModItems.SOLIDIFIER.get(), 16),
                new ItemStack(ModItems.PURIFIER.get(), 16),
                new ItemStack(ModItems.STABILIZER.get(), 8),
                new ItemStack(ModItems.CONCENTRATER.get(), 8),
                new ItemStack(ModItems.SPLASHER.get(), 8),

                new ItemStack(ModItems.WATER_BOTTLE.get(), 64)
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Brewing, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 3));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.ANDESITE, 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(Items.DIORITE, 64));
                }});
                case 3, 4, 7, 8, 9, 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 1));
                    add(new ItemStack(Items.ANDESITE, 64));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 1));
                    add(new ItemStack(Items.DIORITE, 64));
                }});
            }
        }
    }

    public static void spider() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.SPIDER_RUNE.get(), 1)
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Spider, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 8));
                    add(new ItemStack(ModItems.SPIDER_SOUL.get(), 64));
                }});
            }
        }
    }

    public static void Sakura() {
        ItemStack[] itemStacks = {
                new ItemStack(GemItems.SAKURA_GEM.get(), 1),
                new ItemStack(GemItems.SHIP_GEM.get(), 1),
                new ItemStack(ModItems.SAKURA_PETAL_POCKET.get(), 1),
                new ItemStack(ModItems.SHIP_PIECE_POCKET.get(), 1)
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Sakura, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.COMPLETE_GEM.get(), 10));
                    add(new ItemStack(ModItems.SAKURA_PETAL.get(), 256));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.COMPLETE_GEM.get(), 10));
                    add(new ItemStack(ModItems.SHIP_PIECE.get(), 256));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 5));
                    add(new ItemStack(ModItems.SAKURA_PETAL.get(), 64));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 5));
                    add(new ItemStack(ModItems.SHIP_PIECE.get(), 64));
                }});

            }
        }
    }

    public static void Field() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.FIELD_RUNE.get(), 1),
                new ItemStack(ModItems.FIELD_SWORD_0.get(), 1),
                new ItemStack(GemItems.FIELD_GEM.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Field, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FIELD_SOUL.get(), 64));
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 5));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FIELD_RUNE.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.COMPLETE_GEM.get(), 10));
                    add(new ItemStack(ModItems.FIELD_RUNE.get(), 10));
                }});
            }
        }
    }

    public static void GoldSmith() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.BOSS_2_ATTACK_RING_0.get(), 1),
                new ItemStack(ModItems.BOSS_2_ATTACK_RING_1.get(), 1),
                new ItemStack(ModItems.BOSS_2_ATTACK_RING_2.get(), 1),
                new ItemStack(ModItems.BOSS_2_ATTACK_RING_3.get(), 1),
                new ItemStack(ModItems.BOSS_2_MANA_ATTACK_RING_0.get(), 1),
                new ItemStack(ModItems.BOSS_2_MANA_ATTACK_RING_1.get(), 1),
                new ItemStack(ModItems.BOSS_2_MANA_ATTACK_RING_2.get(), 1),
                new ItemStack(ModItems.BOSS_2_MANA_ATTACK_RING_3.get(), 1),
                new ItemStack(ModItems.BOSS_2_DEFENCE_RING_0.get(), 1),
                new ItemStack(ModItems.BOSS_2_DEFENCE_RING_1.get(), 1),
                new ItemStack(ModItems.BOSS_2_DEFENCE_RING_2.get(), 1),
                new ItemStack(ModItems.BOSS_2_DEFENCE_RING_3.get(), 1),
                new ItemStack(ModItems.BOSS_2_HEALTH_RING_0.get(), 1),
                new ItemStack(ModItems.BOSS_2_HEALTH_RING_1.get(), 1),
                new ItemStack(ModItems.BOSS_2_HEALTH_RING_2.get(), 1),
                new ItemStack(ModItems.BOSS_2_HEALTH_RING_3.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.GoldSmith, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_ATTACK_RING_3.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BOSS_2_ATTACK_RING_0.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 2));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BOSS_2_ATTACK_RING_1.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 3));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BOSS_2_ATTACK_RING_2.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 4));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_MANA_RING_3.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BOSS_2_MANA_ATTACK_RING_0.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 2));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BOSS_2_MANA_ATTACK_RING_1.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 3));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BOSS_2_MANA_ATTACK_RING_2.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 4));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_DEFENCE_RING_3.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 1));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BOSS_2_DEFENCE_RING_0.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 2));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BOSS_2_DEFENCE_RING_1.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 3));
                }});
                case 11 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BOSS_2_DEFENCE_RING_2.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 4));
                }});
                case 12 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_HEALTH_RING_3.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 1));
                }});
                case 13 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BOSS_2_HEALTH_RING_0.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 2));
                }});
                case 14 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BOSS_2_HEALTH_RING_1.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 3));
                }});
                case 15 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BOSS_2_HEALTH_RING_2.get(), 1));
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 4));
                }});
            }
        }
    }

    public static void Blood() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 1),
                new ItemStack(ModItems.BLOOD_MANA_RUNE.get(), 2),
                new ItemStack(ModItems.BLOOD_MANA_CURIOS.get())
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Blood, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GEM_PIECE.get(), 4));
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 2));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.BLOOD_MANA_SOUL.get(), 128));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BLOOD_MANA_RUNE.get(), 12));
                    add(new ItemStack(ModItems.COMPLETE_GEM.get()));
                }});
            }
        }
    }

    public static void Earth() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 1),
                new ItemStack(ModItems.EARTH_MANA_RUNE.get(), 2),
                new ItemStack(ModItems.EARTH_MANA_CURIOS.get())
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Earth, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GEM_PIECE.get(), 4));
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 2));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.MANA_BALANCE_EMPTY.get(), 5));
                    add(new ItemStack(ModItems.EARTH_MANA_SOUL.get(), 128));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EARTH_MANA_RUNE.get(), 12));
                    add(new ItemStack(ModItems.COMPLETE_GEM.get()));
                }});
            }
        }
    }

    public static void ManaBook() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.WITHER_BOOK.get(), 1),
                new ItemStack(ModItems.EARTH_BOOK.get(), 1),
                new ItemStack(ModItems.EARTH_BOOK.get(), 1),
                new ItemStack(ModItems.EARTH_BOOK.get(), 1),
                new ItemStack(ModItems.ICE_BOOK.get(), 1),
                new ItemStack(ModItems.ICE_BOOK.get(), 1),
                new ItemStack(ModItems.ICE_BOOK.get(), 1),
                new ItemStack(ModItems.ICE_BOOK.get(), 1),
                new ItemStack(ModItems.GOLDEN_BOOK.get(), 1),
                new ItemStack(ModItems.GOLDEN_BOOK.get(), 1),
                new ItemStack(ModItems.GOLDEN_BOOK.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.ManaBook, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.WITHER_RUNE.get(), 5));
                    add(new ItemStack(ModItems.EVOKER_BOOK_3.get(), 1));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EARTH_MANA_RUNE.get(), 12));
                    add(new ItemStack(ModItems.WITHER_BOOK.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EARTH_MANA_RUNE.get(), 4));
                    add(new ItemStack(ModItems.ICE_BOOK.get(), 1));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.EARTH_MANA_RUNE.get(), 4));
                    add(new ItemStack(ModItems.GOLDEN_BOOK.get(), 1));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ICE_HEART.get(), 4));
                    add(new ItemStack(ModItems.EVOKER_BOOK_3.get(), 1));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ICE_HEART.get(), 4));
                    add(new ItemStack(ModItems.WITHER_BOOK.get(), 1));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ICE_HEART.get(), 4));
                    add(new ItemStack(ModItems.EARTH_BOOK.get(), 1));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.ICE_HEART.get(), 4));
                    add(new ItemStack(ModItems.GOLDEN_BOOK.get(), 1));
                }});
                case 8 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 4));
                    add(new ItemStack(ModItems.WITHER_BOOK.get(), 1));
                }});
                case 9 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 1));
                    add(new ItemStack(ModItems.EARTH_BOOK.get(), 1));
                }});
                case 10 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLDEN_SHEET.get(), 1));
                    add(new ItemStack(ModItems.ICE_BOOK.get(), 1));
                }});
            }
        }
    }

    public static void Slime() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.BIG_SLIME_BALL.get(), 1),
                new ItemStack(ModItems.SLIME_BOOTS.get(), 1),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Slime, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 10));
                    add(new ItemStack(ModItems.SLIME_BALL.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FOREST_BOOTS.get(), 1));
                    add(new ItemStack(ModItems.BIG_SLIME_BALL.get(), 12));
                }});
            }
        }
    }

    public static void taboo() {
        ItemStack intensifiedDevilBlood1 = new ItemStack(ModItems.INTENSIFIED_DEVIL_BLOOD.get());
        ItemStack intensifiedDevilBlood2 = new ItemStack(ModItems.INTENSIFIED_DEVIL_BLOOD.get());
        ItemStack intensifiedDevilBlood3 = new ItemStack(ModItems.INTENSIFIED_DEVIL_BLOOD.get());
        ItemStack purpleIronConstraintStone = new ItemStack(ModItems.PURPLE_IRON_CONSTRAINT_STONE.get());
        ItemStack constrainTaboo = new ItemStack(ModItems.CONSTRAINT_TABOO.get(), 1);
        ItemStack devilAttackSoul = new ItemStack(ModItems.DEVIL_ATTACK_SOUL.get());
        ItemStack devilSwiftSoul = new ItemStack(ModItems.DEVIL_SWIFT_SOUL.get());
        ItemStack devilManaSoul = new ItemStack(ModItems.DEVIL_MANA_SOUL.get());
        ItemStack[] itemStacks = {
                intensifiedDevilBlood1, intensifiedDevilBlood2, intensifiedDevilBlood3,
                purpleIronConstraintStone, constrainTaboo,
                devilAttackSoul, devilSwiftSoul, devilManaSoul
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Taboo, contentList);
        tradeRecipeMap.put(intensifiedDevilBlood1, new ArrayList<>() {{
            add(new ItemStack(ModItems.EARTH_MANA_SOUL.get(), 32));
            add(new ItemStack(ModItems.DEVIL_BLOOD.get(), 1));
        }});
        tradeRecipeMap.put(intensifiedDevilBlood2, new ArrayList<>() {{
            add(new ItemStack(ModItems.BLOOD_MANA_SOUL.get(), 32));
            add(new ItemStack(ModItems.DEVIL_BLOOD.get(), 1));
        }});
        tradeRecipeMap.put(intensifiedDevilBlood3, new ArrayList<>() {{
            add(new ItemStack(ModItems.VOLCANO_CORE.get(), 8));
            add(new ItemStack(ModItems.DEVIL_BLOOD.get(), 1));
        }});
        tradeRecipeMap.put(purpleIronConstraintStone, new ArrayList<>() {{
            add(new ItemStack(ModItems.PURPLE_IRON_INGOT.get(), 8));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 8));
        }});
        tradeRecipeMap.put(constrainTaboo, new ArrayList<>() {{
            add(new ItemStack(ModItems.TABOO_PIECE.get(), 64));
            add(new ItemStack(ModItems.PURPLE_IRON_CONSTRAINT_STONE.get(), 1));
        }});
        tradeRecipeMap.put(devilAttackSoul, List.of(
                new ItemStack(ModItems.DEVIL_SWIFT_SOUL.get(), 2),
                new ItemStack(ModItems.DEVIL_MANA_SOUL.get(), 2)
        ));
        tradeRecipeMap.put(devilSwiftSoul, List.of(
                new ItemStack(ModItems.DEVIL_ATTACK_SOUL.get(), 2),
                new ItemStack(ModItems.DEVIL_MANA_SOUL.get(), 2)
        ));
        tradeRecipeMap.put(devilManaSoul, List.of(
                new ItemStack(ModItems.DEVIL_ATTACK_SOUL.get(), 2),
                new ItemStack(ModItems.DEVIL_SWIFT_SOUL.get(), 2)
        ));
    }

    public static void Parkour() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.PARKOUR_GLOVES.get(), 1),
                new ItemStack(ModItems.KILL_PAPER_LOOT.get(), 1),
                new ItemStack(ModItems.PS_BOTTLE_2.get(), 1),
                new ItemStack(ModItems.COMPLETE_GEM.get(), 1),
                new ItemStack(ModItems.REPUTATION_MEDAL.get(), 1)
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.Parkour, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PARKOUR_MEDAL.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PARKOUR_MEDAL.get(), 4));
                }});
                case 2, 3, 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PARKOUR_MEDAL.get(), 2));
                }});
            }
        }
    }

    public static void castle() {
        ItemStack beaconRune = new ItemStack(ModItems.BEACON_RUNE.get(), 1);
        ItemStack blazeRune = new ItemStack(ModItems.BLAZE_RUNE.get(), 1);
        ItemStack treeRune = new ItemStack(ModItems.TREE_RUNE.get(), 1);
        ItemStack beaconBow = new ItemStack(ModItems.BEACON_BOW.get(), 1);
        ItemStack blazeSword = new ItemStack(ModItems.BLAZE_SWORD.get(), 1);
        ItemStack treeSceptre = new ItemStack(ModItems.TREE_SCEPTRE.get(), 1);
        ItemStack castleSwordPiece = new ItemStack(ModItems.CASTLE_SWORD_PIECE.get());
        ItemStack castleBowPiece = new ItemStack(ModItems.CASTLE_BOW_PIECE.get());
        ItemStack castleSceptrePiece = new ItemStack(ModItems.CASTLE_SCEPTRE_PIECE.get());
        ItemStack castleWeaponGem = new ItemStack(GemItems.CASTLE_WEAPON_GEM.get());
        ItemStack castleArmorGem = new ItemStack(GemItems.CASTLE_ARMOR_GEM.get());
        ItemStack[] itemStacks = {
                blazeRune, beaconRune, treeRune,
                blazeSword, beaconBow, treeSceptre,
                castleSwordPiece, castleBowPiece, castleSceptrePiece,
                castleWeaponGem, castleArmorGem
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.CastleCommon, contentList);
        tradeRecipeMap.put(blazeRune, List.of(
                new ItemStack(ModItems.COMPLETE_GEM.get(), 1),
                new ItemStack(ModItems.REPUTATION_MEDAL.get(), 1),
                new ItemStack(ModItems.BLAZE_SOUL.get(), 64)
        ));
        tradeRecipeMap.put(beaconRune, List.of(
                new ItemStack(ModItems.COMPLETE_GEM.get(), 1),
                new ItemStack(ModItems.REPUTATION_MEDAL.get(), 1),
                new ItemStack(ModItems.BEACON_SOUL.get(), 64)
        ));
        tradeRecipeMap.put(treeRune, List.of(
                new ItemStack(ModItems.COMPLETE_GEM.get(), 1),
                new ItemStack(ModItems.REPUTATION_MEDAL.get(), 1),
                new ItemStack(ModItems.TREE_SOUL.get(), 64)
        ));
        tradeRecipeMap.put(blazeSword, List.of(
                new ItemStack(ModItems.BLAZE_RUNE.get(), 8),
                new ItemStack(ModItems.VOLCANO_SWORD_3.get(), 1)
        ));
        tradeRecipeMap.put(beaconBow, List.of(
                new ItemStack(ModItems.BEACON_RUNE.get(), 8),
                new ItemStack(ModItems.NETHER_BOW.get(), 1)
        ));
        tradeRecipeMap.put(treeSceptre, List.of(
                new ItemStack(ModItems.TREE_RUNE.get(), 8),
                new ItemStack(ModItems.LIFE_SCEPTRE_X.get(), 1)
        ));
        tradeRecipeMap.put(castleSwordPiece, List.of(
                new ItemStack(ModItems.CASTLE_BOW_PIECE.get(), 2),
                new ItemStack(ModItems.CASTLE_SCEPTRE_PIECE.get(), 2)
        ));
        tradeRecipeMap.put(castleBowPiece, List.of(
                new ItemStack(ModItems.CASTLE_SWORD_PIECE.get(), 2),
                new ItemStack(ModItems.CASTLE_SCEPTRE_PIECE.get(), 2)
        ));
        tradeRecipeMap.put(castleSceptrePiece, List.of(
                new ItemStack(ModItems.CASTLE_SWORD_PIECE.get(), 2),
                new ItemStack(ModItems.CASTLE_BOW_PIECE.get(), 2)
        ));
        tradeRecipeMap.put(castleWeaponGem, List.of(
                new ItemStack(ModItems.COMPLETE_GEM.get(), 10),
                new ItemStack(ModItems.CASTLE_CURIOS_POWDER.get(), 16)
        ));
        tradeRecipeMap.put(castleArmorGem, List.of(
                new ItemStack(ModItems.COMPLETE_GEM.get(), 10),
                new ItemStack(ModItems.CASTLE_CURIOS_POWDER.get(), 16)
        ));
    }

    public static void EndPower() {

        ItemStack[] itemStacks = {
                new ItemStack(ModItems.END_CRYSTAL.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.EndPower, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GEM_PIECE.get(), 8));
                    add(new ItemStack(ModItems.SHULKER_SOUL.get(), 64));
                    add(new ItemStack(ModItems.ENDER_MITE_SOUL.get(), 64));
                }});
            }
        }
    }

    public static void SkyGemStore() {

        ItemStack[] itemStacks = {
                new ItemStack(ModItems.PLAIN_RING.get(), 1),
                new ItemStack(ModItems.FOREST_RING.get(), 1),
                new ItemStack(ModItems.VOLCANO_RING.get(), 1),
                new ItemStack(ModItems.LAKE_RING.get(), 1),
                new ItemStack(ModItems.RUBY_NECKLACE.get(), 1),
                new ItemStack(ModItems.SAPPHIRE_NECKLACE.get(), 1),
                new ItemStack(ModItems.FANCY_SAPPHIRE_NECKLACE.get(), 1),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.SkyGemStore, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GEM_PIECE.get(), 64));
                    add(new ItemStack(ModItems.PLAIN_RUNE.get(), 4));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GEM_PIECE.get(), 64));
                    add(new ItemStack(ModItems.FOREST_RUNE.get(), 4));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GEM_PIECE.get(), 64));
                    add(new ItemStack(ModItems.VOLCANO_RUNE.get(), 4));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GEM_PIECE.get(), 64));
                    add(new ItemStack(ModItems.LAKE_RUNE.get(), 4));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VOLCANO_CORE.get(), 64));
                    add(new ItemStack(ModItems.GEM_PIECE.get(), 128));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LAKE_CORE.get(), 64));
                    add(new ItemStack(ModItems.GEM_PIECE.get(), 128));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LAKE_RUNE.get(), 64));
                    add(new ItemStack(ModItems.SAPPHIRE_NECKLACE_3.get(), 1));
                    add(new ItemStack(ModItems.VOLCANO_RUNE.get(), 64));
                    add(new ItemStack(ModItems.RUBY_NECKLACE_3.get(), 1));
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
                    add(new ItemStack(ModItems.SILVER_COIN.get(), 8));
                }});
                case 1, 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SILVER_COIN.get(), 24));
                }});
                case 3, 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SILVER_COIN.get(), 32));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SILVER_COIN.get(), 48));
                }});
            }
        }
    }

    public static void EndRecall() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.KAZE_RECALL_BOOK.get()),
                new ItemStack(ModItems.BLACK_FOREST_RECALL_BOOK.get()),
                new ItemStack(ModItems.SEA_RECALL_BOOK.get()),
                new ItemStack(ModItems.LIGHTNING_RECALL_BOOK.get()),
                new ItemStack(ModItems.NETHER_RECALL_BOOK_1.get()),
                new ItemStack(ModItems.SNOW_RECALL_BOOK.get()),
                new ItemStack(ModItems.FOREST_RECALL_BOOK.get()),
                new ItemStack(ModItems.VOLCANO_RECALL_BOOK.get()),
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.EndRecall, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.KAZE_RUNE.get(), 2));
                    add(new ItemStack(ModItems.RECALL_PIECE.get(), 32));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.HUSK_RUNE.get(), 2));
                    add(new ItemStack(ModItems.RECALL_PIECE.get(), 32));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SEA_RUNE.get(), 2));
                    add(new ItemStack(ModItems.RECALL_PIECE.get(), 32));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.LIGHTNING_RUNE.get(), 2));
                    add(new ItemStack(ModItems.RECALL_PIECE.get(), 32));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.NETHER_SOUL.get(), 2));
                    add(new ItemStack(ModItems.RECALL_PIECE.get(), 32));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SNOW_RUNE.get(), 2));
                    add(new ItemStack(ModItems.RECALL_PIECE.get(), 32));
                }});
                case 6 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.FOREST_RUNE.get(), 2));
                    add(new ItemStack(ModItems.RECALL_PIECE.get(), 32));
                }});
                case 7 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.VOLCANO_RUNE.get(), 2));
                    add(new ItemStack(ModItems.RECALL_PIECE.get(), 32));
                }});
            }
        }
    }

    public static void RoseGoldStore() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 1),

        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.RoseGoldStore, contentList);

        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 64));
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
                new ItemStack(ModItems.BACKPACK_TICKETS.get(), 1),
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
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 64));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.BACKPACK_TICKETS.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 12));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 16));
                }});
                case 4 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 20));
                }});
                case 5 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 28));
                }});
            }
        }
    }

    public static void Pearl() {
        ItemStack[] itemStacks = {
                new ItemStack(ModItems.PEARL_1.get(), 1),
                new ItemStack(ModItems.PEARL_2.get(), 1),
                new ItemStack(ModItems.PEARL_3.get(), 1),
                new ItemStack(ModItems.PEARL_4.get(), 1),
                new ItemStack(ModItems.PEARL_5.get(), 1),
                new ItemStack(ModItems.PEARL_6.get(), 1),
                new ItemStack(ModItems.WORLD_FORGE_STONE.get(), 1)
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
                    add(new ItemStack(ModItems.PEARL_1.get(), 1));
                    add(new ItemStack(ModItems.PEARL_2.get(), 1));
                    add(new ItemStack(ModItems.PEARL_3.get(), 1));
                    add(new ItemStack(ModItems.PEARL_4.get(), 1));
                    add(new ItemStack(ModItems.PEARL_5.get(), 1));
                    add(new ItemStack(ModItems.PEARL_6.get(), 1));
                }});
            }
        }
    }

    public static void plainLeather() {
        ItemStack[] itemStacks = {
                Items.LEATHER.getDefaultInstance(),
                ModItems.PLAIN_RUNE.get().getDefaultInstance(),
                new ItemStack(ModItems.SILVER_COIN.get(), 4),
                new ItemStack(ModItems.PLAIN_MANA_BOOK.get())
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("平原村皮革制作师", "plainLeather", CustomStyle.styleOfPlain, VillagerType.PLAINS,
                VillagerProfession.LEATHERWORKER, contentList);
        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.COPPER_COIN.get(), 8));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_SOUL.get(), 64));
                    add(new ItemStack(ModItems.COPPER_COIN.get(), 32));
                    add(new ItemStack(ModItems.GEM_PIECE.get(), 1));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_SOUL.get(), 64));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_RUNE.get(), 4));
                    add(new ItemStack(ModItems.GEM_PIECE.get(), 4));
                    add(new ItemStack(Items.LEATHER, 16));
                }});
            }
        }
    }

    public static void plainForgeHammer() {
        ItemStack[] itemStacks = {
                ModItems.WOOD_HAMMER.get().getDefaultInstance(),
                ModItems.STONE_HAMMER.get().getDefaultInstance(),
                ModItems.IRON_HAMMER.get().getDefaultInstance(),
                GemItems.PLAIN_GEM.get().getDefaultInstance(),
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("平原村锻造师", "plainForgeHammer", CustomStyle.styleOfPlain, VillagerType.PLAINS,
                VillagerProfession.ARMORER, contentList);
        for (int i = 0; i < itemStacks.length; i++) {
            switch (i) {
                case 0 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.COPPER_COIN.get(), 32));
                }});
                case 1 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.COPPER_COIN.get(), 64));
                }});
                case 2 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.SILVER_COIN.get(), 16));
                }});
                case 3 -> tradeRecipeMap.put(itemStacks[i], new ArrayList<>() {{
                    add(new ItemStack(ModItems.PLAIN_RUNE.get(), 10));
                    add(new ItemStack(ModItems.COMPLETE_GEM.get(), 5));
                }});
            }
        }
    }

    public static void forestForgeHammer() {
        ItemStack woodHammer = new ItemStack(ModItems.WOOD_HAMMER.get());
        ItemStack stoneHammer = new ItemStack(ModItems.STONE_HAMMER.get());
        ItemStack ironHammer = new ItemStack(ModItems.IRON_HAMMER.get());
        ItemStack copperHammer = new ItemStack(ModItems.COPPER_HAMMER.get());
        ItemStack forestGem = new ItemStack(GemItems.FOREST_GEM.get());
        ItemStack[] itemStacks = {
                woodHammer, stoneHammer, ironHammer, copperHammer, forestGem
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("雨林村森林锻造师", "forestForgeHammer", CustomStyle.styleOfForest, VillagerType.JUNGLE,
                VillagerProfession.ARMORER, contentList);
        tradeRecipeMap.put(woodHammer,
                List.of(new ItemStack(ModItems.SILVER_COIN.get(), 4)));
        tradeRecipeMap.put(stoneHammer,
                List.of(new ItemStack(ModItems.SILVER_COIN.get(), 8)));
        tradeRecipeMap.put(ironHammer,
                List.of(new ItemStack(ModItems.SILVER_COIN.get(), 16)));
        tradeRecipeMap.put(copperHammer,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 4)));
        tradeRecipeMap.put(forestGem,
                List.of(new ItemStack(ModItems.FOREST_RUNE.get(), 10),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 5)));
    }

    public static void forestTool() {
        ItemStack oakLog4 = new ItemStack(Items.OAK_LOG, 4);
        ItemStack oakLog16 = new ItemStack(Items.OAK_LOG, 16);
        ItemStack leather = new ItemStack(Items.LEATHER);
        ItemStack forestRune = new ItemStack(ModItems.FOREST_RUNE.get());
        ItemStack forestManaBook = new ItemStack(ModItems.FOREST_MANA_BOOK.get());
        ItemStack originKnifeForest = new ItemStack(ModItems.ORIGIN_KNIFE_FOREST.get());
        ItemStack silverCoin = new ItemStack(ModItems.SILVER_COIN.get(), 8);
        ItemStack wolfLeatherPocket = new ItemStack(ModItems.WOLF_LEATHER_POCKET.get());
        ItemStack[] itemStacks = {
                leather, forestRune, forestManaBook,
                originKnifeForest, silverCoin, oakLog4, oakLog16,
                wolfLeatherPocket
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("雨林村森林工具制作师", "forestTool", CustomStyle.styleOfForest, VillagerType.JUNGLE,
                VillagerProfession.TOOLSMITH, contentList);
        tradeRecipeMap.put(oakLog4,
                List.of(new ItemStack(ModItems.FOREST_SOUL.get(), 1)));
        tradeRecipeMap.put(oakLog16,
                List.of(new ItemStack(ModItems.FOREST_SOUL.get(), 4)));
        tradeRecipeMap.put(leather,
                List.of(new ItemStack(ModItems.SILVER_COIN.get(), 1)));
        tradeRecipeMap.put(forestRune,
                List.of(new ItemStack(ModItems.FOREST_SOUL.get(), 64),
                        new ItemStack(ModItems.SILVER_COIN.get(), 8),
                        new ItemStack(ModItems.GEM_PIECE.get(), 1)));
        tradeRecipeMap.put(forestManaBook,
                List.of(new ItemStack(ModItems.PLAIN_MANA_BOOK.get(), 1),
                        new ItemStack(ModItems.FOREST_RUNE.get(), 4),
                        new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 14),
                        new ItemStack(Items.LEATHER, 12)));
        tradeRecipeMap.put(originKnifeForest,
                List.of(new ItemStack(ModItems.ORIGIN_KNIFE_PLAIN.get(), 1),
                        new ItemStack(ModItems.FOREST_RUNE.get(), 4),
                        new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 14),
                        new ItemStack(ModItems.GOLD_COIN.get(), 5)));
        tradeRecipeMap.put(silverCoin,
                List.of(new ItemStack(ModItems.FOREST_SOUL.get(), 64)));
        tradeRecipeMap.put(wolfLeatherPocket,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 5),
                        new ItemStack(ModItems.WOLF_LEATHER.get(), 64)));
    }

    public static void riverTool() {
        ItemStack waterBottle = new ItemStack(ModItems.WATER_BOTTLE.get(), 64);
        ItemStack lakeRune = new ItemStack(ModItems.LAKE_RUNE.get());
        ItemStack lakeManaBook = new ItemStack(ModItems.LAKE_MANA_BOOK.get());
        ItemStack originKnifeLake = new ItemStack(ModItems.ORIGIN_KNIFE_LAKE.get());
        ItemStack lakeGem = new ItemStack(GemItems.LAKE_GEM.get());
        ItemStack silverCoin = new ItemStack(ModItems.SILVER_COIN.get(), 8);
        ItemStack[] itemStacks = {
                waterBottle, lakeRune, lakeManaBook, originKnifeLake, lakeGem, silverCoin
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("德朗斯蒂克水文学者", "riverTool", CustomStyle.styleOfLake, VillagerType.SWAMP,
                VillagerProfession.LIBRARIAN, contentList);
        tradeRecipeMap.put(waterBottle,
                List.of(new ItemStack(ModItems.LAKE_SOUL.get(), 8)));
        tradeRecipeMap.put(lakeRune,
                List.of(new ItemStack(ModItems.LAKE_SOUL.get(), 64),
                        new ItemStack(ModItems.SILVER_COIN.get(), 8),
                        new ItemStack(ModItems.GEM_PIECE.get(), 2)));
        tradeRecipeMap.put(lakeManaBook,
                List.of(new ItemStack(ModItems.FOREST_MANA_BOOK.get()),
                        new ItemStack(ModItems.LAKE_RUNE.get(), 4),
                        new ItemStack(ModItems.WATER_ELEMENT_PIECE_0.get(), 14),
                        new ItemStack(Items.LEATHER, 12)));
        tradeRecipeMap.put(originKnifeLake,
                List.of(new ItemStack(ModItems.ORIGIN_KNIFE_FOREST.get()),
                        new ItemStack(ModItems.LAKE_RUNE.get(), 4),
                        new ItemStack(ModItems.WATER_ELEMENT_PIECE_0.get(), 14),
                        new ItemStack(ModItems.GOLD_COIN.get(), 8)));
        tradeRecipeMap.put(lakeGem,
                List.of(new ItemStack(ModItems.LAKE_RUNE.get(), 10),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 10)));
        tradeRecipeMap.put(silverCoin,
                List.of(new ItemStack(ModItems.LAKE_SOUL.get(), 64)));
    }

    public static void mineTool() {
        ItemStack mineRune = new ItemStack(ModItems.MINE_RUNE.get());
        ItemStack mineRune1 = new ItemStack(ModItems.MINE_RUNE.get());
        ItemStack mineShield = new ItemStack(ModItems.MINE_SHIELD.get());
        ItemStack originKnifeMine = new ItemStack(ModItems.ORIGIN_KNIFE_MINE.get());
        ItemStack mineManaNote = new ItemStack(ModItems.MINE_MANA_NOTE.get());
        ItemStack mineGem = new ItemStack(GemItems.MINE_GEM.get());
        ItemStack silverCoin = new ItemStack(ModItems.SILVER_COIN.get(), 10);
        ItemStack[] itemStacks = {
                mineRune, mineRune1, mineShield, originKnifeMine, mineManaNote, mineGem, silverCoin
        };

        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("雨林村铁匠", "mineTool", CustomStyle.styleOfMine, VillagerType.SWAMP,
                VillagerProfession.WEAPONSMITH, contentList);
        tradeRecipeMap.put(mineRune,
                List.of(new ItemStack(ModItems.MINE_SOUL.get(), 64),
                        new ItemStack(ModItems.SILVER_COIN.get(), 8),
                        new ItemStack(ModItems.GEM_PIECE.get(), 2)));
        tradeRecipeMap.put(mineRune1,
                List.of(new ItemStack(ModItems.MINE_SOUL_1.get(), 8),
                        new ItemStack(ModItems.SILVER_COIN.get(), 8),
                        new ItemStack(ModItems.GEM_PIECE.get(), 2)));
        tradeRecipeMap.put(mineShield,
                List.of(new ItemStack(ModItems.MINE_RUNE.get(), 8),
                        new ItemStack(ModItems.STONE_ELEMENT_PIECE_0.get(), 14),
                        new ItemStack(Items.IRON_INGOT, 4)));
        tradeRecipeMap.put(originKnifeMine,
                List.of(new ItemStack(ModItems.ORIGIN_KNIFE_LAKE.get()),
                        new ItemStack(ModItems.MINE_RUNE.get(), 8),
                        new ItemStack(ModItems.STONE_ELEMENT_PIECE_0.get(), 14),
                        new ItemStack(ModItems.GOLD_COIN.get(), 10)));
        tradeRecipeMap.put(mineManaNote,
                List.of(new ItemStack(ModItems.LAKE_MANA_BOOK.get(), 1),
                        new ItemStack(ModItems.MINE_RUNE.get(), 4),
                        new ItemStack(ModItems.STONE_ELEMENT_PIECE_0.get(), 14),
                        new ItemStack(Items.LEATHER, 12)));
        tradeRecipeMap.put(mineGem,
                List.of(new ItemStack(ModItems.MINE_RUNE.get(), 10),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 10)));
        tradeRecipeMap.put(silverCoin,
                List.of(new ItemStack(ModItems.MINE_SOUL.get(), 64)));
    }

    public static void volcanoForgeHammer() {
        ItemStack ironHammer = new ItemStack(ModItems.IRON_HAMMER.get());
        ItemStack copperHammer = new ItemStack(ModItems.COPPER_HAMMER.get());
        ItemStack goldHammer = new ItemStack(ModItems.GOLD_HAMMER.get());
        ItemStack diamondHammer = new ItemStack(ModItems.DIAMOND_HAMMER.get());
        ItemStack volcanoGem = new ItemStack(GemItems.VOLCANO_GEM.get());
        ItemStack[] itemStacks = {
                ironHammer, copperHammer, goldHammer, diamondHammer, volcanoGem
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("火山村锻造师", "volcanoForgeHammer", CustomStyle.styleOfVolcano, VillagerType.DESERT,
                VillagerProfession.ARMORER, contentList);
        tradeRecipeMap.put(ironHammer,
                List.of(new ItemStack(ModItems.SILVER_COIN.get(), 16)));
        tradeRecipeMap.put(copperHammer,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 4)));
        tradeRecipeMap.put(goldHammer,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 32)));
        tradeRecipeMap.put(diamondHammer,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 96)));
        tradeRecipeMap.put(volcanoGem,
                List.of(new ItemStack(ModItems.VOLCANO_RUNE.get(), 10),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 10)));
    }

    public static void volcanoTool() {
        ItemStack volcanoRune = new ItemStack(ModItems.VOLCANO_RUNE.get());
        ItemStack volcanoManaBook = new ItemStack(ModItems.VOLCANO_MANA_BOOK.get());
        ItemStack originKnifeVolcano = new ItemStack(ModItems.ORIGIN_KNIFE_VOLCANO.get());
        ItemStack silverCoin = new ItemStack(ModItems.SILVER_COIN.get(), 12);
        ItemStack coal = new ItemStack(Items.COAL);
        ItemStack[] itemStacks = {
                volcanoRune, volcanoManaBook, originKnifeVolcano, coal, silverCoin
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("火山村工具制作师", "volcanoTool", CustomStyle.styleOfVolcano, VillagerType.DESERT,
                VillagerProfession.TOOLSMITH, contentList);
        tradeRecipeMap.put(volcanoRune,
                List.of(new ItemStack(ModItems.VOLCANO_SOUL.get(), 64),
                        new ItemStack(ModItems.GOLD_COIN.get(), 2),
                        new ItemStack(ModItems.GEM_PIECE.get(), 1)));
        tradeRecipeMap.put(volcanoManaBook,
                List.of(new ItemStack(ModItems.MINE_MANA_NOTE.get(), 1),
                        new ItemStack(ModItems.VOLCANO_RUNE.get(), 4),
                        new ItemStack(ModItems.FIRE_ELEMENT_PIECE_0.get(), 14),
                        new ItemStack(Items.LEATHER, 12)));
        tradeRecipeMap.put(originKnifeVolcano,
                List.of(new ItemStack(ModItems.ORIGIN_KNIFE_MINE.get(), 1),
                        new ItemStack(ModItems.VOLCANO_RUNE.get(), 4),
                        new ItemStack(ModItems.FIRE_ELEMENT_PIECE_0.get(), 14),
                        new ItemStack(ModItems.GOLD_COIN.get(), 16)));
        tradeRecipeMap.put(coal,
                List.of(new ItemStack(ModItems.VOLCANO_SOUL.get(), 8)));
        tradeRecipeMap.put(silverCoin,
                List.of(new ItemStack(ModItems.VOLCANO_SOUL.get(), 64)));
    }

    public static void snowForgeHammer() {
        ItemStack ironHammer = new ItemStack(ModItems.IRON_HAMMER.get());
        ItemStack copperHammer = new ItemStack(ModItems.COPPER_HAMMER.get());
        ItemStack goldHammer = new ItemStack(ModItems.GOLD_HAMMER.get());
        ItemStack diamondHammer = new ItemStack(ModItems.DIAMOND_HAMMER.get());
        ItemStack snowGem = new ItemStack(GemItems.SNOW_GEM.get());
        ItemStack[] itemStacks = {
                ironHammer, copperHammer, goldHammer, diamondHammer, snowGem
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("北洋村锻造师", "snowForgeHammer", CustomStyle.styleOfSnow, VillagerType.SNOW,
                VillagerProfession.ARMORER, contentList);
        tradeRecipeMap.put(ironHammer,
                List.of(new ItemStack(ModItems.SILVER_COIN.get(), 16)));
        tradeRecipeMap.put(copperHammer,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 4)));
        tradeRecipeMap.put(goldHammer,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 32)));
        tradeRecipeMap.put(diamondHammer,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 96)));
        tradeRecipeMap.put(snowGem,
                List.of(new ItemStack(ModItems.SNOW_RUNE.get(), 10),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 10)));
    }

    public static void snowTool() {
        ItemStack snowRune = new ItemStack(ModItems.SNOW_RUNE.get());
        ItemStack silverCoin = new ItemStack(ModItems.SILVER_COIN.get(), 16);
        ItemStack[] itemStacks = {
                snowRune, silverCoin
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("北洋村工具制作师", "snowTool", CustomStyle.styleOfSnow, VillagerType.DESERT,
                VillagerProfession.TOOLSMITH, contentList);
        tradeRecipeMap.put(snowRune,
                List.of(new ItemStack(ModItems.SNOW_SOUL.get(), 64),
                        new ItemStack(ModItems.GOLD_COIN.get(), 3),
                        new ItemStack(ModItems.GEM_PIECE.get(), 1)));
        tradeRecipeMap.put(silverCoin,
                List.of(new ItemStack(ModItems.SNOW_SOUL.get(), 64)));
    }

    public static void skyTool() {
        ItemStack originKnifeSky = new ItemStack(ModItems.ORIGIN_KNIFE_SKY.get());
        ItemStack skyRune = new ItemStack(ModItems.SKY_RUNE.get());
        ItemStack skyGem = new ItemStack(GemItems.SKY_GEM.get());
        ItemStack silverCoin = new ItemStack(ModItems.SILVER_COIN.get(), 16);
        ItemStack[] itemStacks = {
                originKnifeSky, skyRune, skyGem, silverCoin
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("天空城锻造师", "skyTool", CustomStyle.styleOfSky, VillagerType.PLAINS,
                VillagerProfession.WEAPONSMITH, contentList);

        tradeRecipeMap.put(originKnifeSky,
                List.of(new ItemStack(ModItems.ORIGIN_KNIFE_VOLCANO.get(), 1),
                        new ItemStack(ModItems.SKY_RUNE.get(), 5),
                        new ItemStack(ModItems.GOLD_COIN.get(), 20)));
        tradeRecipeMap.put(skyRune,
                List.of(new ItemStack(ModItems.SKY_SOUL.get(), 64),
                        new ItemStack(ModItems.GOLD_COIN.get(), 4),
                        new ItemStack(ModItems.GEM_PIECE.get(), 1)));
        tradeRecipeMap.put(skyGem,
                List.of(new ItemStack(ModItems.SKY_RUNE.get(), 10),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 10)));
        tradeRecipeMap.put(silverCoin,
                List.of(new ItemStack(ModItems.SKY_SOUL.get(), 64)));
    }

    public static void mineCharm() {
        ItemStack stonePickaxe = new ItemStack(PickaxeItems.STONE_PICKAXE_0.get());
        ItemStack potion = new ItemStack(Items.POTION);
        potion.getOrCreateTag().putString("Potion", "minecraft:long_night_vision");

        ItemStack minePiece1 = new ItemStack(SpurItems.MINE_PIECE_1.get());
        ItemStack mineCharm0 = new ItemStack(SpurItems.MINE_CHARM_0.get());
        ItemStack mineCharm1 = new ItemStack(SpurItems.MINE_CHARM_1.get());
        ItemStack mineCharm2 = new ItemStack(SpurItems.MINE_CHARM_2.get());
        ItemStack mineCharm3 = new ItemStack(SpurItems.MINE_CHARM_3.get());
        ItemStack mineCharm4 = new ItemStack(SpurItems.MINE_CHARM_4.get());
        ItemStack mineCharm5 = new ItemStack(SpurItems.MINE_CHARM_5.get());
        ItemStack mineCharm6 = new ItemStack(SpurItems.MINE_CHARM_6.get());
        ItemStack oreRune = new ItemStack(ModItems.ORE_RUNE.get(), 1);
        ItemStack[] itemStacks = {
                stonePickaxe, minePiece1, potion, oreRune,
                mineCharm0, mineCharm1, mineCharm2, mineCharm3, mineCharm4, mineCharm5, mineCharm6,

        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("采矿大师", "mineCharm", CustomStyle.styleOfMine, VillagerType.PLAINS,
                VillagerProfession.WEAPONSMITH, contentList);

        tradeRecipeMap.put(stonePickaxe, List.of(new ItemStack(ModItems.GOLD_COIN.get(), 1)));
        tradeRecipeMap.put(potion, List.of(new ItemStack(ModItems.SILVER_COIN.get(), 1)));
        tradeRecipeMap.put(oreRune, List.of(new ItemStack(Items.EMERALD, 2), new ItemStack(Items.DIAMOND, 2),
                new ItemStack(Items.REDSTONE, 8), new ItemStack(Items.LAPIS_LAZULI, 8)));

        tradeRecipeMap.put(minePiece1, List.of(new ItemStack(SpurItems.MINE_PIECE.get(), 16)));

        tradeRecipeMap.put(mineCharm0, List.of(new ItemStack(SpurItems.MINE_PIECE_1.get(), 1)));

        tradeRecipeMap.put(mineCharm1, List.of(new ItemStack(SpurItems.MINE_PIECE_1.get(), 2),
                new ItemStack(SpurItems.MINE_CHARM_0.get())));

        tradeRecipeMap.put(mineCharm2, List.of(new ItemStack(SpurItems.MINE_PIECE_1.get(), 4),
                new ItemStack(SpurItems.MINE_CHARM_1.get())));

        tradeRecipeMap.put(mineCharm3, List.of(new ItemStack(SpurItems.MINE_PIECE_1.get(), 8),
                new ItemStack(SpurItems.MINE_CHARM_2.get())));

        tradeRecipeMap.put(mineCharm4, List.of(new ItemStack(SpurItems.MINE_PIECE_1.get(), 16),
                new ItemStack(SpurItems.MINE_CHARM_3.get())));

        tradeRecipeMap.put(mineCharm5, List.of(new ItemStack(SpurItems.MINE_PIECE_1.get(), 32),
                new ItemStack(SpurItems.MINE_CHARM_4.get())));

        tradeRecipeMap.put(mineCharm6, List.of(new ItemStack(SpurItems.MINE_PIECE_1.get(), 64),
                new ItemStack(SpurItems.MINE_CHARM_5.get())));
    }

    public static void seaCharm() {
        ItemStack fishingRod = new ItemStack(Items.FISHING_ROD);
        ItemStack seaPiece1 = new ItemStack(SpurItems.SEA_PIECE_1.get());
        ItemStack seaCharm0 = new ItemStack(SpurItems.SEA_CHARM_0.get());
        ItemStack seaCharm1 = new ItemStack(SpurItems.SEA_CHARM_1.get());
        ItemStack seaCharm2 = new ItemStack(SpurItems.SEA_CHARM_2.get());
        ItemStack seaCharm3 = new ItemStack(SpurItems.SEA_CHARM_3.get());
        ItemStack seaCharm4 = new ItemStack(SpurItems.SEA_CHARM_4.get());
        ItemStack seaCharm5 = new ItemStack(SpurItems.SEA_CHARM_5.get());
        ItemStack seaCharm6 = new ItemStack(SpurItems.SEA_CHARM_6.get());
        ItemStack[] itemStacks = {
                fishingRod, seaPiece1,
                seaCharm0, seaCharm1, seaCharm2, seaCharm3, seaCharm4, seaCharm5, seaCharm6
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("钓鱼大师", "seaCharm", CustomStyle.styleOfSea, VillagerType.SWAMP,
                VillagerProfession.FISHERMAN, contentList);

        tradeRecipeMap.put(fishingRod, List.of(new ItemStack(ModItems.GOLD_COIN.get(), 1)));

        tradeRecipeMap.put(seaPiece1, List.of(new ItemStack(SpurItems.SEA_PIECE.get(), 64)));

        tradeRecipeMap.put(seaCharm0, List.of(new ItemStack(SpurItems.SEA_PIECE_1.get(), 1)));

        tradeRecipeMap.put(seaCharm1, List.of(new ItemStack(SpurItems.SEA_PIECE_1.get(), 2),
                new ItemStack(SpurItems.SEA_CHARM_0.get())));

        tradeRecipeMap.put(seaCharm2, List.of(new ItemStack(SpurItems.SEA_PIECE_1.get(), 4),
                new ItemStack(SpurItems.SEA_CHARM_1.get())));

        tradeRecipeMap.put(seaCharm3, List.of(new ItemStack(SpurItems.SEA_PIECE_1.get(), 8),
                new ItemStack(SpurItems.SEA_CHARM_2.get())));

        tradeRecipeMap.put(seaCharm4, List.of(new ItemStack(SpurItems.SEA_PIECE_1.get(), 16),
                new ItemStack(SpurItems.SEA_CHARM_3.get())));

        tradeRecipeMap.put(seaCharm5, List.of(new ItemStack(SpurItems.SEA_PIECE_1.get(), 32),
                new ItemStack(SpurItems.SEA_CHARM_4.get())));

        tradeRecipeMap.put(seaCharm6, List.of(new ItemStack(SpurItems.SEA_PIECE_1.get(), 64),
                new ItemStack(SpurItems.SEA_CHARM_5.get())));
    }

    public static void cropCharm() {
        ItemStack cropPiece1 = new ItemStack(SpurItems.CROP_PIECE_1.get());
        ItemStack cropCharm0 = new ItemStack(SpurItems.CROP_CHARM_0.get());
        ItemStack cropCharm1 = new ItemStack(SpurItems.CROP_CHARM_1.get());
        ItemStack cropCharm2 = new ItemStack(SpurItems.CROP_CHARM_2.get());
        ItemStack cropCharm3 = new ItemStack(SpurItems.CROP_CHARM_3.get());
        ItemStack cropCharm4 = new ItemStack(SpurItems.CROP_CHARM_4.get());
        ItemStack cropCharm5 = new ItemStack(SpurItems.CROP_CHARM_5.get());
        ItemStack cropCharm6 = new ItemStack(SpurItems.CROP_CHARM_6.get());
        ItemStack natureCore = new ItemStack(ModItems.NATURAL_CORE.get());
        ItemStack[] itemStacks = {
                cropPiece1, natureCore,
                cropCharm0, cropCharm1, cropCharm2, cropCharm3, cropCharm4, cropCharm5, cropCharm6
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("农耕大师", "cropCharm", CustomStyle.styleOfField, VillagerType.PLAINS,
                VillagerProfession.FARMER, contentList);

        tradeRecipeMap.put(cropPiece1, List.of(new ItemStack(SpurItems.CROP_PIECE.get(), 64)));

        tradeRecipeMap.put(natureCore, List.of(new ItemStack(Items.WHEAT, 24),
                new ItemStack(Items.CARROT, 24),
                new ItemStack(Items.POTATO, 24),
                new ItemStack(Items.BEETROOT, 24),
                new ItemStack(Items.TORCHFLOWER, 24),
                new ItemStack(Items.SWEET_BERRIES, 24)));

        tradeRecipeMap.put(cropCharm0, List.of(new ItemStack(SpurItems.CROP_PIECE_1.get(), 1)));

        tradeRecipeMap.put(cropCharm1, List.of(new ItemStack(SpurItems.CROP_PIECE_1.get(), 2),
                new ItemStack(SpurItems.CROP_CHARM_0.get())));

        tradeRecipeMap.put(cropCharm2, List.of(new ItemStack(SpurItems.CROP_PIECE_1.get(), 4),
                new ItemStack(SpurItems.CROP_CHARM_1.get())));

        tradeRecipeMap.put(cropCharm3, List.of(new ItemStack(SpurItems.CROP_PIECE_1.get(), 8),
                new ItemStack(SpurItems.CROP_CHARM_2.get())));

        tradeRecipeMap.put(cropCharm4, List.of(new ItemStack(SpurItems.CROP_PIECE_1.get(), 16),
                new ItemStack(SpurItems.CROP_CHARM_3.get())));

        tradeRecipeMap.put(cropCharm5, List.of(new ItemStack(SpurItems.CROP_PIECE_1.get(), 32),
                new ItemStack(SpurItems.CROP_CHARM_4.get())));

        tradeRecipeMap.put(cropCharm6, List.of(new ItemStack(SpurItems.CROP_PIECE_1.get(), 64),
                new ItemStack(SpurItems.CROP_CHARM_5.get())));
    }

    public static void logCharm() {
        ItemStack ironAxe = new ItemStack(Items.IRON_AXE);
        ItemStack diamondAxe = new ItemStack(Items.DIAMOND_AXE);
        ItemStack netheriteAxe = new ItemStack(Items.NETHERITE_AXE);
        ItemStack logPiece1 = new ItemStack(SpurItems.LOG_PIECE_1.get());
        ItemStack logCharm0 = new ItemStack(SpurItems.LOG_CHARM_0.get());
        ItemStack logCharm1 = new ItemStack(SpurItems.LOG_CHARM_1.get());
        ItemStack logCharm2 = new ItemStack(SpurItems.LOG_CHARM_2.get());
        ItemStack logCharm3 = new ItemStack(SpurItems.LOG_CHARM_3.get());
        ItemStack logCharm4 = new ItemStack(SpurItems.LOG_CHARM_4.get());
        ItemStack logCharm5 = new ItemStack(SpurItems.LOG_CHARM_5.get());
        ItemStack logCharm6 = new ItemStack(SpurItems.LOG_CHARM_6.get());
        ItemStack[] itemStacks = {
                ironAxe, diamondAxe, netheriteAxe, logPiece1,
                logCharm0, logCharm1, logCharm2, logCharm3, logCharm4, logCharm5, logCharm6
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("伐木大师", "logCharm", CustomStyle.styleOfHusk, VillagerType.JUNGLE,
                VillagerProfession.LEATHERWORKER, contentList);

        tradeRecipeMap.put(ironAxe, List.of(new ItemStack(ModItems.GOLD_COIN.get(), 1)));
        tradeRecipeMap.put(diamondAxe, List.of(new ItemStack(ModItems.GOLD_COIN.get(), 3)));
        tradeRecipeMap.put(netheriteAxe, List.of(new ItemStack(ModItems.GOLD_COIN.get(), 8)));

        tradeRecipeMap.put(logPiece1, List.of(new ItemStack(SpurItems.LOG_PIECE.get(), 64)));

        tradeRecipeMap.put(logCharm0, List.of(new ItemStack(SpurItems.LOG_PIECE_1.get(), 1)));

        tradeRecipeMap.put(logCharm1, List.of(new ItemStack(SpurItems.LOG_PIECE_1.get(), 2),
                new ItemStack(SpurItems.LOG_CHARM_0.get())));

        tradeRecipeMap.put(logCharm2, List.of(new ItemStack(SpurItems.LOG_PIECE_1.get(), 4),
                new ItemStack(SpurItems.LOG_CHARM_1.get())));

        tradeRecipeMap.put(logCharm3, List.of(new ItemStack(SpurItems.LOG_PIECE_1.get(), 8),
                new ItemStack(SpurItems.LOG_CHARM_2.get())));

        tradeRecipeMap.put(logCharm4, List.of(new ItemStack(SpurItems.LOG_PIECE_1.get(), 16),
                new ItemStack(SpurItems.LOG_CHARM_3.get())));

        tradeRecipeMap.put(logCharm5, List.of(new ItemStack(SpurItems.LOG_PIECE_1.get(), 32),
                new ItemStack(SpurItems.LOG_CHARM_4.get())));

        tradeRecipeMap.put(logCharm6, List.of(new ItemStack(SpurItems.LOG_PIECE_1.get(), 64),
                new ItemStack(SpurItems.LOG_CHARM_5.get())));
    }

    public static void elementMaster() {
        ItemStack UDisk = new ItemStack(ModItems.U_DISK.get());

        List<ItemStack> piece1 = List.of(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_1.get()),
                new ItemStack(ModItems.WATER_ELEMENT_PIECE_1.get()),
                new ItemStack(ModItems.STONE_ELEMENT_PIECE_1.get()),
                new ItemStack(ModItems.FIRE_ELEMENT_PIECE_1.get()),
                new ItemStack(ModItems.ICE_ELEMENT_PIECE_1.get()),
                new ItemStack(ModItems.WIND_ELEMENT_PIECE_1.get()),
                new ItemStack(ModItems.LIGHTNING_ELEMENT_PIECE_1.get()));

        List<ItemStack> piece2 = List.of(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_2.get()),
                new ItemStack(ModItems.WATER_ELEMENT_PIECE_2.get()),
                new ItemStack(ModItems.STONE_ELEMENT_PIECE_2.get()),
                new ItemStack(ModItems.FIRE_ELEMENT_PIECE_2.get()),
                new ItemStack(ModItems.ICE_ELEMENT_PIECE_2.get()),
                new ItemStack(ModItems.WIND_ELEMENT_PIECE_2.get()),
                new ItemStack(ModItems.LIGHTNING_ELEMENT_PIECE_2.get()));

        List<ItemStack> crystal = List.of(new ItemStack(ModItems.LIFE_CRYSTAL_0.get()),
                new ItemStack(ModItems.WATER_CRYSTAL_0.get()),
                new ItemStack(ModItems.STONE_CRYSTAL_0.get()),
                new ItemStack(ModItems.FIRE_CRYSTAL_0.get()),
                new ItemStack(ModItems.ICE_CRYSTAL_0.get()),
                new ItemStack(ModItems.WIND_CRYSTAL_0.get()),
                new ItemStack(ModItems.LIGHTNING_CRYSTAL_0.get()));

        List<ItemStack> holyStone = List.of(
                new ItemStack(ModItems.LIFE_HOLY_STONE_0.get()),
                new ItemStack(ModItems.WATER_HOLY_STONE_0.get()),
                new ItemStack(ModItems.STONE_HOLY_STONE_0.get()),
                new ItemStack(ModItems.FIRE_HOLY_STONE_0.get()),
                new ItemStack(ModItems.ICE_HOLY_STONE_0.get()),
                new ItemStack(ModItems.WIND_HOLY_STONE_0.get()),
                new ItemStack(ModItems.LIGHTNING_HOLY_STONE_0.get()),

                new ItemStack(ModItems.LIFE_HOLY_STONE_1.get()),
                new ItemStack(ModItems.WATER_HOLY_STONE_1.get()),
                new ItemStack(ModItems.STONE_HOLY_STONE_1.get()),
                new ItemStack(ModItems.FIRE_HOLY_STONE_1.get()),
                new ItemStack(ModItems.ICE_HOLY_STONE_1.get()),
                new ItemStack(ModItems.WIND_HOLY_STONE_1.get()),
                new ItemStack(ModItems.LIGHTNING_HOLY_STONE_1.get()),

                new ItemStack(ModItems.LIFE_HOLY_STONE_2.get()),
                new ItemStack(ModItems.WATER_HOLY_STONE_2.get()),
                new ItemStack(ModItems.STONE_HOLY_STONE_2.get()),
                new ItemStack(ModItems.FIRE_HOLY_STONE_2.get()),
                new ItemStack(ModItems.ICE_HOLY_STONE_2.get()),
                new ItemStack(ModItems.WIND_HOLY_STONE_2.get()),
                new ItemStack(ModItems.LIGHTNING_HOLY_STONE_2.get()));
        List<ItemStack> contentList = new ArrayList<>() {{
            add(UDisk);
            addAll(crystal);
            addAll(holyStone);
        }};
        MyVillagerData.setMyVillagerData("元素大师", "elementMaster", CustomStyle.styleOfWorld, VillagerType.SNOW,
                VillagerProfession.LIBRARIAN, contentList);
        tradeRecipeMap.put(UDisk, List.of(
                new ItemStack(ModItems.GOLD_COIN.get(), 64)
        ));
        for (int i = 0; i < crystal.size(); i++) {
            ItemStack itemCrystal = crystal.get(i);
            ItemStack itemPiece1 = piece1.get(i);
            ItemStack completeGem = ModItems.COMPLETE_GEM.get().getDefaultInstance();
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
                new ItemStack(NewRuneItems.PLAIN_NEW_RUNE.get()),
                new ItemStack(NewRuneItems.FOREST_NEW_RUNE.get()),
                new ItemStack(NewRuneItems.LAKE_NEW_RUNE.get()),
                new ItemStack(NewRuneItems.VOLCANO_NEW_RUNE.get()),
                new ItemStack(NewRuneItems.EVOKER_NEW_RUNE.get()),

                new ItemStack(NewRuneItems.MINE_NEW_RUNE.get()),
                new ItemStack(NewRuneItems.HUSK_NEW_RUNE.get()),
                new ItemStack(NewRuneItems.LIGHTNING_NEW_RUNE.get()),
                new ItemStack(NewRuneItems.NETHER_NEW_RUNE.get())
        };
        ItemStack[] materials = {
                new ItemStack(ModItems.PLAIN_RUNE.get(), 8),
                new ItemStack(ModItems.FOREST_RUNE.get(), 8),
                new ItemStack(ModItems.LAKE_RUNE.get(), 8),
                new ItemStack(ModItems.VOLCANO_RUNE.get(), 8),
                new ItemStack(ModItems.EVOKER_RUNE.get(), 2),

                new ItemStack(ModItems.MINE_RUNE.get(), 8),
                new ItemStack(ModItems.HUSK_RUNE.get(), 6),
                new ItemStack(ModItems.LIGHTNING_RUNE.get(), 6),
                new ItemStack(ModItems.NETHER_RUNE.get(), 2)
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("符石制作大师", "runeMaster", CustomStyle.styleOfMana, VillagerType.JUNGLE,
                VillagerProfession.LIBRARIAN, contentList);
        for (int i = 0; i < itemStacks.length; i++) {
            tradeRecipeMap.put(itemStacks[i], List.of(new ItemStack(NewRuneItems.EMPTY_RUNE.get(), 2),
                    materials[i]));
        }
    }

    public static void summerEvent() {
        ItemStack goldenCoin = new ItemStack(ModItems.GOLD_COIN.get());
        ItemStack goldenCoin_4 = new ItemStack(ModItems.GOLD_COIN.get(), 4);
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

    public static void endlessCoreStore() {
        List<ItemStack> contentList = new ArrayList<>();
        MyVillagerData.setMyVillagerData("逆熵学者", "endlessStore", CustomStyle.styleOfWorld,
                VillagerType.SAVANNA, VillagerProfession.LIBRARIAN, contentList);
    }

    public static void forgeHammer() {
        ItemStack woodenHammer = new ItemStack(ModItems.WOOD_HAMMER.get());
        ItemStack stoneHammer = new ItemStack(ModItems.STONE_HAMMER.get());
        ItemStack ironHammer = new ItemStack(ModItems.IRON_HAMMER.get());
        ItemStack copperHammer = new ItemStack(ModItems.COPPER_HAMMER.get());
        ItemStack goldHammer = new ItemStack(ModItems.GOLD_HAMMER.get());
        ItemStack diamondHammer = new ItemStack(ModItems.DIAMOND_HAMMER.get());
        ItemStack emeraldHammer = new ItemStack(ModItems.EMERALD_HAMMER.get());
        ItemStack netherHammer = new ItemStack(ModItems.NETHER_HAMMER.get());
        ItemStack endHammer = new ItemStack(ModItems.END_HAMMER.get());

        ItemStack[] itemStacks = {
                woodenHammer, stoneHammer, ironHammer,
                copperHammer, goldHammer, diamondHammer,
                emeraldHammer, netherHammer, endHammer
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("锻造锤商人", "forgeHammer", CustomStyle.styleOfStone,
                VillagerType.SAVANNA, VillagerProfession.TOOLSMITH, contentList);

        tradeRecipeMap.put(woodenHammer,
                List.of(new ItemStack(ModItems.SILVER_COIN.get(), 4)));
        tradeRecipeMap.put(stoneHammer,
                List.of(new ItemStack(ModItems.SILVER_COIN.get(), 8)));
        tradeRecipeMap.put(ironHammer,
                List.of(new ItemStack(ModItems.SILVER_COIN.get(), 16)));
        tradeRecipeMap.put(copperHammer,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 4)));
        tradeRecipeMap.put(goldHammer,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 32)));
        tradeRecipeMap.put(diamondHammer,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 96)));

        tradeRecipeMap.put(emeraldHammer,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 8),
                        new ItemStack(Items.EMERALD, 64)));
        tradeRecipeMap.put(netherHammer,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 16),
                        new ItemStack(ModItems.QUARTZ_RUNE.get(), 8),
                        new ItemStack(ModItems.NETHER_RUNE.get(), 8)));
        tradeRecipeMap.put(endHammer,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 32),
                        new ItemStack(ModItems.END_CRYSTAL.get(), 32)));
    }

    public static void sunCurio() {
        ItemStack roseGoldCoin = new ItemStack(ModItems.ROSE_GOLD_COIN.get());
        ItemStack tearCurio = new ItemStack(SunIslandItems.TEAR_CURIO_0.get());
        ItemStack brokenBlade = new ItemStack(SunIslandItems.BROKEN_BLADE_0.get());
        ItemStack frameArrow = new ItemStack(SunIslandItems.FRAME_ARROW_0.get());
        ItemStack devilPowerCurio = new ItemStack(SunIslandItems.DEVIL_POWER_CURIO.get());
        ItemStack sakuraIndustrySceptre = new ItemStack(HarbingerItems.SAKURA_INDUSTRY_SCEPTRE.get());
        ItemStack tabooPaperCurio = new ItemStack(SunIslandItems.TABOO_PAPER_CURIO.get());
        ItemStack[] itemStacks = {
                roseGoldCoin, tearCurio, devilPowerCurio,
                brokenBlade, frameArrow, tabooPaperCurio,
                sakuraIndustrySceptre
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("旭升岛秘藏商人", "sunCurio", CustomStyle.styleOfSunIsland,
                VillagerType.PLAINS, VillagerProfession.CARTOGRAPHER, contentList);

        tradeRecipeMap.put(roseGoldCoin,
                List.of(new ItemStack(ModItems.REFINED_PIECE.get(), 16),
                        new ItemStack(ModItems.GOLD_COIN.get(), 64)));
        tradeRecipeMap.put(brokenBlade,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 16),
                        new ItemStack(ModItems.GOLDEN_SHEET.get(), 8)));
        tradeRecipeMap.put(frameArrow,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 16),
                        new ItemStack(ModItems.VOLCANO_CORE.get(), 128)));
        tradeRecipeMap.put(tearCurio,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 16),
                        new ItemStack(ModItems.LAKE_CORE.get(), 128)));
        tradeRecipeMap.put(devilPowerCurio,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 16),
                        new ItemStack(ModItems.HUSK_RUNE.get(), 24)));
        tradeRecipeMap.put(tabooPaperCurio,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 16),
                        new ItemStack(ModItems.BLOOD_MANA_RUNE.get(), 12),
                        new ItemStack(ModItems.EARTH_MANA_RUNE.get(), 12)));
        tradeRecipeMap.put(sakuraIndustrySceptre,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 32),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 16)));
    }

    public static void allay() {
        ItemStack attackSkillBook = new ItemStack(AllayItems.ATTACK_SKILL_BOOK.get());
        ItemStack healingSkillBook = new ItemStack(AllayItems.HEALING_SKILL_BOOK.get());
        ItemStack gemPieceSkillBook = new ItemStack(AllayItems.GEM_PIECE_SKILL_BOOK.get());
        ItemStack[] itemStacks = {
                attackSkillBook, healingSkillBook, gemPieceSkillBook
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("悦灵学者", "allay", CustomStyle.styleOfWorld,
                VillagerType.PLAINS, VillagerProfession.CARTOGRAPHER, contentList);
        tradeRecipeMap.put(attackSkillBook,
                List.of(new ItemStack(ModItems.GOLDEN_BEANS.get(), 2)));
        tradeRecipeMap.put(healingSkillBook,
                List.of(new ItemStack(ModItems.GOLDEN_BEANS.get(), 2)));
        tradeRecipeMap.put(gemPieceSkillBook,
                List.of(new ItemStack(ModItems.GOLDEN_BEANS.get(), 2)));
    }

    public static void entrustmentStore() {
        ItemStack allaySpawner = new ItemStack(AllayItems.ALLAY_SPAWNER.get());
        ItemStack allayNugget = new ItemStack(AllayItems.ALLAY_NUGGET.get());
        ItemStack smithBook = new ItemStack(SmithItems.SMITH_BOOK.get());
        ItemStack smithStone = new ItemStack(SmithItems.SMITH_STONE.get());
        ItemStack revelationHeart = new ItemStack(ModItems.REVELATION_HEART.get());
        ItemStack goldenCoinBag = new ItemStack(ModItems.GOLD_COIN_BAG.get(), 3);
        ItemStack gemPiece = new ItemStack(ModItems.GEM_PIECE.get(), 12);
        ItemStack speedComposites = new ItemStack(CompositesItems.SPEED_COMPOSITES.get());
        ItemStack holyChestKey = new ItemStack(HolyItems.HOLY_CHEST_KEY.get());
        ItemStack[] itemStacks = {
                allaySpawner, allayNugget, smithBook, smithStone,
                revelationHeart, goldenCoinBag, gemPiece, speedComposites, holyChestKey
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData(MobKillEntrustment.VILLAGER_NAME, "entrustment",
                CustomStyle.styleOfWorld, VillagerType.PLAINS, VillagerProfession.LIBRARIAN, contentList);

        tradeRecipeMap.put(allaySpawner,
                List.of(new ItemStack(ModItems.BOND.get(), 3)));
        tradeRecipeMap.put(allayNugget,
                List.of(new ItemStack(ModItems.BOND.get(), 1)));
        tradeRecipeMap.put(smithBook,
                List.of(new ItemStack(ModItems.BOND.get(), 3)));
        tradeRecipeMap.put(smithStone,
                List.of(new ItemStack(ModItems.BOND.get(), 1)));

        tradeRecipeMap.put(revelationHeart,
                List.of(new ItemStack(ModItems.GOLDEN_BEANS.get(), 1)));
        tradeRecipeMap.put(goldenCoinBag,
                List.of(new ItemStack(ModItems.GOLDEN_BEANS.get(), 1)));
        tradeRecipeMap.put(gemPiece,
                List.of(new ItemStack(ModItems.GOLDEN_BEANS.get(), 1)));

        tradeRecipeMap.put(speedComposites,
                List.of(new ItemStack(ModItems.GOLDEN_BEANS.get(), 500),
                        new ItemStack(ModItems.MILLION_MONEY.get(), 5),
                        new ItemStack(ModItems.RANDOM_EVENT_MEDAL.get(), 100)));

        tradeRecipeMap.put(holyChestKey,
                List.of(new ItemStack(ModItems.GOLDEN_BEANS.get(), 2)));
    }

    public static void goldCoinStore() {
        ItemStack roseGoldCoin = new ItemStack(ModItems.ROSE_GOLD_COIN.get());
        ItemStack backSpawn = new ItemStack(ModItems.BACK_SPAWN_TICKET.get(), 1);
        ItemStack uDisk = new ItemStack(ModItems.U_DISK.get(), 1);
        ItemStack backpackTicket = new ItemStack(ModItems.BACKPACK_TICKETS.get(), 1);
        ItemStack goldCoinLottery = new ItemStack(ModItems.GOLD_COIN_LOTTERY.get(), 1);
        ItemStack skinTemplate = new ItemStack(moe.plushie.armourers_workshop.init.ModItems.SKIN_TEMPLATE.get(), 1);
        ItemStack stackUpgradeTier4 =
                new ItemStack(net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.STACK_UPGRADE_TIER_4.get(), 1);
        ItemStack pickupUpgrade =
                new ItemStack(net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.ADVANCED_PICKUP_UPGRADE.get(), 1);
        ItemStack inceptionUpgrade =
                new ItemStack(net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.INCEPTION_UPGRADE.get(), 1);
        ItemStack constrainTaboo =
                new ItemStack(ModItems.CONSTRAINT_TABOO.get(), 3);
        ItemStack fantasyMedal1 = new ItemStack(ModItems.FANTASY_MEDAL_1.get());
        ItemStack fantasyBracelet1 = new ItemStack(ModItems.FANTASY_BRACELET_1.get());
        ItemStack fantasyMedal2 = new ItemStack(ModItems.FANTASY_MEDAL_2.get());
        ItemStack fantasyBracelet2 = new ItemStack(ModItems.FANTASY_BRACELET_2.get());
        ItemStack[] itemStacks = {
                roseGoldCoin, backSpawn, uDisk, backpackTicket, goldCoinLottery,
                skinTemplate, stackUpgradeTier4, pickupUpgrade, inceptionUpgrade,
                fantasyMedal1, fantasyBracelet1, fantasyMedal2, fantasyBracelet2,
                constrainTaboo
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        tradeContent.put(StringUtils.VillagerName.GoldCoinStore, contentList);
        tradeRecipeMap.put(roseGoldCoin,
                List.of(new ItemStack(ModItems.REFINED_PIECE.get(), 16),
                        new ItemStack(ModItems.GOLD_COIN.get(), 64)));
        tradeRecipeMap.put(backSpawn, List.of(
                new ItemStack(ModItems.GOLD_COIN.get(), 8)
        ));
        tradeRecipeMap.put(uDisk, List.of(
                new ItemStack(ModItems.GOLD_COIN.get(), 32)
        ));
        tradeRecipeMap.put(backpackTicket, List.of(
                new ItemStack(ModItems.GOLD_COIN.get(), 64)
        ));
        tradeRecipeMap.put(goldCoinLottery, List.of(
                new ItemStack(ModItems.GOLD_COIN.get(), 64)
        ));
        tradeRecipeMap.put(skinTemplate, List.of(
                new ItemStack(ModItems.SKIN_TEMPLATE_PAPER.get())
        ));
        tradeRecipeMap.put(stackUpgradeTier4, List.of(
                new ItemStack(ModItems.STACK_UPGRADE_PAPER.get())
        ));
        tradeRecipeMap.put(pickupUpgrade, List.of(
                new ItemStack(ModItems.PICK_UPGRADE_PAPER.get())
        ));
        tradeRecipeMap.put(inceptionUpgrade, List.of(
                new ItemStack(ModItems.INCEPTION_UPGRADE_PAPER.get())
        ));
        tradeRecipeMap.put(fantasyMedal1, List.of(
                new ItemStack(ModItems.FANTASY_MEDAL.get(), 16),
                new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 16),
                new ItemStack(ModItems.GOLDEN_BEANS.get(), 16)
        ));
        tradeRecipeMap.put(fantasyBracelet1, List.of(
                new ItemStack(ModItems.FANTASY_BRACELET.get(), 16),
                new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 16),
                new ItemStack(ModItems.GOLDEN_BEANS.get(), 16)
        ));
        tradeRecipeMap.put(fantasyMedal2, List.of(
                new ItemStack(ModItems.FANTASY_MEDAL_1.get(), 16)
        ));
        tradeRecipeMap.put(fantasyBracelet2, List.of(
                new ItemStack(ModItems.FANTASY_BRACELET_1.get(), 16)
        ));
        tradeRecipeMap.put(constrainTaboo, List.of(
                new ItemStack(GemItems.MOON_ATTACK_GEM_D.get(), 1)
        ));
    }

    public static void springEvent() {
        ItemStack fireCracker = new ItemStack(SpecialEventItems.FIRE_CRACKER.get(), 64);
        ItemStack fireworkGun = new ItemStack(SpecialEventItems.FIRE_WORK_GUN.get());
        ItemStack redEnvelope = new ItemStack(SpecialEventItems.RED_ENVELOPE.get());
        ItemStack goldIngot = new ItemStack(SpecialEventItems.SPRING_GOLD_INGOT.get());
        ItemStack[] itemStacks = {
                fireCracker, fireworkGun, redEnvelope, goldIngot
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("新春活动兑换", "spring",
                CustomStyle.styleOfSpring, VillagerType.PLAINS, VillagerProfession.LIBRARIAN, contentList);
        tradeRecipeMap.put(fireCracker,
                List.of(new ItemStack(SpecialEventItems.MONEY.get(), 1)));
        tradeRecipeMap.put(fireworkGun,
                List.of(new ItemStack(SpecialEventItems.MONEY.get(), 64)));
        tradeRecipeMap.put(redEnvelope,
                List.of(new ItemStack(SpecialEventItems.MONEY.get(), 10)));
        tradeRecipeMap.put(goldIngot,
                List.of(new ItemStack(SpecialEventItems.SPRING_GOLD_COIN.get(), 5)));
    }

    public static void divineIsland() {
        ItemStack divineRuneWeapon = new ItemStack(DivineIslandItems.DIVINE_RUNE_WEAPON.get());
        ItemStack divineRuneArmor = new ItemStack(DivineIslandItems.DIVINE_RUNE_ARMOR.get());
        ItemStack ghastlyIngot = new ItemStack(DivineIslandItems.GHASTLY_INGOT.get());
        ItemStack refinedDivinePiece = new ItemStack(DivineIslandItems.REFINED_DIVINE_PIECE.get());
        ItemStack[] itemStacks = {
                divineRuneWeapon, divineRuneArmor, ghastlyIngot, refinedDivinePiece
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("圣光贾人", "divineIsland",
                CustomStyle.DIVINE_STYLE, VillagerType.SNOW, VillagerProfession.TOOLSMITH, contentList);
        tradeRecipeMap.put(divineRuneWeapon,
                List.of(new ItemStack(DivineIslandItems.DIVINE_SOUL.get(), 10),
                        new ItemStack(DivineIslandItems.DIVINE_ARROW.get(), 30),
                        new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 1)));
        tradeRecipeMap.put(divineRuneArmor,
                List.of(new ItemStack(DivineIslandItems.DIVINE_SOUL.get(), 10),
                        new ItemStack(DivineIslandItems.DIVINE_GOLEM_SOUL.get(), 30),
                        new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 1)));
        tradeRecipeMap.put(ghastlyIngot,
                List.of(new ItemStack(DivineIslandItems.GHASTLY_NUGGET.get(), 10),
                        new ItemStack(DivineIslandItems.GHASTLY_SOUL.get(), 15),
                        new ItemStack(DivineIslandItems.GHASTLY_GUN_POWDER.get(), 15)));
        tradeRecipeMap.put(refinedDivinePiece,
                List.of(new ItemStack(DivineIslandItems.DIVINE_SOUL.get(), 16),
                        new ItemStack(ModItems.REFINED_PIECE.get(), 16)));
    }

    public static void bunker() {
        ItemStack bunkerRune = new ItemStack(BunkerItems.BUNKER_RUNE.get());
        ItemStack bunkerBossRune = new ItemStack(BunkerItems.BUNKER_BOSS_RUNE.get());

        ItemStack bunkerAttackCrest0 = new ItemStack(BunkerItems.BUNKER_ATTACK_CREST_0.get());
        ItemStack bunkerAttackCrest1 = new ItemStack(BunkerItems.BUNKER_ATTACK_CREST_1.get());
        ItemStack bunkerAttackCrest1_E = new ItemStack(BunkerItems.BUNKER_ATTACK_CREST_1.get());
        ItemStack bunkerAttackCrest2 = new ItemStack(BunkerItems.BUNKER_ATTACK_CREST_2.get());
        ItemStack bunkerAttackCrest2_E = new ItemStack(BunkerItems.BUNKER_ATTACK_CREST_2.get());
        ItemStack bunkerAttackCrest3 = new ItemStack(BunkerItems.BUNKER_ATTACK_CREST_3.get());
        ItemStack bunkerAttackCrest3_E = new ItemStack(BunkerItems.BUNKER_ATTACK_CREST_3.get());
        ItemStack bunkerAttackCrest4 = new ItemStack(BunkerItems.BUNKER_ATTACK_CREST_4.get());
        ItemStack bunkerAttackCrest4_E = new ItemStack(BunkerItems.BUNKER_ATTACK_CREST_4.get());

        ItemStack bunkerManaCrest0 = new ItemStack(BunkerItems.BUNKER_MANA_CREST_0.get());
        ItemStack bunkerManaCrest1 = new ItemStack(BunkerItems.BUNKER_MANA_CREST_1.get());
        ItemStack bunkerManaCrest1_E = new ItemStack(BunkerItems.BUNKER_MANA_CREST_1.get());
        ItemStack bunkerManaCrest2 = new ItemStack(BunkerItems.BUNKER_MANA_CREST_2.get());
        ItemStack bunkerManaCrest2_E = new ItemStack(BunkerItems.BUNKER_MANA_CREST_2.get());
        ItemStack bunkerManaCrest3 = new ItemStack(BunkerItems.BUNKER_MANA_CREST_3.get());
        ItemStack bunkerManaCrest3_E = new ItemStack(BunkerItems.BUNKER_MANA_CREST_3.get());
        ItemStack bunkerManaCrest4 = new ItemStack(BunkerItems.BUNKER_MANA_CREST_4.get());
        ItemStack bunkerManaCrest4_E = new ItemStack(BunkerItems.BUNKER_MANA_CREST_4.get());
        ItemStack[] itemStacks = {
                bunkerRune, bunkerBossRune,
                bunkerAttackCrest0,
                bunkerAttackCrest1, bunkerAttackCrest1_E,
                bunkerAttackCrest2, bunkerAttackCrest2_E,
                bunkerAttackCrest3, bunkerAttackCrest3_E,
                bunkerAttackCrest4, bunkerAttackCrest4_E,
                bunkerManaCrest0,
                bunkerManaCrest1, bunkerManaCrest1_E,
                bunkerManaCrest2, bunkerManaCrest2_E,
                bunkerManaCrest3, bunkerManaCrest3_E,
                bunkerManaCrest4, bunkerManaCrest4_E,
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("禁忌工学者", "bunker",
                CustomStyle.BUNKER_STYLE, VillagerType.SAVANNA, VillagerProfession.TOOLSMITH, contentList);
        tradeRecipeMap.put(bunkerRune,
                List.of(new ItemStack(BunkerItems.BUNKER_SOUL.get(), 30),
                        new ItemStack(ModItems.NETHER_QUARTZ.get(), 8),
                        new ItemStack(ModItems.RUBY.get(), 8)));
        tradeRecipeMap.put(bunkerBossRune,
                List.of(new ItemStack(BunkerItems.BUNKER_BOSS_SOUL.get(), 15),
                        new ItemStack(ModItems.EARTH_MANA_RUNE.get(), 1),
                        new ItemStack(ModItems.BLOOD_MANA_RUNE.get(), 1)));

        tradeRecipeMap.put(bunkerAttackCrest0,
                List.of(new ItemStack(ModItems.VOLCANO_CREST_0.get(), 4),
                        new ItemStack(BunkerItems.BUNKER_BOSS_RUNE.get(), 4)));
        tradeRecipeMap.put(bunkerAttackCrest1,
                List.of(new ItemStack(ModItems.VOLCANO_CREST_1.get(), 4),
                        new ItemStack(BunkerItems.BUNKER_BOSS_RUNE.get(), 4)));
        tradeRecipeMap.put(bunkerAttackCrest1_E,
                List.of(new ItemStack(BunkerItems.BUNKER_ATTACK_CREST_0.get(), 4)));
        tradeRecipeMap.put(bunkerAttackCrest2,
                List.of(new ItemStack(ModItems.VOLCANO_CREST_2.get(), 4),
                        new ItemStack(BunkerItems.BUNKER_BOSS_RUNE.get(), 4)));
        tradeRecipeMap.put(bunkerAttackCrest2_E,
                List.of(new ItemStack(BunkerItems.BUNKER_ATTACK_CREST_1.get(), 4)));
        tradeRecipeMap.put(bunkerAttackCrest3,
                List.of(new ItemStack(ModItems.VOLCANO_CREST_3.get(), 4),
                        new ItemStack(BunkerItems.BUNKER_BOSS_RUNE.get(), 4)));
        tradeRecipeMap.put(bunkerAttackCrest3_E,
                List.of(new ItemStack(BunkerItems.BUNKER_ATTACK_CREST_2.get(), 4)));
        tradeRecipeMap.put(bunkerAttackCrest4,
                List.of(new ItemStack(ModItems.VOLCANO_CREST_4.get(), 4),
                        new ItemStack(BunkerItems.BUNKER_BOSS_RUNE.get(), 4)));
        tradeRecipeMap.put(bunkerAttackCrest4_E,
                List.of(new ItemStack(BunkerItems.BUNKER_ATTACK_CREST_3.get(), 4)));

        tradeRecipeMap.put(bunkerManaCrest0,
                List.of(new ItemStack(ModItems.MANA_CREST_0.get(), 4),
                        new ItemStack(BunkerItems.BUNKER_BOSS_RUNE.get(), 4)));
        tradeRecipeMap.put(bunkerManaCrest1,
                List.of(new ItemStack(ModItems.MANA_CREST_1.get(), 4),
                        new ItemStack(BunkerItems.BUNKER_BOSS_RUNE.get(), 4)));
        tradeRecipeMap.put(bunkerManaCrest1_E,
                List.of(new ItemStack(BunkerItems.BUNKER_MANA_CREST_0.get(), 4)));
        tradeRecipeMap.put(bunkerManaCrest2,
                List.of(new ItemStack(ModItems.MANA_CREST_2.get(), 4),
                        new ItemStack(BunkerItems.BUNKER_BOSS_RUNE.get(), 4)));
        tradeRecipeMap.put(bunkerManaCrest2_E,
                List.of(new ItemStack(BunkerItems.BUNKER_MANA_CREST_1.get(), 4)));
        tradeRecipeMap.put(bunkerManaCrest3,
                List.of(new ItemStack(ModItems.MANA_CREST_3.get(), 4),
                        new ItemStack(BunkerItems.BUNKER_BOSS_RUNE.get(), 4)));
        tradeRecipeMap.put(bunkerManaCrest3_E,
                List.of(new ItemStack(BunkerItems.BUNKER_MANA_CREST_2.get(), 4)));
        tradeRecipeMap.put(bunkerManaCrest4,
                List.of(new ItemStack(ModItems.MANA_CREST_4.get(), 4),
                        new ItemStack(BunkerItems.BUNKER_BOSS_RUNE.get(), 4)));
        tradeRecipeMap.put(bunkerManaCrest4_E,
                List.of(new ItemStack(BunkerItems.BUNKER_MANA_CREST_3.get(), 4)));
    }

    public static void purpleIronWeapon() {
        ItemStack purpleIronBud2 = new ItemStack(ModItems.PURPLE_IRON_BUD_2.get(), 2);
        ItemStack purpleIronSword = new ItemStack(ModItems.PURPLE_IRON_SWORD.get());
        ItemStack purpleIronBow = new ItemStack(ModItems.PURPLE_IRON_BOW.get());
        ItemStack purpleIronSceptre = new ItemStack(ModItems.PURPLE_IRON_SCEPTRE.get());
        ItemStack[] itemStacks = {
                purpleIronBud2,
                purpleIronSword, purpleIronBow, purpleIronSceptre
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("紫晶工匠", "purpleIronWeapon",
                CustomStyle.styleOfPurpleIron, VillagerType.PLAINS, VillagerProfession.TOOLSMITH, contentList);
        tradeRecipeMap.put(purpleIronBud2,
                List.of(new ItemStack(ModItems.PURPLE_IRON_WEAPON_PIECE.get(), 1)));
        tradeRecipeMap.put(purpleIronSword,
                List.of(new ItemStack(ModItems.PURPLE_IRON_WEAPON_PIECE.get(), 2)));
        tradeRecipeMap.put(purpleIronBow,
                List.of(new ItemStack(ModItems.PURPLE_IRON_WEAPON_PIECE.get(), 2)));
        tradeRecipeMap.put(purpleIronSceptre,
                List.of(new ItemStack(ModItems.PURPLE_IRON_WEAPON_PIECE.get(), 2)));
    }

    public static void mushroomGem() {
        ItemStack unknownMushroom = new ItemStack(MushroomItems.UNKNOWN_MUSHROOM.get(), 6);
        ItemStack sputterGem = new ItemStack(GemItems.MUSHROOM_SPUTTER_GEM.get());
        ItemStack parasitismGem = new ItemStack(GemItems.MUSHROOM_PARASITISM_GEM.get());
        ItemStack splitGem = new ItemStack(GemItems.MUSHROOM_SPLIT_GEM.get());
        ItemStack brownMushroomPocket = new ItemStack(MushroomItems.BROWN_MUSHROOM_POCKET.get());
        ItemStack[] itemStacks = {
                unknownMushroom,
                sputterGem, parasitismGem, splitGem
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("菌菇宝石商人", "mushroomGem",
                CustomStyle.MUSHROOM_STYLE, VillagerType.JUNGLE, VillagerProfession.LIBRARIAN, contentList);
        tradeRecipeMap.put(unknownMushroom,
                List.of(new ItemStack(MushroomItems.MUSHROOM_GEM_PIECE.get(), 6)));
        tradeRecipeMap.put(sputterGem,
                List.of(new ItemStack(MushroomItems.MUSHROOM_GEM_PIECE.get(), 12)));
        tradeRecipeMap.put(parasitismGem,
                List.of(new ItemStack(MushroomItems.MUSHROOM_GEM_PIECE.get(), 12)));
        tradeRecipeMap.put(splitGem,
                List.of(new ItemStack(MushroomItems.MUSHROOM_GEM_PIECE.get(), 12)));
        tradeRecipeMap.put(brownMushroomPocket,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 5),
                        new ItemStack(MushroomItems.BROWN_MUSHROOM.get(), 64)));
    }

    public static void divineGem() {
        ItemStack divineGemPassiveTier0 = new ItemStack(DivineIslandItems.DIVINE_GEM_PASSIVE_TIER_0.get());
        ItemStack divineGemBase1Tier0 = new ItemStack(DivineIslandItems.DIVINE_GEM_BASE_1_TIER_0.get());
        ItemStack[] itemStacks = {
                divineGemPassiveTier0, divineGemBase1Tier0
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("纯粹光珠制作师", "divineGem",
                CustomStyle.DIVINE_STYLE, VillagerType.SNOW, VillagerProfession.LIBRARIAN, contentList);
        tradeRecipeMap.put(divineGemPassiveTier0,
                List.of(new ItemStack(DivineIslandItems.DIVINE_GEM_PIECE_0.get(), 90),
                        new ItemStack(DivineIslandItems.DIVINE_BOSS_SOUL.get(), 90),
                        new ItemStack(ModItems.RAINBOW_CRYSTAL.get(), 4)));
        tradeRecipeMap.put(divineGemBase1Tier0,
                List.of(new ItemStack(DivineIslandItems.DIVINE_GEM_PIECE_1.get(), 90),
                        new ItemStack(DivineIslandItems.DIVINE_BOSS_SOUL.get(), 90),
                        new ItemStack(ModItems.RAINBOW_CRYSTAL.get(), 4)));
    }

    public static void iceHoly() {
        ItemStack holyChestKey = new ItemStack(HolyItems.HOLY_CHEST_KEY.get());
        ItemStack iceHolyPieceChest0 = new ItemStack(IceHolyItems.PIECE_CHEST_0.get());
        ItemStack iceHolyPieceChest1 = new ItemStack(IceHolyItems.PIECE_CHEST_1.get());
        ItemStack iceHolyPieceChest2 = new ItemStack(IceHolyItems.PIECE_CHEST_2.get());
        ItemStack iceHolyPieceChest3 = new ItemStack(IceHolyItems.PIECE_CHEST_3.get());
        ItemStack[] itemStacks = {
                holyChestKey,
                iceHolyPieceChest0, iceHolyPieceChest1,
                iceHolyPieceChest2, iceHolyPieceChest3
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("冰霜信徒", "iceHoly",
                CustomStyle.DIVINE_STYLE, VillagerType.SNOW, VillagerProfession.FLETCHER, contentList);
        tradeRecipeMap.put(holyChestKey,
                List.of(new ItemStack(ModItems.GOLDEN_BEANS.get(), 2)));
        tradeRecipeMap.put(iceHolyPieceChest0,
                List.of(new ItemStack(IceHolyItems.PIECE_0.get(), 10)));
        tradeRecipeMap.put(iceHolyPieceChest1,
                List.of(new ItemStack(IceHolyItems.PIECE_1.get(), 10)));
        tradeRecipeMap.put(iceHolyPieceChest2,
                List.of(new ItemStack(IceHolyItems.PIECE_2.get(), 10)));
        tradeRecipeMap.put(iceHolyPieceChest3,
                List.of(new ItemStack(IceHolyItems.PIECE_3.get(), 10)));
    }

    public static void stoneSpider() {
        ItemStack stoneSpiderKnife1 = new ItemStack(NewAreaItems.STONE_SPIDER_KNIFE_1.get());
        ItemStack stoneSpiderKnife2 = new ItemStack(NewAreaItems.STONE_SPIDER_KNIFE_2.get());
        ItemStack stoneSpiderKnife3 = new ItemStack(NewAreaItems.STONE_SPIDER_KNIFE_3.get());
        ItemStack stoneSpiderGemAttack1 = new ItemStack(NewAreaItems.STONE_SPIDER_GEM_ATTACK_1.get());
        ItemStack stoneSpiderGemAttack2 = new ItemStack(NewAreaItems.STONE_SPIDER_GEM_ATTACK_2.get());
        ItemStack stoneSpiderGemMana1 = new ItemStack(NewAreaItems.STONE_SPIDER_GEM_MANA_1.get());
        ItemStack stoneSpiderGemMana2 = new ItemStack(NewAreaItems.STONE_SPIDER_GEM_MANA_2.get());
        ItemStack[] itemStacks = {
                stoneSpiderKnife1, stoneSpiderKnife2, stoneSpiderKnife3,
                stoneSpiderGemAttack1, stoneSpiderGemAttack2,
                stoneSpiderGemMana1, stoneSpiderGemMana2
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("石岸蜘蛛兑换", "stoneSpider",
                CustomStyle.styleOfStone, VillagerType.PLAINS, VillagerProfession.WEAPONSMITH, contentList);
        tradeRecipeMap.put(stoneSpiderKnife1,
                List.of(new ItemStack(NewAreaItems.STONE_SPIDER_KNIFE_0.get(), 1),
                        new ItemStack(NewAreaItems.STONE_SPIDER_RUNE.get(), 64)));
        tradeRecipeMap.put(stoneSpiderKnife2,
                List.of(new ItemStack(NewAreaItems.STONE_SPIDER_KNIFE_1.get(), 1),
                        new ItemStack(NewAreaItems.STONE_SPIDER_RUNE.get(), 64)));
        tradeRecipeMap.put(stoneSpiderKnife3,
                List.of(new ItemStack(NewAreaItems.STONE_SPIDER_KNIFE_2.get(), 1),
                        new ItemStack(NewAreaItems.STONE_SPIDER_RUNE.get(), 64)));
        tradeRecipeMap.put(stoneSpiderGemAttack1,
                List.of(new ItemStack(NewAreaItems.STONE_SPIDER_GEM_ATTACK_0.get(), 1),
                        new ItemStack(NewAreaItems.STONE_SPIDER_RUNE.get(), 64)));
        tradeRecipeMap.put(stoneSpiderGemAttack2,
                List.of(new ItemStack(NewAreaItems.STONE_SPIDER_GEM_ATTACK_1.get(), 1),
                        new ItemStack(NewAreaItems.STONE_SPIDER_RUNE.get(), 64)));
        tradeRecipeMap.put(stoneSpiderGemMana1,
                List.of(new ItemStack(NewAreaItems.STONE_SPIDER_GEM_MANA_0.get(), 1),
                        new ItemStack(NewAreaItems.STONE_SPIDER_RUNE.get(), 64)));
        tradeRecipeMap.put(stoneSpiderGemMana2,
                List.of(new ItemStack(NewAreaItems.STONE_SPIDER_GEM_MANA_1.get(), 1),
                        new ItemStack(NewAreaItems.STONE_SPIDER_RUNE.get(), 64)));
    }

    public static void labourDay() {
        ItemStack labourDayGoldCoin = new ItemStack(SpecialEventItems.OLD_GOLD_COIN.get());
        ItemStack labourDayLottery = new ItemStack(SpecialEventItems.LABOUR_DAY_LOTTERY.get());
        ItemStack labourDayIronHoe = new ItemStack(SpecialEventItems.LABOUR_DAY_IRON_HOE.get());
        ItemStack labourDayIronPickAxe = new ItemStack(SpecialEventItems.LABOUR_DAY_IRON_PICKAXE.get());
        ItemStack[] itemStacks = {
                labourDayGoldCoin, labourDayLottery,
                labourDayIronHoe, labourDayIronPickAxe
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("劳动节兑换", "labourDay",
                CustomStyle.styleOfGold, VillagerType.PLAINS, VillagerProfession.FARMER, contentList);
        tradeRecipeMap.put(labourDayGoldCoin,
                List.of(new ItemStack(SpecialEventItems.OLD_SILVER_COIN.get(), 64)));
        tradeRecipeMap.put(labourDayLottery,
                List.of(new ItemStack(SpecialEventItems.OLD_GOLD_COIN.get(), 1)));
        tradeRecipeMap.put(labourDayIronHoe,
                List.of(new ItemStack(SpecialEventItems.OLD_GOLD_COIN.get(), 28)));
        tradeRecipeMap.put(labourDayIronPickAxe,
                List.of(new ItemStack(SpecialEventItems.OLD_GOLD_COIN.get(), 28)));
    }

    public static void gateWay() {
        ItemStack tpTicket = new ItemStack(ModItems.TP_TICKET.get());
        ItemStack tpPass1Day = new ItemStack(ModItems.TP_PASS_1DAY.get());
        ItemStack tpPass2Day = new ItemStack(ModItems.TP_PASS_2DAY.get());
        ItemStack tpPass3Day = new ItemStack(ModItems.TP_PASS_3DAY.get());
        ItemStack goldCoin16 = new ItemStack(ModItems.GOLD_COIN.get(), 4);
        ItemStack goldCoin26 = new ItemStack(ModItems.GOLD_COIN.get(), 6);
        ItemStack goldCoin40 = new ItemStack(ModItems.GOLD_COIN.get(), 10);
        ItemStack[] itemStacks = {
                tpTicket, tpPass1Day,
                tpPass2Day, tpPass3Day,
                goldCoin16, goldCoin26, goldCoin40
        };
        List<ItemStack> contentList = new ArrayList<>();
        Collections.addAll(contentList, itemStacks);
        MyVillagerData.setMyVillagerData("传送中枢票务员", "gateWay",
                CustomStyle.styleOfEnd, VillagerType.SNOW, VillagerProfession.LIBRARIAN, contentList);
        tradeRecipeMap.put(tpTicket,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 2)));
        tradeRecipeMap.put(tpPass1Day,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 40)));
        tradeRecipeMap.put(tpPass2Day,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 64)));
        tradeRecipeMap.put(tpPass3Day,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 96)));
        tradeRecipeMap.put(goldCoin16,
                List.of(new ItemStack(ModItems.TP_PASS_1DAY.get(), 1)));
        tradeRecipeMap.put(goldCoin26,
                List.of(new ItemStack(ModItems.TP_PASS_2DAY.get(), 1)));
        tradeRecipeMap.put(goldCoin40,
                List.of(new ItemStack(ModItems.TP_PASS_3DAY.get(), 1)));
    }

    public static void mopUpPaper() {
        List<ItemStack> stacks = MopUpPaperItems.ITEMS.getEntries()
                .stream()
                .map(entry -> new ItemStack(entry.get(), 2)).toList();
        MyVillagerData.setMyVillagerData("S级怪物猎人", "mopUpPaper",
                CustomStyle.styleOfEnd, VillagerType.SAVANNA, VillagerProfession.ARMORER, stacks);
        for (ItemStack stack : stacks) {
            MopUpPaper mopUpPaper = (MopUpPaper) stack.getItem();
            if (mopUpPaper.noTeamInstance != null) {
                tradeRecipeMap.put(stack, new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(),
                            mopUpPaper.noTeamInstance.getRewardNeedItemCount() * 8));
                    add(new ItemStack(ModItems.NOTE_PAPER.get(),
                            mopUpPaper.noTeamInstance.getRewardNeedItemCount() * 3));
                }});
            } else {
                tradeRecipeMap.put(stack, new ArrayList<>() {{
                    add(new ItemStack(ModItems.GOLD_COIN.get(), 8));
                    add(new ItemStack(ModItems.NOTE_PAPER.get(), 3));
                }});
            }
        }
    }
}
