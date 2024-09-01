package com.very.wraq.customized.uniform.element;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.element.RainbowCrystal;
import com.very.wraq.projectiles.WraqUniformCurios;
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

public class StoneCurios0 extends WraqElementUniformCurios {

    public StoneCurios0(Properties p_41383_) {
        super(p_41383_);
        Element.StoneElementValue.put(this, 1d);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, RainbowCrystal.rainBowNameFourChar("世界根基"));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("50%最终伤害加成").withStyle(ChatFormatting.RED)));
        Compute.DescriptionPassive(components, Component.literal("层岩蕴能").withStyle(style));
        components.add(Component.literal(" 提升").withStyle(ChatFormatting.WHITE).
                append(Element.Description.StoneElementValue("100%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfStone;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getUniformSuffix();
    }

    public static Map<Player, Boolean> onPlayerMap = new HashMap<>();

    public static boolean isOn(Player player) {
        return WraqUniformCurios.isOn(StoneCurios0.class, player);
    }

    public static double playerFinalDamageEnhance(Player player) {
        if (!isOn(player)) return 0;
        return 0.5;
    }

    public static double playerStoneElementValueEnhance(Player player) {
        if (!isOn(player)) return 1;
        return 2;
    }
}
