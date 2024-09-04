package com.very.wraq.customized.uniform.element;

import com.very.wraq.common.Compute;
import com.very.wraq.customized.WraqUniformCurios;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class LifeCurios0 extends WraqElementUniformCurios {

    public LifeCurios0(Properties p_41383_) {
        super(p_41383_);
        Element.LifeElementValue.put(this, 1d);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Component.literal("生机蕴能").withStyle(style));
        components.add(Component.literal(" 提升").withStyle(ChatFormatting.WHITE).
                append(Element.Description.LifeElementValue("100%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfLife;
    }

    public static WeakHashMap<Player, Boolean> onPlayerMap = new WeakHashMap<>();

    public static boolean isOn(Player player) {
        return WraqUniformCurios.isOn(LifeCurios0.class, player);
    }

    public static double playerLifeElementValueEnhance(Player player) {
        if (!isOn(player)) return 1;
        return 2;
    }
}
