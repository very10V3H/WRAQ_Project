
package com.very.wraq.customized.players.sword.Crush;

import com.very.wraq.customized.Customize;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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

public class CrushiSword extends SwordItem{

    public static int ActiveTick = 0;
    private final double BaseDamage = Customize.AttackDamage;
    private final double BreakDefence = Customize.DefencePenetration;
    private final double DefencePenetration0 = Customize.DefencePenetration0;
    private final double CriticalHitRate = Customize.CritRate;
    private final double HealSteal = Customize.HealthSteal;
    private final double SpeedUp = Customize.MovementSpeed;
    private final double AttackSpeedUp = -2f;
    public CrushiSword(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties().rarity(Utils.LightningItalic));
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,Customize.CritDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.CustomizedList.add(this);
        Utils.SwordTag.put(this,1d);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        if (!level.isClientSide) {
            Compute.USE(player,this);
        }
        return super.use(level, player, useHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfLightingIsland;
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("雷刀").withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("雷神之怒").withStyle(style));
        components.add(Component.literal(" 暴击").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("200%伤害提升").withStyle(style)));
        components.add(Component.literal(" 并且将目标标记为").withStyle(ChatFormatting.WHITE).
                append(Component.literal("易损状态").withStyle(style)));
        components.add(Component.literal(" 攻击").withStyle(ChatFormatting.WHITE).
                append(Component.literal("易损状态").withStyle(style)).
                append(Component.literal("的目标额外获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("200%伤害提升").withStyle(style)));
        Compute.DescriptionPassive(components,Component.literal("雷击").withStyle(style));
        components.add(Component.literal(" 每次攻击会对周围目标造成").withStyle(style).
                append(Component.literal("雷击").withStyle(style)));
        components.add(Component.literal(" 雷击会提供").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("250%")).
                append(Component.literal("持续5s，至多叠加至4层").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 并击碎周围目标").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("50%")));
        Compute.DescriptionActive(components,Component.literal("玉女素心剑法").withStyle(style));
        components.add(Component.literal(" 获得持续10s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.DefencePenetration("30%")));
        components.add(Component.literal(" 并使攻击附带基于目标当前生命值的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("4倍").withStyle(CustomStyle.styleOfBlackForest)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("物理伤害").withStyle(CustomStyle.styleOfBlackForest)));
        Compute.CoolDownTimeDescription(components,20);
        Compute.ManaCostDescription(components,60);
        components.add(Component.literal(" 以雷霆，击碎黑暗！").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一把宙斯圣剑，授予对维瑞阿契做出了杰出贡献的 - Crush1").withStyle(ChatFormatting.AQUA));
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

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
