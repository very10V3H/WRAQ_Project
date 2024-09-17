package com.very.wraq.Items.MainStory_1;

import com.very.wraq.common.attribute.BasicAttributeDescription;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Knife extends SwordItem {
    private double BaseDamage = 5.0d;
    private double BreakDefence = 0.05F;
    private double CriticalHitRate = 0.1F;
    private double CHitDamage = 0.1F;
    private double HealSteal = 0.05F;
    private double SpeedUp = 0.2F;
    private final double AttackSpeedUp = -2f;

    public Knife(Tier tier, int num1, float num2) {
        super(tier, num1, num2, new Properties());
        Utils.attackDamage.put(this, this.BaseDamage);
        Utils.defencePenetration.put(this, this.BreakDefence);
        Utils.healthSteal.put(this, this.HealSteal);
        Utils.critRate.put(this, this.CriticalHitRate);
        Utils.critDamage.put(this, this.CHitDamage);
        Utils.movementSpeedWithoutBattle.put(this, this.SpeedUp);
        Utils.attackSpeedUp.put(this, AttackSpeedUp);
        Utils.mainHandTag.put(this, 1d);
        Utils.weaponList.add(this);

        Utils.swordTag.put(this, 1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        stack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        stack.setHoverName(Component.literal("瑞士军刀").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("匕首").withStyle(ChatFormatting.GREEN)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.WHITE, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.WHITE, ChatFormatting.WHITE);
        components.add(Component.literal("PreSent").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryO").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        p_43278_.hurtAndBreak(0, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
}
