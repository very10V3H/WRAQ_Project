package fun.wraq.process.system.missions.mission2;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.missions.mainline.MainQuestData;
import fun.wraq.process.system.missions.mainline.MainQuestHelper;
import fun.wraq.process.system.missions.mainline.MainQuestStage;
import fun.wraq.process.system.missions.netWorking.MissionScreenOpenS2CPacket;
import fun.wraq.process.system.missions.netWorking.MissionV2DataS2CPacket;
import fun.wraq.process.system.missions.side.ForgeMasterQuest;
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

    // ---- 主线：数据在 MainQuestData，枚举只做薄壳（按字段引用，避免插阶段时错位） ----
    MAIN_01_DEPART_XUSHENG(MainQuestData.MAIN_01),
    MAIN_02_ARRIVE_SKY(MainQuestData.MAIN_02),
    MAIN_03_MAYOR_BRIEFING(MainQuestData.MAIN_03),
    MAIN_04_PLAIN_CHIEF(MainQuestData.MAIN_04),
    MAIN_04A_ROLL(MainQuestData.MAIN_04A),
    MAIN_04B_SKILL_GROUP(MainQuestData.MAIN_04B),
    MAIN_04C_MASTERY(MainQuestData.MAIN_04C),
    MAIN_05_PLAIN_PURGE(MainQuestData.MAIN_05),
    MAIN_06_FOREST_CHIEF(MainQuestData.MAIN_06),
    MAIN_07_PLAINI(MainQuestData.MAIN_07),
    MAIN_08_ALCHEMIST_SUPPORT(MainQuestData.MAIN_08),
    MAIN_09_COAST_CHIEF(MainQuestData.MAIN_09),
    MAIN_10_XUNXI_VISIT(MainQuestData.MAIN_10),
    MAIN_11_NETHER_I(MainQuestData.MAIN_11),
    MAIN_12_BEIFU_CHIEF(MainQuestData.MAIN_12),
    MAIN_13_WIND_HIGHLAND(MainQuestData.MAIN_13),
    MAIN_14_DARK_CASTLE(MainQuestData.MAIN_14),
    MAIN_15_VOLCANO(MainQuestData.MAIN_15),
    MAIN_16_EAST_TOWER(MainQuestData.MAIN_16),
    MAIN_17_FUNCTION_ISLAND(MainQuestData.MAIN_17),
    MAIN_18_LEIGUANG(MainQuestData.MAIN_18),
    MAIN_19_SAKURA_ISLAND(MainQuestData.MAIN_19),
    MAIN_20_MOON_PALACE(MainQuestData.MAIN_20),
    MAIN_21_WANGSHAN(MainQuestData.MAIN_21),
    MAIN_22_EAGLE_FACTORY(MainQuestData.MAIN_22),
    MAIN_23_MUSHROOM(MainQuestData.MAIN_23),
    MAIN_24_END(MainQuestData.MAIN_24),
    MAIN_25_MANA_TOWER(MainQuestData.MAIN_25),
    MAIN_26_ANCIENT_CITY(MainQuestData.MAIN_26),
    MAIN_27_LAVA_BUNKER(MainQuestData.MAIN_27),
    MAIN_28_HOLY_ISLAND(MainQuestData.MAIN_28),
    MAIN_29_POLAR(MainQuestData.MAIN_29),

    // ---- 支线：锻造师 ----
    SIDE_FORGE_I(Te.s("锻造师I", CustomStyle.styleOfPlain),
            Te.s("锻造任意平原武器"),
            Te.s("锻造师"),
            Te.s("需完成 平原清剿"),
            true,
            ForgeMasterQuest::canAccept,
            null,
            ForgeMasterQuest::hasForgedPlainWeapon,
            ForgeMasterQuest::claimReward,
            ForgeMasterQuest.rewardDescription()),

    SIDE_FORGE_II(Te.s("锻造师II", CustomStyle.styleOfPlain),
            Te.s("将任意装备强化至2级"),
            Te.s("锻造师"),
            Te.s("需完成 锻造师I"),
            true,
            ForgeMasterQuest::canAcceptII,
            null,
            ForgeMasterQuest::hasEnhancedToLevel2,
            ForgeMasterQuest::claimRewardII,
            ForgeMasterQuest.rewardDescriptionII()),

    SIDE_FORGE_III(Te.s("锻造师III", CustomStyle.styleOfPlain),
            Te.s("锻造森林粉碎者、森林长弓或生机权杖-X"),
            Te.s("锻造师"),
            Te.s("需完成 锻造师II"),
            true,
            ForgeMasterQuest::canAcceptIII,
            null,
            ForgeMasterQuest::hasForgedIII,
            ForgeMasterQuest::claimRewardIII,
            ForgeMasterQuest.rewardDescriptionIII());

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

    /** 主线阶段：字段全部来自 MainQuestData.Stage，枚举只作为任务列表的载体。 */
    MissionV2(MainQuestStage.Stage stage) {
        this(stage.title(), stage.description(), stage.tips(), stage.frontCondition(), false,
                MainQuestHelper.allow(stage), null,
                MainQuestHelper.condition(stage), MainQuestHelper.submit(stage),
                MainQuestHelper.rewardDescription(stage),
                null, MainQuestHelper.tipsOperation(stage), MainQuestHelper.detailOperation(stage), true);
    }

    public static CompoundTag clientMissionData;

    public void setPlayerStatus(Player player, String status) {
        MissionV2Helper.getMissionV2StatusData(player).putString(name(), status);
    }

    public String getPlayerStatus(Player player) {
        return MissionV2Helper.getMissionV2StatusData(player).getString(name());
    }

    /**
     * AI-Generated, 2026-10-18
     * 任务分类，用于任务列表左侧的分类筛选按钮.
     */
    public enum Category {
        DAILY("每日任务"),
        MAIN("主线任务"),
        SIDE("支线任务");

        public final String displayName;

        Category(String displayName) {
            this.displayName = displayName;
        }
    }

    /**
     * AI-Generated, 2026-10-18
     * 获取任务的分类. 新增任务时需在此处补充分类映射（不设 default，以便新增任务时强制分类）.
     */
    public Category getCategory() {
        if (name().startsWith("MAIN_")) {
            return Category.MAIN;
        }
        return switch (this) {
            case DAILY_EXPLORE, DAILY_KILL, DAILY_COLLECTION, DAILY_CHALLENGE -> Category.DAILY;
            default -> Category.SIDE;
        };
    }

    public static class Status {
        public static final String NOT_ACCEPTED = "notAccepted";
        public static final String IN_PROGRESS = "inProgress";
        public static final String FINISHED = "finished";
    }

    public static void onPlayerTryToAccept(Player player, int ordinal) {
        MissionV2 missionV2 = MissionV2.values()[ordinal];
        if (missionV2.name().startsWith("MAIN_")) {
            sendMSG(player, Te.s("主线任务需要与 NPC 对话推进."));
            return;
        }
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
        if (missionV2.name().startsWith("MAIN_")) {
            sendMSG(player, Te.s("主线任务需要与 NPC 对话交付."));
            return;
        }
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
            ForgeMasterQuest.refreshUnlock(player);
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
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("任务", CustomStyle.styleOfFlexible), content);
    }

    public static void sendMsgForUnlock(Player player, MissionV2 mission) {
        sendMSG(player, Component.literal("新支线已解锁: ").append(mission.title));
    }

    public static void sendDataToClient(Player player) {
        ModNetworking.sendToClient(new MissionV2DataS2CPacket(MissionV2Helper.getMissionV2Data(player)), player);
    }

    public static void openScreenInClient(Player player, int type) {
        ModNetworking.sendToClient(new MissionScreenOpenS2CPacket(type), player);
    }
}
