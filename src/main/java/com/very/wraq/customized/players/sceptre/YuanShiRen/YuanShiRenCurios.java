package com.very.wraq.customized.players.sceptre.YuanShiRen;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
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

public class YuanShiRenCurios extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public YuanShiRenCurios(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfYSR1;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("剧毒鞭笞").withStyle(style));
        components.add(Component.literal(" 普通法球攻击").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("的距离增加100%").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("，并在命中时附加持续2s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("毒素状态").withStyle(style)));
        components.add(Component.literal("  - 毒素状态:").withStyle(style).
                append(Component.literal("每0.5s对目标造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1倍").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionPassive(components,Component.literal("瘴气").withStyle(style));
        components.add(Component.literal(" 对任意目标造成伤害后，将会在目标周围产生 ").withStyle(ChatFormatting.WHITE).
                append(Component.literal("瘴气").withStyle(style)).
                append(Component.literal("，").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("瘴气").withStyle(style)).
                append(Component.literal("将会使处于该范围内的目标受到的伤害提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%").withStyle(style)).
                append(Component.literal("，并受到").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("减速效果").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" 使用能源实验室的高新技术研制的法球发射器。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚毁灭拓展，授予对维瑞阿契做出了杰出贡献的 - 疯狂原-是人").withStyle(ChatFormatting.AQUA));
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

    public static void EffectProvider(Player player, Mob mob) {
        if (IsPlayer(player)) Compute.Damage.LastXpStrengthDamageToMob(player,mob,1,40,10,false);
    }

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        if (player.tickCount % 10 == 0) HighFrequencyParticle(player);
        if (player.tickCount % 20 == 0) {
            Particle(player);
            for (PosTick posTick : list) {
                List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(posTick.vec3,15,15,15));
                mobList.removeIf(mob -> mob.position().distanceTo(posTick.vec3) > 5);
                mobList.forEach(mob -> Compute.AddSlowDownEffect(mob,40,3));
            }
        }
    }

    public record PosTick(Vec3 vec3, int tickCount) {}
    public static List<PosTick> list = new ArrayList<>();
    public static void Passive(Player player, Mob mob) {
        if (!IsPlayer(player)) return;
        boolean flag = true;
        for (PosTick posTick : list) {
            if (posTick.vec3.distanceTo(mob.position()) < 5) flag = false;
        }
        if (!flag) list.removeIf(posTick -> posTick.vec3.distanceTo(mob.position()) < 5);
        list.add(new PosTick(mob.position(),player.getServer().getTickCount() + 100));
    }

    public static void Particle(Player player) {
        list.removeIf(posTick -> posTick.tickCount < player.getServer().getTickCount() + 100);

        list.forEach(vec3 -> {
            ParticleProvider.SpaceRangeParticle((ServerLevel) player.level(), vec3.vec3(), 5, 300, ParticleTypes.SPORE_BLOSSOM_AIR);
        });
    }

    public static void HighFrequencyParticle(Player player) {
        list.forEach(vec3 -> {
            ParticleProvider.VerticleCircleParticle(vec3.vec3,(ServerLevel) player.level(), 0.25,5,80,ParticleTypes.COMPOSTER);
        });
    }

    public static double DamageEnhance(Mob mob) {
        for (PosTick vec3 : list) {
            if (mob.position().distanceTo(vec3.vec3) < 5 && vec3.tickCount > mob.getServer().getTickCount()) return 1;
        }
        return 0;
    }

}
