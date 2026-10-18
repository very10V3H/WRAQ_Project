package fun.wraq.process.system.missions.mainline;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.guide.waypoint.WaypointData;
import fun.wraq.process.func.guide.waypoint.WaypointServerManager;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.missions.mainline.MainQuestStage.Kind;
import fun.wraq.process.system.missions.mainline.MainQuestStage.Objective;
import fun.wraq.process.system.missions.mainline.MainQuestStage.QuestWaypoint;
import fun.wraq.process.system.missions.mainline.MainQuestStage.Stage;
import fun.wraq.process.system.missions.mission2.MissionV2;
import fun.wraq.process.system.missions.mission2.MissionV2Helper;
import fun.wraq.process.system.npc.NpcIds;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * AI-Generated, 2026-10-18
 * 主线任务运行时：状态机、进度、奖励、路径点与客户端展示。
 * <p>
 * 硬规则：
 * <ul>
 *     <li>击杀 / 到达 / 升级 / 通关只写进度，**绝不**改变阶段状态；</li>
 *     <li>阶段推进只由 {@link #accept(Player, Stage)}（对话）与 {@link #complete(Player, Stage)}（对话）完成；</li>
 *     <li>数据存放在 {@code MissionV2Data.MainQuest} 下，随现有任务数据包每 20 tick 同步。</li>
 * </ul>
 */
public final class MainQuestHelper {

    public static final String DATA_KEY = "MainQuest";
    private static final String PROGRESS_KEY = "Progress";
    private static final String INIT_KEY = "initialized";
    private static final String MAIN_WAYPOINT_NAME = "主线目标";

    private MainQuestHelper() {
    }

    // =================================================================
    // 任务 ↔ 阶段
    // =================================================================

    public static boolean isMainMission(MissionV2 mission) {
        return mission != null && mission.name().startsWith("MAIN_");
    }

    public static Stage stageOf(MissionV2 mission) {
        return mission == null ? null : MainQuestData.byMission(mission.name());
    }

    // =================================================================
    // 服务端状态
    // =================================================================

    private static CompoundTag root(Player player) {
        CompoundTag missionData = MissionV2Helper.getMissionV2Data(player);
        if (!missionData.contains(DATA_KEY)) {
            missionData.put(DATA_KEY, new CompoundTag());
        }
        return missionData.getCompound(DATA_KEY);
    }

    private static CompoundTag progressRoot(Player player) {
        CompoundTag root = root(player);
        if (!root.contains(PROGRESS_KEY)) {
            root.put(PROGRESS_KEY, new CompoundTag());
        }
        return root.getCompound(PROGRESS_KEY);
    }

    private static CompoundTag progress(Player player, Stage stage) {
        CompoundTag root = progressRoot(player);
        if (!root.contains(stage.id())) {
            root.put(stage.id(), new CompoundTag());
        }
        return root.getCompound(stage.id());
    }

    public static String status(Player player, Stage stage) {
        return MissionV2Helper.getMissionV2StatusData(player).getString(stage.mission());
    }

    private static void setStatus(Player player, Stage stage, String status) {
        MissionV2Helper.getMissionV2StatusData(player).putString(stage.mission(), status);
    }

    /** 当前主线阶段：第一个 notAccepted / inProgress 的阶段。 */
    public static Stage current(Player player) {
        CompoundTag status = MissionV2Helper.getMissionV2StatusData(player);
        for (Stage stage : MainQuestData.STAGES) {
            String value = status.getString(stage.mission());
            if (value.equals(MissionV2.Status.NOT_ACCEPTED)
                    || value.equals(MissionV2.Status.IN_PROGRESS)) {
                return stage;
            }
        }
        return null;
    }

    public static void ensureInitialized(Player player) {
        CompoundTag root = root(player);
        if (!root.getBoolean(INIT_KEY)) {
            root.putBoolean(INIT_KEY, true);
            boolean any = MainQuestData.STAGES.stream()
                    .anyMatch(stage -> !status(player, stage).isEmpty());
            if (!any) {
                setStatus(player, MainQuestData.first(), MissionV2.Status.NOT_ACCEPTED);
                applyWaypoint(player, MainQuestData.first().giverWaypoint());
            }
            MissionV2.sendDataToClient(player);
        }
    }

    /** 登录时把路径点对齐到当前阶段（阶段状态可能由命令/存档恢复）。 */
    public static void syncWaypoint(Player player) {
        Stage stage = current(player);
        if (stage == null) {
            applyWaypoint(player, null);
            return;
        }
        if (status(player, stage).equals(MissionV2.Status.NOT_ACCEPTED)) {
            applyWaypoint(player, stage.giverWaypoint());
        } else {
            applyWaypoint(player, stage.targetWaypoint() != null
                    ? stage.targetWaypoint() : stage.giverWaypoint());
        }
    }

    public static void handlePlayerTick(Player player) {
        if (player.tickCount % 20 == 0) {
            ensureInitialized(player);
            ensureChain(player);
        }
    }

    /**
     * 版本更新追加阶段后的链路修复：
     * 若某阶段已完成、下一阶段还没有任何状态（老存档停在上一章末尾），自动解锁下一阶段。
     */
    public static void ensureChain(Player player) {
        for (int i = 0; i + 1 < MainQuestData.STAGES.size(); i++) {
            Stage previous = MainQuestData.STAGES.get(i);
            Stage next = MainQuestData.STAGES.get(i + 1);
            if (status(player, previous).equals(MissionV2.Status.FINISHED)
                    && status(player, next).isEmpty()) {
                setStatus(player, next, MissionV2.Status.NOT_ACCEPTED);
                syncWaypoint(player);
                MissionV2.sendDataToClient(player);
                Compute.sendFormatMSG(player, Te.s("主线", CustomStyle.styleOfFlexible),
                        Te.s("新主线已解锁: ", next.title()));
                return;
            }
        }
    }

    // =================================================================
    // 接取 / 交付（唯一推进入口）
    // =================================================================

    public static boolean accept(Player player, Stage stage) {
        if (stage == null || !status(player, stage).equals(MissionV2.Status.NOT_ACCEPTED)) {
            return false;
        }
        progressRoot(player).put(stage.id(), new CompoundTag());
        setStatus(player, stage, MissionV2.Status.IN_PROGRESS);
        applyWaypoint(player, stage.targetWaypoint() != null
                ? stage.targetWaypoint() : stage.giverWaypoint());
        Compute.sendFormatMSG(player, Te.s("主线", CustomStyle.styleOfFlexible),
                Te.s("已接受: ", stage.title()));
        MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
        MissionV2.sendDataToClient(player);
        return true;
    }

    public static boolean complete(Player player, Stage stage) {
        return complete(player, stage, false);
    }

    private static boolean complete(Player player, Stage stage, boolean force) {
        if (stage == null) {
            return false;
        }
        // 纯对话阶段：首次对话直接完成，无需先 accept
        if (stage.kind() == Kind.TALK && status(player, stage).equals(MissionV2.Status.NOT_ACCEPTED)) {
            setStatus(player, stage, MissionV2.Status.IN_PROGRESS);
        }
        if (!status(player, stage).equals(MissionV2.Status.IN_PROGRESS)
                || (!force && !ready(player, stage))) {
            return false;
        }
        setStatus(player, stage, MissionV2.Status.FINISHED);
        stage.rewards().forEach(reward -> {
            ItemStack stack = reward.stack().get();
            if (!stack.isEmpty()) {
                InventoryOperation.giveItemStackWithMSG(player, stack);
            }
        });
        Stage next = MainQuestData.next(stage);
        if (next != null) {
            setStatus(player, next, MissionV2.Status.NOT_ACCEPTED);
            applyWaypoint(player, next.giverWaypoint());
            Compute.sendFormatMSG(player, Te.s("主线", CustomStyle.styleOfFlexible),
                    Te.s("新主线已解锁: ", next.title()));
        } else {
            applyWaypoint(player, null);
            Compute.sendFormatMSG(player, Te.s("主线", CustomStyle.styleOfFlexible),
                    Te.s("第一章已完成，后续主线开发中。"));
        }
        MySound.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);
        MissionV2.sendDataToClient(player);
        return true;
    }

    public static boolean ready(Player player, Stage stage) {
        CompoundTag progress = progress(player, stage);
        for (Objective objective : stage.objectives()) {
            if (!objectiveDone(player, objective, progress)) {
                return false;
            }
        }
        return true;
    }

    private static boolean objectiveDone(Player player, Objective objective, CompoundTag progress) {
        if (objective instanceof Objective.Kill kill) {
            return progress.getInt(kill.counterKey()) >= kill.count();
        }
        if (objective instanceof Objective.Reach reach) {
            return player.level().dimension().equals(Level.OVERWORLD)
                    && player.position().distanceTo(reach.pos()) <= reach.radius();
        }
        if (objective instanceof Objective.Level level) {
            return player.experienceLevel >= level.level();
        }
        if (objective instanceof Objective.CollectItem item) {
            return InventoryOperation.checkPlayerHasItem(player, item.item().get(), item.count());
        }
        if (objective instanceof Objective.Instance instance) {
            return progress.getInt(instance.counterKey()) >= instance.count();
        }
        if (objective instanceof Objective.Daily daily) {
            return progress.getInt(daily.counterKey()) >= 1;
        }
        return true;
    }

    // =================================================================
    // 世界目标进度（只计数，不推进）
    // =================================================================

    public static void recordKill(Player player, String mobName) {
        Stage stage = current(player);
        if (stage == null) {
            return;
        }
        for (Objective objective : stage.objectives()) {
            if (objective instanceof Objective.Kill kill && kill.mob().equals(mobName)) {
                increment(player, stage, kill.counterKey());
                return;
            }
        }
    }

    public static void recordInstance(Player player, String instanceName) {
        Stage stage = current(player);
        if (stage == null) {
            return;
        }
        for (Objective objective : stage.objectives()) {
            if (objective instanceof Objective.Instance instance && instance.name().equals(instanceName)) {
                increment(player, stage, instance.counterKey());
                return;
            }
        }
    }

    public static void recordDaily(Player player, String dailyName) {
        Stage stage = current(player);
        if (stage == null) {
            return;
        }
        for (Objective objective : stage.objectives()) {
            if (objective instanceof Objective.Daily daily && daily.name().equals(dailyName)) {
                increment(player, stage, daily.counterKey());
                return;
            }
        }
    }

    private static void increment(Player player, Stage stage, String key) {
        CompoundTag progress = progress(player, stage);
        progress.putInt(key, progress.getInt(key) + 1);
    }

    // =================================================================
    // GM / 调试
    // =================================================================

    /** 直接把玩家跳到某个阶段（之前的阶段标记完成）。 */
    public static void forceSetStage(Player player, Stage stage) {
        int index = MainQuestData.indexOf(stage.id());
        for (int i = 0; i < MainQuestData.STAGES.size(); i++) {
            Stage value = MainQuestData.STAGES.get(i);
            if (i < index) {
                setStatus(player, value, MissionV2.Status.FINISHED);
            } else if (i == index) {
                setStatus(player, value, MissionV2.Status.NOT_ACCEPTED);
            } else {
                setStatus(player, value, "");
            }
        }
        root(player).putBoolean(INIT_KEY, true);
        progressRoot(player).put(stage.id(), new CompoundTag());
        syncWaypoint(player);
        MissionV2.sendDataToClient(player);
    }

    /** 强制完成当前阶段（跳过世界目标），供 GM 验收。 */
    public static boolean forceComplete(Player player) {
        Stage stage = current(player);
        if (stage == null) {
            return false;
        }
        setStatus(player, stage, MissionV2.Status.IN_PROGRESS);
        return complete(player, stage, true);
    }

    /** 清空主线进度并从第一阶段重新开始。 */
    public static void reset(Player player) {
        for (Stage stage : MainQuestData.STAGES) {
            setStatus(player, stage, "");
        }
        CompoundTag root = root(player);
        root.put(PROGRESS_KEY, new CompoundTag());
        root.putBoolean(INIT_KEY, false);
        ensureInitialized(player);
        syncWaypoint(player);
        MissionV2.sendDataToClient(player);
    }

    // =================================================================
    // 路径点
    // =================================================================

    private static void applyWaypoint(Player player, QuestWaypoint waypoint) {
        WaypointServerManager.removeWaypoint(player, MAIN_WAYPOINT_NAME);
        if (waypoint != null) {
            WaypointServerManager.addWaypoint(player, new WaypointData(MAIN_WAYPOINT_NAME,
                    waypoint.pos().x, waypoint.pos().y, waypoint.pos().z,
                    waypoint.dimension().location().toString()));
        }
        WaypointServerManager.syncToClient(player);
    }

    // =================================================================
    // 客户端展示（任务列表 / HUD）
    // =================================================================

    public static Stage clientCurrent() {
        CompoundTag data = MissionV2.clientMissionData;
        if (data == null) {
            return null;
        }
        CompoundTag status = data.getCompound(MissionV2Helper.MISSION_V2_STATUS_KEY);
        for (Stage stage : MainQuestData.STAGES) {
            String value = status.getString(stage.mission());
            if (value.equals(MissionV2.Status.NOT_ACCEPTED)
                    || value.equals(MissionV2.Status.IN_PROGRESS)) {
                return stage;
            }
        }
        return null;
    }

    private static String clientStatus(Stage stage) {
        if (MissionV2.clientMissionData == null) {
            return "";
        }
        return MissionV2.clientMissionData.getCompound(MissionV2Helper.MISSION_V2_STATUS_KEY)
                .getString(stage.mission());
    }

    private static CompoundTag clientProgress(Stage stage) {
        if (MissionV2.clientMissionData == null) {
            return new CompoundTag();
        }
        return MissionV2.clientMissionData.getCompound(DATA_KEY)
                .getCompound(PROGRESS_KEY).getCompound(stage.id());
    }

    /** HUD 标题：`「主线」 阶段名`；没有当前阶段时返回 null。 */
    public static Component clientTitle() {
        Stage stage = clientCurrent();
        if (stage == null) {
            return null;
        }
        return Te.s("「主线」", CustomStyle.styleOfFlexible, " ", stage.title());
    }

    /** HUD 目标行：未接取时提示找 NPC，进行中显示世界目标。 */
    public static Component clientObjective() {
        Stage stage = clientCurrent();
        if (stage == null) {
            return null;
        }
        if (clientStatus(stage).equals(MissionV2.Status.NOT_ACCEPTED)) {
            String name = NpcIds.displayNameOf(stage.npc());
            return Te.s("与 ", name == null ? stage.npc() : name, " 对话", CustomStyle.styleOfSky);
        }
        return objectiveSummary(stage, clientProgress(stage), 1);
    }

    /** 把阶段目标拼成一行（击杀类带进度）。 */
    public static Component objectiveText(Stage stage, CompoundTag stageProgress) {
        List<Component> parts = new ArrayList<>();
        for (Objective objective : stage.objectives()) {
            parts.add(objective.text(stageProgress));
        }
        return join(parts);
    }

    /**
     * 精简目标文本：只显示前 {@code max} 项，其余用「等 N 项」概括。
     * 用于任务列表提示与迷你 HUD，避免多目标阶段把界面撑爆；完整目标在悬停详情中展示。
     */
    public static Component objectiveSummary(Stage stage, CompoundTag stageProgress, int max) {
        List<Component> parts = new ArrayList<>();
        for (Objective objective : stage.objectives()) {
            parts.add(objective.text(stageProgress));
        }
        if (parts.size() <= max) {
            return join(parts);
        }
        MutableComponent result = join(parts.subList(0, Math.max(1, max)));
        result.append(Te.s(" 等 ", parts.size(), " 项", ChatFormatting.GRAY));
        return result;
    }

    private static MutableComponent join(List<Component> parts) {
        MutableComponent result = Component.empty();
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) {
                result.append(Te.s(" · ", ChatFormatting.GRAY));
            }
            result.append(parts.get(i));
        }
        return result;
    }

    /** 完整目标列表（每项一行），用于任务详情悬停。 */
    public static Component objectiveLines(Stage stage, CompoundTag stageProgress) {
        MutableComponent result = Component.empty();
        List<Objective> objectives = stage.objectives();
        for (int i = 0; i < objectives.size(); i++) {
            if (i > 0) {
                result.append("\n");
            }
            result.append(objectives.get(i).text(stageProgress));
        }
        return result;
    }

    /** 服务端用：读取玩家当前进度并拼接目标文本。 */
    public static Component objectiveText(Player player, Stage stage) {
        return objectiveText(stage, progress(player, stage));
    }

    // =================================================================
    // MissionV2 委托
    // =================================================================

    public static MissionV2.PlayerCondition allow(Stage stage) {
        // 主线只能通过对话接取，任务列表不提供接取入口
        return player -> false;
    }

    public static MissionV2.PlayerCondition condition(Stage stage) {
        return player -> ready(player, stage);
    }

    public static MissionV2.PlayerAction submit(Stage stage) {
        // 主线只能通过对话交付
        return player -> {
        };
    }

    public static List<Component> rewardDescription(Stage stage) {
        return List.of();
    }

    public static MissionV2.ClientComponentOperation tipsOperation(Stage stage) {
        return (mission, data) -> {
            CompoundTag stageProgress = data.getCompound(DATA_KEY)
                    .getCompound(PROGRESS_KEY).getCompound(stage.id());
            String status = data.getCompound(MissionV2Helper.MISSION_V2_STATUS_KEY)
                    .getString(stage.mission());
            if (status.equals(MissionV2.Status.NOT_ACCEPTED)) {
                String name = NpcIds.displayNameOf(stage.npc());
                return Te.s("与 ", name == null ? stage.npc() : name, " 对话",
                        CustomStyle.styleOfSky);
            }
            return objectiveSummary(stage, stageProgress, 1);
        };
    }

    public static MissionV2.ClientComponentOperation detailOperation(Stage stage) {
        return (mission, data) -> {
            CompoundTag stageProgress = data.getCompound(DATA_KEY)
                    .getCompound(PROGRESS_KEY).getCompound(stage.id());
            String status = data.getCompound(MissionV2Helper.MISSION_V2_STATUS_KEY)
                    .getString(stage.mission());
            String giver = NpcIds.displayNameOf(stage.npc());
            String report = NpcIds.displayNameOf(stage.effectiveReportNpc());
            List<Component> lines = new ArrayList<>();
            lines.add(stage.title());
            if (status.equals(MissionV2.Status.NOT_ACCEPTED)) {
                lines.add(Te.s("与 ", giver == null ? stage.npc() : giver,
                        " 对话以接取", CustomStyle.styleOfSky));
            } else {
                lines.add(objectiveLines(stage, stageProgress));
            }
            lines.add(Te.s("完成后与 ", report == null ? stage.effectiveReportNpc() : report,
                    " 对话交付", CustomStyle.styleOfFlexible));
            MutableComponent result = Component.empty();
            for (int i = 0; i < lines.size(); i++) {
                if (i > 0) {
                    result.append("\n");
                }
                result.append(lines.get(i));
            }
            return result;
        };
    }
}
