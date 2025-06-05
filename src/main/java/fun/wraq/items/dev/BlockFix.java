package fun.wraq.items.dev;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlockFix extends Item {

    public BlockFix(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            int X = player.getBlockX();
            int Z = player.getBlockZ();
            for (int i = X - 20; i <= X + 20; i++) {
                for (int j = Z - 20; j <= Z + 20; j++) {
                    for (int k = 61; k >= 20; k--) {
                        BlockPos blockPos = new BlockPos(i, k, j);
                        BlockState blockState = level.getBlockState(blockPos);
                        if (blockState.getBlock().equals(Blocks.SPRUCE_STAIRS)
                                || blockState.getBlock().equals(Blocks.SPRUCE_SLAB)
                                || blockState.getBlock().equals(Blocks.STRIPPED_SPRUCE_WOOD)
                                || blockState.getBlock().equals(Blocks.SPRUCE_PLANKS)) {
                            level.destroyBlock(blockPos, false);
                        }
                    }
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}
