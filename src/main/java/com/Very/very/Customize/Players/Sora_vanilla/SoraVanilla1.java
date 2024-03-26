package com.Very.very.Customize.Players.Sora_vanilla;

import com.Very.very.Projectile.Mana.BlazeSword;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.ModEntityType;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoraVanilla1 extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public SoraVanilla1(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this, 1728d);
        Utils.Defence.put(this, 300d);
        Utils.ManaPenetration0.put(this, 150d);
        Utils.ManaRecover.put(this, 30d);
        Utils.MaxMana.put(this, 100d);
        Utils.CoolDownDecrease.put(this, 0.3);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = Utils.styleOfPower;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);

        Compute.DescriptionPassive(components, Component.literal("装逼我让你飞起来").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 法术与技能将不会消耗").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("")).
                append(Component.literal("，每").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("100")).
                append(Component.literal("将为你提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("1500")));

        Compute.DescriptionPassive(components,Component.literal("终有一死").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 每过0.5s，为你生成一把").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("火刀").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，火刀将会自动锁定半径20格内的目标，造成").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.5倍").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)));
        components.add(Component.literal(" 释放法术").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana).
                append(Component.literal("也会为你生成一把").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("火刀").withStyle(ChatFormatting.RESET).withStyle(style)));

        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚黑桃A，授予对维瑞阿契做出了杰出贡献的 - Sora_vanilla").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = (Player) slotContext.entity();
        IsOn = true;
        Compute.AddCuriosToList(player, stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = null;
        IsOn = false;
        Compute.RemoveCuriosInList(player, stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsPlayer(Player player) {
        return Player != null && Player.equals(player) && IsOn;
    }

    public static BlazeSword[] blazeSwords = new BlazeSword[5];

    public static double ManaDamageUp(Player player) {
        if (!IsPlayer(player)) return 0;
        return (Compute.PlayerAttributes.PlayerMaxMana(player) + 100) * 1500 / 100;
    }

    public static void AddNewBlazeSword(Player player, int num) {
        if (!IsPlayer(player)) return;
        Level level = player.level();
        List<Integer> remainIndex = new ArrayList<>();

        for (int i = 0 ; i < 5 ; i ++) {
            if (blazeSwords[i] != null && blazeSwords[i].distanceTo(player) > 30) {
                blazeSwords[i].remove(Entity.RemovalReason.KILLED);
            }
        }

        for (int i = 0 ; i < 5 ; i ++) {
            if (blazeSwords[i] == null || !blazeSwords[i].isAlive()) remainIndex.add(i);
        }

        if (remainIndex.size() > 0) {
            for (int i = 0 ; i < num ; i ++) {
                Random random = new Random();
                int index = remainIndex.get(random.nextInt(remainIndex.size()));
                remainIndex.remove((Object) index);
                BlazeSword blazeSword = blazeSwords[index];
                if (blazeSword == null || !blazeSword.isAlive()) {
                    blazeSword = new BlazeSword(ModEntityType.BLAZE_SWORD.get(),player,level);
                    blazeSword.moveTo(Compute.GetPlayerBackPos(player,index));
                    blazeSword.setNoGravity(true);
                    blazeSword.setSilent(true);
                    blazeSword.setNoPhysics(true);
                    level.addFreshEntity(blazeSword);
                    blazeSwords[index] = blazeSword;
                }
            }
        }

    }

    public static void FindMobToTrace(Player player) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,AABB.ofSize(player.position(),50,50,50));
        mobList.removeIf(mob -> mob.distanceTo(player) > 20);
        mobList.removeIf(mob -> mob instanceof Villager);
        if (mobList.size() > 0) {
            Mob nearestMob = mobList.get(0);
            double distance = 50;
            for (Mob mob : mobList) {
                if (mob.distanceTo(player) < distance) {
                    distance = mob.distanceTo(player);
                    nearestMob = mob;
                }
            }
            for (int i = 0 ; i < 5 ; i ++) {
                if (blazeSwords[i] != null && blazeSwords[i].tickCount > 10) {
                    blazeSwords[i].setNoPhysics(false);
                    Compute.EntitySmoothlyMoveToPosWithLimitSpeed(blazeSwords[i],nearestMob.position().add(0,0.5,0),1);
                }
            }
        }
    }

    public static void FollowPlayer(Player player) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,AABB.ofSize(player.position(),50,50,50));
        mobList.removeIf(mob -> mob.distanceTo(player) > 20);
        mobList.removeIf(mob -> mob instanceof Villager);
        for (int i = 0 ; i < 5 ; i ++) {
            if (blazeSwords[i] != null && (mobList.size() == 0 || blazeSwords[i].tickCount <= 10)) Compute.EntitySmoothlyMoveToPos(blazeSwords[i],Compute.GetPlayerBackPos(player,i));
        }
    }

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        if (player.tickCount % 10 == 0) AddNewBlazeSword(player,1);
        FindMobToTrace(player);
        FollowPlayer(player);
    }

    public static void Remove(BlazeSword blazeSword) {
        if (Player == null || blazeSword.distanceTo(Player) > 30) blazeSword.remove(Entity.RemovalReason.KILLED);
    }

}
