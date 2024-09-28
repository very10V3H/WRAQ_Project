package fun.wraq.process.system.endlessinstance;

import fun.wraq.common.util.Utils;
import fun.wraq.process.system.endlessinstance.item.EasternTowerPaper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EndlessInstanceItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> EASTERN_TOWER_PAPER = ITEMS.register("eastern_tower_paper",
            () -> new EasternTowerPaper(new Item.Properties().rarity(Rarity.EPIC)));
}
