package com.very.wraq.series.overWorld.SakuraSeries.MineWorker;

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

public class PurpleArmor extends ArmorItem {
    private static final Style style = CustomStyle.styleOfPurpleIron;
    private String type = "";
    public PurpleArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type)
    {
        super(Material,Slots,itemProperties);
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
        Compute.DescriptionPassive(components,Component.literal("紫晶能量屏障").withStyle(style));
        components.add(Component.literal("每过10s，提供持续5s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("10% - 40%")).
                append(Component.literal("的护盾").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("数值基于已装备的紫晶铁装备数量(1~10% 2~20% 3~30% 4~40%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

}
