package com.very.wraq.process.system.teamInstance.instances.blackCastle;

import com.very.wraq.projectiles.RandomCurios;
import com.very.wraq.projectiles.UsageOrGetWayDescriptionItem;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.series.castle.CastleCurios;
import com.very.wraq.common.Utils.ComponentUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CastleNecklace extends WraqCurios implements RandomCurios, UsageOrGetWayDescriptionItem {
    public CastleNecklace(Properties properties) {
        super(properties);
    }

    @Override
    public Component getTypeDescription() {
        return Component.literal("").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getAllTypeDescriptionOfCurios()).
                append(Component.literal(" v = 6 * 1").withStyle(CustomStyle.styleOfCastleCrystal));
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        CastleCurios.randomPassiveText(components, stack);
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfCastle;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfCastle();
    }

    @Override
    public int levelRequirement() {
        return 180;
    }

    @Override
    public boolean isArbitrarily() {
        return false;
    }

    @Override
    public void setAttribute(ItemStack stack) {
        CastleCurios.randomAttributeProvide(stack, 6, 1);
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("完成副本 - ").withStyle(ChatFormatting.WHITE).
                append(Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("基础属性为随机六词条").withStyle(ChatFormatting.WHITE).
                append(Component.literal("全能型").withStyle(CustomStyle.styleOfSakura)).
                append(Component.literal("属性").withStyle(ChatFormatting.WHITE)));
        return components;
    }
}
