package fun.wraq.common.util.struct;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockAndResetTime {
    private BlockState blockState;
    private BlockPos blockPos;
    private int resetTick;

    public BlockAndResetTime(BlockState blockState, BlockPos blockPos, int resetTick) {
        this.blockState = blockState;
        this.blockPos = blockPos;
        this.resetTick = resetTick;
    }

    public void setBlockState(BlockState blockState) {
        this.blockState = blockState;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public int getResetTick() {
        return resetTick;
    }

    public void setResetTick(int resetTick) {
        this.resetTick = resetTick;
    }
}
