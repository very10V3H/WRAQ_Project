package fun.wraq.process.system.cooking;

import cn.mcmod.corn_delight.CornForgeTags;
import cn.mcmod.corn_delight.item.ItemRegistry;
import com.cosmicgelatin.seasonals.core.registry.SeasonalsItems;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.brdle.collectorsreap.common.item.CRItems;
import net.brdle.collectorsreap.data.CRItemTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.loading.FMLLoader;
import umpaz.brewinandchewin.common.registry.BnCItems;
import umpaz.brewinandchewin.common.tag.BnCTags;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.*;

public class CookingValue {

    public static Map<Item, Integer> ingredientItemValueMap = new HashMap<>();
    public static Map<TagKey<Item>, Integer> ingredientTagKeyValueMap = new HashMap<>();

    public static int getIngredientValue(ItemStack ingredientStack) {
        handleValueMap();
        if (ingredientItemValueMap.containsKey(ingredientStack.getItem())) {
            return ingredientItemValueMap.get(ingredientStack.getItem());
        } else {
            int maxValue = 0;
            for (Map.Entry<TagKey<Item>, Integer> entry : ingredientTagKeyValueMap.entrySet()) {
                if (ingredientStack.is(entry.getKey())) {
                    maxValue = Math.max(maxValue, entry.getValue());
                }
            }
            return maxValue;
        }
    }

    public static int getIngredientValue(Item item) {
        return getIngredientValue(item.getDefaultInstance());
    }

    public static void handleIngredientToolTip(Item item, List<Component> components) {
        if (getMealValue(item) > 0) {
            return;
        }
        int ingredientValue = getIngredientValue(item);
        if (ingredientValue > 0) {
            components.add(Te.s(" 食材价值: ", ingredientValue + "VB", ChatFormatting.GOLD));
        }
    }

    public static void handleMealToolTip(ItemStack stack, List<Component> components) {
        // 农夫乐事 食物的配方获取方式 需要手动将json文件复制到assets内
        Item item = stack.getItem();
        int value = getMealValue(item);
        if (value != 0) {
            components.add(Te.s("食材总价: ", (value * stack.getCount()) + "VB", ChatFormatting.GOLD));
            int count = mealIngredientCountMap.getOrDefault(item, 0);
            int typeCount = mealIngredientTypeCountMap.getOrDefault(item, 0);
            if (count > 0) {
                components.add(Te.s("食材数量: ", count + " (+" + count * countExValue + ")", ChatFormatting.GREEN));
            }
            if (typeCount > 0) {
                components.add(Te.s("食材种类: ", typeCount + " (+" + typeCount * typeCountExValue + "%)", ChatFormatting.AQUA));
            }
            int price = (int) ((value + count * countExValue) * (1 + typeCount * 0.01 * typeCountExValue));
            components.add(Te.s("售价: ", (price * stack.getCount()) + "VB", ChatFormatting.GOLD));
            components.add(Te.s("收益率: ",
                    String.format("%.0f%%", (price * 1d / value - 1) * 100), ChatFormatting.RED));
            if (item.equals(ModItems.RAW_PASTA.get())) {
                components.add(Te.s("该食材无法直接售出，但有几率被烹饪委托选中",
                        ChatFormatting.GRAY, ChatFormatting.ITALIC));
            }
        }
    }

    public static int getMealValue(Item item) {
        if (mealValueCacheMap.containsKey(item)) {
            return mealValueCacheMap.get(item);
        }
        int count = 1;
        List<Ingredient> inputItemsIn = getIngredients(item);
        if (inputItemsIn.isEmpty()) {
            mealValueCacheMap.put(item, 0);
            return 0;
        }
        int value = 0;
        int ingredientTypeCount = 0;
        Set<String> countedIngredientTypeSet = new HashSet<>();
        for (Ingredient ingredient : inputItemsIn) {
            int minValue = -1;
            Set<String> tempIngredientTypeSet = new HashSet<>();
            for (ItemStack stack : ingredient.getItems()) {
                int mealValue = getMealValue(stack.getItem());
                int ingredientValue = getIngredientValue(stack);
                if (mealValue > 0) {
                    if (minValue == -1) {
                        minValue = mealValue;
                        tempIngredientTypeSet.addAll(getIngredientTypes(stack.getItem()));
                    } else {
                        minValue = Math.min(minValue, mealValue);
                        tempIngredientTypeSet.addAll(getIngredientTypes(stack.getItem()));
                    }
                } else if (ingredientValue > 0) {
                    if (minValue == -1) {
                        minValue = ingredientValue;
                        tempIngredientTypeSet.addAll(getIngredientTypes(stack.getItem()));
                    } else {
                        minValue = Math.min(minValue, ingredientValue);
                        tempIngredientTypeSet.addAll(getIngredientTypes(stack.getItem()));
                    }
                }
            }
            if (minValue != -1) {
                value += minValue;
                if (tempIngredientTypeSet.stream().noneMatch(countedIngredientTypeSet::contains)) {
                    ++ingredientTypeCount;
                    countedIngredientTypeSet.addAll(tempIngredientTypeSet);
                }
            }
        }
        mealValueCacheMap.put(item, value / count);
        mealIngredientCountMap.put(item, inputItemsIn.size());
        mealIngredientTypeCountMap.put(item, ingredientTypeCount);
        return value / count;
    }

    public static void sellAll(Player player) {
        int cost = 0;
        int sum = 0;
        int count = 0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(ModItems.RAW_PASTA.get())) {
                CookingVillager.sendMSG(player, Te.s("生意面无法直接卖出."));
                continue;
            }
            int mealValue = getMealValue(stack.getItem());
            if (mealValue > 0) {
                cost += getMealValue(stack.getItem()) * stack.getCount();
                count += stack.getCount();
                sum += sell(player, stack);
                InventoryOperation.removeItem(player, stack.getItem(), stack.getCount());
            }
        }
        CookingPlayerData.incrementSellFoodCount(player, count);
        CookingVillager.sendMSG(player, Te.s("共售出了",
                count + "份", CustomStyle.MUSHROOM_STYLE, "食物，",
                "共收入", sum + "VB，", ChatFormatting.GOLD,
                "食材成本为:", cost + "VB.", ChatFormatting.GOLD,
                "(累计售出份数:" + CookingPlayerData.getSellFoodCount(player) + ")", ChatFormatting.GRAY));
    }

    public static int getMealSellValue(ItemStack itemStack) {
        int count = itemStack.getCount();
        return (int) ((getMealValue(itemStack.getItem()) + mealIngredientCountMap.get(itemStack.getItem()) * countExValue)
                * (1 + mealIngredientTypeCountMap.get(itemStack.getItem()) * 0.01 * typeCountExValue) * count);
    }

    public static int sell(Player player, ItemStack itemStack) {
        int value = getMealSellValue(itemStack);
        Compute.VBIncomeAndMSGSend(player, value);
        return value;
    }

    public static final int countExValue = 5;
    public static final int typeCountExValue = 5;

    public static final Map<Item, Integer> mealValueCacheMap = new HashMap<>();
    public static final Map<Item, Integer> mealIngredientCountMap = new HashMap<>();
    public static final Map<Item, Integer> mealIngredientTypeCountMap = new HashMap<>();

    public static void handleTypeToolTip(Item item, List<Component> components) {
        if (getIngredientValue(item) == 0) {
            return;
        }
        Set<String> types = getIngredientTypes(item);
        MutableComponent component = Te.s(" 类型:");
        if (!types.isEmpty()) {
            for (int i = 0; i < types.stream().toList().size(); i++) {
                String type = types.stream().toList().get(i);
                if (i != types.size() - 1) {
                    component.append(Te.s(type + "、"));
                } else {
                    component.append(Te.s(type));
                }
            }
        } else {
            component.append(Te.s("其他"));
        }
        components.add(component);
    }

    public static Set<String> getIngredientTypes(Item item) {
        Set<String> types = new HashSet<>();
        if (ingredientItemTypeMap.containsKey(item)) {
            types.add(ingredientItemTypeMap.get(item));
        } else {
            for (Map.Entry<TagKey<Item>, String> entry : ingredientTagTypeMap.entrySet()) {
                if (item.getDefaultInstance().is(entry.getKey())) {
                    types.add(entry.getValue());
                }
            }
        }
        return types;
    }

    public static void handleToolTip(ItemStack stack, List<Component> components) {
        CookingValue.handleIngredientToolTip(stack.getItem(), components);
        CookingValue.handleMealToolTip(stack, components);
        CookingValue.handleTypeToolTip(stack.getItem(), components);
    }

    public static final String CROPS = "作物(制品)";
    public static final String FRUITS = "水果(制品)";
    public static final String MUSHROOM = "菌类";
    public static final String MILK = "乳制品";
    public static final String CHICKEN = "禽肉";
    public static final String EGG = "蛋类(制品)";
    public static final String MEAT = "红肉(制品)";
    public static final String SEAFOOD = "水产(制品)";
    public static final String MEAL = "成品菜肴";
    public static final String SPECIAL = "特殊";

    public static final Map<Item, String> ingredientItemTypeMap = new HashMap<>() {{
        put(Items.APPLE, FRUITS);
        put(Items.SUGAR, CROPS);
        put(Items.POTATO, CROPS);
        put(Items.BEETROOT, CROPS);
        put(Items.GLOW_BERRIES, FRUITS);
        put(Items.HANGING_ROOTS, SPECIAL);
        put(Items.GLOW_LICHEN, SPECIAL);
        put(BnCItems.FLAXEN_CHEESE_WEDGE.get(), MILK);
        put(BnCItems.FLAXEN_CHEESE_WHEEL.get(), MILK);
        put(Items.CARROT, CROPS);
        put(ItemRegistry.CORNBREAD_BATTER.get(), CROPS);
        put(ModItems.TOMATO_SAUCE.get(), CROPS);
        put(Items.BROWN_MUSHROOM, MUSHROOM);
        put(ItemRegistry.CORNBREAD.get(), MEAL);
        put(Items.BAKED_POTATO, CROPS);
        put(Items.SWEET_BERRIES, FRUITS);
        put(Items.ROTTEN_FLESH, SPECIAL);
        put(Items.BONE_MEAL, SPECIAL);
        put(ModItems.RAW_PASTA.get(), MEAL);
        put(com.baisylia.culturaldelights.item.ModItems.CORN_DOUGH.get(), CROPS);
        put(com.baisylia.culturaldelights.item.ModItems.CUT_AVOCADO.get(), FRUITS);
        put(com.baisylia.culturaldelights.item.ModItems.AVOCADO.get(), FRUITS);
        put(BnCItems.SCARLET_CHEESE_WEDGE.get(), MILK);
        put(BnCItems.SCARLET_CHEESE_WHEEL.get(), MILK);
        put(ModItems.HAM.get(), MEAT);
        put(Items.COCOA_BEANS, CROPS);
        put(Items.RED_MUSHROOM, MUSHROOM);
        put(com.baisylia.culturaldelights.item.ModItems.TORTILLA_CHIPS.get(), CROPS);
        put(Items.DRIED_KELP, CROPS);
        put(ModItems.MINCED_BEEF.get(), MEAT);
        put(com.baisylia.culturaldelights.item.ModItems.EGGPLANT.get(), CROPS);
        put(com.baisylia.culturaldelights.item.ModItems.CUT_EGGPLANT.get(), CROPS);
        put(ModItems.PUMPKIN_SLICE.get(), CROPS);
        put(Items.RABBIT, MEAT);
        put(Items.WHEAT, CROPS);
        put(Items.EGG, EGG);
        put(Items.NETHER_WART, SPECIAL);
        put(Items.INK_SAC, SPECIAL);
        put(ItemRegistry.CORNBREAD.get(), MEAL);
        put(ModItems.BROWN_MUSHROOM_COLONY.get(), MUSHROOM);
        put(CRItems.LIMEADE.get(), FRUITS);
        put(ModItems.COOKED_RICE.get(), CROPS);
        put(CRItems.LIME_SLICE.get(), FRUITS);
        put(Items.HONEY_BOTTLE, SPECIAL);
        put(CRItems.CHIEFTAIN_CRAB_BUCKET.get(), SEAFOOD);
        put(Items.CRIMSON_FUNGUS, MUSHROOM);
        put(Items.WARPED_FUNGUS, MUSHROOM);
        put(CRItems.BAKED_PORTOBELLO_CAP.get(), MUSHROOM);
        put(Items.WARPED_ROOTS, SPECIAL);
        put(Items.KELP, CROPS);
        put(Items.MELON_SLICE, FRUITS);
        put(Items.BLAZE_POWDER, SPECIAL);
        put(ItemRegistry.TORTILLA_CHIP.get(), MEAL);
        put(CRItems.PLATINUM_BASS_HEAD.get(), SEAFOOD);
        put(Items.PUMPKIN, CROPS);
        put(SeasonalsItems.PUMPKIN_PUREE.get(), CROPS);
        put(NeapolitanItems.BANANA.get(), FRUITS);
        put(NeapolitanItems.DRIED_VANILLA_PODS.get(), CROPS);
    }};

    public static final Map<TagKey<Item>, String> ingredientTagTypeMap = new HashMap<>() {{
        put(ForgeTags.RAW_FISHES, SEAFOOD);
        put(ForgeTags.EGGS, EGG);
        put(ForgeTags.CROPS, CROPS);
        put(Tags.Items.BONES, SPECIAL);
        put(Tags.Items.MUSHROOMS, SPECIAL);
        put(CornForgeTags.SEEDS_CORN, CROPS);
        put(ForgeTags.PASTA, CROPS);
        put(ForgeTags.VEGETABLES, CROPS);
        put(ForgeTags.RAW_CHICKEN, CHICKEN);
        put(ForgeTags.COOKED_CHICKEN, CHICKEN);
        put(ForgeTags.COOKED_PORK, MEAT);
        put(ForgeTags.RAW_BEEF, MEAT);
        put(ForgeTags.COOKED_BEEF, MEAT);
        put(ForgeTags.RAW_MUTTON, MEAT);
        put(ForgeTags.COOKED_MUTTON, MEAT);
        put(ForgeTags.RAW_PORK, MEAT);
        put(ForgeTags.MILK, MILK);
        put(BnCTags.CHEESE_WEDGES, MILK);
        put(ForgeTags.BREAD, MEAL);
        put(ForgeTags.DOUGH, CROPS);
        put(ForgeTags.COOKED_EGGS, EGG);
        put(ForgeTags.BERRIES, FRUITS);
        put(CRItemTags.FRUITS, FRUITS);
        put(CRItemTags.CRAB_MEAT, SEAFOOD);
        put(CRItemTags.CRAB_LEG, SEAFOOD);
        put(CRItemTags.SEEDS_POMEGRANATE, CROPS);
        put(CRItemTags.RAW_CLAM, SEAFOOD);
        put(CRItemTags.COOKED_PRAWN, SEAFOOD);
    }};

    public static void handleValueMap() {
        if (ingredientItemValueMap.isEmpty()) {
            ingredientItemValueMap.put(Items.SUGAR, 20);
            ingredientItemValueMap.put(Items.POTATO, 20);
            ingredientItemValueMap.put(Items.BEETROOT, 20);
            ingredientItemValueMap.put(Items.HANGING_ROOTS, 20);
            ingredientItemValueMap.put(Items.GLOW_LICHEN, 20);
            ingredientItemValueMap.put(Items.CARROT, 20);
            ingredientItemValueMap.put(ItemRegistry.CORNBREAD_BATTER.get(), 20);
            ingredientItemValueMap.put(ModItems.TOMATO_SAUCE.get(), 20);
            ingredientItemValueMap.put(Items.ROTTEN_FLESH, 20);
            ingredientItemValueMap.put(Items.BONE_MEAL, 20);
            ingredientItemValueMap.put(ModItems.RAW_PASTA.get(), 20);
            ingredientItemValueMap.put(com.baisylia.culturaldelights.item.ModItems.CORN_DOUGH.get(), 50);
            ingredientItemValueMap.put(Items.COCOA_BEANS, 20);
            ingredientItemValueMap.put(com.baisylia.culturaldelights.item.ModItems.EGGPLANT.get(), 20);
            ingredientItemValueMap.put(com.baisylia.culturaldelights.item.ModItems.CUT_EGGPLANT.get(), 20);
            ingredientItemValueMap.put(ModItems.PUMPKIN_SLICE.get(), 20);
            ingredientItemValueMap.put(Items.WHEAT, 20);
            ingredientItemValueMap.put(Items.NETHER_WART, 20);
            ingredientItemValueMap.put(Items.INK_SAC, 20);
            ingredientItemValueMap.put(Items.WARPED_ROOTS, 20);
            ingredientItemValueMap.put(Items.KELP, 20);
            ingredientItemValueMap.put(Items.BLAZE_POWDER, 20);
            ingredientItemValueMap.put(ItemRegistry.TORTILLA_CHIP.get(), 20);
            ingredientItemValueMap.put(Items.PUMPKIN, 20);
            ingredientItemValueMap.put(NeapolitanItems.DRIED_VANILLA_PODS.get(), 20);
            ingredientItemValueMap.put(NeapolitanItems.ICE_CUBES.get(), 20);

            ingredientItemValueMap.put(ItemRegistry.CORNBREAD.get(), 28);
            ingredientItemValueMap.put(Items.BAKED_POTATO, 28);
            ingredientItemValueMap.put(com.baisylia.culturaldelights.item.ModItems.TORTILLA_CHIPS.get(), 28);
            ingredientItemValueMap.put(Items.DRIED_KELP, 28);
            ingredientItemValueMap.put(ItemRegistry.CORNBREAD.get(), 28);
            ingredientItemValueMap.put(ModItems.COOKED_RICE.get(), 28);
            ingredientItemValueMap.put(SeasonalsItems.PUMPKIN_PUREE.get(), 80);

            ingredientItemValueMap.put(Items.GLOW_BERRIES, 36);
            ingredientItemValueMap.put(Items.APPLE, 36);
            ingredientItemValueMap.put(Items.SWEET_BERRIES, 36);
            ingredientItemValueMap.put(com.baisylia.culturaldelights.item.ModItems.CUT_AVOCADO.get(), 36);
            ingredientItemValueMap.put(com.baisylia.culturaldelights.item.ModItems.AVOCADO.get(), 36);
            ingredientItemValueMap.put(CRItems.LIMEADE.get(), 36);
            ingredientItemValueMap.put(CRItems.LIME_SLICE.get(), 36);
            ingredientItemValueMap.put(Items.MELON_SLICE, 36);
            ingredientItemValueMap.put(NeapolitanItems.BANANA.get(), 36);

            ingredientItemValueMap.put(BnCItems.FLAXEN_CHEESE_WEDGE.get(), 110);
            ingredientItemValueMap.put(BnCItems.FLAXEN_CHEESE_WHEEL.get(), 440);
            ingredientItemValueMap.put(Items.BROWN_MUSHROOM, 54);
            ingredientItemValueMap.put(BnCItems.SCARLET_CHEESE_WHEEL.get(), 440);
            ingredientItemValueMap.put(BnCItems.SCARLET_CHEESE_WEDGE.get(), 110);
            ingredientItemValueMap.put(Items.RED_MUSHROOM, 54);
            ingredientItemValueMap.put(Items.EGG, 54);
            ingredientItemValueMap.put(ModItems.FRIED_EGG.get(), 62);
            ingredientItemValueMap.put(ModItems.BROWN_MUSHROOM_COLONY.get(), 270);
            ingredientItemValueMap.put(Items.CRIMSON_FUNGUS, 72);
            ingredientItemValueMap.put(Items.WARPED_FUNGUS, 72);
            ingredientItemValueMap.put(Items.HONEYCOMB, 54);
            ingredientItemValueMap.put(CRItems.BAKED_PORTOBELLO_CAP.get(), 62);

            ingredientItemValueMap.put(Items.HONEY_BOTTLE, 72);

            ingredientItemValueMap.put(ModItems.HAM.get(), 96);
            ingredientItemValueMap.put(ModItems.MINCED_BEEF.get(), 96);
            ingredientItemValueMap.put(Items.RABBIT, 96);

            ingredientItemValueMap.put(CRItems.CLAM.get(), 300);
            ingredientItemValueMap.put(CRItems.PLATINUM_BASS_HEAD.get(), 144);
            ingredientItemValueMap.put(CRItems.CHIEFTAIN_CRAB_BUCKET.get(), 800);
        }

        if (ingredientTagKeyValueMap.isEmpty()) {
            ingredientTagKeyValueMap.put(ForgeTags.SEEDS, 20);
            ingredientTagKeyValueMap.put(ForgeTags.CROPS_TOMATO, 20);
            ingredientTagKeyValueMap.put(CornForgeTags.CROPS_CORN, 20);
            ingredientTagKeyValueMap.put(Tags.Items.BONES, 20);
            ingredientTagKeyValueMap.put(CornForgeTags.SEEDS_CORN, 20);
            ingredientTagKeyValueMap.put(ForgeTags.PASTA_RAW_PASTA, 20);
            ingredientTagKeyValueMap.put(ForgeTags.VEGETABLES_TOMATO, 20);
            ingredientTagKeyValueMap.put(ForgeTags.VEGETABLES, 20);
            ingredientTagKeyValueMap.put(ForgeTags.CROPS_RICE, 20);
            ingredientTagKeyValueMap.put(ForgeTags.VEGETABLES_ONION, 20);
            ingredientTagKeyValueMap.put(ModTags.WOLF_PREY, 20);
            ingredientTagKeyValueMap.put(ForgeTags.DOUGH, 20);
            ingredientTagKeyValueMap.put(ForgeTags.CROPS_CABBAGE, 20);
            ingredientTagKeyValueMap.put(ForgeTags.CROPS_ONION, 20);
            ingredientTagKeyValueMap.put(BnCTags.HORROR_MEATS, 20);
            ingredientTagKeyValueMap.put(ForgeTags.PASTA, 20);
            ingredientTagKeyValueMap.put(ForgeTags.VEGETABLES_POTATO, 20);
            ingredientTagKeyValueMap.put(CRItemTags.SEEDS_POMEGRANATE, 20);

            ingredientTagKeyValueMap.put(ForgeTags.BREAD, 28);

            ingredientTagKeyValueMap.put(ForgeTags.BERRIES, 36);
            ingredientTagKeyValueMap.put(CRItemTags.FRUITS_LIME, 36);
            ingredientTagKeyValueMap.put(CRItemTags.FRUITS_POMEGRANATE, 36);

            ingredientTagKeyValueMap.put(ForgeTags.EGGS, 54);
            ingredientTagKeyValueMap.put(Tags.Items.MUSHROOMS, 54);
            ingredientTagKeyValueMap.put(ForgeTags.MILK, 54);
            ingredientTagKeyValueMap.put(BnCTags.CHEESE_WEDGES, 54);
            ingredientTagKeyValueMap.put(ForgeTags.COOKED_EGGS, 62);

            ingredientTagKeyValueMap.put(ForgeTags.RAW_CHICKEN, 72);
            ingredientTagKeyValueMap.put(ForgeTags.COOKED_CHICKEN, 80);

            ingredientTagKeyValueMap.put(ForgeTags.RAW_MUTTON, 96);
            ingredientTagKeyValueMap.put(ForgeTags.RAW_PORK, 96);
            ingredientTagKeyValueMap.put(ForgeTags.RAW_BEEF, 96);

            ingredientTagKeyValueMap.put(ForgeTags.COOKED_PORK, 104);
            ingredientTagKeyValueMap.put(ForgeTags.COOKED_BEEF, 104);
            ingredientTagKeyValueMap.put(ForgeTags.COOKED_MUTTON, 104);

            ingredientTagKeyValueMap.put(CRItemTags.CRAB_MEAT, 144);
            ingredientTagKeyValueMap.put(CRItemTags.CRAB_LEG, 144);
            ingredientTagKeyValueMap.put(CRItemTags.RAW_CLAM, 144);
            ingredientTagKeyValueMap.put(ForgeTags.RAW_FISHES, 144);
            ingredientTagKeyValueMap.put(CRItemTags.COOKED_PRAWN, 152);
            ingredientTagKeyValueMap.put(ForgeTags.RAW_FISHES_COD, 144);
        }
    }

    private static List<CookingPotRecipe> cookingPotRecipes = new ArrayList<>();
    public static List<CookingPotRecipe> getCookingPotRecipes() {
        if (cookingPotRecipes.isEmpty()) {
            if (FMLLoader.getDist().isClient()) {
                cookingPotRecipes = ClientUtils.clientLevel.getRecipeManager()
                        .getRecipes().stream().filter(recipe -> recipe.getType().equals(ModRecipeTypes.COOKING.get()))
                        .filter(recipe -> recipe instanceof CookingPotRecipe)
                        .map(recipe -> (CookingPotRecipe) recipe)
                        .toList();
            } else {
                cookingPotRecipes = Tick.server.overworld().getRecipeManager()
                        .getRecipes().stream().filter(recipe -> recipe.getType().equals(ModRecipeTypes.COOKING.get()))
                        .filter(recipe -> recipe instanceof CookingPotRecipe)
                        .map(recipe -> (CookingPotRecipe) recipe)
                        .toList();
            }
        }
        return cookingPotRecipes;
    }

    public static Map<Item, List<Ingredient>> ingredientMap = new HashMap<>();
    public static List<Ingredient> getIngredients(Item item) {
        if (ingredientMap.isEmpty()) {
            getCookingPotRecipes().forEach(recipe -> {
                ingredientMap.put(recipe.getResultItem(RegistryAccess.EMPTY).getItem(), recipe.getIngredients());
            });
        }
        return ingredientMap.getOrDefault(item, List.of());
    }
}
