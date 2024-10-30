package fun.wraq.process.system.ore;

import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OreItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> WRAQ_ORE_1_ITEM = ITEMS.register("wraq_ore_1_item",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Plain), true, true));

    public static final RegistryObject<Item> WRAQ_ORE_2_ITEM = ITEMS.register("wraq_ore_2_item",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Ice), true, true));

    public static final RegistryObject<Item> WRAQ_ORE_3_ITEM = ITEMS.register("wraq_ore_3_item",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Sakura), true, true));

    public static final RegistryObject<Item> WRAQ_ORE_4_ITEM = ITEMS.register("wraq_ore_4_item",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Magma), true, true));

    public static final RegistryObject<Item> MOONTAIN_ORE_ITEM = ITEMS.register("moontain_ore_item",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MOONTAIN), true, true));
}
