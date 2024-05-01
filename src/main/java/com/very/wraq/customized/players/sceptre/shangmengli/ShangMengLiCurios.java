package com.very.wraq.customized.players.sceptre.shangmengli;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
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

public class ShangMengLiCurios extends Item implements ICurioItem {

    public ShangMengLiCurios(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfMana;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("魔能装甲").withStyle(style));
        components.add(Component.literal(" 当").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("")).
                append(Component.literal("高于").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("10000").withStyle(style)).
                append(Component.literal("时，提高").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("25%总")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("25%总")));
        components.add(Component.literal(" 此外，超出10000部分的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("")).
                append(Component.literal("每5000点为你提供:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("10%总")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("10%总")));
        components.add(Component.literal(" 当").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("高于").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1000").withStyle(style)).
                append(Component.literal("时，提高").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("25%总")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaRecover("25")));
        components.add(Component.literal(" Ex:-外置魔能骨骼，使穿着者能瞬间破坏大部分方块。").withStyle(style));
        components.add(Component.literal(" 魔法与科技的完美结合").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚魔能装甲核心，授予对维瑞阿契做出了杰出贡献的 - shangmengli").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Utils.ShangMengLi = (Player) slotContext.entity();
        Utils.ShangMengLiCore = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Utils.ShangMengLi = null;
        Utils.ShangMengLiCore = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsOn(Player player) {
        return Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore;
    }

    public static double MaxHealthEnhance(Player player) {
        if (!IsOn(player)) return 0;
        if (PlayerAttributes.PlayerManaDamage(player) > 10000)
            return (PlayerAttributes.PlayerManaDamage(player) - 10000) * 0.1 / 5000;
        return 0;
    }

    public static double DefenceEnhance(Player player) {
        if (!IsOn(player)) return 0;
        if (PlayerAttributes.PlayerManaDamage(player) > 10000)
            return (PlayerAttributes.PlayerManaDamage(player) - 10000) * 0.1 / 5000;
        return 0;
    }

    public static double ManaRecover(Player player) {
        if (!IsOn(player)) return 0;
        if (PlayerAttributes.PlayerDefence(player) > 1000) return 25;
        return 0;
    }
}
