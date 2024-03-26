package com.Very.very.Customize.Players.cgswd;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class CgswdCurios extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public CgswdCurios(Properties p_41383_) {
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
        Style style = Utils.styleOfMoon;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components, Component.literal("欲买桂花同载酒").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE));
        components.add(Component.literal(" 当你消耗").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("")).
                append(Component.literal("会将消耗的法力值存储，至多存储至4倍法力值上限。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 根据存储的法力值为你至多提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("25%总")).
                append(Component.literal("，并为你提供至多").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%魔法伤害提升").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)));
        Compute.DescriptionPassive(components, Component.literal("朝如青丝暮成雪").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
        components.add(Component.literal(" 普通法球攻击").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana).
                append(Component.literal("将拥有").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("125%基础伤害").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)).
                append(Component.literal("，在命中时，会使目标每0.5s受到").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.5倍").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)).
                append(Component.literal("，持续2s").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 至人无己，神人无功，圣人无名").withStyle(ChatFormatting.RESET).withStyle(style));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚逍遥游，授予对维瑞阿契做出了杰出贡献的 - cgswd").withStyle(ChatFormatting.AQUA));
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

    public static double storeManaValue = 0;
    public static int lastStoreTick = 0;

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        if (lastStoreTick < player.getServer().getTickCount()) {
            storeManaValue = 0;
            Compute.EffectLastTimeSend(player,ModItems.CgswdCurios.get().getDefaultInstance(),200, 0,true);
        }
    }

    public static void StoreValueAdd(Player player, double value) {
        if (!IsPlayer(player)) return;
        int TickCount = player.getServer().getTickCount();
        double maxMana = Compute.PlayerAttributes.PlayerMaxMana(player) + 100;
        storeManaValue = Math.min(maxMana * 4,storeManaValue + value);
        lastStoreTick = TickCount + 200;
        Compute.EffectLastTimeSend(player,ModItems.CgswdCurios.get().getDefaultInstance(),200, (int) (storeManaValue * 100 / (maxMana * 4)),true);
    }

    public static double ExManaDamageValue(Player player) {
        if (!IsPlayer(player)) return 1;
        int TickCount = player.getServer().getTickCount();
        double maxMana = Compute.PlayerAttributes.PlayerMaxMana(player) + 100;
        if (lastStoreTick > TickCount) return 1 + storeManaValue * 0.25 / (maxMana * 4);
        return 1;
    }

    public static double ExManaDamageEnhance(Player player) {
        if (!IsPlayer(player)) return 0;
        int TickCount = player.getServer().getTickCount();
        double maxMana = Compute.PlayerAttributes.PlayerMaxMana(player) + 100;
        if (lastStoreTick > TickCount) return storeManaValue * 0.25 / (maxMana * 4);
        return 0;
    }

    public static double NormalManaAttackDamageRate(Player player, Mob mob) {
        if (!IsPlayer(player)) return 1;
        Compute.Damage.LastXpStrengthDamageToMob(player,mob,0.5,40,10,false);
        return 1.25;
    }


}
