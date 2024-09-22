package com.very.wraq.series.moontain;

import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.moontain.equip.weapon.MoontainSword;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MoontainItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> SWORD = ITEMS.register("moontain_sword",
            () -> new MoontainSword(new Item.Properties().rarity(CustomStyle.MOONTAIN_ITALIC)));
}
