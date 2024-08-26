package com.very.wraq.series.instance.mixture;

import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MixtureItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> MIXTURE = ITEMS.register("mixture",
            () -> new WraqMixture(new Item.Properties().stacksTo(1).rarity(CustomStyle.WorldItalic)
                    , CustomStyle.styleOfWorld
                    , ComponentUtils.getSuffixOfWorldSoul(), 1, 0.3,
                    3, 13, 3, 0));

    public static final RegistryObject<Item> MIXTURE_PLAIN = ITEMS.register("mixture_plain",
            () -> new WraqMixture(new Item.Properties().stacksTo(1).rarity(CustomStyle.PlainItalic)
                    , CustomStyle.styleOfPlain
                    , ComponentUtils.getSuffixOfPlainBoss(), 1, 0.45,
                    4, 13, 3, 50));

    public static final RegistryObject<Item> MIXTURE_NETHER = ITEMS.register("mixture_nether",
            () -> new WraqMixture(new Item.Properties().stacksTo(1).rarity(CustomStyle.NetherItalic)
                    , CustomStyle.styleOfNether
                    , ComponentUtils.getSuffixOfIce(), 1, 0.6,
                    4, 13, 3.5, 90));

    public static final RegistryObject<Item> MIXTURE_PURPLE = ITEMS.register("mixture_purple",
            () -> new WraqMixture(new Item.Properties().stacksTo(1).rarity(CustomStyle.PurpleIronItalic)
                    , CustomStyle.styleOfPurpleIron
                    , ComponentUtils.getSuffixOfPurpleIron(), 1, 0.75,
                    5, 13, 3.5, 120));

    public static final RegistryObject<Item> MIXTURE_ICE = ITEMS.register("mixture_ice",
            () -> new WraqMixture(new Item.Properties().stacksTo(1).rarity(CustomStyle.IceItalic)
                    , CustomStyle.styleOfIce
                    , ComponentUtils.getSuffixOfIce(), 2, 0.5,
                    5, 13, 3.5, 135));

    public static final RegistryObject<Item> MIXTURE_SAKURA = ITEMS.register("mixture_sakura",
            () -> new WraqMixture(new Item.Properties().stacksTo(1).rarity(CustomStyle.SakuraItalic)
                    , CustomStyle.styleOfSakura
                    , ComponentUtils.getSuffixOfSakura(), 2, 0.55,
                    5, 13, 4, 150));

    public static final RegistryObject<Item> MIXTURE_DEVIL = ITEMS.register("mixture_devil",
            () -> new WraqMixture(new Item.Properties().stacksTo(1).rarity(CustomStyle.demonItalic)
                    , CustomStyle.styleOfDemon
                    , ComponentUtils.getSuffixOfDemon(), 2, 0.6,
                    5, 13, 4.5, 150));

    public static final RegistryObject<Item> MIXTURE_MOON = ITEMS.register("mixture_moon",
            () -> new WraqMixture(new Item.Properties().stacksTo(1).rarity(CustomStyle.MoonItalic)
                    , CustomStyle.styleOfMoon
                    , ComponentUtils.getSuffixOfDemon(), 2, 0.65,
                    6, 13, 4.5, 160));
}
