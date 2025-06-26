package fun.wraq.events.server;

import fun.wraq.commands.changeable.CompensateCommand;
import fun.wraq.commands.changeable.PrefixCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.PlayerTeam;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.events.core.BlockEvent;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.events.mob.instance.instances.tower.ManaTowerData;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.VersionCheckS2CPacket;
import fun.wraq.networking.dailyMission.DailyMissionContentS2CPacket;
import fun.wraq.networking.dailyMission.DailyMissionFinishedTimeS2CPacket;
import fun.wraq.networking.misc.AnimationPackets.AnimationTickResetS2CPacket;
import fun.wraq.networking.misc.ManaSyncS2CPacket;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import fun.wraq.networking.misc.TeamPackets.TeamInfoResetS2CPacket;
import fun.wraq.networking.reputation.ReputationValueS2CPacket;
import fun.wraq.networking.reputationMission.ReputationMissionAllowRequestTimeS2CPacket;
import fun.wraq.networking.reputationMission.ReputationMissionContentS2CPacket;
import fun.wraq.networking.reputationMission.ReputationMissionStartTimeS2CPacket;
import fun.wraq.networking.unSorted.SwiftSyncS2CPacket;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.plan.DailySupply;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.process.func.security.Security;
import fun.wraq.process.func.security.mac.MacServer;
import fun.wraq.process.func.security.mac.network.MacRequestS2CPacket;
import fun.wraq.process.system.bank.BondDividends;
import fun.wraq.process.system.bonuschest.BonusChestPlayerData;
import fun.wraq.process.system.cooking.CookingPlayerData;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.entrustment.mob.MobKillEntrustment;
import fun.wraq.process.system.estate.EstatePlayerData;
import fun.wraq.process.system.lottery.NewLotteries;
import fun.wraq.process.system.missions.mission2.MissionV2Helper;
import fun.wraq.process.system.parkour.Parkour;
import fun.wraq.process.system.profession.smith.SmithPlayerData;
import fun.wraq.process.system.randomevent.RandomEventData;
import fun.wraq.process.system.randomevent.impl.special.SpringMobEvent;
import fun.wraq.process.system.reason.Reason;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.teamInstance.NewTeamInstanceHandler;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.process.system.tower.TowerStatusS2CPacket;
import fun.wraq.process.system.vp.VpDataHandler;
import fun.wraq.render.gui.trade.single.SingleItemChangePurchaseLimit;
import fun.wraq.render.gui.trade.weekly.WeeklyStorePlayerData;
import fun.wraq.render.hud.main.QuickUseHud;
import fun.wraq.render.hud.networking.QuickUseDisplayS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events._7shade.SevenShadePiece;
import fun.wraq.series.events.dragonboat.DragonBoatFes;
import fun.wraq.series.events.labourDay.LabourDayOldCoin;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.Scoreboard;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

@Mod.EventBusSubscriber
public class LoginInEvent {
    @SubscribeEvent
    public static void loginEvent(PlayerEvent.PlayerLoggedInEvent event) throws ParseException, IOException, SQLException {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (!Security.checkModBladeList(serverPlayer)) {
                return;
            }

            Scoreboard scoreboard = player.getServer().getScoreboard();
            scoreboard.entityRemoved(player);

            CompoundTag data = player.getPersistentData();

            data.putString(StringUtils.Login.Status, StringUtils.Login.Offline);

            for (int i = 0 ; i < CompensateCommand.rewardNum ; i ++) {
                String singleReward = "singleReward" + i;
                if (data.contains(singleReward)) data.remove(singleReward);
            }

            for (int i = 1 ; i <= 12 ; i ++) {
                String dataKey = "LevelReward" + i * 5;
                data.remove(dataKey);
            }

            boolean isNewPlayer = !data.contains("FirstReward");
            String frontConditionForOldPlayer = "frontConditionForOldPlayer";
            if (isNewPlayer) data.putBoolean(frontConditionForOldPlayer, true);
            if (!data.contains(frontConditionForOldPlayer)) {
                data.putBoolean(frontConditionForOldPlayer, true);
                if (player.experienceLevel > 0) {
                    List<String> tags = List.of(NoTeamInstanceModule.AllowRewardKey.blackCastle, NoTeamInstanceModule.AllowRewardKey.nether,
                            NoTeamInstanceModule.AllowRewardKey.iceKnight, NoTeamInstanceModule.AllowRewardKey.purpleIron,
                            NoTeamInstanceModule.AllowRewardKey.devil, NoTeamInstanceModule.AllowRewardKey.moon,
                            NoTeamInstanceModule.AllowRewardKey.sakuraBoss, NoTeamInstanceModule.AllowRewardKey.blackCastle,
                            NoTeamInstanceModule.AllowRewardKey.moontainBoss);
                    tags.forEach(s -> {
                        NoTeamInstanceModule.putPlayerAllowReward(player, s, true);
                    });
                    Compute.sendFormatMSG(player, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("所有副本的前置条件已解锁").withStyle(ChatFormatting.WHITE));
                    InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.FOR_NEW.get()));
                }
            }

            String singleReward = CompensateCommand.singleReward;
            if (isNewPlayer) data.putBoolean(singleReward, true);
            if (!data.contains(singleReward)) {
                Compute.sendFormatMSG(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                        Component.literal("你有待领取的补偿，输入/vmd compensate领取补偿！").withStyle(ChatFormatting.AQUA));
            }

            String expAdjust = "2.0.37-expAdjust";
            if (isNewPlayer) data.putBoolean(expAdjust, true);
            if (!data.contains(expAdjust) && player.experienceLevel > 180) {
                data.putBoolean(expAdjust, true);
                double levelUpNeedXp = Compute.getCurrentXpLevelUpNeedXpPoint(player.experienceLevel);
                double currentXpRate = data.getDouble("Xp") / levelUpNeedXp;
                int newXpLevel = (int) (180 + (player.experienceLevel - 180) * 0.1);
                data.putInt(StringUtils.ExpLevel, newXpLevel);
                ((ServerPlayer) player).setExperienceLevels(newXpLevel);
                data.putDouble("Xp", Compute.getCurrentXpLevelUpNeedXpPoint(player.experienceLevel) * currentXpRate);
                Compute.sendFormatMSG(player, Component.literal("经验改动").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你的经验已经被改动，原因可查看群公告更新通知").withStyle(ChatFormatting.LIGHT_PURPLE));
                Compute.resetSkillAndAbility(player);
                Compute.sendFormatMSG(player, Component.literal("经验改动").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("因经验改动你的能力与专精点数已被重置").withStyle(ChatFormatting.WHITE));
            }

            List<ServerPlayer> list = event.getEntity().getServer().getPlayerList().getPlayers();
            PrefixCommand.handlePrefix(list);

            data.putDouble("DX", player.getX());
            data.putDouble("DY", player.getY());
            data.putDouble("DZ", player.getZ());
            data.putString("Name", player.getName().getString());

            for (String TickString : StringUtils.TickStringArray) {
                if (data.contains(TickString)) data.putInt(TickString, 0);
            }

            if (!data.contains(StringUtils.Swift) || !data.contains(StringUtils.MaxSwift)) {
                data.putDouble(StringUtils.Swift, 100);
                data.putDouble(StringUtils.MaxSwift, 100);
                ModNetworking.sendToClient(new SwiftSyncS2CPacket(100, 100), serverPlayer);
            }
            if (!data.contains(StringUtils.CurrentCold) || !data.contains(StringUtils.MaxCold)) {
                data.putDouble(StringUtils.CurrentCold, 0);
                data.putDouble(StringUtils.MaxCold, 100);
                ModNetworking.sendToClient(new SwiftSyncS2CPacket(100, 0), serverPlayer);
            }

            if (!data.contains("MANA") || !data.contains("MAXMANA")) {
                data.putDouble("MANA", 100);
                data.putDouble("MAXMANA", 100);
                ModNetworking.sendToClient(new ManaSyncS2CPacket(9, 100), (ServerPlayer) player);
            }
            if (!data.contains(StringUtils.SkillPoint_Total))
                data.putInt(StringUtils.SkillPoint_Total, player.experienceLevel / 2);
            if (!data.contains(StringUtils.AbilityPoint_Total))
                data.putInt(StringUtils.AbilityPoint_Total, player.experienceLevel / 2);
            if (!data.contains("ID_Card")) {
                player.addItem(ModItems.ID_CARD.get().getDefaultInstance());
                data.putBoolean("ID_Card", false);
            }

            Compute.EntropyPacketSend(player);

            if (!data.contains(StringUtils.LastDailyMissionFinishedTime)) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                data.putString(StringUtils.LastDailyMissionFinishedTime, Compute.CalendarToString(calendar));
            }
            ModNetworking.sendToClient(new DailyMissionFinishedTimeS2CPacket(data.getString(StringUtils.LastDailyMissionFinishedTime)), serverPlayer);
            if (Utils.playerReputationMissionStartTime.containsKey(serverPlayer.getName().getString()))
                ModNetworking.sendToClient(new ReputationMissionStartTimeS2CPacket(Utils.playerReputationMissionStartTime.get(serverPlayer.getName().getString())), serverPlayer);
            if (Utils.playerReputationMissionAllowRequestTime.containsKey(serverPlayer.getName().getString())) {
                ModNetworking.sendToClient(new ReputationMissionAllowRequestTimeS2CPacket(Utils.playerReputationMissionAllowRequestTime.get(serverPlayer.getName().getString())), serverPlayer);
            }

            if (!data.contains(StringUtils.Reputation)) {
                data.putInt(StringUtils.Reputation, 0);
            } else
                ModNetworking.sendToClient(new ReputationValueS2CPacket(data.getInt(StringUtils.Reputation)), serverPlayer);

            if (!data.contains(StringUtils.PlayerInstanceProgress)) data.putInt(StringUtils.PlayerInstanceProgress, 0);
            if (data.getInt(StringUtils.PlayerInstanceProgress) < 5) data.putInt(StringUtils.PlayerInstanceProgress, 5);

            Parkour.ParkourInitial(player);
            // 体力值每日4点刷新 与 悬赏重置 与 跑酷重置
            if (!data.contains(StringUtils.PsRefreshDate)) {
                Calendar calendar = Calendar.getInstance();
                data.putString(StringUtils.PsRefreshDate, Compute.CalendarToString(calendar));
                refreshDailyContent(player);
            } else {
                Calendar currentDate = Calendar.getInstance();
                Calendar lastRefreshDate = Compute.StringToCalendar(data.getString(StringUtils.PsRefreshDate));
                Calendar refreshDate = Calendar.getInstance();
                if (currentDate.get(Calendar.HOUR_OF_DAY) >= 4) {
                    refreshDate.set(Calendar.HOUR_OF_DAY, 4);
                    refreshDate.set(Calendar.MINUTE, 0);
                    refreshDate.set(Calendar.SECOND, 0);
                } else {
                    refreshDate.add(Calendar.DAY_OF_MONTH, -1);
                    refreshDate.set(Calendar.HOUR_OF_DAY, 4);
                    refreshDate.set(Calendar.MINUTE, 0);
                    refreshDate.set(Calendar.SECOND, 0);
                }
                if (lastRefreshDate.before(refreshDate)) {
                    data.putString(StringUtils.PsRefreshDate, Compute.CalendarToString(currentDate));
                    refreshDailyContent(player);
                }
            }

            Calendar calendar = Calendar.getInstance();
            // 每周刷新
            if (!data.contains(StringUtils.WeeklyRefreshWeekNum)) {
                data.putInt(StringUtils.WeeklyRefreshWeekNum, calendar.get(Calendar.WEEK_OF_YEAR));
                data.putInt(StringUtils.WeeklyRefreshYearNum, calendar.get(Calendar.YEAR));
                refreshWeeklyContent(player);
            } else {
                int weekOfYear = data.getInt(StringUtils.WeeklyRefreshWeekNum);
                int year = data.getInt(StringUtils.WeeklyRefreshYearNum);
                if (weekOfYear != calendar.get(Calendar.WEEK_OF_YEAR) || year != calendar.get(Calendar.YEAR)) {
                    data.putInt(StringUtils.WeeklyRefreshWeekNum, calendar.get(Calendar.WEEK_OF_YEAR));
                    data.putInt(StringUtils.WeeklyRefreshYearNum, calendar.get(Calendar.YEAR));
                    refreshWeeklyContent(player);
                }
            }

            if (!data.contains(StringUtils.monthlyRefreshMonthNum)) {
                data.putInt(StringUtils.monthlyRefreshMonthNum, calendar.get(Calendar.MONTH));
                data.putInt(StringUtils.monthlyRefreshYearNum, calendar.get(Calendar.YEAR));
                monthlyRefreshContent(player);
            } else {
                int month = data.getInt(StringUtils.monthlyRefreshMonthNum);
                int year = data.getInt(StringUtils.monthlyRefreshYearNum);
                if (month != calendar.get(Calendar.MONTH) || year != calendar.get(Calendar.YEAR)) {
                    data.putInt(StringUtils.monthlyRefreshMonthNum, calendar.get(Calendar.MONTH));
                    data.putInt(StringUtils.monthlyRefreshYearNum, calendar.get(Calendar.YEAR));
                    monthlyRefreshContent(player);
                }
            }

            if (!data.contains("FirstReward")) {
                InventoryOperation.giveItemStack(player, ModItems.FOR_NEW.get().getDefaultInstance());
                Compute.formatBroad(player.level(), Component.literal("维瑞阿契").withStyle(ChatFormatting.WHITE),
                        Component.literal("欢迎新地质学家").withStyle(ChatFormatting.GOLD).
                                append(player.getDisplayName()).
                                append(Component.literal("的到来！").withStyle(ChatFormatting.GOLD)));
                Compute.respawnPlayer(player);
            }
            data.putBoolean("FirstReward", true);
            ModNetworking.sendToClient(new AnimationTickResetS2CPacket(), serverPlayer);

            if (Utils.playerReputationMissionContent.containsKey(player.getName().getString())
                    && Utils.playerReputationMissionContentNum.containsKey(player.getName().getString())
                    && Utils.playerReputationMissionContentNum.get(player.getName().getString()) != 0)
                ModNetworking.sendToClient(new ReputationMissionContentS2CPacket(
                        Utils.playerReputationMissionContent.get(serverPlayer.getName().getString())
                        , Utils.playerReputationMissionContentNum.get(serverPlayer.getName().getString())), serverPlayer);

            if (Utils.playerDailyMissionContent.containsKey(player.getName().getString())
                    && Utils.playerDailyMissionContentNum.containsKey(player.getName().getString())
                    && Utils.playerDailyMissionContentNum.get(player.getName().getString()) != 0)
                ModNetworking.sendToClient(new DailyMissionContentS2CPacket(
                        Utils.playerDailyMissionContent.get(player.getName().getString()),
                        Utils.playerDailyMissionContentNum.get(player.getName().getString())), serverPlayer);

            if (data.contains(StringUtils.ResonanceType))
                Element.PlayerResonanceType.put(player, data.getString(StringUtils.ResonanceType));

            String towerStatus = Tower.getPlayerStatus(player);
            ModNetworking.sendToClient(new TowerStatusS2CPacket(towerStatus), serverPlayer);

            DailySupply.sendStatusToClient(player);
            VpDataHandler.sendPlayerVpValue(player);

            if (data.contains(QuickUseHud.DISPLAY_KEY)) {
                ModNetworking.sendToClient(
                        new QuickUseDisplayS2CPacket(data.getInt(QuickUseHud.DISPLAY_KEY)), serverPlayer);
            }

            if (!serverPlayer.getName().getString().equals("very_H")) {
                ModNetworking.sendToClient(new MacRequestS2CPacket(),serverPlayer);
            }

            SingleItemChangePurchaseLimit.sendAllRecipeTimes(player);
            RankData.onPlayerLogin(player);
            Reason.sendReasonValuePacketToClient(player);
            MobKillEntrustment.sendPacketToClient(player);
            Guide.sendGuideCloseStatusToClient(player);
            SkillV2.syncSkillV2Data(player);
            SkillV2.sendInfoToClient(player);
            SkillV2.afterVerUpdateLogin(player);
            SpringMobEvent.onPlayerLogin(player);
            PlanPlayer.onPlayerLoginSync(player);
            MobSpawn.onPlayerLoginSync(player);
            NewLotteries.sendLotteryRewardTimes(player);

            String enhanceForge = "enhanceForgeEquipAdjust";
            if (!data.contains(enhanceForge)) {
                data.putBoolean(enhanceForge, true);
                for (int i = 0 ; i < player.getInventory().getContainerSize() ; i ++) {
                    ItemStack stack = player.getInventory().getItem(i);
                    Item item = stack.getItem();
                    if (getEnhanceForgeEquipMap().containsKey(item)) {
                        Item itemE = getEnhanceForgeEquipMap().get(item);
                        ItemStack stackE = new ItemStack(itemE);
                        stackE.getOrCreateTagElement(Utils.MOD_ID).merge(stack.getOrCreateTagElement(Utils.MOD_ID));
                        stackE.resetHoverName();
                        Compute.sendFormatMSG(player, Te.s("更新", CustomStyle.styleOfFlexible),
                                Te.s(stack, "已被替换为", stackE));
                        player.getInventory().setItem(i, stackE);
                    }
                }
            }

            WraqCurios.shrinkOtherModSlot(serverPlayer);
            LabourDayOldCoin.onPlayerLoginTips(player);
            EstatePlayerData.onLogin(player);
            CookingPlayerData.sendCookingExpToClient(player);
            SevenShadePiece.sendDataToClient(player);
            DragonBoatFes.onLogin(player);
            WeeklyStorePlayerData.sendDataToClient(player);

            // 更新检查，放在最后吧
            ModNetworking.sendToClient(new VersionCheckS2CPacket(), serverPlayer);
        }
    }

    public static Map<Item, Item> enhanceForgeEquipMap = new HashMap<>();
    public static Map<Item, Item> getEnhanceForgeEquipMap() {
        if (enhanceForgeEquipMap.isEmpty()) {
            enhanceForgeEquipMap.put(ModItems.NETHER_SCEPTRE.get(), ModItems.NETHER_SCEPTRE_E.get());
            enhanceForgeEquipMap.put(ModItems.NETHER_SWORD.get(), ModItems.NETHER_SWORD_E.get());
            enhanceForgeEquipMap.put(ModItems.NETHER_KNIFE.get(), ModItems.NETHER_KNIFE_E.get());
            enhanceForgeEquipMap.put(ModItems.MOON_SWORD.get(), ModItems.MOON_SWORD_E.get());
            enhanceForgeEquipMap.put(ModItems.MOON_BOW.get(), ModItems.MOON_BOW_E.get());
            enhanceForgeEquipMap.put(ModItems.MOON_SCEPTRE.get(), ModItems.MOON_SCEPTRE_E.get());
            enhanceForgeEquipMap.put(ModItems.ICE_SWORD.get(), ModItems.ICE_SWORD_E.get());
            enhanceForgeEquipMap.put(ModItems.ICE_BOW.get(), ModItems.ICE_BOW_E.get());
            enhanceForgeEquipMap.put(ModItems.ICE_SCEPTRE.get(), ModItems.ICE_SCEPTRE_E.get());
            enhanceForgeEquipMap.put(ModItems.CASTLE_SWORD.get(), ModItems.CASTLE_SWORD_E.get());
            enhanceForgeEquipMap.put(ModItems.CASTLE_BOW.get(), ModItems.CASTLE_BOW_E.get());
            enhanceForgeEquipMap.put(ModItems.CASTLE_SCEPTRE.get(), ModItems.CASTLE_SCEPTRE_E.get());
        }
        return enhanceForgeEquipMap;
    }

    public static WeakHashMap<Player, Integer> newPlayerMSGDelay1 = new WeakHashMap<>();


    public static void newMSGSend(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.side.isServer()) {
            int tick = Tick.get();
            newPlayerMSGDelay1.forEach(((player, integer) -> {
                if (integer < tick) {
                    Compute.sendFormatMSG(player, Component.literal("欢迎").withStyle(ChatFormatting.AQUA),
                            Component.literal("欢迎新人！可以参考群文件内玩家编写的教程, 游玩过程有任何建议或问题欢迎在群里@群主或管理员！").withStyle(ChatFormatting.WHITE));
                    MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
                }
            }));
            newPlayerMSGDelay1.entrySet().removeIf(entry -> entry.getValue() < tick);
        }
    }

    @SubscribeEvent
    public static void LogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            BlockEvent.removePlayerLimit(player);
            // 方块内容返还
            BlockEvent.PlayerLogOut(player); // 挖掘 放置方块重置

            Utils.recallList.forEach(recall -> {
                if (recall != null && recall.recallPlayer != null) {
                    if (recall.recallPlayer.equals(serverPlayer)) {
                        Compute.formatBroad(player.level(), Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(Utils.spiderRecall.playerName).
                                        append(Component.literal("尝试回想其在").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(recall.zoneName).withStyle(recall.style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        System.out.println("reset!");
                        recall.Reset();
                    }
                }
            });
            ModNetworking.sendToClient(new TeamInfoResetS2CPacket(), serverPlayer);
            CompoundTag data = serverPlayer.getPersistentData();
            data.putString(StringUtils.Login.Status, StringUtils.Login.Offline);
            Utils.IpArrayList.remove(serverPlayer.getIpAddress());
            for (String TickString : StringUtils.TickStringArray) {
                if (data.contains(TickString)) data.putInt(TickString, 0);
            }

            if (Utils.playerTeamMap.containsKey(player)) {
                PlayerTeam playerTeam = Utils.playerTeamMap.get(player);
                if (playerTeam.getTeamLeader().equals(player)) {

                    Utils.TeamInvitePlayerMap.keySet().forEach(player1 -> {
                        List<PlayerTeam> playerTeamList = Utils.TeamInvitePlayerMap.get(player1);
                        playerTeamList.remove(playerTeam);
                    });

                    Utils.PlayerRequestTeamMap.keySet().forEach(player1 -> {
                        List<PlayerTeam> playerTeamList = Utils.PlayerRequestTeamMap.get(player1);
                        playerTeamList.remove(playerTeam);
                    });


                    playerTeam.getPlayerList().forEach(player1 -> {
                        if (player1 != player) {
                            Utils.playerTeamMap.remove(player1);
                            player1.sendSystemMessage(Component.literal("您所在的队伍已解散。"));
                            ModNetworking.sendToClient(new ScreenSetS2CPacket(0), (ServerPlayer) player1);
                        }
                    });

                    Utils.playerTeamMap.remove(player);

                    player.sendSystemMessage(Component.literal("队伍已解散。"));
                } else {
                    Player teamLeader = Utils.playerTeamMap.get(player).getTeamLeader();
                    String teamName = Utils.playerTeamMap.get(player).getTeamName();
                    Utils.playerTeamMap.get(player).removePlayer(player);
                    Utils.playerTeamMap.remove(player);

                    Utils.playerTeamMap.get(teamLeader).getPlayerList().forEach(player1 -> {
                        Utils.playerTeamMap.put(player1, Utils.playerTeamMap.get(teamLeader));
                        player1.sendSystemMessage(Component.literal("").
                                append(player.getDisplayName()).
                                append(Component.literal("离开了队伍。")));
                    });

                    teamLeader.sendSystemMessage(Component.literal("").append(player.getDisplayName()).
                            append(Component.literal("离开了你的队伍。").withStyle(ChatFormatting.WHITE)));
                    player.sendSystemMessage(Component.literal("您已退出").withStyle(ChatFormatting.WHITE).
                            append(Component.literal("" + teamName)));

                }
            }
            Utils.TeamInvitePlayerMap.remove(player);
            Utils.PlayerRequestTeamMap.remove(player);
            player.getServer().getPlayerList().getPlayers().forEach(player1 -> {
                ModNetworking.sendToClient(new TeamInfoResetS2CPacket(), player1);
            });
            NewTeamInstanceHandler.getInstances().forEach(instance -> {
                if (instance.players.contains(player)) {
                    ++instance.deadTimes;
                }
            });
            Utils.playerNameMap.put(player.getName().getString(), player.getDisplayName());
            Tower.playerInChallengingDeadOrLogout(player);

            NewTeamInstanceHandler.getInstances().forEach(newTeamInstance -> {
                newTeamInstance.players.remove(player);
                if (newTeamInstance.players.isEmpty()) newTeamInstance.clear();
            });

            MacServer.onLogOut(serverPlayer);
            Civil.onPlayerLogOut(serverPlayer);
        }
    }

    public static void refreshDailyContent(Player player) throws IOException {
        CompoundTag data = player.getPersistentData();
        Reason.setPlayerReasonValue(player, Reason.getPlayerReasonUpperLimit(player));
        Utils.playerReputationMissionPunishLevel.remove(player.getName().getString());
        Parkour.ParkourReset(player);
        Utils.playerDailyMissionContent.remove(player.getName().getString());
        Utils.playerDailyMissionContentNum.remove(player.getName().getString());
        data.putBoolean(StringUtils.DailyInstance.DailyLogInstance, true);
        data.putBoolean(StringUtils.DailyInstance.DailyMineInstance, true);
        data.putBoolean(StringUtils.DailyInstance.DailyCropInstance, true);
        data.putString(StringUtils.DailyInstanceCode, StringUtils.DailyInstanceCode0);
        data.putInt(StringUtils.OriginElementGetTimes, 0);
        Compute.sendFormatMSG(player, Component.literal("日常").withStyle(CustomStyle.styleOfHealth),
                Component.literal(" 你的日常活动已被刷新！").withStyle(ChatFormatting.WHITE));
        Tower.resetData(player);
        sunPowerGetCount.put(player.getName().getString(), 0);
        lakeCoreGetCount.put(player.getName().getString(), 0);
        volcanoCoreGetCount.put(player.getName().getString(), 0);
        /*SummerEvent.resetDailyData(player);*/
        SingleItemChangePurchaseLimit.refreshDaily(player);
        RandomEventData.resetWorldSoul5DailyGetTimes(player);
        MobKillEntrustment.setDailyFinishedTimes(player, 0);
        BondDividends.setAllowGetDividends(player, true);
        SmithPlayerData.setDailyReward(player, true);
        MissionV2Helper.onResetDailyContent(player);
        ManaTowerData.resetDailyRecord(player);
        LabourDayOldCoin.refreshSilverCoinGetCount(player);
        ManaTowerData.setManaTowerPieceDailyGetFlag(player, false);
        CookingPlayerData.resetDailyFinishedTimesCount(player);
        RankData.onPlayerDailyLogin(player);
    }

    public static void refreshWeeklyContent(Player player) {
        SingleItemChangePurchaseLimit.refreshWeekly(player);
        MobKillEntrustment.setWeeklyFinishedTimes(player, 0);
    }

    public static void monthlyRefreshContent(Player player) {
        BonusChestPlayerData.resetAllZoneInfo(player);
    }

    public static boolean playerDailyInstanceReward(Player player, int instanceIndex) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(StringUtils.DailyInstanceCode))
            data.putString(StringUtils.DailyInstanceCode, StringUtils.DailyInstanceCode0);
        String s = data.getString(StringUtils.DailyInstanceCode);
        StringBuilder sb = new StringBuilder(s);
        char c = sb.charAt(instanceIndex);
        if (c == '0') {
            sb.setCharAt(instanceIndex, '1');
            data.putString(StringUtils.DailyInstanceCode, sb.toString());
            return true;
        } else return false;
    }

    public static Map<String, Integer> sunPowerGetCount = new HashMap<>();
    public static Map<String, Integer> lakeCoreGetCount = new HashMap<>();
    public static Map<String, Integer> volcanoCoreGetCount = new HashMap<>();
}
