package com.very.wraq.customized.players.bow.Hgj;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
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

public class HgjCurios extends Item implements ICurioItem {

    public HgjCurios(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfFire;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("定军神射").withStyle(style));
        components.add(Component.literal(" 箭矢").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("命中目标后，获得1层").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("定军").withStyle(style)).
                append(Component.literal("，每层").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("定军").withStyle(style)).
                append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("15%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("15%伤害提升").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，至多叠加至5层").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components,Component.literal("烈矢追魂").withStyle(style));
        components.add(Component.literal(" 普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("额外发射3支带有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("30%基础伤害").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("箭矢").withStyle(CustomStyle.styleOfFlexible)));
        components.add(Component.literal(" 箭矢").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("命中目标后，会将目标小范围内的其他敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("小幅度牵引").withStyle(CustomStyle.styleOfMoon1)).
                append(Component.literal("至目标处。").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚烈弓之矢，授予对维瑞阿契做出了杰出贡献的 - ").withStyle(ChatFormatting.AQUA));
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

    public static boolean IsOn(Player player) {
        return Onplayer != null && Onplayer.equals(player) && On;
    }

    public static void GatherMob(Player player, Mob mob) {
        if (!IsOn(player)) return;
        List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(),15,15,15));
        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6 || mob1.equals(mob));
        mobList.forEach(mob1 -> {
            Compute.MonsterGatherProvider(mob1,2,mob.position());
        });
    }

    public static int ExArrowTick = 0;
    public static void PlayerArrowAttack(Player player) {
        if (!IsOn(player)) return;
        ExArrowTick = 6;
    }

    public static void Shoot(Player player, double rate) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, PlayerAttributes.PlayerAttackDamage(player) * rate, true);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
        arrow.setCritArrow(true);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WAX_OFF);

    }

    public static void Tick(Player player) {
        if (!IsOn(player)) return;
        if (ExArrowTick >= 0) ExArrowTick --;
        if (ExArrowTick % 2 == 0) {
            Shoot(player,0.3);
        }
    }

    public static int passiveCount = 0;
    public static int passiveTick = 0;
    public static void ArrowHitMob(Player player) {
        if (!IsOn(player)) return;
        int tickCount = player.getServer().getTickCount();
        if (passiveTick < tickCount) passiveCount = 0;
        passiveCount = Math.min(5, passiveCount + 1);
        passiveTick = tickCount + 100;
        Compute.EffectLastTimeSend(player, ModItems.HgjCurios.get().getDefaultInstance(), 100, passiveCount);
    }

    public static double AttackDamageEnhance(Player player) {
        if (!IsOn(player) || passiveTick < player.getServer().getTickCount()) return 1;
        return 1 + 0.15 * passiveCount;
    }

    public static double DamageEnhance(Player player) {
        if (!IsOn(player) || passiveTick < player.getServer().getTickCount()) return 1;
        return 1 + 0.15 * passiveCount;
    }
}
