package com.very.wraq.series.instance.series.moon;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
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
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MoonCurios extends Item implements ICurioItem {

    public MoonCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = CustomStyle.styleOfMoon1;
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("朔望轮转").withStyle(style));
        components.add(Component.literal(" 每过10s，使你的下次攻击").withStyle(ChatFormatting.WHITE).
                append(Component.literal("附带").withStyle(style)).
                append(Component.literal("目标").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("1%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal(" - 以此致敬维瑞阿契的开拓者 - " + data.getString(StringUtils.MoonCuriosPlayerName)).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        MonthCuriosAttributeProvide(player, stack);
        Compute.AddCuriosToList(player, stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Compute.RemoveCuriosInList(player, stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static Map<Player, Integer> passiveCoolDownMap = new HashMap<>();

    public static double Passive(Player player, Mob mob) {
        if (Utils.playerCuriosListMap.containsKey(player)) {
            List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
            AtomicBoolean isOn = new AtomicBoolean(false);
            curiosList.forEach(itemStack -> {
                if (itemStack.is(ModItems.MoonCurios.get())) isOn.set(true);
            });
            if (isOn.get()) {
                int TickCount = player.getServer().getTickCount();
                if (!passiveCoolDownMap.containsKey(player) || TickCount > passiveCoolDownMap.get(player)) {
                    passiveCoolDownMap.put(player, TickCount + 200);
                    Compute.coolDownTimeSend(player, ModItems.MoonCurios.get().getDefaultInstance(), 200);
                    Compute.playerShieldProvider(player, 200, player.experienceLevel * 20);
                    return mob.getMaxHealth() * 0.01;
                }
            }
        }
        return 0;
    }

    public static void MonthCuriosAttributeProvide(Player player, ItemStack curios) {
        CompoundTag data = curios.getOrCreateTagElement(Utils.MOD_ID);
        double[] AttributeBase = {
                2, 8, 2, 8
        };
        int baseValueIndex = 0;
        int playerXpLevel = player.experienceLevel;

        if (!data.contains(StringUtils.MoonCuriosXpLevel)) {
            data.putInt(StringUtils.MoonCuriosXpLevel, playerXpLevel);
            data.putString(StringUtils.MoonCuriosPlayerName, player.getName().getString());
        }
        playerXpLevel = data.getInt(StringUtils.MoonCuriosXpLevel);
        data.putInt(StringUtils.CuriosAttribute.AttackDamage, (int) (playerXpLevel * AttributeBase[baseValueIndex++]));
        data.putInt(StringUtils.CuriosAttribute.ManaDamage, (int) (playerXpLevel * AttributeBase[baseValueIndex++]));
        data.putInt(StringUtils.CuriosAttribute.Defence, (int) (playerXpLevel * AttributeBase[baseValueIndex++]));
        data.putInt(StringUtils.CuriosAttribute.MaxHealth, (int) (playerXpLevel * AttributeBase[baseValueIndex++]));
    }
}
