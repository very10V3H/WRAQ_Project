package com.Very.very.Series.OverWorldSeries.MainStory_I.Forest;

import com.Very.very.ValueAndTools.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class ForestRing extends Item implements ICurioItem {

    public ForestRing(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("森林戒指").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.BOLD));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionCritDamage(components,0.1f);
        Compute.EmojiDescriptionExpUp(components,0.1f);
        Compute.EmojiDescriptionDefenceRate(components,0.1f);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("ForestGems-I").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putBoolean("forestgems",true);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putBoolean("forestgems",false);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }
    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
