package com.very.wraq.process.Lottery;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Lottery {
    public static boolean PlayerIsInLotteryZone(Player player) {
        Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
        double boundaryX1 = 1547, boundaryX2 = 1589, boundaryZ1 = 1041, boundaryZ2 = 1083, boundaryY1 = 65, boundaryY2 = 136;
        return player.level().equals(overWorld) && player.getX() > boundaryX1 && player.getX() < boundaryX2
                && player.getZ() > boundaryZ1 && player.getZ() < boundaryZ2 && player.getY() > boundaryY1
                && player.getY() < boundaryY2;
    }
}
