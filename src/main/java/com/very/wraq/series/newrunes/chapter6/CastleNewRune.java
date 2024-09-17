package com.very.wraq.series.newrunes.chapter6;

import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.UsageOrGetWayDescriptionItem;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.RuneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CastleNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public CastleNewRune(Properties properties) {
        super(properties);
        Utils.manaDamage.put(this, 200d);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("物法兼修").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 手持").withStyle(ChatFormatting.WHITE).
                append(Component.literal("剑").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("弓").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("会提供等同于").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamage("20%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.attackDamage("")));
        components.add(Component.literal(" 手持").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法杖").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("会提供等同于").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.attackDamage("40%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamage("")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfCastle;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfCastle();
    }

    @Override
    public int levelRequirement() {
        return 0;
    }


    public static boolean isOn(Player player) {
        return WraqCurios.isOn(CastleNewRune.class, player);
    }

    public static double attackDamage(Player player) {
        if (!isOn(player)) return 0;
        ItemStack itemStack = player.getMainHandItem();
        if (Utils.swordTag.containsKey(itemStack.getItem()) || Utils.bowTag.containsKey(itemStack.getItem())) {
            return PlayerAttributes.manaDamage(player) * 0.2;
        }
        return 0;
    }

    public static double manaDamage(Player player) {
        if (!isOn(player)) return 0;
        ItemStack itemStack = player.getMainHandItem();
        if (Utils.sceptreTag.containsKey(itemStack.getItem())) {
            return PlayerAttributes.attackDamage(player) * 0.4;
        }
        return 0;
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("通关").withStyle(ChatFormatting.WHITE).
                append(Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle)).
                append(Component.literal("概率获得").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
