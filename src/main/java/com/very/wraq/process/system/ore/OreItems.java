package com.very.wraq.process.system.ore;

import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SimpleFoiledItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OreItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> WRAQ_ORE_1_ITEM = ITEMS.register("wraq_ore_1_item",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Plain)));

    public static final RegistryObject<Item> WRAQ_ORE_2_ITEM = ITEMS.register("wraq_ore_2_item",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Ice)));

    public static final RegistryObject<Item> WRAQ_ORE_3_ITEM = ITEMS.register("wraq_ore_3_item",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Sakura)));

    public static final RegistryObject<Item> WRAQ_ORE_4_ITEM = ITEMS.register("wraq_ore_4_item",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Magma)));
}
