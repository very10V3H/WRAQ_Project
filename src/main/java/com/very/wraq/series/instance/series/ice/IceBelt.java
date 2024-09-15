package com.very.wraq.series.instance.series.ice;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.RandomCurios;
import com.very.wraq.projectiles.UsageOrGetWayDescriptionItem;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.series.castle.CastleCurios;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IceBelt extends WraqCurios implements RandomCurios, UsageOrGetWayDescriptionItem {

    public IceBelt(Properties properties) {
        super(properties);
        Element.IceElementValue.put(this, 0.5);
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("通过击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal("冰霜骑士").withStyle(CustomStyle.styleOfIce)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("基础属性为随机六词条").withStyle(ChatFormatting.WHITE).
                append(Component.literal("综合型").withStyle(CustomStyle.styleOfIce)).
                append(Component.literal("属性").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getTypeDescription() {
        return Component.literal("").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getComprehensiveTypeDescriptionOfCurios()).
                append(Component.literal(" v = 6 * " + BigDecimal.valueOf(rate()).stripTrailingZeros()).withStyle(CustomStyle.styleOfIce));
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfIce();
    }

    @Override
    public int levelRequirement() {
        return 135;
    }


    @Override
    public void setAttribute(ItemStack stack) {
        CastleCurios.randomAttackAttributeProvide(stack, 2, rate());
        CastleCurios.randomDefenceAttributeProvide(stack, 2, rate());
        CastleCurios.randomFunctionAttributeProvide(stack, 2, rate());
    }

    @Override
    public double rate() {
        return 0.7;
    }
}
