package com.very.wraq.series.overWorld.MainStory_I.Volcano.Sword;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class VolcanoSword extends SwordItem {
    private final int Num;
    public VolcanoSword(Tier tier, int num1, float num2, int Num){
        super(tier,num1,num2,new Properties().rarity(Utils.VolcanoItalic));
        Utils.AttackDamage.put(this,VolcanoSwordAttributes.BaseDamage[Num]);
        Utils.DefencePenetration0.put(this,VolcanoSwordAttributes.DefencePenetration0[Num]);
        Utils.HealthSteal.put(this,VolcanoSwordAttributes.HealthSteal[Num]);
        Utils.CritRate.put(this,VolcanoSwordAttributes.CriticalRate[Num]);
        Utils.CritDamage.put(this,VolcanoSwordAttributes.CriticalDamage[Num]);
        Utils.MovementSpeed.put(this,VolcanoSwordAttributes.MovementSpeed[Num]);
        Utils.AttackSpeedUp.put(this,VolcanoSwordAttributes.AttackSpeedUp[Num]);
        Element.FireElementValue.put(this, VolcanoSwordAttributes.FireElementValue[Num]);
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
        Compute.ForgingHoverName(stack,Component.literal("火山之心").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("短柄剑").withStyle(ChatFormatting.YELLOW)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.YELLOW,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        VolcanoSwordCommonDescription(components,(int) (VolcanoSwordAttributes.ExCritDamage[Num] * 100),
                (int) VolcanoSwordAttributes.ExAttackDamage[Num],VolcanoSwordAttributes.Number[Num]);
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
    public static void VolcanoSwordCommonDescription (List<Component> components, int CritDamage, int AttackDamage, String Num) {
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.YELLOW,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("喷发").withStyle(ChatFormatting.YELLOW));
        Compute.DescriptionNum(components,"1.获得",Compute.AttributeDescription.CritDamage( CritDamage + "%"),"");
        Compute.DescriptionNum(components,"2.获得",Compute.AttributeDescription.ExAttackDamage(AttackDamage + " "),"");
        components.add(Component.literal("持续5s").withStyle(ChatFormatting.WHITE));
        Compute.CoolDownTimeDescription(components,10);
        Compute.ManaCostDescription(components,20);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.YELLOW,ChatFormatting.WHITE);
        components.add(Component.literal("Volcano-Sword" + Num).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
    }

    public int getNum() {
        return Num;
    }
}
