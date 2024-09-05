package com.very.wraq.series.newrunes.chapter1;

import com.very.wraq.events.mob.chapter1.LakeDrownSpawnController;
import com.very.wraq.projectiles.UsageOrGetWayDescriptionItem;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.RuneItem;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;
import java.util.List;

public class LakeNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public LakeNewRune(Properties properties) {
        super(properties);
        Utils.coolDownDecrease.put(this, 0.2);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getFuncTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("流线适应").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("水下呼吸").withStyle(hoverMainStyle())).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("200%游泳速度").withStyle(hoverMainStyle())));

        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfWater;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public int levelRequirement() {
        return 0;
    }


    public static boolean isOn(Player player) {
        return WraqCurios.isOn(LakeNewRune.class, player);
    }

    public static double exSwimSpeed(Player player) {
        if (!isOn(player)) return 0;
        return 2;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 30));
        super.curioTick(slotContext, stack);
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(LakeDrownSpawnController.mobName).withStyle(CustomStyle.styleOfLake)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
