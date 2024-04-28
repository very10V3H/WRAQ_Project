package com.very.wraq.series.nether.Equip.WitherSword;

import com.very.wraq.process.element.Element;
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

public class WitherSword extends SwordItem{
    public final int Num;
    public WitherSword(Tier tier, int num1, float num2, int Num){
        super(tier,num1,num2,new Properties().rarity(Utils.WitherItalic));
        this.Num = Num;
        Utils.AttackDamage.put(this, WitherSwordAttributes.BaseDamage[Num]);
        Utils.DefencePenetration0.put(this, WitherSwordAttributes.DefencePenetration0[Num]);
        Utils.HealthSteal.put(this, WitherSwordAttributes.HealthSteal[Num]);
        Utils.CritRate.put(this, WitherSwordAttributes.CriticalRate[Num]);
        Utils.CritDamage.put(this, WitherSwordAttributes.CriticalDamage[Num]);
        Utils.MovementSpeed.put(this, WitherSwordAttributes.SpeedUp[Num]);
        Utils.AttackSpeedUp.put(this,WitherSwordAttributes.AttackSpeedUp[Num]);
        Element.FireElementValue.put(this, WitherSwordAttributes.FireElementValue[Num]);
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
        Style style = CustomStyle.styleOfWither;
        Compute.ForgingHoverName(stack,Component.literal("凋零碳刃" + WitherSwordAttributes.Number[Num]).withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长剑").withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("碳化").withStyle(style));
        components.add(Component.literal("失去").withStyle(ChatFormatting.RED).
                append(Compute.AttributeDescription.MaxHealth("60%")));
        components.add(Component.literal("获得").withStyle(ChatFormatting.GREEN).
                append(Compute.AttributeDescription.DefencePenetration(WitherSwordAttributes.ActiveEffect[Num])));
        components.add(Component.literal("持续时间: 5s"));
        Compute.CoolDownTimeDescription(components,30);
        Compute.ManaCostDescription(components,20);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal("Wither-Sword" + WitherSwordAttributes.Number[Num]).withStyle(style).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryIII(components);
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
