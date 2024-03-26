package com.Very.very.Series.OverWorldSeries.MainStory_I.Main1Boss;

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

public class LVCord extends Item {
    private final double ExpUp = 0.75f;
    protected double CriticalRate = 0.1F;
    protected double CoolDown = 0.2F;
    protected double MaxHealth = 150.0d;
    protected double Damage = 20.0d;
    public LVCord(Properties p_41383_) {
        super(p_41383_);
        Utils.ExpUp.put(this,ExpUp);
        Utils.CritRate.put(this,CriticalRate);
        Utils.CoolDownDecrease.put(this,CoolDown);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.AttackDamage.put(this,Damage);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("四元之证-传说-其二").withStyle(ChatFormatting.DARK_AQUA).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("传说之证").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_AQUA,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_AQUA,ChatFormatting.WHITE);
        components.add(Component.literal("Legend-Star-II").withStyle(ChatFormatting.DARK_AQUA).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI-Fin.").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
