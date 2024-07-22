
package com.very.wraq.series.instance.PurpleIronKnight;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.BasicAttributeDescription;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.MobAttributes;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class PurpleIronSword extends SwordItem implements PurpleIronTier {

    private static final double[] ExAttackDamage = {
            100, 150, 200, 250
    };

    private static final double[] Defence = {
            150, 300, 450, 600
    };

    private static final double[] MaxHealth = {
            400, 600, 800, 1000
    };

    private final int tier;

    public PurpleIronSword(Properties properties, int tier) {
        super(ItemTier.VMaterial, 2, 0, properties);
        this.tier = tier;
        Utils.attackDamage.put(this, ExAttackDamage[tier]);
        Utils.defence.put(this, Defence[tier]);
        Utils.maxHealth.put(this, MaxHealth[tier]);
        Utils.passiveEquipTag.put(this, 1d);
        Utils.weaponList.add(this);
        Utils.levelRequire.put(this, 120);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        return super.use(level, player, useHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfPurpleIron;
        components.add(Component.literal("器灵                   ").withStyle(CustomStyle.styleOfSakura).append(Component.literal("长剑").withStyle(style)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionPassive(components, Component.literal("晶体析构").withStyle(style));
        components.add(Component.literal(" 基于你与目标的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDefence("")).
                append(Component.literal("差的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("绝对值").withStyle(style)).
                append(Component.literal("至多提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal(new String[]{"20%", "35%", "50%", "65%"}[tier] + "伤害提升").withStyle(style)));
        Compute.LevelRequire(components, Utils.levelRequire.get(this));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfPurpleIronKnight(components);
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

    public static double damageEnhance(Player player, Mob mob) {
        if (player.experienceLevel < 120) return 0;
        boolean isOn = false;
        double rate = 0;
        Inventory inventory = player.getInventory();
        for (int i = 3; i < 9; i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem() instanceof PurpleIronTier purpleIronTier) {
                isOn = true;
                rate = new double[]{0.2, 0.35, 0.5, 0.65}[purpleIronTier.getPassiveTier()];
            }
        }
        if (!isOn) return 0;
        double manaDefence = 0;
        manaDefence = MobAttributes.manaDefence(mob);
        double value = Math.abs(PlayerAttributes.manaDefence(player) - manaDefence);
        return (rate - (rate * 750 / (750 + value)));
    }

    @Override
    public int getPassiveTier() {
        return tier;
    }
}
