package fun.wraq.common.equip;

import fun.wraq.Items.DevelopmentTools.equip.ManageEquip;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.impl.onshoot.OnShootManaArrowCurios;
import fun.wraq.common.impl.onshoot.OnShootManaArrowPassiveEquip;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.events.mob.loot.RandomLootEquip;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.skill.skillv2.mana.ManaNewSkillBase3_0;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.projectiles.mana.ManaArrowHitEntity;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public abstract class WraqSceptre extends WraqMainHandEquip {

    public WraqSceptre(Properties properties) {
        super(properties);
        Utils.sceptreTag.put(this, 1d);
        if (!(this instanceof ManageEquip) && !(this instanceof RandomLootEquip)) {
            Display.sceptreList.add(this);
        }
    }

    @Override
    public Component getType() {
        return Component.literal("法杖").withStyle(CustomStyle.styleOfMana);
    }

    public void shootManaArrow(Player player, double rate, boolean mainShoot, boolean computeManaCost,
                               ManaArrowHitEntity manaArrowHitEntity) {
        if (!computeManaCost || Compute.playerManaCost(player,
                Utils.manaCost.getOrDefault(player.getMainHandItem().getItem(), 0d))) {
            ManaArrow manaArrow = summonManaArrow(player, rate);
            if (manaArrow == null) return;
            manaArrow.mainShoot = mainShoot;
            if (manaArrowHitEntity != null) {
                manaArrow.manaArrowHitEntity = manaArrowHitEntity;
            }
            if (mainShoot) {
                OnShootManaArrowCurios.shoot(player);
                OnShootManaArrowPassiveEquip.shoot(player);
                ManaNewSkillBase3_0.onShoot(player);
            }
            MySound.soundToNearPlayer(player, SoundEvents.PARROT_IMITATE_EVOKER);
        }
    }

    public void shootManaArrow(Player player, double rate, boolean mainShoot) {
        shootManaArrow(player, rate, mainShoot, true, null);
    }

    protected EntityType<ManaArrow> getArrowType() {
        return ModEntityType.NEW_ARROW.get();
    }

    protected String getParticleType() {
        return StringUtils.ParticleTypes.EVOKER;
    }

    protected float getManaArrowSpeed() {
        return 3;
    }

    protected ManaArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        ManaArrow manaArrow = new ManaArrow(getArrowType(), player, level, rate,
                PlayerAttributes.manaPenetration(player), PlayerAttributes.manaPenetration0(player),
                getParticleType());
        manaArrow.setSilent(true);
        manaArrow.setNoGravity(true);
        manaArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f,
                getManaArrowSpeed() + PlayerAttributes.getManaArrowExFlySpeed(player), 1.0f);
        ProjectileUtil.rotateTowardsMovement(manaArrow, 0);
        WraqSceptre.adjustOrb(manaArrow, player);
        level.addFreshEntity(manaArrow);
        return manaArrow;
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
}
