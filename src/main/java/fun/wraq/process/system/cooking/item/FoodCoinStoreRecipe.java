package fun.wraq.process.system.cooking.item;

import fun.wraq.process.system.cooking.CookingItems;
import fun.wraq.render.gui.trade.SingleItemChangePurchaseLimit;
import fun.wraq.render.gui.trade.SingleItemChangeRecipe;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FoodCoinStoreRecipe {
    public static List<SingleItemChangeRecipe> recipes = new ArrayList<>() {{
        add(new SingleItemChangeRecipe(new ItemStack(CookingItems.FOOD_COIN.get(), 5),
                new ItemStack(CookingItems.FOOD_BIG_COIN.get()), SingleItemChangePurchaseLimit.Type.DAILY, 4));
        add(new SingleItemChangeRecipe(new ItemStack(CookingItems.FOOD_BIG_COIN.get(), 12),
                new ItemStack(CookingItems.FOOD_MEDAL_0.get()), SingleItemChangePurchaseLimit.Type.NULL, 0));
        add(new SingleItemChangeRecipe(new ItemStack(CookingItems.FOOD_COIN.get(), 1),
                new ItemStack(CookingItems.FOOD_PACKAGE_0.get()), SingleItemChangePurchaseLimit.Type.NULL, 0));
        add(new SingleItemChangeRecipe(new ItemStack(CookingItems.FOOD_COIN.get(), 2),
                new ItemStack(CookingItems.FOOD_PACKAGE_1.get()), SingleItemChangePurchaseLimit.Type.NULL, 0));
        add(new SingleItemChangeRecipe(new ItemStack(CookingItems.FOOD_COIN.get(), 3),
                new ItemStack(CookingItems.FOOD_PACKAGE_2.get()), SingleItemChangePurchaseLimit.Type.NULL, 0));
        add(new SingleItemChangeRecipe(new ItemStack(CookingItems.FOOD_COIN.get(), 5),
                new ItemStack(CookingItems.FOOD_PACKAGE_3.get()), SingleItemChangePurchaseLimit.Type.NULL, 0));
        add(new SingleItemChangeRecipe(new ItemStack(CookingItems.MUSHROOM_SPLIT_GEM_1.get(), 2),
                new ItemStack(CookingItems.MUSHROOM_SPLIT_GEM_1.get()),
                SingleItemChangePurchaseLimit.Type.DAILY, 0));
        add(new SingleItemChangeRecipe(new ItemStack(CookingItems.MUSHROOM_SPUTTER_GEM_1.get(), 2),
                new ItemStack(CookingItems.MUSHROOM_SPUTTER_GEM_1.get()),
                SingleItemChangePurchaseLimit.Type.DAILY, 0));
        add(new SingleItemChangeRecipe(new ItemStack(CookingItems.MUSHROOM_PARASITISM_GEM_1.get(), 2),
                new ItemStack(CookingItems.MUSHROOM_PARASITISM_GEM_1.get()),
                SingleItemChangePurchaseLimit.Type.DAILY, 0));
    }};
}
