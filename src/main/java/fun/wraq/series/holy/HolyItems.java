package fun.wraq.series.holy;

import fun.wraq.common.util.Utils;
import fun.wraq.series.WraqItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HolyItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> HOLY_CHEST_KEY = ITEMS.register("holy_chest_key",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.RARE), false, true));
}
