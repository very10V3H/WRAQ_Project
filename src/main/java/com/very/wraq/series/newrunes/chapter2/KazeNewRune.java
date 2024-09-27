package com.very.wraq.series.newrunes.chapter2;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.events.mob.chapter2.WindSkeletonSpawnController;
import com.very.wraq.process.func.ChangedAttributesModifier;
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

public class KazeNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public KazeNewRune(Properties properties) {
        super(properties);
        Utils.movementSpeedCommon.put(this, 0.1);
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal(" 击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(WindSkeletonSpawnController.mobName).withStyle(CustomStyle.styleOfWind)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("顺势风动").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 近战攻击").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法球攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("命中目标后").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 为你提供在").withStyle(ChatFormatting.WHITE).
                append(Component.literal("2s内逐渐衰减").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.movementSpeed("40%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfWind;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixChapterII();
    }

    public static void onHit(Player player) {
        if (WraqCurios.isOn(KazeNewRune.class, player)) {
            ChangedAttributesModifier.addAttributeModifier(player, ChangedAttributesModifier.movementSpeedUp,
                    "kazeNewRuneMovementSpeed", 0.4, 40, true);
            Compute.sendEffectLastTime(player, NewRuneItems.kazeNewRune.get(), 40);
        }
    }
}
