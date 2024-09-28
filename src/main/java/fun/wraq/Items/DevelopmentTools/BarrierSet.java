package fun.wraq.Items.DevelopmentTools;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class BarrierSet extends Item {

    public BarrierSet(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            player.sendSystemMessage(Component.literal("" + player.getBlockX() + player.getBlockY() + player.getBlockZ()));
            double X = player.getBlockX() + 10;
            double Y = player.getBlockY();
            double Z = player.getBlockZ();
            for (int i = 0; i <= 10; i++) {
                for (int j = 0; j <= 8; j++) {
                    level.setBlockAndUpdate(new BlockPos((int) X - i, (int) Y + j, (int) Z - i), Blocks.BARRIER.defaultBlockState());
                }
            }
            X -= 10;
            Z -= 10;
            for (int i = 0; i <= 10; i++) {
                for (int j = 0; j <= 8; j++) {
                    level.setBlockAndUpdate(new BlockPos((int) X - i, (int) Y + j, (int) Z + i), Blocks.BARRIER.defaultBlockState());
                }
            }
            X -= 10;
            Z += 10;
            for (int i = 0; i <= 10; i++) {
                for (int j = 0; j <= 8; j++) {
                    level.setBlockAndUpdate(new BlockPos((int) X + i, (int) Y + j, (int) Z + i), Blocks.BARRIER.defaultBlockState());
                }
            }
            X += 10;
            Z += 10;
            for (int i = 0; i <= 10; i++) {
                for (int j = 0; j <= 8; j++) {
                    level.setBlockAndUpdate(new BlockPos((int) X + i, (int) Y + j, (int) Z - i), Blocks.BARRIER.defaultBlockState());
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}
