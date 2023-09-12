package com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ForestArmorBoots extends ArmorItem {
    float defence = 15.0F;
    float speedup = 0.25F;
    public ForestArmorBoots(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Item.Properties());
        Utils.Defence.put(this, defence);
        Utils.SpeedUp.put(this, speedup);
        Utils.ArmorTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("森林靴子").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("靴子").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionDefence(components,defence);
        Compute.EmojiDescriptionMovementSpeed(components,speedup);
        ForestArmorHelmet.ForestArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
}
