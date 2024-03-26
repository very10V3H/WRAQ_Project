package com.Very.very.Series.OverWorldSeries.IceSeries;

import com.Very.very.ValueAndTools.BasicAttributeDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LeatherArmor extends ArmorItem {
    private final double Defence = 33;
    private final double MaxHealth = 333;
    private final double BootsMovementSpeed = 0.4;
    private final Style style = Utils.styleOfSnow;
    private final int Num;
    private final String[] Type = {
            "头盔","胸甲","护腿","靴子"
    };
    public LeatherArmor(ItemMaterial Material, Type Slots, int Num)
    {
        super(Material,Slots,new Properties().rarity(Utils.SnowItalic));
        this.Num = Num;
        Utils.Defence.put(this, Defence);
        if (Num != 3) Utils.MaxHealth.put(this, MaxHealth);
        if (Num == 3) Utils.MovementSpeed.put(this, BootsMovementSpeed);
        Utils.ArmorTag.put(this,1d);
        Utils.ArmorList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("天空之眼").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).
                append(Component.literal(Type[Num]).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("厚实皮囊").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 你在冰雪中的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("寒冷值").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("提升速度").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("降低90%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal("LeatherArmor").withStyle(Utils.styleOfSnow).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfIce(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
