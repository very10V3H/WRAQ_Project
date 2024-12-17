package fun.wraq.process.system.endlessinstance.item;

import fun.wraq.common.util.Utils;
import fun.wraq.process.system.endlessinstance.item.instance.eastern.EasternTowerPaper;
import fun.wraq.process.system.endlessinstance.item.instance.plain.ManaPlainPlant;
import fun.wraq.process.system.endlessinstance.item.special.HoursExHarvestPotion;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EndlessInstanceItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> MANA_PLAIN_PLANT = ITEMS.register("mana_plain_plant",
            () -> new ManaPlainPlant(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> EASTERN_TOWER_PAPER = ITEMS.register("eastern_tower_paper",
            () -> new EasternTowerPaper(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> ENDLESS_INSTANCE_CORE = ITEMS.register("endless_instance_core",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.World), true, true));

    public static final RegistryObject<Item> HOURS_EX_HARVEST_POTION = ITEMS.register("hours_ex_harvest_potion",
            () -> new HoursExHarvestPotion(new Item.Properties().rarity(CustomStyle.GoldBold), 3));
}
