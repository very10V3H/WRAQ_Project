package com.very.wraq.common.Utils.Struct;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockSP {
    private BlockPos blockPos;
    private BlockState blockState;

    public BlockSP(BlockPos blockPos, BlockState blockState) {
        this.blockPos = blockPos;
        this.blockState = blockState;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public void setBlockState(BlockState blockState) {
        this.blockState = blockState;
    }
}
