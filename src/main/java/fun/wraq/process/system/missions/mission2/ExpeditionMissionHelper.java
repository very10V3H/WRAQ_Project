package fun.wraq.process.system.missions.mission2;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * AI-Generated, 2026-07-26
 * <p>
 * 远征任务多步骤辅助类。
 * 管理远征任务的步骤进度（NBT 持久化）、每步的提交条件与提交动作、
 * 以及动态标题/详情显示。
 */
public class ExpeditionMissionHelper {

    private static final String EXPEDITION_DATA_KEY = "ExpeditionMissionData";
    private static final String CURRENT_STEP_KEY = "CurrentStep";

    // =================================================================
    // NBT 工具
    // =================================================================

    private static CompoundTag getExpeditionData(Player player) {
        CompoundTag missionV2Data = MissionV2Helper.getMissionV2Data(player);
        if (!missionV2Data.contains(EXPEDITION_DATA_KEY)) {
            missionV2Data.put(EXPEDITION_DATA_KEY, new CompoundTag());
        }
        return missionV2Data.getCompound(EXPEDITION_DATA_KEY);
    }

    private static CompoundTag getQuestData(Player player, String questName) {
        CompoundTag data = getExpeditionData(player);
        if (!data.contains(questName)) {
            data.put(questName, new CompoundTag());
        }
        return data.getCompound(questName);
    }

    private static int getCurrentStep(Player player, String questName) {
        return getQuestData(player, questName).getInt(CURRENT_STEP_KEY);
    }

    private static void setCurrentStep(Player player, String questName, int step) {
        getQuestData(player, questName).putInt(CURRENT_STEP_KEY, step);
    }

    // =================================================================
    // 远征O - 熔岩 - 熔岩废墟
    // =================================================================

    /** 熔岩学者所在坐标（潮汐城南部） */
    private static final Vec3 SCHOLAR_POSITION = new Vec3(3925, 75, 3320);

    /** 步骤数量 */
    private static final int EXPEDITION_O_STEP_COUNT = 3;

    /** 每步的描述文本（用于动态详情） */
    private static final List<Component> STEP_DESCRIPTIONS = List.of(
            Te.s("① 前往潮汐城南部找到熔岩学者", CustomStyle.styleOfGold),
            Te.s("② 前往火山区域收集火元素碎片 × 32", CustomStyle.styleOfGold),
            Te.s("③ 在火山村锻造任意火山武器与防具", CustomStyle.styleOfGold)
    );

    /** 每步的详细说明（鼠标悬停显示） */
    private static final List<Component> STEP_DETAILS = List.of(
            Te.s("前往潮汐城南部 (" + (int) SCHOLAR_POSITION.x + ", "
                    + (int) SCHOLAR_POSITION.y + ", " + (int) SCHOLAR_POSITION.z
                    + ") 附近，找到熔岩学者了解情况。",
                    ChatFormatting.WHITE),
            Te.s("击杀火山区域怪物收集 火元素碎片 × 32，"
                    + "它们是锻造火山装备的核心材料。",
                    ChatFormatting.WHITE),
            Te.s("在火山村的铁匠铺使用锻造锤，锻造任意一件火山武器"
                    + "（火山裂刃/火山长弓）和任意一件火山防具。",
                    ChatFormatting.WHITE)
    );

    // =================================================================
    // 允许接取条件
    // =================================================================

    /**
     * @return 远征O的允许接取条件：已到接受远征引导阶段或最终阶段
     */
    public static MissionV2.PlayerCondition getAllowAcceptCondition() {
        return (player -> {
            String stage = Guide.getPlayerCurrentStageV2(player);
            return stage.equals(Guide.StageV2.FINAL) || stage.equals(Guide.StageV2.ACCEPT_EXPEDITION_O);
        });
    }

    /**
     * @return 远征I的允许接取条件（暂时不可接取，由远征O完成后自动推送）
     */
    public static MissionV2.PlayerCondition getLuoyanAllowAcceptCondition() {
        return (player -> false);
    }

    // =================================================================
    // 提交条件（按当前步骤判定）
    // =================================================================

    public static MissionV2.PlayerCondition getSubmitCondition() {
        return (player -> {
            int step = getCurrentStep(player, MissionV2.EXPEDITION_O_LAVA.name());
            switch (step) {
                case 0:
                    // 步骤1：靠近熔岩学者
                    return player.position().distanceTo(SCHOLAR_POSITION) < 5;
                case 1:
                    // 步骤2：收集火元素碎片
                    return InventoryOperation.checkPlayerHasItem(player,
                            ModItems.FIRE_ELEMENT_PIECE_0.get(), 32);
                case 2:
                    // 步骤3：拥有火山武器与防具
                    boolean hasWeapon = InventoryOperation.checkPlayerHasItem(player,
                                    ModItems.VOLCANO_SWORD_0.get(), 1)
                            || InventoryOperation.checkPlayerHasItem(player,
                                    ModItems.VOLCANO_BOW_0.get(), 1);
                    boolean hasArmor = InventoryOperation.checkPlayerHasItem(player,
                                    ModItems.VOLCANO_HELMET.get(), 1)
                            || InventoryOperation.checkPlayerHasItem(player,
                                    ModItems.VOLCANO_CHEST.get(), 1)
                            || InventoryOperation.checkPlayerHasItem(player,
                                    ModItems.VOLCANO_LEGGINGS.get(), 1)
                            || InventoryOperation.checkPlayerHasItem(player,
                                    ModItems.VOLCANO_BOOTS.get(), 1);
                    return hasWeapon && hasArmor;
                default:
                    return false;
            }
        });
    }

    // =================================================================
    // 提交动作（消耗物品 → 推进步骤 → 发放奖励/连锁下一任务）
    // =================================================================

    public static MissionV2.PlayerAction getSubmitAction() {
        return (player -> {
            int step = getCurrentStep(player, MissionV2.EXPEDITION_O_LAVA.name());
            CompoundTag statusData = MissionV2Helper.getMissionV2StatusData(player);

            switch (step) {
                case 0:
                    // 寻访完成，无消耗，直接推进
                    break;
                case 1:
                    // 消耗火元素碎片
                    InventoryOperation.removeItemWithoutCheck(player,
                            new ItemStack(ModItems.FIRE_ELEMENT_PIECE_0.get(), 32));
                    break;
                case 2:
                    // 锻造完成，发放奖励
                    giveRewards(player);
                    // 标记远征O完成
                    statusData.putString(MissionV2.EXPEDITION_O_LAVA.name(),
                            MissionV2.Status.FINISHED);
                    // 推送远征I
                    statusData.putString(MissionV2.EXPEDITION_I_LUOYAN.name(),
                            MissionV2.Status.NOT_ACCEPTED);
                    Compute.sendFormatMSG(player, Te.s("远征", CustomStyle.styleOfRed),
                            Te.s("新远征任务已解锁: ", MissionV2.EXPEDITION_I_LUOYAN.title));
                    // 提前返回，避免下面的 setCurrentStep（已完成不需要再推进）
                    return;
            }

            // 推进到下一步（step 0→1, 1→2）
            if (step < EXPEDITION_O_STEP_COUNT - 1) {
                setCurrentStep(player, MissionV2.EXPEDITION_O_LAVA.name(), step + 1);
                // 保持 IN_PROGRESS 状态
                statusData.putString(MissionV2.EXPEDITION_O_LAVA.name(),
                        MissionV2.Status.IN_PROGRESS);
            }
        });
    }

    // =================================================================
    // 奖励
    // =================================================================

    public static List<Component> getRewardDescription() {
        return List.of(
                Te.s(" 金币 × 64", CustomStyle.styleOfGold),
                Te.s(" 世界之魂V × 10", CustomStyle.styleOfWorld),
                Te.s(" 天启之书 × 20", CustomStyle.styleOfSky),
                Te.s(" 解锁远征I - 炼狱 - 熔岩裂谷", CustomStyle.styleOfPower)
        );
    }

    private static void giveRewards(Player player) {
        InventoryOperation.giveItemStackWithMSG(player,
                new ItemStack(ModItems.GOLD_COIN.get(), 64));
        InventoryOperation.giveItemStackWithMSG(player,
                new ItemStack(ModItems.WORLD_SOUL_5.get(), 10));
        InventoryOperation.giveItemStackWithMSG(player,
                new ItemStack(ModItems.REVELATION_BOOK.get(), 20));
    }

    // =================================================================
    // 动态显示
    // =================================================================

    /**
     * @return 动态标题：固定返回任务名，不含步骤信息
     */
    public static MissionV2.ClientComponentOperation getTitleOperation() {
        return ((missionV2, data) -> missionV2.title);
    }

    /**
     * @return 动态提示：显示当前步骤的简短描述
     */
    public static MissionV2.ClientComponentOperation getTipsOperation() {
        return ((missionV2, data) -> {
            CompoundTag statusData = data.getCompound(MissionV2Helper.MISSION_V2_STATUS_KEY);
            String status = statusData.getString(missionV2.name());
            if (status.equals(MissionV2.Status.IN_PROGRESS)) {
                CompoundTag expeditionData = data.getCompound(EXPEDITION_DATA_KEY);
                CompoundTag questData = expeditionData.getCompound(missionV2.name());
                int step = questData.getInt(CURRENT_STEP_KEY);
                if (step >= 0 && step < STEP_DESCRIPTIONS.size()) {
                    return STEP_DESCRIPTIONS.get(step);
                }
            }
            return missionV2.tips;
        });
    }

    /**
     * @return 动态详情：鼠标悬停时显示当前步骤的详细说明
     */
    public static MissionV2.ClientComponentOperation getDetailOperation() {
        return ((missionV2, data) -> {
            CompoundTag statusData = data.getCompound(MissionV2Helper.MISSION_V2_STATUS_KEY);
            String status = statusData.getString(missionV2.name());
            if (status.equals(MissionV2.Status.IN_PROGRESS)) {
                CompoundTag expeditionData = data.getCompound(EXPEDITION_DATA_KEY);
                CompoundTag questData = expeditionData.getCompound(missionV2.name());
                int step = questData.getInt(CURRENT_STEP_KEY);
                if (step >= 0 && step < STEP_DETAILS.size()) {
                    return Te.s("当前步骤: ", CustomStyle.styleOfGold,
                            STEP_DESCRIPTIONS.get(step), "\n",
                            STEP_DETAILS.get(step));
                }
            }
            return missionV2.tips;
        });
    }
}
