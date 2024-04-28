package com.very.wraq.series.end.eventController.BlackForestRecall;

import com.very.wraq.process.element.Element;
import com.very.wraq.render.ToolTip.CustomStyle;
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

public class BlackForestSword4 extends SwordItem{
    private final double BaseDamage = 200.0d;
    private double DefencePenetration0 = 1800;
    private final double CriticalHitRate = 0.3F;
    private final double CHitDamage = 0.5;
    private final double HealSteal = 0.05F;
    private final double SpeedUp = 0.1F;
    private final double AttackRangeUp = 2.0d;
    private final double AttackSpeedUp = -3.2f;
    public BlackForestSword4(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties().rarity(Utils.MagmaItalic));
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.AttackRangeUp.put(this,this.AttackRangeUp);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Element.StoneElementValue.put(this, 1d);
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
        Compute.ForgingHoverName(stack,Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("灵魂收割者").withStyle(CustomStyle.styleOfBlackForest).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("斧头").withStyle(ChatFormatting.DARK_RED)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE, CustomStyle.styleOfBlackForest,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfBlackForest,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("土葬").withStyle(CustomStyle.styleOfBlackForest));
        components.add(Component.literal("使下一次的普通攻击附带:").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal("基于目标当前生命值造成至多4倍的").withStyle(CustomStyle.styleOfBlackForest).
                append(Component.literal("等级强度").withStyle(CustomStyle.styleOfLucky)).
                append(Component.literal("额外物理伤害").withStyle(CustomStyle.styleOfBlackForest)));
        components.add(Component.literal("倍率随目标当前生命值线性增长").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("若目标死亡，则获得自身").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("40%")).
                append(Component.literal("的护盾,持续10s。").withStyle(ChatFormatting.WHITE)));
        Compute.CoolDownTimeDescription(components,3);
        Compute.ManaCostDescription(components,10);
        components.add(Component.literal("BlackForest-Sword-X").withStyle(CustomStyle.styleOfBlackForest).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("Intensified-BlackForest").withStyle(CustomStyle.styleOfVolcano).withStyle(ChatFormatting.ITALIC));
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
