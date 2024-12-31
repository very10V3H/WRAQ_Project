package fun.wraq.common.equip;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.impl.onshoot.OnShootManaArrowCurios;
import fun.wraq.common.impl.onshoot.OnShootManaArrowPassiveEquip;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.projectiles.mana.NewArrow;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter2.evoker.EvokerSceptre;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public abstract class WraqSceptre extends WraqMainHandEquip {

    public WraqSceptre(Properties properties) {
        super(properties);
        Utils.sceptreTag.put(this, 1d);
        Display.sceptreList.add(this);
    }

    @Override
    public Component getType() {
        return Component.literal("法杖").withStyle(CustomStyle.styleOfMana);
    }

    public void shootManaArrow(Player player, double rate, boolean mainShoot) {
        if (Compute.playerManaCost(player,
                Utils.manaCost.getOrDefault(player.getMainHandItem().getItem(), 0d))) {
            AbstractArrow arrow = summonManaArrow(player, rate);
            if (arrow == null) return;
            if (arrow instanceof NewArrow newArrow) {
                newArrow.mainShoot = mainShoot;
            }
            if (arrow instanceof ManaArrow manaArrow) {
                manaArrow.mainShoot = mainShoot;
            }
            if (mainShoot) {
                OnShootManaArrowCurios.shoot(player);
                OnShootManaArrowPassiveEquip.shoot(player);
            }
            MySound.soundToNearPlayer(player, SoundEvents.PARROT_IMITATE_EVOKER);
        }
    }

    protected AbstractArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        if (Compute.playerManaCost(player, EvokerSceptre.ManaCost)) {
            NewArrow newArrow = new NewArrow(player, level, PlayerAttributes.manaDamage(player) * rate,
                    PlayerAttributes.manaPenetration(player), PlayerAttributes.expUp(player),
                    false, PlayerAttributes.manaPenetration0(player));
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);
            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
            WraqSceptre.adjustOrb(newArrow, player);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.WITCH);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.WITCH);
            return newArrow;
        }
        return null;
    }

    public static void adjustOrb(AbstractArrow arrow, Player player) {
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

            arrow.setDeltaMovement(NearestMob.position().add(0, 1, 0).subtract(player.position().add(0, 1.5, 0)).normalize().scale(4.5));
            arrow.moveTo(player.pick(0.5, 0, false).getLocation());
            arrow.setCritArrow(true);
            arrow.setNoGravity(true);
            ProjectileUtil.rotateTowardsMovement(arrow, 1);

            ParticleProvider.createLineParticle(player.level(), (int) NearestMob.distanceTo(player),
                    player.pick(0.5, 0, false).getLocation(), NearestMob.position().add(0, 1, 0), ParticleTypes.SNOWFLAKE);
        }
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return interactionHand.equals(InteractionHand.MAIN_HAND)
                ? InteractionResultHolder.success(player.getMainHandItem()) : InteractionResultHolder.success(player.getOffhandItem());
    }
}
