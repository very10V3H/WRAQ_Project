package com.Very.very.Series.OverWorldSeries.MainStory_I.Gems;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class EvokerGem extends Item {
    private final float ManaDamage = 20;
    private final float ManaRecover = 2;

    public EvokerGem(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsManaDamage.put("EvokerGem",ManaDamage);
        Utils.GemsManaRecover.put("EvokerGem",ManaRecover);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        components.add(Component.literal("蕴含唤魔力量的宝石。").withStyle(Utils.styleOfMana));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionManaAttackDamage(components,ManaDamage);
        Compute.EmojiDescriptionManaRecover(components,ManaRecover);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        components.add(Component.literal("Evoker-Gem-0").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.UNDERLINE));
        components.add(Component.literal("MainStoryII").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
