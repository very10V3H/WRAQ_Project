package fun.wraq.events.server;

import fun.wraq.commands.changeable.PrefixCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.BlockAndResetTime;
import fun.wraq.common.util.struct.Gather;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.events.mob.jungle.JungleMobSpawn;
import fun.wraq.process.func.EffectOnMob;
import fun.wraq.process.func.MobEffectAndDamageMethods;
import fun.wraq.process.func.PersistentRangeEffect;
import fun.wraq.process.func.multiblockactive.rightclick.RightClickActiveHandler;
import fun.wraq.process.system.season.MySeason;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.summer2025.Summer2025;
import fun.wraq.series.instance.series.mushroom.UnknownGem;
import fun.wraq.series.overworld.divine.DivineUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sereneseasons.api.season.SeasonChangedEvent;
import sereneseasons.handler.season.SeasonHandler;
import sereneseasons.season.SeasonSavedData;
import sereneseasons.season.SeasonTime;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

@Mod.EventBusSubscriber
public class LevelEvents {
    private static int seasonChangeTick = 0;
    private static String lastSubSeasonName = "";

    @SubscribeEvent
    public static void season(SeasonChangedEvent event) {
        Level level = event.getLevel();
        if (!level.isClientSide) {
            SeasonSavedData seasonData = SeasonHandler.getSeasonSavedData(level);
            SeasonTime time = new SeasonTime(seasonData.seasonCycleTicks);
            if (Tick.get() != seasonChangeTick && !Objects.equals(lastSubSeasonName, time.getSubSeason().name())) {
                seasonChangeTick = Tick.get();
                lastSubSeasonName = time.getSubSeason().name();
                MySeason.currentElementEffectBroadDelay = Tick.get() + 200;
                Compute.formatBroad(level, Component.literal("季节").withStyle(CustomStyle.styleOfLife),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                        append(MySeason.seasonComponentMap.get(time.getSubSeason().name())).
                        append(Component.literal(" 时分已经到来").withStyle(ChatFormatting.WHITE)));
                MySeason.currentSeason = time.getSubSeason().name();
                MySound.soundToAllPlayer(event.getLevel(), SoundEvents.EXPERIENCE_ORB_PICKUP);
            }
        }
    }

    @SubscribeEvent
    public static void LevelTick(TickEvent.LevelTickEvent event) throws IOException, SQLException, ParseException {

        timeEvent(event);
        /*WorldBossEvent(event);*/
        broad(event);
        stop(event);
        /*LabourDayMobSummon.levelTick(event);*/
        Tower.tick(event);
        MySeason.tick(event);
        MobSpawn.tick(event); // 新怪物生成控制
        NoTeamInstanceModule.tick(event); // 新副本
        EffectOnMob.levelTick(event);
        RightClickActiveHandler.detectNearPlayer(event);
        DivineUtils.handleServerLevelEvent(event);
        JungleMobSpawn.handleLevelTick(event);

        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            PersistentRangeEffect.levelTick(event.level);
            BlockAndResetTime.handleTick(event.level);
        }

        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && Tick.get() % 18000 == 0) {
            ServerLevel serverLevel = (ServerLevel) event.level;
            List<Entity> projectileList = new ArrayList<>();
            serverLevel.getAllEntities().forEach(entity -> {
                if (entity instanceof Projectile) projectileList.add(entity);
            });
            if (projectileList.size() > 1000) {
                projectileList.forEach(entity -> entity.remove(Entity.RemovalReason.KILLED));
            }
        } // 尝试清理

        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            Level level = event.level;
            MobEffectAndDamageMethods.Tick(level);
            int tick = Tick.get();
            if (tick % 100 == 0) {
                tryToRemoveMobInMap();
            }
            Compute.gather(tick); // 聚集
            if (tick % 20 == 1) {
                UnknownGem.handleLevelTick(level);
            }
            Summer2025.handleOverworldLevelTick(level);
        }

        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {

            while (Utils.valueItemEntity.peek() != null) {
                if (Utils.valueItemEntity.peek().getResetTick() < Tick.get())
                    Utils.valueItemEntity.poll().getItemEntity().remove(Entity.RemovalReason.KILLED);
                else break;
            }

            if (event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))
                    && Tick.get() % 100 == 0) {
                List<ServerPlayer> playerList = event.level.getServer().getPlayerList().getPlayers();
                PrefixCommand.handlePrefix(playerList);

                playerList.forEach(Player::refreshDisplayName);
                playerList.forEach(ServerPlayer::refreshTabListName);
            }

            if (Tick.get() != Utils.GatherTickCount) {
                Utils.GatherTickCount = Tick.get();
                List<ServerPlayer> playerList = event.level.getServer().getPlayerList().getPlayers();
                boolean flag = false;
                Gather RemoveGater = null;
                for (Gather gather : Utils.GatherMobMap.keySet()) {
                    if (gather.getTime() > 0) {
                        gather.setTime(gather.getTime() - 1);
                        Queue<Mob> mobQueue = Utils.GatherMobMap.get(gather);
                        for (Mob mob : mobQueue) {
                            mob.setDeltaMovement(gather.getPos().subtract(mob.position()).scale(0.2));
                        }
                    }
                    if (gather.getTime() == 0) {
                        flag = true;
                        RemoveGater = gather;
                    }
                }
                if (flag) Utils.GatherMobMap.remove(RemoveGater);
                flag = false;
                RemoveGater = null;
                for (Gather gather : Utils.GatherPlayerMap.keySet()) {
                    if (gather.getTime() > 0) {
                        gather.setTime(gather.getTime() - 1);
                        Queue<Player> mobQueue = Utils.GatherPlayerMap.get(gather);
                        for (Player mob : mobQueue) {
                            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                                    new ClientboundSetEntityMotionPacket(mob.getId(), gather.getPos().subtract(mob.position()).scale(0.2));
                            for (ServerPlayer serverPlayer : playerList) {
                                serverPlayer.connection.send(clientboundSetEntityMotionPacket);
                            }
                        }
                    }
                    if (gather.getTime() == 0) {
                        flag = true;
                        RemoveGater = gather;
                    }
                }
                if (flag) Utils.GatherPlayerMap.remove(RemoveGater);
            }

            if (event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)) && Tick.get() % 20 == 0) {
                List<Player> playerList = event.level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(1352, 80, 502), 150, 60, 100));
                if (!playerList.isEmpty()) {
                    Random r = new Random();
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, event.level);
                    lightningBolt.setVisualOnly(true);
                    lightningBolt.moveTo(r.nextInt(1182, 1523), 63, r.nextInt(371, 633));
                    lightningBolt.setSilent(true);
                    event.level.addFreshEntity(lightningBolt);
                }
            }
            if (!Utils.MonsterAttributeDataProvider.isEmpty() && Utils.AttributeDataTick != Tick.get()) {
                Utils.AttributeDataTick = Tick.get();
                Utils.MonsterAttributeDataProvider.forEach(monster -> {
                    CompoundTag data = monster.getPersistentData();
                    if (data.getInt("ManaRune2") > 0)
                        data.putInt("ManaRune2", data.getInt("ManaRune2") - 1);
                });
                Utils.MonsterAttributeDataProvider.removeIf(monster -> {
                    CompoundTag data = monster.getPersistentData();
                    return data.getInt("ManaRune2") == 0 || monster.isDeadOrDying();
                });
            }

            if (!Utils.SnowRune2MobController.isEmpty() && Utils.SnowRune2Tick != Tick.get()) {
                Utils.SnowRune2Tick = Tick.get();
                Utils.SnowRune2MobController.forEach(monster -> {
                    CompoundTag data = monster.getPersistentData();
                    if (data.getInt("snowRune2Defence") > 0)
                        data.putInt("snowRune2Defence", data.getInt("snowRune2Defence") - 1);
                });
                Utils.SnowRune2MobController.removeIf(monster -> {
                    CompoundTag data = monster.getPersistentData();
                    return data.getInt("snowRune2Defence") == 0 || monster.isDeadOrDying();
                });
            }
            if (!Utils.witherBonePowerCCMonster.isEmpty() && Utils.witherBonePowerCount != Tick.get()) {
                Utils.witherBonePowerCount = Tick.get();
                Utils.witherBonePowerCCMonster.forEach(monster -> {
                    CompoundTag data = monster.getPersistentData();
                    if (data.getInt("witherBonePower") > 0)
                        data.putInt("witherBonePower", data.getInt("witherBonePower") - 1);
                });
                Utils.witherBonePowerCCMonster.removeIf(monster -> {
                    CompoundTag data = monster.getPersistentData();
                    return data.getInt("witherBonePower") == 0 || monster.isDeadOrDying();
                });
            }
            if (event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
                if (!Utils.overworldIsNight &&
                        event.level.getDayTime() % 24000 >= 12000) {
                    Utils.overworldIsNight = true;
                }
                if (Utils.overworldIsNight &&
                        event.level.dayTime() % 24000 == 1) {
                    Utils.overworldIsNight = false;
                }
            }
        }
    }

    public static void timeEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            Calendar calendar = Calendar.getInstance();
            Level level = event.level;
            List<ServerPlayer> playerList = event.level.getServer().getPlayerList().getPlayers();
            if (Tick.get() % 200 == 0) {
                if (calendar.get(Calendar.HOUR_OF_DAY) == 9 && Utils.TimeEventFlag != 9) {
                    Compute.formatBroad(level, Component.literal("时间").withStyle(ChatFormatting.AQUA),
                            Component.literal("早上好！新的一天有新的开始！").withStyle(ChatFormatting.WHITE));
                    Utils.TimeEventFlag = 9;
                    playerList.forEach(serverPlayer -> {
                        Compute.givePercentExpToPlayer(serverPlayer, 0.2, 0, serverPlayer.experienceLevel);
                    });
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) == 12 && Utils.TimeEventFlag != 12) {
                    Compute.formatBroad(level, Component.literal("时间").withStyle(ChatFormatting.AQUA),
                            Component.literal("午餐时间到了，吃完饭后小憩一会儿吧！").withStyle(ChatFormatting.WHITE));
                    Utils.TimeEventFlag = 12;
                    playerList.forEach(serverPlayer -> {
                        Compute.VBIncomeAndMSGSend(serverPlayer, serverPlayer.experienceLevel * 5);
                    });
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) == 15 && Utils.TimeEventFlag != 15) {
                    Compute.formatBroad(level, Component.literal("时间").withStyle(ChatFormatting.AQUA),
                            Component.literal("三点几嘞！饮茶先咯！").withStyle(ChatFormatting.WHITE));
                    Utils.TimeEventFlag = 15;
                    playerList.forEach(serverPlayer -> {
                        Compute.givePercentExpToPlayer(serverPlayer, 0.2, 0, serverPlayer.experienceLevel);
                    });
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) == 18 && Utils.TimeEventFlag != 18) {
                    Compute.formatBroad(level, Component.literal("时间").withStyle(ChatFormatting.AQUA),
                            Component.literal("今晚吃什么呢？").withStyle(ChatFormatting.WHITE));
                    Utils.TimeEventFlag = 18;
                    playerList.forEach(serverPlayer -> {
                        Compute.VBIncomeAndMSGSend(serverPlayer, serverPlayer.experienceLevel * 5);
                    });
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) == 21 && Utils.TimeEventFlag != 21) {
                    Compute.formatBroad(level, Component.literal("时间").withStyle(ChatFormatting.AQUA),
                            Component.literal("夜生活才刚刚开始！").withStyle(ChatFormatting.WHITE));
                    Utils.TimeEventFlag = 21;
                    playerList.forEach(serverPlayer -> {
                        Compute.givePercentExpToPlayer(serverPlayer, 0.2, 0, serverPlayer.experienceLevel);
                    });
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && Utils.TimeEventFlag != 0) {
                    Compute.formatBroad(level, Component.literal("时间").withStyle(ChatFormatting.AQUA),
                            Component.literal("夜深了，似乎该洗澡休息了呢。").withStyle(ChatFormatting.WHITE));
                    Utils.TimeEventFlag = 0;
                    playerList.forEach(serverPlayer -> {
                        Compute.VBIncomeAndMSGSend(serverPlayer, serverPlayer.experienceLevel * 5);
                    });
                    Utils.Boss2DeadTimes = 0;
                    Compute.formatBroad(level, Component.literal("黄金屋").withStyle(ChatFormatting.GOLD),
                            Component.literal(" 突见忍的实力重回到了初始状态！").withStyle(CustomStyle.styleOfSakura));
                }
            }
        }
    }

    public static void tryToRemoveMobInMap() {
        Utils.playerLaserCoolDown.forEach((player, mobIntegerMap) -> {
            if (mobIntegerMap != null) {
                mobIntegerMap.entrySet().removeIf(mobIntegerEntry -> mobIntegerEntry.getKey().isDeadOrDying());
            }
        });
    }

    public static void broad(TickEvent.LevelTickEvent event) {
        Level level = event.level;
        if (level.getServer() != null && event.phase.equals(TickEvent.Phase.START) && level.equals(level.getServer().getLevel(Level.OVERWORLD))) {
            String[] broadCastContents = {
                    "你知道吗，每日任务的冷却时间是22小时。完成每日任务可以获取水晶碎片、经验值等奖励！",
                    "觉得提示信息过多？使用 /vmd ignore 命令来屏蔽一些信息吧！",
                    "能力点数的配置将大幅影响你的游戏体验。",
                    "快捷使用将使你的主动释放没有前后摇。赶紧配置你的快捷使用键位吧！",
                    "其他维度充满着危险，因此需要你达到75级才可以前往。",
                    "在世界各处散落着奖励箱，每个自然月，你都能够从中获取一些稀有奖励。",
                    "在体力充足的情况下，按下翻滚（默认Z键）可以让跑图更加顺畅、战斗节奏更加紧凑",
                    "不知道做什么了？打开身份卡，查看图鉴，找到你喜爱的装备，努力制作吧！",
                    "欢迎加入维瑞阿契群:184453807，有任何疑问敬请在群里提出！",
                    "你知道吗？身份卡的图鉴可以查看装备的获取方式。",
                    "你知道吗？如果没有归一化元素强度，元素共鸣提供的反应元素量将会忽略不计。",
                    "使用身份卡打开任务列表，查看进行中的任务，完成获得奖励吧！",
                    "对于有路径点的村庄，村庄内基本都有一些基础设施或基础物资商人",
                    "如果你不知道一个材料怎么获取，打开身份卡，点击物品图鉴，选择'材料'，试着推断或找到获取方式吧",
                    "等级带来的增益非常之高，努力提升等级吧",
                    "推荐使用全屏时将界面尺寸调整至3以获得更好的体验",
                    "你知道吗(F3+H)可以隐藏物品描述下方的灰色NBT(如果你曾不小心开启过)，可以让你的物品描述更加干净",
                    "法师的普通攻击也可以造成暴击！",
                    "非常推荐使用新版客户端的光影，可以给游戏带来更好的体验。",
                    "游玩过程中，有任何的细节处觉得不合理，欢迎向铁头提建议",
                    "向铁头私聊反馈bug，可以获取vp奖励！",
                    "有关游戏内容的任何想法，欢迎私聊铁头！qq:2016187250",
                    "当你达到180级后，这类提示信息将不再出现在你的聊天窗中"
            };
            int tick = Tick.get();

            if (tick % 6000 == 0) {
                Random random = new Random();
                level.getServer().getPlayerList().getPlayers()
                        .stream().filter(player -> player.experienceLevel < 180)
                        .forEach(player -> {
                    Compute.sendFormatMSG(player, Te.s("提示", ChatFormatting.AQUA),
                            Te.s(broadCastContents[random.nextInt(broadCastContents.length)]));
                });
            }
        }
    }

    public static void stop(TickEvent.LevelTickEvent event) {
        if (event.level.getServer() != null && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            if (Utils.ServerStopTick != -1) {
                Utils.ServerStopTick--;
            }
            if (Utils.ServerStopTick % 20 == 0) {
                if (Utils.ServerStopTick == 0) {
                    event.level.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
                        serverPlayer.connection.disconnect(Component.literal("服务器正在进行更新维护...").withStyle(ChatFormatting.AQUA));
                    });
                } else {
                    event.level.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("服务器即将重启").withStyle(ChatFormatting.AQUA));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("请尽快退出，防止数据丢失.."/* + Utils.ServerStopTick / 20 + "s"*/).withStyle(ChatFormatting.AQUA));
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        MySound.soundToPlayer(serverPlayer, SoundEvents.ARROW_HIT_PLAYER);
                    });
                }
            }
        }
    }
}
