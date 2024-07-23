package com.very.wraq.series.specialevents.summer;

import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.process.system.forge.ForgeEquipUtils;
import com.very.wraq.projectiles.WraqCurios;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SummerCurios2024 extends WraqCurios {

    private final int tier;
    public SummerCurios2024(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return ForgeEquipUtils.tierStyle.get(tier);
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSummerEvent();
    }

    @Override
    public int levelRequirement() {
        return new int[]{60, 90, 120, 150, 180, 210}[tier];
    }

    @Override
    public boolean isArbitrarily() {
        return false;
    }
}
