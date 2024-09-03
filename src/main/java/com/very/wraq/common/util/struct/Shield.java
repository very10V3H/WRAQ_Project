package com.very.wraq.common.util.struct;

public class Shield {
    private int tick;
    private double value;

    public Shield(int tick, double value) {
        this.tick = tick;
        this.value = value;
    }

    public int getTick() {
        return tick;
    }

    public double getValue() {
        return value;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
