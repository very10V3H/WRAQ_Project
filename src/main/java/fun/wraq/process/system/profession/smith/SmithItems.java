package fun.wraq.process.system.profession.smith;

import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SmithItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> SMITH_STONE = ITEMS.register("smith_stone",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Stone)));

    public static final RegistryObject<Item> STONE_HAMMER = ITEMS.register("smith_stone_hammer",
            () -> new SmithHammer(new Item.Properties().rarity(CustomStyle.MineBold), 1));

    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("smith_iron_hammer",
            () -> new SmithHammer(new Item.Properties().rarity(CustomStyle.SnowBold), 2));

    public static final RegistryObject<Item> COPPER_HAMMER = ITEMS.register("smith_copper_hammer",
            () -> new SmithHammer(new Item.Properties().rarity(CustomStyle.CopperBold), 3));

    public static final RegistryObject<Item> GOLDEN_HAMMER = ITEMS.register("smith_golden_hammer",
            () -> new SmithHammer(new Item.Properties().rarity(CustomStyle.GoldBold), 4));

    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("smith_diamond_hammer",
            () -> new SmithHammer(new Item.Properties().rarity(CustomStyle.IceBold), 5));

    public static final RegistryObject<Item> EMERALD_HAMMER = ITEMS.register("smith_emerald_hammer",
            () -> new SmithHammer(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD), 6));

    public static final RegistryObject<Item> NETHER_HAMMER = ITEMS.register("smith_nether_hammer",
            () -> new SmithHammer(new Item.Properties().rarity(CustomStyle.NetherBold), 7));

    public static final RegistryObject<Item> END_HAMMER = ITEMS.register("smith_end_hammer",
            () -> new SmithHammer(new Item.Properties().rarity(CustomStyle.EndBold), 8));
}
