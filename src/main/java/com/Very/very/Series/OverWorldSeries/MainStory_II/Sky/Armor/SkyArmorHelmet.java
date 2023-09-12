package com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Armor;

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

public class SkyArmorHelmet extends ArmorItem {
    private final float defence = 10.0F;
    private final float healReply = 2F;
    private final float healup = 100.0F;
    public SkyArmorHelmet(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties().rarity(Rarity.UNCOMMON));
        Utils.Defence.put(this, defence);
        Utils.HealReply.put(this, healReply);
        Utils.HealUp.put(this,healup);
        Utils.ArmorTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("天空之眼").withStyle(Utils.styleOfSky).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("头盔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfSky,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionDefence(components,defence);
        Compute.EmojiDescriptionMaxHealth(components,healup);
        Compute.EmojiDescriptionHealthRecover(components,healReply);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfSky,ChatFormatting.WHITE);
        SkyArmorHelmet.SkyArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static void SkyArmorCommonDescription (List<Component> components) {
        Compute.DescriptionOfAddtion(components);
        Compute.SuitDescription(components);
        Compute.DescriptionPassive(components,Component.literal("天空的加护").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSky));
        components.add(Component.literal("1.").
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("高于80%时，提升")).
                append(Compute.AttributeDescription.AttackDamage("25%")).
                append(Component.literal("与").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MovementSpeed("20%")));
        components.add(Component.literal("2.").
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("介于40%与80%之间时，提升")).
                append(Compute.AttributeDescription.AttackDamage("10%")));
        components.add(Component.literal("3.").
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("低于40%时，每十秒获得")).
                append(Compute.AttributeDescription.AttackDamage("10%")).
                append(Component.literal("护盾。")).
                append(Component.literal("持续10s")).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfSky,ChatFormatting.WHITE);
        components.add(Component.literal("SkyArmor-I").withStyle(Utils.styleOfSky).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);
    }
}
