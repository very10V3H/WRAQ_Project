package com.very.wraq.series.overWorld.MainStory_I.Forest.Sword;

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
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class ForestSword extends AxeItem {
    private final int Num;
    public ForestSword(Tier p_40521_, float p_40522_, float p_40523_, Properties p_40524_, int Num) {
        super(p_40521_, p_40522_, p_40523_, p_40524_);
        Utils.AttackDamage.put(this,ForestSwordAttributes.BaseDamage[Num]);
        Utils.DefencePenetration0.put(this,ForestSwordAttributes.DefencePenetration0[Num]);
        Utils.HealthSteal.put(this,ForestSwordAttributes.HealthSteal[Num]);
        Utils.CritRate.put(this,ForestSwordAttributes.CriticalRate[Num]);
        Utils.CritDamage.put(this,ForestSwordAttributes.CriticalDamage[Num]);
        Utils.MovementSpeed.put(this,ForestSwordAttributes.MovementSpeed[Num]);
        Utils.AttackSpeedUp.put(this,ForestSwordAttributes.AttackSpeedUp[Num]);
        Element.LifeElementValue.put(this, ForestSwordAttributes.LifeElementValue[Num]);
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
        Compute.ForgingHoverName(stack,Component.literal("森林粉碎者").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Utils.WeaponTypeComponents.AxeType));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("切割/砍伐").withStyle(ChatFormatting.DARK_RED));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.DefencePenetration("15%+[50]")).
                append(Component.literal("，持续" + (int) ForestSwordAttributes.EffectNum[Num] + "s").withStyle(ChatFormatting.WHITE)));
        Compute.CoolDownTimeDescription(components,30);
        Compute.ManaCostDescription(components,20);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("Forest-Axe" + ForestSwordAttributes.Number[Num]).withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        p_40994_.hurtAndBreak(0, p_40996_, (p_41007_) -> {
            p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
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
