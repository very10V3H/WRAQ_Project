package com.very.wraq.customized.players.sceptre.Eliaoi;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
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

public class EliaoiCurios extends Item implements ICurioItem {

    public EliaoiCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this, Attributes.ManaDamage);
        Utils.Defence.put(this,Attributes.Defence);
        Utils.ManaPenetration0.put(this, Attributes.ManaPenetration0);
        Utils.ManaRecover.put(this, Attributes.ManaRecover);
        Utils.MaxMana.put(this, Attributes.MaxMana);
        Utils.CoolDownDecrease.put(this, Attributes.CoolDown);
        Utils.CustomizedList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        ChatFormatting style = ChatFormatting.RED;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("硫磺火术").withStyle(style));
        components.add(Component.literal(" 激光或法术").withStyle(CustomStyle.styleOfMana).
                append(Component.literal(" 对目标造成伤害后").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("，将会对目标附加持续2s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("硫磺火状态").withStyle(style)));
        components.add(Component.literal(" -硫磺火状态").withStyle(style).
                append(Component.literal("：处于该状态的目标每0.5s受到").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1倍").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("，并且，受到任意来源的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("")).
                append(Component.literal("将受100%伤害提升效能。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 处于").withStyle(ChatFormatting.WHITE).
                append(Component.literal("硫磺火状态").withStyle(style)).
                append(Component.literal("的怪物每受到7次伤害，便会触发一次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("硫磺火喷发").withStyle(style)));
        components.add(Component.literal(" -硫磺火喷发").withStyle(style).
                append(Component.literal("将会造成范围伤害并为周围玩家提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("50%")).
                append(Component.literal("，以及持续2s的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("50%")).
                append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" 厄运铸造了她，但也囚禁着她.").withStyle(ChatFormatting.RED));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚灾厄护符 ，授予对维瑞阿契做出了杰出贡献的 - Eliaoi").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Eliaoi.Player = (Player) slotContext.entity();
        Eliaoi.Curios = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Eliaoi.Player = (Player) slotContext.entity();
        Eliaoi.Curios = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
