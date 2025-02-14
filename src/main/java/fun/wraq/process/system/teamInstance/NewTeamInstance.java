package fun.wraq.process.system.teamInstance;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.common.util.struct.PlayerTeam;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.reason.Reason;
import fun.wraq.process.system.teamInstance.networking.NewTeamInstanceClearS2CPacket;
import fun.wraq.process.system.teamInstance.networking.NewTeamInstanceJoinedPlayerInfoS2CPacket;
import fun.wraq.process.system.teamInstance.networking.NewTeamInstancePrepareInfoS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public abstract class NewTeamInstance {
    public record ConditionSummonMob(int condition, Mob mob, Vec3 summonPos, double detectRange) {
    } // condition: 0 靠近即生成 1靠近且之前的怪物全部死亡

    public Set<Mob> hasSummonedMobs = new HashSet<>();
    public Set<Mob> hasKilledMobs = new HashSet<>();
    public Set<Player> players = new HashSet<>();
    public List<ConditionSummonMob> mobList = new ArrayList<>();
    public int deadTimes = 0;

    public final int maxChallengeTime;
    public final int minPlayerNum;
    public final int maxPlayerNum;
    public boolean inChallenging;
    public final Vec3 prepareCenterPos;
    public final double prepareDetectRange;
    public final MutableComponent description;
    public final MutableComponent regionDescription;
    public final int levelRequire;
    public int startCount = 0;
    public int lastTick = 0;
    public final ResourceKey<Level> dimension;
    public final Vec2 rot;
    public final int reasonCost;
    public PlayerTeam team = null;

    public NewTeamInstance(boolean inChallenging, Vec3 prepareCenterPos, MutableComponent description,
                           MutableComponent regionDescription, double prepareDetectRange, int levelRequire,
                           int minPlayerNum, int maxPlayerNum, int maxChallengeTime,
                           ResourceKey<Level> dimension, Vec2 rot, int reasonCost) {
        this.inChallenging = inChallenging;
        this.prepareCenterPos = prepareCenterPos;
        this.prepareDetectRange = prepareDetectRange;
        this.levelRequire = levelRequire;
        this.description = description;
        this.regionDescription = regionDescription;
        this.minPlayerNum = minPlayerNum;
        this.maxPlayerNum = maxPlayerNum;
        this.maxChallengeTime = maxChallengeTime;
        this.dimension = dimension;
        this.rot = rot;
        this.reasonCost = reasonCost;
    }

    public NewTeamInstance(boolean inChallenging, Vec3 prepareCenterPos, MutableComponent description,
                           MutableComponent regionDescription, double prepareDetectRange, int levelRequire,
                           int minPlayerNum, int maxPlayerNum, int maxChallengeTime,
                           ResourceKey<Level> dimension, Vec2 rot) {
        this(inChallenging, prepareCenterPos, description, regionDescription, prepareDetectRange, levelRequire,
                minPlayerNum, maxPlayerNum, maxChallengeTime, dimension, rot, 0);
    }

    public void tick(Level level) {
        int tickCount = Tick.get();
        // 未在挑战中 进行开始检测

        if (!inChallenging) {
            List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(prepareCenterPos,
                    prepareDetectRange * 2, prepareDetectRange * 2, prepareDetectRange * 2));
            // 1.判断等级需求
            if (tickCount % 100 == 0) {
                playerList.forEach(player -> {
                    if (player.experienceLevel < levelRequire && player.isShiftKeyDown()) {
                        Compute.sendFormatMSG(player, Component.literal("组队副本").withStyle(ChatFormatting.RED),
                                Component.literal("你似乎没有达到进入副本的等级需求: ").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal("Lv." + levelRequire).withStyle(Utils.levelStyleList.get(levelRequire / 25))));
                    }
                });
            }

            playerList.removeIf(player -> player.experienceLevel < levelRequire);
            // 2.分类玩家（准备/未准备）
            Set<Player> preparedPlayers = new HashSet<>();
            Set<Player> unPreparedPlayers = new HashSet<>();
            playerList.forEach(player -> {
                if (player.isShiftKeyDown()) preparedPlayers.add(player);
                else unPreparedPlayers.add(player);
            });
            // 3.向玩家们发送客户端信息
            if (tickCount % 10 == 6) {
                List<Component> preparedPlayerNames = new ArrayList<>();
                List<Component> unPreparedPlayerNames = new ArrayList<>();
                preparedPlayers.forEach(player -> {
                    preparedPlayerNames.add(player.getDisplayName());
                });
                unPreparedPlayers.forEach(player -> {
                    unPreparedPlayerNames.add(player.getDisplayName());
                });
                if (!inChallenging) {
                    playerList.forEach(player -> {
                        ServerPlayer serverPlayer = (ServerPlayer) player;
                        ModNetworking.sendToClient(new NewTeamInstancePrepareInfoS2CPacket(preparedPlayerNames,
                                unPreparedPlayerNames), serverPlayer);
                    });
                }
            }
            // 4.判断是否可以开始挑战
            // 首先判断玩家列表是否为空
            if (!playerList.isEmpty() || !players.isEmpty()) {
                if (unPreparedPlayers.isEmpty() || startCount >= 5) {
                    // 若均准备完成 则设置计时
                    if (tickCount % 20 == 0) {
                        if (!players.isEmpty() || preparedPlayers.size() >= minPlayerNum && preparedPlayers.size() <= maxPlayerNum)
                            startCount++;
                        else startCount = 0;
                        // 计时至5s，即确认挑战的玩家列表
                        if (startCount == 5) {
                            players.addAll(playerList);
                            List<Component> joinedPlayerNames = new ArrayList<>();
                            playerList.forEach(player -> joinedPlayerNames.add(player.getDisplayName()));
                            playerList.forEach(player -> {
                                ModNetworking.sendToClient(new NewTeamInstanceJoinedPlayerInfoS2CPacket(joinedPlayerNames), (ServerPlayer) player);
                            });
                        }
                        // 计时完成 挑战开始
                        if (startCount == 10) {
                            // 初始化怪物列表
                            initMobList(level);
                            inChallenging = true;
                            startCount = 0;
                            players.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                        new ClientboundSetTitleTextPacket(description);
                                ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                        new ClientboundSetSubtitleTextPacket(Component.literal("清理所有怪物!").withStyle(ChatFormatting.RED));
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        } else {
                            // 向玩家们发送计时
                            playerList.forEach(player -> {
                                if (players.isEmpty() && !(preparedPlayers.size() >= minPlayerNum && preparedPlayers.size() <= maxPlayerNum)) {
                                    Compute.sendFormatMSG(player, Component.literal("组队副本").withStyle(ChatFormatting.RED),
                                            Component.literal("当前玩家人数不符合副本的人数要求").withStyle(ChatFormatting.WHITE));
                                } else {
                                    Compute.sendFormatMSG(player, Component.literal("组队副本").withStyle(ChatFormatting.RED),
                                            Component.literal("所有玩家已做好准备，副本将在").withStyle(ChatFormatting.WHITE).
                                                    append(Component.literal((10 - startCount) + "s").withStyle(ChatFormatting.AQUA)).
                                                    append(Component.literal("后开始").withStyle(ChatFormatting.WHITE)));
                                }
                            });

                            players.forEach(player -> {
                                if (startCount >= 5) {
                                    ServerPlayer serverPlayer = (ServerPlayer) player;
                                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                            new ClientboundSetTitleTextPacket(description);
                                    ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                            new ClientboundSetSubtitleTextPacket(Component.literal("将在").withStyle(ChatFormatting.AQUA).
                                                    append(Component.literal((10 - startCount) + "s").withStyle(ChatFormatting.AQUA)).
                                                    append(Component.literal("后开始").withStyle(ChatFormatting.AQUA)));
                                    serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                    serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                                    MySound.soundToPlayer(player, SoundEvents.ARROW_HIT_PLAYER);
                                }
                            });
                        }
                    }
                } else {
                    // 若有玩家未准备 则清空计时
                    startCount = 0;
                }
            }
        }
        // 已在挑战中 进行挑战中的处理与挑战完成检测
        else {
            players.removeIf(Objects::isNull);
            if (players.isEmpty()) {
                clear();
                return;
            }
            Random random = new Random();
            if (deadTimes >= players.size()) {
                Compute.formatBroad(level, Component.literal("团队副本").withStyle(ChatFormatting.RED),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(players.stream().toList().get(random.nextInt(players.size())).getDisplayName()).
                                append(Component.literal("等" + players.size() + "名玩家挑战 ").withStyle(ChatFormatting.WHITE)).
                                append(description).
                                append(Component.literal(" 时，阵亡次数过多，挑战失败。").withStyle(ChatFormatting.WHITE)));
                clear();
                return;
            }
            hasSummonedMobs.forEach(mob -> {
                if (!mob.isAlive()) hasKilledMobs.add(mob);
            });
            if (tickCount % 200 == 8) {
                players.forEach(player -> {
                    Compute.sendFormatMSG(player, Component.literal("团队副本").withStyle(ChatFormatting.RED),
                            Component.literal("还剩").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(String.valueOf(mobList.size() - hasKilledMobs.size())).withStyle(ChatFormatting.RED)).
                                    append(Component.literal("只怪物未清理。").withStyle(ChatFormatting.WHITE)));
                });
            }
            handleTick(level);
            mobList.forEach(conditionSummonMob -> {
                Mob mob = conditionSummonMob.mob();
                if (!hasSummonedMobs.contains(mob)) {
                    if (conditionSummonMob.condition() == 0 && players.stream().anyMatch(player -> player.position()
                            .distanceTo(conditionSummonMob.summonPos) < conditionSummonMob.detectRange)) {
                        hasSummonedMobs.add(mob);
                        level.addFreshEntity(mob);
                    }
                }
            });
            if (mobList.size() - hasKilledMobs.size() == 0 && !allMobIsClear()) {
                players.forEach(player -> {
                    Compute.sendFormatMSG(player, Component.literal("团队副本").withStyle(ChatFormatting.RED),
                            Component.literal("挑战异常，已终止").withStyle(ChatFormatting.WHITE));
                });
                clear();
                return;
            }
            lastTick++;
            if (lastTick / 20 > maxChallengeTime) {
                players.forEach(player -> {
                    Compute.sendFormatMSG(player, Component.literal("团队副本").withStyle(ChatFormatting.RED),
                            Component.literal("超出挑战时间限制，挑战失败。").withStyle(ChatFormatting.RED));
                });
                clear();
            }
            if (allMobIsClear() && !players.isEmpty()) {
                Compute.formatBroad(level, Component.literal("团队副本").withStyle(ChatFormatting.RED),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(players.stream().toList().get(random.nextInt(players.size())).getDisplayName()).
                                append(Component.literal("等" + players.size() + "名玩家用时 ").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(String.format("%.2f", lastTick * 0.05) + "s").withStyle(ChatFormatting.AQUA)).
                                append(Component.literal(" 通关了").withStyle(ChatFormatting.WHITE)).
                                append(description));
                players.forEach(player -> {
                    if (Reason.getPlayerReasonValue(player) < reasonCost) {
                        Compute.sendFormatMSG(player, Component.literal("团队副本").withStyle(ChatFormatting.RED),
                                Te.s("获取奖励所需的", "理智", CustomStyle.styleOfFlexible, "不足。需要",
                                        reasonCost + "理智", CustomStyle.styleOfFlexible));
                        return;
                    }
                    if (!allowReward(player)) {
                        Compute.sendFormatMSG(player, Component.literal("团队副本").withStyle(ChatFormatting.RED),
                                allowRewardCondition());
                        return;
                    }
                    Reason.addOrCostPlayerReasonValue(player, -reasonCost);
                    reward(player);
                    MobSpawn.incrementPlayerKillCount(player, description.getString());
                });
                clear();
            }
        }
        if (tickCount % 20 == 0) armorStandForDisplay(level);
    }

    public abstract void initMobList(Level level);

    public abstract void handleTick(Level level);

    public abstract void reward(Player player);

    public abstract boolean allowReward(Player player);

    public abstract Component allowRewardCondition();

    public abstract List<ItemAndRate> getRewardList();

    public abstract double judgeDamage(Player player, Mob mob, double originDamage);

    public boolean allMobIsClear() {
        for (ConditionSummonMob conditionSummonMob : mobList) {
            if (!conditionSummonMob.mob().isDeadOrDying()) return false;
        }
        return true;
    }

    public boolean beforeMobIsDead(Mob mob) {
        for (ConditionSummonMob conditionSummonMob : mobList) {
            if (!conditionSummonMob.mob().equals(mob) && !conditionSummonMob.mob().isDeadOrDying()) {
                return false;
            }
            if (conditionSummonMob.mob().equals(mob)) return true;
        }
        return true;
    }

    public void clear() {
        players.forEach(player -> {
            ModNetworking.sendToClient(new NewTeamInstanceClearS2CPacket(), (ServerPlayer) player);
        });
        players.clear();
        mobList.forEach(conditionSummonMob -> {
            conditionSummonMob.mob.kill();
        });
        mobList.clear();
        hasKilledMobs.clear();
        inChallenging = false;
        startCount = 0;
        lastTick = 0;
        hasSummonedMobs.clear();
        deadTimes = 0;
        if (team != null) {
            Utils.ChallengingPlayerTeam.remove(team);
        }
    }

    public void armorStandForDisplay(Level level) {
        List<ArmorStand> armorStandList = level.getEntitiesOfClass(ArmorStand.class, AABB.ofSize(prepareCenterPos, prepareDetectRange * 2, prepareDetectRange * 2, prepareDetectRange * 2));
        armorStandList.forEach(ArmorStand::kill);
        if (inChallenging) {
            summonArmorStand(level, new Vec3(0, 0.25, 0), description);
            summonArmorStand(level, new Vec3(0, 0, 0), Component.literal("挑战中" + ".".repeat(Tick.get() % 4)).withStyle(ChatFormatting.RED));
            summonArmorStand(level, new Vec3(0, -0.25, 0), Component.literal("已进行" + lastTick / 20 + "秒").withStyle(ChatFormatting.AQUA));
        } else {
            if (!players.isEmpty()) {
                summonArmorStand(level, new Vec3(0, 0.25, 0), description);
                summonArmorStand(level, new Vec3(0, 0, 0), Component.literal("已就绪").withStyle(ChatFormatting.RED));
            } else {
                summonArmorStand(level, new Vec3(0, 0.25, 0), description);
                summonArmorStand(level, new Vec3(0, 0, 0), Component.literal("待挑战").withStyle(ChatFormatting.AQUA));
                summonArmorStand(level, new Vec3(0, -0.25, 0), Component.literal("人数:").withStyle(ChatFormatting.GREEN).
                        append(Component.literal(minPlayerNum + "-" + maxPlayerNum).withStyle(ChatFormatting.AQUA)));
            }
        }
    }

    public void summonArmorStand(Level level, Vec3 offset, Component name) {
        ArmorStand armorStand = new ArmorStand(EntityType.ARMOR_STAND, level);
        armorStand.setNoGravity(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(name);
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);
        armorStand.noPhysics = true;
        armorStand.setBoundingBox(AABB.ofSize(new Vec3(0, 0, 0), 0.1, 0.1, 0.1));
        armorStand.moveTo(prepareCenterPos.add(offset).add(0.5, 0, 0.5));
        level.addFreshEntity(armorStand);
    }

    public void startByTeam(PlayerTeam playerTeam) {
        players.addAll(playerTeam.getPlayerList());
        startCount = 5;
        team = playerTeam;
    }
}
