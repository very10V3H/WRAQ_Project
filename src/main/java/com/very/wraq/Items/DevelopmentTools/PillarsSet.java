package com.very.wraq.Items.DevelopmentTools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class PillarsSet extends Item {

    public PillarsSet(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && player.isShiftKeyDown()) {
            int X = player.getBlockX();
            int Y = player.getBlockY() - 2;
            int Z = player.getBlockZ();
            setBlock(level, new BlockPos(X, Y, Z));
            setBlock(level, new BlockPos(X + 1, Y, Z));
            setBlock(level, new BlockPos(X, Y, Z + 1));
            setBlock(level, new BlockPos(X - 1, Y, Z));
            setBlock(level, new BlockPos(X, Y, Z - 1));
        }
        if (!level.isClientSide && !player.isShiftKeyDown()) {
            if (Math.abs(Math.abs(player.getYRot()) - 90) < 20) {
                int X = player.getBlockX();
                int Y = player.getBlockY() - 1;
                int Z = player.getBlockZ();
                Z += 3;
                setBlock(level, new BlockPos(X, Y, Z));
                setBlock(level, new BlockPos(X + 1, Y, Z));
                setBlock(level, new BlockPos(X, Y, Z + 1));
                setBlock(level, new BlockPos(X - 1, Y, Z));
                setBlock(level, new BlockPos(X, Y, Z - 1));
                Z -= 6;
                setBlock(level, new BlockPos(X, Y, Z));
                setBlock(level, new BlockPos(X + 1, Y, Z));
                setBlock(level, new BlockPos(X, Y, Z + 1));
                setBlock(level, new BlockPos(X - 1, Y, Z));
                setBlock(level, new BlockPos(X, Y, Z - 1));
            } else {
                int X = player.getBlockX();
                int Y = player.getBlockY() - 1;
                int Z = player.getBlockZ();
                X += 3;
                setBlock(level, new BlockPos(X, Y, Z));
                setBlock(level, new BlockPos(X + 1, Y, Z));
                setBlock(level, new BlockPos(X, Y, Z + 1));
                setBlock(level, new BlockPos(X - 1, Y, Z));
                setBlock(level, new BlockPos(X, Y, Z - 1));
                X -= 6;
                setBlock(level, new BlockPos(X, Y, Z));
                setBlock(level, new BlockPos(X + 1, Y, Z));
                setBlock(level, new BlockPos(X, Y, Z + 1));
                setBlock(level, new BlockPos(X - 1, Y, Z));
                setBlock(level, new BlockPos(X, Y, Z - 1));
            }
        }
        return super.use(level, player, interactionHand);
    }

    public boolean BlockIsWaterOrAir(Level level, BlockPos blockPos) {
        return level.getBlockState(blockPos).is(Blocks.WATER) || level.getBlockState(blockPos).is(Blocks.AIR)
                || level.getBlockState(blockPos).is(Blocks.SEAGRASS) || level.getBlockState(blockPos).is(Blocks.KELP)
                || level.getBlockState(blockPos).is(Blocks.KELP_PLANT) || level.getBlockState(blockPos).is(Blocks.TALL_SEAGRASS)
                || level.getBlockState(blockPos).is(Blocks.GRASS) || level.getBlockState(blockPos).is(Blocks.TALL_GRASS)
                || level.getBlockState(blockPos).is(Blocks.GRASS_BLOCK);
    }

    public void setBlock(Level level, BlockPos blockPos) {
        while (true) {
            level.setBlockAndUpdate(blockPos, Blocks.POLISHED_ANDESITE.defaultBlockState());
            blockPos = blockPos.below();
            if (!BlockIsWaterOrAir(level, blockPos)) break;
        }
    }
}
