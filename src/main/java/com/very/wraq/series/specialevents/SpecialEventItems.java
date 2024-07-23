package com.very.wraq.series.specialevents;

import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.MaterialItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SpecialEventItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> SUMMER_VOUCHER = ITEMS.register("summer_voucher", () ->
            new MaterialItem(new Item.Properties().rarity(CustomStyle.WaterBold)));
}
