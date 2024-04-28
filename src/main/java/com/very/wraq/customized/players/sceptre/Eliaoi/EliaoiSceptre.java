package com.very.wraq.customized.players.sceptre.Eliaoi;

import com.very.wraq.customized.Customize;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.series.instance.Ice.IceSceptreAttributes;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
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
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EliaoiSceptre extends SwordItem {
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
        Compute.ForgingHoverName(stack,Component.literal("爆裂魔杖" + IceSceptreAttributes.Number[Num]).withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("魔法书").withStyle(ChatFormatting.RED)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("深渊炙炎").withStyle(style));
        components.add(Component.literal(" 展开屏障，处于屏障内的玩家").withStyle(ChatFormatting.WHITE).
                append(Component.literal("持续燃烧").withStyle(style)).
                append(Compute.AttributeDescription.MaxHealth("")).
                append(Component.literal("，最低至").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("4%")));
        components.add(Component.literal(" 处于屏障内的玩家受到的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("治疗效果").withStyle(CustomStyle.styleOfHealth)).
                append(Component.literal("将会全部转化为持续5s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionPassive(components,Component.literal("灾厄").withStyle(style));
        components.add(Component.literal(" 基于").withStyle(ChatFormatting.WHITE).
                append(Component.literal("已损失").withStyle(style)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("为你等至多提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("50%总")).
                append(Component.literal("与50%伤害提升").withStyle(ChatFormatting.WHITE)));
        Compute.ManaCoreAddition(stack,components);
        components.add(Component.literal("当被原谅之人尚未能饶恕自身时，得到宽恕所带来的安慰也就仅限于此了.").withStyle(ChatFormatting.RED));
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

    public static Map<Player,Integer> effectPlayer = new HashMap<>();

    public static boolean IsInEffect(Player player) {
        return effectPlayer.containsKey(player) && effectPlayer.get(player) > player.getServer().getTickCount();
    }

    public static int ActiveCoolDownTick = 0;
    public static double ActiveValue = 0;
    public static int ActiveLastTick = 0;

    public static boolean IsPlayer(Player player) {
        return player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.EliaoiBook.get());
    }

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        if (player.tickCount % 5 == 0) {
            Eliaoi.Player = player;
            Eliaoi.EliaoiIsInAttack = 8;
        }
        if (player.tickCount % 20 == 0) {
            List<Player> players = player.level().getEntitiesOfClass(net.minecraft.world.entity.player.Player.class, AABB.ofSize(player.position(),15,15,15));
            players.removeIf(player1 -> player1.distanceTo(player) > 6);
            players.forEach(player1 -> {
                Compute.PlayerHealthDecrease(player1,player1.getMaxHealth() * 0.1,  0.04);
                EliaoiSceptre.effectPlayer.put(player1,player1.getServer().getTickCount() + 200);
            });
        }
    }

    public static void Active(Player player) {
/*
        int TickCount = player.getServer().getTickCount();
        if (ActiveCoolDownTick < TickCount) {
            ActiveCoolDownTick = TickCount + 200;
            Compute.CoolDownTimeSend(player, ModItems.EliaoiBook.get().getDefaultInstance(),200);
            double health = player.getHealth();
            Compute.PlayerHealthDecrease(player,health * 0.99,Component.literal(" 被灾厄吞噬了").withStyle(ChatFormatting.RED));
            Compute.PlayerShieldProvider(player,200,health * 0.99);
            ActiveValue = health;
            ActiveLastTick = TickCount + 200;
            Compute.EffectLastTimeSend(player,ModItems.EliaoiBook.get().getDefaultInstance(),200);
        }
*/
    }

    public static double ManaDamageUp(Player player) {
        if (!IsPlayer(player)) return 1;
        return (1 + 0.5 * (1 - player.getHealth() / player.getMaxHealth()));
    }

    public static double DamageEnhance(Player player) {
        if (!IsPlayer(player)) return 0;
        return 0.5 * (1 - player.getHealth() / player.getMaxHealth());
    }

}
