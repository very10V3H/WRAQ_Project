package com.very.wraq.Items.DevelopmentTools;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.ResetC2SPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BlockPosReset extends Item {

    public BlockPosReset(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ModNetworking.sendToServer(new ResetC2SPacket());
        if (!level.isClientSide) player.sendSystemMessage(Component.literal("Reset!"));
        return super.use(level, player, interactionHand);
    }
}
