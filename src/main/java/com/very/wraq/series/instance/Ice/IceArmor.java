package com.very.wraq.series.instance.Ice;

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

public class IceArmor extends ArmorItem {
    private static final Style style = CustomStyle.styleOfIce;
    private String type = "";
    public IceArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type)
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
        Compute.ForgingHoverName(stack,Component.literal("冰霜").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal(type).withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionPassive(components,Component.literal("寒意释放").withStyle(style));
        components.add(Component.literal(" 每3s对周围单位造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("15%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("魔法伤害").withStyle(ChatFormatting.YELLOW)).
                append(Component.literal(" + ").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("50%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("物理伤害").withStyle(ChatFormatting.LIGHT_PURPLE)));
        components.add(Component.literal("并施加持续2s的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("减速效果").withStyle(style)));
        Compute.DescriptionPassive(components,Component.literal("雪上覆霜").withStyle(style));
        components.add(Component.literal(" 对").withStyle(ChatFormatting.WHITE).
                append(Component.literal("移动速度受损").withStyle(style)).
                append(Component.literal("的敌人，造成的伤害提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("15%").withStyle(CustomStyle.styleOfIce)));
        components.add(Component.literal(" -多件冰霜骑士装备能够线性提升伤害值").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal("IceMemory").withStyle(style).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }
}
