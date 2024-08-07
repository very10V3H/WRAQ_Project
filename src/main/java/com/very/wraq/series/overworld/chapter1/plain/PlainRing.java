package com.very.wraq.series.overworld.chapter1.plain;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class PlainRing extends Item implements ICurioItem {

    public PlainRing(Properties p_41383_) {
        super(p_41383_);
        Utils.critDamage.put(this, 0.1);
        Utils.manaPenetration0.put(this, 10d);
        Utils.expUp.put(this, 0.1);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        stack.setHoverName(Component.literal("平原戒指").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        components.add(Component.literal("增加0.5攀登高度").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.YELLOW));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        components.add(Component.literal("PlainGems-I").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putBoolean("plaingems", true);
        player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).setBaseValue(0.5D);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putBoolean("plaingems", false);
        player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).setBaseValue(0.0D);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
