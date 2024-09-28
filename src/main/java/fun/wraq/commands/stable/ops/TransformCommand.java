package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TransformCommand implements Command<CommandSourceStack> {
    public static TransformCommand instance = new TransformCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        int PosX = player.getBlockX();
        int PosY = player.getBlockY();
        int PosZ = player.getBlockZ();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    BlockPos blockPos = new BlockPos(PosX - 50 + i, PosY - 50 + j, PosZ - 50 + k);
                    BlockState blockState = player.level().getBlockState(blockPos);
                    if (blockState.is(Blocks.MYCELIUM)) {
                        player.level().setBlockAndUpdate(blockPos, Blocks.NETHERRACK.defaultBlockState());
                    }
                    if (blockState.is(Blocks.MUSHROOM_STEM)) {
                        player.level().setBlockAndUpdate(blockPos, Blocks.WHITE_STAINED_GLASS.defaultBlockState());
                    }
                    if (blockState.is(Blocks.RED_MUSHROOM_BLOCK) || blockState.is(Blocks.BROWN_MUSHROOM_BLOCK)) {
                        player.level().setBlockAndUpdate(blockPos, Blocks.RED_STAINED_GLASS.defaultBlockState());
                    }
                    if (blockState.is(Blocks.DIRT) || blockState.is(Blocks.STONE)) {
                        player.level().setBlockAndUpdate(blockPos, Blocks.RED_TERRACOTTA.defaultBlockState());
                    }
                }
            }
        }
        return 0;
    }
}
