package com.very.wraq.process.element.equipAndCurios.fireElement;

import com.very.wraq.process.element.Element;
import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ItemTier;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireElementSceptre extends SwordItem {

    public static Map<Player,Integer> coolDownMap = new HashMap<>();
    public FireElementSceptre(Properties p_42964_) {
        super(ItemTier.VMaterial,2,0, p_42964_);
        Utils.ManaDamage.put(this, 3548d);
        Utils.ManaRecover.put(this, 30d);
        Utils.ManaPenetration0.put(this, 4000d);
        Utils.MovementSpeed.put(this, 0.4);
        Utils.ManaCost.put(this, 15d);
        Utils.CoolDownDecrease.put(this,0.2);
        Element.FireElementValue.put(this, 2d);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SceptreTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfFire;
        Compute.ForgingHoverName(stack,Component.literal("").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
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
                append(Component.literal("0.5倍").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(CustomStyle.styleOfMana)).
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
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        p_40994_.hurtAndBreak(0, p_40996_, (p_41007_) -> {
            p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.ManaAttack(player,15);
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND,new ItemStack(Utils.ManaCoreMap.get(data.getString(StringUtils.ManaCore.ManaCore))));
                data.remove(StringUtils.ManaCore.ManaCore);
            }
        }
        return super.use(level, player, interactionHand);
    }

    public static void Active(Player player) {
        if (Compute.PlayerUseWithHud(player, FireElementSword.playerActiveCoolDownMap,ModItems.FireElementSceptre.get(),0,7)) {
            List<Mob> mobList = Compute.OneShotLaser(player, true, Compute.XpStrengthAPDamage(player,0.5), ModParticles.LONG_RED_SPELL.get());
            mobList.forEach(mob -> Compute.IgniteMob(player, mob, 80));
        }
    }

    public static void IgniteEffect(Player player, Mob mob) {
        if (mob.getRemainingFireTicks() > 0 && player.getMainHandItem().is(ModItems.FireElementSceptre.get())) {
            FireElementSword.playerFireElementValueEnhanceTickMap.put(player, player.getServer().getTickCount() + 40);
        }
    }

    public static void Tick(Player player) {
        if (!player.getMainHandItem().is(ModItems.FireElementSceptre.get())) return;
        if (!FireElementSword.playerIgniteMobMap.containsKey(player)) FireElementSword.playerIgniteMobMap.put(player, new ArrayList<>());
        List<FireElementSword.IgniteMob> list = FireElementSword.playerIgniteMobMap.get(player);
        list.removeIf(igniteMob -> igniteMob.tick() < player.getServer().getTickCount());
        if (list.size() > 0) Compute.EffectLastTimeSend(player, ModItems.FireElementSceptre.get().getDefaultInstance(), 8888, Math.min(3,list.size()), true);
        else Compute.EffectLastTimeSend(player, ModItems.FireElementSceptre.get().getDefaultInstance(), 0, Math.min(3,list.size()), true);
    }

    public static void PlayerIgniteMobEffect(Player player, Mob mob) {
        if (!player.getMainHandItem().is(ModItems.FireElementSceptre.get())) return;
        if (!FireElementSword.playerIgniteMobMap.containsKey(player)) FireElementSword.playerIgniteMobMap.put(player, new ArrayList<>());
        List<FireElementSword.IgniteMob> list = FireElementSword.playerIgniteMobMap.get(player);
        list.removeIf(igniteMob -> igniteMob.tick() < player.getServer().getTickCount());
        if (mob.getRemainingFireTicks() == 0) {
            list.add(new FireElementSword.IgniteMob(mob.getId(), player.getServer().getTickCount() + 60));
        }
    }

    public static void Shoot(Player player) {
        Level level = player.level();
        CompoundTag data = player.getPersistentData();
        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player,15)) {
            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_MAGMA.get(), player,level,
                    Compute.PlayerAttributes.PlayerManaDamage(player),
                    Compute.PlayerAttributes.PlayerManaPenetration(player),
                    Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.FireElement1TickParticle);
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);
            newArrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0,3,1);
            ProjectileUtil.rotateTowardsMovement(newArrow,0);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player,1,0.75,20, ModParticles.FireElementParticle.get());
            ParticleProvider.FaceCircleCreate((ServerPlayer) player,1.5,0.5,16, ModParticles.FireElementParticle.get());
        }
    }
}
