package com.very.wraq.events.core;

import com.very.wraq.blocks.entity.ForgingBlockEntity;
import com.very.wraq.blocks.entity.FurnaceEntity;
import com.very.wraq.blocks.entity.HBrewingEntity;
import com.very.wraq.blocks.entity.InjectBlockEntity;
import com.very.wraq.commands.changeable.CompensateCommand;
import com.very.wraq.commands.changeable.PrefixCommand;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Struct.PlayerTeam;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.events.instance.PurpleIronKnight;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.VersionCheckS2CPacket;
import com.very.wraq.networking.dailyMission.DailyMissionContentS2CPacket;
import com.very.wraq.networking.dailyMission.DailyMissionFinishedTimeS2CPacket;
import com.very.wraq.networking.misc.AnimationPackets.AnimationTickResetS2CPacket;
import com.very.wraq.networking.misc.ManaSyncS2CPacket;
import com.very.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import com.very.wraq.networking.misc.TeamPackets.TeamInfoResetS2CPacket;
import com.very.wraq.networking.reputation.ReputationValueS2CPacket;
import com.very.wraq.networking.reputationMission.ReputationMissionAllowRequestTimeS2CPacket;
import com.very.wraq.networking.reputationMission.ReputationMissionContentS2CPacket;
import com.very.wraq.networking.reputationMission.ReputationMissionStartTimeS2CPacket;
import com.very.wraq.networking.unSorted.ClientLimitSetS2CPacket;
import com.very.wraq.networking.unSorted.PlayerCallBack;
import com.very.wraq.networking.unSorted.SwiftSyncS2CPacket;
import com.very.wraq.process.func.plan.DailySupply;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.endlessinstance.EndlessInstanceItems;
import com.very.wraq.process.system.missions.series.dailyMission.DailyMission;
import com.very.wraq.process.system.parkour.Parkour;
import com.very.wraq.process.system.teamInstance.NewTeamInstanceEvent;
import com.very.wraq.process.system.tower.Tower;
import com.very.wraq.process.system.tower.TowerStatusS2CPacket;
import com.very.wraq.process.system.vp.VpDataHandler;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.specialevents.summer.SummerEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.scores.Scoreboard;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.patchouli.api.PatchouliAPI;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class LoginInEvent {
    @SubscribeEvent
    public static void loginEvent(PlayerEvent.PlayerLoggedInEvent event) throws ParseException, IOException, SQLException {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ModNetworking.sendToClient(new TeamInfoResetS2CPacket(), serverPlayer);

            Scoreboard scoreboard = player.getServer().getScoreboard();
            scoreboard.entityRemoved(player);

            ModNetworking.sendToClient(new ClientLimitSetS2CPacket(serverPlayer.getName().getString()), serverPlayer);
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("欢迎来到 ").withStyle(ChatFormatting.WHITE).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA))));
            CompoundTag data = player.getPersistentData();

            data.putString(StringUtils.Login.Status, StringUtils.Login.Offline);

            for (int i = 0 ; i < 13 ; i ++) {
                String singleReward = "singleReward" + i;
                if (data.contains(singleReward)) data.remove(singleReward);
            }

            String singleReward = CompensateCommand.singleReward;
            if (!data.contains(singleReward)) {
                Compute.sendFormatMSG(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                        Component.literal("你有待领取的补偿，输入/vmd compensate领取补偿！").withStyle(ChatFormatting.AQUA));
            }

            if (CompensateCommand.singleReward.equals("singleReward13")) {
                Compute.sendFormatMSG(player, Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                        Component.literal("中断服务补偿，已提供").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("50%额外产出").withStyle(ChatFormatting.GOLD)));
            }

            String expAdjust = "24.8.1-expAdjust";
            if (!data.contains(expAdjust) && player.experienceLevel > 180) {
                double levelUpNeedXp = Math.pow(Math.E, 3 + (player.experienceLevel / 100d) * 7);
                double currentXpRate = data.getDouble("Xp") / levelUpNeedXp;
                int newXpLevel = (int) (180 + (player.experienceLevel - 180) * 0.5);
                data.putInt(StringUtils.ExpLevel, newXpLevel);
                ((ServerPlayer) player).setExperienceLevels(newXpLevel);
                data.putDouble("Xp", Math.pow(Math.E, 3 + (player.experienceLevel / 100d) * 7) * currentXpRate);
                data.putBoolean(expAdjust, true);
                Compute.sendFormatMSG(player, Component.literal("经验改动").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你的经验已经被改动，原因可查看群公告更新通知").withStyle(ChatFormatting.LIGHT_PURPLE));
                Compute.resetSkillAndAbility(player);
            }

            String skillPointReset = "skillPointReset";
            if (!data.contains(skillPointReset)) {
                data.putBoolean(skillPointReset, true);
                Compute.resetSkillAndAbility(player);
                Compute.sendFormatMSG(player, Component.literal("经验改动").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("因经验改动你的能力与专精点数已被重置").withStyle(ChatFormatting.WHITE));
            }

            if (!data.contains(StringUtils.PatchouliBook)) {
                ItemStack PatchouliBook = PatchouliAPI.get().getBookStack(new ResourceLocation(Utils.MOD_ID, "guide"));
                Compute.itemStackGive(player, PatchouliBook);
                data.putBoolean(StringUtils.PatchouliBook, true);
            }

/*            if (!data.contains("version:1.0.3") && player.experienceLevel >= 200) {
                Compute.FormatMSGSend(player,Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                        Component.literal("你有待领取的补偿，输入/vmd compensate [life/water/fire/stone/iceElement/lightning/wind/ice/devil/taboo/moon/castle/purple]领取补偿！").withStyle(ChatFormatting.AQUA));
            }

            if (!data.contains("version:1.0.3") && player.experienceLevel >= 160) {
                Compute.FormatMSGSend(player,Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                        Component.literal("你有待领取的补偿，输入/vmd compensate [ice/devil/taboo/moon/castle/purple]领取补偿！").withStyle(ChatFormatting.AQUA));
            }*/

            for (int i = 0; i < Utils.AttributeName.length; i++) {
                if (data.contains(Utils.AttributeName[i])) {
                    data.putDouble(Utils.AttributeName[i], 0);
                }
            }

            for (String string : StringUtils.Crest.CrestList) {
                if (data.contains(string)) data.putInt(string, 0);
            }

            List<ServerPlayer> list = event.getEntity().getServer().getPlayerList().getPlayers();
            PrefixCommand.handlePrefix(list);

/*            if (Utils.IpArrayList.contains(serverPlayer.getIpAddress())) {
                serverPlayer.connection.disconnect(Component.literal("同一个IP已经有账户在线了。").withStyle(ChatFormatting.RED));
            }
            else {
                Utils.IpArrayList.add(serverPlayer.getIpAddress());
            }*/

/*            if (Utils.IpLoginMap.containsKey(serverPlayer.getName().getString()) && Utils.IpLoginMap.get(serverPlayer.getName().getString()).equals(serverPlayer.getIpAddress())) {
                data.putString(StringUtils.Login.Status,StringUtils.Login.Online);
                Compute.FormatMSGSend(player,Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                        Component.literal("欢迎回到维瑞阿契！已为您自动登录。").withStyle(ChatFormatting.WHITE));
            }*/

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
                data.putDouble(StringUtils.SkillPoint_Total, player.experienceLevel);
            if (!data.contains(StringUtils.AbilityPoint_Total))
                data.putDouble(StringUtils.AbilityPoint_Total, player.experienceLevel);
            if (!data.contains("ID_Card")) {
                player.addItem(ModItems.ID_Card.get().getDefaultInstance());
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
                DailyRefreshContent(player);
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
                    DailyRefreshContent(player);
                }
            }

            Calendar calendar = Calendar.getInstance();
            // 每周刷新
            if (!data.contains(StringUtils.WeeklyRefreshWeekNum)) {
                data.putInt(StringUtils.WeeklyRefreshWeekNum, calendar.get(Calendar.WEEK_OF_YEAR));
                data.putInt(StringUtils.WeeklyRefreshYearNum, calendar.get(Calendar.YEAR));
                WeeklyRefreshContent(player);
            } else {
                int weekOfYear = data.getInt(StringUtils.WeeklyRefreshWeekNum);
                int year = data.getInt(StringUtils.WeeklyRefreshYearNum);
                if (weekOfYear != calendar.get(Calendar.WEEK_OF_YEAR) || year != calendar.get(Calendar.YEAR)) {
                    data.putInt(StringUtils.WeeklyRefreshWeekNum, calendar.get(Calendar.WEEK_OF_YEAR));
                    data.putInt(StringUtils.WeeklyRefreshYearNum, calendar.get(Calendar.YEAR));
                    WeeklyRefreshContent(player);
                }
            }

            if (!data.contains("FirstReward")) {
                Compute.itemStackGive(player, ModItems.ForNew.get().getDefaultInstance());
                Compute.formatBroad(player.level(), Component.literal("维瑞阿契").withStyle(ChatFormatting.WHITE),
                        Component.literal("欢迎新地质学家").withStyle(ChatFormatting.GOLD).
                                append(player.getDisplayName()).
                                append(Component.literal("的到来！").withStyle(ChatFormatting.GOLD)));
                newPlayerMSGDelay2.put(player, player.getServer().getTickCount() + 100);
                Compute.respawnPlayer(player);
            }
            data.putBoolean("FirstReward", true);

/*            if(!player.getTags().contains("player")) player.addTag("player");
            if (data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline)) {
                if (data.contains(StringUtils.Login.Password)) {
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("使用/vmd login (密码)来登录").withStyle(ChatFormatting.WHITE)));
                }
                else {
                    if(!data.contains("FirstReward")) {
                        Compute.ItemStackGive(player,ModItems.ForNew.get().getDefaultInstance());
                    }
                    data.putBoolean("FirstReward",true);
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("使用/vmd register (密码)来注册").withStyle(ChatFormatting.WHITE)));
                }
            } //一共四处 出售 交互 丢弃 销毁*/
            ModNetworking.sendToClient(new AnimationTickResetS2CPacket(), serverPlayer);
            ModNetworking.sendToClient(new VersionCheckS2CPacket(), serverPlayer);

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
            if (towerStatus != null)
                ModNetworking.sendToClient(new TowerStatusS2CPacket(towerStatus), serverPlayer);
            else Tower.putPlayerStatus(player, towerStatus);

            DailySupply.sendStatusToClient(player);
            VpDataHandler.sendPlayerVpValue(player);
        }
    }

    public static Map<Player, Integer> newPlayerMSGDelay1 = new HashMap<>();
    public static Map<Player, Integer> newPlayerMSGDelay2 = new HashMap<>();
    public static Map<Player, Integer> newPlayerMSGDelay3 = new HashMap<>();

    public static void newMSGSend(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.side.isServer()) {
            int tick = event.getServer().getTickCount();
            newPlayerMSGDelay1.forEach(((player, integer) -> {
                if (integer < tick) {
                    Compute.sendFormatMSG(player, Component.literal("欢迎").withStyle(ChatFormatting.AQUA),
                            Component.literal("欢迎新人！新手教程请查看群文件内玩家编写的教程或查阅游戏内的帕秋莉手册(维瑞阿契wiki),游玩过程有任何建议或问题欢迎在群里@群主或管理员！").withStyle(ChatFormatting.WHITE));
                    Compute.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
                    newPlayerMSGDelay2.put(player, tick + 100);
                }
            }));
            newPlayerMSGDelay1.entrySet().removeIf(entry -> entry.getValue() < tick);

            newPlayerMSGDelay2.forEach(((player, integer) -> {
                if (integer < tick) {
                    Compute.sendFormatMSG(player, Component.literal("欢迎").withStyle(ChatFormatting.AQUA),
                            Component.literal("您可以先打开身份卡，点击物品图鉴，浏览由制作者编写的各种装备，找到心仪的装备制作吧！").withStyle(ChatFormatting.GOLD));
                    Compute.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
                    newPlayerMSGDelay3.put(player, tick + 100);
                }
            }));
            newPlayerMSGDelay2.entrySet().removeIf(entry -> entry.getValue() < tick);

            newPlayerMSGDelay3.forEach(((player, integer) -> {
                if (integer < tick) {
                    Compute.sendFormatMSG(player, Component.literal("欢迎").withStyle(ChatFormatting.AQUA),
                            Component.literal("推荐您打开任务界面，完成游览地图任务，默认按P键，若按键冲突，请前往按键绑定，找到维瑞阿契按键修改。").withStyle(ChatFormatting.GOLD));
                    Compute.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
                }
            }));
            newPlayerMSGDelay3.entrySet().removeIf(entry -> entry.getValue() < tick);

        }
    }

    @SubscribeEvent
    public static void LogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            for (PlayerCallBack playerCallBack : Utils.playerCallBackList) {
                if (playerCallBack.getPlayer().getName().getString().equals(player.getName().getString())) {
                    BlockEntity blockEntity = player.level().getBlockEntity(playerCallBack.getBlockPos());
                    if (blockEntity instanceof ForgingBlockEntity forgingBlockEntity) forgingBlockEntity.drops(player);
                    if (blockEntity instanceof HBrewingEntity hBrewingEntity) hBrewingEntity.drops(player);
                    if (blockEntity instanceof InjectBlockEntity injectBlockEntity) injectBlockEntity.drops(player);
                    if (blockEntity instanceof FurnaceEntity furnaceEntity) furnaceEntity.drops(player);
                    Utils.playerCallBackList.remove(playerCallBack);
                    break;
                }
            } // 方块内容返还
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
            data.putInt("SLTIME", -1);
            data.putInt("PFTIME", -1);
            data.putInt("SVTIME", -1);
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
            Utils.instanceList.forEach(instance -> {
                if (instance.getCurrentChallengePlayerTeam() != null && instance.getCurrentChallengePlayerTeam().getPlayerList().contains(player)) {
                    instance.addDeadTimes();
                }
            });
            Utils.playerNameMap.put(player.getName().getString(), player.getDisplayName());
            Tower.playerInChallengingDeadOrLogout(player);

            NewTeamInstanceEvent.getOverworldInstances().forEach(newTeamInstance -> {
                newTeamInstance.players.remove(player);
                if (newTeamInstance.players.isEmpty()) newTeamInstance.clear();
            });
        }
    }

    public static void DailyRefreshContent(Player player) throws IOException {
        CompoundTag data = player.getPersistentData();
        Compute.addOrCostPlayerPsValue(player, 100);
        Utils.playerReputationMissionPunishLevel.remove(player.getName().getString());
        Parkour.ParkourReset(player);
        Utils.playerDailyMissionContent.remove(player.getName().getString());
        Utils.playerDailyMissionContentNum.remove(player.getName().getString());
        data.putBoolean(StringUtils.DailyInstance.DailyLogInstance, true);
        data.putBoolean(StringUtils.DailyInstance.DailyMineInstance, true);
        data.putBoolean(StringUtils.DailyInstance.DailyCropInstance, true);
        data.putString(StringUtils.DailyInstanceCode, StringUtils.DailyInstanceCode0);
        PurpleIronKnight.RefreshRewardGetTimes(player); //
        data.putInt(StringUtils.OriginElementGetTimes, 0);
        Compute.sendFormatMSG(player, Component.literal("日常").withStyle(CustomStyle.styleOfHealth),
                Component.literal(" 你的日常活动已被刷新！").withStyle(ChatFormatting.WHITE));
        Tower.resetData(player);
        DailyMission.resetData(player);

        sunPowerGetCount.put(player.getName().getString(), 0);
        lakeCoreGetCount.put(player.getName().getString(), 0);
        volcanoCoreGetCount.put(player.getName().getString(), 0);
        SummerEvent.resetDailyData(player);
        Compute.itemStackGive(player, new ItemStack(EndlessInstanceItems.EASTERN_TOWER_PAPER.get(), 3));
    }

    public static void WeeklyRefreshContent(Player player) {

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
