package com.very.wraq.series.nether.Equip.Armor;

import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class NetherManaArmor extends ArmorItem {
    private static final Style style = CustomStyle.styleOfNether;
    private String type = "";
    public NetherManaArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type)
    {
        super(Material,Slots,itemProperties);
        Utils.MaxHealth.put(this,500d);
        Utils.Defence.put(this,50d);
        Utils.ManaDamage.put(this,135d);
        Utils.ManaPenetration.put(this,0.08);
        Utils.ManaPenetration0.put(this,75d);
        Utils.ArmorTag.put(this,1d);
        Utils.ArmorList.add(this);
        switch (type) {
            case 0 -> this.type = "头盔";
            case 1 -> this.type = "胸甲";
            case 2 -> this.type = "护腿";
            case 3 -> this.type = "靴子";
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("紫晶铁").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal(type).withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("下界混沌解构术法").withStyle(CustomStyle.styleOfMana));
/*
        components.add(Component.literal(" -使你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法术攻击").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("可以受到").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("至多40%的").withStyle(ChatFormatting.RED)).
                append(Compute.AttributeDescription.CritRate("")).
                append(Compute.AttributeDescription.CritDamage("")).
                append(Component.literal("加成").withStyle(ChatFormatting.RED)));
*/
        components.add(Component.literal(" -你的普通法球攻击与法术攻击将基于目标的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDefence("")).
                append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("12%伤害提升").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" -每件下界混沌装备提供的伤害提升可以线性增长").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" -当目标的魔法抗性达到500时给予满额伤害提升").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
