package com.very.wraq.customized.players.bow.MyMission;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MyMissionCurios extends Item implements ICurioItem {

    public MyMissionCurios(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfHealth;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("聚能转换").withStyle(style));
        components.add(Component.literal(" 取当前箭矢数量最高目标数量").withStyle(ChatFormatting.WHITE).
                append(Component.literal("层数").withStyle(style)).
                append(Component.literal("，每10层为你提供:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("200%")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExAttackDamage("200")));
        Compute.DescriptionPassive(components,Component.literal("聚能射击").withStyle(style));
        components.add(Component.literal(" 每次攻击命中目标时，有").withStyle(ChatFormatting.WHITE).
                append(Component.literal("20%").withStyle(style)).
                append(Component.literal("的概率在0.8s内射出8枚箭矢").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚誓约领域，授予对维瑞阿契做出了杰出贡献的 - My_mission").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Onplayer = (Player) slotContext.entity();
        On = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Onplayer = null;
        On = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static Player Onplayer;
    public static boolean On;

    public static boolean IsOn(Player player) {
        return Onplayer != null && Onplayer.equals(player) && On;
    }

    public static int getCurrentMaxCount() {

        AtomicInteger maxCount = new AtomicInteger();
        MyMissionBow.mobArrowCountMap.forEach((mob, integer) -> {
            if (mob.isAlive() && integer > maxCount.get()) maxCount.set(integer);
        });
        return maxCount.get();

    }

    public static double CritDamageUp(Player player) {
        if (!IsOn(player)) return 0;
        return getCurrentMaxCount() * 0.2;
    }

    public static double AttackDamageUp(Player player) {
        if (!IsOn(player)) return 0;
        return getCurrentMaxCount() * 20;
    }

    public static int ExArrowTick = 0;

    public static void ArrowHit(Player player) {
        if (!IsOn(player)) return;
        Random random = new Random();
        if (random.nextDouble() < 0.2) ExArrowTick = 16;
    }

    public static void Tick(Player player) {
        if (!IsOn(player)) return;
        if (ExArrowTick > 0) {
            if (ExArrowTick % 2 == 0) Shoot(player);
            ExArrowTick --;
        }
        if (player.tickCount % 4 == 0) {
            Compute.EffectLastTimeSend(player, ModItems.MyMissionCurios.get().getDefaultInstance(),8888,getCurrentMaxCount(),true);
        }
    }

    public static void Shoot(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;

        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                serverPlayer, false,true,1);

        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 1.5f, 1.0f);
        arrow.setCritArrow(true);
        arrow.setNoGravity(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);

    }



}
