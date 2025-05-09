package fun.wraq.common.equip;

import fun.wraq.Items.DevelopmentTools.equip.ManageEquip;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Name;
import fun.wraq.common.impl.onshoot.OnShootArrowCurios;
import fun.wraq.common.impl.onshoot.OnShootArrowEquip;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.core.bow.MyArrow;
import fun.wraq.core.bow.MyArrowHitBlock;
import fun.wraq.customized.uniform.bow.BowCurios5;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.events.mob.loot.RandomLootEquip;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.skill.BowSkillTree;
import fun.wraq.process.system.skill.skillv2.bow.BowNewSkillBase2_0;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.quiver.WraqQuiver;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;

import java.util.List;

public abstract class WraqBow extends WraqMainHandEquip {

    public WraqBow(Properties properties) {
        super(properties);
        Utils.bowTag.put(this, 1d);
        if (!(this instanceof ManageEquip) && !(this instanceof RandomLootEquip)) {
            Display.bowList.add(this);
        }
    }

    @Override
    public Component getType() {
        return Component.literal("长弓").withStyle(CustomStyle.styleOfFlexible);
    }

    public void shoot(Player player, double rate, boolean mainShoot, boolean certainlyCritical, boolean noGravity,
                      MyArrowHitBlock myArrowHitBlock, MyArrowHitBlock myArrowHitBlockEntity) {
        rate += BowSkillTree.skillIndex14(player);
        MyArrow myArrow = summonArrow(player, rate);
        myArrow.mainShoot = mainShoot;
        myArrow.certainlyCritical = certainlyCritical;
        if (myArrowHitBlock != null) {
            myArrow.myArrowHitBlock = myArrowHitBlock;
        }
        if (myArrowHitBlockEntity != null) {
            myArrow.myArrowHitBlockEntity = myArrowHitBlockEntity;
        }
        if (BowCurios5.isOn(player)) {
            noGravity = true;
        }
        myArrow.setNoGravity(noGravity);
        if (mainShoot) {
            OnShootArrowCurios.shoot(player);
            OnShootArrowEquip.shoot(player);
            Damage.onPlayerReleaseNormalAttack(player);
            WraqQuiver.shootQuiverExArrow(player);

            MyArrowHitBlock myArrowHit = BowNewSkillBase2_0.nextHitEffectMap.getOrDefault(Name.get(player), null);
            if (myArrowHit != null) {
                myArrow.myArrowHitBlock = myArrowHit;
                myArrow.myArrowHitBlockEntity = myArrowHit;
                BowNewSkillBase2_0.nextHitEffectMap.remove(Name.get(player));
            }
        }
    }

    public void shoot(Player player, double rate, boolean mainShoot,
                      boolean certainlyCritical, boolean noGravity) {
        shoot(player, rate, mainShoot, certainlyCritical, noGravity, null, null);
    }

    public void shoot(Player player, double rate, boolean mainShoot) {
        shoot(player, rate, mainShoot, false, false);
    }

    public void shoot(Player player, double rate, boolean mainShoot,
                      MyArrowHitBlock myArrowHitBlock, MyArrowHitBlock myArrowHitBlockEntity) {
        shoot(player, rate, mainShoot, false, false, myArrowHitBlock, myArrowHitBlockEntity);
    }

    protected float getArrowSpeed() {
        return 3;
    }

    protected boolean hasGravity() {
        return true;
    }

    protected MyArrow summonArrow(Player player, double rate) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, player.level(), player, true, rate);
        arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f,
                getArrowSpeed() + PlayerAttributes.getArrowExFlySpeed(player), 1.0f);
        arrow.setCritArrow(true);
        arrow.setNoGravity(hasGravity());
        player.level().addFreshEntity(arrow);
        MySound.soundToNearPlayer(player, SoundEvents.ARROW_SHOOT);
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

            ParticleProvider.createLineParticle(player.level(), (int) NearestMob.distanceTo(player),
                    player.pick(0.5, 0, false).getLocation(), NearestMob.position().add(0, 1, 0), ParticleTypes.SNOWFLAKE);
        }
    }

    public static void playShootAnimationAndHandleTrig(Player player) {
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.bowAttack, 8, 10, player, 1
        ) {
            @Override
            public void trig() {
                Item bow = player.getMainHandItem().getItem();
                if (!Utils.bowTag.containsKey(bow)) {
                    return;
                }
                if (bow instanceof WraqBow wraqBow) {
                    wraqBow.shoot(player, 1, true);
                }
            }
        });
    }
}
