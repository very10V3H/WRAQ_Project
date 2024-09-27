package com.very.wraq.series.overworld.chapter1.Snow.Sword;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SnowSword extends WraqSword {
    public SnowSword(Properties p_42964_, int tier) {
        super(p_42964_);
        Utils.attackDamage.put(this, new double[]{100, 105, 110}[tier]);
        Utils.defencePenetration0.put(this, new double[]{6, 7, 8}[tier]);
        Utils.healthSteal.put(this, new double[]{0.02, 105, 110}[tier]);
        Utils.critRate.put(this, new double[]{0.3, 0.33, 0.36}[tier]);
        Utils.critDamage.put(this, new double[]{0.4, 0.45, 0.5}[tier]);
        Utils.movementSpeedWithoutBattle.put(this, new double[]{0.1, 105, 110}[tier]);
        Element.IceElementValue.put(this, 0.2);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSnow;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("凿击-Ex").withStyle(ChatFormatting.AQUA));
        ComponentUtils.descriptionNum(components, "攻击将会大幅降低目标生物的移动速度", Component.literal("2s").withStyle(ChatFormatting.AQUA), "");
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }
}
