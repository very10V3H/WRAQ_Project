package fun.wraq.series.specialevents;

import fun.wraq.commands.changeable.PrefixPaperItem;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.specialevents.midautumn.MidAutumnBow;
import fun.wraq.series.specialevents.midautumn.MidAutumnSceptre;
import fun.wraq.series.specialevents.midautumn.MidAutumnSword;
import fun.wraq.series.specialevents.midautumn.MoonFeather;
import fun.wraq.series.specialevents.summer.SummerCuriosOrEquip2024;
import fun.wraq.series.specialevents.train.TrainSouvenirs;
import fun.wraq.series.specialevents.year2024.Souvenirs2024;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SpecialEventItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> SUMMER_VOUCHER = ITEMS.register("summer_voucher", () ->
            new WraqItem(new Item.Properties().rarity(CustomStyle.WaterBold)));

    public static final RegistryObject<Item> SUMMER_CURIOS0 = ITEMS.register("summer24_curios0", () ->
            new SummerCuriosOrEquip2024(new Item.Properties().rarity(CustomStyle.WaterBold), 0));

    public static final RegistryObject<Item> SUMMER_CURIOS1 = ITEMS.register("summer24_curios1", () ->
            new SummerCuriosOrEquip2024(new Item.Properties().rarity(CustomStyle.WaterBold), 1));

    public static final RegistryObject<Item> SUMMER_CURIOS2 = ITEMS.register("summer24_curios2", () ->
            new SummerCuriosOrEquip2024(new Item.Properties().rarity(CustomStyle.WaterBold), 2));

    public static final RegistryObject<Item> SUMMER_CURIOS3 = ITEMS.register("summer24_curios3", () ->
            new SummerCuriosOrEquip2024(new Item.Properties().rarity(CustomStyle.WaterBold), 3));

    public static final RegistryObject<Item> SUMMER_CURIOS4 = ITEMS.register("summer24_curios4", () ->
            new SummerCuriosOrEquip2024(new Item.Properties().rarity(CustomStyle.WaterBold), 4));

    public static final RegistryObject<Item> SUMMER_CURIOS5 = ITEMS.register("summer24_curios5", () ->
            new SummerCuriosOrEquip2024(new Item.Properties().rarity(CustomStyle.WaterBold), 5));

    public static final RegistryObject<Item> MID_AUTUMN_SOUL = ITEMS.register("mid_autumn_soul", () ->
            new WraqItem(new Item.Properties().rarity(CustomStyle.MoonBold), false, true));

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
            new MidAutumnBow(new Item.Properties().rarity(CustomStyle.MoonBold), 0.6));

    public static final RegistryObject<Item> MID_AUTUMN_SCEPTRE = ITEMS.register("mid_autumn_sceptre", () ->
            new MidAutumnSceptre(new Item.Properties().rarity(CustomStyle.MoonBold), 1.2));

    public static final RegistryObject<Item> MID_AUTUMN_PREFIX = ITEMS.register("mid_autumn_prefix", () ->
            new PrefixPaperItem(new Item.Properties().rarity(CustomStyle.MoonBold),
                    "midAutumnPrefix", "月荧风清", CustomStyle.styleOfMoon));

    public static final RegistryObject<Item> TRAIN_SOUVENIRS = ITEMS.register("train_souvenirs", () ->
            new TrainSouvenirs(new Item.Properties().rarity(CustomStyle.FieldBold)));

    public static final RegistryObject<Item> SOUVENIRS_2024 = ITEMS.register("souvenirs_2024", () ->
            new Souvenirs2024(new Item.Properties().rarity(CustomStyle.GoldBold)));
}
