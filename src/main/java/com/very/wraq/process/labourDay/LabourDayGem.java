package com.very.wraq.process.labourDay;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LabourDayGem extends Item {

    private final double AttackDamage = 155;
    private final double ManaDamage = 620;
    private final double MovementSpeed = 0.4;

    public LabourDayGem(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsAttackDamage.put(StringUtils.GemName.LabourDayGem,AttackDamage);
        Utils.GemsManaDamage.put(StringUtils.GemName.LabourDayGem,ManaDamage);
        Utils.GemsSpeedUp.put(StringUtils.GemName.LabourDayGem,MovementSpeed);
        Utils.AttackDamage.put(this,AttackDamage);
        Utils.ManaDamage.put(this,ManaDamage);
        Utils.MovementSpeed.put(this,MovementSpeed);
        Utils.GemsTag.put(this, 1);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        ChatFormatting style = ChatFormatting.GOLD;
        components.add(Component.literal("这是最后的斗争！").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        OldCoin.LabourDaySuffix(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
