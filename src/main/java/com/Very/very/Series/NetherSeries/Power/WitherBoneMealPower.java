package com.Very.very.Series.NetherSeries.Power;

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

public class WitherBoneMealPower extends Item {

    public WitherBoneMealPower(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND))
        {
            Compute.USE(player,player.getMainHandItem().getItem());
        }
        return super.use(level, player, interactionHand);
    }
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·法术").withStyle(Utils.styleOfMana));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        components.add(Component.literal("对周围单位造成").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("1000%")));
        components.add(Component.literal("扣除自身").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("100%")));
        components.add(Component.literal("扣除自身").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("44%")));
        Compute.CoolDownTimeDescription(components,10);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        components.add(Component.literal("Powers-NeSkeleton").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, flag);
    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
