package com.very.wraq.series.nether.Equip;

import com.very.wraq.series.nether.Equip.Armor.NetherSuitDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class NetherPower extends Item {
    protected double ManaDamage = 40;
    protected double ManaBreakDenfence = 0.2f;
    protected double ManaReply = 5;
    protected double SpeedUp = 0.2f;
    protected double CoolDown = 0.1f;
    public NetherPower(Properties p_41383_) {
        super(p_41383_);
        Utils.AttackDamage.put(this,30d);
        Utils.DefencePenetration.put(this,0.2);
        Utils.ManaDamage.put(this,ManaDamage);
        Utils.ManaPenetration.put(this,ManaBreakDenfence);
        Utils.ManaRecover.put(this,ManaReply);
        Utils.MovementSpeed.put(this,SpeedUp);
        Utils.CoolDownDecrease.put(this,CoolDown);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("下界能量矩阵").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("能量矩阵").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfNether,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfNether,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        NetherSuitDescription.SuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfNether,ChatFormatting.WHITE);
        components.add(Component.literal("Nether-Star").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
