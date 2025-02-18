package fun.wraq.series.overworld.sun;

import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SunIslandItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> TEAR_CURIO_0 = ITEMS.register("tear_curio_0",
            () -> new TearCurio(new Item.Properties().rarity(CustomStyle.WaterBold), 0));
    public static final RegistryObject<Item> TEAR_CURIO_1 = ITEMS.register("tear_curio_1",
            () -> new TearCurio(new Item.Properties().rarity(CustomStyle.WaterBold), 1));
    public static final RegistryObject<Item> TEAR_CURIO_2 = ITEMS.register("tear_curio_2",
            () -> new TearCurio(new Item.Properties().rarity(CustomStyle.WaterBold), 2));
    public static final RegistryObject<Item> TEAR_CURIO_3 = ITEMS.register("tear_curio_3",
            () -> new TearCurio(new Item.Properties().rarity(CustomStyle.WaterBold), 3));

    public static final RegistryObject<Item> BROKEN_BLADE_0 = ITEMS.register("broken_blade_0",
            () -> new BrokenBlade(new Item.Properties().rarity(CustomStyle.GoldBold), 0));
    public static final RegistryObject<Item> BROKEN_BLADE_1 = ITEMS.register("broken_blade_1",
            () -> new BrokenBlade(new Item.Properties().rarity(CustomStyle.GoldBold), 1));
    public static final RegistryObject<Item> BROKEN_BLADE_2 = ITEMS.register("broken_blade_2",
            () -> new BrokenBlade(new Item.Properties().rarity(CustomStyle.GoldBold), 2));
    public static final RegistryObject<Item> BROKEN_BLADE_3 = ITEMS.register("broken_blade_3",
            () -> new BrokenBlade(new Item.Properties().rarity(CustomStyle.GoldBold), 3));

    public static final RegistryObject<Item> FRAME_ARROW_0 = ITEMS.register("frame_arrow_0",
            () -> new FrameArrow(new Item.Properties().rarity(CustomStyle.RedBold), 0));
    public static final RegistryObject<Item> FRAME_ARROW_1 = ITEMS.register("frame_arrow_1",
            () -> new FrameArrow(new Item.Properties().rarity(CustomStyle.RedBold), 1));
    public static final RegistryObject<Item> FRAME_ARROW_2 = ITEMS.register("frame_arrow_2",
            () -> new FrameArrow(new Item.Properties().rarity(CustomStyle.RedBold), 2));
    public static final RegistryObject<Item> FRAME_ARROW_3 = ITEMS.register("frame_arrow_3",
            () -> new FrameArrow(new Item.Properties().rarity(CustomStyle.RedBold), 3));

    public static final RegistryObject<Item> DEVIL_POWER_CURIO = ITEMS.register("devil_power_curio",
            () -> new DevilPowerCurio(new Item.Properties().rarity(CustomStyle.RedBold)));

    public static final RegistryObject<Item> TABOO_PAPER_CURIO = ITEMS.register("taboo_paper_curio",
            () -> new TabooPaper(new Item.Properties().rarity(CustomStyle.demonBold)));
}
