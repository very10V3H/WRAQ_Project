package com.Very.very.Items.DevelopmentTools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlockFill extends Item {

    public BlockFill(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            int X = player.getBlockX();
            int Z = player.getBlockZ();
            for (int i = X - 20; i <= X + 20; i ++) {
                for (int j = Z - 20; j <= Z + 20; j ++) {
                    BlockPos blockPos = new BlockPos(i,62,j);
                    BlockState blockState = level.getBlockState(blockPos);
                    if (!blockState.getBlock().equals(Blocks.WATER)) {
                        for (int k = 61; k >= 20; k --) {
                            BlockPos blockPos1 = new BlockPos(i,k,j);
                            if (!level.getBlockState(blockPos1).getBlock().equals(Blocks.WATER)) break;
                            level.setBlockAndUpdate(blockPos1,blockState);
                        }
                    }
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}
