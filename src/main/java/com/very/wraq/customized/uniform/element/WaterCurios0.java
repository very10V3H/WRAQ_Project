package com.very.wraq.customized.uniform.element;

import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.element.RainbowCrystal;
import com.very.wraq.projectiles.WraqUniformCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaterCurios0 extends WraqUniformCurios {

    public WaterCurios0(Properties p_41383_) {
        super(p_41383_);
        Utils.defence.put(this, 500d);
        Utils.manaDefence.put(this, 500d);
        Utils.defencePenetration.put(this, 0.2);
        Utils.manaPenetration.put(this, 0.2);
        Utils.coolDownDecrease.put(this, 0.25);
        Element.WaterElementValue.put(this, 1d);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, RainbowCrystal.rainBowNameFourChar("世界根基"));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("50%最终伤害加成").withStyle(ChatFormatting.RED)));
        Compute.DescriptionPassive(components, Component.literal("碧水蕴能").withStyle(style));
        components.add(Component.literal(" 提升").withStyle(ChatFormatting.WHITE).
                append(Element.Description.WaterElementValue("100%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfWater;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getUniformSuffix();
    }

    public static Map<Player, Boolean> onPlayerMap = new HashMap<>();

    public static boolean isOn(Player player) {
        return WraqUniformCurios.isOn(WaterCurios0.class, player);
    }

    public static double playerFinalDamageEnhance(Player player) {
        if (!isOn(player)) return 0;
        return 0.5;
    }

    public static double playerWaterElementValueEnhance(Player player) {
        if (!isOn(player)) return 1;
        return 2;
    }
}
