package com.very.wraq.customized.players.sword.ZuoSI;

import com.very.wraq.coreAttackModule.AttackEvent;
import com.very.wraq.coreAttackModule.ManaAttackModule;
import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.Items.MobItem.MobArmor;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
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

public class ZuoSiCurios extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public ZuoSiCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.Defence.put(this,800d);
        Utils.ManaDefence.put(this,800d);
        Utils.DefencePenetration.put(this,0.2);
        Utils.ManaPenetration.put(this,0.2);
        Utils.CoolDownDecrease.put(this,0.25);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfLake;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("撕裂光环").withStyle(style));
        components.add(Component.literal(" 你的主手武器将").withStyle(ChatFormatting.WHITE).
                append(Component.literal("不能进行普通攻击").withStyle(ChatFormatting.BLUE)).
                append(Component.literal("，转而产生一个光环，光环的范围视你的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("主手武器的最大攻击距离（<= 12）").withStyle(ChatFormatting.RED)).
                append(Component.literal("而定，光环每0.25s对范围内的敌人造成一次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通近战攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 每").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.DefencePenetration("1%")).
                append(Component.literal("或")).
                append(Compute.AttributeDescription.ManaPenetration("1%")).
                append(Component.literal("为你提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.5%伤害提升").withStyle(CustomStyle.styleOfPower)));
        Compute.DescriptionPassive(components,Component.literal("摄魂夺魄").withStyle(style));
        components.add(Component.literal(" 你将获得被你最后一次击杀生物的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("100%")).
                append(Component.literal("（当你手持法杖时，将其转化为").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("400%")).
                append(Component.literal("）").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("，并窃取其").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Defence("50%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDefence("50%")).
                append(Component.literal("，持续60s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 最强大武器是什么？没错就是补丁！").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚武器大师的转职证明，授予对维瑞阿契做出了杰出贡献的 - 我就爱作死").withStyle(ChatFormatting.AQUA));
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

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        int tickCount = player.getServer().getTickCount();
        if (tickCount % 5 == 0) RangeAttack(player);
    }

    public static void RangeAttack(Player player) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),30,30,30));
        mobList.removeIf(mob -> mob.distanceTo(player) > 12);
        ItemStack itemStack = player.getMainHandItem();
        if (Utils.SwordTag.containsKey(itemStack.getItem())) mobList.removeIf(mob -> mob.distanceTo(player) > 4);

        if (Utils.SwordTag.containsKey(itemStack.getItem())) {
            mobList.forEach(mob -> {
                AttackEvent.AttackToMonster(mob, player, itemStack.getItem(), player.getPersistentData(), 1);
            });
        } // 近战武器

        if (Utils.BowTag.containsKey(itemStack.getItem())) {
            mobList.forEach(mob -> {
                MyArrow myArrow = new MyArrow(EntityType.ARROW,player.level(),player,true);
                MyArrow.CauseDamage(myArrow,mob,PlayerAttributes.PlayerAttackDamage(player));
            });
        } // 弓

        if (Utils.SceptreTag.containsKey(itemStack.getItem())) {
            mobList.forEach(mob -> {
                ManaAttackModule.BasicAttack(player,mob,1);
            });
        } // 法杖
    }

    public static double lastTimeKilledMobAttack = 0;
    public static double lastTimeKilledMobDefence = 0;
    public static double lastTimeKilledMobManaDefence = 0;
    public static int lastTimeKilledMobRecordTick = 0;
    public static void KillMob(Player player, Mob mob) {
        if (!IsPlayer(player)) return;
        if (mob.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof MobArmor mobArmor) {
            lastTimeKilledMobAttack = mobArmor.AttackDamage;
            lastTimeKilledMobDefence = mobArmor.Defence * 0.5;
            lastTimeKilledMobManaDefence = mobArmor.ManaDefence * 0.5;
            lastTimeKilledMobRecordTick = mob.getServer().getTickCount();
        }
    }

    public static double ExAttackOrManaDamage(Player player, boolean isAttack) {
        if (!IsPlayer(player)) return 0;
        if (lastTimeKilledMobRecordTick + 1200 > player.getServer().getTickCount()) {
            Item item = player.getMainHandItem().getItem();
            if (Utils.SwordTag.containsKey(item) || Utils.BowTag.containsKey(item)) {
                if (isAttack) return lastTimeKilledMobAttack;
            }
            if (Utils.SceptreTag.containsKey(item))
                if (!isAttack) return lastTimeKilledMobAttack * 4;
        }
        return 0;
    }

    public static double DamageEnhance(Player player) {
        return (PlayerAttributes.PlayerDefencePenetration(player) + PlayerAttributes.PlayerManaPenetration(player)) * 0.5;
    }
}
