package fun.wraq.process.system.missions.mission2;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.system.missions.netWorking.MissionScreenOpenS2CPacket;
import fun.wraq.process.system.missions.netWorking.MissionV2DataS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public enum MissionV2 {

    DAILY_EXPLORE(Te.s("日常勘探任务", CustomStyle.styleOfField),
            Te.s("勘探指定位置"), Te.s("前往指定位置附近，自动完成。"), Te.s("每日任务"), true,
            null, null,
            MissionV2Helper.getDailyExploreMissionSubmitCondition(),
            MissionV2Helper.getDailyMissionRewardAction(),
            MissionV2Helper.getDailyRewardDescription(),
            MissionV2Helper.getDailyExploreMissionTitle(),
            MissionV2Helper.getDailyExploreMissionDetail()),

    DAILY_KILL(Te.s("日常清理任务", CustomStyle.styleOfRed),
            Te.s("清理指定怪物"), Te.s("清理一定数量的指定怪物后，自动完成。"),
            Te.s("每日任务"), true,
            null, null,
            MissionV2Helper.getDailyKillMissionSubmitCondition(),
            MissionV2Helper.getDailyMissionRewardAction(),
            MissionV2Helper.getDailyRewardDescription(),
            MissionV2Helper.getDailyKillMissionTitle(),
            MissionV2Helper.getDailyKillMissionDetail()),

    DAILY_COLLECTION(Te.s("日常收集任务", CustomStyle.styleOfFlexible),
            Te.s("收集指定物品"), Te.s("收集指定物品，手动提交。"),
            Te.s("每日任务"), false,
            null, null,
            MissionV2Helper.getDailyCollectionItemMissionSubmitCondition(),
            MissionV2Helper.getDailyCollectionItemMissionRewardAction(),
            MissionV2Helper.getDailyRewardDescription(),
            MissionV2Helper.getDailyCollectionMissionTitle(),
            null),

    DAILY_CHALLENGE(Te.s("日常挑战任务", CustomStyle.styleOfRed),
            Te.s("完成指定挑战"), Te.s("完成一定次数的指定挑战，自动完成。"),
            Te.s("每日任务"), true,
            null, null,
            MissionV2Helper.getDailyChallengeMissionSubmitCondition(),
            MissionV2Helper.getDailyMissionRewardAction(),
            MissionV2Helper.getDailyRewardDescription(),
            MissionV2Helper.getDailyChallengeMissionTitle(),
            MissionV2Helper.getDailyChallengeMissionDetail()),

    EXPEDITION_O_LAVA(Te.s("远征O - 熔岩 - 熔岩废墟", CustomStyle.styleOfRed),
            Te.s("东侧熔岩裂谷对潮汐城产生了威胁，寻找熔岩学者了解情况，收集根源力量，锻造火山装备以应对危机。"),
            Te.s("寻访学者 → 收集根源 → 锻造装备"),
            Te.s("需完成全部引导任务"), true,
            ExpeditionMissionHelper.getAllowAcceptCondition(),
            null,
            ExpeditionMissionHelper.getSubmitCondition(),
            ExpeditionMissionHelper.getSubmitAction(),
            ExpeditionMissionHelper.getRewardDescription(),
            ExpeditionMissionHelper.getTitleOperation(),
            ExpeditionMissionHelper.getTipsOperation(),
            ExpeditionMissionHelper.getDetailOperation(),
            true),

    EXPEDITION_I_LUOYAN(Te.s("远征I - 炼狱 - 熔岩裂谷", CustomStyle.styleOfPower),
            Te.s("深入熔岩裂谷，面对更强大的挑战。"),
            Te.s("等待开放…"),
            Te.s("需完成远征O"), true,
            ExpeditionMissionHelper.getLuoyanAllowAcceptCondition(),
            null,
            null,
            null,
            List.of(),
            null,
            null);

    public interface PlayerCondition {
        boolean can(Player player) throws CommandSyntaxException;
    }

    public interface PlayerAction {
        void action(Player player) throws ParseException, SQLException, CommandSyntaxException;
    }

    public interface ClientComponentOperation {
        Component operation(MissionV2 missionV2, CompoundTag data) throws CommandSyntaxException;
    }

    public final Component title;
    public final Component description;
    public final Component tips;
    public final Component frontCondition;
    public final boolean canAutoSubmit;
    public final boolean multiStep;
    public final PlayerCondition allowAcceptCondition;
    public final PlayerAction acceptAction;
    public final PlayerCondition submitCondition;
    public final PlayerAction submitAction;
    public final List<Component> rewardDescription;
    public final ClientComponentOperation titleOperation;
    public final ClientComponentOperation tipsOperation;
    public final ClientComponentOperation detailOperation;

    MissionV2(Component title, Component description, Component tips, Component frontCondition, boolean canAutoSubmit,
              PlayerCondition allowAcceptCondition, PlayerAction acceptAction,
              PlayerCondition submitCondition, PlayerAction submitAction, List<Component> rewardDescription,
              ClientComponentOperation titleOperation, ClientComponentOperation tipsOperation,
              ClientComponentOperation detailOperation, boolean multiStep) {
        this.title = title;
        this.description = description;
        this.tips = tips;
        this.frontCondition = frontCondition;
        this.canAutoSubmit = canAutoSubmit;
        this.multiStep = multiStep;
        this.allowAcceptCondition = allowAcceptCondition;
        this.acceptAction = acceptAction;
        this.submitCondition = submitCondition;
        this.submitAction = submitAction;
        this.rewardDescription = rewardDescription;
        this.titleOperation = titleOperation;
        this.tipsOperation = tipsOperation;
        this.detailOperation = detailOperation;
    }

    MissionV2(Component title, Component description, Component tips, Component frontCondition, boolean canAutoSubmit,
              PlayerCondition allowAcceptCondition, PlayerAction acceptAction,
              PlayerCondition submitCondition, PlayerAction submitAction, List<Component> rewardDescription,
              ClientComponentOperation titleOperation, ClientComponentOperation tipsOperation,
              ClientComponentOperation detailOperation) {
        this(title, description, tips, frontCondition, canAutoSubmit, allowAcceptCondition, acceptAction,
                submitCondition, submitAction, rewardDescription, titleOperation, tipsOperation, detailOperation, false);
    }

    MissionV2(Component title, Component description, Component tips, Component frontCondition, boolean canAutoSubmit,
              PlayerCondition allowAcceptCondition, PlayerAction acceptAction,
              PlayerCondition submitCondition, PlayerAction submitAction, List<Component> rewardDescription,
              ClientComponentOperation titleOperation, ClientComponentOperation detailOperation) {
        this(title, description, tips, frontCondition, canAutoSubmit, allowAcceptCondition, acceptAction,
                submitCondition, submitAction, rewardDescription, titleOperation, null, detailOperation);
    }

    MissionV2(Component title, Component description, Component tips, Component frontCondition, boolean canAutoSubmit,
              PlayerCondition allowAcceptCondition, PlayerAction acceptAction,
              PlayerCondition submitCondition, PlayerAction submitAction, List<Component> rewardDescription) {
        this(title, description, tips, frontCondition, canAutoSubmit, allowAcceptCondition, acceptAction,
                submitCondition, submitAction, rewardDescription, null, null, null);
    }

    public static CompoundTag clientMissionData;

    public void setPlayerStatus(Player player, String status) {
        MissionV2Helper.getMissionV2StatusData(player).putString(name(), status);
    }

    public String getPlayerStatus(Player player) {
        return MissionV2Helper.getMissionV2StatusData(player).getString(name());
    }

    public static class Status {
        public static final String NOT_ACCEPTED = "notAccepted";
        public static final String IN_PROGRESS = "inProgress";
        public static final String FINISHED = "finished";
    }

    public static void onPlayerTryToAccept(Player player, int ordinal) {
        MissionV2 missionV2 = MissionV2.values()[ordinal];
        String status = MissionV2Helper.getMissionV2StatusData(player).getString(missionV2.name());
        if (status.equals(Status.FINISHED)) {
            sendMSG(player, Te.s("该任务已完成，无需再次接受."));
            return;
        }
        if (status.equals(Status.IN_PROGRESS)) {
            sendMSG(player, Te.s("任务进行中，无需再次接受."));
            return;
        }
        try {
            if (missionV2.allowAcceptCondition == null || missionV2.allowAcceptCondition.can(player)) {
                if (missionV2.acceptAction != null) {
                    missionV2.acceptAction.action(player);
                }
                MissionV2Helper.getMissionV2StatusData(player).putString(missionV2.name(), Status.IN_PROGRESS);
                // 接受远征任务时，完成对应引导
                if (missionV2 == EXPEDITION_O_LAVA) {
                    Guide.trigV2(player, Guide.StageV2.ACCEPT_EXPEDITION_O);
                }
                sendMSG(player, Te.s("已接受 ", missionV2.title));
                MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
                openScreenInClient(player, 2);
            } else {
                sendMSG(player, Te.s("暂时还不能接受任务."));
            }
        } catch (CommandSyntaxException | ParseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void onPlayerTryToSubmit(Player player, int ordinal) {
        MissionV2 missionV2 = MissionV2.values()[ordinal];
        String status = MissionV2Helper.getMissionV2StatusData(player).getString(missionV2.name());
        if (status.equals(Status.NOT_ACCEPTED)) {
            sendMSG(player, Te.s("任务还未接受，暂时不能提交."));
            return;
        }
        if (status.equals(Status.FINISHED)) {
            sendMSG(player, Te.s("任务已经完成，不能重复提交."));
            return;
        }
        try {
            if (missionV2.submitCondition == null || missionV2.submitCondition.can(player)) {
                submit(player, missionV2);
            } else {
                sendMSG(player, Te.s("暂时还没有达到提交任务的条件."));
            }
        } catch (CommandSyntaxException | ParseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void submit(Player player, MissionV2 missionV2) throws ParseException, SQLException {
        if (missionV2.submitAction != null) {
            try {
                missionV2.submitAction.action(player);
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        if (missionV2.multiStep) {
            // 多步骤任务：submitAction 已自行管理状态，此处只发送反馈
            String currentStatus = MissionV2Helper.getMissionV2StatusData(player).getString(missionV2.name());
            if (currentStatus.equals(Status.FINISHED)) {
                sendMSG(player, Te.s(missionV2.title, " 全部完成!"));
                MySound.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);
                openScreenInClient(player, 3);
            } else {
                sendMSG(player, Te.s(missionV2.title, " 步骤完成! 请查看下一步需求"));
                MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
                openScreenInClient(player, 2);
            }
        } else {
            MissionV2Helper.getMissionV2StatusData(player).putString(missionV2.name(), Status.FINISHED);
            sendMSG(player, Te.s(missionV2.title, " 已完成!"));
            MySound.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);
            openScreenInClient(player, 3);
        }
    }

    public static void autoSubmit(Player player) {
        Arrays.stream(MissionV2.values()).toList()
                .stream().filter(missionV2 -> {
                    return missionV2.canAutoSubmit && missionV2.getPlayerStatus(player).equals(Status.IN_PROGRESS);
                })
                .forEach(missionV2 -> {
                    try {
                        if (missionV2.submitCondition == null || missionV2.submitCondition.can(player)) {
                            submit(player, missionV2);
                        }
                    } catch (CommandSyntaxException | ParseException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public static int getNoAcceptedMissionCount(Player player) {
        return (int) Arrays.stream(MissionV2.values())
                .filter(missionV2 -> missionV2.getPlayerStatus(player).equals(MissionV2.Status.NOT_ACCEPTED))
                .count();
    }

    public static int getInProgressMissionCount(Player player) {
        return (int) Arrays.stream(MissionV2.values())
                .filter(missionV2 -> missionV2.getPlayerStatus(player).equals(MissionV2.Status.IN_PROGRESS))
                .count();
    }

    public static void handlePlayerTick(Player player) {
        if (player.tickCount % 20 == 0) {
            autoSubmit(player);
            sendDataToClient(player);
        }
        if (player.tickCount == 300) {
            sendMSG(player, Te.s("有", getNoAcceptedMissionCount(player), CustomStyle.styleOfWorld,
                    "个", "可接受", CustomStyle.styleOfFlexible, "的，",
                    getInProgressMissionCount(player), CustomStyle.styleOfField,
                    "个", "进行中", CustomStyle.styleOfField, "的", "任务。"));
            sendMSG(player, Te.s("使用", ModItems.ID_CARD, "打开", "任务列表", CustomStyle.styleOfFlexible,
                    "以查看详情。"));
        }
        // 引导到接受远征阶段或最终阶段时，推送远征任务
        if (player.tickCount % 100 == 50) {
            String guideStage = Guide.getPlayerCurrentStageV2(player);
            if ((guideStage.equals(Guide.StageV2.FINAL)
                    || guideStage.equals(Guide.StageV2.ACCEPT_EXPEDITION_O))
                    && EXPEDITION_O_LAVA.getPlayerStatus(player).isEmpty()) {
                EXPEDITION_O_LAVA.setPlayerStatus(player, Status.NOT_ACCEPTED);
                sendMSG(player, Te.s("远征任务已开放! 请查看任务列表接取: ",
                        EXPEDITION_O_LAVA.title));
                MySound.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);
            }
        }
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("任务", CustomStyle.styleOfFlexible), content);
    }

    public static void sendDataToClient(Player player) {
        ModNetworking.sendToClient(new MissionV2DataS2CPacket(MissionV2Helper.getMissionV2Data(player)), player);
    }

    public static void openScreenInClient(Player player, int type) {
        ModNetworking.sendToClient(new MissionScreenOpenS2CPacket(type), player);
    }
}
