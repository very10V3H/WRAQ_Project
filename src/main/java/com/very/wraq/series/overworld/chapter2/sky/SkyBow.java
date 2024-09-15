package com.very.wraq.series.overworld.chapter2.sky;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkyBow extends WraqBow {

    public SkyBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 120d);
        Utils.defencePenetration0.put(this, 600d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 0.35);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
        Element.WindElementValue.put(this, 0.8);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSky;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("鹰隼之速").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal("每").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.ExMovementSpeed("1%")).
                append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.DefencePenetration("5")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterII();
    }

}
