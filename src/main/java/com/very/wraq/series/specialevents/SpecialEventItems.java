package com.very.wraq.series.specialevents;

import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.MaterialItem;
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
}
