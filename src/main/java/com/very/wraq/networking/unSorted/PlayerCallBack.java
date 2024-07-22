package com.very.wraq.networking.unSorted;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public class PlayerCallBack {
    private BlockPos blockPos;
    private Player player;
    private Integer time;

    public PlayerCallBack(BlockPos blockPos, Player player, Integer time) {
        this.blockPos = blockPos;
        this.player = player;
        this.time = time;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Integer getTime() {
        return this.time;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }
}
