package com.very.wraq.series.overworld.chapter1.forest.bossItems;

import com.very.wraq.process.system.element.Element;
import com.very.wraq.common.BasicAttributeDescription;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
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

public class ForestBossSword extends SwordItem {
    private final double BaseDamage = 160.0d;
    private final double BreakDefence = 0.20d;
    private final double CriticalHitRate = 0.30d;
    private final double CHitDamage = 0.35;
    private final double HealSteal = 0.03F;
    private final double SpeedUp = 0.2F;
    private final double AttackSpeedUp = -3.2f;

    public ForestBossSword(Tier tier, int num1, float num2) {
        super(tier, num1, num2, new Properties().rarity(CustomStyle.EntropyItalic));
        Utils.attackDamage.put(this, this.BaseDamage);
        Utils.defencePenetration.put(this, this.BreakDefence);
        Utils.healthSteal.put(this, this.HealSteal);
        Utils.critRate.put(this, this.CriticalHitRate);
        Utils.critDamage.put(this, this.CHitDamage);
        Utils.movementSpeedWithoutBattle.put(this, this.SpeedUp);
        Utils.attackSpeedUp.put(this, AttackSpeedUp);
        Element.LifeElementValue.put(this, 1d);
        Utils.mainHandTag.put(this, 1d);
        Utils.weaponList.add(this);
        Utils.swordTag.put(this, 1d);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        Compute.use(player);
        return super.use(level, player, useHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style MainStyle = CustomStyle.styleOfHealth;
        Compute.forgingHoverName(stack);
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Utils.WeaponTypeComponents.AxeType));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
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
        Compute.CoolDownTimeDescription(components, 10);
        Compute.ManaCostDescription(components, 180);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        components.add(Component.literal("Dimension-Forest").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack, level, components, flag);
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
