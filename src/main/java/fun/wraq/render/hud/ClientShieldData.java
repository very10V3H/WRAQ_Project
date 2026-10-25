/**
 * AI-Generated, 2026-10-17
 */
package fun.wraq.render.hud;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端护盾数据。
 *
 * <p>{@link #getValue()} / {@link #get()} 是本地玩家自己的护盾（由 {@code ShieldSyncS2CPacket} 每帧同步）；
 * {@link #getEntityShield(int)} 是其他玩家头顶血条用的护盾值（由服务端广播给附近玩家，
 * 带过期时间，避免实体 id 复用或玩家下线后残留）。</p>
 */
public class ClientShieldData {
    private static int Shield = 0;
    private static int ShieldValue = 0;

    /** 实体 id → 护盾值（其他玩家） */
    private static final Map<Integer, Double> ENTITY_SHIELD = new ConcurrentHashMap<>();
    /** 实体 id → 最后一次收到同步的时间戳 */
    private static final Map<Integer, Long> ENTITY_SHIELD_TIME = new ConcurrentHashMap<>();
    /** 超过该时间未收到刷新就视为护盾已结束 */
    private static final long ENTITY_SHIELD_TIMEOUT_MS = 5000L;

    public static void set(int Shield) {
        ClientShieldData.Shield = Shield;
    }

    public static int get() {
        return Shield;
    }

    public static void setValue(int ShieldValue) {
        ClientShieldData.ShieldValue = ShieldValue;
    }

    public static int getValue() {
        return ShieldValue;
    }

    /**
     * 收到某个实体的护盾同步。
     *
     * @param entityId 实体 id
     * @param value    护盾值，&lt;= 0 表示护盾结束
     */
    public static void setEntityShield(int entityId, double value) {
        if (value <= 0.0) {
            ENTITY_SHIELD.remove(entityId);
            ENTITY_SHIELD_TIME.remove(entityId);
            return;
        }
        ENTITY_SHIELD.put(entityId, value);
        ENTITY_SHIELD_TIME.put(entityId, System.currentTimeMillis());
    }

    /**
     * 获取某个实体的护盾值；无数据或已过期返回 0。
     */
    public static double getEntityShield(int entityId) {
        Long time = ENTITY_SHIELD_TIME.get(entityId);
        if (time == null) {
            return 0.0;
        }
        if (System.currentTimeMillis() - time > ENTITY_SHIELD_TIMEOUT_MS) {
            ENTITY_SHIELD.remove(entityId);
            ENTITY_SHIELD_TIME.remove(entityId);
            return 0.0;
        }
        return ENTITY_SHIELD.getOrDefault(entityId, 0.0);
    }
}
