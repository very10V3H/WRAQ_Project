package com.very.wraq.series.overWorld.MainStory_I.Plain.Sword;

import com.very.wraq.process.element.Element;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class PlainSword extends SwordItem{
    private final int Num;
    public PlainSword(Tier tier, int num1, float num2, int Num){
        super(tier,num1,num2,new Item.Properties().rarity(Utils.PlainItalic));
        Utils.AttackDamage.put(this,PlainSwordAttributes.BaseDamage[Num]);
        Utils.DefencePenetration.put(this,PlainSwordAttributes.DefencePenetration[Num]);
        Utils.HealthSteal.put(this,PlainSwordAttributes.HealthSteal[Num]);
        Utils.CritRate.put(this,PlainSwordAttributes.CriticalRate[Num]);
        Utils.CritDamage.put(this,PlainSwordAttributes.CriticalDamage[Num]);
        Utils.MovementSpeed.put(this,PlainSwordAttributes.MovementSpeed[Num]);
        Utils.AttackSpeedUp.put(this,PlainSwordAttributes.AttackSpeedUp[Num]);
        Element.LifeElementValue.put(this, PlainSwordAttributes.LifeElementValue[Num]);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SwordTag.put(this,1d);
        this.Num = Num;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        Compute.USE(player);
        return super.use(level, player, useHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("平原短匕").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("匕首").withStyle(ChatFormatting.GREEN)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("平原洗礼").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal("右键恢复").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health(PlainSwordAttributes.Effect[Num])));
        Compute.CoolDownTimeDescription(components,20);
        Compute.ManaCostDescription(components,20);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("Plain-Knife" + PlainSwordAttributes.Number[Num]).withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
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
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0d) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    public int getNum() {
        return this.Num;
    }
}
