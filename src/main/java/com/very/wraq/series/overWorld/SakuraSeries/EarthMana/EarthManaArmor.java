package com.very.wraq.series.overWorld.SakuraSeries.EarthMana;

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

public class EarthManaArmor extends ArmorItem {
    private static final Style style = CustomStyle.styleOfBloodMana;
    private String type = "";
    public EarthManaArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type)
    {
        super(Material,Slots,itemProperties);
        Utils.MaxHealth.put(this,800d);
        Utils.ManaDamage.put(this,400d);
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
        Compute.DescriptionPassive(components,Component.literal("旧世斩魔遗怒").withStyle(style));
        components.add(Component.literal(" 将你消耗的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("300%")).
                append(Component.literal("转化为").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Health("")));
        Compute.DescriptionPassive(components,Component.literal("新世唤魔复生").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("将根据目标的当前生命值附带至多").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.25倍").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("额外魔法伤害").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" -多件地蕴魔力防具可以线性增长被动效能。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
