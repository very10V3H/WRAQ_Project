package com.very.wraq.process.system.smelt;

import com.very.wraq.common.Compute;
import com.very.wraq.common.fast.Te;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ItemAndRate;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public enum SmeltRecipe {

    TEST1(Te.m("测试配方1"),
            List.of(new ItemStack(ModItems.goldCoin.get(), 2), new ItemStack(ModItems.silverCoin.get(), 2)),
            List.of(new ItemStack(ModItems.PlainSoul.get(), 2), new ItemStack(ModItems.ForestSoul.get(), 2)),
            List.of(),
            0),

    TEST2(Te.m("测试配方1"),
            List.of(new ItemStack(ModItems.goldCoin.get(), 2), new ItemStack(ModItems.silverCoin.get(), 2)),
            List.of(new ItemStack(ModItems.PlainSoul.get(), 2), new ItemStack(ModItems.ForestSoul.get(), 2)),
            List.of(),
            1),

    TEST3(Te.m("测试配方1"),
            List.of(new ItemStack(ModItems.goldCoin.get(), 2), new ItemStack(ModItems.silverCoin.get(), 2)),
            List.of(new ItemStack(ModItems.PlainSoul.get(), 2), new ItemStack(ModItems.ForestSoul.get(), 2)),
            List.of(),
            2),

    TEST4(Te.m("测试配方1"),
            List.of(new ItemStack(ModItems.goldCoin.get(), 2), new ItemStack(ModItems.silverCoin.get(), 2)),
            List.of(new ItemStack(ModItems.PlainSoul.get(), 2), new ItemStack(ModItems.ForestSoul.get(), 2)),
            List.of(),
            3),

    TEST5(Te.m("测试配方1"),
            List.of(new ItemStack(ModItems.goldCoin.get(), 2), new ItemStack(ModItems.silverCoin.get(), 2)),
            List.of(new ItemStack(ModItems.PlainSoul.get(), 2), new ItemStack(ModItems.ForestSoul.get(), 2)),
            List.of(),
            4),

    TEST6(Te.m("测试配方2"),
            List.of(new ItemStack(ModItems.goldCoin.get())),
            List.of(new ItemStack(ModItems.PlainSoul.get())),
            List.of(),
            5);

    public final Component name;
    public final List<ItemStack> needMaterialList;
    public final List<ItemStack> productList;
    public final List<ItemAndRate> exProductList;
    public final int needMinutes;

    SmeltRecipe(Component name, List<ItemStack> needMaterialList, List<ItemStack> productList,
                List<ItemAndRate> exProductList, int needMinutes) {
        this.name = name;
        this.needMaterialList = needMaterialList;
        this.productList = productList;
        this.exProductList = exProductList;
        this.needMinutes = needMinutes;
    }

    public static SmeltRecipe getRecipeByIndex(int index) {
        return SmeltRecipe.values()[index];
    }

    public static void createSmeltProcess(Player player, int recipeIndex) {
        SmeltRecipe smeltRecipe = getRecipeByIndex(recipeIndex);
        if (Compute.checkPlayerHasItem(player, smeltRecipe.needMaterialList)) {
            if (Smelt.putSmeltSlotInfoToEmptySlot(player, recipeIndex, getMinutesLaterCalendar(smeltRecipe.needMinutes))) {
                // 成功
                Compute.removeItemWithoutCheck(player, smeltRecipe.needMaterialList);
                player.sendSystemMessage(Te.m("success"));
            } else {
                // 失败
                // 发包至客户端 在screen显示信息
                player.sendSystemMessage(Te.m("no empty slot"));
            }
        } else {
            player.sendSystemMessage(Te.m("miss material"));
        }
    }

    public static Calendar getMinutesLaterCalendar(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return calendar;
    }

    public List<Component> getDescription() {
        List<Component> description = new ArrayList<>();
        description.add(Te.m("").append(name));
        description.add(Te.m(" 所需时间: ", ChatFormatting.AQUA).append(Te.m(needMinutes + "min")));
        description.add(Te.m(""));
        description.add(Component.literal(" 产物:").withStyle(ChatFormatting.YELLOW));
        productList.forEach(stack -> {
            description.add(Te.m("  ").append(stack.getDisplayName()).append(Te.m(" * " + stack.getCount(), ChatFormatting.AQUA)));
        });
        description.add(Te.m(""));
        description.add(Te.m(" 需求材料:", ChatFormatting.GREEN));
        needMaterialList.forEach(stack -> {
            description.add(Te.m("  ").append(stack.getDisplayName()).append(Te.m(" * " + stack.getCount(), ChatFormatting.AQUA)));
        });
        return description;
    }
}
