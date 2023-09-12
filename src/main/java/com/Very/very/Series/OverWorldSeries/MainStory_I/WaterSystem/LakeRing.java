package com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem;

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

public class LakeRing extends Item implements ICurioItem {

    public LakeRing(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("湖泊戒指").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionCritDamage(components,0.1f);
        Compute.EmojiDescriptionExpUp(components,0.1f);
        Compute.EmojiDescriptionCoolDown(components,0.1f);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        components.add(Component.literal("LakeGems-I").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
        /*components.add(Component.literal("·经验加成").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(" 25%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·移动速度").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal("+25%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal("+25%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·生命回复").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).append(Component.literal("+1").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·冷却缩减").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD).append(Component.literal("+25%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
*/
    }
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putBoolean("lakegems",true);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putBoolean("lakegems",false);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }
    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
