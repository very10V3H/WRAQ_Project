package com.very.wraq.common.equip;

import com.very.wraq.common.impl.onshoot.OnShootArrowCurios;
import com.very.wraq.common.registry.MySound;
import com.very.wraq.common.util.Utils;
import com.very.wraq.core.MyArrow;
import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public abstract class WraqBow extends WraqMainHandEquip {

    public WraqBow(Properties properties) {
        super(properties);
        Utils.bowTag.put(this, 1d);
    }

    @Override
    public Component getType() {
        return Component.literal("长弓").withStyle(CustomStyle.styleOfFlexible);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return interactionHand.equals(InteractionHand.MAIN_HAND)
                ? InteractionResultHolder.success(player.getMainHandItem()) : InteractionResultHolder.success(player.getOffhandItem());
    }

    public void shoot(ServerPlayer serverPlayer, double rate, boolean mainShoot) {
        MyArrow myArrow = summonArrow(serverPlayer, rate);
        myArrow.mainShoot = mainShoot;
        if (mainShoot) {
            OnShootArrowCurios.shoot(serverPlayer);
        }
    }

    public record ShootParticle(ParticleOptions options, int times) {}
    protected ShootParticle getShootParticle() {
        return new ShootParticle(null, 0);
    }

    protected float getArrowSpeed() {
        return 0;
    }

    protected MyArrow summonArrow(ServerPlayer serverPlayer, double rate) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true, rate);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f,
                getArrowSpeed() == 0 ? 3F : getArrowSpeed(), 1.0f);
        arrow.setCritArrow(true);
        serverPlayer.level().addFreshEntity(arrow);
        MySound.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        if (getShootParticle() != null) {
            for (int i = 0 ; i < getShootParticle().times ; i ++) {
                ParticleProvider.FaceCircleCreate(serverPlayer, 1 + 0.5 * i, 0.75 - 0.25 * i, 20 - 4 * i,
                        getShootParticle().options);
            }
        } else {
            ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WAX_OFF);
        }
        return arrow;
    }

    public static void adjustArrow(MyArrow myArrow, Player player) {
        if (false) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 80, 80, 80));
            mobList.removeIf(mob -> mob.distanceTo(player) > 30 || mob instanceof Civil);

            Mob NearestMob = null;
            double Distance = 80;
            for (Mob mob : mobList) {
                if (mob.distanceTo(player) < Distance) {
                    NearestMob = mob;
                    Distance = mob.distanceTo(player);
                }
            }

            myArrow.setDeltaMovement(NearestMob.position().add(0, 1, 0).subtract(player.position().add(0, 1.5, 0)).normalize().scale(4.5));
            myArrow.moveTo(player.pick(0.5, 0, false).getLocation());
            myArrow.setCritArrow(true);
            myArrow.setNoGravity(true);
            ProjectileUtil.rotateTowardsMovement(myArrow, 1);

            ParticleProvider.LineParticle(player.level(), (int) NearestMob.distanceTo(player),
                    player.pick(0.5, 0, false).getLocation(), NearestMob.position().add(0, 1, 0), ParticleTypes.SNOWFLAKE);
        }
    }
}
