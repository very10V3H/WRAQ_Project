package com.very.wraq.series.overworld.chapter1.volcano;

import com.very.wraq.process.func.power.PowerLogic;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
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

public class VolcanoPower extends Item implements ActiveItem {

    private final int tier;

    public VolcanoPower(Properties p_41383_, int tier) {
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
        Compute.DescriptionActive(components, Component.literal("爆裂之焰").withStyle(CustomStyle.styleOfVolcano));
        components.add(Component.literal(" 使").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围所有敌人爆裂，在每个敌人位置处产生小范围").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamageValue(String.format("%.0f", effect[tier] * 100) + "%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.FireElement("1 + 100%")));
        Compute.CoolDownTimeDescription(components, CoolDownTime[tier]);
        Compute.ManaCostDescription(components, ManaCost[tier]);

        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        components.add(Component.literal("Powers-Volcano").withStyle(CustomStyle.styleOfVolcano));
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
            10, 9, 8, 7
    };

    public static double[] effect = {
            1, 1.25, 1.5, 2
    };

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, VolcanoPower.ManaCost[tier] - 10 * Compute.ArmorCount.ObsiManaE(player), true)) {
            PowerLogic.VolcanoPower(player, this, tier);
            PowerLogic.PlayerReleasePowerType(player, 7);
        }
    }
}
