package fun.wraq.process.system.world;

import net.minecraft.server.level.ServerLevel;

/**
 * 世界监听器。
 * <p>
 * 由 {@link WorldEventHandler} 统一分发：服务端每个世界（维度）每 tick 检查一次，
 * 监听器无需自行订阅 {@link net.minecraftforge.event.TickEvent.LevelTickEvent}。
 * <p>
 * 所有方法均为默认空实现，按需重写即可；回调中的 level 一定是服务端世界。
 * AI-Generated, 2026-10-25
 */
public interface WorldListener {

    /**
     * 每个服务端世界每 tick 的通用回调，用于与该世界相关的周期性逻辑。
     */
    default void onLevelTick(ServerLevel level) {
    }

    /**
     * 天气类型发生切换时调用（晴朗 / 降雨 / 雷暴 之间）。
     */
    default void onWeatherChange(ServerLevel level, WorldWeather from, WorldWeather to) {
    }

    /**
     * 一段连续降水开始时调用。
     */
    default void onRainStart(ServerLevel level) {
    }

    /**
     * 连续降水期间的每 tick 回调。
     *
     * @param continuousRainTicks 本次连续降水已经持续的 tick 数（从 0 开始）
     */
    default void onRainTick(ServerLevel level, int continuousRainTicks) {
    }

    /**
     * 一段连续降水结束时调用（自然放晴或被清除）。
     *
     * @param continuousRainTicks 本次连续降水的总时长（tick）
     */
    default void onRainEnd(ServerLevel level, int continuousRainTicks) {
    }

    /**
     * 连续降水时长达到 {@link WorldEventHandler#MAX_CONTINUOUS_RAIN_TICKS} 时调用。
     * <p>
     * 同一段连续降水只会回调一次，直到放晴后重新开始下雨才会再次回调。
     *
     * @param continuousRainTicks 本次连续降水已经持续的 tick 数
     */
    default void onRainLimitReached(ServerLevel level, int continuousRainTicks) {
    }
}
