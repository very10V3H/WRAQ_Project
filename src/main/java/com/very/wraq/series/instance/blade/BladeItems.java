package com.very.wraq.series.instance.blade;

import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BladeItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> BLADE = ITEMS.register("blade",
            () -> new WraqBlade(new Item.Properties().stacksTo(1).rarity(CustomStyle.WorldItalic)
                    , CustomStyle.styleOfWorld
                    , ComponentUtils.getSuffixOfWorldSoul()
                    , 1.5, 0));

    public static final RegistryObject<Item> BLADE_PLAIN = ITEMS.register("blade_plain",
            () -> new WraqBlade(new Item.Properties().stacksTo(1).rarity(CustomStyle.PlainItalic)
                    , CustomStyle.styleOfPlain
                    , ComponentUtils.getSuffixOfPlainBoss()
                    , 1.75, 50));

    public static final RegistryObject<Item> BLADE_NETHER = ITEMS.register("blade_nether",
            () -> new WraqBlade(new Item.Properties().stacksTo(1).rarity(CustomStyle.NetherItalic)
                    , CustomStyle.styleOfPower
                    , ComponentUtils.getSuffixOfIgniteRevenant()
                    , 2, 90));

    public static final RegistryObject<Item> BLADE_PURPLE = ITEMS.register("blade_purple",
            () -> new WraqBlade(new Item.Properties().stacksTo(1).rarity(CustomStyle.PurpleIronItalic)
                    , CustomStyle.styleOfPurpleIron
                    , ComponentUtils.getSuffixOfPurpleIron()
                    , 2.25, 120));

    public static final RegistryObject<Item> BLADE_ICE = ITEMS.register("blade_ice",
            () -> new WraqBlade(new Item.Properties().stacksTo(1).rarity(CustomStyle.IceItalic)
                    , CustomStyle.styleOfIce
                    , ComponentUtils.getSuffixOfIce()
                    , 2.5, 135));

    public static final RegistryObject<Item> BLADE_SAKURA = ITEMS.register("blade_sakura",
            () -> new WraqBlade(new Item.Properties().stacksTo(1).rarity(CustomStyle.SakuraItalic)
                    , CustomStyle.styleOfSakura
                    , ComponentUtils.getSuffixOfSakura()
                    , 2.75, 150));

    public static final RegistryObject<Item> BLADE_DEVIL = ITEMS.register("blade_devil",
            () -> new WraqBlade(new Item.Properties().stacksTo(1).rarity(CustomStyle.SakuraItalic)
                    , CustomStyle.styleOfDemon
                    , ComponentUtils.getSuffixOfDemon()
                    , 3, 150));

    public static final RegistryObject<Item> BLADE_MOON = ITEMS.register("blade_moon",
            () -> new WraqBlade(new Item.Properties().stacksTo(1).rarity(CustomStyle.MoonItalic)
                    , CustomStyle.styleOfMoon
                    , ComponentUtils.getSuffixOfMoon()
                    , 3.25, 160));
}
