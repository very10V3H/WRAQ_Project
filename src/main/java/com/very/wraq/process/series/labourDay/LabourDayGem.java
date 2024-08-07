package com.very.wraq.process.series.labourDay;

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

public class LabourDayGem extends Item {

    private final double AttackDamage = 155;
    private final double ManaDamage = 310;
    private final double MovementSpeed = 0.4;

    public LabourDayGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsAttackDamage.put(StringUtils.GemName.LabourDayGem, AttackDamage);
        Utils.gemsManaDamage.put(StringUtils.GemName.LabourDayGem, ManaDamage);
        Utils.gemsSpeedUp.put(StringUtils.GemName.LabourDayGem, MovementSpeed);
        Utils.attackDamage.put(this, AttackDamage);
        Utils.manaDamage.put(this, ManaDamage);
        Utils.movementSpeedWithoutBattle.put(this, MovementSpeed);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        ChatFormatting style = ChatFormatting.GOLD;
        components.add(Component.literal("这是最后的斗争！").withStyle(style));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        OldCoin.LabourDaySuffix(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
