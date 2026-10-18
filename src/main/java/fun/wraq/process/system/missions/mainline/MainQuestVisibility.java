package fun.wraq.process.system.missions.mainline;

import fun.wraq.process.system.missions.mainline.MainQuestStage.Stage;
import fun.wraq.process.system.missions.mission2.MissionV2;
import fun.wraq.process.system.missions.mission2.MissionV2Helper;
import fun.wraq.process.system.npc.NpcIds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * AI-Generated, 2026-10-18
 * 按玩家主线阶段控制 NPC 可见性。
 * <p>
 * 规则表按 dialogId 配置 {@code [fromStage, toStage]}（闭区间，to 为空表示之后一直可见）：
 * <ul>
 *     <li>客户端：{@code VmdNpcRenderer.render} 与 {@code VmdNpc.isPickable} 调用
 *         {@link #isVisibleToLocalPlayer(String)}；</li>
 *     <li>服务端：{@code NpcInteractionRegistry.handle} 调用 {@link #isVisibleFor(Player, String)}，
 *         即使客户端被改包也无法与隐藏 NPC 交互。</li>
 * </ul>
 * 未登记的 dialogId 一律可见（交易类 NPC 不受影响）。
 */
public final class MainQuestVisibility {

    private static final Map<String, String[]> RULES = new HashMap<>();

    static {
        rule(NpcIds.MAIN_BEIBEI_STATION, "main_01_depart_xusheng", "main_01_depart_xusheng");
        rule(NpcIds.MAIN_BEIBEI, "main_01_depart_xusheng", "");
        rule(NpcIds.MAIN_SOL, "main_03_mayor_briefing", "");
        rule(NpcIds.MAIN_PP, "main_04_plain_chief", "");
        rule(NpcIds.MAIN_CHEN, "main_06_forest_chief", "");
        rule(NpcIds.MAIN_USHIO, "main_09_coast_chief", "");
        rule(NpcIds.MAIN_TIETOU, "main_10_xunxi_visit", "");
        rule(NpcIds.MAIN_SINGID, "main_11_nether_i", "");
        rule(NpcIds.MAIN_FUFU, "main_12_beifu_chief", "");
        rule(NpcIds.MAIN_SOSO, "main_13_wind_highland", "");
        rule(NpcIds.MAIN_TOLI, "main_17_function_island", "");
        rule(NpcIds.MAIN_YING, "main_19_sakura_island", "");
        rule(NpcIds.MAIN_SHEN, "main_25_mana_tower", "");
    }

    private MainQuestVisibility() {
    }

    private static void rule(String dialogId, String fromStage, String toStage) {
        RULES.put(dialogId, new String[]{fromStage, toStage});
    }

    /** 服务端判定：玩家现在能否看见 / 交互该 NPC。 */
    public static boolean isVisibleFor(Player player, String dialogId) {
        return isVisible(MissionV2Helper.getMissionV2Data(player), dialogId);
    }

    /** 客户端判定：本地玩家现在能否看见该 NPC（数据未同步前默认可见，避免登录瞬间消失）。 */
    public static boolean isVisibleToLocalPlayer(String dialogId) {
        CompoundTag data = MissionV2.clientMissionData;
        if (data == null) {
            return true;
        }
        return isVisible(data, dialogId);
    }

    private static boolean isVisible(CompoundTag missionData, String dialogId) {
        String[] range = RULES.get(dialogId);
        if (range == null) {
            return true;
        }
        int current = currentIndex(missionData);
        int from = MainQuestData.indexOf(range[0]);
        int to = range[1].isEmpty() ? Integer.MAX_VALUE : MainQuestData.indexOf(range[1]);
        if (from < 0 || to < 0) {
            return false;
        }
        return current >= from && current <= to;
    }

    private static int currentIndex(CompoundTag missionData) {
        CompoundTag status = missionData.getCompound(MissionV2Helper.MISSION_V2_STATUS_KEY);
        boolean anyStatus = false;
        for (int i = 0; i < MainQuestData.STAGES.size(); i++) {
            Stage stage = MainQuestData.STAGES.get(i);
            String value = status.getString(stage.mission());
            if (!value.isEmpty()) {
                anyStatus = true;
            }
            if (value.equals(MissionV2.Status.NOT_ACCEPTED)
                    || value.equals(MissionV2.Status.IN_PROGRESS)) {
                return i;
            }
        }
        // 全部完成 → 视为最后阶段之后；完全没有数据 → 视为起点
        return anyStatus ? MainQuestData.STAGES.size() : 0;
    }
}
