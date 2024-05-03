package com.very.wraq.customized.players.bow.littleart;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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

public class LittleartCurios extends Item implements ICurioItem {

    public LittleartCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.AttackDamage.put(this, Attributes.AttackDamage);
        Utils.DefencePenetration0.put(this, Attributes.DefencePenetration0);
        Utils.CritDamage.put(this, Attributes.CritDamage);
        Utils.Defence.put(this, Attributes.Defence);
        Utils.CritRate.put(this, Attributes.CritRate);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfSky;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components, Component.literal("诅咒").withStyle(style));
        components.add(Component.literal(" 受到的伤害").withStyle(ChatFormatting.RED).
                append(Component.literal("翻倍，每秒失去").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("5%")).
                append(Component.literal("，最多减少至30%").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 低于").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("50%")).
                append(Component.literal("，为你提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%伤害提升").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("100%")).
                append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("，持续10s").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components,Component.literal("尖啸").withStyle(style));
        components.add(Component.literal(" 当").withStyle(ChatFormatting.WHITE).
                append(Component.literal("手持弓时").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("，无法进行").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("转而生成一个").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("光环").withStyle(style)).
                append(Component.literal("，每0.25s对12格内的目标造成一次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)));
        components.add(Component.literal(" 造成伤害时，获得:").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 1. ").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("5%总")));
        components.add(Component.literal(" 2. ").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("25%")));
        components.add(Component.literal(" 3. ").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.DefencePenetration("250")));
        components.add(Component.literal(" 箭矢").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("在命中目标时，将造成伤害的0.05%转化为").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Health("")));
        components.add(Component.literal(" 每层持续5s，至多叠加至10层").withStyle(style));
        components.add(Component.literal(" - 获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("飞行能力").withStyle(style)));
        components.add(Component.literal(" 堕落的幽魂，无目的地游荡").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚堕落幽魂，授予对维瑞阿契做出了杰出贡献的 - littleart").withStyle(ChatFormatting.AQUA));
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
                MyArrow.CauseDamage(myArrow,mob, PlayerAttributes.PlayerAttackDamage(player));
            });
        } // 弓
    }

    public static int passiveCount = 0;
    public static int passiveTick = 0;
    public static void causeDamage(Player player) {
        if (!isOn(player)) return;
        int tickCount = player.getServer().getTickCount();
        if (passiveTick < tickCount) passiveCount = 0;
        passiveCount = Math.min(10, passiveCount + 1);
        passiveTick = tickCount + 100;
        Compute.EffectLastTimeSend(player, ModItems.LittleartCurios.get(), 200, passiveCount);
    }

    public static double attackDamageEnhance(Player player) {
        if (!isOn(player)) return 1;
        return 1 + 0.05 * passiveCount;
    }

    public static double critDamageUp(Player player) {
        if (!isOn(player)) return 0;
        return 0.25 * passiveCount;
    }

    public static double defencePenetration0Up(Player player) {
        if (!isOn(player)) return 0;
        return 250 * passiveCount;
    }

    public static int shieldCoolDown = 0;

    public static void tick(Player player) {
        if (!isOn(player)) return;
        if (player.tickCount % 5 == 0) rangeAttack(player);
        if (player.tickCount % 20 == 0) {
            Compute.PlayerHealthDecrease(player, player.getMaxHealth() * 0.1, 0.3);
            if (player.getHealth() / player.getMaxHealth() < 0.5) {
                passive1Tick = player.getServer().getTickCount() + 200;
                if (player.getServer().getTickCount() > shieldCoolDown) {
                    Compute.PlayerShieldProvider(player, 200, PlayerAttributes.PlayerAttackDamage(player));
                    shieldCoolDown = player.getServer().getTickCount() + 200;
                }
            }
        }
    }

    public static void onHitMob(Player player, double damage) {
        if (!isOn(player)) return;
        Compute.PlayerHeal(player, damage * 0.0005);
    }

    public static int passive1Tick = 0;

    public static double damageEnhance(Player player) {
        if (!isOn(player) || passive1Tick < player.getServer().getTickCount()) return 0;
        return 0.5 * (1 - player.getHealth() / player.getMaxHealth());
    }

    public static double movementSpeedUp(Player player) {
        if (!isOn(player) || passive1Tick < player.getServer().getTickCount()) return 0;
        return 0.5 * (1 - player.getHealth() / player.getMaxHealth());
    }
}
