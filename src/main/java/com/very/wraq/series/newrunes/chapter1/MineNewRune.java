package com.very.wraq.series.newrunes.chapter1;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.events.mob.chapter1.MineSkeletonSpawnController;
import com.very.wraq.projectiles.UsageOrGetWayDescriptionItem;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.NewRuneItems;
import com.very.wraq.series.newrunes.RuneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MineNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public MineNewRune(Properties properties) {
        super(properties);
        Utils.movementSpeedWithoutBattle.put(this, -0.1);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("致密金属").withStyle(style));
        components.add(Component.literal(" 拥有高于").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.Health("60%")).
                append(Component.literal("时，为你提供:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.").withStyle(style).
                append(Component.literal("减少15%受到的伤害").withStyle(style)));
        components.add(Component.literal(" 2.").withStyle(style).
                append(Component.literal("12%伤害提升").withStyle(CustomStyle.styleOfPower)));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMine;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public int levelRequirement() {
        return 0;
    }


    public static boolean passiveTrig(Player player) {
        return WraqCurios.isOn(MineNewRune.class, player) && player.getHealth() / player.getMaxHealth() >= 0.6;
    }

    public static void tick(Player player) {
        if (player.tickCount % 20 == 8)
            if (passiveTrig(player)) {
                Compute.sendEffectLastTime(player, NewRuneItems.mineNewRune.get(), 0, true);
            } else {
                Compute.removeEffectLastTime(player, NewRuneItems.mineNewRune.get());
            }
    }

    public static double withstandDamageInfluence(Player player) {
        return passiveTrig(player) ? -0.15 : 0;
    }

    public static double damageEnhance(Player player) {
        return passiveTrig(player) ? 0.12 : 0;
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(MineSkeletonSpawnController.mobName).withStyle(CustomStyle.styleOfMine)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}

