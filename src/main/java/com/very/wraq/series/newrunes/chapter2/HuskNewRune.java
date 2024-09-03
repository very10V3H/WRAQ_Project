package com.very.wraq.series.newrunes.chapter2;

import com.very.wraq.events.mob.chapter2.HuskSpawnController;
import com.very.wraq.process.func.particle.ParticleProvider;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class HuskNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public HuskNewRune(Properties properties) {
        super(properties);
        Utils.healEffectUp.put(this, 0.1);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("灵魂收集").withStyle(style));
        components.add(Component.literal(" 击杀怪物时，会收集其").withStyle(ChatFormatting.WHITE).
                append(Component.literal("灵魂").withStyle(style)));
        components.add(Component.literal(" 每枚").withStyle(ChatFormatting.WHITE).
                append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1%伤害提升").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" 至多可收集20枚，每枚存活5min").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfHusk;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixChapterII();
    }

    @Override
    public int levelRequirement() {
        return 0;
    }

    @Override
    public boolean isArbitrarily() {
        return false;
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(HuskSpawnController.mobName).withStyle(CustomStyle.styleOfHusk)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    public static boolean isOn(Player player) {
        return WraqCurios.isOn(HuskNewRune.class, player);
    }

    public static Map<String, Queue<Integer>> soulCollectionMap = new HashMap<>();

    public static void onKill(Player player, Mob mob) {
        if (!isOn(player)) return;
        handleQueue(player).add(player.getServer().getTickCount());
        ParticleProvider.dustParticle(player, mob.getEyePosition().add(0, 0, 0), 0.3, 8, CustomStyle.styleOfHusk.getColor().getValue());
    }

    public static void tick(Player player) {
        if (!isOn(player)) {
            Compute.removeEffectLastTime(player, NewRuneItems.huskNewRune.get());
            return;
        }
        int size = handleQueue(player).size();
        Compute.sendEffectLastTime(player, NewRuneItems.huskNewRune.get(), size, true);
    }

    public static double damageEnhance(Player player) {
        if (!isOn(player)) return 0;
        return handleQueue(player).size() * 0.01;
    }

    public static Queue<Integer> handleQueue(Player player) {
        int tick = player.getServer().getTickCount();
        String name = player.getName().getString();
        if (!soulCollectionMap.containsKey(name)) soulCollectionMap.put(name, new ArrayDeque<>());
        Queue<Integer> queue = soulCollectionMap.get(name);
        queue.removeIf(integer -> tick - integer > 6000);
        while (queue.size() > 20) queue.remove();
        return queue;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
