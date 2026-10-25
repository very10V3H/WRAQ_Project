package fun.wraq.process.system.world;

import net.minecraft.world.level.Level;

/**
 * 世界降水/天气状态。
 * <p>
 * 以 {@link Level#isRaining()} / {@link Level#isThundering()} 的实际渲染等级为准，
 * 因此下界、末地等无天空光照的维度不会被判定为降水。
 * AI-Generated, 2026-10-25
 */
public enum WorldWeather {

    /** 晴朗 */
    CLEAR,
    /** 降雨 */
    RAIN,
    /** 雷暴（同时伴随降雨） */
    THUNDER;

    public static WorldWeather of(Level level) {
        if (level.isThundering()) {
            return THUNDER;
        }
        if (level.isRaining()) {
            return RAIN;
        }
        return CLEAR;
    }

    /**
     * 是否处于降水状态（降雨或雷暴）。
     */
    public boolean isPrecipitating() {
        return this != CLEAR;
    }
}
