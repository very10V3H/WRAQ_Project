package com.very.wraq.process.qingMing;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
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

public class QingMingGem extends Item {

    private final double AttackDamage = 135;
    private final double ManaDamage = 540;
    private final double MovementSpeed = 0.3;

    public QingMingGem(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsAttackDamage.put(StringUtils.GemName.QingMingGem,AttackDamage);
        Utils.GemsManaDamage.put(StringUtils.GemName.QingMingGem,ManaDamage);
        Utils.GemsSpeedUp.put(StringUtils.GemName.QingMingGem,MovementSpeed);
        Utils.AttackDamage.put(this,AttackDamage);
        Utils.ManaDamage.put(this,ManaDamage);
        Utils.MovementSpeed.put(this,MovementSpeed);
        Utils.GemsTag.put(this, 1);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfForest;
        components.add(Component.literal("雨纷纷，欲断魂").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionDefencePenetration(components,DefencePenetration);
        Compute.EmojiDescriptionManaPenetration(components,ManaPenetration);*/
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        QingTuan.QingMingSuffix(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
