/** AI-Generated, 2026-10-17 */
package fun.wraq.process.system.npc;

import fun.wraq.common.impl.npc.VmdNpcLike;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

/**
 * 实体 → dialogId 的解析器。
 * <p>
 * 解析顺序（前面的优先）：
 * <ol>
 *     <li>实体自身声明了 dialogId（{@link VmdNpcLike#getDialogId()} 非空）；</li>
 *     <li>实体 persistentData 中写过 {@value #PERSISTENT_ID_KEY}（原版村民补写 id 后走这里）；</li>
 *     <li>按显示名反查 {@link NpcIds#byDisplayName(String)}（未迁移实体的兼容回退）。</li>
 * </ol>
 * 第 3 级是「永不断链」的保证：即使世界上有玩家自己 {@code /summon} 的村民，
 * 只要名字与 {@link NpcIds} 登记的一致，行为与改造前完全一样。
 */
public final class NpcIdResolver {

    /** 原版实体的 dialogId 存在这里（Forge 通用 persistentData，不改动实体本体 NBT）。 */
    public static final String PERSISTENT_ID_KEY = "vmd.npcId";

    private NpcIdResolver() {
    }

    /**
     * 解析实体的 dialogId。
     *
     * @return 匹配的 dialogId；完全无法识别时返回 {@code null}（调用方应视为「不是本系统的 NPC」）
     */
    public static String resolve(Entity entity) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof VmdNpcLike like) {
            String declared = like.getDialogId();
            if (declared != null && !declared.isEmpty()) {
                return declared;
            }
        }
        String stored = storedId(entity);
        if (stored != null && !stored.isEmpty()) {
            return stored;
        }
        return NpcIds.byDisplayName(displayNameOf(entity));
    }

    /** 直接把 id 写到实体上（对原版村民即补写 persistentData）。 */
    public static void stamp(Entity entity, String dialogId) {
        if (entity == null) {
            return;
        }
        entity.getPersistentData().putString(PERSISTENT_ID_KEY, dialogId == null ? "" : dialogId);
    }

    /** 读取实体上已写死的 id（不参与名字回退）。 */
    public static String storedId(Entity entity) {
        return entity.getPersistentData().getString(PERSISTENT_ID_KEY);
    }

    /** 取实体显示名（纯文本，不含样式）。 */
    public static String displayNameOf(Entity entity) {
        Component customName = entity.getCustomName();
        return customName != null ? customName.getString() : entity.getName().getString();
    }
}
