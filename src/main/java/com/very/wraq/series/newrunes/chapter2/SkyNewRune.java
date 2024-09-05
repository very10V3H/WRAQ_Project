package com.very.wraq.series.newrunes.chapter2;

import com.very.wraq.events.mob.chapter2.SkyVexSpawnController;
import com.very.wraq.projectiles.UsageOrGetWayDescriptionItem;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.NewRuneItems;
import com.very.wraq.series.newrunes.RuneItem;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkyNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public SkyNewRune(Properties properties) {
        super(properties);
        Utils.critDamage.put(this, 0.1);
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal(" 击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(SkyVexSpawnController.mobName).withStyle(CustomStyle.styleOfSky)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getFuncTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("腾云凌风").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 损失").withStyle(ChatFormatting.RED).
                append(ComponentUtils.AttributeDescription.CritDamage("10%总")).
                append(Component.literal("，获得").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.movementSpeed("40%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfSky;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixChapterII();
    }

    @Override
    public int levelRequirement() {
        return 0;
    }


    public static void tick(Player player) {
        if (WraqCurios.isOn(SkyNewRune.class, player)) {
            Compute.sendEffectLastTime(player, NewRuneItems.skyNewRune.get(), 0, true);
        } else Compute.removeEffectLastTime(player, NewRuneItems.skyNewRune.get());
    }

    public static double critDamageInfluence(Player player) {
        if (WraqCurios.isOn(SkyNewRune.class, player)) {
            return 0.9;
        }
        return 1;
    }
}
