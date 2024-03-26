package com.Very.very.Series.OverWorldSeries.MainStory_I.Plain;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlainPower extends Item {

    private final int Level;
    public PlainPower(Properties p_41383_, int level) {
        super(p_41383_);
        this.Level = level;
        Utils.PowerTag.put(this,1d);
        Utils.WeaponList.add(this);
    }

    public int getLevel() {
        return Level;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·法术").withStyle(Utils.styleOfMana));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        Compute.DescriptionActive(components,Component.literal("平原之风").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPlain));
        components.add(Component.literal("-击退周围怪物，同时对其造成").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamageValue( (Effect[Level] * 100) +"%")));
        components.add(Component.literal("-治疗周围所有玩家").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("能力-智力 * 30").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Health("")));

        Compute.CoolDownTimeDescription(components,CoolDownTime[Level]);
        Compute.ManaCostDescription(components,ManaCost[Level]);

        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        components.add(Component.literal("Powers-Plain").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPlain));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.USE(player);
        return super.use(level, player, interactionHand);
    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static int[] ManaCost = {
            60,50,50,40
    };

    public static int[] CoolDownTime = {
            13,12,11,10
    };

    public static int[] Effect = {
            1,2,3,4
    };

}
