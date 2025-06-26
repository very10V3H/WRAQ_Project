package fun.wraq.events.mob;

import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.teamInstance.instances.blackCastle.NewCastleInstance;
import fun.wraq.series.dragon.SilverDragonBloodWeapon;
import fun.wraq.series.overworld.cold.sc2.stone.StoneSpiderSpawnController;
import fun.wraq.series.overworld.cold.sc4.SuperColdIronGolemSpawnController;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class MobDeadModule {
    public static void deadModule(Mob mob) {
        SilverDragonBloodWeapon.onMobDead(mob);
        StoneSpiderSpawnController.onMobDead(mob);
        SuperColdIronGolemSpawnController.onDead(mob);
        if (mob.getName().getString().contains(NewCastleInstance.mobNameOf1StageMana)) {
            List<Player> playerList = mob.level().getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(), 30, 30, 30));
            playerList.removeIf(LivingEntity::isDeadOrDying);
            Player nearestPlayer = null;
            double distance = Double.MAX_VALUE;
            for (Player player : playerList) {
                if (player.distanceTo(mob) < distance) {
                    nearestPlayer = player;
                    distance = player.distanceTo(mob);
                }
            }
            if (nearestPlayer != null) {
                Damage.causeManaDamageToPlayer(mob, nearestPlayer, 2500);
                ParticleProvider.EntityEffectVerticleCircleParticle(nearestPlayer, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(nearestPlayer, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(nearestPlayer, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(nearestPlayer, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(nearestPlayer, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
        }

        if (mob.getName().getString().contains(NewCastleInstance.mobNameOf1StageAttack)) {
            List<Player> playerList = mob.level().getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(), 30, 30, 30));
            playerList.removeIf(LivingEntity::isDeadOrDying);
            Player nearestPlayer = null;
            double distance = Double.MAX_VALUE;
            for (Player player : playerList) {
                if (player.distanceTo(mob) < distance) {
                    nearestPlayer = player;
                    distance = player.distanceTo(mob);
                }
            }
            if (nearestPlayer != null) {
                Damage.causeAttackDamageToPlayer(mob, nearestPlayer, 5000, 0.3, 750);
                ParticleProvider.EntityEffectVerticleCircleParticle(nearestPlayer, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(nearestPlayer, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(nearestPlayer, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(nearestPlayer, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(nearestPlayer, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
        }
    }
}
