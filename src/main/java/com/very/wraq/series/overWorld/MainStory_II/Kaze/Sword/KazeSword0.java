package com.very.wraq.series.overWorld.MainStory_II.Kaze.Sword;

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

public class KazeSword0 extends SwordItem{
    private final double BaseDamage = 80.0d;
    private double DefencePenetration0 = 1200;
    private final double CriticalHitRate = 0.30d;
    private final double CHitDamage = 0.4;
    private final double HealSteal = 0.05F;
    private final double SpeedUp = 0.4F;
    private final double AttackSpeedUp = -2f;
    public KazeSword0(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties().rarity(Utils.KazeItalic));
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,DefencePenetration0);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Element.WindElementValue.put(this, 0.2);
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
        Compute.ForgingHoverName(stack,Component.literal("灵风").withStyle(CustomStyle.styleOfKaze).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长剑").withStyle(ChatFormatting.AQUA)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfKaze,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfKaze,ChatFormatting.WHITE);
        KazeSword0.KazeSwordCommonDescription(components,"200%","0");
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
    public static void KazeSwordCommonDescription (List<Component> components, String DamageRate, String Num) {
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("1.飓风:").withStyle(CustomStyle.styleOfKaze));
        components.add(Component.literal("若周围无在空中的单位，").withStyle(ChatFormatting.WHITE).
                append(Component.literal("击飞").withStyle(CustomStyle.styleOfKaze)).
                append(Component.literal("周围的所有单位。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("冷却时间: 0.8s").withStyle(ChatFormatting.WHITE).
                append(Component.literal(" 不可被冷却缩减减少").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal("2.狂风绝息:").withStyle(CustomStyle.styleOfKaze));
        components.add(Component.literal("若周围有单位位于空中，则对位于空中的所有单位造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage(DamageRate)).
                append(Component.literal("的物理伤害，并将其强制牵引至地面").withStyle(ChatFormatting.WHITE)));
        Compute.CoolDownTimeDescription(components,3);
        Compute.ManaCostDescription(components,15);
        components.add(Component.literal("Kaze-Sword-" + Num).withStyle(CustomStyle.styleOfKaze).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);
    }
}
