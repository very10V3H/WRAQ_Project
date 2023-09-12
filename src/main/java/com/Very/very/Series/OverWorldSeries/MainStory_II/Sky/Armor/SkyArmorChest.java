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

public class SkyArmorChest extends ArmorItem {
    private final float defence = 10.0F;
    private final float critRate = 0.20F;
    private final float healup = 100.0F;
    public SkyArmorChest(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties().rarity(Rarity.UNCOMMON));
        Utils.Defence.put(this, defence);
        Utils.CriticalHitRate.put(this, critRate);
        Utils.HealUp.put(this,healup);
        Utils.ArmorTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("天空之心").withStyle(Utils.styleOfSky).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("胸甲").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfSky,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionDefence(components,defence);
        Compute.EmojiDescriptionMaxHealth(components,healup);
        Compute.EmojiDescriptionCritRate(components,critRate);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfSky,ChatFormatting.WHITE);
        SkyArmorHelmet.SkyArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
