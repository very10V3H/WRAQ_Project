package com.very.wraq.common.Utils.Struct;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class LastDamage {
    public Mob mob;
    public Player player;
    public boolean IsAd;
    public int Tick;
    public int PerTick;
    public double Rate;

    public LastDamage(Player player, Mob mob, boolean isAd, int tick, int perTick, double rate) {
        this.player = player;
        this.mob = mob;
        this.IsAd = isAd;
        this.Tick = tick;
        this.PerTick = perTick;
        this.Rate = rate;
    }
}
