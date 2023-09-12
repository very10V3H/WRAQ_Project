package com.Very.very.Series.OverWorldSeries.MainStory_I.Field;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class FieldSword3 extends SwordItem {
    private float BaseDamage = 60.0F;
    private float BreakDefence = 0.5F;
    private float CriticalHitRate = 0.5F;
    private float CHitDamage = 1.0F;
    private float HealSteal = 0.2F;
    private float SpeedUp = 0.5F;
    public FieldSword3(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Item.Properties());
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
        Compute.ForgingHoverName(stack,Component.literal("收获之镰-III").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("镰刀").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.BasicSwordDescription(components,BaseDamage,BreakDefence,CriticalHitRate,CHitDamage,HealSteal,SpeedUp);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        components.add(Component.literal("被动:收获").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("击杀单位恢复饱食度"));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
        components.add(Component.literal("Plain-Sickle-0").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        p_43278_.hurtAndBreak(0, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
    @Override
    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0F) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }
}
