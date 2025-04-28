package fun.wraq.series.overworld.mt.curio;

import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ManaTowerItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> PIECE = ITEMS.register("mana_tower_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), true, true));

    public static final RegistryObject<Item> RUNE = ITEMS.register("mana_tower_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY), true, true));

    public static final RegistryObject<Item> NAN_HAI = ITEMS.register("nan_hai",
            () -> new NanHai(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY)));

    public static final RegistryObject<Item> TIAN_SHOU = ITEMS.register("tian_shou",
            () -> new TianShou(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY)));

    public static final RegistryObject<Item> TONG_TIAN = ITEMS.register("tong_tian",
            () -> new TongTian(new Item.Properties().rarity(CustomStyle.MANA_TOWER_BOLD_RARITY)));
}
