package com.very.wraq.series.overWorld.SakuraSeries.BloodMana;

import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
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

public class BloodManaArmor extends ArmorItem {
    private static final Style style = CustomStyle.styleOfBloodMana;
    private String type = "";
    public BloodManaArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type)
    {
        super(Material,Slots,itemProperties);
        Utils.MaxHealth.put(this,800d);
        Utils.AttackDamage.put(this,100d);
        Utils.Defence.put(this,200d);
        Utils.ManaDefence.put(this,200d);
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
        Compute.ForgingHoverName(stack,Component.literal("").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal(type).withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("腥月初升").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.HealthSteal("")).
                append(Component.literal("效能提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("35%").withStyle(style)));
        Compute.DescriptionPassive(components,Component.literal("永升腥月").withStyle(style));
        components.add(Component.literal(" 当你的生命偷取将生命值补满后，溢出的治疗量将化为").withStyle(ChatFormatting.WHITE).
                append(Component.literal("血雨").withStyle(style)));
        components.add(Component.literal(" 血雨").withStyle(style).
                append(Component.literal("将对周围怪物造成等同于治疗量").withStyle(style)).
                append(Component.literal("100%").withStyle(style)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" -多件腥月魔力防具可以线性增长被动效能。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
