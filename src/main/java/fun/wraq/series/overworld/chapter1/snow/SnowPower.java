package fun.wraq.series.overworld.chapter1.snow;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.render.toolTip.CustomStyle;
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
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        Compute.DescriptionActive(components, Component.literal("冰封陵墓").withStyle(CustomStyle.styleOfSnow));
        components.add(Component.literal(" 禁锢").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围所有敌人1s，并造成").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue(String.format("%.0f", effect[tier] * 100) + "%")));
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
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static int[] ManaCost = {
            100, 125, 150, 175
    };

    public static int[] CoolDownTime = {
            12, 11, 10, 8
    };

    public static double[] effect = {
            1, 1.25, 1.5, 1.75
    };

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, SnowPower.ManaCost[tier], true)) {
            PowerLogic.SnowPower(player, this, tier);
            PowerLogic.PlayerReleasePowerType(player, 8);
        }
    }
}
