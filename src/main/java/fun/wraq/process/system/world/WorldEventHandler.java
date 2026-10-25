package fun.wraq.process.system.world;

import com.mojang.logging.LogUtils;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.system.world.listener.RainDurationListener;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 世界监听机制。
 * <p>
 * 统一在服务端逐世界（维度）监听天气状态：记录每个世界的天气、连续降水开始时间与持续时长，
 * 并把变化分发给注册的 {@link WorldListener}。所有监听逻辑集中在此处，避免各处重复订阅
 * {@link TickEvent.LevelTickEvent}。
 * <p>
 * 内置规则：每次连续降水（降雨/雷暴）不得超过 {@link #MAX_CONTINUOUS_RAIN_TICKS}（2 分钟），
 * 超时由 {@link RainDurationListener} 自动清除天气。
 * <p>
 * AI-Generated, 2026-10-25
 */
@Mod.EventBusSubscriber
public class WorldEventHandler {

    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * 连续降水时长上限：2 分钟（2400 tick）。
     */
    public static final int MAX_CONTINUOUS_RAIN_TICKS = Tick.min(2);

    private static final List<WorldListener> LISTENERS = new ArrayList<>();
    private static final Map<ResourceKey<Level>, WorldState> STATES = new HashMap<>();

    static {
        register(new RainDurationListener());
    }

    /**
     * 注册一个世界监听器。重复注册同一实例会被忽略。
     */
    public static void register(WorldListener listener) {
        if (listener != null && !LISTENERS.contains(listener)) {
            LISTENERS.add(listener);
        }
    }

    /**
     * 指定世界当前的连续降水时长（tick），无降水时为 0。
     */
    public static int getContinuousRainTicks(ServerLevel level) {
        WorldState state = STATES.get(level.dimension());
        return state == null ? 0 : state.continuousRainTicks;
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (!event.side.isServer() || event.phase != TickEvent.Phase.START) {
            return;
        }
        if (!(event.level instanceof ServerLevel level)) {
            return;
        }
        tickLevel(level);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        // 同一 JVM 内重开存档时 gameTime 会重置，旧的计时数据必须丢弃
        STATES.clear();
    }

    private static void tickLevel(ServerLevel level) {
        dispatch(listener -> listener.onLevelTick(level));

        WorldWeather weather = WorldWeather.of(level);
        long gameTime = level.getGameTime();
        WorldState state = STATES.computeIfAbsent(level.dimension(), key -> new WorldState());

        if (state.weather != weather) {
            WorldWeather previous = state.weather;
            state.weather = weather;

            if (!previous.isPrecipitating() && weather.isPrecipitating()) {
                state.rainStartTime = gameTime;
                state.continuousRainTicks = 0;
                state.rainLimitReached = false;
                dispatch(listener -> listener.onRainStart(level));
            } else if (previous.isPrecipitating() && !weather.isPrecipitating()) {
                int duration = state.continuousRainTicks;
                state.continuousRainTicks = 0;
                state.rainLimitReached = false;
                dispatch(listener -> listener.onRainEnd(level, duration));
            }

            dispatch(listener -> listener.onWeatherChange(level, previous, weather));
        }

        if (!weather.isPrecipitating()) {
            return;
        }

        state.continuousRainTicks = (int) Math.min(Integer.MAX_VALUE, Math.max(0, gameTime - state.rainStartTime));
        int continuousRainTicks = state.continuousRainTicks;
        dispatch(listener -> listener.onRainTick(level, continuousRainTicks));

        if (!state.rainLimitReached && continuousRainTicks >= MAX_CONTINUOUS_RAIN_TICKS) {
            state.rainLimitReached = true;
            LOGGER.info("[VMD-World] {} 连续降水已达到 {} tick（{} 分钟）上限，自动清除天气.",
                    level.dimension().location(), continuousRainTicks, continuousRainTicks / 1200);
            dispatch(listener -> listener.onRainLimitReached(level, continuousRainTicks));
        }
    }

    /**
     * 逐个分发给监听器，单个监听器抛出异常时不影响其他监听器与服务端 tick。
     */
    private static void dispatch(Consumer<WorldListener> action) {
        for (WorldListener listener : LISTENERS) {
            try {
                action.accept(listener);
            } catch (Exception exception) {
                LOGGER.error("[VMD-World] 世界监听器 {} 执行异常.", listener.getClass().getName(), exception);
            }
        }
    }

    /**
     * 单个世界的监听状态。
     */
    private static final class WorldState {

        private WorldWeather weather = WorldWeather.CLEAR;
        /** 本段连续降水的起始 gameTime，仅用于计算时长 */
        private long rainStartTime;
        /** 本段连续降水已持续的 tick 数 */
        private int continuousRainTicks;
        /** 本段连续降水是否已触发过超时规则 */
        private boolean rainLimitReached;
    }
}
