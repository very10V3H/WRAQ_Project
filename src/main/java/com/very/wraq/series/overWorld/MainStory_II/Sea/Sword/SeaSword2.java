package com.very.wraq.series.overWorld.MainStory_II.Sea.Sword;

import com.very.wraq.process.element.Element;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
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

public class SeaSword2 extends SwordItem{
    private double BaseDamage = 90.0d;
    private double DefencePenetration0 = 1400;
    private double CriticalHitRate = 0.3F;
    private double CHitDamage = 0.55;
    private double HealSteal = 0.05F;
    private double SpeedUp = 0.3F;
    private double AttackRangeUp = 1.5F;
    private final double AttackSpeedUp = -2f;
    public SeaSword2(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties().rarity(Utils.SeaItalic));
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.AttackRangeUp.put(this,this.AttackRangeUp);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Element.WaterElementValue.put(this, 0.6);
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
        Compute.ForgingHoverName(stack,Component.literal("灵魂救赎者-II").withStyle(CustomStyle.styleOfSea).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长刃剑").withStyle(CustomStyle.styleOfSea)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfSea,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfSea,ChatFormatting.WHITE);
        SeaSword0.SeaSwordCommonDescription(components,"II");
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
