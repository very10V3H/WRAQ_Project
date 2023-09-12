package com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ObsiManaArmorHelmet extends ArmorItem {
    float defence = 25.0F;
    float healup = 80.0F;
    float manaup = 10.0F;
    public ObsiManaArmorHelmet(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties());
        Utils.Defence.put(this, defence);
        Utils.HealUp.put(this,healup);
        Utils.ManaUp.put(this,manaup);
        Utils.ArmorTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("黑曜魔力头盔").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.LIGHT_PURPLE,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionDefence(components,defence);
        Compute.EmojiDescriptionMaxHealth(components,healup);
        Compute.EmojiDescriptionMaxMana(components,manaup);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.LIGHT_PURPLE,ChatFormatting.WHITE);
        ObsiManaArmorHelmet.ObsiManaArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static void ObsiManaArmorCommonDescription(List<Component> components) {
        Compute.DescriptionOfAddtion(components);
        Compute.SuitDescription(components);
        Compute.DescriptionPassive(components,Component.literal("黑曜魔力涌动").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana));
        Compute.DescriptionNum(components,"提升",Compute.AttributeDescription.AttackDamage("20%"),"");
        Compute.DescriptionNum(components,"每秒回复",Compute.AttributeDescription.ManaRecover("5"),"");
        Compute.DescriptionNum(components,"获得",Compute.AttributeDescription.CoolDown("25%"),"");
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.LIGHT_PURPLE,ChatFormatting.WHITE);
        components.add(Component.literal("ObsiManaArmor-I").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);
    }
}
