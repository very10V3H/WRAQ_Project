package com.very.wraq.customized.players.sword.LXYZO;

import com.very.wraq.customized.Customize;
import com.very.wraq.render.toolTip.CustomStyle;
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

public class LXYZO_Sword extends SwordItem{
    private final double BaseDamage = Customize.AttackDamage;
    private final double DefencePenetration0 = Customize.DefencePenetration0;
    private final double CriticalHitRate = Customize.CritRate;
    private final double CHitDamage = Customize.CritDamage;
    private final double HealSteal = Customize.HealthSteal;
    private final double SpeedUp = Customize.MovementSpeed;
    private final double AttackSpeedUp = -2f;
    public LXYZO_Sword(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties().rarity(Utils.SpringItalic));
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.CustomizedList.add(this);
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
        Style style = CustomStyle.styleOfSpring;
        Compute.ForgingHoverName(stack,Component.literal("妖刀-樱").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("龙刀").withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("龙吟").withStyle(style));
        components.add(Component.literal(" 你的下次普通攻击将获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("244%伤害提升").withStyle(style)).
                append(Component.literal("并使目标附带流血效果，持续3s").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components,Component.literal("隐龙").withStyle(CustomStyle.styleOfSpring));
        components.add(Component.literal(" 当").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("高于244时，获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("122%")).
                append(Component.literal(" 高于244的部分每244点护甲提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("10%")));
        components.add(Component.literal(" 对流血的目标额外造成").withStyle(ChatFormatting.WHITE).
                append(Component.literal("244%伤害").withStyle(style)));
        components.add(Component.literal(" 新年快乐！龙年大吉！").withStyle(CustomStyle.styleOfSpring));
        Compute.CoolDownTimeDescription(components,10);
        Compute.ManaCostDescription(components,20);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一把龙渊，授予对维瑞阿契做出了杰出贡献的 - LXYZO").withStyle(ChatFormatting.AQUA));
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
