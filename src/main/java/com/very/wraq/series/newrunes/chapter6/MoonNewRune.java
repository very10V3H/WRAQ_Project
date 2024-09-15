package com.very.wraq.series.newrunes.chapter6;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.UsageOrGetWayDescriptionItem;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.NewRuneItems;
import com.very.wraq.series.newrunes.RuneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MoonNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public MoonNewRune(Properties properties) {
        super(properties);
        Utils.coolDownDecrease.put(this, 0.15);
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal(" 通关").withStyle(ChatFormatting.WHITE).append(Component.literal("阿尔忒弥斯").withStyle(CustomStyle.styleOfMoon)).append(Component.literal("概率获得").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("明镜止水").withStyle(CustomStyle.styleOfMoon));
        components.add(Component.literal(" 快捷栏4 ~ 9").withStyle(CustomStyle.styleOfMoon).append(Component.literal("每存在1件可以").withStyle(ChatFormatting.WHITE)).append(Component.literal("主动释放但未在冷却时间的").withStyle(ChatFormatting.AQUA)).append(Component.literal("物品").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 为你提供").withStyle(ChatFormatting.WHITE).append(Component.literal("6%伤害提升").withStyle(CustomStyle.styleOfPower)));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMoon;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfMoon();
    }

    @Override
    public int levelRequirement() {
        return 0;
    }


    public static int getPassiveCount(Player player) {
        if (!WraqCurios.isOn(MoonNewRune.class, player)) return 0;
        int count = 0;
        for (int i = 0; i < 6; i++) {
            ItemStack itemStack = player.getInventory().getItem(3 + i);
            Item item = itemStack.getItem();
            if (item instanceof ActiveItem) {
                if (!player.getCooldowns().isOnCooldown(item)) count++;
            }
        }
        return count;
    }

    public static void tick(Player player) {
        int count = getPassiveCount(player);
        if (count > 0) {
            Compute.sendEffectLastTime(player, NewRuneItems.moonNewRune.get(), count, true);
        } else Compute.removeEffectLastTime(player, NewRuneItems.moonNewRune.get());
    }

    public static double damageEnhance(Player player) {
        int count = getPassiveCount(player);
        return count * 0.06;
    }
}
