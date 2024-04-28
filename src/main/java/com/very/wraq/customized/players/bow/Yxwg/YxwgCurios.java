package com.very.wraq.customized.players.bow.Yxwg;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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
import java.util.List;
import java.util.Random;

public class YxwgCurios extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public YxwgCurios(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfHealth;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("溅射冲击").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("将对目标周围6格的敌人造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("12倍").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("物理伤害").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("并基于怪物数量，为你提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionPassive(components,Component.literal("持续灼烧").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("将对目标造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("灼烧").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("使目标每0.5s受到").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("12倍").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("物理伤害").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" -灼烧与溅射将会轮转").withStyle(ChatFormatting.ITALIC).withStyle(style));
        components.add(Component.literal(" 秩序与混乱，罪恶与惩罚").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚混沌双生之印，授予对维瑞阿契做出了杰出贡献的 - yxwg").withStyle(ChatFormatting.AQUA));
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

    public static void Passive(Player player, Mob mob) {
        if (!IsPlayer(player)) return;
        Random random = new Random();
        if (random.nextBoolean()) Passive1(player,mob);
        else Passive2(player,mob);
    }

    public static void Passive1(Player player, Mob mob) {
        if (!IsPlayer(player)) return;
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(),15,15,15));
        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6);
        mobList.forEach(mob1 -> {
            Compute.Damage.AttackDamageToMonster_AdDamage(player,mob1,Compute.XpStrengthADDamage(player,12));
        });
        Compute.PlayerShieldProvider(player,40,mobList.size() * Compute.PlayerAttributes.PlayerAttackDamage(player));
    }

    public static void Passive2(Player player, Mob mob) {
        if (!IsPlayer(player)) return;
        Compute.Damage.LastXpStrengthDamageToMob(player,mob,12,40,10,true);
        Compute.IgniteMob(player,mob,40);
    }

}
