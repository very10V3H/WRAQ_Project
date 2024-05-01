package com.very.wraq.series.overWorld.SakuraSeries.SakuraMob;

import com.very.wraq.process.element.Element;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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

public class SakuraSword extends SwordItem{
    private final double BaseDamage = 240.0d;
    private double DefencePenetration0 = 2400;
    private final double CriticalHitRate = 0.30d;
    private final double CHitDamage = 0.8;
    private final double HealSteal = 0.08F;
    private final double SpeedUp = 0.5F;
    private final double AttackSpeedUp = -2f;
    public SakuraSword(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties().rarity(Utils.SakuraItalic));
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Element.LifeElementValue.put(this, 1d);
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
        Style MainStyle = CustomStyle.styleOfSakura;
        Compute.ForgingHoverName(stack,Component.literal("妖刀-樱").withStyle(CustomStyle.styleOfSakura).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Utils.WeaponTypeComponents.DemonSword));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("樱灵之息").withStyle(MainStyle));
        components.add(Component.literal("手持该妖刀移动将积攒层数。"));
        Compute.DescriptionActive(components,Component.literal("妖化樱灵").withStyle(CustomStyle.styleOfSakura));
        components.add(Component.literal("当层数满时，右键获得持续5s的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("妖化英灵之力").withStyle(CustomStyle.styleOfSakura)));
        Compute.DescriptionPassive(components,Component.literal("妖化樱灵之力").withStyle(CustomStyle.styleOfSakura));
        components.add(Component.literal("1.").withStyle(CustomStyle.styleOfDemon).
                append(Component.literal("无视怪物伤害。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("2.").withStyle(CustomStyle.styleOfDemon).
                append(Component.literal("将普通攻击造成的50%伤害值转换为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal("3.").withStyle(CustomStyle.styleOfDemon).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MovementSpeed("60%")));
        Compute.CoolDownTimeDescription(components,15);
        Compute.ManaCostDescription(components,40);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        components.add(Component.literal("DemonSword_Sakura").withStyle(CustomStyle.styleOfDemon).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryV(components);
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
