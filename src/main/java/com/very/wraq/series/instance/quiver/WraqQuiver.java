package com.very.wraq.series.instance.quiver;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.projectiles.WraqPassiveEquip;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class WraqQuiver extends WraqPassiveEquip implements ActiveItem {

    private final Component suffix;
    private final Style style;
    private final double rate;
    private final int shootTimes;
    public WraqQuiver(Properties p_40524_, Style style, Component suffix, double rate, int shootTimes
            , double swift, int levelRequire) {
        super(p_40524_);
        this.suffix = suffix;
        this.style = style;
        this.rate = rate;
        this.shootTimes = shootTimes;
        Utils.swiftnessUp.put(this, swift);
        Utils.xpLevelAttackDamage.put(this, 1d);
        Utils.levelRequire.put(this, levelRequire);
    }

    @Override
    public Style getMainStyle() {
        return style;
    }

    @Override
    public List<Component> getAdditionDescriptions() {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionActive(components, Component.literal("速射").withStyle(CustomStyle.styleOfFlexible));
        components.add(Component.literal(" 从箭袋中快速抽出").withStyle(ChatFormatting.WHITE).
                append(Component.literal("" + shootTimes).withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("支箭射出").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 每支箭矢拥有").withStyle(ChatFormatting.WHITE).
                append(Component.literal(String.format("%.0f%%", rate * 100)).withStyle(style)).
                append(Component.literal("基础伤害").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 额外箭矢视为").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)));
        ComponentUtils.coolDownTimeDescription(components, 9);
        return components;
    }

    @Override
    public Component getSuffix() {
        return suffix;
    }

    @Override
    public Component getType() {
        return Component.literal("箭袋").withStyle(CustomStyle.styleOfFlexible);
    }

    public static WeakHashMap<Player, Integer> exShootTimesMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Double> exShootRateMap = new WeakHashMap<>();

    @Override
    public void active(Player player) {
        if (player.experienceLevel < Utils.levelRequire.getOrDefault(this, 0)) return;
        exShootTimesMap.put(player, shootTimes * 2);
        exShootRateMap.put(player, rate);
        getAllQuiver().forEach(item -> {
            Compute.playerItemCoolDown(player, item, 9);
        });
    }

    private List<Item> getAllQuiver() {
        return QuiverItems.ITEMS.getEntries().stream().map(RegistryObject::get).toList();
    }

    public static void tick() {
        exShootTimesMap.forEach((player, tick) -> {
            if (exShootTimesMap.get(player) >= 0) exShootTimesMap.put(player, tick - 1);
            if (exShootTimesMap.get(player) % 2 == 0) {
                if (player.getMainHandItem().getItem() instanceof WraqBow wraqBow) {
                    wraqBow.shoot((ServerPlayer) player, exShootRateMap.get(player));
                }
            }
        });
    }
}
