package com.very.wraq.series.gems.MainStoryIII_D;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class WitherGemD extends Item {
    private final double CritDamage = 0.6;
    private final double MaxHealth = -1000;

    public WitherGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsCritDamage.put(StringUtils.GemName.WitherGemD, CritDamage);
        Utils.gemsMaxHealth.put(StringUtils.GemName.WitherGemD, MaxHealth);
        Utils.critDamage.put(this, CritDamage);
        Utils.maxHealth.put(this, MaxHealth);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfPower;
        components.add(Component.literal("用能量灌注凋零残骨所打造而成。").withStyle(style));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionCritDamage(components,CritDamage);
        Compute.EmojiDescriptionMaxHealth(components,MaxHealth);*/
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
