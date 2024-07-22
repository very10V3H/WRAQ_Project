package com.very.wraq.Items.DevelopmentTools.World.IsLandBarrier;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TowerBuild {
    public static void Build(Level level) {
        int X1 = 1416;
        int X2 = 1440;
        int Z1 = 576;
        int Z2 = 552;
        for (int i = 72; i <= 254; i++) {
            if (i % 15 == 8) {
                X1++;
                X2--;
                Z1--;
                Z2++;
            }
            if (X1 > X2 || Z1 < Z2) break;
            for (int j = X1; j <= X2; j++) {
                Block block = Blocks.CALCITE;
                BlockState blockState = block.defaultBlockState();
                BlockPos blockPos = new BlockPos(j, i, Z1);
                BlockPos blockPos1 = new BlockPos(j, i, Z2);
                level.setBlockAndUpdate(blockPos, blockState);
                level.setBlockAndUpdate(blockPos1, blockState);
            }
            for (int j = Z2; j <= Z1; j++) {
                Block block = Blocks.CALCITE;
                BlockState blockState = block.defaultBlockState();
                BlockPos blockPos = new BlockPos(X1, i, j);
                BlockPos blockPos1 = new BlockPos(X2, i, j);
                level.setBlockAndUpdate(blockPos, blockState);
                level.setBlockAndUpdate(blockPos1, blockState);
            }
        }
    }

    public static void Destory(Level level) {
        int X1 = 1416;
        int X2 = 1440;
        int Z1 = 576;
        int Z2 = 552;
        for (int i = 72; i <= 254; i++) {
            if (i % 15 == 8) {
                X1++;
                X2--;
                Z1--;
                Z2++;
            }
            if (X1 > X2 || Z1 < Z2) break;
            for (int j = X1; j <= X2; j++) {
                BlockPos blockPos = new BlockPos(j, i, Z1);
                BlockPos blockPos1 = new BlockPos(j, i, Z2);
                level.removeBlock(blockPos, false);
                level.removeBlock(blockPos1, false);
            }
            for (int j = Z2; j <= Z1; j++) {
                BlockPos blockPos = new BlockPos(X1, i, j);
                BlockPos blockPos1 = new BlockPos(X2, i, j);
                level.removeBlock(blockPos, false);
                level.removeBlock(blockPos1, false);
            }
        }
    }
}
