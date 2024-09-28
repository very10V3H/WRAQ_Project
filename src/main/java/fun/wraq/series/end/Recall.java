package fun.wraq.series.end;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class Recall {
    public int recallCount;
    public ServerPlayer recallPlayer;
    public int killCount;
    public int recallTimeLimit;
    public boolean recallSuccess;
    public Component playerName;
    public String zoneName;
    public Style style;
    public Entity mob;
    public Entity mob1;

    public Recall() {
        recallCount = -1;
        recallPlayer = null;
        killCount = -1;
        recallTimeLimit = -1;
        recallSuccess = false;
        playerName = null;
        zoneName = null;
        style = null;
        mob = null;
        mob1 = null;
    }

    public void Reset() {
        recallCount = -1;
        recallPlayer = null;
        killCount = -1;
        recallTimeLimit = -1;
        recallSuccess = false;
        playerName = null;
        zoneName = null;
        style = null;
        if (mob != null) mob.remove(Entity.RemovalReason.KILLED);
        mob = null;
        if (mob1 != null) mob1.remove(Entity.RemovalReason.KILLED);
        mob1 = null;
    }
}
