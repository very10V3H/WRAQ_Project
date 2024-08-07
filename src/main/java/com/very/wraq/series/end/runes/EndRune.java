package com.very.wraq.series.end.runes;

import com.very.wraq.events.mob.chapter4_end.EnderManSpawnController;
import com.very.wraq.projectiles.UsageOrGetWayDescriptionItem;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EndRune extends WraqCurios implements UsageOrGetWayDescriptionItem {

    public EndRune(Properties properties) {
        super(properties);
        Utils.expUp.put(this, 0.33);
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(EnderManSpawnController.mobName)).
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
        components.add(Component.literal(" 击杀怪物时，怪物的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("掉落物").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("经验值").withStyle(ChatFormatting.LIGHT_PURPLE)));
        components.add(Component.literal(" 会直接送达，而不是作为掉落物形式掉落").withStyle(ChatFormatting.WHITE));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfEnd;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixChapterIV();
    }

    @Override
    public int levelRequirement() {
        return 0;
    }

    @Override
    public boolean isArbitrarily() {
        return false;
    }
}
