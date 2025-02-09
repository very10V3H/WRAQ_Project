package fun.wraq.series.newrunes.chapter2;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.chapter2.WindSkeletonSpawnController;
import fun.wraq.process.func.ChangedAttributesModifier;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.newrunes.RuneItem;
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
        Utils.movementSpeedCommon.put(this, 0.04);
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
                append(ComponentUtils.AttributeDescription.movementSpeed("20%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfWind;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    public static void onHit(Player player) {
        if (Compute.hasCurios(player, NewRuneItems.kazeNewRune.get())) {
            ChangedAttributesModifier.addAttributeModifier(player, ChangedAttributesModifier.movementSpeedUp,
                    "kazeNewRuneMovementSpeed", 0.2, 40, true);
            Compute.sendEffectLastTime(player, NewRuneItems.kazeNewRune.get(), 40);
        }
    }
}
