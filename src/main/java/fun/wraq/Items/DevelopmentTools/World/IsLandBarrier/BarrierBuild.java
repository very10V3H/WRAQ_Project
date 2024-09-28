package fun.wraq.Items.DevelopmentTools.World.IsLandBarrier;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class BarrierBuild {
    public static void Build(Level level) {
        for (int j = 60; j <= 65; j++) {
            for (int i = 371; i <= 633; i++) {
                Block block = Blocks.GILDED_BLACKSTONE;
                BlockPos blockPos = new BlockPos(1182, j, i);
                BlockPos blockPos1 = new BlockPos(1523, j, i);
                level.setBlockAndUpdate(blockPos, block.defaultBlockState());
                level.setBlockAndUpdate(blockPos1, block.defaultBlockState());
            }
            for (int i = 1182; i <= 1523; i++) {
                Block block = Blocks.GILDED_BLACKSTONE;
                BlockPos blockPos = new BlockPos(i, j, 633);
                BlockPos blockPos1 = new BlockPos(i, j, 371);
                level.setBlockAndUpdate(blockPos, block.defaultBlockState());
                level.setBlockAndUpdate(blockPos1, block.defaultBlockState());
            }
        }
    }

    public static void Destroy(Level level) {
        for (int j = 60; j <= 65; j++) {
            for (int i = 371; i <= 633; i++) {
                BlockPos blockPos = new BlockPos(1182, j, i);
                BlockPos blockPos1 = new BlockPos(1523, j, i);
                level.removeBlock(blockPos, false);
                level.removeBlock(blockPos1, false);
            }
            for (int i = 1182; i <= 1523; i++) {
                BlockPos blockPos = new BlockPos(i, j, 633);
                BlockPos blockPos1 = new BlockPos(i, j, 371);
                level.removeBlock(blockPos, false);
                level.removeBlock(blockPos1, false);

            }
        }
    }
}
