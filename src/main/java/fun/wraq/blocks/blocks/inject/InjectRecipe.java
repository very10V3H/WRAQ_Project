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
import fun.wraq.series.overworld.extraordinary.ExtraordinaryItems;
import fun.wraq.series.overworld.wind.WindItems;
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
        injectingRecipeMap.put(ModItems.PLAIN_SWORD_0.get(),
                new InjectingRecipe(ModItems.PLAIN_RUNE.get(), 1,
                        ModItems.PLAIN_SWORD_1.get()));
        injectingRecipeMap.put(ModItems.PLAIN_SWORD_1.get(),
                new InjectingRecipe(ModItems.PLAIN_RUNE.get(), 2,
                        ModItems.PLAIN_SWORD_2.get()));
        injectingRecipeMap.put(ModItems.PLAIN_SWORD_2.get(),
                new InjectingRecipe(ModItems.PLAIN_RUNE.get(), 3,
                        ModItems.PLAIN_SWORD_3.get()));

        injectingRecipeMap.put(ModItems.PLAIN_BOW_0.get(),
                new InjectingRecipe(ModItems.PLAIN_RUNE.get(), 1,
                        ModItems.PLAIN_BOW_1.get()));
        injectingRecipeMap.put(ModItems.PLAIN_BOW_1.get(),
                new InjectingRecipe(ModItems.PLAIN_RUNE.get(), 2,
                        ModItems.PLAIN_BOW_2.get()));
        injectingRecipeMap.put(ModItems.PLAIN_BOW_2.get(),
                new InjectingRecipe(ModItems.PLAIN_RUNE.get(), 3,
                        ModItems.PLAIN_BOW_3.get()));

        injectingRecipeMap.put(ModItems.LIFE_SCEPTRE_0.get(),
                new InjectingRecipe(ModItems.PLAIN_RUNE.get(), 1,
                        ModItems.LIFE_SCEPTRE_1.get()));
        injectingRecipeMap.put(ModItems.LIFE_SCEPTRE_1.get(),
                new InjectingRecipe(ModItems.PLAIN_RUNE.get(), 2,
                        ModItems.LIFE_SCEPTRE_2.get()));
        injectingRecipeMap.put(ModItems.LIFE_SCEPTRE_2.get(),
                new InjectingRecipe(ModItems.FOREST_RUNE.get(), 3,
                        ModItems.LIFE_SCEPTRE_3.get()));

        injectingRecipeMap.put(ModItems.FOREST_SWORD_0.get(),
                new InjectingRecipe(ModItems.FOREST_RUNE.get(), 1,
                        ModItems.FOREST_SWORD_1.get()));
        injectingRecipeMap.put(ModItems.FOREST_SWORD_1.get(),
                new InjectingRecipe(ModItems.FOREST_RUNE.get(), 2,
                        ModItems.FOREST_SWORD_2.get()));
        injectingRecipeMap.put(ModItems.FOREST_SWORD_2.get(),
                new InjectingRecipe(ModItems.FOREST_RUNE.get(), 3,
                        ModItems.FOREST_SWORD_3.get()));

        injectingRecipeMap.put(ModItems.FOREST_BOW_0.get(),
                new InjectingRecipe(ModItems.FOREST_RUNE.get(), 1,
                        ModItems.FOREST_BOW_1.get()));
        injectingRecipeMap.put(ModItems.FOREST_BOW_1.get(),
                new InjectingRecipe(ModItems.FOREST_RUNE.get(), 2,
                        ModItems.FOREST_BOW_2.get()));
        injectingRecipeMap.put(ModItems.FOREST_BOW_2.get(),
                new InjectingRecipe(ModItems.FOREST_RUNE.get(), 3,
                        ModItems.FOREST_BOW_3.get()));

        injectingRecipeMap.put(ModItems.LAKE_SWORD_0.get(),
                new InjectingRecipe(ModItems.LAKE_RUNE.get(), 1,
                        ModItems.LAKE_SWORD_1.get()));
        injectingRecipeMap.put(ModItems.LAKE_SWORD_1.get(),
                new InjectingRecipe(ModItems.LAKE_RUNE.get(), 2,
                        ModItems.LAKE_SWORD_2.get()));
        injectingRecipeMap.put(ModItems.LAKE_SWORD_2.get(),
                new InjectingRecipe(ModItems.LAKE_RUNE.get(), 3,
                        ModItems.LAKE_SWORD_3.get()));

        injectingRecipeMap.put(ModItems.LAKE_BOW_0.get(),
                new InjectingRecipe(ModItems.LAKE_RUNE.get(), 1,
                        ModItems.LAKE_BOW_1.get()));
        injectingRecipeMap.put(ModItems.LAKE_BOW_1.get(),
                new InjectingRecipe(ModItems.LAKE_RUNE.get(), 2,
                        ModItems.LAKE_BOW_2.get()));
        injectingRecipeMap.put(ModItems.LAKE_BOW_2.get(),
                new InjectingRecipe(ModItems.LAKE_RUNE.get(), 3,
                        ModItems.LAKE_BOW_3.get()));

        injectingRecipeMap.put(ModItems.LAKE_SCEPTRE_0.get(),
                new InjectingRecipe(ModItems.LAKE_RUNE.get(), 1,
                        ModItems.LAKE_SCEPTRE_1.get()));
        injectingRecipeMap.put(ModItems.LAKE_SCEPTRE_1.get(),
                new InjectingRecipe(ModItems.LAKE_RUNE.get(), 2,
                        ModItems.LAKE_SCEPTRE_2.get()));
        injectingRecipeMap.put(ModItems.LAKE_SCEPTRE_2.get(),
                new InjectingRecipe(ModItems.LAKE_RUNE.get(), 3,
                        ModItems.LAKE_SCEPTRE_3.get()));

        injectingRecipeMap.put(ModItems.VOLCANO_SWORD_0.get(),
                new InjectingRecipe(ModItems.VOLCANO_RUNE.get(), 1,
                        ModItems.VOLCANO_SWORD_1.get()));
        injectingRecipeMap.put(ModItems.VOLCANO_SWORD_1.get(),
                new InjectingRecipe(ModItems.VOLCANO_RUNE.get(), 2,
                        ModItems.VOLCANO_SWORD_2.get()));
        injectingRecipeMap.put(ModItems.VOLCANO_SWORD_2.get(),
                new InjectingRecipe(ModItems.VOLCANO_RUNE.get(), 3,
                        ModItems.VOLCANO_SWORD_3.get()));

        injectingRecipeMap.put(ModItems.VOLCANO_BOW_0.get(),
                new InjectingRecipe(ModItems.VOLCANO_RUNE.get(), 1,
                        ModItems.VOLCANO_BOW_1.get()));
        injectingRecipeMap.put(ModItems.VOLCANO_BOW_1.get(),
                new InjectingRecipe(ModItems.VOLCANO_RUNE.get(), 2,
                        ModItems.VOLCANO_BOW_2.get()));
        injectingRecipeMap.put(ModItems.VOLCANO_BOW_2.get(),
                new InjectingRecipe(ModItems.VOLCANO_RUNE.get(), 3,
                        ModItems.VOLCANO_BOW_3.get()));

        injectingRecipeMap.put(ModItems.MINE_SWORD_0.get(),
                new InjectingRecipe(ModItems.MINE_RUNE.get(), 1,
                        ModItems.MINE_SWORD_1.get()));
        injectingRecipeMap.put(ModItems.MINE_SWORD_1.get(),
                new InjectingRecipe(ModItems.MINE_RUNE.get(), 2,
                        ModItems.MINE_SWORD_2.get()));
        injectingRecipeMap.put(ModItems.MINE_SWORD_2.get(),
                new InjectingRecipe(ModItems.MINE_RUNE.get(), 3,
                        ModItems.MINE_SWORD_3.get()));

        injectingRecipeMap.put(ModItems.SNOW_SWORD_0.get(),
                new InjectingRecipe(ModItems.SNOW_RUNE.get(), 1,
                        ModItems.SNOW_SWORD_1.get()));
        injectingRecipeMap.put(ModItems.SNOW_SWORD_1.get(),
                new InjectingRecipe(ModItems.SNOW_RUNE.get(), 2,
                        ModItems.SNOW_SWORD_2.get()));
        injectingRecipeMap.put(ModItems.SNOW_SWORD_2.get(),
                new InjectingRecipe(ModItems.SNOW_RUNE.get(), 3,
                        ModItems.SNOW_SWORD_3.get()));

        injectingRecipeMap.put(ModItems.SEA_SWORD_0.get(),
                new InjectingRecipe(ModItems.SEA_RUNE.get(), 1,
                        ModItems.SEA_SWORD_1.get()));
        injectingRecipeMap.put(ModItems.SEA_SWORD_1.get(),
                new InjectingRecipe(ModItems.SEA_RUNE.get(), 2,
                        ModItems.SEA_SWORD_2.get()));
        injectingRecipeMap.put(ModItems.SEA_SWORD_2.get(),
                new InjectingRecipe(ModItems.SEA_RUNE.get(), 3,
                        ModItems.SEA_SWORD_3.get()));

        injectingRecipeMap.put(ModItems.HUSK_SWORD_0.get(),
                new InjectingRecipe(ModItems.HUSK_RUNE.get(), 1,
                        ModItems.HUSK_SWORD_1.get()));
        injectingRecipeMap.put(ModItems.HUSK_SWORD_1.get(),
                new InjectingRecipe(ModItems.HUSK_RUNE.get(), 2,
                        ModItems.HUSK_SWORD_2.get()));
        injectingRecipeMap.put(ModItems.HUSK_SWORD_2.get(),
                new InjectingRecipe(ModItems.HUSK_RUNE.get(), 3,
                        ModItems.HUSK_SWORD_3.get()));

        injectingRecipeMap.put(ModItems.WITHER_SWORD_0.get(),
                new InjectingRecipe(ModItems.WITHER_RUNE.get(), 1,
                        ModItems.WITHER_SWORD_1.get()));
        injectingRecipeMap.put(ModItems.WITHER_SWORD_1.get(),
                new InjectingRecipe(ModItems.WITHER_RUNE.get(), 2,
                        ModItems.WITHER_SWORD_2.get()));
        injectingRecipeMap.put(ModItems.WITHER_SWORD_2.get(),
                new InjectingRecipe(ModItems.WITHER_RUNE.get(), 3,
                        ModItems.WITHER_SWORD_3.get()));

        injectingRecipeMap.put(ModItems.WITHER_BOW_0.get(),
                new InjectingRecipe(ModItems.NETHER_RUNE.get(), 1,
                        ModItems.WITHER_BOW_1.get()));
        injectingRecipeMap.put(ModItems.WITHER_BOW_1.get(),
                new InjectingRecipe(ModItems.NETHER_RUNE.get(), 2,
                        ModItems.WITHER_BOW_2.get()));
        injectingRecipeMap.put(ModItems.WITHER_BOW_2.get(),
                new InjectingRecipe(ModItems.NETHER_RUNE.get(), 3,
                        ModItems.WITHER_BOW_3.get()));

        injectingRecipeMap.put(ModItems.MAGMA_SCEPTRE_0.get(),
                new InjectingRecipe(ModItems.MAGMA_RUNE.get(), 1,
                        ModItems.MAGMA_SCEPTRE_1.get()));
        injectingRecipeMap.put(ModItems.MAGMA_SCEPTRE_1.get(),
                new InjectingRecipe(ModItems.MAGMA_RUNE.get(), 2,
                        ModItems.MAGMA_SCEPTRE_2.get()));
        injectingRecipeMap.put(ModItems.MAGMA_SCEPTRE_2.get(),
                new InjectingRecipe(ModItems.MAGMA_RUNE.get(), 3,
                        ModItems.MAGMA_SCEPTRE_3.get()));

        injectingRecipeMap.put(ModItems.PIGLIN_HELMET_0.get(),
                new InjectingRecipe(ModItems.PIGLIN_RUNE.get(), 1,
                        ModItems.PIGLIN_HELMET_1.get()));
        injectingRecipeMap.put(ModItems.PIGLIN_HELMET_1.get(),
                new InjectingRecipe(ModItems.PIGLIN_RUNE.get(), 2,
                        ModItems.PIGLIN_HELMET_2.get()));
        injectingRecipeMap.put(ModItems.PIGLIN_HELMET_2.get(),
                new InjectingRecipe(ModItems.PIGLIN_RUNE.get(), 3,
                        ModItems.PIGLIN_HELMET_3.get()));

        injectingRecipeMap.put(ModItems.KAZE_RECALL_SOUL.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.INTENSIFIED_KAZE_SOUL.get()));

        injectingRecipeMap.put(ModItems.SPIDER_RECALL_SOUL.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.INTENSIFIED_SPIDER_SOUL.get()));

        injectingRecipeMap.put(ModItems.BLACK_FOREST_RECALL_SOUL.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.INTENSIFIED_BLACK_FOREST_SOUL.get()));


        injectingRecipeMap.put(ModItems.SEA_RECALL_SOUL.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.INTENSIFIED_SEA_SOUL.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_RECALL_SOUL.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.INTENSIFIED_LIGHTNING_SOUL.get()));

        injectingRecipeMap.put(ModItems.NETHER_RECALL_SOUL.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.INTENSIFIED_NETHER_SOUL.get()));

        injectingRecipeMap.put(ModItems.SNOW_RECALL_SOUL.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.INTENSIFIED_SNOW_SOUL.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_RECALL_SOUL.get(),
                new InjectingRecipe(4, ModItems.VOLCANO_CORE.get(), 64,
                        ModItems.INTENSIFIED_LIGHTNING_SOUL.get()));

        injectingRecipeMap.put(WindItems.WIND_SOUL.get(),
                new InjectingRecipe(64, ModItems.LAKE_CORE.get(), 1,
                        WindItems.WIND_RUNE.get()));

        injectingRecipeMap.put(ModItems.SEA_SOUL.get(),
                new InjectingRecipe(64, ModItems.SUN_POWER.get(), 1,
                        ModItems.SEA_RUNE.get()));

        injectingRecipeMap.put(ModItems.HUSK_SOUL.get(),
                new InjectingRecipe(64, ModItems.SUN_POWER.get(), 1,
                        ModItems.HUSK_RUNE.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_SOUL.get(),
                new InjectingRecipe(64, PickaxeItems.TINKER_IRON.get(), 1,
                        ModItems.LIGHTNING_RUNE.get()));

        injectingRecipeMap.put(ModItems.HUSK_SWORD_3.get(),
                new InjectingRecipe(ModItems.INTENSIFIED_BLACK_FOREST_SOUL.get(), 1,
                        ModItems.HUSK_SWORD_4.get()));

        injectingRecipeMap.put(ModItems.SEA_SWORD_3.get(),
                new InjectingRecipe(ModItems.INTENSIFIED_SEA_SOUL.get(), 1,
                        ModItems.SEA_SWORD_4.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_HELMET.get(),
                new InjectingRecipe(ModItems.INTENSIFIED_LIGHTNING_SOUL.get(), 1,
                        ModItems.IL_HELMET.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_CHEST.get(),
                new InjectingRecipe(ModItems.INTENSIFIED_LIGHTNING_SOUL.get(), 1,
                        ModItems.IL_CHEST.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_LEGGINGS.get(),
                new InjectingRecipe(ModItems.INTENSIFIED_LIGHTNING_SOUL.get(), 1,
                        ModItems.IL_LEGGINGS.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_BOOTS.get(),
                new InjectingRecipe(ModItems.INTENSIFIED_LIGHTNING_SOUL.get(), 1,
                        ModItems.IL_BOOTS.get()));

        injectingRecipeMap.put(ModItems.MANA_SWORD.get(),
                new InjectingRecipe(ModItems.INTENSIFIED_NETHER_SOUL.get(), 1,
                        ModItems.MANA_SWORD_1.get()));

        injectingRecipeMap.put(ModItems.SNOW_SWORD_3.get(),
                new InjectingRecipe(ModItems.INTENSIFIED_SNOW_SOUL.get(), 1,
                        ModItems.SNOW_SWORD_4.get()));

        injectingRecipeMap.put(ModItems.FOREST_SWORD_3.get(),
                new InjectingRecipe(ModItems.INTENSIFIED_FOREST_RECALL_SOUL.get(), 1,
                        ModItems.FOREST_SWORD_4.get()));

        injectingRecipeMap.put(ModItems.VOLCANO_SWORD_3.get(),
                new InjectingRecipe(ModItems.INTENSIFIED_VOLCANO_SOUL.get(), 1,
                        ModItems.VOLCANO_SWORD_4.get()));

        injectingRecipeMap.put(ModItems.MINE_SWORD_3.get(),
                new InjectingRecipe(ModItems.SNOW_RUNE.get(), 1,
                        ModItems.SNOW_SWORD_0.get()));

        injectingRecipeMap.put(ModItems.LIFE_SCEPTRE_3.get(),
                new InjectingRecipe(ModItems.FOREST_RUNE.get(), 2,
                        ModItems.LIFE_SCEPTRE_X.get()));

        injectingRecipeMap.put(ModItems.PLAIN_ATTACK_RING_0.get(),
                new InjectingRecipe(ModItems.PLAIN_COMPLETE_GEM.get(), 1,
                        ModItems.PLAIN_ATTACK_RING_1.get()));
        injectingRecipeMap.put(ModItems.PLAIN_ATTACK_RING_1.get(),
                new InjectingRecipe(ModItems.PLAIN_COMPLETE_GEM.get(), 1,
                        ModItems.PLAIN_ATTACK_RING_2.get()));
        injectingRecipeMap.put(ModItems.PLAIN_ATTACK_RING_2.get(),
                new InjectingRecipe(ModItems.PLAIN_COMPLETE_GEM.get(), 1,
                        ModItems.PLAIN_ATTACK_RING_3.get()));

        injectingRecipeMap.put(ModItems.PLAIN_MANA_RING_0.get(),
                new InjectingRecipe(ModItems.PLAIN_COMPLETE_GEM.get(), 1,
                        ModItems.PLAIN_MANA_RING_1.get()));
        injectingRecipeMap.put(ModItems.PLAIN_MANA_RING_1.get(),
                new InjectingRecipe(ModItems.PLAIN_COMPLETE_GEM.get(), 1,
                        ModItems.PLAIN_MANA_RING_2.get()));
        injectingRecipeMap.put(ModItems.PLAIN_MANA_RING_2.get(),
                new InjectingRecipe(ModItems.PLAIN_COMPLETE_GEM.get(), 1,
                        ModItems.PLAIN_MANA_RING_3.get()));

        injectingRecipeMap.put(ModItems.PLAIN_HEALTH_RING_0.get(),
                new InjectingRecipe(ModItems.PLAIN_COMPLETE_GEM.get(), 1,
                        ModItems.PLAIN_HEALTH_RING_1.get()));
        injectingRecipeMap.put(ModItems.PLAIN_HEALTH_RING_1.get(),
                new InjectingRecipe(ModItems.PLAIN_COMPLETE_GEM.get(), 1,
                        ModItems.PLAIN_HEALTH_RING_2.get()));
        injectingRecipeMap.put(ModItems.PLAIN_HEALTH_RING_2.get(),
                new InjectingRecipe(ModItems.PLAIN_COMPLETE_GEM.get(), 1,
                        ModItems.PLAIN_HEALTH_RING_3.get()));

        injectingRecipeMap.put(ModItems.PLAIN_DEFENCE_RING_0.get(),
                new InjectingRecipe(ModItems.PLAIN_COMPLETE_GEM.get(), 1,
                        ModItems.PLAIN_DEFENCE_RING_1.get()));
        injectingRecipeMap.put(ModItems.PLAIN_DEFENCE_RING_1.get(),
                new InjectingRecipe(ModItems.PLAIN_COMPLETE_GEM.get(), 1,
                        ModItems.PLAIN_DEFENCE_RING_2.get()));
        injectingRecipeMap.put(ModItems.PLAIN_DEFENCE_RING_2.get(),
                new InjectingRecipe(ModItems.PLAIN_COMPLETE_GEM.get(), 1,
                        ModItems.PLAIN_DEFENCE_RING_3.get()));

        injectingRecipeMap.put(ModItems.PLAIN_BOSS_SOUL.get(),
                new InjectingRecipe(16, ModItems.COMPLETE_GEM.get(), 1,
                        ModItems.PLAIN_COMPLETE_GEM.get()));

        injectingRecipeMap.put(ModItems.ICE_BOSS_SOUL.get(),
                new InjectingRecipe(16, ModItems.COMPLETE_GEM.get(), 1,
                        ModItems.ICE_COMPLETE_GEM.get()));

        injectingRecipeMap.put(ModItems.VOLCANO_BOW_3.get(),
                new InjectingRecipe(ModItems.QUARTZ_RUNE.get(), 1,
                        ModItems.NETHER_BOW.get()));

        injectingRecipeMap.put(ModItems.MINE_BOW_0.get(),
                new InjectingRecipe(ModItems.MINE_RUNE.get(), 1,
                        ModItems.MINE_BOW_1.get()));
        injectingRecipeMap.put(ModItems.MINE_BOW_1.get(),
                new InjectingRecipe(ModItems.MINE_RUNE.get(), 2,
                        ModItems.MINE_BOW_2.get()));
        injectingRecipeMap.put(ModItems.MINE_BOW_2.get(),
                new InjectingRecipe(ModItems.MINE_RUNE.get(), 3,
                        ModItems.MINE_BOW_3.get()));

        injectingRecipeMap.put(ModItems.LIFE_MANA_HELMET.get(),
                new InjectingRecipe(ModItems.NATURAL_CORE.get(), 3,
                        ModItems.LIFE_MANA_HELMET_E.get()));

        injectingRecipeMap.put(ModItems.LIFE_MANA_CHEST.get(),
                new InjectingRecipe(ModItems.NATURAL_CORE.get(), 3,
                        ModItems.LIFE_MANA_CHEST_E.get()));

        injectingRecipeMap.put(ModItems.LIFE_MANA_LEGGINGS.get(),
                new InjectingRecipe(ModItems.NATURAL_CORE.get(), 3,
                        ModItems.LIFE_MANA_LEGGINGS_E.get()));

        injectingRecipeMap.put(ModItems.LIFE_MANA_BOOTS.get(),
                new InjectingRecipe(ModItems.NATURAL_CORE.get(), 3,
                        ModItems.LIFE_MANA_BOOTS_E.get()));

        injectingRecipeMap.put(ModItems.OBSI_MANA_HELMET.get(),
                new InjectingRecipe(ModItems.ORE_RUNE.get(), 3,
                        ModItems.OBSI_MANA_HELMET_E.get()));

        injectingRecipeMap.put(ModItems.OBSI_MANA_CHEST.get(),
                new InjectingRecipe(ModItems.ORE_RUNE.get(), 3,
                        ModItems.OBSI_MANA_CHEST_E.get()));

        injectingRecipeMap.put(ModItems.OBSI_MANA_LEGGINGS.get(),
                new InjectingRecipe(ModItems.ORE_RUNE.get(), 3,
                        ModItems.OBSI_MANA_LEGGINGS_E.get()));

        injectingRecipeMap.put(ModItems.OBIS_MANA_BOOTS.get(),
                new InjectingRecipe(ModItems.ORE_RUNE.get(), 3,
                        ModItems.OBSI_MANA_BOOTS_E.get()));

        injectingRecipeMap.put(ModItems.EVOKER_BOOK_3.get(),
                new InjectingRecipe(ModItems.ICE_HEART.get(), 2,
                        ModItems.ICE_BOOK.get()));

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

        injectingRecipeMap.put(ModItems.BLOOD_MANA_CURIOS.get(),
                new InjectingRecipe(ModItems.DEVIL_BLOOD.get(), 6,
                        ModItems.DEVIL_BLOOD_MANA_CURIOS.get()));

        injectingRecipeMap.put(ModItems.EARTH_MANA_CURIOS.get(),
                new InjectingRecipe(ModItems.DEVIL_BLOOD.get(), 6,
                        ModItems.DEVIL_EARTH_MANA_CURIOS.get()));

        injectingRecipeMap.put(ModItems.MOON_SOUL.get(),
                new InjectingRecipe(16, ModItems.COMPLETE_GEM.get(), 2,
                        ModItems.MOON_COMPLETE_GEM.get()));

        injectingRecipeMap.put(ModItems.ICE_SWORD_E.get(),
                new InjectingRecipe(ModItems.DEVIL_BLOOD.get(), 8,
                        ModItems.DEVIL_SWORD.get()));

        injectingRecipeMap.put(ModItems.ICE_BOW_E.get(),
                new InjectingRecipe(ModItems.DEVIL_BLOOD.get(), 8,
                        ModItems.DEVIL_BOW.get()));

        injectingRecipeMap.put(ModItems.ICE_SCEPTRE_E.get(),
                new InjectingRecipe(ModItems.DEVIL_BLOOD.get(), 8,
                        ModItems.DEVIL_SCEPTRE.get()));

        injectingRecipeMap.put(GemItems.SKY_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.SKY_GEM_D.get()));

        injectingRecipeMap.put(GemItems.EVOKER_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.EVOKER_GEM_D.get()));

        injectingRecipeMap.put(GemItems.PLAIN_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.PLAIN_GEM_D.get()));

        injectingRecipeMap.put(GemItems.FOREST_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.FOREST_GEM_D.get()));

        injectingRecipeMap.put(GemItems.LAKE_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.LAKE_GEM_D.get()));

        injectingRecipeMap.put(GemItems.VOLCANO_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.VOLCANO_GEM_D.get()));

        injectingRecipeMap.put(GemItems.SNOW_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.SNOW_GEM_D.get()));

        injectingRecipeMap.put(GemItems.FIELD_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.FIELD_GEM_D.get()));

        injectingRecipeMap.put(GemItems.MINE_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.MINE_GEM_D.get()));

        injectingRecipeMap.put(GemItems.LIFE_MANA_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.LIFE_MANA_GEM_D.get()));

        injectingRecipeMap.put(GemItems.OBSI_MANA_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.OBSI_MANA_GEM_D.get()));

        injectingRecipeMap.put(GemItems.NETHER_SKELETON_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.NETHER_SKELETON_GEM_D.get()));

        injectingRecipeMap.put(GemItems.MAGMA_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.MAGMA_GEM_D.get()));

        injectingRecipeMap.put(GemItems.WITHER_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.WITHER_GEM_D.get()));

        injectingRecipeMap.put(GemItems.PIGLIN_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.PIGLIN_GEM_D.get()));

        injectingRecipeMap.put(GemItems.SAKURA_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.SAKURA_GEM_D.get()));

        injectingRecipeMap.put(GemItems.SHIP_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.SHIP_GEM_D.get()));

        injectingRecipeMap.put(GemItems.MOON_ATTACK_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.MOON_ATTACK_GEM_D.get()));

        injectingRecipeMap.put(GemItems.MOON_MANA_GEM.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        GemItems.MOON_MANA_GEM_D.get()));

        injectingRecipeMap.put(ModItems.BEACON_BOW.get(),
                new InjectingRecipe(ModItems.BEACON_RUNE.get(), 4,
                        ModItems.BEACON_BOW_1.get()));

        injectingRecipeMap.put(ModItems.BEACON_BOW_1.get(),
                new InjectingRecipe(ModItems.BEACON_RUNE.get(), 6,
                        ModItems.BEACON_BOW_2.get()));

        injectingRecipeMap.put(ModItems.BEACON_BOW_2.get(),
                new InjectingRecipe(ModItems.BEACON_RUNE.get(), 8,
                        ModItems.BEACON_BOW_3.get()));

        injectingRecipeMap.put(ModItems.BLAZE_SWORD.get(),
                new InjectingRecipe(ModItems.BLAZE_RUNE.get(), 4,
                        ModItems.BLAZE_SWORD_1.get()));

        injectingRecipeMap.put(ModItems.BLAZE_SWORD_1.get(),
                new InjectingRecipe(ModItems.BLAZE_RUNE.get(), 6,
                        ModItems.BLAZE_SWORD_2.get()));

        injectingRecipeMap.put(ModItems.BLAZE_SWORD_2.get(),
                new InjectingRecipe(ModItems.BLAZE_RUNE.get(), 8,
                        ModItems.BLAZE_SWORD_3.get()));

        injectingRecipeMap.put(ModItems.TREE_SCEPTRE.get(),
                new InjectingRecipe(ModItems.TREE_RUNE.get(), 4,
                        ModItems.TREE_SCEPTRE_1.get()));

        injectingRecipeMap.put(ModItems.TREE_SCEPTRE_1.get(),
                new InjectingRecipe(ModItems.TREE_RUNE.get(), 6,
                        ModItems.TREE_SCEPTRE_2.get()));

        injectingRecipeMap.put(ModItems.TREE_SCEPTRE_2.get(),
                new InjectingRecipe(ModItems.TREE_RUNE.get(), 8,
                        ModItems.TREE_SCEPTRE_3.get()));

        injectingRecipeMap.put(ModItems.CASTLE_NECKLACE.get(),
                new InjectingRecipe(ModItems.CASTLE_NECKLACE.get(), 1,
                        ModItems.CASTLE_NECKLACE.get()));

        injectingRecipeMap.put(ModItems.RUBY_NECKLACE.get(),
                new InjectingRecipe(ModItems.VOLCANO_RUNE.get(), 8,
                        ModItems.RUBY_NECKLACE_1.get()));

        injectingRecipeMap.put(ModItems.RUBY_NECKLACE_1.get(),
                new InjectingRecipe(ModItems.VOLCANO_RUNE.get(), 16,
                        ModItems.RUBY_NECKLACE_2.get()));

        injectingRecipeMap.put(ModItems.RUBY_NECKLACE_2.get(),
                new InjectingRecipe(ModItems.VOLCANO_RUNE.get(), 24,
                        ModItems.RUBY_NECKLACE_3.get()));

        injectingRecipeMap.put(ModItems.SAPPHIRE_NECKLACE.get(),
                new InjectingRecipe(ModItems.LAKE_RUNE.get(), 8,
                        ModItems.SAPPHIRE_NECKLACE_1.get()));

        injectingRecipeMap.put(ModItems.SAPPHIRE_NECKLACE_1.get(),
                new InjectingRecipe(ModItems.LAKE_RUNE.get(), 16,
                        ModItems.SAPPHIRE_NECKLACE_2.get()));

        injectingRecipeMap.put(ModItems.SAPPHIRE_NECKLACE_2.get(),
                new InjectingRecipe(ModItems.LAKE_RUNE.get(), 24,
                        ModItems.SAPPHIRE_NECKLACE_3.get()));

        injectingRecipeMap.put(ModItems.FANCY_SAPPHIRE_NECKLACE.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 1,
                        ModItems.FANCY_SAPPHIRE_NECKLACE_1.get()));

        injectingRecipeMap.put(ModItems.FANCY_SAPPHIRE_NECKLACE_1.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 2,
                        ModItems.FANCY_SAPPHIRE_NECKLACE_2.get()));

        injectingRecipeMap.put(ModItems.FANCY_SAPPHIRE_NECKLACE_2.get(),
                new InjectingRecipe(ModItems.CONSTRAINT_TABOO.get(), 3,
                        ModItems.FANCY_SAPPHIRE_NECKLACE_3.get()));

        injectingRecipeMap.put(ModItems.PURPLE_IRON_BUD_1.get(),
                new InjectingRecipe(16, ModItems.PURPLE_IRON_INGOT.get(), 4,
                        ModItems.PURPLE_IRON_BUD_2.get()));

        injectingRecipeMap.put(ModItems.PURPLE_IRON_BUD_2.get(),
                new InjectingRecipe(16, ModItems.PURPLE_IRON_INGOT.get(), 16,
                        ModItems.PURPLE_IRON_BUD_3.get()));

        injectingRecipeMap.put(ModItems.PURPLE_IRON_SWORD.get(),
                new InjectingRecipe(ModItems.PURPLE_IRON_BUD_3.get(), 1,
                        ModItems.PURPLE_IRON_SWORD_1.get()));

        injectingRecipeMap.put(ModItems.PURPLE_IRON_SWORD_1.get(),
                new InjectingRecipe(ModItems.PURPLE_IRON_BUD_3.get(), 1,
                        ModItems.PURPLE_IRON_SWORD_2.get()));

        injectingRecipeMap.put(ModItems.PURPLE_IRON_SWORD_2.get(),
                new InjectingRecipe(ModItems.PURPLE_IRON_BUD_3.get(), 1,
                        ModItems.PURPLE_IRON_SWORD_3.get()));

        injectingRecipeMap.put(ModItems.PURPLE_IRON_BOW.get(),
                new InjectingRecipe(ModItems.PURPLE_IRON_BUD_3.get(), 1,
                        ModItems.PURPLE_IRON_BOW_1.get()));

        injectingRecipeMap.put(ModItems.PURPLE_IRON_BOW_1.get(),
                new InjectingRecipe(ModItems.PURPLE_IRON_BUD_3.get(), 1,
                        ModItems.PURPLE_IRON_BOW_2.get()));

        injectingRecipeMap.put(ModItems.PURPLE_IRON_BOW_2.get(),
                new InjectingRecipe(ModItems.PURPLE_IRON_BUD_3.get(), 1,
                        ModItems.PURPLE_IRON_BOW_3.get()));

        injectingRecipeMap.put(ModItems.PURPLE_IRON_SCEPTRE.get(),
                new InjectingRecipe(ModItems.PURPLE_IRON_BUD_3.get(), 1,
                        ModItems.PURPLE_IRON_SCEPTRE_1.get()));

        injectingRecipeMap.put(ModItems.PURPLE_IRON_SCEPTRE_1.get(),
                new InjectingRecipe(ModItems.PURPLE_IRON_BUD_3.get(), 1,
                        ModItems.PURPLE_IRON_SCEPTRE_2.get()));

        injectingRecipeMap.put(ModItems.PURPLE_IRON_SCEPTRE_2.get(),
                new InjectingRecipe(ModItems.PURPLE_IRON_BUD_3.get(), 1,
                        ModItems.PURPLE_IRON_SCEPTRE_3.get()));

        injectingRecipeMap.put(ModItems.STAR_RUNE.get(),
                new InjectingRecipe(8, ModItems.STAR_SOUL.get(), 64,
                        ModItems.STAR_STAR.get()));

        injectingRecipeMap.put(ModItems.MOON_HELMET.get(),
                new InjectingRecipe(ModItems.STAR_STAR.get(), 32,
                        ModItems.STAR_HELMET.get()));

        injectingRecipeMap.put(ModItems.MOON_LEGGINGS.get(),
                new InjectingRecipe(ModItems.STAR_STAR.get(), 32,
                        ModItems.STAR_LEGGINGS.get()));

        injectingRecipeMap.put(ModItems.LIFE_CRYSTAL_0.get(),
                new InjectingRecipe(ModItems.LIFE_ELEMENT_PIECE_2.get(), 1,
                        ModItems.LIFE_CRYSTAL_1.get()));

        injectingRecipeMap.put(ModItems.LIFE_CRYSTAL_1.get(),
                new InjectingRecipe(ModItems.LIFE_ELEMENT_PIECE_2.get(), 2,
                        ModItems.LIFE_CRYSTAL_2.get()));

        injectingRecipeMap.put(ModItems.LIFE_CRYSTAL_2.get(),
                new InjectingRecipe(ModItems.LIFE_ELEMENT_PIECE_2.get(), 3,
                        ModItems.LIFE_CRYSTAL_3.get()));

        injectingRecipeMap.put(ModItems.WATER_CRYSTAL_0.get(),
                new InjectingRecipe(ModItems.WATER_ELEMENT_PIECE_2.get(), 1,
                        ModItems.WATER_CRYSTAL_1.get()));

        injectingRecipeMap.put(ModItems.WATER_CRYSTAL_1.get(),
                new InjectingRecipe(ModItems.WATER_ELEMENT_PIECE_2.get(), 2,
                        ModItems.WATER_CRYSTAL_2.get()));

        injectingRecipeMap.put(ModItems.WATER_CRYSTAL_2.get(),
                new InjectingRecipe(ModItems.WATER_ELEMENT_PIECE_2.get(), 3,
                        ModItems.WATER_CRYSTAL_3.get()));

        injectingRecipeMap.put(ModItems.FIRE_CRYSTAL_0.get(),
                new InjectingRecipe(ModItems.FIRE_ELEMENT_PIECE_2.get(), 1,
                        ModItems.FIRE_CRYSTAL_1.get()));

        injectingRecipeMap.put(ModItems.FIRE_CRYSTAL_1.get(),
                new InjectingRecipe(ModItems.FIRE_ELEMENT_PIECE_2.get(), 2,
                        ModItems.FIRE_CRYSTAL_2.get()));

        injectingRecipeMap.put(ModItems.FIRE_CRYSTAL_2.get(),
                new InjectingRecipe(ModItems.FIRE_ELEMENT_PIECE_2.get(), 3,
                        ModItems.FIRE_CRYSTAL_3.get()));

        injectingRecipeMap.put(ModItems.ICE_CRYSTAL_0.get(),
                new InjectingRecipe(ModItems.ICE_ELEMENT_PIECE_2.get(), 1,
                        ModItems.ICE_CRYSTAL_1.get()));

        injectingRecipeMap.put(ModItems.ICE_CRYSTAL_1.get(),
                new InjectingRecipe(ModItems.ICE_ELEMENT_PIECE_2.get(), 2,
                        ModItems.ICE_CRYSTAL_2.get()));

        injectingRecipeMap.put(ModItems.ICE_CRYSTAL_2.get(),
                new InjectingRecipe(ModItems.ICE_ELEMENT_PIECE_2.get(), 3,
                        ModItems.ICE_CRYSTAL_3.get()));

        injectingRecipeMap.put(ModItems.WIND_CRYSTAL_0.get(),
                new InjectingRecipe(ModItems.WIND_ELEMENT_PIECE_2.get(), 1,
                        ModItems.WIND_CRYSTAL_1.get()));

        injectingRecipeMap.put(ModItems.WIND_CRYSTAL_1.get(),
                new InjectingRecipe(ModItems.WIND_ELEMENT_PIECE_2.get(), 2,
                        ModItems.WIND_CRYSTAL_2.get()));

        injectingRecipeMap.put(ModItems.WIND_CRYSTAL_2.get(),
                new InjectingRecipe(ModItems.WIND_ELEMENT_PIECE_2.get(), 3,
                        ModItems.WIND_CRYSTAL_3.get()));

        injectingRecipeMap.put(ModItems.STONE_CRYSTAL_0.get(),
                new InjectingRecipe(ModItems.STONE_ELEMENT_PIECE_2.get(), 1,
                        ModItems.STONE_CRYSTAL_1.get()));

        injectingRecipeMap.put(ModItems.STONE_CRYSTAL_1.get(),
                new InjectingRecipe(ModItems.STONE_ELEMENT_PIECE_2.get(), 2,
                        ModItems.STONE_CRYSTAL_2.get()));

        injectingRecipeMap.put(ModItems.STONE_CRYSTAL_2.get(),
                new InjectingRecipe(ModItems.STONE_ELEMENT_PIECE_2.get(), 3,
                        ModItems.STONE_CRYSTAL_3.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_CRYSTAL_0.get(),
                new InjectingRecipe(ModItems.LIGHTNING_ELEMENT_PIECE_2.get(), 1,
                        ModItems.LIGHTNING_CRYSTAL_1.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_CRYSTAL_1.get(),
                new InjectingRecipe(ModItems.LIGHTNING_ELEMENT_PIECE_2.get(), 2,
                        ModItems.LIGHTNING_CRYSTAL_2.get()));

        injectingRecipeMap.put(ModItems.LIGHTNING_CRYSTAL_2.get(),
                new InjectingRecipe(ModItems.LIGHTNING_ELEMENT_PIECE_2.get(), 3,
                        ModItems.LIGHTNING_CRYSTAL_3.get()));

        injectingRecipeMap.put(ModItems.EVOKER_SWORD.get(),
                new InjectingRecipe(ModItems.EVOKER_RUNE.get(), 4,
                        ModItems.EVOKER_SWORD_1.get()));

        injectingRecipeMap.put(ModItems.EVOKER_SWORD_1.get(),
                new InjectingRecipe(ModItems.EVOKER_RUNE.get(), 6,
                        ModItems.EVOKER_SWORD_2.get()));

        injectingRecipeMap.put(ModItems.EVOKER_SWORD_2.get(),
                new InjectingRecipe(ModItems.EVOKER_RUNE.get(), 8,
                        ModItems.EVOKER_SWORD_3.get()));

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
                new InjectingRecipe(ModItems.SKY_RUNE.get(), 4, ModItems.SKY_KANATA.get()));
        injectingRecipeMap.put(ModItems.SKY_KANATA.get(),
                new InjectingRecipe(ModItems.NETHER_RUNE.get(), 2, ModItems.NETHER_KANATA.get()));
        injectingRecipeMap.put(ModItems.NETHER_KANATA.get(),
                new InjectingRecipe(ModItems.GOLDEN_SHEET.get(), 1, ModItems.SAKURA_KANATA.get()));

        injectingRecipeMap.put(CitadelItems.CITADEL_CURIO_0.get(),
                new InjectingRecipe(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 1, CitadelItems.CITADEL_CURIO_1.get()));
        injectingRecipeMap.put(CitadelItems.CITADEL_CURIO_1.get(),
                new InjectingRecipe(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 2, CitadelItems.CITADEL_CURIO_2.get()));
        injectingRecipeMap.put(CitadelItems.CITADEL_CURIO_2.get(),
                new InjectingRecipe(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 4, CitadelItems.CITADEL_CURIO_3.get()));

        injectingRecipeMap.put(ModItems.ICE_HELMET.get(),
                new InjectingRecipe(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 8, CitadelItems.CITADEL_HELMET.get()));
        injectingRecipeMap.put(ModItems.ICE_CHEST.get(),
                new InjectingRecipe(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 8, CitadelItems.CITADEL_CHEST.get()));
        injectingRecipeMap.put(ModItems.ICE_LEGGINGS.get(),
                new InjectingRecipe(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 8, CitadelItems.CITADEL_LEGGINGS.get()));
        injectingRecipeMap.put(ModItems.ICE_BOOTS.get(),
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

        injectingRecipeMap.put(ModItems.BLAZE_SWORD_3.get(),
                new InjectingRecipe(DivineIslandItems.GHASTLY_INGOT.get(), 64,
                        DivineIslandItems.GHASTLY_SWORD.get()));
        injectingRecipeMap.put(ModItems.BEACON_BOW_3.get(),
                new InjectingRecipe(DivineIslandItems.GHASTLY_INGOT.get(), 64,
                        DivineIslandItems.GHASTLY_BOW.get()));
        injectingRecipeMap.put(ModItems.TREE_SCEPTRE_3.get(),
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

        injectingRecipeMap.put(ExtraordinaryItems.KANUPUS_WING_F.get(),
                new InjectingRecipe(ExtraordinaryItems.KANUPUS_CORE_E.get(), 1, ExtraordinaryItems.KANUPUS_WING_E.get()));
        injectingRecipeMap.put(ExtraordinaryItems.KANUPUS_WING_E.get(),
                new InjectingRecipe(ExtraordinaryItems.KANUPUS_CORE_D.get(), 1, ExtraordinaryItems.KANUPUS_WING_D.get()));
        injectingRecipeMap.put(ExtraordinaryItems.KANUPUS_WING_D.get(),
                new InjectingRecipe(ExtraordinaryItems.KANUPUS_CORE_C.get(), 1, ExtraordinaryItems.KANUPUS_WING_C.get()));
        injectingRecipeMap.put(ExtraordinaryItems.KANUPUS_WING_C.get(),
                new InjectingRecipe(ExtraordinaryItems.KANUPUS_CORE_B.get(), 1, ExtraordinaryItems.KANUPUS_WING_B.get()));
        injectingRecipeMap.put(ExtraordinaryItems.KANUPUS_WING_B.get(),
                new InjectingRecipe(ExtraordinaryItems.KANUPUS_CORE_A.get(), 1, ExtraordinaryItems.KANUPUS_WING_A.get()));
        injectingRecipeMap.put(ExtraordinaryItems.KANUPUS_WING_A.get(),
                new InjectingRecipe(ExtraordinaryItems.KANUPUS_CORE_Z.get(), 1, ExtraordinaryItems.KANUPUS_WING_Z.get()));

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