package com.very.wraq.events.instance;

import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.series.castle.CastleCurios;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Struct.Instance;
import com.very.wraq.common.Utils.Struct.PlayerTeam;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.util.*;

public class Castle {
    public static boolean BossMSGFlag = false;
    public static boolean BossFlag = false;
    public static Vec3 BossPos = new Vec3(853, 78, 1034);
    public static List<Mob> BossList = new ArrayList<>();
    public static List<Player> CauseDamagePlayer = new ArrayList<>();
    public static Map<Integer, List<Mob>> ZoneMobMap = new HashMap<>();
    public static Map<Integer, Boolean> ZoneMobFlag = new HashMap<>();
    public static Map<Integer, Boolean> ZoneClearFlag = new HashMap<>();
    public static Map<Integer, Vec3> ZonePosMap = new HashMap<>() {{
        put(0, new Vec3(833, 78, 1001));
        put(1, new Vec3(816, 78, 1001));
        put(2, new Vec3(798, 78, 1017));
        put(3, new Vec3(765, 78, 1019));
        put(4, new Vec3(766, 78, 1037));
        put(5, new Vec3(789, 78, 1057));
        put(6, new Vec3(798, 78, 1039));
        put(7, new Vec3(824, 78, 1060));
    }};

    public static int ZoneNum = 8;

    public static void ZoneDataInit() {
        for (int i = 0; i < ZoneNum; i++) {
            if (ZoneMobMap.containsKey(i)) {
                List<Mob> list = ZoneMobMap.get(i);
                list.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
                ZoneMobMap.get(i).clear();
            } else ZoneMobMap.put(i, new ArrayList<>());
            ZoneMobFlag.put(i, true);
            ZoneClearFlag.put(i, false);
        }
        BossFlag = true;
        BossMSGFlag = true;
        BossList.clear();
    }

    public static void DetectPlayerNearZone(Player player) {
        ZonePosMap.forEach((integer, vec3) -> {
            if (player.position().distanceTo(vec3) < 6) {
                if (ZoneMobFlag.get(integer)) {
                    ZoneMobFlag.put(integer, false);
                    ZoneMobSummon(integer, player.level());
                }
            }
        });
    }

    public static boolean AllZoneIsClear(List<Player> playerList) {
        boolean flag = true;
        for (int i = 0; i < ZoneNum; i++) {
            if (!DetectZoneIsClear(i, playerList)) flag = false;
        }
        return flag;
    }

    public static int ClearZoneNum() {
        int Count = 0;
        for (int i = 0; i < ZoneNum; i++) {
            if (ZoneClearFlag.get(i)) Count++;
        }
        return Count;
    }

    public static boolean DetectZoneIsClear(int index, List<Player> playerList) {
        if (!ZoneMobFlag.get(index) && DetectZoneMobClear(index) && !ZoneClearFlag.get(index)) {
            ZoneClearFlag.put(index, true);
            playerList.forEach(player -> {
                Compute.sendFormatMSG(player, Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle),
                        Component.literal("第" + (index + 1) + "间怪物已清除！").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("(" + ClearZoneNum() + "/" + "8)").withStyle(ChatFormatting.AQUA)));
                ModNetworking.sendToClient(new SoundsS2CPacket(6), (ServerPlayer) player);
            }); // 清理完毕通知
        }
        return !ZoneMobFlag.get(index) && DetectZoneMobClear(index);
    }

    public static boolean DetectZoneMobClear(int index) {
        List<Mob> list = ZoneMobMap.get(index);
        for (Mob mob : list) {
            if (mob.isAlive()) return false;
        }
        return true;
    }

    public static void SummonNorthMon(List<Mob> mobList, Vec3 pos, Level level) {
        Random random = new Random();
        for (int i = 0; i < 4; i++) mobList.add(new WitherSkeleton(EntityType.WITHER_SKELETON, level));
        mobList.forEach(mob -> {
            Compute.SetMobCustomName(mob, ModItems.MobArmorBlackCastleOneFloorManaHelmet.get(),
                    Component.literal("暗黑城堡遗魂 - 术士").withStyle(CustomStyle.styleOfCastle));

            mob.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorBlackCastleOneFloorManaHelmet.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorBlackCastleOneFloorChest.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorBlackCastleOneFloorLeggings.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorBlackCastleOneFloorBoots.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.MAINHAND, ModItems.CastleSceptre.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.OFFHAND, ModItems.WitherBook.get().getDefaultInstance());

            mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(5 * Math.pow(10, 6));
            mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
            mob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10000);
            mob.setHealth(mob.getMaxHealth());
            mob.moveTo(pos.x + random.nextInt(6) - 3, pos.y, pos.z + random.nextInt(6) - 3);
            level.addFreshEntity(mob);
        });
    } // 鼓励法师

    public static void SummonSouthMon(List<Mob> mobList, Vec3 pos, Level level) {
        Random random = new Random();
        for (int i = 0; i < 1; i++) mobList.add(new WitherSkeleton(EntityType.WITHER_SKELETON, level));
        mobList.forEach(mob -> {
            Compute.SetMobCustomName(mob, ModItems.MobArmorBlackCastleOneFloorAttackHelmet.get(), Component.literal("暗黑城堡遗魂 - 骑士").withStyle(CustomStyle.styleOfCastle));

            mob.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorBlackCastleOneFloorAttackHelmet.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorBlackCastleOneFloorChest.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorBlackCastleOneFloorLeggings.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorBlackCastleOneFloorBoots.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.MAINHAND, ModItems.CastleSword.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.OFFHAND, ModItems.MineShield.get().getDefaultInstance());

            mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(5 * Math.pow(10, 6));
            mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
            mob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10000);
            mob.setHealth(mob.getMaxHealth());
            mob.moveTo(pos.x + random.nextInt(6) - 3, pos.y, pos.z + random.nextInt(6) - 3);
            level.addFreshEntity(mob);
        });
    } //鼓励物理

    public static void ZoneMobSummon(int index, Level level) {
        List<Mob> mobList = ZoneMobMap.get(index);
        Vec3 pos = ZonePosMap.get(index);
        switch (index) {
            case 0, 1, 2, 3 -> {
                SummonNorthMon(mobList, pos, level);
            }

            // 以上为北部四间 鼓励法师AOE
            // 以下为南部四间 鼓励近战与弓箭手单挑

            case 4, 5, 6, 7 -> {
                SummonSouthMon(mobList, pos, level);
            }
        }
    } // 怪物生成

    public static void BossMSGSend(List<Player> playerList) {
        if (!BossMSGFlag) return;
        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                new ClientboundSetTitleTextPacket(Component.literal("敌方增援马上赶到").withStyle(CustomStyle.styleOfCastle));
        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                new ClientboundSetSubtitleTextPacket(Component.literal("前往城堡大门接战！").withStyle(CustomStyle.styleOfCastle));
        playerList.forEach(player -> {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.connection.send(clientboundSetTitleTextPacket);
            serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
        });
        BossMSGFlag = false;
    }

    public static boolean DetectPlayerNearBossPos(List<Player> playerList) {
        for (Player player : playerList) {
            if (player.position().distanceTo(BossPos) < 15) return true;
        }
        return false;
    }

    public static void lightningSummon(Mob mob) {
        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, mob.level());
        lightningBolt.moveTo(mob.position());
        lightningBolt.setVisualOnly(true);
        mob.level().addFreshEntity(lightningBolt);
    }

    public static void SummonBoss(List<Player> playerList, Level level) {
        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                new ClientboundSetTitleTextPacket(Component.literal("敌方增援已经赶到").withStyle(CustomStyle.styleOfCastle));
        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                new ClientboundSetSubtitleTextPacket(Component.literal("前往城堡大门迎战！").withStyle(CustomStyle.styleOfCastle));
        playerList.forEach(player -> {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.connection.send(clientboundSetTitleTextPacket);
            serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
        });

        Random random = new Random();
        for (int i = 0; i < 6; i++) BossList.add(new Zombie(EntityType.ZOMBIE, level));

        for (int i = 0; i < 4; i++) {
            Mob mob = BossList.get(i);
            lightningSummon(mob);
            Compute.SetMobCustomName(mob, ModItems.MobArmorBlackCastleOneFloorManaHelmet.get(), Component.literal("暗黑城堡禁军 - 护卫").withStyle(CustomStyle.styleOfCastle));

            mob.setBaby(true);
            mob.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorBlackCastleOneFloorManaHelmet.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorBlackCastleOneFloorChest.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorBlackCastleOneFloorLeggings.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorBlackCastleOneFloorBoots.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.MAINHAND, ModItems.CastleSword.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.OFFHAND, ModItems.MineShield.get().getDefaultInstance());

            mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(5 * Math.pow(10, 6));
            mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
            mob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10000);
            mob.setHealth(mob.getMaxHealth());
            mob.moveTo(BossPos.x + random.nextInt(6) - 3, BossPos.y, BossPos.z + random.nextInt(6) - 3);
            level.addFreshEntity(mob);
        }

        for (int i = 4; i < 6; i++) {
            Mob mob = BossList.get(i);
            lightningSummon(mob);
            Compute.SetMobCustomName(mob, ModItems.MobArmorBlackCastleOneFloorAttackHelmet.get(), Component.literal("暗黑城堡禁军 - 统领").withStyle(CustomStyle.styleOfCastle));
            mob.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorBlackCastleOneFloorAttackHelmet.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorBlackCastleOneFloorChest.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorBlackCastleOneFloorLeggings.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorBlackCastleOneFloorBoots.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.MAINHAND, ModItems.CastleSword.get().getDefaultInstance());
            mob.setItemSlot(EquipmentSlot.OFFHAND, ModItems.MineShield.get().getDefaultInstance());

            mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(5 * Math.pow(10, 7));
            mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
            mob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10000);
            mob.setHealth(mob.getMaxHealth());
            mob.moveTo(BossPos.x + random.nextInt(6) - 3, BossPos.y, BossPos.z + random.nextInt(6) - 3);
            level.addFreshEntity(mob);
        }
    }

    public static boolean DetectBossIsClear() {
        for (Mob mob : BossList) {
            if (mob.isAlive()) return false;
        }
        return true;
    }

    public static List<Player> getCastlePlayerList(Level level) {
        if (Utils.instanceList.get(9).isChallenge()) {
            Instance instance = Utils.instanceList.get(9);
            PlayerTeam playerTeam = instance.getCurrentChallengePlayerTeam();
            List<Player> playerList = playerTeam.getPlayerList();
            List<Player> playerListGetByName = new ArrayList<>();
            playerList.forEach(player -> {
                if (level.getServer().getPlayerList().getPlayerByName(player.getName().getString()) != null)
                    playerListGetByName.add(level.getServer().getPlayerList().getPlayerByName(player.getName().getString()));
            });
            return playerListGetByName;
        } else return null;
    }

    public static void CauseDamageRecord(Player player, Mob mob) {
        if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorBlackCastleOneFloorAttackHelmet.get())
                || mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorBlackCastleOneFloorManaHelmet.get())) {
            CauseDamagePlayer.add(player);
        }
    } // 需要有造成过伤害的玩家

    public static void CastleEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {

            int InstanceIndex = 9;
            if (Utils.instanceList.get(InstanceIndex).isChallenge()) {
                Instance instance = Utils.instanceList.get(InstanceIndex);
                MutableComponent difficulty = Component.literal("");
                switch (instance.getDifficulty()) {
                    case 0 -> difficulty = Component.literal("一般").withStyle(ChatFormatting.GREEN);
                    case 1 -> difficulty = Component.literal("困难").withStyle(ChatFormatting.LIGHT_PURPLE);
                    case 2 -> difficulty = Component.literal("炼狱").withStyle(ChatFormatting.RED);
                }

                instance.addTick();
                int instanceTick = instance.getTick();
                PlayerTeam playerTeam = instance.getCurrentChallengePlayerTeam();
                List<Player> playerList = playerTeam.getPlayerList();
                List<Player> playerListGetByName = new ArrayList<>();
                Level level = event.level;
                int[] difficultyEnhance = {1, 2, 4};
                int difficultyEnhanceRate = difficultyEnhance[instance.getDifficulty()];
                playerList.forEach(player -> {
                    if (event.level.getServer().getPlayerList().getPlayerByName(player.getName().getString()) != null)
                        playerListGetByName.add(event.level.getServer().getPlayerList().getPlayerByName(player.getName().getString()));
                });
                int playerNum = playerListGetByName.size();
                int startTick = 400;
                if (instanceTick == startTick) ZoneDataInit(); // 数据初始化

                if (instanceTick > startTick) {
                    playerListGetByName.forEach(Castle::DetectPlayerNearZone); // 检测玩家是否在附近 并刷新怪物

                    // 以下已包含各种检测
                    if (AllZoneIsClear(playerListGetByName)) {
                        BossMSGSend(playerListGetByName);
                        if (DetectPlayerNearBossPos(playerListGetByName) && BossFlag) {
                            BossFlag = false;
                            SummonBoss(playerListGetByName, level);
                        }
                        if (!BossFlag && DetectBossIsClear())
                            Reward(playerListGetByName, instance, playerNum, difficultyEnhanceRate, level, playerTeam, instanceTick, startTick, difficulty);
                        // 奖励

                    }
                } // 挑战开始环节


                // 挑战前提示
                if (instanceTick <= startTick) {
                    if (instanceTick % 20 == 0) {
                        if (instanceTick == startTick) {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("暗黑城堡 - 1层").withStyle(CustomStyle.styleOfCastle));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("在十分钟内清理所有怪物!").withStyle(ChatFormatting.RED));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        } else {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("暗黑城堡 - 1层").withStyle(CustomStyle.styleOfCastle));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("做好准备，离副本开始还有 " + (startTick / 20 - instanceTick / 20) + " 秒!"));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        }

                        if (instanceTick < startTick) {
                            List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(811, 78, 1031), 150, 150, 150));
                            list.forEach(mob -> {
                                if (!mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorCastleKnightHelmet.get()))
                                    mob.remove(Entity.RemovalReason.KILLED);
                            });
                        }

                    }
                }

                // 失败
                if (instanceTick == 12200 || playerListGetByName.size() == 0 || instance.getDeadTimes() >= playerListGetByName.size()) {
                    Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍：" + playerTeam.getTeamName()).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("挑战副本:").withStyle(ChatFormatting.WHITE)).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty).
                                    append(Component.literal(" 失败。").withStyle(ChatFormatting.WHITE)));
                    instance.reset();
                    Compute.EndTp(playerListGetByName, instance.getTeleportPosition());
                    CauseDamagePlayer.clear();
                }
            }
        }
    }

    public static void Reward(List<Player> playerListGetByName, Instance instance, int playerNum,
                              int difficultyEnhanceRate, Level level, PlayerTeam playerTeam,
                              int instanceTick, int startTick, Component difficulty) {

        playerListGetByName.forEach(player -> {
            SingleRewardToPlayer(player, difficultyEnhanceRate, playerNum, false);
        });

        Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(playerTeam.getTeamName() + " 用时:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(String.format("%.2f", (instanceTick - startTick) * 0.05) + "s ").withStyle(CustomStyle.styleOfBloodMana)).
                        append(Component.literal("成功挑战了 ").withStyle(ChatFormatting.WHITE)).
                        append(instance.getInstanceName()).
                        append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                        append(difficulty));

        playerListGetByName.forEach(player -> {
            CompoundTag data = player.getPersistentData();
            data.putInt(StringUtils.InstanceTimes.BlackCastle1, data.getInt(StringUtils.InstanceTimes.BlackCastle1) + 1);
        });

        instance.reset();
        Compute.EndTp(playerListGetByName, instance.getTeleportPosition());
        CauseDamagePlayer.clear();
    }

    public static void SingleRewardToPlayer(Player player, int difficultyEnhanceRate, int playerNum, boolean isMopUp) {
        Random random = new Random();
        Level level = player.level();
        if (CauseDamagePlayer.contains(player) || isMopUp) {
            Compute.itemStackGive(player, new ItemStack(ModItems.CastleLoot.get(), difficultyEnhanceRate));

            if (random.nextDouble() <= 0.1 && difficultyEnhanceRate == 4) {
                ItemStack itemStack = ModItems.CastleNecklace.get().getDefaultInstance();
                CastleCurios.randomAttributeProvide(itemStack, 6, 1);
                CastleCurios.RandomPassiveProvide(itemStack);
                Compute.formatBroad(level, Component.literal("黑金").withStyle(CustomStyle.styleOfCastle),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 获得了: ").withStyle(ChatFormatting.WHITE)).
                                append(itemStack.getDisplayName()));
                Compute.itemStackGive(player, itemStack);
            }

            if (!isMopUp) {
                if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                    Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                    append(ModItems.CastleLoot.get().getDefaultInstance().getDisplayName()));
                    Compute.itemStackGive(player, new ItemStack(ModItems.CastleLoot.get(), 2));
                }
            }
        }

        if (LoginInEvent.playerDailyInstanceReward(player, 9)) {
            Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                            append(ModItems.CastleLoot.get().getDefaultInstance().getDisplayName()));
            Compute.itemStackGive(player, new ItemStack(ModItems.CastleLoot.get(), 24));
        }
    }
}












