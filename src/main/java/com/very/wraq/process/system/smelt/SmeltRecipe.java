package com.very.wraq.process.system.smelt;

import com.very.wraq.common.fast.Te;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public enum SmeltRecipe {

    PLAIN_COMPLETE_GEM(Te.m("普莱尼水晶", CustomStyle.styleOfPlain),
            List.of(new ItemStack(ModItems.PlainBossSoul.get(), 64),
                    new ItemStack(ModItems.completeGem.get(), 1),
                    new ItemStack(ModItems.goldCoin.get(), 1)),
            List.of(new ItemStack(ModItems.PlainCompleteGem.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.PlainCompleteGem.get(), 1), 0.25)),
            15),

    FOILED_NETHER_IMPRINT(Te.m("注魔燃魂印记", CustomStyle.styleOfNether),
            List.of(new ItemStack(ModItems.NETHER_IMPRINT.get(), 16),
                    new ItemStack(ModItems.EvokerSoul.get(), 64),
                    new ItemStack(ModItems.goldCoin.get(), 1)),
            List.of(new ItemStack(ModItems.FOILED_NETHER_IMPRINT.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.Ruby.get(), 16), 1)),
            30),

    PURPLE_IRON_BUD2(Te.m("紫晶矿簇", CustomStyle.styleOfPurpleIron),
            List.of(new ItemStack(ModItems.PurpleIronBud1.get(), 48),
                    new ItemStack(ModItems.goldCoin.get(), 1)),
            List.of(new ItemStack(ModItems.PurpleIronBud2.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.PurpleIronBud2.get(), 1), 0.5)),
            45),

    ICE_COMPLETE_GEM(Te.m("冰霜水晶", CustomStyle.styleOfIce),
            List.of(new ItemStack(ModItems.IceSoul.get(), 64),
                    new ItemStack(ModItems.completeGem.get(), 1),
                    new ItemStack(ModItems.goldCoin.get(), 1)),
            List.of(new ItemStack(ModItems.IceCompleteGem.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.IceCompleteGem.get(), 1), 0.25)),
            60),

    GOLDEN_SHEET(Te.m("大金板", CustomStyle.styleOfGold),
            List.of(new ItemStack(ModItems.Boss2Piece.get(), 32),
                    new ItemStack(ModItems.goldCoin.get(), 32)),
            List.of(new ItemStack(ModItems.GOLDEN_SHEET.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(ModItems.goldCoin.get(), 96), 0.5)),
            75),

    DEVIL_BLOOD(Te.m("魔王之血", CustomStyle.styleOfDemon),
            List.of(new ItemStack(ModItems.DevilAttackSoul.get(), 8),
                    new ItemStack(ModItems.DevilSwiftSoul.get(), 8),
                    new ItemStack(ModItems.DevilManaSoul.get(), 8),
                    new ItemStack(ModItems.goldCoin.get(), 16)),
            List.of(new ItemStack(ModItems.DevilBlood.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(ModItems.DevilBlood.get(), 1), 0.25)),
            90),

    MOON_COMPLETE_GEM(Te.m("尘月水晶", CustomStyle.styleOfMoon),
            List.of(new ItemStack(ModItems.MoonSoul.get(), 64),
                    new ItemStack(ModItems.completeGem.get(), 2),
                    new ItemStack(ModItems.goldCoin.get(), 16)),
            List.of(new ItemStack(ModItems.MoonCompleteGem.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.MoonCompleteGem.get(), 1), 0.25)),
            105);

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
        if (InventoryOperation.checkPlayerHasItem(player, smeltRecipe.needMaterialList)) {
            if (Smelt.putSlotInfoToEmptySlot(player, recipeIndex, getMinutesLaterCalendar(smeltRecipe.needMinutes))) {
                // 成功
                InventoryOperation.removeItemWithoutCheck(player, smeltRecipe.needMaterialList);

            } else {
                // 失败
                // 发包至客户端 在screen显示信息

            }
        } else {
            // 背包中所需材料不足
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
        if (!exProductList.isEmpty()) {
            description.add(Te.m(""));
            description.add(Te.m(" 副产物表:", ChatFormatting.AQUA));
            exProductList.forEach(itemAndRate -> {
                description.add(Te.m("  ").append(itemAndRate.getItemStack().getDisplayName())
                        .append(Te.m(" * " + itemAndRate.getItemStack().getCount(), ChatFormatting.AQUA))
                        .append(Te.m(" [", ChatFormatting.AQUA))
                        .append(Te.m(String.format("%.0f%%", itemAndRate.getRate() * 100)))
                        .append(Te.m("]", ChatFormatting.AQUA)));
            });
        }
        return description;
    }
}
