package com.very.wraq.series.instance.series.purple;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.WraqPassiveEquip;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PurpleIronSceptre extends WraqPassiveEquip implements PurpleIronTier {

    private static final double[] ManaDamage = {
            200, 300, 400, 500
    };

    private static final double[] MaxMana = {
            25, 50, 75, 100
    };

    private static final double[] ManaPenetration0 = {
            2, 3, 3, 4
    };

    private final int tier;

    public PurpleIronSceptre(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.manaDamage.put(this, ManaDamage[tier]);
        Utils.maxMana.put(this, MaxMana[tier]);
        Utils.manaPenetration0.put(this, ManaPenetration0[tier]);
        Utils.levelRequire.put(this, 120);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPurpleIron;
    }

    @Override
    public List<Component> getAdditionDescriptions() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("晶体析构").withStyle(style));
        components.add(Component.literal(" 基于你与目标的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDefence("")).
                append(Component.literal("差的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("绝对值").withStyle(style)).
                append(Component.literal("至多提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%伤害提升").withStyle(style)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfPurpleIron();
    }

    @Override
    public Component getType() {
        return Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public int getPassiveTier() {
        return tier;
    }
}
