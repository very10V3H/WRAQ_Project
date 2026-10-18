package fun.wraq.process.system.dialog;

import net.minecraft.network.chat.Component;

/**
 * AI-Generated, 2026-10-17
 * 一页对话里的单个选项。
 * <p>
 * {@code actionId} 是服务端与客户端之间唯一的动作标识，{@code label} / {@code hover} 只负责显示。
 * 服务端在 {@link DialogManager#select} 中会用「重建当前页」的方式校验 actionId 是否真实存在于当前状态，
 * 因此客户端伪造的 actionId（例如停留在冷却中却发送 accept）不会被执行。
 */
public record DialogOption(String actionId, Component label, Component hover) {
}
