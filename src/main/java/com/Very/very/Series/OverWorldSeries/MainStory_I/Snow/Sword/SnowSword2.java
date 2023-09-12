package com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword;

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

public class SnowSword2 extends PickaxeItem {
    public SnowSword2(Tier p_42961_, int p_42962_, float p_42963_, Item.Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
        Utils.BaseDamage.put(this,this.BaseDamage);
        Utils.BreakDefence.put(this,this.BreakDefence);
        Utils.HealSteal.put(this,this.HealSteal);
        Utils.CriticalHitRate.put(this,this.CriticalHitRate);
        Utils.CHitDamage.put(this,this.CHitDamage);
        Utils.SpeedUp.put(this,this.SpeedUp);
        Utils.MainHandTag.put(this,1.0F);
        Utils.SwordTag.put(this,1.0F);
    }
    private float BaseDamage = 65.0F;
    private float BreakDefence = 0.3F;
    private float CriticalHitRate = 0.36F;
    private float CHitDamage = 1.5F;
    private float HealSteal = 0.07F;
    private float SpeedUp = 0.3F;
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("冰川探索者-II").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("镐子").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_AQUA,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.BasicSwordDescription(components,BaseDamage,BreakDefence,CriticalHitRate,CHitDamage,HealSteal,SpeedUp);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_AQUA,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("凿击-Ex").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
        Compute.DescriptionNum(components,"攻击将会大幅降低目标生物的移动速度",Component.literal("2s").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),"");
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_AQUA,ChatFormatting.WHITE);
        components.add(Component.literal("Snow-Pickaxe-II").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        p_40994_.hurtAndBreak(0, p_40996_, (p_41007_) -> {
            p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
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
