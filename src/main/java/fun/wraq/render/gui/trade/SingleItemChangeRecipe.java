package fun.wraq.render.gui.trade;

import fun.wraq.process.system.endlessinstance.EndlessCoreScreen;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record SingleItemChangeRecipe(ItemStack needStack, ItemStack goods, String limitType, int limitTimes) {
    public static List<SingleItemChangeRecipe> recipeList = new ArrayList<>();

    public static List<SingleItemChangeRecipe> getRecipeList() {
        if (recipeList.isEmpty()) {
            recipeList.addAll(
                    EndlessCoreScreen.recipeList
            );
        }
        return recipeList;
    }

    public String getDataKey() {
        return needStack().getItem() + "#" + needStack().getCount() + "#"
                + goods().getItem() + "#" + goods().getCount() + "#" + limitType + "#";
    }
}