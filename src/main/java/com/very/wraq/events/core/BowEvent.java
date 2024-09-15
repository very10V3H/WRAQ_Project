package com.very.wraq.events.core;

import com.very.wraq.common.Compute;
import com.very.wraq.core.ManaAttackModule;
import com.very.wraq.core.MyArrow;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.ParticlePackets.ManaAttackParticleS2CPacket;
import com.very.wraq.process.func.damage.Damage;
import com.very.wraq.projectiles.mana.BlazeSword;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.projectiles.mana.NewArrow;
import com.very.wraq.projectiles.mana.SwordAir;
import com.very.wraq.series.end.curios.EndCurios;
import com.very.wraq.series.end.curios.EndCurios1;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber
public class BowEvent {

    public static WeakHashMap<Entity, List<Integer>> causeDamageList = new WeakHashMap<>();

    @SubscribeEvent
    public static void Projectile(ProjectileImpactEvent event) {
        Entity entity = event.getProjectile();
        Level level = event.getEntity().level();
        List<Entity> list = level.getEntitiesOfClass(Entity.class, entity.getBoundingBox().expandTowards(entity.getDeltaMovement()).inflate(1.0D));

        if (!entity.level().isClientSide && entity instanceof BlazeSword blazeSword && blazeSword.player != null) {
            List<Entity> list0 = blazeSword.level().getEntitiesOfClass(Entity.class, blazeSword.getBoundingBox().expandTowards(blazeSword.getDeltaMovement()).inflate(1.0D));
            if (!list0.isEmpty()) {
                Vec3 vec = blazeSword.getDeltaMovement().normalize();
                double distance = 100;
                Mob nearestMob = null;
                for (int i = 0; i < 20; i++) {
                    Vec3 pos = blazeSword.position().add(vec.scale(0.25 * i));
                    List<Mob> mobList = blazeSword.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos, 1.5, 1.5, 1.5));
                    for (Mob mob : mobList) {
                        if (mob.getEyePosition().distanceTo(pos) < distance) {
                            distance = mob.getEyePosition().distanceTo(pos);
                            nearestMob = mob;
                        }
                    }
                }
                if (nearestMob != null) {
                    Damage.causeManaDamageToMonster_ApDamage(blazeSword.player, nearestMob, Compute.getXpStrengthAPDamage(blazeSword.player, 0.5));
                    blazeSword.remove(Entity.RemovalReason.KILLED);
                } else {
                    event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
                }
            }

        }

        if (!entity.level().isClientSide && entity instanceof MyArrow myArrow) {
            List<Entity> list0 = myArrow.level().getEntitiesOfClass(Entity.class, myArrow.getBoundingBox().expandTowards(myArrow.getDeltaMovement()).inflate(1.0D));
            if (!list0.isEmpty()) {
                if (myArrow.player != null) {
                    Player player = myArrow.player;
                    if (EndCurios.IsOn(player)) {
                        List<Mob> mobList = new ArrayList<>();
                        Vec3 vec = myArrow.getDeltaMovement().normalize();
                        for (int i = 0; i < 20; i++) {
                            Vec3 pos = myArrow.position().add(vec.scale(0.25 * i));
                            List<Mob> list1 = myArrow.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos, 0.75, 0.75, 0.75));
                            list1.forEach(mob -> {
                                if (!mobList.contains(mob)) mobList.add(mob);
                            });
                        }
                        mobList.forEach(mob -> {
                            if (!causeDamageList.containsKey(myArrow))
                                causeDamageList.put(myArrow, new ArrayList<>());
                            List<Integer> causedList = causeDamageList.get(myArrow);
                            if (!causedList.contains(mob.getId())) {
                                MyArrow.CauseDamage(myArrow, mob, myArrow.BaseDamage * (1 + causedList.size() * 0.33));
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
                            List<Mob> mobList = myArrow.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos, 0.75, 0.75, 0.75));
                            for (Mob mob : mobList) {
                                if (mob.getEyePosition().distanceTo(pos) < distance) {
                                    distance = mob.getEyePosition().distanceTo(pos);
                                    nearestMob = mob;
                                }
                            }
                        }
                        if (nearestMob != null) {
                            MyArrow.CauseDamage(myArrow, nearestMob, myArrow.BaseDamage);
                            myArrow.remove(Entity.RemovalReason.KILLED);
                        } else {
                            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
                        }
                    }
                }
            }
        }

        if (!entity.level().isClientSide && entity instanceof ManaArrow manaArrow && !manaArrow.shootFromMob()) {
            List<Entity> list0 = manaArrow.level().getEntitiesOfClass(Entity.class, manaArrow.getBoundingBox().expandTowards(manaArrow.getDeltaMovement()).inflate(1.0D));
            if (!list0.isEmpty()) {
                if (manaArrow.player != null) {
                    Player player = manaArrow.player;
                    if (EndCurios1.IsOn(player)) {
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
                                causeDamageList.put(manaArrow, new ArrayList<>());
                            List<Integer> causedList = causeDamageList.get(manaArrow);
                            if (!causedList.contains(mob.getId())) {
                                ManaAttackModule.BasicAttack(manaArrow.player, mob, manaArrow.BaseDamage * (1 + causedList.size() * 0.33),
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
                            ManaAttackModule.BasicAttack(manaArrow.player, nearestMob, manaArrow.BaseDamage,
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
            List<Entity> list0 = newArrow.level().getEntitiesOfClass(Entity.class, newArrow.getBoundingBox().expandTowards(newArrow.getDeltaMovement()).inflate(1.0D));
            if (!list0.isEmpty()) {
                if (newArrow.player != null) {
                    Player player = newArrow.player;
                    if (EndCurios1.IsOn(player)) {
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
                                causeDamageList.put(newArrow, new ArrayList<>());
                            List<Integer> causedList = causeDamageList.get(newArrow);
                            if (!causedList.contains(mob.getId())) {
                                ManaAttackModule.BasicAttack(newArrow.player, mob, newArrow.BaseDamage * (1 + causedList.size() * 0.33),
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
                            ManaAttackModule.BasicAttack(newArrow.player, nearestMob, newArrow.BaseDamage,
                                    newArrow.BreakDefence, newArrow.BreakDefence0, newArrow.level(), newArrow, newArrow.mainShoot);
                            newArrow.remove(Entity.RemovalReason.KILLED);
                        } else {
                            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
                        }
                    }
                }
            }
        }

        if (!list.isEmpty() && list.get(0) instanceof ArmorStand)
            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
        if (entity instanceof SwordAir) {
            entity.setDeltaMovement(0, 0, 0);
            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
        }
    }

    @SubscribeEvent
    public static void Bow(ArrowLooseEvent event) {
        if (!event.getLevel().isClientSide) {
            event.setCanceled(true);
/*            Player player = event.getEntity();
            
            Level level = event.getLevel();
            ModNetworking.sendToClient(new BowShootS2CPacket(true),(ServerPlayer) player);
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(itemStack.is(ModItems.SkyBow.get()))
            {
                int time = event.getCharge();
                if(time >= 15) time = 15;
                float damage = PlayerAttributes.PlayerAttackDamage(player);
                double CriticalHitRate = PlayerAttributes.PlayerCriticalHitRate(player);
                double CHitDamage = PlayerAttributes.PlayerCriticalHitDamage(player);
                double BreakDefence = PlayerAttributes.PlayerBreakDefence(player);
                float ExpUp = Compute.ExpGetImprove(player);
                double BreakDefence0 = PlayerAttributes.PlayerBreakDefence0(player);
                MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage*((float) time /10),CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND),true);
                if(time == 15)
                {
                    arrow.setCritArrow(true);
                    ModNetworking.sendToClient(new SkyBowShootS2CPacket(true),(ServerPlayer) player);
                }
                
                arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0d,3.0d*((float) time /10),1.0d);
                level.addFreshEntity(arrow);
            }
            else
            {
                if(itemStack.is(ModItems.NetherBow.get()))
                {
                    int time = event.getCharge();
                    if(time >= 30) time = 30;
                    float damage = PlayerAttributes.PlayerAttackDamage(player);
                    double CriticalHitRate = PlayerAttributes.PlayerCriticalHitRate(player);
                    double CHitDamage = PlayerAttributes.PlayerCriticalHitDamage(player);
                    double BreakDefence = PlayerAttributes.PlayerBreakDefence(player);
                    float ExpUp = Compute.ExpGetImprove(player);
                    double BreakDefence0 = PlayerAttributes.PlayerBreakDefence0(player);
                    CompoundTag data = player.getPersistentData();
                    MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage*((float) time /20),CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND),true);
                    if(time == 30 && data.getInt("MANA") >= 40)
                    {
                        arrow.setCritArrow(true);
                        arrow.setNoGravity(true);
                        
                        arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0d,3.0d*((float) time /20),1.0d);
                        arrow.getPersistentData().putBoolean("IsNetherBow",true);
                        arrow.getPersistentData().putDouble("PosX",player.getX());
                        arrow.getPersistentData().putDouble("PosY",player.getY());
                        arrow.getPersistentData().putDouble("PosZ",player.getZ());
                        level.addFreshEntity(arrow);
                        data.putInt("MANA",data.getInt("MANA")-40);
                        Compute.ManaStatusUpdate(player);
                    }
                    else
                    {
                        
                        arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0d,3.0d*((float) time /20),1.0d);
                        level.addFreshEntity(arrow);
                    }
                }
                else
                {
                    int time = event.getCharge();
                    if(time >= 30) time = 30;
                    float damage = PlayerAttributes.PlayerAttackDamage(player);
                    double CriticalHitRate = PlayerAttributes.PlayerCriticalHitRate(player);
                    double CHitDamage = PlayerAttributes.PlayerCriticalHitDamage(player);
                    double BreakDefence = PlayerAttributes.PlayerBreakDefence(player);
                    float ExpUp = Compute.ExpGetImprove(player);
                    double BreakDefence0 = PlayerAttributes.PlayerBreakDefence0(player);
                    MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage*((float) time /20),CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND),true);
                    if(time == 30) arrow.setCritArrow(true);
                    
                    arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0d,3.0d*((float) time /20),1.0d);
                    level.addFreshEntity(arrow);
                }
            }
            event.setCanceled(true);
            player.getPersistentData().putBoolean("arrowflying",true);*/
        }
    }
}
