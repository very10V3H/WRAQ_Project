package com.very.wraq.customized.players.sceptre.shangmengli;

import com.very.wraq.coreAttackModule.ManaAttackModule;
import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ShangMengLiCurios3 extends Item implements ICurioItem {

    public ShangMengLiCurios3(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfMine;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("混沌触碰").withStyle(style));
        components.add(Component.literal(" 伸出一根").withStyle(ChatFormatting.WHITE).
                append(Component.literal("触腕").withStyle(style)).
                append(Component.literal("自动锁定16格内的目标持续鞭挞，每秒造成四次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法球伤害").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 受到你造成的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术伤害").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("的目标，会被一根持续1s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("触手").withStyle(style)).
                append(Component.literal("鞭挞四次，每次造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamageValue("150%")));
        components.add(Component.literal(" 触腕与触手").withStyle(style).
                append(Component.literal("会施加腐蚀效果，每0.5s对目标造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.5倍").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("，持续1s。").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components,Component.literal("能量转换").withStyle(style));
        components.add(Component.literal(" 法术").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("不再消耗").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("")));
        components.add(Component.literal(" 每拥有").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaRecover("10")).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("8%伤害提升")));
        components.add(Component.literal(" 每拥有").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("100")).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%伤害提升")));
        components.add(Component.literal(" 深渊的呼唤").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚活化鞘翅，授予对维瑞阿契做出了杰出贡献的 - shangmengli").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        onPlayer = (Player) slotContext.entity();
        isOn = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        onPlayer = null;
        isOn = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static Player onPlayer;
    public static boolean isOn;

    public static boolean IsOn(Player player) {
        return onPlayer != null && onPlayer.equals(player) && isOn;
    }

    public static Mob nearestMob;
    public record PowerEffectMob(Mob mob, int tick){}
    public static List<PowerEffectMob> mobList = new ArrayList<>();
    public static void Tick(Player player) {
        if (!IsOn(player)) return;
        FindNearestMob(player);
        int tickCount = player.getServer().getTickCount();

        if (nearestMob != null && nearestMob.isAlive()) {
            ParticleProvide(nearestMob, ModParticles.YSR.get());
            if (tickCount % 5 == 0) {
                ManaAttackModule.BasicAttack(player,nearestMob,1);
                Compute.Damage.LastXpStrengthDamageToMob(player,nearestMob,0.5,20,10,false);
            }
        }

        mobList.forEach(powerEffectMob -> {
            if (powerEffectMob.tick > tickCount) {
                ParticleProvide(powerEffectMob.mob, ModParticles.YSR1.get());
                if (tickCount % 5 == 0) {
                    Compute.Damage.ManaDamageToMonster_RateApDamage(player, powerEffectMob.mob, 1.5, false);
                    Compute.Damage.LastXpStrengthDamageToMob(player, powerEffectMob.mob, 0.5, 20, 10, false);
                }
            }
        });

    }

    public static void FindNearestMob(Player player) {
        if (!IsOn(player)) return;
        List<Mob> findMobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),50,50,50));
        findMobList.removeIf(mob -> mob.distanceTo(player) > 20 || !(mob instanceof Monster));
        double nearestDistance = 100;
        for (Mob mob : findMobList) {
            if (mob.distanceTo(player) < nearestDistance) {
                nearestMob = mob;
                nearestDistance = mob.distanceTo(player);
            }
        }
    }

    public static void addMobToMobList(Player player, Mob mob) {
        if (!IsOn(player)) return;
        mobList.add(new PowerEffectMob(mob,player.getServer().getTickCount() + 20));
        mobList.removeIf(powerEffectMob -> powerEffectMob.tick < player.getServer().getTickCount());
    }

    public static void ParticleProvide(Mob mob, ParticleOptions particleOptions) {
        Vec3 Delta = mob.getEyePosition().subtract(mob.position());
        ParticleProvider.CurveParticle(mob.level(),10,mob.position().subtract(Delta),
                mob.getEyePosition(),new Vec3(1,0,0), particleOptions);
        ParticleProvider.CurveParticle(mob.level(),10,mob.position().subtract(Delta),
                mob.getEyePosition(),new Vec3(0,0,1), particleOptions);
        ParticleProvider.CurveParticle(mob.level(),10,mob.position().subtract(Delta),
                mob.getEyePosition(),new Vec3(-1,0,0), particleOptions);
        ParticleProvider.CurveParticle(mob.level(),10,mob.position().subtract(Delta),
                mob.getEyePosition(),new Vec3(0,0,1), particleOptions);
    }

    public static double ManaDamageEnhance(Player player) {
        if (!IsOn(player)) return 0;
        return (PlayerAttributes.PlayerMaxMana(player) + 100) / 100 * 0.25
                + (PlayerAttributes.PlayerManaRecover(player) + 5) / 10 * 0.08;
    }
}
