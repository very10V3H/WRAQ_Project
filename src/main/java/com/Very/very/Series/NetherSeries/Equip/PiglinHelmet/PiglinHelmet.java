package com.Very.very.Series.NetherSeries.Equip.PiglinHelmet;

import com.Very.very.ValueAndTools.BasicAttributeDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PiglinHelmet extends ArmorItem {
    public final int Num;
    private final ChatFormatting style = ChatFormatting.GOLD;
    public PiglinHelmet(ItemMaterial Material, Type Slots, int Num) {
        super(Material,Slots,new Properties().rarity(Utils.PiglinItalic));
        this.Num = Num;
        Utils.Defence.put(this, PiglinHelmetAttributes.Defence[Num]);
        Utils.MaxHealth.put(this, PiglinHelmetAttributes.MaxHealthUp[Num]);
        Utils.ArmorTag.put(this, 1d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("猪灵信物" + PiglinHelmetAttributes.Number[Num]).withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("头盔").withStyle(ChatFormatting.RESET).withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.SuitDescription(components);
        Compute.DescriptionPassive(components,Component.literal("群攻").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal("基于附近怪物数量，为你提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("怪物数量 * " + PiglinHelmetAttributes.Effect[Num])));
        components.add(Component.literal("最大值：200").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("明明是金头盔，为什么不防猪灵呢？").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.STRIKETHROUGH));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal("PiglinHelmet" + PiglinHelmetAttributes.Number[Num]).withStyle(style).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
