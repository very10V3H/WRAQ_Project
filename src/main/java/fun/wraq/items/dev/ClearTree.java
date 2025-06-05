package fun.wraq.items.dev;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class ClearTree extends Item {

    public ClearTree(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        if (!level.isClientSide) {
            int radius = 16;
            for (int i = -radius ; i < radius ; i ++) {
                for (int j = -radius ;  j < radius ; j ++) {
                    for (int k = -radius ; k < radius ; k ++) {
                        BlockPos blockPos = new BlockPos(player.getBlockX() + i, player.getBlockY() + j, player.getBlockZ() + k);
                        String name = level.getBlockState(blockPos).getBlock().getName().toString();
                        if (name.contains("log") || name.contains("leaves")) {
                            level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }
        }
        return super.use(level, player, useHand);
    }
}
