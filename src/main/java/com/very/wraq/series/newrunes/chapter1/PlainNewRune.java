package com.very.wraq.series.newrunes.chapter1;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.events.mob.chapter1.PlainZombieSpawnController;
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

public class PlainNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public PlainNewRune(Properties properties) {
        super(properties);
        Utils.healthRecover.put(this, 5d);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getDefenceTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("复苏之风").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.healthRecover("已损失生命值1%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    public static boolean isOn(Player player) {
        return WraqCurios.isOn(PlainNewRune.class, player);
    }

    public static double playerHealthRecover(Player player) {
        if (isOn(player)) {
            return (player.getMaxHealth() - player.getHealth()) * 0.01;
        }
        return 0;
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        ;
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(PlainZombieSpawnController.mobName).withStyle(CustomStyle.styleOfPlain)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
