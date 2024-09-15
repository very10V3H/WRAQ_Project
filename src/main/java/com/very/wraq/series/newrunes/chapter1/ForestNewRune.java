package com.very.wraq.series.newrunes.chapter1;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.events.mob.chapter1.ForestZombieSpawnController;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForestNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {
    public ForestNewRune(Properties properties) {
        super(properties);
        Utils.maxHealth.put(this, 400d);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getDefenceTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("狂野生长").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 受到来自").withStyle(ChatFormatting.WHITE).
                append(Component.literal("怪物").withStyle(ChatFormatting.RED)).
                append(Component.literal("的伤害且该伤害会使你的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.Health("")).
                append(Component.literal("低于20%").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("时，免疫该伤害").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 并在触发后，为你提供持续5s的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.HealthRecover("10%最大生命值")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfForest;
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
        return WraqCurios.isOn(ForestNewRune.class, player);
    }

    public static Map<String, Integer> passiveNextActiveTime = new HashMap<>();
    public static Map<String, Integer> healthRecoverUpTime = new HashMap<>();

    public static boolean protectPlayerFromDamage(Player player, double damage) {
        if (!isOn(player)) return false;
        String name = player.getName().getString();
        int tick = player.getServer().getTickCount();
        if (WraqCurios.coolDownOver(passiveNextActiveTime, player)) {
            if ((player.getHealth() - damage) / (player.getMaxHealth() - damage) <= 0.2) {
                passiveNextActiveTime.put(name, tick + 1200);
                healthRecoverUpTime.put(name, tick + 100);
                Compute.sendEffectLastTime(player, NewRuneItems.forestNewRune.get(), 100);
                Compute.coolDownTimeSend(player, NewRuneItems.forestNewRune.get(), 1200);
                return true;
            }
        }
        return false;
    }

    public static double playerHealthRecoverUp(Player player) {
        if (!isOn(player) || !WraqCurios.inLastTime(healthRecoverUpTime, player)) return 0;
        return player.getMaxHealth() * 0.1;
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(ForestZombieSpawnController.mobName).withStyle(CustomStyle.styleOfForest)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
