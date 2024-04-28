package com.very.wraq.series.gems.MainStoryI_D;

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

public class LakeGemD extends Item {

    private final double CoolDown = 0.2;
    private final double SpeedUp = 0.2;
    public LakeGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsCoolDown.put(StringUtils.GemName.LakeGemD,CoolDown);
        Utils.GemsSpeedUp.put(StringUtils.GemName.LakeGemD,SpeedUp);
        Utils.CoolDownDecrease.put(this,CoolDown);
        Utils.MovementSpeed.put(this,SpeedUp);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        components.add(Component.literal("湖泊意志的具象，凝聚于此石。").withStyle(ChatFormatting.DARK_BLUE));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionCoolDown(components,CoolDown);
        Compute.EmojiDescriptionMovementSpeed(components,SpeedUp);*/
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
