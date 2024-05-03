package com.very.wraq.customized.players.bow.Shao_Feng;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.process.element.RainbowCrystal;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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

public class ShaoFengCurios extends Item implements ICurioItem {

    public ShaoFengCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.AttackDamage.put(this, Attributes.AttackDamage);
        Utils.DefencePenetration0.put(this, Attributes.DefencePenetration0);
        Utils.CritDamage.put(this, Attributes.CritDamage);
        Utils.Defence.put(this, Attributes.Defence);
        Utils.CritRate.put(this, Attributes.CritRate);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.setHoverName(RainbowCrystal.rainBowNameFourChar("外挂U盘"));
        Style style = CustomStyle.styleOfPower;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components, Component.literal("自瞄").withStyle(style));
        components.add(Component.literal(" 当").withStyle(ChatFormatting.WHITE).
                append(Component.literal("手持弓时").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("，无法进行").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("转而生成一个").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("光环").withStyle(style)).
                append(Component.literal("，每0.25s对12格内的目标造成一次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)));
        components.add(Component.literal(" 每击杀一名敌人，获得:").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 1. ").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("1.25%总")));
        components.add(Component.literal(" 2. ").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("10%")));
        components.add(Component.literal(" 3. ").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.DefencePenetration("100")));
        components.add(Component.literal(" 箭矢").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("在命中目标时，会为你回复").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("5%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Health("")));
        components.add(Component.literal(" 至多叠加至20层，每分钟失去一层").withStyle(style));
        components.add(Component.literal(" 当满层时，获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("50%伤害提升").withStyle(CustomStyle.styleOfPower)));
        Compute.DescriptionPassive(components, Component.literal("飞行").withStyle(style));
        components.add(Component.literal(" - 获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("飞行能力").withStyle(style)));
        components.add(Component.literal(" 射鸡游戏怎么能没有点科技？").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚外挂U盘，授予对维瑞阿契做出了杰出贡献的 - Shao_Feng").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Onplayer = (Player) slotContext.entity();
        On = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Onplayer = null;
        On = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static Player Onplayer;
    public static boolean On;

    public static boolean isOn(Player player) {
        return Onplayer != null && Onplayer.equals(player) && On;
    }

    public static void rangeAttack(Player player) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),30,30,30));
        mobList.removeIf(mob -> mob.distanceTo(player) > 12);
        ItemStack itemStack = player.getMainHandItem();

        if (Utils.BowTag.containsKey(itemStack.getItem())) {
            mobList.forEach(mob -> {
                MyArrow myArrow = new MyArrow(EntityType.ARROW,player.level(),player,true);
                MyArrow.CauseDamage(myArrow, mob, PlayerAttributes.PlayerAttackDamage(player));
                myArrow.remove(Entity.RemovalReason.KILLED);
            });
        } // 弓
    }

    public static int passiveCount = 0;
    public static void killMob(Player player) {
        if (!isOn(player)) return;
        passiveCount = Math.min(20, passiveCount + 1);
        Compute.EffectLastTimeSend(player, ModItems.ShaoFengCurios.get(), 8888, passiveCount, true);
    }

    public static double attackDamageEnhance(Player player) {
        if (!isOn(player)) return 1;
        return 1 + 0.0125 * passiveCount;
    }

    public static double critDamageUp(Player player) {
        if (!isOn(player)) return 0;
        return 0.1 * passiveCount;
    }

    public static double defencePenetration0Up(Player player) {
        if (!isOn(player)) return 0;
        return 100 * passiveCount;
    }

    public static double damageEnhance(Player player) {
        if (!isOn(player) && passiveCount < 20) return 0;
        return 0.5;
    }

    public static void onHitMob(Player player) {
        if (!isOn(player)) return;
        Compute.PlayerHeal(player, PlayerAttributes.PlayerAttackDamage(player) * 0.05);
    }

    public static void tick(Player player) {
        if (!isOn(player)) return;
        if (player.tickCount % 5 == 0) rangeAttack(player);
        if (player.tickCount % 1200 == 0 && passiveCount > 0) {
            passiveCount --;
            Compute.EffectLastTimeSend(player, ModItems.ShaoFengCurios.get(), 8888, passiveCount, true);
        }
        Compute.BallParticle((ServerPlayer) player, player.position(),6, ModParticles.RED_SPELL.get(), 50);
    }

}
