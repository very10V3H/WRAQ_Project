package fun.wraq.series.instance.series.lava.rune;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
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

public class WindRune extends WraqCurios implements RuneItem {

    public WindRune(Properties properties) {
        super(properties);
        Utils.movementSpeedCommon.put(this, 0.04);
        Utils.critDamage.put(this, 0.1);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
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
        ComponentUtils.descriptionPassive(components, Component.literal("腾云凌风").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 损失").withStyle(ChatFormatting.RED).
                append(ComponentUtils.AttributeDescription.critDamage("10%总")).
                append(Component.literal("，获得").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.movementSpeed("20%")));
        return components;
    }

    @Override
    public void tick(Player player) {
        Compute.sendEffectLastTime(player, NewRuneItems.skyNewRune.get(), 0, true);
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfWind;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfAlchemy();
    }
}
