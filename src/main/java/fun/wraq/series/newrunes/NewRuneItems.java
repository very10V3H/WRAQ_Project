package fun.wraq.series.newrunes;

import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.end.runes.EndRune;
import fun.wraq.series.newrunes.chapter1.*;
import fun.wraq.series.newrunes.chapter2.*;
import fun.wraq.series.newrunes.chapter3.NetherNewRune;
import fun.wraq.series.newrunes.chapter6.CastleNewRune;
import fun.wraq.series.newrunes.chapter6.MoonNewRune;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SimpleFoiledItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NewRuneItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> emptyRune = ITEMS.register("empty_rune",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> plainNewRune = ITEMS.register("plain_curios",
            () -> new PlainNewRune(new Item.Properties().rarity(CustomStyle.PlainBold)));

    public static final RegistryObject<Item> forestNewRune = ITEMS.register("forest_curios",
            () -> new ForestNewRune(new Item.Properties().rarity(CustomStyle.ForestBold)));

    public static final RegistryObject<Item> lakeNewRune = ITEMS.register("lake_curios",
            () -> new LakeNewRune(new Item.Properties().rarity(CustomStyle.LakeBold)));

    public static final RegistryObject<Item> volcanoNewRune = ITEMS.register("volcano_curios",
            () -> new VolcanoNewRune(new Item.Properties().rarity(CustomStyle.VolcanoBold)));

    public static final RegistryObject<Item> endNewRune = ITEMS.register("end_rune",
            () -> new EndRune(new Item.Properties().rarity(CustomStyle.EndBold)));

    public static final RegistryObject<Item> evokerNewRune = ITEMS.register("evoker_new_rune",
            () -> new EvokerNewRune(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> mineNewRune = ITEMS.register("mine_new_rune",
            () -> new MineNewRune(new Item.Properties().rarity(CustomStyle.MineBold)));

    public static final RegistryObject<Item> huskNewRune = ITEMS.register("husk_new_rune",
            () -> new HuskNewRune(new Item.Properties().rarity(CustomStyle.HuskBold)));

    public static final RegistryObject<Item> lightningNewRune = ITEMS.register("lightning_new_rune",
            () -> new LightningNewRune(new Item.Properties().rarity(CustomStyle.LightningBold)));

    public static final RegistryObject<Item> netherNewRune = ITEMS.register("nether_new_rune",
            () -> new NetherNewRune(new Item.Properties().rarity(CustomStyle.NetherBold)));

    public static final RegistryObject<Item> castleNewRune = ITEMS.register("castle_new_rune",
            () -> new CastleNewRune(new Item.Properties().rarity(CustomStyle.CastleBold)));

    public static final RegistryObject<Item> kazeNewRune = ITEMS.register("kaze_new_rune",
            () -> new KazeNewRune(new Item.Properties().rarity(CustomStyle.WindBold)));

    public static final RegistryObject<Item> skyNewRune = ITEMS.register("sky_new_rune",
            () -> new SkyNewRune(new Item.Properties().rarity(CustomStyle.SkyBold)));

    public static final RegistryObject<Item> moonNewRune = ITEMS.register("moon_new_rune",
            () -> new MoonNewRune(new Item.Properties().rarity(CustomStyle.MoonBold)));
}
