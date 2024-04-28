package com.very.wraq.series.overWorld.MainStory_I.WaterSystem;

public class LakePowerEffect {
    private final int Tick;
    private final int Effect;
    public LakePowerEffect(int tick, int effect) {
        this.Tick = tick;
        this.Effect = effect;
    }

    public int getEffect() {
        return Effect;
    }

    public int getTick() {
        return Tick;
    }
}
