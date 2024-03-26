package com.Very.very.Series.EndSeries;

import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class Recall {
    public int RecallCount;
    public ServerPlayer RecallPlayer;
    public int KillCount;
    public int RecallTimeLimit;
    public boolean RecallSuccess;
    public Component playerName;
    public String zoneName;
    public Style style;
    public Entity mob;
    public Entity mob1;

    public Recall() {
        RecallCount = -1;
        RecallPlayer = null;
        KillCount = -1;
        RecallTimeLimit = -1;
        RecallSuccess = false;
        playerName = null;
        zoneName = null;
        style = null;
        mob = null;
        mob1 = null;
    }
    public void Reset() {
        RecallCount = -1;
        RecallPlayer = null;
        KillCount = -1;
        RecallTimeLimit = -1;
        RecallSuccess = false;
        playerName = null;
        zoneName = null;
        style = null;
        if (mob != null) mob.remove(Entity.RemovalReason.KILLED);
        mob = null;
        if (mob1 != null) mob1.remove(Entity.RemovalReason.KILLED);
        mob1 = null;
    }
}
