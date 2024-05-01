package com.very.wraq.customized.players.bow.MyMission;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
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

public class MyMissionCurios1 extends Item implements ICurioItem {

    public MyMissionCurios1(Properties p_41383_) {
        super(p_41383_);
        Utils.AttackDamage.put(this, Attributes.AttackDamage);
        Utils.DefencePenetration0.put(this, Attributes.DefencePenetration0);
        Utils.CritDamage.put(this, Attributes.CritDamage);
        Utils.Defence.put(this, Attributes.Defence);
        Utils.CritRate.put(this, Attributes.CritRate);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfHealth;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("狙翎之怒").withStyle(style));
        components.add(Component.literal(" 手持弓时，每10s为你提供:").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("300")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("300%")));
        components.add(Component.literal(" -至多叠加至10层").withStyle(ChatFormatting.WHITE));
        Compute.DescriptionPassive(components,Component.literal("狙神领域").withStyle(style));
        components.add(Component.literal(" 使").withStyle(ChatFormatting.WHITE).
                append(Component.literal("誓约").withStyle(style)).
                append(Component.literal("的伤害提升为原来的3倍").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚誓约聚能，授予对维瑞阿契做出了杰出贡献的 - My_mission").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Onplayer = (Player) slotContext.entity();
        On = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Onplayer = null;
        On = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static Player Onplayer;
    public static boolean On;
    public static int Count = 0;

    public static boolean IsOn(Player player) {
        return Onplayer != null && Onplayer.equals(player) && On;
    }

    public static void Tick(Player player) {
        if (!IsOn(player)) return;
        if (player.tickCount % 200 == 0) {
            Count = Math.min(10,Count + 1);
            Compute.EffectLastTimeSend(player, ModItems.MyMissionCurios1.get().getDefaultInstance(),8888,Count,true);
        }
    }

    public static double AttackDamageUp(Player player) {
        if (!IsOn(player)) return 0;
        return Count * 300;
    }

    public static double CritDamageUp(Player player) {
        if (!IsOn(player)) return 0;
        return Count * 3;
    }
}
