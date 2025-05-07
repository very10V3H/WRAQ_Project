package fun.wraq.process.system.cooking;

import cn.mcmod.corn_delight.item.ItemRegistry;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import net.brdle.collectorsreap.common.item.CRItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import umpaz.brewinandchewin.common.registry.BnCItems;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.ArrayList;
import java.util.List;

public class CookingItems {
    public static final List<Item> cookingProducts = new ArrayList<>();
    public static List<Item> getCookingProducts() {
        if (cookingProducts.isEmpty()) {
            for (Item item : ForgeRegistries.ITEMS) {
                if (CookingValue.getMealValue(item) > 0) {
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
