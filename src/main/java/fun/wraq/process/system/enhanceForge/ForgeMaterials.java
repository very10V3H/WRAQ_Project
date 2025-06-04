package fun.wraq.process.system.enhanceForge;

import fun.wraq.common.registry.ModItems;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ForgeMaterials {
    public static List<Item> chapter1 = new ArrayList<>();

    public static void setChapter1() {
        Item[] items = {
                ModItems.PLAIN_SOUL.get(),
                ModItems.FOREST_SOUL.get(),
                ModItems.LAKE_SOUL.get(),
                ModItems.VOLCANO_SOUL.get(),
                ModItems.MINE_SOUL.get(),
                ModItems.SNOW_SOUL.get()
        };
        chapter1.addAll(List.of(items));
    }

    public static List<Item> chapter2 = new ArrayList<>();

    public static void setChapter2() {
        Item[] items = {
                ModItems.SKY_SOUL.get(),
                ModItems.EVOKER_SOUL.get(),
                ModItems.SEA_SOUL.get(),
                ModItems.HUSK_SOUL.get(),
                ModItems.KAZE_SOUL.get(),
                ModItems.LIGHTNING_SOUL.get(),
        };
        chapter2.addAll(List.of(items));
    }

    public static List<Item> chapter3 = new ArrayList<>();

    public static void setChapter3() {
        Item[] items = {
                ModItems.WITHER_SKELETON_SOUL.get(),
                ModItems.PIGLIN_SOUL.get(),
                ModItems.NETHER_SKELETON_SOUL.get(),
                ModItems.MAGMA_SOUL.get()
        };
        chapter3.addAll(List.of(items));
    }

    public static List<Item> chapter4 = new ArrayList<>();

    public static void setChapter4() {
        Item[] items = {
                /*ModItems.toEnd.get(),*/
                ModItems.RECALL_PIECE.get(),
                ModItems.SHULKER_SOUL.get(),
                ModItems.ENDER_MITE_SOUL.get()
        };
        chapter4.addAll(List.of(items));
    }

    public static List<Item> chapter5 = new ArrayList<>();

    public static void setChapter5() {
        Item[] items = {
                ModItems.SAKURA_PETAL.get(),
                /*ModItems.Wheat.get(),*/
                ModItems.PURPLE_IRON_PIECE.get(),
                ModItems.EARTH_MANA_SOUL.get(),
                ModItems.BLOOD_MANA_SOUL.get()
        };
        chapter5.addAll(List.of(items));
    }

    public static List<Item> chapter6 = new ArrayList<>();

    public static void setChapter6() {
        Item[] items = {
                ModItems.PLAIN_BOSS_SOUL.get(),
                /*ModItems.Main1Crystal.get(),*/
                /*ModItems.Boss2Piece.get(),*/
                ModItems.ICE_BOSS_SOUL.get(),
                /*ModItems.DevilBlood.get(),*/
                ModItems.MOON_SOUL.get(),
                ModItems.TABOO_PIECE.get(),
                ModItems.CASTLE_PIECE.get(),
                /*ModItems.CastleSoul.get(),*/
                ModItems.PURPLE_IRON_BUD_1.get()
        };
        chapter6.addAll(List.of(items));
    }

}
