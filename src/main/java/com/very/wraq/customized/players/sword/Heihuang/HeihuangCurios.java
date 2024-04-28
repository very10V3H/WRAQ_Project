package com.very.wraq.customized.players.sword.Heihuang;

import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import java.util.*;

public class HeihuangCurios extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public HeihuangCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.Defence.put(this,800d);
        Utils.ManaDefence.put(this,800d);
        Utils.DefencePenetration.put(this,0.2);
        Utils.ManaPenetration.put(this,0.2);
        Utils.CoolDownDecrease.put(this,0.25);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfMoon;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("骰子").withStyle(style));
        components.add(Component.literal(" 对").withStyle(ChatFormatting.WHITE).
                append(Component.literal("目标").withStyle(CustomStyle.styleOfMoon)).
                append(Component.literal("发起攻击，将投掷一枚").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("骰子").withStyle(style)));
        components.add(Component.literal(" 将根据").withStyle(ChatFormatting.WHITE).
                append(Component.literal("骰子点数").withStyle(style)).
                append(Component.literal("大于目标之值，为持有者提供效果:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1点:").withStyle(style).
                append(Component.literal("下次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("的伤害提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1200%").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，若对方没有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("骰子").withStyle(style)).
                append(Component.literal("，则提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("2000%").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" 2点:").withStyle(style).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("50%总")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Defence("50%总")).
                append(Component.literal("，持续10s。").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("若对方没有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("骰子").withStyle(style)).
                append(Component.literal("，则获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("200%近战攻击增幅").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" 3点:").withStyle(style).
                append(Component.literal("禁锢").withStyle(CustomStyle.styleOfIce)).
                append(Component.literal("周围敌人5s，对其造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamageValue("禁锢怪物数量 * 400%")).
                append(Component.literal("若对方没有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("骰子").withStyle(style)).
                append(Component.literal("，则对周围敌人造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamageValue("2000%")));
        components.add(Component.literal(" 4点:").withStyle(style).
                append(Component.literal("给予周围玩家一枚").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("骰子").withStyle(style)).
                append(Component.literal("，投掷后立即引爆").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("，对目标造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamageValue("2000%")).
                append(Component.literal("若目标没有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("骰子").withStyle(style)).
                append(Component.literal("，则造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamageValue("4000%")));
        components.add(Component.literal(" 5点:").withStyle(style).
                append(Component.literal("给予目标").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("易损状态").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("，受到的伤害提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("400%").withStyle(ChatFormatting.RED)).
                append(Component.literal("，持续5s。").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("若目标没有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("骰子").withStyle(style)).
                append(Component.literal("，则提升至").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("800%").withStyle(ChatFormatting.RED)));
        components.add(Component.literal(" 6点:").withStyle(style).
                append(Component.literal("立即破坏目标的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("骰子").withStyle(style)).
                append(Component.literal("，并将其点数置为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0").withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚图书馆勋章，授予对维瑞阿契做出了杰出贡献的 - Heihuang_").withStyle(ChatFormatting.AQUA));
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

    public record MobData(Mob mob, int tick) {}

    public static void Attack(Player player, Mob mob) {
        if (!IsPlayer(player)) return;
        Passive(player, mob);
    }

    public static Map<Integer, Integer> passiveMobMap = new HashMap<>();
    public static void Passive(Player player, Mob mob) {
        if (!passiveMobMap.containsKey(mob.getId())) {
            Random random = new Random();
            int playerPoint = random.nextInt(1,8);
            int mobPoint = random.nextInt(1,8);
            int minusValue = playerPoint - mobPoint;
            passiveMobMap.put(mob.getId(),minusValue == 6 ? 0 : mobPoint);

            if (minusValue >= 1) nextAttackDamageUpFlag = true;
            if (minusValue >= 2) passive2EnhanceTick = player.getServer().getTickCount() + 200;
            if (minusValue >= 3) Passive3(player,mob);
            if (minusValue >= 4) {}
            if (minusValue >= 5) Passive5(player,mob);
            if (minusValue == 6) SoundOfBreakDice(player);

            Style style = CustomStyle.styleOfMoon;
            Compute.FormatMSGSend(player, Component.literal("骰子").withStyle(style),
                    Component.literal("你投到了 ").withStyle(ChatFormatting.WHITE).
                            append(Component.literal("" + playerPoint).withStyle(style)).
                            append(Component.literal(" 点").withStyle(ChatFormatting.WHITE)));
            Compute.FormatMSGSend(player, Component.literal("骰子").withStyle(style),
                    Component.literal("目标投到了 ").withStyle(ChatFormatting.WHITE).
                            append(Component.literal("" + mobPoint).withStyle(style)).
                            append(Component.literal(" 点").withStyle(ChatFormatting.WHITE)));

            if (minusValue >= 1) {
                Compute.FormatMSGSend(player, Component.literal("骰子").withStyle(style),
                        Component.literal("触发 ").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("" + Math.min(5,minusValue)).withStyle(style)).
                                append(Component.literal(" 项效果").withStyle(ChatFormatting.WHITE)));
                Compute.EffectLastTimeSend(player, ModItems.HeihuangCurios.get().getDefaultInstance(), 200, Math.min(minusValue, 6));
            }


        }
    }

    public static boolean MobHasNoDice(Mob mob) {
        return (!passiveMobMap.containsKey(mob.getId()) || passiveMobMap.get(mob.getId()) == 0);
    }

    public static boolean nextAttackDamageUpFlag = false;
    public static double Passive1DamageEnhance(Player player, Mob mob) {
        if (!IsPlayer(player)) return 0;
        if (nextAttackDamageUpFlag) {
            nextAttackDamageUpFlag = false;
            SoundOfBreakDiceAttack(player);
            return MobHasNoDice(mob) ? 20 : 12;
        }
        return 0;
    }

    public static int passive2EnhanceTick = 0;
    public static double Passive2CritDamageEnhance(Player player) {
        if (!IsPlayer(player)) return 1;
        if (passive2EnhanceTick > player.getServer().getTickCount()) return 1.5;
        return 1;
    }

    public static double Passive2DefenceEnhance(Player player) {
        if (!IsPlayer(player)) return 1;
        if (passive2EnhanceTick > player.getServer().getTickCount()) return 1.5;
        return 1;
    }

    public static double Passive2AttackDamageEnhance(Player player, Mob mob) {
        if (!IsPlayer(player) || !MobHasNoDice(mob)) return 0;
        return 2;
    }

    public static void Passive3(Player player, Mob mob) {
        if (!IsPlayer(player)) return;
        List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 15, 15, 15));
        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6);
        mobList.forEach(mob1 -> {
             mob1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 100, false, false, false));
             if (!MobHasNoDice(mob)) {
                 Compute.Damage.ManaDamageToMonster_RateApDamage(player,mob1,4 * mobList.size(), false);
             }
             else {
                 Compute.Damage.AttackDamageToMonster_RateAdDamage(player,mob1,20);
             }
        });
    }

    public static Map<Integer, Integer> passive5MobTickMap = new HashMap<>();
    public static Map<Integer, Double> passive5MobRateMap = new HashMap<>();

    public static void Passive5(Player player, Mob mob) {
        if (!IsPlayer(player)) return;
        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100,1,false,false,false));
        passive5MobTickMap.put(mob.getId(), player.getServer().getTickCount() + 100);
        passive5MobRateMap.put(mob.getId(), MobHasNoDice(mob) ? 8d : 4d);
    }

    public static double MobWithstandDamageEnhance(Mob mob) {
        if (passive5MobTickMap.containsKey(mob.getId()) && passive5MobTickMap.get(mob.getId()) > mob.getServer().getTickCount()) {
            return passive5MobRateMap.get(mob.getId());
        }
        return 0;
    }

    public static void SoundOfBreakDiceAttack(Player player) {
        List<Player> playerList = player.level().getEntitiesOfClass(net.minecraft.world.entity.player.Player.class, AABB.ofSize(player.position(), 25, 25, 25));
        playerList.forEach(player1 -> {
            ModNetworking.sendToClient(new SoundsS2CPacket(14), (ServerPlayer) player1);
        });
    }

    public static void SoundOfBreakDice(Player player) {
        List<Player> playerList = player.level().getEntitiesOfClass(net.minecraft.world.entity.player.Player.class, AABB.ofSize(player.position(), 25, 25, 25));
        playerList.forEach(player1 -> {
            ModNetworking.sendToClient(new SoundsS2CPacket(16), (ServerPlayer) player1);
        });
    }

    public static void SoundOfThrowing(Player player) {
        List<Player> playerList = player.level().getEntitiesOfClass(net.minecraft.world.entity.player.Player.class, AABB.ofSize(player.position(), 25, 25, 25));
        playerList.forEach(player1 -> {
            ModNetworking.sendToClient(new SoundsS2CPacket(15), (ServerPlayer) player1);
        });
    }

}
