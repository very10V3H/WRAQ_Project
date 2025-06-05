package fun.wraq.render.gui.trade.weekly;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.process.system.spur.Items.SpurItems;
import fun.wraq.series.end.citadel.CitadelItems;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.gems.GemItems;
import fun.wraq.series.holy.ice.IceHolyItems;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import fun.wraq.series.instance.series.warden.WardenItems;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.overworld.chapter7.C7Items;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import fun.wraq.series.overworld.extraordinary.ExtraordinaryItems;
import fun.wraq.series.overworld.sakura.bunker.BunkerItems;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WeeklyStorePools {
    private static final List<ItemStack> BASIC_REWARD_LIST = new ArrayList<>();
    public static List<ItemStack> getBasicRewardList() {
        if (BASIC_REWARD_LIST.isEmpty()) {
            BASIC_REWARD_LIST.add(new ItemStack(ModItems.COMPLETE_GEM.get(), 3));
            BASIC_REWARD_LIST.add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
            BASIC_REWARD_LIST.add(new ItemStack(ModItems.WORLD_SOUL_5.get(), 10));
            BASIC_REWARD_LIST.add(new ItemStack(ModItems.NOTE_PAPER.get(), 24));
            BASIC_REWARD_LIST.add(new ItemStack(ModItems.EQUIP_PIECE_5.get(), 1));
            BASIC_REWARD_LIST.add(new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 2));
            BASIC_REWARD_LIST.add(new ItemStack(ModItems.TP_PASS_3DAY.get(), 1));
            BASIC_REWARD_LIST.add(new ItemStack(ModItems.SENIOR_POTION_SUPPLY.get(), 2));
            BASIC_REWARD_LIST.add(new ItemStack(SpurItems.SEA_PIECE_1.get(), 1));
            BASIC_REWARD_LIST.add(new ItemStack(SpurItems.MINE_PIECE_1.get(), 1));
        }
        return BASIC_REWARD_LIST;
    }

    private static final List<ItemStack> SENIOR_REWARD_LIST = new ArrayList<>();
    public static List<ItemStack> getSeniorRewardList() {
        if (SENIOR_REWARD_LIST.isEmpty()) {
            SENIOR_REWARD_LIST.add(new ItemStack(ModItems.WORLD_FORGE_STONE.get(), 2));
            SENIOR_REWARD_LIST.add(new ItemStack(ModItems.FORGE_ENHANCE_3.get(), 2));
            SENIOR_REWARD_LIST.add(new ItemStack(ModItems.RAINBOW_CRYSTAL.get(), 2));
            SENIOR_REWARD_LIST.add(new ItemStack(ModItems.SENIOR_SUPPLY.get(), 2));
            SENIOR_REWARD_LIST.add(new ItemStack(ModItems.WORLD_SOUL_5.get(), 40));
            SENIOR_REWARD_LIST.add(new ItemStack(ModItems.EQUIP_PIECE_6.get(), 1));
            SENIOR_REWARD_LIST.add(new ItemStack(ModItems.ORE_SUPPLY.get(), 1));
        }
        return SENIOR_REWARD_LIST;
    }

    private static final List<ItemStack> SPECIAL_REWARD_LIST = new ArrayList<>();
    public static List<ItemStack> getSpecialRewardList() {
        if (SPECIAL_REWARD_LIST.isEmpty()) {
            SPECIAL_REWARD_LIST.add(new ItemStack(SpecialEventItems.DRAGON_DIAMOND.get()));
            SPECIAL_REWARD_LIST.add(new ItemStack(IceHolyItems.PIECE_CHEST_2.get()));
            SPECIAL_REWARD_LIST.add(new ItemStack(ModItems.SUPPLY_BOX_TIER_2.get()));
            SPECIAL_REWARD_LIST.add(new ItemStack(ModItems.SUPPLY_BOX_TIER_3.get()));
            SPECIAL_REWARD_LIST.add(new ItemStack(ExtraordinaryItems.KANUPUS_SWORD_SOUL.get()));
            SPECIAL_REWARD_LIST.add(new ItemStack(ExtraordinaryItems.SHIRO_BOW_SOUL.get()));
            SPECIAL_REWARD_LIST.add(new ItemStack(ExtraordinaryItems.NETHER_SCEPTRE_EX_SOUL.get()));
            SPECIAL_REWARD_LIST.add(new ItemStack(ExtraordinaryItems.DAZZLING_DIAMOND.get()));
        }
        return SPECIAL_REWARD_LIST;
    }

    private static final List<ItemStack> BASIC_MATERIAL_LIST = new ArrayList<>();
    public static List<ItemStack> getBasicMaterialList() {
        if (BASIC_MATERIAL_LIST.isEmpty()) {
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.PLAIN_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.FOREST_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.LAKE_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.VOLCANO_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.MINE_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.FIELD_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.SNOW_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.SKY_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.EVOKER_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.NETHER_SOUL.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.QUARTZ_SOUL.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.WITHER_RUNE.get(), 4));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.PIGLIN_RUNE.get(), 4));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.NETHER_SKELETON_RUNE.get(), 4));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.MAGMA_RUNE.get(), 4));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.SEA_RUNE.get(), 6));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.LIGHTNING_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 2));
            BASIC_MATERIAL_LIST.add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 2));
            BASIC_MATERIAL_LIST.add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 2));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.HUSK_RUNE.get(), 6));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.BIG_SLIME_BALL.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.SPIDER_RUNE.get(), 1));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.PURPLE_IRON_INGOT.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.EARTH_MANA_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.BLOOD_MANA_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.STAR_STAR.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.END_CRYSTAL.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.BEACON_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.TREE_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.BLAZE_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(C7Items.VD_SOUL_POCKET.get(), 1));
            BASIC_MATERIAL_LIST.add(new ItemStack(MoontainItems.SOUL_FRAGMENT_POCKET.get(), 1));
            BASIC_MATERIAL_LIST.add(new ItemStack(MoontainItems.LEATHER_POCKET.get(), 1));
            BASIC_MATERIAL_LIST.add(new ItemStack(MoontainItems.FALLING_SOUL_POCKET.get(), 1));
            BASIC_MATERIAL_LIST.add(new ItemStack(BunkerItems.BUNKER_RUNE.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.SAKURA_PETAL_POCKET.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.SHIP_PIECE_POCKET.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(ModItems.WOLF_LEATHER_POCKET.get(), 8));
            BASIC_MATERIAL_LIST.add(new ItemStack(MushroomItems.BROWN_MUSHROOM_POCKET.get(), 8));
        }
        return BASIC_MATERIAL_LIST;
    }

    private static final List<ItemStack> SENIOR_MATERIAL_LIST = new ArrayList<>();
    public static List<ItemStack> getSeniorMaterialList() {
        if (SENIOR_MATERIAL_LIST.isEmpty()) {
            SENIOR_MATERIAL_LIST.add(new ItemStack(ModItems.PLAIN_BOSS_SOUL.get(), 16));
            SENIOR_MATERIAL_LIST.add(new ItemStack(ModItems.NETHER_IMPRINT.get(), 4));
            SENIOR_MATERIAL_LIST.add(new ItemStack(ModItems.PURPLE_IRON_BUD_1.get(), 16));
            SENIOR_MATERIAL_LIST.add(new ItemStack(ModItems.ICE_HEART.get()));
            SENIOR_MATERIAL_LIST.add(new ItemStack(ModItems.Boss2Piece.get(), 16));
            SENIOR_MATERIAL_LIST.add(new ItemStack(ModItems.TABOO_PIECE.get(), 16));
            SENIOR_MATERIAL_LIST.add(new ItemStack(ModItems.MOON_SOUL.get(), 16));
            SENIOR_MATERIAL_LIST.add(new ItemStack(MoontainItems.FRAGMENT.get(), 16));
            SENIOR_MATERIAL_LIST.add(new ItemStack(MoontainItems.NUGGET.get(), 16));
            SENIOR_MATERIAL_LIST.add(new ItemStack(ModItems.CASTLE_PIECE.get(), 16));
            SENIOR_MATERIAL_LIST.add(new ItemStack(HarbingerItems.HARBINGER_INGOT.get(), 16));
            SENIOR_MATERIAL_LIST.add(new ItemStack(MushroomItems.RED_MUSHROOM.get(), 16));
            SENIOR_MATERIAL_LIST.add(new ItemStack(CitadelItems.CITADEL_EQUIP_ENHANCER.get()));
            SENIOR_MATERIAL_LIST.add(new ItemStack(WardenItems.WARDEN_SOUL_INGOT.get(), 4));
            SENIOR_MATERIAL_LIST.add(new ItemStack(BunkerItems.BUNKER_BOSS_SOUL.get(), 8));
            SENIOR_MATERIAL_LIST.add(new ItemStack(DivineIslandItems.DIVINE_BOSS_SOUL.get(), 8));
            SENIOR_MATERIAL_LIST.add(new ItemStack(DivineIslandItems.DIVINE_BALANCE_STAR.get(), 8));
        }
        return SENIOR_MATERIAL_LIST;
    }

    private static final List<ItemStack> SPECIAL_MATERIAL_LIST = new ArrayList<>();
    public static List<ItemStack> getSpecialMaterialList() {
        if (SPECIAL_MATERIAL_LIST.isEmpty()) {
            SPECIAL_MATERIAL_LIST.add(new ItemStack(GemItems.MOON_ATTACK_GEM.get(), 2));
            SPECIAL_MATERIAL_LIST.add(new ItemStack(GemItems.MOON_MANA_GEM.get(), 2));
            SPECIAL_MATERIAL_LIST.add(new ItemStack(NewRuneItems.CASTLE_NEW_RUNE.get(), 2));
            SPECIAL_MATERIAL_LIST.add(new ItemStack(HarbingerItems.SAKURA_INDUSTRY_INGOT.get(), 2));
            SPECIAL_MATERIAL_LIST.add(new ItemStack(MushroomItems.NETHER_MUSHROOM.get(), 2));
        }
        return SPECIAL_MATERIAL_LIST;
    }
}
