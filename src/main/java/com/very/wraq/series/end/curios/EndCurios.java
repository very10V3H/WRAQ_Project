package com.very.wraq.series.end.curios;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class EndCurios extends Item implements ICurioItem {

    public EndCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
        Utils.attackDamage.put(this, 88d);
        Utils.defencePenetration.put(this, 0.1);
        Utils.critDamage.put(this, 0.2);
        Utils.swiftnessUp.put(this, 1d);
        Utils.movementSpeedWithoutBattle.put(this, 0.3);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        Style style = CustomStyle.styleOfEnd;
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("折跃").withStyle(style));
        components.add(Component.literal(" 使你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("箭矢").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("能够").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("穿透").withStyle(style)).
                append(Component.literal("敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("，每穿过一个敌人，使接下来造成的伤害提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("33%").withStyle(style)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryIV(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Compute.AddCuriosToList((Player) slotContext.entity(), stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Compute.RemoveCuriosInList((Player) slotContext.entity(), stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }


    public static boolean IsOn(Player player) {
        if (!Utils.playerCuriosListMap.containsKey(player)) return false;
        List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
        for (ItemStack itemStack : curiosList) {
            if (itemStack.getItem() instanceof EndCurios) return true;
        }
        return false;
    }


}
