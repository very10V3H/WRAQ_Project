
package com.very.wraq.series.instance.PurpleIronKnight;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.MobAttributes;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.projectiles.WraqPassiveEquip;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PurpleIronSword extends WraqPassiveEquip implements PurpleIronTier {

    private static final double[] ExAttackDamage = {
            100, 150, 200, 250
    };

    private static final double[] Defence = {
            150, 300, 450, 600
    };

    private static final double[] MaxHealth = {
            400, 600, 800, 1000
    };

    private final int tier;

    public PurpleIronSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, ExAttackDamage[tier]);
        Utils.defence.put(this, Defence[tier]);
        Utils.maxHealth.put(this, MaxHealth[tier]);
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
                append(Component.literal(new String[]{"20%", "35%", "50%", "65%"}[tier] + "伤害提升").withStyle(style)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfPurpleIron();
    }

    @Override
    public Component getType() {
        return Component.literal("长剑").withStyle(CustomStyle.styleOfPurpleIron);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static double damageEnhance(Player player, Mob mob) {
        if (player.experienceLevel < 120) return 0;
        boolean isOn = false;
        double rate = 0;
        Inventory inventory = player.getInventory();
        for (int i = 3; i < 9; i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem() instanceof PurpleIronTier purpleIronTier) {
                isOn = true;
                rate = new double[]{0.2, 0.35, 0.5, 0.65}[purpleIronTier.getPassiveTier()];
            }
        }
        if (!isOn) return 0;
        double manaDefence = 0;
        manaDefence = MobAttributes.manaDefence(mob);
        double value = Math.abs(PlayerAttributes.manaDefence(player) - manaDefence);
        return (rate - (rate * 750 / (750 + value)));
    }

    @Override
    public int getPassiveTier() {
        return tier;
    }
}
