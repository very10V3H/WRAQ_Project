package com.very.wraq.customized.players.sword.Crush;

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

public class ZeusCurios extends Item implements ICurioItem {

    public ZeusCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.AttackDamage.put(this, Attributes.AttackDamage);
        Utils.DefencePenetration0.put(this,Attributes.DefencePenetration0);
        Utils.CritDamage.put(this,Attributes.CritDamage);
        Utils.Defence.put(this,Attributes.Defence);
        Utils.CritRate.put(this,Attributes.CritRate);
        Utils.CustomizedList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("宙斯之怒").withStyle(CustomStyle.styleOfLightingIsland));
        components.add(Component.literal(" 每过3s，你的下一次普通近战攻击附带").withStyle(ChatFormatting.WHITE).
                append(Component.literal("怒雷").withStyle(CustomStyle.styleOfLightingIsland)));
        components.add(Component.literal(" 怒雷将使你的攻击附带").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExAttackDamage("360%")).
                append(Component.literal("物理伤害").withStyle(CustomStyle.styleOfPower)));
        Compute.DescriptionPassive(components,Component.literal("封尘").withStyle(CustomStyle.styleOfLightingIsland));
        components.add(Component.literal(" 攻击将会提供").withStyle(ChatFormatting.WHITE).
                append(Component.literal("1").withStyle(CustomStyle.styleOfLightingIsland)).
                append(Component.literal("层充能").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("，暴击将会提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("2").withStyle(CustomStyle.styleOfLightingIsland)).
                append(Component.literal("层充能").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 充能将会至多提供").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("50%")).
                append(Component.literal(" 在满层时，获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("7.5%").withStyle(CustomStyle.styleOfLightingIsland)).
                append(Component.literal("伤害提升并获得等量").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" -众神之父拥有无尽神力，可以瞬间摧毁方块。").withStyle(CustomStyle.styleOfLightingIsland));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚宙斯勋章，授予对维瑞阿契做出了杰出贡献的 - Crush1").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Utils.Crush1 = (Player) slotContext.entity();
        Utils.Crush1ZeusIsOn = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Utils.Crush1 = null;
        Utils.Crush1ZeusIsOn = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
