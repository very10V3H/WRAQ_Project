package fun.wraq.series.crystal;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class CrystalItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> ORIGIN_STONE = ITEMS.register("origin_stone",
            () -> new OriginStone(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ORIGIN_STONE_DISCOUNT = ITEMS.register("origin_stone_discount",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.RARE), true, true, List.of(
                    Te.s("可在与多彩宝石商人购买", ORIGIN_STONE.get(), "时"),
                    Te.s("消耗该物品，折扣", "5%", ChatFormatting.LIGHT_PURPLE, "，相当于",
                            "(14000VB)", ChatFormatting.GOLD)
            )));

    public static final RegistryObject<Item> GREEN_CRYSTAL_C = ITEMS.register("green_crystal_c",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.COMMON), 0, 0));
    public static final RegistryObject<Item> GREEN_CRYSTAL_B = ITEMS.register("green_crystal_b",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.COMMON), 0, 1));
    public static final RegistryObject<Item> GREEN_CRYSTAL_A = ITEMS.register("green_crystal_a",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.UNCOMMON), 0, 2));
    public static final RegistryObject<Item> GREEN_CRYSTAL_P = ITEMS.register("green_crystal_p",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.RARE), 0, 3));
    public static final RegistryObject<Item> GREEN_CRYSTAL_PP = ITEMS.register("green_crystal_pp",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.EPIC), 0, 4));

    public static final RegistryObject<Item> BLUE_CRYSTAL_C = ITEMS.register("blue_crystal_c",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.COMMON), 1, 0));
    public static final RegistryObject<Item> BLUE_CRYSTAL_B = ITEMS.register("blue_crystal_b",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.COMMON), 1, 1));
    public static final RegistryObject<Item> BLUE_CRYSTAL_A = ITEMS.register("blue_crystal_a",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.UNCOMMON), 1, 2));
    public static final RegistryObject<Item> BLUE_CRYSTAL_P = ITEMS.register("blue_crystal_p",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.RARE), 1, 3));
    public static final RegistryObject<Item> BLUE_CRYSTAL_PP = ITEMS.register("blue_crystal_pp",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.EPIC), 1, 4));

    public static final RegistryObject<Item> YELLOW_CRYSTAL_C = ITEMS.register("yellow_crystal_c",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.COMMON), 2, 0));
    public static final RegistryObject<Item> YELLOW_CRYSTAL_B = ITEMS.register("yellow_crystal_b",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.COMMON), 2, 1));
    public static final RegistryObject<Item> YELLOW_CRYSTAL_A = ITEMS.register("yellow_crystal_a",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.UNCOMMON), 2, 2));
    public static final RegistryObject<Item> YELLOW_CRYSTAL_P = ITEMS.register("yellow_crystal_p",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.RARE), 2, 3));
    public static final RegistryObject<Item> YELLOW_CRYSTAL_PP = ITEMS.register("yellow_crystal_pp",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.EPIC), 2, 4));

    public static final RegistryObject<Item> RED_CRYSTAL_C = ITEMS.register("red_crystal_c",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.COMMON), 3, 0));
    public static final RegistryObject<Item> RED_CRYSTAL_B = ITEMS.register("red_crystal_b",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.COMMON), 3, 1));
    public static final RegistryObject<Item> RED_CRYSTAL_A = ITEMS.register("red_crystal_a",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.UNCOMMON), 3, 2));
    public static final RegistryObject<Item> RED_CRYSTAL_P = ITEMS.register("red_crystal_p",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.RARE), 3, 3));
    public static final RegistryObject<Item> RED_CRYSTAL_PP = ITEMS.register("red_crystal_pp",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.EPIC), 3, 4));

    public static final RegistryObject<Item> PURPLE_CRYSTAL_C = ITEMS.register("purple_crystal_c",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.COMMON), 4, 0));
    public static final RegistryObject<Item> PURPLE_CRYSTAL_B = ITEMS.register("purple_crystal_b",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.COMMON), 4, 1));
    public static final RegistryObject<Item> PURPLE_CRYSTAL_A = ITEMS.register("purple_crystal_a",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.UNCOMMON), 4, 2));
    public static final RegistryObject<Item> PURPLE_CRYSTAL_P = ITEMS.register("purple_crystal_p",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.RARE), 4, 3));
    public static final RegistryObject<Item> PURPLE_CRYSTAL_PP = ITEMS.register("purple_crystal_pp",
            () -> new CrystalItem(new Item.Properties().rarity(Rarity.EPIC), 4, 4));
}
