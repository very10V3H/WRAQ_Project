package com.very.wraq.series.instance.plain;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
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

public class PlainAttackRing extends Item implements ICurioItem {

    private final int level;

    public PlainAttackRing(Properties p_41383_, int level) {
        super(p_41383_);
        this.level = level;
        Utils.attackDamage.put(this, (double) Attributes[level]);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
    }

    private final int[] Attributes = {
            30, 60, 90, 120
    };

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Utils.playerAttackRingMap.put(player.getName().getString(), Attributes[this.level]);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Utils.playerAttackRingMap.remove(player.getName().getString());
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
