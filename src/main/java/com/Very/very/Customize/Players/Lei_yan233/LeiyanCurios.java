package com.Very.very.Customize.Players.Lei_yan233;

import com.Very.very.CoreAttackModule.MyArrow;
import com.Very.very.Customize.Uniform.Attributes;
import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
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

public class LeiyanCurios extends Item implements ICurioItem {

    public LeiyanCurios(Properties p_41383_) {
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
        Style style = Utils.styleOfHealth;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("精灵贤者的守护").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 每1s释放一支1倍率的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("精灵之箭").withStyle(ChatFormatting.RESET).withStyle(style)));
        components.add(Component.literal(" -精灵之箭将会自动锁定周围的敌人").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 精灵之箭").withStyle(ChatFormatting.RESET).withStyle(style).
                append(Component.literal("在命中敌人后，会为你提供一层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("精灵贤者的赐福").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，同时为你回复").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("5%")));
        components.add(Component.literal(" 每层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("赐福").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("将为你提供:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExAttackDamage("50")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.DefencePenetration("10%")));
        components.add(Component.literal(" 赐福最高可叠加至5层，持续5s").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 当").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("赐福").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("叠加至").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("5层时，额外释放8支0.2倍率的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("精灵之箭").withStyle(ChatFormatting.RESET).withStyle(style)));

        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚精灵贤者的守护，授予对维瑞阿契做出了杰出贡献的 - Lei_yan233").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Onplayer = (Player) slotContext.entity();
        On = true;
        Count = 0;
        PassiveTick = 0;
        ExArrowTick = 0;
        EffectTick = 0;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Onplayer = null;
        On = false;
        Count = 0;
        PassiveTick = 0;
        ExArrowTick = 0;
        EffectTick = 0;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static Player Onplayer;
    public static boolean On;
    public static int Count = 0;
    public static int PassiveTick = 0;
    public static int ExArrowTick = 0;
    public static int EffectTick = 0;

    public static double ExDefencePenetration(Player player) {
        if (!IsOn(player)) return 0;
        int TickCount = player.getServer().getTickCount();
        if (EffectTick > TickCount) return 0.5;
        if (PassiveTick > TickCount) return Count * 0.1;
        return 0;
    }

    public static double ExDamage(Player player) {
        if (!IsOn(player)) return 0;
        int TickCount = player.getServer().getTickCount();
        if (EffectTick > TickCount) return 250;
        if (PassiveTick > TickCount) return Count * 50;
        return 0;
    }

    public static void CountAdd(Player player) {
        if (!IsOn(player)) return;
        int TickCount = player.getServer().getTickCount();
        if (PassiveTick < TickCount) Count = 0;
        Count = Math.min(5,Count + 1);
        PassiveTick = TickCount + 100;
        if (Count == 5) {
            Count = 0;
            ExArrowTick = 16;
            EffectTick = TickCount + 40;
        }

        Compute.EffectLastTimeSend(player, ModItems.LeiyanCurios.get().getDefaultInstance(), 100, Count);
        Compute.PlayerHeal(player,player.getMaxHealth() * 0.05);
    }

    public static boolean IsOn(Player player) {
        return Onplayer != null && Onplayer.equals(player) && On;
    }

    public static void Tick(Player player) {
        if (!IsOn(player)) return;
        if (player.tickCount % 20 == 0) Shoot(player,1,true);
        if (ExArrowTick > 0) {
            if (ExArrowTick % 2 == 0) Shoot(player,0.2,false);
            ExArrowTick --;
        }
    }

    public static void Shoot (Player player, double rate, boolean WhetherShootByPlayer) {
        ServerPlayer serverPlayer = (ServerPlayer) player;

        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                serverPlayer, WhetherShootByPlayer,true,rate);

        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 1.5f, 1.0f);
        arrow.setCritArrow(true);
        arrow.setNoGravity(true);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);

    }

    public static double ExXpStrengthDamage(Player player) {
        if (!IsOn(player)) return 0;
        return 0;
    }


}
