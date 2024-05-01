package com.very.wraq.events.core;

import com.very.wraq.blocks.entity.ForgingBlockEntity;
import com.very.wraq.blocks.entity.FurnaceEntity;
import com.very.wraq.blocks.entity.HBrewingEntity;
import com.very.wraq.blocks.entity.InjectBlockEntity;
import com.very.wraq.customized.players.bow.Wcndymlgb.WcndymlgbCurios;
import com.very.wraq.customized.players.sceptre.Black_Feisa_.BlackFeisaCurios3;
import com.very.wraq.events.instance.PurpleIronKnight;
import com.very.wraq.Items.Prefix.PrefixInfo;
import com.very.wraq.netWorking.dailyMission.DailyMissionContentS2CPacket;
import com.very.wraq.netWorking.dailyMission.DailyMissionFinishedTimeS2CPacket;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.AnimationPackets.AnimationTickResetS2CPacket;
import com.very.wraq.netWorking.misc.ManaSyncS2CPacket;
import com.very.wraq.netWorking.misc.PrefixPackets.PrefixS2CPacket;
import com.very.wraq.netWorking.misc.TeamPackets.ScreenSetS2CPacket;
import com.very.wraq.netWorking.misc.TeamPackets.TeamInfoResetS2CPacket;
import com.very.wraq.netWorking.reputation.ReputationValueS2CPacket;
import com.very.wraq.netWorking.reputationMission.ReputationMissionAllowRequestTimeS2CPacket;
import com.very.wraq.netWorking.reputationMission.ReputationMissionContentS2CPacket;
import com.very.wraq.netWorking.reputationMission.ReputationMissionStartTimeS2CPacket;
import com.very.wraq.netWorking.unSorted.ClientLimitSetS2CPacket;
import com.very.wraq.netWorking.unSorted.PlayerCallBack;
import com.very.wraq.netWorking.unSorted.SwiftSyncS2CPacket;
import com.very.wraq.netWorking.VersionCheckS2CPacket;
import com.very.wraq.process.element.Element;
import com.very.wraq.process.missions.series.labourDay.LabourDayMission;
import com.very.wraq.process.parkour.Parkour;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Struct.PlayerTeam;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.patchouli.api.PatchouliAPI;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

@Mod.EventBusSubscriber
public class LoginInEvent {
    @SubscribeEvent
    public static void Login0(PlayerEvent.PlayerLoggedInEvent event) throws ParseException, IOException {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            ServerPlayer serverPlayer1 = (ServerPlayer) player;
            ModNetworking.sendToClient(new TeamInfoResetS2CPacket(),serverPlayer1);

            ModNetworking.sendToClient(new ClientLimitSetS2CPacket(serverPlayer1.getName().getString()),serverPlayer1);
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("欢迎来到 ").withStyle(ChatFormatting.WHITE).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA))));
            CompoundTag data = player.getPersistentData();

            data.putString(StringUtils.Login.Status,StringUtils.Login.Offline);

            if (!data.contains(StringUtils.PatchouliBook)) {
                ItemStack PatchouliBook = PatchouliAPI.get().getBookStack(new ResourceLocation(Utils.MOD_ID,"guide"));
                Compute.ItemStackGive(player,PatchouliBook);
                data.putBoolean(StringUtils.PatchouliBook,true);
            }

            if (data.contains("VB") && data.getDouble("VB") > 100000000) data.putDouble("VB",0);

            if (!data.contains("RefreshPs")) {
                if (data.getInt(StringUtils.PsValue) > 100) {
                    data.putInt(StringUtils.PsValue, 100 + (data.getInt(StringUtils.PsValue) - 100) / 10);
                }
                data.putBoolean("RefreshPs",true);
            }

            if (!data.contains("version:1.0.0") && player.experienceLevel >= 200) {
                Compute.FormatMSGSend(player,Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                        Component.literal("你有待领取的补偿，输入/vmd compensate [life/water/fire/stone/iceElement/lightning/wind/ice/devil/taboo/moon/castle/purple]领取补偿！").withStyle(ChatFormatting.AQUA));
            }

            if (!data.contains("version:1.0.0") && player.experienceLevel >= 160) {
                Compute.FormatMSGSend(player,Component.literal("补偿").withStyle(CustomStyle.styleOfSakura),
                        Component.literal("你有待领取的补偿，输入/vmd compensate [ice/devil/taboo/moon/castle/purple]领取补偿！").withStyle(ChatFormatting.AQUA));
            }

            if (data.contains("version:4.10.0")) data.remove("version:4.10.0");
            if (data.contains("version:4.12.0")) data.remove("version:4.12.0");
            if (data.contains("version:4.13.0")) data.remove("version:4.13.0");
            if (data.contains("version:4.15.0")) data.remove("version:4.15.0");

            for(int i = 0; i < Utils.AttributeName.length; i++){
                if(data.contains(Utils.AttributeName[i])){
                    data.putDouble(Utils.AttributeName[i],0);
                }
            }

            for (String string : StringUtils.Crest.CrestList) {
                if (data.contains(string)) data.putInt(string,0);
            }



            List<ServerPlayer> list = event.getEntity().getServer().getPlayerList().getPlayers();
            for (ServerPlayer serverPlayer : list) {
                CompoundTag TmpData = serverPlayer.getPersistentData();
                String Prefix = "初来乍到";
                if (TmpData.contains("Prefix")) Prefix = TmpData.getString("Prefix");
                PrefixInfo prefixInfo = new PrefixInfo(Prefix, serverPlayer.experienceLevel);
                Utils.prefixInfoMap.put(serverPlayer.getName().getString(), prefixInfo);
            }

            for (ServerPlayer serverPlayer : list) {
                for (String playerName : Utils.prefixInfoMap.keySet()) {
                    ModNetworking.sendToClient(new PrefixS2CPacket(playerName, Utils.prefixInfoMap.get(playerName).getPrefix(), Utils.prefixInfoMap.get(playerName).getLevel()), serverPlayer);
                }
            }


            ServerPlayer serverPlayer = (ServerPlayer) player;
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

            data.putDouble("DX",player.getX());
            data.putDouble("DY",player.getY());
            data.putDouble("DZ",player.getZ());
            data.putString("Name",player.getName().getString());

            for (String TickString : StringUtils.TickStringArray) {
                if (data.contains(TickString)) data.putInt(TickString,0);
            }

            if (!data.contains("Green")) data.putInt("Green",-1);
            if (!data.contains(StringUtils.ForestRune.ForestRune)) data.putInt(StringUtils.ForestRune.ForestRune,-1);
            if (!data.contains("volcanoRune")) data.putInt("volcanoRune",-1);
            if (!data.contains("ManaRune")) data.putInt("ManaRune",-1);
            if (!data.contains(StringUtils.Swift) || !data.contains(StringUtils.MaxSwift)) {
                data.putDouble(StringUtils.Swift,100);
                data.putDouble(StringUtils.MaxSwift,100);
                ModNetworking.sendToClient(new SwiftSyncS2CPacket(100,100),serverPlayer);
            }
            if (!data.contains(StringUtils.CurrentCold) || !data.contains(StringUtils.MaxCold)) {
                data.putDouble(StringUtils.CurrentCold,0);
                data.putDouble(StringUtils.MaxCold,100);
                ModNetworking.sendToClient(new SwiftSyncS2CPacket(100,0),serverPlayer);
            }

            if (!data.contains("MANA") || !data.contains("MAXMANA")) {
                data.putDouble("MANA",100);
                data.putDouble("MAXMANA",100);
                ModNetworking.sendToClient(new ManaSyncS2CPacket(9,100),(ServerPlayer) player);
            }
            if (!data.contains(StringUtils.SkillPoint_Total)) data.putDouble(StringUtils.SkillPoint_Total,player.experienceLevel);
            if (!data.contains(StringUtils.AbilityPoint_Total)) data.putDouble(StringUtils.AbilityPoint_Total,player.experienceLevel);
            if (!data.contains("ID_Card")) {
                player.addItem(ModItems.ID_Card.get().getDefaultInstance());
                data.putBoolean("ID_Card",false);
            }

            Compute.EntropyPacketSend(player);

            if (!data.contains(StringUtils.LastDailyMissionFinishedTime)) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH,-1);
                data.putString(StringUtils.LastDailyMissionFinishedTime,Compute.CalendarToString(calendar));
            }
            ModNetworking.sendToClient(new DailyMissionFinishedTimeS2CPacket(data.getString(StringUtils.LastDailyMissionFinishedTime)),serverPlayer);
            if (Utils.playerReputationMissionStartTime.containsKey(serverPlayer.getName().getString()))
                ModNetworking.sendToClient(new ReputationMissionStartTimeS2CPacket(Utils.playerReputationMissionStartTime.get(serverPlayer.getName().getString())),serverPlayer);
            if (Utils.playerReputationMissionAllowRequestTime.containsKey(serverPlayer.getName().getString())) {
                ModNetworking.sendToClient(new ReputationMissionAllowRequestTimeS2CPacket(Utils.playerReputationMissionAllowRequestTime.get(serverPlayer.getName().getString())),serverPlayer);
            }

            if (!data.contains(StringUtils.Reputation)) {
                data.putInt(StringUtils.Reputation,0);
            } else ModNetworking.sendToClient(new ReputationValueS2CPacket(data.getInt(StringUtils.Reputation)),serverPlayer);

            if (!data.contains(StringUtils.Parkour.ParkourTicket)) {
                data.putBoolean(StringUtils.Parkour.ParkourTicket,true);
                Compute.ItemStackGive(player,ModItems.ParkourTicket.get().getDefaultInstance());
            }


            if (!data.contains("XpReset")) {
                data.putDouble("Xp",0);
                data.putBoolean("XpReset",true);
            }

            if (!data.contains(StringUtils.PlayerInstanceProgress)) data.putInt(StringUtils.PlayerInstanceProgress,0);
            if (data.getInt(StringUtils.PlayerInstanceProgress) < 5) data.putInt(StringUtils.PlayerInstanceProgress,5);

            Parkour.ParkourInitial(player);
            // 体力值每日4点刷新 与 悬赏重置 与 跑酷重置
            if (!data.contains(StringUtils.PsRefreshDate)) {
                Calendar calendar = Calendar.getInstance();
                data.putString(StringUtils.PsRefreshDate,Compute.CalendarToString(calendar));
                DailyRefreshContent(player);
            }
            else {
                Calendar currentDate = Calendar.getInstance();
                Calendar lastRefreshDate = Compute.StringToCalendar(data.getString(StringUtils.PsRefreshDate));
                Calendar refreshDate = Calendar.getInstance();
                if (currentDate.get(Calendar.HOUR_OF_DAY) >= 4) {
                    refreshDate.set(Calendar.HOUR_OF_DAY,4);
                    refreshDate.set(Calendar.MINUTE,0);
                    refreshDate.set(Calendar.SECOND,0);
                }
                else {
                    refreshDate.add(Calendar.DAY_OF_MONTH,-1);
                    refreshDate.set(Calendar.HOUR_OF_DAY,4);
                    refreshDate.set(Calendar.MINUTE,0);
                    refreshDate.set(Calendar.SECOND,0);
                }
                if (lastRefreshDate.before(refreshDate)) {
                    data.putString(StringUtils.PsRefreshDate,Compute.CalendarToString(currentDate));
                    DailyRefreshContent(player);
                }
            }

            Calendar calendar = Calendar.getInstance();
            // 每周刷新
            if (!data.contains(StringUtils.WeeklyRefreshWeekNum)) {
                data.putInt(StringUtils.WeeklyRefreshWeekNum,calendar.get(Calendar.WEEK_OF_YEAR));
                data.putInt(StringUtils.WeeklyRefreshYearNum,calendar.get(Calendar.YEAR));
                WeeklyRefreshContent(player);
                Compute.FormatMSGSend(player,Component.literal("周常").withStyle(CustomStyle.styleOfFlexible),
                        Component.literal(""));
            }
            else {
                int weekOfYear = data.getInt(StringUtils.WeeklyRefreshWeekNum);
                int year = data.getInt(StringUtils.WeeklyRefreshYearNum);
                if (weekOfYear != calendar.get(Calendar.WEEK_OF_YEAR) || year != calendar.get(Calendar.YEAR)) {
                    data.putInt(StringUtils.WeeklyRefreshWeekNum,calendar.get(Calendar.WEEK_OF_YEAR));
                    data.putInt(StringUtils.WeeklyRefreshYearNum,calendar.get(Calendar.YEAR));
                    WeeklyRefreshContent(player);
                    Compute.FormatMSGSend(player,Component.literal("周常").withStyle(CustomStyle.styleOfFlexible),
                            Component.literal(""));
                }
            }

/*            if (data.contains("SignIn")) {
                String DateString = data.getString("SignIn");
                SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date1 = tmpDate.parse(DateString);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date1);
                cal.get(Calendar.DATE);
                cal.add(Calendar.HOUR_OF_DAY,22);
                Calendar cal1 = Calendar.getInstance();
                if(cal1.after(cal)) {
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("击杀一只怪物来获取签到奖励！").withStyle(ChatFormatting.WHITE)));
                    data.putBoolean("SignAward",true);
                }
                else {
                    String DateString0 = data.getString("SignIn");
                    SimpleDateFormat tmpDate0 = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date date10 = tmpDate0.parse(DateString0);
                    Calendar cal0 = Calendar.getInstance();
                    cal0.setTime(date10);
                    cal0.add(Calendar.HOUR_OF_DAY,22);
                    int year = cal0.get(Calendar.YEAR);
                    int month = cal0.get(Calendar.MONTH)+1;
                    int day = cal0.get(Calendar.DAY_OF_MONTH);
                    int hour = cal0.get(Calendar.HOUR_OF_DAY);
                    int minutes = cal0.get(Calendar.MINUTE);
                    int seconds = cal0.get(Calendar.SECOND);
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("签到奖励将在 "+year+"年"+month+"月"+day+"日"+hour+"时"+minutes+"分"+seconds+"秒 可用").withStyle(ChatFormatting.WHITE)));
                }
            }
            else {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("击杀一只怪物来获取签到奖励！").withStyle(ChatFormatting.WHITE)));
                data.putBoolean("SignAward",true);
            }*/
            if(!data.contains("FirstReward")) {
                Compute.ItemStackGive(player,ModItems.ForNew.get().getDefaultInstance());
                Compute.FormatBroad(player.level(),Component.literal("维瑞阿契").withStyle(ChatFormatting.WHITE),
                        Component.literal("欢迎新地质学家").withStyle(ChatFormatting.GOLD).
                                append(player.getDisplayName()).
                                append(Component.literal("的到来！").withStyle(ChatFormatting.GOLD)));
                Compute.FormatMSGSend(player,Component.literal("欢迎").withStyle(ChatFormatting.AQUA),
                        Component.literal("欢迎新人！新手教程请查看群文件内玩家编写的教程或查阅游戏内的帕秋莉手册(维瑞阿契wiki),游玩过程有任何建议或问题欢迎在群里@群主或管理员！").withStyle(ChatFormatting.WHITE));
                Compute.FormatMSGSend(player,Component.literal("欢迎").withStyle(ChatFormatting.AQUA),
                        Component.literal("您可以先打开身份卡，点击物品图鉴，浏览由制作者编写的各种装备，找到心仪的装备制作吧！").withStyle(ChatFormatting.GOLD));
            }
            data.putBoolean("FirstReward",true);

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
            serverPlayer.setRespawnPosition(Level.OVERWORLD,new BlockPos(437,69,916),0,true,false);

/*            ModNetworking.sendToClient(new MissionResetS2CPacket(),serverPlayer);

            if (!data.contains(StringUtils.Missions.Mission)) data.putInt(StringUtils.Missions.Mission,1);
            int MissionNum = data.getInt(StringUtils.Missions.Mission);
            if (MissionNum < 11) {
                Compute.FormatMSGSend(player,Component.literal("任务").withStyle(CustomStyle.styleOfSpider),
                        Component.literal("您有尚未完成的任务。[按大小写切换键呼出/隐藏任务纸条，你也可以前往按键绑定自行调整。]").withStyle(ChatFormatting.WHITE));
                Compute.FormatMSGSend(player,Component.literal("任务").withStyle(CustomStyle.styleOfSpider),
                        Component.literal("1.在任务纸条呼出时，你可以使用准星标定来辅助你寻找任务目的地。").withStyle(ChatFormatting.WHITE));
                Compute.FormatMSGSend(player,Component.literal("任务").withStyle(CustomStyle.styleOfSpider),
                        Component.literal("2.在你进行游览任务时，你将不会受到伤害，但同时，你也无法造成伤害。").withStyle(ChatFormatting.WHITE));
                ModNetworking.sendToClient(new MissionInfoS2CPacket(Utils.MissionMap.get(MissionNum).getTitle(),
                        Utils.MissionMap.get(MissionNum).getContent(),
                        Utils.MissionMap.get(MissionNum).getDes().x,Utils.MissionMap.get(MissionNum).getDes().y,
                        Utils.MissionMap.get(MissionNum).getDes().z),serverPlayer);
            }*/
            ModNetworking.sendToClient(new AnimationTickResetS2CPacket(),serverPlayer);
            ModNetworking.sendToClient(new VersionCheckS2CPacket(),serverPlayer1);

            if (Utils.playerReputationMissionContent.containsKey(player.getName().getString())
                    && Utils.playerReputationMissionContentNum.get(player.getName().getString()) != 0)
                ModNetworking.sendToClient(new ReputationMissionContentS2CPacket(
                        Utils.playerReputationMissionContent.get(serverPlayer.getName().getString())
                        ,Utils.playerReputationMissionContentNum.get(serverPlayer.getName().getString())),serverPlayer);

            if (Utils.playerDailyMissionContent.containsKey(player.getName().getString())
                    && Utils.playerDailyMissionContentNum.get(player.getName().getString()) != 0)
                ModNetworking.sendToClient(new DailyMissionContentS2CPacket(
                        Utils.playerDailyMissionContent.get(player.getName().getString()),
                        Utils.playerDailyMissionContentNum.get(player.getName().getString())),serverPlayer);

            if (data.contains(StringUtils.ResonanceType)) Element.PlayerResonanceType.put(player,data.getString(StringUtils.ResonanceType));
        }
    }
    @SubscribeEvent
    public static void LogOut(PlayerEvent.PlayerLoggedOutEvent event)
    {
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
                if (recall != null && recall.RecallPlayer != null) {
                    if (recall.RecallPlayer.equals(serverPlayer)) {
                        Compute.FormatBroad(player.level(),Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(Utils.SpiderRecall.playerName).
                                        append(Component.literal("尝试回想其在").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(recall.zoneName).withStyle(recall.style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        System.out.println("reset!");
                        recall.Reset();
                    }
                }
            });
            ModNetworking.sendToClient(new TeamInfoResetS2CPacket(),serverPlayer);
            CompoundTag data = serverPlayer.getPersistentData();
            data.putString(StringUtils.Login.Status,StringUtils.Login.Offline);
            data.putInt("SLTIME",-1);
            data.putInt("PFTIME",-1);
            data.putInt("SVTIME",-1);
            Utils.IpArrayList.remove(serverPlayer.getIpAddress());
            for (String TickString : StringUtils.TickStringArray) {
                if (data.contains(TickString)) data.putInt(TickString,0);
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
                            ModNetworking.sendToClient(new ScreenSetS2CPacket(0),(ServerPlayer) player1);
                        }
                    });

                    Utils.playerTeamMap.remove(player);

                    player.sendSystemMessage(Component.literal("队伍已解散。"));
                }
                else {
                    Player teamLeader = Utils.playerTeamMap.get(player).getTeamLeader();
                    String teamName = Utils.playerTeamMap.get(player).getTeamName();
                    Utils.playerTeamMap.get(player).removePlayer(player);
                    Utils.playerTeamMap.remove(player);

                    Utils.playerTeamMap.get(teamLeader).getPlayerList().forEach(player1 -> {
                        Utils.playerTeamMap.put(player1,Utils.playerTeamMap.get(teamLeader));
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
                ModNetworking.sendToClient(new TeamInfoResetS2CPacket(),player1);
            });
            Utils.instanceList.forEach(instance -> {
                if (instance.getCurrentChallengePlayerTeam() != null && instance.getCurrentChallengePlayerTeam().getPlayerList().contains(player)) {
                    instance.addDeadTimes();
                }
            });
            Utils.playerNameMap.put(player.getName().getString(),player.getDisplayName());

            if (WcndymlgbCurios.IsPlayer(player)) WcndymlgbCurios.Remove();
            BlackFeisaCurios3.RemoveAllay(player);
        }
    }
    public static void DailyRefreshContent(Player player) {
        CompoundTag data = player.getPersistentData();
        Compute.addOrCostPlayerPsValue(player,100);
        Utils.playerReputationMissionPunishLevel.remove(player.getName().getString());
        Parkour.ParkourReset(player);
        Utils.playerDailyMissionContent.remove(player.getName().getString());
        Utils.playerDailyMissionContentNum.remove(player.getName().getString());
        data.putBoolean(StringUtils.DailyInstance.DailyLogInstance,true);
        data.putBoolean(StringUtils.DailyInstance.DailyMineInstance,true);
        data.putBoolean(StringUtils.DailyInstance.DailyCropInstance,true);
        data.putString(StringUtils.DailyInstanceCode,StringUtils.DailyInstanceCode0);
        PurpleIronKnight.RefreshRewardGetTimes(player); //
        data.putInt(StringUtils.OriginElementGetTimes,0);
        Compute.FormatMSGSend(player,Component.literal("日常").withStyle(CustomStyle.styleOfHealth),
                Component.literal(" 你的日常活动已被刷新！").withStyle(ChatFormatting.WHITE));
        LabourDayMission.acceptStatusSet(player);
    }

    public static void WeeklyRefreshContent(Player player) {

    }


    public static boolean playerDailyInstanceReward(Player player, int instanceIndex) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(StringUtils.DailyInstanceCode)) data.putString(StringUtils.DailyInstanceCode,StringUtils.DailyInstanceCode0);
        String s = data.getString(StringUtils.DailyInstanceCode);
        StringBuilder sb = new StringBuilder(s);
        char c = sb.charAt(instanceIndex);
        if (c == '0') {
            sb.setCharAt(instanceIndex,'1');
            data.putString(StringUtils.DailyInstanceCode,sb.toString());
            return true;
        }
        else return false;
    }

}
