package com.Very.very.Customize.Players.Eliaoi;

import com.Very.very.Customize.Customize;
import com.Very.very.Series.InstanceSeries.Ice.IceSceptreAttributes;
import com.Very.very.ValueAndTools.BasicAttributeDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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

public class EliaoiSceptre extends PickaxeItem {
    private final int Num;
    public EliaoiSceptre(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_, int Num) {
        super(p_42961_, p_42962_, p_42963_, p_42964_.rarity(Utils.RedItalic));
        this.Num = Num;
        Utils.ManaDamage.put(this, Customize.ManaDamage);
        Utils.ManaRecover.put(this, 30d);
        Utils.ManaPenetration0.put(this, Customize.ManaPenetration0);
        Utils.MovementSpeed.put(this, 0.4);
        Utils.ManaCost.put(this, 15d);
        Utils.MainHandTag.put(this,1d);
        Utils.SceptreTag.put(this,1.0d);
        Utils.CustomizedList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        ChatFormatting style = ChatFormatting.RED;
        Compute.ForgingHoverName(stack,Component.literal("爆裂魔杖" + IceSceptreAttributes.Number[Num]).withStyle(ChatFormatting.RESET).withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("魔法书").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("硫磺火屏障").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 展开法阵并射出").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("硫磺火激光").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("并在自身周围生成").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("硫磺火屏障").withStyle(ChatFormatting.RESET).withStyle(style)));
        components.add(Component.literal(" -硫磺火屏障").withStyle(ChatFormatting.RESET).withStyle(style).
                append(Component.literal("：靠近的敌人将会受到硫磺火灼烧并被弹开").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionActive(components,Component.literal("灾厄").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 燃烧").withStyle(ChatFormatting.RESET).withStyle(style).
                append(Component.literal("自身").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Health("99%")).
                append(Component.literal("并获得等量").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾值").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" 基于").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("燃烧").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("为你等至多提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("50%总")).
                append(Component.literal("与50%伤害提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.ManaCoreAddition(stack,components);
        components.add(Component.literal("当被原谅之人尚未能饶恕自身时，得到宽恕所带来的安慰也就仅限于此了.").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作怨戾，授予对维瑞阿契做出了杰出贡献的 - Eliaoi").withStyle(ChatFormatting.AQUA));
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
        if (!level.isClientSide) {
            Eliaoi.Player = player;
            Eliaoi.EliaoiIsInAttack = 8;
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND,new ItemStack(Utils.ManaCoreMap.get(data.getString(StringUtils.ManaCore.ManaCore))));
                data.remove(StringUtils.ManaCore.ManaCore);
            }
        }
        return super.use(level, player, interactionHand);
    }

    public static int ActiveCoolDownTick = 0;
    public static double ActiveValue = 0;
    public static int ActiveLastTick = 0;

    public static boolean IsPlayer(Player player) {
        return player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.EliaoiBook.get());
    }

    public static void Active(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (ActiveCoolDownTick < TickCount) {
            ActiveCoolDownTick = TickCount + 200;
            Compute.CoolDownTimeSend(player, ModItems.EliaoiBook.get().getDefaultInstance(),200);
            double health = player.getHealth();
            Compute.PlayerHealthDecrease(player,health * 0.99,Component.literal(" 被灾厄吞噬了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED));
            Compute.PlayerShieldProvider(player,200,health * 0.99);
            ActiveValue = health;
            ActiveLastTick = TickCount + 200;
            Compute.EffectLastTimeSend(player,ModItems.EliaoiBook.get().getDefaultInstance(),200);
        }
    }

    public static double ManaDamageUp(Player player) {
        if (!IsPlayer(player)) return 1;
        if (ActiveLastTick > player.getServer().getTickCount()) return (1 + ActiveValue * 0.5 / player.getMaxHealth());
        return 1;
    }

    public static double DamageEnhance(Player player) {
        if (!IsPlayer(player)) return 0;
        if (ActiveLastTick > player.getServer().getTickCount()) return (ActiveValue * 0.5 / player.getMaxHealth());
        return 0;
    }

}
