package fun.wraq.process.system.ore;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class PickaxeItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> STONE_PICKAXE_0 = ITEMS.register("stone_pickaxe_0",
            () -> new NormalPickaxe(new Item.Properties(), 0.2, CustomStyle.styleOfMine, false));

    public static final RegistryObject<Item> STONE_PICKAXE_1 = ITEMS.register("stone_pickaxe_1",
            () -> new NormalPickaxe(new Item.Properties(), 0.4, CustomStyle.styleOfMine, false));

    public static final RegistryObject<Item> STONE_PICKAXE_2 = ITEMS.register("stone_pickaxe_2",
            () -> new NormalPickaxe(new Item.Properties(), 0.6, CustomStyle.styleOfMine, false));

    public static final RegistryObject<Item> STONE_PICKAXE_3 = ITEMS.register("stone_pickaxe_3",
            () -> new NormalPickaxe(new Item.Properties(), 0.8, CustomStyle.styleOfMine, false));

    public static final RegistryObject<Item> STONE_PICKAXE_4 = ITEMS.register("stone_pickaxe_4",
            () -> new NormalPickaxe(new Item.Properties(), 1, CustomStyle.styleOfMine, false));

    public static final RegistryObject<Item> TINKER_STONE = ITEMS.register("tinker_stone",
            () -> new WraqItem(new Item.Properties(), true, List.of(
                    Te.s("通过", "炼造", CustomStyle.styleOfPower, "获取")
            )));

    public static final RegistryObject<Item> IRON_PICKAXE_0 = ITEMS.register("iron_pickaxe_0",
            () -> new NormalPickaxe(new Item.Properties(), 1.2, CustomStyle.styleOfMine, false));

    public static final RegistryObject<Item> IRON_PICKAXE_1 = ITEMS.register("iron_pickaxe_1",
            () -> new NormalPickaxe(new Item.Properties(), 1.4, CustomStyle.styleOfMine, false));

    public static final RegistryObject<Item> IRON_PICKAXE_2 = ITEMS.register("iron_pickaxe_2",
            () -> new NormalPickaxe(new Item.Properties(), 1.6, CustomStyle.styleOfMine, false));

    public static final RegistryObject<Item> IRON_PICKAXE_3 = ITEMS.register("iron_pickaxe_3",
            () -> new NormalPickaxe(new Item.Properties(), 1.8, CustomStyle.styleOfMine, false));

    public static final RegistryObject<Item> IRON_PICKAXE_4 = ITEMS.register("iron_pickaxe_4",
            () -> new NormalPickaxe(new Item.Properties(), 2, CustomStyle.styleOfMine, false));

    public static final RegistryObject<Item> TINKER_IRON = ITEMS.register("tinker_iron",
            () -> new WraqItem(new Item.Properties(), true, List.of(
                    Te.s("通过", "炼造", CustomStyle.styleOfPower, "获取")
            )));

    public static final RegistryObject<Item> TINKER_COPPER = ITEMS.register("tinker_copper",
            () -> new WraqItem(new Item.Properties(), true, List.of(
                    Te.s("通过", "炼造", CustomStyle.styleOfPower, "获取")
            )));

    public static final RegistryObject<Item> GOLD_PICKAXE_0 = ITEMS.register("golden_pickaxe_0",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.UNCOMMON), 2.2, CustomStyle.styleOfGold, true));

    public static final RegistryObject<Item> GOLD_PICKAXE_1 = ITEMS.register("golden_pickaxe_1",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.UNCOMMON), 2.4, CustomStyle.styleOfGold, true));

    public static final RegistryObject<Item> GOLD_PICKAXE_2 = ITEMS.register("golden_pickaxe_2",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.UNCOMMON), 2.6, CustomStyle.styleOfGold, true));

    public static final RegistryObject<Item> GOLD_PICKAXE_3 = ITEMS.register("golden_pickaxe_3",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.UNCOMMON), 2.8, CustomStyle.styleOfGold, true));

    public static final RegistryObject<Item> GOLD_PICKAXE_4 = ITEMS.register("golden_pickaxe_4",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.UNCOMMON), 3, CustomStyle.styleOfGold, true));

    public static final RegistryObject<Item> TINKER_GOLD = ITEMS.register("tinker_gold",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.UNCOMMON), true, List.of(
                    Te.s("通过", "炼造", CustomStyle.styleOfPower, "获取")
            )));

    public static final RegistryObject<Item> DIAMOND_PICKAXE_0 = ITEMS.register("diamond_pickaxe_0",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.RARE), 3.2, CustomStyle.styleOfWorld, true));

    public static final RegistryObject<Item> DIAMOND_PICKAXE_1 = ITEMS.register("diamond_pickaxe_1",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.RARE), 3.4, CustomStyle.styleOfWorld, true));

    public static final RegistryObject<Item> DIAMOND_PICKAXE_2 = ITEMS.register("diamond_pickaxe_2",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.RARE), 3.6, CustomStyle.styleOfWorld, true));

    public static final RegistryObject<Item> DIAMOND_PICKAXE_3 = ITEMS.register("diamond_pickaxe_3",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.RARE), 3.8, CustomStyle.styleOfWorld, true));

    public static final RegistryObject<Item> DIAMOND_PICKAXE_4 = ITEMS.register("diamond_pickaxe_4",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.RARE), 4, CustomStyle.styleOfWorld, true));

    public static final RegistryObject<Item> TINKER_DIAMOND = ITEMS.register("tinker_diamond",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.RARE), true, List.of(
                    Te.s("通过", "炼造", CustomStyle.styleOfPower, "获取")
            )));

    public static final RegistryObject<Item> NETHERITE_PICKAXE_0 = ITEMS.register("netherite_pickaxe_0",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.EPIC), 4.2, CustomStyle.styleOfRed, true));

    public static final RegistryObject<Item> NETHERITE_PICKAXE_1 = ITEMS.register("netherite_pickaxe_1",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.EPIC), 4.4, CustomStyle.styleOfRed, true));

    public static final RegistryObject<Item> NETHERITE_PICKAXE_2 = ITEMS.register("netherite_pickaxe_2",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.EPIC), 4.6, CustomStyle.styleOfRed, true));

    public static final RegistryObject<Item> NETHERITE_PICKAXE_3 = ITEMS.register("netherite_pickaxe_3",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.EPIC), 4.8, CustomStyle.styleOfRed, true));

    public static final RegistryObject<Item> NETHERITE_PICKAXE_4 = ITEMS.register("netherite_pickaxe_4",
            () -> new NormalPickaxe(new Item.Properties().rarity(Rarity.EPIC), 5, CustomStyle.styleOfRed, true));

    public static final RegistryObject<Item> TINKER_NETHERITE = ITEMS.register("tinker_netherite",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC), true, List.of(
                    Te.s("通过", "炼造", CustomStyle.styleOfPower, "获取")
            )));
}
