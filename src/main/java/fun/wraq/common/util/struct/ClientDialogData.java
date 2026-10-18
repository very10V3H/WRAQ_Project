package fun.wraq.common.util.struct;

import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * AI-Generated, 2026-10-17
 * 客户端持有的待渲染对话数据（由 DialogOpenS2CPacket 写入，供 DialogScreen 读取）。
 * 放在 struct 包而不是 render 包，是为了让网络包与界面代码都能引用它而不产生客户端类依赖。
 *
 * @param entityId 对话实体在客户端世界的实体 id，-1 表示不渲染模型
 */
public record ClientDialogData(String npcId, String sessionId, Component speaker,
                               List<Component> lines, List<Component> optionLabels,
                               List<Component> optionHovers, List<String> optionActionIds,
                               int entityId) {
}
