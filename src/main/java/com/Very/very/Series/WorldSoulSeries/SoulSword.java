package com.Very.very.Series.WorldSoulSeries;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
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
import java.io.IOException;
import java.util.List;

public class SoulSword extends SwordItem{
    public SoulSword(Tier tier, int num1, float num2, Properties properties){
        super(tier,num1,num2,properties);
        Utils.AttackDamage.put(this,SoulEquipAttribute.BaseAttribute.SoulSword.AttackDamage);
        Utils.CritRate.put(this,SoulEquipAttribute.BaseAttribute.SoulSword.CritRate);
        Utils.CritDamage.put(this,SoulEquipAttribute.BaseAttribute.SoulSword.CritDamage);

        Utils.DefencePenetration0.put(this,SoulEquipAttribute.BaseAttribute.SoulSword.DefencePenetration0);
        Utils.MovementSpeed.put(this,SoulEquipAttribute.BaseAttribute.SoulSword.MovementSpeed);
        Utils.HealthSteal.put(this,SoulEquipAttribute.BaseAttribute.SoulSword.HealthSteal);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SwordTag.put(this,1d);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND) && player.isShiftKeyDown()) {
            try {
                SoulEquipAttribute.Forging(player.getItemInHand(InteractionHand.MAIN_HAND),player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Compute.USE(player);
        return super.use(level, player, interactionHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = Utils.styleOfWorld;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        int KillCount = stack.getOrCreateTagElement(Utils.MOD_ID).getInt(StringUtils.SoulSwordKillCount);
        if (KillCount > 25600) KillCount = 25600;
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Utils.WeaponTypeComponents.NormalSword));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionPassive(components,Component.literal("本源具象").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 击杀怪物以回收").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("世界本源").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("增加").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")));
        components.add(Component.literal(" 当前增加的攻击力为: ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("" + KillCount/100).withStyle(ChatFormatting.RESET).withStyle(style)));
        Compute.DescriptionActive(components,Component.literal("本源打击-咒刃").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 下次攻击造成").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("50%额外物理伤害").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.CoolDownTimeDescription(components,3);
        Compute.ManaCostDescription(components,20);
        components.add(Component.literal(" Idea From:Mr_RED").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfWorldSoul(components);
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
