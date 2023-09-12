package com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze;

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

public class KazeArmorBoots extends ArmorItem {
    final float Speed = 0.5F;
    final float AttackDamage = 40.0F;
    public KazeArmorBoots(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties().rarity(Rarity.UNCOMMON));
        Utils.SpeedUp.put(this, Speed);
        Utils.BaseDamage.put(this,AttackDamage);
        Utils.ArmorTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("翠绿足具").withStyle(Utils.styleOfKaze).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("靴子").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfKaze)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfKaze,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionBaseAttackDamage(components,AttackDamage);
        Compute.EmojiDescriptionMovementSpeed(components,Speed);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfKaze,ChatFormatting.WHITE);
        components.add(Component.literal(" "));
        components.add(Component.literal("KazeArmor-I").withStyle(Utils.styleOfKaze).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
