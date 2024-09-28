package fun.wraq.process.system.enhanceForge;

import fun.wraq.common.registry.ModItems;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ForgeMaterials {
    public static List<Item> chapter1 = new ArrayList<>();

    public static void setChapter1() {
        Item[] items = {
                ModItems.PlainSoul.get(),
                ModItems.ForestSoul.get(),
                ModItems.LakeSoul.get(),
                ModItems.VolcanoSoul.get(),
                ModItems.MineSoul.get(),
                /*ModItems.FieldSoul.get(),*/
                ModItems.SnowSoul.get()
        };
        chapter1.addAll(List.of(items));
    }

    public static List<Item> chapter2 = new ArrayList<>();

    public static void setChapter2() {
        Item[] items = {
                ModItems.SkySoul.get(),
                ModItems.EvokerSoul.get(),
                ModItems.SeaSoul.get(),
                ModItems.huskSoul.get(),
                ModItems.KazeSoul.get(),
                /*ModItems.SpiderSoul.get(),*/
                ModItems.LightningSoul.get(),
                /*ModItems.SlimeBall.get()*/
        };
        chapter2.addAll(List.of(items));
    }

    public static List<Item> chapter3 = new ArrayList<>();

    public static void setChapter3() {
        Item[] items = {
                ModItems.witherSkeletonSoul.get(),
                ModItems.PigLinSoul.get(),
                ModItems.netherSkeletonSoul.get(),
                ModItems.magmaSoul.get()
        };
        chapter3.addAll(List.of(items));
    }

    public static List<Item> chapter4 = new ArrayList<>();

    public static void setChapter4() {
        Item[] items = {
                /*ModItems.toEnd.get(),*/
                ModItems.RecallPiece.get(),
                ModItems.ShulkerSoul.get(),
                ModItems.EnderMiteSoul.get()
        };
        chapter4.addAll(List.of(items));
    }

    public static List<Item> chapter5 = new ArrayList<>();

    public static void setChapter5() {
        Item[] items = {
                ModItems.SakuraPetal.get(),
                /*ModItems.Wheat.get(),*/
                ModItems.PurpleIronPiece.get(),
                ModItems.EarthManaSoul.get(),
                ModItems.BloodManaSoul.get()
        };
        chapter5.addAll(List.of(items));
    }

    public static List<Item> chapter6 = new ArrayList<>();

    public static void setChapter6() {
        Item[] items = {
                ModItems.PlainBossSoul.get(),
                /*ModItems.Main1Crystal.get(),*/
                /*ModItems.Boss2Piece.get(),*/
                ModItems.IceSoul.get(),
                /*ModItems.DevilBlood.get(),*/
                ModItems.MoonSoul.get(),
                ModItems.TabooPiece.get(),
                ModItems.CastlePiece.get(),
                /*ModItems.CastleSoul.get(),*/
                ModItems.PurpleIronBud1.get()
        };
        chapter6.addAll(List.of(items));
    }

}
