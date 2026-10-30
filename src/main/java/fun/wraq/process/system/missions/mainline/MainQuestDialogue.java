package fun.wraq.process.system.missions.mainline;

import fun.wraq.common.fast.Te;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.dialog.DialogCloseS2CPacket;
import fun.wraq.process.system.dialog.DialogManager;
import fun.wraq.process.system.dialog.DialogOption;
import fun.wraq.process.system.dialog.DialogPage;
import fun.wraq.process.system.dialog.DialogTrees;
import fun.wraq.process.system.missions.mainline.MainQuestStage.Dialogue;
import fun.wraq.process.system.missions.mainline.MainQuestStage.Kind;
import fun.wraq.process.system.missions.mainline.MainQuestStage.Stage;
import fun.wraq.process.system.missions.mission2.MissionV2;
import fun.wraq.process.system.npc.NpcIds;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * AI-Generated, 2026-10-18
 * 主线对话树：一棵实现服务全部主线 NPC。
 * <p>
 * 状态 → 页面：
 * <ul>
 *     <li>当前阶段 {@code NOT_ACCEPTED} + 对话者是告知 NPC → 需求页（accept）</li>
 *     <li>当前阶段 {@code IN_PROGRESS} + 对话者是交付 NPC 且目标达成 → 交付页（complete）</li>
 *     <li>{@code IN_PROGRESS} 未达成 → 进度页</li>
 *     <li>{@code FINISHED} 或已完成过的 NPC → 回访页</li>
 *     <li>其它 → 指路页</li>
 * </ul>
 * 只有这里的 accept / complete 能推进阶段；客户端伪造 actionId 会被
 * {@link DialogManager} 的动作白名单与本类的二次校验拦住。
 */
public final class MainQuestDialogue implements DialogTrees.DialogTree {

    public static final MainQuestDialogue INSTANCE = new MainQuestDialogue();

    private MainQuestDialogue() {
    }

    @Override
    public Component onOpen(Player player, String dialogId) {
        // 玩家直接走到天空城贝贝处时，补记 MAIN_01 的到达进度。
        if (NpcIds.MAIN_BEIBEI.equals(dialogId)) {
            MainQuestHelper.handleSkyCityBeibeiArrival(player);
        }
        return null;
    }

    @Override
    public DialogPage buildPage(Player player, String dialogId, String sessionId, Component notice) {
        Stage stage = MainQuestHelper.current(player);
        if (stage != null) {
            String status = MainQuestHelper.status(player, stage);
            String report = stage.effectiveReportNpc();
            if (report.equals(dialogId) && status.equals(MissionV2.Status.IN_PROGRESS)) {
                return MainQuestHelper.ready(player, stage)
                        ? readyPage(stage, player, dialogId, sessionId)
                        : progressPage(stage, player, dialogId, sessionId);
            }
            if (stage.npc().equals(dialogId)) {
                if (status.equals(MissionV2.Status.NOT_ACCEPTED)) {
                    return firstPage(stage, dialogId, sessionId);
                }
                if (status.equals(MissionV2.Status.IN_PROGRESS)) {
                    return progressPage(stage, player, dialogId, sessionId);
                }
                if (status.equals(MissionV2.Status.FINISHED)) {
                    return donePage(stage, dialogId, sessionId);
                }
            }
            if (report.equals(dialogId) && status.equals(MissionV2.Status.FINISHED)) {
                return donePage(stage, dialogId, sessionId);
            }
        }
        // 已完成过的阶段：对该 NPC 说一句回访台词
        for (int i = MainQuestData.STAGES.size() - 1; i >= 0; i--) {
            Stage finished = MainQuestData.STAGES.get(i);
            if (MainQuestHelper.status(player, finished).equals(MissionV2.Status.FINISHED)
                    && (finished.npc().equals(dialogId)
                    || finished.effectiveReportNpc().equals(dialogId))) {
                return donePage(finished, dialogId, sessionId);
            }
        }
        return idlePage(dialogId, sessionId);
    }

    @Override
    public DialogPage handle(Player player, String dialogId, String sessionId, String actionId) {
        Stage stage = MainQuestHelper.current(player);
        if (stage == null) {
            return null;
        }
        if ("accept".equals(actionId)) {
            if (stage.npc().equals(dialogId) && MainQuestHelper.accept(player, stage)) {
                return buildPage(player, dialogId, sessionId, null);
            }
            return null;
        }
        if ("complete".equals(actionId)) {
            if (stage.effectiveReportNpc().equals(dialogId) && MainQuestHelper.complete(player, stage)) {
                return buildPage(player, dialogId, sessionId, null);
            }
            return null;
        }
        if ("close".equals(actionId)) {
            DialogManager.close(player);
            if (player instanceof ServerPlayer serverPlayer) {
                ModNetworking.sendToClient(new DialogCloseS2CPacket(), serverPlayer);
            }
            return null;
        }
        return null;
    }

    // ------------------------------------------------------------------
    // 页面
    // ------------------------------------------------------------------

    private DialogPage firstPage(Stage stage, String dialogId, String sessionId) {
        Dialogue dialogue = stage.dialogue();
        List<DialogOption> options = new ArrayList<>();
        if (stage.kind() == Kind.TALK) {
            options.add(new DialogOption("complete",
                    Te.s(dialogue.firstLabel(), ChatFormatting.GREEN), Te.s("完成本阶段")));
        } else {
            options.add(new DialogOption("accept",
                    Te.s(dialogue.firstLabel(), ChatFormatting.GREEN), Te.s("接取主线")));
        }
        options.add(closeOption());
        return page(dialogId, sessionId, dialogue.firstLines(), options);
    }

    private DialogPage progressPage(Stage stage, Player player, String dialogId, String sessionId) {
        Dialogue dialogue = stage.dialogue();
        String text = dialogue.progressText();
        List<Component> lines = List.of(text != null ? Te.s(text) : MainQuestHelper.objectiveText(player, stage));
        return page(dialogId, sessionId, lines, List.of(closeOption()));
    }

    private DialogPage readyPage(Stage stage, Player player, String dialogId, String sessionId) {
        Dialogue dialogue = stage.dialogue();
        List<Component> lines = dialogue.readyLines().isEmpty()
                ? List.of(MainQuestHelper.objectiveText(player, stage))
                : dialogue.readyLines();
        List<DialogOption> options = List.of(
                new DialogOption("complete", Te.s(dialogue.readyLabel(), ChatFormatting.YELLOW),
                        Te.s("完成本阶段")),
                closeOption());
        return page(dialogId, sessionId, lines, options);
    }

    private DialogPage donePage(Stage stage, String dialogId, String sessionId) {
        String text = stage.dialogue().doneText();
        List<Component> lines = List.of(text == null ? Te.s("这件事已经处理完了。") : Te.s(text));
        return page(dialogId, sessionId, lines, List.of(closeOption()));
    }

    private DialogPage idlePage(String dialogId, String sessionId) {
        return page(dialogId, sessionId,
                List.of(Te.s("【剧情占位】现在还不是时候。")), List.of(closeOption()));
    }

    private DialogPage page(String dialogId, String sessionId, List<Component> lines,
                            List<DialogOption> options) {
        return new DialogPage(dialogId, sessionId,
                Te.s(displayName(dialogId), CustomStyle.styleOfSky), lines, options);
    }

    private DialogOption closeOption() {
        return new DialogOption("close", Te.s("「稍后」", ChatFormatting.GRAY), Te.s("关闭对话"));
    }

    private static String displayName(String dialogId) {
        String name = NpcIds.displayNameOf(dialogId);
        return name == null ? dialogId : name;
    }
}
