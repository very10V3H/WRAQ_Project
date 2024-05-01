package com.very.wraq.customized.uniform.attack;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
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

public class AttackCurios1 extends Item implements ICurioItem {

    public AttackCurios1(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfPower;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("独夫之心").withStyle(style));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("50%最终伤害提升").withStyle(ChatFormatting.RED)));
        Compute.DescriptionPassive(components,Component.literal("横行").withStyle(style));
        components.add(Component.literal(" 暴击").withStyle(style).
                append(Component.literal("将会获得一层").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("暴怒").withStyle(style)).
                append(Component.literal("，每层持续5s，至多叠加至5层").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 每层").withStyle(ChatFormatting.WHITE).
                append(Component.literal("暴怒").withStyle(style)).
                append(Component.literal("为你提供:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("20%")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("5%总")));
        components.add(Component.literal(" 残暴的君主，终将被民众推翻。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal("uniform - sword - 300vp").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        onPlayerMap.put(player,true);
        Compute.AddCuriosToList(player,stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        onPlayerMap.put(player,false);
        Compute.RemoveCuriosInList(player,stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static Map<Player,Boolean> onPlayerMap = new HashMap<>();
    public static Map<Player, Integer> playerCountsMap = new HashMap<>();
    public static Map<Player, Integer> playerLastTickMap = new HashMap<>();

    public static boolean isOn(Player player) {
        return onPlayerMap.containsKey(player) && onPlayerMap.get(player);
    }

    public static double playerFinalDamageEnhance(Player player) {
        if (!isOn(player)) return 0;
        return 0.5;
    }

    public static void playerCritEffect(Player player) {
        if (!isOn(player)) return;
        if (!playerLastTickMap.containsKey(player) || playerLastTickMap.get(player) < player.getServer().getTickCount()) {
            playerCountsMap.put(player, 0);
        }
        playerLastTickMap.put(player, player.getServer().getTickCount() + 100);
        int counts = Math.min(5, playerCountsMap.getOrDefault(player, 0) + 1);
        playerCountsMap.put(player, counts);
        Compute.EffectLastTimeSend(player, ModItems.AttackCurios1.get(), 100, counts);
    }

    public static double playerCritDamageUp(Player player) {
        if (!isOn(player)) return 0;
        if (playerLastTickMap.getOrDefault(player, 0) > player.getServer().getTickCount()) {
            return 0.2 * playerCountsMap.getOrDefault(player, 0);
        }
        return 0;
    }

    public static double playerAttackDamageEnhance(Player player) {
        if (!isOn(player)) return 1;
        if (playerLastTickMap.getOrDefault(player, 0) > player.getServer().getTickCount()) {
            return 1 + 0.05 * playerCountsMap.getOrDefault(player, 0);
        }
        return 1;
    }
}
