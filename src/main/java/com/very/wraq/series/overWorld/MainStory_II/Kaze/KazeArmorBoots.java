package com.very.wraq.series.overWorld.MainStory_II.Kaze;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class KazeArmorBoots extends ArmorItem {
    final double Speed = 0.5;
    final double AttackDamage = 200;
    final double ManaDamage = 800;
    public KazeArmorBoots(ItemMaterial Material, Type Slots)
    {
        super(Material,Slots,new Properties().rarity(Utils.KazeItalic));
        Utils.MovementSpeed.put(this, Speed);
        Utils.AttackDamage.put(this,AttackDamage);
        Utils.ManaDamage.put(this,ManaDamage);
        Utils.ArmorTag.put(this,1d);
        Utils.ArmorList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("翠绿足具").withStyle(CustomStyle.styleOfKaze).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("靴子").withStyle(CustomStyle.styleOfKaze)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfKaze,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfKaze,ChatFormatting.WHITE);
        components.add(Component.literal(" "));
        components.add(Component.literal("KazeArmor-I").withStyle(CustomStyle.styleOfKaze).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
