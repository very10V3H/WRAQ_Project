package com.very.wraq.events.mob.instance.item;

import com.very.wraq.projectiles.RandomCurios;
import com.very.wraq.projectiles.UsageOrGetWayDescriptionItem;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.series.castle.CastleCurios;
import com.very.wraq.common.util.ComponentUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NetherHand extends WraqCurios implements RandomCurios, UsageOrGetWayDescriptionItem {

    public NetherHand(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("通过击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal("燃魂").withStyle(hoverMainStyle())).
                append(Component.literal("概率获取").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("基础属性为随机三词条").withStyle(ChatFormatting.WHITE).
                append(Component.literal("进攻型").withStyle(hoverMainStyle())).
                append(Component.literal("属性").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getTypeDescription() {
        return Component.literal("").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getAttackTypeDescriptionOfCurios()).
                append(Component.literal(" v = 3 * 0.6").withStyle(CustomStyle.styleOfPower));
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPower;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixChapterIII();
    }

    @Override
    public int levelRequirement() {
        return 90;
    }

    @Override
    public boolean isArbitrarily() {
        return false;
    }

    @Override
    public void setAttribute(ItemStack stack) {
        CastleCurios.randomAttackAttributeProvide(stack, 3, 0.6);
    }
}
