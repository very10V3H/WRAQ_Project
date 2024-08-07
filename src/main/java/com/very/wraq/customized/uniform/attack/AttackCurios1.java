package com.very.wraq.customized.uniform.attack;

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

public class AttackCurios1 extends WraqUniformCurios {

    public AttackCurios1(Properties properties) {
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
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Component.literal("独夫之心").withStyle(style));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("50%最终伤害提升").withStyle(ChatFormatting.RED)));
        Compute.DescriptionPassive(components, Component.literal("横行").withStyle(style));
        components.add(Component.literal(" 暴击").withStyle(style).
                append(Component.literal("将会获得一层").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("暴怒").withStyle(style)).
                append(Component.literal("，每层持续5s，至多叠加至5层").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 每层").withStyle(ChatFormatting.WHITE).
                append(Component.literal("暴怒").withStyle(style)).
                append(Component.literal("为你提供:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("20%")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("5%总")));
        components.add(Component.literal(" 只有近战攻击的暴击能够触发横行").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
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

    public static Map<Player, Integer> playerCountsMap = new HashMap<>();
    public static Map<Player, Integer> playerLastTickMap = new HashMap<>();

    public static boolean isOn(Player player) {
        return WraqUniformCurios.isOn(AttackCurios1.class, player);
    }

    public static double playerFinalDamageEnhance(Player player) {
        if (!isOn(player)) return 0;
        return 0.5;
    }

    public static void playerCritEffect(Player player) {
        if (!isOn(player)) return;
        if (!playerLastTickMap.containsKey(player) || playerLastTickMap.get(player) < player.getServer().getTickCount()) {
            playerCountsMap.put(player, 0);
        }
        playerLastTickMap.put(player, player.getServer().getTickCount() + 100);
        int counts = Math.min(5, playerCountsMap.getOrDefault(player, 0) + 1);
        playerCountsMap.put(player, counts);
        Compute.effectLastTimeSend(player, ModItems.AttackCurios1.get(), 100, counts);
    }

    public static double playerCritDamageUp(Player player) {
        if (!isOn(player)) return 0;
        if (playerLastTickMap.getOrDefault(player, 0) > player.getServer().getTickCount()) {
            return 0.2 * playerCountsMap.getOrDefault(player, 0);
        }
        return 0;
    }

    public static double playerAttackDamageEnhance(Player player) {
        if (!isOn(player)) return 1;
        if (playerLastTickMap.getOrDefault(player, 0) > player.getServer().getTickCount()) {
            return 1 + 0.05 * playerCountsMap.getOrDefault(player, 0);
        }
        return 1;
    }
}
