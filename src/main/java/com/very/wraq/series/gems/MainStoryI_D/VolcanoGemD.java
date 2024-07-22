package com.very.wraq.series.gems.MainStoryI_D;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class VolcanoGemD extends Item {
    private final double AttackDamage = 20;
    private final double CritDamage = 0.2;

    public VolcanoGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsAttackDamage.put(StringUtils.GemName.VolcanoGemD, AttackDamage);
        Utils.gemsCritDamage.put(StringUtils.GemName.VolcanoGemD, CritDamage);
        Utils.attackDamage.put(this, AttackDamage);
        Utils.critDamage.put(this, CritDamage);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        components.add(Component.literal("火山意志的具象，凝聚于此石。").withStyle(ChatFormatting.YELLOW));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.YELLOW, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionBaseAttackDamage(components,AttackDamage);
        Compute.EmojiDescriptionCritDamage(components,CritDamage);*/
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.YELLOW, ChatFormatting.WHITE);
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
