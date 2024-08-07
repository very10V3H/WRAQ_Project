package com.very.wraq.customized.uniform.bow;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.projectiles.WraqUniformCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BowCurios0 extends WraqUniformCurios {

    public BowCurios0(Properties p_41383_) {
        super(p_41383_);
        Utils.attackDamage.put(this, Attributes.AttackDamage);
        Utils.defencePenetration0.put(this, Attributes.DefencePenetration0);
        Utils.critDamage.put(this, Attributes.CritDamage);
        Utils.defence.put(this, Attributes.Defence);
        Utils.critRate.put(this, Attributes.CritRate);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Component.literal("自然选择").withStyle(style));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("50%最终伤害提升").withStyle(ChatFormatting.RED)));
        Compute.DescriptionPassive(components, Component.literal("闪避突袭").withStyle(style));
        components.add(Component.literal(" 使用").withStyle(ChatFormatting.WHITE).
                append(Component.literal("翻滚").withStyle(style)).
                append(Component.literal("后，获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Swiftness("15%总")).
                append(Component.literal("，并使").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("箭矢").withStyle(style)).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1.5倍").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("基础伤害").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 不仅是敏捷，力量、智慧对在恶劣环境中的猎手同样重要。").withStyle(style));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfFlexible;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getUniformSuffix();
    }

    public static Map<Player, Boolean> onPlayerMap = new HashMap<>();

    public static boolean IsOn(Player player) {
        return WraqUniformCurios.isOn(BowCurios0.class, player);
    }

    public static double playerFinalDamageEnhance(Player player) {
        if (!IsOn(player)) return 0;
        return 0.5;
    }

    public static int activeLastTick = 0;

    public static void Active(Player player) {
        if (!IsOn(player)) return;
        activeLastTick += player.getServer().getTickCount() + 60;
        Compute.effectLastTimeSend(player, ModItems.BowCurios0.get().getDefaultInstance(), 60);
    }

    public static double BaseDamageEnhance(Player player) {
        if (!IsOn(player)) return 1;
        if (activeLastTick > player.getServer().getTickCount()) return 1.5;
        return 1;
    }

    public static double SwiftnessUp(Player player) {
        if (!IsOn(player)) return 1;
        if (activeLastTick > player.getServer().getTickCount()) return 1.15;
        return 1;
    }
}
