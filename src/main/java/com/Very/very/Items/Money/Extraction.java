package com.Very.very.Items.Money;

import com.Very.very.VMD;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Extraction extends SwordItem {
    private float BaseDamage = 10.0F;
    private float BreakDefence = 0.0F;
    private float CriticalHitRate = 0.0F;
    private float CHitDamage = 0.0F;
    private float HealSteal = 0.0F;
    private float SpeedUp = 0.1F;
    public Extraction(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Item.Properties());
        Utils.BaseDamage.put(this,this.BaseDamage);
        Utils.BreakDefence.put(this,this.BreakDefence);
        Utils.HealSteal.put(this,this.HealSteal);
        Utils.CriticalHitRate.put(this,this.CriticalHitRate);
        Utils.CHitDamage.put(this,this.CHitDamage);
        Utils.SpeedUp.put(this,this.SpeedUp);
        Utils.MainHandTag.put(this,1.0F);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("萃取").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("特殊").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)));
        components.add(Component.literal("··········································").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.OBFUSCATED).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("BASIC:").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("·基础攻击").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(" 10").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·护甲穿透").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal("+0%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal("+0%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·暴击伤害").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD).append(Component.literal("+0%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·生命偷取").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal("+0%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("·移动速度").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal("+10%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("··········································").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.OBFUSCATED).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("ADDITION:").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("被动:萃取"));
        components.add(Component.literal("击杀一个单位获得1*银币").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" "));
        components.add(Component.literal("Money_Tools").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        p_43278_.hurtAndBreak(1, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
}

