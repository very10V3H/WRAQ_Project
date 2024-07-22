package com.very.wraq.Items.DevelopmentTools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SpongeClear extends Item {

    public SpongeClear(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            int X = player.getBlockX();
            int Z = player.getBlockZ();
            int Y = player.getBlockY();
            for (int i = X - 50; i < X + 50; i++) {
                for (int j = Y - 50; j < Y + 50; j++) {
                    for (int k = Z - 50; k < Z + 50; k++) {
                        BlockPos blockPos = new BlockPos(i, j, k);
                        BlockState blockState = level.getBlockState(blockPos);
                        if (blockState.is(Blocks.SPONGE) || blockState.is(Blocks.WET_SPONGE)) {
                            level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}
