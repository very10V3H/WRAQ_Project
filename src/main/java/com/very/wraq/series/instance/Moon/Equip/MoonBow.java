package com.very.wraq.series.instance.Moon.Equip;

import com.very.wraq.core.MyArrow;
import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class MoonBow extends WraqBow {

    public MoonBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 1200d);
        Utils.defencePenetration0.put(this, 2900d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.35);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoon1;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("行星之引").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("会将目标周围半径6内的其他敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("小幅牵引").withStyle(style)).
                append(Component.literal("至目标位置").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components, Component.literal("尘月之风").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("将额外释放").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("两支箭矢").withStyle(CustomStyle.styleOfFlexible)));
        components.add(Component.literal(" 额外箭矢").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%伤害").withStyle(CustomStyle.styleOfMoon)));
        components.add(Component.literal(" 两支额外箭矢").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("会自动锁定自身半径30格内的目标").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfMoon();
    }

    public static void MoonBowExShoot(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.MoonBow.get())) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 80, 80, 80));
            mobList.removeIf(mob -> mob.distanceTo(player) > 30 || mob instanceof Civil);
            Mob NearestMob = null;
            double Distance = 80;
            Mob NearerMob = null;
            for (Mob mob : mobList) {
                if (mob.distanceTo(player) < Distance) {
                    NearestMob = mob;
                    Distance = mob.distanceTo(player);
                }
            }

            if (NearestMob != null) {
                mobList.remove(NearestMob);
                Distance = 80;
                for (Mob mob : mobList) {
                    if (mob.distanceTo(player) < Distance) {
                        NearerMob = mob;
                        Distance = mob.distanceTo(player);
                    }
                }
                if (NearerMob == null) NearerMob = NearestMob;
                MyArrow myArrow = new MyArrow(EntityType.ARROW, player.level(), player, true, 0.25);
                myArrow.setDeltaMovement(NearestMob.position().add(0, 1, 0).subtract(player.position().add(0, 1.5, 0)).normalize().scale(4.5));
                myArrow.moveTo(player.pick(0.5, 0, false).getLocation());
                myArrow.setCritArrow(true);
                myArrow.setNoGravity(true);
                ProjectileUtil.rotateTowardsMovement(myArrow, 1);
                player.level().addFreshEntity(myArrow);
                ParticleProvider.LineParticle(player.level(), (int) NearestMob.distanceTo(player), player.pick(0.5, 0, false).getLocation(), NearestMob.position().add(0, 1, 0), ParticleTypes.FIREWORK);

                MyArrow myArrow1 = new MyArrow(EntityType.ARROW, player.level(), player, true, 0.25);
                myArrow1.setDeltaMovement(NearerMob.position().add(0, 1, 0).subtract(player.position().add(0, 1.5, 0)).normalize().scale(4.5));
                myArrow.moveTo(player.pick(0.5, 0, false).getLocation());
                myArrow.setCritArrow(true);
                myArrow.setNoGravity(true);
                ProjectileUtil.rotateTowardsMovement(myArrow1, 1);
                player.level().addFreshEntity(myArrow1);
                ParticleProvider.LineParticle(player.level(), (int) NearerMob.distanceTo(player), player.pick(0.5, 0, false).getLocation(), NearerMob.position().add(0, 1, 0), ParticleTypes.FIREWORK);
            }
        }
    }

    public static void Passive(Player player, Mob mob) {
        if (!player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.MoonBow.get())) return;
        List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 15, 15, 15));
        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6 || mob1.equals(mob));
        mobList.forEach(mob1 -> {
            Compute.MonsterGatherProvider(mob1, 2, mob.position());
        });
    }

    @Override
    public void shoot(ServerPlayer serverPlayer) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5F, 1.0f);
        MoonBow.MoonBowExShoot(serverPlayer);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.FIREWORK);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.FIREWORK);
    }

}
