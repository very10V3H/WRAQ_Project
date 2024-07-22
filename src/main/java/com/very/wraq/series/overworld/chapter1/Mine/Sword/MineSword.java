package com.very.wraq.series.overworld.chapter1.Mine.Sword;

import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MineSword extends WraqSword {

    private final int tier;
    public MineSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{50, 55, 60, 70}[tier]);
        Utils.defencePenetration0.put(this, new double[]{350, 400, 450, 500}[tier]);
        Utils.healthSteal.put(this, 0.04);
        Utils.critRate.put(this, new double[]{0.55, 0.6, 0.65, 0.7}[tier]);
        Utils.critDamage.put(this, new double[]{0.15, 0.2, 0.25, 0.3}[tier]);
        Element.StoneElementValue.put(this, 0.2);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMine;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("凿击").withStyle(ChatFormatting.GRAY));
        Compute.DescriptionNum(components, "攻击将会降低目标生物的移动速度", Component.literal("2s").withStyle(ChatFormatting.GRAY), "");
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    public int getWraqTier() {
        return tier;
    }
}
