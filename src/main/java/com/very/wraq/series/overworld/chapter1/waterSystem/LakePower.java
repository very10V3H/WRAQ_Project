package com.very.wraq.series.overworld.chapter1.waterSystem;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.func.power.PowerLogic;
import com.very.wraq.process.func.suit.SuitCount;
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
import java.util.WeakHashMap;

public class LakePower extends Item implements ActiveItem {

    private final int tier;

    public static WeakHashMap<Player, Integer> playerDefendTickMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> playerDefendRateMap = new WeakHashMap<>();

    public LakePower(Properties p_41383_, int tier) {
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
        Compute.DescriptionActive(components, Component.literal("迟滞之水").withStyle(CustomStyle.styleOfWater));

        components.add(Component.literal(" 对").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围敌人造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%减速").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("，并削减其").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDefence(String.format("%.0f", effect[tier] * 10) + "%")).
                append(Component.literal("持续2s。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 同时对其造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.ManaDamageValue(String.format("%.0f", effect[tier] * 100) + "%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.WaterElement("1 + 100%")));
        components.add(Component.literal(" 为").withStyle(ChatFormatting.WHITE).
                append(Component.literal("自身").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("周围玩家提供持续4s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal((tier + 1) * 5 + "%").withStyle(CustomStyle.styleOfWater)).
                append(Component.literal("伤害削减。").withStyle(ChatFormatting.GREEN)));
        ComponentUtils.coolDownTimeDescription(components, CoolDownTime[tier]);
        ComponentUtils.manaCostDescription(components, ManaCost[tier]);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        components.add(Component.literal("Powers-Lake").withStyle(CustomStyle.styleOfWater));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static int[] ManaCost = {
            150, 150, 150, 120
    };

    public static int[] CoolDownTime = {
            8, 8, 8, 8
    };

    public static double[] effect = {
            0.5, 1.0, 1.5, 2.0
    };

    public static double PlayerDefend(Player player) {
        if (playerDefendTickMap.containsKey(player) && playerDefendTickMap.get(player) > player.getServer().getTickCount()) {
            return 0.05 * playerDefendRateMap.get(player);
        }
        return 0;
    }


    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, LakePower.ManaCost[tier] - 10 * SuitCount.getObsiManaESuitCount(player), true)) {
            PowerLogic.LakePower(player, this, tier);
            PowerLogic.PlayerReleasePowerType(player, 6);
        }
    }
}
