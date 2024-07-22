package com.very.wraq.Items.DevelopmentTools;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class WaterSet extends Item {

    public WaterSet(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            player.sendSystemMessage(Component.literal("" + player.getBlockX() + player.getBlockY() + player.getBlockZ()));
            int X = player.getBlockX();
            int Y = player.getBlockY();
            int Z = player.getBlockZ() + 9;
            for (int i = 0; i < 19; i++) {
                for (int j = 0; j <= Math.min(i, 18 - i); j++) {
                    BlockPos blockPos = new BlockPos(X - j, Y, Z - i);
                    BlockPos blockPos1 = new BlockPos(X + j, Y, Z - i);
                    if (level.getBlockState(blockPos).getBlock().equals(Blocks.WATER))
                        level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                    else level.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());
                    if (level.getBlockState(blockPos1).getBlock().equals(Blocks.WATER))
                        level.setBlockAndUpdate(blockPos1, Blocks.AIR.defaultBlockState());
                    else level.setBlockAndUpdate(blockPos1, Blocks.WATER.defaultBlockState());
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}
