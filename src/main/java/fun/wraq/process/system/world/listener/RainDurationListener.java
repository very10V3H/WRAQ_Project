package fun.wraq.process.system.world.listener;

import fun.wraq.process.system.world.WorldListener;
import net.minecraft.server.level.ServerLevel;

/**
 * 降水时长限制器：每次连续降水（降雨/雷暴）不得超过 2 分钟，超时后自动清除天气。
 * <p>
 * 由 {@link fun.wraq.process.system.world.WorldEventHandler} 在连续降水达到
 * {@link fun.wraq.process.system.world.WorldEventHandler#MAX_CONTINUOUS_RAIN_TICKS} 时回调，
 * 同一段降水只清除一次。
 * <p>
 * AI-Generated, 2026-10-25
 */
public class RainDurationListener implements WorldListener {

    @Override
    public void onRainLimitReached(ServerLevel level, int continuousRainTicks) {
        clearWeather(level);
    }

    /**
     * 清除该世界的降雨与雷暴。
     * <p>
     * 与原版 /weather clear 一致：{@link ServerLevel#setWeatherParameters(int, int, boolean, boolean)}
     * 不指定强制晴朗时长，原版天气循环会在下一 tick 重新抽取 12000~180000 tick 的放晴时间，
     * 因此不会出现清除后立刻再次下雨的情况。该调用会通过原版逻辑同步到客户端，
     * 雨声、雨滴与天空亮度的平滑过渡无需额外发包。
     */
    public static void clearWeather(ServerLevel level) {
        level.setWeatherParameters(0, 0, false, false);
    }
}
