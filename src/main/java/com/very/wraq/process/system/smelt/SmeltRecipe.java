package com.very.wraq.process.system.smelt;

import com.very.wraq.common.util.ItemAndRate;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Calendar;
import java.util.List;

public enum SmeltRecipe {

    TEST(List.of(), List.of(), List.of(), 0);

    public final List<ItemStack> needMaterialList;
    public final List<ItemStack> productList;
    public final List<ItemAndRate> exProductList;
    public final int needMinutes;

    SmeltRecipe(List<ItemStack> needMaterialList, List<ItemStack> productList,
                List<ItemAndRate> exProductList, int needMinutes) {
        this.needMaterialList = needMaterialList;
        this.productList = productList;
        this.exProductList = exProductList;
        this.needMinutes = needMinutes;
    }

    public static SmeltRecipe getRecipeByIndex(int index) {
        return SmeltRecipe.values()[index];
    }

    public static void createSmeltProcess(Player player, int recipeIndex) {
        if (Smelt.putSmeltSlotInfoToEmptySlot(player, recipeIndex,
                getMinutesLaterCalendar(getRecipeByIndex(recipeIndex).needMinutes))) {
            // 成功

        } else {
            // 失败

        }
    }

    public static Calendar getMinutesLaterCalendar(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return calendar;
    }


}
