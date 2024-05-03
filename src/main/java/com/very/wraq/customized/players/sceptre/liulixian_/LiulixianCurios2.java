package com.very.wraq.customized.players.sceptre.liulixian_;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
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
import java.util.List;

public class LiulixianCurios2 extends Item implements ICurioItem {

    public LiulixianCurios2(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this, Attributes.ManaDamage);
        Utils.Defence.put(this,Attributes.Defence);
        Utils.ManaPenetration0.put(this, Attributes.ManaPenetration0);
        Utils.ManaRecover.put(this, Attributes.ManaRecover);
        Utils.MaxMana.put(this, Attributes.MaxMana);
        Utils.CoolDownDecrease.put(this, Attributes.CoolDown);
        Utils.CustomizedList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfSakura;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("墨心").withStyle(style));
        components.add(Component.literal(" 双击空格可以").withStyle(ChatFormatting.WHITE).
                append(Component.literal("飞行").withStyle(style)));
        Compute.DescriptionPassive(components,Component.literal("琉璃仙法").withStyle(style));
        components.add(Component.literal(" 每").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("100最大")).
                append(Component.literal("将为你提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("2500")));
        components.add(Component.literal(" 每").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExpUp("10%")).
                append(Component.literal("将为你提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MovementSpeed("10%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Swiftness("0.2")));
        components.add(Component.literal(" 每").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("10000")).
                append(Component.literal("将为你提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%魔法伤害提升").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%法球攻击增幅").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 我寄愁心与明月 随君直到夜郎西").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚『琉璃之心』，授予对维瑞阿契做出了杰出贡献的 - liulixian_").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    public static Player onPlayer;
    public static boolean On;
    public static double flySpeed = 0;

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        onPlayer = (Player) slotContext.entity();
        On = true;
        Compute.AddCuriosToList(onPlayer,stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        onPlayer = (Player) slotContext.entity();
        On = false;
        Compute.RemoveCuriosInList(onPlayer,stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsOn(Player player) {
        return onPlayer != null && onPlayer.equals(player) && On;
    }

    public static double ExManaDamage(Player player) {
        if (!IsOn(player)) return 0;
        return (PlayerAttributes.PlayerMaxMana(player) + 100) * 2500 / 100;
    }

    public static double ExMovementSpeed(Player player) {
        if (!IsOn(player)) return 0;
        return PlayerAttributes.PlayerExpUp(player);
    }

    public static double ExSwiftnessUp(Player player) {
        if (!IsOn(player)) return 0;
        return PlayerAttributes.PlayerExpUp(player) * 2;
    }

    public static void Tick(Player player) {

    }

    public static double DamageEnhance(Player player) {
        if (!IsOn(player)) return 0;
        return PlayerAttributes.PlayerManaDamage(player) * 0.25 / 10000;
    }
}
