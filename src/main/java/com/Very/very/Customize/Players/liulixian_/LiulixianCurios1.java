package com.Very.very.Customize.Players.liulixian_;

import com.Very.very.Customize.Uniform.Attributes;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
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

public class LiulixianCurios1 extends Item implements ICurioItem {

    public LiulixianCurios1(Properties p_41383_) {
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
        Style style = Utils.styleOfSakura;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("仙染琉璃").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 普通法球攻击命中目标时，会获得一层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("「墨」").withStyle(ChatFormatting.RESET).withStyle(style)));
        components.add(Component.literal(" 当你拥有").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("3").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("「墨」").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，墨会对仙进行守护。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 当你拥有").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("6").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("「墨」").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("：墨会进行协同攻击。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 当你拥有").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("9").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("「墨」").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("：获得一层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("「仙」").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("并投掷一枚").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("「琉璃八面骰」").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，会触发4种").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("正面效果").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).
                append(Component.literal("与4种").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("负面效果").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)));
        components.add(Component.literal(" 当你拥有").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("3").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("「仙」").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，墨会对仙进一步守护。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 当你拥有").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("6").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("「仙」").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("：墨会进行更强力的协同攻击。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 当你拥有").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("9").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("「仙」").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，会释放").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("『墨仙』").withStyle(ChatFormatting.RESET).withStyle(style)));
        components.add(Component.literal(" 『墨仙』").withStyle(ChatFormatting.RESET).withStyle(style).
                append(Component.literal("：墨会对仙进行最纯粹的守护；墨会尽全力协同攻击；墨与仙的羁绊引起共鸣。持续27秒").withStyle(ChatFormatting.RESET).withStyle(style)));
        Compute.DescriptionPassive(components,Component.literal("墨尘").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 每过一段时间，墨与仙释放一次守护攻击").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" -墨对仙的纯粹守护，使拥有者能够拥有").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("「破万法」与『生生不息』").withStyle(ChatFormatting.RESET).withStyle(style)));
        components.add(Component.literal(" 今日听君歌一曲 如听仙乐耳暂明").withStyle(ChatFormatting.RESET).withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚『仙染墨尘』，授予对维瑞阿契做出了杰出贡献的 - liulixian_").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LiuLiXianCurios1F.Player = (Player) slotContext.entity();
        LiuLiXianCurios1F.isOn = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LiuLiXianCurios1F.Player = null;
        LiuLiXianCurios1F.isOn = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
