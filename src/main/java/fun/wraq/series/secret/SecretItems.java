package fun.wraq.series.secret;

import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SecretItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> CHEST_KEY_HOLY_0 = ITEMS.register("secret_chest_key_holy_0", () ->
            new SecretKey(new Item.Properties().rarity(Rarity.UNCOMMON), 10, SecretSeries.HOLY, 0));
    public static final RegistryObject<Item> CHEST_KEY_HOLY_1 = ITEMS.register("secret_chest_key_holy_1", () ->
            new SecretKey(new Item.Properties().rarity(Rarity.RARE), 20, SecretSeries.HOLY, 1));
    public static final RegistryObject<Item> CHEST_KEY_HOLY_2 = ITEMS.register("secret_chest_key_holy_2", () ->
            new SecretKey(new Item.Properties().rarity(Rarity.EPIC), 40, SecretSeries.HOLY, 2));
    public static final RegistryObject<Item> CHEST_KEY_HOLY_3 = ITEMS.register("secret_chest_key_holy_3", () ->
            new SecretKey(new Item.Properties().rarity(Rarity.EPIC), 80, SecretSeries.HOLY, 3));
    public static final RegistryObject<Item> CHEST_KEY_HOLY_4 = ITEMS.register("secret_chest_key_holy_4", () ->
            new SecretKey(new Item.Properties().rarity(Rarity.EPIC), 160, SecretSeries.HOLY, 4));

    public static final RegistryObject<Item> TREASURE_HOLY_0 = ITEMS.register("secret_treasure_holy_0", () ->
            new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY)));
    public static final RegistryObject<Item> TREASURE_HOLY_1 = ITEMS.register("secret_treasure_holy_1", () ->
            new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY)));
    public static final RegistryObject<Item> TREASURE_HOLY_2 = ITEMS.register("secret_treasure_holy_2", () ->
            new WraqItem(new Item.Properties().rarity(CustomStyle.DIVINE_BOLD_RARITY)));
    public static final RegistryObject<Item> TREASURE_HOLY_3 = ITEMS.register("secret_treasure_holy_3", () ->
            new WraqItem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> TREASURE_HOLY_4 = ITEMS.register("secret_treasure_holy_4", () ->
            new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> CHEST_KEY_NETHER_0 = ITEMS.register("secret_chest_key_nether_0", () ->
            new SecretKey(new Item.Properties().rarity(Rarity.UNCOMMON), 10, SecretSeries.NETHER, 0));
    public static final RegistryObject<Item> CHEST_KEY_NETHER_1 = ITEMS.register("secret_chest_key_nether_1", () ->
            new SecretKey(new Item.Properties().rarity(Rarity.RARE), 20, SecretSeries.NETHER, 1));
    public static final RegistryObject<Item> CHEST_KEY_NETHER_2 = ITEMS.register("secret_chest_key_nether_2", () ->
            new SecretKey(new Item.Properties().rarity(Rarity.EPIC), 40, SecretSeries.NETHER, 2));
    public static final RegistryObject<Item> CHEST_KEY_NETHER_3 = ITEMS.register("secret_chest_key_nether_3", () ->
            new SecretKey(new Item.Properties().rarity(Rarity.EPIC), 80, SecretSeries.NETHER, 3));
    public static final RegistryObject<Item> CHEST_KEY_NETHER_4 = ITEMS.register("secret_chest_key_nether_4", () ->
            new SecretKey(new Item.Properties().rarity(Rarity.EPIC), 160, SecretSeries.NETHER, 4));

    public static final RegistryObject<Item> TREASURE_NETHER_0 = ITEMS.register("secret_treasure_nether_0", () ->
            new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold)));
    public static final RegistryObject<Item> TREASURE_NETHER_1 = ITEMS.register("secret_treasure_nether_1", () ->
            new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold)));
    public static final RegistryObject<Item> TREASURE_NETHER_2 = ITEMS.register("secret_treasure_nether_2", () ->
            new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold)));
    public static final RegistryObject<Item> TREASURE_NETHER_3 = ITEMS.register("secret_treasure_nether_3", () ->
            new WraqItem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> TREASURE_NETHER_4 = ITEMS.register("secret_treasure_nether_4", () ->
            new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));
}
