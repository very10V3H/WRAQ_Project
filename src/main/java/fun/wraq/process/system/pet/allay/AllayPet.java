package fun.wraq.process.system.pet.allay;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.system.pet.allay.skill.AllaySkills;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.*;

public class AllayPet {
    public Allay allay;
    public String ownerName;
    public int xpLevel;

    public AllayPet(Allay allay, String ownerName, int xpLevel) {
        this.allay = allay;
        this.ownerName = ownerName;
        this.xpLevel = xpLevel;
    }

    public static Set<AllayPet> allayPets = new HashSet<>();
    public static Set<Allay> allays = new HashSet<>();
    public static Map<String, AllayPet> playerAllayPetMap = new HashMap<>();
    public static Map<String, Mob> playerIsAttackingMobMap = new HashMap<>();
    public static Map<String, BlockPos> playerLastTickBlockPos = new HashMap<>();
    public static Map<String, Integer> lastMoveTickMap = new HashMap<>();

    public static void handleServerTick() {
        allayPets.stream()
                .filter(allayPet -> Compute.getPlayerByName(allayPet.ownerName) == null)
                .forEach(allayPet -> {
                    allayPet.allay.remove(Entity.RemovalReason.KILLED);
                    playerAllayPetMap.remove(allayPet.ownerName);
                    playerIsAttackingMobMap.remove(allayPet.ownerName);
                });
        allayPets.removeIf(allayPet -> {
            return Compute.getPlayerByName(allayPet.ownerName) == null;
        });

        for (AllayPet allayPet : allayPets) {
            ServerPlayer serverPlayer = Compute.getPlayerByName(allayPet.ownerName);
            if (serverPlayer == null) {
                continue;
            }
            String name = serverPlayer.getName().getString();
            Allay allay = allayPet.allay;
            // 未在同一维度，则将悦灵传送至玩家维度
            if (!serverPlayer.level().dimension().equals(allay.level().dimension())) {
                playerSpawnAllay(serverPlayer);
                continue;
            }
            // 2秒一次治疗
            if (Tick.get() % 40 == 0) {
                AllaySkills.healing(allay, serverPlayer);
            }
            // 判断玩家是否正在攻击存活的怪物，如果是，则协助攻击
            Mob mob = playerIsAttackingMobMap.getOrDefault(allayPet.ownerName, null);
            if (mob != null && mob.isAlive() && mob.distanceTo(serverPlayer) < 32) {
                Compute.EntitySmoothlyMoveToPos(allay, Compute.getLivingEntityFrontOffsetPos(mob));
                // 1秒一次攻击
                if (Tick.get() % 20 == 0) {
                    AllaySkills.tickOfAttack(allay, serverPlayer, mob);
                }
                continue;
            }
            // 判断玩家近距离是否有怪物，如果有，则攻击
            List<? extends Entity> nearEntities = Compute.getNearEntity(serverPlayer, Mob.class, 8);
            List<Mob> nearMobList = nearEntities.stream().filter(entity -> {
                return entity instanceof Mob && !(entity instanceof Allay)
                        && MobSpawn.dropList.containsKey(MobSpawn.getMobOriginName((Mob) entity));
            }).map(entity -> (Mob) entity).toList();
            if (!nearMobList.isEmpty()) {
                Mob nearMob = nearMobList.get(0);
                Compute.EntitySmoothlyMoveToPos(allay, Compute.getLivingEntityBackOffsetPos(nearMob));
                // 1秒一次攻击
                if (Tick.get() % 20 == 0) {
                    AllaySkills.tickOfAttack(allay, serverPlayer, nearMob);
                }
                return;
            }
            // 若玩家正在移动，则保持跟随
            if (playerLastTickBlockPos.containsKey(name)
                    && playerLastTickBlockPos.get(name).getCenter().distanceTo(serverPlayer.blockPosition().getCenter()) > 4) {
                playerLastTickBlockPos.put(name, serverPlayer.blockPosition());
                lastMoveTickMap.put(name, Tick.get() + Tick.s(1));
                return;
            }
            if (lastMoveTickMap.getOrDefault(name, 0) > Tick.get()) {
                Compute.EntitySmoothlyMoveToPos(allay, Compute.getLivingEntityBackOffsetPos(serverPlayer));
                return;
            }
            // 距离玩家超过32格，则传送回玩家位置
            if (allay.distanceTo(serverPlayer) > 32 || allay.isRemoved()) {
                playerSpawnAllay(serverPlayer);
            }
        }
    }

    public static void handleServerPlayerTick(ServerPlayer serverPlayer) {
        if (serverPlayer.tickCount % 20 == 9) {
            Compute.getNearEntity(serverPlayer, Allay.class, 32)
                    .stream().filter(entity -> entity instanceof Allay && !allays.contains(entity))
                    .map(entity -> (Allay) entity)
                    .forEach(allay -> allay.remove(Entity.RemovalReason.KILLED));
        }
    }

    public static void playerSpawnAllay(ServerPlayer serverPlayer) {
        String name = serverPlayer.getName().getString();
        playerRemoveAllay(serverPlayer);

        Level level = serverPlayer.level();
        Allay allay = new Allay(EntityType.ALLAY, level);
        allay.moveTo(Compute.getLivingEntityBackOffsetPos(serverPlayer));
        MobSpawn.setMobCustomName(allay,
                Te.s(AllayPetPlayerData.getAllayName(serverPlayer), CustomStyle.styleOfWorld),
                AllayPetPlayerData.getAllayXpLevel(serverPlayer));

        AllayPet allayPet = new AllayPet(allay, name, serverPlayer.experienceLevel);
        allayPets.add(allayPet);
        allays.add(allay);
        playerAllayPetMap.put(name, allayPet);
        playerLastTickBlockPos.put(name, serverPlayer.blockPosition());
        level.addFreshEntity(allay);
    }

    public static void playerRemoveAllay(ServerPlayer serverPlayer) {
        String name = serverPlayer.getName().getString();
        if (playerAllayPetMap.containsKey(name)) {
            AllayPet allayPet = playerAllayPetMap.get(name);
            allayPet.allay.remove(Entity.RemovalReason.KILLED);
            allayPets.remove(allayPet);
            allays.remove(allayPet.allay);
            playerAllayPetMap.remove(name);
            playerLastTickBlockPos.remove(name);
        }
    }

    public static void onJoinLevel(Allay allay) {
        if (!allays.contains(allay)) {
            allay.remove(Entity.RemovalReason.KILLED);
        }
    }

    public static void onKillMob(Player player, Mob mob) {
        String name = player.getName().getString();
        if (playerAllayPetMap.containsKey(name)) {
            AllayPetPlayerData.giveAllayPercentExp(player, 0.02,
                    MobSpawn.MobBaseAttributes.xpLevel.getOrDefault(MobSpawn.getMobOriginName(mob), 0));
        }
    }

    public static void onServerStop() {
        allayPets.forEach(allayPet -> {
            allayPet.allay.remove(Entity.RemovalReason.KILLED);
        });
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("悦灵", CustomStyle.styleOfWorld), content);
    }
}
