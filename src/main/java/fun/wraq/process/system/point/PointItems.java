package fun.wraq.process.system.point;

import fun.wraq.common.util.Utils;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PointItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> EXPT = ITEMS.register("expt",
            () -> new PointItem(Point.EXPT));

    public static final RegistryObject<Item> DSPT = ITEMS.register("dspt",
            () -> new PointItem(Point.DSPT));

    public static final RegistryObject<Item> ELPT = ITEMS.register("elpt",
            () -> new PointItem(Point.ELPT));

    public static final RegistryObject<Item> SKPT = ITEMS.register("skpt",
            () -> new PointItem(Point.SKPT));

    public static final RegistryObject<Item> NTPT = ITEMS.register("ntpt",
            () -> new PointItem(Point.NTPT));

    public static final RegistryObject<Item> EDPT = ITEMS.register("edpt",
            () -> new PointItem(Point.EDPT));

    public static final RegistryObject<Item> BCPT = ITEMS.register("bcpt",
            () -> new PointItem(Point.BCPT));

    public static final RegistryObject<Item> MTPT = ITEMS.register("mtpt",
            () -> new PointItem(Point.MTPT));

    public static final RegistryObject<Item> OCPT = ITEMS.register("ocpt",
            () -> new PointItem(Point.OCPT));

    public static final RegistryObject<Item> NOPT = ITEMS.register("nopt",
            () -> new PointItem(Point.NOPT));

    public static final RegistryObject<Item> ATPT = ITEMS.register("atpt",
            () -> new PointItem(Point.ATPT));
}
