package fun.wraq.common.util.struct;

import net.minecraft.world.phys.Vec3;

/**
 * AI-Generated, 2026-07-18
 * Mana AOE 扩散效果数据，由 ManaAoeRenderer 渲染。
 */
public class ManaAoeEffect {
    private final Vec3 pos;
    private final long startTimeMs;
    private final long durationMs;
    private final int color;

    public ManaAoeEffect(Vec3 pos, long startTimeMs, long durationMs, int color) {
        this.pos = pos;
        this.startTimeMs = startTimeMs;
        this.durationMs = durationMs;
        this.color = color;
    }

    public Vec3 getPos() { return pos; }
    public long getStartTimeMs() { return startTimeMs; }
    public long getDurationMs() { return durationMs; }
    public int getColor() { return color; }
}
