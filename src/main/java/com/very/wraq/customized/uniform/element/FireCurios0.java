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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireCurios0 extends WraqElementUniformCurios {

    public FireCurios0(Properties p_41383_) {
        super(p_41383_);
        Element.FireElementValue.put(this, 1d);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Component.literal("炽焰蕴能").withStyle(style));
        components.add(Component.literal(" 提升").withStyle(ChatFormatting.WHITE).
                append(Element.Description.FireElementValue("100%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfFire;
    }

    public static Map<Player, Boolean> onPlayerMap = new HashMap<>();

    public static boolean isOn(Player player) {
        return WraqUniformCurios.isOn(FireCurios0.class, player);
    }

    public static double playerFireElementValueEnhance(Player player) {
        if (!isOn(player)) return 1;
        return 2;
    }
}
