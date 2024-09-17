package com.very.wraq.series.overworld.castle;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
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

public class BlazeBracelet extends Item implements ICurioItem {

    public BlazeBracelet(Properties p_41383_) {
        super(p_41383_);
        Utils.defence.put(this, 400d);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = CustomStyle.styleOfPower;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("火焰附加").withStyle(style));
        components.add(Component.literal(" 使你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("灼烧").withStyle(style)).
                append(Component.literal("目标2s").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("，每0.5s造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1倍").withStyle(style)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("物理伤害").withStyle(style)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfCastle(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Compute.AddCuriosToList((Player) slotContext.entity(), stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Compute.RemoveCuriosInList((Player) slotContext.entity(), stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static void Passive(Player player, Mob mob) {
        if (Compute.PlayerHasCurios(player, ModItems.BlazeBracelet.get())) {
            /*Damage.LastXpStrengthDamageToMob(player, mob, 1, 40, 10, true);*/
            Compute.IgniteMob(player, mob, 40);
        }
    }

}
