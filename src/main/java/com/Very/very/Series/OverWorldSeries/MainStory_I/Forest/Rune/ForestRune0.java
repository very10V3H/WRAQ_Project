package com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Rune;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
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

public class ForestRune0 extends Item {
    public ForestRune0(Item.Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        Compute.RuneAttributeDescription(components);
        components.add(Component.literal("获得你").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("10%")).
                append(Component.literal("的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ExAttackDamage("")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("Runes-II").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            player.getPersistentData().putInt(StringUtils.ForestRune.ForestRune,0);
            Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                    Component.literal("森林符石-巨像勇气").withStyle(ChatFormatting.DARK_GREEN).append(Component.literal("已激活").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            data.putString("Owner",player.getName().getString());
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
