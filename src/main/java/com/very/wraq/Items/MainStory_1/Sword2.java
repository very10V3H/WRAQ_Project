package com.very.wraq.Items.MainStory_1;

import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Sword2 extends SwordItem {
    private double BaseDamage = 12.0d;
    private double BreakDefence = 0.3F;
    private double CriticalHitRate = 0.3F;
    private double CHitDamage = 0.75F;
    private double HealSteal = 0.12F;
    private double SpeedUp = 0.12F;

    public Sword2(Tier tier, int num1, float num2) {
        super(tier, num1, num2, new Properties());
        Utils.attackDamage.put(this, this.BaseDamage);
        Utils.defencePenetration.put(this, this.BreakDefence);
        Utils.healthSteal.put(this, this.HealSteal);
        Utils.critRate.put(this, this.CriticalHitRate);
        Utils.critDamage.put(this, this.CHitDamage);
        Utils.movementSpeedWithoutBattle.put(this, this.SpeedUp);
        Utils.mainHandTag.put(this, 1d);
        Utils.weaponList.add(this);

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.setHoverName(Component.literal("阿契长剑-II").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("··········································").withStyle(ChatFormatting.DARK_AQUA).withStyle(ChatFormatting.OBFUSCATED).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("BASIC:").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("·基础攻击").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(" 12").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·护甲穿透").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal("+30%").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal("+30%").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·暴击伤害").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD).append(Component.literal("+75%").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·生命偷取").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal("+12%").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·移动速度").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal("+12%").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("··········································").withStyle(ChatFormatting.DARK_AQUA).withStyle(ChatFormatting.OBFUSCATED).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Initial?Original-I").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryII").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        return super.hurtEnemy(p_43278_, p_43279_, p_43280_);
    }
}
