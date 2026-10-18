package fun.wraq.process.system.dialog;

import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * AI-Generated, 2026-10-17
 * 一页对话：说话者 + 正文若干行 + 可选动作列表。
 * <p>
 * 每次下发都携带新的 {@code sessionId}，客户端点击时原样回传，供 {@link DialogManager} 做会话校验
 * （玩家 / NPC / 页 / 时间窗 / 距离）。
 *
 * @param entityId 对话实体在客户端世界的实体 id，用于在界面左侧渲染其模型；{@code -1} 表示不渲染
 */
public record DialogPage(String npcId, String sessionId, Component speaker,
                         List<Component> lines, List<DialogOption> options, int entityId) {

    public DialogPage(String npcId, String sessionId, Component speaker,
                      List<Component> lines, List<DialogOption> options) {
        this(npcId, sessionId, speaker, lines, options, -1);
    }

    /** 由 {@link DialogManager} 在发送前补上实体 id。 */
    public DialogPage withEntityId(int entityId) {
        return new DialogPage(npcId, sessionId, speaker, lines, options, entityId);
    }

    public boolean hasAction(String actionId) {
        for (DialogOption option : options) {
            if (option.actionId().equals(actionId)) {
                return true;
            }
        }
        return false;
    }
}
