package com.very.wraq.Items.DevelopmentTools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;

public class SakuraChange extends Item {

    public SakuraChange(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            int X = player.getBlockX();
            int Y = player.getBlockY();
            int Z = player.getBlockZ();
            ArrayList<Block> blockStates = new ArrayList<>();
            blockStates.add(Blocks.PINK_STAINED_GLASS_PANE);
            blockStates.add(Blocks.PINK_STAINED_GLASS);
            blockStates.add(Blocks.PINK_WOOL);
            blockStates.add(Blocks.PINK_GLAZED_TERRACOTTA);
            blockStates.add(Blocks.PINK_CONCRETE_POWDER);

            for (int i = X - 50; i <= X + 50; i++) {
                for (int j = Y - 20; j <= Y; j++) {
                    for (int k = Z - 50; k <= Z + 50; k++) {
                        BlockPos blockPos = new BlockPos(i, j, k);
                        if (blockStates.contains(level.getBlockState(blockPos).getBlock())) {
                            level.setBlockAndUpdate(blockPos, Blocks.CHERRY_LEAVES.defaultBlockState());
                        }
                        if (level.getBlockState(blockPos).getBlock().equals(Blocks.OAK_LOG)
                                || level.getBlockState(blockPos).getBlock().equals(Blocks.OAK_WOOD)) {
                            level.setBlockAndUpdate(blockPos, Blocks.CHERRY_LOG.defaultBlockState());
                        }
                    }
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}
