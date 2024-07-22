package com.very.wraq.Items.DevelopmentTools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class IceBarrierSet extends Item {

    public IceBarrierSet(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            player.blockPosition();
            for (int i = player.getBlockY(); i <= 90; i++) {
                level.setBlockAndUpdate(new BlockPos(player.getBlockX(), i, player.getBlockZ()),
                        Blocks.BARRIER.defaultBlockState());
            }
        }
        return super.use(level, player, interactionHand);
    }
}
