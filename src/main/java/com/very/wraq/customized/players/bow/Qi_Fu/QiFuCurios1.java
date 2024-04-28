package com.very.wraq.customized.players.bow.Qi_Fu;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
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

public class QiFuCurios1 extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public QiFuCurios1(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfBloodMana;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("我将死亡，编曲咏唱").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("将额外发射2支箭矢").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components,Component.literal("此刻，大幕渐起").withStyle(style));
        components.add(Component.literal(" 精彩:").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("暴击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("时，为你提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MovementSpeed("100%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Swiftness("2")).
                append(Component.literal("，持续2s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 绝妙:").withStyle(ChatFormatting.RED).
                append(Component.literal("每次击杀敌人时，你将获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MovementSpeed("200%")).
                append(Component.literal("，持续10s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 卓越:").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("每次翻滚时，为你提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Swiftness("3")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MovementSpeed("150%")).
                append(Component.literal("，持续3s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 非凡:").withStyle(CustomStyle.styleOfMoon1).
                append(Component.literal("根据").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MovementSpeed("")).
                append(Component.literal("为你1:4提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ExAttackDamage("")).
                append(Component.literal("，并根据目标已损失生命值，为你提供至多").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("80%伤害提升").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" 我于杀戮之中盛放，亦如黎明中的花朵。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚低语，授予对维瑞阿契做出了杰出贡献的 - 147895").withStyle(ChatFormatting.AQUA));
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

    public static int ExArrowTick = 0;

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        if (ExArrowTick > 0) {
            if (ExArrowTick % 2 == 0) QiFuCurios.Shoot(player,false,true);
            ExArrowTick --;
        }
    }

    public static void Passive(Player player) {
        if (!IsPlayer(player)) return;
        ExArrowTick += 4;
    }

    public static int Passive1LastTick = 0;
    public static void Passive1(Player player) {
        if (!IsPlayer(player)) return;
        Passive1LastTick = player.getServer().getTickCount() + 40;
        Compute.EffectLastTimeSend(player, ModItems.QiFuCurios1Passive1.get().getDefaultInstance(),40);
    }

    public static double Passive1ExMovementSpeed(Player player) {
        if (!IsPlayer(player)) return 0;
        if (Passive1LastTick > player.getServer().getTickCount()) return 1;
        return 0;
    }

    public static double Passive1ExSwiftness(Player player) {
        if (!IsPlayer(player)) return 0;
        if (Passive1LastTick > player.getServer().getTickCount()) return 2;
        return 0;
    }

    public static int Passive2LastTick = 0;
    public static void Passive2(Player player) {
        if (!IsPlayer(player)) return;
        Passive2LastTick = player.getServer().getTickCount() + 200;
        Compute.EffectLastTimeSend(player,ModItems.QiFuCurios1Passive2.get().getDefaultInstance(),200);
    }

    public static double Passive2ExMovementSpeed(Player player) {
        if (!IsPlayer(player)) return 0;
        if (Passive2LastTick > player.getServer().getTickCount()) return 2;
        return 0;
    }

    public static int Passive3LastTick = 0;
    public static void Passive3(Player player) {
        if (!IsPlayer(player)) return;
        Passive3LastTick = player.getServer().getTickCount() + 60;
        Compute.EffectLastTimeSend(player,ModItems.QiFuCurios1Passive3.get().getDefaultInstance(),60);
    }

    public static double Passive3ExMovementSpeed(Player player) {
        if (!IsPlayer(player)) return 0;
        if (Passive3LastTick > player.getServer().getTickCount()) return 1.5;
        return 0;
    }

    public static double Passive3ExSwiftness(Player player) {
        if (!IsPlayer(player)) return 0;
        if (Passive3LastTick > player.getServer().getTickCount()) return 3;
        return 0;
    }

    public static double ExAttackDamage(Player player) {
        if (!IsPlayer(player)) return 0;
        return Compute.PlayerAttributes.PlayerMovementSpeed(player) * 400;
    }

    public static double DamageEnhance(Player player, Mob mob) {
        if (!IsPlayer(player)) return 0;
        return 0.8 * (1 - mob.getHealth() / mob.getMaxHealth());
    }













}
