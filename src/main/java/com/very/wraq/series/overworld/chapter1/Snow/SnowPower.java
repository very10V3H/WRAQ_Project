package com.very.wraq.series.overworld.chapter1.Snow;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.func.power.PowerLogic;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SnowPower extends Item implements ActiveItem {

    private final int tier;

    public SnowPower(Properties p_41383_, int tier) {
        super(p_41383_);
        this.tier = tier;
        Utils.powerTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    public int getTier() {
        return tier;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·法术").withStyle(CustomStyle.styleOfMana));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        Compute.DescriptionActive(components, Component.literal("冰封陵墓").withStyle(CustomStyle.styleOfSnow));
        components.add(Component.literal(" 禁锢").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围所有敌人1s，并造成").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.ManaDamageValue(String.format("%.0f", effect[tier] * 100) + "%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.IceElement("1 + 100%")));
        components.add(Component.literal(" 为").withStyle(ChatFormatting.WHITE).
                append(Component.literal("自身").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("周围所有玩家提供")).
                append(Component.literal("能力 - 智力 * 20").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("，持续2.5s").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.coolDownTimeDescription(components, CoolDownTime[tier]);
        ComponentUtils.manaCostDescription(components, ManaCost[tier]);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        components.add(Component.literal("Powers-Snow").withStyle(CustomStyle.styleOfSnow));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static int[] ManaCost = {
            180, 150, 150, 120
    };

    public static int[] CoolDownTime = {
            12, 11, 10, 8
    };

    public static double[] effect = {
            1.5, 2, 2.5, 3
    };

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, SnowPower.ManaCost[tier], true)) {
            PowerLogic.SnowPower(player, this, tier);
            PowerLogic.PlayerReleasePowerType(player, 8);
        }
    }
}
