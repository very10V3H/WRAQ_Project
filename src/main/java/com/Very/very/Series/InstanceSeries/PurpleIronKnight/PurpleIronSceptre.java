package com.Very.very.Series.InstanceSeries.PurpleIronKnight;

import com.Very.very.Items.MobItem.MobArmor;
import com.Very.very.ValueAndTools.BasicAttributeDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemTier;
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
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class PurpleIronSceptre extends PickaxeItem {

    private static final double[] ManaDamage = {
            800,1200,1600,2000
    };

    private static final double[] MaxMana = {
            50,100,150,200
    };

    private static final double[] ManaPenetration0 = {
            400,500,600,800
    };

    public PurpleIronSceptre(Properties properties, int tier) {
        super(ItemTier.VMaterial, 2, 0, properties);
        Utils.ManaDamage.put(this,ManaDamage[tier]);
        Utils.MaxMana.put(this,MaxMana[tier]);
        Utils.ManaPenetration0.put(this,ManaPenetration0[tier]);
        Utils.PassiveEquipTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.LevelRequire.put(this,160);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = Utils.styleOfPurpleIron;
        Compute.ForgingHoverName(stack,Component.literal("").withStyle(ChatFormatting.RESET).withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("器灵                   ").withStyle(Utils.styleOfSakura).append(Component.literal("法杖").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionPassive(components,Component.literal("晶体析构").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 基于你与目标的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDefence("")).
                append(Component.literal("差的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("绝对值").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，每1000差值提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%伤害提升").withStyle(ChatFormatting.RESET).withStyle(style)));
        Compute.LevelRequire(components,Utils.LevelRequire.get(this));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfPurpleIronKnight(components);
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
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static double DamageEnhance(Player player, Mob mob) {
        if (player.experienceLevel < 160) return 0;
        boolean isOn = false;
        Inventory inventory = player.getInventory();
        for (int i = 3 ; i < 9 ; i ++) {
            if (inventory.getItem(i).getItem() instanceof PurpleIronSceptre) isOn = true;
        }
        if (!isOn) return 0;
        ItemStack helmet = mob.getItemBySlot(EquipmentSlot.HEAD);
        double ManaDefence = 0;
        if (helmet.getItem() instanceof MobArmor mobArmor) ManaDefence = mobArmor.ManaDefence;
        return Math.abs(Compute.PlayerAttributes.PlayerManaDefence(player) - ManaDefence) * 0.25 / 1000;
    }
}
