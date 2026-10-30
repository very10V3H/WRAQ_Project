package fun.wraq.process.system.missions.side;

import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.achievement.AchievementManager;
import fun.wraq.process.system.missions.mission2.MissionV2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;

/**
 * AI-Generated, 2026-10-28
 * 支线「元素学」：玩家达到 135 级后解锁。
 * 第一项任务为按左 Alt 打开元素轮盘。
 */
public final class ElementStudyQuest {

    private static final String OPENED_ROULETTE_KEY = "SideElementI_OpenedRoulette";

    private ElementStudyQuest() {
    }

    public static boolean canAccept(Player player) {
        return player.experienceLevel >= Element.ROULETTE_REQUIRED_LEVEL;
    }

    /** 达到 135 级时解锁第一项元素学任务。 */
    public static void refreshUnlock(Player player) {
        if (canAccept(player)
                && MissionV2.SIDE_ELEMENT_I.getPlayerStatus(player).isEmpty()) {
            MissionV2.SIDE_ELEMENT_I.setPlayerStatus(player, MissionV2.Status.NOT_ACCEPTED);
            MissionV2.sendDataToClient(player);
            MissionV2.sendMsgForUnlock(player, MissionV2.SIDE_ELEMENT_I);
        }
    }

    /** 打开元素轮盘时调用；直接打开时自动接取并完成第一项。 */
    public static void onOpenedRoulette(Player player) {
        String status = MissionV2.SIDE_ELEMENT_I.getPlayerStatus(player);
        if (status.isEmpty() && canAccept(player)) {
            MissionV2.SIDE_ELEMENT_I.setPlayerStatus(player, MissionV2.Status.NOT_ACCEPTED);
            MissionV2.sendDataToClient(player);
            status = MissionV2.Status.NOT_ACCEPTED;
        }
        if (status.equals(MissionV2.Status.NOT_ACCEPTED)) {
            MissionV2.SIDE_ELEMENT_I.setPlayerStatus(player, MissionV2.Status.IN_PROGRESS);
            MissionV2.sendDataToClient(player);
            status = MissionV2.Status.IN_PROGRESS;
        }
        if (!status.equals(MissionV2.Status.IN_PROGRESS)) {
            return;
        }
        player.getPersistentData().putBoolean(OPENED_ROULETTE_KEY, true);
        MissionV2.autoSubmit(player);
        AchievementManager.onElementOpened(player);
    }

    public static boolean hasOpenedRoulette(Player player) {
        return player.getPersistentData().getBoolean(OPENED_ROULETTE_KEY);
    }

    public static void claimReward(Player player) {
        player.getPersistentData().remove(OPENED_ROULETTE_KEY);
    }

    public static List<Component> rewardDescription() {
        return List.of();
    }
}
