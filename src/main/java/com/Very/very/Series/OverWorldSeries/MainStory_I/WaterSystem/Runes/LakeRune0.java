package com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Runes;

import com.Very.very.ValueAndTools.Compute;
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

public class LakeRune0 extends Item{
    public LakeRune0(Item.Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        components.add(Component.literal("基于额外移动速度提供冷却缩减"));
        components.add(Component.literal("每10%额外移动速度提供1%冷却缩减"));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        components.add(Component.literal("Runes-III").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if(!level.isClientSide) {
            player.getPersistentData().putInt("Blue",0);
            player.sendSystemMessage(Component.literal("湖泊符石-循环冷却").withStyle(ChatFormatting.BLUE).append(Component.literal("已激活").withStyle(ChatFormatting.RESET)));
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
