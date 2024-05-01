package com.very.wraq.series.overWorld.MainStory_I.Forest.BossItems;

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

public class ForestBossSword extends SwordItem{
    private final double BaseDamage = 160.0d;
    private final double BreakDefence = 0.20d;
    private final double CriticalHitRate = 0.30d;
    private final double CHitDamage = 0.35;
    private final double HealSteal = 0.03F;
    private final double SpeedUp = 0.2F;
    private final double AttackSpeedUp = -3.2f;
    public ForestBossSword(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties().rarity(Utils.EntropyItalic));
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration.put(this,this.BreakDefence);
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
        Style MainStyle = CustomStyle.styleOfHealth;
        Compute.ForgingHoverName(stack,Component.literal("<次元级>").withStyle(CustomStyle.styleOfEntropy).append(Component.literal("森林制造者").withStyle(CustomStyle.styleOfHealth).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Utils.WeaponTypeComponents.AxeType));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("生机维度展开: ").withStyle(MainStyle)).
                append(Component.literal("森林制造者释放制造森林次元的能量，并将周围生物拖入属于森林次元。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("∰1.削减范围内的所有生物").withStyle(ChatFormatting.WHITE).
                append(Component.literal("20 * 森林次元熵").withStyle(MainStyle)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Defence("")));
        components.add(Component.literal("∰2.削减范围内的所有生物").withStyle(ChatFormatting.WHITE).
                append(Component.literal("10 * 森林次元熵").withStyle(MainStyle)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDefence("")));
        Compute.CoolDownTimeDescription(components,10);
        Compute.ManaCostDescription(components,60);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        components.add(Component.literal("Dimension-Forest").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
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

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
