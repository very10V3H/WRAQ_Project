package com.very.wraq.series.overworld.chapter1.plain;

import com.very.wraq.process.func.power.PowerLogic;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlainPower extends Item implements ActiveItem {

    private final int tier;

    public PlainPower(Properties p_41383_, int tier) {
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
        Compute.DescriptionActive(components, Component.literal("平原之风").withStyle(CustomStyle.styleOfPlain));
        components.add(Component.literal(" 击退").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围怪物，同时对其造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamageValue(String.format("%.0f", effect[this.tier] * 100) + "%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.LifeElement("1 + 100%")));
        components.add(Component.literal(" 为").withStyle(ChatFormatting.WHITE).
                append(Component.literal("自身").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("周围所有玩家提供持续5s的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.movementSpeed("50%")));

        Compute.CoolDownTimeDescription(components, CoolDownTime[this.tier]);
        Compute.ManaCostDescription(components, manaCost[this.tier]);

        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        components.add(Component.literal("Powers-Plain").withStyle(CustomStyle.styleOfPlain));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static int[] manaCost = {
            180, 150, 150, 120
    };

    public static int[] CoolDownTime = {
            13, 12, 11, 10
    };

    public static double[] effect = {
            1, 1.25, 1.5, 2
    };

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, PlainPower.manaCost[tier] - 10 * Compute.ArmorCount.LifeManaE(player), true)) {
            PowerLogic.PlainPower(player, this, tier);
            PowerLogic.PlayerReleasePowerType(player, 4);
        }
    }
}
