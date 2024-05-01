package com.very.wraq.customized.uniform.element;

import com.very.wraq.process.element.Element;
import com.very.wraq.process.element.RainbowCrystal;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
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

public class LifeCurios0 extends Item implements ICurioItem {

    public LifeCurios0(Properties p_41383_) {
        super(p_41383_);
        Utils.Defence.put(this,500d);
        Utils.ManaDefence.put(this,500d);
        Utils.DefencePenetration.put(this,0.2);
        Utils.ManaPenetration.put(this,0.2);
        Utils.CoolDownDecrease.put(this,0.25);
        Element.LifeElementValue.put(this, 1d);
        Utils.CustomizedList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfLife;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components, RainbowCrystal.rainBowNameFourChar("世界根基"));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("50%最终伤害加成").withStyle(ChatFormatting.RED)));
        Compute.DescriptionPassive(components, Component.literal("生机蕴能").withStyle(style));
        components.add(Component.literal(" 提升").withStyle(ChatFormatting.WHITE).
                append(Element.Description.LifeElementValue("100%")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal("uniform - element - 300vp").withStyle(ChatFormatting.AQUA));
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

    public static boolean isOn(Player player) {
        return onPlayerMap.containsKey(player) && onPlayerMap.get(player);
    }

    public static double playerFinalDamageEnhance(Player player) {
        if (!isOn(player)) return 0;
        return 0.5;
    }

    public static double playerLifeElementValueEnhance(Player player) {
        if (!isOn(player)) return 1;
        return 2;
    }
}
