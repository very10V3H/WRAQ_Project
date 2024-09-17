package com.very.wraq.series.gems.MainStoryIII;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
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
    private final double MaxHealth = -500;

    public WitherGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsCritDamage.put(StringUtils.GemName.WitherGem, CritDamage);
        Utils.gemsMaxHealth.put(StringUtils.GemName.WitherGem, MaxHealth);
        Utils.critDamage.put(this, CritDamage);
        Utils.maxHealth.put(this, MaxHealth);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfPower;
        components.add(Component.literal("用能量灌注凋零残骨所打造而成。").withStyle(style));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
/*        Compute.EmojiDescriptionCritDamage(components,CritDamage);
        Compute.EmojiDescriptionMaxHealth(components,MaxHealth);*/
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfChapterIII(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
