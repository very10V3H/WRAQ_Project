package com.very.wraq.series.overworld.chapter1.forest.bow;

import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForestBow extends WraqBow {
    private final int Num;

    public ForestBow(Properties p_40524_, int Num) {
        super(p_40524_);
        this.Num = Num;
        Utils.attackDamage.put(this, ForestBowAttributes.BaseDamage[Num]);
        Utils.defencePenetration0.put(this, ForestBowAttributes.DefencePenetration0[Num]);
        Utils.critRate.put(this, ForestBowAttributes.CriticalRate[Num]);
        Utils.critDamage.put(this, ForestBowAttributes.CriticalDamage[Num]);
        Element.LifeElementValue.put(this, ForestBowAttributes.LifeElementValue[Num]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfForest;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("森林祝福").withStyle(ChatFormatting.DARK_GREEN));
        components.add(Component.literal("箭矢命中目标后治疗自身").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("5%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }
}
