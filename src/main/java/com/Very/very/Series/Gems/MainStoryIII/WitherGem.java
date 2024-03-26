package com.Very.very.Series.Gems.MainStoryIII;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class WitherGem extends Item {
    private final double CritDamage = 0.3;
    private final double MaxHealth = - 500;

    public WitherGem(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsCritDamage.put(StringUtils.GemName.WitherGem,CritDamage);
        Utils.GemsMaxHealth.put(StringUtils.GemName.WitherGem,MaxHealth);
        Utils.CritDamage.put(this,CritDamage);
        Utils.MaxHealth.put(this,MaxHealth);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = Utils.styleOfPower;
        components.add(Component.literal("用能量灌注凋零残骨所打造而成。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionCritDamage(components,CritDamage);
        Compute.EmojiDescriptionMaxHealth(components,MaxHealth);*/
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
