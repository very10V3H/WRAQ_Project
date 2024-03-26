package com.Very.very.Customize.Players.Black_Feisa_;

import com.Very.very.Customize.Uniform.Attributes;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlackFeisaCurios extends Item implements ICurioItem {

    public static Player onPlayer;
    public static boolean IsOn;
    public static int Count = 0;
    public static int ManaUpTick = 0;

    public BlackFeisaCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this, Attributes.ManaDamage);
        Utils.Defence.put(this, Attributes.Defence);
        Utils.ManaPenetration0.put(this, Attributes.ManaPenetration0);
        Utils.ManaRecover.put(this, Attributes.ManaRecover);
        Utils.MaxMana.put(this, Attributes.MaxMana);
        Utils.CoolDownDecrease.put(this, Attributes.CoolDown);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = Utils.styleOfField;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("守旧派的荣光").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 每").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaRecover("100")).
                append(Component.literal("为你提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("20%魔法伤害提升").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)));
        components.add(Component.literal(" 每").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExpUp("100%")).
                append(Component.literal("为你提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("2000")));
        Compute.DescriptionPassive(components,Component.literal("王源中路发狙").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)).
                append(Component.literal("会减少所有法术的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.25s冷却时间").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal(" “嗨~我是癫佬”").withStyle(ChatFormatting.RESET).withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚芙蓉王的意志，授予对维瑞阿契做出了杰出贡献的 - Black_FeiSa_").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        onPlayer = (Player) slotContext.entity();
        IsOn = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        onPlayer = null;
        IsOn = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsPlayer(Player player) {
        return onPlayer != null && onPlayer.equals(player) && IsOn;
    }

    public static Map<Item,Integer> ItemCoolDownTime = new HashMap<>();
    public static int PassiveCount = 0;
    public static int LaserTick = 0;

    public static void CoolDownRecord(Player player, Item item, int tick) {
        if (IsPlayer(player)) {
            ItemCoolDownTime.put(item,tick);
        }
    }

    public static void CoolDownDecrease(Player player) {
        if (!IsPlayer(player)) return;
        Utils.PowerTag.keySet().forEach(item -> {
            if (ItemCoolDownTime.containsKey(item)) {
                float rate = player.getCooldowns().getCooldownPercent(item, 1);
                int CurrentTick = (int) (ItemCoolDownTime.get(item) * rate);
                player.getCooldowns().removeCooldown(item);
                player.getCooldowns().addCooldown(item, Math.max(0, CurrentTick - 5));
                ItemCoolDownTime.put(item, CurrentTick - 5);
            }
        });

    }

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        LaserShoot(player);
    }


    public static void LaserShoot(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (LaserTick > TickCount) {
            Compute.Laser(player,ModParticles.VOLCANO.get(), Compute.XpStrengthAPDamage(player,4),10);
            LaserTick --;
        }
    }

    public static double DamageEnhance(Player player) {
        if (!IsPlayer(player)) return 0;
        return Compute.PlayerAttributes.PlayerManaRecover(player) / 500;
    }

    public static double ExManaDamage(Player player) {
        if (!IsPlayer(player)) return 0;
        return Compute.PlayerExpUp(player) * 2000;
    }

}
