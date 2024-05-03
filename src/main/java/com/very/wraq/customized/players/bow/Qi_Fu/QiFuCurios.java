package com.very.wraq.customized.players.bow.Qi_Fu;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
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

public class QiFuCurios extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public QiFuCurios(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfMoon1;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("月华之力").withStyle(style));
        components.add(Component.literal(" 月光照耀着所有生灵").withStyle(style).
                append(Component.literal("，当你周围有敌人时，每0.5s发射一支箭矢。").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components,Component.literal("潮汐往来月为程").withStyle(style));
        components.add(Component.literal(" 明月勾起潮汐").withStyle(style).
                append(Component.literal("，发射箭矢时获得4层").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("潮汐之力").withStyle(style)));
        components.add(Component.literal(" 每层").withStyle(ChatFormatting.WHITE).
                append(Component.literal("潮汐之力").withStyle(style)).
                append(Component.literal("为你提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritRate("0.25%")).
                append(Component.literal("、").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("1.25%")).
                append(Component.literal("、").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("0.35%")));
        components.add(Component.literal(" 根据当前").withStyle(ChatFormatting.WHITE).
                append(Component.literal("潮汐之力").withStyle(style)).
                append(Component.literal("层数，获得:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.海上明月共潮升(20):").withStyle(style).
                append(Component.literal(" 获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritRate("25%")).
                append(Compute.AttributeDescription.CritDamage("75%")));
        components.add(Component.literal(" 2.潮落夜江斜月里(50):").withStyle(style).
                append(Component.literal(" 获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("125%")).
                append(Compute.AttributeDescription.DefencePenetration("750")));
        components.add(Component.literal(" 3.潮生潮落夜还晓(100):").withStyle(style).
                append(Component.literal(" 额外发射3支箭矢，每支箭矢拥有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("2倍").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("基础伤害。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 江畔何人初见月，江月何年初照人？人生代代无穷已，江月年年望相似。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚月起唤潮汐，授予对维瑞阿契做出了杰出贡献的 - 147895").withStyle(ChatFormatting.AQUA));
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

    public static int Count = 0;

    public static int ExManaBallTickCount = 0;

    public static void CountAdd(Player player, int count) {
        if (!IsPlayer(player)) return;
        Count = Math.min(100,Count + count);
        Compute.EffectLastTimeSend(player, ModItems.QiFuCurios.get().getDefaultInstance(),8888,Count,true);
    }

    public static void CountSub(Player player, int count) {
        if (!IsPlayer(player)) return;
        Count = Math.max(0,Count - count);
        Compute.EffectLastTimeSend(player, ModItems.QiFuCurios.get().getDefaultInstance(),8888,Count,true);
    }

    public static double CountExCritRate(Player player) {
        if (!IsPlayer(player)) return 0;
        return Count * 0.0025;
    }

    public static double CountExCritDamage(Player player) {
        if (!IsPlayer(player)) return 0;
        return Count * 0.0125;
    }

    public static double CountAttackDamageUp(Player player) {
        if (!IsPlayer(player)) return 0;
        return Count * 0.0035;
    }

    public static double Buff1CritRate(Player player) {
        if (IsPlayer(player) && Count >= 20) return 0.25;
        return 0;
    }

    public static double Buff1CritDamage(Player player) {
        if (IsPlayer(player) && Count >= 20) return 0.75;
        return 0;
    }

    public static double Buff2ExCritDamage(Player player) {
        if (IsPlayer(player) && Count >= 50) return 1.25;
        return 0;
    }

    public static double Buff2DefencePenetration0(Player player) {
        if (IsPlayer(player) && Count >= 50) return 750;
        return 0;
    }

    public static void Passive(Player player) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,AABB.ofSize(player.position(),15,15,15));
        mobList.removeIf(mob -> mob.distanceTo(player) > 6);
        if (mobList.size() == 0) return;
        if (player.tickCount % 10 == 0) Shoot(player,true,false);
    }

    public static void Shoot(Player player, boolean isPlayer, boolean isBuff3) {
        double damage = PlayerAttributes.PlayerAttackDamage(player);
        MyArrow arrow = new MyArrow(EntityType.ARROW, player, player.level(),
                player, isBuff3 ? damage * 2 : damage, isPlayer,true,ParticleTypes.FIREWORK);
        arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 4.5F, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, player);
        player.level().addFreshEntity(arrow);
        Compute.SoundToAll(player, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.FIREWORK);
        CountAdd(player,4);
    }

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        Passive(player);
        if (ExManaBallTickCount > 0) {
            ExManaBallTickCount --;
            if (ExManaBallTickCount % 2 == 1) Shoot(player, false,true);
        }
    }


}
