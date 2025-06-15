package fun.wraq.process.system.cooking;

import cn.mcmod.corn_delight.item.ItemRegistry;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.cooking.item.FoodCoin;
import fun.wraq.process.system.cooking.item.FoodMedal;
import fun.wraq.process.system.cooking.item.FoodPackage;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.instance.series.mushroom.gem.MushroomParasitismGem;
import fun.wraq.series.instance.series.mushroom.gem.MushroomSplitGem;
import fun.wraq.series.instance.series.mushroom.gem.MushroomSputterGem;
import net.brdle.collectorsreap.common.item.CRItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import umpaz.brewinandchewin.common.registry.BnCItems;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CookingItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> FOOD_COIN = ITEMS.register("food_coin",
            () -> new FoodCoin(new Item.Properties().rarity(CustomStyle.MUSHROOM_RARITY)));
    public static final RegistryObject<Item> FOOD_BIG_COIN = ITEMS.register("food_big_coin",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MUSHROOM_RARITY), false, true));

    public static final RegistryObject<Item> FOOD_MEDAL_0 = ITEMS.register("food_medal_0",
            () -> new FoodMedal(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY), 0));
    public static final RegistryObject<Item> FOOD_MEDAL_1 = ITEMS.register("food_medal_1",
            () -> new FoodMedal(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY), 1));
    public static final RegistryObject<Item> FOOD_MEDAL_2 = ITEMS.register("food_medal_2",
            () -> new FoodMedal(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY), 2));
    public static final RegistryObject<Item> FOOD_MEDAL_3 = ITEMS.register("food_medal_3",
            () -> new FoodMedal(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY), 3));

    public static final RegistryObject<Item> FOOD_PACKAGE_0 = ITEMS.register("food_package_0",
            () -> new FoodPackage(new Item.Properties().rarity(CustomStyle.MUSHROOM_RARITY), 0, 30));
    public static final RegistryObject<Item> FOOD_PACKAGE_1 = ITEMS.register("food_package_1",
            () -> new FoodPackage(new Item.Properties().rarity(CustomStyle.MUSHROOM_RARITY), 30, 75));
    public static final RegistryObject<Item> FOOD_PACKAGE_2 = ITEMS.register("food_package_2",
            () -> new FoodPackage(new Item.Properties().rarity(CustomStyle.MUSHROOM_RARITY), 75, 150));
    public static final RegistryObject<Item> FOOD_PACKAGE_3 = ITEMS.register("food_package_3",
            () -> new FoodPackage(new Item.Properties().rarity(CustomStyle.MUSHROOM_RARITY), 100, 1000));

    public static final RegistryObject<Item> MUSHROOM_SPUTTER_GEM_1 = ITEMS.register("mushroom_sputter_gem_1",
            () -> new MushroomSputterGem(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY), List.of(),
                    CustomStyle.MUSHROOM_STYLE,
                    Te.s("繁育与扩张的生命魔力", CustomStyle.MUSHROOM_STYLE),
                    ComponentUtils.getSuffixOfMushroom(), true));

    public static final RegistryObject<Item> MUSHROOM_PARASITISM_GEM_1 = ITEMS.register("mushroom_parasitism_gem_1",
            () -> new MushroomParasitismGem(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY), List.of(),
                    CustomStyle.MUSHROOM_STYLE,
                    Te.s("繁育与扩张的生命魔力", CustomStyle.MUSHROOM_STYLE),
                    ComponentUtils.getSuffixOfMushroom(), true));

    public static final RegistryObject<Item> MUSHROOM_SPLIT_GEM_1 = ITEMS.register("mushroom_split_gem_1",
            () -> new MushroomSplitGem(new Item.Properties().rarity(CustomStyle.MUSHROOM_BOLD_RARITY), List.of(),
                    CustomStyle.MUSHROOM_STYLE,
                    Te.s("繁育与扩张的生命魔力", CustomStyle.MUSHROOM_STYLE),
                    ComponentUtils.getSuffixOfMushroom(), true));

    public static final List<Item> cookingProducts = new ArrayList<>();
    public static List<Item> getCookingProducts() {
        if (cookingProducts.isEmpty()) {
            for (Item item : ForgeRegistries.ITEMS) {
                if (CookingValue.getMealValue(item) > 0
                        || !SpecialEventItems.ITEMS.getEntries().stream()
                        .map(RegistryObject::get).collect(Collectors.toSet())
                        .contains(item)) {
                    cookingProducts.add(item);
                }
            }
        }
        return cookingProducts;
    }

    private static final List<Item> cropSellingList = new ArrayList<>();
    public static List<Item> getCropSellingList() {
        if (cropSellingList.isEmpty()) {
            cropSellingList.add(Items.BEETROOT);
            cropSellingList.add(Items.CARROT);
            cropSellingList.add(Items.NETHER_WART);
            cropSellingList.add(Items.POTATO);
            cropSellingList.add(Items.WHEAT);
            cropSellingList.add(Items.PUMPKIN);
            cropSellingList.add(ModItems.CABBAGE.get());
            cropSellingList.add(ModItems.ONION.get());
            cropSellingList.add(ModItems.RICE.get());
            cropSellingList.add(ModItems.TOMATO.get());
            cropSellingList.add(com.baisylia.culturaldelights.item.ModItems.EGGPLANT.get());
            cropSellingList.add(ItemRegistry.CORN.get());
            cropSellingList.add(NeapolitanItems.DRIED_VANILLA_PODS.get());
            cropSellingList.add(Items.SUGAR);
            cropSellingList.add(NeapolitanItems.MINT_LEAVES.get());
            cropSellingList.add(NeapolitanItems.ADZUKI_BEANS.get());
            cropSellingList.add(Items.COCOA_BEANS);
        }
        return cropSellingList;
    }

    private static final List<Item> fruitSellingList = new ArrayList<>();
    public static List<Item> getFruitSellingList() {
        if (fruitSellingList.isEmpty()) {
            fruitSellingList.add(Items.GLOW_BERRIES);
            fruitSellingList.add(Items.SWEET_BERRIES);
            fruitSellingList.add(Items.APPLE);
            fruitSellingList.add(Items.MELON);
            fruitSellingList.add(CRItems.POMEGRANATE.get());
            fruitSellingList.add(CRItems.LIME.get());
            fruitSellingList.add(com.baisylia.culturaldelights.item.ModItems.AVOCADO.get());
            fruitSellingList.add(NeapolitanItems.BANANA.get());
            fruitSellingList.add(NeapolitanItems.STRAWBERRIES.get());
        }
        return fruitSellingList;
    }

    private static final List<Item> mushroomSellingList = new ArrayList<>();
    public static List<Item> getMushroomSellingList() {
        if (mushroomSellingList.isEmpty()) {
            mushroomSellingList.add(Items.BROWN_MUSHROOM);
            mushroomSellingList.add(Items.RED_MUSHROOM);
            mushroomSellingList.add(Items.CRIMSON_FUNGUS);
            mushroomSellingList.add(Items.WARPED_FUNGUS);
            mushroomSellingList.add(CRItems.PORTOBELLO.get());
        }
        return mushroomSellingList;
    }

    private static final List<Item> milkAndEggSellingList = new ArrayList<>();
    public static List<Item> getMilkAndEggSellingList() {
        if (milkAndEggSellingList.isEmpty()) {
            milkAndEggSellingList.add(ModItems.MILK_BOTTLE.get());
            milkAndEggSellingList.add(BnCItems.FLAXEN_CHEESE_WHEEL.get());
            milkAndEggSellingList.add(BnCItems.SCARLET_CHEESE_WHEEL.get());
            milkAndEggSellingList.add(Items.EGG);
        }
        return milkAndEggSellingList;
    }

    private static final List<Item> meatSellingList = new ArrayList<>();
    public static List<Item> getMeatSellingList() {
        if (meatSellingList.isEmpty()) {
            meatSellingList.add(Items.CHICKEN);
            meatSellingList.add(Items.BEEF);
            meatSellingList.add(Items.PORKCHOP);
            meatSellingList.add(Items.MUTTON);
            meatSellingList.add(Items.RABBIT);
            meatSellingList.add(ModItems.HAM.get());
        }
        return meatSellingList;
    }

    private static final List<Item> seafoodSellingList = new ArrayList<>();
    public static List<Item> getSeafoodSellingList() {
        if (seafoodSellingList.isEmpty()) {
            seafoodSellingList.add(Items.KELP);
            seafoodSellingList.add(Items.COD);
            seafoodSellingList.add(Items.SALMON);
            seafoodSellingList.add(Items.TROPICAL_FISH);
            seafoodSellingList.add(CRItems.TIGER_PRAWN.get());
            seafoodSellingList.add(CRItems.PLATINUM_BASS.get());
            seafoodSellingList.add(Items.RABBIT);
            seafoodSellingList.add(ModItems.HAM.get());
            seafoodSellingList.add(CRItems.CHIEFTAIN_CRAB_BUCKET.get());
            seafoodSellingList.add(CRItems.CLAM.get());
        }
        return seafoodSellingList;
    }

    private static final List<Item> specialSellingList = new ArrayList<>();
    public static List<Item> getSpecialSellingList() {
        if (specialSellingList.isEmpty()) {
            specialSellingList.add(Items.WARPED_ROOTS);
            specialSellingList.add(Items.GLOW_LICHEN);
            specialSellingList.add(Items.HANGING_ROOTS);
            specialSellingList.add(Items.NETHER_WART);
            specialSellingList.add(Items.BONE_MEAL);
            specialSellingList.add(Items.ROTTEN_FLESH);
            specialSellingList.add(Items.HONEY_BOTTLE);
            specialSellingList.add(Items.BONE);
            specialSellingList.add(Items.INK_SAC);
            specialSellingList.add(Items.BLAZE_POWDER);
            specialSellingList.add(NeapolitanItems.ICE_CUBES.get());
        }
        return specialSellingList;
    }

    private static final List<Item> allSellingItemList = new ArrayList<>();
    public static List<Item> getAllSellingItems() {
        if (allSellingItemList.isEmpty()) {
            allSellingItemList.addAll(getCropSellingList());
            allSellingItemList.addAll(getFruitSellingList());
            allSellingItemList.addAll(getMushroomSellingList());
            allSellingItemList.addAll(getMilkAndEggSellingList());
            allSellingItemList.addAll(getMeatSellingList());
            allSellingItemList.addAll(getSeafoodSellingList());
            allSellingItemList.addAll(getSpecialSellingList());
        }
        return allSellingItemList;
    }

    public static void onRightClickBlock(Player player, BlockPos blockPos) {
        if (player.getMainHandItem().is(Items.BUCKET)
                && player.level().getBlockState(blockPos).getBlock().toString().contains("sink")) {
            if (player.getMainHandItem().getCount() == 1) {
                player.setItemInHand(InteractionHand.MAIN_HAND, Items.WATER_BUCKET.getDefaultInstance());
            } else {
                player.getMainHandItem().shrink(1);
                player.addItem(Items.WATER_BUCKET.getDefaultInstance());
            }
        }
    }
}
