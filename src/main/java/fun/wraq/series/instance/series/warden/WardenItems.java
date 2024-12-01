package fun.wraq.series.instance.series.warden;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.warden.offhand.WardenBook;
import fun.wraq.series.instance.series.warden.offhand.WardenKnife;
import fun.wraq.series.instance.series.warden.offhand.WardenShield;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WardenItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> WARDEN_MATRIX = ITEMS.register("warden_matrix",
            () -> new WardenMatrix(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD)));

    public static final RegistryObject<Item> WARDEN_SHIELD = ITEMS.register("warden_shield",
            () -> new WardenShield(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD),
                    Te.s("盾牌", CustomStyle.styleOfMine)));

    public static final RegistryObject<Item> WARDEN_KNIFE = ITEMS.register("warden_knife",
            () -> new WardenKnife(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD),
                    Te.s("匕首", ChatFormatting.AQUA)));

    public static final RegistryObject<Item> WARDEN_BOOK = ITEMS.register("warden_book",
            () -> new WardenBook(new Item.Properties().rarity(CustomStyle.WARDEN_BOLD),
                    Te.s("魔导书", CustomStyle.styleOfMana)));
}
