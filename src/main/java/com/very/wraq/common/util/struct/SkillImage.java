package com.very.wraq.common.util.struct;

public class SkillImage {
    private int MaxTime = 0;
    private int TickTime = 0;
    private int Num = 0;

    public SkillImage(int MaxTime, int TickTime, int Num) {
        this.MaxTime = MaxTime;
        this.TickTime = TickTime;
        this.Num = Num;
    }

    public int getNum() {
        return Num;
    }

    public int getMaxTime() {
        return MaxTime;
    }

    public int getTickTime() {
        return TickTime;
    }

    public void setMaxTime(int maxTime) {
        MaxTime = maxTime;
    }

    public void setNum(int num) {
        Num = num;
    }

    public void setTickTime(int tickTime) {
        TickTime = tickTime;
    }
}
