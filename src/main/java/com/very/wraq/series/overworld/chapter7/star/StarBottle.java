package com.very.wraq.series.overworld.chapter7.star;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.func.damage.Damage;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.WeakHashMap;

public class StarBottle extends Item implements ICurioItem {

    public StarBottle(Properties p_41383_) {
        super(p_41383_);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
        Utils.defencePenetration.put(this, 0.15);
        Utils.manaPenetration.put(this, 0.15);
        Utils.movementSpeedWithoutBattle.put(this, 0.4);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        Style style = CustomStyle.styleOfMoon1;
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("聚星集屑").withStyle(style));
        components.add(Component.literal(" 处于战斗状态时，每秒会收集一枚").withStyle(ChatFormatting.WHITE).
                append(Component.literal("星屑").withStyle(style)));
        components.add(Component.literal(" 每拥有10枚").withStyle(ChatFormatting.WHITE).
                append(Component.literal("星屑").withStyle(style)).
                append(Component.literal("，获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("3%伤害提升").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" 当你拥有70枚").withStyle(ChatFormatting.WHITE).
                append(Component.literal("星屑").withStyle(style)).
                append(Component.literal("时，会开始释放收集到的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("星屑").withStyle(style)));
        components.add(Component.literal(" 每秒会将").withStyle(ChatFormatting.WHITE).
                append(Component.literal("星屑").withStyle(style)).
                append(Component.literal("掷向半径16格内的目标，每枚").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("星屑").withStyle(style)).
                append(Component.literal("造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("(星屑数) / 10倍").withStyle(CustomStyle.styleOfSea)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" 只有当星星瓶中的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("星屑").withStyle(style)).
                append(Component.literal("被完全释放后，你才可以再次收集").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("星屑").withStyle(style)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.SuffixOfMainStoryVII(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Compute.AddCuriosToList((Player) slotContext.entity(), stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Compute.RemoveCuriosInList((Player) slotContext.entity(), stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static WeakHashMap<Player, Boolean> playerStatusMap = new WeakHashMap<>(); // false - 收集状态 true 掷出状态
    public static WeakHashMap<Player, Integer> playerCountsMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> playerLastBattleTick = new WeakHashMap<>();

    public static boolean IsOn(Player player) {
        if (!Utils.playerCuriosListMap.containsKey(player)) return false;
        List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
        for (ItemStack itemStack : curiosList) {
            if (itemStack.getItem() instanceof StarBottle) return true;
        }
        return false;
    }

    public static void Tick(Player player) {
        if (!IsOn(player)) return;
        if (player.tickCount % 20 == 0 && PlayerIsInCollectMode(player)) CountAdd(player);
        if (playerCountsMap.containsKey(player) && playerCountsMap.get(player) == 70) {
            playerStatusMap.put(player, true);
        }
        if (!playerStatusMap.containsKey(player) || (playerStatusMap.get(player) && playerCountsMap.get(player) <= 0)) {
            playerStatusMap.put(player, false);
        }
        if (playerStatusMap.get(player) && playerCountsMap.get(player) > 0 && player.tickCount % 20 == 0) {
            RangeAttack(player);
        }
    }

    public static void playerBattleTickMapRefresh(Player player) {
        playerLastBattleTick.put(player, player.getServer().getTickCount());
    }

    public static boolean PlayerIsInCollectMode(Player player) {
        return !playerStatusMap.containsKey(player) || !playerStatusMap.get(player);
    }

    public static void CountAdd(Player player) {
        if (!IsOn(player)) return;
        if (PlayerIsInCollectMode(player) && playerLastBattleTick.containsKey(player) && playerLastBattleTick.get(player) + 100 > player.getServer().getTickCount()) {
            playerCountsMap.put(player, Math.min(70, playerCountsMap.getOrDefault(player, 0) + 1));
            Compute.sendEffectLastTime(player, ModItems.StarBottle.get().getDefaultInstance(), 8888, playerCountsMap.get(player), true);
        }
    }

    public static void RangeAttack(Player player) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 40, 40, 40));
        mobList.removeIf(mob -> mob.distanceTo(player) > 16);
        mobList.forEach(mob -> {
            if (playerCountsMap.get(player) > 0) {
                playerCountsMap.put(player, playerCountsMap.get(player) - 1);
                Damage.causeIgNoreDefenceDamageToMonster(player, mob, Compute.XpStrengthDamage(player, (double) playerCountsMap.get(player) / 10));
                ParticleProvider.LineParticle(player.level(), (int) mob.distanceTo(player) * 2, player.position(), mob.position(), ParticleTypes.FIREWORK);
            }
        });
        Compute.sendEffectLastTime(player, ModItems.StarBottle.get().getDefaultInstance(), 8888, playerCountsMap.get(player), true);
    }

    public static double DamageEnhance(Player player) {
        if (!IsOn(player)) return 0;
        if (playerCountsMap.containsKey(player)) {
            return playerCountsMap.get(player) * 0.03 / 10.0;
        }
        return 0;
    }
}
