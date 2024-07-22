package com.very.wraq.series.overworld.chapter2.kaze;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class KazeArmorBoots extends ArmorItem {

    final double AttackDamage = 250;
    final double ManaDamage = 500;

    public KazeArmorBoots(ItemMaterial Material, Type Slots) {
        super(Material, Slots, new Properties().rarity(CustomStyle.KazeItalic));
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.6);
        Utils.attackDamage.put(this, AttackDamage);
        Utils.manaDamage.put(this, ManaDamage);
        Utils.swiftnessUp.put(this, 1d);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("靴子").withStyle(CustomStyle.styleOfKaze)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfKaze, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfKaze, ChatFormatting.WHITE);
        components.add(Component.literal(" "));
        components.add(Component.literal("KazeArmor-I").withStyle(CustomStyle.styleOfKaze).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
