package fun.wraq.common.util.struct;

import fun.wraq.common.fast.Tick;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public static Map<Level, Set<BlockAndResetTime>> setMap = new HashMap<>();

    public static Set<BlockAndResetTime> getSet(Level level) {
        if (!setMap.containsKey(level)) {
            setMap.put(level, new HashSet<>());
        }
        return setMap.get(level);
    }

    public static void createThenDestroy(Level level, BlockAndResetTime blockAndResetTime) {
        getSet(level).add(blockAndResetTime);
        level.setBlockAndUpdate(blockAndResetTime.blockPos, blockAndResetTime.blockState);
    }

    public static void handleTick(Level level) {
        getSet(level).removeIf(blockAndResetTime -> {
            if (blockAndResetTime.resetTick < Tick.get()) {
                level.destroyBlock(blockAndResetTime.blockPos, false);
                return true;
            }
            return false;
        });
    }

    public static void onServerStop() {
        setMap.forEach((key, value) -> {
            if (value != null) {
                value.forEach(blockAndResetTime -> {
                    key.destroyBlock(blockAndResetTime.blockPos, false);
                });
            }
        });
    }
}
