package fun.wraq.events.core;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.core.ManaAttackModule;
import fun.wraq.core.MyArrow;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.ParticlePackets.ManaAttackParticleS2CPacket;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.projectiles.mana.NewArrow;
import fun.wraq.projectiles.mana.swordair.SwordAir;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber
public class BowEvent {

    public static Map<Entity, Set<Integer>> causeDamageList = new HashMap<>();

    public static void handleServerTick() {
        List<Entity> removeList = new ArrayList<>();
        causeDamageList.forEach((k, v) -> {
            if (k.isRemoved()) removeList.add(k);
        });
        removeList.forEach(e -> causeDamageList.remove(e));
    }

    @SubscribeEvent
    public static void Projectile(ProjectileImpactEvent event) {
        Entity entity = event.getProjectile();
        Level level = event.getEntity().level();
        List<Entity> list = level.getEntitiesOfClass(Entity.class, entity.getBoundingBox().expandTowards(entity.getDeltaMovement()).inflate(1.0D));
        List<LivingEntity> list0 = list.stream()
                .filter(entity1 -> entity1 instanceof LivingEntity livingEntity && livingEntity.isAlive())
                .map(entity1 -> (LivingEntity) entity1)
                .toList();

        if (!list.isEmpty() && (list.get(0) instanceof ArmorStand || list.get(0) instanceof Allay)) {
            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
            return;
        }

        if (!entity.level().isClientSide && entity instanceof MyArrow myArrow) {
            if (!list0.isEmpty()) {
                if (myArrow.player != null) {
                    Player player = myArrow.player;
                    if (Compute.hasCurios(player, ModItems.END_CURIOS_BOW.get())) {
                        List<Mob> mobList = new ArrayList<>();
                        Vec3 vec = myArrow.getDeltaMovement().normalize();
                        for (int i = 0; i < 20; i++) {
                            Vec3 pos = myArrow.position().add(vec.scale(0.25 * i));
                            List<Mob> list1 = myArrow.level().getEntitiesOfClass(Mob.class,
                                    AABB.ofSize(pos, 0.75, 0.75, 0.75));
                            list1.forEach(mob -> {
                                if (!mobList.contains(mob)) mobList.add(mob);
                            });
                        }
                        mobList.forEach(mob -> {
                            if (!causeDamageList.containsKey(myArrow))
                                causeDamageList.put(myArrow, new HashSet<>());
                            Set<Integer> causedList = causeDamageList.get(myArrow);
                            if (!causedList.contains(mob.getId())) {
                                MyArrow.causeDamage(myArrow, mob, 1 + causedList.size() * 0.33);
                                causedList.add(mob.getId());
                            }
                            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
                        });
                    } else {
                        Vec3 vec = myArrow.getDeltaMovement().normalize();
                        double distance = 100;
                        Mob nearestMob = null;
                        for (int i = 0; i < 20; i++) {
                            Vec3 pos = myArrow.position().add(vec.scale(0.25 * i));
                            List<Mob> mobList = myArrow.level().getEntitiesOfClass(Mob.class,
                                    AABB.ofSize(pos, 0.75, 0.75, 0.75));
                            for (Mob mob : mobList) {
                                if (mob.getEyePosition().distanceTo(pos) < distance) {
                                    distance = mob.getEyePosition().distanceTo(pos);
                                    nearestMob = mob;
                                }
                            }
                        }
                        if (nearestMob != null) {
                            MyArrow.causeDamage(myArrow, nearestMob, 1);
                            myArrow.remove(Entity.RemovalReason.KILLED);
                        } else {
                            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
                        }
                    }
                }
            }
        }

        if (!entity.level().isClientSide && entity instanceof ManaArrow manaArrow && !manaArrow.shootFromMob()) {
            if (!list0.isEmpty()) {
                if (manaArrow.player != null) {
                    Player player = manaArrow.player;
                    if (Compute.hasCurios(player, ModItems.END_CURIOS_MANA.get())) {
                        List<Mob> mobList = new ArrayList<>();
                        Vec3 vec = manaArrow.getDeltaMovement().normalize();
                        for (int i = 0; i < 20; i++) {
                            Vec3 pos = manaArrow.position().add(vec.scale(0.25 * i));
                            List<Mob> list1 = manaArrow.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos, 0.75, 0.75, 0.75));
                            list1.forEach(mob -> {
                                if (!mobList.contains(mob)) mobList.add(mob);
                            });
                        }
                        mobList.forEach(mob -> {
                            if (!causeDamageList.containsKey(manaArrow))
                                causeDamageList.put(manaArrow, new HashSet<>());
                            Set<Integer> causedList = causeDamageList.get(manaArrow);
                            if (!causedList.contains(mob.getId())) {
                                ManaAttackModule.BasicAttack(manaArrow.player, mob, (1 + causedList.size() * 0.33),
                                        manaArrow.BreakDefence, manaArrow.BreakDefence0, manaArrow.level(), manaArrow, manaArrow.mainShoot);
                                causedList.add(mob.getId());
                            }
                            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
                        });
                    } else {
                        Vec3 vec = manaArrow.getDeltaMovement().normalize();
                        double distance = 100;
                        Mob nearestMob = null;
                        for (int i = 0; i < 20; i++) {
                            Vec3 pos = manaArrow.position().add(vec.scale(0.25 * i));
                            List<Mob> mobList = manaArrow.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos, 0.75, 0.75, 0.75));
                            for (Mob mob : mobList) {
                                if (mob.getEyePosition().distanceTo(pos) < distance) {
                                    distance = mob.getEyePosition().distanceTo(pos);
                                    nearestMob = mob;
                                }
                            }
                        }
                        if (nearestMob != null && manaArrow.player != null) {
                            ManaAttackModule.BasicAttack(manaArrow.player, nearestMob, 1,
                                    manaArrow.BreakDefence, manaArrow.BreakDefence0, manaArrow.level(), manaArrow, manaArrow.mainShoot);
                            ModNetworking.sendToClient(new ManaAttackParticleS2CPacket(nearestMob.getX(), nearestMob.getY(), nearestMob.getZ(), manaArrow.type), (ServerPlayer) manaArrow.player);
                            manaArrow.remove(Entity.RemovalReason.KILLED);
                        } else {
                            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
                        }
                    }
                }
            }
        }

        if (!entity.level().isClientSide && entity instanceof NewArrow newArrow) {
            if (!list0.isEmpty()) {
                if (newArrow.player != null) {
                    Player player = newArrow.player;
                    if (Compute.hasCurios(player, ModItems.END_CURIOS_MANA.get())) {
                        List<Mob> mobList = new ArrayList<>();
                        Vec3 vec = newArrow.getDeltaMovement().normalize();
                        for (int i = 0; i < 20; i++) {
                            Vec3 pos = newArrow.position().add(vec.scale(0.25 * i));
                            List<Mob> list1 = newArrow.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos, 0.75, 0.75, 0.75));
                            list1.forEach(mob -> {
                                if (!mobList.contains(mob)) mobList.add(mob);
                            });
                        }
                        mobList.forEach(mob -> {
                            if (!causeDamageList.containsKey(newArrow))
                                causeDamageList.put(newArrow, new HashSet<>());
                            Set<Integer> causedList = causeDamageList.get(newArrow);
                            if (!causedList.contains(mob.getId())) {
                                ManaAttackModule.BasicAttack(newArrow.player, mob, (1 + causedList.size() * 0.33),
                                        newArrow.BreakDefence, newArrow.BreakDefence0, newArrow.level(), newArrow, newArrow.mainShoot);
                                causedList.add(mob.getId());
                            }
                            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);

                        });
                    } else {
                        Vec3 vec = newArrow.getDeltaMovement().normalize();
                        double distance = 100;
                        Mob nearestMob = null;
                        for (int i = 0; i < 20; i++) {
                            Vec3 pos = newArrow.position().add(vec.scale(0.25 * i));
                            List<Mob> mobList = newArrow.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos, 0.75, 0.75, 0.75));
                            for (Mob mob : mobList) {
                                if (mob.getEyePosition().distanceTo(pos) < distance) {
                                    distance = mob.getEyePosition().distanceTo(pos);
                                    nearestMob = mob;
                                }
                            }
                        }
                        if (nearestMob != null && newArrow.player != null) {
                            ManaAttackModule.BasicAttack(newArrow.player, nearestMob, 1,
                                    newArrow.BreakDefence, newArrow.BreakDefence0, newArrow.level(), newArrow, newArrow.mainShoot);
                            newArrow.remove(Entity.RemovalReason.KILLED);
                        } else {
                            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
                        }
                    }
                }
            }
        }

        if (entity instanceof SwordAir swordAir) {
            if (swordAir.player != null) {
                if (!causeDamageList.containsKey(entity)) {
                    causeDamageList.put(entity, new HashSet<>());
                }
                Set<Integer> causedEntitySet = causeDamageList.get(entity);
                list.stream()
                        .filter(e -> e instanceof Mob)
                        .map(e -> (Mob) e)
                        .forEach(mob -> {
                            if (!causedEntitySet.contains(mob.getId())) {
                                causedEntitySet.add(mob.getId());
                                swordAir.onHitEntity(mob);
                            }
                        });
            }
            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
        }
    }

    @SubscribeEvent
    public static void Bow(ArrowLooseEvent event) {
        if (!event.getLevel().isClientSide) {
            event.setCanceled(true);
        }
    }
}
