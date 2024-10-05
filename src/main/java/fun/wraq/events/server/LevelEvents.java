package fun.wraq.events.server;

import fun.wraq.commands.changeable.PrefixCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Boss2Damage;
import fun.wraq.common.util.struct.Gather;
import fun.wraq.customized.uniform.bow.BowCurios1;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.EffectOnMob;
import fun.wraq.process.func.MobEffectAndDamageMethods;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.originSummon.OriginSummon;
import fun.wraq.process.system.season.MySeason;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.specialevents.summer.SummerEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
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
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber
public class LevelEvents {
    public static int seasonChangeTick = 0;

    @SubscribeEvent
    public static void season(SeasonChangedEvent event) {
        Level level = event.getLevel();
        if (!level.isClientSide && level.getServer().getTickCount() != seasonChangeTick) {
            seasonChangeTick = level.getServer().getTickCount();
            SeasonSavedData seasonData = SeasonHandler.getSeasonSavedData(level);
            SeasonTime time = new SeasonTime(seasonData.seasonCycleTicks);
            MySeason.currentElementEffectBroadDelay = level.getServer().getTickCount() + 200;
            Compute.formatBroad(level, Component.literal("季节").withStyle(CustomStyle.styleOfLife), Component.literal("").withStyle(ChatFormatting.WHITE).
                    append(MySeason.seasonComponentMap.get(time.getSubSeason().name())).
                    append(Component.literal(" 时分已经到来").withStyle(ChatFormatting.WHITE)));
            MySeason.currentSeason = time.getSubSeason().name();
            MySound.soundToAllPlayer(event.getLevel(), SoundEvents.EXPERIENCE_ORB_PICKUP);
        }
    }

    @SubscribeEvent
    public static void LevelTick(TickEvent.LevelTickEvent event) throws IOException, SQLException, ParseException {

        timeEvent(event);
        /*WorldBossEvent(event);*/
        Broad(event);
        Stop(event);
        OriginSummon.DetectElementPiece(event);
        /*LabourDayMobSummon.levelTick(event);*/
        BowCurios1.tick(event);
        Tower.tick(event);
        MySeason.tick(event);

        MobSpawn.tick(event); // 新怪物生成控制
        NoTeamInstanceModule.tick(event); // 新副本

        EffectOnMob.levelTick(event);

        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && event.level.getServer().getTickCount() % 18000 == 0) {
            ServerLevel serverLevel = (ServerLevel) event.level;
            List<Entity> projectileList = new ArrayList<>();
            serverLevel.getAllEntities().forEach(entity -> {
                if (entity instanceof Projectile) projectileList.add(entity);
            });
            if (projectileList.size() > 1000)
                projectileList.forEach(entity -> entity.remove(Entity.RemovalReason.KILLED));
        }// 尝试清理

        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            MobEffectAndDamageMethods.Tick(event.level);
            int TickCount = event.level.getServer().getTickCount();
            if (TickCount % 100 == 0) TryToRemoveMobInMap();
            if (TickCount % 20 == 0) Element.Tick(event.level);
            Compute.Gather(TickCount); // 聚集
        }

        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {

            while (Utils.valueItemEntity.peek() != null) {
                if (Utils.valueItemEntity.peek().getResetTick() < event.level.getServer().getTickCount())
                    Utils.valueItemEntity.poll().getItemEntity().remove(Entity.RemovalReason.KILLED);
                else break;
            }

            if (event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))
                    && event.level.getServer().getTickCount() % 100 == 0) {
                List<ServerPlayer> playerList = event.level.getServer().getPlayerList().getPlayers();
                PrefixCommand.handlePrefix(playerList);

                playerList.forEach(Player::refreshDisplayName);
                playerList.forEach(ServerPlayer::refreshTabListName);
            }

            if (event.level.getServer().getTickCount() != Utils.GatherTickCount) {
                Utils.GatherTickCount = event.level.getServer().getTickCount();
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

            if (event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)) && event.level.getServer().getTickCount() % 20 == 0) {
                List<Player> playerList = event.level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(1352, 80, 502), 150, 60, 100));
                if (playerList.size() > 0) {
                    Random r = new Random();
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, event.level);
                    lightningBolt.setVisualOnly(true);
                    lightningBolt.moveTo(r.nextInt(1182, 1523), 63, r.nextInt(371, 633));
                    lightningBolt.setSilent(true);
                    event.level.addFreshEntity(lightningBolt);
                }
            }
/*            if(event.level.getServer().getTickCount() % 20 == 0)
            {
                if(event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)) && event.level.isThundering() && Utils.IsLandBarrier)
                {
                    Utils.IsLandBarrier = false;
                    BarrierBuild.Destroy(event.level);
                    Compute.FormatBroad(event.level,Component.literal("唤雷岛").withStyle(CustomStyle.styleOfLightingIsland),
                            Component.literal("唤雷塔的唤雷力量被天空的雷电削弱了。").withStyle(ChatFormatting.WHITE));
                }
                if(event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)) && !event.level.isThundering() && !Utils.IsLandBarrier)
                {
                    Utils.IsLandBarrier = true;
                    BarrierBuild.Build(event.level);
                    Compute.FormatBroad(event.level,Component.literal("唤雷岛").withStyle(CustomStyle.styleOfLightingIsland),
                            Component.literal("天空的响雷停息了，唤雷塔的唤雷力量恢复了。").withStyle(ChatFormatting.WHITE));
                }
            }*/
            if (!Utils.MonsterAttributeDataProvider.isEmpty() && Utils.AttributeDataTick != event.level.getServer().getTickCount()) {
                Utils.AttributeDataTick = event.level.getServer().getTickCount();
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

            if (!Utils.SnowRune2MobController.isEmpty() && Utils.SnowRune2Tick != event.level.getServer().getTickCount()) {
                Utils.SnowRune2Tick = event.level.getServer().getTickCount();
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
            if (!Utils.witherBonePowerCCMonster.isEmpty() && Utils.witherBonePowerCount != event.level.getServer().getTickCount()) {
                Utils.witherBonePowerCount = event.level.getServer().getTickCount();
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
                if (!Utils.OverWorldLevelIsNight &&
                        event.level.getDayTime() % 24000 >= 12000) {
                    Utils.OverWorldLevelIsNight = true;
                }
                if (Utils.OverWorldLevelIsNight &&
                        event.level.dayTime() % 24000 == 1) {
                    Utils.OverWorldLevelIsNight = false;
                }
            }
        }
    }

    public static void timeEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            Calendar calendar = Calendar.getInstance();
            Level level = event.level;
            List<ServerPlayer> playerList = event.level.getServer().getPlayerList().getPlayers();
            if (event.level.getServer().getTickCount() % 200 == 0) {
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

    public static void WorldBossEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            Calendar calendar = Calendar.getInstance();
            Level level = event.level;
            if (Utils.giant != null && Utils.giant.isAlive())
                Utils.GiantBossInfo.setProgress(Utils.giant.getHealth() / Utils.giant.getMaxHealth());
            if (level.getServer().getTickCount() % 20 == 0) {
                if (calendar.get(Calendar.SECOND) == 1 && calendar.get(Calendar.MINUTE) == 59
                        && (calendar.get(Calendar.HOUR_OF_DAY) == 9
                        || calendar.get(Calendar.HOUR_OF_DAY) == 14
                        || calendar.get(Calendar.HOUR_OF_DAY) == 18
                        || calendar.get(Calendar.HOUR_OF_DAY) == 20
                        || calendar.get(Calendar.HOUR_OF_DAY) == 22)) {
                    Compute.formatBroad(level, Component.literal("世界Boss").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal(" 巨人僵尸还有1分钟就要出现了！").withStyle(ChatFormatting.WHITE));
                    Utils.GiantPlayerList.forEach(playerName -> {
                        if (level.getServer().getPlayerList().getPlayerByName(playerName) != null) {
                            ServerPlayer serverPlayer = level.getServer().getPlayerList().getPlayerByName(playerName);
                            serverPlayer.teleportTo((ServerLevel) level, 638, 114, 1357, 0, 0);
                        }
                    });
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) == 10
                        || calendar.get(Calendar.HOUR_OF_DAY) == 15
                        || calendar.get(Calendar.HOUR_OF_DAY) == 19
                        || calendar.get(Calendar.HOUR_OF_DAY) == 21
                        || calendar.get(Calendar.HOUR_OF_DAY) == 23) {
                    if (Utils.GiantHour != calendar.get(Calendar.HOUR_OF_DAY)) {
                        Utils.GiantHour = calendar.get(Calendar.HOUR_OF_DAY);
                        SummonGiant(level);
                    }
                }
                if (Utils.GiantFlag && Utils.giant != null && Utils.giant.isDeadOrDying() && Utils.GiantDamageList.size() > 0) {
                    Utils.GiantFlag = false;
                    Compute.formatBroad(level, Component.literal("世界Boss").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal("  征讨伤害排名如下：").withStyle(ChatFormatting.WHITE));
                    Utils.GiantDamageList.sort(Comparator.comparing(Boss2Damage::getDamage).reversed());
                    AtomicInteger index = new AtomicInteger(1);

                    if (Utils.GiantBossInfo != null) Utils.GiantPlayerList.forEach(playerName -> {
                        if (level.getServer().getPlayerList().getPlayerByName(playerName) != null) {
                            ServerPlayer serverPlayer = level.getServer().getPlayerList().getPlayerByName(playerName);
                            Utils.GiantBossInfo.removePlayer(serverPlayer);
                        }
                    });

                    Utils.GiantDamageList.forEach(boss2Damage -> {
                        if (boss2Damage.getPlayer() != null) {
                            Player player1 = boss2Damage.getPlayer();
                            double damage = boss2Damage.getDamage();
                            Compute.formatBroad(level, Component.literal(index + ".").withStyle(ChatFormatting.RED),
                                    Component.literal(" ").withStyle(ChatFormatting.WHITE).
                                            append(player1.getDisplayName()).
                                            append(Component.literal("  DMG:" + damage + "[" + String.format("%.2f", damage * 100 / (Utils.giant.getMaxHealth())) + "%]").withStyle(ChatFormatting.WHITE)));
                            if (Utils.GiantCommonReward.isEmpty()) Utils.setGiantCommonReward();
                            if (boss2Damage.getDamage() / (Utils.giant.getMaxHealth()) > 0.005) {
                                Utils.GiantCommonReward.forEach(itemStack -> {
                                    InventoryOperation.itemStackGive(player1, new ItemStack(itemStack.getItem(), itemStack.getCount()));
                                });
                                if (index.get() <= 3) {
                                    InventoryOperation.itemStackGive(player1, ModItems.GiantMedal.get().getDefaultInstance());
                                }
                            }
                            index.incrementAndGet();
                        }
                    });
                    Utils.GiantPlayerList.clear();
                    Utils.GiantDamageList.clear();
                }
            }
        }
    }

    public static void SummonGiant(Level level) {

        if (!Utils.GiantPlayerList.isEmpty()) {
            if (Utils.giant != null) Utils.giant.remove(Entity.RemovalReason.KILLED);
            Utils.giant = new Giant(EntityType.GIANT, level);

            MobSpawn.setMobCustomName(Utils.giant, ModItems.MobArmorGiant.get(), Component.literal("悲催的巨人").withStyle(ChatFormatting.GREEN));
            Utils.giant.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorGiant.get().getDefaultInstance());
            Random r = new Random();
            Utils.giant.getAttribute(Attributes.MAX_HEALTH).setBaseValue(r.nextInt(100, 200) * Math.pow(10, 8));
            Utils.giant.setHealth(Utils.giant.getMaxHealth());
            Utils.giant.moveTo(638, 114, 1357);
            level.addFreshEntity(Utils.giant);
            Compute.formatBroad(level, Component.literal("世界Boss").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal(" 巨人僵尸已经出现！").withStyle(ChatFormatting.WHITE));
            Utils.GiantFlag = true;
            Utils.GiantPlayerList.forEach(playerName -> {
                if (level.getServer().getPlayerList().getPlayerByName(playerName) != null) {
                    ServerPlayer serverPlayer = level.getServer().getPlayerList().getPlayerByName(playerName);
                    serverPlayer.teleportTo((ServerLevel) level, 638, 114, 1357, 0, 0);
                }
            });
            Utils.GiantBossInfo = (ServerBossEvent) (new ServerBossEvent(Utils.giant.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);

            Utils.GiantPlayerList.forEach(playerName -> {
                if (level.getServer().getPlayerList().getPlayerByName(playerName) != null) {
                    ServerPlayer serverPlayer = level.getServer().getPlayerList().getPlayerByName(playerName);
                    Utils.GiantBossInfo.addPlayer(serverPlayer);
                }
            });
        } else {
            Compute.formatBroad(level, Component.literal("世界Boss").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal(" 没有人来找巨人僵尸玩，巨人僵尸伤心欲绝。").withStyle(ChatFormatting.WHITE));
        }
    }

    public static void TryToRemoveMobInMap() {
        Utils.playerLaserCoolDown.forEach((player, mobIntegerMap) -> {
            if (mobIntegerMap != null) {
                mobIntegerMap.entrySet().removeIf(mobIntegerEntry -> mobIntegerEntry.getKey().isDeadOrDying());
            }
        });
    }

    public static void Broad(TickEvent.LevelTickEvent event) {
        Level level = event.level;
        if (level.getServer() != null && event.phase.equals(TickEvent.Phase.START) && level.equals(level.getServer().getLevel(Level.OVERWORLD))) {
            String[] BroadCastContent = {
                    "对于物品描述下方有[可灌注/增幅]标签的物品，可以将其放置在能量灌注器灌注位查看灌注配方。",
                    "你知道吗，每日任务的冷却时间是22小时。",
                    "维瑞阿契一直在努力更新。",
                    "觉得爬山难？游泳慢？想要各种除战斗属性外的增益？积攒宝石碎片，找天空城珠宝商人兑换珠宝吧！",
                    "当穿透属性使得抗性在计算中成为负值，负值部分将为本次伤害提供增幅。",
                    "百分比护甲穿透、百分比法术穿透，均是乘算的哦！",
                    "觉得提示信息过多？使用/vmd ignore [Fight/Exp/QuickUse/ItemGet/Rune/Mana/Instance]来屏蔽一些信息吧！",
                    "注意，当你以鞘翅开启状态进入水中，鞘翅可能不会关闭，这将影响你在水中的运动。脱下鞘翅或者找到站立点是解决方法。",
                    "能力点数的配置将大幅影响你的游戏体验。",
                    "快捷使用将使你的主动释放没有前后摇。赶紧配置你的快捷使用键位吧！",
                    "现在，世界各地出现了本源学者。他们带着他们的本源解析器来到了天空城，将解析出来的本源交给他们，可以换取他们的一些研究成果。",
                    "下界维度充满着危险，因此需要你达到75级才可以前往，终界维度同样需要75级。",
                    /*"在世界各处散落着奖励箱，每隔一段时间，你便能够从中获取一些稀有奖励。（测试阶段）",*/
                    "在体力充足的情况下，按下翻滚（默认Z键）可以让跑图更加顺畅、战斗节奏更加紧凑",
                    "你可以通过/vmd bow来开关弓的视角拉伸",
                    "不知道做什么了？打开身份卡，查看图鉴，找到你喜爱的装备，努力制作吧！",
                    "欢迎加入维瑞阿契群:184453807 维瑞阿契更新通知群:693292427",
                    "觉得移动速度过快？使用/vmd speed [效能]来手动调整你的移动速度加成效能吧！（手动调整仅会影响你的移动速度加成属性在玩家移速上的体现）",
                    "你知道吗？身份卡的图鉴可以查看装备的获取方式。",
                    "你知道吗？如果没有归一化元素强度，元素共鸣提供的反应元素量将会忽略不计。",
                    "使用身份卡打开任务列表，查看进行中的任务，完成获得奖励吧！",
                    "推荐打开声音设置中的音乐，享受MC原版BGM！",
                    "对于有路径点的村庄，村庄内基本都有一些基础设施或基础物资商人",
                    "如果你不知道一个材料怎么获取，打开身份卡，点击物品图鉴，选择'材料'，试着推断或找到获取方式吧",
                    "等级带来的增益非常之高，努力提升等级吧",
                    "推荐使用全屏时将界面尺寸调整至3以获得更好的体验",
                    "你知道吗(F3+H)可以隐藏物品描述下方的灰色NBT(如果你曾不小心开启过)，可以让你的物品描述更加干净"
            };
            int tick = level.getServer().getTickCount();

            if (tick % 6000 == 0) {
                Random random = new Random();
                Compute.formatBroad(level, Component.literal("提示").withStyle(ChatFormatting.AQUA),
                        Component.literal(BroadCastContent[random.nextInt(BroadCastContent.length)]).withStyle(ChatFormatting.WHITE));
            }

            if (tick % 12000 == 726) {
                SummerEvent.sendDailyTimeRank(level);
            }
        }
    }

    public static void Stop(TickEvent.LevelTickEvent event) {
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
