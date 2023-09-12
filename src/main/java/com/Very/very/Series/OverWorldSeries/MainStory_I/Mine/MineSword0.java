package com.Very.very.Series.OverWorldSeries.MainStory_I.Mine;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class MineSword0 extends PickaxeItem {
    public MineSword0(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
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
    private final float BaseDamage = 40.0F;
    private final float BreakDefence = 0.4F;
    private final float CriticalHitRate = 0.8F;
    private final float CHitDamage = 0.5F;
    private final float HealSteal = 0.0F;
    private final float SpeedUp = 0.1F;
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("层岩探索者").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("镐子").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GRAY,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.BasicSwordDescription(components,BaseDamage,BreakDefence,CriticalHitRate,CHitDamage,HealSteal,SpeedUp);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GRAY,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        MineSword0.MineSwordCommonDescription(components,"0");
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        return super.hurtEnemy(p_43278_, p_43279_, p_43280_);
    }

    @Override
    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        return super.mineBlock(p_40998_, p_40999_, p_41000_, p_41001_, p_41002_);
    }
    public static void MineSwordCommonDescription (List<Component> components,String Num) {
        Compute.DescriptionPassive(components,Component.literal("凿击").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));
        Compute.DescriptionNum(components,"攻击将会降低目标生物的移动速度",Component.literal("2s").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),"");
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GRAY,ChatFormatting.WHITE);
        components.add(Component.literal("Mine-Pickaxe-" + Num).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);

    }
}
