package com.Very.very.Series.EndSeries;

import net.minecraft.server.level.ServerPlayer;

public class Recall {
    public int RecallCount;
    public ServerPlayer RecallPlayer;
    public int KillCount;
    public int RecallTimeLimit;
    public boolean RecallSuccess;

    public Recall() {
        RecallCount = -1;
        RecallPlayer = null;
        KillCount = -1;
        RecallTimeLimit = -1;
        RecallSuccess = false;
    }
    public void Reset() {
        RecallCount = -1;
        RecallPlayer = null;
        KillCount = -1;
        RecallTimeLimit = -1;
        RecallSuccess = false;

    }
}
