package com.very.wraq.series.overworld.chapter2.lavender;

import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.util.struct.Shield;
import com.very.wraq.projectiles.RandomCurios;
import com.very.wraq.projectiles.UsageOrGetWayDescriptionItem;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.series.castle.CastleCurios;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LavenderBracelet extends WraqCurios implements RandomCurios, UsageOrGetWayDescriptionItem {

    public LavenderBracelet(Properties properties) {
        super(properties);
    }

    @Override
    public Component getTypeDescription() {
        return Component.literal("").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getFuncTypeDescriptionOfCurios()).
                append(Component.literal(" v = 3 * " + BigDecimal.valueOf(rate())).withStyle(CustomStyle.styleOfPlain));
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("纳德斯膜").withStyle(style));
        components.add(Component.literal(" 每秒为你提供持续1.5s的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("5%").withStyle(style)).
                append(ComponentUtils.AttributeDescription.MaxHealth("")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾").withStyle(ChatFormatting.GRAY)));
        return components;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide() && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack stack = player.getItemInHand(interactionHand);
            if (stack.getTagElement(Utils.MOD_ID) == null) CastleCurios.randomAttributeProvide(stack, 4, 0.5);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfJacaranda;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixChapterII();
    }

    @Override
    public int levelRequirement() {
        return 80;
    }


    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀薰楠村东侧，薰衣草田西侧紫晶妖妇概率获取"));
        components.add(Component.literal("基础属性为随机三词条").withStyle(ChatFormatting.WHITE).
                append(Component.literal("防御型").withStyle(CustomStyle.styleOfPlain)).
                append(Component.literal("属性").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    public static void tick(Player player) {
        if (player.tickCount % 20 == 0 && WraqCurios.isOn(LavenderBracelet.class, player)) {
            Shield.providePlayerShield(player, 30, PlayerAttributes.maxHealth(player) * 0.05);
        }
    }

    @Override
    public void setAttribute(ItemStack stack) {
        CastleCurios.randomDefenceAttributeProvide(stack, 3, rate());
    }

    @Override
    public double rate() {
        return 0.5;
    }
}
