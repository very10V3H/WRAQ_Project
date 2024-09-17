package com.very.wraq.series.instance.series.moon.Equip;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.util.struct.Shield;
import com.very.wraq.process.func.damage.Damage;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
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

public class MoonBelt extends Item implements ICurioItem {

    public MoonBelt(Properties p_41383_) {
        super(p_41383_);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
        Utils.attackDamage.put(this, 150d);
        Utils.manaDamage.put(this, 300d);
        Utils.defencePenetration0.put(this, 200d);
        Utils.manaPenetration0.put(this, 200d);
        Utils.coolDownDecrease.put(this, 0.2);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        Style style = CustomStyle.styleOfMoon;
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("苍白之瀑").withStyle(style));
        components.add(Component.literal(" 当你对一名敌人造成伤害或受到伤害，将会激活").withStyle(ChatFormatting.WHITE).
                append(Component.literal(" 尘月玉缠 ").withStyle(style)).
                append(Component.literal("在接下来的10s，将你造成伤害的10%和受到伤害的1000%转化为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("苍月能量").withStyle(CustomStyle.styleOfMoon1)).
                append(Component.literal("，并存储于尘月玉缠之中。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 10s后，对周围所有敌人造成等同于存储的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("苍月能量").withStyle(CustomStyle.styleOfMoon1)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)).
                append(Component.literal("并为你提供等同于").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("苍月能量1%").withStyle(CustomStyle.styleOfMoon1)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" -护盾值不会超过最大生命值的200%").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        ComponentUtils.coolDownTimeDescription(components, 10);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfMoon(components);
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

    public static WeakHashMap<Player, Integer> coolDown = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> damageTick = new WeakHashMap<>();
    public static WeakHashMap<Player, Double> storedDamage = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> statusType = new WeakHashMap<>(); // 0 - 等待状态 1 - 蓄能状态

    public static void PassiveCauseDamage(Player player, double num) {
        if (Compute.isOnCurios(player, ModItems.MoonBelt.get())) {
            int TickCount = player.getServer().getTickCount();
            if (!coolDown.containsKey(player) || coolDown.get(player) < TickCount) {
                if (!statusType.containsKey(player)) statusType.put(player, 0);
                switch (statusType.get(player)) {
                    case 0 -> {
                        statusType.put(player, 1);
                        storedDamage.put(player, num * 0.1);
                        damageTick.put(player, TickCount + 200);
                        Compute.sendEffectLastTime(player, ModItems.MoonBelt.get().getDefaultInstance(), 200);
                    }
                    case 1 -> {
                        storedDamage.put(player, storedDamage.get(player) + num * 0.1);
                    }
                }
            }
        }
    }

    public static void PassiveGetDamage(Player player, double num) {
        if (Compute.isOnCurios(player, ModItems.MoonBelt.get())) {
            int TickCount = player.getServer().getTickCount();
            if (!coolDown.containsKey(player) || coolDown.get(player) < TickCount) {
                if (!statusType.containsKey(player)) statusType.put(player, 0);
                switch (statusType.get(player)) {
                    case 0 -> {
                        statusType.put(player, 1);
                        storedDamage.put(player, num * 10);
                        damageTick.put(player, TickCount + 200);
                        Compute.sendEffectLastTime(player, ModItems.MoonBelt.get().getDefaultInstance(), 200);
                    }
                    case 1 -> {
                        storedDamage.put(player, storedDamage.get(player) + num * 10);
                    }
                }
            }
        }
    }

    public static void Tick(Player player) {
        if (Compute.isOnCurios(player, ModItems.MoonBelt.get())) RangeDamage(player);
    }

    public static void RangeDamage(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (statusType.containsKey(player) && statusType.get(player) == 1
                && damageTick.containsKey(player) && damageTick.get(player) < TickCount
                && storedDamage.containsKey(player)) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 15, 15, 15));
            mobList.forEach(mob -> {
                if (mob.distanceTo(player) < 6) {
                    Damage.causeIgNoreDefenceDamageToMonster(player, mob, storedDamage.get(player));
                }
            });
            Shield.providePlayerShield(player, 200, Math.min(player.getMaxHealth() * 2, storedDamage.get(player) * 0.01));
            statusType.put(player, 0);
            storedDamage.put(player, 0d);
            coolDown.put(player, TickCount + 200);
            Compute.coolDownTimeSend(player, ModItems.MoonBelt.get().getDefaultInstance(), 200);
            ParticleProvider.DisperseParticle(player.position(), (ServerLevel) player.level(), 1, 1, 120, ParticleTypes.FIREWORK, 1);
            ParticleProvider.DisperseParticle(player.position(), (ServerLevel) player.level(), 1.5, 1, 120, ParticleTypes.FIREWORK, 1);

        }
    }
}
