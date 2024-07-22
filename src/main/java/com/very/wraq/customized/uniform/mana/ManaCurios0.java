package com.very.wraq.customized.uniform.mana;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManaCurios0 extends WraqUniformCurios {

    public ManaCurios0(Properties p_41383_) {
        super(p_41383_);
        Utils.manaDamage.put(this, Attributes.ManaDamage);
        Utils.defence.put(this, Attributes.Defence);
        Utils.manaPenetration0.put(this, Attributes.ManaPenetration0);
        Utils.manaRecover.put(this, Attributes.ManaRecover);
        Utils.maxMana.put(this, Attributes.MaxMana);
        Utils.coolDownDecrease.put(this, Attributes.CoolDown);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Component.literal("法术扩频").withStyle(style));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("50%最终伤害提升").withStyle(ChatFormatting.RED)));
        Compute.DescriptionPassive(components, Component.literal("法术调幅").withStyle(style));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("35%总")));
        components.add(Component.literal(" 法术的研究者，也是亚瑟王的挚友和导师——梅林，给予新生法师的礼物。").withStyle(style));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getUniformSuffix();
    }

    public static Map<Player, Boolean> onPlayerMap = new HashMap<>();

    public static boolean IsOn(Player player) {
        return WraqUniformCurios.isOn(ManaCurios0.class, player);
    }

    public static double playerFinalDamageEnhance(Player player) {
        if (!IsOn(player)) return 0;
        return 0.5;
    }

    public static double PlayerFinalManaDamageEnhance(Player player) {
        if (!IsOn(player)) return 1;
        return 1.35;
    }
}
