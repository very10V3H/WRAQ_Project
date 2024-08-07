package com.very.wraq.customized.uniform.attack;

import com.very.wraq.customized.uniform.Attributes;
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
import java.util.List;

public class AttackCurios0 extends WraqUniformCurios {

    public AttackCurios0(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, Attributes.AttackDamage);
        Utils.defencePenetration0.put(this, Attributes.DefencePenetration0);
        Utils.critDamage.put(this, Attributes.CritDamage);
        Utils.defence.put(this, Attributes.Defence);
        Utils.critRate.put(this, Attributes.CritRate);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfPower;
        Compute.DescriptionPassive(components, Component.literal("君临天下").withStyle(style));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("50%最终伤害提升").withStyle(ChatFormatting.RED)));
        Compute.DescriptionPassive(components, Component.literal("暴政").withStyle(style));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("15%总")));
        components.add(Component.literal(" 残暴的君主，终将被民众推翻。").withStyle(style));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPower;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getUniformSuffix();
    }

    public static double playerFinalDamageEnhance(Player player) {
        if (!WraqUniformCurios.isOn(AttackCurios0.class, player)) return 0;
        return 0.5;
    }

    public static double PlayerFinalCritDamageEnhance(Player player) {
        if (!WraqUniformCurios.isOn(AttackCurios0.class, player)) return 1;
        return 1.15;
    }
}
