package fun.wraq.series.overworld.cold.sc2;

import fun.wraq.common.util.Utils;
import fun.wraq.series.overworld.cold.sc2.stray.SuperColdFlower;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SuperColdItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> FLOWER = ITEMS.register("super_cold_flower",
            () -> new SuperColdFlower(new Item.Properties().rarity(Rarity.RARE)));
}
