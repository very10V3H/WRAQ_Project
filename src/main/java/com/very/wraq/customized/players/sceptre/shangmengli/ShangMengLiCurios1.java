package com.very.wraq.customized.players.sceptre.shangmengli;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.toolTip.CustomStyle;
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

public class ShangMengLiCurios1 extends Item implements ICurioItem {

    public ShangMengLiCurios1(Properties p_41383_) {
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
        Compute.DescriptionPassive(components,Component.literal("过充能").withStyle(style));
        components.add(Component.literal(" 1.当你拥有10点").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术专精-力凝魔核：").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 普通法球攻击").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("命中时会进行").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("充能").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" 充能满时").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("额外发射一枚法球").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("并提供一层").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("热量").withStyle(style)));
        components.add(Component.literal(" 每层热量").withStyle(style).
                append(Component.literal("会提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("33%").withStyle(style)).
                append(Component.literal("伤害提升").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 当").withStyle(ChatFormatting.WHITE).
                append(Component.literal("热量").withStyle(style)).
                append(Component.literal("达到").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("3层").withStyle(style)).
                append(Component.literal(" 额外发射三枚法球，并清空层数。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 2.当你拥有10点").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术专精-术法全析：").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 为你提供").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CoolDown("50")));
        components.add(Component.literal(" 并且，你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("会减少你所有法术的0.25s冷却时间").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("附带").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("4倍").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 警告！警告！").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚过载模块，授予对维瑞阿契做出了杰出贡献的 - shangmengli").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Utils.ShangMengLi = (Player) slotContext.entity();
        Utils.ShangMengLiCore1 = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Utils.ShangMengLi = null;
        Utils.ShangMengLiCore1 = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static double ExManaDamage(Player player) {
        if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore1) return Compute.XpStrengthAPDamage(player,4);
        return 0;
    }
}
