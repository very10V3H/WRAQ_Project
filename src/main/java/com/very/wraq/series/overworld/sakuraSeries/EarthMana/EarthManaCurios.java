package com.very.wraq.series.overworld.sakuraSeries.EarthMana;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EarthManaCurios extends WraqCurios {

    public EarthManaCurios(Properties p_41383_, int tier) {
        super(p_41383_);
        Utils.manaDefence.put(this, tier == 0 ? 500d : 800d);
        Utils.manaDamage.put(this, tier == 0 ? 200d : 350d);
        Utils.manaHealthSteal.put(this, tier == 0 ? 0.05 : 0.08);
        Utils.manaRecover.put(this, tier == 0 ? 15d : 25d);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
    }

    @Override
    public Component getTypeDescription() {
        return null;
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("旧世仁慈魔浴").withStyle(style));
        components.add(Component.literal(" 当你受到").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamageValue("")).
                append(Component.literal("时，为你提供持续3s的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.healthRecover("8%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Sakura();
    }
}
