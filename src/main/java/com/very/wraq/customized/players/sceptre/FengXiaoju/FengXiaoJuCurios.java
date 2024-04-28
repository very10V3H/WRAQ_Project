package com.very.wraq.customized.players.sceptre.FengXiaoju;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
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

public class FengXiaoJuCurios extends Item implements ICurioItem {

    public FengXiaoJuCurios(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfLightingIsland;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("z字驱动共振").withStyle(style));
        components.add(Component.literal(" 每当使用").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法杖").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("对相同目标进行第三次攻击额外造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("5倍").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionPassive(components,Component.literal("风暴呼啸").withStyle(style));
        components.add(Component.literal(" 当").withStyle(ChatFormatting.WHITE).
                append(Component.literal("任意伤害").withStyle(CustomStyle.styleOfSea)).
                append(Component.literal("目中目标时，若").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("3s").withStyle(style)).
                append(Component.literal("对目标造成的伤害积累达到目标").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("35%")).
                append(Component.literal("将降下一道").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("风暴之雷").withStyle(style)));
        components.add(Component.literal(" -风暴之雷会对目标造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("20%")).
                append(Compute.AttributeDescription.ManaDamageValue("")).
                append(Component.literal("，并对周围目标造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamageValue("5000%")));
        components.add(Component.literal(" -每个目标仅能生效一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("风暴之雷").withStyle(style)).
                append(Component.literal("，当风暴之雷无法生效时，则为你提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("15%")).
                append(Component.literal(" 持续8s，最多叠加至5层").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 持有者扭转时间，能够大幅减少破坏方块所需的时间").withStyle(style));
        components.add(Component.literal(" 时间，不在于你拥有多少，而在于你怎样使用").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚z型狂涌之心，授予对维瑞阿契做出了杰出贡献的 - Fengxiaoju").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Utils.Fengxiaoju = (Player) slotContext.entity();
        Utils.FengxiaojuCurios = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Utils.Fengxiaoju = null;
        Utils.FengxiaojuCurios = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsOn(Player player) {
        return Utils.Fengxiaoju != null && Utils.Fengxiaoju.equals(player) && Utils.FengxiaojuCurios;
    }
}
