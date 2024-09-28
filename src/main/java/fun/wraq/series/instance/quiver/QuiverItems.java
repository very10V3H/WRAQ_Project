package fun.wraq.series.instance.quiver;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.quiver.WraqQuiver;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class QuiverItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> QUIVER = ITEMS.register("quiver",
            () -> new fun.wraq.series.instance.quiver.WraqQuiver(new Item.Properties().stacksTo(1).rarity(CustomStyle.WorldItalic)
                    , CustomStyle.styleOfWorld
                    , ComponentUtils.getSuffixOfWorldSoul()
                    , 0.3, 1, 0.2, 0));

    public static final RegistryObject<Item> QUIVER_PLAIN = ITEMS.register("quiver_plain",
            () -> new fun.wraq.series.instance.quiver.WraqQuiver(new Item.Properties().stacksTo(1).rarity(CustomStyle.PlainItalic)
                    , CustomStyle.styleOfPlain
                    , ComponentUtils.getSuffixOfPlainBoss()
                    , 0.3, 2, 0.3, 50));

    public static final RegistryObject<Item> QUIVER_NETHER = ITEMS.register("quiver_nether",
            () -> new fun.wraq.series.instance.quiver.WraqQuiver(new Item.Properties().stacksTo(1).rarity(CustomStyle.MagmaItalic)
                    , CustomStyle.styleOfPower
                    , ComponentUtils.getSuffixOfIgniteRevenant()
                    , 0.45, 2, 0.4, 90));

    public static final RegistryObject<Item> QUIVER_PURPLE = ITEMS.register("quiver_purple",
            () -> new fun.wraq.series.instance.quiver.WraqQuiver(new Item.Properties().stacksTo(1).rarity(CustomStyle.PurpleIronItalic)
                    , CustomStyle.styleOfPurpleIron
                    , ComponentUtils.getSuffixOfPurpleIron()
                    , 0.45, 3, 0.5, 120));

    public static final RegistryObject<Item> QUIVER_ICE = ITEMS.register("quiver_ice",
            () -> new fun.wraq.series.instance.quiver.WraqQuiver(new Item.Properties().stacksTo(1).rarity(CustomStyle.IceItalic)
                    , CustomStyle.styleOfIce
                    , ComponentUtils.getSuffixOfIce()
                    , 0.6, 3, 0.6, 135));

    public static final RegistryObject<Item> QUIVER_SAKURA = ITEMS.register("quiver_sakura",
            () -> new fun.wraq.series.instance.quiver.WraqQuiver(new Item.Properties().stacksTo(1).rarity(CustomStyle.SakuraItalic)
                    , CustomStyle.styleOfSakura
                    , ComponentUtils.getSuffixOfSakura()
                    , 0.6, 4, 0.7, 150));

    public static final RegistryObject<Item> QUIVER_DEVIL = ITEMS.register("quiver_devil",
            () -> new fun.wraq.series.instance.quiver.WraqQuiver(new Item.Properties().stacksTo(1).rarity(CustomStyle.demonItalic)
                    , CustomStyle.styleOfDemon
                    , ComponentUtils.getSuffixOfDemon()
                    , 0.75, 4, 0.8, 150));

    public static final RegistryObject<Item> QUIVER_MOON = ITEMS.register("quiver_moon",
            () -> new WraqQuiver(new Item.Properties().stacksTo(1).rarity(CustomStyle.MoonItalic)
                    , CustomStyle.styleOfMoon
                    , ComponentUtils.getSuffixOfMoon()
                    , 0.75, 5, 0.9, 160));
}
