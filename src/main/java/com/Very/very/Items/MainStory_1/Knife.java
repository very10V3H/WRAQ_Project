package com.Very.very.Items.MainStory_1;

import com.Very.very.VMD;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Knife extends SwordItem {
    private float BaseDamage = 5.0F;
    private float BreakDefence = 0.05F;
    private float CriticalHitRate = 0.1F;
    private float CHitDamage = 0.1F;
    private float HealSteal = 0.05F;
    private float SpeedUp = 0.2F;
    public Knife(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties());
        Utils.BaseDamage.put(this,this.BaseDamage);
        Utils.BreakDefence.put(this,this.BreakDefence);
        Utils.HealSteal.put(this,this.HealSteal);
        Utils.CriticalHitRate.put(this,this.CriticalHitRate);
        Utils.CHitDamage.put(this,this.CHitDamage);
        Utils.SpeedUp.put(this,this.SpeedUp);
        Utils.MainHandTag.put(this,1.0F);
        Utils.SwordTag.put(this,1.0F);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("瑞士军刀").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.WHITE,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.BasicSwordDescription(components,BaseDamage,BreakDefence,CriticalHitRate,CHitDamage,HealSteal,SpeedUp);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.WHITE,ChatFormatting.WHITE);
        components.add(Component.literal("PreSent").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryO").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        p_43278_.hurtAndBreak(0, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
}
