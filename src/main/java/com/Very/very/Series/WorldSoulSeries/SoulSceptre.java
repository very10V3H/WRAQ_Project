package com.Very.very.Series.WorldSoulSeries;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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

public class SoulSceptre extends SwordItem {

    public SoulSceptre(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
        Utils.ManaDamage.put(this,SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaAttackDamage);
        Utils.ManaRecover.put(this,SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaRecover);

        Utils.ManaPenetration0.put(this,SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaPenetration0);
        Utils.MovementSpeed.put(this,SoulEquipAttribute.BaseAttribute.SoulSceptre.MovementSpeed);
        Utils.ManaCost.put(this,SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaCost);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SceptreTag.put(this,1d);
    }

    public static final int ManaCost = 20;
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = Utils.styleOfWorld;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Utils.WeaponTypeComponents.Sceptre));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfWorld,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfWorld,ChatFormatting.WHITE);
        Compute.DescriptionActive(components,Component.literal("本源打击-陨石").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 指针处将坠下一枚陨石，陨石会对一定范围内的怪物随机造成持续").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("4s").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("的负面效果").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal("  1.中毒：每秒造成").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("30%")));
        components.add(Component.literal("  2.缓慢：减缓目标").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MovementSpeed("")));
        components.add(Component.literal("  3.燃烧：每秒造成").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("35%")));
        components.add(Component.literal("  4.致残：大幅降低目标").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MovementSpeed("")));
        components.add(Component.literal(" 对一定范围内的玩家随机造成").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("增益效果").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
        components.add(Component.literal("  1.治疗：回复已损失生命值10%的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("")));
        components.add(Component.literal("  2.抗性：持续4秒的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("5%")).
                append(Compute.AttributeDescription.Defence("")).
                append(Compute.AttributeDescription.ManaDamage("5%")).
                append(Compute.AttributeDescription.ManaDefence("")));
        components.add(Component.literal("  3.攻击增幅：持续4秒的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("10%")).
                append(Compute.AttributeDescription.ManaDamage("10%")));
        components.add(Component.literal("  4.穿透增幅：持续4秒的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.DefencePenetration("20%")).
                append(Compute.AttributeDescription.ManaPenetration("20%")));
        Compute.CoolDownTimeDescription(components,8);
        Compute.ManaCostDescription(components,40);
        components.add(Component.literal(" Idea From:Mr_RED").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
        Compute.ManaCoreAddition(stack,components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfWorld,ChatFormatting.WHITE);
        Compute.SuffixOfWorldSoul(components);
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

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND) && player.isShiftKeyDown()) {
            try {
                SoulEquipAttribute.Forging(player.getItemInHand(InteractionHand.MAIN_HAND),player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Compute.ManaAttack(player,4);
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND,new ItemStack(Utils.ManaCoreMap.get(data.getString(StringUtils.ManaCore.ManaCore))));
                data.remove(StringUtils.ManaCore.ManaCore);
            }
        }
/*        HitResult result = player.pick(2,0,true);
        Compute.ParticleWITCH(result.getLocation().x, result.getLocation().y,result.getLocation().z,level,0.5);*/
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static double getManaCost (CompoundTag data) {
        if (data.contains(StringUtils.SoulEquipForge)) return SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaCost - SoulEquipAttribute.ForgingAddition.ManaCost;
        else return SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaCost;
    }
}


