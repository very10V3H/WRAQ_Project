package com.Very.very.Customize.Players.Wcndymlgb;

import com.Very.very.CoreAttackModule.MyArrow;
import com.Very.very.Customize.Uniform.Attributes;
import com.Very.very.Entity.Entities.Civil.Civil;
import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.ModEntityType;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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

public class WcndymlgbCurios extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public WcndymlgbCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.AttackDamage.put(this, Attributes.AttackDamage);
        Utils.DefencePenetration0.put(this,Attributes.DefencePenetration0);
        Utils.CritDamage.put(this,Attributes.CritDamage);
        Utils.Defence.put(this,Attributes.Defence);
        Utils.CritRate.put(this,Attributes.CritRate);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = Utils.styleOfSky;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("维度分身").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 当你受到伤害时，会召唤一个").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("分身").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，其具有以下特性:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.分身将拥有与你相同的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("")));
        components.add(Component.literal(" 2.分身受到伤害时，将使用你当前").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("进行伤害计算").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 3.分身将").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("嘲讽").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSakura)).
                append(Component.literal("半径20格内的怪物对其进行攻击").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 4.分身每秒将对半径10格内的怪物造成一次").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("1倍").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfFlexible)).
                append(Component.literal("基础伤害的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("箭矢攻击").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfFlexible)));
        Compute.DescriptionPassive(components,Component.literal("次元之力").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 箭矢").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfFlexible).
                append(Component.literal("命中目标时，为你与分身回复").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("1%")).
                append(Component.literal("，基于你的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("1%")).
                append(Component.literal("为你提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("等额").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚次元箭袋，授予对维瑞阿契做出了杰出贡献的 - wcndymlgb").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = (Player) slotContext.entity();
        IsOn = true;
        Compute.AddCuriosToList(player,stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = null;
        IsOn = false;
        Compute.RemoveCuriosInList(player,stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsPlayer(Player player) {
        return Player != null && Player.equals(player) && IsOn;
    }

    public static Civil civil;

    public static void Summon(Player player) {
        if (!IsPlayer(player)) return;
        if (civil == null || civil.isDeadOrDying()){
            Level level = player.level();
            civil = new Civil(ModEntityType.CIVIL.get(), level, player);
            civil.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorCastleKnightHelmet.get().getDefaultInstance());
            civil.setCustomName(Component.literal("铁头").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
            civil.setCustomNameVisible(true);
            civil.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).setBaseValue(player.getMaxHealth());
            civil.setHealth(civil.getMaxHealth());
            civil.moveTo(player.position());
            level.addFreshEntity(civil);
        }
    }

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        if (civil != null) {
            if (player.distanceTo(civil) > 20) {
                civil.remove(Entity.RemovalReason.KILLED);
                civil = null;
                return;
            }
            List<Mob> mobList = civil.level().getEntitiesOfClass(Mob.class, AABB.ofSize(civil.position(),60,60,60));
            mobList.removeIf(mob -> mob.distanceTo(civil) > 20 || !(mob instanceof PathfinderMob));
            mobList.forEach(mob -> {
                mob.goalSelector.addGoal(0,new NearestAttackableTargetGoal<Civil>(mob, Civil.class,true));
                mob.goalSelector.addGoal(1,new MoveTowardsTargetGoal((PathfinderMob) mob,0.1,0.1f));
            });
            if (civil.isAlive() && player.tickCount % 20 == 0) {
                mobList.removeIf(mob -> mob.distanceTo(civil) > 10);
                mobList.forEach(mob -> {
                    if (mob != civil) {
                        NormalDamage(player,mob);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                    }
                });
                ParticleProvider.VerticleCircleParticle(civil.position(),(ServerLevel) civil.level(),1,10,160,ParticleTypes.WITCH);
                ParticleProvider.VerticleCircleParticle(civil.position(),(ServerLevel) civil.level(),1.5,10,160,ParticleTypes.WITCH);
            }
        }
    }

    public static void Remove() {
        if (civil != null) {
            civil.remove(Entity.RemovalReason.KILLED);
            civil = null;
        }
    }

    public static void Remove(Civil civil) {
        if (WcndymlgbCurios.civil != null && civil != WcndymlgbCurios.civil) civil.remove(Entity.RemovalReason.KILLED);
    }

    public static void NormalDamage(Player player, Mob mob) {
        MyArrow myArrow = new MyArrow(EntityType.ARROW, player.level(), player, false);
        MyArrow.CauseDamage(myArrow, mob, Compute.PlayerAttributes.PlayerAttackDamage(player));
    }
    public static double ExCritDamage(Player player) {
        if (!IsPlayer(player)) return 0;
        return player.getMaxHealth() / 10000;
    }

    public static void AttackHeal(Player player) {
        if (!IsPlayer(player)) return;
        Compute.PlayerHeal(player,player.getMaxHealth() * 0.01);
        if (civil != null && civil.isAlive()) civil.heal((float) (player.getMaxHealth() * 0.01));
    }


















}
