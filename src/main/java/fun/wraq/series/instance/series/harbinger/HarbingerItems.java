package fun.wraq.series.instance.series.harbinger;

import fun.wraq.common.util.Utils;
import fun.wraq.process.system.channel.SakuraIndustrySceptre;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.instance.series.harbinger.weapon.HarbingerBow;
import fun.wraq.series.instance.series.harbinger.weapon.HarbingerSceptre;
import fun.wraq.series.instance.series.harbinger.weapon.HarbingerSword;
import fun.wraq.series.instance.series.harbinger.weapon.HarbingerWeaponMaterial;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HarbingerItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> HARBINGER_INGOT = ITEMS.register("harbinger_ingot",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.HARBINGER), true, false));

    public static final RegistryObject<Item> HARBINGER_HEART = ITEMS.register("harbinger_heart",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.HARBINGER_BOLD), true, true));

    public static final RegistryObject<Item> HARBINGER_ROD = ITEMS.register("harbinger_rod",
            () -> new HarbingerWeaponMaterial(new Item.Properties().rarity(CustomStyle.HARBINGER_BOLD)));

    public static final RegistryObject<Item> HARBINGER_WEAPON_CORE = ITEMS.register("harbinger_weapon_core",
            () -> new HarbingerWeaponMaterial(new Item.Properties().rarity(CustomStyle.HARBINGER_BOLD)));

    public static final RegistryObject<Item> HARBINGER_SWORD_BLADE = ITEMS.register("harbinger_sword_blade",
            () -> new HarbingerWeaponMaterial(new Item.Properties().rarity(CustomStyle.HARBINGER_BOLD)));

    public static final RegistryObject<Item> HARBINGER_STRING = ITEMS.register("harbinger_string",
            () -> new HarbingerWeaponMaterial(new Item.Properties().rarity(CustomStyle.HARBINGER_BOLD)));

    public static final RegistryObject<Item> HARBINGER_MIRROR = ITEMS.register("harbinger_mirror",
            () -> new HarbingerWeaponMaterial(new Item.Properties().rarity(CustomStyle.HARBINGER_BOLD)));

    public static final RegistryObject<Item> RAW_IRON_INGOT = ITEMS.register("raw_iron_ingot",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.HARBINGER), true, false));

    public static final RegistryObject<Item> RAW_IRON_NUGGET = ITEMS.register("raw_iron_nugget",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.HARBINGER), true, false));

    public static final RegistryObject<Item> SAKURA_INDUSTRY_INGOT = ITEMS.register("sakura_industry_ingot",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SakuraBold), true, true));

    public static final RegistryObject<Item> HARBINGER_SWORD = ITEMS.register("harbinger_sword",
            () -> new HarbingerSword(new Item.Properties().rarity(CustomStyle.HARBINGER_ITALIC)));

    public static final RegistryObject<Item> HARBINGER_BOW = ITEMS.register("harbinger_bow",
            () -> new HarbingerBow(new Item.Properties().rarity(CustomStyle.HARBINGER_ITALIC)));

    public static final RegistryObject<Item> HARBINGER_SCEPTRE = ITEMS.register("harbinger_sceptre",
            () -> new HarbingerSceptre(new Item.Properties().rarity(CustomStyle.HARBINGER_ITALIC)));

    public static final RegistryObject<Item> SAKURA_INDUSTRY_SCEPTRE = ITEMS.register("sakura_industry_sceptre",
            () -> new SakuraIndustrySceptre(new Item.Properties().rarity(CustomStyle.SakuraItalic)));
}
