package fun.wraq.render.gui.villagerTrade;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.cooking.CookingItems;
import fun.wraq.process.system.cooking.CookingValue;
import fun.wraq.render.toolTip.CustomStyle;
import net.brdle.collectorsreap.common.item.CRItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class TradeListNew {
    public static void init() {
        cookingCrop();
        cookingFruit();
        cookingMushroom();
        cookingMilkAndEgg();
        cookingMeat();
        cookingSeafood();
        cookingSpecial();
        cookingTools();
        cooking();
        dragonBoat();
    }

    public static List<ItemStack> getCoinList(int vb) {
        List<ItemStack> stacks = new ArrayList<>();
        if (vb / 144 > 0) {
            stacks.add(new ItemStack(ModItems.GOLD_COIN.get(), vb / 144));
        }
        if (vb % 144 / 12 > 0) {
            stacks.add(new ItemStack(ModItems.silverCoin.get(), vb % 144 / 12));
        }
        if (vb % 12 > 0) {
            stacks.add(new ItemStack(ModItems.copperCoin.get(), vb % 12));
        }
        return stacks;
    }

    public static void cookingCrop() {
        List<ItemStack> stacks = CookingItems.getCropSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("作物(制品)商人", "cookingCrop",
                CustomStyle.styleOfField, VillagerType.PLAINS, VillagerProfession.FARMER, stacks);
        for (ItemStack stack : stacks) {
            TradeList.tradeRecipeMap.put(stack, getCoinList(CookingValue.getIngredientValue(stack.getItem()) * 16));
        }
    }

    public static void cookingFruit() {
        List<ItemStack> stacks = CookingItems.getFruitSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("水果(制品)商人", "cookingFruit",
                CustomStyle.styleOfLife, VillagerType.JUNGLE, VillagerProfession.FARMER, stacks);
        for (ItemStack stack : stacks) {
            int value = CookingValue.getIngredientValue(stack.getItem());
            if (value == 0) {
                if (stack.is(Items.MELON)) {
                    value = 36 * 9;
                } else if (stack.is(CRItems.POMEGRANATE.get())) {
                    value = 36 * 4;
                }
            }
            TradeList.tradeRecipeMap.put(stack, getCoinList(value * 16));
        }
    }

    public static void cookingMushroom() {
        List<ItemStack> stacks = CookingItems.getMushroomSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("菌类商人", "cookingMushroom",
                CustomStyle.MUSHROOM_STYLE, VillagerType.JUNGLE, VillagerProfession.MASON, stacks);
        for (ItemStack stack : stacks) {
            TradeList.tradeRecipeMap.put(stack, getCoinList(CookingValue.getIngredientValue(stack.getItem()) * 16));
        }
    }

    public static void cookingMilkAndEgg() {
        List<ItemStack> stacks = CookingItems.getMilkAndEggSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("蛋奶商人", "cookingMilkAndEgg",
                Style.EMPTY.applyFormat(ChatFormatting.WHITE), VillagerType.JUNGLE, VillagerProfession.BUTCHER, stacks);
        for (ItemStack stack : stacks) {
            TradeList.tradeRecipeMap.put(stack, getCoinList(CookingValue.getIngredientValue(stack.getItem()) * 16));
        }
    }

    public static void cookingMeat() {
        List<ItemStack> stacks = CookingItems.getMeatSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("肉类商人", "cookingMeat",
                CustomStyle.styleOfLife, VillagerType.PLAINS, VillagerProfession.BUTCHER, stacks);
        for (ItemStack stack : stacks) {
            TradeList.tradeRecipeMap.put(stack, getCoinList(CookingValue.getIngredientValue(stack.getItem()) * 16));
        }
    }

    public static void cookingSeafood() {
        List<ItemStack> stacks = CookingItems.getSeafoodSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("水产商人", "cookingSeafood",
                CustomStyle.styleOfWater, VillagerType.SWAMP, VillagerProfession.FISHERMAN, stacks);
        for (ItemStack stack : stacks) {
            int value = CookingValue.getIngredientValue(stack.getItem());
            if (value == 0) {
                if (stack.is(CRItems.TIGER_PRAWN.get())) {
                    value = 144;
                }
            }
            TradeList.tradeRecipeMap.put(stack, getCoinList(value * 16));
        }
    }

    public static void cookingSpecial() {
        List<ItemStack> stacks = CookingItems.getSpecialSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("特殊商人", "cookingSpecial",
                CustomStyle.styleOfFantasy, VillagerType.TAIGA, VillagerProfession.LIBRARIAN, stacks);
        for (ItemStack stack : stacks) {
            TradeList.tradeRecipeMap.put(stack, getCoinList(CookingValue.getIngredientValue(stack.getItem()) * 16));
        }
    }

    public static void cookingTools() {
        ItemStack ironKnife = new ItemStack(vectorwing.farmersdelight.common.registry.ModItems.IRON_KNIFE.get());
        ItemStack goldenKnife = new ItemStack(vectorwing.farmersdelight.common.registry.ModItems.GOLDEN_KNIFE.get());
        ItemStack glassBottle = new ItemStack(Items.GLASS_BOTTLE, 16);
        ItemStack bowl = new ItemStack(Items.BOWL, 16);
        ItemStack cauldron = new ItemStack(Items.CAULDRON, 16);
        ItemStack bucket = new ItemStack(Items.BUCKET, 16);
        ItemStack stick = new ItemStack(Items.STICK, 64);
        List<ItemStack> stacks = List.of(
                ironKnife, goldenKnife,
                glassBottle, bowl, cauldron, bucket
        );
        MyVillagerData.setMyVillagerData("餐具商人", "cookingTools",
                CustomStyle.MUSHROOM_STYLE, VillagerType.PLAINS, VillagerProfession.TOOLSMITH, stacks);
        TradeList.tradeRecipeMap.put(ironKnife, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 16));
        }});
        TradeList.tradeRecipeMap.put(goldenKnife, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 999));
        }});
        TradeList.tradeRecipeMap.put(glassBottle, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 1));
        }});
        TradeList.tradeRecipeMap.put(bowl, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 1));
        }});
        TradeList.tradeRecipeMap.put(cauldron, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 16));
        }});
        TradeList.tradeRecipeMap.put(bucket, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 16));
        }});
        TradeList.tradeRecipeMap.put(stick, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 4));
        }});
    }

    public static void cooking() {
        MyVillagerData.setMyVillagerData("联合研院大吃货 - 老八",
                "cooking", CustomStyle.MUSHROOM_STYLE, VillagerType.SNOW,
                VillagerProfession.LIBRARIAN, List.of());
    }

    public static void dragonBoat() {
        MyVillagerData.setMyVillagerData("粽子大王",
                "dragonBoat", CustomStyle.DRAGON_BOAT_FES_STYLE, VillagerType.JUNGLE,
                VillagerProfession.BUTCHER, List.of());
    }
}
