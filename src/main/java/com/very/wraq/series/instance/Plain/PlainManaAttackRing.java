package com.very.wraq.series.instance.Plain;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
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

public class PlainManaAttackRing extends Item implements ICurioItem {

    private final int level;
    public PlainManaAttackRing(Properties p_41383_, int level) {
        super(p_41383_);
        this.level = level;
        Utils.ManaDamage.put(this,(double) Attributes[level]);
        Utils.CuriosTag.put(this,1d);
        Utils.CuriosList.add(this);
    }
    private final int[] Attributes = {
            50,100,150,200
    };
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Utils.PlayerManaAttackRingMap.put((Player) slotContext.entity(),Attributes[this.level]);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Utils.PlayerManaAttackRingMap.remove((Player) slotContext.entity());
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
