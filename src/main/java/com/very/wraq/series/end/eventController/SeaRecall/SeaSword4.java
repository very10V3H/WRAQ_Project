package com.very.wraq.series.end.eventController.SeaRecall;

import com.very.wraq.process.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class SeaSword4 extends SwordItem{
    private double BaseDamage = 200.0d;
    private double BreakDefence = 0.20d;
    private double DefencePenetration0 = 1800;
    private double CriticalHitRate = 0.3F;
    private double CHitDamage = 0.5;
    private double HealSteal = 0.05F;
    private double SpeedUp = 0.4F;
    private double AttackRangeUp = 2.5F;
    private final double AttackSpeedUp = -2f;
    public SeaSword4(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties().rarity(Utils.MagmaItalic));
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration.put(this,this.BreakDefence);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.AttackRangeUp.put(this,this.AttackRangeUp);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Element.WaterElementValue.put(this, 1d);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SwordTag.put(this,1d);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        Compute.USE(player);
        return super.use(level, player, useHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("灵魂救赎者").withStyle(CustomStyle.styleOfSea).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长刃剑").withStyle(CustomStyle.styleOfSea)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfSea,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE, CustomStyle.styleOfSea,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("海葬").withStyle(CustomStyle.styleOfSea));
        components.add(Component.literal("使下一次的普通攻击附带:").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal("基于目标已损失生命值造成至多4倍的").withStyle(CustomStyle.styleOfSea).
                append(Component.literal("等级强度").withStyle(CustomStyle.styleOfLucky)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal("倍率随目标已损失生命值线性增长").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("若目标死亡，则获得自身").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("40%")).
                append(Component.literal("的生命回复。").withStyle(ChatFormatting.WHITE)));
        Compute.CoolDownTimeDescription(components,3);
        Compute.ManaCostDescription(components,10);
        components.add(Component.literal("Sea-Sword-X").withStyle(CustomStyle.styleOfSea).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("Intensified-Sea").withStyle(CustomStyle.styleOfVolcano).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal(" "));
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
}
