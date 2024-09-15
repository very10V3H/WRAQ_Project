package com.very.wraq.series.specialevents;

import com.very.wraq.commands.changeable.PrefixPaperItem;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.FoiledMaterialItem;
import com.very.wraq.series.MaterialItem;
import com.very.wraq.series.specialevents.midautumn.MidAutumnBow;
import com.very.wraq.series.specialevents.midautumn.MidAutumnSceptre;
import com.very.wraq.series.specialevents.midautumn.MidAutumnSword;
import com.very.wraq.series.specialevents.midautumn.MoonFeather;
import com.very.wraq.series.specialevents.summer.SummerCurios2024;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SpecialEventItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> SUMMER_VOUCHER = ITEMS.register("summer_voucher", () ->
            new MaterialItem(new Item.Properties().rarity(CustomStyle.WaterBold)));

    public static final RegistryObject<Item> SUMMER_CURIOS0 = ITEMS.register("summer24_curios0", () ->
            new SummerCurios2024(new Item.Properties().rarity(CustomStyle.WaterBold), 0));

    public static final RegistryObject<Item> SUMMER_CURIOS1 = ITEMS.register("summer24_curios1", () ->
            new SummerCurios2024(new Item.Properties().rarity(CustomStyle.WaterBold), 1));

    public static final RegistryObject<Item> SUMMER_CURIOS2 = ITEMS.register("summer24_curios2", () ->
            new SummerCurios2024(new Item.Properties().rarity(CustomStyle.WaterBold), 2));

    public static final RegistryObject<Item> SUMMER_CURIOS3 = ITEMS.register("summer24_curios3", () ->
            new SummerCurios2024(new Item.Properties().rarity(CustomStyle.WaterBold), 3));

    public static final RegistryObject<Item> SUMMER_CURIOS4 = ITEMS.register("summer24_curios4", () ->
            new SummerCurios2024(new Item.Properties().rarity(CustomStyle.WaterBold), 4));

    public static final RegistryObject<Item> SUMMER_CURIOS5 = ITEMS.register("summer24_curios5", () ->
            new SummerCurios2024(new Item.Properties().rarity(CustomStyle.WaterBold), 5));

    public static final RegistryObject<Item> MID_AUTUMN_SOUL = ITEMS.register("mid_autumn_soul", () ->
            new FoiledMaterialItem(new Item.Properties().rarity(CustomStyle.MoonBold)));

    public static final RegistryObject<Item> MOON_FEATHER_0 = ITEMS.register("moon_feather_0", () ->
            new MoonFeather(new Item.Properties().rarity(CustomStyle.MoonBold), 0));

    public static final RegistryObject<Item> MOON_FEATHER_1 = ITEMS.register("moon_feather_1", () ->
            new MoonFeather(new Item.Properties().rarity(CustomStyle.MoonBold), 1));

    public static final RegistryObject<Item> MOON_FEATHER_2 = ITEMS.register("moon_feather_2", () ->
            new MoonFeather(new Item.Properties().rarity(CustomStyle.MoonBold), 2));

    public static final RegistryObject<Item> MOON_FEATHER_3 = ITEMS.register("moon_feather_3", () ->
            new MoonFeather(new Item.Properties().rarity(CustomStyle.MoonBold), 3));

    public static final RegistryObject<Item> MID_AUTUMN_SWORD = ITEMS.register("mid_autumn_sword", () ->
            new MidAutumnSword(new Item.Properties().rarity(CustomStyle.MoonBold), 0.6));

    public static final RegistryObject<Item> MID_AUTUMN_BOW = ITEMS.register("mid_autumn_bow", () ->
            new MidAutumnBow(new Item.Properties().rarity(CustomStyle.MoonBold), 3));

    public static final RegistryObject<Item> MID_AUTUMN_SCEPTRE = ITEMS.register("mid_autumn_sceptre", () ->
            new MidAutumnSceptre(new Item.Properties().rarity(CustomStyle.MoonBold), 1.2));

    public static final RegistryObject<Item> MID_AUTUMN_PREFIX = ITEMS.register("mid_autumn_prefix", () ->
            new PrefixPaperItem(new Item.Properties().rarity(CustomStyle.MoonBold),
                    "midAutumnPrefix", "月荧风清", CustomStyle.styleOfMoon));
}
