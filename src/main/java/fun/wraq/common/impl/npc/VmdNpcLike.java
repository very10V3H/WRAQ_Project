/** AI-Generated, 2026-10-17 */
package fun.wraq.common.impl.npc;

import net.minecraft.network.chat.Component;

/**
 * 统一「VMD 可交互 NPC」的抽象。
 * <p>
 * 目前有两类实现：
 * <ul>
 *     <li>{@link fun.wraq.entities.entities.npc.VmdNpc}：玩家模型 NPC（带皮肤、装备、注视）；</li>
 *     <li>原版 {@code Villager}：通过 {@link fun.wraq.process.system.npc.NpcIdResolver} 适配，
 *         其 dialogId 存放在实体 persistentData 中。</li>
 * </ul>
 * 交互事件只依赖本接口，避免再次出现「按实体类型 if-else」的硬编码。
 */
public interface VmdNpcLike {

    /** 对话树标识（{@link fun.wraq.process.system.npc.NpcIds} 中的常量），用于路由对话。 */
    String getDialogId();

    /** 展示名（名字板文本），仅用于显示，不参与路由。 */
    Component getNpcName();
}
