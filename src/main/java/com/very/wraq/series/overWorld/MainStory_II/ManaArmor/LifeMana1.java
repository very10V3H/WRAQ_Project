package com.very.wraq.series.overWorld.MainStory_II.ManaArmor;

import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LifeMana1 extends ArmorItem {
    private static final Style style = CustomStyle.styleOfHealth;
    private String type = "";
    public LifeMana1(ItemMaterial Material, Type Slots, Properties itemProperties, int type)
    {
        super(Material,Slots,itemProperties);
        Utils.Defence.put(this,30d);
        Utils.ManaDamage.put(this,70d);
        Utils.MaxHealth.put(this,100d);
        Utils.MaxMana.put(this,20d);
        Utils.ManaPenetration0.put(this,25d);
        Utils.ManaRecover.put(this,5d);
        Utils.HealthRecover.put(this,5d);
        Utils.MovementSpeed.put(this,0.1);
        Utils.CoolDownDecrease.put(this,0.1);
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
        Compute.DescriptionPassive(components,Component.literal("自然能量迸发").withStyle(CustomStyle.styleOfHealth));
        components.add(Component.literal(" 每穿着一件生机自然魔力装备，都会使你的").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(ModItems.PlainPower.get().getDefaultInstance().getDisplayName()).
                append(Component.literal(" ")).
                append(ModItems.ForestPower.get().getDefaultInstance().getDisplayName()));
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CoolDown("")).
                append(Component.literal("减少").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1s").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaCost("")).
                append(Component.literal("减少")).
                append(Component.literal("10").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
