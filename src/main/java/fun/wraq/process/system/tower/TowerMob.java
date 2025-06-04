package fun.wraq.process.system.tower;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class TowerMob {

    public static void summon1FloorMob(Level level, List<Mob> mobList, List<Vec3> posList) {
        for (Vec3 pos : posList) {
            for (int i = 0; i < 3; i++) {
                Zombie zombie = new Zombie(EntityType.ZOMBIE, level);

                Item helmet = ModItems.MOB_ARMOR_TOWER_1_FLOOR_HELMET.get();

                MobSpawn.setMobCustomName(zombie, helmet, Component.literal("本源生机元素").withStyle(CustomStyle.styleOfLife));
                zombie.setItemSlot(EquipmentSlot.HEAD, helmet.getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.CHEST, ModItems.MOB_ARMOR_LIFE_ELEMENT_CHEST.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.LEGS, ModItems.MOB_ARMOR_LIFE_ELEMENT_LEGGINGS.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.FEET, ModItems.MOB_ARMOR_LIFE_ELEMENT_BOOTS.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.MAINHAND, ModItems.LIFE_ELEMENT_SWORD.get().getDefaultInstance());

                MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), 100);
                MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 500, 60, 60,
                        0.35, 3, 0.2, 10, 15, 15000, 0.3);

                zombie.setHealth(zombie.getMaxHealth());

                Random random = new Random();
                zombie.moveTo(pos.add(random.nextDouble(6) - 3, 0, random.nextDouble(6) - 3));
                mobList.add(zombie);
                level.addFreshEntity(zombie);
            }
        }
    }

    public static void summon2FloorMob(Level level, List<Mob> mobList, List<Vec3> posList) {
        for (Vec3 pos : posList) {
            Stray stray = new Stray(EntityType.STRAY, level);

            Item helmet = ModItems.MOB_ARMOR_TOWER_2_FLOOR_HELMET.get();

            MobSpawn.setMobCustomName(stray, helmet, Component.literal("本源碧水元素").withStyle(CustomStyle.styleOfWater));
            stray.setItemSlot(EquipmentSlot.HEAD, helmet.getDefaultInstance());
            stray.setItemSlot(EquipmentSlot.CHEST, ModItems.MOB_ARMOR_WATER_ELEMENT_CHEST.get().getDefaultInstance());
            stray.setItemSlot(EquipmentSlot.LEGS, ModItems.MOB_ARMOR_WATER_ELEMENT_LEGGINGS.get().getDefaultInstance());
            stray.setItemSlot(EquipmentSlot.FEET, ModItems.MOB_ARMOR_WATER_ELEMENT_BOOTS.get().getDefaultInstance());
            stray.setItemSlot(EquipmentSlot.MAINHAND, ModItems.WATER_ELEMENT_SCEPTRE.get().getDefaultInstance());

            MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(stray), 120);
            MobSpawn.MobBaseAttributes.setMobBaseAttributes(stray, 800, 70, 70, 0.4,
                    4, 0.25, 20, 20, 180000, 0.35);

            stray.setHealth(stray.getMaxHealth());

            stray.moveTo(pos);
            mobList.add(stray);
            level.addFreshEntity(stray);
        }
    }

    public static void summon3FloorMob(Level level, List<Mob> mobList, List<Vec3> posList) {
        for (Vec3 pos : posList) {
            for (int i = 0; i < 4; i++) {
                Blaze blaze = new Blaze(EntityType.BLAZE, level);

                Item helmet = ModItems.MOB_ARMOR_TOWER_3_FLOOR_HELMET.get();

                MobSpawn.setMobCustomName(blaze, helmet, Component.literal("本源炽焰元素").withStyle(CustomStyle.styleOfFire));
                blaze.setItemSlot(EquipmentSlot.HEAD, helmet.getDefaultInstance());
                blaze.setItemSlot(EquipmentSlot.CHEST, ModItems.MOB_ARMOR_FIRE_ELEMENT_CHEST.get().getDefaultInstance());
                blaze.setItemSlot(EquipmentSlot.LEGS, ModItems.MOB_ARMOR_FIRE_ELEMENT_LEGGINGS.get().getDefaultInstance());
                blaze.setItemSlot(EquipmentSlot.FEET, ModItems.MOB_ARMOR_FIRE_ELEMENT_BOOTS.get().getDefaultInstance());
                blaze.setItemSlot(EquipmentSlot.MAINHAND, ModItems.FIRE_ELEMENT_SWORD.get().getDefaultInstance());

                MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(blaze), 140);
                MobSpawn.MobBaseAttributes.setMobBaseAttributes(blaze, 1200, 90, 90,
                        0.45, 5, 0.3, 45, 25, 480000, 0.4);

                blaze.setHealth(blaze.getMaxHealth());

                Random random = new Random();
                blaze.moveTo(pos.add(random.nextDouble(6) - 3, 0, random.nextDouble(6) - 3));
                mobList.add(blaze);
                level.addFreshEntity(blaze);
            }
        }
    }

    public static void summon4FloorMob(Level level, List<Mob> mobList, List<Vec3> posList) {
        for (Vec3 pos : posList) {
            Skeleton skeleton = new Skeleton(EntityType.SKELETON, level);

            Item helmet = ModItems.MOB_ARMOR_TOWER_4_FLOOR_HELMET.get();

            MobSpawn.setMobCustomName(skeleton, helmet, Component.literal("本源层岩元素").withStyle(CustomStyle.styleOfStone));
            skeleton.setItemSlot(EquipmentSlot.HEAD, helmet.getDefaultInstance());
            skeleton.setItemSlot(EquipmentSlot.CHEST, ModItems.MOB_ARMOR_STONE_ELEMENT_CHEST.get().getDefaultInstance());
            skeleton.setItemSlot(EquipmentSlot.LEGS, ModItems.MOB_ARMOR_STONE_ELEMENT_LEGGINGS.get().getDefaultInstance());
            skeleton.setItemSlot(EquipmentSlot.FEET, ModItems.MOB_ARMOR_STONE_ELEMENT_BOOTS.get().getDefaultInstance());
            skeleton.setItemSlot(EquipmentSlot.MAINHAND, ModItems.MINE_SWORD_3.get().getDefaultInstance());

            MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(skeleton), 160);
            MobSpawn.MobBaseAttributes.setMobBaseAttributes(skeleton, 2000, 150, 150,
                    0.5, 5, 0.35, 55, 25, 600 * Math.pow(10, 4), 0.45);

            skeleton.setHealth(skeleton.getMaxHealth());

            skeleton.moveTo(pos);
            mobList.add(skeleton);
            level.addFreshEntity(skeleton);
        }
    }

    public static void summon5FloorMob(Level level, List<Mob> mobList, List<Vec3> posList) {
        for (Vec3 pos : posList) {
            for (int i = 0; i < 3; i++) {
                Zombie zombie = new Zombie(EntityType.ZOMBIE, level);

                Item helmet = ModItems.MOB_ARMOR_TOWER_5_FLOOR_HELMET.get();

                MobSpawn.setMobCustomName(zombie, helmet, Component.literal("本源凛冰元素").withStyle(CustomStyle.styleOfIce));
                zombie.setItemSlot(EquipmentSlot.HEAD, helmet.getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.CHEST, ModItems.MOB_ARMOR_ICE_ELEMENT_CHEST.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.LEGS, ModItems.MOB_ARMOR_ICE_ELEMENT_LEGGINGS.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.FEET, ModItems.MOB_ARMOR_ICE_ELEMENT_BOOTS.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.MAINHAND, ModItems.ICE_SWORD.get().getDefaultInstance());

                MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), 180);
                MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 2500, 200, 200,
                        0.5, 5, 0.4, 55, 25, 800 * Math.pow(10, 4), 0.45);

                zombie.setHealth(zombie.getMaxHealth());
                zombie.setBaby(true);

                Random random = new Random();
                zombie.moveTo(pos.add(random.nextDouble(6) - 3, 0, random.nextDouble(6) - 3));
                mobList.add(zombie);
                level.addFreshEntity(zombie);
            }
        }
    }

    public static void summon6FloorMob(Level level, List<Mob> mobList, List<Vec3> posList) {
        for (Vec3 pos : posList) {
            Zombie zombie = new Zombie(EntityType.ZOMBIE, level);

            Item helmet = ModItems.MOB_ARMOR_TOWER_6_FLOOR_HELMET.get();

            MobSpawn.setMobCustomName(zombie, helmet, Component.literal("本源怒雷元素").withStyle(CustomStyle.styleOfLightning));
            zombie.setItemSlot(EquipmentSlot.HEAD, helmet.getDefaultInstance());
            zombie.setItemSlot(EquipmentSlot.CHEST, ModItems.MOB_ARMOR_LIGHTNING_ELEMENT_CHEST.get().getDefaultInstance());
            zombie.setItemSlot(EquipmentSlot.LEGS, ModItems.MOB_ARMOR_LIGHTNING_ELEMENT_LEGGINGS.get().getDefaultInstance());
            zombie.setItemSlot(EquipmentSlot.FEET, ModItems.MOB_ARMOR_LIGHTNING_ELEMENT_BOOTS.get().getDefaultInstance());
            zombie.setItemSlot(EquipmentSlot.MAINHAND, Items.IRON_SWORD.getDefaultInstance());

            MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombie), 200);
            MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 3500, 250, 250,
                    0.5, 5, 0.5, 70, 25, 3000 * Math.pow(10, 4), 0.45);

            zombie.setHealth(zombie.getMaxHealth());

            zombie.moveTo(pos);
            mobList.add(zombie);
            level.addFreshEntity(zombie);
        }
    }

    public static void tickControl1Floor(List<Mob> mobList) {
        for (Mob mob : mobList) {
            if (mob != null && mob.isAlive()) {
                ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) mob.level(), 1, 1, 120, ModParticles.LONG_PLAIN.get(), 2);
                ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) mob.level(), 1.5, 1, 120, ModParticles.LONG_PLAIN.get(), 2);

                List<Player> playerList = mob.level().getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(), 30, 30, 30));
                playerList.removeIf(player -> player.distanceTo(mob) > 9);
                playerList.forEach(player -> Damage.causeManaDamageToPlayer(mob, player, 600));
                mob.heal(3000);
            }
        }
    }

    public static double mobDefenceUp(Mob mob) {
        fun.wraq.process.system.tower.Tower tower = fun.wraq.process.system.tower.Tower.instanceList.get(1);
        if (tower != null && tower.getMobList() != null && tower.getCurrentPlayer() != null) {
            if (tower.getMobList().contains(mob)) return PlayerAttributes.defence(tower.getCurrentPlayer());
        }
        return 0;
    }

    public static double mobManaDefenceUp(Mob mob) {
        fun.wraq.process.system.tower.Tower tower = fun.wraq.process.system.tower.Tower.instanceList.get(1);
        if (tower != null && tower.getMobList() != null && tower.getCurrentPlayer() != null) {
            if (tower.getMobList().contains(mob)) return PlayerAttributes.manaDefence(tower.getCurrentPlayer());
        }
        return 0;
    }

    public static boolean playerIsChallenging2Floor(Player player) {
        fun.wraq.process.system.tower.Tower tower = fun.wraq.process.system.tower.Tower.instanceList.get(1);
        if (tower != null && tower.getCurrentPlayer() != null) {
            return tower.getCurrentPlayer().equals(player);
        }
        return false;
    }

    public static boolean playerIsChallenging3FloorAndInFire(Player player) {
        if (!player.wasOnFire) return false;
        fun.wraq.process.system.tower.Tower tower = fun.wraq.process.system.tower.Tower.instanceList.get(2);
        if (tower != null && tower.getCurrentPlayer() != null) {
            return tower.getCurrentPlayer().equals(player);
        }
        return false;
    }

    public static double playerIsChallenging5FloorDamageDecrease(Player player) {
        fun.wraq.process.system.tower.Tower tower = fun.wraq.process.system.tower.Tower.instanceList.get(4);
        if (tower != null && tower.getCurrentPlayer() != null) {
            if (player.equals(tower.getCurrentPlayer()))
                return (double) (Tick.get() - tower.getStartTime()) / 4800;
        }
        return 0;
    }

    public static void tickControl6Floor(List<Mob> mobList) {
        fun.wraq.process.system.tower.Tower tower = Tower.instanceList.get(5);
        if (tower != null && tower.getCurrentPlayer() != null) {
            Player player = tower.getCurrentPlayer();
            if (player.tickCount % 100 == 0) {
                mobList.forEach(mob -> {
                    if (mob.isAlive()) {
                        Damage.causeTrueDamageToPlayer(mob, player, player.getMaxHealth() * 0.1);
                        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, player.level());
                        lightningBolt.setVisualOnly(true);
                        lightningBolt.moveTo(player.position());
                        player.level().addFreshEntity(lightningBolt);
                    }
                });
            }
        }
    }

    public static void summonMob(int serialNum, Level level, List<Mob> mobList, List<Vec3> posList) {
        switch (serialNum) {
            case 0 -> summon1FloorMob(level, mobList, posList);
            case 1 -> summon2FloorMob(level, mobList, posList);
            case 2 -> summon3FloorMob(level, mobList, posList);
            case 3 -> summon4FloorMob(level, mobList, posList);
            case 4 -> summon5FloorMob(level, mobList, posList);
            case 5 -> summon6FloorMob(level, mobList, posList);
        }
    }
}
