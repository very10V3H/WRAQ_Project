package com.very.wraq.process.element.equipAndCurios.fireElement;

import com.very.wraq.process.element.Element;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ItemTier;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireElementSword extends SwordItem{
    private final double BaseDamage = 600;
    private final double DefencePenetration0 = 4000;
    private final double CriticalHitRate = 0.30d;
    private final double CHitDamage = 0.9;
    private final double HealSteal = 0.08F;
    private final double SpeedUp = 0.5F;
    private final double AttackSpeedUp = -2f;
    public FireElementSword(Properties properties) {
        super(ItemTier.VMaterial,2,0,properties);
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Element.FireElementValue.put(this, 2d);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SwordTag.put(this,1d);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        Compute.USE(player);
        return super.use(level, player, useHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfFire;
        Compute.ForgingHoverName(stack,Component.literal("").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长剑").withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components, Component.literal("引燃").withStyle(style));
        components.add(Component.literal(" 释放一道").withStyle(ChatFormatting.WHITE).
                append(Component.literal("激光").withStyle(style)).
                append(Component.literal("，对沿途的目标").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("2倍").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("物理伤害").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，并").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("点燃").withStyle(style)).
                append(Component.literal("目标4s").withStyle(ChatFormatting.WHITE)));
        Compute.CoolDownTimeDescription(components, 7);
        Compute.DescriptionPassive(components, Component.literal("燃烬").withStyle(style));
        components.add(Component.literal(" 对处于").withStyle(ChatFormatting.WHITE).
                append(Component.literal("燃烧状态").withStyle(style)).
                append(Component.literal("的目标造成伤害后，提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%归一化炽焰元素强度").withStyle(style)).
                append(Component.literal("，持续2s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 对未处于").withStyle(ChatFormatting.WHITE).
                append(Component.literal("燃烧状态").withStyle(style)).
                append(Component.literal("的目标造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("燃烧").withStyle(style)).
                append(Component.literal("，会为你提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("20%伤害提升").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，至多叠加至").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("60%").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，每个目标持续3s").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfElement(components);
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        p_43278_.hurtAndBreak(0, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
    @Override
    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0d) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        return true;
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
    
    public static Map<Player, Integer> playerActiveCoolDownMap = new HashMap<>();

    public static void Active(Player player) {
        if (Compute.PlayerUseWithHud(player, FireElementSword.playerActiveCoolDownMap,ModItems.FireElementSword.get(),0,7)) {
            List<Mob> mobList = Compute.OneShotLaser(player, true, Compute.XpStrengthADDamage(player,2), ModParticles.LONG_RED_SPELL.get());
            mobList.forEach(mob -> Compute.IgniteMob(player, mob, 80));
        }
    }

    public static Map<Player, Integer> playerFireElementValueEnhanceTickMap = new HashMap<>();

    public static void IgniteEffect(Player player, Mob mob) {
        if (mob.getRemainingFireTicks() > 0 && player.getMainHandItem().is(ModItems.FireElementSword.get())) {
            FireElementSword.playerFireElementValueEnhanceTickMap.put(player, player.getServer().getTickCount() + 40);
        }
    }

    public static double FireElementValueEnhance(Player player) {
        if (playerFireElementValueEnhanceTickMap.containsKey(player) && playerFireElementValueEnhanceTickMap.get(player) > player.getServer().getTickCount()) return 1;
        return 0;
    }

    public record IgniteMob(Integer id, Integer tick) {}
    public static Map<Player, List<IgniteMob>> playerIgniteMobMap = new HashMap<>();

    public static void Tick(Player player) {
        if (!player.getMainHandItem().is(ModItems.FireElementSword.get())) return;
        if (!FireElementSword.playerIgniteMobMap.containsKey(player)) FireElementSword.playerIgniteMobMap.put(player, new ArrayList<>());
        List<IgniteMob> list = FireElementSword.playerIgniteMobMap.get(player);
        list.removeIf(igniteMob -> igniteMob.tick() < player.getServer().getTickCount());
        if (list.size() > 0) Compute.EffectLastTimeSend(player, ModItems.FireElementSword.get().getDefaultInstance(), 8888, Math.min(3,list.size()), true);
        else Compute.EffectLastTimeSend(player, ModItems.FireElementSword.get().getDefaultInstance(), 0, Math.min(3,list.size()), true);
    }

    public static void PlayerIgniteMobEffect(Player player, Mob mob) {
        if (!player.getMainHandItem().is(ModItems.FireElementSword.get())) return;
        if (!FireElementSword.playerIgniteMobMap.containsKey(player)) FireElementSword.playerIgniteMobMap.put(player, new ArrayList<>());
        List<IgniteMob> list = FireElementSword.playerIgniteMobMap.get(player);
        list.removeIf(igniteMob -> igniteMob.tick() < player.getServer().getTickCount());

        if (!mob.wasOnFire) {
            list.add(new IgniteMob(mob.getId(), player.getServer().getTickCount() + 60));
        }
    }

    public static double DamageEnhance(Player player) {
        if (!FireElementSword.playerIgniteMobMap.containsKey(player)) FireElementSword.playerIgniteMobMap.put(player, new ArrayList<>());
        List<IgniteMob> list = FireElementSword.playerIgniteMobMap.get(player);
        list.removeIf(igniteMob -> igniteMob.tick() < player.getServer().getTickCount());
        return Math.min(3,list.size()) * 0.2;
    }
}
