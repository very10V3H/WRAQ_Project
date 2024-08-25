package com.very.wraq.events.mob.instance.item;

import com.very.wraq.projectiles.RandomCurios;
import com.very.wraq.projectiles.UsageOrGetWayDescriptionItem;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.castle.CastleCurios;
import com.very.wraq.common.Utils.ComponentUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlainNecklace extends WraqCurios implements RandomCurios, UsageOrGetWayDescriptionItem {

    public PlainNecklace(Properties properties) {
        super(properties);
    }

    @Override
    public Component getTypeDescription() {
        return Component.literal("").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getFuncTypeDescriptionOfCurios()).
                append(Component.literal(" v = 3 * 0.4").withStyle(CustomStyle.styleOfWorld));
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public int levelRequirement() {
        return 50;
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普莱尼").withStyle(CustomStyle.styleOfPlain)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("基础属性为随机三词条").withStyle(ChatFormatting.WHITE).
                append(Component.literal("功能型").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("属性").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public boolean isArbitrarily() {
        return false;
    }

    @Override
    public void setAttribute(ItemStack stack) {
        CastleCurios.randomFunctionAttributeProvide(stack, 3, 0.4);
    }
}
