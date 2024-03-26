package com.Very.very.Items.MainStory_1;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;


public class Sword1 extends SwordItem {
    private double BaseDamage = 10.0d;
    private double BreakDefence = 0.3F;
    private double CriticalHitRate = 0.3F;
    private double CHitDamage = 0.75F;
    private double HealSteal = 0.1F;
    private double SpeedUp = 0.1F;
    public Sword1(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties());
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration.put(this,this.BreakDefence);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
                Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("阿契长剑-I").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("··········································").withStyle(ChatFormatting.DARK_AQUA).withStyle(ChatFormatting.OBFUSCATED).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("BASIC:").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("·基础攻击").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(" 10").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·护甲穿透").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal("+30%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal("+30%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·暴击伤害").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD).append(Component.literal("+75%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·生命偷取").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal("+10%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·移动速度").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal("+10%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("··········································").withStyle(ChatFormatting.DARK_AQUA).withStyle(ChatFormatting.OBFUSCATED).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Initial?Original-I").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        return super.hurtEnemy(p_43278_, p_43279_, p_43280_);
    }
}

