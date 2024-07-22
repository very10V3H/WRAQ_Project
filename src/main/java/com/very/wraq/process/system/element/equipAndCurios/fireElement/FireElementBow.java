package com.very.wraq.process.system.element.equipAndCurios.fireElement;

import com.very.wraq.core.MyArrow;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FireElementBow extends WraqBow implements ActiveItem {

    public FireElementBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 600d);
        Utils.defencePenetration0.put(this, 4000d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.45);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
        Element.FireElementValue.put(this, 2d);
    }

    public static void IgniteEffect(Player player, Mob mob) {
        if (mob.getRemainingFireTicks() > 0 && player.getMainHandItem().is(ModItems.FireElementBow.get())) {
            FireElementSword.playerFireElementValueEnhanceTickMap.put(player, player.getServer().getTickCount() + 40);
        }
    }

    public static void Tick(Player player) {
        if (!player.getMainHandItem().is(ModItems.FireElementBow.get())) return;
        if (!FireElementSword.playerIgniteMobMap.containsKey(player))
            FireElementSword.playerIgniteMobMap.put(player, new ArrayList<>());
        List<FireElementSword.IgniteMob> list = FireElementSword.playerIgniteMobMap.get(player);
        list.removeIf(igniteMob -> igniteMob.tick() < player.getServer().getTickCount());
        if (list.size() > 0)
            Compute.effectLastTimeSend(player, ModItems.FireElementBow.get().getDefaultInstance(), 8888, Math.min(3, list.size()), true);
        else
            Compute.effectLastTimeSend(player, ModItems.FireElementBow.get().getDefaultInstance(), 0, Math.min(3, list.size()), true);
    }

    public static void PlayerIgniteMobEffect(Player player, Mob mob) {
        if (!player.getMainHandItem().is(ModItems.FireElementBow.get())) return;
        if (!FireElementSword.playerIgniteMobMap.containsKey(player))
            FireElementSword.playerIgniteMobMap.put(player, new ArrayList<>());
        List<FireElementSword.IgniteMob> list = FireElementSword.playerIgniteMobMap.get(player);
        list.removeIf(igniteMob -> igniteMob.tick() < player.getServer().getTickCount());
        if (mob.getRemainingFireTicks() == 0) {
            list.add(new FireElementSword.IgniteMob(mob.getId(), player.getServer().getTickCount() + 60));
        }
    }

    @Override
    public void shoot(ServerPlayer serverPlayer) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5f, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.FireElementParticle.get());
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.FireElementParticle.get());
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfFire;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
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
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfElement();
    }

    @Override
    public void active(Player player) {
        if (Compute.PlayerUseWithHud(player, FireElementSword.playerActiveCoolDownMap, ModItems.FireElementBow.get(), 0, 7)) {
            Compute.playerItemCoolDown(player, this, 7);
            List<Mob> mobList = Compute.OneShotLaser(player, true, Compute.XpStrengthADDamage(player, 2), ModParticles.LONG_RED_SPELL.get());
            mobList.forEach(mob -> Compute.IgniteMob(player, mob, 80));
        }
    }
}

