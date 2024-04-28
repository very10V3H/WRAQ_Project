package com.very.wraq.customized.players.sceptre.Eliaoi;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
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

public class EliaoiCurios1 extends Item implements ICurioItem {

    public EliaoiCurios1(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfCastle;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("替罪").withStyle(style));
        components.add(Component.literal(" 1.当你拥有10点").withStyle(ChatFormatting.WHITE).
                append(Component.literal("术法全析").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("时，每").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("10000")).
                append(Component.literal("为你提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%伤害加成").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 2.当你拥有10点").withStyle(ChatFormatting.WHITE).
                append(Component.literal("力凝魔核").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("时，每").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("10000")).
                append(Component.literal("为你提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%法球攻击增幅").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionPassive(components,Component.literal("恣睢").withStyle(style));
        components.add(Component.literal(" 受到伤害或对敌人造成伤害时，会获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("愤怒").withStyle(style)));
        components.add(Component.literal(" 当你拥有100层").withStyle(ChatFormatting.WHITE).
                append(Component.literal("愤怒").withStyle(style)).
                append(Component.literal("时，会进入").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("愤怒模式").withStyle(style)));
        components.add(Component.literal(" 愤怒模式").withStyle(style).
                append(Component.literal("下受到的伤害值翻倍，并且生命回复清0，获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("总100%")).
                append(Component.literal("以及").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%伤害提升").withStyle(style)));
        components.add(Component.literal(" 聚合成那地牢怪物的灵魂永远无法进入他们所期望的来世，被愤怒玷污、融化，他们终将如此。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚幻灭心元石 ，授予对维瑞阿契做出了杰出贡献的 - Eliaoi").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        onPlayer = (Player) slotContext.entity();
        On = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        onPlayer = (Player) slotContext.entity();
        On = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static Player onPlayer;
    public static boolean On;

    public static int EachTimeCountAddValue = 1;
    public static int PassiveLastTickValue = 200;
    public static int PassiveCoolDownValue = 200;

    public static boolean IsOn(Player player) {
        return onPlayer != null && onPlayer.equals(player) && On;
    }

    public static int Count = 0;
    public static int PassiveLastTick = 0;
    public static int PassiveCoolDownTick = 0;

    public static double MonsterAttackDamageEnhance(Player player) {
        if (!IsOn(player)) return 1;
        if (PassiveLastTick > player.getServer().getTickCount()) return 2;
        return 1;
    }

    public static double ManaDamageUp(Player player) {
        if (!IsOn(player)) return 1;
        if (PassiveLastTick > player.getServer().getTickCount()) return 2;
        return 1;
    }

    public static double HealthRecover(Player player) {
        if (!IsOn(player)) return 1;
        if (PassiveLastTick > player.getServer().getTickCount()) return 0;
        return 1;
    }

    public static double DamageEnhance(Player player) {
        if (!IsOn(player)) return 0;
        int TickCount = player.getServer().getTickCount();
        double damageEnhance = 0;
        CompoundTag data = player.getPersistentData();
        if (Compute.ManaSkillLevelGet(data,11) == 10) {
            damageEnhance += Compute.PlayerAttributes.PlayerManaDamage(player) / 40000;
        }
        if (PassiveLastTick > TickCount) damageEnhance += 1;
        return damageEnhance;
    }

    public static double NormalAttackDamageEnhance(Player player) {
        if (!IsOn(player)) return 0;
        double damageEnhance = 0;
        CompoundTag data = player.getPersistentData();
        if (Compute.ManaSkillLevelGet(data,10) == 10) {
            damageEnhance += Compute.PlayerAttributes.PlayerManaDamage(player) / 40000;
        }
        return damageEnhance;
    }

    public static void CountAdd(Player player) {
        if (!IsOn(player)) return;
        if (PassiveCoolDownTick > player.getServer().getTickCount()) return;
        Count += EachTimeCountAddValue;
        if (Count >= 100) {
            Count = 0;
            Passive(player);
        }
        else Compute.EffectLastTimeSend(player,ModItems.EliaoiCurios1.get().getDefaultInstance(),8888,Count,true);
    }

    public static void Passive(Player player) {
        PassiveLastTick = player.getServer().getTickCount() + PassiveLastTickValue;
        PassiveCoolDownTick = player.getServer().getTickCount() + PassiveCoolDownValue;
        Compute.EffectLastTimeSend(player, ModItems.EliaoiCurios1.get().getDefaultInstance(),PassiveLastTickValue);
    }
}
