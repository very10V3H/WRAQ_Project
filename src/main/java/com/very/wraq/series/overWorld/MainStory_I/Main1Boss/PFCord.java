package com.very.wraq.series.overWorld.MainStory_I.Main1Boss;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PFCord extends Item {
    private final double ExpUp = 0.75f;
    protected double CriticalRate = 0.1F;
    protected double HealReply = 2.0d;
    protected double MaxHealth = 150.0d;
    protected double Defence = 50.0d;
    public PFCord(Properties p_41383_) {
        super(p_41383_);
        Utils.ExpUp.put(this,ExpUp);
        Utils.CritRate.put(this,CriticalRate);
        Utils.HealthRecover.put(this,HealReply);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.Defence.put(this,Defence);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("四元之证-传说-其一").withStyle(ChatFormatting.DARK_AQUA).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("传说之证").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_AQUA,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_AQUA,ChatFormatting.WHITE);
        components.add(Component.literal("Legend-Star-I").withStyle(ChatFormatting.DARK_AQUA).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI-Fin.").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
